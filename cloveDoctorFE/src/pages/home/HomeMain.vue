<template>
    <el-row class="swiper-container">
        <!-- <HomeCategories /> -->
        <el-carousel v-if="local.swiperList.length > 0" class="carousel-container" :interval="swiperInterval">
            <el-carousel-item v-for="item in local.swiperList" :key="item">
                <el-image :src="item.imgUrl" @click="toDrugItem(item.drugId)" />
            </el-carousel-item>
        </el-carousel>
    </el-row>
    <DrugShelf />
</template>

<script setup lang='ts'>
import { Search } from '@element-plus/icons-vue'

import HomeCategories from './HomeCategories.vue';
import DrugShelf from './DrugShelf.vue'
import { onMounted, reactive, ref } from 'vue'
import Constant from '../../common/config';
import axios from 'axios';
import router from '../../router/router';

onMounted(() => {
    getSwiper();
})

const local = reactive({
    swiperList: []
})

const searchInput = ref('');
const searchHint: string = "搜索本店药品";
const swiperInterval: number = 2700;

const getSwiper = () => {
    axios.get(Constant.BASE_URL + '/swiper').then(res => {
        if (res.data.code === 200) {
            local.swiperList = res.data.data;
            if (local.swiperList.length == 0) {
                local.swiperList = [{ 'imgUrl': 'https://iconfont.alicdn.com/t/1ceabb3b-9897-4d11-b22f-d91f9fc576bf.png' }]
            }
        }
    })
}

const toDrugItem = (id: number) => {
    if (id === undefined || id === null) return;
    router.push("/drug/" + id)
}

</script>

<style scoped>
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
