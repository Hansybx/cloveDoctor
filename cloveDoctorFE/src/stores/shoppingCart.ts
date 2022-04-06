import { defineStore } from 'pinia'

interface cartItem {
    drugId: number
    amount?: number
}

export const useCartStore = defineStore({
    id: 'shoppingCart',
    state: () => ({ drugIdList: undefined as unknown as cartItem[] }),
    getters: {
        items: (state) =>
            state.drugIdList.reduce((items, item) => {
                const existingItem = items.find((it) => it.drugId === item)

                if (!existingItem) {
                    items.push({ drugId: item, amount: 1 })
                } else {
                    existingItem.amount++
                }

                return items
            }, []),
    },
    actions: {
        /**
         * Add item to the cart
         * @param {string} name
         */
        addItem(drugId: number) {
            this.drugIdList.push({ drugId, amount: 1 })
        },

        /**
         * Remove item from the cart
         * @param {string} name
         */
        removeItem(drugId: number) {
            const i = this.drugIdList.lastIndexOf(drugId)
            if (i > -1) this.drugIdList.splice(i, 1)
        },

        async purchaseItems() {
            const user = useUserStore()
            if (!user.name) return

            console.log('Purchasing', this.items)
            const n = this.items.length
            this.rawItems = []

            return n
        },
    },
})