<template>
    <div class="home_section">
        <div>
            <div class="title">
                <p class="chart_title">设备种类统计</p>
            </div>
            <div id="etype_bar" :style="{ height: '350px', width: '900px', }"></div>
        </div>

        <div v-if="permiss.$state.show">
            <div class="title">
                <p class="chart_title" style="margin-top: 8px;">近六月增量统计</p>
            </div>

            <div id="increase_month" :style="{ height: '350px', width: '900px', }"></div>
        </div>

        <div v-if="!permiss.$state.show">
            <div class="title">
                <p class="chart_title" style="margin-top: 8px;">我的申请统计</p>
            </div>
            <div id="my_apply_charts" style="display: flex;">
                <div id="myapplystatus" class="my_apply"></div>
                <div id="myapplyetype"  class="my_apply"></div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { initEtypeBar, initLineCahrt,initMyApply } from "@/utils/initCharts";
import axiosApi from '@/utils/axiosapi';
import { userPermission } from '@/store/userPermission'
import localCache from "@/utils/localCache";

const permiss = userPermission();
console.log(permiss.$state.show);
//bar
new axiosApi('/bardata', 'get', null, (res) => {
    const bar_data = res.data.data;
    initEtypeBar(bar_data);
}).useAxios();

if (permiss.$state.show) {
    new axiosApi('/lineincrease', 'get', null, (res) => {
        const res_data = res.data;

        initLineCahrt(res_data.data);
    }).useAxios();
}

if (!permiss.$state.show){
    new axiosApi('/myapplychart?student='+localCache.getCache('username'), 'get', null, (res) => {
        const res_data = res.data;
        const d1 = res_data.data.myApplystatusCounts;
        const d2 = res_data.data.myApplyEtypeCounts

        initMyApply(d1,"myapplystatus",'申请状态');
        initMyApply(d2,"myapplyetype",'申请过的设备种类');
    }).useAxios();
}


window.onerror = (message) => {
    console.log(message);
}
</script>

<style scoped>
.home_section{
    width: fit-content;
}
.title {
    display: flex;
    align-items: center;
    justify-content: center;
}

.chart_title {
    font-family: "宋体";
    font-size: 150%;
    font-weight: 700;
    color: aliceblue;

}

#etype_bar {
    min-width: 900PX;
}

.my_apply{
    margin: 2px;
    height: 350px;
    width: 448px;
}

</style>