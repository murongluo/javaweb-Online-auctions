<template>
    <div class="menupage">
        <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
        >
            <p class="logo-title"><i class="el-icon-user"></i>槐序拍卖平台</p>
            <el-menu-item index="1" style="margin-left: 300px">平台首页</el-menu-item>
            <el-menu-item index="2">平台简介</el-menu-item>
            <el-menu-item index="3">拍品展示</el-menu-item>
            <el-menu-item index="4">拍卖行</el-menu-item>
            <el-submenu index="5">
                <template slot="title">我的</template>
                <el-menu-item index="5-1">我的信息</el-menu-item>
                <el-menu-item index="5-2">前往后台</el-menu-item>
                <el-menu-item index="5-3">退出登录</el-menu-item>
            </el-submenu>
            <!-- 添加登录/注册按钮 -->
            <el-menu-item index="6">登录/注册</el-menu-item>
        </el-menu>
        <div>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:252px;">
                    <div class="user-info">
                        <img :src=userData.image class="user-avator" alt />
                        <div class="user-info-cont">
                            <div class="user-info-name">用户名:{{userData.username}}</div>
                            <div>用户身份:{{userData.userType}}</div>
                        </div>
                    </div>
                    <div class="user-info-list">
                        上次登录时间：
                        <span>{{userData.lastLoginDate  }}</span>
                    </div>
                    <div class="user-info-list">
                        修改信息：
                        <el-button @click="showEditDialog">修改</el-button>
                    </div>
                    <el-dialog title="修改信息" :visible.sync="editDialogVisible" append-to-body>
  <el-form :model="editForm" >
    <el-form-item label="用户名">
      <el-input v-model="editForm.username"></el-input>
    </el-form-item>
    <el-form-item label="密码">
      <el-input v-model="editForm.password" type="password"></el-input>
    </el-form-item>
    <el-form-item label="电子邮件">
      <el-input v-model="editForm.email"></el-input>
    </el-form-item>
    <el-form-item label="地址">
      <el-input v-model="editForm.address"></el-input>
    </el-form-item>
    <el-form-item label="图片URL">
                        <el-upload
                            class="upload-demo"
                            action="http://localhost:8080/upload"
                            name="image"
                            :on-success="handleUploadSuccess"
                            :on-error="handleUploadError"
                            list-type="picture-card">
                            <i class="el-icon-plus"></i>
                        </el-upload>
                    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="editDialogVisible = false">取消</el-button>
    <el-button type="primary" @click="submitEditForm()">确认修改</el-button>
  </div>
</el-dialog>

                </el-card>
                <el-card shadow="hover" style="height:252px;">
                    <div slot="header" class="clearfix">
                        <span>语言详情</span>
                    </div>Vue
                    <el-progress :percentage="71.3" color="#42b983"></el-progress>JavaScript
                    <el-progress :percentage="24.1" color="#f1e05a"></el-progress>CSS
                    <el-progress :percentage="13.7"></el-progress>HTML
                    <el-progress :percentage="5.9" color="#f56c6c"></el-progress>
                </el-card>
            </el-col>
            <el-col :span="16">
                <el-row :gutter="20" class="mgb20">
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-1">
                                <i class="el-icon-lx-goods grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">我的订单</div>
                                    <el-button @click="showOrderDetails">订单详情</el-button>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-dialog title="订单详情" :visible.sync="orderDialogVisible" width="80%" append-to-body>
    <el-table :data="orderDetails" style="width: 100%">
        <el-table-column prop="paymentId" label="支付编号" width="100"></el-table-column>
        <el-table-column label="商品名称" width="180">
            <template #default="scope">
                {{ scope.row.item.name }}
            </template>
        </el-table-column>
        <el-table-column label="卖家" width="150">
            <template #default="scope">
                {{ scope.row.item.seller.username }}
            </template>
        </el-table-column>
        <el-table-column prop="amount" label="金额" width="120"></el-table-column>
        <el-table-column prop="paymentMethod" label="支付方式" width="130"></el-table-column>
        <el-table-column prop="paymentStatus" label="支付状态" width="130">
            <template #default="scope">
                <div>{{ scope.row.paymentStatus }}</div>
                <el-button 
                    v-if="scope.row.paymentStatus === '待支付'" 
                    size="mini" 
                    type="primary" 
                    @click="handlePayment(scope.row)">支付</el-button>
                <el-button 
                    v-else-if="scope.row.paymentStatus === '支付失败'" 
                    size="mini" 
                    type="danger" 
                    @click="handlePayment(scope.row)">重新支付</el-button>
            </template>
        </el-table-column>
        <el-table-column prop="paymentDate" label="支付日期" width="180"></el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
        <el-button @click="orderDialogVisible = false">关闭</el-button>
    </div>
