<template>
    <el-container v-for="item in 3" :key="item" class="shelf-container">
        <el-aside>
            <el-card shadow="hover" class="drugKind">
                <img
                    src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                    class="image"
                />
            </el-card>
        </el-aside>
        <el-main>
            <el-space :size="0" wrap>
                <el-card
                    v-for="item in 8"
                    :key="item"
                    shadow="hover"
                    class="drugItem"
                    :style="cardState"
                >
                    <img
                        src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                        class="image"
                    />
                </el-card>
            </el-space>
        </el-main>
    </el-container>
</template>

<script setup lang='ts'>
import { reactive, onMounted } from 'vue';

const cardState = reactive({
    // height: "",
    width: ""
})

// mounted
onMounted(() => {
    console.log('Component is mounted!')
    getCardWidth();
})

function getCardWidth() {
    // cardState.height = window.innerHeight + "px";
    cardState.width = (window.innerWidth - 700) / 4.2 + "px";
    // cardState.height = 10 + "px";
    // cardState.width = 10 + "px";
    console.log(111);
}

</script>

<style>
.shelf-container {
    margin-bottom: 30px;
}

.el-aside {
    width: 300px;
}

.drugKind {
    /* flex: 2; */
    /* width: 100%; */
    height: 401px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.drugItem {
    flex: 1;
    /* width: 290px; */
    height: 200px;
}

.image {
    height: 100px;
    width: 100px;
}
</style>
