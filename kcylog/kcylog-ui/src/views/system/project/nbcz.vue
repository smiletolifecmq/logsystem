<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="mini"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="项目编号" prop="projectNum">
        <el-input
          v-model="queryParams.projectNum"
          placeholder="请输入项目编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作业部门" prop="department">
        <el-select
          v-model="queryParams.department"
          placeholder="请选择部门"
          clearable
        >
          <el-option
            v-for="item in deptList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="办结日期">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="monthrange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="handleQuery"
        ></el-date-picker>
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          >导出</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="projectList"
      @selection-change="handleSelectionChange"
      size="mini"
    >
      <el-table-column
        width="200px"
        label="委托单位"
        align="center"
        prop="requesterAlias"
      />
      <el-table-column
        width="200px"
        label="项目名称"
        align="center"
        prop="projectNameAlias"
      />
      <el-table-column label="项目编号" align="center" prop="projectNum" />
      <el-table-column label="项目类型" align="center" prop="projectType" />
      <el-table-column label="工程负责人" align="center" prop="userNameAlias" />
      <el-table-column label="作业部门" align="center" prop="department" />
      <!-- <el-table-column label="项目类型" align="center" prop="projectType" />
        <el-table-column
          label="工程内容"
          align="center"
          prop="workcontentAlias"
        /> -->

      <el-table-column label="项目预算金额" align="center" prop="ygmoney">
        <template slot-scope="scope">
          {{ scope.row.ygmoney }}
        </template></el-table-column
      >
      <el-table-column label="办结时间" align="center" prop="ygbjtime">
        <template slot-scope="scope">
          {{ formatDateDAY(scope.row.ygbjtime) }}
        </template></el-table-column
      >
      <el-table-column label="计算时间" align="center" prop="ygtime">
        <template slot-scope="scope">
          {{ formatDate(scope.row.ygtime) }}
        </template></el-table-column
      >

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
            @click="handleNbczbj(scope.row)"
            v-hasPermi="['system:project:nbczbj']"
            >允许编辑</el-button
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
  </div>
</template>
<style>
.hover-effect:hover {
  cursor: pointer;
}
</style>
<script>
import {
  listProjectWaitOneCheck,
  getProject,
  delProject,
  addProject,
  updateProjectYgmoney,
  nbcz,
  updateProjectCqBz,
  updateCad,
  updateProjectNbcz,
} from "@/api/system/project";
import elDragDialog from "@/api/components/el-drag";
import { listUnit } from "@/api/system/unit";
import { addReview, setReviewStatus } from "@/api/system/reviewSub";
import userInfo from "@/store/modules/user";
import FileUpload from "@/components/FileCad";

