<template>
    <div>
        <el-dialog v-model="delDlgVisible" title="提示" width="30%">
            <span>是否确定删除选中的药品？</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dlgClose()">否</el-button>
                    <el-button type="primary" @click="delSubmit()">是</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang='ts'>
import { reactive, ref, } from 'vue'
import Constant from '../../../common/config';
import axios from 'axios'
import { ElMessage } from 'element-plus';

const emits = defineEmits(['getDrugList']);
const delDlgVisible = ref(false);
const state = reactive({
    delType: "",
    delParams: []
})

const dlgOpen = (delType: string, delParams: any) => {
    if (delParams.length != undefined && delParams.length === 0) {
        ElMessage.error('删除对象未选择')
        return;
    }
    delDlgVisible.value = true;
    state.delType = delType;
    state.delParams = delParams;
}


const dlgClose = () => {
    delDlgVisible.value = false;
}

const delSubmit = () => {
    axios.post(Constant.BASE_URL_ADMIN + '/drugs/del/' + state.delType, state.delParams).then(res => {
        if (res.data.code === 200) {
            ElMessage({
                message: res.data.message,
                type: 'success'
            });
            emits('getDrugList');
        } else if (res.data.code === 400) {
            ElMessage.error(res.data.message)
        } else {
            ElMessage.error('糟糕，刚刚失败了！')
        }
    });
    dlgClose();
}

const changeDelDlgVisible = () => {
    console.log('change del dlg');
}

defineExpose({
    dlgOpen,
})
</script>

<style>
</style>
