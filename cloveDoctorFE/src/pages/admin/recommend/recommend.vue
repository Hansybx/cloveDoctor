<template>
    <el-card class="table-container">
        <template #header>
            <div class="search-container">
                <el-button type="primary" :icon="CirclePlus" @click="addDrugs()">新增药品</el-button>
            </div>
        </template>
        <el-table class="drugTable" :data="state.tableData" max-height="600px">
            <el-table-column prop="drugId" label="药品编号" width="250" />
            <el-table-column prop="drugName" label="药品名称" width="240" />
            <el-table-column prop="drugImg" label="药品预览" width="800">
                <template #default="scope">
                    <el-image :src="scope.row.drugImg" class="previewImg"></el-image>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="400">
                <template #default="scope">
                    <el-button
                        size="small"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)"
                    >删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <add-recommend ref="dlgControl" @getDrugs="getDrugs" />
        <el-dialog v-model="state.delDlgVisible" title="提示" width="30%">
            <span>是否确定删除选中的药品？</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="changeDelDlgVisible()">否</el-button>
                    <el-button type="primary" @click="delSubmit()">是</el-button>
                </span>
            </template>
        </el-dialog>
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
    </el-card>
</template>

<script setup lang='ts'>
// 当药品数过多时支持查看更多
import { onMounted, reactive, ref } from 'vue'
import { Delete, CirclePlus } from '@element-plus/icons-vue'
import Constant from '../../../common/config';
import axios from 'axios';
import AddRecommend from './addRecommend.vue';
import { ElMessage } from 'element-plus';

onMounted(() => {
    getDrugs();
})

const state = reactive({
    tableData: [],
    totalNum: 0,
    pageSize: 5,
    currentPage: 1,
    delDlgVisible: false,
    delParam: null,
})

const getDrugs = () => {
    axios.get(Constant.BASE_URL_ADMIN + '/recommend', {
        params: {
            pageNum: state.currentPage,
            pageSize: state.pageSize
        }
    }).then(res => {
        if (res.data.code === 200) {
            console.log(res.data);
            state.tableData = res.data.data.recommendList;
            state.totalNum = res.data.data.totalNum;
        }
    })
}

const dlgControl = ref();
const addDrugs = () => {
    dlgControl.value.dlgOpen();
}
const changePage = (val: number) => {
    state.currentPage = val;
    getDrugs();
}

const handleDelete = (index: number, row: any) => {
    console.log(row)
    state.delParam = row;
    changeDelDlgVisible()
}

const changeDelDlgVisible = () => {
    state.delDlgVisible = !state.delDlgVisible;
}

const delSubmit = () => {
    axios.post(Constant.BASE_URL_ADMIN + '/recommend/delete', state.delParam).then(res => {
        if (res.data.code === 200) {
            ElMessage({
                message: res.data.message,
                type: 'success'
            });
            getDrugs();
        } else if (res.data.code === 400) {
            ElMessage.error(res.data.message)
        } else {
            ElMessage.error('糟糕，刚刚失败了！')
        }
    });

    state.delDlgVisible = false;
}

</script>

<style>
.search-container {
    display: flex;
}

.table-container {
    margin: 10px 0px;
}

.page-container {
    justify-content: center;
}

.previewImg {
    width: 500px;
    height: 250px;
}
</style>

