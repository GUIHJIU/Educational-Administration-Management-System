import { createRouter, createWebHistory } from 'vue-router';
import AddPage from '../views/add.vue';
import ListPage from '../views/scoremanage.vue';
import DeletePage from '../views/deletescore.vue';
import UpdatePage from '../views/update.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/score',
            name: 'list',
            component: ListPage,
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