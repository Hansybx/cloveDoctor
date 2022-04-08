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
                    <div>{{ drugInfo.drugName }}</div>
                </el-form-item>
                <el-form-item label="价格￥">
                    <div>{{ drugInfo.price }}元</div>
                </el-form-item>
                <el-form-item label="有效期至">
                    <div>{{ drugInfo.updateTime }}</div>
                </el-form-item>

                <el-form-item label="库存数量">
                    <div>{{ drugInfo.stock }}</div>
                </el-form-item>
                <el-form-item label="生产厂家">
                    <div>asd</div>
                </el-form-item>
                <el-form-item label="购买数量">
                    <div>asd</div>
                </el-form-item>
            </el-form>
        </el-card>
        <el-image :src="rightImg" class="right-img" />
    </div>
    <el-button-group class="btn-group">
        <el-button type="primary" :icon="ShoppingBag" @click="addToCart()">加入购物车</el-button>
        <el-button type="primary">
            购买本商品
            <el-icon>
                <present />
            </el-icon>
        </el-button>
    </el-button-group>
</template>

<script setup lang='ts'>
import { onMounted, reactive } from 'vue'
import {
    Present,
    ShoppingBag
} from '@element-plus/icons-vue'
import axios from 'axios';
import Constant from '../../common/config';
import { useRoute } from 'vue-router';
import { useCartStore } from '../../stores/shoppingCart';
import { useUserStore } from '../../stores/UserInfo';
import router from '../../router/router';
import { ElMessage } from 'element-plus';

const route = useRoute()
const cart = useCartStore()
const user = useUserStore()
onMounted(() => {
    getDrugInfo();
    console.log(route.matched, route.params)
})

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
            drugId: route.params.id
        }
    }).then(res => {
        if (res.data.code === 200) {
            const data = res.data.data;
            drugInfo.drugImg = data.drugImg;
            drugInfo.drugName = data.drugName;
            drugInfo.price = data.price;
            drugInfo.stock = data.stock;
            drugInfo.updateTime = data.updateTime;
        }
    })
}

const addToCart = () => {
    if (user.userId < 0) {
        router.push('/login')
    } else {
        const param = {
            userId: user.userId,
            drugId: Number(route.params.id),
            drugNum: 1
        }
        axios.post(Constant.BASE_URL_USER + '/drug/add', param).then(res => {
            if (res.data.code === 200) {
                ElMessage({
                    message: res.data.message,
                    type: 'success'
                })
                cart.addCart({ drugId: Number(route.params.id), drugNums: 1 });
            } else if (res.data.code === 400) {
                ElMessage.error(res.data.message)
            } else {
                ElMessage.error('糟糕，添加失败了！')
            }
        })

    }
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
    display: flex;
    align-items: center;
    justify-content: center;
}

.right-img {
    width: 20vw;
}

.btn-group {
    margin-top: 90px;
}

.item-cell {
    display: flex;
    align-items: baseline;
}
</style>
