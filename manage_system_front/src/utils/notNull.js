export function notNullValidation(value) {
    return value == "" ? "error" : "success";
}
export function notNullFeedback(value){
    if(value=="" || value==null){
        return "不能为空！"
    }else{
        return;
    }
}