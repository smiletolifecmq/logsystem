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
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位" prop="unit">
        <el-input
          v-model="queryParams.unit"
          placeholder="请输入单位"
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
          v-hasPermi="['system:clientele:add']"
          >录入</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="clienteleList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="个人专长" align="center" prop="expertise" />
      <el-table-column label="兴趣爱好" align="center" prop="hobbies" />
      <el-table-column label="出生" align="center" prop="born" />
      <el-table-column label="是否吸烟" align="center" prop="smoke">
        <template slot-scope="{ row }">
          {{ row.smoke === 1 ? "是" : "否" }}
        </template>
      </el-table-column>
      <el-table-column label="酒量" align="center" prop="capacity" />
      <el-table-column label="毕业学校" align="center" prop="school" />
      <el-table-column label="学历" align="center" prop="education" />
      <el-table-column label="身高(米)" align="center" prop="height" />
      <el-table-column label="录入人" align="center" prop="createname" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:clientele:query']"
            >详情</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-if="showButton(scope.row.createid)"
            v-hasPermi="['system:clientele:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-if="showButton(scope.row.createid)"
            v-hasPermi="['system:clientele:remove']"
            >删除</el-button
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

    <!-- 添加或修改客户信息对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1000px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-collapse v-model="activeNames">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <div>
            <el-row :gutter="10">
              <el-col style="width: 100%">
                <el-collapse-item title="基本信息" name="1">
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="照片" prop="img">
                        <FileUpload
                          ref="fileUploadModule"
                          :fileSize="200"
                          :fileType="fileType"
                          :limit="1"
                          :showEncryption="true"
                        ></FileUpload>
                      </el-form-item>
                    </el-col>
                  </el-row>

                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="姓名" prop="name">
                        <el-input
                          v-model="form.name"
                          placeholder="请输入姓名"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="个人专长" prop="expertise">
                        <el-input
                          v-model="form.expertise"
                          placeholder="请输入个人专长"
                        />
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="兴趣爱好" prop="hobbies">
                        <el-input
                          v-model="form.hobbies"
                          placeholder="请输入兴趣爱好"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="出生" prop="born">
                        <el-input
                          v-model="form.born"
                          placeholder="请输入出生"
                        />
                      </el-form-item>
                    </el-col>
                  </el-row>

                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="是否吸烟" prop="smoke">
                        <el-radio-group v-model="form.smoke">
                          <el-radio :label="1">是</el-radio>
                          <el-radio :label="0">否</el-radio>
                        </el-radio-group>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="酒量" prop="capacity">
                        <el-input
                          v-model="form.capacity"
                          placeholder="请输入酒量"
                        /> </el-form-item
                    ></el-col>
                  </el-row>

                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="毕业学校" prop="school">
                        <el-input
                          v-model="form.school"
                          placeholder="请输入毕业学校"
                        /> </el-form-item
                    ></el-col>
                    <el-col :span="12">
                      <el-form-item label="学历" prop="education">
                        <el-input
                          v-model="form.education"
                          placeholder="请输入学历"
                        /> </el-form-item
                    ></el-col>
                  </el-row>

                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="身高(米)" prop="height">
                        <el-input
                          v-model="form.height"
                          placeholder="请输入身高，单位米"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="职称" prop="jobtitle">
                        <el-input
                          v-model="form.jobtitle"
                          placeholder="请输入职称"
                        /> </el-form-item
                    ></el-col>
                  </el-row>

                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="单位" prop="unit">
                        <el-input
                          v-model="form.unit"
                          placeholder="请输入单位"
                        /> </el-form-item
                    ></el-col>
                    <el-col :span="12"
                      ><el-form-item label="职位" prop="position">
                        <el-input
                          v-model="form.position"
                          placeholder="请输入职位"
                        />
                      </el-form-item>
                    </el-col>
                  </el-row>

                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="家庭住址" prop="address">
                        <el-input
                          v-model="form.address"
                          placeholder="请输入家庭住址"
                        /> </el-form-item
                    ></el-col>
                    <el-col :span="12"> </el-col>
                  </el-row>
                </el-collapse-item>
              </el-col>
            </el-row>
          </div>

          <div>
            <el-row :gutter="10">
              <el-col style="width: 100%">
                <el-collapse-item title="家庭信息" name="2">
                  <el-button
                    <el-button
                    v-if="form.jtInfo == null || form.jtInfo.length == 0"
                    type="text"
                    icon="el-icon-circle-plus"
                    size="medium"
                    style="margin-left: 20px; margin-bottom: 20px"
                    @click="addJtInfo()"
                  ></el-button>
                  <el-form-item
                    v-for="(info, index) in form.jtInfo"
                    :key="index"
                    prop="jtInfo"
                  >
                    <el-row>
                      <el-col :span="10">
                        <el-form-item label="关系" prop="relation">
                          <el-input
                            v-model="info.relation"
                            placeholder="请输入关系"
                          />
                        </el-form-item>
                      </el-col>
                      <el-col :span="10">
                        <el-form-item label="姓名" prop="jtname">
                          <el-input
                            v-model="info.jtname"
                            placeholder="请输入姓名"
                          />
                        </el-form-item>
                      </el-col>
                      <el-col :span="4">
                        <el-button
                          v-if="index != 0 || form.jtInfo.length == 1"
                          type="text"
                          icon="el-icon-circle-plus"
                          size="medium"
                          style="margin-left: 20px; margin-bottom: 20px"
                          @click="addJtInfo()"
                        ></el-button>
                        <el-button
                          type="text"
                          icon="el-icon-remove"
                          size="medium"
                          style="margin-left: 20px; margin-bottom: 20px"
                          @click="removeJtInfo(index)"
                        ></el-button>
                      </el-col>
                    </el-row>
                  </el-form-item>
                </el-collapse-item>
              </el-col>
            </el-row>
          </div>

          <div>
            <el-row :gutter="10">
              <el-col style="width: 100%">
                <el-collapse-item title="履历" name="3">
                  <el-button
                    <el-button
                    v-if="form.llInfo == null || form.llInfo.length == 0"
                    type="text"
                    icon="el-icon-circle-plus"
                    size="medium"
                    style="margin-left: 20px; margin-bottom: 20px"
                    @click="addLlInfo()"
                  ></el-button>
                  <el-form-item
                    v-for="(info, index) in form.llInfo"
                    :key="index"
                    prop="llInfo"
                  >
                    <el-row>
                      <el-col :span="6">
                        <el-form-item label="单位" prop="dw">
                          <el-input
                            v-model="info.dw"
                            placeholder="请输入单位"
                          />
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item label="职位" prop="zw">
                          <el-input
                            v-model="info.zw"
                            placeholder="请输入职位"
                          />
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item label="时间段" prop="sjd">
                          <el-input
                            v-model="info.sjd"
                            placeholder="请输入时间段"
                          />
                        </el-form-item>
                      </el-col>
                      <el-col :span="4">
                        <el-button
                          v-if="index != 0 || form.llInfo.length == 1"
                          type="text"
                          icon="el-icon-circle-plus"
                          size="medium"
                          style="margin-left: 20px; margin-bottom: 20px"
                          @click="addLlInfo()"
                        ></el-button>
                        <el-button
                          type="text"
                          icon="el-icon-remove"
                          size="medium"
                          style="margin-left: 20px; margin-bottom: 20px"
                          @click="removeLlInfo(index)"
                        ></el-button>
                      </el-col>
                    </el-row>
                  </el-form-item>
                </el-collapse-item>
              </el-col>
            </el-row>
          </div>

          <div>
            <el-row :gutter="10">
              <el-col style="width: 100%">
                <el-collapse-item title="重点项目对接情况" name="4">
                  <el-button
                    <el-button
                    v-if="
                      form.projectInfo == null || form.projectInfo.length == 0
                    "
                    type="text"
                    icon="el-icon-circle-plus"
                    size="medium"
                    style="margin-left: 20px; margin-bottom: 20px"
                    @click="addProjectInfo()"
                  ></el-button>
                  <el-form-item
                    v-for="(info, index) in form.projectInfo"
                    :key="index"
                    prop="projectInfo"
                  >
                    <el-row>
                      <el-col :span="6">
                        <el-form-item label="项目编号" prop="num">
                          <el-input
                            v-model="info.num"
                            placeholder="请输入项目编号"
                          />
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item label="项目名称" prop="xmname">
                          <el-input
                            v-model="info.xmname"
                            placeholder="请输入项目名称"
                          />
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item label="对接情况" prop="content">
                          <el-input
                            type="textarea"
                            :rows="1"
                            v-model="info.content"
                            placeholder="请输入对接情况"
                          />
                        </el-form-item>
                      </el-col>
                      <el-col :span="4">
                        <el-button
                          v-if="index != 0 || form.projectInfo.length == 1"
                          type="text"
                          icon="el-icon-circle-plus"
                          size="medium"
                          style="margin-left: 20px; margin-bottom: 20px"
                          @click="addProjectInfo()"
                        ></el-button>
                        <el-button
                          type="text"
                          icon="el-icon-remove"
                          size="medium"
                          style="margin-left: 20px; margin-bottom: 20px"
                          @click="removeProjectInfo(index)"
                        ></el-button>
                      </el-col>
                    </el-row>
                  </el-form-item>
                </el-collapse-item>
              </el-col>
            </el-row>
          </div>
        </el-form>
        <div class="dialog-footer" style="text-align: right">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-collapse>
    </el-dialog>

    <el-dialog
      :title="titleDetail"
      :visible.sync="openDetail"
      width="1000px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-collapse v-model="activeNames">
        <el-collapse-item title="基本信息" name="1">
          <el-row>
            <el-col :span="12">
              <el-descriptions size="mini" :column="1">
                <el-descriptions-item label="照片">
                  <img
                    :src="`${uploadFileUrl}${formDetail.img}`"
                    class="avatar"
                    @click="openPreview"
                  />
                </el-descriptions-item>
              </el-descriptions>
            </el-col>
            <el-col :span="12">
              <el-descriptions size="mini" :column="2">
                <el-descriptions-item label="姓名">{{
                  formDetail.name
                }}</el-descriptions-item>
                <el-descriptions-item label="个人专长">{{
                  formDetail.expertise
                }}</el-descriptions-item>
                <el-descriptions-item label="兴趣爱好">{{
                  formDetail.hobbies
                }}</el-descriptions-item>
                <el-descriptions-item label="出生">{{
                  formDetail.born
                }}</el-descriptions-item>
                <el-descriptions-item label="是否吸烟">
                  {{ formDetail.smoke === 1 ? "是" : "否" }}
                </el-descriptions-item>
                <el-descriptions-item label="酒量">{{
                  formDetail.capacity
                }}</el-descriptions-item>

                <el-descriptions-item label="毕业学校">{{
                  formDetail.school
                }}</el-descriptions-item>
                <el-descriptions-item label="学历">{{
                  formDetail.education
                }}</el-descriptions-item>
                <el-descriptions-item label="身高(米)">{{
                  formDetail.height
                }}</el-descriptions-item>
                <el-descriptions-item label="职称">{{
                  formDetail.jobtitle
                }}</el-descriptions-item>
                <el-descriptions-item label="单位">{{
                  formDetail.unit
                }}</el-descriptions-item>
                <el-descriptions-item label="职位">{{
                  formDetail.position
                }}</el-descriptions-item>
                <el-descriptions-item label="家庭地址">{{
                  formDetail.address
                }}</el-descriptions-item>
              </el-descriptions>
            </el-col>
          </el-row>
        </el-collapse-item>
        <el-collapse-item title="履历与家庭信息" name="2">
          <el-row>
            <el-col :span="12">
              <el-table :data="formDetail.llInfo" style="width: 100%" border>
                <el-table-column prop="dw" label="单位"> </el-table-column>
                <el-table-column prop="zw" label="职位"> </el-table-column>
                <el-table-column prop="sjd" label="时间段"> </el-table-column>
              </el-table>
            </el-col>

            <el-col :span="11" style="margin-left: 6px">
              <el-table :data="formDetail.jtInfo" style="width: 100%" border>
                <el-table-column prop="relation" label="关系">
                </el-table-column>
                <el-table-column prop="jtname" label="姓名"> </el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </el-collapse-item>
        <el-collapse-item title="重点项目对接情况" name="3">
          <el-row>
            <el-col :span="24">
              <el-table
                :data="formDetail.projectInfo"
                style="width: 100%"
                border
              >
                <el-table-column prop="num" label="项目编号"> </el-table-column>
                <el-table-column prop="xmname" label="项目名称">
                </el-table-column>
                <el-table-column prop="content" label="对接情况">
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </el-collapse-item>
      </el-collapse>
    </el-dialog>

    <!-- 弹出大图的对话框 -->
    <el-dialog :visible.sync="previewVisible" width="70%" @close="closePreview">
      <img
        :src="`${uploadFileUrl}${formDetail.img}`"
        alt="预览图片"
        style="width: 70%"
      />
    </el-dialog>
  </div>
