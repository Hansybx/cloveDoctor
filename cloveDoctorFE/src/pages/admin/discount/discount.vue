<template>
    <el-card class="table-container">
        <template #header>
            <div class="search-container">
                <el-button type="primary" :icon="CirclePlus">新增药品</el-button>
                <el-button type="primary" :icon="Delete">批量删除</el-button>
            </div>
        </template>
        <el-table class="drugTable" :data="tableData" max-height="600px">
            <el-table-column type="selection" width="55" />
            <el-table-column prop="date" label="药品名称" width="220" />
            <el-table-column prop="sid" label="药品链接" width="550" />
            <el-table-column prop="sid" label="药品编号" width="200" />
            <el-table-column prop="state" label="折扣截至时间" width="400" />
            <el-table-column label="操作" width="200">
                <template #default="scope">
                    <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button
                        size="small"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)"
                    >删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            class="page-container"
            background
            layout="prev, pager, next"
            :total="100"
            hide-on-single-page
        />
    </el-card>
</template>

<script setup lang='ts'>
import { ref } from 'vue'
import { Delete, CirclePlus } from '@element-plus/icons-vue'
const keywords = ref('')

const tableData = [
    {
        date: '2016-05-03',
        name: 'Tom',
        state: 'California',
        city: 'Los Angeles',
        address: 'No. 189, Grove St, Los Angeles',
        zip: 'CA 90036',
    },
    {
        date: '2016-05-02',
        name: 'Tom',
        state: 'California',
        city: 'Los Angeles',
        address: 'No. 189, Grove St, Los Angeles',
        zip: 'CA 90036',
    },
]

const handleEdit = (index: number, row: any) => {
    console.log(index, row)
}
const handleDelete = (index: number, row: any) => {
    console.log(index, row)
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
</style>
