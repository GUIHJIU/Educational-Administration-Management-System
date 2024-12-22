<template>
    <div class="course-form">
    <form @submit.prevent="searchCourses">
      <div>
        <label for="searchBy">Search by:</label>
        <select v-model="searchBy" id="searchBy">
          <option value="courseName">课程名</option>
          <option value="courseId">课程代码</option>
          <option value="credit">学分</option>
          <option value="classHour">学时</option>
        </select>
      </div>
      <div>
        <label for="searchValue">Search Value:</label>
        <input v-model="searchValue" type="text" id="searchValue" />
      </div>
      <button type="submit">Search</button>
    </form>
    <ul v-if="courseStore.courses.length">
      <li v-for="course in courseStore.courses" :key="course.courseId">
        {{ course.courseName }}--{{ course.courseId}}--{{ course.credit}}--{{ course.classHour}}
      </li>
    </ul>
    <p v-else>No courses found.</p>
  </div>
</template>
<script lang="ts" setup>
  import { onMounted,ref } from 'vue';
  import { useCourseStore } from '../store/courseStore';
  const courseStore = useCourseStore();
  const searchBy=ref('')
  const searchValue=ref('')
  function searchCourses(){
    courseStore.searchCourse(searchBy.value,searchValue.value)
  }
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