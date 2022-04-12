export default interface order {
    totalAmount: number
    tradeInfo: string
    tradeDate: string
}
export interface tradeDetail {
    drugId?: number
    drugName: string
    drugNum: number
    drugPrice: number
}