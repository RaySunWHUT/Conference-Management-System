<!-- 登陆界面 -->
<template>
    <div class="all">

        <!-- logo -->
        <div class="picture"></div>

        <div class="mydiv">

            <div class="headline">
                会议管理系统
            </div>

            <!-- 用户名、密码输入 -->
            <div class="input">

                <div class="user_id">
                    用户名:
                </div>

            <Input type="text" prefix="ios-contact" v-model="userAccount" class="input_style" /><br/>

            <div class="user_pass">
                密码:
            </div>

            <Input type="password" prefix="ios-star" v-model="password" class="input_style" /><br/>

            </div>

        <div class="forget" @click="forgetPassword()">
                忘记密码?
        </div>

        <router-view></router-view>

        <div class="button">

            <button @click="login">登&nbsp;&nbsp;&nbsp;录</button>

        </div>

    </div>

</div>
</template>

<script>

import storage from '../utils/storage'

export default {

  name: 'login',

  data () {
    return {

      show: null,
      userAccount: '',
      password: ''

    }
  },

  created: function () {

    var _this = this

    document.onkeydown = function (e) {
      
      let key = window.event.keyCode

      if (key == 13) {

        _this.login()
      
      }
    
    }
  
  },

  methods: {
    
    forgetPassword () {
    
      alert('请联系管理员！')
    
    },

    login: function () {

      var _this = this

      _this.message = 'login'

      this.axios({

        method: 'post',

        url: 'http://localhost:8080/login',

        headers: {

          'Content-Type': 'application/json;charset=utf-8'

        },

        data: {

          userAccount: _this.userAccount,
          password: _this.password

        },

        responseType: 'json'

      }).then(function (response) {
        
        // console.table(response.data);

        var info = response.data.info;

        var code = response.data.code;

        if (code == 200) {

          var token = info;

          // var user = {

          //   'userAccount': _this.userAccount,
          //   'password': _this.password

          // }

          storage.set('token', token);

          _this.$store.commit('setToken', token);

          // storage.set('user', user);

          // _this.$store.commit('createUser', user);

          storage.set('userAccount', _this.userAccount);

          _this.$store.commit('setUserAccount', _this.userAccount);

          storage.set('password', _this.password);

          _this.$store.commit('setPassword', _this.password);

          _this.$router.replace('/main');
          _this.$Message.success('登录成功');

        } else if (code == 400 && info == 'incorrect') {

          _this.$Message.error('用户名或密码输入错误！');

        } else {

          // console.table(response.data);

          _this.$Message.error('用户名或密码输入错误！');

        }

      }).catch(function (error) {

          _this.$Message.error('系统错误！');

      })

    }

  },

  computed: {

  }

}
</script>

<style lang="less" scoped>


.all {

    position: relative;
    // top: 0;
    // left: 0;
    // width: 100%;
    // height: 100%;
    // // background-image: url('../assets/loginbg.jpg');
    // background-color: #2d3a4b;
    // background-repeat: no-repeat;
    // background-size: cover;
    height: 100vh;
    background-color: #2d3a4b;

}

.picture {

    position: absolute;
    left: 590px;
    top: 90px;
    background-image: url('../assets/whut.png');
    width: 300px;
    height: 66px;

}

.mydiv {

    position: absolute;
    top: 175px;
    left: 550px;
    background: rgb(255, 255, 255);
    width: 400px;
    height: 360px;

}

.headline {

    position: relative;
    top: 20px;
    text-align: center;
    font-size: 22px;
    letter-spacing: 2px;

}

*{

    padding: 0;
    margin: 0;

}

.forget {

    position: relative;
    left: 160px;
    text-align: center;
    width: 80px;
    top: -10px;
    font-size: 15px;
    color: black;

}

.forget:hover {

    color: red;
    cursor: pointer;

}

.user_id {

    position: relative;
    left: 10px;
    top: 0px;

}

.user_pass {

    position: relative;
    left: 10px;
    top: 0px;

}

.button button {

    position: relative;
    left: 120px;
    top: 15px;
    width: 160px;
    height: 36px;
    font-size: 18px;
    background: rgb(51, 152, 235);
    color: white;
    border: 0px;
    border-radius: 3%;
    cursor: pointer;

}

.button button:hover {

    color: aliceblue;

}

.input {

    position: relative;
    left: 50px;
    top: 50px;
    width: 300px;
    height: 200px;
    font-size: 15px;

}

.input_style {

    width: 300px;
    height: 40px;

}

.errorTip {
    position: absolute;
    top: 200px;
    left: 50px;
    font-size: 14px;
    letter-spacing: 2px;
    color: red;
}


</style>
