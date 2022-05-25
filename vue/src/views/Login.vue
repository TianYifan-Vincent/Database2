<template>
<div style="width: 100%; height: 100vh; background-color: darkslateblue; overflow:hidden">
  <div style="width: 400px; margin: 150px auto">
    <div style="color: #cccccc; font-size: 30px; text-align: center; padding:30px 0 ">汽车维修系统</div>
    <el-form ref="form" :model="form" size="normal" :rules="rules">
      <el-icon><user /></el-icon>
      <el-form-item prop="username">
        <el-input v-model="form.username" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="form.password" placeholder="密码" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button style = "width:100%" type="primary" @click="login">登陆</el-button>
        <el-button style = "margin-left:0;margin-top:10px;width:100%" type="primary" @click="register">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</div>
</template>

<script>
import request from "@/util/request";
import { useStore } from 'vuex'


export default {
  name: "Login",
  setup(){
    const store = useStore()
    return{
      store
    }
  },
  data(){
    return {
      form: {
        username:'',
        password:''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur'},
        ],
      }
    }
  },
  methods:{
    login(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.get(`/user/login/${this.form.username}/${this.form.password}`).then(res => {
            // console.log(res[0].data)
            if (res[0].code === 455) {
              this.store.commit('submitClientInfo', res[0].data)//同步提交个人信息数据
              this.store.commit('setstatus', res[1].data)//提交个人身份
              if (res.length===3){
                this.store.commit('submitVehicleInfo', res[2].data)//同步提交车辆数据
              } else{
                this.store.commit('setVehicle')
              }
              this.$message({
                type: "success",
                message: "登陆成功"
              })
              if (res[1].data === "Client"){
                this.$router.push("/client")  //登陆成功后进行页面跳转，跳转到客户页面
              }
              else if(res[1].data === "Repairman"){
                this.$router.push("/home")  //登陆成功后进行页面跳转，跳转到业务员页面
              }
              else{
                this.$router.push("/user")  //登陆成功后进行页面跳转，跳转到维修员页面
              }

            } else {
              this.$message({
                type: "error",
                message: res[0].msg
              })
            }
          })
        }
      })
    },
    register(){
      this.$router.replace('/register')
    }
  },
}
</script>

<style scoped>

</style>