</el-dialog>
<el-dialog title="支付选项" :visible.sync="paymentDialogVisible" append-to-body>
    <el-form :model="currentPayment">
        <el-form-item label="支付编号">
            <span>{{ currentPayment.paymentId }}</span>
        </el-form-item>
        <el-form-item label="支付金额">
            <span>{{ currentPayment.amount }}</span>
        </el-form-item>
        <el-form-item label="选择支付方式">
            <el-radio-group v-model="currentPayment.paymentMethod">
                <el-radio label="微信">微信</el-radio>
                <el-radio label="支付宝">支付宝</el-radio>
                <el-radio label="银行卡">银行卡</el-radio>
            </el-radio-group>
        </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="paymentDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="processPayment()">确认支付</el-button>
    </div>
</el-dialog>




                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-2">
                                <i class="el-icon-lx-notice grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">我的竞价</div>
                                    <el-button @click="showBidDetails">竞价详情</el-button>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-dialog title="竞价详情" :visible.sync="bidDialogVisible" append-to-body>
    <el-table :data="bidDetails.rows" stripe style="width: 100%">
        <el-table-column prop="bidId" label="竞价ID" width="100"></el-table-column>
        <el-table-column prop="item.name" label="商品名称" width="200"></el-table-column>
        <el-table-column prop="item.startingPrice" label="起始价格" width="120"></el-table-column>
        <el-table-column prop="bidAmount" label="出价金额" width="120"></el-table-column>
        <el-table-column prop="bidTime" label="出价时间" width="180"></el-table-column>
        <el-table-column prop="status" label="竞价状态" width="150"></el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
        <el-button @click="bidDialogVisible = false">关闭</el-button>
    </div>
</el-dialog>
<el-dialog title="商品详情" :visible.sync="productDialogVisible" width="80%" append-to-body>
    <el-table :data="productDetails" style="width: 100%">
        <el-table-column prop="itemId" label="商品ID" width="100"></el-table-column>
        <el-table-column prop="name" label="商品名称" width="200"></el-table-column>
        <el-table-column prop="description" label="描述" width="300"></el-table-column>
        <el-table-column prop="category" label="分类" width="150"></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
            <template #default="scope">
                {{ scope.row.status }}
                <el-button v-if="scope.row.status === '失败'" size="mini" type="text" @click="showEditProductDialog(scope.row)">修改</el-button>
            </template>
        </el-table-column>
        <el-table-column prop="listingDate" label="上架日期" width="180"></el-table-column>
        <el-table-column>
            <template #default="scope">
                <img :src="scope.row.image" alt="商品图片" style="width: 100px; height: auto;">
            </template>
            <template #header>
                <span>商品图片</span>
            </template>
        </el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
        <el-button @click="productDialogVisible = false">关闭</el-button>
    </div>
</el-dialog>
<el-dialog title="修改商品详情" :visible.sync="editProductDialogVisible" width="50%" append-to-body>
    <el-form :model="editProductForm">
        <el-form-item label="商品名称">
            <el-input v-model="editProductForm.name"></el-input>
        </el-form-item>
        <el-form-item label="描述">
            <el-input type="textarea" v-model="editProductForm.description"></el-input>
        </el-form-item>
        <el-form-item label="分类">
            <el-input v-model="editProductForm.category"></el-input>
        </el-form-item>

        <el-form-item label="图片URL">
            <el-upload
                class="upload-demo"
                action="http://localhost:8080/upload"
                name="image"
                :on-success="loadSuccess1"
                :on-error="loadError1"
                list-type="picture-card">
                <i class="el-icon-plus"></i>
                <img v-if="editProductForm.image" :src="editProductForm.image" style="width: 100px; height: auto">
            </el-upload>
        </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="editProductDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEditProductForm()">提交修改</el-button>
    </div>
