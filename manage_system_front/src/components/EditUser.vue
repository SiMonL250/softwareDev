<template>
    <div>
        <n-form :model="edit_user_model">
            <div id="pre_text">
                <pre>{{ edit_user_model.useraccount }}</pre>
            </div>
            <n-form-item path="edit_user_model" label="password">
                <n-input v-model:value="edit_user_model.password"></n-input>
            </n-form-item>

            <n-form-item path="edit_user_model" label="userprivilege">
                <select v-model="edit_user_model.userprivilege" style="height: 2.3em;width: 100%;">
                    <option value="admin">admin</option>
                    <option value="student">student</option>
                </select>
            </n-form-item>

            <div id="edit_user_btns">
                <n-button @click="edit_user_click(edit_user_model)"> 确认 </n-button>
                <n-button @click="emit('unshowEditUserModal', false);"> 取消 </n-button>
            </div>
        </n-form>
    </div>
</template>
<script setup>
import { NForm, NFormItem, NButton, NInput } from 'naive-ui';
import { defineEmits, defineProps, reactive } from 'vue';
import {mMessage} from '@/utils/Message';
import axiosApi from '@/utils/axiosapi';

const emit = defineEmits(['unshowEditUserModal']);
const father_user = defineProps({
    row_user: {
        type: Object,
        data: null
    }
});

const data = reactive(father_user.row_user);
const edit_user_model = reactive({
    useraccount: data.useraccount,
    password: data.password,
    userprivilege: data.userprivilege
});

const edit_user_click = function (m) {
    console.log('edit_user_click', m);
    if (m.password != null) {

        new axiosApi('/changeaccount','post',m,(res)=>{
            const resdata = res.data;
            console.log('change user resdata',resdata);
            if(resdata.code == 200){
                mMessage('修改成功','success');
                emit('unshowEditUserModal', false);
            }else{
                mMessage(resdata.msg,'error');
            }
        }).useAxios();

    } else {
        mMessage('Password cannot be null');
    }
}
</script>

<style scoped>
#pre_text {
    height: 2.5em;
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
    font-size: large;
}

#edit_user_btns {
    display: flex;
    align-items: center;
    justify-content: space-between;
}</style>