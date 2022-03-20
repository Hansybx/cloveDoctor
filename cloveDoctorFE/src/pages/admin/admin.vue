<template>
    <div class="title">茯苓药店管理系统</div>
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
import AdminSidebar from './adminSidebar.vue';

</script>

<style>
.title {
    font-size: 20px;
    min-height: 10vh;
    line-height: 10vh;
    padding-left: 10px;
    text-align: left;
    letter-spacing: 8px;
    color: #fff;
    background-color: #409eff;
}
</style>
