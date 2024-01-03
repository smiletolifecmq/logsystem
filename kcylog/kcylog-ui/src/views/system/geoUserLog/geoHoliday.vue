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
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
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

    <el-table v-loading="loading" :data="holidayList">
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="开始时间" align="center" prop="start" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.start, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="end" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.end, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="天数" align="center" prop="day" />
      <el-table-column label="备注" align="center" prop="remark" />
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

    <!-- 添加或修改地理员工请假对话框 -->
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
        <el-form-item label="开始时间" prop="start">
          <el-date-picker
            clearable
            v-model="form.start"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开始时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="end">
          <el-date-picker
            clearable
            v-model="form.end"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择结束时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="天数" prop="day">
          <el-input v-model="form.day" placeholder="请输入天数" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          />
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
  listHoliday,
  getHoliday,
  delHoliday,
  addHoliday,
  updateHoliday,
} from "@/api/system/geoHoliday";
import { listUser } from "@/api/system/user";

export default {
  name: "Holiday",
  data() {
    return {
      dateRange: [],
      queryUserParams: {
        pageNum: 1,
        pageSize: 9999,
        deptId: null,
      },
      userList: [],
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
      // 地理员工请假表格数据
      holidayList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userName: null,
        start: null,
        end: null,
        day: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [{ required: true, message: "请选择用户", trigger: "blur" }],
        start: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
        ],
        end: [{ required: true, message: "结束时间不能为空", trigger: "blur" }],
        day: [{ required: true, message: "天数不能为空", trigger: "blur" }],
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
    /** 查询地理员工请假列表 */
    getList() {
      this.loading = true;
      listHoliday(this.queryParams).then((response) => {
        this.holidayList = response.rows;
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
        holidayId: null,
        userId: null,
        userName: null,
        start: null,
        end: null,
        day: null,
        remark: null,
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
      this.ids = selection.map((item) => item.holidayId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      var currentDate = new Date();
      var year = currentDate.getFullYear();
      var month = ("0" + (currentDate.getMonth() + 1)).slice(-2); // +1 是因为月份从 0 开始计数
      var day = ("0" + currentDate.getDate()).slice(-2);
      var formattedDate = year + "-" + month + "-" + day;
      this.form.start = formattedDate;
      this.form.end = formattedDate;
      this.open = true;
      this.title = "添加请假信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const holidayId = row.holidayId || this.ids;
      getHoliday(holidayId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改请假信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.holidayId != null) {
            updateHoliday(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHoliday(this.form).then((response) => {
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
      const holidayIds = row.holidayId || this.ids;
      this.$modal
        .confirm('是否确认删除地理员工请假编号为"' + holidayIds + '"的数据项？')
        .then(function () {
          return delHoliday(holidayIds);
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
        "system/holiday/export",
        {
          ...this.queryParams,
        },
        `holiday_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
