<template>
  <div ref="pdfDom" style="padding: 10px">
    <!--    功能区-->
<!--    <div style="margin:10px 0">-->
<!--      <el-button type="primary" @click="add">新增</el-button>-->
<!--      <el-button type="primary">导入</el-button>-->
<!--      <el-button type="primary">导出</el-button>-->
<!--    </div>-->
    <!--    搜索区-->
    <div  style="margin:10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" ></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="select">查询</el-button>
    </div>
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%"
        size="large"
        >
      <el-table-column prop="repairId" label="订单号"  sortable />
      <el-table-column prop="license" label="车牌号"  />
      <el-table-column prop="vin" label="车架号"  />
      <el-table-column prop="category" label="车型"  />
      <el-table-column prop="failure" label="粗略故障描述"  />
      <el-table-column prop="statu" label="状态" style="color: aquamarine" width="80">
        <template #default="scope">
            {{scope.row.statu}}
        </template>
      </el-table-column>
      <el-table-column prop="statu" fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button type="warning" size="medium" plain v-if="scope.row.statu==='未接单'" @click="takeorder(scope.row)">接单</el-button>
          <el-button type="primary" size="medium" plain v-else @click="handleEdit(scope.row)" :disabled="scope.row.statu==='已完成'?true:false">编辑</el-button>
        </template>
      </el-table-column>
      <el-table-column  fixed="right" label="维修委托书" width="120">
        <template #default="scope">
          <el-button type="success" size="medium" plain :disabled="scope.row.statu==='未接单'?true:false" @click="submitExport(scope.row)">生成</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          :pager-count="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
      >
      </el-pagination>
      <el-dialog  v-model="dialogVisible2" title="维修委托书" center style="font-size: large;">
        <el-descriptions  column="2" size="medium"  >
          <el-descriptions-item label="订单号" label-class-name="my-label" class-name="my-content" style="display:flex;justify-content: flex-start;">{{this.attorney1.repairId}}</el-descriptions-item>
          <el-descriptions-item label="登记日期" label-class-name="my-label2" class-name="my-content2" style="display:flex;flex:1;justify-content: flex-end">{{this.attorney1.approachTime}}</el-descriptions-item>
        </el-descriptions>
        <el-descriptions  column="3" size="medium" border >
          <el-descriptions-item label="车牌号" label-class-name="my-label5" class-name="my-content5" >
            {{ this.attorney1.license }}</el-descriptions-item>
          <el-descriptions-item label="客户编号" label-class-name="my-label5" class-name="my-content5">
            {{ this.attorney1.clientId }}</el-descriptions-item>
          <el-descriptions-item label="维修类型" label-class-name="my-label5" class-name="my-content5">
            {{ this.attorney1.repairClassification }}</el-descriptions-item>
        </el-descriptions>
        <el-descriptions  column="3" size="medium" border>
          <el-descriptions-item label="作业分类" label-class-name="my-label5" class-name="my-content5" >{{ this.attorney1.repairClassification }}</el-descriptions-item>
          <el-descriptions-item label="结算方式" label-class-name="my-label5" class-name="my-content5">{{this.attorney1.payment}}</el-descriptions-item>
          <el-descriptions-item label="车架号" label-class-name="my-label5" class-name="my-content5">{{this.attorney1.vin}}</el-descriptions-item>
        </el-descriptions>
        <el-descriptions  column="3" size="medium" border>
          <el-descriptions-item label="进厂里程数" label-class-name="my-label5" class-name="my-content5" >{{this.attorney1.mile}}</el-descriptions-item>
          <el-descriptions-item label="进厂油量" label-class-name="my-label5" class-name="my-content5" >{{this.attorney1.fuel}}%</el-descriptions-item>
          <el-descriptions-item label="进厂时间" label-class-name="my-label5" class-name="my-content5">{{this.attorney1.approachTime}}</el-descriptions-item>
        </el-descriptions>
        <el-descriptions  column="3" size="medium" border>
          <el-descriptions-item label="业务员" label-class-name="my-label5" class-name="my-content5">{{this.attorney1.realname}}</el-descriptions-item>
          <el-descriptions-item label="业务员编号" label-class-name="my-label5" class-name="my-content5">{{this.attorney1.userId}}</el-descriptions-item>
          <el-descriptions-item label="预计完工时间" label-class-name="my-label5" class-name="my-content5">{{this.attorney1.ddl}}</el-descriptions-item>
        </el-descriptions>
        <el-descriptions  column="1" size="medium" border>
          <el-descriptions-item label="故障描述" label-class-name="my-label3" class-name="my-content4"></el-descriptions-item>
        </el-descriptions>
        <el-descriptions  column="1" size="medium" border>
          <el-descriptions-item  label="" label-class-name="my-label4" class-name="my-content3">{{this.attorney1.failure}}</el-descriptions-item>
        </el-descriptions>
        <div v-if="this.attorney2===[]">暂无维修信息</div>
        <div v-else>
          <el-table
              :data="this.attorney2"
              border
              stripe
              size="large"
          >
            <el-table-column prop="proId" label="项目编号"  sortable />
            <el-table-column prop="pname" label="项目名称"  />
            <el-table-column prop="hour" label="工时"  />
            <el-table-column prop="name" label="零件名"  />
            <el-table-column prop="number" label="数量"  />
            <el-table-column prop="cost" label="单价"  />
          </el-table>
          <div v-if="this.attorney1.hour===0">暂无计价信息</div>
          <div v-else>
            <el-descriptions  column="2" size="medium" border>
              <el-descriptions-item label="总工时" label-class-name="my-label5" class-name="my-content5">{{this.attorney1.hour}}</el-descriptions-item>
              <el-descriptions-item label="总计" label-class-name="my-label5" class-name="my-content5">{{this.attorney1.cost}}</el-descriptions-item>
            </el-descriptions>
            <el-descriptions  column="1" size="medium" border>
              <el-descriptions-item label="维修总价" label-class-name="my-label5" class-name="my-content5">{{this.repairtotal}}</el-descriptions-item>
            </el-descriptions>
            <el-descriptions  column="1" size="medium" border>
              <el-descriptions-item label="计价方式" label-class-name="my-label5" class-name="my-content5">(总工时*工时单价(24)+材料费)*折扣率</el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="handleExport">确定</el-button>
            <el-button @click="dialogVisible2 = false">取消</el-button>
          </span>
        </template>
      </el-dialog>

      <el-dialog v-model="dialogVisible" title="编辑订单" width="60%">
        <el-form ref="formData" :model="form" label-width="120px">
          <div style="display: flex">
            <div>
              <el-form-item label="结算方式" prop="payment">
                <el-radio-group v-model="form.payment">
                  <el-radio label="自付" />
                  <el-radio label="索赔" />
                  <el-radio label="三包" />
                </el-radio-group>
              </el-form-item>
              <el-form-item label="进厂时间" prop="approach_date" >
                <el-date-picker
                    v-model="form.approachTime"
                    type="datetime"
                    placeholder="Select date and time"
                />
              </el-form-item>
              <el-form-item label="进厂油量" prop="fuel">
                <el-input type="number" v-model="form.fuel" placeholder="进厂时车辆所剩油量" style="width: 70%"/><span>%</span>
              </el-form-item>
              <el-form-item label="进厂里程" prop="mile">
                <el-input type="number" v-model="form.mile" placeholder="进厂时车辆里程数" style="width: 70%"/><span>km</span>
              </el-form-item>
            </div>
            <div>
              <el-form-item label="维修类型" prop="type">
                <el-radio-group v-model="form.type">
                  <el-radio label="加急" />
                  <el-radio label="普通" />
                </el-radio-group>
              </el-form-item>
              <el-form-item label="作业分类" prop="classification">
                <el-radio-group v-model="form.classification">
                  <el-radio label="小修" />
                  <el-radio label="中修" />
                  <el-radio label="大修" />
                </el-radio-group>
              </el-form-item>
              <el-form-item label="详细故障" prop="failure" >
                <el-input v-model="form.failure" type="textarea" placeholder="请详细描述您车辆的故障（字数在255以下）" maxlength="255" rows="4" clearable size="large"/>
              </el-form-item>
              <el-form-item label="预计出厂时间" prop="ddl" >
                <el-date-picker
                    v-model="form.ddl"
                    type="datetime"
                    placeholder="Select date and time"
                />
              </el-form-item>
            </div>
          </div>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="save">确定</el-button>
            <el-button @click="dialogVisible = false">取消</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
