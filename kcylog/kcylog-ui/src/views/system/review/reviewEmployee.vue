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

      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-plus"
          size="mini"
          v-if="finalHireShow"
          @click="confirmEmployeeInfo"
          >确认最终雇工信息并发起审核</el-button
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
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="雇工列表">
          <el-select
            v-model="employeeName"
            placeholder="请选择"
            @change="selectEmployee"
          >
            <el-option
              v-for="item in reviewEmployeeList"
              :key="item.idCard"
              :label="item.name"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" disabled />
        </el-form-item>
        <el-form-item label="身份证" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证" disabled />
        </el-form-item>
        <el-form-item label="作业时间" prop="workTime">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4 }"
            placeholder="作业时间"
            v-model="form.workTime"
            disabled
          >
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" plain @click="clearTime">清空时间</el-button>
        </el-form-item>
        <el-form-item label="添加时间">
          <el-form-item label="开始时间" prop="startTime">
            <el-date-picker
              clearable
              v-model="form.startTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择工作开始时间"
            >
            </el-date-picker>

            <el-select v-model="startAmPm" placeholder="请选择">
              <el-option label="上午" value="12:00:00"></el-option>
              <el-option label="下午" value="23:59:59"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="结束时间" prop="endTime">
            <el-date-picker
              clearable
              v-model="form.endTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择结束时间"
            >
            </el-date-picker>
            <el-select v-model="endAmPm" placeholder="请选择">
              <el-option label="上午" value="12:00:00"></el-option>
              <el-option label="下午" value="23:59:59"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" plain @click="handleTimeChange"
              >确认</el-button
            >
          </el-form-item>
        </el-form-item>
        <el-form-item label="天数" prop="workDay">
          <el-input v-model="form.workDay" placeholder="天数" disabled />
        </el-form-item>
        <el-form-item label="费用" prop="cost">
          <el-input v-model="form.cost" placeholder="请输入费用" disabled />
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
      :close-on-click-modal="false"
      :close-on-press-escape="false"
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
        <el-button @click="cancelEmployee">取 消</el-button>
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
  confirmEmployee,
} from "@/api/system/reviewEmployee";
import { listReviewEmployee, addReviewEmployee } from "@/api/system/employee";
import { getReview } from "@/api/system/review";

