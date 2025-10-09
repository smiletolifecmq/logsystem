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
      <el-form-item label="专业类型" prop="zylx">
        <el-input
          v-model="queryParams.zylx"
          placeholder="请输入专业类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目类型" prop="xmlx">
        <el-input
          v-model="queryParams.xmlx"
          placeholder="请输入项目类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目编号" prop="xmbh">
        <el-input
          v-model="queryParams.xmbh"
          placeholder="请输入项目编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务安排日期" prop="rwaprq" label-width="100px">
        <el-date-picker
          v-model="dateRange"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="handleQuery"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="作业人员" prop="zyry">
        <el-input
          v-model="queryParams.zyry"
          placeholder="请输入作业人员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="任务安排人员" prop="rwapry" label-width="100px">
        <el-input
          v-model="queryParams.rwapry"
          placeholder="请输入任务安排人员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业主单位" prop="yzdw">
        <el-input
          v-model="queryParams.yzdw"
          placeholder="请输入业主单位"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:serviceWork:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:serviceWork:export']"
          >导出Excel</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExportRjkfwh"
          v-hasPermi="['system:serviceWork:exportRjkfwh']"
          >导出Word(软件开发维护)</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExportSjcl"
          v-hasPermi="['system:serviceWork:exportSjcl']"
          >导出Word(数据处理)</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="workList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="专业类型" align="center" prop="zylx" />
      <el-table-column label="项目类型" align="center" prop="xmlx" />
      <el-table-column label="项目编号" align="center" prop="xmbh" />
      <el-table-column label="项目名称" align="center" prop="xmmc" />
      <el-table-column label="文件名称/功能名称" align="center" prop="gnmc" />
      <el-table-column label="工作内容" align="center" prop="gznr">
        <template slot-scope="scope">
          <el-tooltip
            class="item"
            effect="dark"
            placement="top-start"
            popper-class="tooltip-style"
          >
            <div slot="content">
              {{ scope.row.gznr }}
            </div>
            <span>
              {{
                scope.row.gznr && scope.row.gznr.length > 20
                  ? scope.row.gznr.slice(0, 20) + "..."
                  : scope.row.gznr
              }}
            </span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="数量" align="center" prop="sl" />
      <el-table-column label="单位" align="center" prop="dw" />
      <el-table-column label="单价" align="center" prop="dj" />
      <el-table-column label="费用（元）" align="center" prop="fy" />
      <el-table-column
        label="任务安排日期"
        align="center"
        prop="rwaprq"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.rwaprq, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="作业人员" align="center" prop="zyry" />
      <el-table-column label="作业时间段" align="center" prop="zysjd" />
      <el-table-column label="作业时长" align="center" prop="zysc" />
      <el-table-column label="任务安排人员" align="center" prop="rwapry" />
      <el-table-column label="业主单位" align="center" prop="yzdw" />
      <el-table-column label="是否有归档资料" align="center" prop="gdcl" />
      <el-table-column label="无归档情况说明" align="center" prop="gdsm" />
      <el-table-column label="是否为已下单项目" align="center" prop="xdxm" />
      <el-table-column
        label="操作"
        align="center"
        fixed="right"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            @click="handleYy(scope.row)"
            >引用</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:serviceWork:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:serviceWork:remove']"
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

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="137px">
        <el-form-item label="专业类型" prop="zylx">
          <el-select v-model="form.zylx" placeholder="请选择">
            <el-option
              v-for="item in zylxs"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目类型" prop="xmlxid">
          <el-select
            v-model="form.xmlxid"
            placeholder="请选择"
            filterable
            @change="handleXmlxChange"
          >
            <el-option
              v-for="item in xmlxs"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目编号" prop="xmbh">
          <el-input v-model="form.xmbh" placeholder="请输入项目编号">
            <el-button slot="append" type="primary" @click="handleReference">
              引用
            </el-button></el-input
          >
        </el-form-item>
        <el-form-item label="项目名称" prop="xmmc">
          <el-input
            v-model="form.xmmc"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="文件名称/功能名称" prop="gnmc">
          <el-input
            v-model="form.gnmc"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="工作内容" prop="gznr">
          <el-input
            v-model="form.gznr"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="数量" prop="sl">
          <el-input-number
            v-model="form.sl"
            :precision="2"
            :step="0.1"
          ></el-input-number>
          <span style="color: red"> 单位：{{ dwInfo }}</span>
        </el-form-item>
        <el-form-item label="任务安排日期" prop="rwaprq">
          <el-date-picker
            clearable
            v-model="form.rwaprq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择任务安排日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="作业人员" prop="zyry">
          <el-input v-model="form.zyry" placeholder="请输入作业人员" />
        </el-form-item>
        <el-form-item label="作业时间段" prop="zysjd">
          <el-input v-model="form.zysjd" placeholder="请输入作业时间段" />
        </el-form-item>
        <el-form-item label="作业时长" prop="zysc">
          <el-input v-model="form.zysc" placeholder="请输入作业时长" />
        </el-form-item>
        <el-form-item label="任务安排人员" prop="rwapry">
          <el-input v-model="form.rwapry" placeholder="请输入任务安排人员" />
        </el-form-item>
        <el-form-item label="业主单位" prop="yzdw">
          <el-input v-model="form.yzdw" placeholder="请输入业主单位" />
        </el-form-item>
        <el-form-item label="是否有归档资料" prop="gdcl">
          <el-select v-model="form.gdcl" placeholder="请选择">
            <el-option
              v-for="item in gdcls"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="无归档情况说明" prop="gdsm">
          <el-input
            v-model="form.gdsm"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="是否为已下单项目" prop="xdxm">
          <el-select v-model="form.xdxm" placeholder="请选择">
            <el-option
              v-for="item in xdxms"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="项目列表"
      :visible.sync="xmopen"
      width="1200px"
      append-to-body
    >
      <el-form
        :model="queryParamsxm"
        ref="queryFormxm"
        size="small"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
      >
        <el-form-item label="项目编号" prop="projectNum">
          <el-input
            v-model="queryParamsxm.projectNum"
            placeholder="请输入项目编号"
            clearable
            @keyup.enter.native="handleQueryxm"
          />
        </el-form-item>
        <el-form-item label="项目名称" prop="projectName">
          <el-input
            v-model="queryParamsxm.projectName"
            placeholder="请输入项目名称"
            clearable
            @keyup.enter.native="handleQueryxm"
          />
        </el-form-item>
        <el-form-item label="负责人" prop="userName">
          <el-input
            v-model="queryParamsxm.userName"
            placeholder="请输入负责人名称"
            clearable
            @keyup.enter.native="handleQueryxm"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleQueryxm"
            >搜索</el-button
          >
          <el-button icon="el-icon-refresh" size="mini" @click="resetQueryxm"
            >重置</el-button
          >
        </el-form-item>
      </el-form>

      <el-table v-loading="loading" :data="projectList">
        <el-table-column label="项目编号" align="center" prop="projectNum" />
        <el-table-column label="项目名称" align="center" prop="projectName" />
        <el-table-column label="负责人名称" align="center" prop="userName" />
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-s-promotion"
              @click="handleYyXm(scope.row)"
              >引用</el-button
            ></template
          >
        </el-table-column>
      </el-table>

      <pagination
        v-show="totalxm > 0"
        :total="totalxm"
        :page.sync="queryParamsxm.pageNum"
        :limit.sync="queryParamsxm.pageSize"
        @pagination="getListxm"
      />
    </el-dialog>
  </div>
