<template>
    <div v-if="msg.msgList.length > 0" v-for="item in msg.msgList" :key="item.id">
        <el-badge
            :value="enumToChar(item.msgStatus)"
            :type="enumToType(item.msgStatus)"
            class="badge-container"
        >
            <el-card class="box-card" shadow="never">
                <div class="card-header">
                    <span>{{ item.content }}</span>
                    <el-button-group class="btn-container" v-if="user.userType === 'ADMIN'">
                        <el-button @click="msgDel(item)" class="button" type="text">删除</el-button>
                        <el-dropdown @command="handleCommand" style="padding-left: 10px;">
                            <span class="el-dropdown-link">
                                留言状态
                                <el-icon class="el-icon--right">
                                    <arrow-down />
                                </el-icon>
                            </span>
                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item
                                        :command="{ msg: item, status: MSG_STATUS.DOING }"
                                    >解决中</el-dropdown-item>
                                    <el-dropdown-item
                                        :command="{ msg: item, status: MSG_STATUS.DONE }"
                                    >已解决</el-dropdown-item>
                                    <el-dropdown-item
                                        :command="{ msg: item, status: MSG_STATUS.WAITING }"
                                    >等待解决</el-dropdown-item>
                                    <el-dropdown-item
                                        :command="{ msg: item, status: MSG_STATUS.WILL_DO }"
                                    >已计划解决</el-dropdown-item>
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>
                    </el-button-group>
                </div>
            </el-card>
        </el-badge>
    </div>
    <div v-else>
        <span>暂无留言</span>
    </div>
</template>

<script setup lang='ts'>
import { reactive } from 'vue';
import { useMsgStore } from '../../stores/messageStore';
import { useUserStore } from '../../stores/UserInfo';
import { ArrowDown } from '@element-plus/icons-vue'
import message, { MSG_STATUS } from './Message';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import Constant from '../../common/config';

const msg = useMsgStore()
const user = useUserStore()
const emits = defineEmits(['getPageList']);

const enumToChar = (status: any): string => {
    switch (status) {
        case 'DOING':
            return '解决中';
        case 'DONE':
            return '已解决';
        case 'WAITING':
            return '等待解决';
        case 'WILL_DO':
            return '已计划解决';
        default:
            return "";
    }
}

const enumToType = (status: any): string => {
    switch (status) {
        case 'DOING':
            return 'primary';
        case 'DONE':
            return 'success';
        case 'WAITING':
            return 'danger';
        case 'WILL_DO':
            return 'warning';
        default:
            return "";
    }
}

const msgDel = (item: message) => {
    if (item.id) {
        axios.post(Constant.BASE_URL_USER + '/msg/del', item).then(res => {
            if (res.data.code === 200) {
                ElMessage({
                    message: res.data.message,
                    type: 'success'
                });
                emits('getPageList')
            } else if (res.data.code === 400) {
                ElMessage.error(res.data.message)
            } else {
                ElMessage.error('糟糕，刚刚失败了！')
            }
        })
    }

}

const handleCommand = (command: any) => {
    let curMsg: message = command.msg;
    curMsg.msgStatus = command.status;
    msg.changeMsgStatus(curMsg)
    emits('getPageList');
}
</script>

<style scoped>
.badge-container {
    width: 100%;
}
:deep() .el-badge__content.is-fixed {
    right: 150px;
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.box-card {
    width: 100%;
    margin-bottom: 5px;
}

.btn-container {
    display: flex;
    align-items: center;
}
</style>
