// 在main.ts中添加应用启动时的token验证
import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import {createPinia} from 'pinia'
import {useUserStore} from '@/store/userStore'

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.use(router)

// 在应用挂载前验证token
const userStore = useUserStore()
if (userStore.isAuthenticated) {
    userStore.validateToken().then(isValid => {
        if (!isValid) {
            console.log('Token已失效，请重新登录')
        }
        app.mount('#app')
    })
} else {
    app.mount('#app')
}