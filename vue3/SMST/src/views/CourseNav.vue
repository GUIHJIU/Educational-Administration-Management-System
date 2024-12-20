<template>
    <header>
        <div class="wrapper">
            <nav>
                <RouterLink to="courseall">AllCourse</RouterLink>
                <RouterLink to="selectcourse">SelectCourse</RouterLink>
                <RouterLink to="addcourse">AddCourse</RouterLink>
                <RouterLink to="updatecourse">UpdateCourse</RouterLink>
                <RouterLink to="deletecourse">DeleteCourse</RouterLink>
            </nav>
        </div>
    </header>

    <RouterView />
</template>
<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>