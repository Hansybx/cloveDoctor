<template>
    <el-menu :router="true" class="menu-container" @open="handleOpen" @close="handleClose">
        <el-menu-item index="/admin/charts">
            <el-icon>
                <icon-menu />
            </el-icon>
            <span>数据统计</span>
        </el-menu-item>
        <el-sub-menu index="/admin/main">
            <template #title>
                <el-icon>
                    <icon-menu />
                </el-icon>
                <span>首页配置</span>
            </template>
            <el-menu-item-group>
                <el-menu-item index="/admin/main/swiper">轮播图配置</el-menu-item>
                <el-menu-item index="/admin/main/recommend">推荐药品配置</el-menu-item>
            </el-menu-item-group>
        </el-sub-menu>
        <el-menu-item index="/admin/drugs">
            <el-icon>
                <icon-menu />
            </el-icon>
            <span>药品管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/user">
            <el-icon>
                <setting />
            </el-icon>
            <template #title>用户管理</template>
        </el-menu-item>
        <el-menu-item index="/admin/orders">
            <el-icon>
                <setting />
            </el-icon>
            <template #title>订单管理</template>
        </el-menu-item>
    </el-menu>
</template>

<script setup lang='ts'>
import {
    Menu as IconMenu,
    Setting,
} from '@element-plus/icons-vue'
const handleOpen = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
</script>

<style>
.menu-container {
    width: 200px;
    min-height: 85vh;
}
</style>
