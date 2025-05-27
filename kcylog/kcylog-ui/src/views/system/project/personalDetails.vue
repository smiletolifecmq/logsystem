<template>
  <div class="app-container">
    <el-collapse v-model="activeNames">
      <el-collapse-item title="汇总统计" name="1">
        <el-form
          :model="queryParamsHz"
          ref="queryFormHz"
          size="mini"
          :inline="true"
          label-width="68px"
        >
          <el-form-item label="时间段">
            <el-date-picker
              v-model="dateRangeHz"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQueryHz"
              >搜索</el-button
            >
            <el-button icon="el-icon-refresh" size="mini" @click="resetQueryHz"
              >重置</el-button
            >
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExportHz"
              v-hasPermi="['system:hz:export']"
              >导出</el-button
            >
          </el-form-item>
        </el-form>
        <el-table v-loading="loading" :data="hztjData">
          <el-table-column
            label="项目管理网站流程\部门"
            align="center"
            prop="xmglwz"
          />
          <el-table-column label="工程测绘部" align="center" prop="gcchb" />
          <el-table-column label="管线工程部" align="center" prop="gxgcb" />
          <el-table-column label="不动产测绘部" align="center" prop="bdcchb" />
          <el-table-column label="地理信息部" align="center" prop="dlxxb" />
          <el-table-column label="合计" align="center" prop="hj" />
        </el-table>
      </el-collapse-item>
      <el-collapse-item :title="tjtime" name="2">
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
import { listProjectOperateValue, listProjectHj } from "@/api/system/project";

export default {
  name: "Project",

  data() {
    return {
      hztjData: [],
      queryParamsHz: {},
      tjtime: "",
      activeNames: ["1", "2"],
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

      dateRangeHz: [],

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
    this.getHJ();
  },
  methods: {
    handleQueryHz() {
      this.getHJ();
    },
    getHJ() {
      listProjectHj(
        this.addDateRange(this.queryParamsHz, this.dateRangeHz)
      ).then((response) => {
        this.hztjData = response.rows;
      });
    },
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

    resetQueryHz() {
      this.dateRangeHz = [];
      this.resetForm("queryFormHz");
      this.getHJ();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.projectId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleExportHz() {
      this.download(
        "system/project/exporTj",
        this.addDateRange(this.queryParamsHz, this.dateRangeHz),
        `福清分院项目管理网站月周报_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
