<template>
  <el-menu
      default-active="1"
      class="el-menu-vertical-demo"
      style="width: 200px; min-height: calc(100vh - 50px)"
      :default-openeds="defaultOpenedsArray"
      router
  >
    <el-sub-menu index="1" style="background-color: #d9edf7">
      <template #title><el-icon><setting></setting></el-icon>订单管理</template>
      <el-menu-item index="repairman" style="text-decoration: underline">待确认维修</el-menu-item>
      <el-menu-item index="onrepair" style="text-decoration: underline">进行中维修</el-menu-item>
      <el-menu-item index="repairfinished" style="text-decoration: underline">已完成维修</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script>
import {Setting} from '@element-plus/icons'
export default {
  name: "RepairAside",
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