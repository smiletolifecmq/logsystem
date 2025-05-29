<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>开票统计(年份)</span>
      </div>
      <div ref="chart" style="width: 100%; height: 500px"></div>
    </el-card>

    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>客户开票金额趋势图（近两年）</span>
        <!-- 多选下拉客户筛选框 -->
        <el-select
          v-model="selectedCustomers"
          multiple
          filterable
          collapse-tags
          clearable
          placeholder="筛选客户"
          @change="updateCustomerChart"
          style="margin-top: 10px; width: 400px"
        >
          <el-option
            v-for="customer in allCustomerList"
            :key="customer"
            :label="customer"
            :value="customer"
          />
        </el-select>
      </div>
      <div ref="chartCustomer" style="width: 100%; height: 500px"></div>
    </el-card>

    <!-- 到账统计（年份） -->
    <el-card class="box-card" style="margin-top: 20px">
      <div slot="header" class="clearfix">
        <span>到账统计(年份)</span>
      </div>
      <div ref="chartArrival" style="width: 100%; height: 500px"></div>
    </el-card>

    <!-- 客户到账金额趋势图 -->
    <el-card class="box-card" style="margin-top: 20px">
      <div slot="header" class="clearfix">
        <span>客户到账金额趋势图（近两年）</span>
        <el-select
          v-model="selectedArrivalCustomers"
          multiple
          filterable
          collapse-tags
          clearable
          placeholder="筛选客户"
          @change="updateArrivalCustomerChart"
          style="margin-top: 10px; width: 400px"
        >
          <el-option
            v-for="customer in allArrivalCustomerList"
            :key="customer"
            :label="customer"
            :value="customer"
          />
        </el-select>
      </div>
      <div ref="chartArrivalCustomer" style="width: 100%; height: 500px"></div>
    </el-card>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { listInvoicingAll } from "@/api/system/invoicing";
import { listArrivalAll } from "@/api/system/arrival";

