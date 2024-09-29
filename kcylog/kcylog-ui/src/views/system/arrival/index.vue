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
      <el-form-item label="发票号" prop="dzFph">
        <el-input
          v-model="queryParams.dzFph"
          placeholder="请输入发票号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="到账日期" prop="dzRq">
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
          v-hasPermi="['system:arrival:add']"
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
          v-hasPermi="['system:arrival:export']"
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
      :data="arrivalList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="客户名称" align="center" prop="dzKhmc" />
      <el-table-column label="合同编号" align="center" prop="dzHtbh" />
      <el-table-column label="合同金额" align="center" prop="dzHtje" />
      <el-table-column label="业务性质" align="center" prop="dzYwxz" />
      <el-table-column label="负责人" align="center" prop="dzFzr" />
      <el-table-column
        label="开票日期"
        align="center"
        prop="dzKprq"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dzKprq, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="开票金额" align="center" prop="dzKpje" />
      <el-table-column label="发票号" align="center" prop="dzFph" />
      <el-table-column label="到账金额" align="center" prop="dzMoney" />
      <el-table-column label="到账日期" align="center" prop="dzRq" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dzRq, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="到账类型" align="center" prop="dzType" />
      <el-table-column label="是否专项债资金" align="center" prop="dzIsZx" />
      <el-table-column label="销售方" align="center" prop="dzXsf" />
      <el-table-column label="备注" align="center" prop="dzBz" />
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
            v-hasPermi="['system:arrival:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleKp(scope.row)"
            >查看开票信息</el-button
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

    <!-- 添加或修改经营到账统计对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1000px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户名称" prop="dzKhmc" label-width="150px">
          <el-input v-model="form.dzKhmc" placeholder="请输入客户名称" />
        </el-form-item>
        <el-form-item
          label="合同编号/工程编号"
          prop="dzHtbh"
          label-width="150px"
        >
          <el-input v-model="form.dzHtbh" placeholder="请输入合同编号" />
        </el-form-item>
        <el-form-item label="合同金额" prop="dzHtje" label-width="150px">
          <el-input v-model="form.dzHtje" placeholder="请输入合同金额" />
        </el-form-item>
        <el-form-item label="业务性质" prop="dzYwxz" label-width="150px">
          <el-select v-model="form.dzYwxz" placeholder="请选择">
            <el-option
              v-for="item in dzYwxzs"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开票日期" prop="dzKprq" label-width="150px">
          <el-date-picker
            clearable
            v-model="form.dzKprq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开票日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="开票金额" prop="dzKpje" label-width="150px">
          <el-input-number
            v-model="form.dzKpje"
            :precision="2"
            :step="0.1"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="发票号" prop="dzFph" label-width="150px">
          <el-input
            v-model="form.dzFph"
            placeholder="请输入发票号"
            :disabled="dzFphStatus"
          />
        </el-form-item>
        <el-form-item label="到账金额" prop="dzMoney" label-width="150px">
          <el-input-number
            v-model="form.dzMoney"
            :precision="2"
            :step="0.1"
            :disabled="dzMoneyStatus"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="到账日期" prop="dzRq" label-width="150px">
          <el-date-picker
            clearable
            v-model="form.dzRq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择到账日期"
          >
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="到账类型" prop="dzType" label-width="150px">
          <el-select v-model="form.dzType" placeholder="请选择">
            <el-option
              v-for="item in dzTypes"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="是否专项债资金" prop="dzIsZx" label-width="150px">
          <el-input v-model="form.dzIsZx" placeholder="请输入是否专项债资金" />
        </el-form-item>
        <el-form-item label="销售方" prop="dzXsf" label-width="150px">
          <el-input v-model="form.dzXsf" placeholder="请输入销售方" />
        </el-form-item>
        <el-form-item label="备注" prop="dzBz" label-width="150px">
          <el-input
            v-model="form.dzBz"
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

    <!-- 查看对应的开票详情 -->
    <el-dialog
      :title="kptitle"
      :visible.sync="kpopen"
      width="1000px"
      append-to-body
    >
      <el-descriptions>
        <el-descriptions-item label="合同名称">{{
          dzForm.kpHtmc
        }}</el-descriptions-item>
        <el-descriptions-item label="合同编号/工程编号">
          {{ dzForm.kpHtbh }}
        </el-descriptions-item>
        <el-descriptions-item label="负责部门">
          {{ dzForm.kpFzbm }}
        </el-descriptions-item>
        <el-descriptions-item label="责任人">
          {{ dzForm.kpFzr }}
        </el-descriptions-item>
        <el-descriptions-item label="客户名称">
          {{ dzForm.kpKhmc }}
        </el-descriptions-item>
        <el-descriptions-item label="客户分类">
          {{ dzForm.kpKhfl }}
        </el-descriptions-item>
        <el-descriptions-item label="业务性质">
          {{ dzForm.kpYwxz }}
        </el-descriptions-item>
        <el-descriptions-item label="合同金额">
          {{ dzForm.kpHtje }}
        </el-descriptions-item>
        <el-descriptions-item label="开票日期">
          {{ parseTime(dzForm.kpKprq, "{y}-{m}-{d}") }}
        </el-descriptions-item>
        <el-descriptions-item label="开票金额">
          {{ dzForm.kpKpje }}
        </el-descriptions-item>
        <el-descriptions-item label="发票号">
          {{ dzForm.kpFph }}
        </el-descriptions-item>
        <el-descriptions-item label="发票类型">
          {{ dzForm.kpFplx }}
        </el-descriptions-item>
        <el-descriptions-item label="是否属于上报权责发生制清单内">
          {{ dzForm.kpIsZqd }}
        </el-descriptions-item>
        <el-descriptions-item label="属于上报权责发生制清单内开票金额">
          {{ dzForm.kpZqdMoney }}</el-descriptions-item
        >
        <el-descriptions-item label="销售方">{{
          dzForm.kpXsf
        }}</el-descriptions-item>
        <el-descriptions-item label="备注">{{
          dzForm.kpBz
        }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
import {
  listArrival,
  getArrival,
  addArrival,
  updateArrival,
  getInvoicingByFPH,
} from "@/api/system/arrival";

export default {
  name: "Arrival",
  data() {
    return {
      dzForm: {},
      kptitle: "",
      kpopen: false,
      dzFphStatus: false,
      dzMoneyStatus: false,
      dzTypes: [
        {
          value: "本年度开票本年度到账",
          label: "本年度开票本年度到账",
        },
        {
          value: "非本年度开票到账",
          label: "非本年度开票到账",
        },
      ],
      dzYwxzs: [
        {
          value: "合同",
          label: "合同",
        },
        {
          value: "行政性",
          label: "行政性",
        },
      ],
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
      // 经营到账统计表格数据
      arrivalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dzKhmc: null,
        dzHtbh: null,
        dzHtje: null,
        dzYwxz: null,
        dzFzr: null,
        dzKprq: null,
        dzKpje: null,
        dzFph: null,
        dzMoney: null,
        dzRq: null,
        dzType: null,
        dzIsZx: null,
        dzXsf: null,
        dzBz: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dzKhmc: [
          { required: true, message: "请输入客户名称", trigger: "blur" },
        ],
        dzHtbh: [
          { required: true, message: "请输入合同名称", trigger: "blur" },
        ],
        dzHtje: [
          { required: true, message: "请输入合同金额", trigger: "blur" },
        ],
        dzKpje: [
          { required: true, message: "请输入开票金额", trigger: "blur" },
        ],
        dzKprq: [
          { required: true, message: "请输入开票日期", trigger: "blur" },
        ],
        dzYwxz: [
          { required: true, message: "业务性质不能为空", trigger: "blur" },
        ],
        dzFph: [{ required: true, message: "发票号不能为空", trigger: "blur" }],
        dzRq: [{ required: true, message: "请选择到账日期", trigger: "blur" }],
        dzType: [
          { required: true, message: "请选择到账类型", trigger: "blur" },
        ],
        dzMoney: [
          { required: true, message: "到账金额不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleKp(value) {
      getInvoicingByFPH(value.dzFph).then((response) => {
        if (response.data == undefined || response.data == null) {
          this.$modal.msgError(
            `该到账记录票号对应的开票记录未录入，请对应负责人到开票模块进行录入～`
          );
          return;
        }
        this.dzForm = response.data;
        this.kptitle = "开票详情";
        this.kpopen = true;
      });
    },
    /** 查询经营到账统计列表 */
    getList() {
      this.loading = true;
      listArrival(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.arrivalList = response.rows;
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
        dzId: null,
        dzKhmc: null,
        dzHtbh: null,
        dzHtje: null,
        dzYwxz: null,
        dzFzr: null,
        dzKprq: null,
        dzKpje: null,
        dzFph: null,
        dzMoney: null,
        dzRq: null,
        dzType: null,
        dzIsZx: null,
        dzXsf: null,
        dzBz: null,
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
      this.ids = selection.map((item) => item.dzId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.dzFphStatus = false;
      this.dzMoneyStatus = false;
      this.open = true;
      this.title = "添加到账记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const dzId = row.dzId || this.ids;
      getArrival(dzId).then((response) => {
        this.form = response.data;
        this.dzFphStatus = true;
        this.dzMoneyStatus = true;
        this.open = true;
        this.title = "修改到账记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.dzId != null) {
            updateArrival(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.$confirm(
              `<p>请仔细核定填写的信息，新增的到账数据是<span style="color: red;">无法删除</span>的，特别核对下
              <span style="color: red;">发票号</span>、<span style="color: red;">到账金额</span>，这两个字段内容在之后是<span style="color: red;">不允许修改</span>的，
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
                addArrival(this.form).then((response) => {
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
        "system/arrival/export",
        {
          ...this.queryParams,
        },
        `到账记录_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
