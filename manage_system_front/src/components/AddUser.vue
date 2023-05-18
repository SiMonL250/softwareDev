<template>
    <div>
        <n-form :model="formValue" :label-width="80">
            <div style="display: flex; height: 36px;">

                <div class="radio_userprivilege">
                    <input class="radio_input" name="userprivilege" type="radio" id="student"
                        v-model="formValue.userprivilege" value="student" checked>
                    <label for="student" style="color: black; font-size: large;">student</label>
                </div>

            </div>
            <n-form-item path="formValue.useraccount" label="账号" label-style="color:balck">
                <n-input v-model:value="formValue.useraccount" placeholder="请输入账号" />
            </n-form-item>

            <n-form-item :path="formValue.password" label="密码" label-style="color:black">
                <n-input v-model:value="formValue.password" placeholder="请输入密码" type="password" />
            </n-form-item>

            <div class="login_register_buttons">
                <n-button style="color: black;" @click="registerClick(formValue)"> 确认 </n-button>
            </div>
        </n-form>
    </div>
</template>

<script setup>
import { NForm, NFormItem, NInput, NButton } from 'naive-ui';
import { ref,defineEmits } from 'vue';
import { mMessage } from '@/utils/Message';
import axiosApi from '@/utils/axiosapi';

const formValue = ref(
    {
        useraccount: '',
        password: '',
        userprivilege: 'student'
    });

const emit = defineEmits(['regist'])
// eslint-disable-next-line no-unused-vars
function registerClick(m) {
    if (m.useraccount == "") {
        mMessage("Null Account", 'warning');
        return;
    }
    if(m.password == ''){
        mMessage("Null Password", 'warning');
        return;
    }
    new axiosApi('/register', 'post', m, (response) => {
        let code = response.data.code;
        let ares = code == 200 ? 'success' : 'error';
        emit("regist", false);
        mMessage(response.data.msg, ares);

    }).useAxios();
}
</script>

<style lang="scss" scoped></style>