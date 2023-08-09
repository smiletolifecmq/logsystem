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
      <el-form-item label="业务名称" prop="businessName">
        <el-input
          v-model="queryParams.businessName"
          placeholder="请输入业务名称"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-success"
          size="mini"
          :disabled="multiple"
          @click="batchReviewPass"
          >批量审核通过</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getUpcomingList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="subcontractList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工程编号" align="center" prop="serialNum" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="业务名称" align="center" prop="businessName" />
      <el-table-column label="分包工作量" align="center" prop="workload" />
      <el-table-column label="工作内容" align="center" prop="workcontent" />
      <el-table-column label="委托单位" align="center" prop="entrustUnit" />
      <el-table-column
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
      </el-table-column>
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
      <el-table-column
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
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit-outline"
              @click="showSubcontractInfo(scope.row)"
              >分包审核</el-button
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
      width="700px"
      append-to-body
      v-el-drag-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form ref="formInfo" :model="formInfo" label-width="80px">
        <div class="form-container">
          <el-form-item label="工程编号" prop="serialNum">
            <el-input
              v-model="formInfo.serialNum"
              placeholder="请输入工程编号"
              class="custom-input"
              disabled
            />
          </el-form-item>
          <el-form-item label="负责人">
            <el-input
              v-if="formInfo.user"
              v-model="formInfo.user.userName"
              disabled
              class="custom-input"
            />
          </el-form-item>
        </div>

        <el-form-item label="项目名称" prop="projectName">
          <el-input
            v-model="formInfo.projectName"
            placeholder="请输入项目名称"
            class="custom-input"
            disabled
          />
        </el-form-item>
        <el-form-item label="业务名称" prop="businessName">
          <el-input
            v-model="formInfo.businessName"
            placeholder="请输入业务名称"
            class="custom-input"
            disabled
          />
        </el-form-item>
        <el-form-item label="委托单位" prop="entrustUnit">
          <el-input
            v-model="formInfo.entrustUnit"
            placeholder="请输入内容"
            class="custom-input"
            disabled
          />
        </el-form-item>
        <el-form-item label="分包工作量" prop="workload">
          <el-input
            v-model="formInfo.workload"
            type="textarea"
            placeholder="请输入分包工作量"
            class="textarea-input"
            disabled
          />
        </el-form-item>
        <el-form-item label="工作内容">
          <el-input
            v-model="formInfo.workcontent"
            type="textarea"
            placeholder="请输入工作内容"
            class="textarea-input"
            disabled
          />
        </el-form-item>
        <el-form-item label="协作单位" prop="cooperationUnitJson">
          <el-select
            v-model="formInfo.cooperationUnitJson"
            placeholder="协作单位"
            multiple
            style="width: 260px"
            disabled
            class="custom-input"
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
        <el-form-item label="中签单位" prop="winUnit">
          <el-select
            v-model="formInfo.winUnit"
            placeholder="请选择中签单位"
            style="width: 260px"
            disabled
            class="custom-input"
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
        <el-form-item label="抽签时间" prop="lotTime">
          <el-date-picker
            clearable
            v-model="formInfo.lotTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择抽签时间"
            disabled
            class="custom-input"
          >
          </el-date-picker>
        </el-form-item>

        <div class="form-container">
          <el-form-item label="工期开始" prop="startTime">
            <el-date-picker
              clearable
              v-model="formInfo.startTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择工期开始时间"
              disabled
              class="custom-input"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="" prop="endTime">
            <el-date-picker
              clearable
              v-model="formInfo.endTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择工期结束时间"
              disabled
              class="custom-input"
            >
            </el-date-picker>
          </el-form-item>
        </div>

        <div class="form-container">
          <el-form-item label="审核意见">
            <el-input
              v-model="formInfo.auditOpinion"
              placeholder="请输入审核意见"
              class="custom-input"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="handleReview(formInfo, 2)"
              >通过</el-button
            >
            <el-button type="danger" @click="handleReview(formInfo, 3)"
              >回退</el-button
            >
          </el-form-item>
        </div>
      </el-form>
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
  upcomingListReview,
  setSubcontractProcessStatus,
  setBatchReviewPass,
} from "@/api/system/subcontract";
import { listUnit } from "@/api/system/unit";
import elDragDialog from "@/api/components/el-drag";

export default {
  name: "Subcontract",
  directives: {
    elDragDialog,
  },
  data() {
    return {
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
      formInfo: {},
      // 表单校验
      rules: {
        serialNum: [
          { required: true, message: "工程编号不能为空", trigger: "blur" },
        ],
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" },
        ],
        businessName: [
          { required: true, message: "业务名称不能为空", trigger: "blur" },
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
    batchReviewPass() {
      const subcontractIds = this.ids;
      this.$modal
        .confirm('是否确认对选中的审核单进行批量通过"')
        .then(function () {
          return setBatchReviewPass(subcontractIds);
        })
        .then(() => {
          this.getUpcomingList();
          this.$modal.msgSuccess("审核成功");
        })
        .catch(() => {});
    },
    handleReview(row, status) {
      let reason = "";
      if (status == 2) {
        reason = "此操作将确认审核为通过,是否继续?";
      } else {
        reason = "此操作将确认审核为不通过,是否继续?";
      }
      this.$confirm(reason, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.form.subcontractId = row.subcontractId;
          this.form.status = status;
          this.form.reason = this.formInfo.auditOpinion;
          setSubcontractProcessStatus(this.form).then((response) => {
            this.getUpcomingList();
            if (this.form.status == 2) {
              this.$modal.msgSuccess("已通过审核");
            } else {
              this.$modal.msgSuccess("已拒绝审核");
            }
            this.openInfo = false;
            this.formInfo.auditOpinion = "";
          });
        })
        .catch(() => {});
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
      upcomingListReview(this.queryParams).then((response) => {
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
    },
  },
};
</script>
