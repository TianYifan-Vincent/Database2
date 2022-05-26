<template>
  <div>
    <!--    头部-->
    <Header />
    <!--  主体-->
    <div style="display:flex">
      <!--- 侧边栏 --->
      <RepairAside />
      <!--  内容显示-->
      <router-view style="flex:1" />
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import RepairAside from "@/components/RepairAside";


export default {
  name: "RepairmanLayout",
  components:{
    Header,
    RepairAside
  },
  data(){
    return{
      componentKey:0
    }
  },
  methods:{
  },
  created() {
  }
}
</script>

<style scoped>

</style>