</template>
<style>
.tooltip-style {
  max-width: 400px; /* 限制最大宽度 */
  white-space: normal !important; /* 允许换行 */
  word-break: break-all; /* 自动断行 */
}
</style>

<script>
import {
  listWork,
  getWork,
  delWork,
  addWork,
  updateWork,
} from "@/api/system/work";
import { listType } from "@/api/system/serverType";
import userInfo from "@/store/modules/user";
import { listProject } from "@/api/system/geoProject";
import { exportSubDocx } from "@/utils/doc.js";

export default {
  name: "Work",
  data() {
    return {
      dwInfo: "",
      rules: {
        zylx: [{ required: true, trigger: "blur", message: "请选择专业类型" }],
        xmlxid: [
          { required: true, trigger: "blur", message: "请输入项目类型" },
        ],
      },
      projectList: [],
      xmopen: false,
      dateRange: [],
      xmlxs: [],
      zylxs: [
        {
          value: "软件开发维护",
          label: "软件开发维护",
        },
        {
          value: "数据处理",
          label: "数据处理",
        },
      ],
      xdxms: [
        {
          value: "是",
          label: "是",
        },
        {
          value: "否",
          label: "否",
        },
      ],
      gdcls: [
        {
          value: "是",
          label: "是",
        },
        {
          value: "否",
          label: "否",
        },
      ],
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
      totalxm: 0,
      // 【请填写功能名称】表格数据
      workList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      allxmlx: [],
      // 查询参数
      queryParamsxm: {
        pageNum: 1,
        pageSize: 10,
        projectNum: null,
        projectName: null,
        userName: null,
      },
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        zylx: null,
        xmlx: null,
        xmbh: null,
        xmmc: null,
        gnmc: null,
        gznr: null,
        sl: null,
        rwaprq: null,
        zyry: null,
        zysjd: null,
        zysc: null,
        rwapry: null,
        yzdw: null,
        gdcl: null,
        gdsm: null,
        xdxm: null,
      },
      // 表单参数
      form: {},
      // 表单校验
    };
  },
  created() {
    listType().then((response) => {
      this.allxmlx = response.rows;
      for (var i = 0; i < response.rows.length; i++) {
        if (response.rows[i].suffix != null && response.rows[i].suffix != "") {
          this.xmlxs.push({
            value: response.rows[i].id,
            label: response.rows[i].xmlx + "-" + response.rows[i].suffix,
          });
        } else {
          this.xmlxs.push({
            value: response.rows[i].id,
            label: response.rows[i].xmlx,
          });
        }
      }
    });
    this.getList();
  },
  methods: {
    handleXmlxChange(value) {
      for (var i = 0; i < this.allxmlx.length; i++) {
        if (value === this.allxmlx[i].id) {
          this.dwInfo = this.allxmlx[i].dw;
          return;
        }
      }
    },
    handleYyXm(value) {
      this.form.xmbh = value.projectNum;
      this.form.xmmc = value.projectName;
      this.xmopen = false;
    },
    resetQueryxm() {
      this.resetForm("queryFormxm");
      this.handleQueryxm();
    },
    handleQueryxm() {
      this.queryParamsxm.pageNum = 1;
      this.getListxm();
    },
    getListxm() {
      this.loading = true;
      listProject(this.queryParamsxm).then((response) => {
        this.projectList = response.rows;
        this.totalxm = response.total;
        this.loading = false;
      });
    },
    handleReference() {
      this.xmopen = true;
      this.queryParamsxm = {
        pageNum: 1,
        pageSize: 10,
        projectNum: null,
        projectName: null,
        userName: null,
      };
      this.getListxm();
    },
    showButton(userName) {
      return userName == userInfo.state.name;
    },
    getNowDate() {
      const date = new Date();
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, "0");
      const day = date.getDate().toString().padStart(2, "0");
      return `${year}-${month}-${day}`;
    },
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listWork(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.workList = response.rows;
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
        id: null,
        zylx: null,
        xmlx: null,
        xmbh: null,
        xmmc: null,
        gnmc: null,
        gznr: null,
        sl: null,
        rwaprq: null,
        zyry: null,
        zysjd: null,
        zysc: null,
        rwapry: null,
        yzdw: null,
        gdcl: null,
        gdsm: null,
        xdxm: null,
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
      this.dateRange = [];
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form.rwaprq = this.getNowDate(); // 默认当前日期
      this.form.zyry = userInfo.state.name;
      this.open = true;
      this.title = "添加内部服务工作清单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getWork(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改内部服务工作清单";
      });
    },
    handleYy(row) {
      this.reset();
      const id = row.id || this.ids;
      getWork(id).then((response) => {
        this.form = response.data;
        this.form.id = null;
        this.form.rwaprq = this.getNowDate(); // 默认当前日期
        this.form.zyry = userInfo.state.name;
        this.open = true;
        this.title = "添加内部服务工作清单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateWork(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWork(this.form).then((response) => {
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
      const ids = row.id || this.ids;
      this.$modal
        .confirm(
          '是否确认删除【内部服务工作清单】编号为"' + ids + '"的数据项？'
        )
        .then(function () {
          return delWork(ids);
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
        "system/serviceWork/export",
        {
          ...this.queryParams,
        },
        `地理信息工作内部服务工作清单_${new Date().getTime()}.xlsx`
      );
    },
    handleExportRjkfwh() {
      if (this.dateRange.length == 0) {
        this.$message.error("请选择任务安排日期～");
        return;
      }
      listWork(
        this.addDateRange({ pageNum: 1, pageSize: 9999 }, this.dateRange)
      ).then((response) => {
        var data = {
          form: {
            startTime: this.dateRange[0],
            endTime: this.dateRange[1],
            gzlx: "软件开发维护",
            totalMoney: 0,
          },
          list: [],
        };
        var num = 1;

        for (var i = 0; i < response.rows.length; i++) {
          if (response.rows[i].zylxid == 2) {
            continue;
          }
          if (
            response.rows[i].xmmc != undefined &&
            response.rows[i].xmmc != null &&
            response.rows[i].xmmc != ""
          ) {
            data.list.push({
              content:
                num +
                "、" +
                response.rows[i].xmlx +
                "-" +
                response.rows[i].xmmc +
                "，" +
                response.rows[i].sl +
                response.rows[i].dw +
                "，" +
                response.rows[i].dj +
                "；",
            });
          } else {
            data.list.push({
              content:
                num +
                "、" +
                response.rows[i].xmlx +
                "，" +
                response.rows[i].sl +
                response.rows[i].dw +
                "，" +
                response.rows[i].dj +
                "；",
            });
          }

          data.form.totalMoney = data.form.totalMoney + response.rows[i].fy;
          num++;
        }
        data.form.totalMoney = data.form.totalMoney.toFixed(2);
        exportSubDocx(
          "/nbspb.docx",
          data,
          "项目任务审批表-软件开发维护部分.docx"
        );
      });
    },
    handleExportSjcl() {
      if (this.dateRange.length == 0) {
        this.$message.error("请选择任务安排日期～");
        return;
      }
      listWork(
        this.addDateRange({ pageNum: 1, pageSize: 9999 }, this.dateRange)
      ).then((response) => {
        var data = {
          form: {
            startTime: this.dateRange[0],
            endTime: this.dateRange[1],
            gzlx: "数据处理",
            totalMoney: 0,
          },
          list: [],
        };
        var num = 1;

        for (var i = 0; i < response.rows.length; i++) {
          if (response.rows[i].zylxid == 1) {
            continue;
          }
          if (
            response.rows[i].xmmc != undefined &&
            response.rows[i].xmmc != null &&
            response.rows[i].xmmc != ""
          ) {
            data.list.push({
              content:
                num +
                "、" +
                response.rows[i].xmlx +
                "-" +
                response.rows[i].xmmc +
                "，" +
                response.rows[i].sl +
                response.rows[i].dw +
                "，" +
                response.rows[i].dj +
                "；",
            });
          } else {
            data.list.push({
              content:
                num +
                "、" +
                response.rows[i].xmlx +
                "，" +
                response.rows[i].sl +
                response.rows[i].dw +
                "，" +
                response.rows[i].dj +
                "；",
            });
          }

          data.form.totalMoney = data.form.totalMoney + response.rows[i].fy;
          num++;
        }
        data.form.totalMoney = data.form.totalMoney.toFixed(2);
        exportSubDocx("/nbspb.docx", data, "项目任务审批表-数据处理部分.docx");
      });
    },
  },
};
</script>
