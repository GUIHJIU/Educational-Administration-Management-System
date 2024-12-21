<template>
    <div class="course-form">
      <h1>添加课程信息</h1>
      <form @submit.prevent="addCourse">
        <div>
          <label for="courseName">课程名称:</label>
          <input type="text" v-model="course.courseName" id="courseName" required />
        </div>
        <div>
          <label for="courseHour">课程学时:</label>
          <input type="text" v-model.number="course.classHour" id="courseHour" required />
        </div>
        <div>
          <label for="courseCredits">课程学分:</label>
          <input type="number" v-model.number="course.credit" id="courseCredits" required />
        </div>
        <button type="submit">添加课程</button>
      </form>
    </div>
  </template>
   
  <script lang="ts" setup>
  import axios from 'axios';
  import {ref} from 'vue'
  let course=ref({
     courseName:'',
    classHour:'',
    credit:''
  })
    const addCourse=async()=> {
      if (course.value.courseName && course.value.classHour && course.value.credit) {
        try {
          const response = await axios.post('http://localhost:8080/course/addcourses',course.value);
          course.value.courseName=''
          course.value.classHour=''
          course.value.credit=''
          // 显示成功消息（可选）
          console.log(response.data);
          alert('课程已成功添加！');
        } catch (error) {
          // 处理错误
          console.error('添加课程时出错:', error);
          alert('添加课程失败，请稍后再试。');
        }
      } else {
        alert('请填写所有必填字段');
      }
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