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
      <el-form-item label="工程编号" prop="serialNum">
        <el-input
          v-model="queryParams.serialNum"
          placeholder="请输入工程编号"
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
      <el-form-item label="项目类型" prop="businessName">
        <el-input
          v-model="queryParams.businessName"
          placeholder="请输入项目类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中签单位" prop="winUnit">
        <el-select
          v-model="queryParams.winUnit"
          placeholder="请选择中签单位"
          @change="handleQuery"
        >
          <el-option
            v-for="item in winUnits"
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

    <el-table
      v-loading="loading"
      :data="subcontractList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="工程编号" align="center" prop="serialNum" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="委托单位" align="center" prop="entrustUnit" />
      <el-table-column label="项目类型" align="center" prop="businessName" />
      <el-table-column label="分包工作量" align="center" prop="workload" />
      <!-- <el-table-column label="工作内容" align="center" prop="workcontent" /> -->
      <!-- <el-table-column
        label="协作单位"
        align="center"
        prop="cooperationUnitJson"
      >
        <template slot-scope="scope">
          <div
            v-for="(unit, index) in scope.row.cooperationUnitJson"
            :key="index"
          >
            《{{ unit }}》
          </div>
        </template>
      </el-table-column> -->
      <el-table-column label="中签单位" align="center" prop="winUnit" />
      <el-table-column
        label="抽签时间"
        align="center"
        prop="lotTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lotTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="负责人" align="center" prop="user.userName" />
      <el-table-column label="部门" align="center" prop="dept.deptName" />
      <!-- <el-table-column
        label="工期开始时间"
        align="center"
        prop="startTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="工期结束时间"
        align="center"
        prop="endTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        fixed="right"
      >
        <template slot-scope="scope">
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-s-order"
              @click="showSubcontractInfo(scope.row)"
              >分包详情</el-button
            >
          </div>
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-s-operation"
              @click="handleReviewProcess(scope.row)"
              >流程详情</el-button
            >
          </div>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getUpcomingList"
    />

    <!-- 详情框 -->
    <el-dialog
      :title="titleInfo"
      :visible.sync="openInfo"
      width="1260px"
      append-to-body
      v-el-drag-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-collapse v-model="activeNames" @change="handleChange">
        <el-collapse-item title="项目详情" name="1">
          <div
            v-if="!formInfo.project || !formInfo.project.projectNum"
            style="text-align: center"
          >
            未找到关联项目数据～
          </div>
          <div v-if="formInfo.project && formInfo.project.projectNum">
            <el-descriptions class="margin-top" :column="5" border>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-office-building"></i>
                  项目名称
                </template>
                {{ formInfo.project.projectNameAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-tickets"></i>
                  项目编号
                </template>
                {{ formInfo.project.projectNum }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-notebook-2"></i>
                  项目类型
                </template>
                {{ formInfo.project.projectType }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-money"></i>
                  项目金额
                </template>
                {{ formInfo.project.projectMoneyAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  登记时间
                </template>
                {{ formInfo.project.registerTime }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-s-custom"></i>
                  接待人
                </template>
                {{ formInfo.project.receptionist }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-document"></i>
                  工程内容
                </template>
                {{ formInfo.project.workloadAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-user"></i>
                  工程负责人
                </template>
                {{ formInfo.project.userNameAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-s-home"></i>
                  委托单位
                </template>
                {{ formInfo.project.requesterAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  安排开始时间
                </template>
                {{ formInfo.project.projectStartAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  安排结束时间
                </template>
                {{ formInfo.project.projectEndAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  一检时间
                </template>
                {{ formInfo.project.oneCheck }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  二检时间
                </template>
                {{ formInfo.project.twoCheck }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  通知出件时间
                </template>
                {{ formInfo.project.noticeTime }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  项目出件时间
                </template>
                {{ formInfo.project.projectTime }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  送达时间
                </template>
                {{ formInfo.project.deliveryTime }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  送达时间
                </template>
                {{ formInfo.project.deliveryTime }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-collapse-item>
        <el-collapse-item title="审核单详情" name="2">
          <div>
            <el-row :gutter="10">
              <el-col style="width: 50%">
                <el-card>
                  <div slot="header">
                    <span>分包审核单详情</span>
                    <el-button
                      style="float: right; padding: 3px 0"
                      type="text"
                    ></el-button>
                  </div>

                  <el-descriptions class="margin-top" :column="2" border>
                    <el-descriptions-item>
                      <template slot="label"> 工程编号 </template>
                      {{ formInfo.serialNum }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 负责人 </template>
                      {{ formInfo.user.userName }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 项目名称 </template>
                      {{ formInfo.projectName }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 项目类型 </template>
                      {{ formInfo.businessName }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 委托单位 </template>
                      {{ formInfo.entrustUnit }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 工作内容 </template>
                      {{ formInfo.workcontent }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 分包类型 </template>
                      <span v-if="formInfo.subType == 1">全部分包</span
                      ><span v-if="formInfo.subType == 2">局部分包</span>
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 分包工作量 </template>
                      {{ formInfo.workload }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 抽签单位 </template>
                      <div
                        v-for="(unit, index) in formInfo.cooperationUnitJson"
                        :key="index"
                      >
                        {{ unit }}
                      </div>
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 中签单位 </template>
                      {{ formInfo.winUnit }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 抽签时间 </template>
                      {{ formInfo.lotTime }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 工期开始 </template>
                      {{ formInfo.startTime }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 工期结束 </template>
                      {{ formInfo.endTime }}
                    </el-descriptions-item>
                  </el-descriptions>
                </el-card>
              </el-col>

              <el-col style="width: 50%">
                <el-card>
                  <div slot="header">
                    <span>雇工审核单详情</span>
                    <el-button
                      style="float: right; padding: 3px 0"
                      type="text"
                    ></el-button>
                  </div>

                  <div v-if="!reviewForm" style="text-align: center">
                    未找到关联雇工审核单数据～
                  </div>
                  <div v-if="reviewForm" style="text-align: center">
                    <el-descriptions class="margin-top" :column="2" border>
                      <el-descriptions-item>
                        <template slot="label"> 工程编号 </template>
                        {{ reviewForm.serialNum }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 负责人 </template>
                        {{ reviewForm.user.userName }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 项目名称 </template>
                        {{ reviewForm.projectName }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 委托单位 </template>
                        {{ reviewForm.requester }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 工作量 </template>
                        {{ reviewForm.workload }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 项目金额 </template>
                        {{ reviewForm.porjectMoney }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 分包情况 </template>
                        <div v-if="reviewForm.subcontract == 1">是</div>
                        <div v-if="reviewForm.subcontract == 2">否</div>
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 雇工内容 </template>
                        {{ reviewForm.employmentReason }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 雇工开始时间 </template>
                        {{ reviewForm.startTime
                        }}<span v-if="startAmPm == '12:00:00'">上午</span
                        ><span v-if="startAmPm == '23:59:59'">下午</span>
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 雇工结束时间 </template>
                        {{ reviewForm.endTime
                        }}<span v-if="endAmPm == '12:00:00'">上午</span
                        ><span v-if="endAmPm == '23:59:59'">下午</span>
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 项目工期开始时间 </template>
                        {{ reviewForm.projectStart | formatDate }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 项目工期结束时间 </template>
                        {{ reviewForm.projectEnd | formatDate }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 雇工人数 </template>
                        {{ reviewForm.peopleNum }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 天数 </template>
                        {{ reviewForm.budgetDay }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 预算 </template>
                        {{ reviewForm.budgetMoney }}
                      </el-descriptions-item>
                    </el-descriptions>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-collapse-item>
      </el-collapse>
    </el-dialog>

    <!-- 具体流程 -->

    <el-dialog :visible.sync="reviewProcessOpen" width="300px" append-to-body>
      <div style="height: 300px">
        <el-steps direction="vertical" :active="reviewProcessActive">
          <el-step
            v-for="reviewProcess in reviewProcessList"
            :key="reviewProcess.reviewProcessId"
            :title="reviewProcess.user.userName"
            :status="reviewProcessStatus(reviewProcess)"
            :description="reviewProcessDescription(reviewProcess)"
          ></el-step>
        </el-steps>
      </div>
    </el-dialog>
  </div>
</template>
<style>
.custom-input input {
  color: black !important;
}

.textarea-input textarea {
  color: black !important;
}

.el-form-item__label {
  width: 112px !important;
}

.el-form-item--medium .el-form-item__content {
  margin-left: 112px !important;
}

.form-container {
  display: flex;
}
</style>
<script>
import {
  getSubcontract,
  addSubcontract,
  updateSubcontract,
  getSubcontractProcessList,
  doneListReview,
  listSubcontractReview,
} from "@/api/system/subcontract";
import { listUnit } from "@/api/system/unit";
import elDragDialog from "@/api/components/el-drag";

export default {
  filters: {
    formatDate(value) {
      if (value) {
        const date = new Date(value);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, "0");
        const day = String(date.getDate()).padStart(2, "0");
        return `${year}-${month}-${day}`;
      }
      return "";
    },
  },
  name: "Subcontract",
  directives: {
    elDragDialog,
  },
  data() {
    return {
      startAmPm: "",
      endAmPm: "",
      reviewForm: {
        user: {
          userName: "",
        },
      },
      activeNames: ["1", "2"],
      reviewProcessOpen: false,
      reviewProcessActive: -1,
      reviewProcessList: [],
      titleInfo: "",
      openInfo: false,
      statusArr: [
        {
          value: 0,
          label: "未开始",
        },
        {
          value: 1,
          label: "进行中",
        },
        {
          value: 2,
          label: "通过",
        },
        {
          value: 3,
          label: "未通过",
        },
      ],
      statusVaule: "",
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
      // 分包表格数据
      subcontractList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNum: null,
        lotTime: null,
        projectName: null,
        businessName: null,
        cooperationUnit: null,
        winUnit: null,
        entrustUnit: null,
        workload: null,
        workcontent: null,
        userId: null,
        deptId: null,
        status: null,
        startTime: null,
        endTime: null,
      },
      winUnits: [],
      // 表单参数
      form: {},
      formInfo: {
        user: {
          userName: "",
        },
        project: {
          projectNameAlias: "",
          projectNum: "",
          projectType: "",
          registerTime: "",
          receptionist: "",
          workloadAlias: "",
          userNameAlias: "",
          requesterAlias: "",
          projectStartAlias: null,
          projectEndAlias: null,
          oneCheck: "",
          twoCheck: "",
          noticeTime: "",
          projectTime: "",
          deliveryTime: "",
          projectMoneyAlias: null,
          operate: null,
          operateUser: "",
          operateTime: "",
        },
      },
      // 表单校验
      rules: {
        serialNum: [
          { required: true, message: "工程编号不能为空", trigger: "blur" },
        ],
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" },
        ],
        businessName: [
          { required: true, message: "项目类型不能为空", trigger: "blur" },
        ],
        winUnit: [
          { required: true, message: "中签单位不能为空", trigger: "blur" },
        ],
        userId: [
          {
            required: true,
            message: "用户ID(负责人)不能为空",
            trigger: "blur",
          },
        ],
        deptId: [
          { required: true, message: "部门ID不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getUpcomingList();
    this.loadAllUnits();
  },
  methods: {
    handleChange(val) {
      // console.log(val);
    },
    reviewProcessStatus(reviewProcess) {
      if (reviewProcess.status === 0) {
        return "";
      } else if (reviewProcess.status === 1) {
        return "finish";
      } else if (reviewProcess.status === 2) {
        return "success";
      } else if (reviewProcess.status === 3) {
        return "error";
      }
    },
    reviewProcessDescription(reviewProcess) {
      if (reviewProcess.status === 0) {
        return "审核状态:未开始";
      } else if (reviewProcess.status === 1) {
        return "审核状态:进行中";
      } else if (reviewProcess.status === 2) {
        let description = "审核状态:通过；";
        if (reviewProcess.reason != "" && reviewProcess.reason != null) {
          description = description + "理由:" + reviewProcess.reason + "；";
        }
        description = description + "审核时间:" + reviewProcess.reviewTime;
        return description;
      } else if (reviewProcess.status === 3) {
        let description = "审核状态:拒绝；";
        if (reviewProcess.reason != "" && reviewProcess.reason != null) {
          description = description + "理由:" + reviewProcess.reason + "；";
        }
        description = description + "审核时间:" + reviewProcess.reviewTime;
        return description;
      }
    },
    /** 显示具体流程按钮操作 */
    handleReviewProcess(row) {
      let formObj = {};
      formObj.subcontractId = row.subcontractId;
      getSubcontractProcessList(formObj).then((response) => {
        this.reviewProcessList = response.rows;
        this.reviewProcessActive = -1;
        for (let i = 0; i < response.rows.length; i++) {
          if (response.rows[i].status != 0) {
            this.reviewProcessActive = this.reviewProcessActive + 1;
          }
        }
        this.reviewProcessOpen = true;
      });
    },
    loadAllUnits() {
      listUnit().then((response) => {
        for (let i = 0; i < response.rows.length; i++) {
          const unit = {};
          unit.value = response.rows[i].unitName;
          unit.label = response.rows[i].unitName;
          this.winUnits.push(unit);
        }
      });
    },
    /** 查询分包列表 */
    getUpcomingList() {
      this.loading = true;
      doneListReview(this.queryParams).then((response) => {
        this.subcontractList = response.rows;
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
        subcontractId: null,
        serialNum: null,
        lotTime: null,
        projectName: null,
        businessName: null,
        cooperationUnitJson: null,
        winUnit: null,
        entrustUnit: null,
        workload: null,
        workcontent: null,
        userId: null,
        deptId: null,
        status: null,
        startTime: null,
        endTime: null,
        createTime: null,
        updateTime: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      if (this.statusVaule !== "") {
        this.queryParams.status = this.statusVaule;
      }
      this.getUpcomingList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.statusVaule = "";
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.subcontractId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加分包";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const subcontractId = row.subcontractId || this.ids;
      getSubcontract(subcontractId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改分包";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.subcontractId != null) {
            updateSubcontract(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getUpcomingList();
            });
          } else {
            addSubcontract(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getUpcomingList();
            });
          }
        }
      });
    },
    showSubcontractInfo(row) {
      const subcontractId = row.subcontractId;
      getSubcontract(subcontractId).then((response) => {
        this.formInfo = response.data;
        this.openInfo = true;
        this.titleInfo = "分包详情";
      });

      this.startAmPm = "";
      this.endAmPm = "";
      listSubcontractReview(subcontractId).then((response) => {
        if (response.data) {
          if (
            response.data.startTime != null &&
            response.data.startTime != ""
          ) {
            this.startAmPm = response.data.startTime.substring(11);
            response.data.startTime = response.data.startTime.substring(0, 10);
          }
          if (response.data.endTime != null && response.data.endTime != "") {
            this.endAmPm = response.data.endTime.substring(11);
            response.data.endTime = response.data.endTime.substring(0, 10);
          }
        }

        this.reviewForm = response.data;
      });
    },
  },
};
</script>
