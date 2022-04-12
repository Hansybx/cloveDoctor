<template>
    <el-container>
        <el-main class="main-container">
            <el-card class="tabs-container" type="border-card">
                <template #header>
                    <div class="card-header">
                        <el-button class="button" type="text" @click="openDlg()">发布留言</el-button>
                    </div>
                </template>
                <message-card-list @getPageList="getPageList"></message-card-list>
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

            <el-dialog v-model="state.dlgVisible" title="留下你的宝贵建议吧！" width="30%">
                <el-input
                    v-model="state.msgContent"
                    :rows="3"
                    type="textarea"
                    placeholder="留言内容不能超过100个字"
                />
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="closeDlg()">取消</el-button>
                        <el-button type="primary" @click="msgSend()">确认</el-button>
                    </span>
                </template>
            </el-dialog>
        </el-main>
    </el-container>
</template>

<script lang="ts" setup>
/*
    单纯留言没有回复
    留言可以被标记为 已解决 解决中 计划进行
*/
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { onMounted, reactive } from 'vue';
import Constant from '../../common/config';
import { useMsgStore } from '../../stores/messageStore';
import { useUserStore } from '../../stores/UserInfo';
import message, { MSG_STATUS } from './Message';
import MessageCardList from './MessageCardList.vue';



const msg = useMsgStore()
const user = useUserStore()
const state = reactive({
    dlgVisible: false,
    msgContent: "",
    totalNum: 1,
    pageSize: 10,
    currentPage: 1,
})

onMounted(() => {
    getPageList()
})

const openDlg = () => {
    state.dlgVisible = true
}
const closeDlg = () => {
    state.dlgVisible = false
}

const changeDrugPage = (val: number) => {
    state.currentPage = val;
    getPageList()
}

const msgSend = () => {
    const param: message = {
        userId: user.userId,
        content: state.msgContent,
        msgStatus: MSG_STATUS.DOING,
    }
    closeDlg()
    state.msgContent = ""
    msg.msgCreate(param)
}
const getMessageList = (msgType: string, pageNum: number, pageSize: number) => {
    axios.get(Constant.BASE_URL_USER + '/msg', {
        params: {
            msgType,
            pageNum,
            pageSize
        }
    }).then(res => {
        if (res.data.code === 200) {
            msg.msgList = res.data.data.msgList
            state.totalNum = res.data.data.totalNum
        } else {
            ElMessage.error('糟糕，刚刚请求失败了！')
        }
    })
}

const getPageList = () => {
    getMessageList("", state.currentPage, state.pageSize)
}
</script>

<style scoped>
.space-box {
    height: 50px;
}

.aside-container {
    max-width: 200px;
}

.card-container {
    min-height: 75px;
    margin-bottom: 10px;
}

.tabs-container {
    min-height: 750px;
}

.page-container {
    justify-content: center;
}

:deep() .el-tabs__item {
    height: 50px;
    width: 75px;
}
:deep() .el-tabs--border-card > .el-tabs__content {
    padding: 0;
}
</style>
