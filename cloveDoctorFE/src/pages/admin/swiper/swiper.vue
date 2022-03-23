<template>
    <el-card class="table-container">
        <template #header>
            <div class="search-container">
                <el-button type="primary" :icon="CirclePlus" @click="swiperVisible">新增图片</el-button>
                <el-button type="primary" :icon="Delete">批量删除</el-button>
            </div>
        </template>
        <el-dialog v-model="swiperDialogVisible" title="新增图片">
            <el-form :model="swiperInfo">
                <el-form-item label="图片资源链接" :label-width="formLabelWidth">
                    <el-input v-model="swiperInfo.imgUrl" placeholder="输入图片地址" />
                </el-form-item>
                <el-form-item label="站内药品编号" :label-width="formLabelWidth">
                    <el-input v-model="swiperInfo.drugId" placeholder="输入站内药品编号" />
                </el-form-item>
                <el-form-item label="排序值" :label-width="formLabelWidth">
                    <el-input v-model="swiperInfo.sortId" placeholder="排序值大的优先展示" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="swiperVisible">Cancel</el-button>
                    <el-button type="primary" @click="addSwiper">Confirm</el-button>
                </span>
            </template>
        </el-dialog>
        <el-table class="drugTable" :data="tableData" max-height="600px">
            <el-table-column type="selection" width="55" />
            <el-table-column prop="date" label="图片预览" width="220">
                <template #default="scope">
                    <el-image :src="scope.row.date"></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="sid" label="跳转链接" width="550" />
            <el-table-column prop="name" label="排序值" width="200" />
            <el-table-column prop="state" label="添加时间" width="400" />
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
import { reactive, ref } from 'vue'
import { Delete, CirclePlus } from '@element-plus/icons-vue'
const keywords = ref('')

const tableData = [
    {
        date: 'https://img.alicdn.com/imgextra/i1/O1CN01DRcTyQ1Uzo6tlH1Lq_!!6000000002589-0-tps-1190-400.jpg',
        name: 'Tom',
        state: 'California',
        city: 'Los Angeles',
        address: 'No. 189, Grove St, Los Angeles',
        zip: 'CA 90036',
    },
    {
        date: 'https://img.alicdn.com/imgextra/i1/O1CN01DRcTyQ1Uzo6tlH1Lq_!!6000000002589-0-tps-1190-400.jpg',
        name: 'Tom',
        state: 'California',
        city: 'Los Angeles',
        address: 'No. 189, Grove St, Los Angeles',
        zip: 'CA 90036',
    },
]

// 新增轮播图dialog的信息
const swiperInfo = reactive({
    imgUrl: "",
    drugId: "",
    sortId: "",
    addTime: "",
})

// 新增轮播图dialog 可见
const swiperDialogVisible = ref(false);
const formLabelWidth = '140px';
// 新增轮播图dialog 是否可见
function swiperVisible() {
    swiperDialogVisible.value = !swiperDialogVisible.value
}

function addSwiper() {
    console.log("add");
    swiperDialogVisible.value = false;
}

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
