<template>
  <div class="update-form">
    <h2>修改学生信息</h2>
    <div class="form-container">
      <!-- 只读信息 -->
      <div class="form-item">
        <label>学号:</label>
        <input type="text" v-model="formData.stuNum" readonly class="readonly" />
      </div>
      <div class="form-item">
        <label>姓名:</label>
        <input type="text" v-model="formData.stuName" readonly class="readonly" />
      </div>
      <div class="form-item">
        <label>性别:</label>
        <input type="text" v-model="formData.stuSex" readonly class="readonly" />
      </div>
      <div class="form-item">
        <label>班级:</label>
        <input type="text" v-model="formData.stuClass" readonly class="readonly" />
      </div>
      <div class="form-item">
        <label>专业:</label>
        <input type="text" v-model="formData.stuMajor" readonly class="readonly" />
      </div>
      <div class="form-item">
        <label>学院:</label>
        <input type="text" v-model="formData.stuCollege" readonly class="readonly" />
      </div>
      <div class="form-item">
        <label>状态:</label>
        <input type="text" v-model="formData.stuState" readonly class="readonly" />
      </div>
      <div class="form-item">
        <label>入学时间:</label>
        <input type="text" v-model="formData.stuTime" readonly class="readonly" />
      </div>

      <!-- 可编辑信息 -->
      <div class="form-item">
        <label>电话:</label>
        <input type="text" v-model="formData.stuPhone" />
      </div>
      <div class="form-item">
        <label>家庭住址:</label>
        <input type="text" v-model="formData.stuHome" />
      </div>

      <div class="form-actions">
        <button @click="handleSubmit" class="submit-btn">保存</button>
        <button @click="handleCancel" class="cancel-btn">取消</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const router = useRouter()

interface FormData {
  stuNum: number
  stuName: string
  stuSex: string
  stuClass: string
  stuMajor: string
  stuCollege: string
  stuState: string
  stuTime: string
  stuPhone: string
  stuHome: string
}

const formData = ref<FormData>({
  stuNum: 0,
  stuName: '',
  stuSex: '',
  stuClass: '',
  stuMajor: '',
  stuCollege: '',
  stuState: '',
  stuTime: '',
  stuPhone: '',
  stuHome: ''
})

onMounted(() => {
  const queryParams = route.query
  formData.value.stuNum = parseInt(queryParams.stuNum as string) || 0
  formData.value.stuName = queryParams.stuName as string || ''
  formData.value.stuSex = queryParams.stuSex as string || ''
  formData.value.stuPhone = queryParams.stuPhone as string || ''
  formData.value.stuTime = queryParams.stuTime as string || ''
  formData.value.stuClass = queryParams.stuClass as string || ''
  formData.value.stuMajor = queryParams.stuMajor as string || ''
  formData.value.stuCollege = queryParams.stuCollege as string || ''
  formData.value.stuState = queryParams.stuState as string || ''
  formData.value.stuHome = queryParams.stuHome as string || ''
})

const API_BASE_URL = 'http://localhost:8080/student'

const handleSubmit = async () => {
  try {
    const updateData = {
      stuNum: formData.value.stuNum,
      stuPhone: formData.value.stuPhone,
      stuHome: formData.value.stuHome
    };

    const url = `${API_BASE_URL}/update`;
    console.log('发送更新请求到:', url);
    console.log('更新数据:', updateData);

    const response = await axios.put(url, updateData);
    console.log('服务器响应:', response);

    if (response.status === 200) {
      alert('更新成功');
      router.push('/list');
    }
  } catch (error: any) {
    console.error('完整错误信息:', error);
    console.error('错误状态码:', error.response?.status);
    console.error('错误数据:', error.response?.data);

    // 根据后端返回的状态码显示不同的错误信息
    switch (error.response?.status) {
      case 404:
        alert('未找到该学生信息');
        break;
      case 500:
        alert('系统错误，请稍后重试');
        break;
      default:
        alert('更新失败，请检查网络连接后重试');
    }
  }
};

const handleCancel = () => {
  router.push('/list')
}
</script>

<style scoped>
.update-form {
  max-width: 800px;
  margin: 40px auto;
  padding: 20px;
}

.form-container {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  padding: 24px;
}

h2 {
  text-align: center;
  margin-bottom: 24px;
  color: #333;
}

.form-item {
  display: flex;
  margin-bottom: 16px;
  align-items: center;
}

.form-item label {
  width: 100px;
  color: #666;
}

.form-item input {
  flex: 1;
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.form-item input.readonly {
  background-color: #f5f5f5;
  cursor: not-allowed;
}

.form-actions {
  margin-top: 24px;
  display: flex;
  justify-content: center;
  gap: 16px;
}

.submit-btn, .cancel-btn {
  padding: 8px 32px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.submit-btn {
  background: #1890ff;
  color: white;
}

.submit-btn:hover {
  background: #40a9ff;
}

.cancel-btn {
  background: #f5f5f5;
  color: #666;
}

.cancel-btn:hover {
  background: #e8e8e8;
}
</style>