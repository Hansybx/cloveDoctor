<template>
    <div class="charts-container">
        <div id="drugs" class="chart"></div>
        <div id="user" class="chart"></div>
    </div>
</template>

<script setup lang='ts'>
import axios from 'axios';
import * as echarts from 'echarts';
import { onBeforeMount, onMounted, reactive } from 'vue';
import Constant from '../../../common/config';
import DrugData from './drugData';
import UserData from './userData';

type EChartsOption = echarts.EChartsOption;

onMounted(() => {
    getChartData();
})

const state = reactive({
    drugData: [] as DrugData[],
    userData: {} as UserData,
})

const getChartData = () => {
    axios.get(Constant.BASE_URL_ADMIN + '/chart').then(res => {
        if (res.data.code === 200) {
            console.log(res.data);
            state.drugData = res.data.data.drugData;
            state.userData = res.data.data.userData;
            drawTopDrugs()
            drawUserChart()
        }
    })
}

const drawTopDrugs = () => {

    const chartDom = document.getElementById('drugs')!;
    const topChart = echarts.init(chartDom);
    let topOption: EChartsOption;

    topOption = {
        title: {
            text: '总销售量前五的药品',
            left: 'center'
        },
        dataset: [
            {
                // dimensions: ['name', 'age', 'profession', 'score', 'date'],
                // source: [
                //     ['Hannah Krause', 41, 'Engineer', 379, '2011-02-12'],
                //     ['Zhao Qian', 20, 'Teacher', 351, '2011-03-01'],
                //     ['Jasmin Krause ', 52, 'Musician', 287, '2011-02-14'],
                //     ['Li Lei', 37, 'Teacher', 219, '2011-02-18'],
                //     ['Karle Neumann', 25, 'Engineer', 253, '2011-04-02'],
                //     ['Adrian Groß', 19, 'Teacher', '-', '2011-01-16'],
                //     ['Mia Neumann', 71, 'Engineer', 165, '2011-03-19'],
                //     ['Böhm Fuchs', 36, 'Musician', 318, '2011-02-24'],
                //     ['Han Meimei', 67, 'Engineer', 366, '2011-03-12']
                // ]
                source: state.drugData
            },
            {
                transform: {
                    type: 'sort',
                    config: { dimension: 'sold', order: 'desc' }
                }
            }
        ],
        xAxis: {
            type: 'category',
            axisLabel: { interval: 0, rotate: 30 }
        },
        yAxis: {
        },
        series: {
            type: 'bar',

            encode: { x: 'drugName', y: 'sold' },
            datasetIndex: 1
        }
    };

    topOption && topChart.setOption(topOption);
}

const drawUserChart = () => {
    var chartDom = document.getElementById('user')!;
    var myChart = echarts.init(chartDom);
    var option: EChartsOption;

    option = {
        title: {
            text: '本日新增用户与已有用户数之比',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        series: [
            {
                name: 'Access From',
                type: 'pie',
                radius: '50%',
                data: [
                    { value: state.userData.today, name: '本日新增用户数' },
                    { value: state.userData.before, name: '已有用户数' },
                ],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    option && myChart.setOption(option);
}
</script>

<style scoped>
.charts-container {
    display: flex;
}
.chart {
    height: 75vh;
    width: 45vw;
}
</style>
