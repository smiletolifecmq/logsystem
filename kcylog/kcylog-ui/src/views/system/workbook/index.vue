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
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
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
          v-hasPermi="['system:workbook:add']"
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
          v-hasPermi="['system:workbook:export']"
          >合并导出pdf</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="workbookList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="简介" align="center" prop="introduction" />
      <el-table-column label="事件" align="center" prop="event" />
      <el-table-column label="附件" align="center">
        <template slot-scope="scope">
          <transition-group
            class="upload-file-list el-upload-list el-upload-list--text"
            name="el-fade-in-linear"
            tag="ul"
          >
            <li
              :key="file.url"
              class="el-upload-list__item ele-upload-list__item-content"
              v-for="(file, index) in scope.row.manageFile"
            >
              <el-link
                :href="`${baseUrl}${file.url}`"
                :underline="false"
                target="_blank"
              >
                <span class="el-icon-document">
                  {{ getFileName(file.fileName) }}
                </span>
              </el-link>
            </li>
          </transition-group>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="user.userName" />
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
        label="修改时间"
        align="center"
        prop="updateTime"
        width="160"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:workbook:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:workbook:remove']"
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

    <!-- 添加或修改作业手册对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="简介" prop="introduction">
          <el-input
            v-model="form.introduction"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="事件" prop="event">
          <el-input
            v-model="form.event"
            type="textarea"
            placeholder="请输入事件内容"
          />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number
            v-model="form.sort"
            :min="1"
            label="请输入排序"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="附件" required>
          <FileUpload
            ref="fileUploadModule"
            :fileSize="200"
            :fileType="fileType"
            :limit="1"
          ></FileUpload>
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
  listWorkbook,
  getWorkbook,
  delWorkbook,
  addWorkbook,
  updateWorkbook,
} from "@/api/system/workbook";
import FileUpload from "@/components/FileUpload";

export default {
  name: "Workbook",
  components: {
    FileUpload,
  },
  props: {
    fileType: {
      type: Array,
      default: () => ["pdf"],
    },
  },
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
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
      // 作业手册表格数据
      workbookList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        title: null,
        introduction: null,
        event: null,
        sort: null,
      },
      // 表单参数
      form: {},
      uploadFileList: [],
      // 表单校验
      rules: {
        title: [{ required: true, message: "标题不能为空", trigger: "blur" }],
        sort: [{ required: true, message: "排序不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 获取文件名称
    getFileName(name) {
      if (name.lastIndexOf("/") > -1) {
        return name.slice(name.lastIndexOf("/") + 1);
      } else {
        return "";
      }
    },
    /** 查询作业手册列表 */
    getList() {
      this.loading = true;
      listWorkbook(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.workbookList = response.rows;
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
      if (this.$refs.fileUploadModule != null) {
        this.$refs.fileUploadModule.number = 0;
        this.$refs.fileUploadModule.uploadList = [];
        this.$refs.fileUploadModule.fileList = [];
      }

      this.uploadFileList = [];
      this.form = {
        workbookId: null,
        userId: null,
        title: null,
        introduction: null,
        event: null,
        sort: null,
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
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.workbookId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加作业手册";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const workbookId = row.workbookId || this.ids;
      getWorkbook(workbookId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改作业手册";
        this.$nextTick(() => {
          if (this.$refs.fileUploadModule) {
            this.$refs.fileUploadModule.number = 0;
            this.$refs.fileUploadModule.uploadList = [];
            this.$refs.fileUploadModule.fileList = [];
            for (let i = 0; i < response.data.manageFile.length; i++) {
              let fileTemp = {};
              fileTemp.name = response.data.manageFile[i].fileName;
              fileTemp.url = response.data.manageFile[i].url;
              this.$refs.fileUploadModule.fileList.push(fileTemp);
            }
          }
        });
      });
    },
    /** 提交按钮 */
    submitForm() {
      const uploadListComponent = this.$refs.fileUploadModule;
      const fileList = uploadListComponent.fileList;
      for (let i in fileList) {
        let obj = {};
        let fileName = fileList[i].name.split("/");
        obj.newFileName = fileName[fileName.length - 1];
        obj.oldFileName =
          fileName[fileName.length - 1].split("_")[0] +
          "." +
          fileName[fileName.length - 1].split(".")[1];
        obj.fileName = fileList[i].name;
        obj.url = fileList[i].url;
        this.uploadFileList.push(obj);
      }
      if (this.uploadFileList.length == 0) {
        this.$message.error("请上传附件～");
        return;
      }
      this.form.uploadFileList = this.uploadFileList;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.workbookId != null) {
            updateWorkbook(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWorkbook(this.form).then((response) => {
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
      const workbookIds = row.workbookId || this.ids;
      this.$modal
        .confirm('是否确认删除作业手册编号为"' + workbookIds + '"的数据项？')
        .then(function () {
          return delWorkbook(workbookIds);
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
        "system/workbook/export",
        {
          ...this.queryParams,
        },
        `workbook_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
