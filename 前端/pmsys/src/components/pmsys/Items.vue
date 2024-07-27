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
            <el-menu-item index="1" style="margin-left: 300px;">平台首页</el-menu-item>
            <el-menu-item index="2">平台简介</el-menu-item>
            <el-menu-item index="3">拍品展示</el-menu-item>
            <el-menu-item index="4">拍卖行</el-menu-item>
            <el-submenu index="5">
                <template slot="title">我的</template>
                <el-menu-item index="5-1">我的信息</el-menu-item>
                <el-menu-item index="5-2">前往后台</el-menu-item>
                <el-menu-item index="5-3">退出登录</el-menu-item>
            </el-submenu>
            <el-menu-item index="6">登录/注册</el-menu-item>
        </el-menu>
        <div>
            <div class="container">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="名称">
                        <el-input v-model="formInline.name" placeholder="名称"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">查询</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onAdd">我想出售</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-card class="box-card">
                        <div class="content">
                            <el-row :gutter="20">
                                <el-col :span="8" v-for="(product, index) in data" :key="index">
                                    <el-card class="box-card">
                                        <img :src="product.image" class="image" alt="商品图片" />
                                        <div class="content">
                                            <span class="name">{{ product.name }}</span>
                                            <br />
                                            <span class="description">类型：{{ product.category }}</span>
                                            <br />
                                            <span class="price">起拍价：{{ product.startingPrice }}</span>
                                            <br />
                                        </div>
                                        <div class="bottom clearfix">
                                            <el-button type="text" class="button" @click="viewProduct(product)">查看</el-button>
                                            <el-button type="text" class="button" @click="addToCart(product)">竞价</el-button>
                                            
                                            <el-dialog title="商品详情" :visible.sync="dialogFormVisible" append-to-body>
                                                <div>
                                                    <p><b>商品名称:</b> {{ detail.name }}</p>
                                                    <p><b>描述:</b> {{ detail.description }}</p>
                                                    <p><b>类别:</b> {{ detail.category }}</p>
                                                    <p><b>起拍价:</b> {{ detail.startingPrice }}</p>
                                                    <p><b>上架日期:</b> {{ detail.listingDate }}</p>
                                                    <p><b>状态:</b> {{ detail.status }}</p>
                                                    <p v-if="detail.seller"><b>卖家用户名:</b> {{ detail.seller.username }}</p>
                                                </div>
                                                <div slot="footer" class="dialog-footer">
                                                    <el-button @click="dialogFormVisible = false">关闭</el-button>
                                                </div>
                                            </el-dialog>
                                        </div>
                                    </el-card>
                                </el-col>
                            </el-row>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
            <el-row :gutter="20" style="margin-top: 20px">
                <el-col :span="24">
                    <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-sizes="[3,9, 18, 45, 90]"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="totalProducts">
                    </el-pagination>
                </el-col>
            </el-row>
            <el-dialog title="竞价" :visible.sync="bidDialogVisible" width="30%" append-to-body>
                <div>
                    <p><b>当前最高竞价:</b> {{ currentBid }} 元</p>
                    <p><b>当前物主:</b> {{ currentBidderDisplayName }}</p>
                   
                    
                </div>
              
            </el-dialog>
            <el-dialog title="添加拍品" :visible.sync="addDialogVisible" width="30%" append-to-body>
                <el-form :model="addForm">
                    <el-form-item label="拍品名称">
                        <el-input v-model="addForm.name" placeholder="请输入拍品名称"></el-input>
                    </el-form-item>
                    <el-form-item label="分类">
                        <el-input v-model="addForm.category" placeholder="请输入分类"></el-input>
                    </el-form-item>
                    <el-form-item label="描述">
                        <el-input v-model="addForm.description" type="textarea"></el-input>
                    </el-form-item>
                    
                    <el-form-item label="起拍价">
                        <el-input v-model="addForm.startingPrice" placeholder="请输入起拍价"></el-input>
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
                <span slot="footer" class="dialog-footer">
                    <el-button @click="addDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="submitAddForm">添加</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import Axios from 'axios';

