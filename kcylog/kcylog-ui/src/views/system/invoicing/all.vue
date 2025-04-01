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
      <el-form-item label="发票号" prop="kpFph">
        <el-input
          v-model="queryParams.kpFph"
          placeholder="请输入发票号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="被红冲票号" prop="kpHcph" label-width="100px">
        <el-input
          v-model="queryParams.kpHcph"
          placeholder="请输入被红冲票号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="合同名称" prop="kpHtmc">
        <el-input
          v-model="queryParams.kpHtmc"
          placeholder="请输入合同名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="合同编号" prop="kpHtbh">
        <el-input
          v-model="queryParams.kpHtbh"
          placeholder="请输入合同编号/项目编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="客户名称" prop="kpKhmc">
        <el-input
          v-model="queryParams.kpKhmc"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="开票日期">
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

      <el-form-item label="开票类型" prop="kpType">
        <el-select v-model="queryParams.kpType" placeholder="请选择">
          <el-option
            v-for="item in kpTypes"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:invoicingall:add']"
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
          v-hasPermi="['system:invoicing:export']"
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
      :data="invoicingList"
      @selection-change="handleSelectionChange"
      size="mini"
    >
      <el-table-column label="合同名称" align="center" prop="kpHtmc" />
      <el-table-column label="合同编号/工程编号" align="center" prop="kpHtbh" />
      <el-table-column label="负责部门" align="center" prop="kpFzbm" />
      <el-table-column label="责任人" align="center" prop="kpFzr" />
      <el-table-column label="客户名称" align="center" prop="kpKhmc" />
      <el-table-column label="客户分类" align="center" prop="kpKhfl" />
      <el-table-column label="业务性质" align="center" prop="kpYwxz" />
      <el-table-column label="合同金额" align="center" prop="kpHtje" />
      <el-table-column
        label="开票日期"
        align="center"
        prop="kpKprq"
        width="180"
      />
      <el-table-column label="开票金额" align="center" prop="kpKpje" />
      <el-table-column label="发票号" align="center" prop="kpFph" />
      <el-table-column label="发票类型" align="center" prop="kpFplx" />
      <el-table-column label="被红冲的票号" align="center" prop="kpHcph" />
      <el-table-column
        label="原发票开票时间"
        align="center"
        prop="kpYfpsj"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.kpYfpsj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="是否属于上报权责发生制清单内"
        align="center"
        prop="kpIsZqd"
      />
      <el-table-column
        label="属于上报权责发生制清单内开票金额"
        align="center"
        prop="kpZqdMoney"
      />
      <!-- <el-table-column label="到账时间" align="center" prop="kpDzsj" /> -->
      <el-table-column label="销售方" align="center" prop="kpXsf" />
      <el-table-column label="备注" align="center" prop="kpBz" />
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
            v-hasPermi="['system:invoicingall:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleDz(scope.row)"
            >到账详情</el-button
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

    <!-- 添加或修改经营开票统计对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1000px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="开票类型" prop="kpType" label-width="260px">
          <el-select
            v-model="form.kpType"
            placeholder="请选择"
            :disabled="kpTypeStatus"
          >
            <el-option
              v-for="item in kpTypes"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <span style="margin-left: 10px; color: red">
            如果是红冲票重开，请对比金额是否一致，记得备注说明
          </span>
        </el-form-item>
        <el-form-item label="合同名称" prop="kpHtmc" label-width="260px">
          <el-input v-model="form.kpHtmc" placeholder="请输入合同名称" />
        </el-form-item>
        <el-form-item
          label="合同编号/工程编号"
          prop="kpHtbh"
          label-width="260px"
        >
          <el-input v-model="form.kpHtbh" placeholder="请输入合同编号" />
        </el-form-item>
        <el-form-item label="负责部门" prop="kpFzbm" label-width="260px">
          <el-input v-model="form.kpFzbm" placeholder="请输入负责部门" />
        </el-form-item>
        <el-form-item label="责任人" prop="kpFzr" label-width="260px">
          <el-input v-model="form.kpFzr" placeholder="请输入负责人名称" />
        </el-form-item>
        <el-form-item label="客户名称" prop="kpKhmc" label-width="260px">
          <el-input v-model="form.kpKhmc" placeholder="请输入客户名称" />
        </el-form-item>
        <el-form-item label="客户分类" prop="kpKhfl" label-width="260px">
          <el-select v-model="form.kpKhfl" placeholder="请选择">
            <el-option
              v-for="item in kpKhfls"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="业务性质" prop="kpYwxz" label-width="260px">
          <el-select v-model="form.kpYwxz" placeholder="请选择">
            <el-option
              v-for="item in kpYwxzs"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合同金额" prop="kpHtje" label-width="260px">
          <el-input v-model="form.kpHtje" placeholder="请输入合同金额" />
        </el-form-item>
        <el-form-item label="所在区域" prop="ysSzqu" label-width="260px">
          <el-input v-model="form.ysSzqu" placeholder="请输入所在区域" />
        </el-form-item>
        <el-form-item label="开票日期" prop="kpKprq" label-width="260px">
          <el-date-picker
            clearable
            v-model="form.kpKprq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开票日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="开票金额" prop="kpKpje" label-width="260px">
          <el-input-number
            v-model="form.kpKpje"
            :precision="2"
            :step="0.1"
            :disabled="kpKpjeStatus"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="发票号" prop="kpFph" label-width="260px">
          <el-input
            v-model="form.kpFph"
            placeholder="请输入发票号"
            :disabled="kpFphStatus"
          />
        </el-form-item>
        <el-form-item label="发票类型" prop="kpFplx" label-width="260px">
          <el-select v-model="form.kpFplx" placeholder="请选择">
            <el-option
              v-for="item in kpFplxs"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="被红冲的票号" prop="kpHcph" label-width="260px">
          <el-input
            v-model="form.kpHcph"
            placeholder="请输入被红冲的票号"
            :disabled="kpHcphStatus"
          />
        </el-form-item>
        <el-form-item label="原发票开票时间" prop="kpYfpsj" label-width="260px">
          <el-date-picker
            clearable
            v-model="form.kpYfpsj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请输入原发票开票时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="是否属于上报权责发生制清单内"
          prop="kpIsZqd"
          label-width="260px"
        >
          <el-input
            v-model="form.kpIsZqd"
            placeholder="请输入是否属于上报权责发生制清单内"
          />
        </el-form-item>
        <el-form-item
          label="属于上报权责发生制清单内开票金额"
          prop="kpZqdMoney"
          label-width="260px"
        >
          <el-input-number
            v-model="form.kpZqdMoney"
            :precision="2"
            :step="0.1"
          ></el-input-number>
        </el-form-item>
        <!-- <el-form-item label="到账时间" prop="kpDzsj" label-width="260px">
          <el-input
            v-model="form.kpDzsj"
            placeholder="请输入开票对应的到账时间"
          />
        </el-form-item> -->
        <el-form-item label="销售方" prop="kpXsf" label-width="260px">
          <el-input v-model="form.kpXsf" placeholder="请输入销售方" />
        </el-form-item>
        <el-form-item label="备注" prop="kpBz" label-width="260px">
          <el-input
            v-model="form.kpBz"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看对应的到账详情 -->
    <el-dialog
      :title="dztitle"
      :visible.sync="dzopen"
      width="1000px"
      append-to-body
    >
      <el-table :data="dzForm" size="mini" show-summary>
        <el-table-column label="发票号" align="center" prop="dzFph" />
        <el-table-column label="到账金额" align="center" prop="dzMoney" />
        <el-table-column label="到账日期" align="center" prop="dzRq">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.dzRq, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column label="到账类型" align="center" prop="dzType" />
        <el-table-column label="是否专项债资金" align="center" prop="dzIsZx" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import {
  listInvoicingAll,
  getInvoicing,
  addInvoicing,
  updateInvoicing,
  getArrivalList,
} from "@/api/system/invoicing";

