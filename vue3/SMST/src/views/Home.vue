<template>
  <h1>                                        </h1>
  <div class="home-container">
    <h1 class="welcome-title">欢迎登录！学生信息管理系统</h1>
    <div class="carousel-container">
      <transition-group name="fade">
        <div
          v-for="(image, index) in images"
          :key="image"
          v-show="currentIndex === index"
          class="carousel-slide"
          :style="{ backgroundImage: `url(${image})` }"
        ></div>
      </transition-group>
      <div class="carousel-controls">
        <div
          v-for="(_, index) in images"
          :key="index"
          class="carousel-dot"
          :class="{ active: currentIndex === index }"
          @click="setSlide(index)"
        ></div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';

const images = [
  'https://ehall.lzjtu.edu.cn/CUS_TEMPLATE_LZJTUS/pc/img/chun.ecb0cff8.png',
  'https://ehall.lzjtu.edu.cn/CUS_TEMPLATE_LZJTUS/pc/img/don.7c6aba05.png',
  'https://authserver.lzjtu.edu.cn/login_bg.jpg'
];

const currentIndex = ref(0);
let timer: number | null = null;

const nextSlide = () => {
  currentIndex.value = (currentIndex.value + 1) % images.length;
};

const setSlide = (index: number) => {
  currentIndex.value = index;
};

onMounted(() => {
  // 每5秒自动切换图片
  timer = window.setInterval(nextSlide, 5000);
});

onUnmounted(() => {
  if (timer) {
    clearInterval(timer);
  }
});
</script>

<style scoped>
h1{
  background-color: #0d5db9;
  color: #d9d9d9;
  padding: 20px;
  text-align: center;
  border-radius: 10px;
}
.home-container {
  width: 100%;
  height: 100vh;
  overflow: hidden;
  position: relative;
}

.carousel-container {
  width: 100%;
  height: 100%;
  position: relative;
}

.carousel-slide {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.carousel-controls {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 10px;
  z-index: 10;
}

.carousel-dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.5);
  cursor: pointer;
  transition: all 0.3s ease;
}

.carousel-dot.active {
  background: #fff;
  transform: scale(1.2);
}

/* 淡入淡出动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.fade-enter-to,
.fade-leave-from {
  opacity: 1;
}

.welcome-title {
  position: absolute;
  top: 50px;
  left: 50%;
  transform: translateX(-50%);
  text-align: center;
  color: #fff;
  font-size: 36px;
  font-weight: bold;
  z-index: 20;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
  letter-spacing: 2px;
  background: linear-gradient(to right, rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.4));
  padding: 15px 30px;
  border-radius: 10px;
  backdrop-filter: blur(5px);
}
</style>