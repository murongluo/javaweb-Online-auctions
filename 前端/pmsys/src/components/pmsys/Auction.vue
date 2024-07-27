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
            <div class="container">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="承办方">
                        <el-input v-model="formInline.title" placeholder="请输入承办方"></el-input>
                    </el-form-item>
                    <el-form-item label="拍卖会id">
                        <el-input v-model="formInline.auctionId" placeholder="请输入拍卖会id"></el-input>
                    </el-form-item>
                    <el-form-item label="状态">
                        <el-select v-model="formInline.status" placeholder="请选择状态">
                            <el-option label="不区分" value=""></el-option>
                            <el-option label="结束" value="结束"></el-option>
                            <el-option label="准备中" value="准备中"></el-option>
                            <el-option label="进行中" value="进行中"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">查询</el-button>
                    </el-form-item>
  
                </el-form>
            </div>
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-card class="box-card">
                        <div class="content">
                            <el-row :gutter="20">
                                <el-col :span="8" v-for="(auctionSession, index) in filteredAuctionSessions" :key="index">
                                    <el-card class="box-card">
                                        <img :src="auctionSession.image" class="image" alt="拍卖场次图片" />
                                        <div class="content">
                                            <span class="name">{{ auctionSession.title }}</span>
                                            <br />
                                            <span class="status">{{ auctionSession.status }}</span>
                                            <br />
                                            <span class="description">{{ auctionSession.description }}</span>
                                            <br />
                                        </div>
                                        <div class="bottom clearfix">
                                            <el-button type="text" class="button" @click="joinAuction(auctionSession)">参与</el-button>
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
                    @current-change="handlePageChange"
                    :current-page="currentPage"
                    :page-sizes="[3,9, 18, 45, 90]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="totalAuctionSessions">
                </el-pagination>
                </el-col>
            </el-row>
        </div>
    </div>
</template>
<script>
import Axios from 'axios';

export default {
    data() {
        return {
            formInline: {
                title: '',
                status: '',
                auctionId:''
            },
            searchQuery: '',
            auctionSessions: [],
            totalAuctionSessions: 0,
            pageSize: 3,
            currentPage: 1,
            dialogVisible: false,
            currentImageIndex: -1,
            activeIndex: '4'
        };
    },
    created() {
        this.fetchAuctionSessions();
    },
    computed: {
        filteredAuctionSessions() {
            return this.auctionSessions.filter(session =>
                session.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
                session.description.toLowerCase().includes(this.searchQuery.toLowerCase())
            );
        }
    },

    methods: {
        onSubmit() {
            this.currentPage = 1;  // 重置为第一页
            this.fetchAuctionSessions();
        },
        fetchAuctionSessions() {
            const params = {
                title: this.formInline.title,
                auctionId: this.formInline.auctionId,
                status: this.formInline.status,
                page: this.currentPage,
                pageSize: this.pageSize
            };
            Axios.get('http://localhost:8080/getauction',{ params })
            .then(response => {
                if (response.data.code === 1) {
                    this.auctionSessions = response.data.data.rows;
                    this.totalAuctionSessions = response.data.data.total;
                    this.$message.success('数据加载成功');
                } else {
                    this.$message.error('数据加载失败: ' + response.data.msg);
                }
            })
           
        },
        joinAuction(auctionSession) {
            if (auctionSession.status !== '进行中') {
                this.$message.error('该场次当前不可参与');
                return;
            }
            console.log('参与拍卖场次:', auctionSession);
            this.$message.success('您已成功加入拍卖');
            this.$router.push({
        path: '/auIt',
        query: { auctionId: auctionSession.auctionId }  // 传递 auctionId 作为查询参数
    });
            // 添加实际的逻辑，例如跳转到拍卖详情页面
        },
        handlePageChange(newPage) {
            this.currentPage = newPage;
            this.fetchAuctionSessions(); // Fetch new page data
        },
        handleSizeChange(newSize) {
            this.pageSize = newSize;
            this.fetchAuctionSessions(); // Fetch new page size data
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

.status {
    color: #909399;
    font-size: 14px;
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


