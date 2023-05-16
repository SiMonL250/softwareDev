import {ElMessage} from 'element-plus';

const mMessage = function(msg,type){
    ElMessage({
        message:msg,
        type:type,
        duration:2000
    });
}
export  {
    mMessage
}