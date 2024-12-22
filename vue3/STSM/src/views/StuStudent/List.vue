<template>
  <div class="student-search">
    <div class="search-container">
      <div class="search-box">
        <input
          type="text"
          v-model="stuNum"
          placeholder="请输入学号"
          @keyup.enter="searchStudent"
        />
        <button @click="searchStudent">查询</button>
      </div>

      <div v-if="studentData" class="student-info">
        <h2>学生信息</h2>
        <div class="info-grid">
          <div class="info-item">
            <label>学号:</label>
            <span>{{ studentData.stuNum }}</span>
          </div>
          <div class="info-item">
            <label>姓名:</label>
            <span>{{ studentData.stuName }}</span>
          </div>
          <div class="info-item">
            <label>性别:</label>
            <span>{{ studentData.stuSex }}</span>
          </div>
          <div class="info-item">
            <label>班级:</label>
            <span>{{ studentData.stuClass }}</span>
          </div>
          <div class="info-item">
            <label>专业:</label>
            <span>{{ studentData.stuMajor }}</span>
          </div>
          <div class="info-item">
            <label>学院:</label>
            <span>{{ studentData.stuCollege }}</span>
          </div>
          <div class="info-item">
            <label>状态:</label>
            <span>{{ studentData.stuState }}</span>
          </div>
          <div class="info-item">
            <label>入学时间:</label>
            <span>{{ studentData.stuTime }}</span>
          </div>
          <div class="info-item">
            <label>电话:</label>
            <span>{{ studentData.stuPhone }}</span>
          </div>
          <div class="info-item">
            <label>家庭住址:</label>
            <span>{{ studentData.stuHome }}</span>
          </div>
        </div>

        <div class="actions">
          <button @click="goToUpdate" class="update-btn">
            修改信息
          </button>
        </div>
      </div>

      <div v-else-if="searched" class="no-result">
        未找到该学号的学生信息
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();

interface StudentInfo {
  stuNum: number;
  stuName: string;
  stuSex: string;
  stuPhone: string;
  stuTime: string;
  stuClass: string;
  stuMajor: string;
  stuCollege: string;
  stuState: string;
  stuHome: string;
}

const stuNum = ref('');
const studentData = ref<StudentInfo | null>(null);
const searched = ref(false);
const API_BASE_URL = 'http://localhost:8080/student';


const searchStudent = async () => {
  if (!stuNum.value) {
    alert('请输入学号');
    return;
  }
  else
  {
    const url = `${API_BASE_URL}/search/${stuNum.value}`;
    console.log('发送请求到:', url);
    
    try {
      const response = await axios.get<StudentInfo>(url);
      console.log('服务器响应:', response);
      
      if (response.status === 200 && response.data) 
      {
        studentData.value = response.data;
        searched.value = true;
      }
    } 
    catch (error: any) {
      console.error('完整错误信息:', error);
      console.error('错误状态码:', error.response?.status);
      console.error('错误数据:', error.response?.data);
      
      studentData.value = null;
      searched.value = true;
      
      switch (error.response?.status) {
        case 404:
          alert('未找到该学号的学生');
          break;
        case 500:
          alert('系统错误，请稍后重试');
          break;
        default:
          alert('查询失败，请检查网络连接后重试');
      }
    }
  }
};

const goToUpdate = () => {
  if (!studentData.value) return;
  
  router.push({
    path: '/student/update',
    query: {
      ...studentData.value,
      stuNum: studentData.value.stuNum.toString()
    }
  });
};
</script>

<style scoped>
.student-search {
  max-width: 800px;
  margin: 40px auto;
  padding: 20px;
}

.search-container {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  padding: 24px;
}

.search-box {
  display: flex;
  gap: 12px;
  margin-bottom: 24px;
}

.search-box input {
  flex: 1;
  padding: 8px 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.search-box button {
  padding: 8px 24px;
  background: #1890ff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.student-info {
  border-top: 1px solid #eee;
  padding-top: 24px;
}

.student-info h2 {
  margin-bottom: 24px;
  color: #333;
  text-align: center;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.info-item {
  display: flex;
  align-items: center;
}

.info-item label {
  width: 80px;
  color: #666;
}

.info-item span {
  flex: 1;
  color: #333;
}

.actions {
  margin-top: 24px;
  text-align: center;
}

.update-btn {
  padding: 8px 32px;
  background: #1890ff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.update-btn:hover {
  background: #40a9ff;
}

.no-result {
  text-align: center;
  color: #666;
  padding: 40px 0;
}
</style>