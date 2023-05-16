<template>
    <div class="home_section">
        <div>
            <div class="title">
                <p class="chart_title">设备种类统计</p>
            </div>
            <div id="etype_bar" :style="{ height: '350px',width:'900px', }"></div>
        </div>

        <div v-if="permiss.$state.show">
            <div class="title">
                <p class="chart_title" style="margin-top: 8px;">近六月增量统计</p>
            </div>

            <div id="increase_month" :style="{ height: '350px',width:'900px', }"></div>
        </div>
    </div>
</template>

<script setup>
import { initEtypeBar, initLineCahrt } from "@/utils/initCharts";
import axiosApi from '@/utils/axiosapi';
import { userPermission } from '@/store/userPermission'

const permiss = userPermission();
//bar
new axiosApi('/bardata', 'get', null, (res) => {
    const bar_data = res.data.data;
    initEtypeBar(bar_data);
}).useAxios();

new axiosApi('/lineincrease','get',null,(res)=>{
    const res_data = res.data;

    initLineCahrt(res_data.data);
}).useAxios();


window.onerror = (message) => {
    console.log(message);
}
</script>

<style scoped>
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

.dynamic_render_cookie_chart {
    margin-top: 30px;
}

#pies_box {
    min-width: calc(3 * 310px);
    display: grid;
    grid-template-columns: repeat(3, 33.3%);
    grid-auto-rows: 310px;
}

.pies {
    margin: 1px;
    position: relative;
}
</style>