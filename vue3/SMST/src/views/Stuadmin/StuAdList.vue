<template>
  <div class="list-container">
    <div class="page-header">
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

      <RouterLink class="btn add-btn" to="/StuAdmin/StuAdadd">新增</RouterLink>
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
          <th>操作</th>
        </tr>
        </thead>
        <tbody @click="handleTableRowClick">
        <tr v-for="(student, index) in paginatedStudentList"
            :key="student.studentId"
            :class="{ 'selected-row': selectedStudent === student }">
          <td>{{ index + 1 }}</td>
          <td>{{ student.stuNum }}</td>
          <td>{{ student.stuName }}</td>
          <td>
            <div>
              <span class="status-dot" :class="getStatusClass(student.stuState)"></span>
              {{ getStatusText(student.stuState) }}
            </div>
          </td>
          <td>{{ student.stuSex }}</td>
          <td>{{ student.stuClass }}</td>
          <td>{{ student.stuMajor }}</td>
          <td>{{ student.stuCollege }}</td>
          <td>{{ student.stuPhone }}</td>
          <td>{{ student.stuHome }}</td>
          <td>{{ student.stuTime }}</td>
          <td class="operation-buttons">
            <button class="btn edit-btn" @click="editStudent(student)">
              <i class="fas fa-edit"></i>
              <span>编辑</span>
            </button>
            <button class="btn delete-btn" @click="deleteStudent(student.stuNum)">
              <i class="fas fa-trash"></i>
              <span>删除</span>
            </button>
          </td>
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

    <router-view></router-view>
  </div>

</template>

<script setup>
import {computed, onMounted, ref, watchEffect} from 'vue';
import apiClient from '@/utils/axios.js';
import {useRouter} from 'vue-router';

// 响应式变量声明
const studentList = ref([]);
const searchKeyword = ref('');
const currentPage = ref(1);
const pageSize = ref(10);
const selectedStudent = ref(null);
const router = useRouter();

const API_BASE_URL = 'https://localhost:443/student';

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
    const response = await apiClient().get(`${API_BASE_URL}/List`);
    if (response.status === 200) {
      const data = response.data?.data || response.data || [];
      if (!Array.isArray(data)) {
        console.error('Invalid data format received:', data);
        alert('获取数据格式错误');
        return;
      }

      const convertData = (items) => {
        return items.map(item => ({
          stuNum: item.stu_num || item.stuNum || '',
          stuName: item.stu_name || item.stuName || '',
          stuSex: item.stu_sex || item.stuSex || '',
          stuPhone: item.stu_phone || item.stuPhone || '',
          stuTime: item.stu_time || item.stuTime || '',
          stuClass: item.stu_class || item.stuClass || '',
          stuMajor: item.stu_major || item.stuMajor || '',
          stuCollege: item.stu_college || item.stuCollege || '',
          stuState: item.stu_state || item.stuState || '在读',
          stuHome: item.stu_home || item.stuHome || ''
        }));
      };

      studentList.value = convertData(data).filter(student =>
          Object.values(student).some(value => value !== null && value !== undefined && value !== '')
      );

      if (studentList.value.length === 0) {
        console.log('No valid student data found');
      }
    } else {
      throw new Error('获取学生列表失败');
    }
  } catch (error) {
    console.error('获取学生信息列表出错：', error);
    alert(error.message || '获取数据失败，请稍后重试');
  }
};

const handleSearch = () => {
  currentPage.value = 1;
};

const resetSearch = () => {
  searchKeyword.value = '';
  currentPage.value = 1;
};

const editStudent = (student) => {
  const formattedStudent = {
    stuNum: student.stuNum?.toString() || '',
    stuName: student.stuName?.toString() || '',
    stuSex: student.stuSex?.toString() || '',
    stuClass: student.stuClass?.toString() || '',
    stuMajor: student.stuMajor?.toString() || '',
    stuCollege: student.stuCollege?.toString() || '',
    stuPhone: student.stuPhone?.toString() || '',
    stuTime: student.stuTime?.toString() || '',
    stuState: student.stuState?.toString() || '',
    stuHome: student.stuHome?.toString() || ''
  };
  router.push({
    path: '/StuAdmin/StuAdupdate',
    query: formattedStudent
  });
};


const deleteStudent = async (stuNum) => {
  try {
    if (!stuNum) {
      alert('学号不能为空');
      return;
    }

    if (!confirm('确定要删除该学生信息吗？')) {
      return;
    }

    // 使用路径参数方式调用删除接口
    const response = await apiClient().delete(`${API_BASE_URL}/delete/${stuNum}`);

    if (response.status === 200) {
      alert('删除成功');
      await fetchStudentList();

      const maxPage = Math.ceil((studentList.value.length - 1) / pageSize.value);
      if (currentPage.value > maxPage && maxPage > 0) {
        currentPage.value = maxPage;
      }
    } else {
      throw new Error('删除学生信息失败');
    }
  } catch (error) {
    console.error('删除学生信息出错：', error);
    alert(error.message || '删除失败，请稍后重试');
  }
};

const handleTableRowClick = (event) => {
  if (event.target.tagName === 'BUTTON') {
    return;
  }

  const row = event.target.closest('tr');
  if (row) {
    const studentIndex = Array.from(row.parentNode.children).indexOf(row);
    const student = paginatedStudentList.value[studentIndex];
    if (student) {
      selectedStudent.value = student;
    }
  }
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
  const maxPage = Math.ceil(filteredStudentList.value.length / pageSize.value);
  if (currentPage.value > maxPage && maxPage > 0) {
    currentPage.value = maxPage;
  }
});

