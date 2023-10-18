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
      <el-form-item label="二检时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
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
      <el-table-column label="工作量" align="center" prop="workloadAlias" />
      <el-table-column
        label="工程内容"
        align="center"
        prop="workcontentAlias"
      />
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
      <el-table-column
        label="安排结束时间"
        align="center"
        prop="projectEndAlias"
      />
      <el-table-column label="二检时间" align="center" prop="twoCheckTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.twoCheckTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
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
            icon="el-icon-tickets"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:project:detail']"
            >详情</el-button
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
        <el-form-item label="项目类型" prop="projectType">
          <el-input v-model="form.projectType" placeholder="请输入项目类型" />
        </el-form-item>
        <el-form-item label="登记时间" prop="registerTime">
          <el-input v-model="form.registerTime" placeholder="请输入登记时间" />
        </el-form-item>
        <el-form-item label="接待人" prop="receptionist">
          <el-input v-model="form.receptionist" placeholder="请输入接待人" />
        </el-form-item>
        <el-form-item label="工作量" prop="workloadAlias">
          <el-input
            v-model="form.workloadAlias"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="工程内容" prop="workcontentAlias">
          <el-input
            v-model="form.workcontentAlias"
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

    <el-dialog
      :title="detailTitle"
      :visible.sync="detailOpen"
      width="1260px"
      append-to-body
      v-el-drag-dialog
    >
      <el-collapse v-model="activeNames">
        <el-collapse-item title="项目基本信息" name="1">
          <el-descriptions class="margin-top" :column="4" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                项目名称
              </template>
              {{ form.projectNameAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                项目编号
              </template>
              {{ form.projectNum }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-notebook-2"></i>
                项目类型
              </template>
              {{ form.projectType }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-money"></i>
                项目金额
              </template>
              {{ form.projectMoneyAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document"></i>
                工作量
              </template>
              {{ form.workloadAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document"></i>
                工程内容
              </template>
              {{ form.workcontentAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                工程负责人
              </template>
              {{ form.userNameAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-home"></i>
                委托单位
              </template>
              {{ form.requesterAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                登记时间
              </template>
              {{ form.registerTime }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                安排开始时间
              </template>
              {{ form.projectStartAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                安排结束时间
              </template>
              {{ form.projectEndAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                一检时间
              </template>
              {{ form.oneCheck }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                二检时间
              </template>
              {{ form.twoCheck }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                通知出件时间
              </template>
              {{ form.noticeTime }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                项目出件时间
              </template>
              {{ form.projectTime }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                送达时间
              </template>
              {{ form.deliveryTime }}
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
        <el-collapse-item title="经营产值" name="2">
          <el-descriptions class="margin-top" :column="2" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                经营产值
              </template>
              <span v-if="form.operate != 0.0">{{ form.operate }}</span>
              <span v-if="form.operate == 0.0" style="color: red"
                >暂未填写经营产值</span
              >
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
        <el-collapse-item title="产值结算" name="3">
          <el-table :data="form.projectValue" border style="width: 100%">
            <el-table-column prop="userName" label="用户名" style="width: 100%">
            </el-table-column>
            <el-table-column
              prop="proportion"
              label="占比(%)"
              style="width: 100%"
            >
            </el-table-column>
          </el-table>
        </el-collapse-item>
        <el-collapse-item title="车辆使用登记" name="4">
          <el-table :data="form.projectCar" stripe style="width: 100%">
            <el-table-column prop="recordTime" label="日期">
              <template slot-scope="scope">
                <span>{{
                  parseTime(scope.row.recordTime, "{y}-{m}-{d}")
                }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="projectName" label="项目编号">
            </el-table-column>
            <el-table-column prop="carNum" label="车牌号"> </el-table-column>
            <el-table-column prop="carType" label="用车类型"> </el-table-column>
            <el-table-column prop="number" label="次数"> </el-table-column>
            <el-table-column prop="carExpenses" label="费用"> </el-table-column>
          </el-table>
        </el-collapse-item>
      </el-collapse>
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
import elDragDialog from "@/api/components/el-drag";

export default {
  name: "Project",
  directives: {
    elDragDialog,
  },
  data() {
    return {
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
      listProject(this.addDateRange(this.queryParams, this.dateRange)).then(
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
