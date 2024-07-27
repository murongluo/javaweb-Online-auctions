<template>
    <div>
      <div class="crumbs">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item> <i class="el-icon-goods"></i> 支付订单 </el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div>
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item label="支付ID">
                <el-input v-model="formInline.paymentId" placeholder="请输入商品名称"></el-input>
            </el-form-item>
          <el-form-item label="支付方式">
            <el-select v-model="formInline.paymentMethod" placeholder="请选择">
              <el-option label="未选择" value=""></el-option>
              <el-option label="银行卡" value="银行卡"></el-option>
              <el-option label="支付宝" value="支付宝"></el-option>
              <el-option label="微信" value="微信"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="支付状态">
            <el-select v-model="formInline.paymentStatus" placeholder="请选择">
              <el-option label="不区分" value=""></el-option>
              <el-option label="待支付" value="待支付"></el-option>
              <el-option label="支付成功" value="支付成功"></el-option>
              <el-option label="支付失败" value="支付失败"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div>
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column fixed type="index" label="序号" width="60"> </el-table-column>
          <el-table-column prop="paymentId" label="支付ID" width="120"> </el-table-column>
          <el-table-column prop="item.seller.username" label="卖家用户名" width="120"> </el-table-column>
          <el-table-column prop="item.name" label="商品名称"> </el-table-column>
          <el-table-column prop="amount" label="金额" width="100"> </el-table-column>
          <el-table-column prop="paymentMethod" label="支付方式" width="100"> </el-table-column>
          <el-table-column prop="paymentStatus" label="支付状态" width="100"> </el-table-column>
          <el-table-column prop="paymentDate" label="支付日期" width="180"> </el-table-column>
          <el-table-column prop="payer.username" label="付款人用户名" width="120"> </el-table-column>
          
    
          
        </el-table>
      </div>
      <div class="block">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 30, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalItems"
        >
        </el-pagination>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    created() {
      this.getPaymentsData();
    },
    methods: {
      handleSizeChange(val) {
        this.pageSize = val;
        this.getPaymentsData();
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.getPaymentsData();
      },
      onSubmit() {
        this.currentPage = 1;
        this.getPaymentsData();
      },
      async getPaymentsData() {
        const params = {
          page: this.currentPage,
          pageSize: this.pageSize,
          paymentId:this.formInline.paymentId,
          paymentMethod: this.formInline.paymentMethod,
          paymentStatus: this.formInline.paymentStatus
        };
        try {
          const response = await axios.get('http://localhost:8080/getpay', { params });
          this.tableData = response.data.data.rows;
          this.totalItems = response.data.data.total;
          console.info(this.tableData);
        } catch (error) {
          console.error('Error fetching data:', error);
        }
      },
      handleClick(row) {
        console.log('Handling click for:', row);
      }
    },
    data() {
      return {
        formInline: {
          paymentId:'',
          paymentMethod: '',
          paymentStatus: ''
        },
        tableData: [],
        currentPage: 1,
        pageSize: 10,
        totalItems: 0
      };
    }
  };
  </script>
  
