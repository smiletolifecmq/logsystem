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
      <el-form-item label="项目名称" prop="projectNameAlias">
        <el-input
          v-model="queryParams.projectNameAlias"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目编号" prop="projectNum">
        <el-input
          v-model="queryParams.projectNum"
          placeholder="请输入项目编号"
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
          v-hasPermi="['system:project:add']"
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
      :data="projectList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        label="项目名称"
        align="center"
        prop="projectNameAlias"
      />
      <el-table-column label="项目编号" align="center" prop="projectNum" />
      <el-table-column label="项目类型" align="center" prop="projectType" />
      <el-table-column label="工程内容" align="center" prop="workloadAlias" />
      <el-table-column label="工程负责人" align="center" prop="userNameAlias" />
      <el-table-column label="登记时间" align="center" prop="registerTime" />
      <el-table-column label="接待人" align="center" prop="receptionist" />
      <el-table-column label="委托单位" align="center" prop="requesterAlias" />
      <el-table-column
        label="安排开始时间"
        align="center"
        prop="projectStartAlias"
      />
      <el-table-column
        label="安排结束时间"
        align="center"
        prop="projectEndAlias"
      />
      <!-- <el-table-column label="一检时间" align="center" prop="oneCheck" />
      <el-table-column label="二检时间" align="center" prop="twoCheck" />
      <el-table-column label="通知出件时间" align="center" prop="noticeTime" />
      <el-table-column label="项目出件时间" align="center" prop="projectTime" />
      <el-table-column label="送达时间" align="center" prop="deliveryTime" />
      <el-table-column label="项目金额" align="center" prop="porjectMoney" /> -->
      <!-- <el-table-column label="经营产值" align="center" prop="operate" /> -->
      <!-- <el-table-column
        label="填写经营产值人"
        align="center"
        prop="operateUser"
      />
      <el-table-column
        label="填写经营产值时间"
        align="center"
        prop="operateTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.operateTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column> -->
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
            v-hasPermi="['system:project:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:project:remove']"
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

    <!-- 添加或修改项目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="projectNameAlias">
          <el-input
            v-model="form.projectNameAlias"
            placeholder="请输入项目名称"
          />
        </el-form-item>
        <el-form-item label="项目编号" prop="projectNum">
          <el-input v-model="form.projectNum" placeholder="请输入项目编号" />
        </el-form-item>
        <el-form-item label="登记时间" prop="registerTime">
          <el-input v-model="form.registerTime" placeholder="请输入登记时间" />
        </el-form-item>
        <el-form-item label="接待人" prop="receptionist">
          <el-input v-model="form.receptionist" placeholder="请输入接待人" />
        </el-form-item>
        <el-form-item label="工程内容" prop="workloadAlias">
          <el-input
            v-model="form.workloadAlias"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="工程负责人" prop="userNameAlias">
          <el-input
            v-model="form.userNameAlias"
            placeholder="请输入工程负责人"
          />
        </el-form-item>
        <el-form-item label="委托单位" prop="requesterAlias">
          <el-input
            v-model="form.requesterAlias"
            placeholder="请输入委托单位"
          />
        </el-form-item>
        <el-form-item label="安排开始时间" prop="projectStartAlias">
          <el-input
            v-model="form.projectStartAlias"
            placeholder="请输入安排开始时间"
          />
        </el-form-item>
        <el-form-item label="安排结束时间" prop="projectEndAlias">
          <el-input
            v-model="form.projectEndAlias"
            placeholder="请输入安排结束时间"
          />
        </el-form-item>
        <el-form-item label="一检时间" prop="oneCheck">
          <el-input v-model="form.oneCheck" placeholder="请输入一检时间" />
        </el-form-item>
        <el-form-item label="二检时间" prop="twoCheck">
          <el-input v-model="form.twoCheck" placeholder="请输入二检时间" />
        </el-form-item>
        <el-form-item label="通知出件时间" prop="noticeTime">
          <el-input
            v-model="form.noticeTime"
            placeholder="请输入通知出件时间"
          />
        </el-form-item>
        <el-form-item label="项目出件时间" prop="projectTime">
          <el-input
            v-model="form.projectTime"
            placeholder="请输入项目出件时间"
          />
        </el-form-item>
        <el-form-item label="送达时间" prop="deliveryTime">
          <el-input v-model="form.deliveryTime" placeholder="请输入送达时间" />
        </el-form-item>
        <el-form-item label="项目金额" prop="projectMoneyAlias">
          <el-input
            v-model="form.projectMoneyAlias"
            placeholder="请输入项目金额"
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
  listProject,
  getProject,
  delProject,
  addProject,
  updateProject,
} from "@/api/system/project";

