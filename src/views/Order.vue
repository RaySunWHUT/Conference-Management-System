<template>
    <div class="back">

        <div class="title">会议清单</div>

        <div class="bottom"></div>

        <div class="fine2">

            <Table border :columns="column1" :data="historyItem"></Table>

            <div class="page_position">

                <Page :total="itemCount" :page-size="PageSize" :current.sync="currentPage" @on-change="pageChange" simple></Page>

            </div>

            <div class="addBox">

                <Button type="primary" @click="addItem">添加会议</Button>

            </div>

        </div>

        <Delete v-if="deleteStatus" :parentToChild="deleteName" @delItem = "deleteItem"></Delete>

        <div class="dialog">

            <router-view></router-view>

        </div>

    </div>

</template>

<script>
import Delete from '@/components/DeleteItem.vue'

export default {

  components: {

    Delete

  },

  data () {
    
    return {

      ifvisible: false,

      currentPage: 1,

      itemCount: 0,

      PageSize: 8,

      deleteStatus: false,

      deleteIndex: null,

      deleteName: null,

      addName: null,  // 存放要添加样品的名字

      // deleteStatus: '',  // 是否删除成功

      column1: [

        {

          title: '序号',
          key: 'index',
          align: 'center',
          width: '80',

          render: (h, params) => {
            return h('div', {
              style: {

              }
            }, this.indexArray[params.index])
          }
        },
        {

          title: '会议',
          key: 'paramName',
          align: 'center'

        },
        {

          title: ' ',
          key: 'operate',
          width: 153,

          render: (h, params) => {
            return h('div', [

              h('Button', {

                on: {

                  click: () => {

                    this.makeAppointment(params.row) // 参数为样品名

                  }

                },

                style: {

                  background: 'rgb(18, 107, 240)',
                  color: 'white'

                }

              }, '预约'),

              h('Button', {

                on: {

                  click: () => {
                    // this.makeDelete(params.row)
                    this.deleteStatus = true
                    this.deleteIndex = params.index
                    this.deleteName = params.row
                  }

                },

                style: {

                  background: 'red',
                  color: 'white'

                }

              }, '删除')
            ])
          }
        }
      ],

      historyItem: [],

      indexArray: [],

      testArray: []

    }
  },
  methods: {


    getItem: function () {

      var _this = this;

      this.axios({

        method: 'post',

        url: 'http://192.168.43.17:8080/user/listParams',

        headers: {

          'Content-Type': 'application/json;charset=utf-8',
          'Token': this.$store.state.token

        },

        data: {
          userAccount: _this.$store.state.user.userAccount
        },

        responseType: 'json'

      }).then(function (response) {

        console.log(response.data)
        var paramsList = response.data.info.paramList
        _this.testArray = paramsList
        _this.itemCount = _this.testArray.length

        if (_this.itemCount == 0) { // 若样品数为0

          _this.$router.push('/home')

        } else {

          _this.historyItem = _this.testArray.slice(0, _this.PageSize)

          for (let i = 0; i < _this.PageSize; i++) {

            _this.indexArray.push(i + 1)

          }

        }

      })

    },

    pageChange (index) {

      const _start = (index - 1) * this.PageSize
      const _end = index * this.PageSize
      this.historyItem = this.testArray.slice(_start, _end)
      this.indexArray = []

      for (let i = _start; i < _end; i++) {

        this.indexArray.push(i + 1)

      }

    },

    makeAppointment (index) {

      this.$router.push({ path: '/orderInfo', query: { param: index } })

    },

    deleteItem (msg) {

      console.log(msg)

      if(msg == 'success') {

        this.deleteStatus = false
        this.testArray.splice(this.deleteIndex, 1)
        this.historyItem = this.testArray.slice((this.currentPage - 1) * this.PageSize, this.currentPage * this.PageSize)

      } else if (msg == 'fail') {

        this.deleteStatus = false

      }

    },

    addItem () {

      this.$Modal.info({

        title: '添加会议',
        okText: '添加',
        render: (h) => {

          return h('Input', {
            
            props: {
              value: this.confName,
              placeholder: '请输入会议名称',
              autofocus: true
            
            },
            
            on: {
            
              input: (val) => {
                this.addName = val
              }
            }
          })
        }

      })

    },

    onOk: () => {

      if(this.addName != null) {

        this.axios({

          method: 'post',

          url: 'http://192.168.43.17:8080/user/saveParams',

          headers: {

            'Content-Type': 'application/json;charset=utf-8',
            'Token': this.$store.state.token

          },

          responseType: 'json',

          data: {

            userAccount: this.$store.state.user.userAccount,
            paramName: this.addName

          }
        }).then((res) => {

            console.log(res.data)

            if (res.data.code == 200) {

              this.$Message.info('添加成功!')
              this.getItem()
              this.currentPage = 1
            } else if (res.data.status == 400) {

              this.$Message.error('添加失败!')                  
            }
        
        })

      } else {
        
        this.$Message.error('会议名不能为空')
      
      }

    }

  },

  watch: {

    // $route: {

    //   handler: function () {

    //       this.historyItem = this.testArray.slice(0, this.PageSize);
    //       this.itemCount = this.testArray.length;

    //   }

    // }

  },

  mounted () {

    this.getItem()
  
  }

}
</script>

