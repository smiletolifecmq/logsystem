<template>
  <div class="bpl1-bg">
    <div class="co-title">
      <div
        class="co-title-name"
        style="margin-top: 0.05rem !important; margin-left: 0.5rem !important"
      >
        <b style="font-size: large; color: aliceblue">经营产值管理</b>

        <!-- <img
          src="@/assets/cockpit/subdivision/administration/item1-title.png"
          alt=""
        /> -->
      </div>
      <div class="co-title-right co-title-right-flex">
        <div class="co-title-breathe">
          <i></i><i></i><i></i><i></i><i></i><i></i>
        </div>
      </div>
    </div>
    <div class="content">
      <div class="title">
        <div class="bg"></div>
        <div class="text">当前年度经营总体情况</div>
      </div>
    </div>
    <ul class="bpl1-stac" style="margin-left: -34px">
      <li class="bpl1-st-each">
        <i class="bpl1-ste-icon"
          ><img
            src="@/assets/cockpit/subdivision/administration/item1-icon2.png"
            alt=""
        /></i>
        <div class="bpl1-ste-word">
          <h6>经营产值</h6>
          <p>
            <span>{{ jydata.jycz }}</span>
          </p>
        </div>
      </li>
      <li class="bpl1-st-each">
        <i class="bpl1-ste-icon"
          ><img
            src="@/assets/cockpit/subdivision/administration/item1-icon2.png"
            alt=""
        /></i>
        <div class="bpl1-ste-word">
          <h6>经营净产值(扣分包)</h6>
          <p>
            <span>{{ jydata.jyczqfb }}</span>
          </p>
        </div>
      </li>
      <!-- <li class="bpl1-st-each">
        <i class="bpl1-ste-icon"
          ><img
            src="@/assets/cockpit/subdivision/administration/item1-icon1.png"
            alt=""
        /></i>
        <div class="bpl1-ste-word">
          <h6>经营产值(预估)</h6>
          <p>
            <span>{{ jydata.jyczyg }}</span>
          </p>
        </div>
      </li>
      <li class="bpl1-st-each">
        <i class="bpl1-ste-icon"
          ><img
            src="@/assets/cockpit/subdivision/administration/item1-icon1.png"
            alt=""
        /></i>
        <div class="bpl1-ste-word">
          <h6>经营产值未填报</h6>
          <p>
            <span>{{ jydata.jyczwtb }}</span>
          </p>
        </div>
      </li> -->
    </ul>
    <!--  -->
    <div class="content">
      <div class="title">
        <div class="bg"></div>
        <div class="text">各部门详情(年)</div>
      </div>
      <div class="map-right-menu" :class="!isFold ? 'map-right-menu-no' : ''">
        <div class="list-header" v-show="isFold">
          <p
            v-for="(item, index) in headerData"
            :key="index"
            style="margin-top: auto"
          >
            {{ item.fieldName }}
          </p>
        </div>
        <ul class="list-item" style="margin-left: -41px">
          <!--  -->
          <li v-for="(item, index) in listData" :key="index">
            <div class="name">
              {{ item.name }}
              <div class="before"></div>
            </div>
            <div class="length">{{ item.num1 }}</div>
            <div class="des">{{ item.num2 }}</div>
            <div class="des">{{ item.num3 }}</div>
          </li>
        </ul>
      </div>
    </div>

    <div class="content" style="height: 140px">
      <div class="title">
        <div class="bg"></div>
        <div class="text">各部门详情(月)</div>
        <div class="echarts-area">
          <div class="floorage-echart" ref="chartArrival"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
  name: "BplItem1",
  components: {},
  props: {
    hztjData: {
      type: Array,
      default: () => [],
    },
  },
  watch: {
    hztjData: {
      handler(newVal) {
        // 在这里处理数据，如调用初始化方法
        this.handleData(newVal);
      },
      immediate: true, // 如果希望首次挂载也调用一次
      deep: true, // 可选，如果数组结构较复杂
    },
  },
  mounted() {
    this.chartInstanceArrival = echarts.init(this.$refs.chartArrival);
    this.initFloorage(); // 确保图表 DOM 初始化完成后再调用 setOption
    window.addEventListener("resize", () => {
      this.chartInstanceArrival.resize();
    });
  },
  data() {
    return {
      chartInstanceArrival: null,
      list2: {
        a: [120, 132, 101, 134, 90, 230, 210, 180, 190, 200, 210, 220],
        b: [220, 182, 191, 234, 290, 330, 310, 305, 295, 280, 270, 260],
        c: [150, 232, 201, 154, 190, 330, 410, 390, 380, 370, 360, 350],
        d: [320, 332, 301, 334, 390, 330, 320, 310, 300, 290, 280, 270],
      },
      monthList: [
        "1月",
        "2月",
        "3月",
        "4月",
        "5月",
        "6月",
        "7月",
        "8月",
        "9月",
        "10月",
        "11月",
        "12月",
      ],
      listData: [],
      isFold: true,
      jydata: {
        jycz: 0,
        jyczqfb: 0,
        jyczwtb: 0,
        jyczyg: 0,
      },
      headerData: [
        {
          fieldName: "部门名称",
        },
        {
          fieldName: "经营产值",
        },
        {
          fieldName: "经营净产值",
        },
        {
          fieldName: "经营预估产值",
        },
      ],
    };
  },
  created() {},
  methods: {
    initFloorage() {
      this.chartInstanceArrival.setOption({
        color: ["#afeff6", "#fbd26a", "#ff7f50", "#e60000"], // 每条折线的颜色
        tooltip: {
          trigger: "axis",
        },
        legend: {
          textStyle: {
            color: "#ffffff",
          },
          data: ["部门A", "部门B", "部门C", "部门D"],
        },
        label: {
          color: "#ffffff",
        },
        grid: {
          top: "4%",
          left: "1%",
          right: "3%",
          bottom: "0%",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          data: this.monthList,
          boundaryGap: false, // 从y轴开始
          //设置网格线颜色
          splitLine: {
            show: false,
          },
          axisLine: {
            lineStyle: {
              color: "#17709c",
            },
          },

          axisLabel: {
            show: true,
            textStyle: {
              color: "#ffffff", //更改坐标轴文字颜色
              fontSize: 12, //更改坐标轴文字大小
            },
          },
        },
        yAxis: {
          type: "value",
          axisLabel: {
            show: true,
            textStyle: {
              color: "#00eaff", //更改坐标轴文字颜色
              fontSize: 12, //更改坐标轴文字大小
            },
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: ["#17709c"],
              width: 1,
              type: "solid",
            },
          },
          // y轴轴线颜色
          axisLine: {
            lineStyle: {
              color: "#254177",
              // width: 1 //这里是坐标轴的宽度
            },
          },
        },
        series: [
          {
            name: "部门A",
            type: "line",
            smooth: true,
            data: this.list2.a,
          },
          {
            name: "部门B",
            type: "line",
            smooth: true,
            data: this.list2.b,
          },
          {
            name: "部门C",
            type: "line",
            smooth: true,
            data: this.list2.c,
          },
          {
            name: "部门D",
            type: "line",
            smooth: true,
            data: this.list2.d,
          },
        ],
      });
    },
    handleData(data) {
      if (data.length !== 0) {
        this.jydata.jycz = data[10].hj;
        this.jydata.jyczqfb = data[11].hj;
        this.jydata.jyczyg = data[4].hj;
        this.jydata.jyczwtb = data[9].hj;
        this.listData[0] = {
          name: "工程测绘部",
          num1: data[10].gcchb,
          num2: data[11].gcchb,
          num3: data[4].gcchb,
        };
        this.listData[1] = {
          name: "管线工程部",
          num1: data[10].gxgcb,
          num2: data[11].gxgcb,
          num3: data[4].gxgcb,
        };
        this.listData[2] = {
          name: "不动产测绘部",
          num1: data[10].bdcchb,
          num2: data[11].bdcchb,
          num3: data[4].bdcchb,
        };
        this.listData[3] = {
          name: "地理信息部",
          num1: data[10].dlxxb,
          num2: data[11].dlxxb,
          num3: data[4].dlxxb,
        };
        this.listData.sort((a, b) => b.num1 - a.num1);
      }
    },
  },
};
</script>
<style lang="scss" scoped>
@import "~@/styles/cockpit.scss";
.bpl1-bg {
  width: 100%;
  height: 100%;
  padding: 16px;
  // background: url('~@/assets/cockpit/bpl1-bg.png') no-repeat right center/ 99.3% 100%;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0.1) 0%,
    rgba(255, 255, 255, 0) 100%
  );
  border: 1px solid rgba(255, 255, 255, 0.2);
  position: relative;
  &::before {
    content: "";
    display: block;
    width: 20px;
    height: 103%;
    background: url("~@/assets/cockpit/bpl-bg-line.png") no-repeat 0/ 100% 100%;
    position: absolute;
    left: -10px;
    top: 50%;
    transform: translate(0, -50%);
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
  margin: 22px 0;
  .title {
    position: relative;
    width: 100%;
    height: 24px;
    line-height: 24px;
    font-size: 14px !important;
    color: #fff;
    margin-left: 4px;

    .bg {
      width: 50%;
      height: 100%;
      background: url("~@/assets/cockpit/subdivision/approve/item3-text-bg.png")
        no-repeat 0/ 100% 100%;
      margin-left: -12px;
    }
    .text {
      position: absolute;
      left: 2px;
      top: 0;
      // width: 100%;
      font-size: 16px !important;
    }
  }

  .item {
    li {
      width: 428px;
      height: 44px;
      line-height: 44px;
      background: rgba(255, 255, 255, 0.05);
      display: flex;
      justify-content: space-between;
      margin-top: 12px;
      .lf {
        display: flex;
        .yellow-icon {
          width: 4px;
          height: 4px;

          display: block;
          background: #f9f0d4;
          border-radius: 1px;
          box-shadow: 0 0 4px 1px #f0b04a;
          transform: rotate(45deg);
          margin: 21px 10px auto 12px;
        }
        .label {
          color: rgba(191, 254, 255, 1);
          font-size: 14px !important;
        }
      }
      .rg {
        display: flex;
        .num {
          color: rgba(255, 255, 255, 1);
          font-size: 16px !important;
          span {
            font-size: 12px !important;
            margin-left: 4px;
          }
        }
        .line {
          width: 1px;
          height: 16px;
          background: #38568b;
          margin: 14px 16px 16px 16px;
        }
        .text {
          color: rgba(223, 254, 255, 1);
          margin-right: 16px;
          .green {
            color: green;
            i {
              margin-right: 4px;
              font-weight: 700 !important;
            }
          }
          .red {
            color: #ec4545;
            i {
              margin-right: 4px;
              font-weight: 700 !important;
            }
          }
        }
      }
    }
  }
}
//
//
.bpl1-stac {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;

  .border-right-line {
    border-right: solid 1px rgba(255, 255, 255, 0.1);
  }
  .bpl1-st-each {
    flex: none;
    display: flex;
    width: 44%;
    margin-top: 10px;
    .bpl1-ste-icon {
      flex: none;
      width: 64px;
      height: 64px;
      > img {
        display: block;
        width: 100%;
        height: 100%;
      }
    }
    .bpl1-ste-word {
      margin-left: 8px;
      flex: none;
      > h6 {
        font-size: 14px !important;
        line-height: 18px;
        color: #bffeff;
        text-align: left;
        font-weight: 400;
        margin: 6px 0 0 0;
      }
      > p {
        font-size: 12px !important;
        line-height: 30px;
        color: #fff;
        text-align: left;
        font-weight: 500;
        margin: 4px 0 0 0;
        span {
          font-size: 20px !important;
          margin-left: 8px;
        }
      }
    }
    .bpl1-ste-chart {
      flex: none;
      width: 64px;
      height: 64px;
      padding: 2px;
      position: relative;
      margin: 0 0 0 12px;
      .bpl1-ste-chart-base {
        width: 100%;
        height: 100%;
      }
      .bpl1-ste-chart-word {
        width: 80%;
        position: absolute;
        left: 10%;
        top: 50%;
        transform: translate(0, -50%);
        > h6 {
          font-size: 16px !important;
          text-shadow: 0 0 4px #00ffaa;
          color: #fff;
          line-height: 20px;
          text-align: center;
        }
        > p {
          font-size: 12px !important;
          line-height: 14px;
          color: #bffeff;
          text-align: center;
        }
      }
    }
  }
  .bpl1-st-line {
    flex: none;
    width: 1px;
    height: 64px;
    background: rgba(#fff, 0.1);
    margin: 0 4px;
  }
}
//
.map-right-menu {
  color: #fff;
  width: 100%;
  height: auto;
  .title-area {
    width: 100%;
    margin: auto;
    display: flex;
    justify-content: space-between;
    margin-top: 30px;
    overflow: hidden;
    .title-btn {
      position: relative;
      width: 24px;
      height: 24px;
      background: linear-gradient(
        360deg,
        rgba(0, 149, 255, 0.3) 0%,
        rgba(0, 149, 255, 0) 100%
      );
      border-radius: 50%;
      cursor: pointer;

      &.extend {
        &:after {
          display: block;
          content: "";
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          background: url("~@/assets/cockpit/cho-up-extend.png") no-repeat 0/100%
            100%;
        }
      }
      &.fold {
        &:after {
          display: block;
          content: "";
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          background: url("~@/assets/cockpit/cho-down-fold.png") no-repeat 0/100%
            100%;
        }
      }
    }
    .left {
      font-size: 16px !important;
      color: #fff;
      text-shadow: 0 0 4px #006aff;
    }
    .right {
      font-size: 12px !important;
      color: #dffeff;
    }
  }
  .list-header {
    display: flex;
    width: 100%;
    height: 40px;
    line-height: 40px;
    background: rgba(255, 255, 255, 0.1);
    color: #bffeff;
    margin: auto;
    margin-top: 18px;

    p:nth-of-type(1) {
      flex: 2;
      padding-left: 20px;
    }
    p:nth-of-type(2) {
      flex: 1;
    }
    p:nth-of-type(3) {
      flex: 1;
    }
    p:nth-of-type(4) {
      flex: 1;
    }
  }
  .list-item {
    overflow: auto;
    .list-total {
      display: flex;
      width: 92%;
      height: 21px;
      line-height: 21px;
      background: rgba(255, 255, 255, 0.05);
      margin: auto;
      margin-top: 4px;
      text-align: center;
      color: #bffeff;
      p:nth-of-type(1) {
        flex: 1;
      }
      p:nth-of-type(2) {
        flex: 3;
      }
    }
    li {
      display: flex;
      width: 100%;
      height: 40px;
      line-height: 40px;
      background: rgba(255, 255, 255, 0.05);
      margin: auto;
      margin-top: 10px;
      text-align: left;

      .name {
        flex: 2;
        position: relative;
        padding-left: 20px;
        width: 100%;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        .before {
          position: absolute;
          left: 0;
          top: 0;
          width: 4px;
          height: 40px;
          background: #006aff;
          opacity: 0.5;
          border-right: solid 2px #002160;
        }
      }
      .length {
        flex: 1;
      }
      .des {
        flex: 1;
      }
    }
  }
}

.floorage-echart {
  width: 100%;
  height: 140px;
  // margin-top: 24px;
}
</style>
