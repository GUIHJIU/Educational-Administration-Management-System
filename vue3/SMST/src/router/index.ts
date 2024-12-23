import { createRouter, createWebHistory } from 'vue-router'
import Course from "@/views/Course.vue";
import CourseNav from '@/views/CourseNav.vue'
import AddCourse from '@/views/AddCourse.vue'
import AllCourse from '@/views/AllCourse.vue'
import UpdateCourse from '@/views/UpdateCourse.vue'
import DeleteCourse from '@/views/DeleteCourse.vue'
import SelectCourse from '@/views/SelectCourse.vue'
import TeacherListPage from '../views/Stuadmin/List.vue';
import AddPage from '../views/Stuadmin/add.vue';
import DeletePage from '../views/Stuadmin/delete.vue';
import UpdatePage from '../views/Stuadmin/update.vue';
import StudentUpdatePage from '../views/StuStudent/update.vue';
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
      {
          path: '/',
          redirect: '/list'
      },
      {
          path: '/list',
          name: 'list',
          component: TeacherListPage,
      },
      {
          path: '/add',
          name: 'add',
          component: AddPage,
      },
      {
          path: '/delete',
          name: 'delete',
          component: DeletePage,
      },
      {
          path: '/update',
          name: 'update',
          component: UpdatePage,
      },
      {
          path:'/coursenav',
          component:Course
      },
      {
      path:'/course',
      component:CourseNav,
      children:[
        {
          path:'addcourse',
          component:AddCourse
        },
        {
          path:'allcourse',
          component:AllCourse
        },
        {
          path:'updatecourse',
          component:UpdateCourse
        },
        {
          path:'deletecourse',
          component:DeleteCourse
        },
        {
          path:'selectcourse',
          component:SelectCourse
        }
      ]
    }
  ],
})
export default router

