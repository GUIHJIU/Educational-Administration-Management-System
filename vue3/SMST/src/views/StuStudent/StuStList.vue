<template>
  <h1>                                        </h1>
  <div class="list-container">
    <div class="page-header">
      <h1 class="page-title">学生个人信息</h1>
    </div>

    <div class="content-wrapper">
      <div class="info-card" v-if="studentInfo">
        <div class="info-header">
          <div class="student-basic">
            <h2>{{ studentInfo.stuName }}</h2>
            <div class="status-badge" :class="getStatusClass(studentInfo.stuState)">
              {{ getStatusText(studentInfo.stuState) }}
            </div>
          </div>
          <button class="btn edit-btn" @click="editStudent(studentInfo)">
            <i class="fas fa-edit"></i>
            <span>编辑信息</span>
          </button>
        </div>

        <div class="info-grid">
          <div class="info-item">
            <label>学号</label>
            <span>{{ studentInfo.stuNum }}</span>
          </div>
          <div class="info-item">
            <label>性别</label>
            <span>{{ studentInfo.stuSex }}</span>
          </div>
          <div class="info-item">
            <label>班级</label>
            <span>{{ studentInfo.stuClass }}</span>
          </div>
          <div class="info-item">
            <label>专业</label>
            <span>{{ studentInfo.stuMajor }}</span>
          </div>
          <div class="info-item">
            <label>学院</label>
            <span>{{ studentInfo.stuCollege }}</span>
          </div>
          <div class="info-item">
            <label>电话</label>
            <span>{{ studentInfo.stuPhone }}</span>
          </div>
          <div class="info-item">
            <label>入学时间</label>
            <span>{{ studentInfo.stuTime }}</span>
          </div>
          <div class="info-item full-width">
            <label>家庭住址</label>
            <span>{{ studentInfo.stuHome }}</span>
          </div>
        </div>
      </div>
      <div v-else class="loading">
        <div class="loading-spinner"></div>
        <span>加载中...</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useridentitystore } from '@/store/userStore';

const studentInfo = ref(null);
const router = useRouter();
const userStore = useridentitystore();

const API_BASE_URL = 'http://localhost:8080/student';

// 生命周期钩子
onMounted(() => {
  fetchStudentInfo();
});

// 方法定义
const fetchStudentInfo = async () => {
  try {
    // 使用store中保存的账号（学号）获取学生信息
    const stuNum = userStore.account;
    console.log('Current student number:', stuNum); // 调试日志

    // 如果没有学号，尝试从localStorage获取
    if (!stuNum) {
      const savedAccount = localStorage.getItem('userAccount');
      if (savedAccount) {
        userStore.account = savedAccount;
        console.log('Retrieved account from localStorage:', savedAccount);
      } else {
        console.error('No student number found in store or localStorage');
        alert('未找到学号信息，请重新登录');
        router.push('/'); // 重定向到登录页
        return;
      }
    }

    const response = await axios.get(`${API_BASE_URL}/List`);
    
    if (response.status === 200) {
      let data = response.data?.data || response.data || [];
      console.log('Received data:', data); // 调试日志

      // 在列表中查找匹配的学生
      const studentData = data.find(item => {
        const itemNum = String(item.stu_num || '').trim();
        const searchNum = String(userStore.account).trim();
        console.log('Comparing:', itemNum, 'with', searchNum); // 调试日志
        // 使用相等比较，确保学号完全匹配
        return itemNum === searchNum;
      });

      if (!studentData) {
        console.error('Student not found for number:', userStore.account);
        alert('未找到学生信息，请确认学号是否正确');
        return;
      }

      // 转换数据格式
      studentInfo.value = {
        stuNum: studentData.stu_num || '',
        stuName: studentData.stu_name || '',
        stuSex: studentData.stu_sex || '',
        stuPhone: studentData.stu_phone || '',
        stuTime: studentData.stu_time || '',
        stuClass: studentData.stu_class || '',
        stuMajor: studentData.stu_major || '',
        stuCollege: studentData.stu_college || '',
        stuState: studentData.stu_state || '在读',
        stuHome: studentData.stu_home || ''
      };
    } else {
      throw new Error('获取学生信息失败');
    }
  } catch (error) {
    console.error('获取学生信息出错：', error);
    alert('获取数据失败，请稍后重试');
  }
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
    path: '/StudentList/Stuupdate',
    query: formattedStudent
  });
};

// 状态相关方法
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
  if (!state) return '未���';
  return String(state).trim();
};
</script>

<style scoped>
.list-container {
  background-color: #f8fafc;
  min-height: 100vh;
  padding: 0;
}

.page-header {
  background: linear-gradient(135deg, #1e88e5 0%, #1565c0 100%);
  padding: 32px 24px;
  margin-bottom: 32px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.page-title {
  color: white;
  font-size: 28px;
  font-weight: 600;
  text-align: center;
  margin: 0;
  letter-spacing: 1px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
}

.info-card {
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 24px rgba(0, 0, 0, 0.06);
  padding: 32px;
  margin-bottom: 32px;
}

.info-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  padding-bottom: 24px;
  border-bottom: 1px solid #e2e8f0;
}

.student-basic {
  display: flex;
  align-items: center;
  gap: 16px;
}

.student-basic h2 {
  font-size: 24px;
  color: #1e293b;
  margin: 0;
}

.status-badge {
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: 500;
}

.status-active {
  background-color: #dcfce7;
  color: #15803d;
}

.status-warning {
  background-color: #fef3c7;
  color: #92400e;
}

.status-inactive {
  background-color: #fee2e2;
  color: #b91c1c;
}

.status-graduate {
  background-color: #dbeafe;
  color: #1e40af;
}

.edit-btn {
  background-color: #2563eb;
  color: white;
  padding: 10px 20px;
  border-radius: 8px;
  border: none;
  font-size: 14px;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.edit-btn:hover {
  background-color: #1d4ed8;
  transform: translateY(-1px);
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 24px;
}

.info-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.info-item.full-width {
  grid-column: 1 / -1;
}

.info-item label {
  font-size: 14px;
  color: #64748b;
  font-weight: 500;
}

.info-item span {
  font-size: 16px;
  color: #1e293b;
  font-weight: 400;
}

.loading {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 48px;
  gap: 16px;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 3px solid #e2e8f0;
  border-top-color: #2563eb;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

/* 响应式调整 */
@media (max-width: 768px) {
  .info-header {
    flex-direction: column;
    gap: 16px;
    align-items: flex-start;
  }

  .edit-btn {
    width: 100%;
    justify-content: center;
  }

  .info-grid {
    grid-template-columns: 1fr;
  }
}
h1{
  background-color: #0d5db9;
  color: #d9d9d9;
  padding: 20px;
  text-align: center;
  border-radius: 10px;
}

</style>