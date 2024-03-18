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
      <el-form-item label="名称" prop="settlementName">
        <el-input
          v-model="queryParams.settlementName"
          placeholder="请输入结算单名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="结算状态" prop="isSettlement">
        <el-select
          v-model="isSettlementVaule"
          placeholder="请选择"
          @change="handleQuery"
        >
          <el-option
            v-for="item in isSettlementArr"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建人" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入创建人"
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

    <el-table
      v-loading="loading"
      :data="settlementList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        label="结算单名称"
        align="center"
        prop="settlementName"
      />
      <el-table-column label="创建人" align="center" prop="userName" />
      <el-table-column
        label="是否已确认结算"
        align="center"
        prop="isSettlement"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.isSettlement === 0" style="color: red">否</span>
          <span v-else-if="scope.row.isSettlement === 1" style="color: blue"
            >是</span
          >
          <span v-else>其他状态</span>
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
            v-if="showButton(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-if="showButton(scope.row)"
            >回退上一流程</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-order"
            @click="semploymentReview(scope.row)"
            >审核单</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleEmployeeExport(scope.row)"
            >签领表导出</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleOutsourcingExport(scope.row)"
            >外包申请表导出</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-shop"
            @click="handleDeptExport(scope.row)"
            >按部门导出</el-button
          >

          <el-button
            size="mini"
            type="text"
            icon="el-icon-success"
            v-if="showButton(scope.row)"
            @click="confirmSettlementInfo(scope.row)"
            >确认结算办结</el-button
          >

          <el-button
            size="mini"
            type="text"
            icon="el-icon-folder-add"
            @click="actualFiling(scope.row)"
            >实际表归档</el-button
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

    <!-- 添加或修改结算单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="settlementName">
          <el-input
            v-model="form.settlementName"
            placeholder="请输入结算单名称"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="归档表信息"
      :visible.sync="opengd"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form
        ref="guidanform"
        :model="guidanform"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="文件" required>
          <FileUpload
            ref="fileUploadModule"
            :fileSize="200"
            :fileType="fileType"
            :limit="10"
          ></FileUpload>
        </el-form-item>
        <el-form-item label="整理人" prop="name">
          <el-input
            v-model="guidanform.name"
            type="text"
            placeholder="请输入整理人"
          />
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input
            v-model="guidanform.bz"
            type="textarea"
            placeholder="请输入备注"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitGuidanform">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addSettlementFile,
  listSettlement,
  getSettlement,
  delSettlement,
  addSettlement,
  updateSettlement,
  confirmSettlement,
  getSettlementFile,
} from "@/api/system/settlement";
import FileUpload from "@/components/FileUpload";
import userInfo from "@/store/modules/user";

