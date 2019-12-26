<!-- 取消预约最后那个表 -->
<template>

<div class="mainstyle">

    <div class="manager">会议纪要管理</div>
    <div class="style">

        <div class="fine">

            <Table border :columns="elementCol" :data="recordForm"></Table>

        </div>

        <div>
            <Page :total="pageCount" :page-size="pageSize" class="ct" @on-change="pageChange" simple></Page>
        </div>

        <div class="dialog">
            <router-view :summary="summary"></router-view>
        </div>

    </div>

</div>

</template>

<script>

import storage from '../utils/storage'

export default {

  name: 'managerSummary',

  inject:['reload'],
  data () {

    return {
      
      // 操作
      operator: '上传/更新',

      symbol: "暂无",
      
      summary: {

        sumId: null,
        title: null,
        content: null,
        confId: null,
        updateTime: null

      },
      
      summaryInfo: {

      },

      confId: null,

      pageCount: 0, // 总记录数
      
      pageSize: 8,  // 每页容量

      elementCol: [

        {
          title: '会议编号',
          key: 'confId',
          width: 100,
          align: 'center'
        },

        {
          title: '会议名',
          key: 'confName',
          width: 200,
          align: 'center'
        },

        {
          title: '纪要编号',
          key: 'sumId',
          align: 'center'

        },

        {
          title: '状态',
          key: 'state',
          width: 100,
          align: 'center'
        },

        {
          title: '操作',
          key: 'operate',
          width: 200,
          align: 'center',

          render: (h, params) => {  // params即为Table对象

            return h('div', [

              h('Button', {

                style: {

                  marginRight: '5px',
                  background: 'red',
                  color: 'white'

                },

                on: {

                  click: () => {

                    this.show(params);

                    // if (params.row.sumId == "——") {

                    //     this.operator = '上传';
                    
                    // } else {
                    
                    //     this.operator = '更新';
                    
                    // }

                  }

                }

              }, this.operator),

              h('Button', {

                style: {

                  background: 'blue',
                  color: 'white'

                },

                on: {

                  click: () => {

                    this.cancel(params);
                  
                  }
                
                }

              }, '删除')

            ])

          }

        }

      ],

      testForm: [

      ],
      
      recordForm: [

      ],

      historyData: {}

    }

  },

  mounted () {

    this.getForm();
  
  },

  methods: {

    getForm () {    // 从后端获取会议列表

      var _this = this;
      _this.message = "listSum"; // 可以向后端传的表单

      this.axios({

        method: 'post',

        url: 'http://localhost:8080/admin/listSummary',

        headers: {

        'Content-Type': 'application/json;charset=utf-8',
        'Token': this.$store.state.token

        },

        data: {

          message: _this.message,
          userAccount: this.$store.state.userAccount

        },

        responseType: 'json' // 默认如此


      }).then((res) => {

        var info = res.data.info;

        var code = res.data.code;

        // console.table(info);

        if (code == 200) {
          
          this.pageCount = info.length;

          for (var i = 0; i < info.length; i++) {
            
              if (info[i].sumId == null) {

                  info[i].sumId = this.symbol;

              }

              this.recordForm.push(info[i]);

          }

        }

      }).catch((err) => {

        console.log('error.data :' + err.data);

      });

    },

    pageChange (index) {

      const _start = (index - 1) * this.pageSize;
      const _end = index * this.pageSize;

      this.recordForm = this.testForm.slice(_start, _end);
    
    },

    show (params) { // 上传操作函数体
    
        var summary = params.row;  // 需要访问Table的row才能拿到行的对象

        console.log("上传开始: ");

        console.table(summary);

        console.log("上传结束！");

        this.summary.confId = summary.confId;

        this.summary.sumId = summary.sumId;

        if (this.summary.sumId != this.symbol) { // 存在纪要

            this.axios({

              method: 'post',

              url: 'http://localhost:8080/admin/listSummaryInfo',

              headers: {

                'Content-Type': 'application/json;charset=utf-8',
                'Token': this.$store.state.token

              },

              responseType: 'json',

              data: {

                confId: this.summary.confId

              }
            }).then((res) => {

                var info = res.data.info;
                var code = res.data.code;

                if (code == 200) {

                    console.log("存在纪要请求成功，开始");

                    console.table(info);
                    
                    console.log("存在纪要请求成功, 结束");

                    this.summary.title = info.title;
                    this.summary.content = info.content;
                    this.summary.updateTime = info.updateTime;

                    storage.set('title', this.summary.title);
                    storage.set('sumId', this.summary.sumId);
                    storage.set('updateTime', this.summary.updateTime);
                    storage.set('content', this.summary.content);
                    storage.set('confId', this.summary.confId);

                    this.$store.commit('setTitle', this.summary.title);

                    this.$store.commit('setSumId', this.summary.sumId);
                    
                    this.$store.commit('setUpdateTime', this.summary.updateTime);
                    
                    this.$store.commit('setContent', this.summary.content);
                    
                    this.$store.commit('setConfId', this.summary.confId);
                    
                    this.$router.push('/uploadSummary');

                } else if (code == 400) {

                  this.$Message.error('系统错误!');

                } else {

                  this.$Message.error('未知错误!');

                }

            });

        } else {

            this.summary.title = this.symbol;
            this.summary.content = this.symbol;
            this.summary.updateTime = this.symbol;

            storage.set('title', this.summary.title);
            storage.set('sumId', this.summary.sumId);
            storage.set('updateTime', this.summary.updateTime);
            storage.set('content', this.summary.content);
            storage.set('confId', this.summary.confId);
            this.$store.commit('setTitle', this.summary.title);

            this.$store.commit('setSumId', this.summary.sumId);

            this.$store.commit('setUpdateTime', this.summary.updateTime);

            this.$store.commit('setContent', this.summary.content);

            this.$store.commit('setConfId', this.summary.confId);

            this.$router.push('/uploadSummary');

        }
        
    },

    cancel (params) {   // 删除操作函数体

      var sumId = params.row.sumId;

      this.summary.sumId = sumId;

      if (sumId == this.symbol) {

          this.$Message.info("暂无纪要！");

      } else {

      this.$Modal.warning({

        title: '删除纪要记录',
        okText: '删除',
        closable: true,
        content:'您确定要删除这条会议纪要吗?',
        onOk:() => {

            this.axios({

              method: 'post',

              url: 'http://localhost:8080/admin/deleteSummary',

              headers: {

                'Content-Type': 'application/json;charset=utf-8',
                'Token': this.$store.state.token

              },

              responseType: 'json',

              data: {

                sumId: this.summary.sumId

              }
            }).then((res) => {

                // var info = res.data.info;
                var code = res.data.code;

                if (code == 200) {

                  this.$Message.info('删除成功!');
                  this.reload();  // 刷新界面

                } else if (code == 400) {

                  this.$Message.error('删除失败!');

                } else {

                  this.$Message.error('未知错误!');

                }

            });

        }

      });

      }

    }

  }

}
</script>