export default {
  name: "Project",
  directives: {
    elDragDialog,
  },
  components: {
    FileUpload,
  },
  props: {
    fileType: {
      type: Array,
      default: () => ["dwg"],
    },
  },
  data() {
    return {
      cqxmtitle: "",
      lxValue: [],
      options: [
        {
          value: 1,
          label: "测图类",
          children: [
            {
              value: 1,
              label: "售图项目",
            },
            {
              value: 2,
              label: "地籍图(宗地图)",
            },
            {
              value: 3,
              label: "道路竣工(地形图)",
            },
            {
              value: 4,
              label: "规划建筑竣工(地形图)",
            },
            {
              value: 5,
              label: "园林竣工(测图)",
            },
          ],
        },
        {
          value: 2,
          label: "工程类",
          children: [
            {
              value: 6,
              label: "土方测量",
            },
            {
              value: 7,
              label: "道路测量(含河道)",
            },
            {
              value: 8,
              label: "管线探测",
            },
          ],
        },
      ],
      cadstatus: false,
      fwxopen: false,
      fwxform: {},
      formReviewCq: {},
      cqOpen: false,
      overTimeProjectList: [],
      overTimeOpen: false,
      projectIdMap: {},
      listProjectLocalSelected: [],
      listProjectLocalMap: {},
      winUnits: [],
      startAmPm: "12:00:00",
      endAmPm: "23:59:59",
      activeNamesReviewSub: ["1", "2", "3"],
      formReviewSub: {},
      rulesReviewSub: {
        serialNum: [
          { required: true, message: "编号不能为空", trigger: "blur" },
        ],
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" },
        ],
        requester: [
          { required: true, message: "委托单位不能为空", trigger: "blur" },
        ],
      },
      titleReviewSub: "",
      openReviewSub: false,
      statusArr: [
        {
          value: 2,
          label: "一检办结",
        },
        {
          value: 3,
          label: "二检办结",
        },
      ],
      outputStatusList: [
        {
          value: 0,
          label: "待填写",
        },
        {
          value: 1,
          label: "待结算",
        },
        {
          value: 2,
          label: "已结算",
        },
      ],
      deptList: [
        {
          value: "地理信息部",
          label: "地理信息部",
        },
        {
          value: "工程测绘部",
          label: "工程测绘部",
        },
        {
          value: "管线工程部",
          label: "管线工程部",
        },
        {
          value: "不动产测绘部",
          label: "不动产测绘部",
        },
        {
          value: "测绘工程一部",
          label: "测绘工程一部",
        },
        {
          value: "测绘工程二部",
          label: "测绘工程二部",
        },
        {
          value: "测绘工程三部",
          label: "测绘工程三部",
        },
        {
          value: "测绘工程一部1组",
          label: "测绘工程一部1组",
        },
        {
          value: "测绘工程一部2组",
          label: "测绘工程一部2组",
        },
        {
          value: "测绘工程二部1组",
          label: "测绘工程二部1组",
        },
        {
          value: "测绘工程二部2组",
          label: "测绘工程二部2组",
        },
      ],
      money: 100,
      dateRange: [],
      activeNames: ["1", "2", "3", "4"],
      detailOpen: false,
      detailTitle: "项目详情",
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
        department: "",
        outputStatus: null,
        status: null,
      },
      queryStatisticsParams: {
        pageNum: 1,
        pageSize: 9999,
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
    handleNbczbj(value) {
      this.$confirm("此操作将允许用户编辑该项目预算金额, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          updateProjectNbcz({ projectId: value.projectId }).then((response) => {
            this.$message({
              type: "success",
              message: "成功!",
            });
            this.getList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },
    formatDate(dateString) {
      if (dateString == "" || dateString == null || dateString == undefined) {
        return "";
      }
      const dateObject = new Date(dateString);
      const year = dateObject.getFullYear();
      const month = dateObject.getMonth() + 1;
      const day = dateObject.getDate();
      return `${year}-${(month < 10 ? "0" : "") + month}`;
    },

    formatDateDAY(dateString) {
      if (dateString == "" || dateString == null || dateString == undefined) {
        return "";
      }
      const dateObject = new Date(dateString);
      const year = dateObject.getFullYear();
      const month = dateObject.getMonth() + 1;
      const day = dateObject.getDate();
      return `${year}-${(month < 10 ? "0" : "") + month} - ${day}`;
    },

    /** 查询项目列表 */
    getList() {
      this.loading = true;
      nbcz(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.projectList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.detailOpen = false;
      this.fwxopen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.lxValue = [];
      if (this.$refs.fileUploadModule != null) {
        this.$refs.fileUploadModule.number = 0;
        this.$refs.fileUploadModule.uploadList = [];
        this.$refs.fileUploadModule.fileList = [];
      }
      this.fwxform = {};
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
      this.dateRange = [];
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

    handleDetail(row) {
      this.reset();
      const projectId = row.projectId || this.ids;
      getProject(projectId).then((response) => {
        this.form = response.data;
        this.detailOpen = true;
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.projectId != null) {
            updateProjectYgmoney(this.form).then((response) => {
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
        "system/project/exportNbcz",
        {
          ...this.queryParams,
        },
        `项目预算信息_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
