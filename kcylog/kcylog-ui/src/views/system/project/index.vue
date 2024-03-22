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
      <el-form-item label="项目名称" prop="projectNameAlias">
        <el-input
          v-model="queryParams.projectNameAlias"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目编号" prop="projectNum">
        <el-input
          v-model="queryParams.projectNum"
          placeholder="请输入项目编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择">
          <el-option
            v-for="item in statusArr"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
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

      <el-form-item label="结算状态" prop="outputStatus">
        <el-select v-model="queryParams.outputStatus" placeholder="请选择">
          <el-option
            v-for="item in outputStatusList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="结算时间">
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
          v-hasPermi="['system:project:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:project:export']"
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
      :data="projectList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="委托单位" align="center" prop="requesterAlias" />
      <el-table-column
        label="项目名称"
        align="center"
        prop="projectNameAlias"
      />
      <el-table-column label="项目编号" align="center" prop="projectNum" />
      <el-table-column label="工程负责人" align="center" prop="userNameAlias" />
      <el-table-column label="作业部门" align="center" prop="department" />
      <!-- <el-table-column label="项目类型" align="center" prop="projectType" />
      <el-table-column
        label="工程内容"
        align="center"
        prop="workcontentAlias"
      /> -->
      <el-table-column label="登记时间" align="center" prop="registerTime">
        <template slot-scope="scope">
          {{ formatDate(scope.row.registerTime) }}
        </template>
      </el-table-column>
      <el-table-column
        label="安排开始时间"
        align="center"
        prop="projectStartAlias"
      >
        <template slot-scope="scope">
          {{ formatDate(scope.row.projectStartAlias) }}
        </template></el-table-column
      >
      <el-table-column
        label="安排结束时间"
        align="center"
        prop="projectEndAlias"
      >
        <template slot-scope="scope">
          {{ formatDate(scope.row.projectEndAlias) }}
        </template></el-table-column
      >
      <el-table-column label="工作状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == 0" type="danger">临时安排</el-tag>
          <el-tag v-else-if="scope.row.status == 1" type="success"
            >正式安排</el-tag
          >
          <el-tag v-else-if="scope.row.status == 2" type="warning"
            >一检办结</el-tag
          >
          <el-tag v-else-if="scope.row.status == 3">二检办结</el-tag>
          <el-tag v-else type="danger">其他状态</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="雇工分包" align="center">
        <el-table-column label="状态" align="center" prop="subpackageType">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.subpackageType == 0" type="danger"
              >未设置</el-tag
            >
            <el-tag v-else-if="scope.row.subpackageType == 1" type="danger"
              >非分包</el-tag
            >
            <el-tag v-else-if="scope.row.subpackageType == 2" type="success"
              >单一合同分包</el-tag
            >
            <el-tag v-else-if="scope.row.subpackageType == 3" type="success"
              >框架协议分包</el-tag
            >
            <el-tag v-else type="danger">其他状态</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.issq !== 1"
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="applyReviewSub(scope.row)"
              v-hasPermi="['system:project:applyReviewSub']"
              style="color: red"
              >申请雇工分包</el-button
            >
            <el-tag v-if="scope.row.issq == 1" type="success"
              >已有审核单</el-tag
            >
          </template>
        </el-table-column>
      </el-table-column>

      <!-- <el-table-column label="一检时间" align="center" prop="oneCheck" />
      <el-table-column label="二检时间" align="center" prop="twoCheckTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.twoCheckTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column> -->
      <!-- <el-table-column
        label="结算时间"
        align="center"
        prop="settlementTime"
        width="160"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.settlementTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="结算状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.outputStatus === 0" type="warning"
            >未填写</el-tag
          >
          <el-tag v-else-if="scope.row.outputStatus === 1">待结算</el-tag>
          <el-tag v-else-if="scope.row.outputStatus === 2" type="success"
            >已结算</el-tag
          >
          <el-tag v-else type="danger">其他状态</el-tag>
        </template>
      </el-table-column> -->
      <el-table-column
        fixed="right"
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
            v-hasPermi="['system:project:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:project:detail']"
            >详情</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:project:remove']"
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

    <!-- 添加或修改项目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="projectNameAlias">
          <el-input
            v-model="form.projectNameAlias"
            placeholder="请输入项目名称"
          />
        </el-form-item>
        <el-form-item label="项目编号" prop="projectNum">
          <el-input v-model="form.projectNum" placeholder="请输入项目编号" />
        </el-form-item>
        <el-form-item label="项目类型" prop="projectType">
          <el-input v-model="form.projectType" placeholder="请输入项目类型" />
        </el-form-item>
        <el-form-item label="登记时间" prop="registerTime">
          <el-input v-model="form.registerTime" placeholder="请输入登记时间" />
        </el-form-item>
        <el-form-item label="接待人" prop="receptionist">
          <el-input v-model="form.receptionist" placeholder="请输入接待人" />
        </el-form-item>
        <el-form-item label="工作量" prop="workloadAlias">
          <el-input
            v-model="form.workloadAlias"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="工程内容" prop="workcontentAlias">
          <el-input
            v-model="form.workcontentAlias"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="工程负责人" prop="userNameAlias">
          <el-input
            v-model="form.userNameAlias"
            placeholder="请输入工程负责人"
          />
        </el-form-item>
        <el-form-item label="委托单位" prop="requesterAlias">
          <el-input
            v-model="form.requesterAlias"
            placeholder="请输入委托单位"
          />
        </el-form-item>
        <el-form-item label="安排开始时间" prop="projectStartAlias">
          <el-input
            v-model="form.projectStartAlias"
            placeholder="请输入安排开始时间"
          />
        </el-form-item>
        <el-form-item label="安排结束时间" prop="projectEndAlias">
          <el-input
            v-model="form.projectEndAlias"
            placeholder="请输入安排结束时间"
          />
        </el-form-item>
        <el-form-item label="一检时间" prop="oneCheck">
          <el-input v-model="form.oneCheck" placeholder="请输入一检时间" />
        </el-form-item>
        <el-form-item label="二检时间" prop="twoCheck">
          <el-input v-model="form.twoCheck" placeholder="请输入二检时间" />
        </el-form-item>
        <el-form-item label="通知出件时间" prop="noticeTime">
          <el-input
            v-model="form.noticeTime"
            placeholder="请输入通知出件时间"
          />
        </el-form-item>
        <el-form-item label="项目出件时间" prop="projectTime">
          <el-input
            v-model="form.projectTime"
            placeholder="请输入项目出件时间"
          />
        </el-form-item>
        <el-form-item label="送达时间" prop="deliveryTime">
          <el-input v-model="form.deliveryTime" placeholder="请输入送达时间" />
        </el-form-item>
        <el-form-item label="项目金额" prop="projectMoneyAlias">
          <el-input
            v-model="form.projectMoneyAlias"
            placeholder="请输入项目金额"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :title="detailTitle"
      :visible.sync="detailOpen"
      width="1260px"
      append-to-body
      v-el-drag-dialog
    >
      <el-collapse v-model="activeNames">
        <el-collapse-item title="项目基本信息" name="1">
          <el-descriptions class="margin-top" :column="4" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-home"></i>
                委托单位
              </template>
              {{ form.requesterAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                项目编号
              </template>
              {{ form.projectNum }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                接待人
              </template>
              {{ form.receptionist }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                登记时间
              </template>
              {{ formatDate(form.registerTime) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                项目名称
              </template>
              {{ form.projectNameAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                工程负责人
              </template>
              {{ form.userNameAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                安排开始时间
              </template>
              {{ formatDate(form.projectStartAlias) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                安排结束时间
              </template>
              {{ formatDate(form.projectEndAlias) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-notebook-2"></i>
                项目类型
              </template>
              {{ form.projectType }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-notebook-2"></i>
                作业部门
              </template>
              {{ form.department }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                一检时间
              </template>
              {{ formatDate(form.oneCheck) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                二检时间
              </template>
              {{ formatDate(form.twoCheck) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-money"></i>
                项目金额
              </template>
              {{ form.projectMoneyAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                通知出件时间
              </template>
              {{ formatDate(form.noticeTime) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                项目出件时间
              </template>
              {{ formatDate(form.projectTime) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                送达时间
              </template>
              {{ formatDate(form.deliveryTime) }}
            </el-descriptions-item>
          </el-descriptions>
          <el-descriptions class="margin-top" :column="1" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document"></i>
                工程内容
              </template>
              {{ form.workcontentAlias }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document"></i>
                工作量
              </template>
              {{ form.workloadAlias }}
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
        <el-collapse-item title="经营产值" name="2">
          <el-descriptions class="margin-top" :column="2" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                经营产值
              </template>
              <span v-if="form.operate != 0.0">{{ form.operate }}</span>
              <span v-if="form.operate == 0.0" style="color: red"
                >暂未填写经营产值</span
              >
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
        <el-collapse-item title="产值结算" name="3">
          <el-table :data="form.projectValue" border style="width: 100%">
            <el-table-column prop="userName" label="用户名" style="width: 100%">
            </el-table-column>
            <el-table-column
              prop="proportion"
              label="占比(%)"
              style="width: 100%"
            >
            </el-table-column>
          </el-table>
        </el-collapse-item>
        <el-collapse-item title="车辆使用登记" name="4">
          <el-table :data="form.projectCar" stripe style="width: 100%">
            <el-table-column prop="recordTime" label="日期">
              <template slot-scope="scope">
                <span>{{
                  parseTime(scope.row.recordTime, "{y}-{m}-{d}")
                }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="projectName" label="项目编号">
            </el-table-column>
            <el-table-column prop="carNum" label="车牌号"> </el-table-column>
            <el-table-column prop="carType" label="用车类型">
              <template slot-scope="scope">
                <span v-if="scope.row.carType === 1">单位派车（拼车）</span>
                <span v-else-if="scope.row.carType === 2"
                  >单位派车（独享）</span
                >
                <span v-else-if="scope.row.carType === 3">滴滴</span>
              </template>
            </el-table-column>
            <el-table-column prop="number" label="次数">
              <template slot-scope="scope">
                <span v-if="scope.row.number === 1">一趟</span>
                <span v-else-if="scope.row.number === 2">两趟</span>
                <span v-else-if="scope.row.number === 3">三趟</span>
                <span v-else-if="scope.row.number === 4">四趟</span>
              </template>
            </el-table-column>
            <el-table-column prop="carExpenses" label="费用"> </el-table-column>
          </el-table>
        </el-collapse-item>
      </el-collapse>
    </el-dialog>

    <!-- 添加审核单对话框 -->
    <el-dialog
      :title="titleReviewSub"
      :visible.sync="openReviewSub"
      width="1300px"
      append-to-body
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form
        ref="formReviewSub"
        :model="formReviewSub"
        :rules="rulesReviewSub"
        label-width="80px"
      >
        <el-collapse v-model="activeNamesReviewSub">
          <div>
            <el-row :gutter="10">
              <el-col style="width: 33%">
                <el-card class="box-card">
                  <div slot="header">
                    <span>基本信息</span>
                  </div>
                  <div>
                    <el-form-item label="工程编号" prop="serialNum">
                      <el-input
                        v-model="formReviewSub.serialNum"
                        placeholder="请输入编号"
                        disabled
                      />
                    </el-form-item>
                    <el-form-item label="项目名称" prop="projectName">
                      <el-input
                        v-model="formReviewSub.projectName"
                        placeholder="请输入项目名称"
                        disabled
                      />
                    </el-form-item>
                    <el-form-item label="委托单位" prop="requester">
                      <el-input
                        v-model="formReviewSub.requester"
                        placeholder="请输入委托单位"
                        disabled
                      />
                    </el-form-item>
                    <el-form-item label="项目类型" prop="businessName">
                      <el-input
                        v-model="formReviewSub.businessName"
                        placeholder="请输入项目类型"
                      />
                    </el-form-item>
                    <el-form-item label="项目金额" prop="porjectMoney">
                      <el-input
                        v-model="formReviewSub.porjectMoney"
                        placeholder="请输入项目金额"
                        disabled
                      />
                    </el-form-item>
                    <el-form-item label="工作内容">
                      <el-input
                        v-model="formReviewSub.workcontent"
                        type="textarea"
                        placeholder="请输入工作内容"
                      />
                    </el-form-item>
                    <div class="form-container">
                      <el-form-item label="项目工期" prop="projectStart">
                        <el-date-picker
                          clearable
                          v-model="formReviewSub.projectStart"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择工期开始时间"
                          disabled
                        >
                        </el-date-picker>
                      </el-form-item>
                      <el-form-item label="" prop="projectEnd">
                        <el-date-picker
                          clearable
                          v-model="formReviewSub.projectEnd"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择工期结束时间"
                          disabled
                        >
                        </el-date-picker>
                      </el-form-item>
                    </div>
                  </div>
                </el-card>
              </el-col>
              <el-col style="width: 33%">
                <el-card class="box-card">
                  <div slot="header">
                    <span>雇工详情</span>
                  </div>
                  <div>
                    <!-- <el-form-item label="项目金额" prop="porjectMoney">
                      <el-input-number
                        v-model="formReviewSub.porjectMoney"
                        :precision="2"
                        :step="0.1"
                        :min="0.0"
                        placeholder="请输入项目金额"
                      />
                    </el-form-item> -->
                    <el-form-item label="雇工人数" prop="peopleNum">
                      <el-input-number
                        v-model="formReviewSub.peopleNum"
                        placeholder="请预估雇工人数"
                        :min="0"
                        @change="handleTimeChange"
                      />
                    </el-form-item>
                    <el-form-item label="雇工内容" prop="employmentReason">
                      <el-input
                        v-model="formReviewSub.employmentReason"
                        type="textarea"
                        placeholder="未填写"
                      />
                    </el-form-item>
                    <el-form-item label="雇工开始时间" prop="startTime">
                      <el-date-picker
                        clearable
                        v-model="formReviewSub.startTime"
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
                        v-model="formReviewSub.endTime"
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
                        v-model="formReviewSub.budgetDay"
                        placeholder="请输入预估天数"
                        @change="handleTimeChange(1)"
                      />
                    </el-form-item>
                    <el-form-item label="预算金额" prop="budgetMoney">
                      <el-input
                        v-model="formReviewSub.budgetMoney"
                        placeholder="请输入预算金额"
                        disabled
                      />
                    </el-form-item></div
                ></el-card>
              </el-col>
              <el-col style="width: 33%">
                <el-card class="box-card">
                  <div slot="header">
                    <span>分包详情</span>
                  </div>
                  <div>
                    <el-form-item label="分包类型" prop="subType">
                      <el-radio-group v-model="formReviewSub.subType">
                        <el-radio :label="1">全部分包</el-radio>
                        <el-radio :label="2">局部分包</el-radio>
                      </el-radio-group>
                    </el-form-item>
                    <el-form-item label="预估分包工作量" prop="subWorkload">
                      <el-input
                        v-model="formReviewSub.subWorkload"
                        type="textarea"
                        placeholder="请输入预估分包工作量"
                      />
                    </el-form-item>
                    <el-form-item label="抽签单位" prop="cooperationUnitJson">
                      <el-select
                        v-model="formReviewSub.cooperationUnitJson"
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
                        v-model="formReviewSub.winUnit"
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
                        v-model="formReviewSub.lotTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择抽签时间"
                      >
                      </el-date-picker>
                    </el-form-item></div
                ></el-card>
              </el-col>
            </el-row>
          </div>
        </el-collapse>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormReviewSub(1)"
          >保存</el-button
        >
        <el-button type="success" @click="submitFormReviewSub(2)"
          >保存并发起审核</el-button
        >
        <el-button @click="cancelReviewSub">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listProject,
  getProject,
  delProject,
  addProject,
  updateProject,
  listProjectSelected,
} from "@/api/system/project";
import elDragDialog from "@/api/components/el-drag";
import { listUnit } from "@/api/system/unit";
import { addReview, setReviewStatus } from "@/api/system/reviewSub";

export default {
  name: "Project",
  directives: {
    elDragDialog,
  },
  data() {
    return {
      projectIdMap: {},
      listProjectLocalSelected: [],
      listProjectLocalMap: {},
      winUnits: [],
      startAmPm: "12:00:00",
      endAmPm: "23:59:59",
      activeNamesReviewSub: ["1", "2", "3"],
      formReviewSub: {},
      rulesReviewSub: {
        serialNum: [
          { required: true, message: "编号不能为空", trigger: "blur" },
        ],
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" },
        ],
        requester: [
          { required: true, message: "委托单位不能为空", trigger: "blur" },
        ],
      },
      titleReviewSub: "",
      openReviewSub: false,
      statusArr: [
        {
          value: 0,
          label: "临时安排",
        },
        {
          value: 1,
          label: "正式安排",
        },
        {
          value: 2,
          label: "一检办结",
        },
        {
          value: 3,
          label: "二检办结",
        },
      ],
      outputStatusList: [
        {
          value: 0,
          label: "待填写",
        },
        {
          value: 1,
          label: "待结算",
        },
        {
          value: 2,
          label: "已结算",
        },
      ],
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
      money: 100,
      dateRange: [],
      activeNames: ["1", "2", "3", "4"],
      detailOpen: false,
      detailTitle: "项目详情",
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
        projectNameAlias: null,
        projectNum: null,
        projectType: null,
        registerTime: null,
        receptionist: null,
        workloadAlias: null,
        userNameAlias: null,
        requesterAlias: null,
        projectStartAlias: null,
        projectEndAlias: null,
        oneCheck: null,
        twoCheck: null,
        noticeTime: null,
        projectTime: null,
        deliveryTime: null,
        projectMoneyAlias: null,
        operate: null,
        operateUser: null,
        operateTime: null,
        department: "",
        outputStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        projectNameAlias: [
          { required: true, message: "项目名称不能为空", trigger: "blur" },
        ],
        projectNum: [
          { required: true, message: "项目编号不能为空", trigger: "blur" },
        ],
        projectType: [
          { required: true, message: "项目类型不能为空", trigger: "change" },
        ],
        registerTime: [
          { required: true, message: "登记时间不能为空", trigger: "blur" },
        ],
        receptionist: [
          { required: true, message: "接待人不能为空", trigger: "blur" },
        ],
        userNameAlias: [
          { required: true, message: "工程负责人不能为空", trigger: "blur" },
        ],
        requesterAlias: [
          { required: true, message: "委托单位不能为空", trigger: "blur" },
        ],
        projectStartAlias: [
          { required: true, message: "安排开始时间不能为空", trigger: "blur" },
        ],
        projectEndAlias: [
          { required: true, message: "安排结束时间不能为空", trigger: "blur" },
        ],
        oneCheck: [
          { required: true, message: "一检时间不能为空", trigger: "blur" },
        ],
        twoCheck: [
          { required: true, message: "二检时间不能为空", trigger: "blur" },
        ],
        noticeTime: [
          { required: true, message: "通知出件时间不能为空", trigger: "blur" },
        ],
        projectTime: [
          { required: true, message: "项目出件时间不能为空", trigger: "blur" },
        ],
        deliveryTime: [
          { required: true, message: "送达时间不能为空", trigger: "blur" },
        ],
        projectMoneyAlias: [
          { required: true, message: "项目金额不能为空", trigger: "blur" },
        ],
        operate: [
          { required: true, message: "经营产值不能为空", trigger: "blur" },
        ],
        operateUser: [
          {
            required: true,
            message: "填写经营产值人不能为空",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.getReviewProject();
    this.getList();
    this.loadAllUnits();
  },
  methods: {
    formatDate(dateString) {
      if (dateString == "") {
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
    submitFormReviewSub(status) {
      this.$refs["formReviewSub"].validate((valid) => {
        if (valid) {
          if (
            this.formReviewSub.startTime != null &&
            this.formReviewSub.endTime != null
          ) {
            let oldStartTime = this.formReviewSub.startTime;
            let oldEndTime = this.formReviewSub.endTime;
            this.formReviewSub.startTime =
              this.formReviewSub.startTime + " " + this.startAmPm;
            this.formReviewSub.endTime =
              this.formReviewSub.endTime + " " + this.endAmPm;
            if (this.formReviewSub.startTime > this.formReviewSub.endTime) {
              this.$message({
                showClose: true,
                message: "开始时间不能大于结束时间～",
                type: "error",
              });
              this.formReviewSub.startTime = oldStartTime;
              this.formReviewSub.endTime = oldEndTime;
            } else {
              addReview(this.formReviewSub).then((response) => {
                this.openReviewSub = false;
                if (status == 1) {
                  this.$modal.msgSuccess("新增成功");
                  this.getList();
                } else {
                  this.handleReview(response.data);
                }
              });
            }
          } else if (
            this.formReviewSub.startTime != null &&
            this.formReviewSub.endTime == null
          ) {
            this.formReviewSub.startTime =
              this.formReviewSub.startTime + " " + this.startAmPm;
            addReview(this.formReviewSub).then((response) => {
              this.openReviewSub = false;
              if (status == 1) {
                this.$modal.msgSuccess("新增成功");
                this.getList();
              } else {
                this.handleReview(response.data);
              }
            });
          } else if (
            this.formReviewSub.startTime == null &&
            this.formReviewSub.endTime != null
          ) {
            this.formReviewSub.endTime =
              this.formReviewSub.endTime + " " + this.endAmPm;
            addReview(this.formReviewSub).then((response) => {
              this.openReviewSub = false;
              if (status == 1) {
                this.$modal.msgSuccess("新增成功");
                this.getList();
              } else {
                this.handleReview(response.data);
              }
            });
          } else {
            addReview(this.formReviewSub).then((response) => {
              this.openReviewSub = false;
              if (status == 1) {
                this.$modal.msgSuccess("新增成功");
                this.getList();
              } else {
                this.handleReview(response.data);
              }
            });
          }
        }
      });
    },
    handleReview(reviewId) {
      let data = {};
      data.reviewId = reviewId;
      data.status = 1;
      setReviewStatus(data).then((response) => {
        this.getList();
        this.$modal.msgSuccess("已申请并发起审核");
      });
    },
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
    applyReviewSub(value) {
      this.formReviewSub = {};
      const projectId = value.projectId;
      const projectInfo = this.listProjectLocalMap.get(projectId);
      this.formReviewSub.projectId = projectId;
      if (projectInfo.projectNum != null && projectInfo.projectNum != "") {
        this.formReviewSub.serialNum = projectInfo.projectNum;
      }

      if (
        projectInfo.projectNameAlias != null &&
        projectInfo.projectNameAlias != ""
      ) {
        this.formReviewSub.projectName = projectInfo.projectNameAlias;
      }

      if (
        projectInfo.requesterAlias != null &&
        projectInfo.requesterAlias != ""
      ) {
        this.formReviewSub.requester = projectInfo.requesterAlias;
      }

      if (
        projectInfo.workloadAlias != null &&
        projectInfo.workloadAlias != ""
      ) {
        this.formReviewSub.workload = projectInfo.workloadAlias;
      }

      if (
        projectInfo.projectMoneyAlias != null &&
        projectInfo.projectMoneyAlias != 0
      ) {
        this.formReviewSub.porjectMoney = projectInfo.projectMoneyAlias;
      }

      if (
        projectInfo.projectStartAlias != null &&
        projectInfo.projectStartAlias != 0
      ) {
        this.formReviewSub.projectStart = this.formatDate(
          projectInfo.projectStartAlias
        );
      }

      if (
        projectInfo.projectEndAlias != null &&
        projectInfo.projectEndAlias != 0
      ) {
        this.formReviewSub.projectEnd = this.formatDate(
          projectInfo.projectEndAlias
        );
      }

      if (
        projectInfo.workcontentAlias != null &&
        projectInfo.workcontentAlias != ""
      ) {
        this.formReviewSub.workcontent = projectInfo.workcontentAlias;
      }

      if (
        projectInfo.projectStartAlias != null &&
        projectInfo.projectStartAlias != 0
      ) {
        this.formReviewSub.cpStartTime = this.formatDate(
          projectInfo.projectStartAlias
        );
      }

      if (
        projectInfo.projectEndAlias != null &&
        projectInfo.projectEndAlias != 0
      ) {
        this.formReviewSub.cpEndTime = this.formatDate(
          projectInfo.projectEndAlias
        );
      }

      if (projectInfo.projectType != null && projectInfo.projectType != "") {
        this.formReviewSub.businessName = projectInfo.projectType;
      }

      this.titleReviewSub = "雇工分包申请";
      this.openReviewSub = true;
    },
    cancelReviewSub() {
      this.openReviewSub = false;
      this.formReviewSub = {};
    },
    handleTimeChange(budgetDayType) {
      if (
        this.formReviewSub.startTime != null &&
        this.formReviewSub.endTime != null
      ) {
        let startTimeTemp = "";
        if (this.startAmPm == "23:59:59") {
          startTimeTemp = this.formReviewSub.startTime + " " + "24:00:00";
        } else {
          startTimeTemp = this.formReviewSub.startTime + " " + this.startAmPm;
        }

        let endTimeTemp = "";
        if (this.endAmPm == "23:59:59") {
          endTimeTemp = this.formReviewSub.endTime + " " + "24:00:00";
        } else {
          endTimeTemp = this.formReviewSub.endTime + " " + this.endAmPm;
        }
        const date1 = new Date(startTimeTemp);
        const date2 = new Date(endTimeTemp);
        const diffInMs = date2 - date1;
        const diffInHours = diffInMs / (1000 * 60 * 60);
        if (budgetDayType != 1) {
          this.formReviewSub.budgetDay = (diffInHours / 12 + 1) * 0.5;
        }
        if (this.formReviewSub.peopleNum == undefined) {
          this.formReviewSub.peopleNum = 0;
        }
        this.formReviewSub.budgetMoney =
          (this.formReviewSub.budgetDay / 0.5) *
          this.money *
          this.formReviewSub.peopleNum;
      }
    },
    /** 查询项目列表 */
    getList() {
      this.getReviewProject();
      this.loading = true;
      listProject(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.projectList = response.rows;
          this.total = response.total;
          this.loading = false;
          this.listProjectLocalMap = new Map();
          for (var i = 0; i < response.rows.length; i++) {
            this.listProjectLocalMap.set(
              response.rows[i].projectId,
              response.rows[i]
            );
          }
          for (var j = 0; j < this.projectList.length; j++) {
            if (this.projectIdMap.has(this.projectList[j].projectId)) {
              this.projectList[j].issq = 1;
            }
          }
        }
      );
    },
    getReviewProject() {
      listProjectSelected(1).then((response) => {
        this.listProjectLocalSelected = response.rows;
        this.projectIdMap = new Map();
        for (var i = 0; i < this.listProjectLocalSelected.length; i++) {
          this.projectIdMap.set(
            this.listProjectLocalSelected[i].projectId,
            true
          );
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.detailOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        projectId: null,
        projectNameAlias: null,
        projectNum: null,
        projectType: null,
        registerTime: null,
        receptionist: null,
        workloadAlias: null,
        userNameAlias: null,
        requesterAlias: null,
        projectStartAlias: null,
        projectEndAlias: null,
        oneCheck: null,
        twoCheck: null,
        noticeTime: null,
        projectTime: null,
        deliveryTime: null,
        projectMoneyAlias: null,
        operate: null,
        operateUser: null,
        operateTime: null,
        createTime: null,
        updateTime: null,
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
      this.ids = selection.map((item) => item.projectId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加项目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const projectId = row.projectId || this.ids;
      getProject(projectId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改项目";
      });
    },

    handleDetail(row) {
      this.reset();
      const projectId = row.projectId || this.ids;
      getProject(projectId).then((response) => {
        this.form = response.data;
        this.detailOpen = true;
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.projectId != null) {
            updateProject(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProject(this.form).then((response) => {
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
      const projectIds = row.projectId || this.ids;
      this.$modal
        .confirm('是否确认删除项目编号为"' + projectIds + '"的数据项？')
        .then(function () {
          return delProject(projectIds);
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
          message: "请先选择导出的结算时间范围～",
          type: "warning",
        });
        return;
      }
      this.download(
        "system/project/export",
        {
          ...this.queryParams,
        },
        `project_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
