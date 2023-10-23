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
      <el-form-item label="日期" prop="exportDate">
        <el-date-picker
          v-model="exportDate"
          type="monthrange"
          range-separator="至"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <el-cascader
          v-model="queryParamsDeptId"
          :options="deptOptions"
          @change="handleChangeDept"
          clearable
        ></el-cascader>
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleEmployeeExport(1)"
          >签领表导出</el-button
        >
        <el-button
          type="success"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleEmployeeExport(2)"
          >外包申请表导出</el-button
        >
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleEmployeeExport(3)"
          >按部门导出</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { deptTreeNotPer } from "@/api/system/log";
export default {
  name: "Settlement",
  data() {
    return {
      exportDate: "",
      // 显示搜索条件
      showSearch: true,
      queryParamsDeptId: [],
      // 查询参数
      queryParams: {
        startTimeYear: "",
        startTimeMonth: "",
        endTimeYear: "",
        endTimeMonth: "",
        deptId: null,
      },
      deptOptions: undefined,
    };
  },
  created() {
    this.getDeptTree();
  },
  methods: {
    handleChangeDept(value) {
      this.queryParams.deptId = value[1];
    },
    getDeptTree() {
      deptTreeNotPer().then((response) => {
        this.deptOptions = transformIdToValue(response.data);
      });
    },
    resetQuery() {
      this.queryParamsDeptId = [];
      this.exportDate = "";
      this.queryParams.startTimeYear = "";
      this.queryParams.startTimeMonth = "";
      this.queryParams.endTimeYear = "";
      this.queryParams.endTimeMonth = "";
      this.queryParams.deptId = null;
      this.resetForm("queryForm");
    },
    handleEmployeeExport(value) {
      if (this.exportDate == "" || this.queryParamsDeptId.length == 0) {
        this.$message({
          message: "日期与部门为必选项～",
          type: "warning",
        });
        return;
      }

      this.queryParams.startTimeYear = this.exportDate[0].getFullYear();
      this.queryParams.startTimeMonth = (this.exportDate[0].getMonth() + 1)
        .toString()
        .padStart(2, "0");

      this.queryParams.endTimeYear = this.exportDate[1].getFullYear();
      this.queryParams.endTimeMonth = (this.exportDate[1].getMonth() + 1)
        .toString()
        .padStart(2, "0");

      this.$confirm(
        "由于导出相关数据后,已导出的数据将被锁定不可修改,请仔细确认导出的筛选条件,是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          switch (value) {
            case 1:
              this.download(
                "system/reviewEmployee/exportMonth",
                { ...this.queryParams },
                `劳务费签领表_${new Date().getTime()}.xlsx`
              );
              break;
            case 2:
              this.download(
                "system/reviewEmployee/outsourcingExportMonth",
                { ...this.queryParams },
                `外包申请表_${new Date().getTime()}.xlsx`
              );
              break;
            case 3:
              this.download(
                "system/review/exportMonth",
                { ...this.queryParams },
                `部门外包_${new Date().getTime()}.xlsx`
              );
              break;
            default:
          }
        })
        .catch(() => {});
    },
  },
};
function transformIdToValue(obj) {
  if (Array.isArray(obj)) {
    return obj.map((item) => transformIdToValue(item));
  } else if (typeof obj === "object" && obj !== null) {
    const newObj = {};
    for (let key in obj) {
      if (key === "id") {
        newObj.value = obj[key];
      } else {
        newObj[key] = transformIdToValue(obj[key]);
      }
    }
    return newObj;
  } else {
    return obj;
  }
}
</script>
