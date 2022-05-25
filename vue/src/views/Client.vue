<template>
  <div style="padding: 10px">
    <!--    功能区-->
    <div style="margin:10px 0">
      <el-button type="primary" @click="centerDialogVisible2=true" style="font-size: large">登记信息</el-button>
      <el-button type="primary" @click="centerDialogVisible1 = true" style="font-size: large" >新增车辆</el-button>
      <el-dialog v-model="centerDialogVisible1" title="车辆信息" width="30%" center :before-close="closeExpertFormDialog">
      <el-form ref="Vehicle" :model="formVehicle" label-width="120px" >
        <el-form-item label="车架号" prop="vin">
          <el-input v-model="formVehicle.vin" placeholder="请输入车架号" style="width: 70%"/>
        </el-form-item>
        <el-form-item label="车牌号" prop="license">
          <el-input v-model="formVehicle.license" placeholder="请输入车牌号" style="width: 70%"/>
        </el-form-item>
        <el-form-item label="颜色" prop="color">
          <el-input v-model="formVehicle.color" placeholder="请输入车辆颜色" style="width: 70%"/>
        </el-form-item>
        <el-form-item label="车型" prop="class">
          <el-input v-model="formVehicle.class" placeholder="请输入车型" style="width: 70%"/>
        </el-form-item>
        <el-form-item label="车辆类别" prop="type">
          <el-select v-model="formVehicle.type" placeholder="请输入车辆类别" style="width: 70%">
            <el-option label="微型车" value="微型车" />
            <el-option label="中型车" value="中型车" />
            <el-option label="大型车" value="大型车" />
          </el-select>
        </el-form-item>
        <el-form-item >
          <el-button type="primary" @click="onSubmit">确认新增</el-button>
          <el-button type="primary" @click="cancelExpert1('Vehicle')" style="margin-left: 40px">取消</el-button>
        </el-form-item>
      </el-form>
      </el-dialog>
      <el-dialog v-model="centerDialogVisible2" title="基本信息" width="30%" center :before-close="closeExpertFormDialog2">
        <el-form ref="Client" :model="formClient" label-width="120px" >
          <el-form-item label="名称" prop="name">
            <el-input v-model="formClient.name" placeholder="请输入用户名称" style="width: 70%"/>
          </el-form-item>
          <el-form-item label="性质" prop="nature">
            <el-select v-model="formClient.nature" placeholder="请输入用户性质" style="width: 70%">
              <el-option label="个人" value="个人" />
              <el-option label="单位" value="单位" />
            </el-select>
          </el-form-item>
          <el-form-item label="联系人" prop="contact">
            <el-input v-model="formClient.contact" placeholder="请输入联系人" style="width: 70%"/>
          </el-form-item>
          <el-form-item label="联系电话" prop="phone">
            <el-input v-model="formClient.phone" placeholder="请输入联系电话" style="width: 70%"/>
          </el-form-item>
          <el-form-item >
            <el-button type="primary" @click="onSubmit2">确认</el-button>
            <el-button type="primary" @click="cancelExpert2('Client')" style="margin-left: 40px">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <el-dialog v-model="centerDialogVisible3" title="新增委托" width="30%" center :before-close="closeExpertFormDialog3">
        <el-form ref="Repair" :model="formRepair" label-width="120px " style="margin-left: -40px">
          <el-form-item label="车架号" prop="vin">
            <el-input v-model="formRepair.vin" placeholder="请输入车架号" style="width: 50%"/>
          </el-form-item>
          <el-form-item label="结算方式" prop="resource">
            <el-radio-group v-model="formRepair.resource">
              <el-radio label="自付" />
              <el-radio label="索赔" />
              <el-radio label="三包" />
            </el-radio-group>
          </el-form-item>
          <el-form-item label="进厂时间" prop="approach_time" >
            <el-date-picker
                v-model="formRepair.approach_time"
                type="datetime"
                placeholder="Select date and time"
            />
          </el-form-item>
          <el-form-item label="粗略故障" prop="failure">
            <el-input v-model="formRepair.failure" type="textarea" placeholder="请粗略描述您车辆的故障（字数在255以下）"/>
          </el-form-item>
          <el-form-item label="进厂油量" prop="fuel">
            <el-input v-model="formRepair.fuel" placeholder="进厂时车辆所剩油量" style="width: 50%"/><span>%</span>
          </el-form-item>
          <el-form-item label="进厂里程" prop="mile">
            <el-input v-model="formRepair.mile" placeholder="进厂时车辆里程数" style="width: 50%"/><span>km</span>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit3">确认新增</el-button>
            <el-button type="primary" @click="cancelExpert3('Repair')" style="margin-left: 40px">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
    <div v-for="(item,index) in formVehicles" :key="item">
      <div v-if="item.license">
        <el-descriptions
            class="margin-top"
            title="车辆信息"
            :column="1"
            size=large
            border
            style="width: 60%;font-size: large"
        >
          <template #extra>
            <el-button type="primary" @click="centerDialogVisible3 = true" style="font-size: large;background-color: #001b7a">新增维修委托</el-button>
            <el-button type="primary" style="font-size: large;background-color: #001b7a" @click="$router.push('/onway')">历史维修委托</el-button>
          </template>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <user />
                </el-icon>
                车牌号
              </div>
            </template>
            {{item.license}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <iphone />
                </el-icon>
                车架号
              </div>
            </template>
            {{item.vin}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <location />
                </el-icon>
                颜色
              </div>
            </template>
            {{item.color}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <tickets />
                </el-icon>
                车型
              </div>
            </template>
            <el-tag size="large" style="font-size: large">{{item.category}}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <office-building />
                </el-icon>
                车辆类别
              </div>
            </template>
            {{item.type}}
          </el-descriptions-item>
        </el-descriptions>
        </div>
        <div v-else>暂无车辆信息</div>
    </div>
  </div>
</template>

<script>
import { ref ,computed} from 'vue'
import {useStore} from "vuex";
import request from "@/util/request";
import ClientLayout from "@/layout/ClientLayout";
import ClientAside from "@/components/ClientAside";
export default {
  name: "Client",
  inject:['reload'],
  components:{
    ClientLayout,
    ClientAside
  },
  setup() {
    const centerDialogVisible1 = ref(false)
    const centerDialogVisible2 = ref(false)
    const centerDialogVisible3 = ref(false)
    const size = ref('')
    const value1 = ref('')
    const store = useStore()
    const iconStyle = computed(() => {
      const marginMap = {
        large: '8px',
        default: '6px',
        small: '4px',
      }
      return {
        marginRight: marginMap[size.value] || marginMap.default,
      }
    })
    return {
      centerDialogVisible1,
      centerDialogVisible2,
      centerDialogVisible3,
      size,
      iconStyle,
      store,
      value1
    }
  },
  data() {
    return {
      formVehicles:[],
      formVehicle: {
      },
      formClient: {
      },
      formRepair: {
      }
    }
  },
  methods: {
    load(){
      request.get(`/client/${this.formClient.clientId}`).then(res => {
        this.formVehicles = res.data//数组类的数据
      })
    },
    onSubmit() {
      this.$refs['Vehicle'].validate((valid) => {
        if (valid) {
          request.post("/client",this.formVehicle).then(res => {
            console.log(res)
            if (res.code === 454) {
              this.$message({
                type: "success",
                message: "新增车辆成功"
              })
              // this.store.commit('submitClientInfo', this.formClient)//同步提交个人信息数据
              this.load()
              this.reload()//重新加载组件
            }else if(res.code===2){
              this.$refs["Vehicle"].resetFields();
              this.$message({
                type: "error",
                message: res.msg
              })
            }
            else {
              this.$refs["Vehicle"].resetFields();
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }})
      this.load()
      this.centerDialogVisible1 = false
      // this.formVehicle = JSON.parse(JSON.stringify(this.defaultForm2))
    },
    onSubmit2() {
          request.put("/client",this.formClient).then(res => {
            console.log(res)
            if (res.code === 200) {
              this.$message({
                type: "success",
                message: res.msg
              })
              this.store.commit('submitClientInfo', this.formClient)//同步提交个人信息数据
              this.reload()
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
      this.centerDialogVisible2 = false
    },
    onSubmit3() {
      console.log(this.formRepair)
      this.$refs['Repair'].validate((valid) => {
        if (valid) {
          request.post("/client",this.formRepair).then(res => {
            console.log(res)
            if (res.code === 454) {
              this.$message({
                type: "success",
                message: "新增委托成功"
              })
              // this.store.commit('submitClientInfo', this.formClient)//同步提交个人信息数据
              this.reload()
            }else if(res.code===2){
              this.$refs["Repair"].resetFields();
              this.$message({
                type: "error",
                message: res.msg
              })
            }
            else {
              this.$refs["Repair"].resetFields();
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }})
      this.centerDialogVisible3 = false
    },

//取消dialog
    cancelExpert1(formName) {
      this.$refs[formName].resetFields();
      this.centerDialogVisible1 = false; //关闭对话框
    },
    cancelExpert2(formName) {
      this.$refs[formName].resetFields();
      this.centerDialogVisible2 = false; //关闭对话框
    },
    cancelExpert3(formName) {
      this.$refs[formName].resetFields();
      this.centerDialogVisible3 = false; //关闭对话框
    },
//关闭dialog前
    closeExpertFormDialog(done) {
      this.$refs['Vehicle'].resetFields();
      done();//done 用于关闭 Dialog
    },
    closeExpertFormDialog2(done) {
      this.$refs['Client'].resetFields();
      done();//done 用于关闭 Dialog
    },
    closeExpertFormDialog3(done) {
      this.$refs['Repair'].resetFields();
      done();//done 用于关闭 Dialog
    },

  },
  computed:{
    getvehicleform(){
      return this.store.state.vehicle_form
    },
    getclientform(){
      return this.store.state.form.clientId
    }
  },
  created() {
    this.formVehicles=this.getvehicleform
    this.formClient.clientId=this.getclientform
    this.formVehicle.clientId=this.getclientform
    this.load()
    // this.load()
    // this.reload()
  }
}
</script>

<style scoped>

.cell-item {
  display: flex;
  align-items: center;
}
.margin-top {
  margin-top: 20px;
}
</style>