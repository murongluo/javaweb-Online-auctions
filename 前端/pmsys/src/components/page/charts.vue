<template>
  <el-container>
    <el-header>拍卖数据展示</el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="12"><div id="activityChart" style="height: 400px;"></div></el-col>
        <el-col :span="12"><div id="revenueChart" style="height: 400px;"></div></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12"><div id="typeChart" style="height: 400px;"></div></el-col>
        <el-col :span="12"><div id="priceRelationChart" style="height: 400px;"></div></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12"><div id="conditionChart" style="height: 400px;"></div></el-col>
        <el-col :span="12"><div id="bidsChart" style="height: 400px;"></div></el-col>
      </el-row>
    </el-main>
  </el-container>
</template>
<script>
import * as echarts from 'echarts';  // Correct import for echarts
import 'element-ui/lib/theme-chalk/index.css';

export default {
  mounted() {
    this.initCharts();
  },
  methods: {
    initCharts() {
      // 拍卖活动按类别
      var activityChart = echarts.init(document.getElementById('activityChart'));
      activityChart.setOption({
        title: { text: '拍卖活动按类别' },
        tooltip: {},
        xAxis: {
          data: ["艺术品", "珠宝", "古董", "汽车", "电子产品"]
        },
        yAxis: {},
        series: [{
          name: '拍卖数量',
          type: 'bar',
          data: [120, 300, 230, 200, 270]
        }]
      });

      // 每月拍卖收入
      var revenueChart = echarts.init(document.getElementById('revenueChart'));
      revenueChart.setOption({
        title: { text: '每月拍卖收入' },
        tooltip: {},
        xAxis: {
          type: 'category',
          data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          name: '收入',
          type: 'line',
          data: [8000, 12000, 18000, 15000, 21000, 24000, 20000, 19000, 23000, 25000, 29000, 22000]
        }]
      });

      // 拍卖类型比例
      var typeChart = echarts.init(document.getElementById('typeChart'));
      typeChart.setOption({
        title: { text: '拍卖类型比例' },
        tooltip: {},
        series: [{
          name: '拍卖类型',
          type: 'pie',
          radius: '55%',
          data: [
            { value: 335, name: '线上' },
            { value: 310, name: '现场' },
            { value: 234, name: '保留价' },
            { value: 135, name: '无保留价' }
          ]
        }]
      });

      // 初始价格与最终价格关系
      var priceRelationChart = echarts.init(document.getElementById('priceRelationChart'));
      priceRelationChart.setOption({
        title: { text: '初始价格与最终价格关系' },
        tooltip: {},
        xAxis: { type: 'value', name: '初始价格' },
        yAxis: { type: 'value', name: '最终价格' },
        series: [{
          name: '价格关系',
          type: 'scatter',
          data: [
            [500, 1800], [800, 1500], [1200, 5000], [100, 600], [300, 1200]
          ]
        }]
      });

      // 拍卖品条件评级
      var conditionChart = echarts.init(document.getElementById('conditionChart'));
      conditionChart.setOption({
        title: { text: '拍卖品条件评级' },
        tooltip: {},
        radar: {
          indicator: [
            { name: '外观魅力', max: 5 },
            { name: '功能性', max: 5 },
            { name: '稀有性', max: 5 },
            { name: '需求量', max: 5 }
          ]
        },
        series: [{
          name: '物品条件',
          type: 'radar',
          data: [
            { value: [3, 2, 4, 3], name: '物品A' },
            { value: [4, 4, 2, 1], name: '物品B' }
          ]
        }]
      });

      // 按类别的竞标量
      var bidsChart = echarts.init(document.getElementById('bidsChart'));
      bidsChart.setOption({
        title: { text: '按类别的竞标量' },
        tooltip: {},
        angleAxis: {},
        radiusAxis: {
          type: 'category',
          data: ['艺术品', '珠宝', '古董', '汽车', '电子产品'],
          z: 10
        },
        polar: {},
        series: [{
          type: 'bar',
          data: [1, 2, 3, 4, 5],
          coordinateSystem: 'polar',
          name: '竞标量',
          stack: 'a'
        }]
      });
    }
  }
}
</script>


<style scoped>
.el-header {
  background-color: #b3c0d1;
  color: white;
  text-align: center;
  line-height: 60px;
}
</style>

