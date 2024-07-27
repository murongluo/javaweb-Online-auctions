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
         <!-- 轮播图 -->
         <div class="carousel-container">
            <h2>镇馆之宝</h2>
            <el-carousel :interval="5000" arrow="always">
                <el-carousel-item v-for="(item, index) in images" :key="index">
                    <img :src="item.src" alt="轮播图图片" style="width: 100%; max-height: 100%; object-fit: contain;"  @click="showDialog(index)" >
                </el-carousel-item>
            </el-carousel>
            <el-dialog :visible.sync="dialogVisible" :before-close="handleClose" title="镇馆之宝详情"  append-to-body>
                <p>这是镇馆之宝的详细信息，可以在这里添加任何内容。</p>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">关闭</el-button>
                </span>
            </el-dialog>
        </div>
         <!-- 拍卖流程 -->
         <div class="auction-process">
            <h2>拍卖流程</h2>
            <el-steps :space="300" :active="1" align-center>
                <el-step v-for="(step, index) in auctionSteps" :key="index" :title="step.title" :description="step.description"></el-step>
            </el-steps>
        </div>
             <!-- 拍品上架流程 -->
             <div class="auction-process">
            <h2>拍品上架流程</h2>
            <el-steps :space="300" :active="1" align-center>
                <el-step v-for="(step, index) in auctionUploadSteps" :key="index" :title="step.title" :description="step.description"></el-step>
            </el-steps>
        </div>
    </div>
</template>
  
  <script>
