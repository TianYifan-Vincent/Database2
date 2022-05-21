<template>
  <div style="padding: 10px">
    <!--    功能区-->
    <div style="margin:10px 0">
      <el-button type="primary" @click="centerDialogVisible2 = true" style="font-size: large">登记信息</el-button>
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
        <el-form-item label="车型" prop="type">
          <el-input v-model="formVehicle.type" placeholder="请输入车型" style="width: 70%"/>
        </el-form-item>
        <el-form-item label="车辆类别" prop="class">
          <el-select v-model="formVehicle.class" placeholder="请输入车辆类别" style="width: 70%">
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
          <el-form-item label="进场时间" prop="approach_date" >
            <el-col :span="11">
              <el-date-picker
                  v-model="formRepair.approach_date"
                  type="date"
                  placeholder="Pick a date"
                  style="width: 100%"
              />
            </el-col>
            <el-col :span="2" class="text-center">
              <span class="text-gray-500">-</span>
            </el-col>
            <el-col :span="11">
              <el-time-picker
                  v-model="formRepair.approach_time"
                  placeholder="Pick a time"
                  style="width: 100%"
              />
            </el-col>
          </el-form-item>
          <el-form-item label="粗略故障" prop="failure">
            <el-input v-model="formRepair.failure" type="textarea" placeholder="请粗略描述您车辆的故障（字数在255以下）"/>
          </el-form-item>
          <el-form-item label="进场油量" prop="fuel">
            <el-input v-model="formRepair.fuel" placeholder="进场时车辆所剩油量" style="width: 50%"/><span>L</span>
          </el-form-item>
          <el-form-item label="进场里程" prop="mile">
            <el-input v-model="formRepair.mile" placeholder="进场时车辆里程数" style="width: 50%"/><span>km</span>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit3">确认新增</el-button>
            <el-button type="primary" @click="cancelExpert3('Repair')" style="margin-left: 40px">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
    <div >
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
          <el-button type="primary" style="font-size: large;background-color: #001b7a" >历史维修委托</el-button>
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
          沪HKY640
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
          18100000000
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
          金色
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
          <el-tag size="large" style="font-size: large">帕萨特</el-tag>
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
          微型车
        </el-descriptions-item>
      </el-descriptions>
    </div>
  </div>
</template>

<script>
import { ref ,computed} from 'vue'
import {useStore} from "vuex";

export default {
  name: "Client",
  setup() {
    const centerDialogVisible1 = ref(false)
    const centerDialogVisible2 = ref(false)
    const centerDialogVisible3 = ref(false)
    const size = ref('')
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
      store
    }
  },
  data() {
    return {
      formVehicle: {
        vin: '',
        license: '',
        color: '',
        type: '',
        class: '',
        nature: '',
      },
      formClient: {
        name: '',
        nature: '',
        discount: '',
        contact: '',
        phone: '',
        client_id2: '',
      },
      formRepair: {
        vin:'',
        payment: '',
        approach_date: '',
        approach_time: '',
        failure: '',
        fuel: '',
        mile: '',
      }
    }
  },
  methods: {
    onSubmit() {
      this.centerDialogVisible1 = false
      // this.formVehicle = JSON.parse(JSON.stringify(this.defaultForm2))
    },
    onSubmit2() {
      this.centerDialogVisible2 = false

    },
    onSubmit3() {
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
    // created () {
    //   this.defaultForm = JSON.parse(JSON.stringify(this.formClient))
    //   this.defaultForm2 = JSON.parse(JSON.stringify(this.formVehicle))
    //   this.defaultForm3 = JSON.parse(JSON.stringify(this.formRepair))
    // }

  },
  computed:{
    getformclient(){
      return this.store.state.formClient
    }
  },
  created() {
    console.log(this.getformclient)
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