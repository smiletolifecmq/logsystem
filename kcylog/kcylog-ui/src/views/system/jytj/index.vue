<template>
  <div class="app-container">
    <el-button type="primary" @click="addBidder">新增投标人</el-button>

    <el-table :data="bidders" border style="margin-top: 20px">
      <el-table-column label="序号" type="index" width="60" />

      <el-table-column label="投标人单位名称">
        <template slot-scope="scope">
          <el-input v-model="scope.row.name" placeholder="请输入单位名称" />
        </template>
      </el-table-column>

      <el-table-column label="评标价格(元)">
        <template slot-scope="scope">
          <el-input v-model.number="scope.row.price" placeholder="请输入价格" />
        </template>
      </el-table-column>

      <el-table-column label="价格得分">
        <template slot-scope="scope">
          <span>{{ scope.row.score }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-button
            type="danger"
            size="mini"
            @click="removeBidder(scope.$index)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-button type="success" style="margin-top: 20px" @click="calculateScores">
      计算价格得分
    </el-button>
  </div>
</template>

<script>
export default {
  name: "pbjs",
  data() {
    return {
      bidders: [],
      w1: 0.8,
      w2: 1.15,
      n1: 1,
      n2: 0.5,
      c: 0,
    };
  },
  methods: {
    addBidder() {
      this.bidders.push({ name: "", price: null, score: null });
    },
    removeBidder(index) {
      this.bidders.splice(index, 1);
    },
    calculateScores() {
      const validPrices = this.bidders
        .map((b) => b.price)
        .filter((p) => typeof p === "number" && p > 0);

      if (validPrices.length === 0) {
        this.$message.warning("请先填写有效的评标价格！");
        return;
      }

      const A1 =
        validPrices.reduce((sum, p) => sum + p, 0) / validPrices.length;

      const lower = A1 * this.w1;
      const upper = A1 * this.w2;

      const pricesInRange = validPrices.filter((p) => p >= lower && p <= upper);

      let basePrice;
      if (pricesInRange.length > 0) {
        const A2 =
          pricesInRange.reduce((sum, p) => sum + p, 0) / pricesInRange.length;
        basePrice = A2 * (1 + this.c);
      } else {
        basePrice = A1 * (1 + this.c);
      }

      this.bidders.forEach((bidder) => {
        const P = bidder.price;
        if (typeof P !== "number" || P <= 0) {
          bidder.score = null;
          return;
        }

        const n = P >= basePrice ? this.n1 : this.n2;
        const diff = Math.abs(P - basePrice);
        let score = 100 - 100 * n * (diff / basePrice);

        if (score < 0) score = 0;
        bidder.score = score.toFixed(2);
      });
    },
  },
};
</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style>
