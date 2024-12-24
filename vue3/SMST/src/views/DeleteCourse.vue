<template>
        <div>
      <h1 style="text-align: center">删除课程信息</h1>
          <div style="text-align: center; margin-bottom: 20px;">
            <input
                type="text"
                v-model="searchQuery"
                placeholder="搜索课程名称、教师或课程类型"

            />
          </div>
      <ul>
        <li v-for="course in filteredCourses" :key="course.courseId">
          {{ course.courseId }} - {{ course.courseName }} - {{ course.credit }} - {{ course.classHour }} - {{ course.courseType }} - {{ course.Teacher }}
          <button @click="courseStore.selectCourse(course.courseId)">删除</button>
        </li>
      </ul>
      <div v-if="courseStore.selectedCourse">
        <h2>选中的课程: {{ courseStore.selectedCourse.courseName }}</h2>
        <button @click="deletecourse">确认删除</button>
      </div>
    </div>
</template>
<script lang="ts" setup>
import {onMounted, ref ,computed} from 'vue';
  import { useCourseStore } from '../store/courseStore';
import router from "@/router";
const searchQuery = ref('');
const courseStore = useCourseStore();

  onMounted(() => {
    courseStore.fetchCourse();
  });
const filteredCourses = computed(() => {
  const query = searchQuery.value.toLowerCase();
  return courseStore.courses.filter(course => {
    return (
        course.Teacher.toLowerCase().includes(query) ||
        course.courseName.toLowerCase().includes(query) || // 注意这里也应该添加 toLowerCase() 以保持一致性
        course.courseType.toLowerCase().includes(query)
    );
  });
});
function deletecourse() {
  courseStore.deleteCourse(courseStore.selectedCourse.courseId);
  alert("删除成功");
  searchQuery.value = '';
  courseStore.selectedCourse=null
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