import { createRouter, createWebHistory } from 'vue-router';
import AddPage from '../views/Stuadmin/add.vue';
import TeacherListPage from '../views/StuStudent/List.vue';
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
        }
      
    ]
});

export default router;