<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24" v-if="shouldShowDept">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            node-key="id"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>

      <!--用户数据-->
      <el-col :span="isShow ? 20 : undefined" :xs="isShow ? 24 : undefined">
        <el-form
          :model="queryParams"
          ref="queryForm"
          size="small"
          :inline="true"
          v-show="showSearch"
          label-width="68px"
        >
          <el-form-item label="用户名称" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入用户名称"
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

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:log:add']"
              >新增</el-button
            >
          </el-col>
          <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:log:edit']"
        >修改</el-button>
      </el-col> -->
          <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:log:remove']"
        >删除</el-button>
      </el-col> -->
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:log:export']"
              >导出</el-button
            >
          </el-col>
          <right-toolbar
            :showSearch.sync="showSearch"
            @queryTable="getList"
          ></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="logList">
          <!-- <el-table v-loading="loading" :data="logList" @selection-change="handleSelectionChange"> -->
          <!-- <el-table-column type="selection" width="55" align="center" /> -->
          <el-table-column
            label="用户名称"
            align="center"
            prop="user.userName"
          />
          <el-table-column label="部门" align="center" prop="dept.deptName" />
          <el-table-column label="工作标题" align="center" prop="content">
            <!-- <template slot-scope="scope">
              <div class="content-wrapper">
                <el-tooltip :content="scope.row.content" placement="top">
                  <div
                    class="ellipsis"
                    v-text="truncate(scope.row.content, 10)"
                  ></div>
                </el-tooltip>
              </div>
            </template> -->
          </el-table-column>

          <el-table-column
            label="工作内容"
            align="center"
            prop="completeness"
          />
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
          <!-- <el-table-column label="修改时间" align="center" prop="updateTime" width="160">
        <template slot-scope="scope">
            <span>{{ parseTime(scope.row.updateTime) }}</span>
            </template>
      </el-table-column> -->
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
          >
            <!-- <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:log:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:log:remove']"
          >删除</el-button>
        </template> -->
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:log:edit']"
                v-if="showEditButton(scope.row.createTime, scope.row.userId)"
                >修改</el-button
              >
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:log:remove']"
                v-if="showDeleteButton(scope.row.createTime, scope.row.userId)"
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
      </el-col>
    </el-row>

    <!-- 修改用户日志对话框 -->
    <el-dialog
      :title="title"
      v-el-drag-dialog
      :visible.sync="open"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="工作标题">
          <el-input
            type="textarea"
            v-model="form.content"
            placeholder="请输入工作标题"
            :maxlength="1000"
            :rows="3"
            :autosize="{ minRows: 2 }"
          />
        </el-form-item>
        <el-form-item label="工作内容">
          <el-input
            type="textarea"
            v-model="form.completeness"
            placeholder="请输入工作基本内容"
            :rows="3"
            :maxlength="2000"
            :autosize="{ minRows: 2 }"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加用户日志对话框 -->
    <el-dialog
      :title="editTitle"
      v-el-drag-dialog
      :visible.sync="editOpen"
      append-to-body
    >
      <el-form
        ref="editform"
        :model="editform"
        :rules="editRules"
        label-width="80px"
      >
        <el-form-item
          prop="logs"
          label="工作标题 工作内容"
          v-for="(item, index) in editform.logs"
          :key="index"
        >
          <el-input
            type="textarea"
            v-model="item.content"
            placeholder="请输入工作标题"
            :maxlength="1000"
            :rows="3"
            :autosize="{ minRows: 2 }"
          />

          <div style="display: flex; flex-direction: row">
            <el-input
              type="textarea"
              v-model="item.completeness"
              placeholder="请输入工作基本内容"
              :rows="3"
              :maxlength="2000"
              :autosize="{ minRows: 2 }"
            />

            <el-button v-if="index > 0" type="danger" @click="deleteLog(index)"
              ><i class="el-icon-close"></i
            ></el-button>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addLogClick">新增</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAddForm">确定</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
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
  deptTreeSelect,
} from "@/api/system/log";
import Treeselect from "@riophae/vue-treeselect";
import elDragDialog from "@/api/components/el-drag";
import userInfo from "@/store/modules/user";

