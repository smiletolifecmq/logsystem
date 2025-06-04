<template>
  <div class="bpl1-bg">
    <div class="co-title">
      <div class="co-title-name">
        <img
          src="@/assets/cockpit/subdivision/administration/item1-title.png"
          alt=""
        />
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
        <div class="text">全局督办事务总体情况</div>
      </div>
      <ul class="item">
        <li v-for="(item, index) in data" :key="index">
          <div class="lf">
            <div class="yellow-icon"></div>
            <div class="label">{{ item.name }}</div>
          </div>
          <div class="rg">
            <div class="num">{{ item.count }}<span>件</span></div>
            <div class="line"></div>
            <div class="text">
              环比：
              <span v-if="Number(item.compare) >= 100" class="red"
                ><i class="el-icon-top"></i
                >{{
                  item.compare && parseFloat(item.compare).toFixed(2)
                }}%</span
              >
              <span v-else class="green"
                ><i class="el-icon-bottom"></i
                >{{
                  item.compare && parseFloat(item.compare).toFixed(2)
                }}%</span
              >
            </div>
          </div>
        </li>
      </ul>
    </div>
    <ul class="bpl1-stac" style="margin-left: -34px">
      <li class="bpl1-st-each">
        <i class="bpl1-ste-icon"
          ><img
            src="@/assets/cockpit/subdivision/administration/item1-icon1.png"
            alt=""
        /></i>
        <div class="bpl1-ste-word">
          <h6>办结率</h6>
          <p>
            现状情况<span
              >{{
                parseFloat(
                  (data[1] && data[1].count) / (data[0] && data[0].count)
                ).toFixed(2)
              }}%</span
            >
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
          <h6>正点率</h6>
          <p>
            现状情况<span
              >{{
                parseFloat(
                  (data[2] && data[2].count) / (data[0] && data[0].count)
                ).toFixed(2)
              }}%</span
            >
          </p>
        </div>
      </li>
    </ul>
    <!--  -->
    <div class="content">
      <div class="title">
        <div class="bg"></div>
        <div class="text">处室办结量TOP10</div>
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
              {{ item.dept_name }}
              <div class="before"></div>
            </div>
            <div class="length">{{ item.count }}</div>
            <div class="des">{{ item.bj_count }}</div>
            <div class="des">{{ item.bj_lv }}%</div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BplItem1",
  components: {},
  data() {
    return {
      listData: [
        {
          name: "建设项目审查一处",
          num1: "95",
          num2: "22",
          num3: "95",
        },
        {
          name: "建设项目审查一处",
          num1: "95",
          num2: "22",
          num3: "95",
        },
        {
          name: "建设项目审查一处",
          num1: "95",
          num2: "22",
          num3: "95",
        },
        {
          name: "建设项目审查一处",
          num1: "95",
          num2: "22",
          num3: "95",
        },
      ],
      isFold: true,
      data: {},
      headerData: [
        {
          fieldName: "处室名称",
        },
        {
          fieldName: "任务总量",
        },
        {
          fieldName: "办结量",
        },
        {
          fieldName: "办结率",
        },
      ],
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
      width: 100%;
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
</style>
