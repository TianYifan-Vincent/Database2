import { createStore } from 'vuex'

export default createStore({
  state: {
    form:{},
    vehicle_form:{}
  },
  mutations: {
    //修改用户信息
    submitClientInfo(state,res){
      state.form=res
    },
    submitVehicleInfo(state,res){
      state.vehicle_form=res
    }
  },
  actions: {
  },
  modules: {
  }
})
