<template>
  <div class="form-container">
    <h2 class="page-title">修改考试信息</h2>

    <form class="exam-form" @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>班级</label>
        <input type="text" v-model="formData.class_id" placeholder="请输入班级" readonly>
      </div>

      <div class="form-group">
        <label>考试科目</label>
        <input type="text" v-model="formData.subjects" placeholder="请输入考试科目" required>
      </div>

      <div class="form-group">
        <label>考试时间</label>
        <input type="datetime-local" v-model="formData.time_exam" required>
      </div>

      <div class="form-group">
        <label>考试地点</label>
        <input type="text" v-model="formData.locations" placeholder="请输入考试地点" required>
      </div>

      <div class="form-actions">
        <button type="submit" class="btn submit-btn">
          <i class="fas fa-check"></i>
          <span>保存</span>
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
import {onMounted, ref} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import apiClient from '@/utils/axios';

const router = useRouter();
const route = useRoute();
const API_BASE_URL = 'https://localhost:443/exams';

const formData = ref({
  class_id: '',
  subjects: '',
  time_exam: '',
  locations: ''
});

const examId = ref('');

onMounted(async () => {
  examId.value = route.params.id;
  console.log('Exam ID:', examId.value);
  const queryParams = route.query;
  Object.keys(formData.value).forEach(key => {
    formData.value[key] = queryParams[key] || '';
  });
});

const handleSubmit = async () => {
  try {
    const response = await apiClient.put(`${API_BASE_URL}/update`, formData.value);
    if (response.status === 200) {
      alert('修改成功');
      router.push('/exam');
    } else {
      throw new Error(`修改失败,服务器返回状态码: ${response.status}`);
    }
  } catch (error) {
    console.error('修改考试信息出错:', error);
    if (error.response) {
      // 请求已发出,但服务器响应的状态码不在 2xx 范围内
      alert(`修改失败,服务器返回状态码: ${error.response.status}`);
    } else if (error.request) {
      // 请求已发出,但没有收到响应
      alert('修改失败,没有收到服务器响应');
    } else {
      // 发送请求时出现了错误
      alert('修改失败,请求发送失败');
    }
  }
};

const handleCancel = () => {
  router.push('/exam');
};
</script>

<style scoped>
/* 使用与 ExamAdd.vue 相同的样式 */
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

.exam-form {
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

.form-group input {
  padding: 8px 12px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  font-size: 14px;
  transition: all 0.3s;
}

.form-group input:focus {
  border-color: #40a9ff;
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
  outline: none;
}

.form-group input[readonly] {
  background-color: #f5f5f5;
  cursor: not-allowed;
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