export default {
  name: "Invoicing",
  data() {
    return {
      chartInstanceArrival: null,
      chartInstanceArrivalCustomer: null,
      allArrivalCustomerList: [],
      selectedArrivalCustomers: [],
      arrivalCustomerDataMap: {},

      arrivalList: [],
      invoicingList: [],
      chartInstance: null,
      chartInstanceCustomer: null,
      queryParams: {
        pageNum: 1,
        pageSize: 9999,
        kpFph: null,
        kpType: null,
        kpHtmc: null,
        kpHtbh: null,
        kpKhmc: null,
      },
      allCustomerList: [],
      selectedCustomers: [],
      customerDataMap: {}, // 所有客户数据原始表
    };
  },
  mounted() {
    this.chartInstance = echarts.init(this.$refs.chart);
    this.chartInstanceCustomer = echarts.init(this.$refs.chartCustomer);

    this.chartInstanceArrival = echarts.init(this.$refs.chartArrival);
    this.chartInstanceArrivalCustomer = echarts.init(
      this.$refs.chartArrivalCustomer
    );

    window.addEventListener("resize", () => {
      this.chartInstanceCustomer.resize();
      this.chartInstanceArrival.resize();
      this.chartInstanceArrivalCustomer.resize();
    });
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      listInvoicingAll(
        this.addDateRange(this.queryParams, this.dateRange)
      ).then((response) => {
        this.invoicingList = response.rows;

        // 图一：年度趋势图
        const chartData = this.processChartData();
        const option = this.getChartOption(chartData);
        this.chartInstance.setOption(option);

        // 图二：客户月度趋势图
        this.customerDataMap = this.processCustomerChartData();
        this.allCustomerList = Object.keys(this.customerDataMap);

        // 默认选择含关键字的客户
        const defaultKeywords = ["福建", "公司", "福清"];
        const selectedSet = new Set();

        defaultKeywords.forEach((keyword) => {
          let count = 0;
          for (const name of this.allCustomerList) {
            if (name.includes(keyword)) {
              selectedSet.add(name);
              count++;
            }
            if (count >= 2) break; // 每个关键词取 2 个
            if (selectedSet.size >= 6) break; // 总共最多取 6 个
          }
        });
        this.selectedCustomers = [...selectedSet];

        this.updateCustomerChart();
      });
      listArrivalAll(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.arrivalList = response.rows;

          // 到账图一：年度折线图
          const arrivalChartData = this.processArrivalChartData();
          const arrivalOption = this.getChartOption(arrivalChartData);
          this.chartInstanceArrival.setOption(arrivalOption);

          // 到账图二：客户趋势图
          this.arrivalCustomerDataMap = this.processArrivalCustomerChartData();
          this.allArrivalCustomerList = Object.keys(
            this.arrivalCustomerDataMap
          );

          const defaultKeywords = ["福建", "公司", "福清"];
          const selectedSet = new Set();

          defaultKeywords.forEach((keyword) => {
            let count = 0;
            for (const name of this.allArrivalCustomerList) {
              if (name.includes(keyword)) {
                selectedSet.add(name);
                count++;
              }
              if (count >= 2) break;
              if (selectedSet.size >= 6) break;
            }
          });
          this.selectedArrivalCustomers = [...selectedSet];

          this.updateArrivalCustomerChart();
        }
      );
    },

    // 图一：年度折线图
    processChartData() {
      const result = {};
      const currentYear = new Date().getFullYear();
      const recentYears = [currentYear - 1, currentYear];

      this.invoicingList.forEach((item) => {
        if (!item.kpKprq || !item.kpKpje) return;
        const date = new Date(item.kpKprq);
        const year = date.getFullYear();
        const month = date.getMonth(); // 0-11
        if (!recentYears.includes(year)) return;
        const amount = parseFloat(item.kpKpje);
        if (!result[year]) {
          result[year] = new Array(12).fill(0);
        }
        result[year][month] += amount;
      });

      return result;
    },

    getChartOption(dataMap) {
      const months = [
        "1月",
        "2月",
        "3月",
        "4月",
        "5月",
        "6月",
        "7月",
        "8月",
        "9月",
        "10月",
        "11月",
        "12月",
      ];
      const series = Object.keys(dataMap).map((year) => ({
        name: year,
        type: "line",
        data: dataMap[year],
      }));
      return {
        tooltip: { trigger: "axis" },
        legend: { data: Object.keys(dataMap) },
        xAxis: { type: "category", data: months },
        yAxis: { type: "value", name: "金额（元）" },
        series,
      };
    },

    // 图二：客户趋势图数据整理
    processCustomerChartData() {
      const result = {};
      const currentYear = new Date().getFullYear();
      const recentYears = [currentYear - 1, currentYear];

      this.invoicingList.forEach((item) => {
        if (!item.kpKprq || !item.kpKpje || !item.kpKhmc) return;
        const date = new Date(item.kpKprq);
        const year = date.getFullYear();
        const month = date.getMonth(); // 0-11
        if (!recentYears.includes(year)) return;

        const customer = item.kpKhmc;
        const amount = parseFloat(item.kpKpje);
        const key = `${month + 1}月-${year}`;

        if (!result[customer]) result[customer] = {};
        if (!result[customer][key]) result[customer][key] = 0;
        result[customer][key] += amount;
      });

      return result;
    },

    // 图：根据选择客户更新图表
    updateCustomerChart() {
      const months = [
        "1月",
        "2月",
        "3月",
        "4月",
        "5月",
        "6月",
        "7月",
        "8月",
        "9月",
        "10月",
        "11月",
        "12月",
      ];
      const currentYear = new Date().getFullYear();
      const recentYears = [currentYear - 1, currentYear];
      const xAxisData = [];

      months.forEach((month) => {
        recentYears.forEach((year) => {
          xAxisData.push(`${month}-${year}`);
        });
      });

      const series = this.selectedCustomers.map((customer) => {
        const data = xAxisData.map((key) => {
          return this.customerDataMap[customer]?.[key] || 0;
        });
        return {
          name: customer,
          type: "bar",
          stack: "total",
          emphasis: { focus: "series" },
          data,
        };
      });

      const option = {
        tooltip: {
          trigger: "axis",
          axisPointer: { type: "shadow" },
        },
        legend: {
          type: "scroll",
          orient: "horizontal",
          top: "bottom",
          data: this.selectedCustomers,
        },
        xAxis: {
          type: "category",
          data: xAxisData,
          axisLabel: { rotate: 45 },
        },
        yAxis: {
          type: "value",
          name: "金额（元）",
        },
        series,
      };

      this.chartInstanceCustomer.setOption(option, true);
    },

    getCustomerChartOption(dataMap) {
      const months = [
        "1月",
        "2月",
        "3月",
        "4月",
        "5月",
        "6月",
        "7月",
        "8月",
        "9月",
        "10月",
        "11月",
        "12月",
      ];

      const series = Object.keys(dataMap).map((customer) => ({
        name: customer,
        type: "bar",
        stack: "total",
        emphasis: { focus: "series" },
        data: dataMap[customer],
      }));

      return {
        tooltip: {
          trigger: "axis",
          axisPointer: { type: "shadow" },
        },
        legend: {
          type: "scroll",
          orient: "horizontal",
          top: "bottom",
          data: Object.keys(dataMap),
        },
        xAxis: {
          type: "category",
          data: months,
          axisLabel: { rotate: 30 },
        },
        yAxis: {
          type: "value",
          name: "金额（元）",
        },
        series,
      };
    },

    processArrivalChartData() {
      const result = {};
      const currentYear = new Date().getFullYear();
      const recentYears = [currentYear - 1, currentYear];

      this.arrivalList.forEach((item) => {
        if (!item.dzRq || !item.dzMoney) return;
        const date = new Date(item.dzRq);
        const year = date.getFullYear();
        const month = date.getMonth(); // 0-11
        if (!recentYears.includes(year)) return;
        const amount = parseFloat(item.dzMoney);
        if (!result[year]) {
          result[year] = new Array(12).fill(0);
        }
        result[year][month] += amount;
      });

      return result;
    },

    processArrivalCustomerChartData() {
      const result = {};
      const currentYear = new Date().getFullYear();
      const recentYears = [currentYear - 1, currentYear];

      this.arrivalList.forEach((item) => {
        if (!item.dzRq || !item.dzMoney || !item.dzKhmc) return;
        const date = new Date(item.dzRq);
        const year = date.getFullYear();
        const month = date.getMonth(); // 0-11
        if (!recentYears.includes(year)) return;

        const customer = item.dzKhmc;
        const amount = parseFloat(item.dzMoney);
        const key = `${month + 1}月-${year}`;

        if (!result[customer]) result[customer] = {};
        if (!result[customer][key]) result[customer][key] = 0;
        result[customer][key] += amount;
      });

      return result;
    },
    updateArrivalCustomerChart() {
      const months = [
        "1月",
        "2月",
        "3月",
        "4月",
        "5月",
        "6月",
        "7月",
        "8月",
        "9月",
        "10月",
        "11月",
        "12月",
      ];
      const currentYear = new Date().getFullYear();
      const recentYears = [currentYear - 1, currentYear];
      const xAxisData = [];

      months.forEach((month) => {
        recentYears.forEach((year) => {
          xAxisData.push(`${month}-${year}`);
        });
      });

      const series = this.selectedArrivalCustomers.map((customer) => {
        const data = xAxisData.map((key) => {
          return this.arrivalCustomerDataMap[customer]?.[key] || 0;
        });
        return {
          name: customer,
          type: "bar",
          stack: "total",
          emphasis: { focus: "series" },
          data,
        };
      });

      const option = {
        tooltip: {
          trigger: "axis",
          axisPointer: { type: "shadow" },
        },
        legend: {
          type: "scroll",
          orient: "horizontal",
          top: "bottom",
          data: this.selectedArrivalCustomers,
        },
        xAxis: {
          type: "category",
          data: xAxisData,
          axisLabel: { rotate: 45 },
        },
        yAxis: {
          type: "value",
          name: "金额（元）",
        },
        series,
      };

      this.chartInstanceArrivalCustomer.setOption(option, true);
    },

    // 假设已有
    addDateRange(params, dateRange) {
      return params; // 这里保持原样
    },
  },
};
</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style>
