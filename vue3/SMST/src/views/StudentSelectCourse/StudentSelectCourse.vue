<template>
  <div class="course-select-container">
    <h2>课程选择系统</h2>
    <el-table :data="courseList" border>
      <el-table-column prop="id" label="课程编号"></el-table-column>
      <el-table-column prop="name" label="课程名称"></el-table-column>
      <el-table-column prop="capacity" label="容量"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              type="primary"
              @click="handleSelect(scope.row.id)"
              :disabled="isCourseSelected(scope.row.id)">
            {{ isCourseSelected(scope.row.id) ? '已选课' : '立即选课' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {ref, onMounted} from 'vue';
import axios from 'axios';

export default {
  setup() {
    const courseList = ref([]);
    const selectedCourses = ref([]);

    // 获取课程列表
    const fetchCourses = async () => {
      try {
        const response = await axios.get('https://localhost:443/courses');
        courseList.value = response.data.data;
      } catch (error) {
        console.error('获取课程列表失败:', error);
      }
    };

    // 检查是否已选课
    const isCourseSelected = (courseId) => {
      return selectedCourses.value.includes(courseId);
    };

    // 处理选课操作
    const handleSelect = async (courseId) => {
      const studentId = localStorage.getItem('studentId');

      try {
        const response = await axios.post(
            `https://localhost:443/courses/${courseId}/select?studentId=${studentId}`,
            {},
            {
              headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Refresh-Token': localStorage.getItem('refreshToken')
              }
            }
        );

        if (response.status === 200) {
          selectedCourses.value.push(courseId);
          ElMessage.success('选课成功');
        }
      } catch (error) {
        ElMessage.error(error.response?.data?.message || '选课失败');
      }
    };

    onMounted(() => {
      fetchCourses();
    });

    return {
      courseList,
      selectedCourses,
      isCourseSelected,
      handleSelect
    };
  }
};
</script>

<style scoped>
.course-select-container {
  padding: 20px;
}
</style>
