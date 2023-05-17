<template>
    <div class="data_section">
        <div class="data_section_top">
            <n-form ref="formref" inline label-placement="left" :model="formValue" :rules="rules" :label-width="140">
                <n-form-item label="根据设备名称搜索：" path="filterStr" label-style="color:white">
                    <n-input type="text" v-model:value="formValue.filterStr" placeholder="输入设备名" class="queryinput" />
                </n-form-item>
                <n-form-item>
                    <n-button attr-type="button" @click="filterName(formValue.filterStr)" class="list_page_buttton">
                        搜索
                    </n-button>
                </n-form-item>
                <n-form-item>
                    <n-button attr-type="button" @click="unfilterName()" class="list_page_buttton">
                        刷新
                    </n-button>
                </n-form-item>
            </n-form>

            <div class="add_modal">
                <n-button class="list_page_buttton" @click="add_show_modal = true" v-show="permiss.show">添加设备</n-button>
                <n-modal v-model:show="add_show_modal">
                    <n-card title="Add" size="huge" style="width:650px">
                        <AddEquipment @unshowAddModal="unshowAddModal" />
                    </n-card>
                </n-modal>
            </div>
        </div>
        <n-data-table :columns="createColumns()" :data="table_data" :pagination="paginationReactive" bordered striped
            min-height="600" :loading="loading"/>
        <div>
            <n-modal v-model:show="edit_show_modal">
                <n-card title="Edit" size="huge" style="width:650px">
                    <EditPage :row_data="row_data" :edit_show_modal="edit_show_modal"
                        @unshowEditModal="unshowEditModal">
                    </EditPage>
                </n-card>
            </n-modal>
        </div>

        <div>
            <n-modal v-model:show="show_date_range_selector">
                <n-card title="选择使用期限" style="width:fit-content">
                    <ApplySelectDate :apply_data="{ekey:row_data.ekey,ename:row_data.ename}" 
                    :show_date_range_selector="show_date_range_selector"
                    @unshowSelector="unshowSelector"/>
                </n-card>
            </n-modal>
        </div>
    </div>
</template>
 
<script setup>
import { h, reactive, ref, } from 'vue';
import { NDataTable, NButton, NForm, NFormItem, NInput, NPopconfirm, NModal, NCard } from "naive-ui";
import AddEquipment from './AddEquipment.vue';
import EditPage from './EditPage.vue';
import ApplySelectDate from './ApplySelectDate.vue'
import axiosApi from '@/utils/axiosapi';
import { userPermission } from '@/store/userPermission';
import { mMessage } from '@/utils/Message';

const permiss = userPermission();
console.log('permiss', permiss.show);
//modal
const add_show_modal = ref(false);
const edit_show_modal = ref(false);
const row_data = ref(null);
const show_date_range_selector = ref(false);
//table column
const loading = ref(true);
const enameColumn = reactive({
    title: "设备名",
    key: "ename",
    filterMutiple: false,
    filterOptionValue: null,
    sorter(a, b) {
        return a.ename.localeCompare(b.ename, "zh");
    },
    filter(value, row) {
        return !!~row.ename.indexOf(value.toString());
    }

});

const admin_edit_action = {
    width: 15,
    align: 'center',
    render(row) {
        return h(
            NButton,
            {
                size: 'small',
                onClick: () => {
                    edit_show_modal.value = true;
                    row_data.value = row;
                }
            },
            { default: () => "edit" }
        )
    }

}
const admin_delete_action = {
    width: 24,
    align: 'center',
    render(row) {//row  is the object in this table row
        return h(
            NPopconfirm,
            {
                negativeText: '取消',
                positiveText: '确认',
                onPositiveClick: () => {
                    const key = row.ekey;
                    new axiosApi('/delete?ekey=' + key,'post',null,(res) => {
                        const delete_res = res.data
                        console.log('delete_res', delete_res);
                        if (delete_res.code === 200) {
                            mMessage(res.data.msg, 'success');
                            all_data();
                        } else {
                            mMessage(delete_res.msg, 'error');
                        }

                    }).useAxios();
                },//把要删除的元素传回后端，让后端删除。
                onNegativeClick: () => { console.log("no"); }
            },
            {
                trigger: () => h(NButton, { size: "small" }, { default: () => "delete" }),
                default: () => h('span', {}, { default: () => '确认删除？' })
            }
        );
    },
};
const student_apply_action = {
    width: 24,
    align: 'center',
    // eslint-disable-next-line no-unused-vars
    render: (row) => {
        return h(
            NButton,
            {
                size: 'small',
                onClick: () => {
                    if(row.estatus == '损坏'){
                        mMessage('设备已损坏，不可申请！','warning');
                        return;
                    }
                    show_date_range_selector.value = true;
                    row_data.value = row;
                }
            },
            { default: () => "申请使用" }
        )
    }
}

const actioncolumn = reactive({
    title: "操作",
    key: "actions",
    children: []
});
if (permiss.show) {
    actioncolumn.children.push(admin_edit_action);
    actioncolumn.children.push(admin_delete_action);
} else {
    actioncolumn.children.push(student_apply_action);
}


const createColumns = () => {
    return [
        enameColumn,
        {
            title: "设备序号",
            key: "ekey",
            sorter(a, b) { return a.ekey - b.ekey }
        },
        {
            title: "设备种类",
            key: "etype",
        },
        {
            title: "状态",
            key: "estatus",
            defaultFilterOptionValues: permiss.show ? ['正常', '损坏'] : null,
            filterOptions: permiss.show ? [
                {
                    label: '正常',
                    value: '正常'
                },
                {
                    label: '损坏',
                    value: '损坏'
                }
            ] : null,
            filter(value, row) {
                return ~row.estatus.indexOf(value)
            }
        },
        {
            title: "添加人员",
            key: "useraccount",
        },
        {
            title: "日期",
            key: "addtime",
            render(row) {
                return h(
                    'div',
                    {},
                    { default: () => row.addtime }
                )
            }
        },
        actioncolumn
    ]
};

const paginationReactive = reactive({
    pageSize: 10,
    showQuickJumper: true,
});

//table data
const table_data = ref([]);
const all_data = function () {
    new axiosApi('/getallequipment','get',null,(res) => {
            //表格数据
            const all_data = res.data.data;

            table_data.value = all_data ;
            if(table_data.value!=null) loading.value=false
            else mMessage('加载失败','error');
        }).useAxios();
}
all_data();

// query form
const formref = ref(null);
const formValue = ref({ filterStr: "" });
const rules = ref({
    filterStr: {
        require: true,
    }
});

//query method
function filterName(filterString) {
    console.log(filterString);
    enameColumn.filterOptionValue = filterString;
}
function unfilterName() {
    enameColumn.filterOptionValue = null;
    formValue.value.filterStr = null;
}

//modal
const unshowEditModal = function (param) {
    edit_show_modal.value = param;
}
const unshowAddModal = function (param) {
    add_show_modal.value = param;
}
const unshowSelector = function(param){
    show_date_range_selector.value = param;
}

</script>

<style scoped>
.list_page_buttton {
    color: white;
}

.data_section_top {
    display: flex;
    justify-content: space-between;
}
</style>

<style>
.n-layout-scroll-container {
    display: block;

}

.n-data-table {
    min-width: 620px;
}

.n-pagination .n-pagination-item {
    background-color: white;
}
</style>