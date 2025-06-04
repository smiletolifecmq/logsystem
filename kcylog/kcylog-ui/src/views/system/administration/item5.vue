<template>
  <div class="bpl1-bg">
    <div class="co-title">
      <div class="co-title-name">
        <img
          src="@/assets/cockpit/subdivision/administration/item5-title.png"
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
    <div class="map-right-menu">
      <div class="list-header">
        <p>会议类型</p>
        <p>会议数量</p>
        <p>纪要上传数</p>
      </div>
      <ul class="list-item">
        <!--  -->
        <li v-for="(item, index) in listData" :key="index">
          <div class="name">
            {{ item.dep_name }}
            <div class="before"></div>
          </div>
          <div class="length">{{ item.count }}场</div>
          <div class="des">{{ item.jy_count }}份</div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "Item5",
  components: {},
  data() {
    return {
      listData: [
        {
          name: "外部会议",
          num1: "95",
          num2: "22",
        },
        {
          name: "党政联席会",
          num1: "95",
          num2: "22",
        },
        {
          name: "调研会",
          num1: "95",
          num2: "22",
        },
        {
          name: "局务会",
          num1: "95",
          num2: "22",
        },
        {
          name: "其他会议",
          num1: "95",
          num2: "22",
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
.item3-content {
  width: 98%;
  display: flex;
  justify-content: space-between;
  margin: auto;
  .item {
    width: 22%;
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
        margin-left: -16px;
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
        margin-top: 12px;
        .label {
          width: 80px;
          flex-shrink: 0;
        }
        .process {
          flex: 1;
        }
        .right {
          width: 0;
          flex-shrink: 0;
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
  background-color: #072357;
}
::v-deep .el-progress-bar__inner {
  background: linear-gradient(
    270deg,
    #00d8ff 0%,
    rgba(0, 216, 255, 0.15) 100%
  ) !important;
  box-shadow: 0 0 6px #00d8ff !important;
}
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
    height: 32px;
    line-height: 32px;
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
      height: 32px;
      line-height: 32px;
      background: rgba(255, 255, 255, 0.05);
      margin: auto;
      margin-top: 8px;
      text-align: left;

      .name {
        flex: 2;
        position: relative;
        padding-left: 20px;
        .before {
          position: absolute;
          left: 0;
          top: 0;
          width: 4px;
          height: 32px;
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
