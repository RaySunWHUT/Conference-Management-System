import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import VueAxios from 'vue-axios'
import Qs from 'qs'
import iView, { TabPane } from 'iview'
import 'iview/dist/styles/iview.css' // 很重要, 必须加

import storage from './utils/storage'

// require('./utils/mock')

Vue.config.productionTip = false

Vue.use(VueAxios, axios)
Vue.use(iView)

router.beforeEach((to, from, next) => {

    if (to.fullPath === '/') { // "/"为login路径

        next()

    } else {

        var token = storage.get('token')
        console.log('token', token)

        if (token != null) {

            next()

        } else {

            next('*')

        }

    }

})


new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')