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
      <el-table-column prop="proid" label="项目号"  sortable  />
      <el-table-column prop="proname" label="项目名"  />
      <el-table-column prop="hour" label="工时"  />
      <el-table-column prop="type" label="工种"  />
<!--      <el-table-column prop="number" label="维修员编号"  />-->
      <el-table-column prop="name" label="维修员姓名"  />
      <el-table-column prop="status" label="维修状态"  />

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
      <div style="text-align: center" >
        <el-button type="success" round style="width: 100px;height: 30px;font-size: medium" @click="submit">提交</el-button>
      </div>
      <el-dialog
          v-model="dialogVisible"
          title="选择订单编号"
          width="30%"
      >
        <el-select v-model="value" class="m-2" placeholder="选择订单编号" size="large">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
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
import { ref } from 'vue'
export default {
  name: 'Workroder',
  components: {

  },
  setup(){
    const value = ref('')
    return{
      value
        }
  },
  data() {
    return {
      checked1:false,
      crrentPage: 1,
      form: {},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize:10,
      total: 10,
      multipleSelection: [],
      options:[
        {
          value: 'Option1',
          label: 'Option1',
        },
        {
          value: 'Option2',
          label: 'Option2',
        },
        {
          value: 'Option3',
          label: 'Option3',
        },
        {
          value: 'Option4',
          label: 'Option4',
        },
        {
          value: 'Option5',
          label: 'Option5',
        },],
      tableData: [
        {
          proid:'1',
          proname:'检查',
          hour:0.5,
          type:'电工',
          number:'123',
          name:'tyf',
          status:'空闲中'
        },
        {
          proid:'2',
          proname:'检查',
          hour:1,
          type:'机修工',
          number:'12',
          name:'lml',
          status:'空闲中'
        },
        {
          proid:'3',
          proname:'检查',
          hour:2,
          type:'机修工',
          number:'321',
          name:'xml',
          status:'空闲中'
        },
      ],
    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      request.get(`/user/showallclients/${this.currentPage}/${this.pageSize}/${this.search}`).then(res => {
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
      let arr = this.$refs['multipleTable']
      // console.log('arr', arr)
      console.log(this.multipleSelection)
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