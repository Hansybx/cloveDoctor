<template>
    <!-- <el-col class="cart-container">
        <el-button round class="btn-container">移出购物车</el-button>
        <el-affix position="bottom" :offset="20">
            <el-button type="primary">Offset bottom 20px</el-button>
        </el-affix>
    </el-col>-->
    <el-row>
        <el-col :span="20"></el-col>
        <el-col :span="4">
            <el-button round>移出购物车</el-button>
        </el-col>
    </el-row>
    <div class="cart-items-container">
        <el-card class="card-item-container" shadow="hover" v-for="item in 4" :key="item">
            <div class="card-item">
                <img
                    src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                    class="itemImage"
                />
                <el-col class="card-item-content">
                    <div v-for="o in 4" :key="o">{{ 'List item ' + o }}</div>
                </el-col>
            </div>
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
import { Ref, ref } from 'vue';

let price: Ref<number> = ref(0);
</script>

<style>
.cart-container {
    display: flex;
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
