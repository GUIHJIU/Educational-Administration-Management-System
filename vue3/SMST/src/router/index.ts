import { createRouter, createWebHistory } from 'vue-router'
import CourseNav from '@/views/CourseNav.vue'
import AddCourse from '@/views/AddCourse.vue'
import AllCourse from '@/views/AllCourse.vue'
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
        }
      ]
    }
  ],
})

export default router
