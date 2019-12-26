<!-- 预约功能页面 -->
<template>
    <div class="mainstyle">
        <div class="corresponding">
            会议室列表
        </div>
        <div class="box">

            <div class="lab">
                <Table :row-class-name="changeColorLab" :data="showLab" :columns="column1" size="small" border simple @on-row-click="clickLab"></Table>

                <div class="page">
                    <Page :total="labNumber"  :current="1"  @on-change="changePageLab" :page-size="pageSize" size="small" simple></Page>
                </div>
            </div>

            <div class="equipment">
                <Table :row-class-name="changeColorEquip" :data="showEquip"  :columns="column2" size="small" border simple @on-row-click="clickEquip"></Table>

                <div class="page">
                    <Page :total="equipmentNumber" :current.sync="currentPage"  @on-change="changePageEquip" :page-size="pageSize" size="small" simple></Page>
                </div>
            </div>

            <!-- 日期组件 -->
            <Date @getTime="parentTime"></Date>

            <div class="next">
                <Button type="success" @click="next">确定</Button>
            </div>

            <Modal v-model="modal1" title="您预约的时间段冲突, 已预约时间：">
                <ul class="modal_1">
                    <li v-for="i in conflictingTimes" :key="i">{{i}}</li>
                </ul>
            </Modal>

            <div class="cancel">
                <Button type="error" @click="suspend">取消</Button>
            </div>

            <!-- 添加会议 -->
            <!-- <div class="addBox">

              <Button type="primary" @click="addItem">添加会议</Button>

            </div> -->
            
        </div>

        <div class="alter">
            <router-view></router-view>
        </div>

    </div>
</template>

<script>
import Date from '@/components/Date.vue'
// import store from '../store'

export default {

  name: 'orderInfo',
  components: {

    Date

  },

  data () {

    return {

      confName: null,
      
      roomName: null,

      startTime: null,

      endTime: null,

      message: '',

      currentPage: 1,
      modal1: false,

      conflictingTimes: [], // 冲突时间段

      checkedEquipment: '',

      labIndex: 0,
      equipIndex: 0,

      container: [],
      pageSize: 8,

      // 实验室表格
      labData: [], // 总实验室
      showLab: [], // 每页显示的实验室

      labNumber: 0,

      column1: [

        {

          title: '编号',
          key: 'index',
          align: 'center',
          width: 100
        },

        {

          title: '建筑',
          key: 'lab',
          align: 'center'

        }

      ],

      // 仪器表格
      equipmentData: [], // 总仪器
      showEquip: [], // 每页显示的仪器

      equipmentNumber: 0,

      column2: [

        {

          title: '编号',
          key: 'index',
          align: 'center',
          width: 100

        },

        {

          title: '房间',
          key: 'equipment',
          align: 'center'

        }

      ]

    }
  },

  mounted () { // 页面加载完成后发送请求
  
    var _this = this

    _this.message = 'roomList'

    this.axios({

      method: 'post',

      url: 'http://localhost:8080/admin/listBuildingsAndRooms',

      headers: {

        'Content-Type': 'application/json;charset=utf-8',
        'Token': this.$store.state.token

      },

      data: {

        message: _this.message

      },

      responseType: 'json' // 默认如此

    }).then((res) => {

      console.table(res.data);

      var resData = res.data.info;

      _this.container = res.data.info;

      // console.log(_this.container);

      _this.labNumber = resData.length;

      for (var i = 0; i < resData.length; i++) {

        _this.labData.push({

          index: resData[i].bId,
          lab: resData[i].buildingName

        })

      }

      if (_this.labNumber <= _this.pageSize) { // 实验室数 <= 每页容量

        _this.showLab = _this.labData;

      } else { // 实验室数 > 每页容量

        _this.showLab = _this.labData.slice(0, _this.pageSize);

      }

      for (var j = 0; j < resData[0].rooms.length; j++) { // 将仪器表初始化为一个实验室对应的仪器表

        _this.equipmentData.push({

          index: j + 1,
          equipment: resData[0].rooms[j]

        })
      }

      _this.equipmentNumber = resData[0].rooms.length

      if (_this.equipmentNumber <= _this.pageSize) { // 第一个实验室对应的仪器数量 <= 每页容量

        _this.showEquip = _this.equipmentData.slice(0, _this.equipmentNumber)

      } else { // 仪器数量 > 每页容量

        _this.showEquip = _this.equipmentData.slice(0, _this.pageSize)

      }

    }).catch((err) => {

      console.log('发生错误：error.data :' + err.data);

    })

  },

  methods: {

    parentTime(payload) { // 子传父

      this.startTime = payload.val1;
      this.endTime = payload.val2;

    },
    next () { // 预约

      var _this = this;

      this.axios({

        method: 'post',

        url: 'http://localhost:8080/admin/isConflicting',

        headers: {

        'Content-Type': 'application/json;charset=utf-8',
        'Token': this.$store.state.token

        },

        data: {

          startTime: _this.startTime,
          endTime: _this.endTime,
          roomName: _this.roomName

        },

        responseType: 'json'

      }).then((res) => {
        
        var info = res.data.info;
        var code = res.data.code;

        console.log(res.data);

        if (code == 200) {

          // this.$Message.info('预约成功!');
          // this.$router.replace('/managerOrder');

          this.addItem();

        } else if (code == 400) {
          
          var len = info.contradictDates.length;

          var conDates = info.contradictDates;

          console.table(conDates);

          console.log(conDates[0].conflictingTimes);

          for (var i = 0; i < len; i++) {
            this.conflictingTimes.push(conDates[i].conflictingTimes);
          }

          this.modal1 = true;

        } else {

          this.$Message.error("系统错误!");

        }

      })

    },

    suspend () { // 取消预约

      this.$Modal.confirm({

        title: '警告',
        content: '您确定要取消预约吗?',

        style: '',
        closable: true,
        
        onOk: () => { // 确认键
        
          this.$Message.info('取消成功！');
          this.$router.replace('/ManagerOrder');
        
        },
        
        onCancel: () => { // 取消键

        } 
    
      })
    
    },


    changePageLab (index) { // 分页Page组件, 控制Lab
    
      var start = (index - 1) * this.pageSize;
      var end = index * this.pageSize;
      this.showLab = this.labData.slice(start, end);
    
    },


    changePageEquip (index) { // 分页Page组件, 控制Equip
    
      var start = (index - 1) * this.pageSize;
      var end = index * this.pageSize;
      this.showEquip = this.equipmentData.slice(start, end);
    
    },

    clickLab (data, index) {
    
      this.checkedLab = data.lab;
      this.labIndex = index;
      this.currentPage = 1;

      this.equipmentData.splice(0, this.equipmentData.length); // 每次push前清空数组元素

      for (var j = 0; j < this.container[index].rooms.length; j++) {
    
        this.equipmentData.push({

          index: j + 1,
          equipment: this.container[index].rooms[j]

        })
    
      }

      this.equipmentNumber = this.container[index].rooms.length;

      if (this.equipmentNumber <= this.pageSize) { // 每个实验室对应的仪器数量 <= 每页容量
    
        this.showEquip = this.equipmentData;
    
      } else { // 仪器数量 > 每页容量
    
        this.showEquip = this.equipmentData.slice(0, this.pageSize);
    
      }
    
    },

    clickEquip (data, index) {
    
      this.checkedEquipment = data.equipment;
      this.equipIndex = index;

      this.roomName = this.checkedEquipment;  // 用于传参
    
    },

    changeColorLab (row, index) {
    
      if (index === this.labIndex) {
    
        return 'demo-table-info-row';
    
      }
    
    },

    changeColorEquip (row, index) {
    
      if (index === this.equipIndex) {
    
        return 'demo-table-info-row';
    
      }
    
    },


    addItem () {

      var _this = this;

      this.$Modal.confirm({

        title: '添加会议',
        okText: '添加',
        // loading: true,
        render: (h) => {

          return h('Input', {

            props: {

              value: _this.confName,
              placeholder: '请输入会议名称',
              autofocus: true,

            },

            on: {

              input: (val) => {

                _this.confName = val;

              },

            }

          })

        },
        onOk:() =>{

          if(_this.confName != null) {

            this.axios({

              method: 'post',

              url: 'http://localhost:8080/admin/saveConference',

              headers: {

                'Content-Type': 'application/json;charset=utf-8',
                'Token': this.$store.state.token

              },

              responseType: 'json',

              data: {

                confName: _this.confName,
                roomName: _this.roomName,
                startTime: _this.startTime,
                endTime: _this.endTime,
                userAccount: _this.$store.state.userAccount

              }
            }).then((res) => {

                var info = res.data.info;
                var code = res.data.code;

                console.log(res.data);

                if (code == 200) {

                  this.$Message.info('预约成功!');
                  this.$router.replace('/managerOrder');

                } else if (code == 400) {

                  this.$Message.error('添加失败!');

                } else {

                  this.$Message.error('未知错误!');

                }
            
            })

          } else {

            console.log("----------------------confName: " + _this.confName);
            
            this.$Message.error('会议名不能为空！');
          
          }

        }

      })

    },

  }

}

