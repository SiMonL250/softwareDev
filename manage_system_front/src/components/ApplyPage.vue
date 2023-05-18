<template>
    <div id="admin_cansee" v-if="permiss.$state.show">
        <div id="waiting">
            <p style="margin-top: -10px;">待处理</p>
            <el-table stripe :data="table_data_wait" height="355" >
                <el-table-column v-for="i in table_column_wait" :key="i"  :prop="i.prop" :label="i.label"/>
                <el-table-column label="操作" width="160">
                    <template #default="scope">
                        <el-button size="small" @click="processClick(scope.row, '已同意')">同意</el-button>
                        <el-button size="small" @click="processClick(scope.row, '已拒绝')">拒绝</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <div id="processed">
            <p>已处理</p>
            <el-table stripe height="350" :data="table_data_processed" >
                <el-table-column v-for="i in table_column_processed" :key="i" :prop="i.prop" :label="i.label"  
                :filters="i.filter" :filter-method="i.filter_method"/>
                <el-table-column label="操作" >
                    <template #default="scope">
                        <el-button size="small" @click="deleteRecord(scope.row.applykey)">删除</el-button>

                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
    <div id="studentapply" v-if="!permiss.$state.show">
        <p>我的申请</p>
        <el-table stripe height="550" :data="table_data_processed" >
            <el-table-column v-for="i in table_column_processed" :key="i" :prop="i.prop" :label="i.label" 
            :filters="i.filter" :filter-method="i.filter_method"/>
        </el-table>
    </div>
</template>

<script setup>
import { ref } from "vue";
import { ElTable, ElTableColumn, ElButton } from 'element-plus';
import { userPermission } from '@/store/userPermission'
import localCache from "@/utils/localCache";
import { mMessage } from "@/utils/Message";
import axiosApi from '@/utils/axiosapi';

const permiss = userPermission();

//根据applystatus显示表格
const table_column_wait = [//到时候 admin 直接本地获取就好了； 对于student的页面，未处理就没有admin和processdate
    { prop: 'ekey', label: '设备序号' },
    { prop: 'ename', label: '设备名称' },
    { prop: 'applicant', label: '申请人' },
    { prop: 'applystatus', label: '状态' },
    { prop: 'applydate', label: '申请日期' },
    { prop: 'datestart', label: '开始时间' },
    { prop: 'datedue', label: '到期时间' },

];
const table_column_processed = [
    { prop: 'ekey', label: '设备序号' },
    { prop: 'ename', label: '设备名称' },
    { prop: 'applicant', label: '申请人' },
    { prop: 'admin', label: '处理人' },
    {
        prop: 'applystatus', label: '状态',
        filter: [{ text: '已同意', value: '已同意' }, { text: '已拒绝', value: '已拒绝' },{ text: '待处理', value: '待处理' },{ text: '已过期', value: '已过期' }],
        filter_method:(value,row)=>{return row.applystatus == value;},
    },
    { prop: 'applydate', label: '申请日期' },
    { prop: 'datestart', label: '开始时间' },
    { prop: 'datedue', label: '到期时间' },
    { prop: 'processdate', label: '处理日期' }
];

const table_data_wait = ref([]);
const table_data_processed = ref([]);

const getallapply = function () {
    new axiosApi('/getallapply', 'get', null, (res) => {
        let table_data = [];
        table_data = res.data.data;

        if (permiss.$state.show) {
            table_data_wait.value = table_data.filter((item) => {
                return item.applystatus == '待处理';
            });

            table_data_processed.value = table_data.filter((item) => {
                return item.applystatus != '待处理';
            });
        } else {
            const student = localCache.getCache('username');
            table_data_processed.value = table_data.filter((item) => {
                return item.applicant == student;
            });
        }
    }).useAxios();
}
getallapply();

const processClick = function (row, status) {

    row.applystatus = status;
    row.admin = localCache.getCache('username');
    console.log('afterprocess', row);

    new axiosApi('/processapply', 'post', row, (res) => {
        const res_data = res.data;

        if (res_data.code == 200) {
            mMessage(res_data.msg, 'success');
            getallapply();
        } else {
            mMessage(res_data.msg, 'error');
        }

    }).useAxios();
}

const deleteRecord = function (applykey) {
    console.log(applykey);
    new axiosApi('/deleterecord/?applykey=' + applykey, 'post', null, (res) => {
        let msgt = res.data.code == 200 ? 'success' : 'error';
        mMessage(res.data.msg, msgt);
        getallapply();
    }).useAxios();
}
</script>

<style scoped>
p {
    font-size: 20px;
    font-family: '微软雅黑';
    color: aliceblue;
    display: flex;
    align-items: center;
    justify-content: left;
    margin: 4px;

}
</style>