<template>
  <div class="form-container">
    <h2 class="page-title">修改学生信息</h2>
    <form class="student-form" @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>学号</label>
        <input type="text" v-model="formData.stuNum" placeholder="请输入学号" readonly>
      </div>
      <div class="form-group">
        <label>姓名</label>
        <input type="text" v-model="formData.stuName" placeholder="请输入姓名" required>
      </div>
      <div class="form-group">
        <label>性别</label>
        <select v-model="formData.stuSex" required>
          <option value="">请选择性别</option>
          <option value="男">男</option>
          <option value="女">女</option>
        </select>
      </div>
      <div class="form-group">
        <label>班级</label>
        <input type="text" v-model="formData.stuClass" placeholder="请输入班级" required>
      </div>
      <div class="form-group">
        <label>专业</label>
        <input type="text" v-model="formData.stuMajor" placeholder="请输入专业" required>
      </div>
      <div class="form-group">
        <label>学院</label>
        <input type="text" v-model="formData.stuCollege" placeholder="请输入学院" required>
      </div>
      <div class="form-group">
        <label>电话</label>
        <input type="tel" v-model="formData.stuPhone" placeholder="请输入电话号码" required>
      </div>
      <div class="form-group">
        <label>入学时间</label>
        <input type="date" v-model="formData.stuTime" required>
      </div>
      <div class="form-group">
        <label>学生状态</label>
        <select v-model="formData.stuState" required>
          <option value="在读">在读</option>
          <option value="休学">休学</option>
          <option value="退学">退学</option>
          <option value="毕业">毕业</option>
        </select>
      </div>
      <div class="form-group">
        <label>家庭住址</label>
        <input type="text" v-model="formData.stuHome" placeholder="请输入家庭住址" required>
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
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const route = useRoute();
const API_BASE_URL = 'http://localhost:8080/student';

const formData = ref({
  stuNum: '',
  stuName: '',
  stuSex: '',
  stuClass: '',
  stuMajor: '',
  stuCollege: '',
  stuPhone: '',
  stuTime: '',
  stuState: '',
  stuHome: ''
});

onMounted(() => {
  // 更严谨地从路由查询参数中获取学生信息，只获取期望的字段并进行类型转换等处理（假设后端要求对应的数据类型）
  const queryParams = route.query;
  formData.value.stuNum = queryParams.stuNum || '';
  formData.value.stuName = queryParams.stuName || '';
  formData.value.stuSex = queryParams.stuSex || '';
  formData.value.stuClass = queryParams.stuClass || '';
  formData.value.stuMajor = queryParams.stuMajor || '';
  formData.value.stuCollege = queryParams.stuCollege || '';
  formData.value.stuPhone = queryParams.stuPhone || '';
  formData.value.stuTime = queryParams.stuTime || '';
  formData.value.stuState = queryParams.stuState || '';
  formData.value.stuHome = queryParams.stuHome || '';
});

const handleSubmit = async () => {
  // 转换数据为服务器期望的格式（snake_case）
  const studentInfoToUpdate = {
    stu_num: formData.value.stuNum,
    stu_name: formData.value.stuName,
    stu_sex: formData.value.stuSex,
    stu_class: formData.value.stuClass,
    stu_major: formData.value.stuMajor,
    stu_college: formData.value.stuCollege,
    stu_phone: formData.value.stuPhone,
    stu_time: formData.value.stuTime,
    stu_state: formData.value.stuState,
    stu_home: formData.value.stuHome
  };

  try {
    const response = await axios.put(`${API_BASE_URL}/update`, studentInfoToUpdate);
    if (response.status === 200) {
      alert('修改成功');
      router.push('/list');
    } else {
      throw new Error('修改失败');
    }
  } catch (error) {
    console.error('修改学生信息出错：', error);
    alert(error.message || '修改失败，请稍后重试');
  }
};

const handleCancel = () => {
  router.push('/list');
};
</script>

<style scoped>
/* 使用与 add.vue 相同的样式 */
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

.student-form {
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

.form-group input,
.form-group select {
  padding: 8px 12px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  font-size: 14px;
  transition: all 0.3s;
}

.form-group input:focus,
.form-group select:focus {
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