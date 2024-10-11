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
      <el-form-item label="项目名称" prop="zqXmmc">
        <el-input
          v-model="queryParams.zqXmmc"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合同编号" prop="zqHtbh">
        <el-input
          v-model="queryParams.zqHtbh"
          placeholder="请输入合同编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="甲方单位" prop="zqJfdw">
        <el-input
          v-model="queryParams.zqJfdw"
          placeholder="请输入甲方单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="市场专员" prop="zqZrr">
        <el-input
          v-model="queryParams.zqZrr"
          placeholder="请输入市场专员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上报时间" prop="zqTime">
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
          v-hasPermi="['system:responsibilities:add']"
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
          v-hasPermi="['system:responsibilities:export']"
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
      :data="responsibilitiesList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="项目名称" align="center" prop="zqXmmc" fixed>
        <template #default="scope">
          <el-tooltip
            class="item"
            effect="dark"
            :content="scope.row.zqXmmc"
            placement="top"
          >
            <span>
              {{
                scope.row.zqXmmc.length > 6
                  ? scope.row.zqXmmc.slice(0, 6) + "..."
                  : scope.row.zqXmmc
              }}
            </span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="合同编号" align="center" prop="zqHtbh" />
      <el-table-column label="甲方单位" align="center" prop="zqJfdw" />
      <el-table-column label="签订合同额(元)" align="center" prop="zqQdhtje" />
      <el-table-column
        label="审结后合同金额(元)"
        align="center"
        prop="zqSjhtje"
      />
      <el-table-column label="合同专业类别" align="center" prop="zqHtlb" />
      <el-table-column label="合同付款阶段" align="center" prop="zqHtjd" />
      <el-table-column label="合同可请款比例" align="center" prop="zqHtqkbl" />
      <el-table-column
        label="合同可开票金额(元)"
        align="center"
        prop="zqHtkkpje"
      />
      <el-table-column
        label="合同已开票金额(元)"
        align="center"
        prop="zqYkpje"
      />
      <el-table-column label="已进款金额(元)" align="center" prop="zqYjkje" />
      <el-table-column
        label="开票未进款金额(元)"
        align="center"
        prop="zqWjkje"
      />
      <el-table-column
        label="本次可开票金额(元)"
        align="center"
        prop="zqKkpje"
      />
      <el-table-column label="进度说明" align="center" prop="zqJdsm" />
      <el-table-column label="备注" align="center" prop="zqBz" />
      <el-table-column label="备注" align="center" prop="zqBzje" />
      <el-table-column label="市场专员" align="center" prop="zqZrr" />
      <el-table-column
        label="责权发生制上报时间"
        align="center"
        prop="zqTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.zqTime, "{y}-{m}-{d}") }}</span>
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
            @click="handleJiKp(scope.row)"
            v-hasPermi="['system:responsibilities:jlkp']"
            v-if="showButton(scope.row.zqZrr)"
            >新增开票</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:responsibilities:edit']"
            v-if="showButton(scope.row.zqZrr)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:responsibilities:remove']"
            v-if="showButton(scope.row.zqZrr)"
            >删除</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleKpList(scope.row)"
            >开票记录</el-button
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

    <!-- 添加或修改责权发生制对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1000px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="zqXmmc" label-width="200px">
          <el-input v-model="form.zqXmmc" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="合同编号" prop="zqHtbh" label-width="200px">
          <el-input v-model="form.zqHtbh" placeholder="请输入合同编号" />
        </el-form-item>
        <el-form-item label="甲方单位" prop="zqJfdw" label-width="200px">
          <el-input v-model="form.zqJfdw" placeholder="请输入甲方单位" />
        </el-form-item>
        <el-form-item label="签订合同额" prop="zqQdhtje" label-width="200px">
          <el-input-number
            v-model="form.zqQdhtje"
            :precision="2"
            :step="0.1"
          ></el-input-number>
        </el-form-item>
        <el-form-item
          label="审结后合同金额"
          prop="zqSjhtje"
          label-width="200px"
        >
          <el-input-number
            v-model="form.zqSjhtje"
            :precision="2"
            :step="0.1"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="合同专业类别" prop="zqHtlb" label-width="200px">
          <el-input v-model="form.zqHtlb" placeholder="请输入合同专业类别" />
        </el-form-item>
        <el-form-item label="合同付款阶段" prop="zqHtjd" label-width="200px">
          <el-input v-model="form.zqHtjd" placeholder="请输入合同付款阶段" />
        </el-form-item>
        <el-form-item
          label="合同可请款比例"
          prop="zqHtqkbl"
          label-width="200px"
        >
          <el-input
            v-model="form.zqHtqkbl"
            placeholder="请输入合同可请款比例"
          />
        </el-form-item>
        <el-form-item
          label="合同可开票金额"
          prop="zqHtkkpje"
          label-width="200px"
        >
          <el-input-number
            v-model="form.zqHtkkpje"
            :precision="2"
            :step="0.1"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="合同已开票金额" prop="zqYkpje" label-width="200px">
          <el-input-number
            v-model="form.zqYkpje"
            :precision="2"
            :step="0.1"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="已进款金额" prop="zqYjkje" label-width="200px">
          <el-input-number
            v-model="form.zqYjkje"
            :precision="2"
            :step="0.1"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="开票未进款金额" prop="zqWjkje" label-width="200px">
          <el-input-number
            v-model="form.zqWjkje"
            :precision="2"
            :step="0.1"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="本次可开票金额" prop="zqKkpje" label-width="200px">
          <el-input-number
            v-model="form.zqKkpje"
            :precision="2"
            :step="0.1"
            :disabled="zqKkpjeStatus"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="进度说明" prop="zqJdsm" label-width="200px">
          <el-input v-model="form.zqJdsm" placeholder="请输入进度说明" />
        </el-form-item>
        <el-form-item label="备注" prop="zqBz" label-width="200px">
          <el-input
            v-model="form.zqBz"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="备注" prop="zqBzje" label-width="200px">
          <el-input
            v-model="form.zqBzje"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item
          label="责权发生制上报时间"
          prop="zqTime"
          label-width="200px"
        >
          <el-date-picker
            clearable
            v-model="form.zqTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择责权发生制上报时间"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 新增开票信息 -->
    <el-dialog
      title="新增开票信息"
      :visible.sync="kpopen"
      width="500px"
      append-to-body
    >
      <el-form ref="kpform" :model="kpform" :rules="kprules" label-width="80px">
        <el-form-item label="开票金额" prop="infoMoney">
          <el-input-number
            v-model="kpform.infoMoney"
            :precision="2"
            :step="0.1"
          ></el-input-number>
        </el-form-item>

        <el-form-item label="开票日期" prop="infoTime">
          <el-date-picker
            clearable
            v-model="kpform.infoTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择责权发生制上报时间"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormKp">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 开票列表信息 -->
    <el-dialog
      title="开票记录"
      :visible.sync="kpjlopen"
      width="1000px"
      append-to-body
    >
      <el-table :data="infoList">
        <el-table-column label="项目名称" align="center" prop="infoXmmc" />
        <el-table-column label="合同编号" align="center" prop="infoHtbh" />
        <el-table-column label="甲方单位" align="center" prop="infoJfdw" />
        <el-table-column label="签订合同额" align="center" prop="infoQdhtje" />
        <el-table-column
          label="开票时间"
          align="center"
          prop="infoTime"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.infoTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column label="责权ID" align="center" prop="zqId" />
        <el-table-column label="开票金额" align="center" prop="infoMoney" />
        <el-table-column label="市场专员" align="center" prop="infoZrr" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import {
  listResponsibilities,
  getResponsibilities,
  delResponsibilities,
  addResponsibilities,
  updateResponsibilities,
  getResponsibilitiesInfo,
} from "@/api/system/responsibilities";
import { addInfo } from "@/api/system/responsibilitiesInfo";
import userInfo from "@/store/modules/user";

