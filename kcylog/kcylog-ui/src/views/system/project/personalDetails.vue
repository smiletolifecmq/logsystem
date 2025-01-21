<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="mini"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="人员名称" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入人员名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作业部门" prop="department">
        <el-select
          v-model="queryParams.department"
          placeholder="请选择部门"
          clearable
        >
          <el-option
            v-for="item in deptList"
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

    <el-collapse v-model="activeNames">
      <el-collapse-item :title="tjtime" name="1">
        <el-table v-loading="loading" :data="projectList">
          <el-table-column label="人员名称" align="center" prop="userName" />
          <el-table-column
            label="经营产值(系数不参与计算)"
            align="center"
            prop="money"
          />
          <el-table-column
            label="经营产值(系数参与计算)"
            align="center"
            prop="coefficientMoney"
          />
          <el-table-column
            label="利润(系数不参与计算)"
            align="center"
            prop="profitMoney"
          />
          <el-table-column
            label="利润(系数参与计算)"
            align="center"
            prop="coefficientProfitMoney"
          />
          <el-table-column
            label="完成项目数"
            align="center"
            prop="projectNum"
          />
          <el-table-column label="分包项目数" align="center" prop="fbNum" />
        </el-table>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>
<style>
.hover-effect:hover {
  cursor: pointer;
}
</style>
<script>
import { listProjectOperateValue } from "@/api/system/project";

export default {
  name: "Project",

  data() {
    return {
      tjtime: "",
      activeNames: ["1"],
      deptList: [
        {
          value: "地理信息部",
          label: "地理信息部",
        },
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
          value: "测绘工程一部",
          label: "测绘工程一部",
        },
        {
          value: "测绘工程二部",
          label: "测绘工程二部",
        },
        {
          value: "测绘工程三部",
          label: "测绘工程三部",
        },
        {
          value: "测绘工程一部1组",
          label: "测绘工程一部1组",
        },
        {
          value: "测绘工程一部2组",
          label: "测绘工程一部2组",
        },
        {
          value: "测绘工程二部1组",
          label: "测绘工程二部1组",
        },
        {
          value: "测绘工程二部2组",
          label: "测绘工程二部2组",
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
      // 项目表格数据
      projectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        department: null,
      },
      // 表单参数
      form: {},

      // 表单校验
    };
  },
  created() {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = String(currentDate.getMonth() + 1).padStart(2, "0"); // 获取当前月份，注意月份从0开始，所以加1
    const currentMonth = `${year}-${month}`;
    this.tjtime = "统计月份为：" + currentMonth;
    this.getList();
  },
  methods: {
    /** 查询项目列表 */
    getList() {
      this.loading = true;
      listProjectOperateValue(
        this.addDateRange(this.queryParams, this.dateRange)
      ).then((response) => {
        this.projectList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
      this.ids = selection.map((item) => item.projectId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
  },
};
</script>
