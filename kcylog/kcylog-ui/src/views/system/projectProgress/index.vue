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
      <el-form-item label="项目编号" prop="itemNumber">
        <el-input
          v-model="queryParams.itemNumber"
          placeholder="请项目编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业主单位" prop="ownerUnit">
        <el-input
          v-model="queryParams.ownerUnit"
          placeholder="请输入业主单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
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
          v-hasPermi="['system:progress:add']"
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
      :data="progressList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="项目编号" align="center" prop="itemNumber" />
      <el-table-column label="业主单位" align="center" prop="ownerUnit" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="项目负责人" align="center" prop="user.userName" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="160"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
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
            v-hasPermi="['system:progress:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:progress:remove']"
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

    <!-- 添加或修改项目进度管控对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目编号" prop="itemNumber">
          <el-input v-model="form.itemNumber" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="业主单位" prop="ownerUnit">
          <el-input v-model="form.ownerUnit" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="负责人" prop="userId">
          <el-autocomplete
            v-model="selectedUserName"
            :fetch-suggestions="querySearchUser"
            placeholder="请选择项目负责人"
            @select="selectUser"
            clearable
          ></el-autocomplete>
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
  listProgress,
  getProgress,
  delProgress,
  addProgress,
  updateProgress,
} from "@/api/system/projectProgress";
import { listUser } from "@/api/system/user";

export default {
  name: "Progress",
  data() {
    return {
      selectedUserName: "",
      restaurants: [],
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
      // 项目进度管控表格数据
      progressList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemNumber: null,
        ownerUnit: null,
        projectName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemNumber: [
          { required: true, message: "项目编号不能为空", trigger: "blur" },
        ],
        ownerUnit: [
          { required: true, message: "业主单位不能为空", trigger: "blur" },
        ],
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" },
        ],
        userId: [
          { required: true, message: "项目负责人不能为空", trigger: "change" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.loadAllUsers();
  },
  methods: {
    querySearchUser(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString
        ? restaurants.filter(this.createStateFilter(queryString))
        : restaurants;
      cb(results);
    },
    selectUser(item) {
      this.form.userId = item.userId;
    },
    createStateFilter(queryString) {
      return (state) => {
        return (
          state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    loadAllUsers() {
      listUser(this.queryUserParams).then((response) => {
        for (let i = 0; i < response.rows.length; i++) {
          let userObject = {
            value: "",
            userId: 0,
          };
          userObject.value = response.rows[i].userName;
          userObject.userId = response.rows[i].userId;
          this.restaurants.push(userObject);
        }
      });
    },
    /** 查询项目进度管控列表 */
    getList() {
      this.loading = true;
      listProgress(this.queryParams).then((response) => {
        this.progressList = response.rows;
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
        progressId: null,
        itemNumber: null,
        ownerUnit: null,
        projectName: null,
        userId: null,
        viewPeople: null,
        sharpening: null,
        bidding: null,
        tender: null,
        contract: null,
        designBook: null,
        jobProcess: null,
        workSummary: null,
        inspectionReport: null,
        initialTest: null,
        finalInspection: null,
        createTime: null,
        updateTime: null,
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
      this.ids = selection.map((item) => item.progressId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加项目进度管控";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const progressId = row.progressId || this.ids;
      getProgress(progressId).then((response) => {
        this.form = response.data;
        this.form.userId = response.data.userId;
        this.selectedUserName = response.data.user.userName;
        this.open = true;
        this.title = "修改项目进度管控";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.progressId != null) {
            updateProgress(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProgress(this.form).then((response) => {
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
      const progressIds = row.progressId || this.ids;
      this.$modal
        .confirm(
          '是否确认删除项目进度管控编号为"' + progressIds + '"的数据项？'
        )
        .then(function () {
          return delProgress(progressIds);
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
        "system/progress/export",
        {
          ...this.queryParams,
        },
        `progress_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
