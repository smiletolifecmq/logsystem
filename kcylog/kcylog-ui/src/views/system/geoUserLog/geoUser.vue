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
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
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
          v-hasPermi="['system:user:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
          >展开/折叠</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="userList"
      row-key="geoUserId"
      :default-expand-all="isExpandAll"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="是否为检查人员" align="center" prop="isCheck">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isCheck === 0" type="info">否</el-tag>
          <el-tag v-else-if="scope.row.isCheck === 1">是</el-tag>
          <el-tag v-else type="warning">未知</el-tag>
        </template>
      </el-table-column>

      <el-table-column
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
            v-hasPermi="['system:user:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:user:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改地理用户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24" v-if="form.parentId !== 0">
            <el-form-item label="上级人员" prop="parentId">
              <treeselect
                v-model="form.parentId"
                :options="deptOptions"
                :normalizer="normalizer"
                placeholder="选择上级人员"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="用户名称" prop="userId">
              <el-select
                v-model="form.userId"
                placeholder="请选择"
                :disabled="editDisabled"
              >
                <el-option
                  v-for="item in userListAll"
                  :key="item.userId"
                  :label="item.userName"
                  :value="item.userId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="检查状态">
              <el-radio v-model="form.isCheck" :label="0">否</el-radio>
              <el-radio v-model="form.isCheck" :label="1">是</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
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
  listGeoUser,
  getGeoUser,
  delGeoUser,
  addGeoUser,
  updateGeoUser,
  listDeptExcludeChild,
} from "@/api/system/geoUser";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { listUser } from "@/api/system/user";

export default {
  name: "User",
  components: { Treeselect },
  data() {
    return {
      editDisabled: false,
      dateRange: [],
      userListAll: [],
      deptOptions: [],
      isExpandAll: false,
      refreshTable: true,
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
      // 地理用户表格数据
      userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 9999,
        parentId: null,
        userId: null,
        ancestors: null,
        userName: null,
        isCheck: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户ID不能为空", trigger: "blur" },
        ],
        isCheck: [
          {
            required: true,
            message: "是否为检查人员，0否1是不能为空",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    listUser(this.addDateRange(this.queryParams, this.dateRange)).then(
      (response) => {
        this.userListAll = response.rows;
      }
    );
    this.getList();
  },
  methods: {
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.userId,
        label: node.userName,
        children: node.children,
      };
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 查询地理用户列表 */
    getList() {
      this.loading = true;
      listGeoUser(this.queryParams).then((response) => {
        this.userList = this.handleTree(response.rows, "userId");
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.editDisabled = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        geoUserId: null,
        parentId: null,
        userId: null,
        ancestors: null,
        userName: null,
        isCheck: null,
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
      this.ids = selection.map((item) => item.geoUserId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      if (row != undefined) {
        this.form.parentId = row.deptId;
      }
      this.open = true;
      this.title = "添加地理用户";
      listGeoUser().then((response) => {
        this.deptOptions = this.handleTree(response.rows, "userId");
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.editDisabled = true;
      this.reset();
      const geoUserId = row.geoUserId || this.ids;
      getGeoUser(geoUserId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改地理用户";
        listDeptExcludeChild(row.userId).then((response) => {
          this.deptOptions = this.handleTree(response.data, "userId");
          if (this.deptOptions.length == 0) {
            const noResultsOptions = {
              userId: this.form.parentId,
              userName: this.form.userName,
              children: [],
            };
            this.deptOptions.push(noResultsOptions);
          }
        });
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.geoUserId != null) {
            updateGeoUser(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGeoUser(this.form).then((response) => {
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
      const geoUserIds = row.geoUserId || this.ids;
      this.$modal
        .confirm('是否确认删除地理用户编号为"' + geoUserIds + '"的数据项？')
        .then(function () {
          return delGeoUser(geoUserIds);
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
        "system/user/export",
        {
          ...this.queryParams,
        },
        `user_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
