<template>
  <div class="bb-bg">
    <div class="co-title">
      <div class="co-title-name">
        <img
          src="@/assets/cockpit/subdivision/administration/item2-title.png"
          alt=""
        />
      </div>
      <div class="co-title-right">
        <div class="co-title-breathe">
          <i></i><i></i><i></i><i></i><i></i><i></i>
        </div>
      </div>
    </div>
    <div class="bb4-main">
      <ul class="bb4-list">
        <li class="bb4-l-each">
          <div class="bb4-le-sub bb4-le-sub1">
            <div class="bb4-leb-light"></div>
            <div class="bb4-leb-tri"></div>
            <div class="bb4-leb-word">
              <p>{{ totalObj.count }}</p>
              <h6>登记量</h6>
            </div>
          </div>
        </li>
        <li class="bb4-l-each">
          <div class="bb4-le-sub bb4-le-sub1">
            <div class="bb4-leb-light"></div>
            <div class="bb4-leb-tri"></div>
            <div class="bb4-leb-word">
              <p>{{ totalObj.bj_count }}</p>
              <h6>办结量</h6>
            </div>
          </div>
        </li>
        <li class="bb4-l-each">
          <div class="bb4-le-sub bb4-le-sub3">
            <div class="bb4-leb-light"></div>
            <div class="bb4-leb-tri"></div>
            <div class="bb4-leb-word">
              <p>{{ totalObj.bj_lv }}%</p>
              <h6>办结率</h6>
            </div>
          </div>
        </li>
        <li class="bb4-l-each">
          <div class="bb4-le-sub bb4-le-sub2">
            <div class="bb4-leb-light"></div>
            <div class="bb4-leb-tri"></div>
            <div class="bb4-leb-word">
              <p>{{ totalObj.zd_lv }}%</p>
              <h6>正点率</h6>
            </div>
          </div>
        </li>
      </ul>
    </div>
    <!--  -->
    <div class="item3-content">
      <div class="item">
        <div class="title">
          <div class="bg"></div>
          <div class="text">处室办理量TOP5</div>
        </div>
        <ul>
          <li v-for="(item, index) in list1" :key="index">
            <div class="label" :title="item.dep_name">{{ item.dep_name }}</div>
            <div class="process">
              <el-progress
                v-if="item.value"
                :percentage="Number(item.value)"
                :show-text="false"
                :format="format"
              ></el-progress>
            </div>
            <div class="right">{{ item.count }}</div>
          </li>
        </ul>
      </div>

      <div class="item">
        <div class="title">
          <div class="bg"></div>
          <div class="text">处室正点率TOP5</div>
        </div>
        <ul>
          <li v-for="(item, index) in list2" :key="index">
            <div class="label" :title="item.dep_name">{{ item.dep_name }}</div>
            <div class="process">
              <el-progress
                v-if="item.zd_lv"
                :percentage="Number(item.zd_lv)"
              ></el-progress>
            </div>
            <div class="right"></div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Item2",
  components: {},
  data() {
    return {
      list1: [
        {
          label: "用途管制处",
          num: "100",
        },
        {
          label: "权益处",
          num: "90",
        },
        {
          label: "信访处",
          num: "80",
        },
        {
          label: "科室处",
          num: "70",
        },
        {
          label: "建二处",
          num: "60",
        },
      ],
      list2: [
        {
          label: "用途管制处",
          num: "100",
        },
        {
          label: "权益处",
          num: "90",
        },
        {
          label: "信访处",
          num: "80",
        },
        {
          label: "科室处",
          num: "70",
        },
        {
          label: "建二处",
          num: "60",
        },
      ],
      totalObj: {},
    };
  },
  created() {},
  methods: {
    format(percentage) {
      return percentage;
    },
  },
};
</script>
<style lang="scss" scoped>
@import "~@/styles/cockpit.scss";

.bb-bg {
  width: 100%;
  height: 100%;
  padding: 16px;
  background: url("~@/assets/cockpit/subdivision/administration/item-bg.png")
    no-repeat 0/ 100% 100%;
  .bpl-container {
    padding-top: 8px;
    overflow-x: hidden;
  }
}
.bb4-main {
  width: 100%;
  position: relative;
  z-index: 1;
  ul {
    list-style: none;
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
        top: 44px;
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
  width: 98%;
  display: flex;
  justify-content: space-between;
  margin: auto;
  margin-top: 20px;
  .item {
    width: 46%;
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
      margin-left: 2px;
      li {
        width: 100%;
        display: flex;
        margin-top: 22px;
        .label {
          width: 94px;
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
</style>
