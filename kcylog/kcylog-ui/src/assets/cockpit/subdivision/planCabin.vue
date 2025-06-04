<template>
  <div class="page-index">
    <div class="cockpit-bg">
      <div class="co-head">
        <Head ref="headRef" @switchPage="switchPageFun"></Head>
      </div>
      <Plan v-if="curPage === 1"></Plan>
      <Linkage v-if="curPage === 2"></Linkage>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import Head from './components/planningOverview/head.vue'
import Plan from './components/planningOverview/index.vue'
import Linkage from './components/fiveLinkage/index.vue'

@Component({
  name: 'planCabin',
  components: {
    Head,
    Plan,
    Linkage
  }
})
export default class planCabin extends Vue {
  private curPage = 1
  switchPageFun(type: number) {
    this.curPage = type
  }
  mounted() {}
}
</script>
<style lang="scss" scoped>
@import '~@/styles/cockpit.scss';
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
</style>
