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
        size="large">
      <el-table-column prop="repairId" label="订单号" width="180" sortable />
      <el-table-column prop="license" label="车牌号" width="180" />
      <el-table-column prop="vin" label="车架号"  />
      <el-table-column prop="proId" label="维修项目编号"  />
      <el-table-column prop="pname" label="维修项目名"  />
      <el-table-column prop="failure" label="具体问题"  />
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
    </div>
  </div>
</template>

<script>
// @ is an alias to /src

import request from "@/util/request";
import {useStore} from "vuex";
export default {
  name: 'Repairfinished',
  components: {

  },
  data() {
    return {
      userId:null,
      currentPage: 1,
      form: {},
      dialogVisible: false,
      search: null,
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
    load(){
      request.get(`/user/repairman/finished/${this.userId}/${this.currentPage}/${this.pageSize}/${this.search}`).then(res => {
        console.log(res)
        this.tableData = res.data.list//数组类的数据
        this.total = res.data.total//总条数
      })
    },
    add(){
      this.dialogVisible = true;
      this.form = {}
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