// 添加状态相关方法
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
/* 修改标题栏样式，与 Stuteacher 保持一致 */
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

/* 调整容器样式以适应新的标题栏 */
.list-container {
  background-color: white;
  min-height: 100vh;
  padding: 24px 32px;
  display: flex;
  flex-direction: column;
}

/* 调整顶部操作区域的边距 */
.top-actions {
  margin-top: 0; /* 由于添加了标题栏，减少上边距 */
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f8fafc;
  padding: 16px 24px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

/* 搜索区域 */
.search-container {
  display: flex;
  align-items: center;
  gap: 12px;
}

.search-item {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
}

.search-item input {
  width: 280px;
  height: 36px;
  padding: 8px 16px 8px 40px;
  border: 1px solid #e2e8f0;
  border-radius: 6px;
  font-size: 14px;
  transition: all 0.3s;
  background-color: white;
}

.search-item input:focus {
  border-color: #706aeb;
  box-shadow: 0 0 0 2px rgba(79, 70, 229, 0.1);
  outline: none;
}

.search-icon
{
  position: absolute;
  left: 12px;
  color: #2563eb;
}

/* 按钮基础样式 */
.btn {
  height: 32px;
  padding: 0 12px;
  border: none;
  border-radius: 4px;
  font-size: 13px;
  font-weight: 500;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  cursor: pointer;
  transition: all 0.3s;
  min-width: 76px;
}

/* 搜索按钮 */
.search-btn {
  background-color: #2563eb;  /* 加深的蓝色，与顶部标题栏一致 */
  color: white;
}

.search-btn:hover {
  background-color: #2155c5;  /* 悬停时更深 */
}

/* 新增按钮 */
.add-btn {
  background-color: #2563eb;  /* 加深的蓝色 */
  color: white;
  text-decoration: none;
}

.add-btn:hover {
  background-color: #1a5ac0;  /* 悬停时深 */
}

/* 编辑按钮 */
.edit-btn {
  background-color: #2563eb; /* 浅蓝色 */
  color: white;
}

.edit-btn:hover {
  background-color: #1a5ac0; /* 悬停时稍深 */
}

/* 重置按钮 */
.reset-btn {
  background-color: #2563eb;  /* 加深的灰色 */
  color: white;  /* 改为白色文字 */
}

.reset-btn:hover {
  background-color: #1855d7;  /* 悬停时更深 */
}

/* 删除按钮保持原样 */
.delete-btn {
  background-color: #ef4444;
  color: white;
}

.delete-btn:hover {
  background-color: #dc2626;
}

/* 表格容器 */
.table-container {
  margin-top: 24px;
  border-radius: 8px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

/* 表格样式 */
.student-table {
  width: 100%;
  min-width: 1200px;
  border-collapse: separate;
  border-spacing: 0;
  text-align: center;
}

.student-table th {
  background-color: #f8fafc;
  padding: 16px 12px;
  font-weight: 600;
  color: #1e293b;
  border-bottom: 2px solid #e2e8f0;
  white-space: nowrap;
  text-align: center;
}

.student-table td {
  padding: 14px 12px;
  border-bottom: 1px solid #e2e8f0;
  color: #475569;
  transition: all 0.2s;
  text-align: center;
  vertical-align: middle;
  height: 60px;
}

/* 修改状态列的样式 */
.student-table td:nth-child(4) {
  text-align: center;
}

.student-table td:nth-child(4) > div {
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.status-dot {
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-right: 8px;
}

/* 确保状态文本与点对齐 */
.student-table td:nth-child(4) span {
  display: inline-flex;
  align-items: center;
}

.student-table tbody tr {
  height: 60px; /* 统一行高 */
}

.student-table tbody tr:hover {
  background-color: #f8fafc;
}

/* 操作按钮 */
.operation-buttons {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  padding: 0 4px;
}

.edit-btn {
  background-color: #3b82f6;
  color: white;
}

.edit-btn:hover {
  background-color: #2563eb;
}

.delete-btn {
  background-color: #ef4444;
  color: white;
}

.delete-btn:hover {
  background-color: #dc2626;
}

/* 分页样式 */
.pagination {
  margin-top: 24px;
  padding: 16px 0;
  display: flex;
  justify-content: center;
  gap: 8px;
  border-top: 1px solid #e2e8f0;
}

.pagination button {
  padding: 6px 12px;
  height: 32px;
  min-width: 70px;
  font-size: 13px;
  border: 1px solid #e2e8f0;
  border-radius: 6px;
  background-color: white;
  color: #475569;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.pagination button:hover:not(:disabled) {
  border-color: #4f46e5;
  color: #4f46e5;
}

.pagination button:disabled {
  background-color: #f8fafc;
  color: #94a3b8;
  cursor: not-allowed;
}

.page-info {
  padding: 6px 12px;
  background-color: #f8fafc;
  border-radius: 6px;
  color: #475569;
  font-weight: 500;
  font-size: 13px;
}

/* 状态样式 */
.status-dot {
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-right: 8px;
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

.selected-row {
  background-color: #f0f7ff !important;
}
</style>