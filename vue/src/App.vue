<template>
  <div>
    <router-view v-if="isRouterAlive" />

  </div>
</template>

<style>

</style>

<script>

export default {
  name:"Layout",
  provide(){
    return{
      reload:this.reload
    }
  },
  data(){
    return{
      isRouterAlive:true
    }
  },
  methods:{
    reload(){
      this.isRouterAlive=false
      this.$nextTick(function (){
        this.isRouterAlive=true
      })
    }
  }
}
</script>