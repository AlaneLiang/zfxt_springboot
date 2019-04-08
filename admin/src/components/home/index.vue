<style lang='less'>
</style>
<template>
  <div class="search-list-vue frame-page h-panel">
    <div class="h-panel-bar"><span class="h-panel-title">我的课表</span></div>
    <div class="h-panel-bar">
    </div>
    <div class="h-panel-body">
      <div>
        <AItem v-for="d of datas" :key="d.id" :item="d" :loading="loading"></AItem>
      </div>
    </div>
  </div>
</template>
<script>

function initData() {
  let timeTable = JSON.parse(window.sessionStorage.getItem('timetable'));

  let list = [];
  for (let i = 0; i < timeTable.length; i++) {
    console.log(i)
    let data = {
      title: timeTable[i].name,
      tags: [timeTable[i].classRoom, timeTable[i].teacher, timeTable[i].classTime,timeTable[i].weekNum],
      desc: ''
    };
    list.push(Utils.extend({ id: i }, data));
  }
  return list;
}
export default {
  data() {
    return {
      datas: [{}, {}, {}],
      loading: true,
    };
  },
  mounted() {
    this.init();
  },
  watch: {
    params() {
      this.getData();
    }
  },
  methods: {
    init() {
      this.getData();
    },
    getData(reload = false) {
      this.datas = initData();
      this.loading = false;
    },
    getLocalData() {
      let timeTable = JSON.parse(window.sessionStorage.getItem('timetable'));
      let list = [];
      for (let i = 0; i < timeTable.size(); i++) {
        let data = {
          title: timeTable[i].name,
          tags: [timeTable[i].classRoom, timeTable[i].teacher, timeTable[i].classTime,timeTable[i].weekNum],
          desc: '一个基于Vue.js的高质量UI组件库，一个基于Vue.js的高质量UI组件库，一个基于Vue.js的高质量UI组件库，一个基于Vue.js的高质量UI组件库，一个基于Vue.js的高质量UI组件库'
        };
        list.push(Utils.extend({ id: i }, data));
      }
      return list;
    }
  },
  computed: {

  }
};
</script>
