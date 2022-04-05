<template>
    <!-- 搜索 -->
    <div class="search-container">
        <el-input v-model="searchInput" :placeholder="searchHint" size="large" clearable />
        <el-button class="searchBtn" type="primary" :icon="Search" size="large">搜索</el-button>
    </div>
    <el-row class="swiper-container">
        <!-- <HomeCategories /> -->
        <el-carousel class="carousel-container" :interval="swiperInterval">
            <el-carousel-item v-for="item in 4" :key="item">
                <h3>{{ item }}</h3>
            </el-carousel-item>
        </el-carousel>
    </el-row>
    <DrugShelf />
</template>

<script setup lang='ts'>
import { Search } from '@element-plus/icons-vue'

import HomeCategories from './HomeCategories.vue';
import DrugShelf from './DrugShelf.vue'
import { ref } from 'vue'

const searchInput = ref('');
const searchHint: string = "搜索本店药品";
const swiperInterval: number = 2700;
</script>

<style scoped>
.search-container {
    padding: 15px 0;
    height: 50px;
    display: flex;
}

.searchBtn {
    height: 45px;
    width: 200px;
}

.el-input__inner {
    height: 45px;
}

.swiper-container {
    margin-bottom: 25px;
}

.carousel-container {
    height: 300px;
    flex: 1;
}

.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
    text-align: center;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
    background-color: #d3dce6;
}
</style>
