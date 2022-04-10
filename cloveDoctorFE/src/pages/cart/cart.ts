export default interface cart {
    id: number
    drugId: number
    drugImg: string
    drugName: string
    drugNum: number
    price: number
    updateTime: string
}

export interface drugListItem {
    drugId: number
    drugName: string
    drugNum: number
    drugPrice: number
}