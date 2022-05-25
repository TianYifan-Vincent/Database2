import { createStore } from 'vuex'

export default createStore({
  state: {
    form:{},
    status:'',//用户的身份是client
    vehicle_form:[]
  },
  mutations: {
    //修改用户信息
    submitClientInfo(state,res){
      state.form=res
    },
    submitVehicleInfo(state,res){
      state.vehicle_form=res
    },
    //车辆信息置为空
    setVehicle(state) {
      state.vehicle_form = []
    }
    ,
    //修改用户身份
    setstatus(state,res){
      state.status=res
    }
  },
  actions: {
  },
  modules: {
  }
})
