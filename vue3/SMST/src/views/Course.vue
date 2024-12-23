<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import {useridentitystore} from '@/store/userStore'
import {storeToRefs} from 'pinia'
const userstore=useridentitystore()
const x=storeToRefs(userstore)
function changehole()
{
  if(x.identity.value=='teacher')
    userstore.identity='student'
  else
    userstore.identity='teacher'
}
</script>

<template>
  <header>
    <div class="wrapper">
      <nav>
        <RouterLink to="/course" v-if="x.identity.value==='teacher'">course</RouterLink>
        <h1 v-else>什么都没有</h1>
      </nav>
      <button @click="changehole">切换身份</button>
    </div>
  </header>
  <div v-if="x.identity.value==='teacher'">
    <RouterView />
  </div>

</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
