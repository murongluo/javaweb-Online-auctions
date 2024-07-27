<template>
    <div class="payment-form">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="订单号">
          <el-input v-model="form.orderId" placeholder="请输入订单号"></el-input>
        </el-form-item>
        <el-form-item label="拍卖品ID">
          <el-input v-model="form.auctionItemId" placeholder="请输入拍卖品ID"></el-input>
        </el-form-item>
        <el-form-item label="支付方式">
          <el-select v-model="form.paymentMethod" placeholder="请选择">
            <el-option label="微信" value="wechat"></el-option>
            <el-option label="支付宝" value="alipay"></el-option>
            <el-option label="银行卡" value="bankCard"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="金额">
          <el-input v-model.number="form.amount" placeholder="请输入支付金额"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="confirmPayment">支付</el-button>
        </el-form-item>
      </el-form>
      <el-dialog
        title="支付确认"
        :visible.sync="dialogVisible"
        width="30%"
        @close="handleDialogClose">
        <span>请确认您的支付信息，点击确认进行支付。</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelPayment">取消</el-button>
          <el-button type="primary" @click="submitPayment">确认</el-button>
        </span>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import { Message } from 'element-ui';
  
  export default {
    data() {
      return {
        form: {
          orderId: '1',
          auctionItemId: '1',
          paymentMethod: '',
          amount: null
        },
        dialogVisible: false
      };
    },
    methods: {
      confirmPayment() {
        this.dialogVisible = true;
      },
      submitPayment() {
        console.log('Payment details:', this.form);
        this.dialogVisible = false;
        Message({
          message: '支付成功！订单已更新',
          type: 'success',
          duration: 5000
        });
      },
      cancelPayment() {
        this.dialogVisible = false;
        Message({
          message: '支付已取消',
          type: 'info',
          duration: 5000
        });
      },
      handleDialogClose() {
        this.dialogVisible = false;
      }
    }
  };
  </script>
  
  <style>
  .payment-form {
    width: 400px;
    margin: 50px auto;
  }
  .dialog-footer {
    text-align: right;
  }
  </style>
  
