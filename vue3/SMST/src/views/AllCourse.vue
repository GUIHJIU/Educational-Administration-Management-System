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
        <td>{{ item.Exam }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts" setup>
import apiClient from '@/utils/axios';
import {onMounted, reactive} from 'vue';

const CourseList = reactive([]); // 初始化为空数组

onMounted(async () => {
  try {
    const response = await apiClient.get('https://localhost:443/course', {
      params: {
        page: 1,
        size: 100 // 获取所有课程
      }
    });

    console.log('完整课程响应:', response);

    // 确认响应结构
    if (response.data?.data?.list && Array.isArray(response.data.data.list)) {
      const courses = response.data.data.list;

      // 清空现有列表
      CourseList.splice(0, CourseList.length);

      // 添加新课程（使用正确的字段名）
      courses.forEach(course => {
        CourseList.push({
          courseId: course.courseId,

          courseName: course.courseName,
          credit: course.credit,
          classHour: course.classHour,
          courseType: course.courseType,
          // 使用正确的教师字段名
          teacherId: course.teacherId,
          // 可选：添加其他需要的字段
          capacity: course.capacity,
          remaining: course.remaining
        });
      });

      console.log(`成功加载 ${courses.length} 门课程`);
    } else {
      console.error('无效的响应结构:', response.data);
    }
  } catch (error) {
    console.error('获取课程失败:', {
      message: error.message,
      response: error.response
    });
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