<style scoped lang="less">

.manager {

    position: absolute;
    top: 100px;
    left: 45%;
    font-size: 24px;
    color: white;
}

.mainstyle {

    position: absolute;
    left: 0px;
    top: 0px;
    width: 100%;
    height: 100%;
    //height: 670px;
    text-align: center;
    // background: -webkit-linear-gradient( rgb(102, 176, 236) ,rgb(179, 200, 211)); /* Safari 5.1 - 6.0 */
    // background: -o-linear-gradient( rgb(102, 176, 236), rgb(179, 200, 211)); /* Opera 11.1 - 12.0 */
    // background: -moz-linear-gradient(rgb(102, 176, 236),rgb(179, 200, 211)); /* Firefox 3.6 - 15 */
    // background: linear-gradient( rgb(59, 158, 238) , rgb(94, 180, 226)); /* 标准的语法 */
    background-image: url(../assets/bg.jpg);
    background-repeat: no-repeat;
    background-size: cover;
}

.fine {

    position: relative;
    top: 150px;
    left: 10%;
    width: 80%;

}

.ct {
    position: absolute;
    top: 585px;
    left: 680px;
}

.remove {

    float: left;

}

.dialog {

    position: relative;
    color: #2e2c2d;
    font-size: 16px;

}

.style {

    position: relative;
    height: 500px;
    width: 100%;

}

</style>
