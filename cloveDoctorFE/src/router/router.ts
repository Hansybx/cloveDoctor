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
                path: '/message',
                component: () => import("../pages/message/MessagePage.vue"),
            }
        ]
    },
    {
        path: '/login',
        name: 'login',
        component: () => import("../pages/login/Login.vue"),
    },
    {
        path: '/register',
        name: 'register',
        component: () => import("../pages/register/Register.vue"),
    },
   
]

const router = createRouter({
    // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

export default router;