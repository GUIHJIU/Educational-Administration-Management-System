<template>
  <div class="index">
    <!-- 头部导航 -->
    <header class="index-header">
      <div class="index-header-left">
        <h2>
          <img src="../../assets/images/logo.png" alt="Logo">
        </h2>
        <p>本科教务管理系统</p>
      </div>
      <div class="index-header-right">
        <div class="search-box">
          <input
              type="text"
              class="search-input"
              placeholder="搜索功能或菜单"
              @keyup="handleSearch"
          />
          <span class="icon-search"></span>
        </div>
        <div class="user-info">
          <span class="icon-user"></span>
          <span>username</span>
          <span class="logout" @click="handleLogout">退出</span>
        </div>
      </div>
    </header>

    <!-- 主体内容 -->
    <main class="index-main">
      <aside class="sidebar">
        <!-- 菜单管理 -->
        <nav class="menu">
          <ul>
            <li v-for="menu in menus" :key="menu.code" @click="toggleMenu(menu.code)">
              <div class="menu-item">
                <img :src="menu.icon" alt="" class="menu-icon" />
                <span>{{ menu.title }}</span>
                <span class="icon-arrow-down" v-if="menu.submenus.length"></span>
              </div>
              <ul v-if="menu.expanded" class="submenu">
                <li
                    v-for="submenu in menu.submenus"
                    :key="submenu.code"
                    @click.stop="navigateTo(submenu.src)"
                >
                  {{ submenu.title }}
                </li>
              </ul>
            </li>
          </ul>
        </nav>
      </aside>

      <section class="content">
        <h1>欢迎使用本科教务管理系统</h1>
        <p>请选择左侧菜单项以继续操作。</p>
      </section>
    </main>
  </div>
</template>

<script>
import icon1 from '@/assets/images/nav5.png';
import icon2 from '@/assets/images/nav6.png';
export default {
  name: "TeachingServicePlatform",
  data() {
    return {
      menus: [
        {
          code: "menu1",
          title: "课程管理",
          icon: icon1,
          expanded: false,
          submenus: [
            { code: "submenu1", title: "课程查询", src: "/course-query" },
            { code: "submenu2", title: "选课管理", src: "/course-selection" },
          ],
        },
        {
          code: "menu2",
          title: "学生管理",
          icon: icon2,
          expanded: false,
          submenus: [
            { code: "submenu1", title: "学生信息", src: "/student-info" },
            { code: "submenu2", title: "成绩查询", src: "/grade-query" },
          ],
        },
      ],
    };
  },
  methods: {
    handleSearch(event) {
      console.log("Search triggered with query:", event.target.value);
    },
    handleLogout() {
      if (confirm("确认退出系统吗？")) {
        console.log("用户已退出");
      }
    },
    toggleMenu(code) {
      this.menus.forEach((menu) => {
        if (menu.code === code) {
          menu.expanded = !menu.expanded;
        } else {
          menu.expanded = false;
        }
      });
    },
    navigateTo(src) {
      console.log(`Navigating to: ${src}`);
      this.$router.push(src);
    },
  },
};
</script>

<style scoped>
.index {
  width: 100vw; /* 视口宽度 */
  height: 100vh; /* 视口高度 */
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #f0f2f5; /* 淡灰色背景 */
}

.index-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 60px;
  background-color: #004085; /* 深蓝色背景 */
  color: white;
  padding: 0 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.index-header-left h2 img {
  height: 40px;
}

.index-header-left p {
  margin-left: 10px;
  font-size: 18px;
  font-weight: bold;
}

.index-header-right {
  display: flex;
  align-items: center;
}

.search-box {
  position: relative;
  margin-right: 20px;
}

.search-input {
  padding: 8px 12px;
  border-radius: 20px;
  border: 1px solid #ccc;
  width: 200px;
  transition: border-color 0.3s; /* 平滑边框颜色变化 */
}

.search-input:focus {
  border-color: #004085; /* 聚焦时边框颜色 */
}

.icon-search {
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  color: gray;
}

.user-info span {
  margin-left: 10px;
  cursor: pointer;
}

.index-main {
  display: flex;
  flex: 1;
}

.sidebar {
  width: 250px;
  background-color: #e8e8e8; /* 侧边栏背景色 */
  border-right: 1px solid #ddd;
  overflow-y: auto;
}

.menu {
  list-style: none;
  padding: 0;
  margin: 0;
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.3s; /* 平滑背景颜色变化 */
}

.menu-item:hover {
  background-color: #d1d1d1; /* 悬停时背景色 */
}

.menu-icon {
  width: 24px;
  height: 24px;
  margin-right: 10px;
}

.submenu {
  list-style: none;
  padding-left: 20px;
  background-color: #f9f9f9; /* 子菜单背景色 */
}

.submenu li {
  padding: 8px 20px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submenu li:hover {
  background-color: #e0e0e0; /* 子菜单项悬停时背景色 */
}

.content {
  flex: 1;
  padding: 20px;
  background-color: #ffffff;
  overflow-y: auto;
}
</style>


