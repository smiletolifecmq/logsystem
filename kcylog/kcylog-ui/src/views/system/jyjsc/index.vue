<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span style="color: red">{{ tjTitle }}</span>
      </div>
      <el-row>
        <el-col>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>经营报表</span>
            </div>
            <div>
              <el-table :data="tableData" :span-method="objectSpanMethod">
                <el-table-column
                  prop="ht"
                  label="类型"
                  header-align="center"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop="ndmb"
                  label="年度目标"
                  header-align="center"
                  align="center"
                >
                </el-table-column>
                <el-table-column label="完成情况" header-align="center">
                  <el-table-column label="前台" header-align="center">
                    <el-table-column
                      label="项数"
                      header-align="center"
                      prop="qtxs"
                      align="center"
                    >
                    </el-table-column>
                    <el-table-column
                      label="金额"
                      header-align="center"
                      prop="qtje"
                      align="center"
                    >
                    </el-table-column>
                  </el-table-column>
                  <el-table-column label="合同" header-align="center">
                    <el-table-column
                      label="项数"
                      header-align="center"
                      align="center"
                      prop="htxs"
                    >
                    </el-table-column>
                    <el-table-column
                      label="金额"
                      header-align="center"
                      align="center"
                      prop="htje"
                    >
                    </el-table-column>
                  </el-table-column>
                </el-table-column>
                <el-table-column
                  label="合计"
                  header-align="center"
                  align="center"
                >
                  <el-table-column
                    label="项数"
                    header-align="center"
                    align="center"
                    prop="hjxs"
                  >
                  </el-table-column>
                  <el-table-column
                    label="金额"
                    header-align="center"
                    align="center"
                    prop="hjje"
                  >
                  </el-table-column>
                </el-table-column>
                <el-table-column
                  label="经营收入"
                  header-align="center"
                  prop="jysr"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  label="完成比例"
                  header-align="center"
                  prop="wcbl"
                  align="center"
                >
                </el-table-column>
              </el-table>
            </div>
          </el-card>
        </el-col>
        <el-col :span="24">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>应收账款月统计</span>
            </div>
            <el-row>
              <el-col :span="24">
                <div>
                  <el-table :data="tableDataTj">
                    <el-table-column
                      prop="snjdyszk"
                      label="上一年度应收账款"
                      header-align="center"
                      align="center"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="lshk"
                      label="历史回款"
                      header-align="center"
                      align="center"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="bndxz"
                      label="本年度新增"
                      header-align="center"
                      align="center"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="xzzk"
                      label="账款新增情况"
                      header-align="center"
                      align="center"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="qbyszk"
                      label="全部应收账款"
                      header-align="center"
                      align="center"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="zjl"
                      label="增加率"
                      header-align="center"
                      align="center"
                    >
                    </el-table-column>
                  </el-table>
                </div>
              </el-col>
              <el-col :span="8">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>其中1年期内</span>
                  </div>
                  <div class="text item">上月存量：{{ yszkyData.syclOne }}</div>
                  <div class="text item">本月新增：{{ yszkyData.byxzOne }}</div>
                  <div class="text item">本月回款：{{ yszkyData.byhkOne }}</div>
                  <div class="text item">
                    红冲（正数）：{{ yszkyData.hcOne }}
                  </div>
                  <div class="text item">
                    移至1到3年期：{{ yszkyData.oneTwo }}
                  </div>
                  <div class="text item">合计：{{ yszkyData.totalOne }}</div>
                </el-card>
              </el-col>
              <el-col :span="8">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>其中1到3年期</span>
                  </div>
                  <div class="text item">上月存量：{{ yszkyData.syclTwo }}</div>
                  <div class="text item">本月新增：{{ yszkyData.byxzTwo }}</div>
                  <div class="text item">本月回款：{{ yszkyData.byhkTwo }}</div>
                  <div class="text item">
                    红冲（正数）：{{ yszkyData.hcTwo }}
                  </div>
                  <div class="text item">
                    移至1到3年期：{{ yszkyData.twoThree }}
                  </div>
                  <div class="text item">合计：{{ yszkyData.totalTwo }}</div>
                </el-card>
              </el-col>
              <el-col :span="8">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>其中3年期以上</span>
                  </div>
                  <div class="text item">
                    上月存量：{{ yszkyData.syclThree }}
                  </div>
                  <div class="text item">
                    本月新增：{{ yszkyData.byxzThree }}
                  </div>
                  <div class="text item">
                    本月回款：{{ yszkyData.byhkThree }}
                  </div>
                  <div class="text item">
                    红冲（正数）：{{ yszkyData.hcThree }}
                  </div>
                  <div class="text item">坏账：{{ yszkyData.hzThree }}</div>
                  <div class="text item">合计：{{ yszkyData.totalThree }}</div>
                </el-card>
              </el-col>

              <el-col :span="8">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>一年期内应收账款</span>
                  </div>
                  <div class="text item">国有债权：{{ yszkyData.gyOne }}</div>
                  <div class="text item">民营债权：{{ yszkyData.myOne }}</div>
                  <div class="text item">小计：{{ yszkyData.gymyOne }}</div>
                </el-card>
              </el-col>
              <el-col :span="8">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>一至三年之间应收账款</span>
                  </div>
                  <div class="text item">国有债权：{{ yszkyData.gyTwo }}</div>
                  <div class="text item">民营债权：{{ yszkyData.myTwo }}</div>
                  <div class="text item">小计：{{ yszkyData.gymyTwo }}</div>
                </el-card>
              </el-col>
              <el-col :span="8">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>三年以上应收账款</span>
                  </div>
                  <div class="text item">国有债权：{{ yszkyData.gyThree }}</div>
                  <div class="text item">民营债权：{{ yszkyData.myThree }}</div>
                  <div class="text item">小计：{{ yszkyData.gymyThree }}</div>
                </el-card>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>应收账款清单</span>
            </div>
            <div class="text item">总表金额：{{ yszkqdData.zb }}</div>
            <div class="text item">1年内金额：{{ yszkqdData.yn }}</div>
            <div class="text item">1年至3年金额：{{ yszkqdData.ysn }}</div>
            <div class="text item">3年以上金额：{{ yszkqdData.sn }}</div>
            <div class="text item">
              本上报新增3年期以上金额：{{ yszkqdData.xzsn }}
            </div>
          </el-card>
        </el-col>
        <el-col :span="16">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>催收发函统计</span>
            </div>
            <el-row>
              <el-col :span="12">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>本月新增</span>
                  </div>
                  <div class="text item">催款函：{{ cstjData.bycsh }}</div>
                  <div class="text item">律师函：{{ cstjData.bylsh }}</div>
                  <div class="text item">起诉：{{ cstjData.byqs }}</div>
                </el-card>
              </el-col>
              <el-col :span="12">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>总计</span>
                  </div>
                  <div class="text item">催款函：{{ cstjData.zgcsh }}</div>
                  <div class="text item">律师函：{{ cstjData.zglsh }}</div>
                  <div class="text item">
                    起诉（含仲裁）：{{ cstjData.gzqs }}
                  </div>
                  <div class="text item">
                    其中三年期以上应收项数：{{ cstjData.snys }}
                  </div>
                  <div class="text item">
                    其中三年期以上发函、起诉项数：{{ cstjData.snqs }}
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import {
  getExport,
  exportCs,
  exportYs,
  exportJyyb,
} from "@/api/system/collection";

