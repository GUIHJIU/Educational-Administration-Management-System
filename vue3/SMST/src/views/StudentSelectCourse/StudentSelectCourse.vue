<template>
  <h1></h1>
  <h1></h1>
  <div class="course-select-container">
    <h2>课程选择系统</h2>
    <div class="info-section">
      <el-alert
          v-if="selectedCourses.length > 0"
          :title="'已选课程数量: ' + selectedCourses.length"
          type="success"
          show-icon
          :closable="false">
      </el-alert>
    </div>
    <el-table :data="CourseList" border>
      <el-table-column prop="courseId" label="课程编号"></el-table-column>
      <el-table-column prop="courseName" label="课程名称"></el-table-column>
      <el-table-column prop="credit" label="学分"></el-table-column>
      <el-table-column prop="classHour" label="学时"></el-table-column>
      <el-table-column prop="teacherId" label="授课教师ID"></el-table-column>
      <el-table-column prop="capacity" label="容量"></el-table-column>
      <el-table-column prop="remaining" label="剩余名额"></el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button
              v-if="scope.row"
              type="primary"
              @click="handleSelect(scope.row.courseId)"
              :disabled="isCourseSelected(scope.row.courseId)">
            {{ isCourseSelected(scope.row.courseId) ? '已选课' : '立即选课' }}
          </el-button>
          <el-button
              v-if="isCourseSelected(scope.row.courseId)"
              type="danger"
              @click="handleDrop(scope.row.courseId)">
            退课
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
import {useUserStore} from '@/store/userStore';
import {ElMessage} from 'element-plus';

export default {
  setup() {
    const CourseList = reactive([]);
    const selectedCourses = ref([]);
    const loading = ref(true);
    const userstore = useUserStore();

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
        ElMessage.error('获取课程列表失败');
      } finally {
        loading.value = false;
      }
    };
    const fetchSelectedCourses = async () => {
      try {
        const studentId = userstore.id;
        const response = await apiClient.get(`https://localhost:443/course/student/${studentId}/courses`);
        if (response.data && response.data.code === 200 && Array.isArray(response.data.data)) {
          selectedCourses.value = response.data.data.map(course => course.courseId);

        }
        console.log('已选课程列表:', selectedCourses.value);
      } catch (error) {
        console.error('获取已选课程列表失败:', error);
        ElMessage.error('获取已选课程信息失败');
      }
    };

    // 检查是否已选课
    const isCourseSelected = (courseId) => {
      return selectedCourses.value.includes(courseId);
    };

    // 处理选课操作
    const handleSelect = async (courseId) => {
      const studentId = userstore.id;

      try {
        const response = await apiClient.post(
            `https://localhost:443/course/${courseId}/select?studentId=${studentId}`
        );

        if (response.data.code === 200) {
          selectedCourses.value.push(courseId);
          ElMessage.success('选课成功');

          // 更新课程列表中的余量信息
          const course = CourseList.find(c => c.courseId === courseId);
          if (course) {
            course.remaining -= 1;
          }
        } else {
          ElMessage.error(response.data.message || '选课失败');
        }
      } catch (error) {
        ElMessage.error(error.response?.data?.message || '选课失败');
      }
    };

    const handleDrop = async (courseId) => {
      const studentId = userstore.id;

      try {
        const response = await apiClient.delete(
            `https://localhost:443/course/${courseId}/drop?studentId=${studentId}`
        );

        if (response.data.code === 200) {
          const index = selectedCourses.value.indexOf(courseId);
          if (index > -1) {
            selectedCourses.value.splice(index, 1);
          }
          ElMessage.success('退课成功');

          // 更新课程列表中的余量信息
          const course = CourseList.find(c => c.courseId === courseId);
          if (course) {
            course.remaining += 1;
          }
        } else {
          ElMessage.error(response.data.message || '退课失败');
        }
      } catch (error) {
        ElMessage.error(error.response?.data?.message || '退课失败');
      }
    };

    onMounted(() => {
      fetchCourses();
      fetchSelectedCourses();
    });

    return {
      CourseList,
      selectedCourses,
      isCourseSelected,
      handleSelect,
      handleDrop,
      loading
    };
  }
};
</script>

<style scoped>
h1 {
  background-color: #0d5db9;
  color: #d9d9d9;
  padding: 20px;
  text-align: center;
  border-radius: 10px;
}
.course-select-container {
  padding: 20px;
}

.info-section {
  margin-bottom: 20px;
}
</style>
