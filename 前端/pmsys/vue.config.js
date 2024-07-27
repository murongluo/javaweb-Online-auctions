module.exports = {
    devServer: {
      port: 7000,
      proxy: {
        '/api': {
          target: 'http://localhost:8080', // 此处修改为正确的地址
          ws: true,
          changeOrigin: true,
          pathRewrite: {
            '^/api': ''
          },
          // 添加这一行，将 withCredentials 设置为 false
          withCredentials: false
        }
      }
    }
  };
  
