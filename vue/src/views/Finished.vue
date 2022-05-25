<template>
  <div style="padding: 10px">
    <!--    搜索区-->
    <div style="margin:10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%"></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="select">查询</el-button>
    </div>
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%"
        size="large">
      <!--      <el-table-column prop="repair_id" label="订单号"  sortable />-->
      <el-table-column prop="license" label="车牌号"  />
      <el-table-column prop="vin" label="车架号"  />
      <el-table-column prop="category" label="车型"  />
      <el-table-column prop="failure" label="故障描述"  />
      <el-table-column prop="approachTime" label="进厂时间"  />
      <el-table-column prop="finish_time" label="实际完成时间"  />
      <el-table-column  fixed="right" label="维修委托书">
        <template #default="scope">
          <el-button type="success"  @click="handleEdit(scope.row)">生成</el-button>
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
    </div>
  </div>
</template>

<script>
// @ is an alias to /src

import request from "@/util/request";
import {useStore} from "vuex";
export default {
  name: 'Finished',
  components: {

  },
  data() {
    return {
      clientId:null,
      crrentPage: 1,
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
      store,
    }
  },
  computed:{
    getclientform(){
      return this.store.state.form.clientId
    }
  },
  created(){
    this.clientId=this.getclientform
    this.load()
  },
  watch:{
    // 侦听器本质上是一个函数，要监视哪个数据的变化，就把数据名作为方法名即可
    // 新值在前，旧值在后
    search(newVal) {
      if (newVal === null)
        return
      this.load()
    }
  },
  methods:{
    select(){
      this.load()
    },
    load(){
      request.get(`/client/finished/${this.clientId}/${this.currentPage}/${this.pageSize}/${this.search}`).then(res => {
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
    handleSizeChange(pageSize){ //改变当前每页个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){  //改变当前页码触发
      this.currentPage = pageNum
      this.load()
    },
  },
}
</script>
