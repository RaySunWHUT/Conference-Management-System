<template>
    <div class="dialog-cover">
        <div class = "dialog-input">
            <strong class="word">您确定要删除这条预约记录吗?</strong>

            <div class="close_button">
                <Icon type="md-close" size="21" style="cursor: pointer" @click="cancel"/>
            </div>

            <div class="area_warn">
                该操作是<strong>不可恢复</strong>的！
                <br>
                请点击"<strong>确认删除</strong>", 以确认此操作
            </div>

            <!-- ghost -->
            <Button class="mk" @click="done" >确定删除</Button>

        </div>
    </div>
</template>

<script>
export default {

  data () {

    return {

      item: '',

      url: 'http://localhost:8080/admin/deleteConference'

    }

  },

  methods: {

    cancel () {

      this.$router.replace('/managerOrder')

    },

    done () {
      
      _this = this;

      this.item = this.$route.query.item;

      this.axios({

        method: 'post',

        url: _this.url,

        headers: {

          'Content-Type': 'application/json;charset=utf-8',
          'Token': this.$store.state.token

        },

        data: {
          
          confId: _this.item

        },

        responseType: 'json'

      }).then((response) => {

        console.log(response.data);

        var info = response.data.info;

        var code = response.data.code;

        if (code == 200) {
          
          console.log('Delete successful!!!');

          this.$router.replace('/managerOrder');
          
          this.$Message.success('删除成功');

          // this.$Message.error('发生未知错误')
          // console.log('Unkonwn Error occured!')

        } else {
          
          console.log('Delete failed!');

          this.$Message.error('删除失败！');
          this.$router.replace('/managerOrder');

        }

      })

      // this.$router.replace('/order')

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
    background: rgba(231, 230, 230, 0.8);
    position: fixed;
    z-index: 5;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;

}

.dialog-input {

    position: fixed;
    z-index: 11;
    top: 225px;
    left: 520px;
    width: 500px;
    height: 256px;
    border: 1px solid black;
    // border: 0px;
    background: rgba(255, 255, 255, 0.418);
    border-radius: 15px;
    text-align: center;
    overflow: hidden;

}

.word {

    position: absolute;
    top: 20px;
    left: 33px;
    width: 400px;
    text-align: left;
    font-size: 20px;

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
    top: 75px;
    left: 33px;
    width: 480px;
    font-size: 18px;
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
    top: 175px;
    left: 30px;
    width: 440px;

}

.ivu-btn-default {

    color: rgb(233, 116, 116);
    border-color: antrgba(203,36,49,.4);
    border: solid 1px;
}

</style>
