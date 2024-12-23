<template>
  <div class="form-container">
    <h2 class="page-title">修改学生信息</h2>
    <form class="student-form" @submit.prevent="handleSubmit">
      <!-- 只读信息区域 -->
      <div class="info-section">
        <h3 class="section-title">基本信息</h3>
        <div class="form-group" v-for="field in ['stuNum', 'stuName','stuSex','stuClass','stuMajor','stuCollege','stuTime','stuState']" :key="field">
          <label>{{ field === 'stuNum'? '学号' : field === 'stuName'? '姓名' : field === 'stuSex'? '性别' : field === 'stuClass'? '班级' : field === 'stuMajor'? '专业' : field === 'stuCollege'? '学院' : field === 'stuTime'? '入学时间' : '学生状态' }}</label>
          <input type="text" :readonly="true" :class="['readonly-input', { 'is-invalid': field === 'stuPhone' &&!isPhoneValid, 'is-invalid': field === 'stuHome' &&!isHomeValid }]" :value="formData[field]" />
        </div>
      </div>
      <!-- 可编辑信息区域 -->
      <div class="edit-section">
        <h3 class="section-title">可修改信息</h3>
        <div class="form-group">
          <label>电话</label>
          <input
            type="tel"
            v-model="formData.stuPhone"
            placeholder="请输入电话号码"
            required
            class="editable-input"
            pattern="[0-9]{11}"
            title="请输入11位手机号码"
          />
        </div>
        <div class="form-group">
          <label>家庭住址</label>
          <input
            type="text"
            v-model="formData.stuHome"
            placeholder="请输入家庭住址"
            required
            class="editable-input"
          />
        </div>
      </div>
      <div class="form-actions">
        <button type="submit" class="btn submit-btn">保存修改</button>
        <button type="button" class="btn cancel-btn" @click="handleCancel">取消</button>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const route = useRoute();
const API_BASE_URL = 'http://localhost:8080/student';

interface FormData {
  stuNum: number;
  stuName: string;
  stuSex: string;
  stuPhone: string;
  stuTime: string;
  stuClass: string;
  stuMajor: string;
  stuCollege: string;
  stuState: string;
  stuHome: string;
}

const formData = ref<FormData>({
  stuNum: 0,
  stuName: '',
  stuSex: '',
  stuPhone: '',
  stuTime: '',
  stuClass: '',
  stuMajor: '',
  stuCollege: '',
  stuState: '',
  stuHome: ''
});

const isPhoneValid = ref(true);
const isHomeValid = ref(true);

onMounted(() => {
  const queryParams = route.query;
  for (const key in formData.value) {
     formData.value[key] = queryParams[key as keyof typeof queryParams] as string || '';
    if (key ==='stuNum') {
      formData.value[key] = parseInt(queryParams[key as keyof typeof queryParams] as string) || 0;
    }
  }
});

const handleSubmit = async () => {
  isPhoneValid.value = /^[0-9]{11}$/.test(formData.value.stuPhone);
  isHomeValid.value = formData.value.stuHome.trim().length > 0;
  if (!isPhoneValid.value ||!isHomeValid.value) {
    return;
  }
  try {
    const updateData = {
      stu_num: formData.value.stuNum,
      stu_name: formData.value.stuName,
      stu_sex: formData.value.stuSex,
      stu_phone: formData.value.stuPhone,
      stu_time: formData.value.stuTime,
      stu_class: formData.value.stuClass,
      stu_major: formData.value.stuMajor,
      stu_college: formData.value.stuCollege,
      stu_state: formData.value.stuState,
      stu_home: formData.value.stuHome
    };
    console.log('发送的更新数据:', JSON.stringify(updateData, null, 2));
    const response = await axios.put(`${API_BASE_URL}/update`, updateData, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    console.log('服务器响应:', response);
    if (response.status === 200) {
      alert('更新成功！');
      router.push('/list');
    } else {
      alert('更新失败：服务器返回未知状态');
    }
  } catch (error: any) {
    console.error('更新失败:', error);
    console.error('请求数据:', error.config?.data);
    console.error('错误响应:', {
      status: error.response?.status,
      data: error.response?.data,
      headers: error.response?.headers
    });
    if (error.response?.status === 400) {
      alert('更新失败：请求数据格式不正确，请检查所有字段');
    } else if (error.response?.status === 500) {
      alert('更新失败：服务器内部错误');
    } else {
      alert(`更新失败：${error.message || '未知错误'}`);
    }
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
  text-align: center;
  justify-content: center;
}

.cancel-btn:hover {
  background-color: #e6e8eb;
  color: #262626;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* 添加新的样式 */
.info-section,
.edit-section {
  grid-column: span 2;
  margin-bottom: 24px;
}

.section-title {
  color: #1890ff;
  font-size: 18px;
  margin-bottom: 16px;
  padding-bottom: 8px;
  border-bottom: 2px solid #f0f0f0;
}

.readonly-input {
  background-color: #f5f5f5!important;
  color: #666;
  cursor: not-allowed;
  border: 1px solid #d9d9d9!important;
}

.editable-input {
  background-color: white;
  border: 1px solid #40a9ff!important;
}

.editable-input:focus {
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
}

.is-invalid {
  border-color: red!important;
}
</style>