</el-dialog>




                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-3">
                                <i class="el-icon-lx-goods grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">我的商品</div>
                                    <el-button @click="showProductDetails">商品详情</el-button>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>

                <el-card shadow="hover" style="height:403px;">
                    <div slot="header" class="clearfix">
                        <span>待办事项</span>
                        <el-button style="float: right; padding: 3px 0" type="text">添加</el-button>
                    </div>
                    <el-table :show-header="false" :data="todoList" style="width:100%;">
                        <el-table-column width="40">
                            <template slot-scope="scope">
                                <el-checkbox v-model="scope.row.status"></el-checkbox>
                            </template>
                        </el-table-column>
                        <el-table-column>
                            <template slot-scope="scope">
                                <div
                                    class="todo-item"
                                    :class="{'todo-item-del': scope.row.status}"
                                >{{scope.row.title}}</div>
                            </template>
                        </el-table-column>
                        <el-table-column width="60">
                            <template>
                                <i class="el-icon-edit"></i>
                                <i class="el-icon-delete"></i>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card shadow="hover">
                    <schart ref="bar" class="schart" canvasId="bar" :options="options"></schart>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card shadow="hover">
                    <schart ref="line" class="schart" canvasId="line" :options="options2"></schart>
                </el-card>
            </el-col>
        </el-row>
    </div>
    </div>
</template>
<script>
 import axios from 'axios';
