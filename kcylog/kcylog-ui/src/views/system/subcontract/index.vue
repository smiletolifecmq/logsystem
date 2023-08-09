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
        <el-select
          v-model="queryParams.businessName"
          placeholder="请选择抽检业务名称"
          @change="handleQuery"
          clearable
        >
          <el-option
            v-for="item in businessNames"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
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
      :data="subcontractList"
      @selection-change="handleSelectionChange"
    >
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
      <el-table-column label="审核状态" align="center" prop="status">
        <template slot-scope="scope">
          <span v-if="scope.row.status === 0">未开始</span>
          <span v-else-if="scope.row.status === 1">进行中</span>
          <span v-else-if="scope.row.status === 2">通过</span>
          <span v-else-if="scope.row.status === 3">未通过</span>
          <span v-else>其他状态</span>
        </template>
      </el-table-column>
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
              icon="el-icon-s-comment"
              @click="showSubcontractInfo(scope.row)"
              >分包详情</el-button
            >
          </div>
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit-outline"
              @click="handleReview(scope.row)"
              v-if="scope.row.status === 0 || scope.row.status === 3"
              >发起审核</el-button
            >
          </div>
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-if="
                scope.row.status === 0 ||
                scope.row.status === 3 ||
                scope.row.startEdit === 1
              "
              >编辑</el-button
            >
          </div>
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-if="scope.row.status === 0 || scope.row.status === 3"
              >删除</el-button
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
      @pagination="getList"
    />

    <!-- 添加或修改分包对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="工程编号" prop="serialNum">
          <el-input v-model="form.serialNum" placeholder="请输入工程编号" />
        </el-form-item>
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="业务名称" prop="businessName">
          <el-select
            v-model="form.businessName"
            placeholder="请选择抽检业务名称"
            style="width: 260px"
          >
            <el-option
              v-for="item in businessNames"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="委托单位" prop="entrustUnit">
          <el-input v-model="form.entrustUnit" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="分包工作量" prop="workload">
          <el-input
            v-model="form.workload"
            type="textarea"
            placeholder="请输入分包工作量"
          />
        </el-form-item>
        <el-form-item label="工作内容">
          <el-input
            v-model="form.workcontent"
            type="textarea"
            placeholder="请输入工作内容"
          />
        </el-form-item>
        <el-form-item label="协作单位" prop="cooperationUnitJson">
          <el-select
            v-model="form.cooperationUnitJson"
            placeholder="协作单位"
            multiple
            style="width: 260px"
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
            v-model="form.winUnit"
            placeholder="请选择中签单位"
            style="width: 260px"
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
            v-model="form.lotTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择抽签时间"
          >
          </el-date-picker>
        </el-form-item>
        <div class="form-container">
          <el-form-item label="项目工期" prop="startTime">
            <el-date-picker
              clearable
              v-model="form.startTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择工期开始时间"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="" style="margin-left: -100px" prop="endTime">
            <el-date-picker
              clearable
              v-model="form.endTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择工期结束时间"
            >
            </el-date-picker>
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 项目编号选择 -->
    <el-dialog
      :title="projectInfo"
      :visible.sync="projectOpen"
      width="1000px"
      append-to-body
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form
        :model="queryProjectParams"
        ref="queryProjectForm"
        size="small"
        :inline="true"
        v-show="showProjectSearch"
        label-width="68px"
      >
        <el-form-item label="工程编号" prop="serialNum">
          <el-input
            v-model="queryProjectParams.serialNum"
            placeholder="请输入工程编号"
            clearable
            @keyup.enter.native="handleProjectQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleProjectQuery"
            >搜索</el-button
          >
        </el-form-item>
      </el-form>

      <el-table v-loading="loading" :data="projectList">
        <el-table-column label="工程编号" align="center" prop="XMBH" />
        <el-table-column label="项目名称" align="center" prop="XMMC" />
        <el-table-column label="工作内容" align="center" prop="GCNR" />
        <el-table-column label="委托单位" align="center" prop="WTDW" />
        <el-table-column label="工期开始" align="center" prop="XMKSSJ" />
        <el-table-column label="工期结束" align="center" prop="XMJSSJ" />
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
                icon="el-icon-document-checked"
                @click="checkImportProject(scope.row)"
                >检测是否已导入</el-button
              >
            </div>
            <div>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-upload"
                @click="importProject(scope.row)"
                >导入</el-button
              >
            </div>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="projectTotal > 0"
        :total="projectTotal"
        :page.sync="queryProjectParams.pageNum"
        :limit.sync="queryProjectParams.pageSize"
        @pagination="getProjectList"
      />
    </el-dialog>

    <!-- 详情框 -->
    <el-dialog
      :title="titleInfo"
      :visible.sync="openInfo"
      width="600px"
      append-to-body
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
              placeholder="请输入委托单位"
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
          <el-form-item label="项目工期" prop="startTime">
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
          <el-form-item label="" style="margin-left: -100px" prop="endTime">
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
  listSubcontract,
  getSubcontract,
  delSubcontract,
  addSubcontract,
  updateSubcontract,
  getSubcontractProcessList,
  setReviewStatus,
  getSubcontractBySerialNum,
} from "@/api/system/subcontract";
import { listUnit } from "@/api/system/unit";
import { fetchProjectData } from "@/utils/otherItems";

