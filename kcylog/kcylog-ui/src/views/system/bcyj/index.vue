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
      <el-form-item label="项目编号" prop="projectCode">
        <el-input
          v-model="queryParams.projectCode"
          placeholder="请输入项目编号"
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
      <el-form-item label="委托单位" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入委托单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="managerUserName">
        <el-input
          v-model="queryParams.managerUserName"
          placeholder="请输入负责人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="部门" prop="jobOrgName">
        <el-input
          v-model="queryParams.jobOrgName"
          placeholder="请输入部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收件状态" prop="receiveStatus">
        <el-select v-model="queryParams.receiveStatus" placeholder="请选择">
          <el-option
            v-for="item in receiveStatusS"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="盖章状态" prop="stampStatus">
        <el-select v-model="queryParams.stampStatus" placeholder="请选择">
          <el-option
            v-for="item in stampStatusS"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="验收状态" prop="checkStatus">
        <el-select v-model="queryParams.checkStatus" placeholder="请选择">
          <el-option
            v-for="item in checkStatusS"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
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
          <el-tag v-show="scope.row.status == 1" type="danger"
            >收件将要超期项目数(2天内)</el-tag
          >
          <el-tag v-show="scope.row.status == 2" type="danger"
            >整改将要超期项目数(2天内)</el-tag
          >
          <el-tag v-show="scope.row.status == 3" type="danger"
            >收件超期项目数</el-tag
          >
          <el-tag v-show="scope.row.status == 4" type="danger"
            >整改超期项目数</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="gcchbNumWork" label="工程测绘部" align="center">
        <template slot-scope="scope">
          <el-tag
            v-show="scope.row.status == 1"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('工程测绘部', 1)"
            >{{ scope.row.gcchbNumWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == 2"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('工程测绘部', 2)"
            >{{ scope.row.gcchbNumWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == 3"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('工程测绘部', 3)"
            >{{ scope.row.gcchbNumWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == 4"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('工程测绘部', 4)"
            >{{ scope.row.gcchbNumWork }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="bdcchbWork" label="不动产测绘部" align="center">
        <template slot-scope="scope">
          <el-tag
            v-show="scope.row.status == 1"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('不动产测绘部', 1)"
            >{{ scope.row.bdcchbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == 2"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('不动产测绘部', 2)"
            >{{ scope.row.bdcchbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == 3"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('不动产测绘部', 3)"
            >{{ scope.row.bdcchbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == 4"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('不动产测绘部', 4)"
            >{{ scope.row.bdcchbWork }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="gxgcbWork" label="管线工程部" align="center">
        <template slot-scope="scope">
          <el-tag
            v-show="scope.row.status == 1"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('管线工程部', 1)"
            >{{ scope.row.gxgcbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == 2"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('管线工程部', 2)"
            >{{ scope.row.gxgcbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == 3"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('管线工程部', 3)"
            >{{ scope.row.gxgcbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == 4"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('管线工程部', 4)"
            >{{ scope.row.gxgcbWork }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="dlxxbWork" label="地理信息部" align="center">
        <template slot-scope="scope">
          <el-tag
            v-show="scope.row.status == 1"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('地理信息部', 1)"
            >{{ scope.row.dlxxbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == 2"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('地理信息部', 2)"
            >{{ scope.row.dlxxbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == 3"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('地理信息部', 3)"
            >{{ scope.row.dlxxbWork }}</el-tag
          >
          <el-tag
            v-show="scope.row.status == 4"
            type="danger"
            class="hover-effect"
            @click="handleOverTimeOpen('地理信息部', 4)"
            >{{ scope.row.dlxxbWork }}</el-tag
          >
        </template>
      </el-table-column>
    </el-table>

    <el-table
      v-loading="loading"
      :data="bcyjList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="项目编号" align="center" prop="projectCode" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="收件状态" align="center" prop="receiveStatus">
        <template slot-scope="scope">
          <el-tag v-show="scope.row.receiveStatus == 1" type="danger"
            >未收件</el-tag
          >
          <el-tag v-show="scope.row.receiveStatus == 2" type="success"
            >收件办结</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column label="盖章状态" align="center" prop="stampStatus">
        <template slot-scope="scope">
          <el-tag v-show="scope.row.stampStatus == 1" type="danger"
            >未盖章</el-tag
          >
          <el-tag v-show="scope.row.stampStatus == 2" type="success"
            >已盖章</el-tag
          >
          <el-tag
            v-show="scope.row.stampStatus != 1 && scope.row.stampStatus != 2"
            type="info"
            >无需盖章</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column label="验收状态" align="center" prop="checkStatus">
        <template slot-scope="scope">
          <el-tag v-show="scope.row.checkStatus == 1" type="danger"
            >未验收</el-tag
          >
          <el-tag v-show="scope.row.checkStatus == 2" type="success"
            >验收办结</el-tag
          >
          <el-tag v-show="scope.row.checkStatus == 3" type="warning"
            >验收拒绝</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column label="委托单位" align="center" prop="customerName" />
      <el-table-column label="负责人" align="center" prop="managerUserName" />
      <el-table-column label="部门" align="center" prop="jobOrgName" />

      <el-table-column
        label="二检通过日期"
        align="center"
        prop="secondCheckTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.secondCheckTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="收件截止时间"
        align="center"
        prop="receiveCutoffTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.receiveCutoffTime, "{y}-{m}-{d}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="移交人" align="center" prop="yjUserName" />
      <el-table-column label="移交日期" align="center" prop="yjsj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.yjsj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="收件人" align="center" prop="sjUserName" />
      <el-table-column label="收件日期" align="center" prop="sjsj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sjsj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="退回整改日期"
        align="center"
        prop="zgsj"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.zgsj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="整改截止时间"
        align="center"
        prop="rectifyCutoffTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.rectifyCutoffTime, "{y}-{m}-{d}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="盖章日期" align="center" prop="gzsj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.gzsj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="验收人" align="center" prop="ysUserName" />
      <el-table-column label="验收日期" align="center" prop="yssj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.yssj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="是否有归档资料"
        align="center"
        prop="isArchive"
        width="111"
      >
        <template slot-scope="scope">
          <el-tag v-show="scope.row.isArchive == 0" type="danger">否</el-tag>
          <el-tag v-show="scope.row.isArchive == 1" type="warning">是</el-tag>
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
        <el-table-column label="项目编号" align="center" prop="projectCode" />
        <el-table-column label="项目名称" align="center" prop="projectName" />
        <el-table-column label="收件状态" align="center" prop="receiveStatus">
          <template slot-scope="scope">
            <el-tag v-show="scope.row.receiveStatus == 1" type="danger"
              >未收件</el-tag
            >
            <el-tag v-show="scope.row.receiveStatus == 2" type="success"
              >收件办结</el-tag
            >
          </template>
        </el-table-column>

        <el-table-column label="盖章状态" align="center" prop="stampStatus">
          <template slot-scope="scope">
            <el-tag v-show="scope.row.stampStatus == 1" type="danger"
              >未盖章</el-tag
            >
            <el-tag v-show="scope.row.stampStatus == 2" type="success"
              >已盖章</el-tag
            >
            <el-tag
              v-show="scope.row.stampStatus != 1 && scope.row.stampStatus != 2"
              type="info"
              >无需盖章</el-tag
            >
          </template>
        </el-table-column>

        <el-table-column label="验收状态" align="center" prop="checkStatus">
          <template slot-scope="scope">
            <el-tag v-show="scope.row.checkStatus == 1" type="danger"
              >未验收</el-tag
            >
            <el-tag v-show="scope.row.checkStatus == 2" type="success"
              >验收办结</el-tag
            >
            <el-tag v-show="scope.row.checkStatus == 3" type="warning"
              >验收拒绝</el-tag
            >
          </template>
        </el-table-column>

        <el-table-column label="委托单位" align="center" prop="customerName" />
        <el-table-column label="负责人" align="center" prop="managerUserName" />
        <el-table-column label="部门" align="center" prop="jobOrgName" />

        <el-table-column
          label="二检通过日期"
          align="center"
          prop="secondCheckTime"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{
              parseTime(scope.row.secondCheckTime, "{y}-{m}-{d}")
            }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="收件截止时间"
          align="center"
          prop="receiveCutoffTime"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{
              parseTime(scope.row.receiveCutoffTime, "{y}-{m}-{d}")
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="移交人" align="center" prop="yjUserName" />
        <el-table-column
          label="移交日期"
          align="center"
          prop="yjsj"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.yjsj, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column label="收件人" align="center" prop="sjUserName" />
        <el-table-column
          label="收件日期"
          align="center"
          prop="sjsj"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.sjsj, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="退回整改日期"
          align="center"
          prop="zgsj"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.zgsj, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="整改截止时间"
          align="center"
          prop="rectifyCutoffTime"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{
              parseTime(scope.row.rectifyCutoffTime, "{y}-{m}-{d}")
            }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="盖章日期"
          align="center"
          prop="gzsj"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.gzsj, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column label="验收人" align="center" prop="ysUserName" />
        <el-table-column
          label="验收日期"
          align="center"
          prop="yssj"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.yssj, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="是否有归档资料"
          align="center"
          prop="isArchive"
          width="111"
        >
          <template slot-scope="scope">
            <el-tag v-show="scope.row.isArchive == 0" type="danger">否</el-tag>
            <el-tag v-show="scope.row.isArchive == 1" type="warning">是</el-tag>
          </template>
        </el-table-column>
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
import { listBcyj } from "@/api/system/bcyj";

export default {
  name: "Bcyj",
  data() {
    return {
      overTitle: "",
      overTimeOpen: false,
      overTimeProjectList: [],
      statisticsData: [],
      checkStatusS: [
        {
          value: 1,
          label: "未验收",
        },
        {
          value: 2,
          label: "验收办结",
        },
        {
          value: 3,
          label: "验收拒绝",
        },
      ],
      stampStatusS: [
        {
          value: 1,
          label: "未盖章",
        },
        {
          value: 2,
          label: "已盖章",
        },
      ],
      receiveStatusS: [
        {
          value: 1,
          label: "未收件",
        },
        {
          value: 2,
          label: "收件办结",
        },
      ],
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
      // 【请填写功能名称】表格数据
      bcyjList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectCode: null,
        projectName: null,
        receiveStatus: null,
        stampStatus: null,
        checkStatus: null,
        customerName: null,
        managerUserName: null,
        secondCheckTime: null,
        receiveCutoffTime: null,
        yjUserName: null,
        yjsj: null,
        sjUserName: null,
        sjsj: null,
        zgsj: null,
        rectifyCutoffTime: null,
        gzsj: null,
        ysUserName: null,
        yssj: null,
        isArchive: null,
        jobOrgName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    this.getStatisticsData();
  },
  methods: {
    getStatisticsData() {
      listBcyj({ pageNum: 1, pageSize: 9999 }).then((response) => {
        const project = response.rows;
        let myMap = new Map();
        for (let i = 0; i < project.length; i++) {
          let key = project[i].jobOrgName;
          if (key == "" || key == null || key == undefined) {
            continue;
          }
          if (myMap.has(key)) {
            let num = myMap.get(key);
            if (project[i].issjcq == 1) {
              num.issjcq++;
            }
            if (project[i].iszgcq == 1) {
              num.iszgcq++;
            }
            if (project[i].sjmscq == 1) {
              num.sjmscq++;
            }
            if (project[i].zgmscq == 1) {
              num.zgmscq++;
            }
            myMap.set(key, num);
          } else {
            let num = {
              issjcq: 0,
              iszgcq: 0,
              sjmscq: 0,
              zgmscq: 0,
            };
            if (project[i].issjcq == 1) {
              num.issjcq++;
            }
            if (project[i].iszgcq == 1) {
              num.iszgcq++;
            }
            if (project[i].sjmscq == 1) {
              num.sjmscq++;
            }
            if (project[i].zgmscq == 1) {
              num.zgmscq++;
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
          numData.gcchbNumWork = myMap.get("工程测绘部").sjmscq;
        }
        if (myMap.has("不动产测绘部")) {
          numData.bdcchbWork = myMap.get("不动产测绘部").sjmscq;
        }
        if (myMap.has("管线工程部")) {
          numData.gxgcbWork = myMap.get("管线工程部").sjmscq;
        }
        if (myMap.has("地理信息部")) {
          numData.dlxxbWork = myMap.get("地理信息部").sjmscq;
        }
        this.statisticsData.push(numData);

        numData = {
          status: 2,
          gcchbNumWork: 0,
          bdcchbWork: 0,
          gxgcbWork: 0,
          dlxxbWork: 0,
        };

        if (myMap.has("工程测绘部")) {
          numData.gcchbNumWork = myMap.get("工程测绘部").zgmscq;
        }
        if (myMap.has("不动产测绘部")) {
          numData.bdcchbWork = myMap.get("不动产测绘部").zgmscq;
        }
        if (myMap.has("管线工程部")) {
          numData.gxgcbWork = myMap.get("管线工程部").zgmscq;
        }
        if (myMap.has("地理信息部")) {
          numData.dlxxbWork = myMap.get("地理信息部").zgmscq;
        }
        this.statisticsData.push(numData);

        numData = {
          status: 3,
          gcchbNumWork: 0,
          bdcchbWork: 0,
          gxgcbWork: 0,
          dlxxbWork: 0,
        };

        if (myMap.has("工程测绘部")) {
          numData.gcchbNumWork = myMap.get("工程测绘部").issjcq;
        }
        if (myMap.has("不动产测绘部")) {
          numData.bdcchbWork = myMap.get("不动产测绘部").issjcq;
        }
        if (myMap.has("管线工程部")) {
          numData.gxgcbWork = myMap.get("管线工程部").issjcq;
        }
        if (myMap.has("地理信息部")) {
          numData.dlxxbWork = myMap.get("地理信息部").issjcq;
        }
        this.statisticsData.push(numData);

        numData = {
          status: 4,
          gcchbNumWork: 0,
          bdcchbWork: 0,
          gxgcbWork: 0,
          dlxxbWork: 0,
        };

        if (myMap.has("工程测绘部")) {
          numData.gcchbNumWork = myMap.get("工程测绘部").iszgcq;
        }
        if (myMap.has("不动产测绘部")) {
          numData.bdcchbWork = myMap.get("不动产测绘部").iszgcq;
        }
        if (myMap.has("管线工程部")) {
          numData.gxgcbWork = myMap.get("管线工程部").iszgcq;
        }
        if (myMap.has("地理信息部")) {
          numData.dlxxbWork = myMap.get("地理信息部").iszgcq;
        }
        this.statisticsData.push(numData);
        console.log(this.statisticsData);
      });
    },
    handleOverTimeOpen(value, status) {
      if (status == 1) {
        this.overTitle = "收件将要超期项目数(2天内)";
      } else if (status == 2) {
        this.overTitle = "整改将要超期项目数(2天内)";
      } else if (status == 3) {
        this.overTitle = "收件超期项目数";
      } else if (status == 4) {
        this.overTitle = "整改超期项目数";
      }
      this.overTimeProjectList = [];
      listBcyj({ pageNum: 1, pageSize: 9999, jobOrgName: value }).then(
        (response) => {
          const project = response.rows;
          for (let i = 0; i < project.length; i++) {
            switch (status) {
              case 1:
                if (project[i].sjmscq == 1) {
                  this.overTimeProjectList.push(project[i]);
                }
                break;
              case 2:
                if (project[i].zgmscq == 1) {
                  this.overTimeProjectList.push(project[i]);
                }
                break;
              case 3:
                if (project[i].issjcq == 1) {
                  this.overTimeProjectList.push(project[i]);
                }
                break;
              case 4:
                if (project[i].iszgcq == 1) {
                  this.overTimeProjectList.push(project[i]);
                }
                break;
              default:
                console.log("未知类型");
            }
          }
          this.overTimeOpen = true;
        }
      );
    },
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listBcyj(this.queryParams).then((response) => {
        this.bcyjList = response.rows;
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
        projectCode: null,
        projectName: null,
        receiveStatus: null,
        stampStatus: null,
        checkStatus: null,
        customerName: null,
        managerUserName: null,
        secondCheckTime: null,
        receiveCutoffTime: null,
        yjUserName: null,
        yjsj: null,
        sjUserName: null,
        sjsj: null,
        zgsj: null,
        rectifyCutoffTime: null,
        gzsj: null,
        ysUserName: null,
        yssj: null,
        isArchive: null,
        jobOrgName: null,
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
      this.ids = selection.map((item) => item.projectCode);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
  },
};
</script>
