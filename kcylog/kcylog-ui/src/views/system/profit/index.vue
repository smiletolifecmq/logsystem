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
          >导出</el-button
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
        <el-form-item label="预留" prop="yl" v-hasPermi="['system:profit:bz']">
          <el-input v-model="form.yl" placeholder="请输入预留" />
        </el-form-item>
        <el-form-item label="预结" prop="yj" v-hasPermi="['system:profit:bz']">
          <el-input v-model="form.yj" placeholder="请输入预结" />
        </el-form-item>
        <el-form-item
          label="核定内部生产产值"
          prop="hdnbcz"
          v-hasPermi="['system:profit:ch']"
        >
          <el-input
            v-model="form.hdnbcz"
            placeholder="请输入核定内部生产产值"
          />
        </el-form-item>
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
        yearStr === value.nf &&
        monthStr === value.yf &&
        value.bm === userInfo.state.deptName
      );
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
      if (
        this.queryParams.nf == null ||
        this.queryParams.nf == undefined ||
        this.queryParams.nf == ""
      ) {
        this.$message.error("请选择年份～");
        return;
      }
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

              // 替换的对象
              console.log(responseData.rows);
              const dataToReplace = responseData.rows;
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

              // 替换占位符
              sheet1.eachRow((row) => {
                row.eachCell((cell) => {
                  if (typeof cell.value === "string") {
                    for (const key in gcchb) {
                      cell.value = cell.value.replace(
                        new RegExp(`{${key}}`, "g"),
                        gcchb[key]
                      );
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
