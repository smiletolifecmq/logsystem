<template>
  <div class="bpl1-bg">
    <div class="co-title">
      <div class="co-title-name">
        <img
          src="@/assets/cockpit/subdivision/administration/item4-title.png"
          alt=""
        />
      </div>
      <div class="co-title-right co-title-right-flex">
        <!-- <div class="text">当前月份</div> -->
        <div class="co-title-breathe">
          <i></i><i></i><i></i><i></i><i></i><i></i>
        </div>
      </div>
    </div>
    <div class="content">
      <div class="total-area">
        <div class="label">综合登记数:</div>
        <div class="num">{{ totalNum }}<span>份</span></div>
      </div>
      <div class="zb-zx-content">
        <!-- <div class="zx-img" id="boxChart"></div> -->
        <div class="bpr2-chartbox">
          <div class="bpr2-chart-bg"></div>
          <div class="box-chart" id="boxChart"></div>
        </div>
        <ul class="zx-list">
          <li v-for="(item, index) in listArr" :key="index">
            <div class="icon" :style="`background:${colorArr[index]}`"></div>
            <div class="label" :title="item.dep_name + '(' + item.count + ')'">
              {{ item.dep_name }}
              <span>
                {{ item.count }}
              </span>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Item4",
  components: {},
  data() {
    return {
      totalNum: 0,
      colorArr: [
        "#49C384",
        "#2B8EF3",
        "#E42C31",
        "#F79014",
        "#DAFA94",
        "#49C384",
        "#2B8EF3",
        "#E42C31",
        "#F79014",
        "#DAFA94",
      ],
      listArr: [
        {
          value: 10,
          label: "建议提案",
        },
        {
          value: 9,
          label: "网络舆情",
        },
        {
          value: 8,
          label: "依申请公开",
        },
        {
          value: 7,
          label: "信访收文",
        },
        {
          value: 6,
          label: "市局长信箱",
        },
      ],
      rankData: [],
      options: {
        credits: {
          enabled: false,
        },
        chart: {
          type: "pie",
          plotBackgroundColor: null,
          plotBorderWidth: null,
          plotShadow: false,
          backgroundColor: null,
          options3d: {
            enabled: true,
            alpha: 60,
          },
        },
        title: {
          text: null,
        },
        subtitle: {
          text: null,
        },
        plotOptions: {
          pie: {
            size: 110,
            innerSize: 70,
            depth: 20,
            dataLabels: {
              //数据标签
              enabled: false, //是否启用
            },
            showInLegend: false,
          },
        },
        legend: {
          layout: "vertical",
          align: "right",
          // verticalAlign: 'middle',
          floating: true,
          symbolwidth: 6,
          symbolHeight: 6,
          itemStyle: {
            fontSize: "12px",
            color: "#fff",
            fontWeight: "normal",
            paddingTop: "20px",
          },
          itemHoverStyle: {
            color: "#00EAFF",
          },
        },
        series: [
          {
            name: "占比（%）",
            center: ["24%", "40%"],
            colors: this.colorArr,
            data: [
              // ['城镇集中建设区  50%', 50],
            ],
          },
        ],
      },
    };
  },
  created() {},
  methods: {},
};
</script>
<style lang="scss" scoped>
@import "~@/styles/cockpit.scss";
.bpl1-bg {
  width: 100%;
  height: 100%;
  padding: 16px;
  background: url("~@/assets/cockpit/subdivision/administration/item-bg4.png")
    no-repeat 0/ 100% 100%;
  position: relative;
  display: flex;
  flex-direction: column;

  .co-title {
    flex-shrink: 0;
  }
  .co-title-right-flex {
    display: flex;
    .text {
      font-size: 12px !important;
      color: rgba(223, 254, 255, 1);
      margin-top: 8px;
      margin-right: 6px;
    }
  }
}
.content {
  .total-area {
    width: 428px;
    height: 50px;
    line-height: 50px;
    background: url("~@/assets/cockpit/subdivision/administration/total-bg.png")
      no-repeat 0/ 100% 100%;
    display: flex;
    justify-content: space-between;
    padding: 0 20px 0 32px;
    margin-top: 16px;
    .label {
      color: rgba(0, 181, 211, 1);
      font-size: 18px !important;
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
    }
    .num {
      color: #fff;
      font-size: 28px !important;
      span {
        font-size: 14px !important;
        margin-left: 4px;
      }
    }
  }
}
.zb-zx-content {
  margin-top: 10px;
  position: relative;
  .zx-list {
    position: absolute;
    right: 0;
    top: 20px;
    color: #fff;
    font-size: 12px !important;
    display: flex;
    flex-wrap: wrap;
    width: 220px;
    margin-top: 30px;
    li {
      width: 50%;
      display: flex;
      margin-top: 8px;
      .icon {
        width: 10px;
        height: 10px;
        background: rgba(73, 195, 132, 1);
      }
      .label {
        width: calc(100% - 20px);
        margin-left: 10px;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
        span {
          margin-left: 4px;
        }
      }
    }
  }
  .zx-img {
    width: 100%;
    height: 118px;

    svg {
      width: 100% !important;
      height: 100%;
    }
  }
  .bpr2-chartbox {
    width: 100%;
    height: 162px;
    position: relative;
    margin-top: 14px;
    margin-left: -20px;
    .box-chart {
      width: 100%;
      height: 100%;
      margin-top: -10px;
    }
    .bpr2-chart-bg {
      width: 172px;
      height: 100px;
      background: url("~@/assets/cockpit/bpr2-chart-bg.png") no-repeat 0/ 100% 100%;
      position: absolute;
      left: 30px;
      top: 44px;
      z-index: -1;
    }
  }
}
</style>
