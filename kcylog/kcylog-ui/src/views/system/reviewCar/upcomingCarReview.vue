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
      <el-form-item label="用车时间" prop="recordTime">
        <el-date-picker
          clearable
          v-model="queryParams.recordTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择用车时间"
        >
        </el-date-picker>
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
      <el-table-column
        label="用车时间"
        align="center"
        prop="recordTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.recordTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="user.userName" />
      <el-table-column label="部门" align="center" prop="dept.deptName" />
      <el-table-column label="审核状态" align="center" prop="reviewStatus">
        <template slot-scope="scope">
          <span v-if="scope.row.reviewStatus === 0">未开始</span>
          <span v-else-if="scope.row.reviewStatus === 1">进行中</span>
          <span v-else-if="scope.row.reviewStatus === 2">通过</span>
          <span v-else-if="scope.row.reviewStatus === 3">未通过</span>
          <span v-else>未知状态</span>
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
            icon="el-icon-s-operation"
            @click="showCarReviewDetail(scope.row)"
            >用车审核</el-button
          >
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
      @pagination="getList"
    />

    <!-- 用车详情 -->
    <el-dialog
      :title="title"
      :visible.sync="detailOpen"
      width="1400px"
      append-to-body
      :show-close="false"
    >
      <el-form
        ref="carDetailform"
        :model="carDetailform"
        :rules="rules"
        label-width="80px"
      >
        <el-collapse v-model="activeNames">
          <el-collapse-item title="日期与部门信息" name="1">
            <el-form-item label="日期" prop="recordTime">
              <el-date-picker
                disabled
                clearable
                v-model="carDetailform.recordTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择日期"
                class="custom-input"
              >
              </el-date-picker>
            </el-form-item>
            <el-form-item label="部门" prop="deptId">
              <el-cascader
                disabled
                style="width: 400px"
                v-model="queryParamsDeptId"
                :options="deptOptions"
                @change="handleChangeDept"
                class="custom-input"
              ></el-cascader>
            </el-form-item>
          </el-collapse-item>
          <el-collapse-item title="车辆使用信息登记" name="2">
            <el-form-item
              v-for="(project, index) in carDetailform.projectCar"
              :key="index"
              prop="projectCar"
            >
              <el-row style="margin-left: -80px">
                <el-col :span="4">
                  <el-form-item label="项目编号" prop="projectId">
                    <el-select
                      disabled
                      v-model="project.projectId"
                      placeholder="请选择关联项目"
                      filterable
                      class="custom-input"
                    >
                      <el-option
                        v-for="item in listProjectLocal"
                        :key="item.projectId"
                        :label="item.projectNum"
                        :value="item.projectId"
                        :disabled="item.disabled"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item label="车牌号" prop="carNum">
                    <el-input
                      disabled
                      v-model="project.carNum"
                      placeholder="请输入车牌号"
                      class="custom-input"
                    /> </el-form-item
                ></el-col>
                <el-col :span="4">
                  <el-form-item label="用车类型" prop="carType">
                    <el-select
                      disabled
                      v-model="project.carType"
                      placeholder="请选择用车类型"
                      class="custom-input"
                    >
                      <el-option
                        v-for="item in carTypes"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col
                  :span="4"
                  v-if="project.carType === 1 || project.carType === 2"
                >
                  <el-form-item label="次数" prop="number">
                    <el-select
                      disabled
                      v-model="project.number"
                      placeholder="请选择次数"
                      class="custom-input"
                    >
                      <el-option
                        v-if="project.carType == 1"
                        v-for="item in pcNumbers"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      >
                      </el-option>
                      <el-option
                        v-if="project.carType == 2"
                        v-for="item in dxNumbers"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item label="费用" prop="carExpenses">
                    <el-input-number
                      disabled
                      v-model="project.carExpenses"
                      :precision="2"
                      :step="0.1"
                      :min="0.0"
                      placeholder="请输入用车费用"
                      class="custom-input"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form-item>
          </el-collapse-item>
          <el-collapse-item title="审核操作" name="1"> </el-collapse-item>
          <el-descriptions class="margin-top" :column="2" border>
            <el-descriptions-item>
              <template slot="label"> 审核意见 </template>
              <el-select
                v-model="carDetailform.reason"
                filterable
                clearable
                allow-create
                placeholder="请输入审核意见"
              >
                <el-option
                  v-for="item in auditOpinions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label"> 操作 </template>
              <div class="form-container">
                <el-button
                  type="success"
                  @click="handleReview(carDetailform, 2)"
                  >通过</el-button
                >
                <el-button type="danger" @click="handleReview(carDetailform, 3)"
                  >回退</el-button
                >
              </div>
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelCar">取 消</el-button>
      </div>
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

<script>
import {
  upcomingCarReview,
  getReview,
  delReview,
  addReview,
  updateReview,
  getCarReviewProcessList,
  setReviewProcessStatus,
} from "@/api/system/carReviewIndex";
import { deptTreeSelectUnlimited } from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { listProject } from "@/api/system/project";

