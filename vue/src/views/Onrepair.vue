<template>
  <div style="padding: 10px">
    <el-descriptions  column="3" size="large" border>
      <el-descriptions-item label="订单号" label-class-name="my-label" class-name="my-content">{{this.titleData.repairId}}</el-descriptions-item>
      <el-descriptions-item label="车牌号" label-class-name="my-label" class-name="my-content">{{this.titleData.license}}</el-descriptions-item>
      <el-descriptions-item label="车架号" label-class-name="my-label" class-name="my-content">{{this.titleData.vin}}</el-descriptions-item>
    </el-descriptions>
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%"
        size="large">
      <el-table-column prop="proId" label="维修项目编号" width="180" sortable />
      <el-table-column prop="pname" label="项目名称" width="180" />
      <el-table-column prop="hour" label="额定工时"  />
      <el-table-column prop="matIds" label="零件号"  >
        <template #default="scope" style="display: flex;">
          <div v-for="item in scope.row.matIds" :key="item" style="margin-left:0px;border-bottom-style:solid;border-bottom-color: #ebeef5;text-align: center">
            <span>{{item}}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="names" label="零件名"  >
        <template #default="scope" style="display: flex;">
            <div v-for="item in scope.row.names" :key="item" style="margin-left:0px;border-bottom-style:solid;border-bottom-color: #ebeef5;text-align: center">
              <span>{{item}}</span>
            </div>
        </template>
      </el-table-column>
      <el-table-column prop="numbers" label="零件数量"  >
      <template #default="scope" style="display: flex;">
        <div v-for="item in scope.row.numbers" :key="item" style="margin-left:0px;border-bottom-style:solid;border-bottom-color: #ebeef5;text-align: center">
          <span>{{item}}</span>
        </div>
      </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope" style="display: flex;">
          <el-button type="success" plain @click="handleEdit(scope.row)" style="font-size: medium;">添加零件</el-button>
          <el-button  plain @click="handleFinish(scope.row)" style="font-size: medium;margin-top: 10px;margin-left: 0px;background-color: #f5e2b5">完成维修</el-button>
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

      <el-dialog
          v-model="dialogVisible"
          title="添加零件"
          width="30%"
      >
        <el-form ref="formData" :model="form" label-width="120px">
<!--          <el-form-item label="零件编号" prop="clientID">-->
<!--            <el-input v-model="form.matId" placeholder="请输入客户编号" style="width: 70%"/>-->
<!--          </el-form-item>-->
          <el-form-item label="零件名称" prop="nature" style="width: 70%">
            <el-select v-model="form.name" class="m-2" placeholder="选择零件名称" style="width: 70%">
              <el-option
                  v-for="item in options"
                  :key="item"
                  :label="item"
                  :value="item"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="零件个数" prop="discount">
            <el-input type="number" v-model="form.number" placeholder="请输入零件个数" style="width: 70%"/>
          </el-form-item>
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

import request from "@/util/request";
import {useStore} from "vuex";
export default {
  name: 'Repairman',
  components: {

  },
  data() {
    return {
      matId:null,
      repairmanId:null,
      currentPage: 1,
      form: {},
      dialogVisible: false,
      search: '',
      pageSize:10,
      total: 10,
      options:[],
      tableData: [],
      titleData:{},
    }
  },
  setup(){
    const store = useStore()
    return{
      store,
    }
  },
  computed:{
    getuserform(){
      return this.store.state.form.userId
    }
  },
  created(){
    this.repairmanId=this.getuserform
    this.form.repairmanId=this.getuserform
    this.load()
  },
  methods:{
    load(){
      this.tableData=[]
      this.titleData={}
      request.get(`/user/repairman/ongoing/${this.repairmanId}/${this.currentPage}/${this.pageSize}`).then(res => {
        console.log(res)
        this.titleData=res[0].data
        this.form.repairId=this.titleData.repairId
        this.tableData = res[1].data.list//数组类的数据
        this.total=res[1].data.total
      })
    },
    add(){
      this.dialogVisible = true;
      this.form = {}
    },
    save() {
      this.form.number=parseInt(this.form.number)
      console.log(this.form)
      request.post("/user/repairman/ongoing/addMaterial", this.form).then(res => {
        console.log(res)
        if (res.code === 454) {
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
      this.form.maintain=row
      request.get("/user/repairman/ongoing/getMaterial").then(res => {
        console.log(res)
        this.options=res.data
      })
      this.dialogVisible = true
    },
    handleFinish(row){
      row.repairmanId=this.repairmanId
      row.repairId=this.form.repairId
      console.log(row)
      request.post("/user/repairman/ongoing/finishMaintain", row).then(res => {
        console.log(res)
        if (res.code === 457) {
          this.$message({
            type: "success",
            message: res.msg
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
      // sleep(1000)
      this.load()
      console.log("完成后的数据",this.tableData)
    },
    handleSizeChange(pageSize){ //改变当前每页个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){  //改变当前页码触发
      this.currentPage = pageNum
      this.load()
    }
  },
}
</script>
<style>
.my-label {
  background: #c6f1e8 !important;
  color: black !important;
  font-size: large;
}
.my-content {
  background: #c6f1e8;
  color: black;
  font-size: large;
}
</style>
