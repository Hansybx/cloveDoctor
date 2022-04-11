import axios from "axios";
import { ElMessage } from "element-plus";
import { defineStore } from "pinia";
import Constant from "../common/config";
import message, { MSG_STATUS } from "../pages/message/Message";

export const useMsgStore = defineStore({
    id: 'msgInfo',
    state: () => ({
        msgList: [] as message[],
        msgNum: 0
    }),

    actions: {
        
        msgCreate(msg: message) {
            axios.post(Constant.BASE_URL_USER + '/msg/create', msg).then(res => {
                if (res.data.code === 200) {
                    ElMessage({
                        message: res.data.message,
                        type: 'success'
                    });
                } else if (res.data.code === 400) {
                    ElMessage.error(res.data.message)
                } else {
                    ElMessage.error('糟糕，刚刚失败了！')
                }
            })
        },
        changeMsgStatus(msg: message){
            axios.post(Constant.BASE_URL_USER + '/msg/change', msg).then(res => {
                if (res.data.code === 200) {
                    ElMessage({
                        message: res.data.message,
                        type: 'success'
                    });
                } else if (res.data.code === 400) {
                    ElMessage.error(res.data.message)
                } else {
                    ElMessage.error('糟糕，刚刚失败了！')
                }
            })
        }
    },
    persist: {
        enabled: true
    }
})