import localCache from '@/utils/localCache'
import {defineStore} from 'pinia'
const defaultList={
    'admin':['1','2'],
    'student':['2']
}
export const userPermission = defineStore('permiss',{
    state:()=>{
        const role = localCache.getCache('role');
        return {
            role:role? role:'',//userprilege
            show:role=='admin'?true:false,
            key:role ? defaultList[role] : [],//permiss list;
        }
    },
    actions:{
        handleSet(val){
            this.role = val;
            this.key = defaultList[val];
            if(val == 'admin'){
                this.show = true;
            }else{
                this.show = false;
            }
        },
        gethandle(){
            console.log('userPermission',this.show);
        }
    }
})