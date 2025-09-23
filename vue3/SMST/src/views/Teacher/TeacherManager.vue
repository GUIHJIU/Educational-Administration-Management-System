<template>
  <h1></h1>
  <div class="list-container">
    <h2 class="page-title">教师信息管理</h2>

    <div class="top-actions">
      <div class="search-container">
        <div class="search-item">
          <i class="fas fa-search search-icon"></i>
          <input
              type="text"
              placeholder="请输入教师工号或姓名搜索"
              v-model="searchKeyword"
              @input="handleSearch"
          />
        </div>
        <button class="btn search-btn" @click="handleSearch">
          <i class="fas fa-search"></i>
          <span>搜索</span>
        </button>
      </div>
      <div class="button-group">
        <button class="btn add-btn" @click="handleAdd">
          <i class="fas fa-plus"></i>
          <span>添加</span>
        </button>
        <button class="btn edit-btn" @click="handleEdit">
          <i class="fas fa-edit"></i>
          <span>编辑</span>
        </button>
        <button class="btn delete-btn" @click="handleDelete">
          <i class="fas fa-trash"></i>
          <span>删除</span>
        </button>
      </div>
    </div>

    <div class="table-container">
      <table class="student-table">
        <thead>
        <tr>
          <th>选择</th>
          <th>序号</th>
          <th>工号</th>
          <th>姓名</th>
          <th>性别</th>
          <th>电话</th>
          <th>学院</th>
          <th>专业</th>
          <th>创建时间</th>
          <th>更新时间</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(teacher, index) in paginatedTeacherList" :key="teacher.id">
          <td>
            <input
                type="checkbox"
                :value="teacher.id"
                v-model="selectedTeachers"
            />
          </td>
          <td>{{ (currentPage - 1) * pageSize + index + 1 }}</td>
          <td>{{ teacher.teacherNum }}</td>
          <td>{{ teacher.teacherName }}</td>
          <td>{{ teacher.teacherSex }}</td>
          <td>{{ teacher.teacherPhone }}</td>
          <td>{{ teacher.teacherCollege }}</td>
          <td>{{ teacher.teacherMajor }}</td>
          <td>{{ teacher.createTime }}</td>
          <td>{{ teacher.updateTime }}</td>
        </tr>
        </tbody>
      </table>

      <div v-if="paginatedTeacherList.length === 0" class="empty-message">
        <p>暂无教师信息</p>
      </div>
    </div>

    <div class="pagination-container">
      <div class="pagination-info">
        共 {{ filteredTeacherList.length }} 条记录，第 {{ currentPage }} 页，共 {{ totalPages }} 页
      </div>
      <div class="pagination-controls">
        <button
            class="btn pagination-btn"
            :disabled="currentPage === 1"
            @click="prevPage"
        >
          上一页
        </button>
        <span class="pagination-text">{{ currentPage }} / {{ totalPages }}</span>
        <button
            class="btn pagination-btn"
            :disabled="currentPage === totalPages"
            @click="nextPage"
        >
          下一页
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {computed, onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import apiClient from '@/utils/axios';

interface Teacher {
  id: number;
  teacherNum: string;
  teacherName: string;
  teacherSex: string;
  teacherPhone: string;
  teacherCollege: string;
  teacherMajor: string;
  createTime: string;
  updateTime: string;
}

const router = useRouter();
const teacherList = ref<Teacher[]>([]);
const searchKeyword = ref('');
const currentPage = ref(1);
const pageSize = ref(10);
const selectedTeachers = ref<number[]>([]);
const API_BASE_URL = 'https://localhost:443/teachers';

// 计算属性：过滤后的教师列表
const filteredTeacherList = computed(() => {
  const keyword = searchKeyword.value.trim().toLowerCase();
  if (!keyword) {
    return teacherList.value;
  }
  return teacherList.value.filter(teacher => {
    const teacherNum = teacher.teacherNum?.toString().toLowerCase() || '';
    const teacherName = teacher.teacherName?.toString().toLowerCase() || '';
    return teacherNum.includes(keyword) || teacherName.includes(keyword);
  });
});

// 计算属性：分页后的教师列表
const paginatedTeacherList = computed(() => {
  const startIndex = (currentPage.value - 1) * pageSize.value;
  const endIndex = startIndex + pageSize.value;
  return filteredTeacherList.value.slice(startIndex, endIndex);
});

// 计算属性：总页数
const totalPages = computed(() => {
  return Math.ceil(filteredTeacherList.value.length / pageSize.value);
});

// 生命周期钩子
onMounted(() => {
  fetchTeacherList();
});

// 方法定义
const fetchTeacherList = async () => {
  try {
    const response = await apiClient.get(`${API_BASE_URL}/List`);
    if (response.data && response.status === 200) {
      // 转换下划线命名为驼峰命名
      const convertData = (data) => {
        return data.map(item => ({
          id: item.id,
          teacherNum: item.teacher_num,
          teacherName: item.teacher_name,
          teacherSex: item.teacher_sex,
          teacherPhone: item.teacher_phone,
          teacherCollege: item.teacher_college,
          teacherMajor: item.teacher_major,
          createTime: item.create_time,
          updateTime: item.update_time
        }));
      };

      teacherList.value = Array.isArray(response.data) ? convertData(response.data) :
          Array.isArray(response.data.data) ? convertData(response.data.data) : [];
    } else {
      console.error('获取教师信息列表失败', response);
      alert('获取教师列表失败，请稍后重试');
    }
  } catch (error) {
    console.error('获取教师信息列表出错：', error);
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

const handleAdd = () => {
  router.push('/teacher/add');
};

const handleEdit = () => {
  if (selectedTeachers.value.length !== 1) {
    alert('请选择一条记录进行编辑');
    return;
  }
  router.push(`/teacher/edit/${selectedTeachers.value[0]}`);
};

const handleDelete = async () => {
  if (selectedTeachers.value.length === 0) {
    alert('请至少选择一条记录进行删除');
    return;
  }

  if (!confirm('确定要删除选中的教师信息吗？')) {
    return;
  }

  try {
    // 批量删除
    const deletePromises = selectedTeachers.value.map(id =>
        apiClient.delete(`${API_BASE_URL}/delete/${id}`)
    );

    await Promise.all(deletePromises);
    alert('删除成功');
    selectedTeachers.value = [];
    await fetchTeacherList();
  } catch (error) {
    console.error('删除教师信息出错：', error);
    alert('删除失败，请稍后重试');
  }
};
</script>

<style scoped>
.list-container {
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  margin: 20px;
}

.page-title {
  color: #262626;
  font-size: 24px;
  margin-bottom: 24px;
  text-align: center;
}

.top-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 0 10px;
}

.search-container {
  display: flex;
  align-items: center;
  gap: 12px;
}

.search-item {
  position: relative;
  display: flex;
  align-items: center;
}

.search-icon {
  position: absolute;
  left: 12px;
  color: #bfbfbf;
  z-index: 1;
}

.search-item input {
  padding: 8px 12px 8px 36px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  font-size: 14px;
  width: 240px;
  transition: all 0.3s;
}

.search-item input:focus {
  border-color: #40a9ff;
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
  outline: none;
}

.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  display: inline-flex;
  align-items: center;
  gap: 6px;
  transition: all 0.3s;
  height: 36px;
}

.search-btn {
  background-color: #40a9ff;
  color: white;
}

.search-btn:hover {
  background-color: #1890ff;
}

.add-btn {
  background-color: #52c41a;
  color: white;
}

.add-btn:hover {
  background-color: #389e0d;
}

.edit-btn {
  background-color: #faad14;
  color: white;
}

.edit-btn:hover {
  background-color: #d48806;
}

.delete-btn {
  background-color: #ff4d4f;
  color: white;
}

.delete-btn:hover {
  background-color: #f5222d;
}

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

.student-table th,
.student-table td {
  padding: 12px 8px;
  border-bottom: 1px solid #f0f0f0;
}

.student-table th {
  background-color: #fafafa;
  font-weight: 600;
  color: #595959;
  font-size: 14px;
}

.student-table tbody tr:hover {
  background-color: #f5f5f5;
}

.student-table tbody tr:last-child td {
  border-bottom: none;
}

.empty-message {
  text-align: center;
  padding: 40px 0;
  color: #8c8c8c;
}

.pagination-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 24px;
  padding: 0 10px;
}

.pagination-info {
  color: #8c8c8c;
  font-size: 14px;
}

.pagination-controls {
  display: flex;
  align-items: center;
  gap: 12px;
}

.pagination-btn {
  background-color: #fff;
  border: 1px solid #d9d9d9;
  color: #595959;
}

.pagination-btn:hover:not(:disabled) {
  border-color: #40a9ff;
  color: #40a9ff;
}

.pagination-btn:disabled {
  color: #bfbfbf;
  cursor: not-allowed;
}

.pagination-text {
  color: #595959;
  font-size: 14px;
}
</style>