export default {
  name: "Employee",
  data() {
    return {
      money: 100,
      startAmPm: "12:00:00",
      endAmPm: "23:59:59",
      employeeName: "",
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
      workTimeStamp: [],
      finalHireShow: false,
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
        cost: [{ required: true, message: "费用不能为空", trigger: "blur" }],
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
    getReview(this.reviewId).then((response) => {
      if (response.data.finalHire === 0) {
        this.finalHireShow = true;
      }
    });
    this.getList();
    this.getEmployeeList();
  },
  methods: {
    clearTime() {
      this.form.workTime = null;
      this.form.workDay = null;
      this.form.cost = null;
      this.workTimeStamp = [];
    },
    handleTimeChange() {
      if (this.form.startTime != null && this.form.endTime != null) {
        if (
          this.form.startTime + " " + this.startAmPm >
          this.form.endTime + " " + this.endAmPm
        ) {
          this.$message({
            showClose: true,
            message: "开始时间不能大于结束时间～",
            type: "error",
          });
          return;
        }

        let startTimeTemp = "";
        if (this.startAmPm == "23:59:59") {
          startTimeTemp = this.form.startTime + ":下午";
        } else {
          startTimeTemp = this.form.startTime + ":上午";
        }
        let endTimeTemp = "";
        if (this.endAmPm == "23:59:59") {
          endTimeTemp = this.form.endTime + ":下午";
        } else {
          endTimeTemp = this.form.endTime + ":上午";
        }
        if (this.form.workTime == null) {
          this.form.workTime = "";
        }
        this.form.workTime =
          this.form.workTime + startTimeTemp + "~" + endTimeTemp + ";";

        let startAmPmTemp = "";
        if (this.startAmPm == "23:59:59") {
          startAmPmTemp = this.form.startTime + " " + "24:00:00";
        } else {
          startAmPmTemp = this.form.startTime + " " + this.startAmPm;
        }

        let endAmPmTemp = "";
        if (this.endAmPm == "23:59:59") {
          endAmPmTemp = this.form.endTime + " " + "24:00:00";
        } else {
          endAmPmTemp = this.form.endTime + " " + this.endAmPm;
        }
        const date1 = new Date(startAmPmTemp);
        const timestamp1 = Date.parse(startAmPmTemp);

        const date2 = new Date(endAmPmTemp);
        const timestamp2 = Date.parse(endAmPmTemp);

        const diffInMs = date2 - date1;
        const diffInHours = diffInMs / (1000 * 60 * 60);
        if (this.form.workDay == null) {
          this.form.workDay = 0;
        }
        this.form.workDay = this.form.workDay + (diffInHours / 12 + 1) * 0.5;
        if (this.form.cost == null) {
          this.form.cost = 0;
        }
        this.form.cost = (this.form.workDay / 0.5) * this.money;
        let workTime = {};
        workTime.startTime = timestamp1 / 1000;
        workTime.endTime = timestamp2 / 1000;
        this.workTimeStamp.push(workTime);
        this.form.workTimeStamp = this.workTimeStamp;
      }
    },
    selectEmployee(employee) {
      this.employeeName = employee.name;
      this.form.name = employee.name;
      this.form.idCard = employee.idCard;
    },
    submitEmployeeForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          addReviewEmployee(this.employeeForm).then((response) => {
            this.$modal.msgSuccess("新增成功");
            this.employeeOpen = false;
            this.getEmployeeList();
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
    // 取消按钮
    cancelEmployee() {
      this.open = false;
      this.employeeForm = {
        name: null,
        idCard: null,
      };
      this.resetForm("employeeForm");
      this.employeeOpen = false;
    },
    // 表单重置
    reset() {
      this.workTimeStamp = [];
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
      this.employeeName = "";
      this.title = "添加雇工实际工作内容记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const reviewEmployeeId = row.reviewEmployeeId || this.ids;
      getEmployee(reviewEmployeeId).then((response) => {
        this.form = response.data;
        const workTimeArr = response.data.workTime.split(";");

        for (let i = 0; i < workTimeArr.length - 1; i++) {
          const timeArr = workTimeArr[i].split("~");
          const timeArr1 = timeArr[0].split(":");
          const timeArr2 = timeArr[1].split(":");

          let startAmPmTemp1 = "";
          if (timeArr1[1] == "上午") {
            startAmPmTemp1 = timeArr1[0] + " " + "12:00:00";
          } else {
            startAmPmTemp1 = timeArr1[0] + " " + "24:00:00";
          }

          let endAmPmTemp1 = "";
          if (timeArr2[1] == "上午") {
            endAmPmTemp1 = timeArr2[0] + " " + "12:00:00";
          } else {
            endAmPmTemp1 = timeArr2[0] + " " + "24:00:00";
          }

          const timestampSelf1 = Date.parse(startAmPmTemp1);

          const timestampSelf2 = Date.parse(endAmPmTemp1);

          let workTimeObj = {};
          workTimeObj.startTime = timestampSelf1 / 1000;
          workTimeObj.endTime = timestampSelf2 / 1000;
          this.workTimeStamp.push(workTimeObj);
        }
        this.employeeName = "";
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
    confirmEmployeeInfo() {
      if (this.employeeList.length == 0) {
        this.$modal.msgError("提交失败,请填写实际的雇工信息～");
        return;
      }
      const reviewId = this.reviewId;
      this.$modal
        .confirm("是否确认发起审核，一旦确认将不可修改该审核单的雇工信息～")
        .then(function () {
          return confirmEmployee(reviewId);
        })
        .then(() => {
          this.finalHireShow = false;
          this.getList();
          this.$modal.msgSuccess("提交成功～");
        })
        .catch(() => {});
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