import Schart from 'vue-schart';
import bus from '../common/bus';
export default {
    name: 'dashboard',
    data() {
        return { 
            editProductDialogVisible: false,
            editProductForm: {
                itemId: '',
                name: '',
                description: '',
                category: '',
                image: '',
                status:'待审核'
            },
            productDialogVisible: false,
            productDetails: [],
        bidDialogVisible: false,
        bidDetails: { total: 0, rows: [] },
        paymentDialogVisible: false,
        currentPayment: {
            paymentId: '',
            amount: 0,
            paymentMethod: '微信'
        },
        orderDialogVisible: false,
        orderDetails: [],
        editDialogVisible: false,
        editForm: {
            username: '',
            password: '',
            email: '',
            address: '',
            image: ''
        },
            userData: null,
            name: localStorage.getItem('ms_username'),
            todoList: [
                {
                    title: '今天要修复100个bug',
                    status: false
                },
                {
                    title: '今天要修复100个bug',
                    status: false
                },
                {
                    title: '今天要写100行代码加几个bug吧',
                    status: false
                },
                {
                    title: '今天要修复100个bug',
                    status: false
                },
                {
                    title: '今天要修复100个bug',
                    status: true
                },
                {
                    title: '今天要写100行代码加几个bug吧',
                    status: true
                }
            ],
            data: [
                {
                    name: '2018/09/04',
                    value: 1083
                },
                {
                    name: '2018/09/05',
                    value: 941
                },
                {
                    name: '2018/09/06',
                    value: 1139
                },
                {
                    name: '2018/09/07',
                    value: 816
                },
                {
                    name: '2018/09/08',
                    value: 327
                },
                {
                    name: '2018/09/09',
                    value: 228
                },
                {
                    name: '2018/09/10',
                    value: 1065
                }
            ],
            options: {
                type: 'bar',
                title: {
                    text: '最近一周各品类销售图'
                },
                xRorate: 25,
                labels: ['周一', '周二', '周三', '周四', '周五'],
                datasets: [
                    {
                        label: '家电',
                        data: [234, 278, 270, 190, 230]
                    },
                    {
                        label: '百货',
                        data: [164, 178, 190, 135, 160]
                    },
                    {
                        label: '食品',
                        data: [144, 198, 150, 235, 120]
                    }
                ]
            },
            options2: {
                type: 'line',
                title: {
                    text: '最近几个月各品类销售趋势图'
                },
                labels: ['6月', '7月', '8月', '9月', '10月'],
                datasets: [
                    {
                        label: '家电',
                        data: [234, 278, 270, 190, 230]
                    },
                    {
                        label: '百货',
                        data: [164, 178, 150, 135, 160]
                    },
                    {
                        label: '食品',
                        data: [74, 118, 200, 235, 90]
                    }
                ]
            }
        };
    },
    components: {
        Schart
    },
    computed: {
        role() {
            return this.name === 'admin' ? '超级管理员' : '普通用户';
        }
    },
    mounted() {
    this.fetchUserData();
  },
    methods: {
        submitEditProductForm() {
            this.editProductForm.status='待审核'
            axios.post('http://localhost:8080/upitem', this.editProductForm)
                .then(response => {
                    if (response.data.code === 1) {
                        this.$message.success('商品更新成功');
                        this.editProductDialogVisible = false;
                        this.fetchProductDetails();  // Refresh the product details
                    } else {
                        this.$message.error('更新失败: ' + response.data.msg);
                    }
                })
                
        },
        loadSuccess1(response) {
            this.editProductForm.image = response; // Assume response has url property
            console.info(response)
            this.$message.success('图片上传成功');
        },
       loadError1() {
            this.$message.error('图片上传失败');
        },
        showEditProductDialog(product) {
            this.editProductForm = { ...product };  // Spread operator to copy product details
            this.editProductDialogVisible = true;
        },
        fetchProductDetails() {
            const sellerId = this.userData.userId;  // Using userId as sellerId
            axios.get(`http://localhost:8080/getitem`, { params: { sellerId: sellerId } })
            .then(response => {
                if (response.data.code === 1 && response.data.data.total > 0) {
                    this.productDetails = response.data.data.rows;  // Store the product details
                    this.$message.success('商品详情加载成功');
                } else {
                    this.$message.error('没有找到商品详情');
                    this.productDetails = [];  // Reset if no details found
                }
            })
            .catch(error => {
                console.error('请求商品详情出错:', error);
                this.$message.error('无法加载商品详情，请检查网络连接');
            });
        },
        showProductDetails() {
            this.productDialogVisible = true;
            this.fetchProductDetails();  // Add a method to fetch product details from the server
        },

        showBidDetails() {
        this.bidDialogVisible = true;
        this.fetchBidDetails();  // Method to fetch bid details from the server
    },
    fetchBidDetails() {
        const bidderId = this.userData.userId;  // Using userId as bidderId
        axios.get(`http://localhost:8080/getbids`, { params: { bidderId: bidderId } })
        .then(response => {
            if (response.data.code === 1 && response.data.data.total > 0) {
                this.bidDetails = response.data.data;  // Store the bid details
                this.$message.success('加载竞价详情成功');
            } else {
                this.$message.error('没有找到竞价详情');
                this.bidDetails = { total: 0, rows: [] };  // Reset if no details found
            }
        })
        
    },
        processPayment() {
        this.$confirm('确定要执行支付操作吗?', '确认支付', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            this.currentPayment.paymentStatus = '支付成功';
            this.updatePaymentStatus();
        }).catch(() => {
            this.currentPayment.paymentStatus = '支付失败';
            this.updatePaymentStatus();
        });
    },
    updatePaymentStatus() {
        // Assume paymentId and paymentStatus are set
        console.log('Updating payment status:', this.currentPayment);
        axios.post('http://localhost:8080/uppay', {
            paymentId: this.currentPayment.paymentId,
            paymentStatus: this.currentPayment.paymentStatus,
            paymentMethod: this.currentPayment.paymentMethod
        })
        .then(response => {
            if (response.data.code === 1) {
                this.$message.success('支付状态更新成功');
                this.fetchOrderDetails(); // Refresh the order details
                this.paymentDialogVisible = false;
            } else {
                this.$message.error('支付状态更新失败: ' + response.data.msg);
            }
        })
        .catch(error => {
            console.error('Error updating payment status:', error);
            this.$message.error('支付状态更新出错');
        });
    },
        handlePayment(paymentDetail) {
        this.currentPayment.paymentId = paymentDetail.paymentId;
        this.currentPayment.amount = paymentDetail.amount;
        this.paymentDialogVisible = true;
    },
        showOrderDetails() {
        this.orderDialogVisible = true;
        this.fetchOrderDetails();  // 假设有一个方法来从服务器获取订单详情
    },
    fetchOrderDetails() {
        const payerId = this.userData.userId;  // Ensure userData is populated correctly
        axios.get(`http://localhost:8080/getpay`, { params: { payerId: payerId } })
        .then(response => {
            if (response.data.code === 1) {
                this.orderDetails = response.data.data.rows;
                this.$message.success('订单数据加载成功');
            } else {
                this.$message.error('订单数据加载失败: ' + response.data.msg);
            }
        })
        .catch(error => {
            console.error('请求订单数据出错:', error);
            this.$message.error('无法加载订单数据，请检查网络连接');
        });
    },
        showEditDialog() {
        this.editForm = {
            userId:this.userData.userId,
            username: this.userData.username,
            password: '',  // 密码应当被保密，不自动填充
            email: this.userData.email,
            address: this.userData.address,
            image: this.userData.image
        };
        this.editDialogVisible = true;
    },
    handleUploadSuccess(response) {
            this.editForm.image = response; // Assume response has url property
            console.info(response)
            this.$message.success('图片上传成功');
        },
        handleUploadError() {
            this.$message.error('图片上传失败');
        },
    submitEditForm() {
        axios.post('http://localhost:8080/upuser', this.editForm, {
           
        })
        .then(response => {
            if (response.data.code === 1) {
                this.$message.success('信息更新成功');
                this.editDialogVisible = false;
                this.fetchUserData();  // 重新加载用户数据
            } else {
                this.$message.error('更新失败: ' + response.data.msg);
            }
        })

    },
        fetchUserData() {
    const user = JSON.parse(localStorage.getItem('user'));
    console.info(user)
    if (user && user.token && user.userId) {
      axios.get('http://localhost:8080/getusers', {
        headers: {
            'token': user.token
        },
        params: {
          userId: user.userId
        }
      })
      .then(response => {
      
       this.userData=response.data.data.rows[0];
       console.info(this.userData)
      })
      .catch(error => {
        this.handleError('Error fetching user data', error);
      });
    } else {
      this.$message.error('User data not found in local storage');
    }
  },
  handleError(msg, error = null) {
    console.error('Error:', error || msg);
    this.$message.error(msg);
  },
        changeDate() {
            const now = new Date().getTime();
            this.data.forEach((item, index) => {
                const date = new Date(now - (6 - index) * 86400000);
                item.name = `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`;
            });
        }
    },
    handleSelect(key, keyPath) {
            console.log('Selected:', key, keyPath);
            switch(key) {
                case '1':
                    this.$router.push('/home'); 
                    break;
                case '2':
                    this.$router.push('/about');  
                    break;
                case '3':
                    this.$router.push('/items');  
                    break;
                case '4':
                    this.$router.push('/auction');  
                    break;
                case '5-1':
                    this.$router.push('/my'); 
                    break;
                case '5-2':
                    this.$router.push('/admin/login'); 
                    break;
                case '5-3':
                    this.$router.push('/');  
                    break;
                case '6':
                    this.$router.push('/syslogin');  
                    break;
                default: this.$router.push('/*');
                    
            }
        }
};
</script>

