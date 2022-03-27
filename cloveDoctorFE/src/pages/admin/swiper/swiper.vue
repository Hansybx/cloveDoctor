<template>
    <el-card class="table-container">
        <template #header>
            <div class="search-container">
                <el-button type="primary" :icon="CirclePlus" @click="swiperVisible">新增图片</el-button>
                <el-button type="primary" :icon="Delete" @click="changeDelDlgVisibleMulti()">批量删除</el-button>
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
        <el-table
            class="drugTable"
            :data="state.tableData"
            max-height="600px"
            @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55" />
            <el-table-column prop="imgUrl" label="图片预览" width="520">
                <template #default="scope">
                    <el-image :src="scope.row.imgUrl" class="previewImg"></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="drugId" label="药品Id" width="250" />
            <el-table-column prop="sortId" label="排序值" width="200" />
            <el-table-column prop="addTime" label="添加时间" width="400" />
            <el-table-column label="操作" width="200">
                <template #default="scope">
                    <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button
                        size="small"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)"
                    >删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog v-model="swiperEditDlgVisible" title="轮播图信息编辑">
            <el-form ref="editDlgRef" :model="swiperInfo" :rules="dlgRules">
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
                    <el-button @click="swiperEditDlgCancel()">取消</el-button>
                    <el-button type="primary" @click="swiperEditDlgConfirm(editDlgRef)">确定</el-button>
                </span>
            </template>
        </el-dialog>
        <el-dialog v-model="delDlgVisible" title="提示" width="30%">
            <span>是否确定删除选中的轮播图？</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="changeDelDlgVisible()">否</el-button>
                    <el-button type="primary" @click="delSwiper()">是</el-button>
                </span>
            </template>
        </el-dialog>

        <el-pagination
            class="page-container"
            background
            hide-on-single-page
            layout="prev, pager, next"
            :total="state.totalNum"
            :page-size="state.pageSize"
            :current-page="state.currentPage"
            @current-change="changeSwiperPage"
        />
    </el-card>
</template>

