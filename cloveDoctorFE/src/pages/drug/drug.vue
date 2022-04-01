<template>
    <div class="row-container">
        <el-image :src="imgUrl" class="img-container"></el-image>
        <el-card class="box-card">
            <el-form
                label-position="right"
                label-width="100px"
                :model="formLabelAlign"
                style="max-width: 460px"
            >
                <el-form-item label="名称">
                    <el-input v-model="input" disabled placeholder="Please input" />
                </el-form-item>
                <el-form-item label="价格">
                    <el-input v-model="formLabelAlign.region" />
                </el-form-item>
                <el-form-item label="生产日期">
                    <el-date-picker v-model="value1" type="date" placeholder="Pick a day" disabled />
                </el-form-item>
                <el-form-item label="数量">
                    <el-input-number v-model="num" :min="1" :max="10" @change="handleChange" />
                </el-form-item>
                <el-form-item label="库存">
                    <el-input v-model="input" disabled placeholder="Please input" />
                </el-form-item>
                <el-form-item label="生产厂家">
                    <el-input v-model="formLabelAlign.region" />
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
import { computed, reactive, ref } from 'vue'
import {
    Iphone,
    Location,
    OfficeBuilding,
    Tickets,
    User,
} from '@element-plus/icons-vue'

const imgUrl = ref("https://img.alicdn.com/imgextra/i1/O1CN01DRcTyQ1Uzo6tlH1Lq_!!6000000002589-0-tps-1190-400.jpg");
const rightImg = "https://iconfont.alicdn.com/t/7a91a623-667b-412d-803c-ad0848e4124c.png";
const formLabelAlign = reactive({
    name: '',
    region: '',
    type: '',
})
</script>

<style>
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
