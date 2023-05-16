<template>
    <div>
        <n-form :model="edit_model">
            <n-form-item path="edit_model" label="设备名称" :validation-status="notNullValidation(edit_model.ename)"
                :feedback="notNullFeedback(edit_model.ename)">
                <n-input v-model:value="edit_model.ename" placeholder="修改设备名称" />
            </n-form-item>

            <n-form-item path="edit_model" label="设备种类" :validation-status="notNullValidation(edit_model.etype)"
                :feedback="notNullFeedback(edit_model.etype)">
                <n-input v-model:value="edit_model.etype" placeholder="修改设备种类" />
            </n-form-item>

            <n-form-item path="edit_model" label="设备状态">
                <select name="eststus" id="estatus" class="n-input" v-model="edit_model.estatus"
                    style="height: 2.3em;text-align-last: center;">
                    <option value="正常">正常</option>
                    <option value="损坏">损坏</option>
                </select>
            </n-form-item>

            <n-form-item path="edit_model" label="添加人员" :validation-status="notNullValidation(edit_model.useraccount)"
                :feedback="notNullFeedback(edit_model.etype)">
                <n-input v-model:value="edit_model.useraccount" placeholder="添加人员" />
            </n-form-item>

            <n-form-item path="edit_model" label="添加日期">
                <n-input v-model:value="edit_model.addtime" type="date" placeholder="" />
            </n-form-item>

            <n-form-item>
                <n-button class="filter_buttton" attr-type="button" @click="editClick(edit_model)">确认</n-button>
            </n-form-item>
        </n-form>
    </div>
</template>
<script setup>
import { reactive, defineProps, defineEmits } from "vue";
import { NForm, NFormItem, NButton, NInput } from 'naive-ui';
import { dateFormat } from '@/utils/dateFormat';
import { notNullValidation, notNullFeedback } from "@/utils/notNull"
import { mMessage } from '@/utils/Message';
import axiosApi from "@/utils/axiosapi";

const emit = defineEmits(['unshowEditModal'])
const father_data = defineProps({
    row_data: {
        type: Object,
        data: null,
    },
});

const data = reactive(father_data.row_data);
console.log(father_data.row_data);
const edit_model = reactive({//声明表单键
    ename: data == null ? "" : data.ename,
    ekey: data == null ? "" : data.ekey,
    etype: data == null ? "" : data.etype,
    estatus: data == null ? "" : data.estatus, //添加设备时总是正常
    useraccount: data == null ? "" : data.useraccount,
    addtime: data == null ? null : dateFormat(new Date(data.addtime))
});

const editClick = function (m) {
    if (m.ekey == "") {
        mMessage("Null Equipment!!!", 'error')
    } else {
        console.log("Edit data", m);
        
        new axiosApi('/editequipment','post',m,(res) => {
            const edit_res = res.data;
            console.log(edit_res);
            if (edit_res.code == 200) {
                mMessage(edit_res.msg, 'success');
            } else {
                mMessage(edit_res.msg, 'error');
            }
        }).useAxios();

        emit("unshowEditModal", false);
    }
};
</script>
<!-- equipment object  //
    {
        "ename":"",
        "ekey":"",
        "etype":"",
        "estatus":"",//正常、损坏、
        "addtime":date,        
    }
 -->