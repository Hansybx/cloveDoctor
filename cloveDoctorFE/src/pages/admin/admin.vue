<template>
    <div class="title-container">
        <div class="title">茯苓药店管理系统</div>
        <el-button class="text-btn" type="text" @click="toHome">返回商城</el-button>
    </div>

    <el-container>
        <el-aside width="200px">
            <AdminSidebar />
        </el-aside>
        <el-main>
            <router-view></router-view>
        </el-main>
    </el-container>
</template>

<script setup lang='ts'>
import router from '../../router/router';
import AdminSidebar from './adminSidebar.vue';

const toHome = () => {
    router.push('/')
}
</script>

<style scoped>
.title-container {
    display: flex;
    background-color: #409eff;
    justify-content: space-around;
    align-items: center;
}
.title {
    font-size: 20px;
    min-height: 10vh;
    line-height: 10vh;
    padding-left: 10px;
    text-align: left;
    letter-spacing: 8px;
    color: #fff;
}

.text-btn {
    font-size: 20px;
    color: #fff;
}
</style>
