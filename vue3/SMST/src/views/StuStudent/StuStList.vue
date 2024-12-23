<template>
  <div class="list-container">
    <div class="page-header">
      <h1 class="page-title">学生信息查询</h1>
    </div>

    <div class="search-container">
      <div class="search-box">
        <input
            type="text"
            v-model="stuNum"
            placeholder="请输入学号"
            @keyup.enter="searchStudent"
        />
        <button class="search-btn" @click="searchStudent">查询</button>
      </div>
    </div>

    <!-- 学生信息显示区域 -->
    <div v-if="studentData" class="info-container">
      <h2>学生信息</h2>
      <div class="info-grid">
        <div class="info-item">
          <label>学号:</label>
          <span class="info-value">{{ studentData.stuNum }}</span>
        </div>
        <div class="info-item">
          <label>姓名:</label>
          <span class="info-value">{{ studentData.stuName }}</span>
        </div>
        <div class="info-item">
          <label>性别:</label>
          <span class="info-value">{{ studentData.stuSex }}</span>
        </div>
        <div class="info-item">
          <label>班级:</label>
          <span class="info-value">{{ studentData.stuClass }}</span>
        </div>
        <div class="info-item">
          <label>专业:</label>
          <span class="info-value">{{ studentData.stuMajor }}</span>
        </div>
        <div class="info-item">
          <label>学院:</label>
          <span class="info-value">{{ studentData.stuCollege }}</span>
        </div>
        <div class="info-item">
          <label>状态:</label>
          <span class="info-value">{{ studentData.stuState }}</span>
        </div>
        <div class="info-item">
          <label>入学时间:</label>
          <span class="info-value">{{ studentData.stuTime }}</span>
        </div>
        <div class="info-item">
          <label>电话:</label>
          <span class="info-value">{{ studentData.stuPhone }}</span>
        </div>
        <div class="info-item">
          <label>家庭住址:</label>
          <span class="info-value">{{ studentData.stuHome }}</span>
        </div>
      </div>

      <div class="actions">
        <button class="update-btn" @click="goToUpdate">修改信息</button>
      </div>
    </div>

    <div v-else-if="searched" class="no-result">
      未找到该学号的学生信息
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();

