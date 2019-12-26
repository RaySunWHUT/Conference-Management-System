<!-- 取消预约最后那个表 -->
<template>

<div class="mainstyle">

    <div class="manager">会议信息管理</div>
    <div class="style">

        <div class="fine">

            <Table border :columns="elementCol" :data="recordForm" @on-row-click="getConfId"></Table>

        </div>

        <div>
            <Page :total="pageCount" :page-size="pageSize" class="ct" @on-change="pageChange" simple></Page>
        </div>

        <div class="dialog">
            <router-view :confer="confer"></router-view>
        </div>

    </div>

</div>

</template>

<script>

export default {

  name: 'managerOrder',

  inject:['reload'],
  data () {

    return {

      hello: false,

      confer: {

        confId: null,
        confName: null,
        startTime: null,
        endTime: null,
        buildingName: null,
        roomName: null

      },

      seTime: [],

      buildingAndRoom: [],

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
          title: '会议地点',
          key: 'venue',
          align: 'center'

        },

        {
          title: '会议时间',
          key: 'confTime',
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

                  }

                }

              }, '修改'),

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

    this.getForm()
  
  },

  methods: {

    getConfId(data, index) {

      this.confId = data.confId;

    },

    getForm () {

      var _this = this;
      _this.message = "listConf"; // 可以向后端传的表单

      this.axios({

        method: 'post',

        url: 'http://localhost:8080/admin/listConferences',

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

        if (code == 200) {
          
          _this.pageCount = info.length;

          console.table(info);

          for (var i = 0; i < info.length; i++) {

              this.recordForm.push(info[i]);

          }

        }

      }).catch((err) => {

        console.log('error.data :' + err.data);

      })

    },

    pageChange (index) {

      const _start = (index - 1) * this.pageSize;
      const _end = index * this.pageSize;

      this.recordForm = this.testForm.slice(_start, _end);
    
    },

    show (params) {
    
      var conf = params.row;  // 需要访问Table的row才能拿到行的对象

      this.confer.confId = conf.confId;
      this.confer.confName = conf.confName;
      
      var venue = conf.venue;

      this.buildingAndRoom = venue.split(" ");

      this.confer.buildingName = this.buildingAndRoom[0];
      this.confer.roomName = this.buildingAndRoom[1];

      var confTime = conf.confTime;

      this.seTime = confTime.split("——");

      this.confer.startTime = this.seTime[0];

      this.confer.endTime = this.seTime[1];

      console.log("-----------------------");
      console.table(this.confer);

      this.$router.replace('/managerOrder/formInfo');

    },

    cancel (params) {

      var confId = params.row.confId;

      this.$Modal.warning({

        title: '删除会议',
        okText: '删除',
        closable: true,
        content:'您确定要删除这条预约记录吗?',
        onOk:() =>{

            this.axios({

              method: 'post',

              url: 'http://localhost:8080/admin/deleteConference',

              headers: {

                'Content-Type': 'application/json;charset=utf-8',
                'Token': this.$store.state.token

              },

              responseType: 'json',

              data: {

                confId: confId

              }
            }).then((res) => {

                var info = res.data.info;
                var code = res.data.code;

                // console.table(res.data);

                if (code == 200) {

                  this.$Message.info('删除成功!');
                  this.reload();  // 刷新界面

                } else if (code == 400) {

                  this.$Message.error('删除失败!');

                } else {

                  this.$Message.error('未知错误!');

                }

            })

        }

      })

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