export default {
    data() {
        return {
            addDialogVisible: false,
            addForm: {
                name: '',
                description: '',
                startingPrice: '',
                image: '',
                category:''
            },
            itemId: '',
            detail: {},
            dialogFormVisible: false,
            bidDialogVisible: false,
            currentBid: 0,
            currentBidderId: null,
            currentBidderDisplayName: '',
            bidAmount: '',
            formInline: {
                name: '',
            },
            data: [],
            totalProducts: 0,
            pageSize: 3,
            currentPage: 1,
            activeIndex: '3'
        };
    },
    created() {
    this.getData();
},
    methods: {
        onAdd() {
            this.addDialogVisible = true;
        },
        async submitAddForm() {
            console.log('提交添加拍品:', this.addForm);
            const userData = JSON.parse(localStorage.getItem('user'));
            Axios.post('http://localhost:8080/additem',{
                sellerId:userData.userId,
                name:this.addForm.name,
                startingPrice:this.addForm.startingPrice,
                description:this.addForm.description,
                image:this.addForm.image,
                category:this.addForm.category
            }).then(response => {
        if (response.data.code === 1) {
            this.$message.success('拍品添加成功');
            // 可以在这里清空表单或执行其他逻辑
            this.addForm = { name: '', startingPrice: '', description: '', image: '', category: '' };
            this.addDialogVisible = false;
        } else {
            this.$message.error(`拍品添加失败: ${response.data.msg}`);
        }
    })
   
            // 在这里添加向后端提交的逻辑
            // 例如使用 Axios 发送 POST 请求
            this.addDialogVisible = false; // 成功后关闭对话框
        },
        handleUploadSuccess(response, file) {
        this.addForm.image = response; // 更新模型中的 image URL
        this.$message.success('图片上传成功');
    },
    handleUploadError(error) {
        console.error('图片上传失败:', error);
        this.$message.error('图片上传失败');
    },
        onSubmit() {
            this.getData();
        },
        async getData() {
            try {
                const response = await Axios.get('http://localhost:8080/getitem', {
                    params: {
                        name: this.formInline.name,
                        status: "通过",
                        page: this.currentPage,
                        pageSize: this.pageSize
                    }
                });
                this.data = response.data.data.rows;
                this.totalProducts = response.data.data.total;
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        },
        viewProduct(product) {
            this.detail = product;
            this.dialogFormVisible = true;
        },
        async addToCart(product) {
            this.detail = product;
            this.itemId = product.itemId
            const userData = JSON.parse(localStorage.getItem('user'));
            try {
                const response = await Axios.get(`http://localhost:8080/getbidmaxprice/${product.itemId}`);
                if (response.data.code === 1 && response.data.msg === "sucess") {
                    this.currentBid = response.data.data.bidAmount; // 最高竞价金额
                    this.currentBidderId = response.data.data.bidderId; // 最高竞价者ID

                    if (userData && userData.userId === this.currentBidderId) {
                        this.currentBidderDisplayName = "您";
                    } else {
                        this.currentBidderDisplayName = "****";
                    }
                } else {
                    this.currentBid = product.startingPrice; // 如果没有竞价，则显示起拍价
                    this.currentBidderDisplayName = '无'; // 没有竞价者
                }
            } catch (error) {
                console.error('Error fetching bid data:', error);
                this.currentBid = product.startingPrice; // 出错则显示起拍价
                this.currentBidderDisplayName = '无'; // 出错则没有竞价者
            }
            this.bidDialogVisible = true;
        },
        submitBid() {
            const userData = JSON.parse(localStorage.getItem('user'));
            console.log(`提交竞价: ${this.bidAmount} for product ${this.detail.name}`);
            console.log(this.detail.itemId);
            console.log(userData.userId);
            console.log(this.bidAmount);
            console.log(this.currentBid);
            if (this.bidAmount > this.currentBid) {
                Axios.post(`http://localhost:8080/addbids`, {
                    itemId: this.detail.itemId,
                    bidAmount: this.bidAmount,
                    bidderId: userData.userId
                })
                .then(response => {
                    if (response.data.code === 1) {
                        this.$message({
                            type: 'success',
                            message: '竞价成功!'
                        });
                        this.bidDialogVisible = false;
                        this.bidAmount = ''; // Reset bid amount after successful submission
                        this.currentBid = this.bidAmount; // Update the current bid to the new bid amount
                        this.currentBidderDisplayName = "您"; // Set the current bidder display name to "You"
                    } else {
                        this.$message({
                            type: 'error',
                            message: `竞价失败: ${response.data.msg}`
                        });
                        alert(`竞价失败: ${response.data.msg}`);
                    }
                });
            }
            // 这里可以添加提交到后端的逻辑
            this.bidDialogVisible = false;
            this.bidAmount = ''; // 重置竞价金额
        },
        handlePageChange(newPage) {
            this.currentPage = newPage;
            this.getData();
        },
        handleSizeChange(newSize) {
            this.pageSize = newSize;
            this.getData();
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
    }
};
</script>


<style scoped>
/* Styles here */
.image {
    width: 100%;
    object-fit: cover;
}
</style>




<style scoped>
.image {
    width: 150px;
    height: 150px;
    object-fit: cover; /* 确保图片被缩放以适应容器，而不会被裁剪 */
}

.gallery-carousel {
    text-align: center; /* 居中显示 */
    margin: 20px; /* 调整外边距以突出显示 */
}

.el-carousel {
    height: 300px; /* 根据实际情况调整高度 */
}

.el-carousel-item img {
    width: 100%;
    height: 100%;
    object-fit: cover; /* 图片会被缩放以适应容器，而不会被裁剪 */
}

.gallery-title {
    color: #ffd04b; /* 显眼的颜色 */
}

.el-dialog {
    margin-top: 20vh; /* 对话框顶部与页面顶部对齐 */
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

/* 内容容器样式 */
.container {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    margin-top: 50px;
    height: 100%; /* 确保高度足够 */
    overflow: auto; /* 添加滚动条 */
}

.block {
    background-color: #f7f7f7;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    width: 300px;
    min-height: 260px;
    position: relative;
    overflow: visible; /* 确保内容不会被剪切 */
}

.block h2 {
    margin: 0;
    padding: 20px;
    background-color: #545c64;
    color: #fff;
    position: absolute;
    top: 0;
    width: 100%;
    transition: top 0.3s ease-in-out;
}

.block p {
    padding: 20px;
    color: #333;
    font-size: 16px;
    position: absolute;
    top: 60px;
    width: 100%;
    opacity: 0;
    transition: opacity 0.5s ease;
}

.block:hover p {
    opacity: 1;
}

/* 合作伙伴容器样式 */
.partners-container {
    display: flex;
    flex-wrap: wrap; /* 确保元素可以换行 */
    justify-content: space-around; /* 分布间隔平均 */
    align-items: flex-start; /* 对齐到容器的顶部 */
    margin-top: 50px;
    padding: 20px;
    height: 100%; /* 确保高度足够 */
    overflow: auto; /* 添加滚动条 */
}

.partners-container h2 {
    text-align: center;
    font-size: 30px;
    color: #333;
    margin: 20px 0;
    width: 100%;
}

.partner-block {
    background-color: #f7f7f7;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    width: 22%; /* 调整宽度以适应一行显示四个元素 */
    min-height: 320px; /* 调整高度以保持一致 */
    margin: 10px 1%; /* 调整边距以适应响应式设计 */
    padding: 20px;
    text-align: center;
    transition: transform 0.3s ease;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    overflow: visible; /* 确保内容不会被剪切 */
}

.partner-block:hover {
    transform: translateY(-10px);
}

.partner-logo {
    width: 150px; /* 控制 logo 的大小 */
    height: auto;
    margin-bottom: 20px;
}
.partner-name {
    font-size: 24px;
    margin-bottom: 10px;
    color: #333;
}

.partner-description {
    font-size: 14px;
    color: #666;
    line-height: 1.6;
    text-align: justify;
}

/* 调整容器高度以显示滚动条 */
.container,
.partners-container {
    height: 100%; /* 确保高度足够 */
    overflow: auto; /* 添加滚动条 */
}

/* 确保内容元素可以填充容器的高度 */
.block,
.partner-block {
    min-height: 100%; /* 确保内容元素可以填充容器的高度 */
}

/* 使用flex-grow属性填充容器 */
.block,
.partner-block {
    flex-grow: 1; /* 确保子元素可以填充可用空间 */
}

/* 确保父元素没有overflow: hidden */
.parent-container {
    overflow: auto; /* 确保父元素没有overflow: hidden */
}

/* 调整父容器高度以显示滚动条 */
.parent-container {
    height: 100vh; /* 或者设置其他合适的高度 */
    overflow: auto; /* 添加滚动条 */
}
.el-row {
    margin-bottom: 20px;
}

.el-col {
    border-radius: 4px;
}

.box-card {
    margin-bottom: 20px;
}

.image {
    width: 100%;
    display: block;
}

.content {
    padding: 14px;
    font-size: 14px;
    line-height: 20px;
    color: #303133;
    word-wrap: break-word;
}

.name {
    font-size: 16px;
    color: #303133;
    font-weight: bold;
}

.description {
    color: #909399;
    font-size: 14px;
}

.price {
    color: #f56c6c;
    font-size: 14px;
    font-weight: bold;
}

.bottom {
    margin-top: 13px;
    line-height: 12px;
}

.button {
    padding: 0;
    margin-left: 5px;
}
</style>


