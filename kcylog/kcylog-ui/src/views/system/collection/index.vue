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
      <el-form-item label="负责人" prop="ysFzr">
        <el-input
          v-model="queryParams.ysFzr"
          placeholder="请输入负责人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发票号" prop="ysFph">
        <el-input
          v-model="queryParams.ysFph"
          placeholder="请输入发票号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否坏账" prop="ysIshz">
        <el-select v-model="queryParams.ysIshz" placeholder="请选择">
          <el-option
            v-for="item in ysIshzs"
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
          v-hasPermi="['system:collection:export']"
          >导出总表、1年内、1-3年、3年以上、本上报周期新增三年以上清单</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExportCstj"
          v-hasPermi="['system:collection:cstj']"
          >导出催收发函统计表</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExportYstj"
          v-hasPermi="['system:collection:yszktj']"
          >导出应收账款统计表</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="collectionList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="发票号" align="center" prop="ysFph" />
      <el-table-column label="合同名称" align="center">
        <template #default="scope">
          <el-tooltip
            class="item"
            effect="dark"
            :content="scope.row.ysHtmc"
            placement="top"
          >
            <span>
              {{
                scope.row.ysHtmc.length > 6
                  ? scope.row.ysHtmc.slice(0, 6) + "..."
                  : scope.row.ysHtmc
              }}
            </span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="合同编号" align="center" prop="ysHtbh" />
      <el-table-column label="负责部门" align="center" prop="ysFzbm" />
      <el-table-column label="负责人" align="center" prop="ysFzr" />
      <el-table-column label="客户名称" align="center" prop="ysKhmc" />
      <el-table-column label="客户分类" align="center" prop="ysKhfl" />
      <el-table-column label="合同金额" align="center" prop="ysHtje" />
      <el-table-column
        label="开票日期"
        align="center"
        prop="ysKprq"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ysKprq, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="开票金额" align="center" prop="ysKpje" />
      <el-table-column label="已到账金额" align="center" prop="ysYdzje" />
      <el-table-column label="未到账金额" align="center" prop="ysWdzje" />
      <el-table-column label="发函类型" align="center" prop="ysFhlx" />
      <el-table-column label="发函时间" align="center" prop="ysFhsj" />
      <el-table-column label="账龄" align="center" prop="ysZl" />
      <el-table-column label="是否坏账" align="center" prop="ysIshz">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ysIshz === 0" type="success">否</el-tag>
          <el-tag v-else-if="scope.row.ysIshz === 1" type="warning">是</el-tag>
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
            v-hasPermi="['system:collection:edit']"
            v-if="showButton(scope.row.ysFzr)"
            >编辑</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleDetail(scope.row)"
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

    <!-- 添加或修改应收账款对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1000px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="是否坏账" prop="ysIshz" label-width="200px">
          <el-select v-model="form.ysIshz" placeholder="请选择">
            <el-option
              v-for="item in ysIshzs"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="已发送催款函/请款函"
          prop="ysFhlx"
          label-width="200px"
        >
          <el-input
            v-model="form.ysFhlx"
            placeholder="请输入已发送催款函/请款函"
          />
        </el-form-item>
        <el-form-item
          label="发函时间（发几份写几条）"
          prop="ysFhsj"
          label-width="200px"
        >
          <el-input
            v-model="form.ysFhsj"
            placeholder="发函时间（发几份写几条"
          />
        </el-form-item>
        <el-form-item label="措施、未收回原因" prop="ysCs" label-width="200px">
          <el-input
            v-model="form.ysCs"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="备注" prop="ysBz" label-width="200px">
          <el-input
            v-model="form.ysBz"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="资金来源" prop="ysZjly" label-width="200px">
          <el-input v-model="form.ysZjly" placeholder="请输入资金来源" />
        </el-form-item>
        <el-form-item label="跟踪情况" prop="ysGzqk" label-width="200px">
          <el-input
            v-model="form.ysGzqk"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item
          label="最新一次进展情况"
          prop="ysZxqk"
          label-width="200px"
        >
          <el-input
            v-model="form.ysZxqk"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item
          label="合同约定支付时间"
          prop="ysHtrq"
          label-width="200px"
        >
          <el-input
            v-model="form.ysHtrq"
            placeholder="请输入合同约定支付时间"
          />
        </el-form-item>
        <el-form-item label="诉讼到期时间" prop="ysSsdqsj" label-width="200px">
          <el-input v-model="form.ysSsdqsj" placeholder="请输入诉讼到期时间" />
        </el-form-item>
        <el-form-item
          label="业主所在地是否为四城区"
          prop="ysIssq"
          label-width="200px"
        >
          <el-input
            v-model="form.ysIssq"
            placeholder="请输入业主所在地是否为四城区"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="应收账款详情"
      :visible.sync="ysopen"
      width="1000px"
      append-to-body
    >
      <el-descriptions>
        <el-descriptions-item label="发票号" label-class-name="bold-label">{{
          form.ysFph
        }}</el-descriptions-item>
        <el-descriptions-item label="合同名称" label-class-name="bold-label">{{
          form.ysHtmc
        }}</el-descriptions-item>
        <el-descriptions-item label="合同编号" label-class-name="bold-label">{{
          form.ysHtbh
        }}</el-descriptions-item>
        <el-descriptions-item label="负责部门" label-class-name="bold-label">{{
          form.ysFzbm
        }}</el-descriptions-item>
        <el-descriptions-item label="责任人" label-class-name="bold-label">{{
          form.ysFzr
        }}</el-descriptions-item>
        <el-descriptions-item label="客户名称" label-class-name="bold-label">{{
          form.ysKhmc
        }}</el-descriptions-item>
        <el-descriptions-item label="客户分类" label-class-name="bold-label">{{
          form.ysKhfl
        }}</el-descriptions-item>
        <el-descriptions-item label="合同金额" label-class-name="bold-label">{{
          form.ysHtje
        }}</el-descriptions-item>
        <el-descriptions-item label="开票日期" label-class-name="bold-label">{{
          parseTime(form.ysKprq, "{y}-{m}-{d}")
        }}</el-descriptions-item>
        <el-descriptions-item label="开票金额" label-class-name="bold-label">{{
          form.ysKpje
        }}</el-descriptions-item>
        <el-descriptions-item
          label="已到账金额"
          label-class-name="bold-label"
          >{{ form.ysYdzje }}</el-descriptions-item
        >
        <el-descriptions-item
          label="未到账金额"
          label-class-name="bold-label"
          >{{ form.ysWdzje }}</el-descriptions-item
        >
        <el-descriptions-item
          label="已发送催款函/请款函"
          label-class-name="bold-label"
          >{{ form.ysFhlx }}</el-descriptions-item
        >
        <el-descriptions-item label="发函时间" label-class-name="bold-label">{{
          form.ysFhsj
        }}</el-descriptions-item>
        <el-descriptions-item label="资金来源" label-class-name="bold-label">{{
          form.ysZjly
        }}</el-descriptions-item>
        <el-descriptions-item
          label="未收回原因"
          label-class-name="bold-label"
          >{{ form.ysCs }}</el-descriptions-item
        >
        <el-descriptions-item label="备注" label-class-name="bold-label">{{
          form.ysBz
        }}</el-descriptions-item>
        <el-descriptions-item label="跟踪情况" label-class-name="bold-label">{{
          form.ysGzqk
        }}</el-descriptions-item>
        <el-descriptions-item
          label="最新一次进展情况"
          label-class-name="bold-label"
          >{{ form.ysZxqk }}</el-descriptions-item
        >
        <el-descriptions-item
          label="合同约定支付时间"
          label-class-name="bold-label"
          >{{ form.ysHtrq }}</el-descriptions-item
        >
        <el-descriptions-item
          label="诉讼到期时间"
          label-class-name="bold-label"
          >{{ form.ysSsdqsj }}</el-descriptions-item
        >
        <el-descriptions-item
          label="业主所在地区是否为四城区"
          label-class-name="bold-label"
          >{{ form.ysIssq }}</el-descriptions-item
        >
      </el-descriptions>
    </el-dialog>

    <!-- 上报时间选择 -->
    <el-dialog
      title="时间选择"
      :visible.sync="timeOpen"
      width="500px"
      append-to-body
    >
      <el-form
        ref="timeForm"
        :model="timeForm"
        :rules="timeRules"
        label-width="80px"
      >
        <el-form-item
          label="上次上报时间"
          prop="ysKprqLast"
          label-width="200px"
        >
          <el-date-picker
            v-model="timeForm.ysKprqLast"
            type="date"
            placeholder="选择日期"
            :picker-options="pickerOptions"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="本次上报时间" prop="ysKprqCs" label-width="200px">
          <el-date-picker
            v-model="timeForm.ysKprqCs"
            type="date"
            placeholder="选择日期"
            :picker-options="pickerOptionsCs"
            disabled
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormTime">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 催收发函时间选择 -->
    <el-dialog
      title="时间选择"
      :visible.sync="csTimeOpen"
      width="500px"
      append-to-body
    >
      <el-form
        ref="csTimeForm"
        :model="csTimeForm"
        :rules="csTimeRules"
        label-width="80px"
      >
        <el-form-item label="本次上报时间" prop="ysKprqCs" label-width="200px">
          <el-date-picker
            v-model="csTimeForm.ysKprqCs"
            type="date"
            placeholder="选择日期"
            :picker-options="pickerOptionsCs"
            disabled
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormCS">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 应收账款时间选择 -->
    <el-dialog
      title="时间选择"
      :visible.sync="ysTimeOpen"
      width="500px"
      append-to-body
    >
      <el-form
        ref="ysTimeForm"
        :model="ysTimeForm"
        :rules="ysTimeRules"
        label-width="80px"
      >
        <el-form-item
          label="上次上报时间"
          prop="ysKprqLast"
          label-width="200px"
        >
          <el-date-picker
            v-model="ysTimeForm.ysKprqLast"
            type="date"
            placeholder="选择日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="本次上报时间" prop="ysKprqCs" label-width="200px">
          <el-date-picker
            v-model="ysTimeForm.ysKprqCs"
            type="date"
            placeholder="选择日期"
            disabled
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormYS">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listCollection,
  getCollection,
  updateCollection,
  exportCs,
  exportYs,
} from "@/api/system/collection";
import userInfo from "@/store/modules/user";
import ExcelJS from "exceljs";