export default {
  components: { Treeselect },
  name: "Review",
  data() {
    return {
      auditOpinions: [
        { value: "同意", label: "同意" },
        { value: "不同意", label: "不同意" },
      ],
      detailOpen: false,
      carTypes: [
        {
          value: 1,
          label: "单位派车（拼车）",
        },
        {
          value: 2,
          label: "单位派车（独享）",
        },
        {
          value: 3,
          label: "滴滴",
        },
      ],
      pcNumbers: [
        {
          value: 1,
          label: "拼车一趟",
        },
        {
          value: 2,
          label: "拼车二趟",
        },
        {
          value: 3,
          label: "拼车三趟",
        },
        {
          value: 4,
          label: "拼车四趟",
        },
      ],
      dxNumbers: [
        {
          value: 1,
          label: "独享一趟",
        },
        {
          value: 2,
          label: "独享二趟",
        },
        {
          value: 3,
          label: "独享三趟",
        },
        {
          value: 4,
          label: "独享四趟",
        },
      ],
      queryProjectListParams: {
        pageNum: 1,
        pageSize: 9999,
      },
      listProjectLocal: [],
      listProjectLocalMap: {},
      reviewProcessList: [],
      queryParamsDeptId: [],
      reviewProcessActive: -1,
      reviewProcessOpen: false,
      activeNames: ["1", "2", "3"],
      deptOptions: undefined,
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
      // 车辆使用审核表格数据
      reviewList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recordTime: null,
        userId: null,
        deptId: null,
        reviewStatus: null,
      },
      carDetailform: {},
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        recordTime: [
          { required: true, message: "请选择用车时间", trigger: "blur" },
        ],
        deptId: [{ required: true, message: "请选择部门", trigger: "blur" }],
        projectCar: [
          {
            validator: this.checkProjectCar,
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDeptTree();
    this.getProjectListLocal();
  },
  methods: {
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
          this.form.carReviewId = row.carReviewId;
          this.form.status = status;
          this.form.reason = row.reason;
          setReviewProcessStatus(this.form).then((response) => {
            this.getList();
            if (this.form.status == 2) {
              this.$modal.msgSuccess("已通过审核");
            } else {
              this.$modal.msgSuccess("已驳回审核");
            }
            this.detailOpen = false;
          });
        })
        .catch(() => {});
    },
    getProjectListLocal() {
      listProject(this.queryProjectListParams).then((response) => {
        this.listProjectLocalMap = new Map();
        for (var i = 0; i < response.rows.length; i++) {
          this.listProjectLocalMap.set(
            response.rows[i].projectId,
            response.rows[i].projectNum
          );
        }
        this.listProjectLocal = response.rows;
      });
    },
    checkProjectCar(rule, value, callback) {
      if (value.length == 0) {
        callback(new Error("请添加用车信息～"));
        return;
      }
      callback();
    },
    removeProject(index) {
      this.form.projectCar.splice(index, 1);
    },
    addProject() {
      if (this.form.projectCar == null) {
        this.form.projectCar = [];
      }
      this.form.projectCar.push({
        projectId: null,
        projectName: "",
      });
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
    handleReviewProcess(row) {
      let formObj = {};
      formObj.carReviewId = row.carReviewId;
      getCarReviewProcessList(formObj).then((response) => {
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
    handleChangeDept(value) {
      this.form.deptId = value[1];
    },
    getDeptTree() {
      deptTreeSelectUnlimited().then((response) => {
        this.deptOptions = transformIdToValue(response.data);
      });
    },
    /** 查询车辆使用审核列表 */
    getList() {
      this.loading = true;
      upcomingCarReview(this.queryParams).then((response) => {
        this.reviewList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    cancelCar() {
      this.detailOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        carReviewId: null,
        recordTime: null,
        userId: null,
        deptId: null,
        reviewStatus: null,
        projectCar: [],
      };
      this.queryParamsDeptId = [];
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
      this.ids = selection.map((item) => item.carReviewId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    showCarReviewDetail(row) {
      this.reset();
      const carReviewId = row.carReviewId || this.ids;
      getReview(carReviewId).then((response) => {
        this.queryParamsDeptId.push(100);
        this.queryParamsDeptId.push(response.data.deptId);
        this.carDetailform = response.data;
        this.detailOpen = true;
        this.title = "车辆使用详情";
      });
    },
    /** 提交按钮 */
    submitForm() {
      for (let i = 0; i < this.form.projectCar.length; i++) {
        this.form.projectCar[i].projectName = this.listProjectLocalMap.get(
          this.form.projectCar[i].projectId
        );
      }
      this.$confirm(
        "确定操作将直接发起审核，不可进行修改操作，请仔细确认填写信息～",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          this.$refs["form"].validate((valid) => {
            if (valid) {
              if (this.form.carReviewId != null) {
                updateReview(this.form).then((response) => {
                  this.$modal.msgSuccess("修改成功");
                  this.open = false;
                  this.detailOpen = false;
                  this.getList();
                });
              } else {
                addReview(this.form).then((response) => {
                  this.$modal.msgSuccess("新增成功");
                  this.open = false;
                  this.detailOpen = false;
                  this.getList();
                });
              }
            }
          });
        })
        .catch(() => {});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const carReviewIds = row.carReviewId || this.ids;
      this.$modal
        .confirm(
          '是否确认删除车辆使用审核编号为"' + carReviewIds + '"的数据项？'
        )
        .then(function () {
          return delReview(carReviewIds);
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
        "system/review/export",
        {
          ...this.queryParams,
        },
        `review_${new Date().getTime()}.xlsx`
      );
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
        if (obj[key] == 201) {
          newObj.disabled = true;
        }
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
<style>
.custom-input input {
  color: black !important;
}
</style>
