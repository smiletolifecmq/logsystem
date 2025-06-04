<template>
  <div class="co-body">
    <div class="co-head" style="background-color: #042c63">
      <div class="co-head">
        <div class="coh-lock">
          <div class="coh-lock-base"></div>
          <div class="coh-lock-breathe">
            <i></i><i></i><i></i><i></i><i></i><i></i>
          </div>
        </div>
        <div class="coh-line"></div>
        <div class="coh-base">
          <div class="coh-bab-top"></div>
          <div class="coh-bab-yl"></div>
          <div class="coh-bab-yr"></div>
          <div class="coh-bap-title">
            <div class="coh-bap-title-name"></div>
          </div>
        </div>
        <div class="coh-rine"></div>
        <div class="coh-rock">
          <div class="coh-rock-base">
            <div class="coh-rockb-sup">
              <span>{{ systemDate.date }}</span>
              <p>{{ systemDate.week }}</p>
              <h6>{{ systemDate.time }}</h6>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="co-body-sup" style="background-color: #042c63">
      <div class="co-bp-left">
        <ul style="list-style-type: none">
          <li class="co-bpl-item1">
            <Item1 ref="Item1Ref"></Item1>
          </li>
        </ul>
      </div>
      <!-- <div class="co-bp-mid">
        <ul class="mid-item" style="list-style-type: none">
          <li class="co-mid-item1">
            <Item2 ref="Item2Ref"></Item2>
          </li>
          <li class="co-mid-item2">
            <Item3 ref="Item3Ref"></Item3>
          </li>
        </ul>
      </div>
      <div class="co-bp-right">
        <ul style="list-style-type: none">
          <li class="co-right-item1">
            <Item4 ref="Item4Ref"></Item4>
          </li>
          <li class="co-right-item2">
            <Item5 ref="Item5Ref"></Item5>
          </li>
          <li class="co-right-item3">
            <Item6 ref="Item6Ref"></Item6>
          </li>
        </ul>
      </div> -->
    </div>
  </div>
</template>

<script>
import Item1 from "./system/administration/item1.vue";
import Item2 from "./system/administration/item2.vue";
import Item3 from "./system/administration/item3.vue";
import Item4 from "./system/administration/item4.vue";
import Item5 from "./system/administration/item5.vue";
import Item6 from "./system/administration/item6.vue";
export default {
  name: "Index",
  components: {
    Item1,
    Item2,
    Item3,
    Item4,
    Item5,
    Item6,
  },
  data() {
    return {
      systemDate: {
        date: "",
        week: "",
        time: "",
      },
    };
  },
  created() {
    this.getTime();
  },
  methods: {
    getTime() {
      let date = new Date();
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate() - 1;
      this.curTime = `${year}/${month}/${day}`;
    },
  },
};
</script>
<style lang="scss" scoped>
@import "~@/styles/cockpit.scss";
.page-index {
  width: 100%;
  height: 100%;
  background: #002160;
  overflow: auto;
}
.cockpit-bg {
  width: 1920px;
  height: 1080px;
  background: #002160;
  margin: auto;
}
.co-head {
  width: 100%;
  height: 82px;
  position: relative;
  z-index: 100;
}
.co-body {
  width: 100%;
  position: relative;
  z-index: 80;
  .co-body-bg {
    width: 100%;
    height: 40%;
    position: absolute;
    left: 0;
    bottom: 0;
    z-index: -1;
    > img {
      display: block;
      width: 100%;
      height: 100%;
    }
  }
  .co-body-sup {
    width: 100%;
    // padding: 8px 12px 12px 12px;
    position: relative;
    display: flex;
    ul {
      // border: solid 1px green;
      height: 100%;
    }
    .mid-item {
      .co-mid-item1,
      .co-mid-item2 {
        height: 489px;
      }
      .co-mid-item1 {
        margin-bottom: 6px;
        margin-top: -3px;
      }
    }

    &:after {
      content: "";
      display: table;
      clear: both;
    }
    .co-bp-center {
      width: 55%;
      height: 100%;
      padding: 0 0 12px;
      position: absolute;
      top: 0;
      left: 50%;
      transform: translate(-50%, 0);
      z-index: 90;
    }
    .co-bp-left {
      width: 460px;
      flex-shrink: 0;

      .co-bpl-item1 {
        height: 980px;
      }
    }
    .co-bp-mid {
      flex: 1;
      padding: 0 12px;
    }
    .co-bp-right {
      width: 460px;
      flex-shrink: 0;
      .co-right-item1,
      .co-right-item2,
      .co-right-item3 {
        height: 323.5px;
        // border: solid 1px yellow;
      }
      .co-right-item2 {
        margin: 6px 0;
      }
      .co-right-item1 {
        margin-top: -3px;
      }
    }
  }
  .co-body-sub {
    width: 100%;
    padding: 0 16px 16px;
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    .co-bb-item {
      flex: none;
      width: 24.4%;
      height: 240px;
    }
  }
}
.co-body-content {
  .co-mt {
    margin-top: 12px;
  }
  display: flex;
  color: white;
  .co-left {
    width: 476px;
    .co-lf-content {
      margin: 12px;
      // .co-lf-back {
      //   // height: 20px;
      // }
      .co-lf-zt {
        height: 723px;
        margin-top: 10px;
      }
      .co-lf-zx {
        height: 233px;
      }
    }
  }
  .co-right {
    width: 1428px;
  }
}
::v-deep .com-name-style {
  font-size: 18px !important;
  color: #fff;
  text-shadow: 0 0 14px #006aff !important;
}
::v-deep .co-title-name {
  height: 42px !important;
  margin-top: -9px !important;
  img {
    height: 70% !important;
    margin-top: 6px;
  }
}

