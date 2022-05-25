<template>
  <div style="height:50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex ">
    <div style="cursor:pointer;width:200px; padding-left:30px; font-weight: bold; color: cornflowerblue" @click="judgestatus">汽车维修系统</div>
    <div style="flex:1"></div>
    <div style="width:100px;padding-right:20px">
        <el-dropdown>
          <span class="el-dropdown-link">
            {{username}}
            <el-icon class="el-icon--right">
            <arrow-down />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="$router.push('/changepassword')">修改密码</el-dropdown-item>
              <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
<!--      <el-dialog v-model="centerDialogVisible" title="修改密码" width="30%" center>-->
<!--        <el-form-item prop="username">-->
<!--          <el-input v-model="form.username" placeholder="用户名"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item prop="password">-->
<!--          <el-input v-model="form.password" placeholder="密码" show-password></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item prop="confirm">-->
<!--          <el-input v-model="form.confirm" placeholder="重新输入密码" show-password></el-input>-->
<!--        </el-form-item>-->
<!--      </el-dialog>-->
    </div>
  </div>
</template>

<script>
import {useStore} from "vuex";
export default {
  name: "Header",
  data(){
    return{
      username:'',
      status:'',
    }
  },
  methods:{
    judgestatus(){
      if(this.status==="Client"){
        this.$router.push('/client')
      }
    }
  },
  setup(){
    const store = useStore()
    return{
      store,
    }
  },
  computed:{
    getform(){
      return this.store.state.form.clientId2
    },
    getstatus(){
      return this.store.state.status
    }
  },
  created() {
    this.username=this.getform
    this.status=this.getstatus
  }
}
</script>

<style scoped>

</style>