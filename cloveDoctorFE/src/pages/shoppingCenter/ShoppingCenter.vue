<template>
    <div class="drugs-container">
        <!-- 搜索 -->
        <div class="search-container">
            <el-input v-model="state.keywords" placeholder="输入关键词进行搜索" size="large" clearable />
            <el-button class="searchBtn" type="primary" :icon="Search" @click="drugSearch()">搜索</el-button>
        </div>
        <el-card
            class="card-item-container"
            shadow="hover"
            v-for="item in state.tableData"
            :key="item.id"
            @click="toDrugItem(item.id)"
        >
            <div class="card-item">
                <img :src="item.drugImg" class="itemImage" />
                <!-- <el-col >
                    <div v-for="o in 4" :key="o">{{ 'List item ' + o }}</div>
                </el-col>-->
                <el-form
                    label-position="right"
                    label-width="100px"
                    :model="item"
                    class="card-item-content"
                >
                    <el-form-item label="名称">
                        <div>{{ item.drugName }}</div>
                    </el-form-item>
                    <el-form-item label="价格￥">
                        <div>{{ item.price }}元</div>
                        <!-- <el-input v-model="formLabelAlign.region" /> -->
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
        <el-pagination
            class="page-container"
            background
            layout="prev, pager, next"
            :total="state.totalNum"
            :page-size="state.pageSize"
            :current-page="state.currentPage"
            @current-change="changePage"
            hide-on-single-page
        />
    </div>
</template>

<script setup lang='ts'>
import axios from 'axios';
import { Search } from '@element-plus/icons-vue'
import { onMounted, reactive } from 'vue';
import Constant from '../../common/config';
import router from '../../router/router';
import drugItem from './drugItem';

const state = reactive({
    tableData: [] as drugItem[],
    totalNum: 10,
    pageSize: 5,
    currentPage: 1,
    selectOpt: 1, //搜索相关 状态
    keywords: '', //搜索相关 关键字
    selected: false, //搜索相关 是否使用过搜索
})

onMounted(() => {
    getDrugList();
})

const changePage = (val: number) => {
    state.currentPage = val;
    getDrugList();
}

const getDrugList = () => {
    axios.get(Constant.BASE_URL + '/drugs', {
        params: {
            pageNum: state.currentPage,
            pageSize: state.pageSize
        }
    }).then(res => {
        if (res.data.code === 200) {
            console.log(res.data);
            state.tableData = res.data.data.drugList;
            state.totalNum = res.data.data.totalNum;
            console.log(state.tableData)
        }
    })
}

const drugSearch = () => {
    if (state.keywords.length === 0) {
        getDrugList();
        return;
    }

    let searchPage = 1;
    // if (state.selected) searchPage = state.currentPage;


    axios.get(Constant.BASE_URL + '/drugs/search', {
        params: {
            keyword: state.keywords,
            status: state.selectOpt,
            pageNum: searchPage,
            pageSize: state.pageSize
        }
    }).then(res => {
        if (res.data.code === 200) {
            console.log(res.data);
            state.tableData = res.data.data.drugList;
            state.totalNum = res.data.data.totalNum;
            state.selected = true;
        }
    })
}

const toDrugItem = (id: number) => {
    router.push("/drug/" + id)
}
</script>

<style scoped>
:deep() .el-input__inner {
    height: 45px;
}
.search-container {
    padding: 15px 0;
    height: 50px;
    display: flex;
}

.searchBtn {
    height: 45px;
    width: 200px;
}
.drugs-container {
    width: 100%;
}
.card-item-container {
    margin: 5px 0px;
}

.card-item {
    display: flex;
}

.card-item-content {
    display: flex;
    align-items: flex-start;
    flex-direction: column;
    justify-content: space-between;
    margin-left: 10px;
}

.itemImage {
    /* display: flex; */
    width: 175px;
    height: 175px;
}

.btn-group {
    display: flex;
    flex-direction: row-reverse;
}

.page-container {
    justify-content: center;
    margin: 30px 0px;
}
</style>
