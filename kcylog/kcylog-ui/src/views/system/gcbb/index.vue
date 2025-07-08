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
      <el-form-item label="所属分院" prop="branchOrgName">
        <el-select
          v-model="queryParams.branchOrgName"
          filterable
          placeholder="请选择"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
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
          v-hasPermi="['system:gcbb:export']"
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
      :data="gcbbList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="登记时间" align="center" prop="cTime" />
      <el-table-column label="项目编号" align="center" prop="projectCode" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="项目类型" align="center" prop="projectTypeName" />
      <el-table-column label="委托单位" align="center" prop="customerName" />
      <el-table-column label="所属分院" align="center" prop="branchOrgName" />
      <el-table-column label="合同编号" align="center" prop="contractNo" />
      <el-table-column label="合同金额" align="center" prop="contractAmount" />
      <el-table-column
        label="结算金额"
        align="center"
        prop="contractFinalAmount"
      />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改百川工程报表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目编号" prop="projectCode">
          <el-input v-model="form.projectCode" placeholder="请输入项目编号" />
        </el-form-item>
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目类型" prop="projectTypeName">
          <el-input
            v-model="form.projectTypeName"
            placeholder="请输入项目类型"
          />
        </el-form-item>
        <el-form-item label="委托单位" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入委托单位" />
        </el-form-item>
        <el-form-item label="所属分院" prop="branchOrgName">
          <el-input v-model="form.branchOrgName" placeholder="请输入所属分院" />
        </el-form-item>
        <el-form-item label="合同编号" prop="contractNo">
          <el-input v-model="form.contractNo" placeholder="请输入合同编号" />
        </el-form-item>
        <el-form-item label="合同金额" prop="contractAmount">
          <el-input
            v-model="form.contractAmount"
            placeholder="请输入合同金额"
          />
        </el-form-item>
        <el-form-item label="结算金额" prop="contractFinalAmount">
          <el-input
            v-model="form.contractFinalAmount"
            placeholder="请输入结算金额"
          />
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
  listGcbb,
  getGcbb,
  delGcbb,
  addGcbb,
  updateGcbb,
} from "@/api/system/gcbb";

export default {
  name: "Gcbb",
  data() {
    return {
      options: [
        {
          value: "测绘工程院",
          label: "测绘工程院",
        },
        {
          value: "马尾分院",
          label: "马尾分院",
        },
        {
          value: "永泰分院",
          label: "永泰分院",
        },
        {
          value: "闽清分院",
          label: "闽清分院",
        },

        {
          value: "闽侯分院",
          label: "闽侯分院",
        },

        {
          value: "市政工程院",
          label: "市政工程院",
        },
        {
          value: "连江分院",
          label: "连江分院",
        },
        {
          value: "罗源分院",
          label: "罗源分院",
        },
        {
          value: "信息工程院",
          label: "信息工程院",
        },
        {
          value: "智慧城市工程院",
          label: "智慧城市工程院",
        },
        {
          value: "工程勘察院",
          label: "工程勘察院",
        },
        {
          value: "福清分公司",
          label: "福清分公司",
        },

        {
          value: "新区分公司",
          label: "新区分公司",
        },
        {
          value: "平潭分院",
          label: "平潭分院",
        },
        {
          value: "福建百宝图科技有限公司",
          label: "福建百宝图科技有限公司",
        },
        {
          value: "中禹（福建）数字科技有限公司",
          label: "中禹（福建）数字科技有限公司",
        },
        {
          value: "罗源县丝路港湾勘测设计有限公司",
          label: "罗源县丝路港湾勘测设计有限公司",
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
      // 百川工程报表表格数据
      gcbbList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        createTime: null,
        projectCode: null,
        projectName: null,
        projectTypeName: null,
        customerName: null,
        branchOrgName: null,
        contractNo: null,
        contractAmount: null,
        contractFinalAmount: null,
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
    /** 查询百川工程报表列表 */
    getList() {
      this.loading = true;
      listGcbb(this.queryParams).then((response) => {
        this.gcbbList = response.rows;
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
      this.form = {
        id: null,
        createTime: null,
        projectCode: null,
        projectName: null,
        projectTypeName: null,
        customerName: null,
        branchOrgName: null,
        contractNo: null,
        contractAmount: null,
        contractFinalAmount: null,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加百川工程报表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getGcbb(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改百川工程报表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateGcbb(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGcbb(this.form).then((response) => {
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
        .confirm('是否确认删除百川工程报表编号为"' + ids + '"的数据项？')
        .then(function () {
          return delGcbb(ids);
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
        "system/gcbb/export",
        {
          ...this.queryParams,
        },
        `工程报表_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
