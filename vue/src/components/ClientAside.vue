<template>
  <el-menu
      default-active="1"
      class="el-menu-vertical-demo"
      style="width: 200px; min-height: calc(100vh - 50px)"
      :default-openeds="defaultOpenedsArray"
  >
    <el-sub-menu index="1" style="background-color: #d9edf7" >
      <template #title ><el-icon><document /></el-icon>
        <span >基本信息</span></template>
      <el-menu-item index="name" class="div1">客户名称：</el-menu-item>
      <el-menu-item index="nature" class="div1">客户性质：</el-menu-item>
      <el-menu-item index="phone" class="div1">联系电话：</el-menu-item>
      <el-menu-item index="contact" class="div1">联系人：</el-menu-item>
      <el-menu-item index="discount" class="div1">折扣率：</el-menu-item>
<!--      <el-menu-item index="changeinfo" style="text-decoration: underline">更改个人信息</el-menu-item>-->
<!--      <el-menu-item index="changepwd" style="text-decoration: underline" @click="centerDialogVisible = true">更改密码</el-menu-item>-->
    </el-sub-menu>
    <el-sub-menu index="2" style="background-color: #dff0d8">
      <template #title><el-icon><setting /></el-icon>查看申请详情</template>
      <el-menu-item index="in" style="text-decoration: underline">进行中</el-menu-item>
      <el-menu-item index="finish" style="text-decoration: underline">已完成</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script>
import {Document, Setting } from '@element-plus/icons'
import {ref} from "vue";
export default {
  name: "ClientAside",
  components: {Document, Setting},
  setup(){
      const centerDialogVisible = ref(false)
    return{
        centerDialogVisible
    }
  },
  data(){
    return{
      defaultOpenedsArray:['1','2'],
      form: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur'},
        ],
        confirm: [
          { required: true, message: '请再次输入密码', trigger: 'blur'},
        ],
      }
    }
  },
  methods:{

  }
}
</script>

<style>
  .div1{
    margin-left: -20px;
  }
</style>