<template>
    <el-dialog v-model="state.dlgVisible" title="药品编辑">
        <el-form ref="dlgRef" :model="drugInfo" :rules="dlgRules">
            <el-form-item label="药品名称" prop="drugName" :label-width="formLabelWidth">
                <el-input v-model="drugInfo.drugName" placeholder="输入药品名称" />
            </el-form-item>
            <el-form-item label="药品图片链接" prop="drugImg" :label-width="formLabelWidth">
                <el-input v-model="drugInfo.drugImg" placeholder="输入图片地址" />
            </el-form-item>
            <el-form-item label="药品保质期至" prop="updateTime" :label-width="formLabelWidth">
                <el-date-picker v-model="drugInfo.updateTime" type="date" placeholder="选择药品有效期" format="YYYY/MM/DD"
                    value-format="YYYY-MM-DD" />
            </el-form-item>
            <el-form-item label="药品价格￥" prop="price" :label-width="formLabelWidth">
                <el-input-number v-model="drugInfo.price" :precision="2" :step="0.1" />
            </el-form-item>
            <el-form-item label="药品库存" prop="stock" :label-width="formLabelWidth">
                <el-input-number v-model="drugInfo.stock" :min="1" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dlgClose(dlgRef)">取消</el-button>
                <el-button type="primary" @click="formSubmit(dlgRef)">确定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup lang='ts'>
import { onMounted, reactive, ref } from 'vue'
import type { FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus'
import drugs from './drugs';
import axios from 'axios'
import Constant from '../../../common/config';

const formLabelWidth = '140px';
const state = reactive({
    dlgVisible: false,
})

const drugInfo: drugs = reactive({
    drugName: "",
    drugImg: "",
    price: 1.00,
    stock: 1,
    updateTime: "",
    status: 1,
})

const emits = defineEmits(['getDrugList']);

// 数字正则校验整数/小数（正负零
const numberValidator = (rule: any, value: any, callback: any) => {
    // if(value === 1) callback("请输入整数！");
    const regNumber = /^[1-9][0-9]*([\.][0-9]{1,2})?$/;
    if (!regNumber.test(value)) callback("请输入数字！");
    callback();
}

const dlgRef = ref<FormInstance>();
// 新增轮播图dialog 校验规则
const dlgRules = reactive({
    drugImg: [
        { required: true, message: '请输入图片链接', trigger: 'blur' },
    ],
    drugName: [
        { required: true, message: '请输入药品名称', trigger: 'blur' },
    ],
    price: [
        { required: true, message: '请输入药品价格', trigger: 'blur' },
        { validator: numberValidator, required: true, trigger: 'blur' },
    ],
    stock: [
        { required: true, message: '请输入药品库存', trigger: 'blur' },
        { validator: numberValidator, required: true, trigger: 'blur' },
    ],
    updateTime: [
        { required: true, message: '请选择药品保质期', trigger: 'blur' },
    ],
})
const isEdit = ref(false);
const dlgOpen = (isEditflag: boolean, params: drugs) => {
    state.dlgVisible = true;
    isEdit.value = isEditflag;
    if (params) {
        drugInfo.drugImg = params.drugImg;
        drugInfo.drugName = params.drugName;
        drugInfo.id = params.id;
        drugInfo.price = params.price;
        drugInfo.status = params.status;
        drugInfo.stock = params.stock;
        drugInfo.updateTime = params.updateTime;
    } else {
        drugInfo.drugName = "";
        drugInfo.drugImg = "";
        drugInfo.price = 0;
        drugInfo.stock = 0;
        drugInfo.updateTime = "";
    }
}


const dlgInfoResert = (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    formEl.resetFields();
    // drugInfo.drugName = "";
    // drugInfo.drugImg = "";
    // drugInfo.price = null;
    // drugInfo.stock = null;
    // drugInfo.updateTime = "";
}

const dlgClose = (formEl: FormInstance | undefined) => {
    state.dlgVisible = false;
    dlgInfoResert(formEl);
}

const formSubmit = async (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    let urlSuffix: string = "";
    if (isEdit.value) {
        urlSuffix = '/update'
    }


    await formEl.validate((valid: any, fields: any) => {
        if (valid) {
            axios.post(Constant.BASE_URL_ADMIN + '/drugs' + urlSuffix, drugInfo).then(res => {
                if (res.data.code === 200) {
                    ElMessage({
                        message: res.data.message,
                        type: 'success'
                    });
                    emits('getDrugList');
                } else if (res.data.code === 400) {
                    ElMessage.error(res.data.message)
                } else {
                    ElMessage.error('糟糕，请求失败了！')
                }
            })
        } else {
            ElMessage.error('糟糕，请求失败了！')
        }
    });
    dlgClose(formEl);
}

defineExpose({
    dlgOpen,
})
</script>

<style>
</style>