<style lang="less">

.title {

    position: absolute;
    top: 100px;
    left: 680px;
    color: white;
    font-size: 25px;
    font-display: initial;

}

.addBox button {

    width: 130px;
    height: 45px;
    font-size: 20px;
    text-indent: -0.1em;
    font-weight: normal;

}


.addBox {

    position: absolute;
    top: 435px;
    left: 868px;
    float: right;

}

.back {

    position: absolute;
    left: 0px;
    top: 0px;
    //width: 100%;
    //height: 96%;
    // background: -webkit-linear-gradient( rgb(102, 176, 236) ,rgb(179, 200, 211)); /* Safari 5.1 - 6.0 */
    // background: -o-linear-gradient( rgb(102, 176, 236), rgb(179, 200, 211)); /* Opera 11.1 - 12.0 */
    // background: -moz-linear-gradient(rgb(102, 176, 236),rgb(179, 200, 211)); /* Firefox 3.6 - 15 */
    // background: linear-gradient( rgb(59, 158, 238) , rgb(94, 180, 226)); /* 标准的语法 */
    background-image: url(../assets/bg.jpg);
    width: 100%;
    height: 100%;
    background-repeat: no-repeat;
    background-size: cover;
}

.fine2 {

    position: absolute;
    top: 150px;
    left: 260px;
    width: 1000px;
    z-index: 2;

}

.bottom {

    position: absolute;
    top: 150px;
    left: 260px;
    width: 1000px;
    height: 425px;
    background: #fff;

}

.page {

    left: 848px;
    margin-top: 1%;
    position: absolute;

}

.page_position {

    position: absolute;
    top: 430px;
    left: 390px;
    width: 200px;
    height: 100px;
    float: right;

}

.dialog-input {

    position: fixed;
    z-index: 11;
    top: 250px;
    left: 35%;
    width: 30%;
    height: 100px;
    border: 1px solid black;
    background: white;
    border-radius: 15px;
    text-align: center;
    overflow: hidden;

}

.bl {

    position: absolute;
    top: 15px;
    left: 20px;
    width: 350px;

}

.mk {

    position: absolute;
    top: 58px;
    left: 98px;

}

.mk2 {

    position: absolute;
    top: 58px;
    left: 220px;

}

.dialog {

    position: relative;
    color: #2e2c2d;
    font-size: 16px;

}

.ivu-table .demo-table-info-row td{

    background-color: #2db7f5;
    color: #fff;

}

</style>
