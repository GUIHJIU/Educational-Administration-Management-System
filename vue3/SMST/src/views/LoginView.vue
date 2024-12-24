<template>
  <div class="login-form">
    <h2>Login</h2>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" v-model="username" id="username" required />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" v-model="password" id="password" required />
      </div>
      <button type="submit">Login</button>
      <!-- <p v-if="errorMessage" class="error">{{ errorMessage }}</p> -->
    </form>
  </div>
</template>
<script lang="ts" setup>

import axios from 'axios';
import { ref,  reactive } from 'vue';
import { useRouter } from 'vue-router';
import {useridentitystore} from '@/store/userStore'
const userstore=useridentitystore()
const username=ref('');
const password=ref('');
const router = useRouter();
const user=reactive({
  username:'',
  position:''
})
const handleLogin = async () => {
  try {
    // 发送 POST 请求到 Spring 后端
    const response = await axios.post<{ username: string, position: string }>('http://localhost:8080/login/login', {
      username: username.value,
      password: password.value,
    });

    if (response.status === 200) {
      userstore.identity = response.data.position;
      // 保存用户名（学号）到 store 和 localStorage
      userstore.account = username.value; // 使用输入的用户名作为学号
      localStorage.setItem('userAccount', username.value);

      console.log('登录成功:', response);
      console.log('保存的账号:', username.value);

      alert("登录成功");
      router.push('/home');
    }
  } catch (error) {
    console.error('登录失败:', error);
    alert('登录失败，请检查用户名和密码');
  }
};

</script>

<style scoped>
.login-form {
  position: absolute; /* 设置绝对定位 */
  top: 50%; /* 垂直方向上移50% */
  left: 50%; /* 水平方向上移50% */
  transform: translate(-50%, -50%); /* 向左和向上移动自身宽度的50%，实现居中 */
  width: 100%; /* 宽度设置为100% */
  max-width: 400px; /* 设置最大宽度，防止在大屏幕上过宽 */
  height: auto; /* 高度自适应内容 */
  margin: 0; /* 移除外边距 */
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

h2 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  color: #555;
}

input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #45a049;
}

/* 错误消息样式 */
.error {
  color: red;
  text-align: center;
  margin-top: 10px;
}
</style>