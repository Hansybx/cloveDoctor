<template>
    <el-row>
        <el-col :span="12">
            <div class="logo-container">
                <img class="logo" src="../../assets/pharmacyLogo.png" />
                <!-- <span class="text-container">茯苓药店</span> -->
                <el-button @click="toHome" class="text-container" type="text">茯苓药店</el-button>
            </div>
        </el-col>
        <el-col class="logo-container" :span="4">
            <!-- <div class="logo-container">
                <el-icon :size="26" class="icon-container">
                    <message />
                </el-icon>
                <span @click="toMsg" class="text-container">我的消息</span>
            </div>-->
            <el-button @click="toMsg" class="text-container" type="text" :icon="Message">我的消息</el-button>
        </el-col>
        <el-col class="logo-container" :span="4">
            <!-- 购物车内商品降价通知 -->
            <!-- <div class="logo-container">
                <el-icon :size="26" class="icon-container">
                    <shopping-cart />
                </el-icon>
                <span class="text-container">购物车</span>
            </div>-->
            <el-button @click="toCart" class="text-container" type="text" :icon="ShoppingCart">购物车</el-button>
        </el-col>
        <el-col class="logo-container" :span="4">
            <!-- <div class="logo-container">
                <el-icon :size="26" class="icon-container">
                    <user />
                </el-icon>
                <span class="text-container">登录</span>
            </div>-->
            <el-button @click="toLogin" class="text-container" type="text" :icon="User">登录</el-button>
        </el-col>
    </el-row>
</template>

<script setup lang='ts'>
import { Message, ShoppingCart, User } from '@element-plus/icons-vue'
import router from '../../router/router';

function toHome(): void {
    router.push('/home');
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
</script>

<style>
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
