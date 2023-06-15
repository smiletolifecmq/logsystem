<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >新增雇佣信息</el-button
        >
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="addNewEmployee"
          >新增雇工人员</el-button
        >
      </el-col>

      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="employeeList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="身份证" align="center" prop="idCard" />
      <el-table-column label="作业时间" align="center" prop="workTime" />
      <el-table-column label="天数" align="center" prop="workDay" />
      <el-table-column label="费用" align="center" prop="cost" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改雇工实际工作内容记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="身份证" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证" />
        </el-form-item>
        <el-form-item label="作业时间" prop="workTime">
          <el-input v-model="form.workTime" placeholder="请输入作业时间" />
        </el-form-item>
        <el-form-item label="天数" prop="workDay">
          <el-input v-model="form.workDay" placeholder="请输入天数" />
        </el-form-item>
        <el-form-item label="费用" prop="cost">
          <el-input v-model="form.cost" placeholder="请输入费用" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改雇工对话框 -->
    <el-dialog
      :title="employeeTitle"
      :visible.sync="employeeOpen"
      width="500px"
      append-to-body
    >
      <el-form
        ref="form"
        :model="employeeForm"
        :rules="employeeRules"
        label-width="80px"
      >
        <el-form-item label="姓名" prop="name">
          <el-input v-model="employeeForm.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="身份证" prop="idCard">
          <el-input v-model="employeeForm.idCard" placeholder="请输入身份证" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitEmployeeForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listEmployee,
  getEmployee,
  delEmployee,
  addEmployee,
  updateEmployee,
} from "@/api/system/reviewEmployee";
import { listReviewEmployee, addReviewEmployee } from "@/api/system/employee";

export default {
  name: "Employee",
  data() {
    return {
      employeeTitle: "",
      // 雇工表格数据
      reviewEmployeeList: [],
      // 查询参数
      queryEmployeeParams: {
        pageNum: 1,
        pageSize: 9999,
      },
      reviewId: 0,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      employeeOpen: false,
      employeeForm: {},
      // 雇工实际工作内容记录表格数据
      employeeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        idCard: null,
        workTime: null,
        workDay: null,
        cost: null,
        reviewId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        idCard: [
          { required: true, message: "身份证不能为空", trigger: "blur" },
        ],
        workTime: [
          { required: true, message: "作业时间不能为空", trigger: "blur" },
        ],
        workDay: [{ required: true, message: "天数不能为空", trigger: "blur" }],
      },
      employeeRules: {
        name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        idCard: [
          { required: true, message: "身份证不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.reviewId = this.$route.params && this.$route.params.reviewId;
    this.getList();
    this.getEmployeeList();
  },
  methods: {
    submitEmployeeForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          addReviewEmployee(this.employeeForm).then((response) => {
            this.$modal.msgSuccess("新增成功");
            this.employeeOpen = false;
          });
        }
      });
    },
    getEmployeeList() {
      listReviewEmployee(this.queryEmployeeParams).then((response) => {
        this.reviewEmployeeList = response.rows;
      });
    },
    addNewEmployee() {
      this.employeeTitle = "新增雇工人员";
      this.employeeForm = {
        name: null,
        idCard: null,
      };
      this.resetForm("employeeForm");
      this.employeeOpen = true;
    },
    /** 查询雇工实际工作内容记录列表 */
    getList() {
      this.loading = true;
      this.queryParams.reviewId = this.reviewId;
      listEmployee(this.queryParams).then((response) => {
        this.employeeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        reviewEmployeeId: null,
        name: null,
        idCard: null,
        workTime: null,
        workDay: null,
        cost: null,
        reviewId: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.reviewEmployeeId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加雇工实际工作内容记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const reviewEmployeeId = row.reviewEmployeeId || this.ids;
      getEmployee(reviewEmployeeId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改雇工实际工作内容记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.reviewEmployeeId != null) {
            updateEmployee(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.reviewId = this.reviewId;
            addEmployee(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const reviewEmployeeIds = row.reviewEmployeeId || this.ids;
      this.$modal
        .confirm(
          '是否确认删除雇工实际工作内容记录编号为"' +
            reviewEmployeeIds +
            '"的数据项？'
        )
        .then(function () {
          return delEmployee(reviewEmployeeIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "system/employee/export",
        {
          ...this.queryParams,
        },
        `employee_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
