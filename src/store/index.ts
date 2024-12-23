import { createStore } from 'vuex';

export const store = createStore({
  state: {
    studentId: ''
  },
  // 其他 Vuex 配置...
});

export function useStore() {
  return store;
} 