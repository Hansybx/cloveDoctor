<template>
    <el-container>
        <el-aside class="aside-container">
            <div class="space-box"></div>
            <el-card shadow="hover" class="card-container" v-for="item in 4" :key="item">item</el-card>
        </el-aside>
        <el-main class="main-container">
            <el-tabs class="tabs-container" type="border-card">
                <el-tab-pane label="通知">通知</el-tab-pane>
                <el-tab-pane label="回复">回复</el-tab-pane>
            </el-tabs>
        </el-main>
    </el-container>
</template>

<script lang="ts" setup>

</script>

<style>
.space-box {
    height: 50px;
}

.aside-container {
    max-width: 200px;
}

.card-container {
    min-height: 75px;
    margin-bottom: 10px;
}

.tabs-container {
    min-height: 750px;
}

.el-tabs__item {
    height: 50px;
    width: 75px;
}

</style>
