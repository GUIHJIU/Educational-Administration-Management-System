<template>
  <div class="list-container">
    <div class="page-header">
      <h1 class="page-title">学生学籍信息管理</h1>
    </div>
    
    <div class="top-actions">
      <div class="search-container">
        <div class="search-item">
          <i class="fas fa-search search-icon"></i>
          <input
            type="text"
            placeholder="请输入学号或姓名搜索"
            v-model="searchKeyword"
            @input="handleSearch"
          />
          <button class="btn search-btn" @click="handleSearch">
            <i class="fas fa-search"></i>
            <span>搜索</span>
          </button>
          <button class="btn reset-btn" @click="resetSearch">
            <i class="fas fa-undo"></i>
            <span>重置</span>
          </button>
        </div>
      </div>
    </div>

    <div class="table-container">
      <table class="student-table">
        <thead>
          <tr>
            <th>序号</th>
            <th>学号</th>
            <th>姓名</th>
            <th>状态</th>
            <th>性别</th>
            <th>班级</th>
            <th>专业</th>
            <th>学院</th>
            <th>电话</th>
            <th>家庭住址</th>
            <th>入学时间</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(student, index) in paginatedStudentList" 
              :key="student.studentId">
            <td>{{ index + 1 }}</td>
            <td>{{ student.stuNum }}</td>
            <td>{{ student.stuName }}</td>
            <td>
              <span class="status-dot" :class="getStatusClass(student.stuState)"></span>
              {{ getStatusText(student.stuState) }}
            </td>
            <td>{{ student.stuSex }}</td>
            <td>{{ student.stuClass }}</td>
            <td>{{ student.stuMajor }}</td>
            <td>{{ student.stuCollege }}</td>
            <td>{{ student.stuPhone }}</td>
            <td>{{ student.stuHome }}</td>
            <td>{{ student.stuTime }}</td>
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
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';

const studentList = ref([]);
const searchKeyword = ref('');
const currentPage = ref(1);
const pageSize = ref(10);
const API_BASE_URL = 'http://localhost:8080/student';

// 计算属性
const filteredStudentList = computed(() => {
  const keyword = searchKeyword.value.trim().toLowerCase();
  if (!keyword) {
    return studentList.value;
  }
  return studentList.value.filter(student => {
    const stuNum = student.stuNum?.toString().toLowerCase() || '';
    const stuName = student.stuName?.toString().toLowerCase() || '';
    return stuNum.includes(keyword) || stuName.includes(keyword);
  });
});

const paginatedStudentList = computed(() => {
  const startIndex = (currentPage.value - 1) * pageSize.value;
  const endIndex = startIndex + pageSize.value;
  return filteredStudentList.value.slice(startIndex, endIndex);
});

const totalPages = computed(() => {
  return Math.ceil(filteredStudentList.value.length / pageSize.value);
});

// 生命周期钩子
onMounted(() => {
  fetchStudentList();
});

