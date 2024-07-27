<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-goods"></i> 拍品管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div>
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="竞价Id">
          <el-input v-model="formInline.bidId" placeholder="竞价Id"></el-input>
        </el-form-item>
        <el-form-item label="拍品名">
          <el-input v-model="formInline.name" placeholder="拍品名"></el-input>
        </el-form-item>
        <el-form-item label="物主">
          <el-input v-model="formInline.sellername" placeholder="物主"></el-input>
        </el-form-item>
        <el-form-item label="竞价人">
          <el-input v-model="formInline.bidname" placeholder="竞价人"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="formInline.status" placeholder="状态">
            <el-option label="不区分" value=""></el-option>
            <el-option label="竞价成功" value="竞价成功"></el-option>
            <el-option label="竞价失败" value="竞价失败"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData" border show-header style="width: 100%">
        <el-table-column fixed type="index" label="序号" width="60"></el-table-column>
        <el-table-column prop="bidId" label="拍卖ID" width="100"></el-table-column>
        <el-table-column prop="bidAmount" label="竞价" width="100"></el-table-column>
        <el-table-column prop="bidTime" label="竞价时间" width="180"></el-table-column>
        <el-table-column prop="item.seller.username" label="物主"></el-table-column>
        <el-table-column prop="bidder.username" label="竞价人"></el-table-column>
        <el-table-column prop="item.name" label="商品名"></el-table-column>
        <el-table-column prop="status" label="状态"></el-table-column>
        <el-table-column label="图片" width="150">
            <template slot-scope="scope">
              <img :src="scope.row.item.image" style="width: 100px; height: auto" />
            </template>
          </el-table-column>
          <el-table-column label="操作" width="100">
        <template v-slot:default="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">详情</el-button>
        </template>
      </el-table-column>
        </el-table>
        <el-dialog :visible.sync="detailsDialogVisible" title="竞拍详情" width="70%">
  <div v-if="selectedItem">
    <p>竞拍ID: {{ selectedItem.bidId }}</p>
    <p>商品名称: {{ selectedItem.item.name }}</p>
    <p>商品描述: {{ selectedItem.item.description }}</p>
    <p>商品类别: {{ selectedItem.item.category }}</p>
    <p>起拍价: {{ selectedItem.item.startingPrice }}</p>
    <p>上市日期: {{ selectedItem.item.listingDate }}</p>
    <p>竞拍金额: {{ selectedItem.bidAmount }}</p>
    <p>竞拍时间: {{ selectedItem.bidTime }}</p>
    <p>竞拍状态: {{ selectedItem.status }}</p>
    <h3>物主信息:</h3>
    <p>姓名: {{ selectedItem.item.seller.username }}</p>
    <p>id: {{ selectedItem.item.seller.userId }}</p>
    <h3>竞拍人信息:</h3>
    <p>姓名: {{ selectedItem.bidder.username }}</p>
    <p>id: {{ selectedItem.bidder.userId }}</p>

    <img :src="selectedItem.item.image" alt="商品图片" style="width: 100%; height: auto">
  </div>
</el-dialog>

   
      <el-pagination
      @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
        :total="totalItems">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      formInline: {
        name: '',
        status: '',
        bidId:'',
        sellername:'',
        bidname:''
      },
      currentPage: 1,
      pageSize: 10,
      totalItems: 0,
      tableData: [],
      selectedItem: null,
      detailsDialogVisible: false
    };
  },
  created() {
    this.getData();
  },
  methods: {
    handleClick(item) {
      this.selectedItem = item;
      this.detailsDialogVisible = true;
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getData();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getData();
    },
    onSubmit() {
      this.getData();
    },
    async getData() {
      const params = {
      page: this.currentPage,
      pageSize: this.pageSize,
      bidId: this.formInline.bidId,
      status: this.formInline.status,
      name: this.formInline.name,
      sellername: this.formInline.sellername,
      bidname: this.formInline.bidname

      };
      try {
        const response = await axios.get('http://localhost:8080/getbids', { params });
        this.tableData = response.data.data.rows;
        this.totalItems = response.data.data.total;
      } catch (error) {
        console.error('Error fetching data:', error);
        this.$message.error('获取数据失败，请重试');
      }
    }
  }
};
</script>

<style scoped>
/* 你可以在这里添加一些针对本组件的特定样式 */
</style>

