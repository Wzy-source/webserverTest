module.exports = {
  devServer: {
    proxy: {
      "^/api": {
        // target: "http://courselearning.seec.seecoder.cn/",
        target: "http://localhost:8080",
        ws: true,
        changeOrigin: true
        // pathRewrite: {
        // '^/api/': '/', // remove base path
        // },
      }
    },
    disableHostCheck: true
  }
};