// 定义学生信息接口类型，明确各属性的类型
interface StudentInfo {
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

// 用于将下划线命名属性转换为驼峰命名的函数
function convertToCamelCase(obj: any): any {
  if (typeof obj!== 'object' || obj === null) {
    return obj;
  }
  if (Array.isArray(obj)) {
    return obj.map((item) => convertToCamelCase(item));
  }
  const result: any = {};
  for (const key in obj) {
    const newKey = key.replace(/_([a-z])/g, (_, letter) => letter.toUpperCase());
    result[newKey] = convertToCamelCase(obj[key]);
  }
  return result;
}

// 用于绑定输入框中学号值的响应式数据
const stuNum = ref('');
// 存储后端返回的学生信息的响应式数据，初始值为null
const studentData = ref<StudentInfo | null>(null);
// 标记是否已经进行了查询操作，初始值为false
const searched = ref(false);
// 后端接口的基础URL，假设本地服务端运行在8080端口且学生信息相关接口前缀为/student
const API_BASE_URL = 'http://localhost:8080/student';

// 发起查询学生信息的函数
const searchStudent = async () => {
  // 去除输入学号前后的空白字符
  const inputStuNum = stuNum.value.trim();
  if (inputStuNum === '')
  {
    // 如果学号为空，给出更友好的提示
    alert('请输入学号后再进行查询操作哦~');
    return;
  }

  let numericStuNum: number;
  try {
    numericStuNum = parseInt(inputStuNum);
    if (isNaN(numericStuNum)) {
      // 如果转换学号为数字失败，提示输入有效的数字学号
      alert('请输入有效的数字学号呀');
      return;
    }
  } catch (error) {
    console.error('转换学号为数字时出错:', error);
    alert('学号格式出现问题，请检查后重新输入');
    return;
  }

  const url = `${API_BASE_URL}/search/${numericStuNum}`;
  console.log('发送请求到:', url);

  try {
    const response = await axios.get(url);
    console.log('服务器响应:', response);

    if (response.status === 200 && response.data) {
      const responseData = convertToCamelCase(response.data);
      // 对学号等关键属性进行类型校验和转换
      let convertedStuNum: number;
      try {
        convertedStuNum = parseInt(responseData.stuNum);
        responseData.stuNum = convertedStuNum;
      } catch (error)
      {
        console.error('学号类型转换错误:', error);
        // 可以根据情况进行相应处理，比如提示用户数据异常等
      }
      studentData.value = responseData;
      searched.value = true;
      console.log('获取到的学生数据:', studentData.value);
    } else
    {
      studentData.value = null;
      searched.value = true;
      // 更明确的提示未找到对应学号学生的信息
      alert('很抱歉，未找到该学号对应的学生信息哦，请确认学号是否正确');
    }
  } catch (error: any) {
    console.error('查询失败:', error);
    studentData.value = null;
    searched.value = true;
    if (error.response?.status === 404) {
      alert('未找到该学号对应的学生信息呢，你可以检查下学号是否输错啦');
    } else {
      alert('查询出现意外错误，请稍后重试或联系管理员哦');
    }
  }
};

// 跳转到修改信息页面的函数
const goToUpdate = () => {
  if (!studentData.value || typeof studentData.value.stuNum === 'undefined') {
    alert('请先查询到学生信息后，再点击修改信息按钮哦');
    return;
  }

  try {
    // 使用 String() 函数安全地转换数字，并处理各属性可能为空的情况
    const queryParams: Record<string, string> = {
      stuNum: String(studentData.value.stuNum),
      stuName: String(studentData.value.stuName || ''),
      stuSex: String(studentData.value.stuSex || ''),
      stuPhone: String(studentData.value.stuPhone || ''),
      stuTime: String(studentData.value.stuTime || ''),
      stuClass: String(studentData.value.stuClass || ''),
      stuMajor: String(studentData.value.stuMajor || ''),
      stuCollege: String(studentData.value.stuCollege || ''),
      stuState: String(studentData.value.stuState || ''),
      stuHome: String(studentData.value.stuHome || '')
    };

    console.log('跳转参数:', queryParams);

    // 使用正确的路由路径，跳转到修改信息页面并传递参数
    router.push({
      path: '/update',
      query: queryParams
    });
  } catch (error) {
    console.error('导航错误:', error);
    alert('哎呀，页面跳转出现问题了，请重试一下哦');
  }
};
</script>

<style scoped>
.list-container {
  padding: 24px;
  background-color: #f8fafc;
  min-height: 100vh;
  font-family: "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.page-header {
  background: #1277d6;
  padding: 24px;
  margin: -24px -24px 24px -24px;
  text-align: center;
}

.page-title {
  color: white;
  font-size: 24px;
  margin: 0;
  font-weight: bold;
}

.search-container {
  max-width: 600px;
  margin: 32px auto;
  background: white;
  padding: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.search-box {
  display: flex;
  gap: 12px;
}

.search-box input {
  flex: 1;
  padding: 8px 16px;
  border: 1px solid #e2e8f0;
  border-radius: 6px;
  font-size: 16px;
}

.search-btn {
  padding: 8px 24px;
  background: #1277d6;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 16px;
  font-weight: bold;
}

.info-container {
  max-width: 800px;
  margin: 32px auto;
  background: white;
  padding: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.info-container h2 {
  text-align: center;
  margin-bottom: 24px;
  color: #1277d6;
  font-size: 20px;
  font-weight: bold;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  padding: 0 20px;
}

.info-item {
  display: flex;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px solid #f0f0f0;
}

.info-item label {
  width: 90px;
  color: #666;
  font-weight: bold;
  font-size: 15px;
}

.info-value {
  flex: 1;
  color: #000;
  font-weight: bold;
  font-size: 15px;
  margin-left: 10px;
}

.actions {
  margin-top: 32px;
  text-align: center;
}

.update-btn {
  padding: 10px 40px;
  background: #1277d6;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 16px;
  font-weight: bold;
  transition: background-color 0.3s;
}

.update-btn:hover {
  background: #0d5db9;
}

.no-result {
  text-align: center;
  color: #666;
  padding: 40px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  margin: 32px auto;
  font-size: 16px;
  font-weight: bold;
}
</style>