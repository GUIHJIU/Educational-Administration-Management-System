import { createRouter, createWebHistory } from 'vue-router'
import Course from "@/views/Course.vue";
import CourseNav from '@/views/CourseNav.vue'
import AddCourse from '@/views/AddCourse.vue'
import AllCourse from '@/views/AllCourse.vue'
import UpdateCourse from '@/views/UpdateCourse.vue'
import DeleteCourse from '@/views/DeleteCourse.vue'
import SelectCourse from '@/views/SelectCourse.vue'
import TeacherListPage from '../views/Stuteacher/StuTeList.vue';
import AdminListPage from '../views/Stuadmin/StuAdList.vue';
import StudentListPage from '../views/StuStudent/StuStList.vue';
import StuAddPage from '../views/Stuadmin/StuAdadd.vue';
import StuDeletePage from '../views/Stuadmin/StuAddelete.vue';
import StuUpdatePage1 from '../views/Stuadmin/StuAdupdate.vue';
import StuUpdatePage2 from '../views/StuStudent/StuStupdate.vue';
import Home from "@/views/Home.vue";
import ListPage from '../views/scoremanage.vue';
import LoginPage from "@/views/LoginView.vue";
import {useridentitystore} from '@/store/userStore'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            component: LoginPage,

        },
        {
            path:'/home',
            component:Home,
            meta:{
                requiresRole: ['admin','teacher','student']
            }
        },
        {path: '/StuAdmin',
            component: AdminListPage,
            meta: {
                requiresRole: ['admin'] // 标记此路由需要超级用户角色才能访问
            },
        },
        {
            path: '/StuAdmin/StuAdadd',
            component: StuAddPage
        },
        {
            path: '/StuAdmin/StuAdupdate',
            component: StuUpdatePage1
        },
        {
            path: '/StuAdmin/StuAddelete',
            component: StuDeletePage
        },
        {
            path: '/TeacherList',
            component:TeacherListPage,
            meta: {
                requiresRole: ['teacher'] // 标记此路由需要教师用户角色才能访问
            }
            },
        {
            path: '/StudentList',
            component: StudentListPage,
            meta: {
                requiresRole: ['student'] // 标记此路由需要普通用户角色才能访问
            },
        },
         {
                    path:'/StudentList/Stuupdate',
                    component:StuUpdatePage2
         },
        {
            path:'/coursenav',
            component:Course,
            meta:{
                requiresRole: ['admin']
            }
        },
        {
            path:'/course',
            component:CourseNav,
            children:[
                {
                    path:'addcourse',
                    component:AddCourse,
                    meta:{
                        requiresRole:['teacher','admin']
                    }
                },
                {
                    path:'allcourse',
                    component:AllCourse,
                    meta:{
                        requiresRole:['teacher','admin','student']
                    }
                },
                {
                    path:'updatecourse',
                    component:UpdateCourse,
                    meta:{
                        requiresRole:['teacher','admin']
                    }
                },
                {
                    path:'deletecourse',
                    component:DeleteCourse,
                    meta:{
                        requiresRole:['teacher','admin']
                    }
                },
                {
                    path:'selectcourse',
                    component:SelectCourse,
                    meta:{
                        requiresRole:['teacher','admin','student']
                    }
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

router.beforeEach((to, from, next) => {
    const userstore=useridentitystore()
    const userRole = userstore.identity;
    if (to.meta.requiresRole)
    {
        // 如果有角色要求，对比当前用户角色是否匹配
        if (Array.isArray(to.meta.requiresRole) && to.meta.requiresRole.includes(userRole)) {
            next();
        }
        else
        {
            // 角色不匹配，跳转到登录页或者其他提示页面，这里跳转到登录页
            next("/");
        }
    }
    else {
        console.log('因角色不匹配，跳转到登录页');
        next(); // 添加这一行，放行没有角色要求的路由
    }
});
export default router

