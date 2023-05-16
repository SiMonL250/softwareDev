<template>
  <div id="top_box">
    <div class="logoAndtitle">
      <img src="@/assets/images/logo.png" alt="logo" class="logo">
      <p>实验室设备管理系统</p>
    </div>
    <div class="avatar">
      <n-dropdown trigger="click" :options="dropdown_options">
        <n-avatar round size="large" src="https://www.naiveui.com/assets/naivelogo-93278402.svg" alt="avatar"></n-avatar>
      </n-dropdown>
      <label id="uasr_name">{{ localCache.getCache('username') }}</label>
    </div>
  </div>

  <div id="operate_box">
    <n-layout has-sider ref="n_Layout_width">
      <n-layout-sider collapse-mode="width" :collapsed-width="87" :width="mcollapsed.handleGet()" :show-collapsed-content="true"
        :show-trigger="showTrigger()" content-style="padding: 12px;color:white;" bordered >
        <n-menu inverted :collapsed-width="64" :collapsed-icon-size="22" :options="getmenuOptions()" disabled-field />
      </n-layout-sider>

      <n-layout-content content-style="padding: 24px;" show-trigger="arrow-circle">
        <router-view />
      </n-layout-content>
    </n-layout>
  </div>
  <footer style="display: flex;align-items:center;justify-content: center;">
    <hr>
    <em>2000301819林树民-GUET-软件开发课设</em>
  </footer>
</template>

<script setup>
import { NLayout, NLayoutSider, NLayoutContent, NAvatar, NMenu, NDropdown, NButton} from "naive-ui";
import { h, onMounted } from "vue";
import { NIcon } from "naive-ui";
import {
  BookOutline as BookIcon,
  Home, List
} from "@vicons/ionicons5";
import { RouterLink, RouterView } from "vue-router";
import localCache from "@/utils/localCache";
import {msidebarColllapsed} from '@/store/sidebarCollapsed'

const mcollapsed = msidebarColllapsed();
console.log('collapsed',mcollapsed.handleGet());
console.log('username', localCache.getCache('username'));
function renderIcon(icon) {
  return () => h(NIcon, null, { default: () => h(icon) })
}
function showTrigger(){
  return mcollapsed.handleGet()==0?false:'arrow-circle';
}
const getmenuOptions = function () {

  const menuOptions = [//HomePage、EquipmentList 所有用户可见
    {
      label: () => h(
        RouterLink,
        {
          to: "HomePage",
          params: "HomePage"
        },
        { default: () => "主页" }
      ),
      key: "HomePage",
      icon: renderIcon(Home)
    },

    {//
      label: () => h(
        RouterLink,
        {
          to: "EquipmentList",
          params: "EquipmentList"
        },
        { default: () => "设备列表" }
      ),
      key: "EquipmentList",
      icon: renderIcon(List)
    },
  ];

  const admin_cansee = [
    {
      label: () => h(
        RouterLink,
        {
          to: "applyrecord",
          params: "applyrecord"
        },
        { default: () => "申请列表" }
      ),
      key: "ApplyRecord",
      icon: renderIcon(BookIcon)
    },
    {
      label: () => h(
        RouterLink,
        {
          to: "ManageUser",
          params: "ManageUser"
        },
        { default: () => "管理用户" }
      ),
      key: "AddEquipment",
      icon: renderIcon(BookIcon)
    }
  ];

  menuOptions.push.apply(menuOptions, admin_cansee);

  return menuOptions;
}


const dropdown_options = [
  {
    label: () => h(
      'div', { class: "sss", },
      h(
        RouterLink, { to: "Login", params: "Login" },
        () => h(
          NButton,
          {
            size: 'medium',
            onClick: () => {
              console.log("注销登录");
              localCache.deleteCache('role');
              localCache.deleteCache('username');
              mcollapsed.handleSet(0);
              document.querySelector("#uasr_name").innerHTML = "null";
            }
          },
          { default: () => "注销登录" }
        )
      )

    )
  }
];


onMounted(() => {
  const e = window.Error;
  // eslint-disable-next-line no-unused-vars
  window.onerror = function (message, source, line, column, error) {
    console.warn(message, source, line, column, error)
    if (message === 'ResizeObserver loop limit exceeded') {
      return false;
    } else {
      return e(...arguments)
    }
  };

})


/* resizeobserver loop limit exceeded */
const debounce = (fn, delay) => {
  let timer = null;
  return function () {
    let context = this;
    let args = arguments;
    clearTimeout(timer);
    timer = setTimeout(function () {
      fn.apply(context, args);
    }, delay);
  }
}

const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
  constructor(callback) {
    callback = debounce(callback, 16);
    super(callback);
  }
}
  /* resizeobserver loop limit exceeded */

</script>
<style scoped>
#top_box {
  width: 100%;
  height: 63px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: rgb(34, 36, 36);
  user-select: none;
}

.logoAndtitle {
  height: 36px;
  display: flex;
  align-items: center;

}

.logo {
  height: 32px;
  width: 32px;
  margin: 4px;
}

p {
  color: white;
  font-size: large;
  font-family: "Gill Sans", sans-serif;
}

.avatar {
  width: 135px;
  margin: 2px;
  margin-right: 25px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.avatar>#uasr_name {
  color: aliceblue;
  margin-left: 10px;
}


</style>
<style>
/* global style*/
* {
  margin: 0%;
  padding: 0%;
}
body{
  overflow: hidden;
}
/* global style */
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  overflow: hidden;
}
#operate_box,
.n-layout-sider,
.n-layout-content {
  height: 90vh;
}

.n-layout-sider {
  background-color: rgb(49, 54, 54);
}

.n-layout-content,
#operate_box {
  background-color: rgb(71, 77, 77);
}
</style>