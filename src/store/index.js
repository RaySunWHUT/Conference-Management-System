import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({

    state: { // 数据保存在state内，在任何组件内通过this.$store.state.XX来读取

        data: {

        },

        token: null,

        user: {

            userAccount: null,
            password: null,
            role: null,
            status: null

        }

    },

    mutations: { // 用来直接修改state内的数据；在组件内，通过·this.$store.commit(方法名)来执行mutations

        setLabValue(state, value) { // 将实验室数据存入data
            state.data.Lab = value
        },

        setEquipmentValue(state, value) { // 将仪器存入data
            state.data.equipments = value
        },

        setExperimentValue(state, value) {
            state.data.experiments = value
        },

        setToken(state, value) {
            state.token = value
        },

        createUser(state, payload) {
            state.user.userAccount = payload.userAccount
            state.user.password = payload.password

            if (state.token === null) { // token失效，status == 0
                state.user.status = 0
            } else { // token存在, status == 1
                state.user.status = 1
            }
        }

    },

    actions: { // 提交的是mutation, 并且可以异步操作
        // action在组件内通过this.$store.dipatch(方法名)来触发
        // 作用：存在业务逻辑

    },

    getters: { // 将各个组件中的computed中的方法提取出来

    },

    modules: { // 用来将store分割到不同模块

    }

})