<script setup lang='ts'>
import { onMounted, reactive, ref } from 'vue'
import { Delete, CirclePlus } from '@element-plus/icons-vue'
import type { FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus'
import CommonUtils from '../../../common/commonUtils'
import axios from 'axios'
import Constant from '../../../common/config'
// 新增轮播图dialog的信息
interface swiper {
    id: number | null,
    imgUrl: string,
    drugId: number | null,
    sortId: number | null,
    addTime: string,
}

onMounted(() => {
    getSwiperList();
})

const state = reactive({
    tableData: [],
    totalNum: 0,
    pageSize: 10,
    currentPage: 1,
})

// 新增轮播图dialog的信息
const swiperInfo: swiper = reactive({
    id: null,
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
const numberValidator = (rule: any, value: any, callback: any) => {
    // if(value === 1) callback("请输入整数！");
    const regNumber = /^(-)?(([0-9])|([1-9]([0-9]+)))$/;
    if (!regNumber.test(value)) callback("请输入整数！");
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
        { validator: numberValidator, required: true, trigger: 'blur' },
    ],
    sortId: [
        { required: true, message: '请输入排序值', trigger: 'blur' },
        { validator: numberValidator, required: true, trigger: 'blur' },
    ],
})
// 新增轮播图
const addSwiper = async (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    swiperInfo.addTime = CommonUtils.dateFormat(new Date());

    await formEl.validate((valid: any, fields: any) => {
        if (valid) {
            axios.post(Constant.BASE_URL_ADMIN + '/swiper', swiperInfo).then(res => {
                if (res.data.code === 200) {
                    ElMessage({
                        message: res.data.message,
                        type: 'success'
                    })
                    formEl.resetFields();
                    getSwiperList();
                } else if (res.data.code === 400) {
                    ElMessage.error(res.data.message)
                } else {
                    ElMessage.error('糟糕，添加失败了！')
                }
            })
        } else {
            ElMessage.error('糟糕，添加失败了！')
        }
    })
    swiperDialogVisible.value = false;
}

// 获取轮播图
const getSwiperList = () => {
    axios.get(Constant.BASE_URL_ADMIN + '/swiper', {
        params: {
            pageNum: state.currentPage,
            pageSize: state.pageSize
        }
    }).then(res => {
        if (res.data.code === 200) {
            console.log(res.data);
            state.tableData = res.data.data.swiperList;
            state.totalNum = res.data.data.totalNum;
        }
    })
}

const changeSwiperPage = (val: number) => {
    state.currentPage = val;
    getSwiperList();
}

// 轮播图信息编辑
const swiperEditDlgVisible = ref(false);
const editDlgRef = ref<FormInstance>();
// 轮播图信息获取
const handleEdit = (row: swiper) => {
    swiperEditDlgVisible.value = true;
    swiperInfo.id = row.id;
    swiperInfo.drugId = row.drugId;
    swiperInfo.sortId = row.sortId;
    swiperInfo.imgUrl = row.imgUrl;
    swiperInfo.addTime = row.addTime;
}
// 轮播图信息清空
const swiperEditDlgResert = () => {
    swiperInfo.id = null;
    swiperInfo.drugId = null;
    swiperInfo.sortId = null;
    swiperInfo.imgUrl = "";
}

const swiperEditDlgCancel = () => {
    swiperEditDlgVisible.value = false;
    swiperEditDlgResert();
}
// 轮播图信息提交
const swiperEditDlgConfirm = async (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    await formEl.validate((valid: any, fields: any) => {
        if (valid) {
            axios.post(Constant.BASE_URL_ADMIN + '/swiper/update', swiperInfo).then(res => {
                if (res.data.code === 200) {
                    ElMessage({
                        message: res.data.message,
                        type: 'success'
                    });
                    getSwiperList();
                } else if (res.data.code === 400) {
                    ElMessage.error(res.data.message)
                } else {
                    ElMessage.error('糟糕，添加失败了！')
                }
            })
        } else {
            ElMessage.error('糟糕，添加失败了！')
        }
    });
    swiperEditDlgCancel();
}

const swiperDelList = ref<swiper[]>([]);
const swiperDel = ref<swiper>();
const delDlgVisible = ref(false);
const delMulti = ref(false);
const changeDelDlgVisible = () => {
    delDlgVisible.value = !delDlgVisible.value;
}

const changeDelDlgVisibleMulti = () => {
    delDlgVisible.value = !delDlgVisible.value;
    delMulti.value = !delMulti.value ;
}

const handleDelete = (index: number, row: swiper) => {
    changeDelDlgVisible();
    swiperDel.value = row;
}

const delSwiper = () => {
    console.log(delMulti.value,'flag');
    if (delMulti.value) {
        multiDelSwiper();
        changeDelDlgVisibleMulti();
    } else {
        delSwiperSingle();
        changeDelDlgVisible();
    }
}

const delSwiperSingle = () => {
    axios.post(Constant.BASE_URL_ADMIN + '/swiper/del', swiperDel.value).then(res => {
        if (res.data.code === 200) {
            ElMessage({
                message: res.data.message,
                type: 'success'
            });
            getSwiperList();
        } else if (res.data.code === 400) {
            ElMessage.error(res.data.message)
        } else {
            ElMessage.error('糟糕，刚刚失败了！')
        }
    })
}

const handleSelectionChange = (val: swiper[]) => {
    swiperDelList.value = val;
}

const multiDelSwiper = () => {
    if (swiperDelList.value.length === 0) {
        ElMessage.error('删除对象未选择')
        return;
    }
    axios.post(Constant.BASE_URL_ADMIN + '/swiper/del/multi', swiperDelList.value).then(res => {
        if (res.data.code === 200) {
            ElMessage({
                message: res.data.message,
                type: 'success'
            });
            delMulti.value = false;
            getSwiperList();
        } else if (res.data.code === 400) {
            ElMessage.error(res.data.message)
        } else {
            ElMessage.error('糟糕，刚刚失败了！')
        }
    })
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
    height: 150px;
    width: 380px;
}
</style>
