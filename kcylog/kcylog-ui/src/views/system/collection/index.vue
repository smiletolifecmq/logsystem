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
      <el-form-item label="截止日期" prop="ysKprqCs" label-width="100px">
        <el-date-picker
          v-model="queryParams.ysKprqCs"
          type="date"
          placeholder="选择日期"
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:collection:export']"
          >导出总表、1年内、1-3年、3年以上、本上报周期新增三年以上清单</el-button
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
  </div>
</template>

<script>
import {
  listCollection,
  getCollection,
  updateCollection,
} from "@/api/system/collection";
import userInfo from "@/store/modules/user";

export default {
  name: "Collection",
  data() {
    return {
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
    showButton(userName) {
      return userName == userInfo.state.name;
    },
    /** 查询应收账款列表 */
    getList() {
      this.loading = true;
      if (this.queryParams.ysKprqCs) {
        // 将日期对象转换为 yyyy-mm 格式的字符串
        const date = new Date(this.queryParams.ysKprqCs);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, "0"); // 月份从 0 开始
        const day = String(date.getDate()).padStart(2, "0"); //
        this.queryParams.ysKprqCs = `${year}-${month}-${day}`;
      }
      listCollection(this.queryParams).then((response) => {
        this.collectionList = response.rows;
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
    /** 导出按钮操作 */
    handleExport() {
      if (!this.queryParams.ysKprqCs) {
        this.$modal.msgError(`请选择截止日期`);
        return;
      }
      this.download(
        "system/collection/export",
        {
          ...this.queryParams,
        },
        "应收账款清单_" + this.queryParams.ysKprqCs + ".xlsx"
      );
    },
  },
};
</script>

<style>
.bold-label {
  font-weight: bold;
}
</style>