export default {
  name: "jyjsc",
  data() {
    return {
      tjTitle: "",
      dayStart: "",
      dayEnd: "",
      queryParams: {},
      yszkqdData: {},
      csTimeForm: {},
      cstjData: {},
      yszkyData: {},
      tableData: [],
      jyTimeForm: {},
      tableDataTj: [],
    };
  },
  created() {
    const date = new Date();
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0"); // 月份从 0 开始
    const day = String(date.getDate()).padStart(2, "0"); //

    const date1 = new Date();
    date1.setMonth(date1.getMonth(), 0); // 设置为上个月的最后一天
    const year1 = date1.getFullYear();
    const month1 = String(date1.getMonth() + 1).padStart(2, "0");
    const day1 = String(date1.getDate()).padStart(2, "0");
    this.queryParams.ysKprqLast = `${year1}-${month1}-${day1}`;
    this.queryParams.ysKprqCs = `${year}-${month}-${day}`;
    this.tjTitle =
      `统计截止时间为：${this.queryParams.ysKprqCs}，上次上报时间为：` +
      `${this.queryParams.ysKprqLast}  单位（万元）`;
    this.yszktj();
    this.handleExportCstj();
    this.handleExportYstj();
    this.handleExportJyyb();
  },
  methods: {
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      // 判断是否为第9列 (columnIndex === 8)
      if (columnIndex === 8) {
        // 如果是第一行（rowIndex === 0），合并第一行和第二行
        if (rowIndex === 0) {
          return {
            rowspan: 2,
            colspan: 1,
          };
        }
        // 如果是第二行（rowIndex === 1），隐藏单元格
        else if (rowIndex === 1) {
          return {
            rowspan: 0,
            colspan: 0,
          };
        }
      }
    },
    yszktj() {
      getExport(this.queryParams).then((response) => {
        this.yszkqdData = response.data;
      });
    },
    handleExportCstj() {
      this.csTimeForm.ysKprqCs = this.queryParams.ysKprqCs;
      exportCs(this.csTimeForm).then((responseData) => {
        this.cstjData = responseData.data;
      });
    },
    handleExportYstj() {
      exportYs(this.queryParams).then((responseData) => {
        this.yszkyData = responseData.data;
      });
    },

    handleExportJyyb() {
      this.jyTimeForm.ysKprqCs = this.queryParams.ysKprqCs;

      exportJyyb(this.jyTimeForm).then((responseData) => {
        var item = {};
        item.ht = "开票";
        item.ndmb = "5092(4392)";
        item.qtxs = responseData.data.qtkp;
        item.qtje = responseData.data.qtkpje;
        item.htxs = responseData.data.htkp;
        item.htje = responseData.data.htkpje;
        item.hjxs = responseData.data.qtkp + responseData.data.htkp;
        item.hjje = responseData.data.qtkpje + responseData.data.htkpje;
        item.wcbl = `${responseData.data.kpzbOne}%(${responseData.data.kpzbTwo}%)`;
        item.jysr =
          responseData.data.qtkpje +
          responseData.data.htkpje +
          responseData.data.qzje;
        this.tableData.push(item);

        var item = {};
        item.ht = "权责制";
        item.ndmb = "/";
        item.qtxs = "/";
        item.qtje = "/";
        item.htxs = "/";
        item.htje = "/";
        item.hjxs = responseData.data.qzxs;
        item.hjje = responseData.data.qzje;
        item.wcbl = `${responseData.data.qzzbOne}%(${responseData.data.qzzbTwo}%)`;
        this.tableData.push(item);

        var item = {};
        item.ht = "到账";
        item.ndmb = "4371(3770)";
        item.qtxs = responseData.data.qtdz;
        item.qtje = responseData.data.qtdzje;
        item.htxs = responseData.data.htdz;
        item.htje = responseData.data.htdzje;
        item.hjxs = responseData.data.qtdz + responseData.data.htdz;
        item.hjje = responseData.data.qtdzje + responseData.data.htdzje;
        item.wcbl = `${responseData.data.htzbOne}%(${responseData.data.htzbTwo}%)`;
        this.tableData.push(item);
        var item = {
          snjdyszk: 0,
          lshk: 0,
          bndxz: 0,
          xzzk: 0,
          qbyszk: 0,
          zjl: 0,
        };
        item.snjdyszk = responseData.data.snjdyszk;
        item.lshk = responseData.data.lshk;
        item.bndxz = responseData.data.bndxz;
        item.xzzk = responseData.data.xzzk;
        item.qbyszk = responseData.data.qbyszk;
        item.zjl = responseData.data.zjl + "%";
        this.tableDataTj.push(item);
      });
    },
  },
};
</script>
