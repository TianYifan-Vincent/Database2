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
      <el-table-column prop="client_id" label="客户编号" width="180" sortable />
      <el-table-column prop="name" label="客户名称" width="180" />
      <el-table-column prop="nature" label="客户性质"  />
      <el-table-column prop="discount" label="折扣率"  />
      <el-table-column prop="contact" label="联系人"  />
      <el-table-column prop="phone" label="联系电话"  />
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
<!--          <el-button type="text" size="small" @click="handleDelete(scope.row)">删除</el-button>-->
<!--          <el-popconfirm-->
<!--              confirm-button-text="确认"-->
<!--              cancel-button-text="不，谢谢"-->
<!--              :icon="InfoFilled"-->
<!--              icon-color="red"-->
<!--              title="这一段内容确定要删除吗？"-->
<!--              @confirm="handleDelete(scope.row.id)"-->
<!--          >-->
<!--            <template #reference>-->
<!--              <el-button type="text">删除</el-button>-->
<!--            </template>-->
<!--          </el-popconfirm>-->
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

      <el-dialog
          v-model="dialogVisible"
          title="基本信息"
          width="30%"
      >
        <el-form ref="formData" :model="form" label-width="120px">
            <el-form-item label="客户编号" prop="clientID">
              <el-input v-model="form.client_id" placeholder="请输入客户编号" style="width: 70%"/>
            </el-form-item>
            <el-form-item label="客户名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入客户名称" style="width: 70%"/>
            </el-form-item>
            <el-form-item label="客户性质" prop="nature">
              <el-select v-model="form.nature" placeholder="请输入客户性质" style="width: 70%">
                <el-option label="个人" value="个人" />
                <el-option label="单位" value="单位" />
              </el-select>
            </el-form-item>
            <el-form-item label="折扣率" prop="discount">
              <el-input v-model="form.discount" placeholder="请输入折扣率" style="width: 70%"/>
            </el-form-item>
            <el-form-item label="联系人" prop="contact">
              <el-input v-model="form.contact" placeholder="请输入联系人" style="width: 70%"/>
            </el-form-item>
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" style="width: 70%"/>
            </el-form-item>
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
  name: 'Home',
  components: {

  },
  data() {
    return {
      crrentPage: 1,
      form: {},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize:10,
      total: 10,
      tableData: [
        {
          client_id:'1',
          name:'皮皮',
          nature:'个人',
          discount:0.9,
          contact:'闪光皮皮',
          phone:'19382761717',
        },
        {
          client_id:'2',
          name:'皮皮1',
          nature:'个人1',
          discount:0.8,
          contact:'闪光皮皮1',
          phone:'19382761',
        },
        {
          client_id:'3',
          name:'皮',
          nature:'个',
          discount:0.7,
          contact:'闪光',
          phone:'1938717',
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
    }
  },
}
</script>