export default {
  // computed: {
  //   isOneDayBefore() {
  //     return (createTime) => {
  //       const oneDayInMs = 24 * 60 * 60 * 1000;
  //       const createTimeMs = new Date(createTime).getTime();
  //       const currentMs = new Date().getTime();
  //       return currentMs - createTimeMs >= oneDayInMs;
  //     };
  //   },
  // },
  name: "Log",
  components: { Treeselect },
  directives: {
    elDragDialog,
  },

  data() {
    return {
      isShow: true,
      shouldShowDept: false,
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
      // 用户日志表格数据
      logList: [],
      // 弹出层标题
      title: "",
      editTitle: "",
      // 是否显示弹出层
      open: false,
      editOpen: false,
      // 部门名称
      deptName: undefined,
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 部门树选项
      deptOptions: undefined,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        completeness: null,
        content: null,
        deptId: null,
      },

      // 表单参数
      form: {},
      editform: { logs: [{ content: "", completeness: "" }] },
      // 表单校验
      rules: {},
      editRules: {
        logs: [
          {
            validator: (rule, value, callback) => {
              if (!value || value.length === 0) {
                callback(new Error("至少添加一个工作日志"));
              } else {
                for (let i = 0; i < value.length; i++) {
                  if (!value[i].content || !value[i].completeness) {
                    callback(new Error("请填写完整的工作标题和工作基本内容"));
                    return;
                  }
                }
                callback();
              }
            },
            trigger: "blur",
            required: true, // 添加 required 属性
          },
        ],
      },
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    },
  },
  created() {
    this.getList();
    this.getDeptTree();
    this.checkShowDept();
  },
  methods: {
    checkShowDept() {
      if (userInfo.state.deptId == 100 || userInfo.state.deptId == 201) {
        this.shouldShowDept = true;
      } else {
        this.isShow = false;
      }
    },
    truncate(str, len) {
      if (str.length > len) {
        return str.substring(0, len) + "...";
      } else {
        return str;
      }
    },
    deleteLog(index) {
      this.editform.logs.splice(index, 1); // 删除对应索引的模块
    },
    addLogClick() {
      this.editform.logs.push({ content: "", completeness: "" });
    },

    showEditButton(createTime, id) {
      const userId = id;
      const now = new Date();
      const createTimeDate = new Date(createTime);
      const todayStart = new Date(
        now.getFullYear(),
        now.getMonth(),
        now.getDate()
      );
      return createTimeDate > todayStart && userId == userInfo.state.userId;
    },
    showDeleteButton(createTime, id) {
      const userId = id;
      const now = new Date();
      const createTimeDate = new Date(createTime);
      const todayStart = new Date(
        now.getFullYear(),
        now.getMonth(),
        now.getDate()
      );
      return createTimeDate > todayStart && userId == userInfo.state.userId;
    },
    /** 查询用户日志列表 */
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
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.handleQuery();
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then((response) => {
        this.deptOptions = response.data;
      });
    },
    // 取消按钮
    cancel() {
      this.editOpen = false;
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        userLogId: null,
        userId: null,
        completeness: null,
        content: null,
        createTime: null,
        updateTime: null,
      };
      this.editform = { logs: [{ content: "", completeness: "" }] };
      this.resetForm("form");
      this.resetForm("editform");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.queryParams.deptId = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.userLogId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.editOpen = true;
      this.editTitle = "添加用户日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const userLogId = row.userLogId || this.ids;
      getLog(userLogId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.userLogId != null) {
            updateLog(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    submitAddForm() {
      if (this.editform.logs.length === 0) {
        this.$message.error("请添加工作标题和工作基本内容");
        return;
      }
      this.$refs["editform"].validate((valid) => {
        if (valid) {
          addLog(this.editform).then((response) => {
            this.$modal.msgSuccess("新增成功");
            this.editOpen = false;
            this.dateRange = [];
            this.queryParams.deptId = null;
            this.resetForm("queryForm");
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const userLogIds = row.userLogId || this.ids;
      this.$modal
        .confirm('是否确认删除用户日志编号为"' + userLogIds + '"的数据项？')
        .then(function () {
          return delLog(userLogIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      let excelTime = "";
      if (this.dateRange.length == 2) {
        excelTime = this.dateRange[0] + "~" + this.dateRange[1];
      } else {
        // 获取当前时间戳
        const timestamp = new Date().getTime();

        // 将时间戳转换为Date对象
        const date = new Date(timestamp);

        // 将Date对象转换为指定格式的字符串
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, "0");
        const day = String(date.getDate()).padStart(2, "0");
        excelTime = `${year}-${month}-${day}`;
      }
      console.log(excelTime);
      this.download(
        "system/log/export",
        {
          ...this.queryParams,
        },
        `工作日志_` + excelTime + `.xlsx`
      );
    },
  },
};
</script>
