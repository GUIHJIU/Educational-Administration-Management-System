import './assets/main.css'

import { createApp } from 'vue'
import App from  './App.vue'
import router from './router'
import '@icon-park/vue-next/styles/index.css'; // 引入样式

import * as IconPark from '@icon-park/vue-next';

const app = createApp(App)

app.use(router)

app.mount('#app')
