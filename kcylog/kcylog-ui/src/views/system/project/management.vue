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
      <el-form-item label="分包单位" prop="winUnit">
        <el-select
          v-model="queryParams.winUnit"
          placeholder="请选择"
          style="width: 115px"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="项目名称" prop="projectNameAlias">
        <el-input
          v-model="queryParams.projectNameAlias"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 115px"
        />
      </el-form-item>
      <el-form-item label="项目编号" prop="projectNum">
        <el-input
          v-model="queryParams.projectNum"
          placeholder="请输入项目编号"
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 115px"
        />
      </el-form-item>

      <el-form-item label="作业部门" prop="department">
        <el-select
          v-model="queryParams.department"
          placeholder="请选择部门"
          clearable
          style="width: 115px"
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
          style="width: 115px"
        />
      </el-form-item>
      <el-form-item label="工作状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择"
          style="width: 115px"
        >
          <el-option
            v-for="item in statusList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="安排时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 200px"
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
      <el-table-column prop="status" label="类型（二检办结前）" align="center">
        <template slot-scope="scope">
          <el-tag type="success" v-show="scope.row.status == 0"
            >分包项目数｜审核单数｜抽签数</el-tag
          >
          <el-tag v-show="scope.row.status == 1" type="success"
            >汇创｜越扬｜易测｜地星｜金地</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="工程测绘部" align="center">
        <template slot-scope="scope">
          <el-tag type="success" v-show="scope.row.status == 0"
            >{{ scope.row.gcxms }} | {{ scope.row.gcsps }} |
            {{ scope.row.gccqs }}</el-tag
          >
          <el-tag type="success" v-show="scope.row.status == 1"
            >{{ scope.row.gctjnum }} | {{ scope.row.gcyynum }} |
            {{ scope.row.gcycnum }} | {{ scope.row.gcdxnum }} |
            {{ scope.row.gcjdnum }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="不动产测绘部" align="center">
        <template slot-scope="scope">
          <el-tag type="success" v-show="scope.row.status == 0"
            >{{ scope.row.bdxms }} | {{ scope.row.bdsps }} |
            {{ scope.row.bdcqs }}</el-tag
          >
          <el-tag type="success" v-show="scope.row.status == 1"
            >{{ scope.row.bdtjnum }} | {{ scope.row.bdyynum }} |
            {{ scope.row.bdycnum }} | {{ scope.row.bddxnum }} |
            {{ scope.row.bdjdnum }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="管线工程部" align="center">
        <template slot-scope="scope">
          <el-tag type="success" v-show="scope.row.status == 0"
            >{{ scope.row.gxxms }} | {{ scope.row.gxsps }} |
            {{ scope.row.gxcqs }}</el-tag
          >
          <el-tag type="success" v-show="scope.row.status == 1"
            >{{ scope.row.gxtjnum }} | {{ scope.row.gxyynum }} |
            {{ scope.row.gxycnum }} | {{ scope.row.gxdxnum }} |
            {{ scope.row.gxjdnum }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="地理信息部" align="center">
        <template slot-scope="scope">
          <el-tag type="success" v-show="scope.row.status == 0"
            >{{ scope.row.dlxms }} | {{ scope.row.dlsps }} |
            {{ scope.row.dlcqs }}</el-tag
          >
          <el-tag type="success" v-show="scope.row.status == 1"
            >{{ scope.row.dltjnum }} | {{ scope.row.dlyynum }} |
            {{ scope.row.dlycnum }} | {{ scope.row.dldxnum }} |
            {{ scope.row.dljdnum }}</el-tag
          >
        </template>
      </el-table-column>
    </el-table>

    <el-table :data="statisticsWinUnit" style="width: 100%">
      <el-table-column prop="status" label="项目数" align="center">
        <template slot-scope="scope">
          <el-tag type="success">作业中｜待一检｜待二检</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="天津汇创" align="center">
        <template slot-scope="scope">
          <el-tag type="success"
            >{{ scope.row.tjnum }} | {{ scope.row.tjnumone }} |
            {{ scope.row.tjnumtwo }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="福建越扬" align="center">
        <template slot-scope="scope">
          <el-tag type="success"
            >{{ scope.row.yynum }} | {{ scope.row.yynumone }} |
            {{ scope.row.yynumtwo }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="易测工程" align="center">
        <template slot-scope="scope">
          <el-tag type="success"
            >{{ scope.row.ycnum }} | {{ scope.row.ycnumone }} |
            {{ scope.row.ycnumtwo }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="地星测绘" align="center">
        <template slot-scope="scope">
          <el-tag type="success"
            >{{ scope.row.dxnum }} | {{ scope.row.dxnumone }} |
            {{ scope.row.dxnumtwo }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="金地勘测" align="center">
        <template slot-scope="scope">
          <el-tag type="success"
            >{{ scope.row.jdnum }} | {{ scope.row.jdnumone }} |
            {{ scope.row.jdnumtwo }}</el-tag
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
        label="分包单位"
        align="center"
        prop="winUnit"
        width="130px"
        fixed
      >
        <template slot-scope="scope">
          {{
            scope.row.reviewSub && scope.row.reviewSub.length > 0
              ? scope.row.reviewSub[0].winUnit
              : ""
          }}
        </template>
      </el-table-column>
      <el-table-column
        label="作业部门"
        align="center"
        prop="department"
        width="100px"
      />
      <el-table-column
        label="委托单位"
        align="center"
        prop="requesterAlias"
        width="200px"
      />
      <el-table-column
        label="项目名称"
        align="center"
        prop="projectNameAlias"
        width="200px"
      />
      <el-table-column
        label="项目编号"
        align="center"
        prop="projectNum"
        width="100px"
      />
      <el-table-column label="负责人" align="center" prop="userNameAlias" />
      <!-- <el-table-column label="项目类型" align="center" prop="projectType" />
          <el-table-column
            label="工程内容"
            align="center"
            prop="workcontentAlias"
          /> -->
      <el-table-column
        label="安排开始时间"
        align="center"
        prop="projectStartAlias"
        width="85px"
      >
        <template slot-scope="scope">
          {{ formatDate(scope.row.projectStartAlias) }}
        </template></el-table-column
      >
      <el-table-column
        label="安排结束时间"
        align="center"
        prop="projectEndAlias"
        width="85px"
      >
        <template slot-scope="scope">
          {{ formatDate(scope.row.projectEndAlias) }}
        </template></el-table-column
      >
      <el-table-column
        label="作业办结时间"
        align="center"
        prop="doTime"
        width="85px"
      >
        <template slot-scope="scope">
          {{ homeworkCompleted(scope.row) }}
        </template></el-table-column
      >
      <el-table-column label="提前工期" align="center" width="57px">
        <template slot-scope="scope">
          <el-tag type="danger" v-show="scope.row.leadTime < 0">{{
            scope.row.leadTime
          }}</el-tag>
          <el-tag type="success" v-show="scope.row.leadTime >= 0">{{
            scope.row.leadTime
          }}</el-tag>
        </template></el-table-column
      >
      <el-table-column
        label="工作状态"
        align="center"
        prop="status"
        width="100px"
      >
        <template slot-scope="scope">
          <el-tag v-show="scope.row.status == 0" type="info">临时安排</el-tag>
          <el-tag v-show="scope.row.status == 1">正式安排</el-tag>
          <el-tag v-show="scope.row.status == 2" type="success"
            >一检办结</el-tag
          >
          <el-tag v-show="scope.row.status == 3" type="success"
            >二检办结</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column
        label="抽签过程"
        align="center"
        prop="drawStatus"
        width="57px"
      >
        <template slot-scope="scope">
          <el-tag v-show="scope.row.drawStatus == 0" type="danger">无</el-tag>
          <el-tag v-show="scope.row.drawStatus == 1" type="success">有</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="雇工分包" align="center">
        <el-table-column
          label="状态"
          align="center"
          prop="subpackageType"
          width="120px"
        >
          <template slot-scope="scope">
            <el-tag v-show="scope.row.subpackageType == 0" type="danger"
              >未设置</el-tag
            >
            <el-tag v-show="scope.row.subpackageType == 1" type="info"
              >非分包</el-tag
            >
            <el-tag v-show="scope.row.subpackageType == 2" type="success"
              >单一合同分包</el-tag
            >
            <el-tag v-show="scope.row.subpackageType == 3" type="success"
              >框架协议分包</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="审核单状态" align="center" width="110px">
          <template slot-scope="scope">
            <el-tag
              v-show="scope.row.issq == 1"
              type="success"
              class="hover-effect"
              @click="showReviewInfo(scope.row)"
              >已有审核单</el-tag
            >
            <el-tag v-show="showReviewStatus(scope.row.issq)" type="info"
              >未有审核单</el-tag
            >
          </template>
        </el-table-column>
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
            >详情</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleLotteryProcess(scope.row)"
            v-hasPermi="['system:project:lotteryProcess']"
            >抽签过程</el-button
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

            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document"></i>
                办结超期备注
              </template>
              {{ form.completionNotes }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document"></i>
                一检超期备注
              </template>
              {{ form.oneCheckNotes }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document"></i>
                二检超期备注
              </template>
              {{ form.twoCheckNotes }}
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

    <el-dialog
      title="抽签过程"
      :visible.sync="cqgcOpen"
      width="500px"
      append-to-body
    >
      <el-form ref="cqgcForm" :model="cqgcForm" label-width="80px">
        <el-form-item label="抽签状态" prop="drawStatus">
          <el-select v-model="cqgcForm.drawStatus" placeholder="请选择">
            <el-option
              v-for="item in drawList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCqgcForm">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :title="titleInfo"
      :visible.sync="openInfo"
      width="1260px"
      append-to-body
      v-el-drag-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-collapse v-model="activeNamesInfo">
        <div>
          <el-row :gutter="10">
            <el-col style="width: 50%">
              <el-card class="box-card">
                <div slot="header" class="clearfix">
                  <span>项目详情</span>
                </div>
                <div
                  v-if="!formInfo.project || !formInfo.project.projectNum"
                  style="text-align: center"
                >
                  未找到关联项目数据～
                </div>
                <div v-if="formInfo.project && formInfo.project.projectNum">
                  <el-descriptions class="margin-top" :column="2">
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-s-home"></i>
                        委托单位
                      </template>
                      {{ formInfo.project.requesterAlias }}
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
                        <i class="el-icon-office-building"></i>
                        项目名称
                      </template>
                      {{ formInfo.project.projectNameAlias }}
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
                        <i class="el-icon-notebook-2"></i>
                        项目类型
                      </template>
                      {{ formInfo.project.projectType }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-notebook-2"></i>
                        作业部门
                      </template>
                      {{ formInfo.project.department }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-time"></i>
                        登记时间
                      </template>
                      {{ formatDateReviewSub(formInfo.project.registerTime) }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-user"></i>
                        接待人
                      </template>
                      {{ formInfo.project.receptionist }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-time"></i>
                        安排开始时间
                      </template>
                      {{
                        formatDateReviewSub(formInfo.project.projectStartAlias)
                      }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-time"></i>
                        安排结束时间
                      </template>
                      {{
                        formatDateReviewSub(formInfo.project.projectEndAlias)
                      }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-time"></i>
                        一检时间
                      </template>
                      {{ formatDateReviewSub(formInfo.project.oneCheck) }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-time"></i>
                        二检时间
                      </template>
                      {{ formatDateReviewSub(formInfo.project.twoCheck) }}
                    </el-descriptions-item>
                  </el-descriptions>
                  <el-descriptions class="margin-top" :column="1">
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-document"></i>
                        工程内容
                      </template>
                      {{ formInfo.project.workcontentAlias }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-document"></i>
                        工作量
                      </template>
                      {{ formInfo.project.workloadAlias }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label">
                        <i class="el-icon-money"></i>
                        项目预估金额
                      </template>
                      {{ formInfo.porjectMoney }}
                    </el-descriptions-item>
                  </el-descriptions>
                </div>
              </el-card>
            </el-col>
            <el-col style="width: 50%">
              <el-card class="box-card">
                <div slot="header" class="clearfix">
                  <span>审核单详情</span>
                </div>

                <el-collapse-item name="5">
                  <template slot="title">
                    分包详情<i>
                      <el-tag
                        size="mini"
                        v-if="formInfo.subpackageType == 0"
                        type="danger"
                        >未设置</el-tag
                      >
                      <el-tag
                        size="mini"
                        v-else-if="formInfo.subpackageType == 1"
                        type="danger"
                        >非分包</el-tag
                      >
                      <el-tag
                        size="mini"
                        v-else-if="formInfo.subpackageType == 2"
                        type="success"
                        >单一合同分包</el-tag
                      >
                      <el-tag
                        size="mini"
                        v-else-if="formInfo.subpackageType == 3"
                        type="success"
                        >框架协议分包</el-tag
                      >
                      <el-tag size="mini" v-else type="danger">其他状态</el-tag>
                    </i>
                  </template>
                  <el-card>
                    <div style="text-align: center">
                      <el-descriptions class="margin-top" :column="2">
                        <el-descriptions-item>
                          <template slot="label"> 分包类型 </template>
                          <span v-if="subcontractForm.subType == 1"
                            >全部分包</span
                          ><span v-if="subcontractForm.subType == 2"
                            >局部分包</span
                          >
                        </el-descriptions-item>
                        <el-descriptions-item>
                          <template slot="label"> 抽签过程 </template>
                          <el-tag
                            v-if="formInfo.project.drawStatus == 0"
                            type="danger"
                            size="mini"
                            >无</el-tag
                          >
                          <el-tag
                            v-if="formInfo.project.drawStatus == 1"
                            type="success"
                            size="mini"
                            >有</el-tag
                          >
                        </el-descriptions-item>
                        <el-descriptions-item>
                          <template slot="label"> 抽签时间 </template>
                          {{
                            parseTime(subcontractForm.lotTime, "{y}-{m}-{d}")
                          }}
                        </el-descriptions-item>
                        <el-descriptions-item>
                          <template slot="label"> 抽签业务名称 </template>
                          {{ subcontractForm.businessName }}
                        </el-descriptions-item>
                      </el-descriptions>
                      <el-descriptions class="margin-top" :column="1">
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
                          <template slot="label"> 预估分包工作量 </template>
                          {{ subcontractForm.subWorkload }}
                        </el-descriptions-item>
                      </el-descriptions>
                    </div>
                  </el-card>
                </el-collapse-item>

                <el-collapse-item name="6">
                  <template slot="title">
                    雇工详情<i>
                      <el-tag
                        size="mini"
                        v-if="formInfo.manType == 0"
                        type="danger"
                        >非雇工</el-tag
                      >

                      <el-tag
                        size="mini"
                        v-else-if="formInfo.manType == 1"
                        type="success"
                        >雇工</el-tag
                      >
                      <el-tag
                        size="mini"
                        v-else-if="formInfo.manType == 2"
                        type="success"
                        >第三方雇工</el-tag
                      >
                    </i>
                  </template>

                  <el-card>
                    <el-descriptions class="margin-top" :column="2">
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
                      <el-descriptions-item>
                        <template slot="label"> 雇工方式 </template>
                        <span v-if="formInfo.manType === 0"
                          ><el-tag type="danger" size="mini">非雇工</el-tag>
                        </span>
                        <span v-if="formInfo.manType === 1"
                          ><el-tag type="success" size="mini"
                            >雇工</el-tag
                          ></span
                        >
                        <span v-if="formInfo.manType === 2"
                          ><el-tag type="success" size="mini"
                            >第三方雇工</el-tag
                          ></span
                        >
                      </el-descriptions-item>
                    </el-descriptions>
                    <el-descriptions class="margin-top" :column="1">
                      <el-descriptions-item>
                        <template slot="label"> 雇工内容 </template>
                        {{ formInfo.employmentReason }}
                      </el-descriptions-item>
                    </el-descriptions>
                  </el-card>
                </el-collapse-item>
              </el-card>
            </el-col>
          </el-row>
        </div>

        <el-collapse-item title="雇工信息详情" name="1">
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
  </div>
</template>
<style>
.hover-effect:hover {
  cursor: pointer;
}
</style>

<script>
import {
  listProjectForFb,
  listProjectForFbTwoCheck,
  getProject,
  listProjectSelected,
  updateProjectDrawStatus,
} from "@/api/system/project";
import { listEmployee } from "@/api/system/reviewEmployeeSub";
import { getReview } from "@/api/system/reviewSub";
import elDragDialog from "@/api/components/el-drag";
import { listUnit } from "@/api/system/unit";
import userInfo from "@/store/modules/user";

export default {
  name: "Project",
  directives: {
    elDragDialog,
  },
  data() {
    return {
      statisticsWinUnit: [],
      employeeList: [],
      queryParamsEmployee: {
        pageNum: 1,
        pageSize: 9999,
        reviewId: null,
      },
      subcontractForm: {
        user: {
          userName: "",
        },
      },
      activeNamesInfo: ["1", "2", "3"],
      openInfo: false,
      titleInfo: "",
      formInfo: {
        user: {
          userName: "",
        },
        project: {
          projectNameAlias: "",
          projectNum: "",
          projectType: "",
          registerTime: "",
          receptionist: "",
          workloadAlias: "",
          userNameAlias: "",
          requesterAlias: "",
          projectStartAlias: null,
          projectEndAlias: null,
          oneCheck: "",
          twoCheck: "",
          noticeTime: "",
          projectTime: "",
          deliveryTime: "",
          projectMoneyAlias: null,
          operate: null,
          operateUser: "",
          operateTime: "",
        },
      },
      options: [
        {
          value: "天津汇创测绘技术有限公司",
          label: "天津汇创测绘技术有限公司",
        },
        {
          value: "福建越扬信息科技有限公司",
          label: "福建越扬信息科技有限公司",
        },
        {
          value: "福建省易测工程勘测有限公司",
          label: "福建省易测工程勘测有限公司",
        },
        {
          value: "黑龙江省地星测绘科技股份有限公司",
          label: "黑龙江省地星测绘科技股份有限公司",
        },
        {
          value: "武汉大势智慧科技有限公司",
          label: "武汉大势智慧科技有限公司",
        },
        {
          value: "福州翔飞航空科技有限公司",
          label: "福州翔飞航空科技有限公司",
        },
        {
          value: "福建金地勘测规划有限公司",
          label: "福建金地勘测规划有限公司",
        },
      ],
      drawList: [
        {
          value: 0,
          label: "无",
        },
        {
          value: 1,
          label: "有",
        },
      ],
      cqgcOpen: false,
      cqgcForm: {},
      formReviewCq: {},
      cqOpen: false,
      overTimeOpen: false,
      manTypes: [
        { value: 0, label: "非雇工" },
        { value: 1, label: "雇工" },
        { value: 2, label: "第三方雇工" },
      ],
      statisticsData: [],
      centerDialogVisible: false,
      projectCode: "",
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
          value: 1,
          label: "新增作业",
        },
        {
          value: 2,
          label: "作业中",
        },
        {
          value: 3,
          label: "作业完成",
        },
        {
          value: 4,
          label: "作业办结",
        },
      ],
      statusList: [
        {
          value: 0,
          label: "临时安排",
        },
        {
          value: 1,
          label: "正式安排",
        },
        {
          value: -1,
          label: "作业办结",
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
      projectListTj: [],
      overTimeProjectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryStatisticsParams: {
        pageNum: 1,
        pageSize: 9999,
      },
      queryOverTimeParams: {
        pageNum: 1,
        pageSize: 9999,
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
    this.getListToTj();
  },
  methods: {
    getListToTj() {
      this.getReviewProject();
      this.loading = true;
      listProjectForFbTwoCheck(this.queryStatisticsParams).then((response) => {
        this.$nextTick(() => {
          this.projectListTj = [];
          this.projectListTj = response.rows;
          this.listProjectLocalMap = new Map();
          for (var i = 0; i < response.rows.length; i++) {
            this.listProjectLocalMap.set(
              response.rows[i].projectId,
              response.rows[i]
            );
          }
          let data = {
            status: 0,
            gcxms: 0,
            gcsps: 0,
            gccqs: 0,
            bdxms: 0,
            bdsps: 0,
            bdcqs: 0,
            gxxms: 0,
            gxsps: 0,
            gxcqs: 0,
            dlxms: 0,
            dlsps: 0,
            dlcqs: 0,
            tjnum: 0,
            yynum: 0,
            ycnum: 0,
            dxnum: 0,
            dsnum: 0,
            xfnum: 0,
            jdnum: 0,

            tjnumone: 0,
            yynumone: 0,
            ycnumone: 0,
            dxnumone: 0,
            dsnumone: 0,
            xfnumone: 0,
            jdnumone: 0,

            tjnumtwo: 0,
            yynumtwo: 0,
            ycnumtwo: 0,
            dxnumtwo: 0,
            dsnumtwo: 0,
            xfnumtwo: 0,
            jdnumtwo: 0,
          };

          let data1 = {
            status: 1,
            gctjnum: 0,
            gcyynum: 0,
            gcycnum: 0,
            gcdxnum: 0,
            gcjdnum: 0,

            bdtjnum: 0,
            bdyynum: 0,
            bdycnum: 0,
            bddxnum: 0,
            bdjdnum: 0,

            gxtjnum: 0,
            gxyynum: 0,
            gxycnum: 0,
            gxdxnum: 0,
            gxjdnum: 0,

            dltjnum: 0,
            dlyynum: 0,
            dlycnum: 0,
            dldxnum: 0,
            dljdnum: 0,
          };
          for (var j = 0; j < this.projectListTj.length; j++) {
            switch (this.projectListTj[j].department) {
              case "工程测绘部":
                data.gcxms++;
                if (this.projectListTj[j].drawStatus == 1) {
                  data.gccqs++;
                }
                if (this.projectIdMap.has(this.projectListTj[j].projectId)) {
                  data.gcsps++;
                }

                if (
                  this.projectListTj[j].reviewSub[0] &&
                  this.projectListTj[j].reviewSub[0].winUnit
                ) {
                  switch (this.projectListTj[j].reviewSub[0].winUnit) {
                    case "天津汇创测绘技术有限公司":
                      data1.gctjnum++;
                      break;
                    case "福建越扬信息科技有限公司":
                      data1.gcyynum++;
                      break;
                    case "福建省易测工程勘测有限公司":
                      data1.gcycnum++;
                      break;
                    case "黑龙江省地星测绘科技股份有限公司":
                      data1.gcdxnum++;
                      break;
                    case "福建金地勘测规划有限公司":
                      data1.gcjdnum++;
                      break;
                  }
                }

                break;
              case "不动产测绘部":
                data.bdxms++;
                if (this.projectListTj[j].drawStatus == 1) {
                  data.bdcqs++;
                }
                if (this.projectIdMap.has(this.projectListTj[j].projectId)) {
                  data.bdsps++;
                }

                if (
                  this.projectListTj[j].reviewSub[0] &&
                  this.projectListTj[j].reviewSub[0].winUnit
                ) {
                  switch (this.projectListTj[j].reviewSub[0].winUnit) {
                    case "天津汇创测绘技术有限公司":
                      data1.bdtjnum++;
                      break;
                    case "福建越扬信息科技有限公司":
                      data1.bdyynum++;
                      break;
                    case "福建省易测工程勘测有限公司":
                      data1.bdycnum++;
                      break;
                    case "黑龙江省地星测绘科技股份有限公司":
                      data1.bddxnum++;
                      break;
                    case "福建金地勘测规划有限公司":
                      data1.bdjdnum++;
                      break;
                  }
                }
                break;
              case "管线工程部":
                data.gxxms++;
                if (this.projectListTj[j].drawStatus == 1) {
                  data.gxcqs++;
                }
                if (this.projectIdMap.has(this.projectListTj[j].projectId)) {
                  data.gxsps++;
                }

                if (
                  this.projectListTj[j].reviewSub[0] &&
                  this.projectListTj[j].reviewSub[0].winUnit
                ) {
                  switch (this.projectListTj[j].reviewSub[0].winUnit) {
                    case "天津汇创测绘技术有限公司":
                      data1.gxtjnum++;
                      break;
                    case "福建越扬信息科技有限公司":
                      data1.gxyynum++;
                      break;
                    case "福建省易测工程勘测有限公司":
                      data1.gxycnum++;
                      break;
                    case "黑龙江省地星测绘科技股份有限公司":
                      data1.gxdxnum++;
                      break;
                    case "福建金地勘测规划有限公司":
                      data1.gxjdnum++;
                      break;
                  }
                }
                break;
              case "地理信息部":
                data.dlxms++;
                if (this.projectListTj[j].drawStatus == 1) {
                  data.dlcqs++;
                }
                if (this.projectIdMap.has(this.projectListTj[j].projectId)) {
                  data.dlsps++;
                }
                if (
                  this.projectListTj[j].reviewSub[0] &&
                  this.projectListTj[j].reviewSub[0].winUnit
                ) {
                  switch (this.projectListTj[j].reviewSub[0].winUnit) {
                    case "天津汇创测绘技术有限公司":
                      data1.dltjnum++;
                      break;
                    case "福建越扬信息科技有限公司":
                      data1.dlyynum++;
                      break;
                    case "福建省易测工程勘测有限公司":
                      data1.dlycnum++;
                      break;
                    case "黑龙江省地星测绘科技股份有限公司":
                      data1.dldxnum++;
                      break;
                    case "福建金地勘测规划有限公司":
                      data1.dljdnum++;
                      break;
                  }
                }
                break;
            }

            if (this.projectIdMap.has(this.projectListTj[j].projectId)) {
              switch (this.projectListTj[j].reviewSub[0].winUnit) {
                case "天津汇创测绘技术有限公司":
                  if (this.projectListTj[j].workStatus == 2) {
                    data.tjnum++;
                  }
                  if (
                    this.projectListTj[j].workStatus == 4 &&
                    this.projectListTj[j].status == 1
                  ) {
                    data.tjnumone++;
                  }
                  if (this.projectListTj[j].status == 2) {
                    data.tjnumtwo++;
                  }
                  break;
                case "福建越扬信息科技有限公司":
                  if (this.projectListTj[j].workStatus == 2) {
                    data.yynum++;
                  }
                  if (
                    this.projectListTj[j].workStatus == 4 &&
                    this.projectListTj[j].status == 1
                  ) {
                    data.yynumone++;
                  }
                  if (this.projectListTj[j].status == 2) {
                    data.yynumtwo++;
                  }
                  break;
                case "福建省易测工程勘测有限公司":
                  if (this.projectListTj[j].workStatus == 2) {
                    data.ycnum++;
                  }
                  if (
                    this.projectListTj[j].workStatus == 4 &&
                    this.projectListTj[j].status == 1
                  ) {
                    data.ycnumone++;
                  }
                  if (this.projectListTj[j].status == 2) {
                    data.ycnumtwo++;
                  }
                  break;
                case "黑龙江省地星测绘科技股份有限公司":
                  if (this.projectListTj[j].workStatus == 2) {
                    data.dxnum++;
                  }
                  if (
                    this.projectListTj[j].workStatus == 4 &&
                    this.projectListTj[j].status == 1
                  ) {
                    data.dxnumone++;
                  }
                  if (this.projectListTj[j].status == 2) {
                    data.dxnumtwo++;
                  }
                  break;

                case "武汉大势智慧科技有限公司":
                  if (this.projectListTj[j].workStatus == 2) {
                    data.dsnum++;
                  }
                  if (
                    this.projectListTj[j].workStatus == 4 &&
                    this.projectListTj[j].status == 1
                  ) {
                    data.dsnumone++;
                  }
                  if (this.projectListTj[j].status == 2) {
                    data.dsnumtwo++;
                  }
                  break;

                case "福州翔飞航空科技有限公司":
                  if (this.projectListTj[j].workStatus == 2) {
                    data.xfnum++;
                  }
                  if (
                    this.projectListTj[j].workStatus == 4 &&
                    this.projectListTj[j].status == 1
                  ) {
                    data.xfnumone++;
                  }
                  if (this.projectListTj[j].status == 2) {
                    data.xfnumtwo++;
                  }
                  break;

                case "福建金地勘测规划有限公司":
                  if (this.projectListTj[j].workStatus == 2) {
                    data.jdnum++;
                  }
                  if (
                    this.projectListTj[j].workStatus == 4 &&
                    this.projectListTj[j].status == 1
                  ) {
                    data.jdnumone++;
                  }
                  if (this.projectListTj[j].status == 2) {
                    data.jdnumtwo++;
                  }
                  break;
              }
            }
          }
          this.statisticsData.push(data);
          this.statisticsData.push(data1);
          this.statisticsWinUnit.push(data);
        });
      });
    },
    formatDateReviewSub(dateString) {
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
    showReviewInfo(row) {
      const reviewId = row.reviewSub[0].reviewId;
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
        this.activeNamesInfo = ["2", "3", "4"];
        if (
          this.formInfo.subpackageType != 0 &&
          this.formInfo.subpackageType != 1
        ) {
          this.activeNamesInfo.push("5");
        }
        if (this.formInfo.manType != 0) {
          this.activeNamesInfo.push("1");
          this.activeNamesInfo.push("6");
        }
        if (this.formInfo.subcontract == 0) {
          this.formInfo.subcontract = null;
        }
        this.subcontractForm = response.data;
        this.openInfo = true;
        this.titleInfo = "详情";
      });
      this.queryParamsEmployee.reviewId = reviewId;
      listEmployee(this.queryParamsEmployee).then((response) => {
        this.employeeList = response.rows;
      });
    },
    showFetailXt(value) {
      if (!value.projectNum) return false;
      const substrings = ["图", "售", "数"];
      return substrings.some((substring) =>
        value.projectNum.includes(substring)
      );
    },
    handleLotteryProcess(value) {
      this.cqgcForm.projectId = value.projectId;
      this.cqgcOpen = true;
    },
    submitCqgcForm() {
      this.$refs["cqgcForm"].validate((valid) => {
        updateProjectDrawStatus(this.cqgcForm).then((response) => {
          this.$modal.msgSuccess("修改成功");
          this.cqgcOpen = false;
          this.cqgcForm = {};
          this.getList();
        });
      });
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
    homeworkCompleted(value) {
      var doTime = this.formatDate(value.doTime);
      if (value.workStatus == 4) {
        return doTime;
      } else {
        return "";
      }
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

    cancelReviewSub() {
      this.openReviewSub = false;
      this.formReviewSub = {};
    },

    /** 查询项目列表 */
    getList() {
      this.getReviewProject();
      this.loading = true;
      listProjectForFb(
        this.addDateRange(this.queryParams, this.dateRange)
      ).then((response) => {
        this.$nextTick(() => {
          this.projectList = [];
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
            } else {
              this.projectList[j].issq = 0;
            }
          }
        });
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
      const projectId = row.projectId || this.ids;
      getProject(projectId).then((response) => {
        this.form = response.data;
        this.detailOpen = true;
      });
    },
  },
};
</script>