</template>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
<script>
import {
  listClientele,
  getClientele,
  delClientele,
  addClientele,
  updateClientele,
} from "@/api/system/clientele";
import FileUpload from "@/components/FileUpload";
import userInfo from "@/store/modules/user";

export default {
  name: "Clientele",
  components: {
    FileUpload,
  },
  props: {
    fileType: {
      type: Array,
      default: () => ["png", "jpeg", "jpg"],
    },
  },
  data() {
    return {
      previewVisible: false,
      uploadFileUrl: process.env.VUE_APP_BASE_API, // 上传文件服务器地址
      activeNames: ["1", "2", "3", "4"],
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
      // 客户信息表格数据
      clienteleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        img: null,
        name: null,
        expertise: null,
        hobbies: null,
        born: null,
        smoke: null,
        capacity: null,
        school: null,
        education: null,
        height: null,
        jobtitle: null,
        unit: null,
        position: null,
        address: null,
      },
      formDetail: {},
      openDetail: false,
      titleDetail: "",
      // 表单参数
      form: {
        smoke: 0,
      },
      // 表单校验
      rules: {
        smoke: [
          {
            required: true,
            message: "是否吸烟，0否1是不能为空",
            trigger: "change",
          },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 打开预览框
    openPreview() {
      this.previewVisible = true;
    },
    // 关闭预览框
    closePreview() {
      this.previewVisible = false;
    },
    showButton(userId) {
      return userId == userInfo.state.userId;
    },
    removeProjectInfo(index) {
      this.form.projectInfo.splice(index, 1);
      this.$forceUpdate();
    },
    addProjectInfo() {
      this.form.projectInfo.push({});
      this.$forceUpdate();
    },
    removeJtInfo(index) {
      this.form.jtInfo.splice(index, 1);
      this.$forceUpdate();
    },
    addJtInfo() {
      this.form.jtInfo.push({});
      this.$forceUpdate();
    },
    removeLlInfo(index) {
      this.form.llInfo.splice(index, 1);
      this.$forceUpdate();
    },
    addLlInfo() {
      this.form.llInfo.push({});
      this.$forceUpdate();
    },
    handleAvatarSuccess(res, file) {
      this.form.img = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isImage = file.type === "image/jpeg" || file.type === "image/png";

      if (!isImage) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      return isImage;
    },
    /** 查询客户信息列表 */
    getList() {
      this.loading = true;
      listClientele(this.queryParams).then((response) => {
        this.clienteleList = response.rows;
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
      if (this.$refs.fileUploadModule != null) {
        this.$refs.fileUploadModule.number = 0;
        this.$refs.fileUploadModule.uploadList = [];
        this.$refs.fileUploadModule.fileList = [];
      }
      this.form = {
        id: null,
        img: null,
        name: null,
        expertise: null,
        hobbies: null,
        born: null,
        smoke: 0,
        capacity: null,
        school: null,
        education: null,
        height: null,
        jobtitle: null,
        unit: null,
        position: null,
        address: null,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form = { jtInfo: [], llInfo: [], projectInfo: [] };
      this.open = true;
      this.title = "添加客户信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getClientele(id).then((response) => {
        this.form = response.data;

        this.open = true;
        this.title = "修改客户信息";
      });
    },
    handleDetail(row) {
      const id = row.id || this.ids;
      getClientele(id).then((response) => {
        this.formDetail = response.data;
        this.openDetail = true;
        this.titleDetail = "详情";
      });
    },
    /** 提交按钮 */
    submitForm() {
      const uploadListComponent = this.$refs.fileUploadModule;
      const fileList = uploadListComponent.fileList;
      if (fileList.length > 0) {
        this.form.img = fileList[0].url;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateClientele(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addClientele(this.form).then((response) => {
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
        .confirm('是否确认删除客户信息编号为"' + ids + '"的数据项？')
        .then(function () {
          return delClientele(ids);
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
        "system/clientele/export",
        {
          ...this.queryParams,
        },
        `clientele_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
