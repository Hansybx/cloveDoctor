export default interface message {
    id?: number
    userId: number
    content: string
    msgStatus: MSG_STATUS
}

export enum MSG_STATUS {
    WILL_DO, // 将要完成
    DONE, // 已完成
    DOING, // 正在进行
    WAITING, //无操作等待其他操作进行
}