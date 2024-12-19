<template>
  <div>
    <p>正在删除学生信息，请稍候...</p>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const stuNum = route.params.stuNum; // 获取要删除的学生学号，注意这里从路由参数获取

const deleteStudentInfo = async () => {
  // 全局配置后端接口基础地址，方便统一管理和修改
  const API_BASE_URL = 'http://localhost:8080/student';
  try {
    const response = await axios.delete(`${API_BASE_URL}/delete/${stuNum}`);
    if (response.status === 200 ) 
    {
      // 删除成功后，返回上一页（假设上一页是列表页，可根据实际情况调整路由跳转逻辑）
      router.go(-1);
    } else 
    {
      console.error('删除学生信息失败，后端返回信息：', response.data);
      alert('删除学生信息失败，请稍后重试');
    }
  } catch (error) 
  {
    console.error('删除学生信息出错：', error);
    alert('网络异常，删除学生信息失败，请检查网络或联系管理员');
  }
};

onMounted(() => {
  deleteStudentInfo();
});
</script>

<style scoped>
div {
  text-align: center;
  margin-top: 50px;
}
</style>