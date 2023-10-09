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
          placeholder="请输入编号"
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
      <el-form-item label="委托单位" prop="requester">
        <el-input
          v-model="queryParams.requester"
          placeholder="请输入委托单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <el-cascader
          v-model="queryParamsDeptId"
          :options="deptOptions"
          @change="handleChangeDept"
          clearable
        ></el-cascader>
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
      :data="reviewList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="工程编号" align="center" prop="serialNum" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="委托单位" align="center" prop="requester" />
      <!-- <el-table-column label="分包情况" align="center" prop="subcontract">
        <template slot-scope="scope">
          <span v-if="scope.row.subcontract === 1">是</span>
          <span v-else-if="scope.row.subcontract === 2">否</span>
          <span v-else></span>
        </template>
      </el-table-column>
      <el-table-column label="雇工人数" align="center" prop="peopleNum" />
      <el-table-column label="雇工金额" align="center" prop="budgetMoney" /> -->
      <el-table-column label="负责人" align="center" prop="user.userName" />
      <el-table-column label="部门" align="center" prop="dept.deptName" />
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
              icon="el-icon-user-solid"
              @click="showReviewInfo(scope.row)"
              >审核单详情</el-button
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
      :page-sizes="[10, 20, 50, 100]"
      @pagination="getUpcomingList"
    />
    <!-- 具体流程 -->

    <el-dialog :visible.sync="reviewProcessOpen" width="300px" append-to-body>
      <div style="height: 300px">
        <el-steps direction="vertical" :active="reviewProcessActive">
          <el-step
            v-for="reviewProcess in reviewProcessList"
            :key="reviewProcess.id"
            :title="
              reviewProcess.userId === 1 &&
              (reviewProcess.status != 2 || reviewProcess.status != 4)
                ? '填写最终雇工信息中～'
                : reviewProcess.user.userName
            "
            :status="reviewProcessStatus(reviewProcess)"
            :description="
              reviewProcess.userId === 1 && reviewProcess.status === 2
                ? ''
                : reviewProcessDescription(reviewProcess)
            "
            v-if="!(reviewProcess.userId === 1 && reviewProcess.status === 2)"
          ></el-step>
        </el-steps>
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
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
                  工作量
                </template>
                {{ formInfo.project.workloadAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-document"></i>
                  工程内容
                </template>
                {{ formInfo.project.workcontentAlias }}
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
            </el-descriptions>
          </div>
        </el-collapse-item>
        <el-collapse-item title="审核单详情" name="2">
          <div>
            <el-row :gutter="10">
              <el-col style="width: 50%">
                <el-card>
                  <div slot="header">
                    <span>雇工详情</span>
                    <el-button
                      style="float: right; padding: 3px 0"
                      type="text"
                    ></el-button>
                  </div>

                  <el-descriptions class="margin-top" :column="2" border>
                    <!-- <el-descriptions-item>
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
                      <template slot="label"> 委托单位 </template>
                      {{ formInfo.requester }}
                    </el-descriptions-item> -->
                    <el-descriptions-item>
                      <template slot="label"> 工作量 </template>
                      {{ formInfo.workload }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 项目金额 </template>
                      {{ formInfo.porjectMoney }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 分包情况 </template>
                      <div v-if="formInfo.subcontract == 1">是</div>
                      <div v-if="formInfo.subcontract == 2">否</div>
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 雇工内容 </template>
                      {{ formInfo.employmentReason }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 雇工开始时间 </template>
                      {{ formInfo.startTime
                      }}<span v-if="startAmPm == '12:00:00'">上午</span
                      ><span v-if="startAmPm == '23:59:59'">下午</span>
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 雇工结束时间 </template>
                      {{ formInfo.endTime
                      }}<span v-if="endAmPm == '12:00:00'">上午</span
                      ><span v-if="endAmPm == '23:59:59'">下午</span>
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 项目工期开始时间 </template>
                      {{ formInfo.projectStart | formatDate }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 项目工期结束时间 </template>
                      {{ formInfo.projectEnd | formatDate }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 雇工人数 </template>
                      {{ formInfo.peopleNum }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 天数 </template>
                      {{ formInfo.budgetDay }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 预算 </template>
                      {{ formInfo.budgetMoney }}
                    </el-descriptions-item>
                  </el-descriptions>
                </el-card>
              </el-col>

              <el-col style="width: 50%">
                <el-card>
                  <div slot="header">
                    <span>分包详情</span>
                    <el-button
                      style="float: right; padding: 3px 0"
                      type="text"
                    ></el-button>
                  </div>
                  <div style="text-align: center">
                    <el-descriptions class="margin-top" :column="2" border>
                      <!-- <el-descriptions-item>
                        <template slot="label"> 工程编号 </template>
                        {{ subcontractForm.serialNum }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 负责人 </template>
                        {{ subcontractForm.user.userName }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 项目名称 </template>
                        {{ subcontractForm.projectName }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 项目类型 </template>
                        {{ subcontractForm.businessName }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 委托单位 </template>
                        {{ subcontractForm.requester }}
                      </el-descriptions-item> -->
                      <el-descriptions-item>
                        <template slot="label"> 工作内容 </template>
                        {{ subcontractForm.workcontent }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 分包类型 </template>
                        <span v-if="subcontractForm.subType == 1">全部分包</span
                        ><span v-if="subcontractForm.subType == 2"
                          >局部分包</span
                        >
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 预估分包工作量 </template>
                        {{ subcontractForm.subWorkload }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 实际分包工作量 </template>
                        {{ subcontractForm.realWorkload }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 抽签单位 </template>
                        <div
                          v-for="(
                            unit, index
                          ) in subcontractForm.cooperationUnitJson"
                          :key="index"
                        >
                          {{ unit }}
                        </div>
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 中签单位 </template>
                        {{ subcontractForm.winUnit }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 抽签时间 </template>
                        {{ parseTime(subcontractForm.lotTime, "{y}-{m}-{d}") }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 工期开始 </template>
                        {{
                          parseTime(subcontractForm.cpStartTime, "{y}-{m}-{d}")
                        }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 工期结束 </template>
                        {{
                          parseTime(subcontractForm.cpEndTime, "{y}-{m}-{d}")
                        }}
                      </el-descriptions-item>
                    </el-descriptions>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-collapse-item>

        <el-collapse-item title="雇工信息详情" name="3">
          <div>
            <el-row :gutter="10">
              <el-col style="width: 100%">
                <el-card>
                  <div slot="header">
                    <span>雇工信息详情</span>
                    <el-button
                      style="float: right; padding: 3px 0"
                      type="text"
                    ></el-button>
                  </div>
                  <el-table
                    highlight-current-row
                    style="width: 100%"
                    :data="employeeList"
                  >
                    <el-table-column label="姓名" align="center" prop="name" />
                    <el-table-column
                      label="身份证"
                      align="center"
                      prop="idCard"
                    />
                    <el-table-column
                      label="作业时间"
                      align="center"
                      prop="workTime"
                    />
                    <el-table-column
                      label="天数"
                      align="center"
                      prop="workDay"
                    />
                    <el-table-column label="费用" align="center" prop="cost" />
                  </el-table>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-collapse-item>
      </el-collapse>
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
  settlementListReview,
  getReviewProcessList,
  getReview,
  setBatchEttlement,
} from "@/api/system/reviewSub";
import elDragDialog from "@/api/components/el-drag";
import { deptTreeSelect } from "@/api/system/log";
import { listEmployee } from "@/api/system/reviewEmployeeSub";

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
  name: "Review",
  directives: {
    elDragDialog,
  },
  data() {
    return {
      activeNames: ["1", "2", "3"],
      employeeList: [],
      queryParamsDeptId: [],
      // 部门树选项
      deptOptions: undefined,
      titleInfo: "",
      openInfo: false,
      startAmPm: "12:00:00",
      endAmPm: "23:59:59",
      reviewProcessOpen: false,
      reviewProcessActive: -1,
      reviewProcessList: [],
      // 日期范围
      dateRange: [],
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
      // 审核单表格数据
      reviewList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
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
      subcontractForm: {
        user: {
          userName: "",
        },
      },
      formEttlement: {},
      settlementId: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNum: null,
        projectName: null,
        requester: null,
        deptId: undefined,
        settlementId: undefined,
      },
      queryParamsEmployee: {
        pageNum: 1,
        pageSize: 9999,
        reviewId: null,
      },
    };
  },
  created() {
    this.settlementId = this.$route.params && this.$route.params.settlementId;
    this.queryParams.settlementId = this.settlementId;
    this.getUpcomingList();
    this.getDeptTree();
  },
  methods: {
    handleChange(val) {
      // console.log(val);
    },
    generateStatement() {
      const reviewIds = this.ids;
      this.$prompt("请输入结算单名称", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        closeOnPressEscape: false,
        inputPattern: /^.+$/,
        inputErrorMessage: "请输入结算单名称",
      })
        .then(({ value }) => {
          this.formEttlement.reviewIds = reviewIds;
          this.formEttlement.settlementName = value;
          setBatchEttlement(this.formEttlement).then((response) => {
            if (response.code == 200) {
              this.$modal.msgSuccess("已生成结算单");
              this.getUpcomingList();
            } else {
              this.$message({
                showClose: true,
                message: "生成结算单失败",
                type: "error",
              });
            }
          });
        })
        .catch(() => {});
    },
    handleChangeDept(value) {
      this.queryParams.deptId = value[1];
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then((response) => {
        this.deptOptions = transformIdToValue(response.data);
      });
    },
    finalEmploymentInfo(row) {
      const reviewId = row.reviewId;
      this.$router.push("/system/review-employee/info/" + reviewId);
    },
    filterTime(timeString) {
      if (timeString != "" && timeString != null) {
        const timeSubstring = timeString.substring(11);
        if (timeSubstring == this.startAmPm) {
          return "上午";
        } else {
          return "下午";
        }
      }
    },
    /** 显示具体流程按钮操作 */
    handleReviewProcess(row) {
      let formObj = {};
      formObj.reviewId = row.reviewId;
      getReviewProcessList(formObj).then((response) => {
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
    /** 查询审核单列表 */
    getUpcomingList() {
      this.loading = true;
      this.queryParams.status = 4;
      settlementListReview(
        this.addDateRange(this.queryParams, this.dateRange)
      ).then((response) => {
        this.reviewList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.settlementId = this.settlementId;
      this.queryParams.pageNum = 1;
      this.getUpcomingList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.statusVaule = "";
      this.dateRange = [];
      this.queryParamsDeptId = [];
      this.resetForm("queryForm");
      this.queryParams.settlementId = this.settlementId;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.reviewId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },

    /** 导出按钮操作 */
    handleEmployeeExport() {
      this.download(
        "system/reviewEmployee/export",
        { ...this.queryParams },
        `雇工工作记录_雇工_${new Date().getTime()}.xlsx`
      );
    },

    handleDeptExport() {
      this.queryParams.status = 2;
      this.download(
        "system/review/export",
        { ...this.queryParams },
        `雇工工作记录_部门_${new Date().getTime()}.xlsx`
      );
    },

    showReviewInfo(row) {
      const reviewId = row.reviewId;
      getReview(reviewId).then((response) => {
        if (response.data.startTime != null && response.data.startTime != "") {
          this.startAmPm = response.data.startTime.substring(11);
          response.data.startTime = response.data.startTime.substring(0, 10);
        }
        if (response.data.endTime != null && response.data.endTime != "") {
          this.endAmPm = response.data.endTime.substring(11);
          response.data.endTime = response.data.endTime.substring(0, 10);
        }
        this.formInfo = response.data;
        if (this.formInfo.subcontract == 0) {
          this.formInfo.subcontract = null;
        }
        this.openInfo = true;
        this.subcontractForm = response.data;
        this.titleInfo = "项目编号:" + row.projectName + "详情";
      });
      this.queryParamsEmployee.reviewId = reviewId;
      listEmployee(this.queryParamsEmployee).then((response) => {
        this.employeeList = response.rows;
      });
    },
  },
};
function transformIdToValue(obj) {
  if (Array.isArray(obj)) {
    return obj.map((item) => transformIdToValue(item));
  } else if (typeof obj === "object" && obj !== null) {
    const newObj = {};
    for (let key in obj) {
      if (key === "id") {
        newObj.value = obj[key];
      } else {
        newObj[key] = transformIdToValue(obj[key]);
      }
    }
    return newObj;
  } else {
    return obj;
  }
}
</script>
