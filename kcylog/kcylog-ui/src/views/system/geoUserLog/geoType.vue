<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:type:add']"
          >新增</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="typeList"
      row-key="typeId"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column label="类型名称" align="center" prop="typeName" />
      <el-table-column label="单价" align="center" prop="typeMoney" />
      <el-table-column label="单位" align="center" prop="unit" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="是否有难易程度" align="center" prop="degree">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.degree === 0" type="info">否</el-tag>
          <el-tag v-else-if="scope.row.degree === 1">是</el-tag>
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
            v-hasPermi="['system:type:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:type:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改类型配置表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="上级类型" prop="parentId">
          <treeselect
            v-model="form.parentId"
            :options="menuOptions"
            :normalizer="normalizer"
            :show-count="true"
            placeholder="选择上级菜单"
          />
        </el-form-item>
        <el-form-item label="类型名称" prop="typeName">
          <el-input v-model="form.typeName" placeholder="请输入类型名称" />
        </el-form-item>
        <el-form-item label="单价" prop="typeMoney">
          <el-input v-model="form.typeMoney" placeholder="请输入单价" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="难易程度">
          <el-radio v-model="form.degree" :label="0">否</el-radio>
          <el-radio v-model="form.degree" :label="1">是</el-radio>
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
  listType,
  getType,
  delType,
  addType,
  updateType,
} from "@/api/system/geoType";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Type",
  components: { Treeselect },
  data() {
    return {
      menuOptions: [],
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
      // 类型配置表表格数据
      typeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 9999,
        typeName: null,
        typeMoney: null,
        parentId: null,
        unit: null,
        degree: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        typeName: [
          { required: true, message: "类型名称不能为空", trigger: "blur" },
        ],
        parentId: [
          { required: true, message: "父ID不能为空", trigger: "blur" },
        ],
        degree: [
          {
            required: true,
            message: "是否有难易程度，0否1是不能为空",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.typeId,
        label: node.typeName,
        children: node.children,
      };
    },
    getTreeselect() {
      listType(this.queryParams).then((response) => {
        this.menuOptions = [];
        const menu = { typeId: 0, menuName: "主类型", children: [] };
        this.menuOptions = this.handleTree(response.rows, "typeId");
        this.menuOptions.push(menu);
      });
    },
    /** 查询类型配置表列表 */
    getList() {
      this.loading = true;
      listType(this.queryParams).then((response) => {
        this.typeList = this.handleTree(response.rows, "typeId");
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
        typeId: null,
        typeName: null,
        typeMoney: null,
        parentId: null,
        remark: null,
        unit: null,
        degree: null,
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
      this.ids = selection.map((item) => item.typeId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.typeId) {
        this.form.parentId = row.typeId;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加类型配置表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const typeId = row.typeId || this.ids;
      getType(typeId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改类型配置表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.typeId != null) {
            updateType(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addType(this.form).then((response) => {
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
      const typeIds = row.typeId || this.ids;
      this.$modal
        .confirm('是否确认删除类型配置表编号为"' + typeIds + '"的数据项？')
        .then(function () {
          return delType(typeIds);
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
        "system/type/export",
        {
          ...this.queryParams,
        },
        `type_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
