<template>
    <div>
      <h1>修改课程信息</h1>
      <ul>
        <li v-for="course in courseStore.courses" :key="course.courseId">
            {{ course.courseId }} - {{ course.courseName }} - {{ course.credit }} - {{ course.classHour }}
          <button @click="courseStore.selectCourse(course.courseId)">修改</button>
        </li>
      </ul>
      <div v-if="courseStore.selectedCourse">
        <h2>选中的课程: {{ courseStore.selectedCourse.courseName }}</h2>
        <form @submit.prevent="handleSubmit">
          <div>
            <label for="name">课程名称:</label>
            <input type="text" v-model="courseStore.selectedCourse.courseName" id="name" required />
            <label for="credit">课程学分:</label>
            <input type="text" v-model="courseStore.selectedCourse.credit" id="credit" required />
            <label for="classhour">课程学时:</label>
            <input type="text" v-model="courseStore.selectedCourse.classHour" id="classhour" required />
          </div>
          <!-- 其他课程信息的表单字段 -->
          <button type="submit">保存修改</button>
        </form>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { onMounted } from 'vue';
  import { useCourseStore } from '../store/courseStore';
  
  const courseStore = useCourseStore();
  
  onMounted(() => {
    courseStore.fetchCourse();
  });
  
  const handleSubmit = () => {
    courseStore.updateCourse();
  };
  </script>
  <style scoped>
      .course-form {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
   
  form div {
    margin-bottom: 15px;
  }
   
  label {
    display: block;
    margin-bottom: 5px;
  }
   
  input {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
  }
   
  button {
    padding: 10px 15px;
    background-color: #42b983;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
   
  button:hover {
    background-color: #36976f;
  }
   
  ul {
    list-style-type: none;
    padding: 0;
  }
   
  li {
    background: #f9f9f9;
    margin: 10px 0;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
  }
</style>