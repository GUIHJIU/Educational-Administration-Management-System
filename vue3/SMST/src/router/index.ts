import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import AddPage from '../views/add.vue';
import ListPage from '../views/List.vue';
import DeletePage from '../views/delete.vue';
import UpdatePage from '../views/update.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/list',
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