// 方法定义
const fetchStudentList = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/List`);
    if (response.data && response.status === 200) {
      // 转换下划线命名为驼峰命名
      const convertData = (data) => {
        return data.map(item => ({
          stuNum: item.stu_num,
          stuName: item.stu_name,
          stuSex: item.stu_sex,
          stuPhone: item.stu_phone,
          stuTime: item.stu_time,
          stuClass: item.stu_class,
          stuMajor: item.stu_major,
          stuCollege: item.stu_college,
          stuState: item.stu_state,
          stuHome: item.stu_home
        }));
      };
      
      studentList.value = Array.isArray(response.data) ? convertData(response.data) : 
                         Array.isArray(response.data.data) ? convertData(response.data.data) : [];
      
      // 过滤掉无效数据（所有字段都为 null 的数据）
      studentList.value = studentList.value.filter(student => 
        Object.values(student).some(value => value !== null && value !== undefined)
      );
    } else {
      console.error('获取学生信息列表失败', response);
      alert('获取学生列表失败，请稍后重试');
    }
  } catch (error) {
    console.error('获取学生信息列表出错：', error);
    alert('获取数据失败，请联系管理员');
  }
};

const handleSearch = () => {
  currentPage.value = 1;
};

const resetSearch = () => {
  searchKeyword.value = '';
  currentPage.value = 1;
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

// 添加状态相关的方法
const getStatusClass = (state) => {
  const status = String(state).trim();
  
  switch (status) {
    case '在读':
      return 'status-active';
    case '休学':
      return 'status-warning';
    case '退学':
      return 'status-inactive';
    case '毕业':
      return 'status-graduate';
    default:
      return 'status-unknown';
  }
};

const getStatusText = (state) => {
  if (!state) return '未知';
  return String(state).trim();
};
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
  color: white;
  font-size: 24px;
  font-weight: 600;
  text-align: center;
  margin: 0;
  letter-spacing: 1px;
  position: static;
  padding-bottom: 0;
}

/* 顶部操作区域 */
.top-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
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
  border-radius: 5px;
  font-size: 14px;
  transition: all 0.3s;
}

.search-item input:focus {
  border-color: #11161b;
  box-shadow: 0 0 0 2px rgba(47, 133, 214, 0.2);
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
  min-width: 1100px;
  border-collapse: collapse;
  text-align: center;
  table-layout: fixed;
}

.student-table th {
  background-color: #fafafa;
  padding: 10px 8px;
  font-weight: 700;
  color: #262626;
  border-bottom: 1px solid #f0f0f0;
  white-space: nowrap;
  font-size: 15px;
}

.student-table td {
  padding: 0 8px;
  border-bottom: 1px solid #ebeef5;
  color: #606266;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  height: 40px;
  line-height: 40px;
}

.student-table tbody tr:hover {
  background-color: #f5f7fa;
}

.selected-row {
  background-color: #ecf5ff;
}

/* 操作按钮 */
.operation-buttons {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 12px;
  padding: 4px 16px;
  min-width: 180px;
  margin-left: 0;
  position: relative;
  right: -45px;
}

.operation-buttons .btn {
  padding: 6px 16px;
  height: 32px;
  min-width: 80px;
  font-size: 14px;
}

.edit-btn {
  background-color: #40a9ff;
  color: white;
}

.edit-btn:hover {
  background-color: #235e98;
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

.search-btn {
  background-color: #40a9ff;
  color: white;
}

.reset-btn {
  background-color: #f0f2f5;
  color: #595959;
}

.reset-btn:hover {
  background-color: #e6e8eb;
  color: #262626;
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

/* 空数据提示 */
.student-table tbody:empty::after {
  content: "无数据";
  display: block;
  text-align: center;
  padding: 20px;
  color: #909399;
  font-size: 14px;
}

/* 按钮组样式 */
.button-group {
  display: flex;
  gap: 8px;
  margin-left: 8px;
}

/* 序号列的特殊样式 */
.student-table th:first-child,
.student-table td:first-child {
  width: 80px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* 其他列的文字可以换行 */
.student-table td:not(:first-child) {
  white-space: normal;
  word-break: break-all;
}

/* 表格标题样式 */
.student-table th:last-child,
.student-table td:last-child {
  text-align: center;
  padding: 14px 20px;
  width: 270px;
  position: relative;
}

/* 添加标题栏样式 */
.page-header {
  background: #1277d6;
  padding: 24px 24px;
  margin: -24px -32px 24px -32px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.page-title {
  color: white;
  font-size: 24px;
  font-weight: 600;
  text-align: center;
  margin: 0;
  letter-spacing: 1px;
  line-height: 1.5;
}

/* 状态样式 */
.status-dot {
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-right: 8px;
  position: relative;
  top: -1px;
  vertical-align: middle;
}

.status-active {
  background-color: #10b981;
  box-shadow: 0 0 8px rgba(16, 185, 129, 0.4);
}

.status-warning {
  background-color: #f59e0b;
  box-shadow: 0 0 8px rgba(245, 158, 11, 0.4);
}

.status-inactive {
  background-color: #ef4444;
  box-shadow: 0 0 8px rgba(239, 68, 68, 0.4);
}

.status-graduate {
  background-color: #3b82f6;
  box-shadow: 0 0 8px rgba(59, 130, 246, 0.4);
}

.status-unknown {
  background-color: #9ca3af;
  box-shadow: 0 0 8px rgba(156, 163, 175, 0.4);
}

/* 表格单元格居中 */
.student-table th,
.student-table td 
{
  text-align: center;
  vertical-align: middle;
}

/* 状态列的特殊样式 */
.student-table td:nth-child(4) {
  text-align: center;
  padding: 0 8px;
  height: 40px;
  line-height: 40px;
}

/* 修改表格列宽 */
.student-table {
  width: 100%;
  min-width: 1100px;
  border-collapse: collapse;
  text-align: center;
  table-layout: fixed;
}

/* 设置各列的宽度 */
.student-table th:nth-child(10) { width: 200px; } /* 家庭住址 */
.student-table th:nth-child(11) { width: 120px; } /* 入学时间 */

/* 确保单元格内容不会溢出 */
.student-table td {
  padding: 0 8px;
  border-bottom: 1px solid #ebeef5;
  color: #606266;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  height: 40px;
  line-height: 40px;
}

/* 确保家庭住址列的内容居中显示 */
.student-table td:nth-child(10) {
  text-align: center;
  padding: 0 12px;
  height: 40px;
  line-height: 40px;
}
</style>