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
      <el-form-item label="合同编号" prop="num">
        <el-input
          v-model="queryParams.num"
          placeholder="请输入合同编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合同名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入合同名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户名称" prop="khmc">
        <el-input
          v-model="queryParams.khmc"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="fzr">
        <el-input
          v-model="queryParams.fzr"
          placeholder="请输入负责人"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['system:contract:add']"
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
          v-hasPermi="['system:contract:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table :data="statisticsData" style="width: 100%">
      <el-table-column prop="status" label="类型" align="center">
        <template slot-scope="scope">
          <el-tag v-show="scope.row.status == 0" type="danger"
            >诉讼到期90天预警条数</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="cml" label="陈美玲" align="center">
        <template slot-scope="scope">
          <el-tag
            v-show="scope.row.status == 0"
            type="danger"
            class="hover-effect"
            >{{ scope.row.cml }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="wyy" label="王媛媛" align="center">
        <template slot-scope="scope">
          <el-tag
            v-show="scope.row.status == 0"
            type="danger"
            class="hover-effect"
            >{{ scope.row.wyy }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="cxy" label="陈晓钰" align="center">
        <template slot-scope="scope">
          <el-tag
            v-show="scope.row.status == 0"
            type="danger"
            class="hover-effect"
            >{{ scope.row.cxy }}</el-tag
          >
        </template>
      </el-table-column>
    </el-table>
    <el-table
      v-loading="loading"
      :data="contractList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="合同编号" align="center" prop="num" />
      <el-table-column label="合同名称" align="center" prop="name" />
      <el-table-column label="客户名称" align="center" prop="khmc" />
      <el-table-column label="客户分类" align="center" prop="classify" />
      <el-table-column label="负责人" align="center" prop="fzr" />
      <el-table-column label="合同金额" align="center" prop="htje" />
      <el-table-column label="签订日期" align="center" prop="qdrq" />
      <el-table-column label="结算金额" align="center" prop="jsje" />
      <el-table-column label="资金来源" align="center" prop="zjly" />
      <el-table-column label="业务属性" align="center" prop="ywsx" />
      <el-table-column label="已开票金额" align="center" prop="ykpje" />
      <el-table-column label="未开票金额" align="center" prop="wkpje" />
      <el-table-column label="已到账金额" align="center" prop="ydzje" />
      <el-table-column label="未到账金额" align="center" prop="wdzje" />
      <el-table-column
        label="合同约定支付时间"
        align="center"
        prop="ydsj"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ydsj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发函时间" align="center" prop="fhsj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.fhsj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="诉讼到期时间"
        align="center"
        prop="ssdqsj"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ssdqsj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="离诉讼到期天数" align="center" prop="day">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.day > 0">{{
            scope.row.day
          }}</el-tag>
          <el-tag type="danger" v-if="scope.row.day < 0">{{
            scope.row.day
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="财审情况" align="center" prop="csqk" />
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
            v-if="showButton(scope.row.fzr)"
            v-hasPermi="['system:contract:edit']"
            >修改</el-button
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

    <!-- 添加或修改合同管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="137px">
        <el-form-item label="合同编号" prop="num">
          <el-input v-model="form.num" placeholder="请输入合同编号" />
        </el-form-item>
        <el-form-item label="合同名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入合同名称" />
        </el-form-item>
        <el-form-item label="客户名称" prop="khmc">
          <el-input v-model="form.khmc" placeholder="请输入客户名称" />
        </el-form-item>
        <el-form-item label="客户分类" prop="classify">
          <el-input v-model="form.classify" placeholder="请输入客户分类" />
        </el-form-item>
        <el-form-item label="合同金额" prop="htje">
          <el-input v-model="form.htje" placeholder="请输入合同金额" />
        </el-form-item>
        <el-form-item label="签订日期" prop="qdrq">
          <el-input v-model="form.qdrq" placeholder="请输入签订日期" />
        </el-form-item>
        <el-form-item label="结算金额" prop="jsje">
          <el-input v-model="form.jsje" placeholder="请输入结算金额" />
        </el-form-item>
        <el-form-item label="资金来源" prop="zjly">
          <el-input v-model="form.zjly" placeholder="请输入资金来源" />
        </el-form-item>
        <el-form-item label="业务属性" prop="ywsx">
          <el-input v-model="form.ywsx" placeholder="请输入业务属性" />
        </el-form-item>
        <el-form-item label="已开票金额" prop="ykpje">
          <el-input v-model="form.ykpje" placeholder="请输入已开票金额" />
        </el-form-item>
        <el-form-item label="未开票金额" prop="wkpje">
          <el-input v-model="form.wkpje" placeholder="请输入未开票金额" />
        </el-form-item>
        <el-form-item label="已到账金额" prop="ydzje">
          <el-input v-model="form.ydzje" placeholder="请输入已到账金额" />
        </el-form-item>
        <el-form-item label="未到账金额" prop="wdzje">
          <el-input v-model="form.wdzje" placeholder="请输入未到账金额" />
        </el-form-item>
        <el-form-item label="合同约定支付时间" prop="ydsj">
          <el-date-picker
            clearable
            v-model="form.ydsj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择合同约定支付时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发函时间" prop="fhsj">
          <el-date-picker
            clearable
            v-model="form.fhsj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发函时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="诉讼到期时间" prop="ssdqsj">
          <el-date-picker
            clearable
            v-model="form.ssdqsj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择诉讼到期时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="财审情况" prop="csqk">
          <el-input v-model="form.csqk" placeholder="请输入财审情况" />
        </el-form-item>
        <el-form-item label="合同支付条款" prop="htzftk">
          <el-input
            v-model="form.htzftk"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input
            v-model="form.bz"
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
  </div>
</template>

<script>
import {
  listContract,
  getContract,
  delContract,
  addContract,
  updateContract,
} from "@/api/system/contract";
import userInfo from "@/store/modules/user";

export default {
  name: "Contract",
  data() {
    return {
      statisticsData: [],
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
      // 合同管理表格数据
      contractList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      queryStatisticsParams: {
        pageNum: 1,
        pageSize: 9999,
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        num: null,
        name: null,
        khmc: null,
        classify: null,
        fzr: null,
        htje: null,
        qdrq: null,
        jsje: null,
        zjly: null,
        ywsx: null,
        ykpje: null,
        wkpje: null,
        ydzje: null,
        wdzje: null,
        ydsj: null,
        fhsj: null,
        ssdqsj: null,
        csqk: null,
        htzftk: null,
        bz: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    this.getStatisticsData();
  },
  methods: {
    showButton(fzr) {
      console.log(userInfo.state);
      return fzr == userInfo.state.name;
    },
    getStatisticsData() {
      listContract(this.queryStatisticsParams).then((response) => {
        console.log(response.rows);
        var numData = {
          status: 0,
          cxy: 0,
          wyy: 0,
          cml: 0,
        };
        for (var i = 0; i < response.rows.length; i++) {
          if (
            response.rows[i].ssdqsj != null &&
            response.rows[i].day >= 0 &&
            response.rows[i].day <= 90
          ) {
            switch (response.rows[i].fzr) {
              case "陈美玲":
                numData.cml++;
                break;
              case "王媛媛":
                numData.wyy++;
                break;
              case "陈晓钰":
                numData.cxy++;
                break;
            }
          }
        }
        this.statisticsData.push(numData);
      });
    },
    /** 查询合同管理列表 */
    getList() {
      this.loading = true;
      listContract(this.queryParams).then((response) => {
        this.contractList = response.rows;
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
        conId: null,
        num: null,
        name: null,
        khmc: null,
        classify: null,
        fzr: null,
        htje: null,
        qdrq: null,
        jsje: null,
        zjly: null,
        ywsx: null,
        ykpje: null,
        wkpje: null,
        ydzje: null,
        wdzje: null,
        ydsj: null,
        fhsj: null,
        ssdqsj: null,
        csqk: null,
        htzftk: null,
        bz: null,
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
      this.ids = selection.map((item) => item.conId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加合同管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const conId = row.conId || this.ids;
      getContract(conId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改合同管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.conId != null) {
            updateContract(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addContract(this.form).then((response) => {
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
      const conIds = row.conId || this.ids;
      this.$modal
        .confirm('是否确认删除合同管理编号为"' + conIds + '"的数据项？')
        .then(function () {
          return delContract(conIds);
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
        "system/contract/export",
        {
          ...this.queryParams,
        },
        `合同管理(汇总)_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
