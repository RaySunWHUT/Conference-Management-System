<template>
    <div class="dialog-cover">
        <div class = "dialog-input">
            <strong class="word">您确定要删除{{parentToChild.paramName}}吗?</strong>

            <div class="close_button">
                <Icon type="md-close" size="21" style="cursor: pointer" @click="cancel"/>
            </div>

            <div class="area_warn">
                该操作是<strong>不可恢复</strong>的，这将删除{{parentToChild.paramName}}下<strong>所有</strong>实验数据和预约
                <br>记录。
                <br>
                请输入"<strong>确认删除</strong>"确认此操作
            </div>
            <div class="sure_position1">我</div>
            <Input class="bl" size="large" v-model="value" placeholder="确认删除"/>
            <div class="sure_position2">此样品</div>
            <Button class="mk" @click="add">确定</Button>

        </div>
    </div>
</template>

<script>
export default {

  props:['parentToChild'], // 删除样品名

  data () {
    return {

      value: '',
    }
  },

  methods: {

    cancel () {
      this.$emit('delItem', 'fail')
    },

    add () {

      if (this.value == '确认删除') {
        this.axios({

          method: 'post',

          url: 'http://192.168.43.17:8080/user/deleteParam',

          headers: {

            'Content-Type': 'application/json;charset=utf-8'

          },

          data: {
            coreId: this.parentToChild.coreId
          },

          responseType: 'json'
        }).then((response) => {
          console.log(response.data)
          if (response.data.info == 200) {
            this.$emit('delItem', 'success')
            console.log('Delete successful!!!')
            this.$Message.success('删除成功')
          } else if (response.data.info == 400) {
            this.$emit('delItem', 'fail')
            this.$Message.error('删除失败！')
            console.log('Delete failed!')
          }
        })
        // this.$router.replace('/order')
      }
    }

  }

}
</script>

<style scoped lang="less">

Button {

    width: 70px;
    height: 38px;
    color: white;
    font-size: 18px;
}

.dialog-cover {
    background: rgba(94, 91, 91, 0.8);
    position: fixed;
    z-index: 13;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;

}

.dialog-input {

    position: fixed;
    z-index: 11;
    top: 225px;
    left:600px;
    width: 500px;
    height: 256px;
    // border: 1px solid black;
    border: 0px;
    background: rgb(255, 255, 255);
    border-radius: 15px;
    text-align: center;
    overflow: hidden;

}

.word {

    position: absolute;
    top: 6px;
    left: 33px;
    width: 400px;
    text-align: left;
    font-size: 18px;

}

.close_button {

    position: absolute;
    top: 4px;
    left: 469px;
    color: rgba(36, 26, 7, 0.658);

}

.close_button:hover {

    color: rgb(119, 119, 226);

}

.area_warn {

    position: absolute;
    top: 45px;
    left: 33px;
    width: 480px;
    font-size: 16px;
    text-align: left;
}

.bl {

    position: absolute;
    top: 154px;
    left: 60px;
    width: 75px;
    height: 40px;

}

.sure_position1 {

    position: absolute;
    top: 159px;
    left: 40px;
    font-size: 17px;

}

.sure_position2 {

    position: absolute;
    top: 159px;
    left: 140px;
    font-size: 17px;
}

.mk {

    position: absolute;
    top: 205px;
    left: 30px;
    width: 440px;

}

.ivu-btn-default {

    color: rgb(226, 41, 41);
    border-color: rgb(226, 41, 41);
    border: solid 1px;

}

</style>
