import { createRouter, createWebHashHistory } from 'vue-router'
// import Home from '../pages/home/home.vue'

const routes = [
    { path: '/', redirect: '/home' },
    {
        path: '/home',
        name: 'home',
        component: () => import("../pages/home/Home.vue"),
        children: [
            {
                path: '',
                component: () => import("../pages/home/HomeMain.vue"),
            },
            {
                path: '/cart',
                component: () => import("../pages/cart/cart.vue"),
            },
            {
                path: '/shopping',
                component: () => import("../pages/shoppingCenter/ShoppingCenter.vue"),
            },
            {
                path: '/message',
                component: () => import("../pages/message/MessagePage.vue"),
            },
            {
                path:'/drug/:id',
                component: () => import("../pages/drug/drug.vue"),
            }
        ]
    },
    {
        path: '/login',
        name: 'login',
        component: () => import("../pages/login/Login.vue"),
    },
    {
        path: '/admin',
        name: 'admin',
        component: () => import("../pages/admin/admin.vue"),
        children: [
            {
                path: '',
                component: () => import("../pages/admin/swiper/swiper.vue"),
            },
            {
                path: '/admin/drugs',
                component: () => import("../pages/admin/drugs/drugs.vue"),
            },
            {
                path: '/admin/user',
                component: () => import("../pages/admin/user/user.vue"),
            },
            {
                path: '/admin/orders',
                component: () => import("../pages/admin/order/order.vue"),
            },
            {
                path: '/admin/main/swiper',
                component: () => import("../pages/admin/swiper/swiper.vue"),
            },
            {
                path: '/admin/main/recommend',
                component: () => import("../pages/admin/recommend/recommend.vue"),
            },
        ]
    }
]

const router = createRouter({
    // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

export default router;