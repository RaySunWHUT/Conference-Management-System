<template>
  <div class="dialog-cover mainstyle">

    <div class="dialog-content">

        <div class="conf">
            <div class="cid">会议编号: {{ confId }}<br/></div>
            会议名称：<Input v-model="confName" placeholder="Enter something..." style="width: 300px" /><br/>
            <div class="broom">
              <!-- Select的v-model绑定若要生效, 需要让v-model与Option的value中的值相匹配才行 -->
              建筑：<Select v-model="buildingName" style="width:200px" @on-change="clickBuild" :label-in-value="false">
                      <Option v-for="item in buildings" :value="item.buildingName" :key="item.buildingName">
                        {{ item.buildingName }}
                      </Option>
                  </Select><br/>

              房间：<Select v-model="roomName" style="width:200px" @on-change="clickRoom" :label-in-value="false">
                      <Option v-for="item in rooms" :value="item.roomName" :key="item.roomName">
                        {{ item.roomName }}
                      </Option>
                  </Select><br/>
            </div>
        </div>

        <div class="sureButton">
          <Button type="primary" shape="circle" @click="update" long>确认修改</Button>
        </div>

        <div class="removeButton">
          <Button type="error" shape="circle" @click="dialogRemove" long>取消</Button>
        </div>

        <div class="date">
          <Date @getTime="parentTime" :times="times"></Date>
        </div>

    </div>

  </div>
</template>

<script>

import Date from '@/components/Date.vue'

export default {

  name: 'formInfo',

  inject:['reload'],

  props: {

    confer: {

      confId: Number,
      confName: String,
      startTime: String,
      endTime: String,
      buildingName: String,
      roomName: String
    
    }

  },

  components: {

    Date

  },

  create() {

    this.ready();
  
  },

  data() {

    return {

      times: {

        startTime: null,
        endTime: null,
      
      },
      
      confId: null,
      confName: null,
      buildingName: null,
      roomName: null,
      message: '',
      buildings: [],
      rooms: [],
      startTime: null,
      endTime: null
      
    }

  },


  mounted () {

    this.getForm();

    this.ready();

  },

  methods: {

    parentTime(payload) { // 子传父

      this.startTime = payload.val1;
      this.endTime = payload.val2;

    },

    getBuildIndex(buildingName) { // 根据buildingName获取建筑编号

        for (var i = 0; i < this.buildings.length; i++) {

            if (this.buildings[i].buildingName == buildingName) {

                  return this.buildings[i].bId;

            }

        }

    },

    clickBuild(data) {

      var buildingName = data;
      
      var index = this.getBuildIndex(buildingName);

      index = index - 1;  // bId - 1, 得到index

      var vRoom = this.buildings[index].rooms;

      this.rooms.length = 0;  // 清空rooms

      for (var i = 0; i < vRoom.length; i++) {
        
          this.rooms.push({
            roomName: vRoom[i]
          });

      }

      this.roomName = this.rooms[0].roomName;

    },


    clickRoom(data) {

      this.roomName = data;
      
    },

    getForm () {

      var _this = this;

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

      }).then(res => {
        
        var info = res.data.info;

        var code = res.data.code;

        var len = info.length;

        if (code == 200) {

          for (var i = 0; i < len; i++) {

            _this.buildings.push({

              bId: info[i].bId,
              buildingName: info[i].buildingName,
              rooms: info[i].rooms

            });

          }

        }

      }).catch(err => {

        console.log('error.data :' + err.data);

      });

    },

    dialogRemove () {
      
      this.$router.replace('/managerOrder');
    
    },

    update () {

      var _this = this;

      var confSpace = this.confName.split(" ").join("").length;

      if (this.confName == '' || this.startTime == '' || this.endTime == '' || confSpace == 0) {

         this.$Message.warning("会议名称不能为空！");

      } else {
        
          this.axios({

          method: 'post',

          url: 'http://localhost:8080/admin/updateConference',

          headers: {

            'Content-Type': 'application/json;charset=utf-8',
            'Token': this.$store.state.token

          },

          data: {

            confId: _this.confId,
            confName: _this.confName,
            startTime: _this.startTime,
            endTime: _this.endTime,
            roomName: _this.roomName

          },

          responseType: 'json' // 默认如此

        }).then(res => {
          
          var info = res.data.info;
          var code = res.data.code;

          if (code == 200) {

            this.$router.replace('/managerOrder');
            this.$Message.success('修改成功！');
            this.reload();  // 刷新界面

          } else {
            
            this.$Message.error('修改失败！');

          }

        }).catch(err => {

          this.$Message.error('系统错误！');

        })

      }
    
    },

    ready() {

      this.confId = this.confer.confId;
      this.confName = this.confer.confName;
      this.buildingName = this.confer.buildingName;
      this.roomName = this.confer.roomName;

      // 有些不优雅, 有时间改
      this.startTime = this.times.startTime = this.confer.startTime;
      this.endTime = this.times.endTime = this.confer.endTime;

      // 赋予初值
      this.rooms.push({
        roomName: this.roomName
      });

    }

  }

}
</script>


<style scoped lang="less">

.dialog-content {

  position: fixed;
  z-index: 11;
  top: 100px;
  left: 25%;
  width: 50%;
  height: 600px;
  border: 1px solid black;
  background: white;
  border-radius: 20px;
  overflow: hidden;

}

.dialog-cover {

  background: rgba(0, 0, 0, 0.8);
  position: fixed;
  z-index: 5;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;

}

.removeButton {

  position: absolute;
  z-index: 10;
  top: 495px;
  left: 55%;
  width: 100px;
  height: 56px;

}

.sureButton {

  position: absolute;
  z-index: 10;
  top: 495px;
  left: 27%;
  width: 100px;
  height: 56px;

}

.date {
  
  position: absolute;
  margin-top: -40px;
  margin-left: -45px;

}

.conf {
  
  position: absolute;
  margin-top: 40px;
  margin-left: 80px;
  line-height: 4em;

}

.cid {
  position: relative;
  margin-top: 40px;
  margin-left: -290px;
  line-height: 5em;
}

.broom {
  position: relative;
  margin-left: -130px;
  line-height: 5em;
}


</style>
