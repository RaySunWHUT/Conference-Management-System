<template>
    <div class="dialog-cover mainstyle">
        <div class="dialog-content">
            <div class="k">
                <div class="c" v-for="(value, key, index) in FormData" :key="index">
                    <Form :model="formTop" label-position="top">
                            <FormItem label="">
                                {{ key }} :
                                <Input v-bind="value" placeholder="input your parameter......" />
                            </FormItem>
                    </Form>
                </div>
            </div>

            <div class="sureButton">
                <Button type="primary" shape="circle" @click="clickData" long>确定</Button>
            </div>

            <div class="removeButton">
                <Button type="error" shape="circle" @click="dialogRemove" long>取消</Button>
            </div>
        </div>

    </div>

</template>

<script>
export default {

  data () {
    return {

      FormData: {},

      testData: [

        {

          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime2: '300'

        },

        {

          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'

        },

        {

          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'

        },

        {

          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'

        },

        {

          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'

        },

        {

          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'

        },

        {

          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'

        },

        {
          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'

        },

        {
          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'
        },

        {
          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'
        },

        {
          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'
        },

        {
          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'
        },

        {

          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'

        },

        {
          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'
        },

        {
          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime: '300'
        },

        {
          temperatureLow: '600',
          temperatureHigh: '1900',
          voltage: '60',
          heatingRate: '100',
          holdingTime: '20',
          popolarizationTime1: '300'
        }

      ]

    }
  },
  methods: {

    dialogRemove () { // 取消
      this.$router.replace('/orderInfo')
    },
    clickData () { // 确定

      this.$emit('')
      this.$router.replace('/managerOrder')
      this.$Message.success('预约成功！')
      
    }

  },

  created () {
    this.FormData = this.testData[0]
  }

}
</script>

<style scoped lang="less">

.mainstyle  {

    text-align: left;

}

.dialog-content {

    position: fixed;
    z-index: 11;
    top: 75px;
    left: 25%;
    width: 50%;
    height: 600px;
    // border: 1px solid black;
    border: 0px;
    background: white;
    border-radius: 20px;
    overflow: hidden;

}

.dialog-cover {

    background: rgba(219, 218, 218, 0.8);
    position: fixed;
    z-index: 5;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;

}

.removeButton {

    position: absolute;
    z-index: 10;
    top: 520px;
    left: 60%;
    width: 100px;
    height: 56px;

}

.sureButton {

    position: absolute;
    z-index: 10;
    top: 520px;
    left: 27%;
    width: 100px;
    height: 56px;

}

.k {

    position: fixed;
    z-index: 10;
    top: 130px;
    left: 30%;
    width: 40%;
    height: 440px;
    background: white;
    overflow-y: scroll;

}

.c {

    width: 400px;

}

</style>
