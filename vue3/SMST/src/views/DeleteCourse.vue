<template>
        <div>
      <h1>删除课程信息</h1>
      <ul>
        <li v-for="course in courseStore.courses" :key="course.courseId">
            {{ course.courseId }} - {{ course.courseName }} - {{ course.credit }} - {{ course.classHour }}
          <button @click="courseStore.selectCourse(course.courseId)">删除</button>
        </li>
      </ul>
      <div v-if="courseStore.selectedCourse">
        <h2>选中的课程: {{ courseStore.selectedCourse.courseName }}</h2>
        <button @click="courseStore.deleteCourse(courseStore.selectedCourse.courseId)">确认删除</button>
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