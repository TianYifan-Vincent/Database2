import { createStore } from 'vuex'

export default createStore({
  state: {
    formClient:{}
  },
  mutations: {
    //修改用户信息
    submitClientInfo(state,form){
      state.formClient=form
    }
  },
  actions: {
  },
  modules: {
  }
})
