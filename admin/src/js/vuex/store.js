import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    simpleUser: {},
    timeTable: {},
    msgCount: {
      messages: 2
    },
    siderCollapsed: false,
    signIn: window.sessionStorage.getItem('session') &&
      window.sessionStorage.getItem('user') &&
      window.sessionStorage.getItem('timetable') != null
  },
  mutations: {
    updateSimpleUser(state, simpleUser) {
      state.simpleUser = simpleUser;
    },
    updateTimeTable(state, data) {
      state.timeTable = data;
    },
    updateSiderCollapse(state, isShow) {
      setTimeout(() => {
        G.trigger('page_resize');
      }, 600);
      state.siderCollapsed = isShow;
    },
    updateMsgCount(state, data) {
      state.msgCount = data;
    },
    setSignInStatus(state, signIn) {
      state.signIn = signIn;
    }
  },
  actions: {
    async updateSimpleUser({ commit }, simpleUser) {
      commit('updateSimpleUser', simpleUser);
    },
    updateTimeTable(context, data) {
      context.commit('updateTimeTable', data);
    },
    updateSiderCollapse(context, data) {
      context.commit('updateSiderCollapse', data);
    },
    updateMsgCount(context, data) {
      context.commit('updateMsgCount', data);
    },
    async getSignInStatus({ commit }, signIn) {
      commit('setSignInStatus', signIn);
    }
  },
  getters: {
    account: state => {
      return state.simpleUser;
    },
    timetable: state => {
      return state.timeTable;
    },
    siderCollapsed: state => {
      return state.siderCollapsed;
    },
    msgCount: state => {
      return state.msgCount;
    },
    signInStatus: state => {
      return state.signIn;
    }
  }
});
