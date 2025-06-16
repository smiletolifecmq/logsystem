<template>
  <div class="bb-bg">
    <div class="co-title">
      <div
        class="co-title-name"
        style="margin-top: 0.05rem !important; margin-left: 0.5rem !important"
      >
        <b style="font-size: large; color: aliceblue">本年度项目状态占比</b>
      </div>
      <div class="co-title-right"></div>
    </div>
    <div class="bpl-container">
      <ul style="list-style-type: none; flex: 2" class="co-mid-container">
        <li class="co-mid-item1">
          <div class="co-title">
            <div
              class="co-title-name"
              style="
                margin-top: 0.05rem !important;
                margin-left: 0.5rem !important;
              "
            >
              <b style="font-size: large; color: aliceblue">工程测绘部</b>
            </div>

            <div class="co-title-right"></div>
          </div>
          <div class="bpl-container">
            <div class="bb4-main">
              <ul class="bb4-list">
                <li class="bb4-l-each">
                  <div class="bb4-le-sub bb4-le-sub1">
                    <div class="bb4-leb-light"></div>
                    <div class="bb4-leb-tri"></div>
                    <div class="bb4-leb-word">
                      <p>{{ gcchbrwap }}</p>
                      <h6 style="color: indianred">
                        {{
                          (
                            (gcchbrwap /
                              (gcchbrwap + gxgcbrwap + bdcrwap + dlrwap)) *
                            100
                          ).toFixed(2)
                        }}%
                      </h6>
                      <h6>任务安排</h6>
                    </div>
                  </div>
                </li>
                <li class="sp-right-list">
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">作业中</p>

                      <p class="num">
                        {{ gcchbzyz }}
                        <span style="color: indianred"
                          >({{
                            ((gcchbzyz / gcchbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">项目分包数</p>
                      <p class="num">
                        {{ gcchbfbs }}
                        <span style="color: indianred"
                          >({{
                            ((gcchbfbs / gcchbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">作业办结</p>
                      <p class="num">
                        {{ gcchbzybj }}
                        <span style="color: indianred"
                          >({{
                            ((gcchbzybj / gcchbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">一检办结</p>
                      <p class="num">
                        {{ gcchbyj
                        }}<span style="color: indianred"
                          >({{
                            ((gcchbyj / gcchbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">二检办结</p>
                      <p class="num">
                        {{ gcchbej
                        }}<span style="color: indianred"
                          >({{
                            ((gcchbej / gcchbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">项目出件</p>
                      <p class="num">
                        {{ gcchbcj
                        }}<span style="color: indianred"
                          >({{
                            ((gcchbcj / gcchbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">成果送达</p>
                      <p class="num">
                        {{ gcchbsd
                        }}<span style="color: indianred"
                          >({{
                            ((gcchbsd / gcchbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </li>
        <li class="co-mid-item1">
          <div class="co-title">
            <div
              class="co-title-name"
              style="
                margin-top: 0.05rem !important;
                margin-left: 0.5rem !important;
              "
            >
              <b style="font-size: large; color: aliceblue">管线工程部</b>
            </div>

            <div class="co-title-right"></div>
          </div>
          <div class="bpl-container">
            <div class="bb4-main">
              <ul class="bb4-list">
                <li class="bb4-l-each">
                  <div class="bb4-le-sub bb4-le-sub1">
                    <div class="bb4-leb-light"></div>
                    <div class="bb4-leb-tri"></div>
                    <div class="bb4-leb-word">
                      <p>{{ gxgcbrwap }}</p>
                      <h6 style="color: indianred">
                        {{
                          (
                            (gxgcbrwap /
                              (gcchbrwap + gxgcbrwap + bdcrwap + dlrwap)) *
                            100
                          ).toFixed(2)
                        }}%
                      </h6>
                      <h6>任务安排</h6>
                    </div>
                  </div>
                </li>
                <li class="sp-right-list">
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">作业中</p>
                      <p class="num">
                        {{ gxgcbzyz
                        }}<span style="color: indianred"
                          >({{
                            ((gxgcbzyz / gxgcbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">项目分包数</p>
                      <p class="num">
                        {{ gxgcbfbs }}
                        <span style="color: indianred"
                          >({{
                            ((gxgcbfbs / gxgcbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">作业办结</p>
                      <p class="num">
                        {{ gxgcbzybj }}
                        <span style="color: indianred"
                          >({{
                            ((gxgcbzybj / gxgcbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">一检办结</p>
                      <p class="num">
                        {{ gxgcbyj }}
                        <span style="color: indianred"
                          >({{
                            ((gxgcbyj / gxgcbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">二检办结</p>
                      <p class="num">
                        {{ gxgcbej }}
                        <span style="color: indianred"
                          >({{
                            ((gxgcbej / gxgcbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">项目出件</p>
                      <p class="num">
                        {{ gxgcbcj }}
                        <span style="color: indianred"
                          >({{
                            ((gxgcbcj / gxgcbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">成果送达</p>
                      <p class="num">
                        {{ gxgcbsd }}
                        <span style="color: indianred"
                          >({{
                            ((gxgcbsd / gxgcbrwap) * 100).toFixed(2)
                          }})%</span
                        >
                      </p>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </li>
      </ul>
      <ul style="list-style-type: none; flex: 2" class="co-mid-container">
        <li class="co-mid-item1">
          <div class="co-title">
            <div
              class="co-title-name"
              style="
                margin-top: 0.05rem !important;
                margin-left: 0.5rem !important;
              "
            >
              <b style="font-size: large; color: aliceblue">不动产测绘部</b>
            </div>

            <div class="co-title-right"></div>
          </div>
          <div class="bpl-container">
            <div class="bb4-main">
              <ul class="bb4-list">
                <li class="bb4-l-each">
                  <div class="bb4-le-sub bb4-le-sub1">
                    <div class="bb4-leb-light"></div>
                    <div class="bb4-leb-tri"></div>
                    <div class="bb4-leb-word">
                      <p>{{ bdcrwap }}</p>
                      <h6 style="color: indianred">
                        {{
                          (
                            (bdcrwap /
                              (gcchbrwap + gxgcbrwap + bdcrwap + dlrwap)) *
                            100
                          ).toFixed(2)
                        }}%
                      </h6>
                      <h6>任务安排</h6>
                    </div>
                  </div>
                </li>
                <li class="sp-right-list">
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">作业中</p>
                      <p class="num">
                        {{ bdczyz }}
                        <span style="color: indianred"
                          >({{ ((bdczyz / bdcrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">项目分包数</p>
                      <p class="num">
                        {{ bdcfbs }}
                        <span style="color: indianred"
                          >({{ ((bdcfbs / bdcrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">作业办结</p>
                      <p class="num">
                        {{ bdczybj }}
                        <span style="color: indianred"
                          >({{ ((bdczybj / bdcrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">一检办结</p>
                      <p class="num">
                        {{ bdcyj }}
                        <span style="color: indianred"
                          >({{ ((bdcyj / bdcrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">二检办结</p>
                      <p class="num">
                        {{ bdcej }}
                        <span style="color: indianred"
                          >({{ ((bdcej / bdcrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">项目出件</p>
                      <p class="num">
                        {{ bdccj }}
                        <span style="color: indianred"
                          >({{ ((bdccj / bdcrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">成果送达</p>
                      <p class="num">
                        {{ bdcsd }}
                        <span style="color: indianred"
                          >({{ ((bdcsd / bdcrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </li>
        <li class="co-mid-item1">
          <div class="co-title">
            <div
              class="co-title-name"
              style="
                margin-top: 0.05rem !important;
                margin-left: 0.5rem !important;
              "
            >
              <b style="font-size: large; color: aliceblue">地理信息部</b>
            </div>

            <div class="co-title-right"></div>
          </div>
          <div class="bpl-container">
            <div class="bb4-main">
              <ul class="bb4-list">
                <li class="bb4-l-each">
                  <div class="bb4-le-sub bb4-le-sub1">
                    <div class="bb4-leb-light"></div>
                    <div class="bb4-leb-tri"></div>
                    <div class="bb4-leb-word">
                      <p>{{ dlrwap }}</p>
                      <h6 style="color: indianred">
                        {{
                          (
                            (dlrwap /
                              (gcchbrwap + gxgcbrwap + bdcrwap + dlrwap)) *
                            100
                          ).toFixed(2)
                        }}%
                      </h6>
                      <h6>任务安排</h6>
                    </div>
                  </div>
                </li>
                <li class="sp-right-list">
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">作业中</p>
                      <p class="num">
                        {{ dlzyz }}
                        <span style="color: indianred"
                          >({{ ((dlzyz / dlrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">项目分包数</p>
                      <p class="num">
                        {{ dlfbs }}
                        <span style="color: indianred"
                          >({{ ((dlfbs / dlrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">作业办结</p>
                      <p class="num">
                        {{ dlzybj }}
                        <span style="color: indianred"
                          >({{ ((dlzybj / dlrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">一检办结</p>
                      <p class="num">
                        {{ dlyj }}
                        <span style="color: indianred"
                          >({{ ((dlyj / dlrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">二检办结</p>
                      <p class="num">
                        {{ dlej }}
                        <span style="color: indianred"
                          >({{ ((dlej / dlrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">项目出件</p>
                      <p class="num">
                        {{ dlcj }}
                        <span style="color: indianred"
                          >({{ ((dlcj / dlrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                  <div class="item">
                    <div class="icon"></div>
                    <div class="text-area">
                      <p class="text">成果送达</p>
                      <p class="num">
                        {{ dlsd }}
                        <span style="color: indianred"
                          >({{ ((dlsd / dlrwap) * 100).toFixed(2) }})%</span
                        >
                      </p>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "Item7",
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
      gcchbrwap: 0,
      gcchbzyz: 0,
      gcchbfbs: 0,
      gcchbzybj: 0,
      gcchbyj: 0,
      gcchbej: 0,
      gcchbcj: 0,
      gcchbsd: 0,

      gxgcbrwap: 0,
      gxgcbzyz: 0,
      gxgcbfbs: 0,
      gxgcbzybj: 0,
      gxgcbyj: 0,
      gxgcbej: 0,
      gxgcbcj: 0,
      gxgcbsd: 0,

      bdcrwap: 0,
      bdczyz: 0,
      bdcfbs: 0,
      bdczybj: 0,
      bdcyj: 0,
      bdcej: 0,
      bdccj: 0,
      bdcsd: 0,

      dlrwap: 0,
      dlzyz: 0,
      dlfbs: 0,
      dlzybj: 0,
      dlyj: 0,
      dlej: 0,
      dlcj: 0,
      dlsd: 0,
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
    };
  },
  created() {},
  methods: {
    handleData(data) {
      if (data.length !== 0) {
        this.gcchbrwap = data[1].gcchb;
        this.gcchbzyz = data[18].gcchb;
        this.gcchbfbs = data[19].gcchb;
        this.gcchbzybj = data[20].gcchb;
        this.gcchbyj = data[21].gcchb;
        this.gcchbej = data[22].gcchb;
        this.gcchbcj = data[23].gcchb;
        this.gcchbsd = data[24].gcchb;

        this.gxgcbrwap = data[1].gxgcb;
        this.gxgcbzyz = data[18].gxgcb;
        this.gxgcbfbs = data[19].gxgcb;
        this.gxgcbzybj = data[20].gxgcb;
        this.gxgcbyj = data[21].gxgcb;
        this.gxgcbej = data[22].gxgcb;
        this.gxgcbcj = data[23].gxgcb;
        this.gxgcbsd = data[24].gxgcb;

        this.bdcrwap = data[1].bdcchb;
        this.bdczyz = data[18].bdcchb;
        this.bdcfbs = data[19].bdcchb;
        this.bdczybj = data[20].bdcchb;
        this.bdcyj = data[21].bdcchb;
        this.bdcej = data[22].bdcchb;
        this.bdccj = data[23].bdcchb;
        this.bdcsd = data[24].bdcchb;

        this.dlrwap = data[1].dlxxb;
        this.dlzyz = data[18].dlxxb;
        this.dlfbs = data[19].dlxxb;
        this.dlzybj = data[20].dlxxb;
        this.dlyj = data[21].dlxxb;
        this.dlej = data[22].dlxxb;
        this.dlcj = data[23].dlxxb;
        this.dlsd = data[24].dlxxb;
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
.co-mid-container {
  display: flex;
  padding: 0;
  margin: 0;
  list-style: none;
}

.co-mid-item1,
.co-mid-item2 {
  flex: 1; /* 或使用固定宽度，如 width: 50%; */
  margin-right: 10px; /* 可选：设置间距 */
}
.co-mid-item2 {
  margin-right: 0; /* 最后一项去掉右边距 */
}

.bb-bg {
  width: 100%;
  height: 100%;
  padding: 12px 12px 12px 16px;
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
  .bpl-container {
    padding-top: 8px;
    overflow-x: hidden;
  }
}
.bb4-main {
  width: 100%;
  position: relative;
  z-index: 1;
}
.right-text {
  font-size: 12px !important;
  color: #dffeff;
}
.bb4-list {
  list-style-type: none;
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  .sp-right-list {
    width: 100%;
    // flex: 2;
    margin-left: 10px;
    display: flex;
    flex-wrap: wrap;
    border-left: solid 1px rgba(255, 255, 255, 0.1);
    margin-top: -4px;

    .item {
      display: flex;
      background: rgba(255, 255, 255, 0.05);
      width: 114px;
      // height: 42px;
      padding: 8px;
      margin-left: 12px;
      .icon {
        width: 4px;
        height: 4px;
        margin-top: 4px;
        margin-right: 6px;
        display: block;
        background: #f9f0d4;
        border-radius: 1px;
        box-shadow: 0 0 4px 1px #f0b04a;
        transform: rotate(45deg);
      }
      .text-area {
        margin-top: -13px;
        .text {
          font-size: 12px !important;
          color: #dffeff;
        }
        .num {
          color: #fff;
          font-size: 14px;
          // line-height: 20px;
        }
      }
    }
  }
  .bb4-l-each {
    flex: 1;
    position: relative;
    margin-left: -29px;
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
      width: 112px;
      height: 138px;
      position: relative;
      margin: 0 auto;
      .bb4-leb-light {
        width: 171px;
        height: 100px;
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
        // top: 11px;
        z-index: 10;
        > p {
          font-size: 22px !important;
          text-align: center;
          color: #fff;
          text-shadow: 0 0 4px #fff;
        }
        > h6 {
          width: 65%;
          font-size: 12px !important;
          line-height: 16px;
          text-align: center;
          color: #dffeff;
          margin: auto;
          margin-top: 8px;
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