export default {
  name: "Settlement",
  props: {
    fileType: {
      type: Array,
      default: () => ["docx", "doc", "ppt", "pdf"],
    },
  },
  data() {
    return {
      opengd: false,
      isSettlementArr: [
        {
          value: 0,
          label: "否",
        },
        {
          value: 1,
          label: "是",
        },
      ],
      // 遮罩层
      loading: true,
      isSettlementVaule: "",
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
      // 结算单表格数据
      settlementList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        settlementName: null,
        userName: null,
      },
      queryParamsExport: {},
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      guidanform: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    submitGuidanform() {
      this.uploadFileList = [];
      const uploadListComponent = this.$refs.fileUploadModule;
      const fileList = uploadListComponent.fileList;
      for (let i in fileList) {
        let obj = {};
        let fileName = fileList[i].name.split("/");
        obj.newFileName = fileName[fileName.length - 1];
        obj.oldFileName = "";
        if (fileList[i].oldFileName == null || fileList[i].oldFileName == "") {
          obj.oldFileName = fileList[i].oldName;
        } else {
          obj.oldFileName = fileList[i].oldFileName;
        }
        obj.fileName = fileList[i].name;
        obj.url = fileList[i].url;
        this.uploadFileList.push(obj);
      }
      if (this.uploadFileList.length == 0) {
        this.$message.error("请上传文件～");
        return;
      }
      this.guidanform.uploadFileList = this.uploadFileList;
      this.$refs["guidanform"].validate((valid) => {
        console.log(this.guidanform);
        addSettlementFile(this.guidanform).then((response) => {
          this.$modal.msgSuccess("保存成功");
          this.opengd = true;
          this.actualFiling(this.guidanform);
        });
      });
    },
    actualFiling(row) {
      this.opengd = true;
      this.guidanform.settlementId = row.settlementId;
      getSettlementFile(row.settlementId).then((response) => {
        this.uploadFileList = [];
        if (response.rows.length == 0) {
          this.$refs.fileUploadModule.number = 0;
          this.$refs.fileUploadModule.uploadList = [];
          this.$refs.fileUploadModule.fileList = [];
          this.guidanform.name = "";
          this.guidanform.bz = "";
        } else {
          this.guidanform = response.rows[0];
          this.$nextTick(() => {
            if (this.$refs.fileUploadModule) {
              this.$refs.fileUploadModule.number = 0;
              this.$refs.fileUploadModule.uploadList = [];
              this.$refs.fileUploadModule.fileList = [];
              for (let i = 0; i < response.rows.length; i++) {
                let fileTemp = {};
                fileTemp.name = response.rows[i].fileName;
                fileTemp.url = response.rows[i].url;
                fileTemp.fileName = response.rows[i].fileName;
                fileTemp.newFileName = response.rows[i].newFileName;
                fileTemp.oldFileName = response.rows[i].oldFileName;
                this.$refs.fileUploadModule.fileList.push(fileTemp);
              }
            }
          });
        }
      });
    },
    confirmSettlementInfo(row) {
      const settlementId = row.settlementId;
      this.$modal
        .confirm("是否确认，一旦确认将不可修改和回退上一流程～")
        .then(function () {
          return confirmSettlement(settlementId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("提交成功～");
        })
        .catch(() => {});
    },
    handleDeptExport(settlement) {
      this.queryParamsExport.settlementId = settlement.settlementId;
      this.queryParamsExport.status = 4;
      this.download(
        "system/review/export",
        { ...this.queryParamsExport },
        settlement.settlementName + `_部门_${new Date().getTime()}.xlsx`
      );
    },
    handleEmployeeExport(settlement) {
      this.queryParamsExport.settlementId = settlement.settlementId;
      this.download(
        "system/reviewEmployee/export",
        { ...this.queryParamsExport },
        settlement.settlementName + `_劳务费签领表_${new Date().getTime()}.xlsx`
      );
    },
    handleOutsourcingExport(settlement) {
      this.queryParamsExport.settlementId = settlement.settlementId;
      this.download(
        "system/reviewEmployee/outsourcingExport",
        { ...this.queryParamsExport },
        settlement.settlementName +
          `_劳务外包申请表_${new Date().getTime()}.xlsx`
      );
    },
    semploymentReview(row) {
      const settlementId = row.settlementId;
      this.$router.push("/system/settlement-review/list/" + settlementId);
    },
    showButton(row) {
      return row.userId == userInfo.state.userId && row.isSettlement === 0;
    },
    /** 查询结算单列表 */
    getList() {
      this.loading = true;
      listSettlement(this.queryParams).then((response) => {
        this.settlementList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.opengd = false;
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
      this.guidanform = {};
      this.form = {
        settlementId: null,
        settlementName: null,
        userName: null,
        createTime: null,
        updateTime: null,
      };
      this.resetForm("form");
      this.resetForm("guidanform");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      if (this.isSettlementVaule !== "") {
        this.queryParams.isSettlement = this.isSettlementVaule;
      }
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.isSettlementVaule = "";
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.settlementId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加结算单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const settlementId = row.settlementId || this.ids;
      getSettlement(settlementId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改结算单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.settlementId != null) {
            updateSettlement(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSettlement(this.form).then((response) => {
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
      const settlementIds = row.settlementId || this.ids;
      this.$modal
        .confirm(
          '是否确认删除结算单名称为"' +
            row.settlementName +
            '"的数据项？注意:一旦删除，该结算单包含的审核单将变成待结算'
        )
        .then(function () {
          return delSettlement(settlementIds);
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
        "system/settlement/export",
        {
          ...this.queryParams,
        },
        `settlement_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
