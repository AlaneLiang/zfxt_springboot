const path = require('path');
module.exports = {
  port: 9019,
  root: 'dist',
  webpack: {
    console: true,
    publicPath: '/',
    output: {
      './index.html': {
        entry: './src/app',
        commons: ['common']
      }
    },
    commonTrunk: {
      common: [
        'manba',
        'js-model',
        './src/js/common/utils',
        './src/js/common/fetch',
        'hey-global',
        'hey-log',
        'heyui',
      ]
    },
    alias: {
      model: './src/js/model/',
      js: './src/js/',
      components: './src/components/',
    },
    global: {
      Utils: [path.resolve(__dirname, 'src/js/common/utils'), 'default'],
      Manba: 'manba',
      HeyUI: 'heyui',
      Model: 'js-model',
      G: 'hey-global',
      log: 'hey-log',
      fetch: [path.resolve(__dirname, 'src/js/common/fetch'), 'default'],
    },
    devServer: {
      proxy: {
       // 此处应该配置为开发服务器的后台地址
        '/': {
          target: 'http://localhost:8080',

        },
        '/sys/user': {
          target: 'http://localhost:8080',

        }
      },
      historyApiFallback: true
    },
    globalVars: './src/css/var.less',
    externals: {}
  },
  copy: ['static/images/*', 'call/*', './baidu_verify_7O2vpVMzwg.html']
};
