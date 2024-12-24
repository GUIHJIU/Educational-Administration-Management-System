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
<!--        <RouterLink to="/course/addcourse" class="nav-button">添加课程</RouterLink>-->
<!--        <RouterLink to="/course/updatecourse" class="nav-button">修改课程</RouterLink>-->
<!--        <RouterLink to="/course/deletecourse" class="nav-button">删除课程</RouterLink>-->
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
h1{
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
  background-color: #4CAF50;/* 按钮背景透明，因为header已经有绿色背景了 */
  color: white; /* 文本颜色为白色，以便在绿色背景上清晰可见 */
  padding: 10px 20px; /* 按钮内边距，用于增加按钮的点击区域和视觉效果 */
  text-decoration: none; /* 去除链接的下划线 */
  border: none; /* 去除按钮边框 */
  cursor: pointer; /* 鼠标悬停时显示为手型，表示可点击 */
  font-size: 16px; /* 字体大小，可以根据需要调整 */
  transition: background-color 0.3s ease; /* 添加背景颜色过渡效果，提升用户体验 */
}

.nav-button:hover {
  background-color: rgba(255, 255, 255, 0.2); /* 鼠标悬停时按钮背景变为半透明白色，增加交互反馈 */
  color:black;
}
</style>