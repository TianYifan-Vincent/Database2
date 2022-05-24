<template>
  <div>
    <!--    头部-->
    <Header />
    <!--  主体-->
    <div style="display:flex">
      <!--- 侧边栏 --->
      <ClientAside />
      <!--  内容显示-->
      <router-view style="flex:1" />
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import ClientAside from "@/components/ClientAside";


export default {
  name: "ClientLayout",
  components:{
    Header,
    ClientAside
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