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
      <el-form-item label="申请时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
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
      :data="infoList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="项目编号" align="center" prop="projectCode" />
      <el-table-column label="项目类型" align="center" prop="projectTypeName" />
      <el-table-column label="分包合同号" align="center" prop="firmName" />
      <el-table-column label="分包金额" align="center" prop="settleMoney" />
      <el-table-column label="中签单位" align="center" prop="subcontractNo" />
      <el-table-column label="申请时间" align="center" prop="createTime">
        <template slot-scope="scope">{{
          formatDate(scope.row.createTime)
        }}</template>
      </el-table-column>
      <!-- <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:info:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:info:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column> -->
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改分包明细表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目ID" prop="projectId">
          <el-input v-model="form.projectId" placeholder="请输入项目ID" />
        </el-form-item>
        <el-form-item label="项目编号" prop="projectCode">
          <el-input v-model="form.projectCode" placeholder="请输入项目编号" />
        </el-form-item>
        <el-form-item label="分包合同" prop="firmName">
          <el-input v-model="form.firmName" placeholder="请输入分包合同" />
        </el-form-item>
        <el-form-item label="中签单位" prop="subcontractNo">
          <el-input v-model="form.subcontractNo" placeholder="请输入中签单位" />
        </el-form-item>
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目类型" prop="projectTypeName">
          <el-input
            v-model="form.projectTypeName"
            placeholder="请输入项目类型"
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
  listInfo,
  getInfo,
  delInfo,
  addInfo,
  updateInfo,
} from "@/api/system/chargeInfo";
import { exportSubDocx } from "@/utils/doc.js";

export default {
  name: "Info",
  data() {
    return {
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
      // 分包明细表表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectId: null,
        projectCode: null,
        settleMoney: null,
        firmName: null,
        subcontractNo: null,
        projectName: null,
        projectTypeName: null,
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
    formatDate(dateString) {
      if (dateString == "" || dateString == null || dateString == undefined) {
        return "";
      }
      const dateObject = new Date(dateString);
      const year = dateObject.getFullYear();
      const month = dateObject.getMonth() + 1;
      const day = dateObject.getDate();
      return `${year}-${(month < 10 ? "0" : "") + month}-${
        (day < 10 ? "0" : "") + day
      }`;
    },
    /** 查询分包明细表列表 */
    getList() {
      this.loading = true;
      listInfo(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.infoList = response.rows;
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
        id: null,
        projectId: null,
        projectCode: null,
        settleMoney: null,
        firmName: null,
        subcontractNo: null,
        createTime: null,
        projectName: null,
        projectTypeName: null,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加分包明细表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getInfo(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改分包明细表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then((response) => {
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
        .confirm('是否确认删除分包明细表编号为"' + ids + '"的数据项？')
        .then(function () {
          return delInfo(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      if (this.dateRange.length == 0) {
        this.$message({
          type: "error",
          message: "请选择申请时间～",
        });
        return;
      }
      listInfo(
        this.addDateRange(
          {
            pageNum: 1,
            pageSize: 9999,
          },
          this.dateRange
        )
      ).then((response) => {
        const list = response.rows;
        const data = {
          form: {
            createTime: getOtherDate(this.dateRange[0]),
            exportTime: getNowDate(),
            startTime: getNowDate(),
            endTime: getTwoNowDate(),
          },
          list: [],
        };
        for (var i = 0; i < list.length; i++) {
          data.list.push({
            num: i + 1,
            projectCode: list[i].projectCode,
            projectName: list[i].projectName,
            projectTypeName: list[i].projectTypeName ?? "",
            subcontractNo: list[i].subcontractNo,
          });
        }
        exportSubDocx(
          "/sub_bc_template.docx",
          data,
          "福清分公司分包公示表.docx"
        );
      });
    },
  },
};

function getNowDate() {
  // 获取当前日期
  var date = new Date();

  // 获取当前月份
  var nowMonth = date.getMonth() + 1;

  // 获取当前是几号
  var strDate = date.getDate();

  // 最后拼接字符串，得到一个格式为(yyyy-MM-dd)的日期
  var nowDate = date.getFullYear() + "年" + nowMonth + "月" + strDate + "日";
  return nowDate;
}

function getOtherDate(value) {
  var inputDateStr = value;

  // 将字符串转换为Date对象
  var inputDate = new Date(inputDateStr);

  // 获取年、月、日
  var year = inputDate.getFullYear();
  var month = inputDate.getMonth() + 1;
  var day = inputDate.getDate();

  // 格式化输出
  var formattedDate = year + "年" + month + "月" + day + "日";
  return formattedDate;
}

function getTwoNowDate() {
  // 获取当前时间
  var currentDate = new Date();

  // 添加2天的时间
  currentDate.setDate(currentDate.getDate() + 2);

  // 获取年、月、日
  var year = currentDate.getFullYear();
  var month = currentDate.getMonth() + 1;
  var day = currentDate.getDate();

  // 格式化输出
  var formattedDate = year + "年" + month + "月" + day + "日";

  return formattedDate;
}
</script>