// import {downloadPDF} from "@/util/pdf.js"  //工具方法，导出操作
import request from "@/util/request";
import {useStore} from "vuex";
import {ref} from "vue";
import {getPdf} from "@/util/pdf";
export default {
  name: 'Manageorder',
  components: {

  },
  data() {
    return {
      // repairtotal:0,
      totalsum:0,
      attorney1:{},
      attorney2:[],
      userId:null,
      form: {},
      dialogVisible: false,
      dialogVisible2: false,
      buttonvisible: true,
      search: null,
      currentPage: 1,
      pageSize:10,
      total: 10,
      tableData: [],
      tableData2: [],
    }
  },
  setup(){
    const store = useStore()
    const pdfDom =ref()
    return{
      store,
      pdfDom
    }
  },
  computed:{
    repairtotal(){
      return `(${this.attorney1.hour}*24+${this.totalsum})*${this.attorney1.discount}%=${this.attorney1.cost}`
    },
    getuserform(){
      return this.store.state.form.userId
    }
  },
  created(){
    this.userId=this.getuserform
    this.load()
  },
  watch:{
    // 侦听器本质上是一个函数，要监视哪个数据的变化，就把数据名作为方法名即可
    // 新值在前，旧值在后
    search(newVal) {
      if (newVal === ''){
        this.search=null
      }
      this.load()
    }
  },
  methods:{
    select(){
      this.load()
    },
    load(){
      request.get(`/user/show/repair/${this.currentPage}/${this.pageSize}/${this.search}`).then(res => {
        // console.log(res)
        this.tableData = res.data.list//数组类的数据
        this.total = res.data.total//总条数
      })
    },
    add(){
      this.dialogVisible = true;
      this.form = {}
    },
    save() {
      // console.log(this.userId)
      // console.log(this.form)
      request.put(`/user/repair/${this.userId}`, this.form).then(res => {
        // console.log(res)
        if (res.code === 457) {
          this.$message({
            type: "success",
            message: "编辑成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
      this.load()
      this.dialogVisible = false
    },
    handleEdit(row){
      // console.log(row)
      this.form=row
      this.dialogVisible = true
    },
    handleExport(row){
      getPdf(this.pdfDom)
    },
    submitExport(row){
      console.log(row)
      request.get(`/user/getAttorney/${row.vin}/${row.failure}`).then(res => {
        console.log(res)
        this.attorney1=res[0].data
        this.attorney2=res[1].data
        for(const item of this.attorney2){
          this.totalsum+=item.cost
        }

      })
      this.dialogVisible2=true
  },
    handleSizeChange(pageSize){ //改变当前每页个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){  //改变当前页码触发
      this.currentPage = pageNum
      this.load()
    },
    takeorder(row){
      for (let i=0;i<this.tableData.length;i++){
        if(row===this.tableData[i]){
          this.tableData[i].statu='等待派单'
          break
        }
      }
    }
  },
}
</script>
<style>
.my-label {
  color: black !important;
  font-size:  medium;
}
.my-content {
  color: black;
  font-size: medium;
}
.my-label2 {
  justify-content: flex-end !important;
  color: black !important;
  font-size:  medium;
}
.my-content2 {
  color: black;
  font-size: medium;
}
.my-label3 {
  display: flex;
  justify-content: center;
}
.my-content3 {
  display: flex;
  justify-content: center;
}
.my-label4 {
  width: 0;
}
.my-content4 {
  width: 0;
}
.my-label5 {
  width: 150px;
}
.my-content5 {
  width: 150px;
}
</style>