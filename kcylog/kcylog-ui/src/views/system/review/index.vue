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
      <el-form-item label="审核状态" prop="status">
        <el-select
          v-model="statusVaule"
          placeholder="请选择"
          @change="handleQuery"
        >
          <el-option
            v-for="item in statusArr"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
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
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          >删除</el-button
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
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="serialNum" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="委托单位" align="center" prop="requester" />
      <el-table-column label="项目金额" align="center" prop="porjectMoney" />
      <el-table-column label="工作量" align="center" prop="workload" />
      <el-table-column label="负责人" align="center" prop="user.userName" />
      <el-table-column label="部门" align="center" prop="dept.deptName" />
      <el-table-column label="审核状态" align="center" prop="status">
        <template slot-scope="scope">
          <span v-if="scope.row.status === 0">未开始</span>
          <span v-else-if="scope.row.status === 1">进行中</span>
          <span v-else-if="scope.row.status === 2">通过</span>
          <span v-else-if="scope.row.status === 3">未通过</span>
          <span v-else>其他状态</span>
        </template>
      </el-table-column>
      <el-table-column label="人数" align="center" prop="peopleNum" />
      <el-table-column
        label="预估雇工工作开始时间"
        align="center"
        prop="startTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="预估雇工工作结束时间"
        align="center"
        prop="endTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预估天数" align="center" prop="budgetDay" />
      <el-table-column label="预算金额" align="center" prop="budgetMoney" />
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
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click="handleReview(scope.row)"
            v-if="scope.row.status === 0 || scope.row.status === 3"
            >发起审核</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-if="scope.row.status === 0 || scope.row.status === 3"
            >修改基本信息</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-if="scope.row.status === 0 || scope.row.status === 3"
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

    <!-- 添加或修改审核单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!-- <el-form-item label="关联项目" prop="userId">
          <el-autocomplete
            v-model="form.userId"
            :fetch-suggestions="querySearchReviewer"
            placeholder="请选择项目"
            @select="selectReviewer"
            @clear="clearReviewer"
            clearable
          ></el-autocomplete>
        </el-form-item> -->
        <el-form-item label="编号" prop="serialNum">
          <el-input v-model="form.serialNum" placeholder="请输入编号" />
        </el-form-item>
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="委托单位" prop="requester">
          <el-input v-model="form.requester" placeholder="请输入委托单位" />
        </el-form-item>
        <el-form-item label="项目金额" prop="porjectMoney">
          <el-input-number
            v-model="form.porjectMoney"
            :precision="2"
            :step="0.1"
            placeholder="请输入项目金额"
          />
        </el-form-item>
        <el-form-item label="工作量" prop="workload">
          <el-input
            v-model="form.workload"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="预估雇工工作开始时间" prop="startTime">
          <el-date-picker
            clearable
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择预估雇工工作开始时间"
          >
          </el-date-picker>

          <el-select v-model="startAmPm" placeholder="请选择">
            <el-option label="上午" value="上午"></el-option>
            <el-option label="下午" value="下午"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="预估雇工工作结束时间" prop="endTime">
          <el-date-picker
            clearable
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择预估雇工工作结束时间"
          >
          </el-date-picker>
          <el-select v-model="endAmPm" placeholder="请选择">
            <el-option label="上午" value="上午"></el-option>
            <el-option label="下午" value="下午"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="预估天数" prop="budgetDay">
          <el-input
            v-model="form.budgetDay"
            placeholder="请输入预估天数"
            disabled
          />
        </el-form-item>
        <el-form-item label="预算金额" prop="budgetMoney">
          <el-input
            v-model="form.budgetMoney"
            placeholder="请输入预算金额"
            disabled
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
  listReview,
  getReview,
  delReview,
  addReview,
  updateReview,
  setReviewStatus,
} from "@/api/system/review";
import { listUser } from "@/api/system/user";

export default {
  name: "Review",
  data() {
    return {
      queryUserParams: {
        pageNum: 1,
        pageSize: 9999,
      },
      restaurants: [],
      startAmPm: "上午",
      endAmPm: "下午",
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
      // 日期范围
      dateRange: [],
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
      // 审核单表格数据
      reviewList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNum: null,
        projectName: null,
        requester: null,
        porjectMoney: null,
        workload: null,
        userId: null,
        deptId: null,
        status: null,
        peopleNum: null,
        startTime: null,
        endTime: null,
        budgetDay: null,
        budgetMoney: null,
        finalTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        serialNum: [
          { required: true, message: "编号不能为空", trigger: "blur" },
        ],
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" },
        ],
        requester: [
          { required: true, message: "委托单位不能为空", trigger: "blur" },
        ],
        porjectMoney: [
          { required: true, message: "项目金额不能为空", trigger: "blur" },
        ],
        peopleNum: [
          { required: true, message: "人数不能为空", trigger: "blur" },
        ],
        title: [
          { required: true, message: "配置标题不能为空", trigger: "blur" },
        ],
        // userId: [
        //   { required: true, message: "请选择关联项目", trigger: "change" },
        // ],
      },
    };
  },
  created() {
    this.getList();
    // this.loadAllUsers();
  },
  methods: {
    clearReviewer() {},
    querySearchReviewer(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString
        ? restaurants.filter(this.createStateFilter(queryString))
        : restaurants;
      cb(results);
    },
    selectReviewer(item) {
      console.log(item.value);
      console.log(item.userId);
    },
    createStateFilter(queryString) {
      return (state) => {
        return (
          state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    loadAllUsers() {
      listUser(this.addDateRange(this.queryUserParams, this.dateRange)).then(
        (response) => {
          for (let i = 0; i < response.rows.length; i++) {
            let userObject = {
              value: "",
              userId: 0,
            };
            userObject.value = response.rows[i].userName;
            userObject.userId = response.rows[i].userId;
            this.restaurants.push(userObject);
          }
        }
      );
    },
    /** 查询审核单列表 */
    getList() {
      this.loading = true;
      listReview(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.reviewList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        reviewId: null,
        serialNum: null,
        projectName: null,
        requester: null,
        porjectMoney: null,
        workload: null,
        userId: null,
        deptId: null,
        status: null,
        peopleNum: null,
        startTime: null,
        endTime: null,
        budgetDay: null,
        budgetMoney: null,
        createTime: null,
        updateTime: null,
        finalTime: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      if (this.statusVaule != "") {
        this.queryParams.status = this.statusVaule;
      }
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.statusVaule = "";
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.reviewId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加审核单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const reviewId = row.reviewId || this.ids;
      getReview(reviewId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改审核单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.reviewId != null) {
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
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const reviewIds = row.reviewId || this.ids;
      this.$modal
        .confirm('是否确认删除审核单编号为"' + row.serialNum + '"的数据项？')
        .then(function () {
          return delReview(reviewIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 发起审核 */
    handleReview(row) {
      const reviewIds = row.reviewId || this.ids;
      this.$modal
        .confirm(
          '是否确认对审核单编号为"' + row.serialNum + '"的审核单发起审核申请?'
        )
        .then(() => {
          this.form.reviewId = row.reviewId;
          this.form.status = 1;
          setReviewStatus(this.form).then((response) => {
            this.getList();
            this.$modal.msgSuccess("已发起审核");
          });
        })
        .catch(() => {});
    },
  },
};
</script>
