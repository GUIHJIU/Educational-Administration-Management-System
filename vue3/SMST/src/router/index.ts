import { createRouter, createWebHistory } from 'vue-router'
import Course from "@/views/Course.vue";
import CourseNav from '@/views/CourseNav.vue'
import AddCourse from '@/views/AddCourse.vue'
import AllCourse from '@/views/AllCourse.vue'
import UpdateCourse from '@/views/UpdateCourse.vue'
import DeleteCourse from '@/views/DeleteCourse.vue'
import SelectCourse from '@/views/SelectCourse.vue'
import TeacherListPage from '../views/Stuadmin/StuAdList.vue';
import AddPage from '../views/Stuadmin/StuAdadd.vue';
import DeletePage from '../views/Stuadmin/StuAddelete.vue';
import UpdatePage from '../views/Stuadmin/StuAdupdate.vue';
import ListPage from '../views/scoremanage.vue';
import StudentUpdatePage from '../views/StuStudent/StuStupdate.vue';
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            redirect: '/List'
        },
        {
            path: '/List',
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
        },
        {
            path: '/exam',
            name: 'exam',
            component: () => import('../views/ExamList.vue')
        },
        {
            path: '/examAdd',
            name: 'ExamAdd',
            component: () => import('../views/ExamAdd.vue')
        },
        {
            path: '/exam/update',
            name: 'ExamUpdate',
            component: () => import('../views/ExamUpdate.vue')
        },
        {
            path: '/exam/delete/:id',
            name: 'ExamDelete',
            component: () => import('../views/ExamDelete.vue')
        },
        {
            path: '/score',
            name: 'list',
            component: ListPage,
        },
    ],
})
export default router

