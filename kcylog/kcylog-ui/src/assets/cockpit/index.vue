<template>
  <div class="cockpit-bg">
    <div class="co-head">
      <Head ref="headRef"></Head>
    </div>
    <div class="co-body">
      <div class="co-body-bg"><img src="@/assets/cockpit/co-body-bg.png" alt="" /></div>
      <div class="co-body-sup">
        <ul class="co-bp-left">
          <li class="co-bpl-item1">
            <BplItem1 ref="bplItem1Ref" :infoData="infoData" @unitChange="unitChange"></BplItem1>
          </li>
          <li class="co-bpl-item2">
            <BplItem2 ref="bplItem2Ref" :infoData="infoData"></BplItem2>
          </li>
          <li class="co-bpl-item3">
            <BplItem3 ref="bplItem3Ref" :infoData="infoData"></BplItem3>
          </li>
        </ul>
        <div class="co-bp-center">
          <BpCenter ref="bpCenterRef"></BpCenter>
        </div>
        <ul class="co-bp-right">
          <li class="co-bpr-item1">
            <BprItem1 ref="bprItem1Ref" :infoData="infoData"></BprItem1>
          </li>
          <li class="co-bpr-item2">
            <BprItem2 ref="bprItem2Ref" :infoData="infoData"></BprItem2>
          </li>
          <li class="co-bpr-item3">
            <BprItem3 ref="bprItem3Ref" :infoData="infoData"></BprItem3>
          </li>
        </ul>
      </div>
      <ul class="co-body-sub">
        <li class="co-bb-item">
          <BbItem1 ref="bbItem1Ref" :infoData="infoData"></BbItem1>
        </li>
        <li class="co-bb-item">
          <BbItem2 ref="bbItem2Ref" :infoData="infoData"></BbItem2>
        </li>
        <li class="co-bb-item">
          <BbItem3 ref="bbItem3Ref" :infoData="infoData"></BbItem3>
        </li>
        <li class="co-bb-item">
          <BbItem4 ref="bbItem4Ref" :infoData="infoData"></BbItem4>
        </li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import { CockpitApi } from '@/api/cockpit'
import Head from './indexComponents/head.vue'
import BplItem1 from './indexComponents/bplItem1.vue'
import BplItem2 from './indexComponents/bplItem2.vue'
import BplItem3 from './indexComponents/bplItem3.vue'
import BpCenter from './indexComponents/bpCenter.vue'
import BprItem1 from './indexComponents/bprItem1.vue'
import BprItem2 from './indexComponents/bprItem2.vue'
import BprItem3 from './indexComponents/bprItem3.vue'
import BbItem1 from './indexComponents/bbItem1.vue'
import BbItem2 from './indexComponents/bbItem2.vue'
import BbItem3 from './indexComponents/bbItem3.vue'
import BbItem4 from './indexComponents/bbItem4.vue'

@Component({
  name: 'Cockpit',
  components: {
    Head,
    BplItem1,
    BplItem2,
    BplItem3,
    BpCenter,
    BprItem1,
    BprItem2,
    BprItem3,
    BbItem1,
    BbItem2,
    BbItem3,
    BbItem4
  }
})
export default class Cockpit extends Vue {
  private params = {
    unit: '1',
    year: '2020'
  }
  private infoData: any = {}
  mounted() {
    this.getCockpitData()
  }
  getCockpitData() {
    CockpitApi.CockpitDriver(this.params).then((res: any) => {
      this.infoData = res.data || {}
    })
  }
  unitChange(val: any) {
    this.params.unit = val
    this.getCockpitData()
  }
}
</script>
<style lang="scss" scoped>
@import '~@/styles/cockpit.scss';
.cockpit-bg {
  width: 100%;
  height: 100%;
  background: #002160;
  overflow-y: auto;
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
    height: 750px;
    padding: 12px 12px 8px;
    position: relative;
    &:after {
      content: '';
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
      float: left;
      width: 28%;
      height: 100%;
      display: flex;
      flex-flow: column nowrap;
      justify-content: space-between;
      align-items: flex-start;
      z-index: 80;
      .co-bpl-item1 {
        flex: none;
        width: 100%;
        height: 32.4%;
      }
      .co-bpl-item2 {
        flex: none;
        width: 80%;
        height: 32.4%;
      }
      .co-bpl-item3 {
        flex: none;
        width: 105%;
        height: 32.4%;
      }
    }
    .co-bp-right {
      float: right;
      width: 28%;
      height: 100%;
      display: flex;
      flex-flow: column nowrap;
      justify-content: space-between;
      align-items: flex-end;
      z-index: 80;
      .co-bpr-item1 {
        flex: none;
        width: 100%;
        height: 32.4%;
      }
      .co-bpr-item2 {
        flex: none;
        width: 80%;
        height: 32.4%;
      }
      .co-bpr-item3 {
        flex: none;
        width: 105%;
        height: 32.4%;
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
</style>
