<template>
    <div id="log_form">
        <div id="log_box">
            <!-- Input elements should have autocomplete  -->
            <n-form ref="formRef" :model="formValue" :rules="rules" :label-width="80">
                <div style="display: flex;">
                    <div class="radio_userprivilege">
                        <input class="radio_input" name="userprivilege" type="radio" id="admin"
                            v-model="formValue.userprivilege" value="admin">
                        <label for="admin" style="color: white;">admin</label>
                    </div>
                    <div class="radio_userprivilege">
                        <input class="radio_input" name="userprivilege" type="radio" id="student"
                            v-model="formValue.userprivilege" value="student">
                        <label for="student" style="color: white;">student</label>
                    </div>
                </div>
                <n-form-item path="formValue.useraccount" label="账号" label-style="color:white"
                    :validation-status="notNullValidation(formValue.useraccount)"
                    :feedback="notNullFeedback(formValue.useraccount)">
                    <n-input v-model:value="formValue.useraccount" placeholder="请输入账号" />
                </n-form-item>

                <n-form-item :path="formValue.password" label="账号" label-style="color:white"
                    :validation-status="notNullValidation(formValue.password)"
                    :feedback="notNullFeedback(formValue.password)">
                    <n-input v-model:value="formValue.password" placeholder="请输入密码" type="password" />
                </n-form-item>

                <div class="login_register_buttons">
                    <n-button style="color: white;" @click="loginClick(formValue)"> 登录 </n-button>
                    <n-button style="color: white;" @click="registerClick(formValue)"> 注册 </n-button>
                </div>
            </n-form>
        </div>
    </div>
</template>

<script setup>
import axiosApi from '@/utils/axiosapi';
import { NForm, NFormItem, NInput, NButton } from 'naive-ui';
import { ref } from 'vue';
import { notNullValidation, notNullFeedback } from "@/utils/notNull";
import localCache from '@/utils/localCache';
import { useRouter } from 'vue-router';
import { userPermission } from '@/store/userPermission';
import { msidebarColllapsed } from '@/store/sidebarCollapsed'
import { mMessage } from '@/utils/Message';

const permiss = userPermission();
const mcolllapased = msidebarColllapsed();
const router = useRouter();
const formRef = ref(null);
const formValue = ref(
    {
        useraccount: '',
        password: '',
        userprivilege: ''
    });

const rules = {
    useraccount: {
        required: true,
        message: "请输入账号",
        trigger: "blur"
    },
    password: {
        required: true,
        message: "请输入密码",
        trigger: "blur"
    }
};
function loginClick(m) {
    if (m.useraccount == "") {
        mMessage("Null Account", 'warning');
        return;
    }
    if (m.userprivilege == "") {
        mMessage("Null Privilege", 'warning');
        return;
    }
    if (m.password == "") {
        mMessage("Null Password", 'warning');

        return;
    }
    //登录接口在这用,跨域
    console.log(m);
    new axiosApi('/login/?useraccount=' + m.useraccount + '&password=' + m.password + '&userprivilege=' + m.userprivilege,
        'get', null, (response) => {
            let data = response.data;
            if (data.code == -100) {
                mMessage(data.msg, 'error');
                return;
            }
            if (data.code == 200) {
                mMessage('登陆成功!', 'success');

                const user_present = data.data;
                localCache.setCache("username", user_present.useraccount);
                localCache.setCache("role", user_present.userprivilege);
                const role = localCache.getCache('role');
                permiss.handleSet(role);
                mcolllapased.handleSet(240);
                document.querySelector('#uasr_name').innerHTML = user_present.useraccount;

                router.push('/');
            }
        }).useAxios();
}
function registerClick(m) {
    if (m.useraccount == "") {
        mMessage("Null Account", 'warning');
        return;
    }
    new axiosApi('/register', 'post', m, (response) => {
        let code = response.data.code;
        let ares = code == 200 ? 'success' : 'error';
        mMessage(response.data.msg, ares);

    }).useAxios();
}

</script>
<style scoped>
#log_form {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
}

.login_register_buttons {
    display: flex;
    justify-content: space-between;
}

#log_box {
    width: 360px;
    height: 360px;
}

.radio_userprivilege {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 6px;
    font-size: large;
}

.radio_input {
    height: 18px;
    width: 18px;
}

.radio_input:checked {
    transform: scale(1.15);
}

input[type="radio"]::before {
    position: relative;
    content: "";
    top: -1px;
    left: -1px;
    width: 17px;
    height: 17px;
    display: block;
    border-radius: 50%;
    background-color: #fff;
    border: 1px solid green;
    z-index: 5;
}

input[type="radio"]:checked::after {
    position: relative;
    content: "";
    bottom: 15px;
    left: 4px;
    width: 9px;
    height: 9px;
    display: block;
    border-radius: 50%;
    visibility: visible;
    background-color: green;
    z-index: 6;
}
</style>