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
      <el-form-item label="日志日期" prop="logDate">
        <el-date-picker
          clearable
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="handleQuery"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="用户名称" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名称"
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
          v-hasPermi="['system:geoLog:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:geoLog:export']"
          >导出</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExportWord"
          v-hasPermi="['system:geoLog:exportWord']"
          >word导出</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExportExcel"
          v-hasPermi="['system:geoLog:exportExcel']"
          >excle导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="logList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        label="日志日期"
        align="center"
        prop="logDate"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.logDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名称" align="center" prop="userName" />
      <el-table-column
        label="工作内容"
        align="center"
        prop="geoLogInfo"
        width="180"
      >
        <template slot-scope="scope">
          <div v-for="(unit, index) in scope.row.geoLogInfo" :key="index">
            {{ index + 1 }}:{{ unit.workdetail }}
          </div>
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
            icon="el-icon-tickets"
            @click="handleDetail(scope.row)"
            >详情</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-if="showButton(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="showButton(scope.row)"
            @click="handleDelete(scope.row)"
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

    <!-- 添加或修改地理部门日志对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1400px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="日志日期" prop="logDate">
          <el-date-picker
            clearable
            v-model="form.logDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择日志日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-collapse v-model="activeNames">
          <el-collapse-item title="日志内容" name="1">
            <el-button
              v-if="form.geoLogInfo != null && form.geoLogInfo.length == 0"
              type="text"
              icon="el-icon-circle-plus"
              size="medium"
              style="margin-left: 20px; margin-bottom: 20px"
              @click="addGeoLogInfo()"
            ></el-button>
            <el-form-item
              v-for="(logInfo, index) in form.geoLogInfo"
              :key="index"
              prop="geoLogInfo"
            >
              <el-row>
                <el-col :span="4" style="margin-left: -60px">
                  <el-form-item label="类型" prop="typeArrJson">
                    <el-cascader
                      filterable
                      :options="typeList"
                      v-model="logInfo.typeArrJson"
                      :show-all-levels="false"
                      @change="handleCascaderChange(index, $event)"
                    ></el-cascader>
                  </el-form-item>
                </el-col>
                <el-col :span="1">
                  <el-tooltip
                    :content="logInfo.remark"
                    placement="bottom"
                    effect="dark"
                  >
                    <el-form-item label="">
                      <el-button icon="el-icon-info" size="mini"
                        >备注</el-button
                      >
                    </el-form-item>
                  </el-tooltip>
                </el-col>
                <el-col :span="3">
                  <el-form-item label="难度" prop="difficulty">
                    <el-select
                      @change="handleDifficultyChange()"
                      v-model="logInfo.difficulty"
                      placeholder="请选择"
                      :disabled="logInfo.disabled"
                    >
                      <el-option
                        v-for="item in difficultys"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item label="工作量" prop="workload">
                    <el-input-number
                      v-model="logInfo.workload"
                      :precision="2"
                      :step="0.1"
                      :min="0"
                      :controls="false"
                      size="small"
                      :placeholder="logInfo.unit"
                    ></el-input-number>
                  </el-form-item>
                </el-col>
                <el-col :span="1.5" style="font-weight: bold">
                  {{ logInfo.unit }}</el-col
                >
                <el-col :span="4">
                  <el-form-item label="工作内容" prop="workdetail">
                    <el-input
                      type="textarea"
                      :rows="1"
                      v-model="logInfo.workload"
                      placeholder="请输入工作详情"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item label="关联项目">
                    <el-select
                      v-model="logInfo.projectId"
                      placeholder="请选择"
                      filterable
                      clearable
                      @change="handleProjectIdChange()"
                    >
                      <el-option
                        v-for="item in projectList"
                        :key="item.projectId"
                        :label="item.projectNum"
                        :value="item.projectId"
                        :disabled="item.disabled"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col style="display: none">
                  <el-form-item label="单价">
                    <el-input v-model="logInfo.typeMoney" disabled> </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="2">
                  <el-button
                    v-if="index != 0 || form.geoLogInfo.length == 1"
                    type="text"
                    icon="el-icon-circle-plus"
                    size="medium"
                    style="margin-left: 20px; margin-bottom: 20px"
                    @click="addGeoLogInfo()"
                  ></el-button>
                  <el-button
                    type="text"
                    icon="el-icon-remove"
                    size="medium"
                    style="margin-left: 20px; margin-bottom: 20px"
                    @click="removeGeoLogInfo(index)"
                  ></el-button>
                </el-col>
              </el-row>
            </el-form-item>
          </el-collapse-item>
        </el-collapse>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="详情"
      :visible.sync="detailOpen"
      width="1400px"
      append-to-body
    >
      <el-form
        ref="detailForm"
        :model="detailForm"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="日志日期" prop="logDate">
          <el-date-picker
            clearable
            v-model="detailForm.logDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择日志日期"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-collapse v-model="activeNames">
          <el-collapse-item title="日志内容" name="1">
            <el-form-item
              v-for="(logInfo, index) in detailForm.geoLogInfo"
              :key="index"
              prop="geoLogInfo"
            >
              <el-row>
                <el-col :span="4">
                  <el-form-item
                    label="类型"
                    prop="typeArrJson"
                    style="margin-left: -60px"
                  >
                    <el-cascader
                      disabled
                      filterable
                      :options="typeList"
                      v-model="logInfo.typeArrJson"
                      :show-all-levels="false"
                      @change="handleCascaderChange(index, $event)"
                    ></el-cascader>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item label="难度" prop="difficulty">
                    <el-select
                      disabled
                      @change="handleDifficultyChange()"
                      v-model="logInfo.difficulty"
                      placeholder="请选择"
                    >
                      <el-option
                        v-for="item in difficultys"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item label="工作量" prop="workload">
                    <el-input-number
                      v-model="logInfo.workload"
                      :precision="2"
                      :step="0.1"
                      :min="0"
                      :controls="false"
                      size="small"
                      :placeholder="logInfo.unit"
                      disabled
                    ></el-input-number>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item label="工作内容" prop="workdetail">
                    <el-input
                      type="textarea"
                      :rows="1"
                      v-model="logInfo.workload"
                      placeholder="请输入工作详情"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item label="关联项目">
                    <el-select
                      v-model="logInfo.projectId"
                      placeholder="请选择"
                      filterable
                      clearable
                      @change="handleProjectIdChange()"
                      disabled
                    >
                      <el-option
                        v-for="item in projectList"
                        :key="item.projectId"
                        :label="item.projectNum"
                        :value="item.projectId"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form-item>
          </el-collapse-item>
        </el-collapse>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {
  listLog,
  getLog,
  delLog,
  addLog,
  updateLog,
  listLogExport,
  listLogExportWord,
} from "@/api/system/geoLog";
import { listType } from "@/api/system/geoType";
import { listProject } from "@/api/system/geoProject";
import userInfo from "@/store/modules/user";
import { exportMultipleDocx } from "@/utils/zip.js";
import { utils, writeFile } from "xlsx";

