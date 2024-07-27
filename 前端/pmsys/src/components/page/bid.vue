<template>
    <div>
      <div class="crumbs">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item>
            <i class="el-icon-goods"></i> 拍品管理
          </el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div>
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item label="竞拍ID">
            <el-input v-model="formInline.bidId" placeholder="竞拍ID"></el-input>
          </el-form-item>
          <el-form-item label="竞拍状态">
            <el-select v-model="formInline.type" placeholder="竞拍状态">
              <el-option label="不区分" value=""></el-option>
              <el-option label="成功" value="成功"></el-option>
              <el-option label="失败" value="失败"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
        </el-form>
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column fixed type="index" label="序号" width="60"></el-table-column>
          <el-table-column prop="bidId" label="竞价ID" width="80"></el-table-column>
          <el-table-column prop="bidAmount" label="竞拍价" width="100"></el-table-column>
          <el-table-column prop="bidTime" label="竞价时间" width="160"></el-table-column>
          <el-table-column prop="item.name" label="商品名" width="120"></el-table-column>
          <el-table-column prop="item.seller.username" label="物主" width="100"></el-table-column>
          <el-table-column prop="bidder.username" label="竞拍人" width="100"></el-table-column>
          <el-table-column prop="status" label="状态" width="100"></el-table-column>
          <el-table-column label="图片" width="150">
            <template slot-scope="scope">
              <img :src="scope.row.item.image" style="width: 100px; height: auto" />
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        formInline: {
          bidId: '',
          type: ''
        },
        tableData: [],
        currentPage: 1,
        pageSize: 10,
        total: 0
      };
    },
    methods: {
      handleSizeChange(newSize) {
        this.pageSize = newSize;
        this.getdate(this.currentPage, this.pageSize);
      },
      handleCurrentChange(newPage) {
        this.currentPage = newPage;
        this.getdate(this.currentPage, this.pageSize);
      },
      onSubmit() {
        this.getdate(this.currentPage, this.pageSize);
      },
      async getdate(page, pageSize) {
        try {
          const response = await axios.get('http://localhost:8080/getbids', {
            params: {
              bidId: this.formInline.bidId,
              status: this.formInline.type,
              page: page,
              pageSize: pageSize
            }
          });
          console.info(response)
          this.tableData = response.data.data.rows;
          this.total = response.data.data.total;
        } catch (error) {
          console.error('Error fetching data:', error);
          this.$message.error('获取数据失败，请重试');
        }
      }
    }
  };
  </script>
  
  <style scoped>
  /* Add any specific styles for your component here */
  </style>
  
