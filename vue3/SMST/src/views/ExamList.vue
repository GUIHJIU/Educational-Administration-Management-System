<template>
    <div class="list-container">
      <h2 class="page-title">考试信息列表</h2>
      
      <div class="top-actions">
        <div class="search-container">
          <div class="search-item">
            <i class="fas fa-search search-icon"></i>
            <input
              type="text"
              placeholder="请输入班级或科目搜索"
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
        
        <RouterLink class="btn add-btn" to="/examAdd">
          <i class="fas fa-plus"></i>
          <span>新增</span>
        </RouterLink>
      </div>
  
      <div class="table-container">
        <table class="exam-table">
          <thead>
            <tr>
              <th>序号</th>
              <th>班级</th>
              <th>考试科目</th>
              <th>考试时间</th>
              <th>考试地点</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody @click="handleTableRowClick">
            <tr v-for="(exam, index) in paginatedExamList" 
                :key="exam.class_id" 
                :class="{ 'selected-row': selectedExam === exam }">
              <td>{{ index + 1 }}</td>
              <td>{{ exam.class_id }}</td>
              <td>{{ exam.subjects }}</td>
              <td>{{ exam.time_exam }}</td>
              <td>{{ exam.locations }}</td>
              <td class="operation-buttons">
                <button class="btn edit-btn" @click="editExam(exam)">
                  <i class="fas fa-edit"></i>
                  <span>编辑</span>
                </button>
                <button class="btn delete-btn" @click="deleteExam(exam.class_id)">
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
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted, computed, watchEffect } from 'vue';
  import axios, { AxiosError } from 'axios';
  import { useRouter } from 'vue-router';
  import { store } from '../store/index';
  
  // 响应式变量声明
  interface Exam {
    class_id: string;
    subjects: string;
    time_exam: string;
    locations: string;
  }
  const examList = ref<Exam[]>([]);
  const searchKeyword = ref('');
  const currentPage = ref(1);
  const pageSize = ref(10);
  const selectedExam = ref<Exam | null>(null);
  const router = useRouter();
  const studentId = ref('');
  
  const API_BASE_URL = 'http://localhost:8080/exams';
  
  // 计算属性
  const filteredExamList = computed(() => {
    const keyword = searchKeyword.value.trim().toLowerCase();
    if (!keyword) {
      return examList.value;
    }
    return examList.value.filter(exam => {
      const classId = exam.class_id?.toString().toLowerCase() || '';
      const subjects = exam.subjects?.toString().toLowerCase() || '';
      return classId.includes(keyword) || subjects.includes(keyword);
    });
  });
  
  const paginatedExamList = computed(() => {
    const startIndex = (currentPage.value - 1) * pageSize.value;
    const endIndex = startIndex + pageSize.value;
    return filteredExamList.value.slice(startIndex, endIndex);
  });
  
  const totalPages = computed(() => {
    return Math.ceil(filteredExamList.value.length / pageSize.value);
  });
  
  // 生命周期钩子
  onMounted(() => {
    studentId.value = store.state.studentId;
    fetchExamList();
  });
  
  // 方法定义
  const fetchExamList = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/List`);
      if (response.data && response.status === 200) {
        examList.value = Array.isArray(response.data) ? response.data : 
                         Array.isArray(response.data.data) ? response.data.data : [];
      } else {
        console.error('获取考试信息列表失败', response);
        alert('获取考试列表失败，请稍后重试');
      }
    } catch (error) {
      console.error('获取考试信息列表出错：', error);
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
  
  const editExam = (exam: Exam) => {
    const formattedExam = {
      class_id: exam.class_id?.toString() || '',
      subjects: exam.subjects?.toString() || '',
      time_exam: exam.time_exam?.toString() || '',
      locations: exam.locations?.toString() || ''
    };
    router.push({ 
      name: 'ExamUpdate',
      query: formattedExam
    });
  };
  
  const deleteExam = async (classId: string) => {
    try {
      if (!confirm('确定要删除该考试信息吗？')) {
        return;
      }
  
      const response = await axios.delete(`${API_BASE_URL}/delete/${classId}`);
      if (response.status === 200) {
        alert('删除成功');
        await fetchExamList();
        
        const maxPage = Math.ceil((examList.value.length - 1) / pageSize.value);
        if (currentPage.value > maxPage && maxPage > 0) {
          currentPage.value = maxPage;
        }
      } else {
        throw new Error('删除考试信息失败');
      }
    } catch (error) {
      const axiosError = error as AxiosError;
      console.error('删除考试信息出错：', axiosError);
      alert(axiosError.message || '删除失败，请稍后重试');
    }
  };
  
  const handleTableRowClick = (event: MouseEvent) => {
    if (!event.target) {
      return;
    }
    
    if ((event.target as HTMLElement).tagName === 'BUTTON') {
      return;
    }
    
    const row = (event.target as HTMLElement).closest('tr');
    if (row) {
      const parentNode = row.parentNode;
      if (parentNode) {
        const examIndex = Array.from(parentNode.children).indexOf(row);
        const exam = paginatedExamList.value[examIndex];
        if (exam) {
          selectedExam.value = exam;
        }
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
    const maxPage = Math.ceil(filteredExamList.value.length / pageSize.value);
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
  .exam-table tbody:empty::after {
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
  .exam-table th:first-child,
  .exam-table td:first-child {
    width: 80px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  
  /* 其他列的文字可以换行 */
  .exam-table td:not(:first-child) {
    white-space: normal;
    word-break: break-all;
  }
  
  /* 表格标题样式 */
  .exam-table th:last-child,
  .exam-table td:last-child {
    text-align: center;
    padding: 14px 20px;
    width: 270px;
    position: relative;
  }
  </style>