// head
.co-head {
  width: 100%;
  height: 100%;
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;
  align-items: flex-start;
  .coh-lock {
    flex: none;
    width: 297px;
    height: 100%;
    padding: 16px 16px;
    background: url("~@/assets/cockpit/subdivision/coh-lock.png") no-repeat
      center bottom/ 100% 84px;
    margin-right: -1px;
    position: relative;
    .coh-lock-base {
      width: 100%;
      display: flex;
      flex-flow: row nowrap;
      .coh-lockb-location {
        flex: none;
        height: 30px;
        > i {
          display: inline-block;
          width: 36px;
          height: 40px;
          vertical-align: top;
          margin: -5px 0 0 0;
        }
        > span {
          display: inline-block;
          font-size: 16px !important;
          line-height: 30px;
          color: #fff;
          text-shadow: 0 0 4px rgba(#fff, 0.8);
          vertical-align: top;
        }
      }
      .coh-lockb-word {
        flex: none;
        height: 30px;
        margin: 0 0 0 12px;
        > span {
          display: inline-block;
          font-size: 16px !important;
          line-height: 30px;
          color: #00eaff;
          text-shadow: 0 0 4px rgba(#00eaff, 0.8);
          vertical-align: middle;
        }
      }
    }
  }
  .coh-rock {
    flex: none;
    width: 300px;
    height: 100%;
    padding: 16px 16px;
    background: url("~@/assets/cockpit/subdivision/coh-rock.png") no-repeat
      center bottom/ 100% 84px;
    margin-left: -1px;
    position: relative;
    .coh-rock-base {
      width: 100%;
      .coh-rockb-sup {
        width: 100%;
        display: flex;
        flex-flow: row nowrap;
        justify-content: flex-end;
        align-items: center;
        > span {
          flex: none;
          font-size: 16px !important;
          line-height: 30px;
          color: #00eaff;
          text-shadow: 0 0 4px rgba(#00eaff, 0.8);
          font-weight: 400;
          margin: 0 16px 0 0;
        }
        > p {
          flex: none;
          font-size: 16px !important;
          line-height: 30px;
          color: #00eaff;
          text-shadow: 0 0 4px rgba(#00eaff, 0.8);
          font-weight: 400;
          margin: 0 16px 0 0;
        }
        > h6 {
          flex: none;
          font-size: 20px !important;
          line-height: 30px;
          color: #fff;
          text-shadow: 0 0 4px rgba(#fff, 0.8);
          font-weight: 400;
        }
      }
      .coh-rockb-sub {
        width: 100%;
        height: 20px;
        margin: 2px 0 0 0;
        display: flex;
        > i {
          display: inline-block;
          width: 31px;
          height: 30px;
          vertical-align: top;
          margin: -6px 0 0 0;
        }
        > span {
          display: inline-block;
          background: url("~@/assets/cockpit/coh-rockb-sub-block.png") no-repeat
            center center/ 100% 100%;
          font-size: 12px !important;
          line-height: 20px;
          color: #fff;
          text-shadow: 0 0 4px rgba(#fff, 0.8);
          vertical-align: top;
        }
      }
    }
  }
  .coh-line {
    flex: auto;
    height: 100%;
    background: url("~@/assets/cockpit/subdivision/coh-line.png") no-repeat
      center bottom/ 100% 49px;
  }
  .coh-rine {
    flex: auto;
    height: 100%;
    background: url("~@/assets/cockpit/subdivision/coh-rine.png") no-repeat
      center bottom/ 100% 49px;
  }
  .coh-base {
    flex: none;
    width: 920px;
    height: 100%;
    position: relative;
    z-index: 10;
    .coh-bab-top {
      width: 1280px;
      height: 21px;
      background: url("~@/assets/cockpit/coh-bab-top.png") no-repeat 0/ 100% 100%;
      position: absolute;
      top: 0;
      left: 50%;
      transform: translate(-50%, 0);
      z-index: 10;
    }
    .coh-bab-yl {
      width: 302px;
      height: 49px;
      background: url("~@/assets/cockpit/subdivision/coh-bab-yl.png") no-repeat
        0/ 100% 100%;
      position: absolute;
      left: 0;
      bottom: 0;
      z-index: 10;
    }
    .coh-bab-yr {
      width: 302px;
      height: 49px;
      background: url("~@/assets/cockpit/subdivision/coh-bab-yr.png") no-repeat
        0/ 100% 100%;
      position: absolute;
      right: 0;
      bottom: 0;
      z-index: 10;
    }
    .coh-bap-title {
      width: 604px;
      height: 104px;
      background: url("~@/assets/cockpit/coh-bap-title.png") no-repeat 0/ 100% 100%;
      position: absolute;
      top: -12px;
      left: 50%;
      transform: translate(-50%, 0);
      z-index: 20;
      .coh-bap-title-name {
        width: 395px;
        height: 35px;
        background: url("~@/assets/cockpit/subdivision/administration/page-title.png")
          no-repeat 0/ 100% 100%;
        position: absolute;
        top: 48%;
        left: 50%;
        transform: translate(-50%, -50%);
      }
    }
    .coh-bap-btn {
      width: 198px;
      height: 51px;
      background: url("~@/assets/cockpit/coh-bap-btn.png") no-repeat center -12px/
        210px 87px;
      position: absolute;
      bottom: -22px;
      cursor: pointer;
      z-index: 30;
      &:hover {
        background-image: url("~@/assets/cockpit/coh-bap-btn-on.png");
      }
      &::before {
        content: "";
        display: block;
        width: 116px;
        height: 86px;
        position: absolute;
        background: url("~@/assets/cockpit/coh-bap-btn-left.png") no-repeat 0/ 100%
          100%;
        top: -18px;
        left: -21px;
        animation: btnLineLeft ease 2s infinite;
      }
      &::after {
        content: "";
        display: block;
        width: 116px;
        height: 86px;
        position: absolute;
        background: url("~@/assets/cockpit/coh-bap-btn-right.png") no-repeat 0/ 100%
          100%;
        top: -18px;
        right: -21px;
        animation: btnLineRight ease 2s infinite;
      }
      > img {
        display: inline-block;
        width: 86px;
        height: 30px;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -60%);
      }
    }
    .coh-bap-btn1 {
      left: 30px;
    }
    .coh-bap-btn2 {
      right: 30px;
    }
  }
}
@keyframes btnLineLeft {
  0% {
    transform: translate(0, 0);
  }
  50% {
    transform: translate(-5px, 0);
  }
  100% {
    transform: translate(0, 0);
  }
}
@keyframes btnLineRight {
  0% {
    transform: translate(0, 0);
  }
  50% {
    transform: translate(5px, 0);
  }
  100% {
    transform: translate(0, 0);
  }
}
//呼吸灯
.coh-lock-breathe {
  display: flex;
  flex-flow: row nowrap;
  position: absolute;
  top: 6px;
  right: -60px;
  > i {
    flex: none;
    width: 32px;
    height: 26px;
    background: url("~@/assets/cockpit/coh-lock-breathe.png") no-repeat 0/ 100% 100%;
    margin: 0 -11px;
    &:nth-child(1) {
      animation: lockBreathe1 linear 2s infinite;
    }
    &:nth-child(2) {
      animation: lockBreathe2 linear 2s infinite;
    }
    &:nth-child(3) {
      animation: lockBreathe3 linear 2s infinite;
    }
    &:nth-child(4) {
      animation: lockBreathe4 linear 2s infinite;
    }
    &:nth-child(5) {
      animation: lockBreathe5 linear 2s infinite;
    }
    &:nth-child(6) {
      animation: lockBreathe6 linear 2s infinite;
    }
  }
}
.coh-rock-breathe {
  display: flex;
  flex-flow: row nowrap;
  position: absolute;
  top: 6px;
  left: -60px;
  > i {
    flex: none;
    width: 32px;
    height: 26px;
    background: url("~@/assets/cockpit/coh-rock-breathe.png") no-repeat 0/ 100% 100%;
    margin: 0 -11px;
    &:nth-child(6) {
      animation: lockBreathe1 linear 2s infinite;
    }
    &:nth-child(5) {
      animation: lockBreathe2 linear 2s infinite;
    }
    &:nth-child(4) {
      animation: lockBreathe3 linear 2s infinite;
    }
    &:nth-child(3) {
      animation: lockBreathe4 linear 2s infinite;
    }
    &:nth-child(2) {
      animation: lockBreathe5 linear 2s infinite;
    }
    &:nth-child(1) {
      animation: lockBreathe6 linear 2s infinite;
    }
  }
}
@keyframes lockBreathe1 {
  0% {
    opacity: 0.25;
  }
  10% {
    opacity: 0.4;
  }
  20% {
    opacity: 0.55;
  }
  30% {
    opacity: 0.7;
  }
  40% {
    opacity: 0.85;
  }
  50% {
    opacity: 1;
  }
  60% {
    opacity: 0.85;
  }
  70% {
    opacity: 0.7;
  }
  80% {
    opacity: 0.55;
  }
  90% {
    opacity: 0.4;
  }
  100% {
    opacity: 0.25;
  }
}
@keyframes lockBreathe2 {
  0% {
    opacity: 0.4;
  }
  10% {
    opacity: 0.55;
  }
  20% {
    opacity: 0.7;
  }
  30% {
    opacity: 0.85;
  }
  40% {
    opacity: 1;
  }
  50% {
    opacity: 0.85;
  }
  60% {
    opacity: 0.7;
  }
  70% {
    opacity: 0.55;
  }
  80% {
    opacity: 0.4;
  }
  90% {
    opacity: 0.25;
  }
  100% {
    opacity: 0.4;
  }
}
@keyframes lockBreathe3 {
  0% {
    opacity: 0.55;
  }
  10% {
    opacity: 0.7;
  }
  20% {
    opacity: 0.85;
  }
  30% {
    opacity: 1;
  }
  40% {
    opacity: 0.85;
  }
  50% {
    opacity: 0.7;
  }
  60% {
    opacity: 0.55;
  }
  70% {
    opacity: 0.4;
  }
  80% {
    opacity: 0.25;
  }
  90% {
    opacity: 0.25;
  }
  100% {
    opacity: 0.55;
  }
}
@keyframes lockBreathe4 {
  0% {
    opacity: 0.7;
  }
  10% {
    opacity: 0.85;
  }
  20% {
    opacity: 1;
  }
  30% {
    opacity: 0.85;
  }
  40% {
    opacity: 0.7;
  }
  50% {
    opacity: 0.55;
  }
  60% {
    opacity: 0.4;
  }
  70% {
    opacity: 0.25;
  }
  80% {
    opacity: 0.25;
  }
  90% {
    opacity: 0.25;
  }
  100% {
    opacity: 0.7;
  }
}
@keyframes lockBreathe5 {
  0% {
    opacity: 0.85;
  }
  10% {
    opacity: 1;
  }
  20% {
    opacity: 0.85;
  }
  30% {
    opacity: 0.7;
  }
  40% {
    opacity: 0.55;
  }
  50% {
    opacity: 0.4;
  }
  60% {
    opacity: 0.25;
  }
  70% {
    opacity: 0.25;
  }
  80% {
    opacity: 0.25;
  }
  90% {
    opacity: 0.25;
  }
  100% {
    opacity: 0.85;
  }
}
@keyframes lockBreathe6 {
  0% {
    opacity: 1;
  }
  10% {
    opacity: 0.85;
  }
  20% {
    opacity: 0.7;
  }
  30% {
    opacity: 0.55;
  }
  40% {
    opacity: 0.4;
  }
  50% {
    opacity: 0.25;
  }
  60% {
    opacity: 0.25;
  }
  70% {
    opacity: 0.25;
  }
  80% {
    opacity: 0.25;
  }
  90% {
    opacity: 0.25;
  }
  100% {
    opacity: 1;
  }
}
.co-back {
  position: absolute;
  left: 7px;
  bottom: -26px;
  width: 200px;
  cursor: pointer;
}
.co-bg-lx {
  position: absolute;
  left: 227px;
  bottom: -50px;
  width: 212.51px;
  height: 107px;
  background: transparent;
  opacity: 0.7;
  img {
    width: 100%;
  }
}
.co-right-menu {
  position: absolute;
  right: 7px;
  bottom: -8px;
  display: flex;
  img {
    cursor: pointer;
    width: 200px;
    height: 52px;
    display: block;
  }
  .cur-style {
    margin-top: 0px;
    height: 48px !important;
  }
}
</style>
