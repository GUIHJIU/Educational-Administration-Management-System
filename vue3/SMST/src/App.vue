<script setup lang="ts">
import {RouterLink, RouterView, useRouter} from 'vue-router';
import {useUserStore} from '@/store/userStore'

const userstore = useUserStore()
const router=useRouter()
function handleLogout(){
  userstore.clearUser()
  router.push("/")
}
</script>
<template>
  <div id="app-container">
  <header class="header">
    <div class="header-content">
      <span>{{userstore.identity}} -- {{userstore.username}}</span>
      <button class="logout-button" @click="handleLogout">退出</button>
    </div>
  </header>
  <div v-if="userstore.identity === 'admin'" id="app">
    <nav class="navbar">
      <RouterLink to="/home" class="nav-link">首页</RouterLink>
      <!--      针对老师和管理员的用户信息不存在-->
      <RouterLink to="/user-info" class="nav-link">用户信息管理</RouterLink>
      <RouterLink to="/StuAdmin" class="nav-link">学生学籍管理</RouterLink>
      <!--      待修改，教师管理不存在-->
      <!--      <RouterLink to="/TeacherList" class="nav-link">教师管理</RouterLink>-->
      <RouterLink to="/course" class="nav-link">课程信息管理</RouterLink>
      <RouterLink to="/score" class="nav-link">成绩管理</RouterLink>
      <RouterLink to="/exam" class="nav-link">考试管理</RouterLink>
      <RouterLink to="/select-course" class="nav-link">选课管理</RouterLink>
    </nav>
    <main class="main-content">
      <RouterView />
    </main>
  </div>
  <div v-else-if="userstore.identity === 'teacher'" id="app">
    <nav class="navbar">
      <RouterLink to="/home" class="nav-link">首页</RouterLink>
      <!--      -->
      <RouterLink to="/user-info" class="nav-link">用户信息管理</RouterLink>
      <RouterLink to="/TeacherList" class="nav-link">学生管理</RouterLink>
      <RouterLink to="/course" class="nav-link">课程管理</RouterLink>
      <RouterLink to="/score" class="nav-link">成绩管理</RouterLink>
      <RouterLink to="/exam" class="nav-link">考试管理</RouterLink>
      <RouterLink to="/select-course" class="nav-link">选课管理</RouterLink>
    </nav>
    <main class="main-content">
      <RouterView />
    </main>
  </div>
  <div v-else-if="userstore.identity === 'student'" id="app">
    <nav class="navbar">
      <RouterLink to="/home" class="nav-link">首页</RouterLink>
      <RouterLink :to="{ path: '/Persion', query: { studentId: userstore.username }}" class="nav-link">用户信息管理</RouterLink>
      <RouterLink to="/course" class="nav-link">课程信息管理</RouterLink>
      <RouterLink to="/StudentList" class="nav-link">学生信息</RouterLink>
      <RouterLink to="/score" class="nav-link">成绩查询</RouterLink>
      <RouterLink to="/course-selection" class="nav-link">选课管理</RouterLink>
      <RouterLink to="/studentexam" class="nav-link">考试管理</RouterLink>
    </nav>
    <main class="main-content">
      <RouterView />
    </main>
  </div>
  <div v-else id="app">
    <nav class="navbar">
      <RouterLink to="/home" class="nav-link">首页</RouterLink>
    </nav>
    <main>
      <RouterView />
    </main>
  </div>
  </div>
</template>
<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  overflow: hidden;
}
#app {
  display: flex;
  width: 100%;
  height: 100vh;
  background-color: #f0f2f5;
  font-family: 'PingFang SC', 'Helvetica Neue', Arial, sans-serif;
  overflow: hidden;
}
#app-container {
  height: 100%;
  margin: 0;
  font-family: 'PingFang SC', 'Helvetica Neue', Arial, sans-serif;
}
.navbar {
  width: 220px;
  height: 100vh;
  background: linear-gradient(180deg, #2a3397 0%, #283593 100%);
  padding: 1.5rem 0;
  display: flex;
  flex-direction: column;
  box-shadow: 4px 0 15px rgba(0, 0, 0, 0.1);
  position: fixed;
  left: 0;
  top: 0;
  z-index: 1000;
}

.nav-link {
  color: rgba(255, 255, 255, 0.85);
  text-decoration: none;
  padding: 0.8rem 1.5rem;
  margin: 0.2rem 1rem;
  border-radius: 6px;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  font-size: 0.95rem;
  letter-spacing: 0.5px;
}

.nav-link:hover {
  background-color: rgba(255, 255, 255, 0.1);
  color: #ffffff;
  transform: translateX(5px);
}

.nav-link.RouterLink-active {
  background-color: rgba(255, 255, 255, 0.15);
  color: #ffffff;
  font-weight: 500;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.main-content {
  position: absolute;
  left: 220px;
  right: 0;
  top: 0;
  bottom: 0;
  overflow-y: auto;
  overflow-x: hidden;
  background-color: #f5f7fa;
}
.header {
  position: fixed;
  top: 0;
  left: 0; /* 可选，通常用于确保header从左边缘开始 */
  right: 0; /* 可选，用于确保header横跨整个页面宽度 */
  background-color: #0d5db9;
  color: #d9d9d9;
  display: flex;
  justify-content: flex-end;
  align-items: center; /* 修正为center以实现垂直居中，如果内容高度一致 */
  padding: 10px 20px;
  width: 100%; /* 实际上，当left和right都设置为0时，width不是必需的 */
  z-index: 1000; /* 可选，用于确保header位于其他内容之上 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 可选，为header添加阴影以增加深度感 */
}

.header-content {
  display: flex; /* 确保内部元素（文字和按钮）也是flex布局 */
  align-items: center; /* 内部元素垂直居中 */
}
/* 添加过渡动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.logout-button {
  margin-left: 10px;
  padding: 5px 10px;
  background-color: #d9d9d9;
  color: #0d5db9;
  border: none;
  cursor: pointer;
  border-radius: 4px;
}

.logout-button:hover {
  background-color: #c0c0c0;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

@media (max-width: 768px) {
  .navbar {
    width: 100%;
    height: auto;
    position: relative;
    padding: 0.5rem;
  }

  .nav-link {
    margin: 0.2rem 0.5rem;
    padding: 0.8rem 1.2rem;
  }

  .main-content {
    margin-left: 0;
    width: 100%;
  }
}
</style>