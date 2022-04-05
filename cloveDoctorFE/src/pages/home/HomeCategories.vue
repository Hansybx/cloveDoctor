<template>
    <div>
        <el-col>
            <el-card shadow="hover" class="box-card">
                <span>快速找药</span>
            </el-card>
            <el-card shadow="hover" class="box-card">
                <span>儿童</span>
            </el-card>
            <el-card shadow="hover" class="box-card">
                <span>成人</span>
            </el-card>
            <el-card shadow="hover" class="box-card">
                <span>家中常备</span>
            </el-card>
        </el-col>
    </div>
</template>

<script setup lang='ts'>

const spaceSize: number = 50;

</script>

<style scoped>
.box-card {
    flex: 1;
    min-width: 200px;
    height: 73px;
}
</style>
