import Vue from 'vue';
import App from 'components/App.vue';
import store from 'js/vuex/store.js'
import heyuiConfig from 'js/config/heyui-config';
import routerConfig from 'js/config/router-config';
import 'js/vue/components';

require('./css/app.less');

//process.env.NODE_ENV == 'development' && require('./mock')

//HeyUI已经设定为全局变量，无需引用
//设定全局变量请参考根目录下的hey.js文件



heyuiConfig();
Vue.use(HeyUI);

const router = routerConfig();

const app = new Vue({
  store,
  router,
  el: '#app',
  render: h => h(App)
});

export default app;
