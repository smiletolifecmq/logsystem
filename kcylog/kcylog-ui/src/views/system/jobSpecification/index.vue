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
      <el-form-item label="关键字" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标签或文件名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标准号" prop="standard">
        <el-input
          v-model="queryParams.standard"
          placeholder="请输入标准号"
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
          v-hasPermi="['system:specification:add']"
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
      :data="specificationList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="文件" align="center">
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
              <el-button
                size="mini"
                @click="downloadFile(file.fileName, file.oldFileName)"
                >预览失败点此下载</el-button
              >
            </li>
          </transition-group>
        </template>
      </el-table-column>
      <el-table-column label="标签" align="center" prop="title" />
      <el-table-column label="标准号" align="center" prop="standard" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="创建人" align="center" prop="user.userName" />
      <el-table-column
        label="上传时间"
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:specification:edit']"
            v-if="showButton(scope.row.userId)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:specification:remove']"
            v-if="showButton(scope.row.userId)"
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

    <!-- 添加或修改作业规范对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文件" required>
          <FileUpload
            ref="fileUploadModule"
            :fileSize="200"
            :fileType="fileType"
            :limit="1"
            @fileUploaded="handleFileUploaded"
          ></FileUpload>
        </el-form-item>

        <el-form-item label="标准号" prop="standard">
          <el-input v-model="form.standard" placeholder="请输入标准号" />
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入备注内容"
          />
        </el-form-item>

        <el-form-item label="标签" prop="title">
          <el-input
            v-model="form.title"
            placeholder="提示:多个标签用英文的;进行隔开"
          />
        </el-form-item>
        <el-form-item label="可选字符">
          <el-row>
            <el-col :span="4" v-for="letter in letters">
              <el-button @click="selectLetter(letter)">{{ letter }}</el-button>
            </el-col>
          </el-row>
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
  listSpecification,
  getSpecification,
  delSpecification,
  addSpecification,
  updateSpecification,
} from "@/api/system/jobSpecification";
import FileUpload from "@/components/FileUpload";
import userInfo from "@/store/modules/user";

export default {
  name: "Specification",
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
      letters: [],
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
      // 作业规范表格数据
      specificationList: [],
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
      },
      // 表单参数
      form: {},
      uploadFileList: [],
      // 表单校验
      rules: {
        title: [{ required: true, message: "标签不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    downloadFile(url, fileName) {
      this.downloadGet("/common/download/resource?resource=" + url, fileName);
    },
    handleFileUploaded(uploadedFile) {
      this.letters = [];
      let selectStr = uploadedFile.originalFilename.split(".")[0] + ";";
      for (let i = 0; i < selectStr.length; i++) {
        let letter = selectStr.charAt(i);
        if (letter == " ") {
          continue;
        }
        this.letters.push(letter);
      }
    },
    selectLetter(letter) {
      if (this.form.title == null) {
        this.form.title = "";
      }
      this.form.title = this.form.title + letter;
    },
    showButton(userId) {
      return userId == userInfo.state.userId;
    },
    // 获取文件名称
    getFileName(name) {
      if (name.lastIndexOf("/") > -1) {
        return name.slice(name.lastIndexOf("/") + 1);
      } else {
        return "";
      }
    },
    /** 查询作业规范列表 */
    getList() {
      this.loading = true;
      listSpecification(
        this.addDateRange(this.queryParams, this.dateRange)
      ).then((response) => {
        this.specificationList = response.rows;
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
      if (this.$refs.fileUploadModule != null) {
        this.$refs.fileUploadModule.number = 0;
        this.$refs.fileUploadModule.uploadList = [];
        this.$refs.fileUploadModule.fileList = [];
      }

      this.uploadFileList = [];
      this.form = {
        jobId: null,
        userId: null,
        title: null,
        createTime: null,
        updateTime: null,
      };
      this.letters = [];
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
      this.ids = selection.map((item) => item.jobId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加作业规范";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const jobId = row.jobId || this.ids;
      getSpecification(jobId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改作业规范";
        let selectStr =
          response.data.manageFile[0].oldFileName.split(".")[0] + ";";
        for (let i = 0; i < selectStr.length; i++) {
          let letter = selectStr.charAt(i);
          if (letter == " ") {
            continue;
          }
          this.letters.push(letter);
        }
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
      this.uploadFileList = [];
      const uploadListComponent = this.$refs.fileUploadModule;
      const fileList = uploadListComponent.fileList;
      var pattern = /[\u4e00-\u9fa5]/; // 使用 Unicode 范围表示中文字符
      const status = pattern.test(this.form.standard);
      if (status) {
        this.$message.error("标准号不能包含中文～");
        return;
      }
      for (let i in fileList) {
        let obj = {};
        let fileName = fileList[i].name.split("/");
        obj.newFileName = fileName[fileName.length - 1];
        obj.oldFileName = fileList[i].oldName;
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
          if (this.form.jobId != null) {
            updateSpecification(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSpecification(this.form).then((response) => {
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
      const jobIds = row.jobId || this.ids;
      this.$modal
        .confirm('是否确认删除作业规范编号为"' + jobIds + '"的数据项？')
        .then(function () {
          return delSpecification(jobIds);
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
        "system/specification/export",
        {
          ...this.queryParams,
        },
        `specification_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
