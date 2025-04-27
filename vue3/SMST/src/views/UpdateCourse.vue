<template>
    <div>
      <h1 style="text-align: center">修改课程信息</h1>
      <div style="text-align: center; margin-bottom: 20px;">
        <input
            type="text"
            v-model="searchQuery"
            placeholder="搜索课程名称、教师或课程类型"

        />
      </div>
      <div v-if="courseStore.selectedCourse">
        <h2>选中的课程: {{ courseStore.selectedCourse.courseName }}</h2>
        <form @submit.prevent="handleSubmit">
          <div>
            <label for="name">课程名称:</label>
            <input type="text" v-model="courseStore.selectedCourse.courseName" id="name" required />
            <label for="credit">课程学分:</label>
            <input type="text" v-model="courseStore.selectedCourse.credit" id="credit" required />
            <label for="classhour">课程学时:</label>
            <input type="text" v-model="courseStore.selectedCourse.classHour" id="classhour" required/>
            <label for="courseType">课程类型:</label>
            <input type="text" v-model="courseStore.selectedCourse.courseType" id="courseType" required/>
            <label for="Teacher">课程教师:</label>
            <input type="text" v-model="courseStore.selectedCourse.Teacher" id="Teacher" required/>
          </div>
          <!-- 其他课程信息的表单字段 -->
          <button type="submit">保存修改</button>
        </form>
      </div>
      <ul>
        <li v-for="course in filteredCourses" :key="course.courseId">
          {{ course.courseId }} - {{ course.courseName }} - {{ course.credit }} - {{ course.classHour }} -
          {{ course.courseType }} - {{ course.Teacher }}
          <button @click="courseStore.selectCourse(course.courseId)">修改</button>
        </li>
      </ul>

    </div>
  </template>

  <script lang="ts" setup>
  import { onMounted ,ref,computed} from 'vue';
  import { useCourseStore } from '../store/courseStore';
  import router from "@/router";
  const searchQuery = ref('');
  const courseStore = useCourseStore();

  onMounted(() => {
    courseStore.fetchCourse();

  });
  const filteredCourses = computed(() => {
    const query = searchQuery.value;
    return courseStore.courses.filter(course => {
      return (
          course.Teacher.includes(query) ||
          course.courseName.includes(query) ||
          course.courseType.includes(query)
      );
    });
  });
  const handleSubmit = () => {
    courseStore.updateCourse();
    alert("修改成功")
    searchQuery.value = '';
    courseStore.selectedCourse=null
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