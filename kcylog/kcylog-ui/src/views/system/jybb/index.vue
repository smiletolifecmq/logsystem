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
      <el-form-item label="所属分院" prop="orgName">
        <el-select
          v-model="queryParams.orgName"
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
    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:jybb:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row> -->

    <el-table
      v-loading="loading"
      :data="jybbList"
      show-summary
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="分院" align="center" prop="orgName" />
      <el-table-column
        label="合同/订单金额"
        align="center"
        prop="finalAmount"
      />
      <el-table-column label="开票金额" align="center" prop="invoiceAmount" />
      <el-table-column label="补助金额" align="center" prop="subsidyAmount" />
      <el-table-column
        label="分配给本分院金额"
        align="center"
        prop="ownToOwnAmount"
      />
      <el-table-column
        label="分配给其他分院金额"
        align="center"
        prop="ownToOtherAmount"
      />
      <el-table-column
        label="其他分院分配的金额"
        align="center"
        prop="otherToOwnAmount"
      />
      <el-table-column label="归属本分院金额" align="center" prop="ownAmount" />
      <el-table-column label="到账金额" align="center" prop="paymentAmount" />
      <el-table-column
        label="应收账款"
        align="center"
        prop="receivableAmount"
      />
      <el-table-column
        label="投标中标金额"
        align="center"
        prop="tenderAmount"
      />
    </el-table>

    <!-- 添加或修改百川分院经营金额对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分院" prop="orgName">
          <el-input v-model="form.orgName" placeholder="请输入分院" />
        </el-form-item>
        <el-form-item label="合同/订单金额" prop="finalAmount">
          <el-input
            v-model="form.finalAmount"
            placeholder="请输入合同/订单金额"
          />
        </el-form-item>
        <el-form-item label="开票金额" prop="invoiceAmount">
          <el-input v-model="form.invoiceAmount" placeholder="请输入开票金额" />
        </el-form-item>
        <el-form-item label="补助金额" prop="subsidyAmount">
          <el-input v-model="form.subsidyAmount" placeholder="请输入补助金额" />
        </el-form-item>
        <el-form-item label="分配给本分院金额" prop="ownToOwnAmount">
          <el-input
            v-model="form.ownToOwnAmount"
            placeholder="请输入分配给本分院金额"
          />
        </el-form-item>
        <el-form-item label="分配给其他分院金额" prop="ownToOtherAmount">
          <el-input
            v-model="form.ownToOtherAmount"
            placeholder="请输入分配给其他分院金额"
          />
        </el-form-item>
        <el-form-item label="其他分院分配的金额" prop="otherToOwnAmount">
          <el-input
            v-model="form.otherToOwnAmount"
            placeholder="请输入其他分院分配的金额"
          />
        </el-form-item>
        <el-form-item label="归属本分院金额" prop="ownAmount">
          <el-input
            v-model="form.ownAmount"
            placeholder="请输入归属本分院金额"
          />
        </el-form-item>
        <el-form-item label="到账金额" prop="paymentAmount">
          <el-input v-model="form.paymentAmount" placeholder="请输入到账金额" />
        </el-form-item>
        <el-form-item label="应收账款" prop="receivableAmount">
          <el-input
            v-model="form.receivableAmount"
            placeholder="请输入应收账款"
          />
        </el-form-item>
        <el-form-item label="投标中标金额" prop="tenderAmount">
          <el-input
            v-model="form.tenderAmount"
            placeholder="请输入投标中标金额"
          />
        </el-form-item>
        <el-form-item label="年份" prop="year">
          <el-input v-model="form.year" placeholder="请输入年份" />
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
  listJybb,
  getJybb,
  delJybb,
  addJybb,
  updateJybb,
} from "@/api/system/jybb";

export default {
  name: "Jybb",
  data() {
    return {
      options: [
        {
          value: "测绘工程院(含外设)",
          label: "测绘工程院(含外设)",
        },
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
          value: "市政工程院(含外设)",
          label: "市政工程院(含外设)",
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
          value: "新区分公司(含外设)",
          label: "新区分公司(含外设)",
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
      // 百川分院经营金额表格数据
      jybbList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orgName: null,
        finalAmount: null,
        invoiceAmount: null,
        subsidyAmount: null,
        ownToOwnAmount: null,
        ownToOtherAmount: null,
        otherToOwnAmount: null,
        ownAmount: null,
        paymentAmount: null,
        receivableAmount: null,
        tenderAmount: null,
        year: null,
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
    /** 查询百川分院经营金额列表 */
    getList() {
      this.loading = true;
      listJybb(this.queryParams).then((response) => {
        this.jybbList = response.rows;
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
        orgName: null,
        finalAmount: null,
        invoiceAmount: null,
        subsidyAmount: null,
        ownToOwnAmount: null,
        ownToOtherAmount: null,
        otherToOwnAmount: null,
        ownAmount: null,
        paymentAmount: null,
        receivableAmount: null,
        tenderAmount: null,
        year: null,
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
      this.title = "添加百川分院经营金额";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJybb(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改百川分院经营金额";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJybb(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addJybb(this.form).then((response) => {
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
        .confirm('是否确认删除百川分院经营金额编号为"' + ids + '"的数据项？')
        .then(function () {
          return delJybb(ids);
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
        "system/jybb/export",
        {
          ...this.queryParams,
        },
        `jybb_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
