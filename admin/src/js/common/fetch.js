import axios from 'axios';
import Qs from 'qs';
import HeyUI from 'heyui';
import store from '../vuex/store.js';

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
  let token = window.sessionStorage.getItem('session');
  if (token != null) {
    config.headers['sessionId'] = token;
  }
  // 在发送请求之前做些什么
  if (config.method == 'get') {
    HeyUI.$LoadingBar.start();
  } else {
    HeyUI.$Loading('加载中');
  }

  return config;
}, function (error) {
  // 对请求错误做些什么
  // HeyUI.$Loading.close();
  return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
  HeyUI.$Loading.close();
  HeyUI.$LoadingBar.success();
  // 对响应数据做点什么
  if (response.data.code == '999990') {
    window.sessionStorage.removeItem('session');
    window.sessionStorage.removeItem('user');
    window.sessionStorage.removeItem('timetable');
    window.sessionStorage.removeItem('score');
    store.dispatch('getSignInStatus', false);
  }
  if (response.data.code == '999991') {
    HeyUI.$Message.error(response.data.msg);
  }
  return response;
}, function (error) {
  // 对响应错误做点什么
  HeyUI.$Loading.close();
  HeyUI.$LoadingBar.success();
  return Promise.reject(error);
});

let fetch = {
  send(url, params) {
    return new Promise((resolve, reject) => {
      axios.post(url, params).then(response => {
        resolve(response.data);
      });
    }); ;
  },
  post(url, params) {
    return new Promise((resolve, reject) => {
      params = Qs.stringify(params);
      axios.post(url, params, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).then(response => {
        resolve(response.data);
      });
    });
  },
  get(url, params) {
    return new Promise((resolve, reject) => {
      axios.get(url, params).then(response => {
        resolve(response.data);
      });
    });
  },
  upload(url, formData) {
    return new Promise((resolve, reject) => {
      axios.post(url, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(response => {
        resolve(response.data);
      });
    });
  },
  download(url) {
    return new Promise((resolve, reject) => {
      axios({
        url: url,
        method: 'GET',
        responseType: 'blob' // important
      }).then((res) => {
        const url = window.URL.createObjectURL(new Blob([res.data]));
        const link = document.createElement('a');
        link.href = url;
        let filename = res.headers['content-disposition'];
        filename = filename.substr(filename.indexOf('=') + 1);
        link.setAttribute('download', filename);
        document.body.appendChild(link);
        link.click();
      });
    });
  }
};

export default fetch;
