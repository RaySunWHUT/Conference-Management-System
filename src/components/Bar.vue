<!--  点击添加药品后弹出来得界面 确认添加药品 -->
<template>
    <div class="dialog-cover">
        <div class = "dialog-input">
            <div class="bl">
                <Input v-model="value" placeholder="placeholder"/>
                <!-- <span slot="prepend">样品名称</span> -->
            </div>
            <div class="mk">
                <Button type="primary" @click="add">确定</Button>
            </div>
            <div class="mk2">
                <Button type="error" @click="cancel">取消</Button>
            </div>
        </div>
    </div>
</template>

<script>
export default {

  data: function () {
    return {
      value: ''
    }
  },
  props: ['placeholder'],

  methods: {

    cancel () {
      this.$emit('cancel')
    },

    add () {
      this.$emit('getName', this.value)
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
    z-index: 100;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;

}

.dialog-input {

    position: fixed;
    z-index: 11;
    top: 275px;
    left: 35%;
    width: 400px;
    height: 126px;
    // border: 1px solid black;
    border: 0px;
    background: white;
    border-radius: 15px;
    text-align: center;
    overflow: hidden;

}

.bl {

    position: absolute;
    top: 25px;
    left: 30px;
    width: 330px;

}

.mk {

    position: absolute;
    top: 75px;
    left: 98px;

}

.mk2 {

    position: absolute;
    top: 75px;
    left: 220px;

}
</style>
