<template>
    <div>
        <div class="cart-items-container">
            <el-pagination
                class="page-container"
                background
                layout="prev, pager, next"
                :total="state.totalNum"
                :page-size="state.pageSize"
                :current-page="state.currentPage"
                @current-change="changePage"
                hide-on-single-page
            />
            <el-card
                class="card-item-container"
                shadow="hover"
                v-for="item in state.tableData"
                :key="item.drugId"
            >
                <div class="card-item">
                    <el-checkbox
                        v-model="state.checkList[item.drugId]"
                        @change="checkboxChange(item)"
                        class="checkbox-container"
                    />

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
                        <el-form-item label="单价￥">
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
                <!-- <el-button class="cart-btn" round @click="getHTML()">移出购物车</el-button> -->
            </el-card>
        </div>
        <el-affix class="payment-container" position="bottom" :offset="20">
            <el-card class="payment-card">
                <el-row class="priceTag">
                    <div>合计</div>
                    <div>{{ state.sumPrice }}</div>
                    <div>￥</div>
                    <el-button type="primary" @click="getHTML()">结算</el-button>
                </el-row>
            </el-card>
        </el-affix>
    </div>
</template>

<script setup lang='ts'>
import axios from 'axios';
import { onMounted, reactive, Ref, ref } from 'vue';
import Constant from '../../common/config';
import { useUserStore } from '../../stores/UserInfo';
import cart, { drugListItem } from './cart';
import moment from 'moment'
import { ElMessage } from 'element-plus';
import { v4 as uuidv4 } from 'uuid';
import drugTrade from '../drug/DrugTrade';
import CommonUtils from '../../common/commonUtils';

const user = useUserStore()
const state = reactive({
    tableData: [] as cart[],
    checkList: [],
    totalNum: 0,
    pageSize: 10,
    currentPage: 1,
    sumPrice: 0,
    payHTML: '',
    purchaseList: [] as drugListItem[],
})

onMounted(() => {
    getCartList()
    console.log(state.checkList)
})


const changePage = (val: number) => {
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

const checkboxChange = (item: cart) => {
    // console.log(item)
    const param: drugListItem = {
        drugId: item.drugId,
        drugName: item.drugName,
        drugNum: item.drugNum,
        drugPrice: item.price * item.drugNum
    }

    state.checkList.forEach((value, index) => {
        if (value && index == item.drugId) {
            state.sumPrice += item.price * item.drugNum;
            state.purchaseList.push(param);
        } else if (!value && index == item.drugId) {
            state.sumPrice -= item.price * item.drugNum;
            const curItem = state.purchaseList.find((cur) => cur.drugId === param.drugId)
            if (curItem) state.purchaseList.splice(state.purchaseList.indexOf(curItem), 1);
        }
    })
    console.log(state.purchaseList)
}

const getHTML = () => {
    if (state.purchaseList.length <= 0) return;
    const param: drugTrade = {
        outTradeNo: uuidv4(),
        totalAmount: state.sumPrice,
        subject: 'xxxxx',
        userId: user.userId,
        drugList: state.purchaseList,
        tradeDate: CommonUtils.dateFormat(new Date()),
        returnUrl: 'http://localhost:3000/#/cart'
    }

    axios.post(Constant.BASE_URL_USER + '/trade', param).then(res => {
        if (res.data.code === 200) {
            state.payHTML = res.data.data.body
            console.log(state.payHTML)
            const div = document.createElement('div')
            /* 下面的data.content就是后台返回接收到的数据 */
            div.innerHTML = res.data.data.body

            document.body.appendChild(div)
            // document.forms[0].submit()
            document.forms.punchout_form.submit();

        } else if (res.data.code === 400) {
            ElMessage.error(res.data.message)
        } else {
            ElMessage.error('糟糕,失败了！')
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
