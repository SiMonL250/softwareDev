<template>
    <!-- n-form 收集equipmentInfo 再生成当前的Date() 对象进行封装 -->
    <div class="form_box">
        <n-form id="add_form" :model="equipmentInfo_model" :rules="form_rules" :inline=false>
            <n-form-item label="设备名称：" path="ename">
                <n-input type="text" v-model:value="equipmentInfo_model.ename" placeholder="请输入设备名" clearable />
            </n-form-item>

            <n-form-item label="设备序号：" path="ekey" first>
                <n-input v-model:value="equipmentInfo_model.ekey" placeholder="请输入设备序号" :clearable="false"
                    :allow-input="onlyAllowNumber" />
            </n-form-item>

            <n-form-item label="设备种类：" path="etype">
                <n-input type="text" v-model:value="equipmentInfo_model.etype" placeholder="请输入设备种类" clearable />
            </n-form-item>

            <n-form-item label="添加日期：" path="addtime">
                <n-input type="date" v-model:value="date_ref" :placeholder="null" />
            </n-form-item>

            <n-form-item>
                <n-button @click="buttonSubmmit(equipmentInfo_model, date_ref)">
                    确认
                </n-button>
            </n-form-item>
        </n-form>
    </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';
import { NForm, NFormItem, NButton, NInput } from "naive-ui";
import axiosApi from '@/utils/axiosapi';
import localCache from '@/utils/localCache';
import { mMessage } from '@/utils/Message';

const emit = defineEmits(["unshowAddModal"])
/*equipment object  //
    {
        "ename":"",
        "ekey":"",
        "etype":"",
        "estatus":"",//正常、损坏、
        "addtime":date,        
    }
*/

const equipmentInfo_model = ref({//声明表单键
    ename: "",
    ekey: "",
    etype: "",
    estatus: "正常", //添加设备时总是正常,
    useraccount: localCache.getCache('username'),
    addtime: null
});
const date_ref = ref(null); //声明表单对象

const form_rules = {
    ename: [
        {
            required: true,
            message: "需要设备名称",
            trigger: ["input", "blur"]
        }
    ],
    ekey: [
        {
            required: true,
            message: "需要设备序号",
            trigger: ["input", "blur"]
        }
    ],
    etype: [
        {
            required: true,
            message: "需要设备种类",
            trigger: ["input", "blur"]
        }
    ],
};
const onlyAllowNumber = function (value) { return !value || /^\d+$/.test(value); }
function buttonSubmmit(m, date) {
    // console.log("m", m.ename == "");
    if (date != null) {
        m.addtime = new Date(date).getTime();
    }
    if (m == null) {
        mMessage("null object", 'warning');
    } else if (m.ename == "" || m.ekey == "" || m.type == "") {
        mMessage("some option(s) null", 'warning');
    } else {
        // data.push(m);
        console.log("data", m);
        new axiosApi('/addequipment', 'post', m, (res) => {
            console.log(res.data);
            if (res.data.code == 200) {
                emit("unshowAddModal", false);
            }
        }).useAxios();
    }

}

</script>
<style scoped>
#add_form {
    display: block;
}
</style>