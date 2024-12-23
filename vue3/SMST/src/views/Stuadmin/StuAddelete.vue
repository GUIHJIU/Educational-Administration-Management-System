<template>
  <div>
    <p>正在删除学生信息，请稍候...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const stuNum = route.params.stuNum;

const deleteStudentInfo = async () => {
  if (!stuNum) {
    alert('未找到要删除的学生学号');
    router.push('/list');
    return;
  }

  const API_BASE_URL = 'http://localhost:8080/student';
  try {
    const response = await axios.delete(`${API_BASE_URL}/delete/${stuNum}`);

    if (response.status === 200) {
      alert('删除成功');
      router.push('/list');
    } else {
      throw new Error('删除失败');
    }
  } catch (error) {
    console.error('删除学生信息出错：', error);
    alert(error.message || '删除失败，请稍后重试');
    router.push('/list');
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