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
            <el-menu-item index="6">登录/注册</el-menu-item>
        </el-menu>
        <div class="container">
    <div
      v-for="(feature, index) in features"
      :key="index"
      class="block"
      @mouseenter="feature.showContent = true"
      @mouseleave="feature.showContent = false"
    >
      <h2 :class="{ 'active': feature.showContent }">{{ feature.title }}</h2>
      <transition name="fade">
        <p v-if="feature.showContent">{{ feature.description }}</p>
      </transition>
    </div>
  </div>
  <div class="partners-container">
    <h2>合作商</h2>
        <div class="partner-block" v-for="(partner, index) in partners" :key="index">
            <img :src="partner.logo" :alt="partner.name" class="partner-logo">
            <h3 class="partner-name">{{ partner.name }}</h3>
            <p class="partner-description">{{ partner.description }}</p>
        </div>
    </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            partners: [
                {
                    name: '华硕',
                    logo: 'https://ts1.cn.mm.bing.net/th/id/R-C.07becef890b78ebe0dad2c55f51b268e?rik=8CSiBD8vZxqGYg&riu=http%3a%2f%2fpic.ntimg.cn%2ffile%2f20200414%2f25038502_112915205539_2.jpg&ehk=u5MqFowGW13cPUZw2%2bBk%2bQrXOUElTlFsSeabF4D48TI%3d&risl=&pid=ImgRaw&r=0',
                    description: '华硕是一家全球领先的电脑和科技公司，专注于创新和品质。'
                },
                {
                    name: '腾讯',
                    logo: 'https://p1.ssl.qhimg.com/t0133e5d966484e047d.jpg',
                    description: '腾讯是中国最大的互联网综合服务提供商之一，致力于连接人们和提供优质服务。'
                },
                {
                    name: '阿里巴巴',
                    logo: 'https://data.alibabagroup.com/ecms-files/886024452/296d05a1-c52a-4f5e-abf2-0d49d4c0d6b3.png',
                    description: '阿里巴巴是全球最大的电子商务平台，致力于促进全球贸易和商业创新。'
                },
              
            ], 
            features: [
        {
          title: '品类丰富',
          description: '我们的平台涵盖了艺术品、收藏品、珠宝首饰、名表奢侈品、数码产品等多个品类，满足您的多样化需求。',
          showContent: false
        },
        {
          title: '安全可靠',
          description: '我们重视用户信息安全，采用先进的技术手段保障您的交易安全。同时，我们的专业团队会对所有商品进行严格审核，确保商品的真实性和合法性。',
          showContent: false
        },
        {
          title: '操作简便',
          description: '用户界面设计简洁直观，让您轻松上手。无论是发布商品、参与竞拍还是支付收货，都能一键完成，节省您的宝贵时间。',
          showContent: false
        }
      ],
            activeIndex: '2',
            swiperOptions: {
                pagination: {
                    el: '.swiper-pagination',
                    clickable: true
                }
            },
        };
    },
    methods: {
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

<style>/* 全局样式 */
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
}

/* 菜单栏样式 */
.menupage {
  position: relative;
  width: 100%;
  top: 0;
  z-index: 100;
  height: 100%; /* 确保高度足够 */
  overflow: auto; /* 添加滚动条 */
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
  min-height: 260px; /* 调整这个值以适应内容 */
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

/* 清除浮动 */
.clearfix::after {
  content: "";
  display: table;
  clear: both;
}

/* 调整容器高度以显示滚动条 */
.container, .partners-container {
  height: 100%; /* 确保高度足够 */
  overflow: auto; /* 添加滚动条 */
}

/* 确保内容元素可以填充容器的高度 */
.block, .partner-block {
  min-height: 100%; /* 确保内容元素可以填充容器的高度 */
}

/* 使用flex-grow属性填充容器 */
.block, .partner-block {
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


</style>


