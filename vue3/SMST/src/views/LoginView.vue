<template>
  <div class="login-container">
    <div class="background-overlay"></div>
    
    <div class="login-form">
      <h2>学生信息管理系统</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">用户名:</label>
          <input type="text" v-model="username" id="username" required placeholder="请输入用户名" />
        </div>
        <div class="form-group">
          <label for="password">密码:</label>
          <input type="password" v-model="password" id="password" required placeholder="请输入密码" />
        </div>
        <button type="submit">登录</button>
      </form>
    </div>
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
/* 重置基础样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 确保html和body占满整个视口 */
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

.login-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url('https://ehall.lzjtu.edu.cn/CUS_TEMPLATE_LZJTUS/pc/img/chun.ecb0cff8.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  overflow: hidden;
}

.left-image {
  width: 45%;
  height: 100%;
  background-image: url('https://ehall.lzjtu.edu.cn/CUS_TEMPLATE_LZJTUS/pc/img/chun.ecb0cff8.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  box-shadow: 5px 0 15px rgba(0, 0, 0, 0.2);
  position: relative;
  z-index: 2;
}

.right-content {
  width: 55%;
  height: 100%;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, rgba(255, 255, 255, 0.1), rgba(255, 255, 255, 0));
  backdrop-filter: blur(10px);
}

.login-form {
  width: 400px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 15px;
  box-shadow: 
    0 10px 30px rgba(0, 0, 0, 0.2),
    0 0 20px rgba(0, 0, 0, 0.1),
    inset 0 0 15px rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  position: relative;
  z-index: 2;
  transform: translateZ(0);
}

h2 {
  text-align: center;
  color: #333;
  margin-bottom: 30px;
  font-size: 28px;
  font-weight: 600;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 25px;
}

label {
  display: block;
  margin-bottom: 8px;
  color: #333;
  font-weight: 500;
}

input {
  width: 100%;
  padding: 14px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  font-size: 15px;
  transition: all 0.3s ease;
  background: rgba(255, 255, 255, 0.9);
  box-shadow: 
    inset 0 2px 4px rgba(0, 0, 0, 0.05),
    0 2px 4px rgba(255, 255, 255, 0.1);
}

input:focus {
  outline: none;
  border-color: #4CAF50;
  box-shadow: 
    0 0 0 3px rgba(76, 175, 80, 0.2),
    inset 0 2px 4px rgba(0, 0, 0, 0.05);
  transform: translateY(-1px);
}

button {
  width: 100%;
  padding: 14px;
  background: linear-gradient(145deg, #4CAF50, #45a049);
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 1px;
  transition: all 0.3s ease;
  box-shadow: 
    0 4px 15px rgba(76, 175, 80, 0.3),
    0 2px 5px rgba(0, 0, 0, 0.1);
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
}

button:hover {
  background: linear-gradient(145deg, #45a049, #3d8b40);
  transform: translateY(-2px);
  box-shadow: 
    0 6px 20px rgba(76, 175, 80, 0.4),
    0 4px 8px rgba(0, 0, 0, 0.1);
}

button:active {
  transform: translateY(0);
  box-shadow: 
    0 2px 10px rgba(76, 175, 80, 0.3),
    0 1px 3px rgba(0, 0, 0, 0.1);
}

/* 响应式设计 */
@media (max-width: 480px) {
  .login-form {
    width: 90%;
    margin: 0 20px;
    padding: 30px 20px;
  }
}

/* 防止iOS safari的橡皮筋效果 */
@supports (-webkit-touch-callout: none) {
  .login-container {
    position: fixed;
  }
}
</style>