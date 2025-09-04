import {fileURLToPath, URL} from 'node:url'
import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import AutoImport from 'unplugin-auto-import/vite';
import Components from 'unplugin-vue-components/vite';
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers';

// 新增：引入所需的Node.js模块
import fs from 'fs'
import path from 'path'

// https://vite.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueDevTools(),
        AutoImport({resolvers: [ElementPlusResolver()]}),
        Components({resolvers: [ElementPlusResolver()]}),
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        },
    },
    // 新增：服务器配置，启用HTTPS
    server: {
        https: {
            key: fs.readFileSync(path.resolve("E:\\WORKSPACE\\Java\\javaworkspace\\StudentManagementSystemTest\\", 'example.test+3-key.pem')), // 私钥文件路径
            cert: fs.readFileSync(path.resolve("E:\\WORKSPACE\\Java\\javaworkspace\\StudentManagementSystemTest\\", 'example.test+3.pem'))    // 证书文件路径
        },
        // 可选：明确指定主机和端口
        host: 'localhost',
        port: 5173
    }
})