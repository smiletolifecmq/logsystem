<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <blockquote>
          <h1>项目介绍</h1>
          <li>
            这是一个专门用于编写、管理和导出用户工作日志的系统。我们旨在为用户提供一个高效、便捷、易于使用的平台，帮助他们轻松地记录和管理每天的工作日志。同时，我们还为用户提供了灵活的导出选项，使他们可以方便地导出并分享自己的工作日志。我们不断优化系统的性能和用户体验，以确保用户在使用过程中能够得到最好的体验。
          </li>
          <h1>福清分公司综合信息管理系统使用说明</h1>
          <h3>模块划分</h3>
          <img style="width: 95%" :src="moduleImg" class="sidebar-logo" />
          <div>从上图中可知，项目主要分为三个模块</div>
          <div>1. 个人中心模块</div>
          <div>2. 首页模块</div>
          <div>3. 用户日志模块</div>
          <h3>个人中心模块</h3>
          <div>点击个人中心模块出现以下界面</div>
          <img :src="personalIconImg" class="sidebar-logo" />
          <div>再次点击个人中心出现以下界面</div>
          <img style="width: 95%" :src="personalEditImg" class="sidebar-logo" />
          <div>在这里用户可以修改自己的个人信息与密码。</div>
          <h3>首页模块</h3>
          <div>包含项目介绍与使用说明</div>
          <img style="width: 95%" :src="headImg" class="sidebar-logo" />
          <h3>用户日志模块</h3>
          <img style="width: 95%" :src="userLogImg" class="sidebar-logo" />
          <div style="text-indent: 2em">
            图中1、2、3分别为：部门查询、用户名称查询以及时间范围查询，这三者查询条件在
            查询数据时为且的关系。由于权限管理的限制，图中1的部分只有公司领导才能查看。点击图中4部分对日志进行新增，出现以下界面：
          </div>
          <img style="width: 60%" :src="addUserLogImg" class="sidebar-logo" />
          <div style="text-indent: 2em">
            可以点击新增或者红色部分的❌，对当前需要创建的日志条数进行新增或者删除，点击确认即可创建日志。点击图中的5部分可以对日志进行导出，如果选择了图中1、2、3中的条件，则日志会根据条件进行导出，查询条件有以下这几个组合：
          </div>
          <li>图中1、2、3都为空，则显示所有数据</li>
          <li>
            图中选择1，2、3为空，则根据部门进行导出（只有公司领导有这个权限）
          </li>
          <li>图中选择2，1、3为空，则根据用户名称进行导出</li>
          <li>图中选择3，1、2为空，则根据时间范围进行导出</li>
          <li>
            图中选择1、2，3为空，则根据部门里面存在的用户名称数据进行导出（只有公司领导有这个权限）
          </li>
          <li>
            图中选择1、3，2为空，则根据部门里面某个时间段的数据进行导出（只有公司领导有这个权限）
          </li>
          <li>图中选择2、3，1为空，则导出符合条件的用户某个时间段的数据</li>
          <li>图中1、2，3全部选择，则同时满足这三个条件的数据才进行导出</li>
          <div>
            点击图中的6部分可以对当前的日志进行修改。点击图中的7部分可以对当前的日志进行删除。
          </div>
          <div style="color: red; text-indent: 2em">
            注意：每个用户只能对自己的数据进行修改删除，且当天的日志信息只能当天修改删除，一旦错过就不能修改跟删除了，如2023年5月29号创建一条日志，则只能在2023年5月29号这天对这条日志进行修改删除，2023年5月29号之后就不能再进行修改删除了。
          </div>
        </blockquote>

        <hr />
      </el-col>
    </el-row>
    <el-divider />
  </div>
</template>

<script>
import moduleImgUrl from "@/assets/images/muduleImg.png";
import personalIconImgUrl from "@/assets/images/personalIconImg.png";
import personalEditImgUrl from "@/assets/images/personalEditImg.png";
import headImgUrl from "@/assets/images/headImg.png";
import userLogImgUrl from "@/assets/images/userLogImg.png";
import addUserLogImgUrl from "@/assets/images/addUserLogImg.png";

export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.5",
      moduleImg: moduleImgUrl,
      personalIconImg: personalIconImgUrl,
      personalEditImg: personalEditImgUrl,
      headImg: headImgUrl,
      userLogImg: userLogImgUrl,
      addUserLogImg: addUserLogImgUrl,
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
