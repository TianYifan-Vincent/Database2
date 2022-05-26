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
          <el-button type="success" size="medium" plain :disabled="scope.row.statu==='未接单'?true:false" @click="handleEdit(scope.row)">生成</el-button>
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
      <el-dialog v-model="dialogVisible" title="编辑订单" width="60%">
        <el-form ref="formData" :model="form" label-width="120px">
          <div style="display: flex">
            <div>
              <el-form-item label="结算方式" prop="resource">
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
              <el-form-item label="维修类型" prop="resource">
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
              <el-form-item label="预计出厂时间" prop="leave_time" >
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
import {useStore} from "vuex";
export default {
  name: 'Manageorder',
  components: {

  },
  data() {
    return {
      userId:null,
      form: {},
      dialogVisible: false,
      buttonvisible: true,
      search: null,
      currentPage: 1,
      pageSize:10,
      total: 10,
      tableData: [],
    }
  },
  setup(){
    const store = useStore()
    return{
      store
    }
  },
  computed:{
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
        return
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
        console.log(res)
        this.tableData = res.data.list//数组类的数据
        this.total = res.data.total//总条数
      })
    },
    add(){
      this.dialogVisible = true;
      this.form = {}
    },
    save() {
      console.log(this.userId)
      console.log(this.form)
      request.put(`/user/repair/${this.userId}`, this.form).then(res => {
        console.log(res)
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
      this.form.repairId=row.repairId
      this.dialogVisible = true
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

</style>