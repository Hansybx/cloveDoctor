<template>
    <el-card class="table-container">
        <template #header>
            <div class="search-container">
                <el-input v-model="state.keywords" class="searchInput" placeholder="输入用户账号" />
                <el-button @click="search()" type="primary" :icon="Search">搜索</el-button>
            </div>
        </template>
        <el-table class="drugTable" :data="state.tableData" max-height="600px">
            <el-table-column prop="id" label="用户Id" width="350" />
            <el-table-column prop="username" label="用户账号" width="400" />
            <el-table-column prop="type" label="身份权限" width="350">
                <template #default="scope">
                    <el-tag v-if="scope.row.type === 'ADMIN'">管理员</el-tag>
                    <el-tag v-else class="ml-2" type="info">普通用户</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="550">
                <template #default="scope">
                    <el-button size="small" @click="handleEdit(scope.row)">编辑权限</el-button>
                    <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog v-model="state.dlgVisible" title="订单详情" width="30%">
            <el-radio v-model="state.userRow.type" label="ADMIN" size="large">管理员</el-radio>
            <el-radio v-model="state.userRow.type" label="USER" size="large">普通用户</el-radio>
            <template #footer>
                <el-button type="primary" @click="state.dlgVisible = false">取消</el-button>
                <el-button type="primary" @click="changeType()">确定</el-button>
            </template>
        </el-dialog>
        <el-dialog v-model="state.delVisible" title="提示" width="30%">
            <span>该操作将会删除此用户,是否确定?</span>
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
            @current-change="changePage"
            hide-on-single-page
        />
    </el-card>
</template>


<script setup lang='ts'>
import { onMounted, reactive, ref } from 'vue'
import { Search, CirclePlus } from '@element-plus/icons-vue'
import axios from 'axios';
import Constant from '../../../common/config';
import { ElMessage } from 'element-plus';

interface UserInfo {
    id: number
    username: number
    type: string
    password: string
}

const state = reactive({
    tableData: [] as UserInfo[],
    totalNum: 10,
    pageSize: 10,
    currentPage: 1,
    keywords: '',
    dlgVisible: false,
    delVisible: false,
    userRow: undefined as unknown as UserInfo,

})

onMounted(() => {
    getUserList()
})
const changePage = (val: number) => {
    state.currentPage = val;
    let flag = state.keywords.trim();
    if (flag.length > 0) {
        search()
    } else {
        getUserList()
    }
}

const getUserList = () => {
    axios.get(Constant.BASE_URL_ADMIN + '/user', {
        params: {
            pageNum: state.currentPage,
            pageSize: state.pageSize
        }
    }).then(res => {
        if (res.data.code === 200) {
            state.tableData = res.data.data.userList;
            state.totalNum = res.data.data.total;
            console.log(state.tableData)
        }
    })
}

const search = () => {
    axios.get(Constant.BASE_URL_ADMIN + '/user/search', {
        params: {
            keywords: state.keywords.trim(),
            pageNum: state.currentPage,
            pageSize: state.pageSize
        }
    }).then(res => {
        if (res.data.code === 200) {
            console.log(res.data);
            state.tableData = res.data.data.userList;
            state.totalNum = res.data.data.total;
            console.log(state.tableData)
        }
    })
}

const handleEdit = (row: UserInfo) => {
    state.dlgVisible = true
    state.userRow = row
}

const changeType = () => {
    console.log(state.userRow.type)
    axios.post(Constant.BASE_URL_ADMIN + '/user/change/type', state.userRow).then(res => {
        if (res.data.code === 200) {
            ElMessage({
                message: res.data.message,
                type: 'success'
            });
        } else if (res.data.code === 400) {
            ElMessage.error(res.data.message)
        } else {
            ElMessage.error('糟糕，请求失败了！')
        }
    })
    state.dlgVisible = false
}
const handleDelete = (row: UserInfo) => {
    state.delVisible = true
    state.userRow = row
}

const delConfrim = () => {
    if (state.userRow)
        axios.post(Constant.BASE_URL_ADMIN + '/user/del', state.userRow).then(res => {
            if (res.data.code === 200) {
                ElMessage({
                    message: res.data.message,
                    type: 'success'
                })
                getUserList();
            } else if (res.data.code === 400) {
                ElMessage.error(res.data.message)
            } else {
                ElMessage.error('糟糕，失败了！')
            }
        })
    state.delVisible = false
}

</script>

<style>
.search-container {
    display: flex;
}

.table-container {
    margin: 10px 0px;
    /* display: flex;
    justify-content: center; */
    /* width: 100%; */
}

.page-container {
    justify-content: center;
}
</style>
