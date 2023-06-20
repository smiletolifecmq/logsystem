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
      <el-form-item label="编号" prop="serialNum">
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
      <el-form-item label="创建时间">
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
    <!-- 
      <el-row :gutter="10" class="mb8">
        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getUpcomingList"
        ></right-toolbar>
      </el-row> -->

    <el-table v-loading="loading" :data="reviewList">
      <el-table-column label="编号" align="center" prop="serialNum">
        <template slot-scope="scope">
          <a @click="showReviewInfo(scope.row)" style="color: blue">
            {{ scope.row.serialNum }}
          </a>
        </template>
      </el-table-column>
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="委托单位" align="center" prop="requester" />
      <el-table-column label="项目金额" align="center" prop="porjectMoney" />
      <el-table-column label="工作量" align="center" prop="workload" />
      <el-table-column label="雇工金额" align="center" prop="budgetMoney" />
      <el-table-column label="负责人" align="center" prop="user.userName" />
      <el-table-column label="部门" align="center" prop="dept.deptName" />
      <!-- <el-table-column label="审核状态" align="center" prop="status">
        <template slot-scope="scope">
          <span v-if="scope.row.status === 0">未开始</span>
          <span v-else-if="scope.row.status === 1">进行中</span>
          <span v-else-if="scope.row.status === 2">通过</span>
          <span v-else-if="scope.row.status === 3">未通过</span>
          <span v-else>其他状态</span>
        </template>
      </el-table-column> -->
      <el-table-column label="人数" align="center" prop="peopleNum" />
      <el-table-column
        label="预估雇工工作开始时间"
        align="center"
        prop="startTime"
        width="180"
      >
        <template slot-scope="scope">
          <span
            >{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}:{{
              filterTime(scope.row.startTime)
            }}</span
          >
        </template>
      </el-table-column>
      <el-table-column
        label="预估雇工工作结束时间"
        align="center"
        prop="endTime"
        width="180"
      >
        <template slot-scope="scope">
          <span
            >{{ parseTime(scope.row.endTime, "{y}-{m}-{d}") }}:{{
              filterTime(scope.row.endTime)
            }}</span
          >
        </template>
      </el-table-column>
      <el-table-column label="预估天数" align="center" prop="budgetDay" />
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
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-operation"
            @click="handleReviewProcess(scope.row)"
            >流程详情</el-button
          >
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
    <!-- 具体流程 -->

    <el-dialog :visible.sync="reviewProcessOpen" width="300px" append-to-body>
      <div style="height: 300px">
        <el-steps direction="vertical" :active="reviewProcessActive">
          <el-step
            v-for="reviewProcess in reviewProcessList"
            :key="reviewProcess.id"
            :title="reviewProcess.user.userName"
            :status="reviewProcessStatus(reviewProcess)"
            :description="reviewProcessDescription(reviewProcess)"
          ></el-step>
        </el-steps>
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog
      :title="titleInfo"
      :visible.sync="openInfo"
      width="600px"
      append-to-body
    >
      <el-form ref="formInfo" :model="formInfo" label-width="80px">
        <el-form-item label="编号" prop="serialNum">
          <el-input
            v-model="formInfo.serialNum"
            placeholder="请输入编号"
            disabled
            class="custom-input"
          />
        </el-form-item>
        <el-form-item label="项目名称" prop="projectName">
          <el-input
            v-model="formInfo.projectName"
            placeholder="请输入项目名称"
            disabled
            class="custom-input"
          />
        </el-form-item>
        <el-form-item label="委托单位" prop="requester">
          <el-input
            v-model="formInfo.requester"
            placeholder="请输入委托单位"
            disabled
            class="custom-input"
          />
        </el-form-item>
        <el-form-item label="项目金额" prop="porjectMoney">
          <el-input-number
            v-model="formInfo.porjectMoney"
            :precision="2"
            :step="0.1"
            :min="0.0"
            placeholder="请输入项目金额"
            disabled
            class="custom-input"
          />
        </el-form-item>
        <el-form-item label="工作量" prop="workload">
          <el-input
            v-model="formInfo.workload"
            type="textarea"
            placeholder="请输入内容"
            disabled
            class="textarea-input"
          />
        </el-form-item>
        <el-form-item label="雇工人数" prop="peopleNum">
          <el-input-number
            v-model="formInfo.peopleNum"
            placeholder="请预估雇工人数"
            :min="0"
            disabled
            class="custom-input"
          />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            clearable
            v-model="formInfo.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择预估雇工工作开始时间"
            disabled
            class="custom-input"
          >
          </el-date-picker>

          <el-select
            v-model="startAmPm"
            placeholder="请选择"
            disabled
            class="custom-input"
          >
            <el-option label="上午" value="12:00:00"></el-option>
            <el-option label="下午" value="23:59:59"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            clearable
            v-model="formInfo.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择预估雇工工作结束时间"
            disabled
            class="custom-input"
          >
          </el-date-picker>
          <el-select
            v-model="endAmPm"
            placeholder="请选择"
            disabled
            class="custom-input"
          >
            <el-option label="上午" value="12:00:00"></el-option>
            <el-option label="下午" value="23:59:59"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="预估天数" prop="budgetDay">
          <el-input
            v-model="formInfo.budgetDay"
            placeholder="请输入预估天数"
            disabled
            class="custom-input"
          />
        </el-form-item>
        <el-form-item label="预算金额" prop="budgetMoney">
          <el-input
            v-model="formInfo.budgetMoney"
            placeholder="请输入预算金额"
            disabled
            class="custom-input"
          />
        </el-form-item>
      </el-form>
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
</style>
<script>
import {
  doneListReview,
  getReviewProcessList,
  getReview,
} from "@/api/system/review";

export default {
  name: "Review",
  data() {
    return {
      openInfo: false,
      formInfo: {},
      titleInfo: "",
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNum: null,
        projectName: null,
        requester: null,
      },
    };
  },
  created() {
    this.getUpcomingList();
  },
  methods: {
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
      doneListReview(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.reviewList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getUpcomingList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.statusVaule = "";
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // // 多选框选中数据
    // handleSelectionChange(selection) {
    //   this.ids = selection.map((item) => item.reviewId);
    //   this.single = selection.length !== 1;
    //   this.multiple = !selection.length;
    // },

    showReviewInfo(row) {
      const reviewId = row.reviewId || this.ids;
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
        this.openInfo = true;
        this.titleInfo = "审核单详情";
      });
    },
  },
};
</script>
