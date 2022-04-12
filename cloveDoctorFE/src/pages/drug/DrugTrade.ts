export default interface drugTrade {
    outTradeNo: string
    totalAmount: number
    subject: string
    userId: number
    drugList: drugTradeItem[]
    returnUrl: string
    tradeDate: string
}

interface drugTradeItem {
    drugId: number
    drugName: string
    drugNum: number
    drugPrice: number
}