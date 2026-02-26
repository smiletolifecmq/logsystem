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
      <el-form-item label="年份" prop="nf">
        <el-date-picker
          v-model="queryParams.nf"
          type="year"
          value-format="yyyy"
          placeholder="选择年"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="月份" prop="yf">
        <el-select v-model="queryParams.yf" placeholder="请选择">
          <el-option
            v-for="item in yfs"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="部门" prop="bm">
        <el-select v-model="queryParams.bm" placeholder="请选择">
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
          v-hasPermi="['system:profit:export']"
          >导出当前年份</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="profitList"
      @selection-change="handleSelectionChange"
      size="mini"
    >
      <el-table-column label="年份" align="center" prop="nf" />
      <el-table-column label="月份" align="center" prop="yf" />
      <el-table-column label="部门" align="center" prop="bm" />
      <el-table-column label="完成生产经营收入" align="center" prop="jysr" />
      <el-table-column label="核减上月预结" align="center" prop="hjsyyj" />
      <el-table-column label="结算上月预留" align="center" prop="jssyyl" />
      <el-table-column label="预留" align="center" prop="yl" />
      <el-table-column label="预结" align="center" prop="yj" />
      <el-table-column label="上报生产经营收入" align="center" prop="sbjysr" />
      <el-table-column label="核定内部生产产值" align="center" prop="hdnbcz" />
      <el-table-column label="项目分包实际支出" align="center" prop="fbje" />
      <el-table-column
        label="完成项目产生的分包金额"
        align="center"
        prop="wcxmje"
      />
      <el-table-column label="常规雇工" align="center" prop="cggg" />
      <el-table-column label="劳务派遣" align="center" prop="lwpq" />
      <el-table-column label="博达支出" align="center" prop="bdzc" />
      <el-table-column label="研发人员申报产值" align="center" prop="yfcz" />
      <el-table-column label="是否已办结" align="center" prop="bj">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.bj === 0" type="info">否</el-tag>
          <el-tag v-else-if="scope.row.bj === 1">是</el-tag>
          <el-tag v-else type="warning">未知</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:profit:edit']"
            v-if="showButton(scope.row)"
            >编辑</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-circle-check"
            @click="handleBj(scope.row)"
            v-hasPermi="['system:profit:bj']"
            v-if="showBj(scope.row)"
            >办结</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleInfo(scope.row)"
            v-hasPermi="['system:profit:query']"
            >详情</el-button
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

    <!-- 添加或修改福清分公司净利润申报表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="190px">
        <el-form-item
          label="核减上月预结"
          prop="hjsyyj"
          v-hasPermi="['system:profit:bz']"
        >
          <el-input v-model="form.hjsyyj" placeholder="请输入核减上月预结" />
        </el-form-item>
        <el-form-item label="预留" prop="yl" v-hasPermi="['system:profit:xy']">
          <el-input v-model="form.yl" placeholder="请输入预留" />
        </el-form-item>
        <el-form-item label="预结" prop="yj" v-hasPermi="['system:profit:ch']">
          <el-input v-model="form.yj" placeholder="请输入预结" />
        </el-form-item>
        <!-- <el-form-item
          label="核定内部生产产值"
          prop="hdnbcz"
          v-hasPermi="['system:profit:ch']"
        >
          <el-input
            v-model="form.hdnbcz"
            placeholder="请输入核定内部生产产值"
          />
        </el-form-item> -->
        <el-form-item
          label="项目分包实际支出"
          prop="fbje"
          v-hasPermi="['system:profit:ch']"
        >
          <el-input v-model="form.fbje" placeholder="请输入项目分包实际支出" />
        </el-form-item>
        <el-form-item
          label="完成项目产生的分包金额"
          prop="wcxmje"
          v-hasPermi="['system:profit:ch']"
        >
          <el-input
            v-model="form.wcxmje"
            placeholder="请输入完成项目产生的分包金额"
          />
        </el-form-item>
        <el-form-item
          label="常规雇工"
          prop="cggg"
          v-hasPermi="['system:profit:ch']"
        >
          <el-input v-model="form.cggg" placeholder="请输入常规雇工" />
        </el-form-item>
        <el-form-item
          label="劳务派遣"
          prop="lwpq"
          v-hasPermi="['system:profit:xy']"
        >
          <el-input v-model="form.lwpq" placeholder="请输入劳务派遣" />
        </el-form-item>
        <el-form-item
          label="博达支出"
          prop="bdzc"
          v-hasPermi="['system:profit:xy']"
        >
          <el-input v-model="form.bdzc" placeholder="请输入博达支出" />
        </el-form-item>
        <el-form-item
          label="研发人员申报产值"
          prop="yfcz"
          v-hasPermi="['system:profit:xy']"
        >
          <el-input v-model="form.yfcz" placeholder="请输入研发人员申报产值" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :title="titlexq"
      :visible.sync="openxq"
      width="800px"
      append-to-body
    >
      <el-descriptions :column="2">
        <el-descriptions-item label="年份">{{
          this.form.nf
        }}</el-descriptions-item>
        <el-descriptions-item label="月份">{{
          this.form.yf
        }}</el-descriptions-item>
        <el-descriptions-item label="部门">{{
          this.form.bm
        }}</el-descriptions-item>
        <el-descriptions-item label="完成生产经营收入">{{
          this.form.jysr
        }}</el-descriptions-item>

        <el-descriptions-item label="核减上月预结">{{
          this.form.hjsyyj
        }}</el-descriptions-item>
        <el-descriptions-item label="结算上月预留">{{
          this.form.jssyyl
        }}</el-descriptions-item>
        <el-descriptions-item label="预留">{{
          this.form.yl
        }}</el-descriptions-item>
        <el-descriptions-item label="预结">{{
          this.form.yj
        }}</el-descriptions-item>
        <el-descriptions-item label="上报生产经营收入">{{
          this.form.sbjysr
        }}</el-descriptions-item>
        <el-descriptions-item label="核定内部生产产值">{{
          this.form.hdnbcz
        }}</el-descriptions-item>
        <el-descriptions-item label="项目分包实际支出（不纳入绩效核算）">{{
          this.form.fbje
        }}</el-descriptions-item>
        <el-descriptions-item label="完成项目产生的分包金额（含第三方雇工）">{{
          this.form.wcxmje
        }}</el-descriptions-item>
        <el-descriptions-item label="常规雇工">{{
          this.form.cggg
        }}</el-descriptions-item>
        <el-descriptions-item label="劳务派遣">{{
          this.form.lwpq
        }}</el-descriptions-item>
        <el-descriptions-item label="博达支出">{{
          this.form.bdzc
        }}</el-descriptions-item>
        <el-descriptions-item label="研发人员申报产值（不纳入净利润核算）">{{
          this.form.yfcz
        }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
import { listProfit, getProfit, updateProfit } from "@/api/system/profit";
import userInfo from "@/store/modules/user";
import ExcelJS from "exceljs";

export default {
  name: "Profit",
  data() {
    return {
      titlexq: "",
      openxq: false,
      yfs: [
        {
          value: "1",
          label: "1月",
        },
        {
          value: "2",
          label: "2月",
        },
        {
          value: "3",
          label: "3月",
        },
        {
          value: "4",
          label: "4月",
        },
        {
          value: "5",
          label: "5月",
        },
        {
          value: "6",
          label: "6月",
        },
        {
          value: "7",
          label: "7月",
        },
        {
          value: "8",
          label: "8月",
        },
        {
          value: "9",
          label: "9月",
        },
        {
          value: "10",
          label: "10月",
        },
        {
          value: "11",
          label: "11月",
        },
        {
          value: "12",
          label: "12月",
        },
      ],
      options: [
        {
          value: "工程测绘部",
          label: "工程测绘部",
        },
        {
          value: "管线工程部",
          label: "管线工程部",
        },
        {
          value: "不动产测绘部",
          label: "不动产测绘部",
        },
        {
          value: "地理信息部",
          label: "地理信息部",
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
      // 福清分公司净利润申报表表格数据
      profitList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        nf: null,
        yf: null,
        bm: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        // nf: [{ required: true, message: "年份不能为空", trigger: "blur" }],
        // yf: [{ required: true, message: "月份不能为空", trigger: "blur" }],
        // bm: [{ required: true, message: "部门不能为空", trigger: "blur" }],
        // jysr: [
        //   {
        //     required: true,
        //     message: "完成生产经营收入不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // hjsyyj: [
        //   { required: true, message: "核减上月预结不能为空", trigger: "blur" },
        // ],
        // jssyyl: [
        //   { required: true, message: "结算上月预留不能为空", trigger: "blur" },
        // ],
        // yl: [{ required: true, message: "预留不能为空", trigger: "blur" }],
        // yj: [{ required: true, message: "预结不能为空", trigger: "blur" }],
        // sbjysr: [
        //   {
        //     required: true,
        //     message: "上报生产经营收入不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // hdnbcz: [
        //   {
        //     required: true,
        //     message: "核定内部生产产值不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // fbje: [
        //   {
        //     required: true,
        //     message: "项目分包实际支出不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // wcxmje: [
        //   {
        //     required: true,
        //     message: "完成项目产生的分包金额不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // cggg: [
        //   { required: true, message: "常规雇工不能为空", trigger: "blur" },
        // ],
        // lwpq: [
        //   { required: true, message: "劳务派遣不能为空", trigger: "blur" },
        // ],
        // bdzc: [
        //   { required: true, message: "博达支出不能为空", trigger: "blur" },
        // ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    showButton(value) {
      const now = new Date();
      // 年份字符串
      const yearStr = now.getFullYear().toString();
      // 月份字符串（不补 0，自然数）
      const monthStr = (now.getMonth() + 1).toString();

      // return yearStr === value.nf && monthStr === value.yf;

      return (
        (yearStr === value.nf &&
          monthStr === value.yf &&
          value.bm === userInfo.state.deptName &&
          value.bj != 1) ||
        (yearStr === value.nf &&
          monthStr === value.yf &&
          (userInfo.state.userId == 1 ||
            userInfo.state.userId == 11 ||
            userInfo.state.userId == 19 ||
            userInfo.state.userId == 100) &&
          value.bj != 1)
      );
    },
    showBj(value) {
      return value.bj === 0;
    },
    handleBj(value) {
      this.$confirm("是否办结?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          updateProfit({ id: value.id, bj: 1 }).then((response) => {
            this.$modal.msgSuccess("办结成功");
            this.getList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消办结",
          });
        });
    },
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateProfit(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    /** 查询福清分公司净利润申报表列表 */
    getList() {
      this.loading = true;
      listProfit(this.queryParams).then((response) => {
        this.profitList = response.rows;
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
        nf: null,
        yf: null,
        bm: null,
        jysr: null,
        hjsyyj: null,
        jssyyl: null,
        yl: null,
        yj: null,
        sbjysr: null,
        hdnbcz: null,
        fbje: null,
        wcxmje: null,
        cggg: null,
        lwpq: null,
        bdzc: null,
        yfcz: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      console.log(this.queryParams);
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

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getProfit(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title =
          "修改福清分公司净利润申报表-" +
          row.bm +
          "-" +
          row.nf +
          "年" +
          row.yf +
          "月";
      });
    },

    handleInfo(row) {
      this.reset();
      const id = row.id || this.ids;
      getProfit(id).then((response) => {
        this.form = response.data;
        this.openxq = true;
        this.titlexq =
          "福清分公司净利润申报表-" +
          row.bm +
          "-" +
          row.nf +
          "年" +
          row.yf +
          "月";
      });
    },

    /** 导出按钮操作 */
    handleExport() {
      this.queryParams.nf = new Date().getFullYear();

      listProfit({ nf: this.queryParams.nf, pageNum: 1, pageSize: 9999 }).then(
        (responseData) => {
          fetch("/sbcz.xlsx")
            .then((response) => {
              if (!response.ok) throw new Error("Network response was not ok");
              return response.arrayBuffer();
            })
            .then((data) => {
              const workbook = new ExcelJS.Workbook();
              return workbook.xlsx.load(data);
            })
            .then((workbook) => {
              const sheet1 = workbook.getWorksheet("工程测绘部");
              const sheet2 = workbook.getWorksheet("管线工程部");
              const sheet3 = workbook.getWorksheet("不动产测绘部");
              const sheet4 = workbook.getWorksheet("地理信息部");

              // 工程测绘部数据
              var gcchb = {
                jysr1: 0,
                jysr2: 0,
                jysr3: 0,
                jysr4: 0,
                jysr5: 0,
                jysr6: 0,
                jysr7: 0,
                jysr8: 0,
                jysr9: 0,
                jysr10: 0,
                jysr11: 0,
                jysr12: 0,
                jysrhj: 0,
                hjsyyj1: 0,
                hjsyyj2: 0,
                hjsyyj3: 0,
                hjsyyj4: 0,
                hjsyyj5: 0,
                hjsyyj6: 0,
                hjsyyj7: 0,
                hjsyyj8: 0,
                hjsyyj9: 0,
                hjsyyj10: 0,
                hjsyyj11: 0,
                hjsyyj12: 0,
                hjsyyjhj: 0,
                jssyyl1: 0,
                jssyyl2: 0,
                jssyyl3: 0,
                jssyyl4: 0,
                jssyyl5: 0,
                jssyyl6: 0,
                jssyyl7: 0,
                jssyyl8: 0,
                jssyyl9: 0,
                jssyyl10: 0,
                jssyyl11: 0,
                jssyyl12: 0,
                jssyylhj: 0,
                yl1: 0,
                yl2: 0,
                yl3: 0,
                yl4: 0,
                yl5: 0,
                yl6: 0,
                yl7: 0,
                yl8: 0,
                yl9: 0,
                yl10: 0,
                yl11: 0,
                yl12: 0,
                ylhj: 0,
                yj1: 0,
                yj2: 0,
                yj3: 0,
                yj4: 0,
                yj5: 0,
                yj6: 0,
                yj7: 0,
                yj8: 0,
                yj9: 0,
                yj10: 0,
                yj11: 0,
                yj12: 0,
                yjhj: 0,
                sbjysr1: 0,
                sbjysr2: 0,
                sbjysr3: 0,
                sbjysr4: 0,
                sbjysr5: 0,
                sbjysr6: 0,
                sbjysr7: 0,
                sbjysr8: 0,
                sbjysr9: 0,
                sbjysr10: 0,
                sbjysr11: 0,
                sbjysr12: 0,
                sbjysrhj: 0,
                hdnbcz1: 0,
                hdnbcz2: 0,
                hdnbcz3: 0,
                hdnbcz4: 0,
                hdnbcz5: 0,
                hdnbcz6: 0,
                hdnbcz7: 0,
                hdnbcz8: 0,
                hdnbcz9: 0,
                hdnbcz10: 0,
                hdnbcz11: 0,
                hdnbcz12: 0,
                hdnbczhj: 0,
                srhj1: 0,
                srhj2: 0,
                srhj3: 0,
                srhj4: 0,
                srhj5: 0,
                srhj6: 0,
                srhj7: 0,
                srhj8: 0,
                srhj9: 0,
                srhj10: 0,
                srhj11: 0,
                srhj12: 0,
                srhjhj: 0,
                fbje1: 0,
                fbje2: 0,
                fbje3: 0,
                fbje4: 0,
                fbje5: 0,
                fbje6: 0,
                fbje7: 0,
                fbje8: 0,
                fbje9: 0,
                fbje10: 0,
                fbje11: 0,
                fbje12: 0,
                fbjehj: 0,
                wcxmje1: 0,
                wcxmje2: 0,
                wcxmje3: 0,
                wcxmje4: 0,
                wcxmje5: 0,
                wcxmje6: 0,
                wcxmje7: 0,
                wcxmje8: 0,
                wcxmje9: 0,
                wcxmje10: 0,
                wcxmje11: 0,
                wcxmje12: 0,
                wcxmjehj: 0,
                cggg1: 0,
                cggg2: 0,
                cggg3: 0,
                cggg4: 0,
                cggg5: 0,
                cggg6: 0,
                cggg7: 0,
                cggg8: 0,
                cggg9: 0,
                cggg10: 0,
                cggg11: 0,
                cggg12: 0,
                cggghj: 0,
                lwpq1: 0,
                lwpq2: 0,
                lwpq3: 0,
                lwpq4: 0,
                lwpq5: 0,
                lwpq6: 0,
                lwpq7: 0,
                lwpq8: 0,
                lwpq9: 0,
                lwpq10: 0,
                lwpq11: 0,
                lwpq12: 0,
                lwpqhj: 0,
                bdzc1: 0,
                bdzc2: 0,
                bdzc3: 0,
                bdzc4: 0,
                bdzc5: 0,
                bdzc6: 0,
                bdzc7: 0,
                bdzc8: 0,
                bdzc9: 0,
                bdzc10: 0,
                bdzc11: 0,
                bdzc12: 0,
                bdzchj: 0,
                zchj1: 0,
                zchj2: 0,
                zchj3: 0,
                zchj4: 0,
                zchj5: 0,
                zchj6: 0,
                zchj7: 0,
                zchj8: 0,
                zchj9: 0,
                zchj10: 0,
                zchj11: 0,
                zchj12: 0,
                zchjhj: 0,
                jll1: 0,
                jll2: 0,
                jll3: 0,
                jll4: 0,
                jll5: 0,
                jll6: 0,
                jll7: 0,
                jll8: 0,
                jll9: 0,
                jll10: 0,
                jll11: 0,
                jll12: 0,
                jllhj: 0,
              };

              // 管线工程部数据
              var gxgcb = {
                jysr1: 0,
                jysr2: 0,
                jysr3: 0,
                jysr4: 0,
                jysr5: 0,
                jysr6: 0,
                jysr7: 0,
                jysr8: 0,
                jysr9: 0,
                jysr10: 0,
                jysr11: 0,
                jysr12: 0,
                jysrhj: 0,
                hjsyyj1: 0,
                hjsyyj2: 0,
                hjsyyj3: 0,
                hjsyyj4: 0,
                hjsyyj5: 0,
                hjsyyj6: 0,
                hjsyyj7: 0,
                hjsyyj8: 0,
                hjsyyj9: 0,
                hjsyyj10: 0,
                hjsyyj11: 0,
                hjsyyj12: 0,
                hjsyyjhj: 0,
                jssyyl1: 0,
                jssyyl2: 0,
                jssyyl3: 0,
                jssyyl4: 0,
                jssyyl5: 0,
                jssyyl6: 0,
                jssyyl7: 0,
                jssyyl8: 0,
                jssyyl9: 0,
                jssyyl10: 0,
                jssyyl11: 0,
                jssyyl12: 0,
                jssyylhj: 0,
                yl1: 0,
                yl2: 0,
                yl3: 0,
                yl4: 0,
                yl5: 0,
                yl6: 0,
                yl7: 0,
                yl8: 0,
                yl9: 0,
                yl10: 0,
                yl11: 0,
                yl12: 0,
                ylhj: 0,
                yj1: 0,
                yj2: 0,
                yj3: 0,
                yj4: 0,
                yj5: 0,
                yj6: 0,
                yj7: 0,
                yj8: 0,
                yj9: 0,
                yj10: 0,
                yj11: 0,
                yj12: 0,
                yjhj: 0,
                sbjysr1: 0,
                sbjysr2: 0,
                sbjysr3: 0,
                sbjysr4: 0,
                sbjysr5: 0,
                sbjysr6: 0,
                sbjysr7: 0,
                sbjysr8: 0,
                sbjysr9: 0,
                sbjysr10: 0,
                sbjysr11: 0,
                sbjysr12: 0,
                sbjysrhj: 0,
                hdnbcz1: 0,
                hdnbcz2: 0,
                hdnbcz3: 0,
                hdnbcz4: 0,
                hdnbcz5: 0,
                hdnbcz6: 0,
                hdnbcz7: 0,
                hdnbcz8: 0,
                hdnbcz9: 0,
                hdnbcz10: 0,
                hdnbcz11: 0,
                hdnbcz12: 0,
                hdnbczhj: 0,
                srhj1: 0,
                srhj2: 0,
                srhj3: 0,
                srhj4: 0,
                srhj5: 0,
                srhj6: 0,
                srhj7: 0,
                srhj8: 0,
                srhj9: 0,
                srhj10: 0,
                srhj11: 0,
                srhj12: 0,
                srhjhj: 0,
                fbje1: 0,
                fbje2: 0,
                fbje3: 0,
                fbje4: 0,
                fbje5: 0,
                fbje6: 0,
                fbje7: 0,
                fbje8: 0,
                fbje9: 0,
                fbje10: 0,
                fbje11: 0,
                fbje12: 0,
                fbjehj: 0,
                wcxmje1: 0,
                wcxmje2: 0,
                wcxmje3: 0,
                wcxmje4: 0,
                wcxmje5: 0,
                wcxmje6: 0,
                wcxmje7: 0,
                wcxmje8: 0,
                wcxmje9: 0,
                wcxmje10: 0,
                wcxmje11: 0,
                wcxmje12: 0,
                wcxmjehj: 0,
                cggg1: 0,
                cggg2: 0,
                cggg3: 0,
                cggg4: 0,
                cggg5: 0,
                cggg6: 0,
                cggg7: 0,
                cggg8: 0,
                cggg9: 0,
                cggg10: 0,
                cggg11: 0,
                cggg12: 0,
                cggghj: 0,
                lwpq1: 0,
                lwpq2: 0,
                lwpq3: 0,
                lwpq4: 0,
                lwpq5: 0,
                lwpq6: 0,
                lwpq7: 0,
                lwpq8: 0,
                lwpq9: 0,
                lwpq10: 0,
                lwpq11: 0,
                lwpq12: 0,
                lwpqhj: 0,
                bdzc1: 0,
                bdzc2: 0,
                bdzc3: 0,
                bdzc4: 0,
                bdzc5: 0,
                bdzc6: 0,
                bdzc7: 0,
                bdzc8: 0,
                bdzc9: 0,
                bdzc10: 0,
                bdzc11: 0,
                bdzc12: 0,
                bdzchj: 0,
                zchj1: 0,
                zchj2: 0,
                zchj3: 0,
                zchj4: 0,
                zchj5: 0,
                zchj6: 0,
                zchj7: 0,
                zchj8: 0,
                zchj9: 0,
                zchj10: 0,
                zchj11: 0,
                zchj12: 0,
                zchjhj: 0,
                jll1: 0,
                jll2: 0,
                jll3: 0,
                jll4: 0,
                jll5: 0,
                jll6: 0,
                jll7: 0,
                jll8: 0,
                jll9: 0,
                jll10: 0,
                jll11: 0,
                jll12: 0,
                jllhj: 0,
                jy: 0,
              };

              // 不动产测绘部数据
              var bdxchb = {
                jysr1: 0,
                jysr2: 0,
                jysr3: 0,
                jysr4: 0,
                jysr5: 0,
                jysr6: 0,
                jysr7: 0,
                jysr8: 0,
                jysr9: 0,
                jysr10: 0,
                jysr11: 0,
                jysr12: 0,
                jysrhj: 0,
                hjsyyj1: 0,
                hjsyyj2: 0,
                hjsyyj3: 0,
                hjsyyj4: 0,
                hjsyyj5: 0,
                hjsyyj6: 0,
                hjsyyj7: 0,
                hjsyyj8: 0,
                hjsyyj9: 0,
                hjsyyj10: 0,
                hjsyyj11: 0,
                hjsyyj12: 0,
                hjsyyjhj: 0,
                jssyyl1: 0,
                jssyyl2: 0,
                jssyyl3: 0,
                jssyyl4: 0,
                jssyyl5: 0,
                jssyyl6: 0,
                jssyyl7: 0,
                jssyyl8: 0,
                jssyyl9: 0,
                jssyyl10: 0,
                jssyyl11: 0,
                jssyyl12: 0,
                jssyylhj: 0,
                yl1: 0,
                yl2: 0,
                yl3: 0,
                yl4: 0,
                yl5: 0,
                yl6: 0,
                yl7: 0,
                yl8: 0,
                yl9: 0,
                yl10: 0,
                yl11: 0,
                yl12: 0,
                ylhj: 0,
                yj1: 0,
                yj2: 0,
                yj3: 0,
                yj4: 0,
                yj5: 0,
                yj6: 0,
                yj7: 0,
                yj8: 0,
                yj9: 0,
                yj10: 0,
                yj11: 0,
                yj12: 0,
                yjhj: 0,
                sbjysr1: 0,
                sbjysr2: 0,
                sbjysr3: 0,
                sbjysr4: 0,
                sbjysr5: 0,
                sbjysr6: 0,
                sbjysr7: 0,
                sbjysr8: 0,
                sbjysr9: 0,
                sbjysr10: 0,
                sbjysr11: 0,
                sbjysr12: 0,
                sbjysrhj: 0,
                hdnbcz1: 0,
                hdnbcz2: 0,
                hdnbcz3: 0,
                hdnbcz4: 0,
                hdnbcz5: 0,
                hdnbcz6: 0,
                hdnbcz7: 0,
                hdnbcz8: 0,
                hdnbcz9: 0,
                hdnbcz10: 0,
                hdnbcz11: 0,
                hdnbcz12: 0,
                hdnbczhj: 0,
                srhj1: 0,
                srhj2: 0,
                srhj3: 0,
                srhj4: 0,
                srhj5: 0,
                srhj6: 0,
                srhj7: 0,
                srhj8: 0,
                srhj9: 0,
                srhj10: 0,
                srhj11: 0,
                srhj12: 0,
                srhjhj: 0,
                fbje1: 0,
                fbje2: 0,
                fbje3: 0,
                fbje4: 0,
                fbje5: 0,
                fbje6: 0,
                fbje7: 0,
                fbje8: 0,
                fbje9: 0,
                fbje10: 0,
                fbje11: 0,
                fbje12: 0,
                fbjehj: 0,
                wcxmje1: 0,
                wcxmje2: 0,
                wcxmje3: 0,
                wcxmje4: 0,
                wcxmje5: 0,
                wcxmje6: 0,
                wcxmje7: 0,
                wcxmje8: 0,
                wcxmje9: 0,
                wcxmje10: 0,
                wcxmje11: 0,
                wcxmje12: 0,
                wcxmjehj: 0,
                cggg1: 0,
                cggg2: 0,
                cggg3: 0,
                cggg4: 0,
                cggg5: 0,
                cggg6: 0,
                cggg7: 0,
                cggg8: 0,
                cggg9: 0,
                cggg10: 0,
                cggg11: 0,
                cggg12: 0,
                cggghj: 0,
                lwpq1: 0,
                lwpq2: 0,
                lwpq3: 0,
                lwpq4: 0,
                lwpq5: 0,
                lwpq6: 0,
                lwpq7: 0,
                lwpq8: 0,
                lwpq9: 0,
                lwpq10: 0,
                lwpq11: 0,
                lwpq12: 0,
                lwpqhj: 0,
                bdzc1: 0,
                bdzc2: 0,
                bdzc3: 0,
                bdzc4: 0,
                bdzc5: 0,
                bdzc6: 0,
                bdzc7: 0,
                bdzc8: 0,
                bdzc9: 0,
                bdzc10: 0,
                bdzc11: 0,
                bdzc12: 0,
                bdzchj: 0,
                zchj1: 0,
                zchj2: 0,
                zchj3: 0,
                zchj4: 0,
                zchj5: 0,
                zchj6: 0,
                zchj7: 0,
                zchj8: 0,
                zchj9: 0,
                zchj10: 0,
                zchj11: 0,
                zchj12: 0,
                zchjhj: 0,
                jll1: 0,
                jll2: 0,
                jll3: 0,
                jll4: 0,
                jll5: 0,
                jll6: 0,
                jll7: 0,
                jll8: 0,
                jll9: 0,
                jll10: 0,
                jll11: 0,
                jll12: 0,
                jllhj: 0,
              };

              // 地理信息部数据
              var dlxx = {
                jysr1: 0,
                jysr2: 0,
                jysr3: 0,
                jysr4: 0,
                jysr5: 0,
                jysr6: 0,
                jysr7: 0,
                jysr8: 0,
                jysr9: 0,
                jysr10: 0,
                jysr11: 0,
                jysr12: 0,
                jysrhj: 0,
                hjsyyj1: 0,
                hjsyyj2: 0,
                hjsyyj3: 0,
                hjsyyj4: 0,
                hjsyyj5: 0,
                hjsyyj6: 0,
                hjsyyj7: 0,
                hjsyyj8: 0,
                hjsyyj9: 0,
                hjsyyj10: 0,
                hjsyyj11: 0,
                hjsyyj12: 0,
                hjsyyjhj: 0,
                jssyyl1: 0,
                jssyyl2: 0,
                jssyyl3: 0,
                jssyyl4: 0,
                jssyyl5: 0,
                jssyyl6: 0,
                jssyyl7: 0,
                jssyyl8: 0,
                jssyyl9: 0,
                jssyyl10: 0,
                jssyyl11: 0,
                jssyyl12: 0,
                jssyylhj: 0,
                yl1: 0,
                yl2: 0,
                yl3: 0,
                yl4: 0,
                yl5: 0,
                yl6: 0,
                yl7: 0,
                yl8: 0,
                yl9: 0,
                yl10: 0,
                yl11: 0,
                yl12: 0,
                ylhj: 0,
                yj1: 0,
                yj2: 0,
                yj3: 0,
                yj4: 0,
                yj5: 0,
                yj6: 0,
                yj7: 0,
                yj8: 0,
                yj9: 0,
                yj10: 0,
                yj11: 0,
                yj12: 0,
                yjhj: 0,
                sbjysr1: 0,
                sbjysr2: 0,
                sbjysr3: 0,
                sbjysr4: 0,
                sbjysr5: 0,
                sbjysr6: 0,
                sbjysr7: 0,
                sbjysr8: 0,
                sbjysr9: 0,
                sbjysr10: 0,
                sbjysr11: 0,
                sbjysr12: 0,
                sbjysrhj: 0,
                hdnbcz1: 0,
                hdnbcz2: 0,
                hdnbcz3: 0,
                hdnbcz4: 0,
                hdnbcz5: 0,
                hdnbcz6: 0,
                hdnbcz7: 0,
                hdnbcz8: 0,
                hdnbcz9: 0,
                hdnbcz10: 0,
                hdnbcz11: 0,
                hdnbcz12: 0,
                hdnbczhj: 0,
                srhj1: 0,
                srhj2: 0,
                srhj3: 0,
                srhj4: 0,
                srhj5: 0,
                srhj6: 0,
                srhj7: 0,
                srhj8: 0,
                srhj9: 0,
                srhj10: 0,
                srhj11: 0,
                srhj12: 0,
                srhjhj: 0,
                fbje1: 0,
                fbje2: 0,
                fbje3: 0,
                fbje4: 0,
                fbje5: 0,
                fbje6: 0,
                fbje7: 0,
                fbje8: 0,
                fbje9: 0,
                fbje10: 0,
                fbje11: 0,
                fbje12: 0,
                fbjehj: 0,
                wcxmje1: 0,
                wcxmje2: 0,
                wcxmje3: 0,
                wcxmje4: 0,
                wcxmje5: 0,
                wcxmje6: 0,
                wcxmje7: 0,
                wcxmje8: 0,
                wcxmje9: 0,
                wcxmje10: 0,
                wcxmje11: 0,
                wcxmje12: 0,
                wcxmjehj: 0,
                cggg1: 0,
                cggg2: 0,
                cggg3: 0,
                cggg4: 0,
                cggg5: 0,
                cggg6: 0,
                cggg7: 0,
                cggg8: 0,
                cggg9: 0,
                cggg10: 0,
                cggg11: 0,
                cggg12: 0,
                cggghj: 0,
                lwpq1: 0,
                lwpq2: 0,
                lwpq3: 0,
                lwpq4: 0,
                lwpq5: 0,
                lwpq6: 0,
                lwpq7: 0,
                lwpq8: 0,
                lwpq9: 0,
                lwpq10: 0,
                lwpq11: 0,
                lwpq12: 0,
                lwpqhj: 0,
                bdzc1: 0,
                bdzc2: 0,
                bdzc3: 0,
                bdzc4: 0,
                bdzc5: 0,
                bdzc6: 0,
                bdzc7: 0,
                bdzc8: 0,
                bdzc9: 0,
                bdzc10: 0,
                bdzc11: 0,
                bdzc12: 0,
                bdzchj: 0,
                zchj1: 0,
                zchj2: 0,
                zchj3: 0,
                zchj4: 0,
                zchj5: 0,
                zchj6: 0,
                zchj7: 0,
                zchj8: 0,
                zchj9: 0,
                zchj10: 0,
                zchj11: 0,
                zchj12: 0,
                zchjhj: 0,
                jll1: 0,
                jll2: 0,
                jll3: 0,
                jll4: 0,
                jll5: 0,
                jll6: 0,
                jll7: 0,
                jll8: 0,
                jll9: 0,
                jll10: 0,
                jll11: 0,
                jll12: 0,
                jllhj: 0,
                jy: 0,
                yf1: 0,
                yf2: 0,
                yf3: 0,
                yf4: 0,
                yf5: 0,
                yf6: 0,
                yf7: 0,
                yf8: 0,
                yf9: 0,
                yf10: 0,
                yf11: 0,
                yf12: 0,
                yfhj: 0,
              };
              for (var i = 0; i < responseData.rows.length; i++) {
                switch (responseData.rows[i].bm) {
                  case "工程测绘部":
                    const jysr = `jysr${responseData.rows[i].yf}`;
                    const hjsyyj = `hjsyyj${responseData.rows[i].yf}`;
                    const jssyyl = `jssyyl${responseData.rows[i].yf}`;
                    const yl = `yl${responseData.rows[i].yf}`;
                    const yj = `yj${responseData.rows[i].yf}`;
                    const sbjysr = `sbjysr${responseData.rows[i].yf}`;
                    const hdnbcz = `hdnbcz${responseData.rows[i].yf}`;
                    const srhj = `srhj${responseData.rows[i].yf}`;
                    const fbje = `fbje${responseData.rows[i].yf}`;
                    const wcxmje = `wcxmje${responseData.rows[i].yf}`;
                    const cggg = `cggg${responseData.rows[i].yf}`;
                    const lwpq = `lwpq${responseData.rows[i].yf}`;
                    const bdzc = `bdzc${responseData.rows[i].yf}`;
                    const zchj = `zchj${responseData.rows[i].yf}`;
                    const jll = `jll${responseData.rows[i].yf}`;

                    gcchb[jysr] = responseData.rows[i].jysr;
                    gcchb[hjsyyj] = responseData.rows[i].hjsyyj;
                    gcchb[jssyyl] = responseData.rows[i].jssyyl;
                    gcchb[yl] = responseData.rows[i].yl;
                    gcchb[yj] = responseData.rows[i].yj;
                    gcchb[sbjysr] =
                      gcchb[jysr] -
                      gcchb[hjsyyj] +
                      gcchb[jssyyl] -
                      gcchb[yl] +
                      gcchb[yj];

                    gcchb[hdnbcz] = responseData.rows[i].hdnbcz;
                    gcchb[srhj] = gcchb[sbjysr] + gcchb[hdnbcz];
                    gcchb[fbje] = responseData.rows[i].fbje;
                    gcchb[wcxmje] = responseData.rows[i].wcxmje;
                    gcchb[cggg] = responseData.rows[i].cggg;
                    gcchb[lwpq] = responseData.rows[i].lwpq;
                    gcchb[bdzc] = responseData.rows[i].bdzc;
                    gcchb[zchj] =
                      gcchb[wcxmje] + gcchb[cggg] + gcchb[lwpq] + gcchb[bdzc];
                    gcchb[jll] = gcchb[srhj] - gcchb[zchj];

                    gcchb.jysrhj = gcchb.jysrhj + responseData.rows[i].jysr;
                    gcchb.hjsyyjhj =
                      gcchb.hjsyyjhj + responseData.rows[i].hjsyyj;
                    gcchb.jssyylhj =
                      gcchb.jssyylhj + responseData.rows[i].jssyyl;
                    gcchb.ylhj = gcchb.ylhj + responseData.rows[i].yl;
                    gcchb.yjhj = gcchb.yjhj + responseData.rows[i].yj;
                    gcchb.sbjysrhj = gcchb.sbjysrhj + gcchb[sbjysr];
                    gcchb.hdnbczhj =
                      gcchb.hdnbczhj + responseData.rows[i].hdnbcz;
                    gcchb.srhjhj = gcchb.srhjhj + gcchb[srhj];
                    gcchb.fbjehj = gcchb.fbjehj + responseData.rows[i].fbje;
                    gcchb.wcxmjehj =
                      gcchb.wcxmjehj + responseData.rows[i].wcxmje;
                    gcchb.cggghj = gcchb.cggghj + responseData.rows[i].cggg;
                    gcchb.lwpqhj = gcchb.lwpqhj + responseData.rows[i].lwpq;
                    gcchb.bdzchj = gcchb.bdzchj + responseData.rows[i].bdzc;
                    gcchb.zchjhj = gcchb.zchjhj + gcchb[zchj];
                    gcchb.jllhj = gcchb.jllhj + gcchb[jll];
                    break;

                  case "管线工程部":
                    const jysrgx = `jysr${responseData.rows[i].yf}`;
                    const hjsyyjgx = `hjsyyj${responseData.rows[i].yf}`;
                    const jssyylgx = `jssyyl${responseData.rows[i].yf}`;
                    const ylgx = `yl${responseData.rows[i].yf}`;
                    const yjgx = `yj${responseData.rows[i].yf}`;
                    const sbjysrgx = `sbjysr${responseData.rows[i].yf}`;
                    const hdnbczgx = `hdnbcz${responseData.rows[i].yf}`;
                    const srhjgx = `srhj${responseData.rows[i].yf}`;
                    const fbjegx = `fbje${responseData.rows[i].yf}`;
                    const wcxmjegx = `wcxmje${responseData.rows[i].yf}`;
                    const cggggx = `cggg${responseData.rows[i].yf}`;
                    const lwpqgx = `lwpq${responseData.rows[i].yf}`;
                    const bdzcgx = `bdzc${responseData.rows[i].yf}`;
                    const zchjgx = `zchj${responseData.rows[i].yf}`;
                    const jllgx = `jll${responseData.rows[i].yf}`;

                    gxgcb[jysrgx] = responseData.rows[i].jysr;
                    gxgcb[hjsyyjgx] = responseData.rows[i].hjsyyj;
                    gxgcb[jssyylgx] = responseData.rows[i].jssyyl;
                    gxgcb[ylgx] = responseData.rows[i].yl;
                    gxgcb[yj] = responseData.rows[i].yj;
                    gxgcb[sbjysrgx] =
                      gxgcb[jysrgx] -
                      gxgcb[hjsyyjgx] +
                      gxgcb[jssyylgx] -
                      gxgcb[ylgx] +
                      gxgcb[yjgx];

                    gxgcb[hdnbczgx] = responseData.rows[i].hdnbcz;
                    gxgcb[srhjgx] = gxgcb[sbjysrgx] + gxgcb[hdnbczgx];
                    gxgcb[fbjegx] = responseData.rows[i].fbje;
                    gxgcb[wcxmjegx] = responseData.rows[i].wcxmje;
                    gxgcb[cggggx] = responseData.rows[i].cggg;
                    gxgcb[lwpqgx] = responseData.rows[i].lwpq;
                    gxgcb[bdzcgx] = responseData.rows[i].bdzc;
                    gxgcb[zchjgx] =
                      gxgcb[wcxmjegx] +
                      gxgcb[cggggx] +
                      gxgcb[lwpqgx] +
                      gxgcb[bdzcgx];
                    gxgcb[jllgx] = gxgcb[srhjgx] - gxgcb[zchjgx];

                    gxgcb.jysrhj = gxgcb.jysrhj + responseData.rows[i].jysr;
                    gxgcb.hjsyyjhj =
                      gxgcb.hjsyyjhj + responseData.rows[i].hjsyyj;
                    gxgcb.jssyylhj =
                      gxgcb.jssyylhj + responseData.rows[i].jssyyl;
                    gxgcb.ylhj = gxgcb.ylhj + responseData.rows[i].yl;
                    gxgcb.yjhj = gxgcb.yjhj + responseData.rows[i].yj;
                    gxgcb.sbjysrhj = gxgcb.sbjysrhj + gxgcb[sbjysrgx];
                    gxgcb.hdnbczhj =
                      gxgcb.hdnbczhj + responseData.rows[i].hdnbcz;
                    gxgcb.srhjhj = gxgcb.srhjhj + gxgcb[srhjgx];
                    gxgcb.fbjehj = gxgcb.fbjehj + responseData.rows[i].fbje;
                    gxgcb.wcxmjehj =
                      gxgcb.wcxmjehj + responseData.rows[i].wcxmje;
                    gxgcb.cggghj = gxgcb.cggghj + responseData.rows[i].cggg;
                    gxgcb.lwpqhj = gxgcb.lwpqhj + responseData.rows[i].lwpq;
                    gxgcb.bdzchj = gxgcb.bdzchj + responseData.rows[i].bdzc;
                    gxgcb.zchjhj = gxgcb.zchjhj + gxgcb[zchjgx];
                    gxgcb.jllhj = gxgcb.jllhj + gxgcb[jllgx];
                    break;
                  case "不动产测绘部":
                    const jysrbd = `jysr${responseData.rows[i].yf}`;
                    const hjsyyjbd = `hjsyyj${responseData.rows[i].yf}`;
                    const jssyylbd = `jssyyl${responseData.rows[i].yf}`;
                    const ylbd = `yl${responseData.rows[i].yf}`;
                    const yjbd = `yj${responseData.rows[i].yf}`;
                    const sbjysrbd = `sbjysr${responseData.rows[i].yf}`;
                    const hdnbczbd = `hdnbcz${responseData.rows[i].yf}`;
                    const srhjbd = `srhj${responseData.rows[i].yf}`;
                    const fbjebd = `fbje${responseData.rows[i].yf}`;
                    const wcxmjebd = `wcxmje${responseData.rows[i].yf}`;
                    const cgggbd = `cggg${responseData.rows[i].yf}`;
                    const lwpqbd = `lwpq${responseData.rows[i].yf}`;
                    const bdzcbd = `bdzc${responseData.rows[i].yf}`;
                    const zchjbd = `zchj${responseData.rows[i].yf}`;
                    const jllbd = `jll${responseData.rows[i].yf}`;

                    bdxchb[jysrbd] = responseData.rows[i].jysr;
                    bdxchb[hjsyyjbd] = responseData.rows[i].hjsyyj;
                    bdxchb[jssyylbd] = responseData.rows[i].jssyyl;
                    bdxchb[ylbd] = responseData.rows[i].yl;
                    bdxchb[yjbd] = responseData.rows[i].yj;
                    bdxchb[sbjysrbd] =
                      bdxchb[jysrbd] -
                      bdxchb[hjsyyjbd] +
                      bdxchb[jssyylbd] -
                      bdxchb[ylbd] +
                      bdxchb[yjbd];

                    bdxchb[hdnbczbd] = responseData.rows[i].hdnbcz;
                    bdxchb[srhjbd] = bdxchb[sbjysrbd] + bdxchb[hdnbczbd];
                    bdxchb[fbjebd] = responseData.rows[i].fbje;
                    bdxchb[wcxmjebd] = responseData.rows[i].wcxmje;
                    bdxchb[cgggbd] = responseData.rows[i].cggg;
                    bdxchb[lwpqbd] = responseData.rows[i].lwpq;
                    bdxchb[bdzcbd] = responseData.rows[i].bdzc;
                    bdxchb[zchjbd] =
                      bdxchb[wcxmjebd] +
                      bdxchb[cgggbd] +
                      bdxchb[lwpqbd] +
                      bdxchb[bdzcbd];
                    bdxchb[jllbd] = bdxchb[srhjbd] - bdxchb[zchjbd];

                    bdxchb.jysrhj = bdxchb.jysrhj + responseData.rows[i].jysr;
                    bdxchb.hjsyyjhj =
                      bdxchb.hjsyyjhj + responseData.rows[i].hjsyyj;
                    bdxchb.jssyylhj =
                      bdxchb.jssyylhj + responseData.rows[i].jssyyl;
                    bdxchb.ylhj = bdxchb.ylhj + responseData.rows[i].yl;
                    bdxchb.yjhj = bdxchb.yjhj + responseData.rows[i].yj;
                    bdxchb.sbjysrhj = bdxchb.sbjysrhj + bdxchb[sbjysrbd];
                    bdxchb.hdnbczhj =
                      bdxchb.hdnbczhj + responseData.rows[i].hdnbcz;
                    bdxchb.srhjhj = bdxchb.srhjhj + bdxchb[srhjbd];
                    bdxchb.fbjehj = bdxchb.fbjehj + responseData.rows[i].fbje;
                    bdxchb.wcxmjehj =
                      bdxchb.wcxmjehj + responseData.rows[i].wcxmje;
                    bdxchb.cggghj = bdxchb.cggghj + responseData.rows[i].cggg;
                    bdxchb.lwpqhj = bdxchb.lwpqhj + responseData.rows[i].lwpq;
                    bdxchb.bdzchj = bdxchb.bdzchj + responseData.rows[i].bdzc;
                    bdxchb.zchjhj = bdxchb.zchjhj + bdxchb[zchjbd];
                    bdxchb.jllhj = bdxchb.jllhj + bdxchb[jllbd];
                    break;
                  case "地理信息部":
                    const jysrdl = `jysr${responseData.rows[i].yf}`;
                    const hjsyyjdl = `hjsyyj${responseData.rows[i].yf}`;
                    const jssyyldl = `jssyyl${responseData.rows[i].yf}`;
                    const yldl = `yl${responseData.rows[i].yf}`;
                    const yjdl = `yj${responseData.rows[i].yf}`;
                    const sbjysrdl = `sbjysr${responseData.rows[i].yf}`;
                    const hdnbczdl = `hdnbcz${responseData.rows[i].yf}`;
                    const srhjdl = `srhj${responseData.rows[i].yf}`;
                    const fbjedl = `fbje${responseData.rows[i].yf}`;
                    const wcxmjedl = `wcxmje${responseData.rows[i].yf}`;
                    const cgggdl = `cggg${responseData.rows[i].yf}`;
                    const lwpqdl = `lwpq${responseData.rows[i].yf}`;
                    const bdzcdl = `bdzc${responseData.rows[i].yf}`;
                    const zchjdl = `zchj${responseData.rows[i].yf}`;
                    const jlldl = `jll${responseData.rows[i].yf}`;
                    const yfdl = `yf${responseData.rows[i].yf}`;

                    dlxx[jysrdl] = responseData.rows[i].jysr;
                    dlxx[hjsyyjdl] = responseData.rows[i].hjsyyj;
                    dlxx[jssyyldl] = responseData.rows[i].jssyyl;
                    dlxx[yldl] = responseData.rows[i].yl;
                    dlxx[yjdl] = responseData.rows[i].yj;
                    dlxx[sbjysrdl] =
                      dlxx[jysrdl] -
                      dlxx[hjsyyjdl] +
                      dlxx[jssyyldl] -
                      dlxx[yldl] +
                      dlxx[yjdl];

                    dlxx[hdnbczdl] = responseData.rows[i].hdnbcz;
                    dlxx[srhjdl] = dlxx[sbjysrdl] + dlxx[hdnbczdl];
                    dlxx[fbjedl] = responseData.rows[i].fbje;
                    dlxx[wcxmjedl] = responseData.rows[i].wcxmje;
                    dlxx[cgggdl] = responseData.rows[i].cggg;
                    dlxx[lwpqdl] = responseData.rows[i].lwpq;
                    dlxx[bdzcdl] = responseData.rows[i].bdzc;
                    dlxx[zchjdl] =
                      dlxx[wcxmjedl] +
                      dlxx[cgggdl] +
                      dlxx[lwpqdl] +
                      dlxx[bdzcdl];
                    dlxx[jlldl] = dlxx[srhjdl] - dlxx[zchjdl];
                    dlxx[yfdl] = responseData.rows[i].yfcz;

                    dlxx.jysrhj = dlxx.jysrhj + responseData.rows[i].jysr;
                    dlxx.hjsyyjhj = dlxx.hjsyyjhj + responseData.rows[i].hjsyyj;
                    dlxx.jssyylhj = dlxx.jssyylhj + responseData.rows[i].jssyyl;
                    dlxx.ylhj = dlxx.ylhj + responseData.rows[i].yl;
                    dlxx.yjhj = dlxx.yjhj + responseData.rows[i].yj;
                    dlxx.sbjysrhj = dlxx.sbjysrhj + dlxx[sbjysrdl];
                    dlxx.hdnbczhj = dlxx.hdnbczhj + responseData.rows[i].hdnbcz;
                    dlxx.srhjhj = dlxx.srhjhj + dlxx[srhjdl];
                    dlxx.fbjehj = dlxx.fbjehj + responseData.rows[i].fbje;
                    dlxx.wcxmjehj = dlxx.wcxmjehj + responseData.rows[i].wcxmje;
                    dlxx.cggghj = dlxx.cggghj + responseData.rows[i].cggg;
                    dlxx.lwpqhj = dlxx.lwpqhj + responseData.rows[i].lwpq;
                    dlxx.bdzchj = dlxx.bdzchj + responseData.rows[i].bdzc;
                    dlxx.zchjhj = dlxx.zchjhj + dlxx[zchjdl];
                    dlxx.jllhj = dlxx.jllhj + dlxx[jlldl];
                    dlxx.yfhj = dlxx.yfhj + dlxx[yfdl];
                    break;
                  default:
                  // 如果没有匹配的 case，执行这里的代码
                }
              }
              // gxgcb.yjhj = gxgcb.yjhj + 297900;
              // dlxx.yjhj = dlxx.yjhj + 970000;
              gxgcb.jy =
                gxgcb.hjsyyjhj - gxgcb.jssyylhj + gxgcb.ylhj - gxgcb.yjhj;
              dlxx.jy = dlxx.hjsyyjhj - dlxx.jssyylhj + dlxx.ylhj - dlxx.yjhj;
              // 替换占位符
              sheet1.eachRow((row) => {
                row.eachCell((cell) => {
                  if (typeof cell.value === "string") {
                    for (const key in gcchb) {
                      const regex = new RegExp(`{${key}}`, "g");
                      if (regex.test(cell.value)) {
                        // 如果占位符是整格单元格，直接替换成数值类型
                        if (cell.value === `{${key}}`) {
                          cell.value = gcchb[key]; // 保持原数值类型
                        } else {
                          // 否则正常字符串替换（比如 "合计:{jysr1}"）
                          cell.value = cell.value.replace(
                            regex,
                            gcchb[key].toString()
                          );
                        }
                      }
                    }
                  }
                });
              });
              sheet2.eachRow((row) => {
                row.eachCell((cell) => {
                  if (typeof cell.value === "string") {
                    for (const key in gxgcb) {
                      const regex = new RegExp(`{${key}}`, "g");
                      if (regex.test(cell.value)) {
                        // 如果占位符是整格单元格，直接替换成数值类型
                        if (cell.value === `{${key}}`) {
                          cell.value = gxgcb[key]; // 保持原数值类型
                        } else {
                          // 否则正常字符串替换（比如 "合计:{jysr1}"）
                          cell.value = cell.value.replace(
                            regex,
                            gxgcb[key].toString()
                          );
                        }
                      }
                    }
                  }
                });
              });

              sheet3.eachRow((row) => {
                row.eachCell((cell) => {
                  if (typeof cell.value === "string") {
                    for (const key in bdxchb) {
                      const regex = new RegExp(`{${key}}`, "g");
                      if (regex.test(cell.value)) {
                        // 如果占位符是整格单元格，直接替换成数值类型
                        if (cell.value === `{${key}}`) {
                          cell.value = bdxchb[key]; // 保持原数值类型
                        } else {
                          // 否则正常字符串替换（比如 "合计:{jysr1}"）
                          cell.value = cell.value.replace(
                            regex,
                            bdxchb[key].toString()
                          );
                        }
                      }
                    }
                  }
                });
              });

              sheet4.eachRow((row) => {
                row.eachCell((cell) => {
                  if (typeof cell.value === "string") {
                    for (const key in dlxx) {
                      const regex = new RegExp(`{${key}}`, "g");
                      if (regex.test(cell.value)) {
                        // 如果占位符是整格单元格，直接替换成数值类型
                        if (cell.value === `{${key}}`) {
                          cell.value = dlxx[key]; // 保持原数值类型
                        } else {
                          // 否则正常字符串替换（比如 "合计:{jysr1}"）
                          cell.value = cell.value.replace(
                            regex,
                            dlxx[key].toString()
                          );
                        }
                      }
                    }
                  }
                });
              });

              // 导出修改后的文件
              return workbook.xlsx.writeBuffer();
            })
            .then((buffer) => {
              const blob = new Blob([buffer], {
                type: "application/octet-stream",
              });
              const url = window.URL.createObjectURL(blob);
              const a = document.createElement("a");
              a.href = url;
              a.download = `福清分公司各生产部门净利润明细表_${new Date().getTime()}.xls`;
              document.body.appendChild(a);
              a.click();
              a.remove();
              this.cancel();
            })
            .catch((error) => {
              console.error("Error loading the Excel file:", error);
            });
        }
      );
    },
  },
};
</script>
