<template>
    <el-card class="table-container">
        <el-table class="drugTable" :data="state.tableData" max-height="600px">
            <el-table-column type="index" width="60" />
            <el-table-column prop="totalAmout" label="订单总价(元)" width="200" />
            <el-table-column prop="tradeDate" label="创建时间" width="600" />
            <el-table-column label="操作" width="650">
                <template #default="scope">
                    <el-button size="small" @click="showInfo(scope.row)">订单详情</el-button>
                    <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
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
        <el-dialog v-model="state.delVisible" title="提示" width="30%">
            <span>该操作将会删除此订单,是否确定?</span>
            <template #footer>
                <el-button type="primary" @click="state.delVisible = false">取消</el-button>
                <el-button type="primary" @click="delConfrim()">确定</el-button>
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
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { onMounted, reactive, ref } from 'vue'
import Constant from '../../common/config';
import { useUserStore } from '../../stores/UserInfo';
import order, { tradeDetail } from './order';

const user = useUserStore()
const state = reactive({
    tableData: [] as order[],
    totalNum: 10,
    pageSize: 10,
    currentPage: 1,
    dlgVisible: false,
    delVisible: false,
    delRow: undefined as unknown as order,
    curOrderInfo: [] as tradeDetail[],
})

onMounted(() => {
    getTradeList()
})

const getTradeList = () => {
    axios.get(Constant.BASE_URL_USER + '/trade', {
        params: {
            userId: user.userId,
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

const showInfo = (row: order) => {
    state.curOrderInfo = JSON.parse(row.tradeInfo)
    state.dlgVisible = true
}
const handleDelete = (row: order) => {
    console.log(row)
    state.delVisible = true
    state.delRow = row
}

const delConfrim = () => {

    if (state.delRow)
        axios.post(Constant.BASE_URL_USER + '/trade/del', state.delRow).then(res => {
            if (res.data.code === 200) {
                ElMessage({
                    message: res.data.message,
                    type: 'success'
                })
                getTradeList();
            } else if (res.data.code === 400) {
                ElMessage.error(res.data.message)
            } else {
                ElMessage.error('糟糕，失败了！')
            }
        })
    state.delVisible = false

}
const changeDrugPage = (val: number) => {
    state.currentPage = val;
    getTradeList()
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

