<template>
    <div class="auction-platform-intro">
      <h1>槐序拍卖平台</h1>
      <h2>您的艺术品拍卖首选</h2>
      <el-button type="primary" @click="gotoAuctions">开始竞拍</el-button>
      <p>
        槐序拍卖平台是一家专注于艺术品、古董及收藏品交易的在线拍卖平台。我们为收藏爱好者、投资者和艺术品交易者提供了一个专业、安全、便捷的交易平台。
      </p>
      <p>
        在槐序拍卖平台，您可以通过网上竞拍和直播拍卖的方式参与到各类精品拍卖活动中。我们与众多知名专家、鉴定师和艺术品机构合作，确保每件拍品的品质和真实性。我们致力于为用户提供全方位的服务，包括鉴定评估、藏品展示、交易协助等。
      </p>
      <p>
        无论您是想寻找珍稀艺术品、投资收藏品还是展示自己的藏品，槐序拍卖平台都能满足您的需求。我们秉承公正、透明、诚信的原则，为用户提供安全可靠的交易环境。参与槐序拍卖平台的拍卖活动，让您享受艺术品交易的乐趣，感受收藏的魅力。
      </p>
      <p>
        槐序拍卖平台，让艺术品交易更简单，让收藏更有品味。欢迎您加入我们的行列，共同探索艺术与收藏的无限可能！
      </p>
    </div>
  </template>
  
  <script>
  export default {
    name: 'AuctionPlatformIntro',
    methods: {
      gotoAuctions() {
        // 导航到拍卖页面
        this.$router.push('/home');
      }
    }
  }
  </script>
  
  <style scoped>
  .auction-platform-intro {
  background: url('https://www.jsdaima.com/kindeditor/attached/image/20200211/20200211232130_91338.png') no-repeat center center fixed;
  background-size: cover;
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh; /* 使背景图片充满整个视口高度 */
  min-height: 600px; /* 最小高度，防止在视口高度很小的情况下内容被裁剪 */
  transition: background-size 0.5s ease;
}

.auction-platform-intro:hover {
  background-size: 110% 110%; /* 鼠标悬停时放大背景图 */
}

h1 {
  font-size: 36px;
  margin-bottom: 20px;
  font-weight: bold;
  text-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
}

h2 {
  font-size: 24px;
  margin-bottom: 40px;
  font-weight: bold;
}

p {
  font-size: 18px;
  line-height: 1.6;
  margin-bottom: 20px;
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.el-button {
  font-size: 18px;
  background-color: transparent;
  border-color: white;
  color: white;
  margin-top: 40px;
  padding: 10px 20px;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.el-button:hover {
  background-color: rgba(255, 255, 255, 0.2);
}

/* 鼠标悬停在背景图上时，为文字添加动态效果 */
.auction-platform-intro:hover h1,
.auction-platform-intro:hover h2,
.auction-platform-intro:hover p {
  transform: scale(1.05);
  transition: transform 0.3s ease;
}

  </style>
  
