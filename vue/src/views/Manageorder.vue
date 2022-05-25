<template>
  <div style="padding: 10px">
    <!--    功能区-->
<!--    <div style="margin:10px 0">-->
<!--      <el-button type="primary" @click="add">新增</el-button>-->
<!--      <el-button type="primary">导入</el-button>-->
<!--      <el-button type="primary">导出</el-button>-->
<!--    </div>-->
    <!--    搜索区-->
    <div style="margin:10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%"
        size="large"
        >
      <el-table-column prop="repair_id" label="订单号"  sortable />
      <el-table-column prop="license" label="车牌号"  />
      <el-table-column prop="vin" label="车架号"  />
      <el-table-column prop="category" label="车型"  />
      <el-table-column prop="failure" label="粗略故障描述"  />
      <el-table-column prop="state" fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button type="warning"  v-if="scope.row.state" @click="takeorder(scope.row)">接单</el-button>
          <el-button type="primary" v-else @click="handleEdit(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
      <el-table-column  fixed="right" label="维修委托书" width="120">
        <template #default="scope">
          <el-button type="success" :disabled="scope.row.state?true:false" @click="handleEdit(scope.row)">生成</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="crrentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          :pager-count="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
      >
      </el-pagination>
      <el-dialog v-model="dialogVisible" title="编辑订单" width="60%">
        <el-form ref="formData" :model="form" label-width="120px">
          <div style="display: flex">
            <div>
              <el-form-item label="车架号" prop="vin">
                <el-input v-model="form.vin" placeholder="请输入车架号" style="width: 50%"/>
              </el-form-item>
              <el-form-item label="结算方式" prop="resource">
                <el-radio-group v-model="form.resource">
                  <el-radio label="自付" />
                  <el-radio label="索赔" />
                  <el-radio label="三包" />
                </el-radio-group>
              </el-form-item>
              <el-form-item label="进厂时间" prop="approach_date" >
                <el-date-picker
                    v-model="form.approach_time"
                    type="datetime"
                    placeholder="Select date and time"
                />
              </el-form-item>
              <el-form-item label="进厂油量" prop="fuel">
                <el-input v-model="form.fuel" placeholder="进厂时车辆所剩油量" style="width: 50%"/><span>%</span>
              </el-form-item>
              <el-form-item label="进厂里程" prop="mile">
                <el-input v-model="form.mile" placeholder="进厂时车辆里程数" style="width: 50%"/><span>km</span>
              </el-form-item>
            </div>
            <div>
              <el-form-item label="维修类型" prop="resource">
                <el-radio-group v-model="form.type">
                  <el-radio label="加急" />
                  <el-radio label="普通" />
                </el-radio-group>
              </el-form-item>
              <el-form-item label="作业分类" prop="classification">
                <el-radio-group v-model="form.type">
                  <el-radio label="小型" />
                  <el-radio label="中型" />
                  <el-radio label="大型" />
                </el-radio-group>
              </el-form-item>
              <el-form-item label="详细故障" prop="failure" >
                <el-input v-model="form.failure" type="textarea" placeholder="请详细描述您车辆的故障（字数在255以下）" />
              </el-form-item>
              <el-form-item label="出厂时间" prop="leave_time" >
                <el-date-picker
                    v-model="form.leave_time"
                    type="datetime"
                    placeholder="Select date and time"
                />
              </el-form-item>
            </div>
          </div>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="save">确定</el-button>
          </span>
        </template>
    </el-dialog>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src

import request from "@/util/request";

export default {
  name: 'Manageorder',
  components: {

  },
  data() {
    return {
      crrentPage: 1,
      form: {},
      dialogVisible: false,
      buttonvisible: true,
      search: '',
      currentPage: 1,
      pageSize:10,
      total: 10,
      tableData: [
        {
          repair_id:1,
          license:'沪A88888',
          vin:'12342131',
          category:'帕萨特',
          failure:'发动机故障灯亮',
          state:true//待接单
        },
        {
          repair_id:2,
          license:'沪A88288',
          vin:'12322131',
          category:'QQ',
          failure:'新赛车，来个属性强化',
          state:false
        },
        {
          repair_id:3,
          license:'沪A83888',
          vin:'12342144',
          category:'奥迪',
          failure:'添加空调氟利昂',
          state: false
        },
      ],
    }
  },
  setup(){
    // const dialogTableVisible=this.$refs['formData'].resetFields(); //重置表单数据，清除校验信息
    // return{
    //       dialogTableVisible,
    // }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      request.get(`/user/showallclients/ongoing/${this.currentPage}/${this.pageSize}/${this.search}`).then(res => {
        console.log(res)
        this.tableData = res.data.records//数组类的数据
        this.total = res.data.total//总条数
      })
    },
    add(){
      this.dialogVisible = true;
      this.form = {}
    },
    save() {
      if (this.form.id) {
        request.put("/api/user", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      } else {  //新增
        request.post("/api/user", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      }
      this.load()
      this.dialogVisible = false
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      //console.log("Edit Click!")
      // this.$refs['formData'].resetFields();
      this.dialogVisible = true
    },
    // handleDelete(id){
    //   request.delete("/api/user/" + id).then(res => {
    //     if (res.code === '0'){
    //       this.$message({
    //         type: "success",
    //         message: "删除成功"
    //       })
    //     }else{
    //       this.$message({
    //         type: "error",
    //         message: res.msg
    //       })
    //     }
    //     this.load() //重新加载表格数据
    //   })
    // },
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
          this.tableData[i].state=false
          break
        }
      }
    }
  },
}
</script>
<style>

</style>