export default {
  name: "Responsibilities",
  data() {
    return {
      infoList: [],
      kpjlopen: false,
      kpform: {},
      kpopen: false,
      dateRange: [],
      zqKkpjeStatus: false,
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
      // 责权发生制表格数据
      responsibilitiesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        zqXmmc: null,
        zqHtbh: null,
        zqJfdw: null,
        zqQdhtje: null,
        zqSjhtje: null,
        zqHtlb: null,
        zqHtjd: null,
        zqHtqkbl: null,
        zqHtkkpje: null,
        zqYkpje: null,
        zqYjkje: null,
        zqWjkje: null,
        zqKkpje: null,
        zqJdsm: null,
        zqBz: null,
        zqZrr: null,
        zqTime: null,
        isDelete: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      kprules: {
        infoMoney: [
          { required: true, message: "请填写开票金额", trigger: "blur" },
        ],
        infoTime: [
          { required: true, message: "请选择开票日期", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleKpList(value) {
      getResponsibilitiesInfo(value.zqId).then((response) => {
        this.infoList = response.data;
        this.kpjlopen = true;
      });
    },
    handleJiKp(value) {
      this.kpform.zqId = value.zqId;
      this.kpform.infoXmmc = value.zqXmmc;
      this.kpform.infoHtbh = value.zqHtbh;
      this.kpform.infoJfdw = value.zqJfdw;
      this.kpform.infoQdhtje = value.zqQdhtje;
      this.kpform.infoZrr = value.zqZrr;
      this.kpopen = true;
    },
    showButton(userName) {
      return userName == userInfo.state.name;
    },
    /** 查询责权发生制列表 */
    getList() {
      this.loading = true;
      listResponsibilities(
        this.addDateRange(this.queryParams, this.dateRange)
      ).then((response) => {
        this.responsibilitiesList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.kpopen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.kpform = {
        zqId: null,
        infoMoney: null,
        infoTime: null,
      };
      this.form = {
        zqId: null,
        zqXmmc: null,
        zqHtbh: null,
        zqJfdw: null,
        zqQdhtje: null,
        zqSjhtje: null,
        zqHtlb: null,
        zqHtjd: null,
        zqHtqkbl: null,
        zqHtkkpje: null,
        zqYkpje: null,
        zqYjkje: null,
        zqWjkje: null,
        zqKkpje: null,
        zqJdsm: null,
        zqBz: null,
        zqZrr: null,
        zqTime: null,
        isDelete: null,
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
      this.ids = selection.map((item) => item.zqId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.zqKkpjeStatus = false;
      this.title = "添加责权发生制";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.zqKkpjeStatus = true;
      const zqId = row.zqId || this.ids;
      getResponsibilities(zqId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改责权发生制";
      });
    },
    submitFormKp() {
      this.$refs["kpform"].validate((valid) => {
        if (valid) {
          addInfo(this.kpform).then((response) => {
            this.$modal.msgSuccess("新增成功");
            this.kpopen = false;
            this.getList();
          });
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.zqId != null) {
            updateResponsibilities(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addResponsibilities(this.form).then((response) => {
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
      const zqIds = row.zqId || this.ids;
      this.$modal
        .confirm('是否确认删除责权发生制编号为"' + zqIds + '"的数据项？')
        .then(function () {
          return delResponsibilities(zqIds);
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
        "system/responsibilities/export",
        {
          ...this.queryParams,
        },
        `责权发生制清单_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
