<template>
  <h1>                                        </h1>
  <h1>                                        </h1>
  <div class="list-container">
    <h2 class="page-title">学生成绩管理</h2>
  
    <div class="top-actions">
      <div class="search-container">
        <div v-if="userStore.identity === 'teacher'" class="search-item">
          <i class="fas fa-search search-icon"></i>
          <input
            type="text"
            placeholder="请输入学生ID搜索"
            v-model="studentid"
            @input="handleSearch"
          />
        </div>
        <div class="search-item">
          <i class="fas fa-search search-icon"></i>
          <input
            type="text"
            placeholder="请输入课程ID搜索"
            v-model="courseid"
            @input="handleSearch"
          />
        </div>
        <button class="btn search-btn" @click="handleSearch">
          <i class="fas fa-search"></i>
          <span>搜索</span>
        </button>
      </div>
  
      <div class="button-group">
        <RouterLink v-if="userStore.identity === 'teacher'" class="btn add-btn" to="/add">
          <i class="fas fa-plus"></i>
          <span>添加</span>
        </RouterLink>
        <RouterLink v-if="userStore.identity === 'teacher'" class="btn edit-btn" to="/update">
          <i class="fas fa-edit"></i>
          <span>编辑</span>
        </RouterLink>
        <RouterLink v-if="userStore.identity === 'teacher'" class="btn delete-btn" to="/delete">
          <i class="fas fa-trash"></i>
          <span>删除</span>
        </RouterLink>
      </div>
    </div>
  
    <div class="table-container">
      <table class="student-table">
        <thead>
          <tr>
            <th>序号</th>
            <th>学生ID</th>
            <th>课程ID</th>
            <th>成绩</th>
            <th>
              <button class="btn sort-btn" @click="sortScores">
                <i class="fas fa-sort-amount-up"></i>
                <span>排序</span>
              </button>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(score, index) in paginatedScoreList" :key="index">
            <td>{{ index + 1 }}</td>
            <td>{{ score.studentid }}</td>
            <td>{{ score.courseid }}</td>
            <td>{{ score.score }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1">首页</button>
      <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
      <span class="page-info">{{ currentPage }} / {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">下一页</button>
      <button @click="nextPage" :disabled="currentPage === totalPages">尾页</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watchEffect } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useridentitystore } from '@/store/userStore'; // 假设你有一个 userStore 模块

// 响应式变量声明
const scoreList = ref([]);
const studentid = ref('');
const courseid = ref('');
const currentPage = ref(1);
const pageSize = ref(10);
const router = useRouter();
const userStore = useridentitystore();

const API_BASE_URL = 'http://localhost:8080/score';

// 控制是否排序的变量
const isSorted = ref(false);

// 计算属性
const filteredScoreList = computed(() => {
  if (!studentid.value && !courseid.value) {
    return scoreList.value;
  }
  return scoreList.value.filter(score => {
    return score.studentid.toString().includes(studentid.value) &&
    score.courseid.toString().includes(courseid.value);
  });
});

const sortedScoreList = computed(() => {
  if (isSorted.value) {
    return [...filteredScoreList.value].sort((a, b) => b.score - a.score);
  }
  return filteredScoreList.value;
});

const paginatedScoreList = computed(() => {
  const startIndex = (currentPage.value - 1) * pageSize.value;
  const endIndex = startIndex + pageSize.value;
  return sortedScoreList.value.slice(startIndex, endIndex);
});

const totalPages = computed(() => {
  return Math.ceil(sortedScoreList.value.length / pageSize.value);
});

// 生命周期钩子
onMounted(() => {
  fetchScoreList();
});

// 方法定义
const fetchScoreList = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/List`);
    if (response.data && response.status === 200) {
      scoreList.value = Array.isArray(response.data) ? response.data :
                        Array.isArray(response.data.data) ? response.data.data : [];
      // 根据用户身份过滤成绩列表
      if (userStore.identity === 'student') {
        scoreList.value = scoreList.value.filter(score => score.studentid.toString() === userStore.username);
      }
    } else {
      console.error('获取学生成绩列表失败', response);
      alert('获取学生成绩列表失败，请稍后重试');
    }
  } catch (error) {
    console.error('获取学生成绩列表出错：', error);
    alert('获取数据失败，请联系管理员');
  }
};

const handleSearch = () => {
  currentPage.value = 1;
};

const sortScores = () => {
  isSorted.value = !isSorted.value;
  currentPage.value = 1;
};

const editStudent = () => {
  // 编辑学生成绩的逻辑
};

const deleteStudent = () => {
  // 删除学生成绩的逻辑
};

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

watchEffect(() => {
  const maxPage = Math.ceil(sortedScoreList.value.length / pageSize.value);
  if (currentPage.value > maxPage && maxPage > 0) {
    currentPage.value = maxPage;
  }
});
</script>

<style scoped>
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

.add-btn {
  background-color: #40a9ff;
  color: white;
  text-decoration: none;
}

.add-btn:hover {
  background-color: #1890ff;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(24, 144, 255, 0.2);
}

.edit-btn {
  background-color: #40a9ff;
  color: white;
}

.edit-btn:hover {
  background-color: #1890ff;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(24, 144, 255, 0.2);
}

.delete-btn {
  background-color: #ff7875;
  color: white;
}

.delete-btn:hover {
  background-color: #ff4d4f;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(255, 77, 79, 0.2);
}

/* 表格样式 */
.table-container {
  margin-top: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  overflow-x: auto;
  width: 100%;
}

.student-table {
  width: 100%;
  min-width: 1200px;
  border-collapse: collapse;
  text-align: center;
  table-layout: fixed;
}

.student-table th {
  background-color: #fafafa;
  padding: 14px 8px;
  font-weight: 500;
  color: #262626;
  border-bottom: 1px solid #f0f0f0;
  white-space: nowrap;
  font-size: 15px;
}

.student-table td {
  padding: 12px 8px;
  border-bottom: 1px solid #ebeef5;
  color: #606266;
  max-width: 150px;
  line-height: 1.4;
  vertical-align: middle;
}

.student-table tbody tr:hover {
  background-color: #f5f7fa;
}

/* 分页样式 */
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  gap: 8px;
}

.pagination button {
  padding: 8px 16px;
  background-color: white;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  color: #606266;
}

.pagination button:not(:disabled):hover {
  color: #1e88e5;
  border-color: #1e88e5;
  background-color: #e3f2fd;
}

.pagination button:disabled {
  background-color: #f5f7fa;
  color: #c0c4cc;
  cursor: not-allowed;
}

.page-info {
  padding: 8px 12px;
  background-color: #f5f7fa;
  border-radius: 4px;
  color: #606266;
}
h1{
  background-color: #0d5db9;
  color: #d9d9d9;
  padding: 20px;
  text-align: center;
  border-radius: 10px;
}
</style>
