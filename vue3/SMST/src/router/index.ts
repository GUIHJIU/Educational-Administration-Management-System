import { createRouter, createWebHistory } from 'vue-router'
import CourseNav from '@/views/CourseNav.vue'
import AddCourse from '@/views/AddCourse.vue'
import AllCourse from '@/views/AllCourse.vue'
import UpdateCourse from '@/views/UpdateCourse.vue'
import DeleteCourse from '@/views/DeleteCourse.vue'
import SelectCourse from '@/views/SelectCourse.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
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
