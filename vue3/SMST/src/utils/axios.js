// src/utils/axios.js
import axios from 'axios';
import {useUserStore} from "@/store/userStore.ts";

// 创建 axios 实例
const apiClient = axios.create({
    baseURL: 'https://localhost:443',
    timeout: 10000,
    withCredentials: true
});

// 请求拦截器 - 全局添加 Authorization 头
apiClient.interceptors.request.use(
    (config) => {
        const accessToken = localStorage.getItem('accessToken');
        if (accessToken) {
            config.headers.Authorization = `Bearer ${accessToken}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);
// 取消注释并完善响应拦截器
apiClient.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        // 如果是401错误且未尝试过刷新
        if (error.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

            try {
                // 尝试刷新token
                const newToken = await refreshToken();

                // 更新存储和请求头
                const userStore = useUserStore();
                userStore.setTokens(newToken, localStorage.getItem('refreshToken'));
                apiClient.defaults.headers.common['Authorization'] = `Bearer ${newToken}`;
                originalRequest.headers['Authorization'] = `Bearer ${newToken}`;

                // 重新发送原始请求
                return apiClient(originalRequest);
            } catch (refreshError) {
                // 刷新失败，清除token并重定向到登录页
                const userStore = useUserStore();
                userStore.clearUser();
                window.location.href = '/';
                return Promise.reject(refreshError);
            }
        }

        return Promise.reject(error);
    }
);

// 完善Token刷新函数
async function refreshToken() {
    try {
        const refreshToken = localStorage.getItem('refreshToken');
        const response = await apiClient.post('/auth/refresh', {refreshToken});

        if (response.headers['authorization']) {
            const authHeader = response.headers['authorization'];
            return authHeader.startsWith('Bearer ') ? authHeader.substring(7) : authHeader;
        }

        throw new Error('未返回新的access token');
    } catch (error) {
        console.error('刷新token失败:', error);
        throw error;
    }
}

export default apiClient;