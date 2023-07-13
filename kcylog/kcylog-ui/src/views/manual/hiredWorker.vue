<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <blockquote>
          <h3>负责人职责</h3>
          <img style="width: 95%" :src="principal" class="sidebar-logo" />
          <li>拥有雇工审核管理模块：我的审核单</li>
          <li>
            操作：新增、修改、删除、发起审核、查看审核单流程详情、填写最终雇工信息
          </li>
          <h3>部门长职责</h3>
          <img style="width: 95%" :src="upcomingReview" class="sidebar-logo" />
          <img style="width: 95%" :src="doneReview" class="sidebar-logo" />
          <img style="width: 95%" :src="passedReview" class="sidebar-logo" />
          <img style="width: 95%" :src="settlement" class="sidebar-logo" />
          <li>拥有雇工审核管理模块</li>
          <div>待办审核单：查看需要审核的审核单</div>
          <div>已办理审核单：查看当前审核人已经审核的审核单</div>
          <div>待结算审核单：可选取多个审核单，并生成结算单</div>
          <div>结算办结：可查看生成的结算单，并根据雇工或者部门进行导出</div>
          <li>操作</li>
          <div>待办审核单模块：对审核单进行通过或者不通过操作</div>
          <div>已办理审核单模块：展示当前审核人已经办理好了的审核单</div>
          <div>
            待结算审核单模块：展示已经通过审核流程未结算的审核单，可选取多个审核单并生成结算单
          </div>
          <div>结算办结模块：对结算单进行修改、删除、导出</div>
          <h3>按雇工导出</h3>
          <img style="width: 95%" :src="laborExport" class="sidebar-logo" />
          <h3>按部门导出</h3>
          <img style="width: 95%" :src="deptExport" class="sidebar-logo" />
          <h3>分管职责</h3>
          <li>拥有部门长的所有职责，在此基础上还拥有以下权限</li>
          <div>已办理审核单模块：可以对审核单开启编辑功能</div>
          <div>对待办审核单进行通过时，该审核单就能进行最终雇工的填写</div>
          <h3>总经理职责</h3>
          <li>拥有分管的所有职责，在此基础上还拥有以下权限</li>
          <div>
            待结算审核单：只有总经理审核通过的审核单才能在这里进行展示、并生成结算单
          </div>
        </blockquote>

        <hr />
      </el-col>
    </el-row>
    <el-divider />
  </div>
</template>

<script>
import principal from "@/assets/images/principal.png";
import upcomingReview from "@/assets/images/upcomingReview.png";
import doneReview from "@/assets/images/doneReview.png";
import passedReview from "@/assets/images/passedReview.png";
import settlement from "@/assets/images/settlement.png";
import laborExport from "@/assets/images/laborExport.png";
import deptExport from "@/assets/images/deptExport.png";

export default {
  name: "Index",
  data() {
    return {
      principal: principal,
      upcomingReview: upcomingReview,
      doneReview: doneReview,
      passedReview: passedReview,
      settlement: settlement,
      laborExport: laborExport,
      deptExport: deptExport,
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>
