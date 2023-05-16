import { defineStore } from "pinia";


export const msidebarColllapsed=defineStore('sidbar',{
        state:()=>{
            return {
                collapsed:240
            }
        },
        actions:{
            handleSet(islogin){
                this.collapsed = islogin;
            },
            handleGet(){
                return this.collapsed;
            }
        }
        
    })
