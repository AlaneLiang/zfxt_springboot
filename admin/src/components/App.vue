<style lang="less">
  @gradient-color: #3788ee;
  @bg-color: #f7f8fa;
  @title-color:#3a3a3a;
  @text-color: #7e7e7e;
  @placeholder-color: #7e7e7e;
  .login-vue {
    text-align: center;
    position: absolute;
    top: 0;
    bottom: 0;
    right: 0;
    left: 0;
    background: @bg-color;
    .login-container {
      width: 320px;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      .login-content {
        letter-spacing: 2px;
        background: #FFF;
        padding: 70px 30px 20px;
        box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.06);
        border-radius: 3px;
        box-sizing: border-box;
        >div {
          margin: 30px 0;
          &.login-input {
            position: relative;
            .placeholder {
              position: absolute;
              color: @placeholder-color;
              top: 6px;
              font-size: 16px;
              transition: all .2s;
              left: 0;
              pointer-events: none;
            }
            input {
              font-size: 16px;
              padding: 8px 0;
              height: 40px;
              width: 100%;
              border: none;
              border-radius: 0;
              border-bottom: 1px solid #d3d3d3;
              box-shadow: inset 0 0 0 1000px #fff;
              outline: none;
              box-sizing: border-box;
              transition: .3s;
              font-weight: 200;
              &:focus {
                border-bottom-color: @gradient-color;
                box-shadow: inset 0 0 0 1000px #fff;
              }
            }
            input:focus + .placeholder,input:-webkit-autofill + .placeholder, .placeholder.fixed{
              font-size: 13px;
              top: -16px;
            }
          }
          &.login-title {
            font-size: 30px;
            color: @title-color;
            line-height: 1;
            margin: -16px 0px 40px;
            font-weight: 200;
          }

        }
        > .buttonDiv {
          margin-top: 45px;
          .h-btn {
            padding: 12px 0;
            font-size: 18px;
            opacity: .8;
            border-radius: 3px;
            background: @gradient-color;
            border-color: @gradient-color;
            &:hover {
              opacity: 1;
              background: @gradient-color;
              border-color: @gradient-color;
            }
          }
        }
      }
      .copyright {
        letter-spacing: 1px;
        margin-top: 30px;
        color: @text-color;
        a {
          color: @text-color;
        }
      }
    }
  }
</style>

<template>
  <div id="app">
    <template>
      <div class="login" v-if="!signIn">
      <div class="login-vue">
        <div class="login-container">
          <div class="login-content">
            <div class="login-title">校园助手</div>
            <div class="login-name login-input">
              <input type="text" name="username" v-model="data.username" autocomplete="off"/>
              <span class="placeholder" :class="{fixed: data.username != '' && data.username != null}">教务系统账号</span>
            </div>
            <div class="login-password login-input">
              <input type="password" name="password" v-model="data.password" @keyup.enter="submit" autocomplete="off"/>
              <span class="placeholder" :class="{fixed: data.password != '' && data.password != null}">教务系统密码</span>
            </div>
            <div class="buttonDiv">
              <Button :loading="loading" block color="primary" size="l" @click="submit">登录</Button>
            </div>
          </div>
          <div class="copyright"> Copyright © 2019 校园助手 - <a href="">author lx</a>
            <div style="margin-top: 5px">
             <span  style="text-align: center;align-content: center;">Powered by  </span> <a href="https://www.upyun.com"> <img src="../images/youpai.png" style="width: 80px;height: 30px;align-content: center"></a>
            </div>
          </div>

        </div>
      </div>
      </div>
      <div v-else>
        <router-view></router-view>
      </div>
    </template>

  </div>
</template>
<script>

import store from 'js/vuex/store';

export default {
  data() {
    return {
      data: { username: '', password: '' },
      loading: false
    };
  },
  store,
  mounted() {
  },
  computed: {
    signIn() {
      return this.$store.state.signIn;
    }
  },
  methods: {
    submit() {
      this.loading = true;
      let username = this.data.username;
      let password = this.data.password;
      if (username == '') {
        this.$Message.error('账号不能为空');
        this.loading = false;
        return;
      }
      if (password == '') {
        this.$Message.error('密码不能为空');
        this.loading = false;
        return;
      }

      let params = {
        username: username,
        password: password
      };

      fetch.post('/zfxt/login', params).then((res) => {
        if (res.code != '000000') {
          this.$Message.error(res.msg);
          this.loading = false;
          return;
        }

        window.sessionStorage.clear();

        this.password = '';
        let m = res.result;
        window.sessionStorage.setItem('session', m.sessionId);
        this.$store.dispatch('getSignInStatus', true);
        window.sessionStorage.setItem('user', JSON.stringify(m.record.info));
        window.sessionStorage.setItem('timetable', JSON.stringify(m.record.courseArrayList));
        this.loading = false;
        this.$router.push('/');
      });
    }
  }
};
</script>
