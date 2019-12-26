<template>
    <div class="mainstyle">
        <div class="selfs">
            <div class="title">
                标题: <Input v-model="title" placeholder="Enter something..." style="width: 300px"/><br/>
            </div>

            <div class="content">
                <div class="con">内容: </div>
                <textarea style="width: 500px; height: 300px" spellcheck="false" v-model="content">
                    
                </textarea>
            </div>

            <div class="lastTime">
                <!-- updateTime： -->
                {{ updateTime }}
            </div>

            <div class="sureButton">
            <Button type="success" shape="circle" @click="sure" long>{{ operator }}</Button>
            </div>

            <div class="removeButton">
            <Button type="error" shape="circle" @click="suspend" long>取消</Button>
            </div>

        </div>

    </div>

</template>

<script>

export default {

  name: 'uploadSummary',

  inject:['reload'],

  data () {

    return {

        sumId: null,

        confId: null,

        title: null,

        content: null,

        updateTime: null,

        operator: 'operator',

        symbol: "暂无",

    }

  },

  watch: {

    //   summary: {

    //     handler(newObj, oldObj) {   

    //         this.summary = newObj;

    //     },

    //     deep: true,
    //     // immediate: true,

    //   }
  
  },


  mounted() {

      this.ready();
  
  },


  methods: {

    ready() {

        console.log("-------------- ready --------------");

        this.sumId = this.$store.state.sumId;
        this.title = this.$store.state.title;
        this.content = this.$store.state.content;
        this.updateTime = this.$store.state.updateTime;
        this.confId = this.$store.state.confId;

        console.log(this.sumId);

        console.log(this.confId);

        console.log("-------------- ready end --------------");

        if (this.sumId != this.symbol) { // 有sumId, 更新

            this.operator = "更新";

        } else {   // 无sumId, 上传
            
            this.operator = "上传";

        }

    },

    sure () {

        console.log("---------- " + this.operator + " 开始 ----------");

        console.log("confId: " + this.confId);

        console.log("title: "  + this.title);

        console.log("content:" + this.content);

        console.log("this.sumId" + this.sumId);

        console.log("this.symbol: " + this.symbol);

        console.log("------------------- operator结束 ------------------");

        if (this.sumId == this.symbol) {    // 无sumId, 上传

            console.log("无sumId, 上传; 进入");

            this.axios({

                method: 'post',

                url: 'http://localhost:8080/admin/saveSummary',

                headers: {

                    'Content-Type': 'application/json;charset=utf-8',
                    'Token': this.$store.state.token

                },

                data: {

                    title: this.title,
                    content: this.content,
                    confId: this.confId

                },

                responseType: 'json' // 默认如此

                }).then((res) => {

                var info = res.data.info;
                var code = res.data.code;

                console.log("---------------无sumId/上传 info start ---------------");

                console.table(res);

                console.table(info);
                
                console.log(code);

                console.log("---------------无sumId/上传 info end ---------------");


                if (code == 200) {

                    this.$Message.success("上传成功");
                    this.$router.replace('/managerSummary');
                    this.reload();
                
                }

                }).catch((err) => {

                    this.$Message.error("error.data");

            });

        } else {    // 更新
            
            console.log("有sumId, 进入");

            this.axios({

                method: 'post',

                url: 'http://localhost:8080/admin//updateSummary',

                headers: {

                    'Content-Type': 'application/json;charset=utf-8',
                    'Token': this.$store.state.token

                },

                data: {

                    title: this.title,
                    content: this.content,
                    sumId: this.sumId,
                    confId: this.confId

                },

                responseType: 'json' // 默认如此

                }).then((res) => {

                var info = res.data.info;
                var code = res.data.code;

                console.log("--------------- 有sumId info start ---------------");

                console.table(res);

                console.table(info);
                
                console.log(code);

                console.log("--------------- 有sumId info end ---------------");

                if (code == 200) {

                    this.$Message.success("更新成功！");
                    this.$router.replace('/managerSummary');
                    this.reload();
                
                }

                }).catch((err) => {

                    this.$Message.error("error.data");

            });

        }

        

    },
    suspend () { // 取消编辑纪要

      this.$Modal.confirm({

        title: '警告',
        content: '您要取消编辑吗? 已编辑的内容将不会保存',

        style: '',
        closable: true,
        
        onOk: () => { // 确认键
        
          this.$router.replace('/ManagerSummary');
        
        },
        
        onCancel: () => { // 取消键

        } 
    
      })
    
    },

  }

}

</script>

<style scoped lang="less">

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

}

.selfs {

    position: relative;
    top: 80px;
    height: 99vh;

}


.title {

    position: absolute;
    top: 40px;
    left: 460px;

}

.content {

    position: absolute;
    top: 120px;
    left: 510px;

}

.content textarea {

    resize: none;
    word-break: break-all;
    border: 1px solid gray;
    border-radius: 1%;
    font-weight: bold;
    font-size: 15px;
    font-family: 'Courier New', Courier, monospace;
    background-color: aliceblue;

}


.con {

    position: absolute;
    top: 0px;
    left: -92px;
    font-size: 25px;
    color: white;
    
}

.lastTime {

    position: absolute;
    top: 440px;
    // left: 700px;
    left: 800px;
    font-size: 20px;
    color: white;

}

.sureButton {

  position: absolute;
  z-index: 10;
  top: 515px;
  left: 32%;
  width: 100px;
  height: 56px;

}

.removeButton {

  position: absolute;
  z-index: 10;
  top: 515px;
  left: 52%;
  width: 100px;
  height: 56px;

}

</style>
