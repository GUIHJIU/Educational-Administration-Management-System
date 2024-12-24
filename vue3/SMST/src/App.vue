<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router';
import {useridentitystore} from '@/store/userStore'
const userstore=useridentitystore()
</script>
<template>
  <div v-if="userstore.identity === 'admin'" id="app">
    <nav class="navbar">
      <RouterLink to="/home" class="nav-link">首页</RouterLink>
      <RouterLink to="/user-info" class="nav-link">用户信息管理</RouterLink>
      <RouterLink to="/course" class="nav-link">课程信息管理</RouterLink>
      <RouterLink to="/StuAdmin" class="nav-link">学生学籍管理</RouterLink>
      <RouterLink to="/score" class="nav-link">成绩管理</RouterLink>
      <RouterLink to="/course-selection" class="nav-link">选课管理</RouterLink>
      <RouterLink to="/exam" class="nav-link">考试管理</RouterLink>
    </nav>
    <main class="main-content">
      <RouterView />
    </main>
  </div>
  <div v-else-if="userstore.identity === 'teacher'" id="app">
    <nav class="navbar">
      <RouterLink to="/home" class="nav-link">首页</RouterLink>
      <RouterLink to="/user-info" class="nav-link">用户信息管理</RouterLink>
      <RouterLink to="/course" class="nav-link">课程信息管理</RouterLink>
      <RouterLink to="/TeacherList" class="nav-link">学生学籍管理</RouterLink>
      <RouterLink to="/score" class="nav-link">成绩管理</RouterLink>
      <RouterLink to="/course-selection" class="nav-link">选课管理</RouterLink>
      <RouterLink to="/exam" class="nav-link">考试管理</RouterLink>
    </nav>
    <main class="main-content">
      <RouterView />
    </main>
  </div>
  <div v-else-if="userstore.identity === 'student'" id="app">
    <nav class="navbar">
      <RouterLink to="/home" class="nav-link">首页</RouterLink>
      <RouterLink to="/user-info" class="nav-link">用户信息管理</RouterLink>
      <RouterLink to="/course" class="nav-link">课程信息管理</RouterLink>
      <RouterLink to="/StudentList" class="nav-link">学生学籍管理</RouterLink>
      <RouterLink to="/score" class="nav-link">成绩管理</RouterLink>
      <RouterLink to="/course-selection" class="nav-link">选课管理</RouterLink>
      <RouterLink to="/exam" class="nav-link">考试管理</RouterLink>
    </nav>
    <main class="main-content">
      <RouterView />
    </main>
  </div>
  <div v-else id="app">
    <nav class="navbar">
    </nav>
    <main>
      <RouterView />
    </main>
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

/* 添加过渡动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
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