export default {
  name: "Log",
  data() {
    return {
      projectList: [],
      queryProjectParams: {
        pageNum: 1,
        pageSize: 9999,
      },
      difficultys: [
        {
          value: 0,
          label: "极简单(0.5)",
        },
        {
          value: 1,
          label: "简单(0.8)",
        },
        {
          value: 2,
          label: "一般(1)",
        },
        {
          value: 3,
          label: "困难(1.2)",
        },
        {
          value: 4,
          label: "极困难(1.5)",
        },
      ],
      typeListMap: {},
      queryTypeParams: {
        pageNum: 1,
        pageSize: 9999,
      },
      typeList: [],
      logId: 0,
      activeNames: ["1"],
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
      // 地理部门日志表格数据
      logList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      detailOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        logDate: null,
        userId: null,
        userName: null,
      },
      // 表单参数
      form: {},
      detailForm: {},
      // 表单校验
      rules: {
        logDate: [
          { required: true, message: "日志日期不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    listType(this.queryTypeParams).then((response) => {
      this.typeList = response.rows;
      this.typeList = convertToTree(this.typeList);
      this.typeListMap = new Map();

      for (var i = 0; i < response.rows.length; i++) {
        this.typeListMap.set(response.rows[i].typeId, response.rows[i]);
      }
    });
    listProject(this.queryProjectParams).then((response) => {
      this.projectList = response.rows;
      const userId = userInfo.state.userId;
      for (var i = 0; i < this.projectList.length; i++) {
        if (this.projectList[i].userId == userId) {
          this.projectList[i].disabled = true;
        }
      }
    });
  },
  methods: {
    showButton(user) {
      const now = new Date();
      const createTimeDate = new Date(user.createTime);
      const todayStart = new Date(
        now.getFullYear(),
        now.getMonth(),
        now.getDate()
      );
      return (
        createTimeDate > todayStart && user.userId == userInfo.state.userId
      );
    },
    handleProjectIdChange() {
      this.$forceUpdate();
    },
    handleDifficultyChange() {
      this.$forceUpdate();
    },
    handleCascaderChange(index, value) {
      const typeId = value[value.length - 1];
      const typeObj = this.typeListMap.get(typeId);
      this.form.geoLogInfo[index].remark = typeObj.remark;
      this.form.geoLogInfo[index].typeMoney = typeObj.typeMoney;
      this.form.geoLogInfo[index].unit = "单位:" + typeObj.unit;
      this.form.geoLogInfo[index].difficulty = 2;
      this.form.geoLogInfo[index].typeId = typeId;
      this.form.geoLogInfo[index].degree = typeObj.degree;
      if (typeObj.degree === 0) {
        this.form.geoLogInfo[index].disabled = true;
      } else {
        this.form.geoLogInfo[index].disabled = false;
      }
      this.$forceUpdate();
    },
    removeGeoLogInfo(index) {
      this.form.geoLogInfo.splice(index, 1);
      this.$forceUpdate();
    },
    addGeoLogInfo() {
      this.form.geoLogInfo.push({
        difficulty: 2,
      });
      this.$forceUpdate();
    },
    /** 查询地理部门日志列表 */
    getList() {
      this.loading = true;
      listLog(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.logList = response.rows;
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
        logId: null,
        logDate: null,
        userId: null,
        userName: null,
        createTime: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      if (this.dateRange.length != 0) {
        this.queryParams.logDate = this.dateRange[1];
      }
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.logId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      var currentDate = new Date();
      var year = currentDate.getFullYear();
      var month = ("0" + (currentDate.getMonth() + 1)).slice(-2); // +1 是因为月份从 0 开始计数
      var day = ("0" + currentDate.getDate()).slice(-2);
      var formattedDate = year + "-" + month + "-" + day;
      this.form.logDate = formattedDate;

      this.form.geoLogInfo = [];
      this.open = true;
      this.title = "添加日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const logId = row.logId || this.ids;
      getLog(logId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改日志";
      });
    },

    handleDetail(row) {
      const logId = row.logId || this.ids;
      getLog(logId).then((response) => {
        this.detailForm = response.data;
        this.detailOpen = true;
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.logId != null) {
            updateLog(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if (this.form.geoLogInfo.length == 0) {
              this.$modal.msgError("请填写日志内容");
              return;
            } else {
              for (var j = 0; j < this.form.geoLogInfo.length; j++) {
                if (
                  this.form.geoLogInfo[j].typeId == undefined ||
                  this.form.geoLogInfo[j].typeId == null ||
                  this.form.geoLogInfo[j].typeId == 0 ||
                  this.form.geoLogInfo[j].workload == undefined ||
                  this.form.geoLogInfo[j].workload == null
                ) {
                  this.$modal.msgError("请填写完整的日志内容");
                  return;
                }
              }
            }
            addLog(this.form).then((response) => {
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
      const logIds = row.logId || this.ids;
      this.$modal
        .confirm('是否确认删除日志编号为"' + logIds + '"的数据项？')
        .then(function () {
          return delLog(logIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      if (this.dateRange.length == 0) {
        this.$modal.msgError("请填写选择日期范围");
        return;
      }
      listLogExport(this.queryParams).then((response) => {
        const dataArray = response.rows;
        const zipFileName =
          this.dateRange[0] + "~" + this.dateRange[1] + "地理信息部产值.zip";
        exportMultipleDocx("/log_personal.docx", dataArray, zipFileName);
      });
    },
    /** word导出按钮操作 */
    handleExportWord() {
      if (this.dateRange.length == 0) {
        this.$modal.msgError("请填写选择日期范围");
        return;
      }
      listLogExportWord(this.queryParams).then((response) => {
        const dataArray = response.rows;
        const zipFileName =
          this.dateRange[0] + "~" + this.dateRange[1] + "地理信息部产值.zip";
        exportMultipleDocx("/log_word.docx", dataArray, zipFileName);
      });
    },
    /** excel导出按钮操作 */
    handleExportExcel() {
      if (this.dateRange.length == 0) {
        this.$modal.msgError("请填写选择日期范围");
        return;
      }
      listLogExportWord(this.queryParams).then((response) => {
        const dataArray = response.rows;
        const excelData = [["人员", "产值"]];
        for (var i = 0; i < dataArray.length; i++) {
          let data = [];
          data[0] = dataArray[i].user_name;
          data[1] = dataArray[i].total_money.toFixed(2);
          excelData.push(data);
        }
        const wb = utils.book_new();
        const ws = utils.aoa_to_sheet(excelData);
        utils.book_append_sheet(wb, ws, "Sheet1");
        const fileName =
          this.dateRange[0] + "~" + this.dateRange[1] + "地理信息部产值";
        writeFile(wb, fileName + ".xlsx");
      });
    },
  },
};

function convertToTree(data) {
  const map = {};
  const tree = [];

  data.forEach((item) => {
    map[item.typeId] = item;
    item.children = [];
  });

  data.forEach((item) => {
    if (item.parentId !== 0) {
      if (map[item.parentId]) {
        map[item.parentId].children.push(item);
      }
    } else {
      tree.push(item);
    }
  });

  function renameKeys(obj) {
    const renamed = {};
    for (let prop in obj) {
      if (prop === "typeId") {
        renamed["value"] = obj[prop];
      } else if (prop === "typeName") {
        renamed["label"] = obj[prop];
      } else {
        renamed[prop] = obj[prop];
      }
    }
    return renamed;
  }

  function traverseAndRename(node) {
    node = renameKeys(node);
    if (node.children.length > 0) {
      node.children = node.children.map((child) => {
        const modifiedChild = traverseAndRename(child);
        delete modifiedChild.children; // 移除最后一级的 children 键
        return modifiedChild;
      });
    }
    return node;
  }

  return tree.map(traverseAndRename);
}
</script>
