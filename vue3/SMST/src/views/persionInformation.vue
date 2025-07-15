<template>
  <div class="person-info-container">
    <h2 class="title">学生信息</h2>
    
    <div v-if="loading" class="loading">
      正在加载学生信息...
    </div>
    
    <div v-else-if="error" class="error-message">
      {{ error }}
    </div>
    
    <div v-else-if="studentInfo" class="info-content">
      <div class="info-column">
        <!-- 左列：短标签 -->
        <div class="info-row">
          <span class="info-label">姓名</span>
          <span class="info-value">{{ studentInfo.姓名 }}</span>
        </div>
        
        <div class="info-row">
          <span class="info-label">性别</span>
          <span class="info-value">{{ studentInfo.性别 }}</span>
        </div>
        
        <div class="info-row">
          <span class="info-label">民族</span>
          <span class="info-value">{{ studentInfo.名族 }}</span>
        </div>
        
        <div class="info-row">
          <span class="info-label">学制</span>
          <span class="info-value">{{ studentInfo.学制 }}</span>
        </div>
        
        <div class="info-row">
          <span class="info-label">学院</span>
          <span class="info-value">{{ studentInfo.学院 }}</span>
        </div>
        
        <div class="info-row">
          <span class="info-label">专业</span>
          <span class="info-value">{{ studentInfo.专业 }}</span>
        </div>
        
        <div class="info-row">
          <span class="info-label">班级</span>
          <span class="info-value">{{ studentInfo.班级 }}</span>
        </div>
      </div>

      <div class="info-column">
        <!-- 右列：长标签 -->
        <div class="info-row">
          <span class="info-label">证件类型</span>
          <span class="info-value">{{ studentInfo.证件类型 }}</span>
        </div>
        
        <div class="info-row">
          <span class="info-label">证件号码</span>
          <span class="info-value">{{ studentInfo.证件号码 }}</span>
        </div>
        
        <div class="info-row">
          <span class="info-label">出生日期</span>
          <span class="info-value">{{ studentInfo.出生日期 }}</span>
        </div>
        
        <div class="info-row">
          <span class="info-label">入学日期</span>
          <span class="info-value">{{ studentInfo.入学日期 }}</span>
        </div>
        
        <div class="info-row">
          <span class="info-label">政治面貌</span>
          <span class="info-value">{{ studentInfo.政治面貌 }}</span>
        </div>
        
        <div class="info-row">
          <span class="info-label">当前所在级</span>
          <span class="info-value">{{ studentInfo.当前所在级 }}</span>
        </div>
        
        <div class="info-row">
          <span class="info-label">预计毕业日</span>
          <span class="info-value">{{ studentInfo.预计毕业日 }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const studentInfo = ref(null)
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    const studentId = route.query.studentId
    if (!studentId) {
      throw new Error('未提供学号')
    }
    
    // 修改为正确的后端API地址
    const response = await axios.get(`https://localhost:443/api/student-info/${studentId}`)
    
    if (response.data) {
      studentInfo.value = response.data
      console.log('获取到的学生信息:', response.data)
    } else {
      throw new Error('未获取到学生信息')
    }
  } catch (err) {
    console.error('Error details:', err)
    error.value = '获取学生信息失败: ' + (err.response?.data?.message || err.message)
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.person-info-container {
  padding: 20px;
  background-color: #fff;
  min-height: 100%;
}

.info-content {
  max-width: 1200px;
  margin: 0 auto;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  padding: 30px;
  display: flex;
  gap: 60px;
}

.info-column {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.info-row {
  display: flex;
  align-items: center;
  padding: 10px 0;
  border-bottom: none;
}

.info-label {
  min-width: 100px;
  color: #666;
  font-weight: 500;
  text-align: right;
  padding-right: 20px;
  position: relative;
}

.info-label::after {
  content: ":";
  position: absolute;
  right: 8px;
  top: 50%;
  transform: translateY(-50%);
}

.info-value {
  color: #333;
  flex: 1;
  padding-left: 20px;
}

.loading {
  text-align: center;
  padding: 20px;
  color: #666;
}

.error-message {
  text-align: center;
  padding: 20px;
  color: #ff4444;
}

@media (max-width: 768px) {
  .info-content {
    grid-template-columns: 1fr;
    padding: 15px;
  }
  
  .info-label {
    min-width: 80px;
  }
}
</style>
