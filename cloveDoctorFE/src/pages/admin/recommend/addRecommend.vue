<template>
    <el-dialog v-model="state.dlgVisible" title="添加推荐商品">
        <div style="display: flex;">
            <el-input
                v-model="state.keyword"
                class="w-50 m-2"
                placeholder="搜索药品"
                :prefix-icon="Search"
            />
            <el-button type="primary" :icon="Search" @click="drugSearch()" />
        </div>
        <el-table
            :data="state.drugList"
            height="auto"
            style="width: 100%;"
            @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="60" />
            <el-table-column prop="id" label="药品Id" width="180" />
            <el-table-column prop="drugName" label="药品名" width="680" />
        </el-table>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dlgClose()">取消</el-button>
                <el-button type="primary" @click="drugsAdd()">确认添加</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup lang='ts'>
import { reactive, ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import Constant from '../../../common/config';
import axios from 'axios';
import { ElMessage } from 'element-plus';

const formLabelWidth = '140px'

const state = reactive({
    dlgVisible: false,
    keyword: "",
    addParams: [],
    drugList: []
})
const emits = defineEmits(['getDrugs']);

const dlgOpen = () => {
    state.dlgVisible = true;
}

const handleSelectionChange = (val: any) => {
    state.addParams = val;
}
const dlgClose = () => {
    state.dlgVisible = false;
}

const drugSearch = () => {
    axios.get(Constant.BASE_URL_ADMIN + '/drugs/search/recommend', {
        params: {
            keyword: state.keyword
        }
    }).then(res => {
        if (res.data.code === 200) {
            state.drugList = res.data.data;
            if (res.data.data.length === 0) {
                ElMessage({
                    message: "不存在符合条件的数据",
                    type: 'success'
                });
            }
        }
    })
}

const drugsAdd = () => {
    axios.post(Constant.BASE_URL_ADMIN + '/recommend/add', state.addParams).then(res => {
        if (res.data.code === 200) {
            ElMessage({
                message: res.data.message,
                type: 'success'
            });
            dlgClose();
            emits('getDrugs');
        } else if (res.data.code === 400) {
            ElMessage.error(res.data.message)
            dlgClose();
        } else {
            dlgClose();
            ElMessage.error('糟糕，刚刚失败了！')
        }
    })

}

defineExpose({
    dlgOpen,
})
</script>

<style>
</style>
