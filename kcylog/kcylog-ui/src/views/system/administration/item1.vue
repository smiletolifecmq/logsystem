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
            <!-- <div class="des">{{ item.num3 }}</div> -->
          </li>
        </ul>
      </div>
    </div>

    <div class="content" style="height: 200px">
      <div class="title">
        <div class="bg"></div>
        <div class="text">经营产值(月)</div>
        <div class="echarts-area">
          <div class="floorage-echart" ref="chartArrival"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { listProjectHjMonth } from "@/api/system/project";

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
      queryParamsHz: {},
      dateRangeHz: [],
      chartInstanceArrival: null,
      list2: {
        a: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        b: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        c: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        d: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        e: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        f: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
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
      ],
    };
  },
  created() {},
  methods: {
    initFloorage() {
      listProjectHjMonth(
        this.addDateRange(this.queryParamsHz, this.dateRangeHz)
      ).then((response) => {
        var dl = response.经营产值.地理信息部;
        var gc = response.经营产值.工程测绘部;
        var bdc = response.经营产值.不动产测绘部;
        var gx = response.经营产值.管线工程部;

        var dlqn = response.上一年度.地理信息部;
        var gcqn = response.上一年度.工程测绘部;
        var bdcqn = response.上一年度.不动产测绘部;
        var gxqn = response.上一年度.管线工程部;

        var dlll = response.利润.地理信息部;
        var gcll = response.利润.工程测绘部;
        var bdcll = response.利润.不动产测绘部;
        var gxll = response.利润.管线工程部;

        this.listData[0] = {
          name: "工程测绘部",
          num1: 0,
          num2: 0,
        };
        this.listData[1] = {
          name: "管线工程部",
          num1: 0,
          num2: 0,
        };
        this.listData[2] = {
          name: "不动产测绘部",
          num1: 0,
          num2: 0,
        };
        this.listData[3] = {
          name: "地理信息部",
          num1: 0,
          num2: 0,
        };

        for (const key in dlll) {
          const value = dlll[key];
          this.jydata.jyczqfb += value;
          this.listData[3].num2 += value;
        }

        for (const key in gcll) {
          const value = gcll[key];
          this.jydata.jyczqfb += value;
          this.listData[0].num2 += value;
        }

        for (const key in bdcll) {
          const value = bdcll[key];
          this.jydata.jyczqfb += value;
          this.listData[2].num2 += value;
        }

        for (const key in gxll) {
          const value = gxll[key];
          this.jydata.jyczqfb += value;
          this.listData[1].num2 += value;
        }

        for (const key in dl) {
          const value = dl[key];
          this.list2.a[key - 1] = value;
          this.jydata.jycz += value;
          this.listData[3].num1 += value;
          this.list2.e[key - 1] = this.list2.e[key - 1] + value;
        }

        for (const key in gc) {
          const value = gc[key];
          this.list2.b[key - 1] = value;
          this.jydata.jycz += value;
          this.listData[0].num1 += value;
          this.list2.e[key - 1] = this.list2.e[key - 1] + value;
        }

        for (const key in bdc) {
          const value = bdc[key];
          this.list2.c[key - 1] = value;
          this.list2.e[key - 1] = this.list2.e[key - 1] + value;
          this.jydata.jycz += value;
          this.listData[2].num1 += value;
        }

        for (const key in gx) {
          const value = gx[key];
          this.list2.d[key - 1] = value;
          this.jydata.jycz += value;
          this.listData[1].num1 += value;
          this.list2.e[key - 1] = this.list2.e[key - 1] + value;
        }

        for (const key in dlqn) {
          const value = dlqn[key];
          this.list2.f[key - 1] = this.list2.f[key - 1] + value;
        }

        for (const key in gcqn) {
          const value = gcqn[key];
          this.list2.f[key - 1] = this.list2.f[key - 1] + value;
        }

        for (const key in bdcqn) {
          const value = bdcqn[key];
          this.list2.f[key - 1] = this.list2.f[key - 1] + value;
        }

        for (const key in gxqn) {
          const value = gxqn[key];
          this.list2.f[key - 1] = this.list2.f[key - 1] + value;
        }

        this.jydata.jycz = this.jydata.jycz.toFixed(2);
        this.listData[0].num1 = this.listData[0].num1.toFixed(2);
        this.listData[1].num1 = this.listData[1].num1.toFixed(2);

        this.listData[2].num1 = this.listData[2].num1.toFixed(2);
        this.listData[3].num1 = this.listData[3].num1.toFixed(2);

        this.jydata.jyczqfb = this.jydata.jyczqfb.toFixed(2);
        this.listData[0].num2 = this.listData[0].num2.toFixed(2);
        this.listData[1].num2 = this.listData[1].num2.toFixed(2);

        this.listData[2].num2 = this.listData[2].num2.toFixed(2);
        this.listData[3].num2 = this.listData[3].num2.toFixed(2);

        this.listData.sort((a, b) => b.num1 - a.num1);

        for (var i = 0; i < this.list2.a.length; i++) {
          this.list2.a[i] = this.list2.a[i].toFixed(2);
        }

        for (var i = 0; i < this.list2.b.length; i++) {
          this.list2.b[i] = this.list2.b[i].toFixed(2);
        }

        for (var i = 0; i < this.list2.c.length; i++) {
          this.list2.c[i] = this.list2.c[i].toFixed(2);
        }

        for (var i = 0; i < this.list2.e.length; i++) {
          this.list2.e[i] = this.list2.e[i].toFixed(2);
        }

        for (var i = 0; i < this.list2.f.length; i++) {
          this.list2.f[i] = this.list2.f[i].toFixed(2);
        }

        this.chartInstanceArrival.setOption({
          color: [
            "#afeff6",
            "#fbd26a",
            "#ff7f50",
            "#e60000",
            "#32CD32",
            "#8A2BE2",
          ], // 每条折线的颜色
          tooltip: {
            trigger: "axis",
          },
          legend: [
            {
              bottom: 0,
              textStyle: {
                color: "#ffffff",
              },
              data: ["地理信息部", "工程测绘部", "不动产测绘部", "管线工程部"],
            },
            {
              bottom: 20,
              textStyle: {
                color: "#ffffff",
              },
              data: ["总计", "上一年度"],
            },
          ],
          label: {
            color: "#ffffff",
          },
          grid: {
            top: "4%",
            left: "1%",
            right: "3%",
            bottom: "30%",
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
              name: "地理信息部",
              type: "line",
              smooth: true,
              data: this.list2.a,
            },
            {
              name: "工程测绘部",
              type: "line",
              smooth: true,
              data: this.list2.b,
            },
            {
              name: "不动产测绘部",
              type: "line",
              smooth: true,
              data: this.list2.c,
            },
            {
              name: "管线工程部",
              type: "line",
              smooth: true,
              data: this.list2.d,
            },
            {
              name: "总计",
              type: "line",
              smooth: true,
              data: this.list2.e,
            },
            {
              name: "上一年度",
              type: "line",
              smooth: true,
              data: this.list2.f,
            },
          ],
        });
      });
    },
    handleData(data) {},
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
  margin: 12px 0;
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
  height: 200px;
  // margin-top: 24px;
}
</style>
