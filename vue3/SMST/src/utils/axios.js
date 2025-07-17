// src/utils/axios.js
import axios from 'axios';

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

// 响应拦截器 - 全局处理 token 刷新
// apiClient.interceptors.response.use(
//     (response) => response,
//     async (error) => {
//         const originalRequest = error.config;
//
//         // 如果是 401 错误且未尝试过刷新
//         if (error.response.status === 401 && !originalRequest._retry) {
//             originalRequest._retry = true;
//
//             try {
//                 // 尝试刷新 token
//                 const newToken = await refreshToken();
//
//                 // 更新存储和请求头
//                 localStorage.setItem('accessToken', newToken);
//                 apiClient.defaults.headers.common['Authorization'] = `Bearer ${newToken}`;
//                 originalRequest.headers['Authorization'] = `Bearer ${newToken}`;
//
//                 // 重新发送原始请求
//                 return apiClient(originalRequest);
//             } catch (refreshError) {
//                 // 刷新失败，清除 token 并重定向到登录页
//                 localStorage.removeItem('accessToken');
//                 localStorage.removeItem('refreshToken');
//                 router.push('/');
//                 return Promise.reject(refreshError);
//             }
//         }
//
//         return Promise.reject(error);
//     }
// );

// Token 刷新函数
async function refreshToken() {
    try {
        const refreshToken = localStorage.getItem('refreshToken');
        const response = await apiClient.post('https://localhost:443/auth/refresh', {refreshToken});

        if (response.headers['authorization']) {
            const authHeader = response.headers['authorization'];
            return authHeader.startsWith('Bearer ') ? authHeader.substring(7) : authHeader;
        }

        throw new Error('未返回新的 access token');
    } catch (error) {
        console.error('刷新 token 失败:', error);
        throw error;
    }
}

export default apiClient;