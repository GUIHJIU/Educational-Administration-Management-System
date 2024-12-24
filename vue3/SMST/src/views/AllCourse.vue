<template>
  <div>
    <h1>课程列表</h1>
    <table>
      <thead>
      <tr>
        <th>课程ID</th>
        <th>课程名称</th>
        <th>学分</th>
        <th>课时</th>
        <th>课程类型</th>
        <th>教师</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="item in CourseList" :key="item.courseId">
        <td>{{ item.courseId }}</td>
        <td>{{ item.courseName }}</td>
        <td>{{ item.credit }}</td>
        <td>{{ item.classHour }}</td>
        <td>{{ item.courseType }}</td>
        <td>{{ item.Teacher }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts" setup>
import axios from 'axios';
import { ref, reactive, onMounted } from 'vue';

const CourseList = reactive([]); // 初始化为空数组

onMounted(async () => {
    try {
        const response = await axios.get('http://localhost:8080/course/course');
        // 正确使用map来转换数组
        CourseList.push(...response.data.map(course => ({
            courseId: course.courseId,
            courseName: course.courseName,
            credit: course.credit,
            classHour: course.classHour,
            courseType: course.courseType,
            Teacher: course.Teacher
        })));
    } catch (error) {
        console.error('Failed to fetch Course:', error);
    }
});
</script>

<style scoped>
h1 {
  text-align: center; /* 将h1元素内的文本居中 */
}
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
  text-align: left;
}

tr:nth-child(even) {
  background-color: #f9f9f9;
}
</style>