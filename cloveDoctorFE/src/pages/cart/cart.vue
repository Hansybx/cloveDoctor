<template>
    <div class="cart-items-container">
        <el-pagination
            class="page-container"
            background
            layout="prev, pager, next"
            :total="state.totalNum"
            :page-size="state.pageSize"
            :current-page="state.currentPage"
            @current-change="changeDrugPage"
            hide-on-single-page
        />
        <el-card
            class="card-item-container"
            shadow="hover"
            v-for="item in state.tableData"
            :key="item"
        >
            <div class="card-item">
                <el-checkbox class="checkbox-container" v-model="radio1" />
                <el-image :src="item.drugImg" class="itemImage"></el-image>
                <el-form
                    label-position="right"
                    label-width="100px"
                    :model="item"
                    class="card-item-content"
                >
                    <el-form-item label="名称">
                        <div>{{ item.drugName }}</div>
                    </el-form-item>
                    <el-form-item label="价格￥">
                        <div>{{ item.price }}元</div>
                    </el-form-item>
                    <el-form-item label="添加时间">
                        <div>{{ moment(item.updateTime).format().slice(0, -6) }}</div>
                    </el-form-item>
                    <el-form-item label="数量">
                        <div>{{ item.drugNum }}</div>
                    </el-form-item>
                </el-form>
            </div>
            <el-button class="cart-btn" round @click="removeItem(item.id)">移出购物车</el-button>
        </el-card>
    </div>
    <el-affix class="payment-container" position="bottom" :offset="20">
        <el-card class="payment-card">
            <el-row class="priceTag">
                <div>合计</div>
                <div>{{ price }}</div>
                <div>￥</div>
                <el-button type="primary">结算</el-button>
            </el-row>
        </el-card>
    </el-affix>
</template>

<script setup lang='ts'>
import axios from 'axios';
import { onMounted, reactive, Ref, ref } from 'vue';
import Constant from '../../common/config';
import { useUserStore } from '../../stores/UserInfo';
import cart from './cart';
import moment from 'moment'
import { ElMessage } from 'element-plus';

const user = useUserStore()
const state = reactive({
    tableData: [] as cart[],
    totalNum: 0,
    pageSize: 10,
    currentPage: 1,
})
const radio1 = ref('1')

onMounted(() => {
    getCartList()
})

let price: Ref<number> = ref(0);

const changeDrugPage = (val: number) => {
    state.currentPage = val;
    getCartList();
}

const getCartList = () => {
    axios.get(Constant.BASE_URL_USER + '/cart', {
        params: {
            userId: user.userId,
            pageNum: state.currentPage,
            pageSize: state.pageSize
        }
    }).then(res => {
        if (res.data.code === 200) {
            console.log(res.data);
            state.tableData = res.data.data.cartList;
            state.totalNum = res.data.data.total;
        }
    })
}

const removeItem = (id: number) => {
    const param = { id: id }
    axios.post(Constant.BASE_URL_USER + '/cart/del', param).then(res => {
        if (res.data.code === 200) {
            ElMessage({
                message: res.data.message,
                type: 'success'
            })
            getCartList();
        } else if (res.data.code === 400) {
            ElMessage.error(res.data.message)
        } else {
            ElMessage.error('糟糕，失败了！')
        }
    })
}

</script>

<style scoped>
.page-container {
    justify-content: center;
}

.checkbox-container {
    margin-top: 50px;
    margin-right: 20px;
}
.cart-items-container {
    width: 100%;
}
.card-item-container {
    margin: 5px 0px;
}

.card-item {
    display: flex;
}

.card-item-content {
    display: flex;
    align-items: flex-start;
    flex-direction: column;
    margin-left: 10px;
}

.cart-btn {
    float: right;
    margin-top: -30px;
}

.itemImage {
    /* display: flex; */
    width: 175px;
    height: 175px;
}

.payment-container {
    width: 90%;
    margin: 0 auto;
}
.priceTag {
    line-height: 100%;
    align-items: center;
    flex-direction: row;
    justify-content: flex-end;
}
</style>
