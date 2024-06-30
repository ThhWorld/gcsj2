const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,
  devServer: {
      port:8080,
    // 本地配置
    proxy: {
      '/api': {
        target: 'http://localhost:8081',  // 后台接口域名
        changeOrigin: true,  //是否跨域
        pathRewrite:{
          '^/api': '/'
        }
      },
    }
  }
})
