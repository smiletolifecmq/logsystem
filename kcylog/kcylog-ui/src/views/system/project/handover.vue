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
      <el-form-item label="委托单位" prop="requesterAlias">
        <el-input
          v-model="queryParams.requesterAlias"
          placeholder="请输入委托单位名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
      <el-form-item label="项目类型" prop="projectType">
        <el-input
          v-model="queryParams.projectType"
          placeholder="请输入项目类型"
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
      <el-form-item label="负责人" prop="userNameAlias">
        <el-input
          v-model="queryParams.userNameAlias"
          placeholder="请输入项目负责人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收件状态" prop="receiveStatus">
        <el-select v-model="queryParams.receiveStatus" placeholder="请选择">
          <el-option
            v-for="item in receiveStatusArr"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="归档状态" prop="checkStatus">
        <el-select v-model="queryParams.checkStatus" placeholder="请选择">
          <el-option
            v-for="item in checkStatusArr"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="二检时间">
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

    <el-table :data="statisticsData" style="width: 100%">
      <el-table-column prop="status" label="类型" align="center">
        <template slot-scope="scope">
          <el-tag v-show="scope.row.status == 1" type="success"
            >归档中项目数</el-tag
          >
          <el-tag v-show="scope.row.status == -2" type="danger"
            >收件将要超期项目数(2天内)</el-tag
          >
          <el-tag v-show="scope.row.status == -3" type="danger"
            >归档将要超期项目数(2天内)</el-tag
          >
          <el-tag v-show="scope.row.status == 0" type="danger"
            >收件超期项目数</el-tag
          >
          <el-tag v-show="scope.row.status == -1" type="danger"
            >归档超期项目数</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="gcchbNumWork" label="工程测绘部" align="center">
        <template slot-scope="scope">
          <el-tag v-show="scope.row.status == 1" type="success">{{
            scope.row.gcchbNumWork
          }}</el-tag>
          <el-tag
            v-show="scope.row.status == 0"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('工程测绘部', 0)"
            >{{ scope.row.gcchbNumWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == -1"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('工程测绘部', -1)"
            >{{ scope.row.gcchbNumWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == -2"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('工程测绘部', -2)"
            >{{ scope.row.gcchbNumWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == -3"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('工程测绘部', -3)"
            >{{ scope.row.gcchbNumWork }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="bdcchbWork" label="不动产测绘部" align="center">
        <template slot-scope="scope">
          <el-tag v-show="scope.row.status == 1" type="success">{{
            scope.row.bdcchbWork
          }}</el-tag>
          <el-tag
            v-show="scope.row.status == 0"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('不动产测绘部', 0)"
            >{{ scope.row.bdcchbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == -1"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('不动产测绘部', -1)"
            >{{ scope.row.bdcchbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == -2"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('不动产测绘部', -2)"
            >{{ scope.row.bdcchbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == -3"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('不动产测绘部', -3)"
            >{{ scope.row.bdcchbWork }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="gxgcbWork" label="管线工程部" align="center">
        <template slot-scope="scope">
          <el-tag v-show="scope.row.status == 1" type="success">{{
            scope.row.gxgcbWork
          }}</el-tag>
          <el-tag
            v-show="scope.row.status == 0"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('管线工程部', 0)"
            >{{ scope.row.gxgcbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == -1"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('管线工程部', -1)"
            >{{ scope.row.gxgcbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == -2"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('管线工程部', -2)"
            >{{ scope.row.gxgcbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == -3"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('管线工程部', -3)"
            >{{ scope.row.gxgcbWork }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="dlxxbWork" label="地理信息部" align="center">
        <template slot-scope="scope">
          <el-tag v-show="scope.row.status == 1" type="success">{{
            scope.row.dlxxbWork
          }}</el-tag>
          <el-tag
            v-show="scope.row.status == 0"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('地理信息部', 0)"
            >{{ scope.row.dlxxbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == -1"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('地理信息部', -1)"
            >{{ scope.row.dlxxbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == -2"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('地理信息部', -2)"
            >{{ scope.row.dlxxbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == -3"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('地理信息部', -3)"
            >{{ scope.row.dlxxbWork }}</el-tag
          >
        </template>
      </el-table-column>
    </el-table>

    <el-table
      v-loading="loading"
      :data="projectList"
      @selection-change="handleSelectionChange"
      size="mini"
    >
      <el-table-column
        fixed
        label="委托单位"
        align="center"
        prop="requesterAlias"
      >
        <template slot-scope="scope">
          {{ scope.row.projectList.requesterAlias }}
        </template>
      </el-table-column>
      <el-table-column
        fixed
        label="项目名称"
        align="center"
        prop="projectNameAlias"
      >
        <template slot-scope="scope">
          {{ scope.row.projectList.projectNameAlias }}
        </template>
      </el-table-column>
      <el-table-column fixed label="项目编号" align="center" prop="projectNum">
        <template slot-scope="scope">
          {{ scope.row.projectList.projectNum }}
        </template>
      </el-table-column>
      <el-table-column fixed label="版本" align="center" prop="version">
        <template slot-scope="scope">
          {{ scope.row.version }}
        </template>
      </el-table-column>
      <el-table-column label="工程负责人" align="center" prop="userNameAlias">
        <template slot-scope="scope">
          {{ scope.row.projectList.userNameAlias }}
        </template>
      </el-table-column>
      <el-table-column label="作业部门" align="center" prop="department">
        <template slot-scope="scope">
          {{ scope.row.projectList.department }}
        </template>
      </el-table-column>
      <el-table-column label="二检时间" align="center" prop="twoCheck">
        <template slot-scope="scope">
          {{ formatDate(scope.row.projectList.twoCheck) }}
        </template></el-table-column
      >
      <el-table-column label="移交时间" align="center" prop="transferTime">
        <template slot-scope="scope" v-if="scope.row.transferTime != null">
          {{ formatDate(scope.row.transferTime) }}
        </template>
        <template slot-scope="scope" v-else> </template>
      </el-table-column>
      <el-table-column label="收件时间" align="center" prop="receiveTime">
        <template slot-scope="scope" v-if="scope.row.receiveTime != null">
          {{ formatDate(scope.row.receiveTime) }}
        </template>
        <template slot-scope="scope" v-else> </template>
      </el-table-column>
      <el-table-column
        label="收件截止时间"
        align="center"
        prop="receiveCutoffTime"
      >
        <template slot-scope="scope" v-if="scope.row.receiveCutoffTime != null">
          <span style="color: green">
            {{ formatDate(scope.row.receiveCutoffTime) }}</span
          >
        </template>
        <template slot-scope="scope" v-else> </template>
      </el-table-column>
      <el-table-column label="收件提前天数" align="center">
        <template slot-scope="scope">
          <el-tag
            v-show="scope.row.isArchive != 1 && scope.row.isArchive != null"
            type="success"
            >无归档</el-tag
          >
          <div v-show="scope.row.isArchive == 1 || scope.row.isArchive == null">
            <el-tag
              type="danger"
              v-show="scope.row.projectList.receiveDays < 0"
              >{{ scope.row.projectList.receiveDays }}</el-tag
            >
            <el-tag
              type="success"
              v-show="scope.row.projectList.receiveDays >= 0"
              >{{ scope.row.projectList.receiveDays }}</el-tag
            >
          </div>
        </template></el-table-column
      >
      <el-table-column label="盖章时间" align="center" prop="stampTime">
        <template slot-scope="scope" v-if="scope.row.stampTime != null">
          {{ formatDate(scope.row.stampTime) }}
        </template>
        <template slot-scope="scope" v-else> </template>
      </el-table-column>
      <el-table-column label="验收时间" align="center" prop="checkTime">
        <template slot-scope="scope" v-if="scope.row.checkTime != null">
          {{ formatDate(scope.row.checkTime) }}
        </template>
        <template slot-scope="scope" v-else> </template>
      </el-table-column>
      <el-table-column label="盖章确认时间" align="center" prop="marketingTime">
        <template slot-scope="scope" v-if="scope.row.marketingTime != null">
          {{ formatDate(scope.row.marketingTime) }}
        </template>
        <template slot-scope="scope" v-else> </template>
      </el-table-column>
      <el-table-column label="归档时间" align="center" prop="archiveTime">
        <template slot-scope="scope" v-if="scope.row.archiveTime != null">
          {{ formatDate(scope.row.archiveTime) }}
        </template>
        <template slot-scope="scope" v-else> </template>
      </el-table-column>
      <el-table-column
        label="归档截止时间"
        align="center"
        prop="rectifyCutoffTime"
      >
        <template slot-scope="scope" v-if="scope.row.rectifyCutoffTime != null">
          <span style="color: green">{{
            formatDate(scope.row.rectifyCutoffTime)
          }}</span>
        </template>
        <template slot-scope="scope" v-else> </template>
      </el-table-column>
      <el-table-column label="归档提前天数" align="center">
        <template slot-scope="scope">
          <el-tag
            v-show="scope.row.isArchive != 1 && scope.row.isArchive != null"
            type="success"
            >无归档</el-tag
          >
          <div v-show="scope.row.isArchive == 1 || scope.row.isArchive == null">
            <el-tag
              type="danger"
              v-show="scope.row.projectList.archiveDays < 0"
              >{{ scope.row.projectList.archiveDays }}</el-tag
            >
            <el-tag
              type="success"
              v-show="scope.row.projectList.archiveDays >= 0"
              >{{ scope.row.projectList.archiveDays }}</el-tag
            >
          </div>
        </template></el-table-column
      >
      <el-table-column label="是否已收件" align="center" prop="receiveStatus">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.receiveStatus == 2" type="success">是</el-tag>
          <el-tag v-else type="danger">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否已归档" align="center" prop="checkStatus">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.checkStatus == 2" type="success">是</el-tag>
          <el-tag v-else type="danger">否</el-tag>
        </template>
      </el-table-column>
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
            icon="el-icon-tickets"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:project:query']"
            >项目详情</el-button
          >
          <el-button
            v-show="scope.row.isArchive == 1 || scope.row.isArchive == null"
            size="mini"
            type="text"
            icon="el-icon-s-order"
            @click="handleProcessDetail(scope.row)"
            >流程详情</el-button
          >
          <el-button
            v-if="
              ['图', '售', '数'].some((substring) =>
                scope.row.projectNum.includes(substring)
              )
            "
            size="mini"
            type="text"
            icon="el-icon-picture"
            @click="handleGeo(scope.row)"
            v-hasPermi="['system:project:geoInfo']"
            >查看选图</el-button
          >
          <el-tag
            v-show="scope.row.isArchive != 1 && scope.row.isArchive != null"
            type="success"
            >无归档</el-tag
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
                <i class="el-icon-office-building"></i>
                经办人
              </template>
              {{ form.customerContractName }}
            </el-descriptions-item>

            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                手机号
              </template>
              {{ form.customerContractPhone }}
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
                <i class="el-icon-money"></i>
                项目金额
              </template>
              {{ form.projectMoneyAlias }}
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
          </el-descriptions>

          <el-descriptions class="margin-top" :column="1" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document"></i>
                工程内容
              </template>
              {{ form.workcontentAlias }}
              <el-button
                v-if="showFetailXt(form)"
                type="text"
                icon="el-icon-picture"
                @click="handleGeo(form)"
                v-hasPermi="['system:project:geoInfo']"
                >查看选图</el-button
              >
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
        <el-collapse-item title="人员安排" name="3">
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
                      <el-input-number
                        v-model="formReviewSub.porjectMoney"
                        placeholder="请输入项目金额"
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

    <el-dialog
      :visible.sync="projectProcessOpen"
      width="400px"
      append-to-body
      :title="projectProcessTitle"
    >
      <el-steps
        :active="activeProcessNum"
        align-center
        finish-status="success"
        direction="vertical"
      >
        <el-step title="移交" :description="yjDescription"></el-step>
        <el-step title="收件" :description="sjDescription"></el-step>
        <el-step title="盖章" :description="gzDescription"></el-step>
        <el-step title="验收" :description="ysDescription"></el-step>
        <el-step title="盖章确认" :description="gzqrDescription"></el-step>
        <el-step title="归档" :description="gdDescription"></el-step>
      </el-steps>
    </el-dialog>

    <el-dialog
      :title="overTitle"
      :visible.sync="overTimeOpen"
      width="1400px"
      append-to-body
      v-el-drag-dialog
    >
      <el-table
        v-loading="loading"
        :data="overTimeProjectList"
        size="mini"
        height="500"
      >
        <el-table-column
          fixed
          label="委托单位"
          align="center"
          prop="requesterAlias"
        >
          <template slot-scope="scope">
            {{ scope.row.projectList.requesterAlias }}
          </template>
        </el-table-column>
        <el-table-column
          fixed
          label="项目名称"
          align="center"
          prop="projectNameAlias"
        >
          <template slot-scope="scope">
            {{ scope.row.projectList.projectNameAlias }}
          </template>
        </el-table-column>
        <el-table-column
          fixed
          label="项目编号"
          align="center"
          prop="projectNum"
        >
          <template slot-scope="scope">
            {{ scope.row.projectList.projectNum }}
          </template>
        </el-table-column>
        <el-table-column fixed label="版本" align="center" prop="version">
          <template slot-scope="scope">
            {{ scope.row.version }}
          </template>
        </el-table-column>
        <el-table-column label="工程负责人" align="center" prop="userNameAlias">
          <template slot-scope="scope">
            {{ scope.row.projectList.userNameAlias }}
          </template>
        </el-table-column>
        <el-table-column label="作业部门" align="center" prop="department">
          <template slot-scope="scope">
            {{ scope.row.projectList.department }}
          </template>
        </el-table-column>
        <el-table-column label="二检时间" align="center" prop="twoCheck">
          <template slot-scope="scope">
            {{ formatDate(scope.row.projectList.twoCheck) }}
          </template></el-table-column
        >
        <el-table-column label="移交时间" align="center" prop="transferTime">
          <template slot-scope="scope" v-if="scope.row.transferTime != null">
            {{ formatDate(scope.row.transferTime) }}
          </template>
          <template slot-scope="scope" v-else> </template>
        </el-table-column>
        <el-table-column label="收件时间" align="center" prop="receiveTime">
          <template slot-scope="scope" v-if="scope.row.receiveTime != null">
            {{ formatDate(scope.row.receiveTime) }}
          </template>
          <template slot-scope="scope" v-else> </template>
        </el-table-column>
        <el-table-column
          label="收件截止时间"
          align="center"
          prop="receiveCutoffTime"
        >
          <template
            slot-scope="scope"
            v-if="scope.row.receiveCutoffTime != null"
          >
            <span style="color: green">
              {{ formatDate(scope.row.receiveCutoffTime) }}</span
            >
          </template>
          <template slot-scope="scope" v-else> </template>
        </el-table-column>
        <el-table-column label="收件提前天数" align="center">
          <template slot-scope="scope">
            <el-tag
              v-show="scope.row.isArchive != 1 && scope.row.isArchive != null"
              type="success"
              >无归档</el-tag
            >
            <div
              v-show="scope.row.isArchive == 1 || scope.row.isArchive == null"
            >
              <el-tag
                type="danger"
                v-show="scope.row.projectList.receiveDays < 0"
                >{{ scope.row.projectList.receiveDays }}</el-tag
              >
              <el-tag
                type="success"
                v-show="scope.row.projectList.receiveDays >= 0"
                >{{ scope.row.projectList.receiveDays }}</el-tag
              >
            </div>
          </template></el-table-column
        >
        <el-table-column label="盖章时间" align="center" prop="stampTime">
          <template slot-scope="scope" v-if="scope.row.stampTime != null">
            {{ formatDate(scope.row.stampTime) }}
          </template>
          <template slot-scope="scope" v-else> </template>
        </el-table-column>
        <el-table-column label="验收时间" align="center" prop="checkTime">
          <template slot-scope="scope" v-if="scope.row.checkTime != null">
            {{ formatDate(scope.row.checkTime) }}
          </template>
          <template slot-scope="scope" v-else> </template>
        </el-table-column>
        <el-table-column
          label="盖章确认时间"
          align="center"
          prop="marketingTime"
        >
          <template slot-scope="scope" v-if="scope.row.marketingTime != null">
            {{ formatDate(scope.row.marketingTime) }}
          </template>
          <template slot-scope="scope" v-else> </template>
        </el-table-column>
        <el-table-column label="归档时间" align="center" prop="archiveTime">
          <template slot-scope="scope" v-if="scope.row.archiveTime != null">
            {{ formatDate(scope.row.archiveTime) }}
          </template>
          <template slot-scope="scope" v-else> </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="归档截止时间"
          align="center"
          prop="rectifyCutoffTime"
        >
          <template
            slot-scope="scope"
            v-if="scope.row.rectifyCutoffTime != null"
          >
            <span style="color: green">{{
              formatDate(scope.row.rectifyCutoffTime)
            }}</span>
          </template>
          <template slot-scope="scope" v-else> </template>
        </el-table-column>
        <el-table-column label="归档提前天数" align="center" fixed="right">
          <template slot-scope="scope">
            <el-tag
              v-show="scope.row.isArchive != 1 && scope.row.isArchive != null"
              type="success"
              >无归档</el-tag
            >
            <div
              v-show="scope.row.isArchive == 1 || scope.row.isArchive == null"
            >
              <el-tag
                type="danger"
                v-show="scope.row.projectList.archiveDays < 0"
                >{{ scope.row.projectList.archiveDays }}</el-tag
              >
              <el-tag
                type="success"
                v-show="scope.row.projectList.archiveDays >= 0"
                >{{ scope.row.projectList.archiveDays }}</el-tag
              >
            </div>
          </template></el-table-column
        >
      </el-table>
    </el-dialog>
  </div>
</template>
<style>
.hover-effect:hover {
  cursor: pointer;
}
</style>
<script>
import {
  listProjectHandover,
  getProject,
  delProject,
  addProject,
  updateProject,
  listProjectSelected,
} from "@/api/system/project";
import elDragDialog from "@/api/components/el-drag";
import { listUnit } from "@/api/system/unit";
import { addReview, setReviewStatus } from "@/api/system/reviewSub";
import userInfo from "@/store/modules/user";

export default {
  name: "Project",
  directives: {
    elDragDialog,
  },
  data() {
    return {
      overTitle: "",
      overTimeOpen: false,
      overTimeProjectList: [],
      statisticsData: [],
      yjDescription: "",
      sjDescription: "",
      gzDescription: "",
      ysDescription: "",
      gzqrDescription: "",
      gdDescription: "",
      activeProcessNum: 0,
      projectProcessTitle: "",
      projectProcessOpen: false,
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
      receiveStatusArr: [
        {
          value: 1,
          label: "未收件",
        },
        {
          value: 2,
          label: "已收件",
        },
      ],
      checkStatusArr: [
        {
          value: 2,
          label: "已归档",
        },
        {
          value: -1,
          label: "未归档",
        },
      ],
      statusArr: [
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
      queryParamsTj: {
        pageNum: 1,
        pageSize: 9999,
        checkStatus: -1,
      },
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
        status: null,
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
    this.getStatisticsData();
  },
  methods: {
    showFetailXt(value) {
      if (!value.projectNum) return false;
      const substrings = ["图", "售", "数"];
      return substrings.some((substring) =>
        value.projectNum.includes(substring)
      );
    },
    handleGeo(value) {
      this.projectCode = value.projectNum;
      // this.centerDialogVisible = true;
      window.open(
        "http://192.168.110.100/fqismap/?sysname=ViewMapInFQIS&salemapid=" +
          value.projectId,
        "_blank"
      );
    },
    getStatisticsData() {
      listProjectHandover(this.addDateRange(this.queryParamsTj)).then(
        (response) => {
          const project = response.rows;
          let myMap = new Map();
          for (let i = 0; i < project.length; i++) {
            let key = project[i].projectList.department;
            if (key == "" || key == null || key == undefined) {
              continue;
            }
            if (myMap.has(key)) {
              let num = myMap.get(key);
              if (project[i].isArchive == 1) {
                num.workCount++;
              }
              if (
                project[i].receiveStatus == 1 &&
                project[i].isArchive == 1 &&
                project[i].projectList.receiveDays < 0
              ) {
                num.receiveDays++;
              }
              if (
                project[i].checkStatus != 2 &&
                project[i].isArchive == 1 &&
                project[i].projectList.archiveDays < 0
              ) {
                num.archiveDays++;
              }
              if (
                project[i].receiveStatus == 1 &&
                project[i].isArchive == 1 &&
                project[i].projectList.receiveDays > 0 &&
                project[i].projectList.receiveDays <= 2
              ) {
                num.receiveDaysTq++;
              }
              if (
                project[i].checkStatus != 2 &&
                project[i].isArchive == 1 &&
                project[i].projectList.archiveDays > 0 &&
                project[i].projectList.archiveDays <= 2
              ) {
                num.archiveDaysTq++;
              }

              myMap.set(key, num);
            } else {
              let num = {
                workCount: 0,
                receiveDays: 0,
                archiveDays: 0,
                receiveDaysTq: 0,
                archiveDaysTq: 0,
              };
              if (project[i].isArchive == 1) {
                num.workCount++;
              }
              if (
                project[i].receiveStatus == 1 &&
                project[i].isArchive == 1 &&
                project[i].projectList.receiveDays < 0
              ) {
                num.receiveDays++;
              }
              if (
                project[i].checkStatus != 2 &&
                project[i].isArchive == 1 &&
                project[i].projectList.archiveDays < 0
              ) {
                num.archiveDays++;
              }
              if (
                project[i].receiveStatus == 1 &&
                project[i].isArchive == 1 &&
                project[i].projectList.receiveDays > 0 &&
                project[i].projectList.receiveDays <= 2
              ) {
                num.receiveDaysTq++;
              }
              if (
                project[i].checkStatus != 2 &&
                project[i].isArchive == 1 &&
                project[i].projectList.archiveDays > 0 &&
                project[i].projectList.archiveDays <= 2
              ) {
                num.archiveDaysTq++;
              }
              myMap.set(key, num);
            }
          }
          let numData = {
            status: 1,
            gcchbNumWork: 0,
            bdcchbWork: 0,
            gxgcbWork: 0,
            dlxxbWork: 0,
          };

          if (myMap.has("工程测绘部")) {
            numData.gcchbNumWork = myMap.get("工程测绘部").workCount;
          }
          if (myMap.has("不动产测绘部")) {
            numData.bdcchbWork = myMap.get("不动产测绘部").workCount;
          }
          if (myMap.has("管线工程部")) {
            numData.gxgcbWork = myMap.get("管线工程部").workCount;
          }
          if (myMap.has("地理信息部")) {
            numData.dlxxbWork = myMap.get("地理信息部").workCount;
          }
          this.statisticsData.push(numData);

          numData = {
            status: -2,
            gcchbNumWork: 0,
            bdcchbWork: 0,
            gxgcbWork: 0,
            dlxxbWork: 0,
          };

          if (myMap.has("工程测绘部")) {
            numData.gcchbNumWork = myMap.get("工程测绘部").receiveDaysTq;
          }
          if (myMap.has("不动产测绘部")) {
            numData.bdcchbWork = myMap.get("不动产测绘部").receiveDaysTq;
          }
          if (myMap.has("管线工程部")) {
            numData.gxgcbWork = myMap.get("管线工程部").receiveDaysTq;
          }
          if (myMap.has("地理信息部")) {
            numData.dlxxbWork = myMap.get("地理信息部").receiveDaysTq;
          }
          this.statisticsData.push(numData);

          numData = {
            status: -3,
            gcchbNumWork: 0,
            bdcchbWork: 0,
            gxgcbWork: 0,
            dlxxbWork: 0,
          };

          if (myMap.has("工程测绘部")) {
            numData.gcchbNumWork = myMap.get("工程测绘部").archiveDaysTq;
          }
          if (myMap.has("不动产测绘部")) {
            numData.bdcchbWork = myMap.get("不动产测绘部").archiveDaysTq;
          }
          if (myMap.has("管线工程部")) {
            numData.gxgcbWork = myMap.get("管线工程部").archiveDaysTq;
          }
          if (myMap.has("地理信息部")) {
            numData.dlxxbWork = myMap.get("地理信息部").archiveDaysTq;
          }
          this.statisticsData.push(numData);

          numData = {
            status: 0,
            gcchbNumWork: 0,
            bdcchbWork: 0,
            gxgcbWork: 0,
            dlxxbWork: 0,
          };

          if (myMap.has("工程测绘部")) {
            numData.gcchbNumWork = myMap.get("工程测绘部").receiveDays;
          }
          if (myMap.has("不动产测绘部")) {
            numData.bdcchbWork = myMap.get("不动产测绘部").receiveDays;
          }
          if (myMap.has("管线工程部")) {
            numData.gxgcbWork = myMap.get("管线工程部").receiveDays;
          }
          if (myMap.has("地理信息部")) {
            numData.dlxxbWork = myMap.get("地理信息部").receiveDays;
          }
          this.statisticsData.push(numData);

          numData = {
            status: -1,
            gcchbNumWork: 0,
            bdcchbWork: 0,
            gxgcbWork: 0,
            dlxxbWork: 0,
          };

          if (myMap.has("工程测绘部")) {
            numData.gcchbNumWork = myMap.get("工程测绘部").archiveDays;
          }
          if (myMap.has("不动产测绘部")) {
            numData.bdcchbWork = myMap.get("不动产测绘部").archiveDays;
          }
          if (myMap.has("管线工程部")) {
            numData.gxgcbWork = myMap.get("管线工程部").archiveDays;
          }
          if (myMap.has("地理信息部")) {
            numData.dlxxbWork = myMap.get("地理信息部").archiveDays;
          }
          this.statisticsData.push(numData);
        }
      );
    },
    handleOverTimeOpen(value, status) {
      if (status == 0) {
        this.overTitle = "收件超期项目";
      } else if (status == -1) {
        this.overTitle = "归档超期项目";
      } else if (status == -2) {
        this.overTitle = "收件将要超期项目数(2天内)";
      } else if (status == -3) {
        this.overTitle = "归档将要超期项目数(2天内)";
      }
      this.overTimeProjectList = [];
      this.queryParamsTj.department = value;
      listProjectHandover(this.addDateRange(this.queryParamsTj)).then(
        (response) => {
          const project = response.rows;
          for (let i = 0; i < project.length; i++) {
            let key = project[i].projectList.department;
            if (key == "" || key == null || key == undefined) {
              continue;
            }

            if (
              project[i].receiveStatus == 1 &&
              project[i].isArchive == 1 &&
              project[i].projectList.receiveDays < 0 &&
              status == 0
            ) {
              this.overTimeProjectList.push(project[i]);
            }
            if (
              project[i].receiveStatus == 1 &&
              project[i].isArchive == 1 &&
              project[i].projectList.receiveDays > 0 &&
              project[i].projectList.receiveDays <= 2 &&
              status == -2
            ) {
              this.overTimeProjectList.push(project[i]);
            }
            if (
              project[i].checkStatus != 2 &&
              project[i].isArchive == 1 &&
              project[i].projectList.archiveDays < 0 &&
              status == -1
            ) {
              this.overTimeProjectList.push(project[i]);
            }
            if (
              project[i].checkStatus != 2 &&
              project[i].isArchive == 1 &&
              project[i].projectList.archiveDays > 0 &&
              project[i].projectList.archiveDays <= 2 &&
              status == -3
            ) {
              this.overTimeProjectList.push(project[i]);
            }
          }
          this.queryParamsTj.department = null;
          this.overTimeOpen = true;
        }
      );
    },
    handleProcessDetail(projectInfo) {
      this.projectProcessTitle = projectInfo.projectList.projectNum;
      this.activeProcessNum = 0;
      this.yjDescription = "";
      this.sjDescription = "";
      this.gzDescription = "";
      this.ysDescription = "";
      this.gzqrDescription = "";
      this.gdDescription = "";
      if (
        projectInfo.isArchive != 0 &&
        projectInfo.isArchive != null &&
        projectInfo.isArchive != undefined
      ) {
        //移交
        if (projectInfo.transferStatus == 2) {
          this.activeProcessNum++;
          this.yjDescription =
            "移交人员:" +
            projectInfo.transferUserName +
            "  移交时间:" +
            this.formatDate(projectInfo.transferTime);
        }
        //收件
        if (projectInfo.receiveStatus == 2) {
          this.activeProcessNum++;
          this.sjDescription =
            "收件人员:" +
            projectInfo.receiveUserName +
            "  收件时间:" +
            this.formatDate(projectInfo.receiveTime);
        }
        //盖章
        if (projectInfo.stampStatus == 2 || projectInfo.stampStatus == 4) {
          if (projectInfo.stampStatus == 2) {
            this.activeProcessNum++;
            this.gzDescription =
              "盖章人员:" +
              projectInfo.stampUserName +
              "  盖章时间:" +
              this.formatDate(projectInfo.stampTime);
          }
          if (projectInfo.stampStatus == 4) {
            this.activeProcessNum++;
            this.gzDescription = "无需盖章";
          }
        }
        //验收
        if (projectInfo.checkStatus == 2 || projectInfo.checkStatus == 4) {
          this.activeProcessNum++;
          this.ysDescription =
            "验收人员:" +
            projectInfo.checkUserName +
            "  验收时间:" +
            this.formatDate(projectInfo.checkTime);
        }
        //盖章确认
        if (
          projectInfo.marketingConfirm == 1 ||
          ((projectInfo.checkStatus == 2 || projectInfo.checkStatus == 4) &&
            projectInfo.stampStatus == 4) ||
          projectInfo.checkStatus == 2
        ) {
          this.activeProcessNum++;
          if (projectInfo.stampStatus == 2) {
            this.gzqrDescription =
              "盖章确认人员:" +
              projectInfo.marketingUserName +
              "  盖章确认时间:" +
              this.formatDate(projectInfo.marketingTime);
          } else if (
            projectInfo.stampStatus != 2 &&
            projectInfo.checkStatus == 2
          ) {
            this.gzqrDescription = "未盖章，但已归档";
          } else {
            this.gzqrDescription = "无需盖章";
          }
        }
        //归档
        if (projectInfo.checkStatus == 2) {
          this.activeProcessNum++;
          this.gdDescription =
            "归档人员:" +
            projectInfo.archiveUserName +
            "  归档时间:" +
            this.formatDate(projectInfo.archiveTime);
        }
      } else {
        this.activeProcessNum = 0;
      }
      this.projectProcessOpen = true;
    },
    showReviewStatus(status) {
      if (
        status != 1 &&
        (userInfo.state.userId == 8 ||
          userInfo.state.userId == 9 ||
          userInfo.state.userId == 1 ||
          userInfo.state.userId == 11 ||
          userInfo.state.userId == 12 ||
          userInfo.state.userId == 13 ||
          userInfo.state.userId == 14 ||
          userInfo.state.userId == 15)
      ) {
        return true;
      } else {
        return false;
      }
    },
    formatDate(dateString) {
      if (dateString == "" || dateString == null || dateString == undefined) {
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

      this.formReviewSub.subpackageType = projectInfo.subpackageType;

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
      listProjectHandover(
        this.addDateRange(this.queryParams, this.dateRange)
      ).then((response) => {
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
      });
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
      const projectId = row.projectId;
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
