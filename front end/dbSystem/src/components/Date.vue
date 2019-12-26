<template>
    <div>
        <div class="footer">

            <div class="word">预约时间:</div>

            <div class="footer_no1">
                <DatePicker type="date" :options="options3" @on-change="SelectDate" size="large" placeholder="选择日期" confirm v-model="year"></DatePicker>
            </div>

            <div class="decorate_1">:</div>

            <div class="footer_no2">

                <TimePicker format="HH:mm" :steps="[1, 60, 1]" @on-change = "SelectStartTime" size="large" placeholder="开始时间" v-model="sTime"></TimePicker>

            </div>

            <div class="decorate_2">-</div>

            <div class="footer_no3">

                <TimePicker hide-disabled-options format="HH:mm" :steps="[1, 60, 1]" @on-change="SelectEndTime" :disabled-hours="hours" size="large" placeholder="结束时间" v-model="eTime"></TimePicker>

            </div>

        </div>

    </div>

</template>

<script>
export default {

  props: {

    times: {

      startTime: String,
      endTime: String

    }

  },

  watch: {
    
    times: {

      handler(newObj, oldObj) {

        this.ok = newObj;

        this.passTime = this.ok.startTime.split(" ");
        this.sTime = this.passTime[1];
        
        this.passTime = this.ok.endTime.split(" ");
        this.eTime = this.passTime[1];

        // 赋予年月
        this.year = this.passTime[0];

      },

      deep: true,

      // 此处留记, 需要慎重考虑
      // immediate: true,
      
    },


  },
  
  data () {

    return {

      ok: null,
      
      year: null, // 存储传递年月

      passTime: [], // 暂存

      sTime: null,  // 存储传递起始时间
      eTime: null,  // 存储传递结束时间

      value1: '',

      hours: [],

      Date: {},

      startHour: {},

      endHour: {},
      
      startTime: {},

      endTime: {},

      options3: {

        disabledDate (date) {

          return date && date.valueOf() < Date.now() - 86400000;

        }

      },

      suffix: ":00"

    }

  },

  methods: {

    upward() {  // 向父组件传递
        
      this.$emit('getTime', {
      
        val1: this.startTime,
        val2: this.endTime
        
      })

    },


    SelectDate (e) {

      this.Date = e;

    },


    SelectStartTime (e) { // 选择开始时间

      var time = e;
      this.value1 = time;
      
      var temp = time.split(':');

      var k = temp[0] * 2 / 2 + 1;
      
      var b = temp[1] * 2 / 2;
      
      if (temp[0] < 10) {
      
        this.value1 = '0' + k + ':' + temp[1];
      
      } else {
      
        this.value1 = k + ':' + temp[1];
      
      }
      
      this.startHour = e;

      var arr = time.split(':');
      var hour = arr[0];
      this.hours = [];
      
      for (var i = 0; i <= hour; i++) {
      
        this.hours.push(i)
      
      }

    },


    SelectEndTime (e) { // 选择结束时间

      this.endHour = e;

      this.startTime = this.Date + " " + this.startHour + this.suffix;

      this.endTime = this.Date + " " + this.endHour + this.suffix;

      this.upward();
    
    },
  
  }

}

</script>

<style scoped lang="less">

.footer {

    position: absolute;
    left: 0px;
    top: 445px;
    width: 1000px;
    height: 40px;
    background: none;
    font-size: 15px;

}

.decorate_1 {

    position: absolute;
    left: 418px;
    top: 3px;
    font-size: 18px;
}

.decorate_2 {

    position: absolute;
    left: 540px;
    top: 7px;
    font-size: 15px;
}

.footer_no1 {

    position: absolute;
    left: 200px;

}

.word {

    position: absolute;
    left: 125px;
    top: 5px;

}

.footer_no2 {

    position: absolute;
    right: 462px;
    width: 112px

}

.footer_no3 {

    position: absolute;
    right: 340px;
    width: 112px

}

</style>
