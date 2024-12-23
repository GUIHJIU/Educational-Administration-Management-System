import { createRouter, createWebHistory } from 'vue-router';
import AddPage from '../views/add.vue';
import ListPage from '../views/List.vue';
import DeletePage from '../views/delete.vue';
import UpdatePage from '../views/update.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [

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

    ]
});

export default router;