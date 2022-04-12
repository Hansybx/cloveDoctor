<template>
    <el-card class="table-container">
        <template #header>
            <div class="search-container">
                <el-input v-model="state.keyword" class="searchInput" placeholder="输入用户账号" />
                <el-button type="primary" :icon="Search" @click="search()">搜索</el-button>
            </div>
        </template>
        <el-table class="drugTable" :data="state.tableData" max-height="600px">
            <el-table-column type="index" width="60" />
            <el-table-column prop="username" label="用户账号" width="250" />
            <el-table-column prop="tradeNo" label="订单编号" width="500" />
            <el-table-column prop="totalAmout" label="订单总价(元)" width="200" />
            <el-table-column prop="tradeDate" label="创建时间" width="220" />
            <el-table-column label="操作" width="400">
                <template #default="scope">
                    <el-button size="small" @click="showInfo(scope.row)">查看订单详情</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog v-model="state.dlgVisible" title="订单详情" width="30%">
            <el-table :data="state.curOrderInfo">
                <el-table-column property="drugName" label="药品名" width="150" />
                <el-table-column property="drugNum" label="药品数量" width="200" />
                <el-table-column property="drugPrice" label="药品总价（元）" />
            </el-table>
            <template #footer>
                <el-button type="primary" @click="state.dlgVisible = false">确定</el-button>
            </template>
        </el-dialog>
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
    </el-card>
</template>


<script setup lang='ts'>
import { onMounted, reactive, ref } from 'vue'
import { Search, CirclePlus } from '@element-plus/icons-vue'
import axios from 'axios';
import Constant from '../../../common/config';
import OrderAdmin from './OrderAdmin';
import { tradeDetail } from '../../tradeInfo/order';
const keywords = ref('')

const state = reactive({
    tableData: [] as OrderAdmin[],
    totalNum: 10,
    pageSize: 10,
    currentPage: 1,
    keyword: '',
    dlgVisible: false,
    curOrderInfo: [] as tradeDetail[],
})

onMounted(() => {
    getTradeList()
})

const getTradeList = () => {
    axios.get(Constant.BASE_URL_ADMIN + '/trade', {
        params: {
            pageNum: state.currentPage,
            pageSize: state.pageSize
        }
    }).then(res => {
        if (res.data.code === 200) {
            console.log(res.data);
            state.tableData = res.data.data.tradeList;
            state.totalNum = res.data.data.total;
            console.log(state.tableData)
        }
    })
}

const search = () => {
    axios.get(Constant.BASE_URL_ADMIN + '/trade/search', {
        params: {
            keyword: state.keyword.trim(),
            pageNum: state.currentPage,
            pageSize: state.pageSize
        }
    }).then(res => {
        if (res.data.code === 200) {
            console.log(res.data);
            state.tableData = res.data.data.tradeList;
            state.totalNum = res.data.data.total;
            console.log(state.tableData)
        }
    })
}

const showInfo = (row: OrderAdmin) => {
    state.curOrderInfo = JSON.parse(row.tradeInfo)
    state.dlgVisible = true
}

const changeDrugPage = (val: number) => {
    state.currentPage = val;
    let flag = state.keyword.trim();
    if (flag.length > 0) {
        search()
    } else {
        getTradeList()
    }

}

</script>

<style scoped>
.search-container {
    display: flex;
}

.table-container {
    margin: 10px 0px;
}

.page-container {
    justify-content: center;
}
</style>
