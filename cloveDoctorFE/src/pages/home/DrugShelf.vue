<template>
    <el-container class="shelf-container">
        <el-aside>
            <el-card shadow="hover" class="drugKind" :style="handleKindHeight()">
                <div class="card-header">
                    <span>药品推荐</span>
                </div>
                <el-image :src="drugKindImg" fit="contain" :style="handleKindImg()" />
            </el-card>
        </el-aside>
        <el-main>
            <el-space :size="0" wrap>
                <el-card
                    v-for="item in local.recommendList"
                    shadow="hover"
                    class="drugItem"
                    @click="toDrugItem(item.id)"
                >
                    <el-image :src="item.drugImg" class="imageItem" />
                    <div>{{ item.drugName }}</div>
                    <div>￥{{ item.price }}</div>
                </el-card>
            </el-space>
        </el-main>
    </el-container>
</template>

<script setup lang='ts'>
import axios from 'axios';
import { reactive, onMounted, ref, onBeforeMount, Ref } from 'vue';
import Constant from '../../common/config';
import router from '../../router/router';

onBeforeMount(() => {
    getRecommend();
})

// mounted
onMounted(() => {
    handleKindHeight();
})

const local = reactive({ recommendList: [] })
const handleKindHeight = () => {
    if (local.recommendList.length <= 4) {
        return { height: "200px" }
    }
}

const handleKindImg = () => {
    if (local.recommendList.length <= 4) {
        return { height: "160px" }
    }
}

const drugKindImg: string = "https://iconfont.alicdn.com/t/7a91a623-667b-412d-803c-ad0848e4124c.png@500h_500w.png";

const getRecommend = () => {
    axios.get(Constant.BASE_URL + '/recommend').then(res => {
        if (res.data.code === 200) {
            local.recommendList = res.data.data;
        }
    })
}

const toDrugItem = (id:number) => {
    router.push("/drug/"+id)
}

</script>

<style>
.shelf-container {
    margin-bottom: 30px;
}

.el-aside {
    width: 18vw;
}

.drugKind {
    height: 401px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: flex-start;
}

.drugItem {
    flex: 1;
    width: 15vw;
    height: 200px;
}

.imageItem {
    height: 120px;
}
</style>
