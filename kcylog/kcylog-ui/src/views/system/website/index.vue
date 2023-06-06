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
      <el-form-item label="网站标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入网站标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="网站地址" prop="webUrl">
        <el-input
          v-model="queryParams.webUrl"
          placeholder="请输入网站地址"
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
          v-hasPermi="['system:website:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:website:edit']"
          >修改</el-button
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
          v-hasPermi="['system:website:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:website:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="websiteList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="webId" />
      <el-table-column label="网站标题" align="center" prop="title" />
      <el-table-column label="网站地址" align="center" prop="webUrl" />

      <el-table-column label="图片" align="center" prop="imgUrl">
        <template slot-scope="scope">
          <img
            :src="getFullImageUrl(scope.row.imgUrl)"
            alt="图片"
            style="width: 100px; height: 100px"
          />
        </template>
      </el-table-column>
      <el-table-column label="点击数" align="center" prop="num" />
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
            v-hasPermi="['system:website:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:website:remove']"
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

    <!-- 添加或修改网站整合对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="网站标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入网站标题" />
        </el-form-item>
        <el-form-item label="网站地址" prop="webUrl">
          <el-input v-model="form.webUrl" placeholder="请输入网站地址" />
        </el-form-item>

        <el-form-item label="图片" prop="imgUrl">
          <el-upload
            class="upload-demo"
            :action="uploadWebImg"
            :on-remove="removeWebImg"
            :on-success="handleWebUrlSuccess"
            :limit="1"
            ref="uploadImg"
            list-type="picture"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
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
  listWebsite,
  getWebsite,
  delWebsite,
  addWebsite,
  updateWebsite,
} from "@/api/system/website";

export default {
  name: "Website",
  data() {
    return {
      baseUrl: "",
      imgUrl: "",
      uploadWebImg: "",
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
      // 网站整合表格数据
      websiteList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        webUrl: null,
        imgUrl: null,
        num: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "网站标题不能为空", trigger: "blur" },
        ],
        webUrl: [
          { required: true, message: "网站地址不能为空", trigger: "blur" },
        ],
      },
    };
  },
  mounted() {
    this.uploadWebImg = `${process.env.VUE_APP_BASE_API}/system/website/weburl`;
    this.baseUrl = process.env.VUE_APP_WEB_IMG_URL;
  },
  created() {
    this.getList();
  },
  computed: {
    getFullImageUrl() {
      return (imgUrl) => {
        return this.baseUrl + imgUrl;
      };
    },
  },
  methods: {
    // 处理上传成功后的响应
    handleWebUrlSuccess(response) {
      this.imgUrl = response.imgUrl;
    },
    // 移除上传图片触发事件
    removeWebImg(file, fileList) {
      this.imgUrl = "";
    },
    /** 查询网站整合列表 */
    getList() {
      this.loading = true;
      listWebsite(this.queryParams).then((response) => {
        this.websiteList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.$refs.uploadImg.clearFiles();
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        webId: null,
        title: null,
        webUrl: null,
        imgUrl: null,
        num: null,
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
      this.ids = selection.map((item) => item.webId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.imgUrl = "";
      if (this.$refs.uploadImg != undefined) {
        this.$refs.uploadImg.clearFiles();
      }
      this.open = true;
      this.title = "添加网站整合";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.imgUrl = "";
      if (this.$refs.uploadImg != undefined) {
        this.$refs.uploadImg.clearFiles();
      }
      const webId = row.webId || this.ids;
      getWebsite(webId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改网站整合";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if (this.imgUrl == "") {
        this.$message({
          message: "请上传图片",
          type: "warning",
        });
      } else {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            if (this.form.webId != null) {
              this.form.imgUrl = this.imgUrl;
              updateWebsite(this.form).then((response) => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              this.form.imgUrl = this.imgUrl;
              addWebsite(this.form).then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
            this.$refs.uploadImg.clearFiles();
            this.imgUrl = "";
          }
        });
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const webIds = row.webId || this.ids;
      this.$modal
        .confirm('是否确认删除网站整合编号为"' + webIds + '"的数据项？')
        .then(function () {
          return delWebsite(webIds);
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
        "system/website/export",
        {
          ...this.queryParams,
        },
        `website_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
