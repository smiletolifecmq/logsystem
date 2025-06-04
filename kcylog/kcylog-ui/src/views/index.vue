<template>
  <div class="co-body">
    <div class="co-head" style="background-color: #042c63">
      <Head></Head>
    </div>
    <div class="co-body-sup" style="background-color: #042c63">
      <ul style="list-style-type: none; flex: 2">
        <li class="co-mid-item1">
          <Item2 ref="Item2Ref"></Item2>
        </li>
        <li class="co-mid-item2">
          <Item3 ref="Item3Ref"></Item3>
        </li>
        <li class="co-mid-item2">
          <Item6 ref="Item6Ref"></Item6>
        </li>
      </ul>
      <ul style="list-style-type: none; flex: 2; margin-top: -1px">
        <li class="co-right-item1">
          <Item4 ref="Item4Ref"></Item4>
        </li>
        <li class="co-right-item2" style="margin-top: 2px">
          <Item1 ref="Item1Ref"></Item1>
        </li>
        <!-- <li class="co-right-item2">
          <Item6 ref="Item6Ref"></Item6>
        </li> -->
      </ul>
    </div>
  </div>
</template>

<script>
import Head from "./system/administration/head.vue";
import Item1 from "./system/administration/item1.vue";
import Item2 from "./system/administration/item2.vue";
import Item3 from "./system/administration/item3.vue";
import Item4 from "./system/administration/item4.vue";
import Item5 from "./system/administration/item5.vue";
import Item6 from "./system/administration/item6.vue";
export default {
  name: "Index",
  components: {
    Head,
    Item1,
    Item2,
    Item3,
    Item4,
    Item5,
    Item6,
  },
  data() {
    return {};
  },
  created() {},
  methods: {},
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
  height: 62px;
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
    width: calc(100% - 0px);
    display: flex;
    padding: 12px;
    margin: 0;
    gap: 12px; /* 防止间距 */
    ul {
      // border: solid 1px green;
      list-style: none;
      margin: 0;
      padding: 0;
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
      flex: 1;
      flex-shrink: 0;

      .co-bpl-item1 {
        height: 860px;
      }
    }
    .co-bp-mid {
      flex: 2;
    }
    .co-bp-right {
      flex: 2;
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

.co-body-sup1 {
  width: calc(100% - 0px);
  display: flex;
  padding: 12px;
  margin: 0;
  gap: 12px; /* 防止间距 */
  ul {
    // border: solid 1px green;
    list-style: none;
    margin: 0;
    padding: 0;
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
    flex: 1;
    flex-shrink: 0;

    .co-bpl-item1 {
      height: 860px;
    }
  }
  .co-bp-mid {
    flex: 2;
  }
  .co-bp-right {
    flex: 2;
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
</style>
