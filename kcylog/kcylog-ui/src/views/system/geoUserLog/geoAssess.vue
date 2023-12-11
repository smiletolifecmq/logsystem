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
      <el-form-item label="评定日期" prop="assessDate">
        <el-date-picker
          clearable
          v-model="queryParams.assessDate"
          type="month"
          value-format="yyyy-MM"
          placeholder="请选择评定日期"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:assess:add']"
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
      :data="assessList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        label="评定日期"
        align="center"
        prop="assessDate"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.assessDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="评定人名称"
        align="center"
        prop="assessUserName"
      />
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
            v-hasPermi="['system:assess:edit']"
            v-if="showButton(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-if="showButton(scope.row)"
            v-hasPermi="['system:assess:remove']"
            >删除</el-button
          >

          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
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

    <!-- 添加或修改评定表对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1200px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="评定日期" prop="assessDate">
          <el-date-picker
            clearable
            v-model="form.assessDate"
            type="month"
            value-format="yyyy-MM"
            placeholder="请选择评定日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-collapse v-model="activeNames">
          <el-collapse-item title="用户列表" name="1">
            <el-form-item
              v-for="(geoAssess, index) in form.geoAssessInfo"
              :key="index"
              prop="geoAssessInfo"
            >
              <el-row>
                <el-col :span="5" style="margin-left: -80px">
                  <el-form-item label="用户名" prop="userName">
                    <el-input
                      v-model="geoAssess.userName"
                      placeholder="请输入用户名"
                      disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="配合系数" prop="fitCoefficient">
                    <el-input-number
                      v-model="geoAssess.fitCoefficient"
                      :precision="2"
                      :step="0.1"
                      :min="0"
                      :controls="false"
                      size="small"
                    ></el-input-number>
                  </el-form-item>
                </el-col>
                <el-col :span="7">
                  <el-form-item
                    label="质量系数"
                    prop="workCoefficient"
                    size="small"
                  >
                    <el-input-number
                      v-model="geoAssess.workCoefficient"
                      :precision="2"
                      :step="0.1"
                      :min="0"
                      :controls="false"
                    ></el-input-number>
                  </el-form-item>
                </el-col>
                <el-col :span="10" style="margin-left: -100px">
                  <el-form-item label="备注" prop="remark">
                    <el-input
                      v-model="geoAssess.remark"
                      placeholder="请输入备注"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form-item>
          </el-collapse-item>
        </el-collapse>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog
      title="详情"
      :visible.sync="detailOpen"
      width="1200px"
      append-to-body
    >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="评定日期" prop="assessDate">
          <el-date-picker
            clearable
            v-model="form.assessDate"
            type="month"
            value-format="yyyy-MM"
            placeholder="请选择评定日期"
            disabled
          >
          </el-date-picker>
        </el-form-item>
        <el-collapse v-model="activeNames">
          <el-collapse-item title="用户列表" name="1">
            <el-form-item
              v-for="(geoAssess, index) in form.geoAssessInfo"
              :key="index"
              prop="geoAssessInfo"
            >
              <el-row>
                <el-col :span="5" style="margin-left: -80px">
                  <el-form-item label="用户名" prop="userName">
                    <el-input
                      v-model="geoAssess.userName"
                      placeholder="请输入用户名"
                      disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="配合系数" prop="fitCoefficient">
                    <el-input-number
                      v-model="geoAssess.fitCoefficient"
                      :precision="2"
                      :step="0.1"
                      :min="0"
                      :controls="false"
                      size="small"
                      disabled
                    ></el-input-number>
                  </el-form-item>
                </el-col>
                <el-col :span="7">
                  <el-form-item
                    label="质量系数"
                    prop="workCoefficient"
                    size="small"
                  >
                    <el-input-number
                      v-model="geoAssess.workCoefficient"
                      :precision="2"
                      :step="0.1"
                      :min="0"
                      :controls="false"
                      disabled
                    ></el-input-number>
                  </el-form-item>
                </el-col>
                <el-col :span="10" style="margin-left: -100px">
                  <el-form-item label="备注" prop="remark">
                    <el-input
                      v-model="geoAssess.remark"
                      placeholder="请输入备注"
                      disabled
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form-item>
          </el-collapse-item>
        </el-collapse>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {
  listAssess,
  getAssess,
  delAssess,
  addAssess,
  updateAssess,
  listAssessUser,
} from "@/api/system/geoAssess";
import userInfo from "@/store/modules/user";

export default {
  name: "Assess",
  data() {
    return {
      detailOpen: false,
      activeNames: ["1"],
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
      // 评定表表格数据
      assessList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        assessDate: null,
        assessUserId: null,
        assessUserName: null,
      },
      // 表单参数
      form: {
        geoAssessInfo: [
          {
            remork: "",
          },
        ],
      },
      // 表单校验
      rules: {
        assessDate: [
          { required: true, message: "日期不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    showButton(assess) {
      return assess.assessUserId == userInfo.state.userId;
    },
    /** 查询评定表列表 */
    getList() {
      this.loading = true;
      listAssess(this.queryParams).then((response) => {
        this.assessList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      this.form = {
        geoAssessInfo: [
          {
            remork: "",
          },
        ],
      };
    },
    // 表单重置
    reset() {
      this.form = {
        assessId: null,
        assessDate: null,
        assessUserId: null,
        assessUserName: null,
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
      this.ids = selection.map((item) => item.assessId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      //   this.reset();
      listAssessUser().then((response) => {
        this.form.geoAssessInfo = response.rows;
        this.open = true;
        this.title = "添加评定表";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const assessId = row.assessId || this.ids;
      getAssess(assessId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改评定表";
      });
    },

    /** 详情 */
    handleDetail(row) {
      this.reset();
      const assessId = row.assessId || this.ids;
      getAssess(assessId).then((response) => {
        this.form = response.data;
        this.detailOpen = true;
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.assessId != null) {
            updateAssess(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAssess(this.form).then((response) => {
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
      const assessIds = row.assessId || this.ids;
      this.$modal
        .confirm('是否确认删除评定表编号为"' + assessIds + '"的数据项？')
        .then(function () {
          return delAssess(assessIds);
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
        "system/assess/export",
        {
          ...this.queryParams,
        },
        `assess_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
