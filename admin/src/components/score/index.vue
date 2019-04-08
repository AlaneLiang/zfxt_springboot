<style lang='less'>
  .table-basic-vue {}
</style>
<template>
  <div class="table-basic-vue frame-page h-panel">
    <div class="h-panel-bar"><span class="h-panel-title">成绩查询</span></div>
    <div class="h-panel-body">
      <Table :loading="loading" :datas="datas">
        <TableItem title="序号" :width="100">
          <template slot-scope="props">{{props.index+1}} </template>
        </TableItem>
        <TableItem :width="100" prop="classCode" title="课程代码"></TableItem>
        <TableItem :width="100" prop="className" title="课程名"></TableItem>
        <TableItem :width="100"  title="卷面分">
          <template slot-scope="props">
             <div v-if="props.data.examScore === '&nbsp;'">无卷面</div>
            <div  v-else>{{props.data.examScore}}</div>
          </template>
        </TableItem>
        <TableItem :width="100" prop="lastScore" title="最终成绩"></TableItem>
        <TableItem :width="100" prop="credit" title="学分"></TableItem>
        <TableItem :width="100"  title="是否补考">
          <template slot-scope="props">
            <span class="h-tag h-tag-bg-gray" v-if="props.data.isMakeup === '&nbsp;'">无补考</span>
            <span class="h-tag h-tag-bg-gray" v-else-if ="props.data.isMakeup === '0'">否</span>
            <span class="h-tag h-tag-bg-green" v-else >是</span>
          </template>
        </TableItem>
      </Table>
      <p></p>
      <Pagination v-if="pagination.total>0" :size="pagination.size" :cur="pagination.page" align="right" :total="pagination.total" @change="changePage" />
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      htmlTag: '&nbsp;',
      keyword: '',
      pagination: {
        page: 1,
        size: 20,
        total: 0
      },
      datas: [],
      loading: false
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.getData();
    },
    changePage(page) {
      this.pagination.page = page.cur;
      this.pagination.size = page.size;
      this.getData();
    },
    getData(reload = false) {
      if (reload) {
        this.pagination.page = 1;
      }
      this.loading = true;
      let score = window.sessionStorage.getItem('score');
      if (score == null) {
        this.getNetData();
        this.loading = false;
      } else {
        this.datas = JSON.parse(score);
        this.loading = false;
      }
    },
    getNetData() {
      fetch.get('/zfxt/score').then((res) => {
        if (res.code != '000000') {
          this.$Message.error(res.msg);
          this.loading = false;
          return;
        }
        let m = res.result;
        this.datas = m;
        window.sessionStorage.setItem('score', JSON.stringify(m));
      });
    }
  },
  computed: {

  }
};
</script>
