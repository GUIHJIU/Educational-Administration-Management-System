import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

import IdView from '../views/IdView.vue' // 修改导入名称以匹配文件名
import LoginView from '../views/LoginView.vue'
import mainViews from "../views/interface/mainViews.vue";



const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: LoginView // 使用正确的文件名
    },
    {
    path:'/interface',
      component:mainViews
    }
  ]
})

export default router