<template>
  <div class="bpl1-bg">
    <div class="co-title">
      <div class="co-title-name">
        <img
          src="@/assets/cockpit/subdivision/administration/item6-title.png"
          alt=""
        />
      </div>
      <div class="co-title-right">
        <div class="co-title-breathe">
          <i></i><i></i><i></i><i></i><i></i><i></i>
        </div>
      </div>
    </div>
    <div class="content">
      <div class="total-area">
        <div class="label">档案总数:</div>
        <div class="num">{{ totalObj.count }}<span>份</span></div>
      </div>
      <div class="title">
        <div class="bg"></div>
        <div class="text">档案借阅排名情况</div>
        <div class="unit">单位：人次</div>
      </div>
      <div class="list-container-top">
        <div class="lf">
          <ul>
            <li v-for="(item, index) in list1" :key="index">
              <div class="name-area">
                <div class="index" :class="index > 2 ? 'index-2' : 'index-1'">
                  {{ index + 1 }}
                </div>
                <div class="label" :title="item.type_name">
                  {{ item.type_name }}
                </div>
              </div>

              <div class="num">{{ item.count }}</div>
            </li>
          </ul>
        </div>
        <div class="lf">
          <ul>
            <li v-for="(item, index) in list2" :key="index">
              <div class="name-area">
                <div class="index index-2">{{ index + 5 }}</div>
                <div class="label" :title="item.type_name">
                  {{ item.type_name }}
                </div>
              </div>

              <div class="num">{{ item.count }}</div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Item6",
  components: {},
  data() {
    return {
      totalObj: {
        count: "0",
      },
      list1: [],
      list2: [],
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
  .title {
    position: relative;
    width: 100%;
    height: 26px;
    line-height: 26px;
    font-size: 16px !important;
    color: #fff;
    margin-top: 16px;
    // margin-left: 4px;
    .bg {
      width: 80%;
      height: 100%;
      background: url("~@/assets/cockpit/subdivision/approve/item3-text-bg.png")
        no-repeat 0/ 100% 100%;
      margin-left: -12px;
    }
    .text {
      position: absolute;
      left: 8px;
      top: 0;
      width: 100%;
    }
    .unit {
      position: absolute;
      right: 0;
      top: 0;
      font-size: 12px !important;
    }
  }
}
.list-container-top {
  display: flex;

  justify-content: space-between;
  .lf {
    width: 46%;
    display: block;
    .title {
      font-size: 14px !important;
      color: #dffeff;
      margin-top: 12px;
    }
    ul {
      margin-top: 16px;
      li {
        display: flex;
        font-size: 12px !important;
        color: #fff;
        justify-content: space-between;
        margin-top: 16px;
        .name-area {
          display: flex;
          .index {
            width: 20px;
            height: 20px;
            line-height: 20px;
            text-align: center;
          }
          .label {
            margin-left: 4px;
            text-overflow: ellipsis;
            white-space: nowrap;
            width: 150px;
            overflow: hidden;
            color: rgba(191, 254, 255, 1);
            font-size: 14px !important;
          }
          .index-1 {
            background: url("~@/assets/cockpit/subdivision/administration/index-icon.png")
              no-repeat 0/ 100% 100%;
          }
          .index-2 {
            // background: url('~@/assets/cockpit/subdivision/approve/item2-index-1.png') no-repeat 0/ 100% 100%;
            width: 20px;
            line-height: 20px;
            border: 1px solid #485f86;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 50%;
          }
        }
      }
    }
  }
}
</style>
