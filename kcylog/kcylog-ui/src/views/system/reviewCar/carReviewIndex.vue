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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
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
            v-if="scope.row.reviewStatus === 0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <el-button
            v-if="scope.row.reviewStatus === 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            >删除</el-button
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

    <!-- 添加或修改车辆使用审核对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1000px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-collapse v-model="activeNames">
          <el-collapse-item title="日期与部门信息" name="1">
            <el-form-item label="日期" prop="recordTime">
              <el-date-picker
                clearable
                v-model="form.recordTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择日期"
              >
              </el-date-picker>
            </el-form-item>
            <el-form-item label="部门" prop="deptId">
              <el-cascader
                style="width: 400px"
                v-model="queryParamsDeptId"
                :options="deptOptions"
                @change="handleChangeDept"
              ></el-cascader>
            </el-form-item>
          </el-collapse-item>
          <el-collapse-item title="车辆使用信息登记" name="2">
          </el-collapse-item>
        </el-collapse>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
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
  listReview,
  getReview,
  delReview,
  addReview,
  updateReview,
  getCarReviewProcessList,
} from "@/api/system/carReviewIndex";
import { deptTreeSelectUnlimited } from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  components: { Treeselect },
  name: "Review",
  data() {
    return {
      reviewProcessList: [],
      queryParamsDeptId: [],
      reviewProcessActive: -1,
      reviewProcessOpen: false,
      activeNames: ["1", "2"],
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
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        recordTime: [
          { required: true, message: "请选择用车时间", trigger: "blur" },
        ],
        deptId: [{ required: true, message: "请选择部门", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
    this.getDeptTree();
  },
  methods: {
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
      listReview(this.queryParams).then((response) => {
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
    // 表单重置
    reset() {
      this.form = {
        carReviewId: null,
        recordTime: null,
        userId: null,
        deptId: null,
        reviewStatus: null,
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
    /** 新增按钮操作 */
    handleAdd() {
      this.queryParamsDeptId = [];
      this.reset();
      this.open = true;
      this.title = "车辆使用登记";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.queryParamsDeptId = [];
      const carReviewId = row.carReviewId || this.ids;
      getReview(carReviewId).then((response) => {
        this.queryParamsDeptId.push(100);
        this.queryParamsDeptId.push(response.data.deptId);
        this.form = response.data;
        this.open = true;
        this.title = "车辆使用修改";
      });
    },
    /** 提交按钮 */
    submitForm() {
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
                  this.getList();
                });
              } else {
                addReview(this.form).then((response) => {
                  this.$modal.msgSuccess("新增成功");
                  this.open = false;
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
