<template>
    <div class="home-container">
        <el-container>
            <el-header class="header-container">
                <HomeHeader />
            </el-header>
            <el-main>
                <router-view :key="route.fullPath"></router-view>
            </el-main>
        </el-container>
    </div>
</template>

<script setup lang='ts'>
import { useRoute } from 'vue-router';
import HomeHeader from './HomeHeader.vue';
const route = useRoute()
</script>

<style scoped>
.home-container {
    padding: 0 10vw;
}

.header-container {
    height: 100px;
    padding-left: 0px;
    padding-right: 0px;
}

.el-main {
    padding: 0px;
}
</style>
