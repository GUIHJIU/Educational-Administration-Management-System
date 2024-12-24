<template>
    <div>
        <h1>课程列表</h1>
        <ul>
            
            <li v-for="item in CourseList" :key="item.courseId">
                {{ item.courseId }} - {{ item.courseName }} - {{ item.credit }} - {{ item.classHour }}
            </li>
        </ul>
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
            classHour: course.classHour
        })));
    } catch (error) {
        console.error('Failed to fetch Course:', error);
    }
});
</script>

<style scoped>
ul {
    list-style-type: none;
    padding: 0;
}

li {
    background: #f9f9f9;
    margin: 0.5em 0;
    padding: 0.5em;
    border: 1px solid #ddd;
}
</style>