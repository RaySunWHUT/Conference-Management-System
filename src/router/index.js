import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '@/views/Login'
import Nav from '@/components/Navigation'
import Order from '@/views/Order'
import OrderInfo from '@/views/OrderInfo'
import ManagerOrder from '@/views/ManagerOrder'
import cancelOrder from '@/components/cancelOrder'
import DeleteItem from '@/components/DeleteItem'
import Bar from '@/components/Bar'
import ListInfo from '@/views/ListInfo'
import FormInfo from '@/views/FormInfo'
import ManagerSummary from '@/views/ManagerSummary'
import UploadSummary from '@/views/UploadSummary'

Vue.use(VueRouter)

const routes = [

    // {
    //     path: '/login', // path为/, 对应的组件为首页
    //     name: 'login', // 映射的组件
    //     component: Login
    // },
    {
        path: '/',
        name: 'login',
        component: Login
    },

    {
        path: '/uploadSummary',
        name: 'uploadSummary',
        components: {

            default: UploadSummary,
            nav: Nav

        },

    },

    {
        path: '/managerSummary',
        name: 'managerSummary',
        components: {

            default: ManagerSummary,
            nav: Nav

        },

    },

    {
        path: '/main',
        name: 'main',
        components: {

            default: Home,
            nav: Nav

        }
    },
    {
        path: '/order',
        name: 'order',
        meta: {

            requireAuth: true

        },
        components: {

            default: Order,
            nav: Nav

        },
        children: [{
            path: 'bar',
            component: Bar
        }]
    },

    {
        path: '/orderInfo',
        name: 'orderInfo',
        components: {

            default: OrderInfo,
            nav: Nav

        },
        children: [{
            path: 'listInfo',
            component: ListInfo
        }]
    },

    {
        path: '/managerOrder',
        name: 'managerOrder',
        components: {
            default: ManagerOrder,
            nav: Nav
        },
        children: [{
                path: 'formInfo',
                component: FormInfo
            },
            {
                path: 'cancelOrder',
                component: cancelOrder
            }
        ]

    },
    {
        path: '*', // 访问路径不存在时, 重定向到登录界面
        redirect: '/'
    }

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router