import { createRouter, createWebHashHistory } from 'vue-router'
import { useUserStore } from '../stores/UserInfo'
// import Home from '../pages/home/home.vue'

const admin = "ADMIN";

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
                meta: { isLogin: true }
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
                path: '/drug/:id',
                component: () => import("../pages/drug/drug.vue"),
            },
            {
                path: '/user/order',
                component: () => import("../pages/tradeInfo/TradeInfo.vue"),
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
                meta: { isAdmin: true }
            },
            {
                path: '/admin/drugs',
                component: () => import("../pages/admin/drugs/drugs.vue"),
                meta: { isAdmin: true }
            },
            {
                path: '/admin/user',
                component: () => import("../pages/admin/user/user.vue"),
                meta: { isAdmin: true }
            },
            {
                path: '/admin/orders',
                component: () => import("../pages/admin/order/order.vue"),
                meta: { isAdmin: true }
            },
            {
                path: '/admin/main/swiper',
                component: () => import("../pages/admin/swiper/swiper.vue"),
                meta: { isAdmin: true }
            },
            {
                path: '/admin/main/recommend',
                component: () => import("../pages/admin/recommend/recommend.vue"),
                meta: { isAdmin: true }
            },
        ]
    }
]

const router = createRouter({
    // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

router.beforeEach((to, from) => {
    const user = useUserStore();
    
    if (to.meta.isLogin) {
        if (user.userId < 0) {
            router.push('/login')
        }
    }

    if (to.meta.isAdmin) {
        if (user.userType !== admin) {
            return false;
        }
    }
})

export default router;

