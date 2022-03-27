export default interface drugs {
    id?: number,
    drugName: string,
    drugImg: string,
    stock: number,
    price: number,
    updateTime: string,
    status?: number, // 0 下架 1 在售
}