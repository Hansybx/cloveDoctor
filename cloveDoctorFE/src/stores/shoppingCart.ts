import { defineStore } from 'pinia'

interface cartItem {
    drugId: number
    drugNums: number
}

export const useCartStore = defineStore({
    id: 'shoppingCart',
    state: () => ({ cartItemList: [] as cartItem[] }),

    actions: {
        cartInit(cartItems: cartItem[]) {
            this.cartItemList = cartItems;
        },

        addCart(cartItem: cartItem) {
            const existItem = this.cartItemList.find((item) => item.drugId === cartItem.drugId)
            if (existItem) {
                existItem.drugNums += cartItem.drugNums
            } else {
                this.cartItemList.push(cartItem)
            }
        },

        removeItem(cartItem: cartItem) {
            const existItem = this.cartItemList.find((item) => item.drugId === cartItem.drugId)
            if (existItem) {
                const index = this.cartItemList.indexOf(existItem);
                this.cartItemList.splice(index,1);
            }
        }
    },
    persist: {
        enabled: true
    }
})