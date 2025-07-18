<template>
  <div class="course-select-container">
    <h2>课程选择系统</h2>
    <el-table :data="CourseList" border>
      <el-table-column prop="courseId" label="课程编号"></el-table-column>
      <el-table-column prop="courseName" label="课程名称"></el-table-column>
      <el-table-column prop="capacity" label="容量"></el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button
              v-if="scope.row"
              type="primary"
              @click="handleSelect(scope.row.courseId)"
              :disabled="isCourseSelected(scope.row.courseId)">
            {{ isCourseSelected(scope.row.courseId) ? '已选课' : '立即选课' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div v-if="loading">加载课程中...</div>
  </div>
</template>

<script>
import {onMounted, reactive, ref} from 'vue';
import apiClient from '@/utils/axios.js';
import {useridentitystore} from '@/store/userStore';

export default {
  setup() {
    const CourseList = reactive([]);
    const selectedCourses = ref([]);
    const loading = ref(true);
    const userstore = useridentitystore();

    const fetchCourses = async () => {
      try {
        const response = await apiClient.get('https://localhost:443/course');
        // 双重保障：确保是数组且过滤无效数据
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
        }
      } catch (error) {
        console.error('获取课程列表失败:', error);
      } finally {
        loading.value = false;
      }
    };

    // 检查是否已选课
    const isCourseSelected = (courseId) => {
      return selectedCourses.value.includes(courseId);
    };

    // 处理选课操作
    const handleSelect = async (courseId) => {
      // const studentId = localStorage.getItem('studentId');
      const studentId = userstore.id

      try {
        const response = await apiClient.post(
            `https://localhost:443/course/${courseId}/select?studentId=${studentId}`
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
      CourseList,
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
