<template>
  <el-menu
      default-active="1"
      class="el-menu-vertical-demo"
      style="width: 200px; min-height: calc(100vh - 50px)"
      :default-openeds="defaultOpenedsArray"
      router
  >
    <el-sub-menu index="1" style="background-color: #d9edf7">
      <template #title><el-icon><setting></setting></el-icon>系统管理</template>
      <el-menu-item index="home">用户管理</el-menu-item>
      <el-menu-item index="Manageorder">订单管理</el-menu-item>
      <el-menu-item index="Workorder">派工管理</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script>
import {Setting} from '@element-plus/icons'
export default {
  name: "Aside",
  components: {Setting},
  data(){
    return{
      defaultOpenedsArray:['1'],
    }
  }
}
</script>

<style>

</style>