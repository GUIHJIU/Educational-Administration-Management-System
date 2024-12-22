<template>
  <div class="form-container">
    <h2 class="page-title">更新学生成绩</h2>
    
    <form class="student-form" @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>学生ID</label>
        <input type="number" v-model="formData.studentid" placeholder="请输入学生ID" required>
      </div>
      
      <div class="form-group">
        <label>课程ID</label>
        <input type="number" v-model="formData.courseid" placeholder="请输入课程ID" required>
      </div>
      
      <div class="form-group">
        <label>成绩</label>
        <input type="number" v-model="formData.score" placeholder="请输入成绩" required>
      </div>
      
      <div class="form-actions">
        <button type="submit" class="btn submit-btn">
          <i class="fas fa-check"></i>
          <span>更新</span>
        </button>
        <button type="button" class="btn cancel-btn" @click="handleCancel">
          <i class="fas fa-times"></i>
          <span>取消</span>
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const route = useRoute();
const API_BASE_URL = 'http://localhost:8080/student';

const formData = ref({
  studentid: '',
  courseid: '',
  score: ''
});

onMounted(() => {
  // 从路由查询参数中获取学生成绩信息
  const queryParams = route.query;
  Object.keys(formData.value).forEach(key => {
    formData.value[key] = queryParams[key] || '';
  });
});

const handleSubmit = async () => {
  try {
    const response = await axios.put(`${API_BASE_URL}/update`, formData.value);
    if (response.status === 200) {
      alert('更新成功');
      router.push('/score');
    } else {
      throw new Error('更新失败');
    }
  } catch (error) {
    console.error('更新学生成绩出错：', error);
    alert(error.message || '更新失败，请稍后重试');
  }
};

const handleCancel = () => {
  router.push('/score');
};
</script>

<style scoped>
.form-container {
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 2rem auto;
}

.page-title {
  color: #262626;
  font-size: 24px;
  margin-bottom: 32px;
  text-align: center;
  position: relative;
  padding-bottom: 12px;
}

.page-title::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 60px;
  height: 3px;
  background-color: #40a9ff;
  border-radius: 2px;
}

.student-form {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 24px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-group label {
  font-size: 14px;
  color: #262626;
  font-weight: 500;
}

.form-group input,
.form-group select {
  padding: 8px 12px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  font-size: 14px;
  transition: all 0.3s;
}

.form-group input:focus,
.form-group select:focus {
  border-color: #40a9ff;
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
  outline: none;
}

.form-actions {
  grid-column: span 2;
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 32px;
  padding: 0 20px;
}

.btn {
  padding: 8px 24px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 15px;
  display: inline-flex;
  align-items: center;
  gap: 8px;
  transition: all 0.3s;
  height: 36px;
  min-width: 100px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.btn i {
  font-size: 15px;
}

.submit-btn {
  background-color: #40a9ff;
  color: white;
}

.submit-btn:hover {
  background-color: #1890ff;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(24, 144, 255, 0.2);
}

.cancel-btn {
  background-color: #f0f2f5;
  color: #595959;
}

.cancel-btn:hover {
  background-color: #e6e8eb;
  color: #262626;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
</style>
