<template>
  <div>
    <p>正在删除学生信息，请稍候...</p>
  </div>
</template>

<script setup>
import {onMounted} from 'vue';
import apiClient from '@/utils/axios.js';
import {useRoute, useRouter} from 'vue-router';

const route = useRoute();
const router = useRouter();
const stuNum = route.params.stuNum;

const deleteStudentInfo = async () => {
  if (!stuNum) {
    alert('未找到要删除的学生学号');
    await router.push('/AdminList');
    return;
  }

  const API_BASE_URL = 'https://localhost:443/student';
  try {
    const response = await apiClient.delete(`${API_BASE_URL}/delete/${stuNum}`);

    if (response.status === 200) {
      alert('删除成功');
      await router.push('/AdminList');
    } else {
      throw new Error('删除失败');
    }
  } catch (error) {
    console.error('删除学生信息出错：', error);
    alert(error.message || '删除失败，请稍后重试');
    await router.push('/AdminList');
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