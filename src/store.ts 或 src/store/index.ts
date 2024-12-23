import { createStore } from 'vuex';

export const store = createStore({
  // 你的 Vuex 配置
});

export function useStore() {
  return store;
} 