import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import axios from 'axios';
import VueAxios from 'vue-axios';
import 'element-plus/dist/index.css';//element-plus style sheet
import {createPinia} from 'pinia'

const pinia = createPinia();

const app = createApp(App);
app.use(router,axios,VueAxios);
app.use(pinia);

app.mount('#app');



/*
{useraccount: 'admin1', password: '123456', userprivilege: 'admin'}
*/