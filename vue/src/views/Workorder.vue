<template>
  <div style="padding: 10px">
    <div style="margin:10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table
        :data="tableData"
        border
        stripe
        highlight-current-row
        style="width: 100%"
        size="large"
        ref="multipleTable"
        @selection-change="handleSelectionChange"
        >
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="proId" label="项目号"  sortable  />
      <el-table-column prop="pname" label="项目名"  />
      <el-table-column prop="hour" label="工时"  />
      <el-table-column prop="job" label="工种"  />
<!--      <el-table-column prop="repairmanId" label="维修员编号"  />-->
      <el-table-column prop="realname" label="维修员姓名"  />
      <el-table-column prop="statu" label="维修状态"  />

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
      <div style="text-align: center" >
        <el-button type="success" round style="width: 100px;height: 30px;font-size: medium" @click="submit">提交</el-button>
      </div>
      <el-dialog
          v-model="dialogVisible"
          title="选择订单编号"
          width="30%"
      >
        <el-select v-model="optionvalue" class="m-2" placeholder="选择订单编号" size="large">
          <el-option
              v-for="item in options"
              :key="item"
              :label="item"
              :value="item"
          />
        </el-select>
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
import { ref } from 'vue';
import {useStore} from "vuex";
export default {
  name: 'Workroder',
  components: {

  },
  setup(){
    const store = useStore()
    const value = ref('')
    return{
      value,
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
        this.search=null
      }
      this.load()
    }
  },
  data() {
    return {
      userId:null,
      optionvalue:null,//选择的订单编号
      checked1:false,
      form: {},
      optionform:{},
      dialogVisible: false,
      search: null,
      currentPage: 1,
      pageSize:10,
      total: 10,
      multipleSelection: [],
      options:[],
      tableData: [],
    }
  },
  methods:{
    load(){
      request.get(`/user/show/repairman/${this.currentPage}/${this.pageSize}/${this.search}`).then(res => {
        console.log(res)
        this.tableData = res[0].data.list//数组类的数据
        this.total = res[0].data.total//总条数
        this.options=res[1].data//所有可派工订单
      })
    },
    add(){
      this.dialogVisible = true;
      this.form = {}
    },
    save() {
      this.optionform.repairId=this.optionvalue
      console.log(this.optionform)
      request.post("/user/userSubmit", this.optionform).then(res => {
        console.log(res)
        if (res.code === 505) {
          this.$message({
            type: "success",
            message: "派工成功"
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
      this.form = JSON.parse(JSON.stringify(row))
      //console.log("Edit Click!")
      // this.$refs['formData'].resetFields();
      // this.$refs.table.style.backgroundColor='#d9edf7'
    },
    handleSizeChange(pageSize){ //改变当前每页个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){  //改变当前页码触发
      this.currentPage = pageNum
      this.load()
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    submit(){
      console.log(this.multipleSelection)
      this.optionform.list=this.multipleSelection
      this.optionform.userId=this.userId
      this.dialogVisible=true
    }
  },
}
</script>
<style>
/* 选中某行时的背景色*/
.el-table__body tr.current-row > td {
  background-color: #d9edf7 !important;
}

/*鼠标移入某行时的背景色*/
/*.el-table--enable-row-hover .el-table__body tr:hover > td {*/
/*  background-color: yellow;*/
/*}*/
</style>