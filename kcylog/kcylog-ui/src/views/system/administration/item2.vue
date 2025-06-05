<template>
  <div class="bb-bg">
    <div class="co-title">
      <div
        class="co-title-name"
        style="margin-top: 0.05rem !important; margin-left: 0.5rem !important"
      >
        <b style="font-size: large; color: aliceblue">项目管理</b>
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
              <p>{{ totalObj.zyz }}</p>
              <h6>作业中</h6>
            </div>
          </div>
        </li>
        <li class="bb4-l-each">
          <div class="bb4-le-sub bb4-le-sub1">
            <div class="bb4-leb-light"></div>
            <div class="bb4-leb-tri"></div>
            <div class="bb4-leb-word">
              <p>{{ totalObj.rwap }}</p>
              <h6>任务安排</h6>
            </div>
          </div>
        </li>
        <li class="bb4-l-each">
          <div class="bb4-le-sub bb4-le-sub3">
            <div class="bb4-leb-light"></div>
            <div class="bb4-leb-tri"></div>
            <div class="bb4-leb-word">
              <p>{{ totalObj.fbs }}</p>
              <h6>分包数</h6>
            </div>
          </div>
        </li>
        <li class="bb4-l-each">
          <div class="bb4-le-sub bb4-le-sub2">
            <div class="bb4-leb-light"></div>
            <div class="bb4-leb-tri"></div>
            <div class="bb4-leb-word">
              <p>{{ totalObj.zybj }}</p>
              <h6>作业办结</h6>
            </div>
          </div>
        </li>
      </ul>
    </div>
    <!--  -->
    <div class="item3-content" style="margin-left: -12px; margin-top: auto">
      <div class="item">
        <div class="title" style="margin-left: 12px">
          <div class="bg"></div>
          <div class="text">作业中TOP</div>
        </div>
        <ul>
          <li v-for="(item, index) in list1" :key="index">
            <div class="label" :title="item.label">{{ item.label }}</div>
            <div class="process">
              <el-progress
                v-if="item.num"
                :percentage="(item.num / item.total) * 100"
                :show-text="false"
              ></el-progress>
            </div>
            <div class="right">{{ item.num }}</div>
          </li>
        </ul>
      </div>

      <div class="item">
        <div class="title" style="margin-left: 21px">
          <div class="bg"></div>
          <div class="text">任务安排TOP</div>
        </div>
        <ul>
          <li v-for="(item, index) in list2" :key="index">
            <div class="label" :title="item.label">{{ item.label }}</div>
            <div class="process">
              <el-progress
                v-if="item.num"
                :percentage="(item.num / item.total) * 100"
                :show-text="false"
              ></el-progress>
            </div>
            <div class="right">{{ item.num }}</div>
          </li>
        </ul>
      </div>
    </div>

    <!--  -->
    <div class="item3-content" style="margin-left: -12px; margin-top: auto">
      <div class="item">
        <div class="title" style="margin-left: 12px">
          <div class="bg"></div>
          <div class="text">项目分包数TOP</div>
        </div>
        <ul>
          <li v-for="(item, index) in list3" :key="index">
            <div class="label" :title="item.label">{{ item.label }}</div>
            <div class="process">
              <el-progress
                v-if="item.num"
                :percentage="(item.num / item.total) * 100"
                :show-text="false"
              ></el-progress>
            </div>
            <div class="right">{{ item.num }}</div>
          </li>
        </ul>
      </div>

      <div class="item">
        <div class="title" style="margin-left: 21px">
          <div class="bg"></div>
          <div class="text">作业办结TOP</div>
        </div>
        <ul>
          <li v-for="(item, index) in list4" :key="index">
            <div class="label" :title="item.label">{{ item.label }}</div>
            <div class="process">
              <el-progress
                v-if="item.num"
                :percentage="(item.num / item.total) * 100"
                :show-text="false"
              ></el-progress>
            </div>
            <div class="right">{{ item.num }}</div>
          </li>
        </ul>
      </div>
    </div>

    <div class="bb4-main" style="margin-top: 2.3rem">
      <ul class="bb4-list">
        <li class="bb4-l-each">
          <div class="bb4-le-sub bb4-le-sub1">
            <div class="bb4-leb-light"></div>
            <div class="bb4-leb-tri"></div>
            <div class="bb4-leb-word">
              <p>{{ totalObj.yjbj }}</p>
              <h6>一检办结</h6>
            </div>
          </div>
        </li>
        <li class="bb4-l-each">
          <div class="bb4-le-sub bb4-le-sub1">
            <div class="bb4-leb-light"></div>
            <div class="bb4-leb-tri"></div>
            <div class="bb4-leb-word">
              <p>{{ totalObj.ejbj }}</p>
              <h6>二检办结</h6>
            </div>
          </div>
        </li>
        <li class="bb4-l-each">
          <div class="bb4-le-sub bb4-le-sub3">
            <div class="bb4-leb-light"></div>
            <div class="bb4-leb-tri"></div>
            <div class="bb4-leb-word">
              <p>{{ totalObj.xmcj }}</p>
              <h6>项目出件</h6>
            </div>
          </div>
        </li>
        <li class="bb4-l-each">
          <div class="bb4-le-sub bb4-le-sub2">
            <div class="bb4-leb-light"></div>
            <div class="bb4-leb-tri"></div>
            <div class="bb4-leb-word">
              <p>{{ totalObj.cgsd }}</p>
              <h6>成果送达</h6>
            </div>
          </div>
        </li>
      </ul>
    </div>
    <!--  -->
    <div class="item3-content" style="margin-left: -12px; margin-top: auto">
      <div class="item">
        <div class="title" style="margin-left: 12px">
          <div class="bg"></div>
          <div class="text">一检办结TOP</div>
        </div>
        <ul>
          <li v-for="(item, index) in list5" :key="index">
            <div class="label" :title="item.label">{{ item.label }}</div>
            <div class="process">
              <el-progress
                v-if="item.num"
                :percentage="(item.num / item.total) * 100"
                :show-text="false"
              ></el-progress>
            </div>
            <div class="right">{{ item.num }}</div>
          </li>
        </ul>
      </div>

      <div class="item">
        <div class="title" style="margin-left: 21px">
          <div class="bg"></div>
          <div class="text">二检办结TOP</div>
        </div>
        <ul>
          <li v-for="(item, index) in list6" :key="index">
            <div class="label" :title="item.label">{{ item.label }}</div>
            <div class="process">
              <el-progress
                v-if="item.num"
                :percentage="(item.num / item.total) * 100"
                :show-text="false"
              ></el-progress>
            </div>
            <div class="right">{{ item.num }}</div>
          </li>
        </ul>
      </div>
    </div>
    <!--  -->
    <div class="item3-content" style="margin-left: -12px; margin-top: auto">
      <div class="item">
        <div class="title" style="margin-left: 12px">
          <div class="bg"></div>
          <div class="text">项目出件TOP</div>
        </div>
        <ul>
          <li v-for="(item, index) in list7" :key="index">
            <div class="label" :title="item.label">{{ item.label }}</div>
            <div class="process">
              <el-progress
                v-if="item.num"
                :percentage="(item.num / item.total) * 100"
                :show-text="false"
              ></el-progress>
            </div>
            <div class="right">{{ item.num }}</div>
          </li>
        </ul>
      </div>

      <div class="item">
        <div class="title" style="margin-left: 21px">
          <div class="bg"></div>
          <div class="text">成果送达TOP</div>
        </div>
        <ul>
          <li v-for="(item, index) in list8" :key="index">
            <div class="label" :title="item.label">{{ item.label }}</div>
            <div class="process">
              <el-progress
                v-if="item.num"
                :percentage="(item.num / item.total) * 100"
                :show-text="false"
              ></el-progress>
            </div>
            <div class="right">{{ item.num }}</div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Item2",
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
  mounted() {},
  components: {},
  data() {
    return {
      tjData: [],
      list1: [],
      list2: [],
      list3: [],
      list4: [],
      list5: [],
      list6: [],
      list7: [],
      list8: [],
      totalObj: {
        zyz: 0,
        rwap: 0,
        fbs: 0,
        zybj: 0,
        yjbj: 0,
        ejbj: 0,
        xmcj: 0,
        cgsd: 0,
      },
    };
  },
  created() {},
  methods: {
    handleData(data) {
      if (data.length !== 0) {
        this.tjData = data;
        this.totalObj.zyz = this.tjData[0].hj;
        this.totalObj.rwap = this.tjData[1].hj;
        this.totalObj.fbs = this.tjData[2].hj;
        this.totalObj.zybj = this.tjData[3].hj;

        this.totalObj.yjbj = this.tjData[5].hj;
        this.totalObj.ejbj = this.tjData[6].hj;
        this.totalObj.xmcj = this.tjData[7].hj;
        this.totalObj.cgsd = this.tjData[8].hj;

        // 作业中
        this.list1[0] = {
          label: "工程测绘部",
          num: this.tjData[0].gcchb,
          total: this.tjData[0].hj,
        };
        this.list1[1] = {
          label: "管线工程部",
          num: this.tjData[0].gxgcb,
          total: this.tjData[0].hj,
        };
        this.list1[2] = {
          label: "不动产测绘部",
          num: this.tjData[0].bdcchb,
          total: this.tjData[0].hj,
        };
        this.list1[3] = {
          label: "地理信息部",
          num: this.tjData[0].dlxxb,
          total: this.tjData[0].hj,
        };
        this.list1.sort((a, b) => b.num - a.num);
        // 任务安排
        this.list2[0] = {
          label: "工程测绘部",
          num: this.tjData[1].gcchb,
          total: this.tjData[1].hj,
        };
        this.list2[1] = {
          label: "管线工程部",
          num: this.tjData[1].gxgcb,
          total: this.tjData[1].hj,
        };
        this.list2[2] = {
          label: "不动产测绘部",
          num: this.tjData[1].bdcchb,
          total: this.tjData[1].hj,
        };
        this.list2[3] = {
          label: "地理信息部",
          num: this.tjData[1].dlxxb,
          total: this.tjData[1].hj,
        };
        this.list2.sort((a, b) => b.num - a.num);
        // 项目分包数
        this.list3[0] = {
          label: "工程测绘部",
          num: this.tjData[2].gcchb,
          total: this.tjData[2].hj,
        };
        this.list3[1] = {
          label: "管线工程部",
          num: this.tjData[2].gxgcb,
          total: this.tjData[2].hj,
        };
        this.list3[2] = {
          label: "不动产测绘部",
          num: this.tjData[2].bdcchb,
          total: this.tjData[2].hj,
        };
        this.list3[3] = {
          label: "地理信息部",
          num: this.tjData[2].dlxxb,
          total: this.tjData[2].hj,
        };
        this.list3.sort((a, b) => b.num - a.num);
        // 作业办结
        this.list4[0] = {
          label: "工程测绘部",
          num: this.tjData[3].gcchb,
          total: this.tjData[3].hj,
        };
        this.list4[1] = {
          label: "管线工程部",
          num: this.tjData[3].gxgcb,
          total: this.tjData[3].hj,
        };
        this.list4[2] = {
          label: "不动产测绘部",
          num: this.tjData[3].bdcchb,
          total: this.tjData[3].hj,
        };
        this.list4[3] = {
          label: "地理信息部",
          num: this.tjData[3].dlxxb,
          total: this.tjData[3].hj,
        };
        this.list4.sort((a, b) => b.num - a.num);
        // 一检办结
        this.list5[0] = {
          label: "工程测绘部",
          num: this.tjData[5].gcchb,
          total: this.tjData[5].hj,
        };
        this.list5[1] = {
          label: "管线工程部",
          num: this.tjData[5].gxgcb,
          total: this.tjData[5].hj,
        };
        this.list5[2] = {
          label: "不动产测绘部",
          num: this.tjData[5].bdcchb,
          total: this.tjData[5].hj,
        };
        this.list5[3] = {
          label: "地理信息部",
          num: this.tjData[5].dlxxb,
          total: this.tjData[5].hj,
        };
        this.list5.sort((a, b) => b.num - a.num);
        // 二检办结
        this.list6[0] = {
          label: "工程测绘部",
          num: this.tjData[6].gcchb,
          total: this.tjData[6].hj,
        };
        this.list6[1] = {
          label: "管线工程部",
          num: this.tjData[6].gxgcb,
          total: this.tjData[6].hj,
        };
        this.list6[2] = {
          label: "不动产测绘部",
          num: this.tjData[6].bdcchb,
          total: this.tjData[6].hj,
        };
        this.list6[3] = {
          label: "地理信息部",
          num: this.tjData[6].dlxxb,
          total: this.tjData[6].hj,
        };
        this.list6.sort((a, b) => b.num - a.num);
        // 项目出件
        this.list7[0] = {
          label: "工程测绘部",
          num: this.tjData[7].gcchb,
          total: this.tjData[7].hj,
        };
        this.list7[1] = {
          label: "管线工程部",
          num: this.tjData[7].gxgcb,
          total: this.tjData[7].hj,
        };
        this.list7[2] = {
          label: "不动产测绘部",
          num: this.tjData[7].bdcchb,
          total: this.tjData[7].hj,
        };
        this.list7[3] = {
          label: "地理信息部",
          num: this.tjData[7].dlxxb,
          total: this.tjData[7].hj,
        };
        this.list7.sort((a, b) => b.num - a.num);
        // 成果送达
        this.list8[0] = {
          label: "工程测绘部",
          num: this.tjData[8].gcchb,
          total: this.tjData[8].hj,
        };
        this.list8[1] = {
          label: "管线工程部",
          num: this.tjData[8].gxgcb,
          total: this.tjData[8].hj,
        };
        this.list8[2] = {
          label: "不动产测绘部",
          num: this.tjData[8].bdcchb,
          total: this.tjData[8].hj,
        };
        this.list8[3] = {
          label: "地理信息部",
          num: this.tjData[8].dlxxb,
          total: this.tjData[8].hj,
        };
        this.list8.sort((a, b) => b.num - a.num);
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
        top: 6px;
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
        // width: 100%;
      }
    }
    ul {
      color: #fff;
      margin-left: -50px;
      li {
        width: 100%;
        display: flex;
        margin-top: 22px;
        .label {
          width: 94px;
          flex-shrink: 0;
          text-align: right;
          padding-right: 8px;
          margin-top: -5px;
          text-overflow: ellipsis;
          overflow: hidden;
          white-space: nowrap;
          font-size: small;
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
