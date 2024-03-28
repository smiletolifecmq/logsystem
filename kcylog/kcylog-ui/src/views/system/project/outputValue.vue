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
      <el-form-item label="负责人" prop="userNameAlias">
        <el-input
          v-model="queryParams.userNameAlias"
          placeholder="请输入项目负责人"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <el-table-column label="登记时间" align="center" prop="registerTime">
        <template slot-scope="scope">
          {{ formatDate(scope.row.registerTime) }}
        </template>
      </el-table-column>
      <el-table-column label="接待人" align="center" prop="receptionist" />
      <el-table-column label="委托单位" align="center" prop="requesterAlias" />
      <!-- <el-table-column label="产值占比" align="center" prop="projectValue">
        <template v-slot:default="scope">
          <div v-for="(project, index) in scope.row.projectValue" :key="index">
            <span
              >用户名称:{{ project.userName }} - 占比:{{
                project.proportion
              }}%</span
            >
          </div>
        </template>
      </el-table-column> -->
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
          <el-descriptions class="margin-top" :column="2" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-home"></i>
                经营产值
              </template>
              {{ form.operate }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                分包金额
              </template>
              {{ form.fbMoney }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                雇工金额
              </template>
              {{ ggje(form) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-notebook-2"></i>
                利润
              </template>
              <el-tag v-if="calculateProfit(form) > 0">
                {{ calculateProfit(form) }}</el-tag
              >
              <el-tag type="danger" v-if="calculateProfit(form) <= 0">
                {{ calculateProfit(form) }}</el-tag
              >
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                分包工作量
              </template>
              {{ form.fbWorkload }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                雇工分包申请单
              </template>
              <el-tag>{{ calculateLaborSub(form) }}</el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
        <el-collapse-item title="人员安排" name="2">
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <!-- <el-button
              v-if="form.projectValue != null && form.projectValue.length == 0"
              type="text"
              icon="el-icon-circle-plus"
              size="medium"
              style="margin-left: 20px; margin-bottom: 20px"
              @click="addProject()"
            ></el-button> -->
            <el-form-item
              v-for="(project, index) in form.projectValue"
              :key="index"
              prop="projectValue"
            >
              <el-row>
                <el-col :span="8">
                  <el-form-item label="用户名" prop="userName">
                    <!-- <el-autocomplete
                      disabled
                      class="inline-input"
                      v-model="project.userName"
                      :fetch-suggestions="querySearch"
                      placeholder="请输入用户名"
                    ></el-autocomplete> -->
                    <el-input
                      disabled
                      class="inline-input"
                      v-model="project.userName"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="占比" prop="proportion">
                    <el-input-number
                      disabled
                      v-model="project.proportion"
                      :max="100"
                    ></el-input-number>
                  </el-form-item>
                </el-col>
                <el-col :span="1">% </el-col>
                <!-- <el-col :span="7">
                  <el-button
                    :disabled="form.outputStatus === 1"
                    v-if="index != 0 || form.projectValue.length == 1"
                    type="text"
                    icon="el-icon-circle-plus"
                    size="medium"
                    style="margin-left: 20px; margin-bottom: 20px"
                    @click="addProject()"
                  ></el-button>
                  <el-button
                    :disabled="form.outputStatus === 1"
                    type="text"
                    icon="el-icon-remove"
                    size="medium"
                    style="margin-left: 20px; margin-bottom: 20px"
                    @click="removeProject(index)"
                  ></el-button>
                </el-col> -->
              </el-row>
            </el-form-item>
          </el-form>
          <div
            class="dialog-footer"
            style="display: flex; justify-content: flex-end"
          >
            <!-- <el-button
              v-if="form.outputStatus === 0"
              type="success"
              @click="submitForm(1)"
              >办 结</el-button
            >
            <el-button
              v-if="form.outputStatus === 0"
              type="primary"
              @click="submitForm(0)"
              >保 存</el-button
            > -->
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-collapse-item>
      </el-collapse>
    </el-dialog>
  </div>
</template>
<style>
.form-container {
  display: flex;
}
</style>

<script>
import {
  listUpcomingProject,
  getProject,
  updateProjectValue,
} from "@/api/system/project";
import elDragDialog from "@/api/components/el-drag";
import { listUser } from "@/api/system/user";

export default {
  name: "Project",
  directives: {
    elDragDialog,
  },
  data() {
    return {
      dateRange: [],
      queryUserParams: {
        pageNum: 1,
        pageSize: 9999,
        deptId: null,
      },
      restaurants: [],
      projectId: 0,
      activeNames: ["1", "2"],
      detailOpen: false,
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
        outputStatus: null,
        isJqr: null,
      },
      // 表单参数
      form: {},
      rules: {
        projectValue: [
          {
            validator: this.checkProjectValue,
            trigger: "blur",
          },
        ],
      },
      // 表单校验
    };
  },
  created() {
    listUser(this.addDateRange(this.queryUserParams, this.dateRange)).then(
      (response) => {
        for (let i = 0; i < response.rows.length; i++) {
          let restaurant = {
            value: response.rows[i].userName,
          };
          this.restaurants.push(restaurant);
        }
      }
    );
    this.getList();
  },
  methods: {
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
    querySearch(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString
        ? restaurants.filter(this.createFilter(queryString))
        : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (
          restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) ===
          0
        );
      };
    },

    checkProjectValue(rule, value, callback) {
      for (let i = 0; i < value.length; i++) {
        if (
          !value[i].userName ||
          !value[i].proportion ||
          value[i].proportion === 0
        ) {
          callback(new Error("用户名和占比都是必填项，且占比不能为0"));
          return;
        }
      }
      callback();
    },
    addProject() {
      this.form.projectValue.push({
        project_id: this.projectId,
        user_name: "",
        proportion: 0,
      });
    },
    removeProject(index) {
      this.form.projectValue.splice(index, 1);
    },
    handleChange(val) {
      console.log(val);
    },
    /** 查询项目列表 */
    getList() {
      this.queryParams.outputStatus = 0;
      this.queryParams.isJqr = 1;
      this.loading = true;
      listUpcomingProject(this.queryParams).then((response) => {
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
      this.projectId = projectId;
      getProject(projectId).then((response) => {
        this.form = response.data;
        this.detailOpen = true;
      });
    },

    submitForm(item) {
      if (item === 1) {
        this.$confirm("办结之后将无法新增或修改产值比例～", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.$refs["form"].validate((valid) => {
              if (valid) {
                let num = 0;
                for (let i = 0; i < this.form.projectValue.length; i++) {
                  this.form.projectValue[i].projectId = this.projectId;
                  num = num + this.form.projectValue[i].proportion;
                }
                if (num > 100) {
                  this.$message({
                    message: "总占比不能大于100～",
                    type: "warning",
                  });
                  return;
                }
                this.form.outputStatus = 1;
                updateProjectValue(this.form).then((response) => {
                  this.$modal.msgSuccess("办结成功");
                  this.detailOpen = false;
                  this.getList();
                  this.form.outputStatus = 0;
                });
              }
            });
          })
          .catch(() => {});
      } else {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            let num = 0;
            for (let i = 0; i < this.form.projectValue.length; i++) {
              this.form.projectValue[i].projectId = this.projectId;
              num = num + this.form.projectValue[i].proportion;
            }
            if (num > 100) {
              this.$message({
                message: "总占比不能大于100～",
                type: "warning",
              });
              return;
            }
            updateProjectValue(this.form).then((response) => {
              this.$modal.msgSuccess("保存成功");
              this.detailOpen = false;
              this.getList();
            });
          }
        });
      }
    },
  },
};
</script>
