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
      <el-form-item label="拜访时间" prop="bfsj">
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
          v-hasPermi="['system:khdcmyd:add']"
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
          v-hasPermi="['system:khdcmyd:export']"
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
      :data="khdcmydList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="拜访时间" align="center" prop="bfsj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.bfsj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="拜访形式" align="center" prop="bfxs" />
      <el-table-column label="市场专员" align="center" prop="sczy" />
      <el-table-column label="随行人员" align="center" prop="sxry" />
      <el-table-column label="拜访客户名称" align="center" prop="khmc" />
      <el-table-column label="拜访对象姓名" align="center" prop="dxxm" />
      <el-table-column label="拜访具体内容" align="center" prop="nr" />
      <el-table-column label="需要改进工作" align="center" prop="gjgz" />
      <el-table-column label="工期满意度" align="center" prop="gqmyd" />
      <el-table-column label="产品质量满意度" align="center" prop="zlmyd" />
      <el-table-column label="服务满意度" align="center" prop="fwmyd" />
      <el-table-column label="项目负责人服务态度" align="center" prop="fzrtd" />
      <el-table-column label="接洽人员服务态度" align="center" prop="ryfwd" />
      <el-table-column label="综合评价" align="center" prop="zhpj" />
      <el-table-column label="满意率" align="center" prop="myl" />
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:khdcmyd:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:khdcmyd:remove']"
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

    <!-- 添加或修改客户调查满意度对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="拜访时间" prop="bfsj">
          <el-date-picker
            clearable
            v-model="form.bfsj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择拜访时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="拜访形式" prop="bfxs">
          <template>
            <el-radio v-model="form.bfxs" label="现场拜访">现场拜访</el-radio>
            <el-radio v-model="form.bfxs" label="电话回访">电话回访</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="市场专员" prop="sczy">
          <el-input v-model="form.sczy" placeholder="请输入市场专员" />
        </el-form-item>
        <el-form-item label="随行人员" prop="sxry">
          <el-input v-model="form.sxry" placeholder="请输入随行人员" />
        </el-form-item>
        <el-form-item label="拜访客户名称" prop="khmc">
          <el-input v-model="form.khmc" placeholder="请输入拜访客户名称" />
        </el-form-item>
        <el-form-item label="拜访对象姓名" prop="dxxm">
          <el-input v-model="form.dxxm" placeholder="请输入拜访对象姓名" />
        </el-form-item>
        <el-form-item label="拜访具体内容" prop="nr">
          <el-input
            v-model="form.nr"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="需要改进工作" prop="gjgz">
          <el-input
            v-model="form.gjgz"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="工期满意度" prop="gqmyd">
          <template>
            <el-radio v-model="form.gqmyd" label="满意">满意</el-radio>
            <el-radio v-model="form.gqmyd" label="较满意">较满意</el-radio>
            <el-radio v-model="form.gqmyd" label="不满意">不满意</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="产品质量满意度" prop="zlmyd">
          <template>
            <el-radio v-model="form.zlmyd" label="满意">满意</el-radio>
            <el-radio v-model="form.zlmyd" label="较满意">较满意</el-radio>
            <el-radio v-model="form.zlmyd" label="不满意">不满意</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="服务满意度" prop="fwmyd">
          <template>
            <el-radio v-model="form.fwmyd" label="满意">满意</el-radio>
            <el-radio v-model="form.fwmyd" label="较满意">较满意</el-radio>
            <el-radio v-model="form.fwmyd" label="不满意">不满意</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="项目负责人服务态度" prop="fzrtd">
          <template>
            <el-radio v-model="form.fzrtd" label="满意">满意</el-radio>
            <el-radio v-model="form.fzrtd" label="较满意">较满意</el-radio>
            <el-radio v-model="form.fzrtd" label="不满意">不满意</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="接洽人员服务态度" prop="ryfwd">
          <template>
            <el-radio v-model="form.ryfwd" label="满意">满意</el-radio>
            <el-radio v-model="form.ryfwd" label="较满意">较满意</el-radio>
            <el-radio v-model="form.ryfwd" label="不满意">不满意</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="综合评价" prop="zhpj">
          <template>
            <el-radio v-model="form.zhpj" label="满意">满意</el-radio>
            <el-radio v-model="form.zhpj" label="较满意">较满意</el-radio>
            <el-radio v-model="form.zhpj" label="不满意">不满意</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="满意率" prop="myl">
          <el-input v-model="form.myl" placeholder="请输入满意率" />
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
  listKhdcmyd,
  getKhdcmyd,
  delKhdcmyd,
  addKhdcmyd,
  updateKhdcmyd,
} from "@/api/system/khdcmyd";

export default {
  name: "Khdcmyd",
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
      // 客户调查满意度表格数据
      khdcmydList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bfsj: null,
        bfxs: null,
        sczy: null,
        sxry: null,
        khmc: null,
        dxxm: null,
        nr: null,
        gjgz: null,
        gqmyd: null,
        zlmyd: null,
        fwmyd: null,
        fzrtd: null,
        ryfwd: null,
        zhpj: null,
        myl: null,
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
    /** 查询客户调查满意度列表 */
    getList() {
      this.loading = true;
      listKhdcmyd(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.khdcmydList = response.rows;
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
        bfsj: null,
        bfxs: null,
        sczy: null,
        sxry: null,
        khmc: null,
        dxxm: null,
        nr: null,
        gjgz: null,
        gqmyd: null,
        zlmyd: null,
        fwmyd: null,
        fzrtd: null,
        ryfwd: null,
        zhpj: null,
        myl: null,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加客户调查满意度";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getKhdcmyd(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改客户调查满意度";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateKhdcmyd(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addKhdcmyd(this.form).then((response) => {
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
        .confirm('是否确认删除客户调查满意度编号为"' + ids + '"的数据项？')
        .then(function () {
          return delKhdcmyd(ids);
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
        "system/khdcmyd/export",
        {
          ...this.queryParams,
        },
        `客户拜访记录_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
