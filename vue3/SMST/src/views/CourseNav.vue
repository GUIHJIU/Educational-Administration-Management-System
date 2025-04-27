<template>
  <h1>                                        </h1>
  <h1>                                        </h1>
  <header>
    <div class="wrapper" v-if="userstore.identity==='teacher'">
      <nav>
        <RouterLink to="/course/allcourse" class="nav-button">所有课程</RouterLink>
        <RouterLink to="/course/selectcourse" class="nav-button">选择课程</RouterLink>
        <RouterLink to="/course/addcourse" class="nav-button">添加课程</RouterLink>
        <RouterLink to="/course/updatecourse" class="nav-button">修改课程</RouterLink>
        <RouterLink to="/course/deletecourse" class="nav-button">删除课程</RouterLink>
      </nav>
    </div>
    <div class="wrapper" v-else-if="userstore.identity==='admin'">
      <nav>
        <RouterLink to="/course/allcourse" class="nav-button">所有课程</RouterLink>
        <RouterLink to="/course/selectcourse" class="nav-button">选择课程</RouterLink>
        <RouterLink to="/course/addcourse" class="nav-button">添加课程</RouterLink>
        <RouterLink to="/course/updatecourse" class="nav-button">修改课程</RouterLink>
        <RouterLink to="/course/deletecourse" class="nav-button">删除课程</RouterLink>
      </nav>
    </div>
    <div class="wrapper" v-else-if="userstore.identity==='student'">
      <nav>
        <RouterLink to="/course/allcourse" class="nav-button">所有课程</RouterLink>
        <RouterLink to="/course/selectcourse" class="nav-button">选择课程</RouterLink>

      </nav>
    </div>
    <div class="wrapper" v-else>
      <nav>
        <RouterLink to="/" class="nav-button">返回登录</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />
</template>
<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import {useridentitystore} from '@/store/userStore'
const userstore=useridentitystore()
</script>
<style scoped>
header {
  background-color: #0d5db9;
  padding: 10px 0; /* 上下内边距，用于增加按钮与屏幕边缘的距离 */
  text-align: center; /* 文本居中，使按钮在屏幕上方居中分布 */
}

h1 {
  background-color: #0d5db9;
  color: #d9d9d9;
  padding: 20px;
  text-align: center;
  border-radius: 10px;
}

.wrapper {
  display: flex; /* 使用flex布局 */
  justify-content: space-around; /* 使按钮在水平方向上均匀分布 */
  width: 100%; /* 宽度设置为100%，确保按钮分布在整个屏幕宽度上 */
}

.nav-button {
  background-color: #ffffff; /* 白色背景 */
  color: #0d5db9; /* 文本颜色与header背景相同，形成对比 */
  padding: 10px 20px; /* 按钮内边距 */
  text-decoration: none; /* 去除链接的下划线 */
  border: none; /* 去除按钮边框 */
  cursor: pointer; /* 鼠标悬停时显示为手型 */
  font-size: 16px; /* 字体大小 */
  border-radius: 8px; /* 增加圆角 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 添加阴影 */
  transition: background-color 0.3s ease, box-shadow 0.3s ease; /* 添加背景颜色和阴影的过渡效果 */
}

.nav-button:hover {
  background-color: #e0e0e0; /* 鼠标悬停时背景变为浅灰色 */
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* 增加阴影强度 */
}
</style>