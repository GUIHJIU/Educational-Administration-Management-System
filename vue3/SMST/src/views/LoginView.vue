<template>
    <div class="login-form">
        <h2>Login</h2>
        <form @submit.prevent="handleLogin">
            <div>
                <label for="username">Username:</label>
                <input type="text" v-model="username" id="username" required />
            </div>
            <div>
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
    const username=ref('');
    const password=ref('');
    const handleLogin = async () => {
  try {
    // 发送 POST 请求到 Spring 后端
    const response = await axios.post('http://localhost:8080/login/login', {
      username: username.value,
      password: password.value,
    });
    
    // 根据后端响应处理逻辑
    console.log('登录成功:', response.data);
    alert("登录成功");
    // 这里可以添加跳转到其他页面、显示成功消息等逻辑
  } catch (error) {
    // 处理请求错误
    console.error('登录失败:', error);
    // 这里可以显示错误消息等
  }
};

</script>