export default {
  name: "Project",
  data() {
    return {
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
      // 项目表格数据
      projectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectNameAlias: null,
        projectNum: null,
        projectType: null,
        registerTime: null,
        receptionist: null,
        workloadAlias: null,
        userNameAlias: null,
        requesterAlias: null,
        projectStartAlias: null,
        projectEndAlias: null,
        oneCheck: null,
        twoCheck: null,
        noticeTime: null,
        projectTime: null,
        deliveryTime: null,
        projectMoneyAlias: null,
        operate: null,
        operateUser: null,
        operateTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        projectNameAlias: [
          { required: true, message: "项目名称不能为空", trigger: "blur" },
        ],
        projectNum: [
          { required: true, message: "项目编号不能为空", trigger: "blur" },
        ],
        projectType: [
          { required: true, message: "项目类型不能为空", trigger: "change" },
        ],
        registerTime: [
          { required: true, message: "登记时间不能为空", trigger: "blur" },
        ],
        receptionist: [
          { required: true, message: "接待人不能为空", trigger: "blur" },
        ],
        userNameAlias: [
          { required: true, message: "工程负责人不能为空", trigger: "blur" },
        ],
        requesterAlias: [
          { required: true, message: "委托单位不能为空", trigger: "blur" },
        ],
        projectStartAlias: [
          { required: true, message: "安排开始时间不能为空", trigger: "blur" },
        ],
        projectEndAlias: [
          { required: true, message: "安排结束时间不能为空", trigger: "blur" },
        ],
        oneCheck: [
          { required: true, message: "一检时间不能为空", trigger: "blur" },
        ],
        twoCheck: [
          { required: true, message: "二检时间不能为空", trigger: "blur" },
        ],
        noticeTime: [
          { required: true, message: "通知出件时间不能为空", trigger: "blur" },
        ],
        projectTime: [
          { required: true, message: "项目出件时间不能为空", trigger: "blur" },
        ],
        deliveryTime: [
          { required: true, message: "送达时间不能为空", trigger: "blur" },
        ],
        projectMoneyAlias: [
          { required: true, message: "项目金额不能为空", trigger: "blur" },
        ],
        operate: [
          { required: true, message: "经营产值不能为空", trigger: "blur" },
        ],
        operateUser: [
          {
            required: true,
            message: "填写经营产值人不能为空",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询项目列表 */
    getList() {
      this.loading = true;
      listProject(this.queryParams).then((response) => {
        this.projectList = response.rows;
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
        projectId: null,
        projectNameAlias: null,
        projectNum: null,
        projectType: null,
        registerTime: null,
        receptionist: null,
        workloadAlias: null,
        userNameAlias: null,
        requesterAlias: null,
        projectStartAlias: null,
        projectEndAlias: null,
        oneCheck: null,
        twoCheck: null,
        noticeTime: null,
        projectTime: null,
        deliveryTime: null,
        projectMoneyAlias: null,
        operate: null,
        operateUser: null,
        operateTime: null,
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
      this.ids = selection.map((item) => item.projectId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加项目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const projectId = row.projectId || this.ids;
      getProject(projectId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改项目";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.projectId != null) {
            updateProject(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProject(this.form).then((response) => {
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
      const projectIds = row.projectId || this.ids;
      this.$modal
        .confirm('是否确认删除项目编号为"' + projectIds + '"的数据项？')
        .then(function () {
          return delProject(projectIds);
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
        "system/project/export",
        {
          ...this.queryParams,
        },
        `project_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