export default {
  name: "Subcontract",
  data() {
    return {
      reviewProcessOpen: false,
      reviewProcessActive: -1,
      reviewProcessList: [],
      titleInfo: "",
      openInfo: false,
      businessNames: [
        {
          value: "1:500数字化修测",
          label: "1:500数字化修测",
        },
        {
          value: "1:500数字化新测",
          label: "1:500数字化新测",
        },
        {
          value: "竣工地形测量",
          label: "竣工地形测量",
        },
        {
          value: "工程控制测量",
          label: "工程控制测量",
        },
        {
          value: "道路测量",
          label: "道路测量",
        },
        {
          value: "河道测量",
          label: "河道测量",
        },
        {
          value: "新增管线探测",
          label: "新增管线探测",
        },
        {
          value: "已有管线外业核查",
          label: "已有管线外业核查",
        },
        {
          value: "cctv检测",
          label: "cctv检测",
        },
        {
          value: "管道QV概查",
          label: "管道QV概查",
        },
        {
          value: "面积测量",
          label: "面积测量",
        },
        {
          value: "其他零星工程",
          label: "其他零星工程",
        },
        {
          value: "立面测量",
          label: "立面测量",
        },
        {
          value: "1:500地籍测量",
          label: "1:500地籍测量",
        },
        {
          value: "1:500地籍图修测",
          label: "1:500地籍图修测",
        },
        {
          value: "园林竣工测量",
          label: "园林竣工测量",
        },
      ],
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
      projectInfo: "",
      projectOpen: false,
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
      showProjectSearch: true,
      // 总条数
      total: 0,
      projectTotal: 0,
      // 分包表格数据
      subcontractList: [],
      projectList: [],
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
      queryProjectParams: {
        pageNum: 1,
        pageSize: 10,
        serialNum: null,
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
        cooperationUnitJson: [
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
    this.getList();
    this.loadAllUnits();
  },
  methods: {
    importProject(row) {
      this.reset();
      this.resetForm("queryProjectForm");
      this.open = true;
      this.title = "添加分包";
      this.projectOpen = false;
      if (row.XMBH != null && row.XMBH != "") {
        this.form.serialNum = row.XMBH;
      }

      if (row.XMMC != null && row.XMMC != "") {
        this.form.projectName = row.XMMC;
      }

      if (row.WTDW != null && row.WTDW != "") {
        this.form.entrustUnit = row.WTDW;
      }

      if (row.GCNR != null && row.GCNR != "") {
        this.form.workcontent = row.GCNR;
      }

      if (row.XMKSSJ != null && row.XMKSSJ != "") {
        this.form.startTime = row.XMKSSJ;
      }

      if (row.XMJSSJ != null && row.XMJSSJ != "") {
        this.form.endTime = row.XMJSSJ;
      }
    },
    checkImportProject(row) {
      getSubcontractBySerialNum(row.XMBH).then((response) => {
        if (response.data != null) {
          this.$message({
            message:
              "该项目编号已被<" + response.data.user.userName + ">导入过～",
            type: "warning",
          });
        } else {
          this.$message({
            message: "该项目编号未被导入过～",
            type: "success",
          });
        }
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
    /** 发起审核 */
    handleReview(row) {
      this.$modal
        .confirm(
          '是否确认对审核单编号为"' + row.serialNum + '"的审核单发起审核申请?'
        )
        .then(() => {
          this.form.subcontractId = row.subcontractId;
          this.form.status = 1;
          setReviewStatus(this.form).then((response) => {
            this.getList();
            this.$modal.msgSuccess("已发起审核");
          });
        })
        .catch(() => {});
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
    getList() {
      this.loading = true;
      listSubcontract(this.queryParams).then((response) => {
        this.subcontractList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    getProjectList() {
      this.loading = true;

      fetchProjectData(this.queryProjectParams)
        .then((response) => {
          this.projectList = response.rows;
          this.projectTotal = response.total;
          this.loading = false;
        })
        .catch((error) => {
          this.$message.error("请求项目管理数据失败～");
        });

      // let res = {
      //   total: 1000, //总行数
      //   rows: [
      //     {
      //       XMBH: "项目编号1",
      //       XMMC: "项目名称1",
      //       GCNR: "工作内容1",
      //       WTDW: "委托单位1",
      //       GZL: "工作量1",
      //       YSJE: "预算金额1",
      //       XMKSSJ: "2023-08-08",
      //       XMJSSJ: "2023-08-14",
      //     },
      //     {
      //       XMBH: "项目编号2",
      //       XMMC: "项目名称2",
      //       GCNR: "工作内容2",
      //       WTDW: "委托单位2",
      //       GZL: "工作量2",
      //       YSJE: "预算金额2",
      //     },
      //   ],
      // };
      // this.projectList = res.rows;
      // this.projectTotal = res.total;
      // this.loading = false;
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
      this.getList();
    },
    handleProjectQuery() {
      this.queryProjectParams.pageNum = 1;
      this.getProjectList();
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
      this.$confirm("是否需要获取项目管理系统数据?", "提示", {
        confirmButtonText: "是",
        cancelButtonText: "否",
        type: "success",
      })
        .then(() => {
          this.projectInfo = "工程项目列表";
          this.projectOpen = true;
          this.getProjectList();
        })
        .catch(() => {
          this.open = true;
          this.title = "添加分包";
        });
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
              this.getList();
            });
          } else {
            addSubcontract(this.form).then((response) => {
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
      const subcontractIds = row.subcontractId || this.ids;
      this.$modal
        .confirm('是否确认删除分包编号为"' + subcontractIds + '"的数据项？')
        .then(function () {
          return delSubcontract(subcontractIds);
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
        "system/subcontract/export",
        {
          ...this.queryParams,
        },
        `subcontract_${new Date().getTime()}.xlsx`
      );
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
