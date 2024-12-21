<template>
    <header>
        <div class="wrapper">
            <nav>
                <RouterLink to="/course/allcourse">所有课程</RouterLink>
                <RouterLink to="/course/selectcourse">选择课程</RouterLink>
                <RouterLink to="/course/addcourse">添加课程</RouterLink>
                <RouterLink to="/course/updatecourse">修改课程</RouterLink>
                <RouterLink to="/course/deletecourse">删除课程</RouterLink>
            </nav>
        </div>
    </header>

    <RouterView />
</template>
<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>