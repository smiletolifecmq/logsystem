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
      <el-form-item label="接待人" prop="receptionist">
        <el-input
          v-model="queryParams.receptionist"
          placeholder="请输入接待人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="userNameAlias">
        <el-input
          v-model="queryParams.userNameAlias"
          placeholder="请输入项目负责人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="二检时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          type="daterange"
          value-format="yyyy-MM-dd"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="经营产值" prop="operateUser">
        <el-select
          v-model="queryParams.operateUser"
          placeholder="请选择是否已填写"
          clearable
        >
          <el-option
            v-for="item in operates"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
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
      <el-table-column label="委托单位" align="center" prop="requesterAlias" />
      <el-table-column
        label="项目名称"
        align="center"
        prop="projectNameAlias"
      />
      <el-table-column label="项目编号" align="center" prop="projectNum" />
      <el-table-column label="项目类型" align="center" prop="projectType" />
      <el-table-column label="工程负责人" align="center" prop="userNameAlias" />
      <el-table-column label="登记时间" align="center" prop="registerTime">
        <template slot-scope="scope">
          {{ formatDate(scope.row.registerTime) }}
        </template>
      </el-table-column>
      <el-table-column label="二检时间" align="center" prop="twoCheck">
        <template slot-scope="scope">
          {{ formatDate(scope.row.twoCheck) }}
        </template>
      </el-table-column>
      <el-table-column label="接待人" align="center" prop="receptionist" />
      <el-table-column label="产值状态" align="center">
        <template slot-scope="scope">
          <el-tag v-show="scope.row.operateStatus == 0" type="danger"
            >未分配</el-tag
          >
          <el-tag v-show="scope.row.operateStatus == 1" type="success"
            >已分配</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="经营产值" align="center" prop="operate" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:project:detail']"
            >经营产值</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="handlePeopleDetail(scope.row)"
            >详情</el-button
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

    <el-dialog
      :title="detailTitle"
      :visible.sync="detailOpen"
      width="1000px"
      append-to-body
      v-el-drag-dialog
    >
      <el-collapse v-model="activeNames" @change="handleChange">
        <el-collapse-item title="详情" name="1">
          <el-descriptions class="margin-top" :column="4" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-home"></i>
                委托单位
              </template>
              {{ form.requesterAlias }}
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
                <i class="el-icon-user"></i>
                接待人
              </template>
              {{ form.receptionist }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                登记时间
              </template>
              {{ formatDate(form.registerTime) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                项目名称
              </template>
              {{ form.projectNameAlias }}
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
                <i class="el-icon-time"></i>
                安排开始时间
              </template>
              {{ formatDate(form.projectStartAlias) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                安排结束时间
              </template>
              {{ formatDate(form.projectEndAlias) }}
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
                <i class="el-icon-notebook-2"></i>
                作业部门
              </template>
              {{ form.department }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                一检时间
              </template>
              {{ formatDate(form.oneCheck) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                二检时间
              </template>
              {{ formatDate(form.twoCheck) }}
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
                <i class="el-icon-time"></i>
                通知出件时间
              </template>
              {{ formatDate(form.noticeTime) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                项目出件时间
              </template>
              {{ formatDate(form.projectTime) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                送达时间
              </template>
              {{ formatDate(form.deliveryTime) }}
            </el-descriptions-item>
          </el-descriptions>
          <el-descriptions class="margin-top" :column="1" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document"></i>
                工程内容
              </template>
              {{ form.workcontentAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document"></i>
                工作量
              </template>
              {{ form.workloadAlias }}
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
        <el-collapse-item title="经营产值填写" name="2">
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="经营产值" prop="operate">
              <el-input-number
                v-model="form.operate"
                :precision="2"
                :step="0.1"
                :max="99999999"
              ></el-input-number>
            </el-form-item>
          </el-form>
          <div
            class="dialog-footer"
            style="display: flex; justify-content: flex-end"
          >
            <el-button
              v-hasPermi="['system:operating:add']"
              type="primary"
              @click="submitForm"
              >确 定</el-button
            >
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-collapse-item>
      </el-collapse>
    </el-dialog>

    <el-dialog
      title="项目"
      :visible.sync="detailPeopleOpen"
      width="1000px"
      append-to-body
      v-el-drag-dialog
    >
      <el-collapse v-model="activeNames" @change="handleChange">
        <el-collapse-item title="详情" name="1">
          <el-descriptions class="margin-top" :column="2" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-home"></i>
                经营产值
              </template>
              {{ formPeople.operate }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                分包金额
              </template>
              {{ formPeople.fbMoney }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                雇工金额
              </template>
              {{ ggje(formPeople) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-notebook-2"></i>
                利润
              </template>
              <el-tag v-if="calculateProfit(formPeople) > 0">
                {{ calculateProfit(formPeople) }}</el-tag
              >
              <el-tag type="danger" v-if="calculateProfit(formPeople) <= 0">
                {{ calculateProfit(formPeople) }}</el-tag
              >
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                分包工作量
              </template>
              {{ formPeople.fbWorkload }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                雇工分包申请单
              </template>
              <el-tag>{{ calculateLaborSub(formPeople) }}</el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
        <el-collapse-item title="人员安排" name="2">
          <template>
            <el-table :data="formPeople.projectValue" style="width: 100%">
              <el-table-column prop="userName" label="用户名" align="center" />
              <el-table-column prop="proportion" label="占比" align="center" />
              <el-table-column prop="money" label="经营产值" align="center">
                <template slot-scope="scope">
                  <el-tag> {{ scope.row.money }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="profitMoney" label="利润" align="center">
                <template slot-scope="scope">
                  <el-tag type="success"> {{ scope.row.profitMoney }}</el-tag>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-collapse-item>
      </el-collapse>
    </el-dialog>
  </div>
</template>

<script>
import {
  listProjectOperate,
  getProject,
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
      formPeople: {},
      dateRange: [],
      operates: [
        {
          value: "1",
          label: "是",
        },
        {
          value: "2",
          label: "否",
        },
      ],
      activeNames: ["1", "2"],
      detailOpen: false,
      detailPeopleOpen: false,
      detailTitle: "项目",
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
      rules: {
        operate: [
          { required: true, message: "请填写经营产值", trigger: "blur" },
        ],
      },
      // 表单校验
    };
  },
  created() {
    this.getList();
  },
  methods: {
    calculateLaborSub(value) {
      // 0无、1未开始、2驳回、3进行中（展示是谁审核中）、通过
      var status = "无审核单";
      if (
        value.reviewSubOne != null &&
        value.reviewSubOne != undefined &&
        value.reviewSubOne.reviewEmployee != null &&
        value.reviewSubOne.reviewEmployee != undefined
      ) {
        if (value.reviewSubOne.status == 2 || value.reviewSubOne.status == 4) {
          status = "该项目审核单已通过";
        } else if (value.reviewSubOne.status == 3) {
          status = "该项目审核单被驳回";
        } else if (value.reviewSubOne.status == 0) {
          status = "该项目审核单未发起审核";
        } else if (
          value.reviewSubOne.status == 1 &&
          value.reviewSubOne.reviewSubProcess[3].status == 1
        ) {
          status = "该项目审核单已通过";
        } else {
          if (value.reviewSubOne.reviewSubProcess[0].status == 1) {
            status =
              value.reviewSubOne.reviewSubProcess[0].user.userName + "审核中";
          }
          if (value.reviewSubOne.reviewSubProcess[1].status == 1) {
            status =
              value.reviewSubOne.reviewSubProcess[1].user.userName + "审核中";
          }
          if (value.reviewSubOne.reviewSubProcess[2].status == 1) {
            status = "填写最终雇工信息中";
          }
        }
      }
      return status;
    },
    calculateProfit(value) {
      var cost = 0;
      if (
        value.reviewSubOne != null &&
        value.reviewSubOne != undefined &&
        value.reviewSubOne.reviewEmployee != null &&
        value.reviewSubOne.reviewEmployee != undefined
      ) {
        for (let i = 0; i < value.reviewSubOne.reviewEmployee.length; i++) {
          cost = cost + value.reviewSubOne.reviewEmployee[i].cost;
        }
      }
      return value.operate - value.fbMoney - cost;
    },
    ggje(value) {
      if (value.reviewSubOne == null || value.reviewSubOne == undefined) {
        return 0;
      }
      if (
        value.reviewSubOne.reviewEmployee == null ||
        value.reviewSubOne.reviewEmployee == undefined
      ) {
        return 0;
      }
      var cost = 0;
      for (let i = 0; i < value.reviewSubOne.reviewEmployee.length; i++) {
        cost = cost + value.reviewSubOne.reviewEmployee[i].cost;
      }
      return cost;
    },
    formatDate(dateString) {
      if (dateString == "") {
        return "";
      }
      const dateObject = new Date(dateString);
      const year = dateObject.getFullYear();
      const month = dateObject.getMonth() + 1;
      const day = dateObject.getDate();
      return `${year}-${(month < 10 ? "0" : "") + month}-${
        (day < 10 ? "0" : "") + day
      }`;
    },
    formatDateToMonth(dateString) {
      if (dateString == "") {
        return "";
      }
      const dateObject = new Date(dateString);
      const year = dateObject.getFullYear();
      const month = dateObject.getMonth() + 1;
      return `${year}-${(month < 10 ? "0" : "") + month}`;
    },
    handleChange(val) {
      console.log(val);
    },
    /** 查询项目列表 */
    getList() {
      this.loading = true;
      listProjectOperate(
        this.addDateRange(this.queryParams, this.dateRange)
      ).then((response) => {
        this.projectList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
    handlePeopleDetail(row) {
      this.reset();
      const projectId = row.projectId || this.ids;
      this.projectId = projectId;
      getProject(projectId).then((response) => {
        this.formPeople = response.data;
        this.detailPeopleOpen = true;
      });
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

    submitForm() {
      this.$refs["form"].validate((valid) => {
        const tempForm = this.form;
        this.form = {
          projectId: 0,
          operate: 0,
          operateUser: "",
          operateTime: "",
        };
        this.form.projectId = tempForm.projectId;
        this.form.operate = tempForm.operate;
        this.form.operateUser = this.$store.getters.name;
        this.form.operateTime = new Date();
        if (valid) {
          updateProject(this.form).then((response) => {
            this.$modal.msgSuccess("填写成功");
            this.detailOpen = false;
            this.getList();
          });
        }
      });
    },
  },
};
</script>