export default {
    data() {
        return {
            dialogVisible: false,
            activeIndex: '1',
            swiperOptions: {
                pagination: {
                    el: '.swiper-pagination',
                    clickable: true
                }
            },
            auctionUploadSteps: [
                { title: '提交拍卖品信息', description: '个人需要准备好拍卖品的相关信息，包括名称、描述、照片等，然后向拍卖机构或网站提交这些信息。' },
                { title: '审核与评估', description: '拍卖机构或网站会对提交的拍卖品信息进行审核和评估，确认其真实性和合法性，以及评估其潜在的市场价值。' },
                { title: '签订协议', description: '通过审核后，个人需要和拍卖机构或网站签订相关的协议，包括拍卖的费用、佣金、责任分工等。' },
                { title: '准备拍卖品', description: '个人需要准备好拍卖品的物品本身，确保其在拍卖过程中可以展示和展示的充分。' },
                { title: '上架拍卖品', description: '拍卖机构或网站根据审核后的信息和协议，将拍卖品上架到拍卖平台上，供潜在买家进行浏览和竞拍。' },
                { title: '竞拍和成交', description: '潜在买家在拍卖平台上对拍卖品进行竞拍，最终达成交易。' },
                { title: '结算与交付', description: '买家支付拍卖价后，个人需要将拍卖品交付给买家，或者按照协议选择其他交付方式。' }
            ],
            images: [
        { src: 'http://img2.baidu.com/it/u=200415398,1461279263&fm=253&app=138&f=JPEG?w=800&h=1011' },
        { src: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fss2.meipian.me%2Fusers%2F67839186%2F2fbee5f4e506447d8b20fe8d8694aade.jpeg%3Fmeipian-raw%2Fbucket%2Fivwen%2Fkey%2FdXNlcnMvNjc4MzkxODYvMmZiZWU1ZjRlNTA2NDQ3ZDhiMjBmZThkODY5NGFhZGUuanBlZw%3D%3D%2Fsign%2F294b8574af4a7c2ddcb79d2d2f1071b6.jpg&refer=http%3A%2F%2Fss2.meipian.me&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1716789914&t=757bbbec042b607d5755d446aefff917' },
        { src: 'http://img2.baidu.com/it/u=200415398,1461279263&fm=253&app=138&f=JPEG?w=800&h=1011' }
        // 添加更多图片
      ],
            auctionSteps: [
                { title: '注册或登记', description: '参与竞拍前，需要先进行注册或登记，提供个人信息和联系方式等。' },
                { title: '预展或查看', description: '有些竞拍活动会提前举行预展或公开查看，让竞拍者有机会实地查看物品的品质和状况。' },
                { title: '出价', description: '在竞拍开始时，竞拍者可以根据自己的意愿和预算出价，通常出价是以一定金额递增的。' },
                { title: '竞拍', description: '在竞拍过程中，竞拍者可以根据其他竞拍者的出价和动向来调整自己的出价。' },
                { title: '中标', description: '当竞拍时间结束或达到最终竞拍价时，最高价的竞拍者获得竞拍物品。' },
                { title: '结算', description: '中标者需要支付最终竞拍价，然后可以领取竞拍物品或安排物品的交付和运输。' }
            ]
        };
    },
    methods: {
        showDialog(index) {
            
                
                this.dialogVisible = true;
            
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
  
  
<style>
.menupage {
    position: relative; 
    width: 100%;
    top: 0;
    z-index: 100;
}

.menupage .el-menu.el-menu--horizontal {
    border-bottom: none;
    height: 100px;
    line-height: 100px;
}

.menupage .el-menu.el-menu--horizontal > .el-menu-item,
.menupage .el-menu--horizontal > .el-submenu .el-submenu__title {
    height: 100px;
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

.auction-process {
    margin-top: 100px;
    text-align: center;
    padding: 0 20px;
 
    margin-bottom: 10px; /* 在底部留出10px的空白 */
}

.auction-process h2 {
    margin-bottom: 20px;
    font-size: 24px;
    font-weight: bold;
}

/* 为每个步骤设置不同的颜色 */
.el-step:nth-child(1) .el-step__head.is-process,
.el-step:nth-child(1) .el-step__title,
.el-step:nth-child(1) .el-step__description {
    color: #FFC0CB; /* 粉色 */
}

.el-step:nth-child(2) .el-step__head.is-process,
.el-step:nth-child(2) .el-step__title,
.el-step:nth-child(2) .el-step__description {
    color: #90EE90; /* 绿色 */
}

.el-step:nth-child(3) .el-step__head.is-process,
.el-step:nth-child(3) .el-step__title,
.el-step:nth-child(3) .el-step__description {
    color: #87CEEB; /* 蓝色 */
}

.el-step:nth-child(4) .el-step__head.is-process,
.el-step:nth-child(4) .el-step__title,
.el-step:nth-child(4) .el-step__description {
    color: #FFA07A; /* 橙色 */
}

.el-step:nth-child(5) .el-step__head.is-process,
.el-step:nth-child(5) .el-step__title,
.el-step:nth-child(5) .el-step__description {
    color: #9370DB; /* 紫色 */
}
.el-step:nth-child(6) .el-step__head.is-process,
.el-step:nth-child(6) .el-step__title,
.el-step:nth-child(6) .el-step__description {
    color: #F08080; /* 红色 */
}
.el-step:nth-child(7) .el-step__head.is-process,
.el-step:nth-child(7) .el-step__title,
.el-step:nth-child(7) .el-step__description {
    color: #F08080; /* 红色 */
}

.carousel-container {
    width: 100%;
    overflow: auto; /* 允许滚动条出现，以显示超出的内容 */
}

.carousel-container img {
    width: 100%;
    object-fit: contain; /* 图片会被缩放以适应容器，而不会被裁剪 */
}

/* 鼠标悬停放大效果 */
.el-step:hover {
    transform: scale(1.1);
    transition: transform 0.3s ease-in-out;
}

/* 响应式设计 */
@media (max-width: 768px) {
    .menupage .el-menu.el-menu--horizontal > .el-menu-item,
    .menupage .el-menu--horizontal > .el-submenu .el-submenu__title {
        padding: 0 20px; /* 减少内边距 */
    }

    .carousel-container,
    .auction-process {
        height: auto; /* 自适应高度 */
    }

    .el-steps {
        display: block; /* 将步骤条变为垂直布局 */
    }

    .el-steps .el-step {
        margin-bottom: 10px; /* 步骤之间增加一些间距 */
    }
}
</style>

  
