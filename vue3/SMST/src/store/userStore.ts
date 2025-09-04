import {defineStore} from 'pinia'
import apiClient from "@/utils/axios";

interface UserState {
    identity: string
    username: string
    accessToken: string
    refreshToken: string
    id: number
}

// 定义角色权限映射
const ROLE_PERMISSIONS = {
    admin: {
        canAccessAll: true,
        routes: ['home', 'StuAdmin', 'TeacherList', 'StudentList', 'course', 'score', 'exam', 'select-course', 'Persion', 'studentexam'],
        features: ['student_management', 'teacher_management', 'course_management', 'score_management', 'exam_management', 'selection_management']
    },
    teacher: {
        canAccessAll: false,
        routes: ['home', 'TeacherList', 'course', 'score', 'exam', 'select-course'],
        features: ['course_management', 'score_management', 'exam_management', 'selection_management']
    },
    student: {
        canAccessAll: false,
        routes: ['home', 'StudentList', 'course', 'score', 'select-course', 'Persion', 'studentexam'],
        features: ['course_selection', 'score_view', 'exam_view', 'personal_info']
    }
}

export const useUserStore = defineStore('userStore', {
    state: (): UserState => ({
        identity: localStorage.getItem('userIdentity') || 'null',
        username: localStorage.getItem('userAccount') || 'null',
        accessToken: localStorage.getItem('accessToken') || 'null',
        refreshToken: localStorage.getItem('refreshToken') || 'null',
        id: parseInt(localStorage.getItem('userId') || '0'),
    }),

    getters: {
        isAuthenticated: (state): boolean => {
            return state.accessToken !== 'null' && state.accessToken !== null
        },
        userRole: (state): string => state.identity,
        userPermissions: (state) => {
            return ROLE_PERMISSIONS[state.identity] || {routes: [], features: [], canAccessAll: false}
        },
        // 检查是否有特定功能权限
        hasFeature: (state) => (feature: string) => {
            if (state.identity === 'admin') return true
            return ROLE_PERMISSIONS[state.identity]?.features.includes(feature) || false
        },
        // 检查是否可以访问特定路由
        canAccessRoute: (state) => (routeName: string) => {
            if (state.identity === 'admin') return true
            return ROLE_PERMISSIONS[state.identity]?.routes.includes(routeName) || false
        }
    },

    actions: {
        setUser(userInfo: { identity: string; username: string; id: number }) {
            this.identity = userInfo.identity
            this.username = userInfo.username
            this.id = userInfo.id

            localStorage.setItem('userIdentity', userInfo.identity)
            localStorage.setItem('userAccount', userInfo.username)
            localStorage.setItem('userId', userInfo.id.toString())
        },

        setTokens(accessToken: string, refreshToken: string) {
            this.accessToken = accessToken
            this.refreshToken = refreshToken

            localStorage.setItem('accessToken', accessToken)
            localStorage.setItem('refreshToken', refreshToken)
        },

        clearUser() {
            this.identity = 'null'
            this.username = 'null'
            this.accessToken = 'null'
            this.refreshToken = 'null'
            this.id = 0

            localStorage.removeItem('userIdentity')
            localStorage.removeItem('userAccount')
            localStorage.removeItem('accessToken')
            localStorage.removeItem('refreshToken')
            localStorage.removeItem('userId')
        },

        // 验证token有效性
        async validateToken() {
            const token = this.accessToken

            if (!token || token === 'null') {
                this.clearUser()
                return false
            }

            try {
                const response = await apiClient.post('/auth/validate')

                if (response.status === 200) {
                    return true
                } else {
                    this.clearUser()
                    return false
                }
            } catch (error) {
                console.error('验证token时出错:', error)
                this.clearUser()
                return false
            }
        }
    }
})