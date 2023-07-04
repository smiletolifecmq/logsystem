<template>
  <div class="app-container">
    <el-row>
      <el-col
        :span="8"
        style="width: 20%; !important"
        :offset="0"
        v-hasPermi="['system:log:list']"
      >
        <el-card :body-style="{ padding: '0px' }">
          <img :src="logInstructions" class="image" />
          <div style="padding: 14px">
            <span>日志使用手册</span>
            <div class="bottom clearfix">
              <el-button type="text" class="button" @click="handleDetail(1)"
                >查看详情</el-button
              >
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col
        :span="8"
        style="width: 20%; !important"
        :offset="1"
        v-hasPermi="['system:review:list']"
      >
        <el-card :body-style="{ padding: '0px' }">
          <img :src="employmentInstructions" class="image" />
          <div style="padding: 14px">
            <span>雇工使用手册</span>
            <div class="bottom clearfix">
              <el-button type="text" class="button" @click="handleDetail(2)"
                >查看详情</el-button
              >
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-divider />
  </div>
</template>

<style>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>

<script>
import logInstructions from "@/assets/images/logInstructions.png";
import employmentInstructions from "@/assets/images/employmentInstructions.png";

export default {
  data() {
    return {
      logInstructions: logInstructions,
      employmentInstructions: employmentInstructions,
    };
  },
  methods: {
    handleDetail(num) {
      switch (num) {
        case 1:
          this.$router.push("/manual/work_log/info");
          break;
        case 2:
          this.$router.push("/manual/hired_worker/info");
          break;
      }
    },
  },
};
</script>
