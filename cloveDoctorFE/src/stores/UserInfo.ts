import { defineStore } from 'pinia'

interface userRes {
    id: number,
    username: string
    type: string
    password: string
}

export const useUserStore = defineStore({
    id: 'userInfo',
    state: () => ({
        userId: -1,
        userName: "",
        userType: "",
    }),

    actions: {
        loginSuccess(res: userRes) {
            console.log(11);
            this.userId = res.id
            this.userName = res.username
            this.userType = res.type
        }
    },
    persist: {
        enabled: true
    }
})