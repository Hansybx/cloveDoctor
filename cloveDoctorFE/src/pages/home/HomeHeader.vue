<template>
    <el-row>
        <el-col :span="8">
            <div class="logo-container">
                <img class="logo" src="../../assets/pharmacyLogo.png" />
                <el-button @click="toHome" class="text-container" type="text">茯苓药店</el-button>
            </div>
        </el-col>
        <el-col class="logo-container" :span="4">
            <el-button @click="toShopCenter" class="text-container" type="text" :icon="Goods">药品中心</el-button>
        </el-col>
        <el-col class="logo-container" :span="4">
            <el-button @click="toMsg" class="text-container" type="text" :icon="Message">我的消息</el-button>
        </el-col>
        <el-col class="logo-container" :span="4">
            <!-- 购物车内商品降价通知 -->
            <el-button @click="toCart" class="text-container" type="text" :icon="ShoppingCart">购物车</el-button>
        </el-col>
        <el-col v-if="user.userId < 0" class="logo-container" :span="4">
            <el-button @click="toLogin()" class="text-container" type="text" :icon="User">登录</el-button>
        </el-col>
        <el-col v-else class="logo-container" :span="4">
            <el-dropdown @command="handleCommand">
                <el-button class="text-container" type="text" :icon="User">
                    {{ user.userName }}
                    <el-icon class="el-icon--right">
                        <arrow-down />
                    </el-icon>
                </el-button>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item command="/user/order">我的订单</el-dropdown-item>
                        <el-dropdown-item
                            v-if="user.userType === admin"
                            command="/admin"
                        >管理系统</el-dropdown-item>
                        <el-dropdown-item command="/">退出</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </el-col>
    </el-row>
</template>

<script setup lang='ts'>
import { Message, ShoppingCart, User, Goods, ArrowDown } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router';
import { useUserStore } from '../../stores/UserInfo';
const router = useRouter()
const user = useUserStore();
const admin = 'ADMIN'

function toHome(): void {
    router.push('/home');
}

function toShopCenter() {
    router.push('/shopping');
}

function toMsg(): void {
    router.push('/message');
}

function toCart(): void {
    router.push('/cart')
}

function toLogin(): void {
    router.replace('/login');
}

const handleCommand = (command: string) => {
    if (command === '/') {
        user.$reset()
    }
    router.replace(command)
}

</script>

<style scoped>
.bg-purple {
    background: #d3dce6;
}

.grid-content {
    border-radius: 4px;
    min-height: 36px;
}

.text-container {
    font-size: 20px;
    color: #000;
    /* min-height: 36px; */
    /* display: flex;
    align-items: center;
    justify-content: center; */
}

/* .icon-container {
    height: 20px;
} */

/* .header-container {
    height: 150px;
} */

.logo-container {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 400px;
}

.logo {
    width: 100px;
    height: 100px;
}
</style>