<style scoped>
.el-row {
    margin-bottom: 20px;
}

.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}

.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}

.grid-num {
    font-size: 30px;
    font-weight: bold;
}

.grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
}

.grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
}

.user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
}

.user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
}

.user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
}

.user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
}

.user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
}

.user-info-list span {
    margin-left: 70px;
}

.mgb20 {
    margin-bottom: 20px;
}

.todo-item {
    font-size: 14px;
}

.todo-item-del {
    text-decoration: line-through;
    color: #999;
}

.schart {
    width: 100%;
    height: 300px;
}
</style>


<style>
/* 菜单栏样式 */
.menupage {
    position: relative;
    width: 100%;
    top: 0;
    z-index: 100;
    height: 100%; /* 确保高度足够 */
}

.menupage .el-menu.el-menu--horizontal {
    border-bottom: none;
    height: 100px; /* 保持原有高度 */
    line-height: 100px;
    background-color: #545c64;
}

.menupage .el-menu.el-menu--horizontal > .el-menu-item,
.menupage .el-menu--horizontal > .el-submenu .el-submenu__title {
    height: 100px; /* 保持原有高度 */
    line-height: 100px;
    padding: 0px 50px;
    font-size: 16px;
    letter-spacing: 4px;
}

.menupage .el-menu--horizontal > .el-menu-item.is-active,
.menupage .el-menu--horizontal > .el-submenu.is-active .el-submenu__title {
    border-bottom-width: 4px;
}

.menupage .logo-title .el-icon-user {
    margin-right: 5px;
}

.logo-title {
    position: absolute;
    left: 100px;
    top: 0px;
    color: #fff;
    font-size: 26px;
    cursor: pointer;
}



</style>


