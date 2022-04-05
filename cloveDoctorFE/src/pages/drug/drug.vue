<template>
    <div class="row-container">
        <el-image :src="drugInfo.drugImg" class="img-container"></el-image>
        <el-card class="box-card">
            <el-form
                label-position="right"
                label-width="100px"
                :model="drugInfo"
                style="max-width: 460px"
            >
                <el-form-item label="名称">
                    <div>asd</div>
                </el-form-item>
                <el-form-item label="价格">
                    <div>{{ drugInfo.price }}</div>
                    <!-- <el-input v-model="formLabelAlign.region" /> -->
                </el-form-item>
                <el-form-item label="有效期至">
                    <div>asd</div>
                    <!-- <el-date-picker v-model="value1" type="date" placeholder="Pick a day" disabled /> -->
                </el-form-item>
                <el-form-item label="数量">
                    <div>asd</div>
                    <!-- <el-input-number v-model="num" :min="1" :max="10" @change="handleChange" /> -->
                </el-form-item>
                <el-form-item label="库存">
                    <div>asd</div>
                    <!-- <el-input v-model="input" disabled placeholder="Please input" /> -->
                </el-form-item>
                <el-form-item label="生产厂家">
                    <div>asd</div>
                    <!-- <el-input v-model="formLabelAlign.region" /> -->
                </el-form-item>
                <el-form-item label="操作">
                    <el-button-group>
                        <el-button type="primary">Previous Page</el-button>
                        <el-button type="primary">Next Page</el-button>
                    </el-button-group>
                </el-form-item>
            </el-form>
        </el-card>
        <el-image :src="rightImg" class="right-img" />
    </div>
</template>

<script setup lang='ts'>
import { computed, onMounted, reactive, ref, watch } from 'vue'
import {
    Iphone,
    Location,
    OfficeBuilding,
    Tickets,
    User,
} from '@element-plus/icons-vue'
import axios from 'axios';
import Constant from '../../common/config';
import { useRoute } from 'vue-router';

const route = useRoute()
onMounted(() => {
    getDrugInfo();
})

// watch(route.fullPath(), (newVal, oldVal) => {
//     console.log(newVal, oldVal);
// })

const imgUrl = ref("https://img.alicdn.com/imgextra/i1/O1CN01DRcTyQ1Uzo6tlH1Lq_!!6000000002589-0-tps-1190-400.jpg");
const rightImg = "https://iconfont.alicdn.com/t/e4e56e8b-60a7-4090-a0cd-d963714ffd03.png@500h_500w.png";
const drugInfo = reactive({
    drugImg: '',
    drugName: '',
    price: null,
    stock: null,
    updateTime: '',
})

const getDrugInfo = () => {
    axios.get(Constant.BASE_URL + '/drug', {
        params: {
            drugId: 15
        }
    }).then(res => {
        if (res.data.code === 200) {
            const data = res.data.data;
            drugInfo.drugImg = data.drugImg;
            drugInfo.drugName = data.drugName;
            drugInfo.price = data.price;
            drugInfo.stock = data.stock;
            drugInfo.updateTime = data.updateTime;
            console.log(res.data.data);
        }
    })
}

</script>

<style scoped>
.row-container {
    height: 400px;
    display: flex;
}
.img-container {
    width: 30vw;
}

.box-card {
    width: 30vw;
}

.right-img {
    width: 20vw;
}

.item-cell {
    display: flex;
    align-items: baseline;
}
</style>