export default {
  name: "Invoicing",
  data() {
    return {
      kpKhfls: [
        {
          value: "事业单位",
          label: "事业单位",
        },
        {
          value: "民营企业",
          label: "民营企业",
        },
        {
          value: "国有企业",
          label: "国有企业",
        },
      ],
      dzForm: [],
      dztitle: "到账详情",
      dzopen: false,
      kpKpjeStatus: false,
      kpHcphStatus: false,
      kpFphStatus: false,
      kpTypeStatus: false,
      kpFplxs: [
        {
          value: "数电-专票",
          label: "数电-专票",
        },
        {
          value: "数电-普票",
          label: "数电-普票",
        },
      ],
      kpYwxzs: [
        {
          value: "合同",
          label: "合同",
        },
        {
          value: "行政性",
          label: "行政性",
        },
      ],
      kpTypes: [
        {
          value: 1,
          label: "正常",
        },
        {
          value: 2,
          label: "红冲",
        },
      ],
      // 日期范围
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
      // 经营开票统计表格数据
      invoicingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        kpFph: null,
        kpType: null,
        kpHtmc: null,
        kpHtbh: null,
        kpKhmc: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ysSzqu: [
          { required: true, message: "请输入所在区域", trigger: "blur" },
        ],
        kpFzr: [
          { required: true, message: "请输入负责人名称", trigger: "blur" },
        ],
        kpHtje: [
          { required: true, message: "请输入合同金额", trigger: "blur" },
        ],
        // kpHtmc: [
        //   { required: true, message: "请输入合同名称", trigger: "blur" },
        // ],
        kpHtbh: [
          { required: true, message: "请输入合同编号", trigger: "blur" },
        ],
        kpFzbm: [
          { required: true, message: "请输入负责部门", trigger: "blur" },
        ],
        kpKhmc: [
          { required: true, message: "请输入客户名称", trigger: "blur" },
        ],
        // kpKhfl: [
        //   { required: true, message: "请输入客户分类", trigger: "blur" },
        // ],
        // kpYwxz: [
        //   { required: true, message: "业务性质不能为空", trigger: "blur" },
        // ],
        kpKpje: [
          { required: true, message: "开票金额不能为空", trigger: "blur" },
        ],
        kpFph: [{ required: true, message: "发票号不能为空", trigger: "blur" }],
        kpFplx: [
          { required: true, message: "发票类型不能为空", trigger: "blur" },
        ],
        kpKprq: [
          { required: true, message: "开票类型不能为空", trigger: "blur" },
        ],
        kpType: [
          {
            required: true,
            message: "请选择开票类型",
            trigger: "change",
          },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleDz(value) {
      getArrivalList(value.kpFph).then((response) => {
        if (response.rows.length == 0) {
          this.$modal.msgError(`无到账记录～`);
          return;
        }
        this.dzForm = response.rows;
        this.dzopen = true;
      });
    },
    /** 查询经营开票统计列表 */
    getList() {
      this.loading = true;
      listInvoicingAll(
        this.addDateRange(this.queryParams, this.dateRange)
      ).then((response) => {
        this.invoicingList = response.rows;
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
        kpId: null,
        kpHtmc: null,
        kpHtbh: null,
        kpFzbm: null,
        kpFzr: null,
        kpKhmc: null,
        kpKhfl: null,
        kpYwxz: null,
        kpHtje: null,
        kpKprq: null,
        kpKpje: null,
        kpFph: null,
        kpFplx: null,
        kpHcph: null,
        kpYfpsj: null,
        kpIsZqd: null,
        kpZqdMoney: null,
        kpDzsj: null,
        kpXsf: null,
        kpBz: null,
        kpType: null,
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
      this.ids = selection.map((item) => item.kpId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.kpHcphStatus = false;
      this.kpFphStatus = false;
      this.kpTypeStatus = false;
      this.kpKpjeStatus = false;
      this.open = true;
      this.title = "添加开票记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const kpId = row.kpId || this.ids;
      getInvoicing(kpId).then((response) => {
        this.form = response.data;
        this.kpHcphStatus = true;
        this.kpFphStatus = true;
        this.kpTypeStatus = true;
        this.kpKpjeStatus = true;
        this.open = true;
        this.title = "修改开票记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (
            this.form.kpType == 2 &&
            (this.form.kpHcph == "" ||
              this.form.kpHcph == null ||
              this.form.kpHcph == undefined)
          ) {
            this.$modal.msgError(`请输入被红冲票号`);
            return;
          }
          if (
            this.form.kpType == 2 &&
            (this.form.kpYfpsj == "" ||
              this.form.kpYfpsj == null ||
              this.form.kpYfpsj == undefined)
          ) {
            this.$modal.msgError(`请输入原发票开票时间`);
            return;
          }
          if (this.form.kpType == 2 && this.form.kpKpje > 0) {
            this.$modal.msgError(`红冲开票金额不能为正数～`);
            return;
          }
          if (this.form.kpId != null) {
            updateInvoicing(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.$confirm(
              `<p>请仔细核定填写的信息，新增的开票数据是<span style="color: red;">无法删除</span>的，特别核对下
                <span style="color: red;">开票类型</span>、<span style="color: red;">开票金额</span>、
                <span style="color: red;">发票号</span>、<span style="color: red;">被红冲票号</span>，这四个字段内容在之后是<span style="color: red;">不允许修改</span>的，
                确认信息后再点击确认！！</p>`,
              "提示",
              {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
                dangerouslyUseHTMLString: true, // 启用 HTML
              }
            )
              .then(() => {
                addInvoicing(this.form).then((response) => {
                  this.$modal.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                });
              })
              .catch(() => {
                this.$message({
                  type: "info",
                  message: "已取消新增",
                });
              });
          }
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "system/invoicing/exportAll",
        {
          ...this.queryParams,
        },
        `开票记录_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
