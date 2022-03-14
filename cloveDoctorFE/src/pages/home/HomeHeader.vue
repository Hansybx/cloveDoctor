<template>
    <div>
        <el-row>
            <el-col :span="12">
                <div class="bg-purple text-container">茯苓药店</div>
            </el-col>
            <el-col :span="4">
                <div class="bg-purple text-container">我的消息</div>
            </el-col>
            <el-col :span="4">
                <!-- 购物车内商品降价通知 -->
                <div class="bg-purple text-container">购物车</div>
            </el-col>
            <el-col :span="4">
                <div class="bg-purple text-container">登录</div>
            </el-col>
        </el-row>
    </div>
</template>

<script setup lang='ts'>

</script>

<style>
.bg-purple {
    background: #d3dce6;
}

.grid-content {
    border-radius: 4px;
    min-height: 36px;
}

.text-container {
    font-size: 20px;
    min-height: 36px;
    display: flex;
    align-items: center;
    justify-content: center;
}
</style>
