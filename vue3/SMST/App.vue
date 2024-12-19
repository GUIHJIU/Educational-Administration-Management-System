<template>
  <div class="app-container">
    <div class="sidebar">
      <div class="logo-container">
        <h2>教务管理系统</h2>
      </div>
      <div class="nav-links">
        <RouterLink to="/user-info" class="nav-item">
          <el-button type="primary" plain>
            <el-icon><User /></el-icon>
            用户信息管理
          </el-button>
        </RouterLink>
        <RouterLink to="/course-info" class="nav-item">
          <el-button type="primary" plain>
            <el-icon><Reading /></el-icon>
            课程信息管理
          </el-button>
        </RouterLink>
        <RouterLink to="/List" class="nav-item">
          <el-button type="primary" plain>
            <el-icon><Management /></el-icon>
            学生学籍管理
          </el-button>
        </RouterLink>
        <RouterLink to="/score" class="nav-item">
          <el-button type="primary" plain>
            <el-icon><DataLine /></el-icon>
            成绩管理
          </el-button>
        </RouterLink>
        <RouterLink to="/course-selection" class="nav-item">
          <el-button type="primary" plain>
            <el-icon><Select /></el-icon>
            选课管理
          </el-button>
        </RouterLink>
        <RouterLink to="/exam" class="nav-item">
          <el-button type="primary" plain>
            <el-icon><EditPen /></el-icon>
            考试管理
          </el-button>
        </RouterLink>
      </div>
    </div>

    <div class="main-content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup>
import { User, Reading, Management, DataLine, Select, EditPen } from '@element-plus/icons-vue'
</script>

<style scoped>
.app-container {
  display: flex;
  min-height: 100vh;
}

.sidebar {
  width: 220px;
  background: #f0f8ff;
  padding: 20px 10px;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
}

.logo-container {
  text-align: center;
  padding: 20px 0;
  margin-bottom: 20px;
  border-bottom: 1px solid #e0e0e0;
}

.logo-container h2 {
  color: #409EFF;
  margin: 0;
  font-size: 20px;
}

.nav-links {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.nav-item {
  text-decoration: none;
}

.nav-item .el-button {
  width: 100%;
  justify-content: flex-start;
  padding: 12px 15px;
  background-color: rgba(64, 158, 255, 0.1);
  border-color: transparent;
  transition: all 0.3s;
}

.nav-item .el-button:hover {
  background-color: rgba(64, 158, 255, 0.2);
  transform: translateX(5px);
}

.el-icon {
  margin-right: 8px;
}

.main-content {
  flex: 1;
  padding: 20px;
  background-color: #fff;
}
</style> 