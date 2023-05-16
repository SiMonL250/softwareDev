<template>
    <div id="daterange">
        <n-form-item>
            <n-date-picker type="daterange" v-model:value="daterange" :is-date-disabled="disabledDate" />
        </n-form-item>

        <n-form-item>
            <div id="apply_button">
                <n-button attr-type="button" @click="applyEquipment()">确认</n-button>
            </div>
        </n-form-item>

    </div>
</template>

<script setup>
import { defineEmits, defineProps, ref } from "vue";
import { NDatePicker, NFormItem, NButton } from "naive-ui";
import localCache from "@/utils/localCache";
import { mMessage } from "@/utils/Message";
import { userPermission } from '@/store/userPermission';
import axiosApi from "@/utils/axiosapi";


const emit = defineEmits(["unshowSelector"]);
const father_data = defineProps({
    apply_data: {
        type: Object,
        data: null,
    },
});
const permiss = userPermission();

let datenow = new Date();
let year = datenow.getFullYear();
let mon = datenow.getMonth() + 1;
let day = datenow.getDate() + 1;

const daterange = ref([Date.now(), new Date(year + '-' + mon + '-' + day).getTime()]);

const disabledDate = function (d) {
    let datenow = new Date();
    let year = datenow.getFullYear();
    let mon = datenow.getMonth() + 1;
    let day = datenow.getDate();
    return d < new Date(year + '-' + mon + '-' + day).getTime();

};



const applyEquipment = function () {
    if (permiss.$state.show) {
        mMessage('错误！', 'error');
        return;
    }
    let time = new Date().getTime();
    let ekey = father_data.apply_data.ekey;
    const applybody = {
        applykey: time + localCache.getCache('username') + ekey,
        ekey: ekey,
        ename: father_data.apply_data.ename,
        applicant: localCache.getCache('username'),
        admin: null,
        applystatus: '待处理',
        applydate: time,
        processdate: null,
        datedue: daterange.value[1],
        datestart: daterange.value[0],
    }
    console.log('apply to use', applybody);
    
    new axiosApi('/apply','post',applybody,(res) => {
        const res_data = res.data;
        console.log('apply res', res_data);
        let ares = res_data.code == 200 ? 'success' : 'error';
        mMessage(res_data.msg, ares);
        emit("unshowSelector", false);
    }).useAxios();
}
</script>

<style scoped>
#apply_button {
    width: 100%;
    display: flex;
    justify-content: end;
    align-items: center;
}
</style>