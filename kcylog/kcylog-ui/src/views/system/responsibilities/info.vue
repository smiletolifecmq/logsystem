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
      <el-form-item label="项目名称" prop="infoXmmc">
        <el-input
          v-model="queryParams.infoXmmc"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合同编号" prop="infoHtbh">
        <el-input
          v-model="queryParams.infoHtbh"
          placeholder="请输入合同编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="甲方单位" prop="infoJfdw">
        <el-input
          v-model="queryParams.infoJfdw"
          placeholder="请输入甲方单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开票时间" prop="infoTime">
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
      <el-form-item label="市场专员" prop="infoZrr">
        <el-input
          v-model="queryParams.infoZrr"
          placeholder="请输入市场专员"
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:responsibilitiesInfo:export']"
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
      :data="infoList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="项目名称" align="center" prop="infoXmmc" />
      <el-table-column label="合同编号" align="center" prop="infoHtbh" />
      <el-table-column label="甲方单位" align="center" prop="infoJfdw" />
      <el-table-column label="签订合同额" align="center" prop="infoQdhtje" />
      <el-table-column
        label="开票时间"
        align="center"
        prop="infoTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.infoTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="责权ID" align="center" prop="zqId" />
      <el-table-column label="开票金额" align="center" prop="infoMoney" />
      <el-table-column label="市场专员" align="center" prop="infoZrr" />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改责权发生制开票明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="infoXmmc">
          <el-input v-model="form.infoXmmc" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="合同编号" prop="infoHtbh">
          <el-input v-model="form.infoHtbh" placeholder="请输入合同编号" />
        </el-form-item>
        <el-form-item label="甲方单位" prop="infoJfdw">
          <el-input v-model="form.infoJfdw" placeholder="请输入甲方单位" />
        </el-form-item>
        <el-form-item label="签订合同额" prop="infoQdhtje">
          <el-input v-model="form.infoQdhtje" placeholder="请输入签订合同额" />
        </el-form-item>
        <el-form-item label="开票时间" prop="infoTime">
          <el-date-picker
            clearable
            v-model="form.infoTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开票时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="责权ID" prop="zqId">
          <el-input v-model="form.zqId" placeholder="请输入责权ID" />
        </el-form-item>
        <el-form-item label="开票金额" prop="infoMoney">
          <el-input v-model="form.infoMoney" placeholder="请输入开票金额" />
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
  listInfo,
  getInfo,
  delInfo,
  addInfo,
  updateInfo,
} from "@/api/system/responsibilitiesInfo";

export default {
  name: "Info",
  data() {
    return {
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
      // 责权发生制开票明细表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        infoXmmc: null,
        infoHtbh: null,
        infoJfdw: null,
        infoQdhtje: null,
        infoTime: null,
        zqId: null,
        infoMoney: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询责权发生制开票明细列表 */
    getList() {
      this.loading = true;
      listInfo(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.infoList = response.rows;
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
        infoId: null,
        infoXmmc: null,
        infoHtbh: null,
        infoJfdw: null,
        infoQdhtje: null,
        infoTime: null,
        zqId: null,
        infoMoney: null,
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
      this.ids = selection.map((item) => item.infoId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加责权发生制开票明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const infoId = row.infoId || this.ids;
      getInfo(infoId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改责权发生制开票明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.infoId != null) {
            updateInfo(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then((response) => {
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
      const infoIds = row.infoId || this.ids;
      this.$modal
        .confirm(
          '是否确认删除责权发生制开票明细编号为"' + infoIds + '"的数据项？'
        )
        .then(function () {
          return delInfo(infoIds);
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
        "system/responsibilitiesInfo/export",
        {
          ...this.queryParams,
        },
        `责权发生制开票详情_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
