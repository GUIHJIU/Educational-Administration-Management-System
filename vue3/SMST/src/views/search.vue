<template>
    <div class="student-info-container">
      <!-- 页面标题 -->
      <h2 class="page-title">学生信息列表</h2>
      <!-- 搜索框区域 -->
      <div class="search-container">
        <div class="search-item">
          <input
            type="text"
            id="stuNumSearch"
            placeholder="请输入学号搜索"
            v-model="searchStuNum"
            @input="filterStudents"
          />
          <button class="search-btn" @click="filterStudentsByStuNum">搜索</button>
        </div>
        <div class="search-item">
          <input
            type="text"
            id="stuNameSearch"
            placeholder="请输入姓名搜索"
            v-model="searchStuName"
            @input="filterStudents"
          />
          <button class="search-btn" @click="filterStudentsByStuName">搜索</button>
        </div>
        <button class="reset-btn" @click="resetSearch">重置</button>
      </div>
      <!-- 导航按钮区域 -->
      <nav class="nav-buttons">
        <RouterLink class="nav-link add-link" to="/add">增加</RouterLink>
      </nav>
      <!-- 学生信息表格 -->
      <div class="student-table-wrapper">
        <table class="student-table">
          <thead>
            <tr>
              <!-- 序号表头 -->
              <th class="serial-number-header">序号</th>
              <!-- 学号表头 -->
              <th class="student-num-header">学号</th>
              <!-- 姓名表头 -->
              <th class="student-name-header">姓名</th>
              <!-- 性别表头 -->
              <th class="student-sex-header">性别</th>
              <!-- 班级表头 -->
              <th class="student-class-header">班级</th>
              <!-- 专业表头 -->
              <th class="student-major-header">专业</th>
              <!-- 学院表头 -->
              <th class="student-college-header">学院</th>
              <!-- 电话表头 -->
              <th class="student-phone-header">电话</th>
              <!-- 入学时间表头 -->
              <th class="student-time-header">入学时间</th>
              <!-- 操作表头 -->
              <th class="operation-header">操作</th>
            </tr>
          </thead>
          <tbody class="tbody-scrollable">
            <!-- 循环渲染学生信息列表，每条学生信息对应一行 -->
            <tr v-for="(student, index) in paginatedStudentList" :key="student.studentId">
              <!-- 显示序号 -->
              <td class="serial-number">{{ index + 1 }}</td>
              <td class="student-num">{{ student.stuNum }}</td>
              <td class="student-name">{{ student.stuName }}</td>
              <td class="student-sex">{{ student.stuSex }}</td>
              <td class="student-class">{{ student.stuClass }}</td>
              <td class="student-major">{{ student.stuMajor }}</td>
              <td class="student-college">{{ student.stuCollege }}</td>
              <td class="student-phone">{{ student.stuPhone }}</td>
              <td class="student-time">{{ student.stuTime }}</td>
              <td class="operation-buttons">
                <!-- 修改按钮，点击跳转到修改学生信息页面并传递学生ID参数 -->
                <button class="edit-btn" @click="editStudent(student)">修改</button>
                <!-- 删除按钮，点击调用deleteStudent方法并传递当前行学生的学号 -->
                <button class="delete-btn" @click="deleteStudent(student.stuNum)">删除</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- 分页导航区域 -->
      <div class="pagination">
        <button @click="prevPage" :disabled="currentPage === 1">首页</button>
        <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
        <span>{{ currentPage }} / {{ totalPages }}</span>
        <button @click="nextPage" :disabled="currentPage === totalPages">下一页</button>
        <button @click="nextPage" :disabled="currentPage === totalPages">尾页</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, computed, watchEffect } from 'vue';
  import axios from 'axios';
  import { useRouter } from 'vue-router';
  
  // 响应式变量，用于存储从后端获取的原始学生信息列表，初始化为空数组
  const studentList = ref([]);
  // 响应式变量，用于存储用户输入的学号搜索关键词，初始为空字符串
  const searchStuNum = ref('');
  // 响应式变量，用于存储用户输入的姓名搜索关键词，初始为空字符串
  const searchStuName = ref('');
  // 响应式变量，用于存储当前页码，初始为 1
  const currentPage = ref(1);
  // 响应式变量，用于存储每页显示的学生数量，这里设置为 10
  const pageSize = ref(10);
  // 响应式变量，用于存储经过搜索过滤后的学生信息列表，通过计算属性根据搜索关键词动态计算
  const filteredStudentList = computed(() => {
    return studentList.value.filter(student => 
    {
      const matchStuNum =!searchStuNum.value || student.stuNum.includes(searchStuNum.value);
      const matchStuName =!searchStuName.value || student.stuName.includes(searchStuName.value);
      return matchStuNum && matchStuName;
    });
  });
  // 响应式变量，用于存储分页后的学生信息列表
  const paginatedStudentList = computed(() => {
    const startIndex = (currentPage.value - 1) * pageSize.value;
    const endIndex = startIndex + pageSize.value;
    return filteredStudentList.value.slice(startIndex, endIndex);
  });
  // 响应式变量，用于存储总页数
  const totalPages = computed(() => {
    return Math.ceil(filteredStudentList.value.length / pageSize.value);
  });
  // 获取路由实例，用于页面跳转操作
  const router = useRouter();
  
  // 全局配置后端接口基础地址，方便统一管理和修改
  const API_BASE_URL = 'http://localhost:8080/student';
  
  // 在组件挂载后，发送 HTTP GET 请求获取学生信息数据，优化错误提示信息
  onMounted(() => {
    fetchStudentList();
  });
  
  // 封装获取学生信息列表的方法，方便复用和统一错误处理
  const fetchStudentList = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/List`);
      if (response.status === 200) {
        studentList.value = response.data;
      } else {
        console.error('获取学生信息列表失败', response);
        alert(`获取学生信息列表失败，错误信息：${response.statusText}，请稍后重试`);
      }
    } catch (error) {
      console.error('获取学生信息列表出错：', error);
      alert('获取学生信息列表出现错误，请联系管理员');
    }
  };
  
  // 根据学号搜索关键词过滤学生信息列表的方法
  const filterStudentsByStuNum = () => {
    currentPage.value = 1;
    const newFilteredList = studentList.value.filter(student =>
      student.stuN.includes(searchStuNum.value)
    );
    filteredStudentList.value = newFilteredList;
  };
  
  // 根据姓名搜索关键词过滤学生信息列表的方法
  const filterStudentsByStuName = () => 
  {
    currentPage.value = 1;
    const newFilteredList = studentList.value.filter(student =>
      student.stuName.includes(searchStuName.value)
    );
    filteredStudentList.value = newFilteredList;
  };
  
  // 重置搜索条件的方法
  const resetSearch = () => {
    searchStuNum.value = '';
    searchStuName.value = '';
    currentPage.value = 1;
  };
  
  // 跳转到添加学生信息页面的方法，通过路由名称 'add' 进行跳转（可根据实际需求保留或删除，已有RouterLink也能实现跳转）
  const goToAddPage = () => {
    router.push({ name: 'add' });
  };
  
  // 跳转到修改学生信息页面的方法，传递当前选中学生的 ID 作为参数，路由名称为 'update'
  const editStudent = (student) => {
    router.push({ name: 'update', params: { studentId: student.studentId } });
  };
  
  // 修改后的删除学生信息方法，确保删除后能自动触发页面更新
  const deleteStudent = async (stuNum) => {
    try {
      // 调用后端删除接口，将传递进来的学号拼接到URL中，发送DELETE请求给后端
      const response = await axios.delete(`${API_BASE_URL}/delete/${stuNum}`);
      if (response.status === 200) {
        const updatedList = studentList.value.filter(s => s.stuNum!== stuNum);
        studentList.value = updatedList;
      } else {
        console.error('删除学生信息失败', response);
        alert('删除学生信息失败，请稍后重试');
      }
    } catch (error) {
      console.error('删除学生信息出错：', error);
      alert('删除学生信息出现网络错误，请联系管理员');
    }
  };
  
  // 上一页方法
  const prevPage = () => {
    if (currentPage.value > 1) {
      currentPage.value--;
    }
  };
  
  // 下一页方法
  const nextPage = () => {
    if (currentPage.value < totalPages.value) {
      currentPage.value++;
    }
  };
  </script>
  
  <style scoped>
  .student-info-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
  }
  
  .page-title {
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
    color: #333;
  }
  
  .search-container {
    margin-bottom: 10px;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
  }
  
  .search-item {
    margin: 0 10px 10px 10px;
    display: flex;
    flex-direction: row;
    align-items: center;
  }
  
  .search-item label {
    margin-right: 5px;
  }
  
  .search-item input {
    width: 200px;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .search-btn,
  .reset-btn {
    padding: 8px 15px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    margin: 0 5px;
    font-size: 14px;
  }
  
  .search-btn {
    background-color: #007bff;
    color: white;
  }
  
  .search-btn:hover {
    background-color: #0056b3;
  }
  
  .reset-btn {
    background-color: #ccc;
  }
  
  .reset-btn:hover {
    background-color: #aaa;
  }
  
  .nav-buttons {
    display: flex;
    justify-content: space-around;
    margin-bottom: 10px;
  }
  
  .nav-link {
    background-color: #007bff;
    color: white;
    text-decoration: none;
    padding: 10px 20px;
    border-radius: 4px;
    transition: background-color 0.3s ease;
    font-size: 16px;
  }
  
  .nav-link:hover {
    background-color: #0056b3;
  }
  
  .student-table-wrapper {
    max-height: 300px;
    overflow-y: auto;
  }
  
  .student-table {
    width: 100%;
    margin: 0 auto;
    border-collapse: collapse;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  }
  
  thead {
    position: sticky;
    top: 0;
    background-color: #f4f4f4;
  }
  
  th,
  td {
    border: 1px solid #ccc;
    padding: 12px 15px;
    text-align: center;
  }
  
  .serial-number-header,
  .student-num-header,
  .student-name-header,
  .student-sex-header,
  .student-class-header,
  .student-major-header,
  .student-college-header,
  .student-phone-header,
  .student-time-header,
  .operation-header {
    font-weight: bold;
  }
  
  .operation-buttons button {
    background-color: #e6f7ff;
    color: #333;
    border: none;
    border-radius: 4px;
    padding: 8px 15px;
    margin-right: 10px;
    margin-top: 10px;
    margin-bottom: 10px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    white-space: nowrap;
    display: inline-block;
    font-size: 14px;
  }
  
  .operation-buttons button:hover {
    background-color: #b3e0ff;
  }
  
  .delete-btn {
    margin-right: 0;
  }
  
  .pagination {
    display: flex;
    justify-content: center;
    margin-top: 10px;
  }
  
  .pagination button {
    padding: 8px 15px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    margin: 0 5px;
    font-size: 14px;
  }
  
  .pagination button:hover {
    background-color: #ccc;
  }
  </style>这段代码，将学号搜索和姓名搜索合成一个，并给出美化般的，功能依旧可以实现