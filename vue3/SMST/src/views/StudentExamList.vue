<template>
  <h1>                                        </h1>
  <header class="list-container">
    <h1 style="text-align: center">学生考试信息列表</h1>
  </header>

  <div class="table-container">
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
      <p>没有考试信息</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useridentitystore} from '@/store/userStore'
const userstore=useridentitystore()
import { ref, onMounted } from 'vue';
import axios from 'axios';

interface Exam {
  class_id: string;
  subjects: string;
  time_exam: string;
  locations: string;
}

const examList = ref<Exam[]>([]);
const API_BASE_URL = 'http://localhost:8080/studentexams';
const studentId = ref('');

onMounted(() => {
  studentId.value = userstore.username;
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
</script>

<style >
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


</style>