<template>
    <div>
        <h1 style="color: white;"> 用户管理</h1>
        <n-data-table :columns="users_table_columns" :data="user_table_data" :pagination="paginationReactive" striped :loading="loading"
        style="min-height: 606px;"/>
    </div>
    <n-modal v-model:show="show_edit_user_modal">
        <n-card style="width: 360px" size="huge" aria-modal="true">
            <EditUser :row_user="row_user" :show_edit_user_modal="show_edit_user_modal"
                @unshowEditUserModal="unshowEditUserModal" />
        </n-card>
    </n-modal>
</template>

<script setup>
import { NDataTable, NButton, NPopconfirm, NModal, NCard } from 'naive-ui';
import { h, reactive, ref } from 'vue';
import EditUser from '@/components/EditUser.vue'
import {mMessage} from '@/utils/Message';
import localCache from '@/utils/localCache';
import axiosApi from '@/utils/axiosapi';

const show_edit_user_modal = ref(false);
const loading = ref(true);
const row_user = ref(null);
const users_table_actioncolumns = reactive({

    title: "操作",
    key: "actions",
    align: 'center',
    children: [
        {
            width: 15,
            align: 'center',
            // eslint-disable-next-line no-unused-vars
            render(row) {
                return h(
                    NButton,
                    {
                        size: 'small',
                        onClick: () => {
                            show_edit_user_modal.value = true;
                            row_user.value = row;
                        }
                    },
                    { default: () => "edit" }
                )
            }

        },
        {
            width: 24,
            align: 'center',
            render(row) {//row  is the object in this table row
                return [

                    h(
                        NPopconfirm,
                        {
                            negativeText: '取消',
                            positiveText: '确认',
                            onPositiveClick: () => { 
                                let useraccount = row.useraccount;
                                if(useraccount == localCache.getCache('username')){
                                    mMessage('不能删除自己','error');
                                    return;
                                }
                                new axiosApi('/deleteuser/?useraccount='+useraccount,'post',null,(res)=>{
                                    let res_data = res.data;
                                    console.log(res_data);
                                    let mtype = res_data.code==200?'success':'error';
                                    mMessage(res_data.msg,mtype);
                                    get_alluser();
                                }).useAxios();//把要删除的元素传回后端，让后端删除。
                            },
                            onNegativeClick: () => { console.log("no"); }
                        },
                        {
                            trigger: () => h(NButton, { size: "small" }, { default: () => "delete" }),
                            default: () => h('span', {}, { default: () => '确认删除？' })
                        }
                    )
                ];
            },
        },

    ]

}
);
const users_table_columns = [
    {
        title: "账号",
        key: "useraccount"
    },
    {
        title: "密码",
        key: "password"
    },
    {
        title: "权限",
        key: "userprivilege",
        defaultFilterOptionValues: ['admin', 'student','teacher'],
        filterOptions: [
            { label: "admin", value: 'admin' },
            { label: "teacher", value: 'teacher' },
            { label: "student", value: 'student' },
        ],
        filter(value, row) {
            return ~row.userprivilege.indexOf(value)
        }

    },
    users_table_actioncolumns
];
const paginationReactive = reactive({
    pageSize: 10,
    showQuickJumper: true,

});

const user_table_data = ref([]);

const get_alluser = function () {
    new axiosApi('/getalluser','get',null,(res) => {
            user_table_data.value = res.data.data;
            console.log(user_table_data);
            if(user_table_data.value!=null) loading.value=false;
            else mMessage('加载失败','error');
        }).useAxios();
}
get_alluser();
const unshowEditUserModal = function (param) {
    show_edit_user_modal.value = param;
    get_alluser();
}
</script>

<style scoped></style>