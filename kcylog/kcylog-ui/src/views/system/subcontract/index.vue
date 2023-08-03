<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工程编号" prop="serialNum">
        <el-input
          v-model="queryParams.serialNum"
          placeholder="请输入工程编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="抽签时间" prop="lotTime">
        <el-date-picker clearable
          v-model="queryParams.lotTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择抽签时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务名称" prop="businessName">
        <el-input
          v-model="queryParams.businessName"
          placeholder="请输入业务名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中签单位" prop="winUnit">
        <el-input
          v-model="queryParams.winUnit"
          placeholder="请输入中签单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工期开始时间" prop="startTime">
        <el-date-picker clearable
          v-model="queryParams.startTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择工期开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="工期结束时间" prop="endTime">
        <el-date-picker clearable
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择工期结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
          v-hasPermi="['system:subcontract:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:subcontract:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:subcontract:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:subcontract:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="subcontractList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="分包ID" align="center" prop="subcontractId" />
      <el-table-column label="工程编号" align="center" prop="serialNum" />
      <el-table-column label="抽签时间" align="center" prop="lotTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lotTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="业务名称" align="center" prop="businessName" />
      <el-table-column label="协作单位" align="center" prop="cooperationUnit" />
      <el-table-column label="中签单位" align="center" prop="winUnit" />
      <el-table-column label="委托单位" align="center" prop="entrustUnit" />
      <el-table-column label="工作量" align="center" prop="workload" />
      <el-table-column label="工作内容" align="center" prop="workcontent" />
      <el-table-column label="用户ID(负责人)" align="center" prop="userId" />
      <el-table-column label="部门ID" align="center" prop="deptId" />
      <el-table-column label="审核状态(0:未开始;1进行中;2通过;3:未通过)" align="center" prop="status" />
      <el-table-column label="工期开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="工期结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:subcontract:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:subcontract:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改分包对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="工程编号" prop="serialNum">
          <el-input v-model="form.serialNum" placeholder="请输入工程编号" />
        </el-form-item>
        <el-form-item label="抽签时间" prop="lotTime">
          <el-date-picker clearable
            v-model="form.lotTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择抽签时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="业务名称" prop="businessName">
          <el-input v-model="form.businessName" placeholder="请输入业务名称" />
        </el-form-item>
        <el-form-item label="协作单位" prop="cooperationUnit">
          <el-input v-model="form.cooperationUnit" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="中签单位" prop="winUnit">
          <el-input v-model="form.winUnit" placeholder="请输入中签单位" />
        </el-form-item>
        <el-form-item label="委托单位" prop="entrustUnit">
          <el-input v-model="form.entrustUnit" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="工作量" prop="workload">
          <el-input v-model="form.workload" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="工作内容">
          <editor v-model="form.workcontent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="工期开始时间" prop="startTime">
          <el-date-picker clearable
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择工期开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="工期结束时间" prop="endTime">
          <el-date-picker clearable
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择工期结束时间">
          </el-date-picker>
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
import { listSubcontract, getSubcontract, delSubcontract, addSubcontract, updateSubcontract } from "@/api/system/subcontract";

export default {
  name: "Subcontract",
  data() {
    return {
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
      // 分包表格数据
      subcontractList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNum: null,
        lotTime: null,
        projectName: null,
        businessName: null,
        cooperationUnit: null,
        winUnit: null,
        entrustUnit: null,
        workload: null,
        workcontent: null,
        userId: null,
        deptId: null,
        status: null,
        startTime: null,
        endTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        serialNum: [
          { required: true, message: "工程编号不能为空", trigger: "blur" }
        ],
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        businessName: [
          { required: true, message: "业务名称不能为空", trigger: "blur" }
        ],
        winUnit: [
          { required: true, message: "中签单位不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "用户ID(负责人)不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "部门ID不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询分包列表 */
    getList() {
      this.loading = true;
      listSubcontract(this.queryParams).then(response => {
        this.subcontractList = response.rows;
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
        subcontractId: null,
        serialNum: null,
        lotTime: null,
        projectName: null,
        businessName: null,
        cooperationUnit: null,
        winUnit: null,
        entrustUnit: null,
        workload: null,
        workcontent: null,
        userId: null,
        deptId: null,
        status: null,
        startTime: null,
        endTime: null,
        createTime: null,
        updateTime: null
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
      this.ids = selection.map(item => item.subcontractId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加分包";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const subcontractId = row.subcontractId || this.ids
      getSubcontract(subcontractId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改分包";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.subcontractId != null) {
            updateSubcontract(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSubcontract(this.form).then(response => {
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
      const subcontractIds = row.subcontractId || this.ids;
      this.$modal.confirm('是否确认删除分包编号为"' + subcontractIds + '"的数据项？').then(function() {
        return delSubcontract(subcontractIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/subcontract/export', {
        ...this.queryParams
      }, `subcontract_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
