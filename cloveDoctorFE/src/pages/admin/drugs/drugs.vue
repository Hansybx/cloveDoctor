<template>
    <el-card class="table-container">
        <template #header>
            <div class="search-container">
                <el-input v-model="keywords" class="searchInput" placeholder="Type something" />
                <el-button type="primary" :icon="Search">搜索</el-button>
                <el-button type="primary" :icon="CirclePlus" @click="addDrugs()">新增药品</el-button>
                <el-button type="primary" :icon="CirclePlus" @click="handleDeleteMulti()">批量删除</el-button>
            </div>
        </template>
        <el-table
            class="drugTable"
            :data="state.tableData"
            max-height="600px"
            @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55" />
            <el-table-column prop="drugName" label="药品名称" width="270" />
            <el-table-column prop="id" label="药品编号" width="100" />
            <el-table-column prop="drugImg" label="药品图片" width="300">
                <template #default="scope">
                    <el-image :src="scope.row.drugImg" class="previewImg"></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="stock" label="药品库存" width="150" />
            <el-table-column prop="updateTime" label="药品到期时间" width="200" />
            <el-table-column prop="price" label="药品售价￥" width="150" />
            <el-table-column prop="status" label="药品状态" width="150">
                <template #default="scope">
                    <el-tag v-if="scope.row.status === 1">在售</el-tag>
                    <el-tag v-else class="ml-2" type="info">已下架</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="250">
                <template #default="scope">
                    <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button
                        v-if="scope.row.status === 1"
                        size="small"
                        type="info"
                        @click="changeStatus(scope.row)"
                    >下架</el-button>

                    <el-button
                        v-else
                        size="small"
                        type="success"
                        @click="changeStatus(scope.row)"
                    >再上架</el-button>

                    <el-button size="small" type="danger" @click="handleDeleteSingle(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <add-drug-dlg ref="dlgControl" @getDrugList="getDrugList" />
        <del-drug-dlg ref="delDlgControl" @getDrugList="getDrugList" />
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
import { reactive, ref, onMounted } from 'vue'
import { Search, CirclePlus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import AddDrugDlg from './addDrugDlg.vue';
import axios from 'axios'
import Constant from '../../../common/config';
import DelDrugDlg from './delDrugDlg.vue';
import drugs from './drugs';
// todo 删除 批量删除 编辑 搜索 
onMounted(() => {
    getDrugList();
})

const keywords = ref('')

const state = reactive({
    tableData: [],
    totalNum: 0,
    pageSize: 10,
    currentPage: 1,
    delType: 'single', // 删除类型 single multi
    delParams: [],
    editType: 'add', // 编辑类型 add edit
})

const dlgControl = ref();
const delDlgControl = ref();

const handleSelectionChange = (val: any) => {
    state.delParams = val;
}

const addDrugs = () => {
    dlgControl.value.dlgOpen();
}

const getDrugList = () => {
    axios.get(Constant.BASE_URL_ADMIN + '/drugs', {
        params: {
            pageNum: state.currentPage,
            pageSize: state.pageSize
        }
    }).then(res => {
        if (res.data.code === 200) {
            console.log(res.data);
            state.tableData = res.data.data.drugList;
            state.totalNum = res.data.data.totalNum;
        }
    })
}

const changeDrugPage = (val: number) => {
    state.currentPage = val;
    getDrugList();
}

const changeStatus = (row: any) => {
    axios.post(Constant.BASE_URL_ADMIN + '/drugs/status', row).then(res => {
        if (res.data.code === 200) {
            ElMessage({
                message: res.data.message,
                type: 'success'
            });
            getDrugList();
        } else if (res.data.code === 400) {
            ElMessage.error(res.data.message)
        } else {
            ElMessage.error('糟糕，刚刚失败了！')
        }
    })
}

const handleEdit = (index: number, row: any) => {
    console.log(index, row)
}
const handleDeleteSingle = (row: any) => {
    state.delType = 'single';
    state.delParams = row;
    delDlgControl.value.dlgOpen(state.delType, state.delParams);
    // console.log(row)
}

const handleDeleteMulti = () => {
    state.delType = 'multi';
    delDlgControl.value.dlgOpen(state.delType, state.delParams);
    console.log('multi');
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
    height: 130px;
    width: 260px;
}
</style>
