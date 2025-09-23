<template>
  <h1></h1>
  <div class="form-container">
    <h2 class="page-title">编辑教师信息</h2>

    <form @submit.prevent="handleSubmit" class="teacher-form">
      <div class="form-group">
        <label for="teacherNum">教师工号 *</label>
        <input
            type="text"
            id="teacherNum"
            v-model="formData.teacherNum"
            required
            placeholder="请输入教师工号"
            readonly
        />
      </div>

      <div class="form-group">
        <label for="teacherName">教师姓名 *</label>
        <input
            type="text"
            id="teacherName"
            v-model="formData.teacherName"
            required
            placeholder="请输入教师姓名"
        />
      </div>

      <div class="form-group">
        <label for="teacherSex">性别 *</label>
        <select id="teacherSex" v-model="formData.teacherSex" required>
          <option value="">请选择性别</option>
          <option value="男">男</option>
          <option value="女">女</option>
        </select>
      </div>

      <div class="form-group">
        <label for="teacherPhone">电话 *</label>
        <input
            type="text"
            id="teacherPhone"
            v-model="formData.teacherPhone"
            required
            placeholder="请输入电话号码"
        />
      </div>

      <div class="form-group">
        <label for="teacherCollege">所属学院 *</label>
        <input
            type="text"
            id="teacherCollege"
            v-model="formData.teacherCollege"
            required
            placeholder="请输入所属学院"
        />
      </div>

      <div class="form-group">
        <label for="teacherMajor">专业 *</label>
        <input
            type="text"
            id="teacherMajor"
            v-model="formData.teacherMajor"
            required
            placeholder="请输入专业"
        />
      </div>

      <div class="form-actions">
        <button type="submit" class="btn submit-btn">
          <i class="fas fa-save"></i>
          <span>更新</span>
        </button>
        <button type="button" class="btn cancel-btn" @click="handleCancel">
          <i class="fas fa-times"></i>
          <span>取消</span>
        </button>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import apiClient from '@/utils/axios';

interface FormData {
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

const route = useRoute();
const router = useRouter();
const formData = ref<FormData>({
  id: 0,
  teacherNum: '',
  teacherName: '',
  teacherSex: '',
  teacherPhone: '',
  teacherCollege: '',
  teacherMajor: '',
  createTime: '',
  updateTime: ''
});

const API_BASE_URL = 'https://localhost:443/teachers';

// 获取教师信息
const fetchTeacherInfo = async (id: string) => {
  try {
    const response = await apiClient.get(`${API_BASE_URL}/${id}`);
    if (response.data && response.status === 200) {
      const teacher = response.data;
      formData.value = {
        id: teacher.id,
        teacherNum: teacher.teacher_num,
        teacherName: teacher.teacher_name,
        teacherSex: teacher.teacher_sex,
        teacherPhone: teacher.teacher_phone,
        teacherCollege: teacher.teacher_college,
        teacherMajor: teacher.teacher_major,
        createTime: teacher.create_time,
        updateTime: teacher.update_time
      };
    } else {
      console.error('获取教师信息失败', response);
      alert('获取教师信息失败，请稍后重试');
    }
  } catch (error) {
    console.error('获取教师信息出错：', error);
    alert('获取数据失败，请联系管理员');
  }
};

onMounted(() => {
  const teacherId = route.params.id as string;
  if (teacherId) {
    fetchTeacherInfo(teacherId);
  }
});

const handleSubmit = async () => {
  // 构造要发送的数据
  const teacherInfoToUpdate = {
    id: formData.value.id,
    teacherNum: formData.value.teacherNum,
    teacherName: formData.value.teacherName,
    teacherSex: formData.value.teacherSex,
    teacherPhone: formData.value.teacherPhone,
    teacherCollege: formData.value.teacherCollege,
    teacherMajor: formData.value.teacherMajor,
    createTime: formData.value.createTime,
    updateTime: new Date().toISOString().slice(0, 19).replace('T', ' ')
  };

  try {
    const response = await apiClient.put(`${API_BASE_URL}/update`, teacherInfoToUpdate);
    if (response.status === 200) {
      alert('修改成功');
      await router.push('/teacher/list');
    } else {
      throw new Error('修改失败');
    }
  } catch (error) {
    console.error('修改教师信息出错：', error);
    alert(error.message || '修改失败，请稍后重试');
  }
};

const handleCancel = () => {
  router.push('/teacher/list');
};
</script>

<style scoped>
/* 使用与 TeacherAdd.vue 相同的样式 */
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

.teacher-form {
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
