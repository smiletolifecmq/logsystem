<template>
  <div class="excel-processor">
    <el-card class="box-card">
      <template slot="header">
        <div class="card-header">
          <span>界址点坐标成果表处理工具</span>
        </div>
      </template>

      <el-form :model="form" label-width="120px">
        <!-- 基础标题输入 -->
        <el-form-item label="地块名称">
          <el-input
            v-model="form.baseTitle"
            placeholder="请输入地块名称，留空则使用文件名"
          />
          <div class="el-upload__tip">
            如不填写，则使用上传文件的文件名作为地块名称
          </div>
        </el-form-item>

        <!-- 数据文件上传 -->
        <el-form-item label="上传数据文件">
          <el-upload
            class="upload-demo"
            action="#"
            :auto-upload="false"
            :show-file-list="true"
            :on-change="handleDataFilesChange"
            :on-remove="handleDataFileRemove"
            multiple
            accept=".xlsx,.xls"
            ref="upload"
          >
            <el-button type="success" icon="Upload">选择数据文件</el-button>
            <div slot="tip" class="el-upload__tip">
              可选择多个Excel数据文件，支持.xls或.xlsx格式
            </div>
          </el-upload>
        </el-form-item>

        <!-- 处理按钮 -->
        <el-form-item>
          <el-button
            type="primary"
            @click="processFiles"
            :disabled="!canProcess"
            :loading="processing"
          >
            {{ processing ? "处理中..." : "开始处理" }}
          </el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 处理结果信息 -->
      <div v-if="resultInfo" class="result-info">
        <el-alert
          :title="resultInfo.message"
          :type="resultInfo.type"
          show-icon
          :closable="false"
        />
      </div>
    </el-card>

    <!-- 文件列表 -->
    <el-card class="file-list-card" v-if="dataFiles.length > 0">
      <template slot="header">
        <div class="card-header">
          <span>已选择文件</span>
        </div>
      </template>

      <div v-for="(file, index) in dataFiles" :key="index" class="file-item">
        <el-tag type="success" effect="dark">数据文件{{ index + 1 }}:</el-tag>
        <span class="file-name">{{ file.name }}</span>
        <el-button
          type="danger"
          icon="Delete"
          size="small"
          circle
          @click="removeDataFile(index)"
        />
      </div>
    </el-card>

    <!-- 预览信息 -->
    <el-card class="preview-card" v-if="dataFiles.length > 0">
      <template slot="header">
        <div class="card-header">
          <span>文件输出预览</span>
        </div>
      </template>

      <div class="preview-info">
        <p>
          <strong>输出格式:</strong>
          {{ dataFiles.length === 1 ? "Excel文件" : "ZIP压缩包" }}
        </p>
        <p><strong>基础标题:</strong> {{ form.baseTitle || "使用文件名" }}</p>
        <p><strong>生成文件名:</strong></p>
        <ul>
          <li v-for="(file, index) in dataFiles" :key="index">
            {{
              (form.baseTitle || getFileNameWithoutExtension(file.name)) +
              "界址点坐标成果表" +
              (form.baseTitle ? "（" + (index + 1) + "）" : "") +
              (dataFiles.length === 1 ? ".xlsx" : "")
            }}
          </li>
        </ul>
        <p v-if="dataFiles.length > 1">
          <strong>压缩包名:</strong>
          {{ (form.baseTitle || "界址点数据") + "_处理结果.zip" }}
        </p>
      </div>
    </el-card>
  </div>
</template>

<script>
import { processJzd } from "@/api/system/jzd";
import { saveAs } from "file-saver";

export default {
  name: "ExcelProcessor",
  data() {
    return {
      form: {
        baseTitle: "",
      },
      dataFiles: [],
      processing: false,
      resultInfo: null,
    };
  },
  computed: {
    canProcess() {
      return this.dataFiles.length > 0;
    },
  },
  methods: {
    handleDataFilesChange(file, fileList) {
      const uniqueFiles = fileList.filter(
        (item, index, self) =>
          index === self.findIndex((f) => f.name === item.name)
      );
      this.dataFiles = uniqueFiles.map((item) => item.raw);
    },
    handleDataFileRemove(file, fileList) {
      this.dataFiles = fileList.map((item) => item.raw);
    },
    removeDataFile(index) {
      this.dataFiles.splice(index, 1);
    },
    getFileNameWithoutExtension(fileName) {
      if (!fileName) return "处理文件";
      const dotIndex = fileName.lastIndexOf(".");
      return dotIndex > 0 ? fileName.substring(0, dotIndex) : fileName;
    },
    async processFiles() {
      if (!this.canProcess) {
        this.$message({
          showClose: true,
          message: "请先选择数据文件",
          type: "error",
        });
        return;
      }

      this.processing = true;
      this.resultInfo = null;

      try {
        const formData = new FormData();
        this.dataFiles.forEach((file) => {
          formData.append("files", file);
        });
        if (this.form.baseTitle) {
          formData.append("baseTitle", this.form.baseTitle);
        }

        const data = await processJzd(formData);
        const blob = new Blob([data]);
        let fileName;
        if (this.dataFiles.length === 1) {
          const baseName =
            this.form.baseTitle ||
            this.getFileNameWithoutExtension(this.dataFiles[0].name);
          fileName = baseName + "_界址点成果表.xlsx";
        } else {
          const baseName = this.form.baseTitle || "界址点成果表";
          fileName = baseName + "_处理结果.zip";
        }
        saveAs(blob, fileName);

        this.resultInfo = {
          message: `成功处理 ${this.dataFiles.length} 个文件，正在下载${
            this.dataFiles.length === 1 ? "Excel文件" : "ZIP包"
          }...`,
          type: "success",
        };
        this.$message({
          showClose: true,
          message: "处理完成，文件已开始下载",
          type: "success",
        });
      } catch (error) {
        console.error("处理文件时出错:", error);
        this.resultInfo = {
          message: "处理文件时出错，请检查文件格式或联系管理员",
          type: "error",
        };
        this.$message({
          showClose: true,
          message: "处理失败",
          type: "error",
        });
      } finally {
        this.processing = false;
      }
    },
    resetForm() {
      this.processing = false;
      this.form.baseTitle = "";
      this.$refs.upload.clearFiles();
      this.dataFiles = [];
      this.resultInfo = null;
    },
  },
};
</script>

<style scoped>
.excel-processor {
  max-width: 900px;
  margin: 20px auto;
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.upload-demo {
  width: 100%;
}

.result-info {
  margin-top: 20px;
}

.file-list-card,
.preview-card {
  margin-top: 20px;
}

.file-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  padding: 10px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.file-item .el-tag {
  margin-right: 10px;
}

.file-name {
  flex: 1;
  margin-right: 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.preview-info p {
  margin: 5px 0;
}

.preview-info ul {
  margin: 5px 0 5px 20px;
}

.preview-info li {
  margin: 3px 0;
}
</style>
