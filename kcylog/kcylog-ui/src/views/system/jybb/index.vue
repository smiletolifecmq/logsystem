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
      height="510"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="分院" align="center" prop="orgName" fixed />
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
      <el-table-column label="总项目数（年｜周）" align="center">
        <template slot-scope="scope">
          {{ scope.row.totalCount }} | {{ scope.row.weekCount }}
        </template>
      </el-table-column>

      <el-table-column align="center">
        <template #header>
          项目编号含<span style="color: red">售</span>项目数（年｜周）
        </template>
        <template slot-scope="scope">
          {{ scope.row.sellYearCount }} | {{ scope.row.sellWeekCount }}
        </template>
      </el-table-column>

      <el-table-column align="center">
        <template #header>
          项目编号含<span style="color: red">规</span>项目数（年｜周）
        </template>
        <template slot-scope="scope">
          {{ scope.row.planYearCount }} | {{ scope.row.planWeekCount }}
        </template>
      </el-table-column>

      <el-table-column label="项目编号含项目数" align="center">
        <template #header>
          项目编号含<span style="color: red">籍</span>项目数
        </template>
        <template slot-scope="scope">
          {{ scope.row.jiCount }}
        </template>
      </el-table-column>

      <el-table-column align="center">
        <template #header>
          项目编号含<span style="color: red">管</span>项目数（cctv | 其它）
        </template>
        <template slot-scope="scope">
          {{ scope.row.pipeCctvCount }} | {{ scope.row.pipeOtherCount }}
        </template>
      </el-table-column>

      <el-table-column align="center">
        <template #header>
          项目编号含<span style="color: red">政</span>项目数（年-土方或控制 ｜
          年-道路）
        </template>
        <template slot-scope="scope">
          {{ scope.row.govYearEarthControlCount }} |
          {{ scope.row.govYearRoadCount }}
        </template>
      </el-table-column>

      <el-table-column align="center">
        <template #header>
          项目编号含<span style="color: red">政</span>项目数（周-土方或控制 ｜
          周-道路）
        </template>
        <template slot-scope="scope">
          {{ scope.row.govWeekEarthControlCount }} |
          {{ scope.row.govWeekRoadCount }}
        </template>
      </el-table-column>
    </el-table>
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
