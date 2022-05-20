<template>
  <div style="padding: 10px">
    <!--    功能区-->
    <div style="margin:10px 0">
      <el-button type="primary" @click="centerDialogVisible2 = true" style="font-size: large">登记信息</el-button>
      <el-button type="primary" @click="centerDialogVisible = true" style="font-size: large" >新增车辆</el-button>
      <el-button type="primary" style="font-size: large">历史维修委托</el-button>
      <el-button type="primary" style="font-size: large">新增维修委托</el-button>
      <el-dialog v-model="centerDialogVisible" title="车辆信息" width="30%" center>
      <el-form :model="form" label-width="120px" style="margin-left: -40px">
        <el-form-item label="车架号">
          <el-input v-model="form.vin" />
        </el-form-item>
        <el-form-item label="车牌号">
          <el-input v-model="form.license" />
        </el-form-item>
        <el-form-item label="颜色">
          <el-input v-model="form.color" />
        </el-form-item>
        <el-form-item label="车型">
          <el-input v-model="form.type" />
        </el-form-item>
        <el-form-item label="车辆类别">
          <el-select v-model="form.class" placeholder="请输入车辆类别">
            <el-option label="微型车" value="微型车" />
            <el-option label="中型车" value="中型车" />
            <el-option label="大型车" value="大型车" />
          </el-select>
        </el-form-item>
        <el-form-item >
          <el-button type="primary" @click="onSubmit,centerDialogVisible = false">确认新增</el-button>
          <el-button type="primary" @click="centerDialogVisible = false" style="margin-left: 40px">取消</el-button>
        </el-form-item>
      </el-form>
      </el-dialog>
      <el-dialog v-model="centerDialogVisible2" title="基本信息" width="30%" center>
        <el-form :model="form" label-width="120px" style="margin-left: -40px">
          <el-form-item label="名称">
            <el-input v-model="form.vin" />
          </el-form-item>
          <el-form-item label="性质">
            <el-select v-model="form.class" placeholder="请输入车辆类别">
              <el-option label="个人" value="个人" />
              <el-option label="单位" value="单位" />
            </el-select>
          </el-form-item>
          <el-form-item label="联系人">
            <el-input v-model="form.color" />
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="form.type" />
          </el-form-item>
          <el-form-item >
            <el-button type="primary" @click="onSubmit,centerDialogVisible2 = false">确认</el-button>
            <el-button type="primary" @click="centerDialogVisible2 = false" style="margin-left: 40px">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: "Client",
  setup(){
    const centerDialogVisible = ref(false)
    const centerDialogVisible2 = ref(false)
    return{
      centerDialogVisible,
      centerDialogVisible2
    }
  },
  data(){
    return{
      form:{
        vin:'',
        license:'',
        color:'',
        type:'',
        class:'',
        name:'',
        nature:'',

      }
    }
  },
  methods:{
    onSubmit(){
      console.log(this.form.class)
    },
  },

}
</script>

<style scoped>

</style>