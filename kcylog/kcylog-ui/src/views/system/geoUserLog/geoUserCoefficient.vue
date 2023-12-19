<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="用户名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >新增</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="coefficientList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="用户名称" align="center" prop="name" />
      <el-table-column label="系数" align="center" prop="coefficient" />
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

    <!-- 添加或修改人员系数对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名称" prop="userId">
          <el-select v-model="form.userId" filterable placeholder="请选择">
            <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="个人系数" prop="coefficient">
          <el-input-number
            v-model="form.coefficient"
            :precision="2"
            :step="0.1"
            :min="0"
          ></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listCoefficient,
  getCoefficient,
  delCoefficient,
  addCoefficient,
  updateCoefficient,
} from "@/api/system/geoUserCoefficient";
import { listUser } from "@/api/system/user";

export default {
  name: "Coefficient",
  data() {
    return {
      userList: [],
      dateRange: [],
      queryUserParams: {
        pageNum: 1,
        pageSize: 9999,
        deptId: null,
      },
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
      // 人员系数表格数据
      coefficientList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        userId: null,
        coefficient: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [{ required: true, message: "请选择用户", trigger: "blur" }],
        coefficient: [
          { required: true, message: "系数不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    listUser(this.addDateRange(this.queryUserParams, this.dateRange)).then(
      (response) => {
        this.userList = response.rows;
      }
    );
    this.getList();
  },
  methods: {
    /** 查询人员系数列表 */
    getList() {
      this.loading = true;
      listCoefficient(this.queryParams).then((response) => {
        this.coefficientList = response.rows;
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
        coefficientId: null,
        name: null,
        userId: null,
        coefficient: null,
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
      this.ids = selection.map((item) => item.coefficientId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加人员系数";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const coefficientId = row.coefficientId || this.ids;
      getCoefficient(coefficientId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改人员系数";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.coefficientId != null) {
            updateCoefficient(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCoefficient(this.form).then((response) => {
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
      const coefficientIds = row.coefficientId || this.ids;
      this.$modal
        .confirm('是否确认删除人员系数编号为"' + coefficientIds + '"的数据项？')
        .then(function () {
          return delCoefficient(coefficientIds);
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
        "system/coefficient/export",
        {
          ...this.queryParams,
        },
        `coefficient_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
