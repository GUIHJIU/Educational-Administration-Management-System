import { createStore } from 'vuex';

export const store = createStore({
  state: {
    studentId: ''
  },
  mutations: {
    setStudentId(state, studentId) {
      state.studentId = studentId;
    }
  },
  actions: {},
  modules: {}
});

export default store;

export function useStore() {
  return store;
}
