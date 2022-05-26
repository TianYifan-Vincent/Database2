<template>
  <div style="padding: 10px">
    <el-descriptions  column="3" size="large" border>
      <el-descriptions-item label="订单号" label-class-name="my-label" class-name="my-content">kooriookami</el-descriptions-item>
      <el-descriptions-item label="车牌号" label-class-name="my-label" class-name="my-content">18100000000</el-descriptions-item>
      <el-descriptions-item label="车架号" label-class-name="my-label" class-name="my-content">Suzhou</el-descriptions-item>
    </el-descriptions>
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%"
        size="large">
      <el-table-column prop="client_id" label="维修项目编号" width="180" sortable />
      <el-table-column prop="name" label="项目名称" width="180" />
      <el-table-column prop="nature" label="额定工时"  />
      <el-table-column prop="discount" label="零件号"  >
        <template #default="scope" style="display: flex;">
          <div v-for="item in scope.row.discount" :key="item" style="margin-left:0px;border-bottom-style:solid;border-bottom-color: #ebeef5;text-align: center">
            <span>{{item}}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="contact" label="零件名"  >
        <template #default="scope" style="display: flex;">
            <div v-for="item in scope.row.contact" :key="item" style="margin-left:0px;border-bottom-style:solid;border-bottom-color: #ebeef5;text-align: center">
              <span>{{item}}</span>
            </div>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="零件数量"  />
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope" style="display: flex;">
          <el-button type="success" plain @click="handleEdit(scope.row)" style="font-size: medium;">添加零件</el-button>
          <el-button  plain @click="handleEdit(scope.row)" style="font-size: medium;margin-top: 10px;margin-left: 0px;background-color: #f5e2b5">完成维修</el-button>
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
  name: 'Repairman',
  components: {

  },
  data() {
    return {
      currentPage: 1,
      form: {},
      dialogVisible: false,
      search: '',
      pageSize:10,
      total: 10,
      tableData: [
        {
          client_id:'1',
          name:'皮皮',
          nature:'个人',
          discount:[1,2,3],
          contact:['前制动盘','制动液','轮胎'],
          phone:'19382761717',
        },
        {
          client_id:'2',
          name:'皮皮1',
          nature:'个人1',
          discount:0.8,
          contact:['1','2','3'],
          phone:'19382761',
        },
        {
          client_id:'3',
          name:'皮',
          nature:'个',
          discount:0.7,
          contact:['1','2','3'],
          phone:'1938717',
        },
      ],
    }
  },
  setup(){
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
