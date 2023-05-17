import { createRouter, createWebHashHistory } from "vue-router";
import HomePage from "@/components/HomePage";
import EquipmentList from "@/components/EquipmentList";
import LogPage from "@/components/LogPage";
import ManageUser from "@/components/ManageUser";
import ApplyPage from '@/components/ApplyPage';
import PageNoPermiss from '@/components/PageNoPermiss';
import localCache from "@/utils/localCache";
import {userPermission} from '@/store/userPermission';

//permiss:admin['1','2'] student:['2']
const routes = [
  {
    path: "/Login",
    component: LogPage,
  },
  {
    path: "/403",
    component: PageNoPermiss,
  },
  {
    path: "/HomePage",
    component: HomePage,
    meta:{permiss:'3'}
  },
  {
    path: "/",
    redirect:'/HomePage',
    hidden:true,
    meta:{permiss:'3'}
  },
  {
    path: "/EquipmentList",
    component: EquipmentList,
    meta:{permiss:'3'}
  },
  {
    path: "/applyrecord",
    component: ApplyPage,
    meta:{permiss:'3'}
  },
  {
    path: "/ManageUser",
    component: ManageUser,
    meta:{permiss:'1'}
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});
// eslint-disable-next-line no-unused-vars
router.beforeEach((to,from,next)=>{
  const role =localCache.getCache('role');
  const permiss = userPermission();
  if(!role && to.path!='/Login'){
    next('/Login')
  }else if(to.meta.permiss && !permiss.key.includes(to.meta.permiss)){
    // 如果没有权限，则进入403
    console.log('?',permiss.key);
    next('/403');
  }else{
    next();
  }
})
export default router;
