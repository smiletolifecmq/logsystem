<template>
  <div class="bpl1-bg">
    <div class="co-title">
      <div
        class="co-title-name"
        style="margin-top: 0.05rem !important; margin-left: 0.5rem !important"
      >
        <b style="font-size: large; color: aliceblue">超期管理</b>
      </div>
      <div class="co-title-right">
        <div class="co-title-breathe">
          <i></i><i></i><i></i><i></i><i></i><i></i>
        </div>
      </div>
    </div>
    <div class="item3-top">
      <div class="bb4-main">
        <ul class="bb4-list">
          <li class="bb4-l-each">
            <div class="bb4-le-sub bb4-le-sub1">
              <div class="bb4-leb-light"></div>
              <div class="bb4-leb-tri"></div>
              <div class="bb4-leb-word">
                <p>{{ totalObj.cq }}</p>
                <h6>工期超期</h6>
              </div>
            </div>
          </li>
          <li class="bb4-l-each">
            <div class="bb4-le-sub bb4-le-sub1">
              <div class="bb4-leb-light"></div>
              <div class="bb4-leb-tri"></div>
              <div class="bb4-leb-word">
                <p>{{ totalObj.yjbj }}</p>
                <h6>一检办结(超1周)</h6>
              </div>
            </div>
          </li>

          <li class="bb4-l-each">
            <div class="bb4-le-sub bb4-le-sub2">
              <div class="bb4-leb-light"></div>
              <div class="bb4-leb-tri"></div>
              <div class="bb4-leb-word">
                <p>{{ totalObj.ejbj }}</p>
                <h6>二检办结(超1周)</h6>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <div class="item3-content">
        <div class="item">
          <div class="title">
            <div class="bg"></div>
            <div class="text">工期超期TOP</div>
          </div>
          <ul>
            <li
              v-for="(item, index) in list1"
              :key="index"
              style="margin-top: 16px"
            >
              <div class="label" :title="item.label">
                {{ item.label }}
              </div>
              <div class="process">
                <el-progress
                  v-if="item.num"
                  :percentage="(item.num / item.total) * 100"
                  :show-text="false"
                ></el-progress>
              </div>
              <div class="right">
                {{ item.num }}
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <div class="item3-top" style="margin-top: 4.5rem">
      <div class="bb4-main">
        <ul class="bb4-list">
          <li class="bb4-l-each">
            <div class="bb4-le-sub bb4-le-sub1">
              <div class="bb4-leb-light"></div>
              <div class="bb4-leb-tri"></div>
              <div class="bb4-leb-word">
                <p>{{ totalObj.cqone }}</p>
                <h6>工期超期(超1月)</h6>
              </div>
            </div>
          </li>
          <li class="bb4-l-each">
            <div class="bb4-le-sub bb4-le-sub1">
              <div class="bb4-leb-light"></div>
              <div class="bb4-leb-tri"></div>
              <div class="bb4-leb-word">
                <p>{{ totalObj.yjbjone }}</p>
                <h6>一检办结(超1月)</h6>
              </div>
            </div>
          </li>

          <li class="bb4-l-each">
            <div class="bb4-le-sub bb4-le-sub2">
              <div class="bb4-leb-light"></div>
              <div class="bb4-leb-tri"></div>
              <div class="bb4-leb-word">
                <p>{{ totalObj.ejbjone }}</p>
                <h6>二检办结(超1月)</h6>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <div class="item3-content">
        <div class="item">
          <div class="title">
            <div class="bg"></div>
            <div class="text">工期超期(超1月)TOP</div>
          </div>
          <ul>
            <li
              v-for="(item, index) in list2"
              :key="index"
              style="margin-top: 16px"
            >
              <div class="label" :title="item.label">
                {{ item.label }}
              </div>
              <div class="process">
                <el-progress
                  v-if="item.num"
                  :percentage="(item.num / item.total) * 100"
                  :show-text="false"
                ></el-progress>
              </div>
              <div class="right">
                {{ item.num }}
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!-- <div class="item3-bottom">
      <div class="title">
        <div class="bg"></div>
        <div class="text">近半年发文情况</div>
      </div>

      <div class="echarts-area">
        <div class="floorage-echart" id="floorage2"></div>
      </div>
    </div> -->
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
  name: "Item3",
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
  data() {
    return {
      totalObj: {
        cq: 0,
        yjbj: 0,
        ejbj: 0,
        cqone: 0,
        yjbjone: 0,
        ejbjone: 0,
      },
      list1: [],
      list2: [],
      monthList: ["1月", "2月", "3月", "4月", "5月", "6月"],
    };
  },
  created() {},
  methods: {
    handleData(data) {
      if (data.length !== 0) {
        this.totalObj.cq = data[12].hj;
        this.totalObj.yjbj = data[14].hj;
        this.totalObj.ejbj = data[16].hj;
        this.totalObj.cqone = data[13].hj;
        this.totalObj.yjbjone = data[15].hj;
        this.totalObj.ejbjone = data[17].hj;

        this.list1[0] = {
          label: "工程测绘部",
          num: data[12].gcchb,
          total: data[12].hj,
        };
        this.list1[1] = {
          label: "管线工程部",
          num: data[12].gxgcb,
          total: data[12].hj,
        };
        this.list1[2] = {
          label: "不动产测绘部",
          num: data[12].bdcchb,
          total: data[12].hj,
        };
        this.list1[3] = {
          label: "地理信息部",
          num: data[12].dlxxb,
          total: data[12].hj,
        };
        this.list1.sort((a, b) => b.num - a.num);

        this.list2[0] = {
          label: "工程测绘部",
          num: data[13].gcchb,
          total: data[13].hj,
        };
        this.list2[1] = {
          label: "管线工程部",
          num: data[13].gxgcb,
          total: data[13].hj,
        };
        this.list2[2] = {
          label: "不动产测绘部",
          num: data[13].bdcchb,
          total: data[13].hj,
        };
        this.list2[3] = {
          label: "地理信息部",
          num: data[13].dlxxb,
          total: data[13].hj,
        };
        this.list2.sort((a, b) => b.num - a.num);
      }
    },
    format(percentage) {
      return percentage;
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
  background: url("~@/assets/cockpit/subdivision/administration/item-bg.png")
    no-repeat 0/ 100% 100%;
  position: relative;
  display: flex;
  flex-direction: column;

  .co-title {
    flex-shrink: 0;
  }
}
.bb4-main {
  width: 46%;
  position: relative;
  z-index: 1;
  margin-top: 26px;
  ul {
    list-style: none;
    // margin: 0;
    padding: 0;
  }
}

.bb4-list {
  width: 100%;
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;
  margin-top: 20px;
  .bb4-l-each {
    flex: 1;
    position: relative;
    .bb4-le-sup1 {
      width: 100%;
      height: 20px;
      font-size: 12px !important;
      line-height: 16px;
      color: #dffeff;
      text-align: center;
      margin-top: 7px;
    }
    .bb4-le-sup2 {
      width: 100%;
      height: 34px;
      font-size: 12px !important;
      line-height: 17px;
      color: #dffeff;
      text-align: center;
      margin-top: 7px;
    }
    .bb4-le-sub {
      width: 94px;
      height: 114px;
      position: relative;
      margin: 0 auto;
      .bb4-leb-light {
        width: 142px;
        height: 98px;
        position: absolute;
        top: -8px;
        left: 50%;
        transform: translate(-50%, 0);
        z-index: 5;
        animation: bbLight ease 2s infinite;
      }
      .bb4-leb-tri {
        width: 20px;
        height: 10px;
        background: url("~@/assets/cockpit/bb4-leb-tri.png") no-repeat 0/ 100% 100%;
        position: absolute;
        bottom: 8px;
        left: 50%;
        transform: translate(-50%, 0);
        z-index: 5;
        animation: bbTri ease 2s infinite;
      }

      .bb4-leb-word {
        width: 100%;
        position: absolute;
        left: 0;
        // top: 44px;
        z-index: 10;
        > p {
          font-size: 22px !important;
          text-align: center;
          color: #fff;
          text-shadow: 0 0 4px #fff;
        }
        > h6 {
          font-size: 12px;
          text-align: center;
          color: #fff;
          margin-top: 4px;
        }
      }
    }
    .bb4-le-sub1 {
      background: url("~@/assets/cockpit/bb4-le-sub1.png") no-repeat 0/ 100% 100%;
      .bb4-leb-light {
        background: url("~@/assets/cockpit/bb4-leb-light1.png") no-repeat 0/ 100%
          100%;
      }
    }
    .bb4-le-sub2 {
      background: url("~@/assets/cockpit/bb4-le-sub2.png") no-repeat 0/ 100% 100%;
      .bb4-leb-light {
        background: url("~@/assets/cockpit/bb4-leb-light2.png") no-repeat 0/ 100%
          100%;
      }
    }
    .bb4-le-sub3 {
      background: url("~@/assets/cockpit/bb4-le-sub3.png") no-repeat 0/ 100% 100%;
      .bb4-leb-light {
        background: url("~@/assets/cockpit/bb4-leb-light3.png") no-repeat 0/ 100%
          100%;
      }
    }
  }
}
.item3-content {
  width: 46%;
  display: flex;
  justify-content: space-between;

  .item {
    width: 100%;
    .title {
      position: relative;
      width: 100%;
      height: 24px;
      line-height: 24px;
      font-size: 14px !important;
      color: #fff;
      margin-top: 16px;
      .bg {
        width: 100%;
        height: 100%;
        background: url("~@/assets/cockpit/subdivision/approve/item3-text-bg.png")
          no-repeat 0/ 100% 100%;
        margin-left: -22px;
      }
      .text {
        position: absolute;
        left: 2px;
        top: 0;
        width: 100%;
      }
    }
    ul {
      color: #fff;
      margin-left: -50px;
      li {
        width: 86%;
        display: flex;
        margin-top: 22px;
        .label {
          margin-top: -6px;
          font-size: small;
          width: 106px;
          flex-shrink: 0;
          text-align: right;
          padding-right: 8px;
          text-overflow: ellipsis;
          overflow: hidden;
          white-space: nowrap;
        }
        .process {
          flex: 1;
        }
        .right {
          width: 0;
          flex-shrink: 0;
          margin-left: 6px;
          margin-top: -3px;
        }
      }
    }
  }
}
::v-deep .el-progress__text {
  width: 32px;
  color: #fff;
}
::v-deep .el-progress-bar__outer {
  background-color: #304c7e;
  height: 10px !important;
  border-radius: 0 !important;
}
::v-deep .el-progress-bar__inner {
  background: linear-gradient(
    270deg,
    #00d8ff 0%,
    rgba(0, 216, 255, 0.15) 100%
  ) !important;
  box-shadow: 0 0 6px #00d8ff !important;
  border-radius: 0 !important;
}
.item3-top {
  display: flex;
  justify-content: space-between;
  width: 100%;
}
@keyframes bbLight {
  0% {
    opacity: 1;
  }
  50% {
    opacity: 0.5;
  }
  100% {
    opacity: 1;
  }
}
@keyframes bbTri {
  0% {
    transform: translate(-50%, 0);
  }
  50% {
    transform: translate(-50%, -5px);
  }
  100% {
    transform: translate(-50%, 0);
  }
}
.item3-bottom {
  height: 230px;
  .title {
    position: relative;
    width: 100%;
    height: 24px;
    line-height: 24px;
    font-size: 14px !important;
    color: #fff;
    margin-top: 16px;
    .bg {
      width: 50%;
      height: 100%;
      background: url("~@/assets/cockpit/subdivision/approve/item3-text-bg.png")
        no-repeat 0/ 100% 100%;
      margin-left: -22px;
    }
    .text {
      position: absolute;
      left: 2px;
      top: 0;
      width: 100%;
    }
  }
  .floorage-echart {
    width: 100%;
    height: 140px;
    margin-top: 24px;
  }
}
</style>
