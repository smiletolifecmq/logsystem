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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          >导出</el-button
        >
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="jybbListFq" ref="fixedTable">
      <el-table-column
        label="分院"
        align="center"
        prop="orgName"
        fixed
        width="150px"
      />
      <el-table-column
        label="合同/订单金额"
        align="center"
        prop="finalAmount"
        width="130px"
      />
      <el-table-column
        label="开票金额"
        align="center"
        prop="invoiceAmount"
        width="130px"
      />
      <el-table-column
        label="分配给本分院金额"
        align="center"
        prop="ownToOwnAmount"
        width="130px"
      />
      <el-table-column
        label="分配给其他分院金额"
        align="center"
        prop="ownToOtherAmount"
        width="140px"
      />
      <el-table-column
        label="其他分院分配的金额"
        align="center"
        prop="otherToOwnAmount"
        width="140px"
      />
      <el-table-column
        label="归属本分院金额"
        align="center"
        prop="ownAmount"
        width="130px"
      />
      <el-table-column
        label="到账金额"
        align="center"
        prop="paymentAmount"
        width="130px"
      />
      <el-table-column
        label="应收账款"
        align="center"
        prop="receivableAmount"
        width="130px"
      />
      <el-table-column label="总项目数（年｜月）" align="center" width="140px">
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(1, scope.row.orgName)"
            >{{ scope.row.totalCount }}</span
          >
          |
          <span
            class="hover-effect"
            @click="handleGcbbList(2, scope.row.orgName)"
            >{{ scope.row.weekCount }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="140px">
        <template #header> 售图项目（年｜月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(3, scope.row.orgName)"
            >{{ scope.row.sellYearCount }}</span
          >
          |
          <span
            class="hover-effect"
            @click="handleGcbbList(4, scope.row.orgName)"
            >{{ scope.row.sellWeekCount }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="140px">
        <template #header> 规划项目（年｜月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(5, scope.row.orgName)"
            >{{ scope.row.planYearCount }}</span
          >
          |
          <span
            class="hover-effect"
            @click="handleGcbbList(6, scope.row.orgName)"
            >{{ scope.row.planWeekCount }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="150px">
        <template #header> 土方或控制（年｜月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(10, scope.row.orgName)"
            >{{ scope.row.govYearEarthControlCount }}</span
          >
          |
          <span
            class="hover-effect"
            @click="handleGcbbList(12, scope.row.orgName)"
            >{{ scope.row.govWeekEarthControlCount }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="150px">
        <template #header> 道路测量（年｜月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(11, scope.row.orgName)"
            >{{ scope.row.govYearRoadCount }}</span
          >
          |
          <span
            class="hover-effect"
            @click="handleGcbbList(13, scope.row.orgName)"
            >{{ scope.row.govWeekRoadCount }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="140px">
        <template #header> 地籍勘界（年｜月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(7, scope.row.orgName)"
            >{{ scope.row.jiCount }}</span
          >
          ｜
          <span
            class="hover-effect"
            @click="handleGcbbList(14, scope.row.orgName)"
            >{{ scope.row.jiCountMonth }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="140px">
        <template #header> CCTV（年 ｜ 月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(8, scope.row.orgName)"
            >{{ scope.row.pipeCctvCount }}</span
          >
          |
          <span
            class="hover-effect"
            @click="handleGcbbList(15, scope.row.orgName)"
            >{{ scope.row.pipeCctvCountMonth }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="150px">
        <template #header> 管线项目（年 ｜ 月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(9, scope.row.orgName)"
            >{{ scope.row.pipeOtherCount }}</span
          >
          ｜
          <span
            class="hover-effect"
            @click="handleGcbbList(16, scope.row.orgName)"
            >{{ scope.row.pipeOtherCountMonth }}</span
          >
        </template>
      </el-table-column>
    </el-table>

    <el-table
      v-loading="loading"
      :data="jybbList"
      :show-header="false"
      height="520"
      ref="scrollTable"
    >
      <el-table-column
        label="分院"
        align="center"
        prop="orgName"
        fixed
        width="150px"
      />
      <el-table-column
        label="合同/订单金额"
        align="center"
        prop="finalAmount"
        width="130px"
      />
      <el-table-column
        label="开票金额"
        align="center"
        prop="invoiceAmount"
        width="130px"
      />
      <el-table-column
        label="分配给本分院金额"
        align="center"
        prop="ownToOwnAmount"
        width="130px"
      />
      <el-table-column
        label="分配给其他分院金额"
        align="center"
        prop="ownToOtherAmount"
        width="140px"
      />
      <el-table-column
        label="其他分院分配的金额"
        align="center"
        prop="otherToOwnAmount"
        width="140px"
      />
      <el-table-column
        label="归属本分院金额"
        align="center"
        prop="ownAmount"
        width="130px"
      />
      <el-table-column
        label="到账金额"
        align="center"
        prop="paymentAmount"
        width="130px"
      />
      <el-table-column
        label="应收账款"
        align="center"
        prop="receivableAmount"
        width="130px"
      />
      <el-table-column label="总项目数（年｜月）" align="center" width="140px">
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(1, scope.row.orgName)"
            >{{ scope.row.totalCount }}</span
          >
          |
          <span
            class="hover-effect"
            @click="handleGcbbList(2, scope.row.orgName)"
            >{{ scope.row.weekCount }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="140px">
        <template #header> 售图项目（年｜月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(3, scope.row.orgName)"
            >{{ scope.row.sellYearCount }}</span
          >
          |
          <span
            class="hover-effect"
            @click="handleGcbbList(4, scope.row.orgName)"
            >{{ scope.row.sellWeekCount }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="140px">
        <template #header> 规划项目（年｜月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(5, scope.row.orgName)"
            >{{ scope.row.planYearCount }}</span
          >
          |
          <span
            class="hover-effect"
            @click="handleGcbbList(6, scope.row.orgName)"
            >{{ scope.row.planWeekCount }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="150px">
        <template #header> 土方或控制（年｜月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(10, scope.row.orgName)"
            >{{ scope.row.govYearEarthControlCount }}</span
          >
          |
          <span
            class="hover-effect"
            @click="handleGcbbList(12, scope.row.orgName)"
            >{{ scope.row.govWeekEarthControlCount }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="150px">
        <template #header> 道路测量（年｜月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(11, scope.row.orgName)"
            >{{ scope.row.govYearRoadCount }}</span
          >
          |
          <span
            class="hover-effect"
            @click="handleGcbbList(13, scope.row.orgName)"
            >{{ scope.row.govWeekRoadCount }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="140px">
        <template #header> 地籍勘界（年｜月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(7, scope.row.orgName)"
            >{{ scope.row.jiCount }}</span
          >
          ｜
          <span
            class="hover-effect"
            @click="handleGcbbList(14, scope.row.orgName)"
            >{{ scope.row.jiCountMonth }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="140px">
        <template #header> CCTV（年 ｜ 月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(8, scope.row.orgName)"
            >{{ scope.row.pipeCctvCount }}</span
          >
          |
          <span
            class="hover-effect"
            @click="handleGcbbList(15, scope.row.orgName)"
            >{{ scope.row.pipeCctvCountMonth }}</span
          >
        </template>
      </el-table-column>

      <el-table-column align="center" width="150px">
        <template #header> 管线项目（年 ｜ 月） </template>
        <template slot-scope="scope">
          <span
            class="hover-effect"
            @click="handleGcbbList(9, scope.row.orgName)"
            >{{ scope.row.pipeOtherCount }}</span
          >
          ｜
          <span
            class="hover-effect"
            @click="handleGcbbList(16, scope.row.orgName)"
            >{{ scope.row.pipeOtherCountMonth }}</span
          >
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="项目列表"
      :visible.sync="conditionOpen"
      width="1400px"
      append-to-body
    >
      <el-table v-loading="loading" :data="gcbbList">
        <el-table-column label="登记时间" align="center" prop="cTime" />
        <el-table-column label="项目编号" align="center" prop="projectCode" />
        <el-table-column label="项目名称" align="center" prop="projectName" />
        <el-table-column
          label="项目类型"
          align="center"
          prop="projectTypeName"
        />
        <el-table-column label="委托单位" align="center" prop="customerName" />
        <el-table-column label="所属分院" align="center" prop="branchOrgName" />
        <el-table-column label="合同编号" align="center" prop="contractNo" />
        <el-table-column
          label="合同金额"
          align="center"
          prop="contractAmount"
        />
        <el-table-column
          label="结算金额"
          align="center"
          prop="contractFinalAmount"
        />
      </el-table>

      <pagination
        v-show="gcbbTotal > 0"
        :total="gcbbTotal"
        :page.sync="queryGcbbParams.pageNum"
        :limit.sync="queryGcbbParams.pageSize"
        @pagination="getGcbbList"
      />
    </el-dialog>
  </div>
</template>
<style>
.hover-effect:hover {
  cursor: pointer;
}
</style>

<script>
import {
  listJybb,
  getJybb,
  delJybb,
  addJybb,
  updateJybb,
} from "@/api/system/jybb";
import { getTypeGcbb } from "@/api/system/gcbb";

export default {
  name: "Jybb",
  data() {
    return {
      jybbListFq: [],
      gcbbTotal: 0,
      conditionOpen: false,
      gcbbList: [],
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
      queryGcbbParams: {
        pageNum: 1,
        pageSize: 10,
        dataType: null,
        branchOrgName: null,
      },
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
  mounted() {
    this.bindScrollSync();
  },
  methods: {
    bindScrollSync() {
      this.$nextTick(() => {
        const topWrapper = this.$refs.fixedTable?.bodyWrapper;
        const bottomWrapper = this.$refs.scrollTable?.bodyWrapper;
        if (topWrapper && bottomWrapper) {
          // 下滚动驱动上
          bottomWrapper.addEventListener("scroll", () => {
            topWrapper.scrollLeft = bottomWrapper.scrollLeft;
          });
          // 上滚动驱动下
          topWrapper.addEventListener("scroll", () => {
            bottomWrapper.scrollLeft = topWrapper.scrollLeft;
          });
        }
      });
    },
    getGcbbList() {
      this.loading = true;
      getTypeGcbb(this.queryGcbbParams).then((response) => {
        this.gcbbList = response.rows;
        this.gcbbTotal = response.total;
        this.loading = false;
      });
    },
    handleGcbbList(value, fy) {
      this.queryGcbbParams = {
        pageNum: 1,
        pageSize: 10,
        dataType: value,
        branchOrgName: fy,
      };
      getTypeGcbb(this.queryGcbbParams).then((response) => {
        this.gcbbList = response.rows;
        this.gcbbTotal = response.total;
        this.loading = false;
        this.conditionOpen = true;
      });
    },
    /** 查询百川分院经营金额列表 */
    getList() {
      this.loading = true;
      listJybb(this.queryParams).then((response) => {
        this.jybbListFq = [];
        this.jybbList = response.rows;
        this.jybbListFq.push(this.jybbList[0]);
        this.jybbList.shift();
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
        `经营报表_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
