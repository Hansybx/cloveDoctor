<template>
    <el-card class="table-container">
        <template #header>
            <div class="search-container">
                <el-button type="primary" :icon="CirclePlus" @click="swiperVisible">新增图片</el-button>
                <el-button type="primary" :icon="Delete">批量删除</el-button>
            </div>
        </template>
        <el-dialog v-model="swiperDialogVisible" title="新增图片">
            <el-form ref="dlgRef" :model="swiperInfo" :rules="dlgRules">
                <el-form-item label="图片资源链接" prop="imgUrl" :label-width="formLabelWidth">
                    <el-input v-model="swiperInfo.imgUrl" placeholder="输入图片地址" />
                </el-form-item>
                <el-form-item label="站内药品编号" prop="drugId" :label-width="formLabelWidth">
                    <el-input v-model="swiperInfo.drugId" placeholder="输入站内药品ID" />
                </el-form-item>
                <el-form-item label="排序值" prop="sortId" :label-width="formLabelWidth">
                    <el-input v-model="swiperInfo.sortId" placeholder="只展示排序值最高的前五张" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="swiperVisible">取消</el-button>
                    <el-button type="primary" @click="addSwiper(dlgRef)">确定</el-button>
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
import { FormInstance, ElMessage } from 'element-plus'
import CommonUtils from '../../../common/commonUtils'
import axios from 'axios'
import Constant from '../../../common/config'
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
interface swiper {
    imgUrl: string,
    drugId: number | null,
    sortId: number | null,
    addTime: string,
}
const swiperInfo: swiper = reactive({
    imgUrl: "",
    drugId: null,
    sortId: null,
    addTime: "",
})

// 新增轮播图dialog 可见
const swiperDialogVisible = ref(false);
const formLabelWidth = '140px';
// 新增轮播图dialog 是否可见
function swiperVisible() {
    swiperDialogVisible.value = !swiperDialogVisible.value;
}


// 数字正则校验整数（正负零
const numberValidator = (rule: any, value: any, callback: any) =>{
    // if(value === 1) callback("请输入整数！");
    const regNumber = /^(-)?(([0-9])|([1-9]([0-9]+)))$/;
    if(!regNumber.test(value)) callback("请输入整数！");
    callback();
}

const dlgRef = ref<FormInstance>();
// 新增轮播图dialog 校验规则
const dlgRules = reactive({
    imgUrl: [
        { required: true, message: '请输入图片链接', trigger: 'blur' },
    ],
    drugId: [
        { required: true, message: '请输入药品站内ID', trigger: 'blur' },
        { validator: numberValidator,required: true, trigger: 'blur' },
        // { type: 'number', message: '药品站内ID必须为数字值', trigger: 'blur' },
    ],
    sortId: [
        { required: true, message: '请输入排序值', trigger: 'blur' },
        { validator: numberValidator,required: true, trigger: 'blur' },
        // { type: 'number', message: '排序值必须为数字值', trigger: 'blur' },
    ],
})

const addSwiper = async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    swiperInfo.addTime = CommonUtils.dateFormat(new Date());
    console.log(swiperInfo);

    await formEl.validate((valid: any, fields: any) => {
        console.log(valid);
        if (valid) {
            console.log('submit!')
            axios.post(Constant.BASE_URL_ADMIN + '/swiper', swiperInfo).then(res => {
                if (res.data.code === 200) {
                    ElMessage({
                        message: res.data.message,
                        type: 'success'
                    })
                } else {
                    ElMessage.error('糟糕，添加失败了！')
                }
            })
            formEl.resetFields();
        } else {
            console.log('error submit!', fields)
        }
    })
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