</script>

<style scoped lang="less">

.next Button {

    position: absolute;
    left: 700px;
    top: 450px;
    width: 130px;
    height: 40px;
    font-size: 17px;
}

.cancel Button {

    position: absolute;
    left: 850px;
    top: 450px;
    width: 130px;
    height: 40px;
    font-size: 17px;

}

.mainstyle {

    position: absolute;
    left: 0px;
    top: 0px;
    width: 100%;
    height: 110%;
    background-image: url(../assets/bg.jpg);
    background-repeat: no-repeat;
    background-size: cover;
    text-align: center;
    // background: -webkit-linear-gradient( rgb(102, 176, 236) ,rgb(179, 200, 211)); /* Safari 5.1 - 6.0 */
    // background: -o-linear-gradient( rgb(102, 176, 236), rgb(179, 200, 211)); /* Opera 11.1 - 12.0 */
    // background: -moz-linear-gradient(rgb(102, 176, 236),rgb(179, 200, 211)); /* Firefox 3.6 - 15 */
    // background: linear-gradient( rgb(59, 158, 238) , rgb(94, 180, 226)); /* 标准的语法 */

}

.corresponding {

    position: absolute;
    font-size: 20px;
    color:white;
    left: 687px;
    top: 80px;

}

.box {

    position: absolute;
    left: 268px;
    top: 125px;
    width: 1000px;
    height: 560px;
    background: #fff;

}

.page {

    position: absolute;
    top: 362px;
    width: 160px;
    height: 36px;
    left: 155px;

}

.lab {

    position: absolute;
    left: 0px;
    top: 0px;
    width: 500px;
    height: 400px;
    background: #fff;

}

.equipment {

    position: absolute;
    right: 0px;
    top: 0px;
    width: 500px;
    height: 400px;
    background: #fff;

}

.ivu-table .demo-table-info-row td{

    background-color: #2db7f5;
    color: #fff;

}

.modal_1 {

    position: relative;
    font-size: 18px;
    left: 30px;
}

</style>
