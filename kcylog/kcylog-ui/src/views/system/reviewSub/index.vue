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
      <el-form-item label="工程编号" prop="serialNum">
        <el-input
          v-model="queryParams.serialNum"
          placeholder="请输入编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="委托单位" prop="requester">
        <el-input
          v-model="queryParams.requester"
          placeholder="请输入委托单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审核状态" prop="status">
        <el-select
          v-model="statusVaule"
          placeholder="请选择"
          @change="handleQuery"
        >
          <el-option
            v-for="item in statusArr"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="雇工提交" prop="finalHire">
        <el-select
          v-model="finalHireValue"
          placeholder="请选择"
          @change="handleQuery"
        >
          <el-option
            v-for="item in finalHireArr"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
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
      :data="reviewList"
      @selection-change="handleSelectionChange"
    >
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="工程编号" align="center" prop="serialNum" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="委托单位" align="center" prop="requester" />
      <!-- <el-table-column label="项目金额" align="center" prop="porjectMoney" />
      <el-table-column label="工作量" align="center" prop="workload" />
      <el-table-column label="分包情况" align="center" prop="subcontract">
        <template slot-scope="scope">
          <span v-if="scope.row.subcontract === 1">是</span>
          <span v-else-if="scope.row.subcontract === 2">否</span>
          <span v-else></span>
        </template>
      </el-table-column>
      <el-table-column label="雇工金额" align="center" prop="budgetMoney" /> -->
      <el-table-column label="负责人" align="center" prop="user.userName" />
      <el-table-column label="部门" align="center" prop="dept.deptName" />
      <!-- <el-table-column label="审核状态" align="center" prop="status">
        <template slot-scope="scope">
          <span v-if="scope.row.status === 0">未开始</span>
          <span v-else-if="scope.row.status === 1">进行中</span>
          <span v-else-if="scope.row.status === 2">通过</span>
          <span v-else-if="scope.row.status === 3">未通过</span>
          <span v-else>其他状态</span>
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="人数" align="center" prop="peopleNum" />
      <el-table-column
        label="预估雇工工作开始时间"
        align="center"
        prop="startTime"
        width="180"
      >
        <template slot-scope="scope">
          <span
            >{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}:{{
              filterTime(scope.row.startTime)
            }}</span
          >
        </template>
      </el-table-column>
      <el-table-column
        label="预估雇工工作结束时间"
        align="center"
        prop="endTime"
        width="180"
      >
        <template slot-scope="scope">
          <span
            >{{ parseTime(scope.row.endTime, "{y}-{m}-{d}") }}:{{
              filterTime(scope.row.endTime)
            }}</span
          >
        </template>
      </el-table-column>
      <el-table-column label="预估天数" align="center" prop="budgetDay" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="160"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        fixed="right"
      >
        <template slot-scope="scope">
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-s-comment"
              @click="showReviewInfo(scope.row)"
              >审核单详情</el-button
            >
          </div>
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit-outline"
              @click="handleReview(scope.row)"
              v-if="scope.row.status === 0 || scope.row.status === 3"
              >发起审核</el-button
            >
          </div>
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-if="
                scope.row.status === 0 ||
                scope.row.status === 3 ||
                scope.row.startEdit === 1
              "
              >修改基本信息</el-button
            >
          </div>
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-if="scope.row.status === 0 || scope.row.status === 3"
              >删除</el-button
            >
          </div>
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-s-operation"
              @click="handleReviewProcess(scope.row)"
              >流程详情</el-button
            >
          </div>
          <div>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-user-solid"
              @click="finalEmploymentInfo(scope.row)"
              v-if="
                showEmployeeButton(
                  scope.row.finalTime,
                  scope.row.status,
                  scope.row.finalSecondStatus
                )
              "
              >最终雇工信息</el-button
            >
          </div>
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

    <!-- 具体流程 -->

    <el-dialog :visible.sync="reviewProcessOpen" width="300px" append-to-body>
      <div style="height: 300px">
        <el-steps direction="vertical" :active="reviewProcessActive">
          <el-step
            v-for="reviewProcess in reviewProcessList"
            :key="reviewProcess.reviewProcessId"
            :title="
              reviewProcess.userId === 1 &&
              (reviewProcess.status != 2 || reviewProcess.status != 4)
                ? '填写最终雇工信息中～'
                : reviewProcess.user.userName
            "
            :status="reviewProcessStatus(reviewProcess)"
            :description="
              reviewProcess.userId === 1 && reviewProcess.status != 2
                ? ''
                : reviewProcessDescription(reviewProcess)
            "
            v-if="!(reviewProcess.userId === 1 && reviewProcess.status === 2)"
          ></el-step>
        </el-steps>
      </div>
    </el-dialog>

    <!-- 添加或修改审核单对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1000px"
      append-to-body
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-collapse v-model="activeNames">
          <el-collapse-item title="项目详情" name="1">
            <el-form-item label="关联项目" prop="projectId">
              <el-select
                v-model="form.projectId"
                placeholder="请选择关联项目"
                filterable
                @change="handleSelectChange"
              >
                <el-option
                  v-for="item in listProjectLocal"
                  :key="item.projectId"
                  :label="item.projectNum"
                  :value="item.projectId"
                  :disabled="item.disabled"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-collapse-item>
          <el-collapse-item title="雇工详情" name="2">
            <el-form-item label="工程编号" prop="serialNum">
              <el-input v-model="form.serialNum" placeholder="请输入编号" />
            </el-form-item>
            <el-form-item label="项目名称" prop="projectName">
              <el-input
                v-model="form.projectName"
                placeholder="请输入项目名称"
              />
            </el-form-item>
            <el-form-item label="委托单位" prop="requester">
              <el-input v-model="form.requester" placeholder="请输入委托单位" />
            </el-form-item>
            <el-form-item label="项目金额" prop="porjectMoney">
              <el-input-number
                v-model="form.porjectMoney"
                :precision="2"
                :step="0.1"
                :min="0.0"
                placeholder="请输入项目金额"
              />
            </el-form-item>
            <el-form-item label="工作量" prop="workload">
              <el-input
                v-model="form.workload"
                type="textarea"
                placeholder="请输入内容"
              />
            </el-form-item>
            <el-form-item label="分包情况" prop="subcontract">
              <el-select v-model="form.subcontract" placeholder="请选择">
                <el-option
                  label="是"
                  :value="1"
                  :selected="form.subcontract === 1"
                ></el-option>
                <el-option
                  label="否"
                  :value="2"
                  :selected="form.subcontract === 2"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="雇工人数" prop="peopleNum">
              <el-input-number
                v-model="form.peopleNum"
                placeholder="请预估雇工人数"
                :min="0"
                @change="handleTimeChange"
              />
            </el-form-item>
            <el-form-item label="雇工内容" prop="employmentReason">
              <el-input
                v-model="form.employmentReason"
                type="textarea"
                placeholder="未填写"
              />
            </el-form-item>
            <el-form-item label="雇工开始时间" prop="startTime">
              <el-date-picker
                clearable
                v-model="form.startTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择预估雇工工作开始时间"
                @change="handleTimeChange"
              >
              </el-date-picker>

              <el-select
                v-model="startAmPm"
                placeholder="请选择"
                @change="handleTimeChange"
              >
                <el-option label="上午" value="12:00:00"></el-option>
                <el-option label="下午" value="23:59:59"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="雇工结束时间" prop="endTime">
              <el-date-picker
                clearable
                v-model="form.endTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择预估雇工工作结束时间"
                @change="handleTimeChange"
              >
              </el-date-picker>
              <el-select
                v-model="endAmPm"
                placeholder="请选择"
                @change="handleTimeChange"
              >
                <el-option label="上午" value="12:00:00"></el-option>
                <el-option label="下午" value="23:59:59"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="预估天数" prop="budgetDay">
              <el-input-number
                :precision="1"
                :step="0.5"
                :min="0.0"
                v-model="form.budgetDay"
                placeholder="请输入预估天数"
                @change="handleTimeChange(1)"
              />
            </el-form-item>
            <el-form-item label="预算金额" prop="budgetMoney">
              <el-input
                v-model="form.budgetMoney"
                placeholder="请输入预算金额"
                disabled
              />
            </el-form-item>
            <div class="form-container">
              <el-form-item label="项目工期" prop="projectStart">
                <el-date-picker
                  clearable
                  v-model="form.projectStart"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="请选择工期开始时间"
                >
                </el-date-picker>
              </el-form-item>
              <el-form-item
                label=""
                style="margin-left: -100px"
                prop="projectEnd"
              >
                <el-date-picker
                  clearable
                  v-model="form.projectEnd"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="请选择工期结束时间"
                >
                </el-date-picker>
              </el-form-item>
            </div>
          </el-collapse-item>
          <el-collapse-item title="分包详情" name="3">
            <el-form-item label="工程编号" prop="serialNum">
              <el-input v-model="form.serialNum" placeholder="请输入工程编号" />
            </el-form-item>
            <el-form-item label="项目名称" prop="projectName">
              <el-input
                v-model="form.projectName"
                placeholder="请输入项目名称"
              />
            </el-form-item>
            <el-form-item label="项目类型" prop="businessName">
              <el-input
                v-model="form.businessName"
                placeholder="请输入抽检项目类型"
              />
            </el-form-item>
            <el-form-item label="工作内容">
              <el-input
                v-model="form.workcontent"
                type="textarea"
                placeholder="请输入工作内容"
              />
            </el-form-item>
            <el-form-item label="分包类型" prop="subType">
              <el-radio-group v-model="form.subType">
                <el-radio :label="1">全部分包</el-radio>
                <el-radio :label="2">局部分包</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="分包工作量" prop="subWorkload">
              <el-input
                v-model="form.subWorkload"
                type="textarea"
                placeholder="请输入分包工作量"
              />
            </el-form-item>
            <el-form-item label="抽签单位" prop="cooperationUnitJson">
              <el-select
                v-model="form.cooperationUnitJson"
                placeholder="抽签单位"
                multiple
                style="width: 260px"
              >
                <el-option
                  v-for="item in winUnits"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="中签单位" prop="winUnit">
              <el-select
                v-model="form.winUnit"
                placeholder="请选择中签单位"
                style="width: 260px"
              >
                <el-option
                  v-for="item in winUnits"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="抽签时间" prop="lotTime">
              <el-date-picker
                clearable
                v-model="form.lotTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择抽签时间"
              >
              </el-date-picker>
            </el-form-item>
            <div class="form-container">
              <el-form-item label="项目工期" prop="cpStartTime">
                <el-date-picker
                  clearable
                  v-model="form.cpStartTime"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="请选择工期开始时间"
                >
                </el-date-picker>
              </el-form-item>
              <el-form-item
                label=""
                style="margin-left: -100px"
                prop="cpEndTime"
              >
                <el-date-picker
                  clearable
                  v-model="form.cpEndTime"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="请选择工期结束时间"
                >
                </el-date-picker>
              </el-form-item>
            </div>
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
      :title="titleInfo"
      :visible.sync="openInfo"
      width="1260px"
      append-to-body
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-collapse v-model="activeNamesTemp">
        <el-collapse-item title="项目详情" name="1">
          <div
            v-if="!formInfo.project || !formInfo.project.projectNum"
            style="text-align: center"
          >
            未找到关联项目数据～
          </div>
          <div v-if="formInfo.project && formInfo.project.projectNum">
            <el-descriptions class="margin-top" :column="5" border>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-office-building"></i>
                  项目名称
                </template>
                {{ formInfo.project.projectNameAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-tickets"></i>
                  项目编号
                </template>
                {{ formInfo.project.projectNum }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-notebook-2"></i>
                  项目类型
                </template>
                {{ formInfo.project.projectType }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-money"></i>
                  项目金额
                </template>
                {{ formInfo.project.projectMoneyAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  登记时间
                </template>
                {{ formInfo.project.registerTime }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-s-custom"></i>
                  接待人
                </template>
                {{ formInfo.project.receptionist }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-document"></i>
                  工程内容
                </template>
                {{ formInfo.project.workloadAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-user"></i>
                  工程负责人
                </template>
                {{ formInfo.project.userNameAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-s-home"></i>
                  委托单位
                </template>
                {{ formInfo.project.requesterAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  安排开始时间
                </template>
                {{ formInfo.project.projectStartAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  安排结束时间
                </template>
                {{ formInfo.project.projectEndAlias }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  一检时间
                </template>
                {{ formInfo.project.oneCheck }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  二检时间
                </template>
                {{ formInfo.project.twoCheck }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  通知出件时间
                </template>
                {{ formInfo.project.noticeTime }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  项目出件时间
                </template>
                {{ formInfo.project.projectTime }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  送达时间
                </template>
                {{ formInfo.project.deliveryTime }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  送达时间
                </template>
                {{ formInfo.project.deliveryTime }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-collapse-item>

        <el-collapse-item title="审核单详情" name="2">
          <div>
            <el-row :gutter="10">
              <el-col style="width: 50%">
                <el-card>
                  <div slot="header">
                    <span>雇工详情</span>
                    <el-button
                      style="float: right; padding: 3px 0"
                      type="text"
                    ></el-button>
                  </div>

                  <el-descriptions class="margin-top" :column="2" border>
                    <el-descriptions-item>
                      <template slot="label"> 工程编号 </template>
                      {{ formInfo.serialNum }}
                    </el-descriptions-item>
                    <el-descriptions-item v-if="formInfo.user">
                      <template slot="label"> 负责人 </template>
                      {{ formInfo.user.userName }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 项目名称 </template>
                      {{ formInfo.projectName }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 委托单位 </template>
                      {{ formInfo.requester }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 工作量 </template>
                      {{ formInfo.workload }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 项目金额 </template>
                      {{ formInfo.porjectMoney }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 分包情况 </template>
                      <div v-if="formInfo.subcontract == 1">是</div>
                      <div v-if="formInfo.subcontract == 2">否</div>
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 雇工内容 </template>
                      {{ formInfo.employmentReason }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 雇工开始时间 </template>
                      {{ formInfo.startTime
                      }}<span v-if="startAmPm == '12:00:00'">上午</span
                      ><span v-if="startAmPm == '23:59:59'">下午</span>
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 雇工结束时间 </template>
                      {{ formInfo.endTime
                      }}<span v-if="endAmPm == '12:00:00'">上午</span
                      ><span v-if="endAmPm == '23:59:59'">下午</span>
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 项目工期开始时间 </template>
                      {{ formInfo.projectStart | formatDate }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 项目工期结束时间 </template>
                      {{ formInfo.projectEnd | formatDate }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 雇工人数 </template>
                      {{ formInfo.peopleNum }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 天数 </template>
                      {{ formInfo.budgetDay }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label"> 预算 </template>
                      {{ formInfo.budgetMoney }}
                    </el-descriptions-item>
                  </el-descriptions>
                </el-card>
              </el-col>

              <el-col style="width: 50%">
                <el-card>
                  <div slot="header">
                    <span>分包详情</span>
                    <el-button
                      style="float: right; padding: 3px 0"
                      type="text"
                    ></el-button>
                  </div>

                  <div style="text-align: center">
                    <el-descriptions class="margin-top" :column="2" border>
                      <el-descriptions-item>
                        <template slot="label"> 工程编号 </template>
                        {{ subcontractForm.serialNum }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 负责人 </template>
                        {{ subcontractForm.user.userName }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 项目名称 </template>
                        {{ subcontractForm.projectName }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 项目类型 </template>
                        {{ subcontractForm.businessName }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 委托单位 </template>
                        {{ subcontractForm.requester }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 工作内容 </template>
                        {{ subcontractForm.workcontent }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 分包类型 </template>
                        <span v-if="subcontractForm.subType == 1">全部分包</span
                        ><span v-if="subcontractForm.subType == 2"
                          >局部分包</span
                        >
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 分包工作量 </template>
                        {{ subcontractForm.workload }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 抽签单位 </template>
                        <div
                          v-for="(
                            unit, index
                          ) in subcontractForm.cooperationUnitJson"
                          :key="index"
                        >
                          {{ unit }}
                        </div>
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 中签单位 </template>
                        {{ subcontractForm.winUnit }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 抽签时间 </template>
                        {{ parseTime(subcontractForm.lotTime, "{y}-{m}-{d}") }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 工期开始 </template>
                        {{
                          parseTime(subcontractForm.cpStartTime, "{y}-{m}-{d}")
                        }}
                      </el-descriptions-item>
                      <el-descriptions-item>
                        <template slot="label"> 工期结束 </template>
                        {{
                          parseTime(subcontractForm.cpEndTime, "{y}-{m}-{d}")
                        }}
                      </el-descriptions-item>
                    </el-descriptions>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-collapse-item>

        <el-collapse-item title="雇工信息详情" name="3">
          <div>
            <el-row :gutter="10">
              <el-col style="width: 100%">
                <el-card>
                  <div slot="header">
                    <span>雇工信息详情</span>
                    <el-button
                      style="float: right; padding: 3px 0"
                      type="text"
                    ></el-button>
                  </div>
                  <el-table
                    highlight-current-row
                    style="width: 100%"
                    :data="employeeList"
                  >
                    <el-table-column label="姓名" align="center" prop="name" />
                    <el-table-column
                      label="身份证"
                      align="center"
                      prop="idCard"
                    />
                    <el-table-column
                      label="作业时间"
                      align="center"
                      prop="workTime"
                    />
                    <el-table-column
                      label="天数"
                      align="center"
                      prop="workDay"
                    />
                    <el-table-column label="费用" align="center" prop="cost" />
                  </el-table>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-collapse-item>
      </el-collapse>
    </el-dialog>

    <!-- 项目编号选择 -->
    <el-dialog
      :title="projectInfo"
      :visible.sync="projectOpen"
      width="1000px"
      append-to-body
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form
        :model="queryProjectParams"
        ref="queryProjectForm"
        size="small"
        :inline="true"
        v-show="showProjectSearch"
        label-width="68px"
      >
        <el-form-item label="工程编号" prop="serialNum">
          <el-input
            v-model="queryProjectParams.serialNum"
            placeholder="请输入工程编号"
            clearable
            @keyup.enter.native="handleProjectQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleProjectQuery"
            >搜索</el-button
          >
        </el-form-item>
      </el-form>

      <el-table v-loading="loading" :data="projectList">
        <el-table-column label="工程编号" align="center" prop="XMBH" />
        <el-table-column label="项目名称" align="center" prop="XMMC" />
        <el-table-column label="工作量" align="center" prop="GZL" />
        <el-table-column label="项目金额" align="center" prop="YSJE" />
        <el-table-column label="委托单位" align="center" prop="WTDW" />
        <el-table-column label="工期开始" align="center" prop="XMKSSJ" />
        <el-table-column label="工期结束" align="center" prop="XMJSSJ" />
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <div>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-document-checked"
                @click="checkImportProject(scope.row)"
                >检测是否已导入</el-button
              >
            </div>
            <div>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-upload"
                @click="importProject(scope.row)"
                >导入</el-button
              >
            </div>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="projectTotal > 0"
        :total="projectTotal"
        :page.sync="queryProjectParams.pageNum"
        :limit.sync="queryProjectParams.pageSize"
        @pagination="getProjectList"
      />
    </el-dialog>
  </div>
</template>
<style>
.custom-input input {
  color: black !important;
}

.textarea-input textarea {
  color: black !important;
}

.el-form-item__label {
  width: 112px !important;
}

.el-form-item--medium .el-form-item__content {
  margin-left: 112px !important;
}

.form-container {
  display: flex;
}
</style>
<script>
import {
  listReview,
  getReview,
  delReview,
  addReview,
  updateReview,
  setReviewStatus,
  getReviewProcessList,
  getReviewBySerialNum,
} from "@/api/system/reviewSub";
import { listProject, listProjectSelected } from "@/api/system/project";
import { listUser } from "@/api/system/user";
import { fetchProjectData } from "@/utils/otherItems";
import { listUnit } from "@/api/system/unit";
import { listEmployee } from "@/api/system/reviewEmployeeSub";

export default {
  filters: {
    formatDate(value) {
      if (value) {
        const date = new Date(value);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, "0");
        const day = String(date.getDate()).padStart(2, "0");
        return `${year}-${month}-${day}`;
      }
      return "";
    },
  },
  name: "Review",
  data() {
    return {
      queryParamsEmployee: {
        pageNum: 1,
        pageSize: 9999,
        reviewId: null,
      },
      employeeList: [],
      subcontractForm: {
        user: {
          userName: "",
        },
      },
      activeNamesTemp: ["1", "2", "3"],
      winUnits: [],
      activeNames: ["1"],
      listProjectLocalSelected: [],
      listProjectLocal: [],
      listProjectLocalMap: {},
      projectList: [],
      showProjectSearch: true,
      projectInfo: "",
      projectOpen: false,
      queryProjectParams: {
        pageNum: 1,
        pageSize: 10,
        serialNum: null,
      },
      projectTotal: 0,
      subcontract: "2",
      money: 100,
      reviewProcessActive: -1,
      queryUserParams: {
        pageNum: 1,
        pageSize: 9999,
      },
      restaurants: [],
      startAmPm: "12:00:00",
      endAmPm: "23:59:59",
      statusArr: [
        {
          value: 0,
          label: "未开始",
        },
        {
          value: 1,
          label: "进行中",
        },
        {
          value: 2,
          label: "通过",
        },
        {
          value: 3,
          label: "未通过",
        },
      ],
      finalHireArr: [
        {
          value: 0,
          label: "否",
        },
        {
          value: 1,
          label: "是",
        },
      ],
      statusVaule: "",
      finalHireValue: "",
      // 日期范围
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
      // 审核单表格数据
      reviewList: [],
      // 弹出层标题
      title: "",
      titleInfo: "",
      // 是否显示弹出层
      open: false,
      reviewProcessOpen: false,
      openInfo: false,
      reviewProcessList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNum: null,
        projectName: null,
        requester: null,
        porjectMoney: null,
        workload: null,
        userId: null,
        deptId: null,
        status: null,
        peopleNum: null,
        startTime: null,
        endTime: null,
        budgetDay: null,
        budgetMoney: null,
        finalTime: null,
      },
      queryProjectListParams: {
        pageNum: 1,
        pageSize: 9999,
      },
      // 表单参数
      form: {},
      formInfo: {},
      // 表单校验
      rules: {
        serialNum: [
          { required: true, message: "编号不能为空", trigger: "blur" },
        ],
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" },
        ],
        requester: [
          { required: true, message: "委托单位不能为空", trigger: "blur" },
        ],
        projectId: [
          { required: true, message: "请选择关联项目", trigger: "change" },
        ],
      },
    };
  },
  computed: {},
  created() {
    this.getList();
    this.getProjectListLocal();
    this.loadAllUnits();
  },
  methods: {
    loadAllUnits() {
      listUnit().then((response) => {
        for (let i = 0; i < response.rows.length; i++) {
          const unit = {};
          unit.value = response.rows[i].unitName;
          unit.label = response.rows[i].unitName;
          this.winUnits.push(unit);
        }
      });
    },
    handleSelectChange(value) {
      this.activeNames.push("2");
      this.activeNames.push("3");
      const projectInfo = this.listProjectLocalMap.get(value);
      if (projectInfo.projectNum != null && projectInfo.projectNum != "") {
        this.form.serialNum = projectInfo.projectNum;
      }

      if (
        projectInfo.projectNameAlias != null &&
        projectInfo.projectNameAlias != ""
      ) {
        this.form.projectName = projectInfo.projectNameAlias;
      }

      if (
        projectInfo.requesterAlias != null &&
        projectInfo.requesterAlias != ""
      ) {
        this.form.requester = projectInfo.requesterAlias;
      }

      if (
        projectInfo.requesterAlias != null &&
        projectInfo.requesterAlias != ""
      ) {
        this.form.workload = projectInfo.requesterAlias;
      }

      if (
        projectInfo.projectMoneyAlias != null &&
        projectInfo.projectMoneyAlias != 0
      ) {
        this.form.porjectMoney = projectInfo.projectMoneyAlias;
      }

      if (
        projectInfo.projectStartAlias != null &&
        projectInfo.projectStartAlias != 0
      ) {
        this.form.projectStart = projectInfo.projectStartAlias;
      }

      if (
        projectInfo.projectEndAlias != null &&
        projectInfo.projectEndAlias != 0
      ) {
        this.form.projectEnd = projectInfo.projectEndAlias;
      }

      if (
        projectInfo.workloadAlias != null &&
        projectInfo.workloadAlias != ""
      ) {
        this.form.workcontent = projectInfo.workloadAlias;
      }

      if (
        projectInfo.projectStartAlias != null &&
        projectInfo.projectStartAlias != 0
      ) {
        this.form.cpStartTime = projectInfo.projectStartAlias;
      }

      if (
        projectInfo.projectEndAlias != null &&
        projectInfo.projectEndAlias != 0
      ) {
        this.form.cpEndTime = projectInfo.projectEndAlias;
      }

      if (projectInfo.projectType != null && projectInfo.projectType != "") {
        this.form.businessName = projectInfo.projectType;
      }
    },
    getProjectListLocal() {
      listProject(this.queryProjectListParams).then((response) => {
        this.listProjectLocalMap = new Map();
        for (var i = 0; i < response.rows.length; i++) {
          this.listProjectLocalMap.set(
            response.rows[i].projectId,
            response.rows[i]
          );
        }
        this.listProjectLocal = response.rows;
        listProjectSelected(1).then((response) => {
          this.listProjectLocalSelected = response.rows;
          const projectIdMap = new Map();
          for (var i = 0; i < this.listProjectLocalSelected.length; i++) {
            projectIdMap.set(this.listProjectLocalSelected[i].projectId, true);
          }

          for (var j = 0; j < this.listProjectLocal.length; j++) {
            if (projectIdMap.has(this.listProjectLocal[j].projectId)) {
              this.listProjectLocal[j].disabled = true;
            }
          }
        });
      });
    },
    checkImportProject(row) {
      getReviewBySerialNum(row.XMBH).then((response) => {
        if (response.data != null) {
          this.$message({
            message:
              "该项目编号已被<" + response.data.user.userName + ">导入过～",
            type: "warning",
          });
        } else {
          this.$message({
            message: "该项目编号未被导入过～",
            type: "success",
          });
        }
      });
    },
    handleProjectQuery() {
      this.queryProjectParams.pageNum = 1;
      this.getProjectList();
    },
    getProjectList() {
      this.loading = true;

      fetchProjectData(this.queryProjectParams)
        .then((response) => {
          this.projectList = response.rows;
          this.projectTotal = response.total;
          this.loading = false;
        })
        .catch((error) => {
          this.$message.error("请求项目管理数据失败～");
        });
    },
    showEmployeeButton(finalTime, status, finalSecondStatus) {
      if ((finalSecondStatus == 1 || status == 2) && status != 4) {
        return true;
      }
      return false;
    },
    finalEmploymentInfo(row) {
      const reviewId = row.reviewId;
      this.openInfo = false;
      this.$router.push(
        "/system/reviewSub-employee/edit/" + reviewId + "/" + row.finalHire
      );
    },
    filterTime(timeString) {
      if (timeString != "" && timeString != null) {
        const timeSubstring = timeString.substring(11);
        if (timeSubstring == this.startAmPm) {
          return "上午";
        } else {
          return "下午";
        }
      }
    },
    handleTimeChange(budgetDayType) {
      if (this.form.startTime != null && this.form.endTime != null) {
        let startTimeTemp = "";
        if (this.startAmPm == "23:59:59") {
          startTimeTemp = this.form.startTime + " " + "24:00:00";
        } else {
          startTimeTemp = this.form.startTime + " " + this.startAmPm;
        }

        let endTimeTemp = "";
        if (this.endAmPm == "23:59:59") {
          endTimeTemp = this.form.endTime + " " + "24:00:00";
        } else {
          endTimeTemp = this.form.endTime + " " + this.endAmPm;
        }
        const date1 = new Date(startTimeTemp);
        const date2 = new Date(endTimeTemp);
        const diffInMs = date2 - date1;
        const diffInHours = diffInMs / (1000 * 60 * 60);
        if (budgetDayType != 1) {
          this.form.budgetDay = (diffInHours / 12 + 1) * 0.5;
        }
        this.form.budgetMoney =
          (this.form.budgetDay / 0.5) * this.money * this.form.peopleNum;
      }
    },
    reviewProcessDescription(reviewProcess) {
      if (reviewProcess.status === 0) {
        return "审核状态:未开始";
      } else if (reviewProcess.status === 1) {
        return "审核状态:进行中";
      } else if (reviewProcess.status === 2) {
        let description = "审核状态:通过；";
        if (reviewProcess.reason != "" && reviewProcess.reason != null) {
          description = description + "理由:" + reviewProcess.reason + "；";
        }
        description = description + "审核时间:" + reviewProcess.reviewTime;
        return description;
      } else if (reviewProcess.status === 3) {
        let description = "审核状态:驳回；";
        if (reviewProcess.reason != "" && reviewProcess.reason != null) {
          description = description + "理由:" + reviewProcess.reason + "；";
        }
        description = description + "审核时间:" + reviewProcess.reviewTime;
        return description;
      }
    },
    reviewProcessStatus(reviewProcess) {
      if (reviewProcess.status === 0) {
        return "";
      } else if (reviewProcess.status === 1) {
        return "finish";
      } else if (reviewProcess.status === 2) {
        return "success";
      } else if (reviewProcess.status === 3) {
        return "error";
      }
    },
    clearReviewer() {},
    querySearchReviewer(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString
        ? restaurants.filter(this.createStateFilter(queryString))
        : restaurants;
      cb(results);
    },
    selectReviewer(item) {
      console.log(item.value);
      console.log(item.userId);
    },
    createStateFilter(queryString) {
      return (state) => {
        return (
          state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    loadAllUsers() {
      listUser(this.addDateRange(this.queryUserParams, this.dateRange)).then(
        (response) => {
          for (let i = 0; i < response.rows.length; i++) {
            let userObject = {
              value: "",
              userId: 0,
            };
            userObject.value = response.rows[i].userName;
            userObject.userId = response.rows[i].userId;
            this.restaurants.push(userObject);
          }
        }
      );
    },
    /** 查询审核单列表 */
    getList() {
      this.loading = true;
      listReview(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.reviewList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.openInfo = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.openInfo = false;
      this.form = {
        reviewId: null,
        serialNum: null,
        projectName: null,
        requester: null,
        porjectMoney: null,
        workload: null,
        userId: null,
        deptId: null,
        status: null,
        peopleNum: null,
        startTime: null,
        endTime: null,
        budgetDay: null,
        budgetMoney: null,
        createTime: null,
        updateTime: null,
        finalTime: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      if (this.statusVaule !== "") {
        this.queryParams.status = this.statusVaule;
      }
      if (this.finalHireValue !== "") {
        this.queryParams.finalHire = this.finalHireValue;
      }
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.statusVaule = "";
      this.finalHireValue = "";
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.reviewId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.activeNames = ["1"];
      this.open = true;
      this.title = "添加审核单";
      // this.$confirm("是否需要获取项目管理系统数据?", "提示", {
      //   confirmButtonText: "是",
      //   cancelButtonText: "否",
      //   type: "success",
      // })
      //   .then(() => {
      //     this.projectInfo = "工程项目列表";
      //     this.projectOpen = true;
      //     this.getProjectList();
      //     this.getProjectListLocal();
      //   })
      //   .catch(() => {
      //     this.open = true;
      //     this.title = "添加审核单";
      //   });
    },
    importProject(row) {
      this.reset();
      this.resetForm("queryProjectForm");
      this.open = true;
      this.title = "添加审核单";
      this.projectOpen = false;
      if (row.XMBH != null && row.XMBH != "") {
        this.form.serialNum = row.XMBH;
      }

      if (row.XMMC != null && row.XMMC != "") {
        this.form.projectName = row.XMMC;
      }

      if (row.WTDW != null && row.WTDW != "") {
        this.form.requester = row.WTDW;
      }

      if (row.GZL != null && row.GZL != "") {
        this.form.workload = row.GZL;
      }

      if (row.YSJE != null && row.YSJE != 0) {
        this.form.porjectMoney = row.YSJE;
      }

      if (row.XMKSSJ != null && row.XMKSSJ != 0) {
        this.form.projectStart = row.XMKSSJ;
      }

      if (row.XMJSSJ != null && row.XMJSSJ != 0) {
        this.form.projectEnd = row.XMJSSJ;
      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.activeNames = ["1", "2", "3"];
      const reviewId = row.reviewId || this.ids;
      getReview(reviewId).then((response) => {
        if (response.data.startTime != null && response.data.startTime != "") {
          this.startAmPm = response.data.startTime.substring(11);
          response.data.startTime = response.data.startTime.substring(0, 10);
        }
        if (response.data.endTime != null && response.data.endTime != "") {
          this.endAmPm = response.data.endTime.substring(11);
          response.data.endTime = response.data.endTime.substring(0, 10);
        }
        this.form = response.data;
        if (this.form.subcontract == 0) {
          this.form.subcontract = null;
        }
        this.open = true;
        this.title = "修改审核单";
      });
    },
    /** 显示具体流程按钮操作 */
    handleReviewProcess(row) {
      let formObj = {};
      formObj.reviewId = row.reviewId;
      getReviewProcessList(formObj).then((response) => {
        this.reviewProcessList = response.rows;
        this.reviewProcessActive = -1;
        for (let i = 0; i < response.rows.length; i++) {
          if (response.rows[i].status != 0) {
            this.reviewProcessActive = this.reviewProcessActive + 1;
          }
        }
        this.reviewProcessOpen = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.reviewId != null) {
            if (this.form.startTime != null && this.form.endTime != null) {
              let oldStartTime = this.form.startTime;
              let oldEndTime = this.form.endTime;
              this.form.startTime = this.form.startTime + " " + this.startAmPm;
              this.form.endTime = this.form.endTime + " " + this.endAmPm;
              if (this.form.startTime > this.form.endTime) {
                this.$message({
                  showClose: true,
                  message: "开始时间不能大于结束时间～",
                  type: "error",
                });
                this.form.startTime = oldStartTime;
                this.form.endTime = oldEndTime;
              } else {
                updateReview(this.form).then((response) => {
                  this.$modal.msgSuccess("修改成功");
                  this.open = false;
                  this.openInfo = false;
                  this.getList();
                  location.reload();
                });
              }
            } else if (
              this.form.startTime != null &&
              this.form.endTime == null
            ) {
              this.form.startTime = this.form.startTime + " " + this.startAmPm;
              updateReview(this.form).then((response) => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.openInfo = false;
                this.getList();
                location.reload();
              });
            } else if (
              this.form.startTime == null &&
              this.form.endTime != null
            ) {
              this.form.endTime = this.form.endTime + " " + this.endAmPm;
              updateReview(this.form).then((response) => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.openInfo = false;
                this.getList();
                location.reload();
              });
            } else {
              updateReview(this.form).then((response) => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.openInfo = false;
                this.getList();
                location.reload();
              });
            }
          } else {
            if (this.form.startTime != null && this.form.endTime != null) {
              let oldStartTime = this.form.startTime;
              let oldEndTime = this.form.endTime;
              this.form.startTime = this.form.startTime + " " + this.startAmPm;
              this.form.endTime = this.form.endTime + " " + this.endAmPm;
              if (this.form.startTime > this.form.endTime) {
                this.$message({
                  showClose: true,
                  message: "开始时间不能大于结束时间～",
                  type: "error",
                });
                this.form.startTime = oldStartTime;
                this.form.endTime = oldEndTime;
              } else {
                addReview(this.form).then((response) => {
                  this.$modal.msgSuccess("新增成功");
                  this.open = false;
                  this.openInfo = false;
                  this.getList();
                  location.reload();
                });
              }
            } else if (
              this.form.startTime != null &&
              this.form.endTime == null
            ) {
              this.form.startTime = this.form.startTime + " " + this.startAmPm;
              addReview(this.form).then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.openInfo = false;
                this.getList();
                location.reload();
              });
            } else if (
              this.form.startTime == null &&
              this.form.endTime != null
            ) {
              this.form.endTime = this.form.endTime + " " + this.endAmPm;
              addReview(this.form).then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.openInfo = false;
                this.getList();
                location.reload();
              });
            } else {
              addReview(this.form).then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.openInfo = false;
                this.getList();
                location.reload();
              });
            }
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const reviewIds = row.reviewId || this.ids;
      this.$modal
        .confirm('是否确认删除审核单编号为"' + row.serialNum + '"的数据项？')
        .then(function () {
          return delReview(reviewIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
          location.reload();
        })
        .catch(() => {});
    },
    /** 发起审核 */
    handleReview(row) {
      const reviewIds = row.reviewId || this.ids;
      this.$modal
        .confirm(
          '是否确认对审核单编号为"' + row.serialNum + '"的审核单发起审核申请?'
        )
        .then(() => {
          this.form = {};
          this.form.reviewId = row.reviewId;
          this.form.status = 1;
          setReviewStatus(this.form).then((response) => {
            this.getList();
            this.$modal.msgSuccess("已发起审核");
          });
        })
        .catch(() => {});
    },

    showReviewInfo(row) {
      const reviewId = row.reviewId || this.ids;
      getReview(reviewId).then((response) => {
        if (response.data.startTime != null && response.data.startTime != "") {
          this.startAmPm = response.data.startTime.substring(11);
          response.data.startTime = response.data.startTime.substring(0, 10);
        }
        if (response.data.endTime != null && response.data.endTime != "") {
          this.endAmPm = response.data.endTime.substring(11);
          response.data.endTime = response.data.endTime.substring(0, 10);
        }
        this.formInfo = response.data;
        if (this.formInfo.subcontract == 0) {
          this.formInfo.subcontract = null;
        }
        this.subcontractForm = response.data;
        this.openInfo = true;
        this.titleInfo = "审核单详情";
      });
      this.queryParamsEmployee.reviewId = reviewId;
      listEmployee(this.queryParamsEmployee).then((response) => {
        this.employeeList = response.rows;
      });
    },
  },
};
</script>