export default {
  name: "Collection",
  data() {
    return {
      ysIshzs: [
        {
          value: 0,
          label: "否",
        },
        {
          value: 1,
          label: "是",
        },
      ],
      pickerOptionsCs: {
        disabledDate: (date) => {
          const currentDate = new Date();
          const startOfThisMonth = new Date(
            currentDate.getFullYear(),
            currentDate.getMonth(),
            1
          ); // 本月第一天
          const endOfThisMonth = new Date(
            currentDate.getFullYear(),
            currentDate.getMonth() + 1,
            0
          ); // 本月最后一天

          // 禁用不在本月和上个月范围内的日期
          return date < startOfThisMonth || date > endOfThisMonth;
        },
      },
      pickerOptions: {
        disabledDate: (date) => {
          const currentDate = new Date();

          const endOfThisMonth = new Date(
            currentDate.getFullYear(),
            currentDate.getMonth() + 1,
            0
          );

          const startOfLastMonth = new Date(
            currentDate.getFullYear(),
            currentDate.getMonth() - 1,
            1
          );

          // 禁用不在本月和上个月范围内的日期
          return date < startOfLastMonth || date > endOfThisMonth;
        },
      },
      ysIshzs: [
        {
          value: 0,
          label: "否",
        },
        {
          value: 1,
          label: "是",
        },
      ],
      csTimeForm: {},
      ysTimeForm: {},
      ysTimeOpen: false,
      csTimeOpen: false,
      timeOpen: false,
      timeForm: {},
      ysopen: false,
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
      // 应收账款表格数据
      collectionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ysFzr: null,
        ysKprqCs: null,
        ysFph: null,
      },
      // 表单参数
      form: {},
      timeRules: {
        ysKprqCs: [
          { required: true, message: "请选择本次上报时间", trigger: "blur" },
          { validator: this.validateDate, trigger: "change" },
        ],
        ysKprqLast: [
          { required: true, message: "请选择上次上报时间", trigger: "blur" },
        ],
      },
      ysTimeRules: {
        ysKprqCs: [
          { required: true, message: "请选择本次上报时间", trigger: "blur" },
          { validator: this.validateDate, trigger: "change" },
        ],
        ysKprqLast: [
          { required: true, message: "请选择上次上报时间", trigger: "blur" },
        ],
      },
      csTimeRules: {
        ysKprqCs: [
          { required: true, message: "请选择本次上报时间", trigger: "blur" },
        ],
      },
      // 表单校验
      rules: {
        ysFzr: [{ required: true, message: "负责人不能为空", trigger: "blur" }],
        ysKpje: [
          { required: true, message: "开票金额不能为空", trigger: "blur" },
        ],
        ysYdzje: [
          { required: true, message: "已到账金额不能为空", trigger: "blur" },
        ],
        ysWdzje: [
          { required: true, message: "未到账金额不能为空", trigger: "blur" },
        ],
        ysFph: [{ required: true, message: "发票号不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    validateDate(rule, value, callback) {
      if (!value) {
        return callback(new Error("请选择本次上报时间"));
      }
      const lastDate = new Date(this.timeForm.ysKprqLast);
      const currentDate = new Date(value);
      if (currentDate <= lastDate) {
        callback(new Error("本次上报时间必须大于上次上报时间"));
      } else {
        callback();
      }
    },
    showButton(userName) {
      return userName == userInfo.state.name;
    },
    /** 查询应收账款列表 */
    getList() {
      this.loading = true;
      listCollection(this.queryParams).then((response) => {
        this.collectionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.timeOpen = false;
      this.csTimeOpen = false;
      this.ysTimeOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.csTimeForm = {
        ysKprqCs: new Date(),
      };
      this.ysTimeForm = {
        ysKprqCs: new Date(),
        ysKprqLast: null,
      };
      this.timeForm = {
        ysKprqCs: new Date(),
        ysKprqLast: null,
      };
      this.form = {
        ysId: null,
        ysHtmc: null,
        ysHtbh: null,
        ysFzbm: null,
        ysFzr: null,
        ysKhmc: null,
        ysKhfl: null,
        ysHtje: null,
        ysKprq: null,
        ysKpje: null,
        ysYdzje: null,
        ysWdzje: null,
        ysFhlx: null,
        ysFhsj: null,
        ysCs: null,
        ysBz: null,
        ysZjly: null,
        ysFph: null,
        ysGzqk: null,
        ysZxqk: null,
        ysHtrq: null,
        ysSsdqsj: null,
        ysIssq: null,
        ysStatus: null,
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
      this.ids = selection.map((item) => item.ysId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加应收账款";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const ysId = row.ysId || this.ids;
      getCollection(ysId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改应收账款";
      });
    },
    handleDetail(row) {
      const ysId = row.ysId || this.ids;
      getCollection(ysId).then((response) => {
        this.form = response.data;
        this.ysopen = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.ysId != null) {
            updateCollection(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
          }
        }
      });
    },
    submitFormTime() {
      this.$refs["timeForm"].validate((valid) => {
        if (valid) {
          if (this.timeForm.ysKprqCs) {
            // 将日期对象转换为 yyyy-mm 格式的字符串
            const date = new Date(this.timeForm.ysKprqCs);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, "0"); // 月份从 0 开始
            const day = String(date.getDate()).padStart(2, "0"); //
            this.queryParams.ysKprqCs = `${year}-${month}-${day}`;
          }
          if (this.timeForm.ysKprqLast) {
            // 将日期对象转换为 yyyy-mm 格式的字符串
            const date = new Date(this.timeForm.ysKprqLast);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, "0"); // 月份从 0 开始
            const day = String(date.getDate()).padStart(2, "0"); //
            this.queryParams.ysKprqLast = `${year}-${month}-${day}`;
          }
          this.download(
            "system/collection/export",
            {
              ...this.queryParams,
            },
            "应收账款清单_" + this.queryParams.ysKprqCs + ".xlsx"
          );
          this.cancel();
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.timeOpen = true;
      this.timeForm = { ysKprqCs: new Date() };
      // if (!this.queryParams.ysKprqCs) {
      //   this.$modal.msgError(`请选择截止日期`);
      //   return;
      // }
    },
    handleExportCstj() {
      this.csTimeOpen = true;
      this.csTimeForm = {
        ysKprqCs: new Date(),
      };
    },
    handleExportYstj() {
      this.ysTimeOpen = true;
      this.ysTimeForm = { ysKprqCs: new Date() };
    },
    submitFormCS() {
      this.$refs["csTimeForm"].validate((valid) => {
        if (valid) {
          var dateRq = "";
          if (this.csTimeForm.ysKprqCs) {
            // 将日期对象转换为 yyyy-mm 格式的字符串
            const date = new Date(this.csTimeForm.ysKprqCs);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, "0"); // 月份从 0 开始
            const day = String(date.getDate()).padStart(2, "0"); //
            this.queryParams.ysKprqCs = `${year}-${month}-${day}`;
            dateRq = `${year}年${month}月${day}日`;
          }

          exportCs(this.queryParams).then((responseData) => {
            fetch("/csfh.xlsx")
              .then((response) => {
                if (!response.ok)
                  throw new Error("Network response was not ok");
                return response.arrayBuffer();
              })
              .then((data) => {
                const workbook = new ExcelJS.Workbook();
                return workbook.xlsx.load(data);
              })
              .then((workbook) => {
                const sheet = workbook.getWorksheet("催收发函统计表");

                // 替换的对象
                responseData.data.tjrq = dateRq;
                const dataToReplace = responseData.data;

                // 替换占位符
                sheet.eachRow((row) => {
                  row.eachCell((cell) => {
                    if (typeof cell.value === "string") {
                      for (const key in dataToReplace) {
                        cell.value = cell.value.replace(
                          new RegExp(`{${key}}`, "g"),
                          dataToReplace[key]
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
                a.download = "催收发函统计表_" + dateRq + ".xlsx";
                document.body.appendChild(a);
                a.click();
                a.remove();
                this.cancel();
              })
              .catch((error) => {
                console.error("Error loading the Excel file:", error);
              });
          });
        }
      });
    },
    submitFormYS() {
      this.$refs["ysTimeForm"].validate((valid) => {
        if (valid) {
          var dateRq = "";
          if (this.ysTimeForm.ysKprqCs) {
            // 将日期对象转换为 yyyy-mm 格式的字符串
            const date = new Date(this.ysTimeForm.ysKprqCs);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, "0"); // 月份从 0 开始
            const day = String(date.getDate()).padStart(2, "0"); //
            this.queryParams.ysKprqCs = `${year}-${month}-${day}`;
            dateRq = `${year}年${month}月${day}日`;
          }
          if (this.ysTimeForm.ysKprqLast) {
            // 将日期对象转换为 yyyy-mm 格式的字符串
            const date = new Date(this.ysTimeForm.ysKprqLast);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, "0"); // 月份从 0 开始
            const day = String(date.getDate()).padStart(2, "0"); //
            this.queryParams.ysKprqLast = `${year}-${month}-${day}`;
          }
          exportYs(this.queryParams).then((responseData) => {
            fetch("/yszktj.xlsx")
              .then((response) => {
                if (!response.ok)
                  throw new Error("Network response was not ok");
                return response.arrayBuffer();
              })
              .then((data) => {
                const workbook = new ExcelJS.Workbook();
                return workbook.xlsx.load(data);
              })
              .then((workbook) => {
                const sheet = workbook.getWorksheet("应收账款统计表");

                // 替换的对象
                responseData.data.tjrq = dateRq;
                const dataToReplace = responseData.data;

                // 替换占位符
                sheet.eachRow((row) => {
                  row.eachCell((cell) => {
                    if (typeof cell.value === "string") {
                      for (const key in dataToReplace) {
                        cell.value = cell.value.replace(
                          new RegExp(`{${key}}`, "g"),
                          dataToReplace[key]
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
                a.download = "应收账款统计表_" + dateRq + ".xlsx";
                document.body.appendChild(a);
                a.click();
                a.remove();
                this.cancel();
              })
              .catch((error) => {
                console.error("Error loading the Excel file:", error);
              });
          });
        }
      });
    },
  },
};
</script>

<style>
.bold-label {
  font-weight: bold;
}
</style>
