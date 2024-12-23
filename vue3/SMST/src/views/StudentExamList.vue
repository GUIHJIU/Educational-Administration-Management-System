<template>
  <div class="list-container">
    <h2 class="page-title">学生考试信息列表</h2>
    
    <div class="top-actions">
      <div class="search-container">
        <div class="search-item">
          <i class="fas fa-search search-icon"></i>
          <input
            type="text"
            placeholder="请输入学号"
            v-model="inputStudentNumber"
            @keyup.enter="searchExams"
          />
          <button class="btn search-btn" @click="searchExams">
            <i class="fas fa-search"></i>
            <span>搜索</span>
          </button>
        </div>
      </div>
    </div>
    
    <div v-if="!studentId" class="empty-message">
      <p>请输入学号并点击搜索</p>
    </div>
    
    <div v-else class="table-container">
      <table class="exam-table">
        <thead>
          <tr>
            <th>序号</th>
            <th>班级</th>
            <th>考试科目</th>
            <th>考试时间</th>
            <th>考试地点</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(exam, index) in examList" :key="exam.class_id">
            <td>{{ index + 1 }}</td>
            <td>{{ exam.class_id }}</td>
            <td>{{ exam.subjects }}</td>
            <td>{{ exam.time_exam }}</td>
            <td>{{ exam.locations }}</td>
          </tr>
        </tbody>
      </table>
      
      <div v-if="examList.length === 0" class="empty-message">
        <p>没有找到考试信息</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';
import { useStore } from '@/store';

interface Exam {
  class_id: string;
  subjects: string;
  time_exam: string;
  locations: string;
}

const examList = ref<Exam[]>([]);
const API_BASE_URL = 'http://localhost:8080/studentexams';
const studentId = ref('');
const store = useStore();
const inputStudentNumber = ref('');

onMounted(() => {
  studentId.value = store.state.studentId;
  console.log('Student ID:', studentId.value);
  fetchExamList();
});

const fetchExamList = async () => {
  if (!studentId.value) {
    console.error('Student ID is empty');
    return;
  }

  try {
    const response = await axios.get(`${API_BASE_URL}/studentid`, {
      params: {
        studentid: studentId.value
      }
    });
    
    if (response.data && response.status === 200) {
      examList.value = Array.isArray(response.data) ? response.data : 
                       Array.isArray(response.data.data) ? response.data.data : [];
    } else {
      console.error('获取学生考试信息列表失败', response);
      alert('获取学生考试信息列表失败,请稍后重试');
    }
  } catch (error) {
    console.error('获取学生考试信息列表出错:', error);
    alert('获取数据失败,请联系管理员');
  }
};

// 监听 studentId 的变化
watch(() => store.state.studentId, (newValue, oldValue) => {
  if (newValue !== oldValue) {
    fetchExamList();
  }
});

const searchExams = () => {
  if (inputStudentNumber.value.trim() !== '') {
    studentId.value = inputStudentNumber.value.trim();
    store.commit('setStudentId', studentId.value);
  }
};
</script>

<style scoped>
/* 使用与 ExamList.vue 相同的样式 */
.list-container {
  background-color: white;
  padding: 1.5rem;
  min-height: 100%;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  margin: 0;
  border-radius: 0;
}

.page-title {
  color: #262626;
  font-size: 24px;
  margin-bottom: 32px;
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

/* 顶部操作区域 */
.top-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.search-container {
  display: flex;
  gap: 16px;
  align-items: center;
}

.search-item {
  position: relative;
  display: flex;
  align-items: center;
  gap: 10px;
}

.search-icon {
  position: absolute;
  left: 12px;
  top: 50%;
  transform: translateY(-50%);
  color: #909399;
}

.search-item input {
  width: 240px;
  height: 32px;
  padding: 4px 12px 4px 32px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  font-size: 14px;
  transition: all 0.3s;
}

.search-item input:focus {
  border-color: #40a9ff;
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
}

/* 按钮样式 */
.btn {
  padding: 8px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 15px;
  display: inline-flex;
  align-items: center;
  gap: 8px;
  transition: all 0.3s;
  height: 36px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.btn i {
  font-size: 15px;
}

.btn span {
  white-space: nowrap;
}

.search-btn {
  background-color: #40a9ff;
  color: white;
}

.search-btn:hover {
  background-color: #1890ff;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(24, 144, 255, 0.2);
}

/* 表格样式 */
.table-container {
  margin-top: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  overflow-x: auto;
  width: 100%;
}

.exam-table {
  width: 100%;
  min-width: 1200px;
  border-collapse: collapse;
  text-align: center;
  table-layout: fixed;
}

.exam-table th {
  background-color: #fafafa;
  padding: 14px 8px;
  font-weight: 500;
  color: #262626;
  border-bottom: 1px solid #f0f0f0;
  white-space: nowrap;
  font-size: 15px;
}

.exam-table td {
  padding: 12px 8px;
  border-bottom: 1px solid #ebeef5;
  color: #606266;
  max-width: 150px;
  line-height: 1.4;
  vertical-align: middle;
}

.exam-table tbody tr:hover {
  background-color: #f5f7fa;
}

/* 空数据提示 */
.exam-table tbody:empty::after {
  content: "无数据";
  display: block;
  text-align: center;
  padding: 20px;
  color: #909399;
  font-size: 14px;
}

.empty-message {
  text-align: center;
  padding: 20px;
  color: #999;
}
</style> 