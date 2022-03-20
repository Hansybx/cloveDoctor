<template>
    <el-card class="table-container">
        <template #header>
            <div class="search-container">
                <el-input v-model="keywords" class="searchInput" placeholder="Type something" />
                <el-select v-model="value" class="m-2" placeholder="订单状态">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
                <el-button type="primary" :icon="Search">搜索</el-button>
                <el-button type="primary" :icon="CirclePlus">新增订单</el-button>
            </div>
        </template>
        <el-table class="drugTable" :data="tableData" max-height="600px">
            <el-table-column prop="date" label="订单编号" width="220" />
            <el-table-column prop="sid" label="订单总价" width="200" />
            <el-table-column prop="name" label="订单状态" width="300" />
            <el-table-column prop="discount" label="创建时间" width="500" />
            <el-table-column label="操作" width="400">
                <template #default="scope">
                    <el-button size="small" @click="handleEdit(scope.$index, scope.row)">订单详情</el-button>
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
import { Search, CirclePlus } from '@element-plus/icons-vue'
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

const value = ref('')

const options = [
  {
    value: 'Option1',
    label: 'Option1',
  },
  {
    value: 'Option2',
    label: 'Option2',
  },
  {
    value: 'Option3',
    label: 'Option3',
  },
  {
    value: 'Option4',
    label: 'Option4',
  },
  {
    value: 'Option5',
    label: 'Option5',
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
