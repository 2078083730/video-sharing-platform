<script setup lang="ts">
import {onMounted, ref} from "vue";
import {changeVideoStatus, getVideoList} from "@/api/video";
import {ElMessage} from "element-plus";

const path = ref('http://localhost:9090')
const title = ref('')
const tableData = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const getVideoList1 = (title:any)=>{
  getVideoList(title,pageNum.value,pageSize.value).then(res=>{
    tableData.value = res.data.data.rows
    total.value = res.data.data.total
    pageNum.value = res.data.data.pageNum
    pageSize.value = res.data.data.pageSize
  })
}
const handleSizeChange = (val: number) => {
  pageSize.value = val
  getVideoList1(title.value)
}
const handleCurrentChange = (val: number) => {
  pageNum.value = val
  getVideoList1(title.value)
}
onMounted(()=>{
  getVideoList1('')
})
const dialogTableVisible = ref(false)
const showVideoUrl = ref('')
const showVideo = (basePath:any)=>{
  dialogTableVisible.value = true
  showVideoUrl.value = path.value + basePath
}
const showDialogTableVisible = ref(false)
const shenheForm = ref({})
const handleEdit = (item:any)=>{
  showDialogTableVisible.value = true
  shenheForm.value = {...item}
  console.log(shenheForm.value)
}
const changeStatus = (status:any,videoId:any)=>{
  changeVideoStatus(status,videoId).then(res=>{
    if(res.data.code == 200){
      showDialogTableVisible.value = false
      getVideoList1('')
      ElMessage.success('操作成功')
    }
  })
}
</script>

<template>
<div class="manager">
  <div style="display: flex;align-items: center;width: 400px;margin: 20px 0">
    <el-input v-model="title" size="large" placeholder="请输入要搜索的视频"></el-input>
    <el-button @click="getVideoList1(title)" style="height: 40px;margin-left: 10px" type="primary">搜 索
    </el-button>
  </div>
  <el-table :data="tableData" border style="width: 100%">
    <el-table-column prop="pic" label="视频封面" width="180" >
      <template #default="scoped">
        <img :src="path + scoped.row.pic" style="width: 60px;height: 60px;border-radius: 10px" >
      </template>
    </el-table-column>
    <el-table-column prop="username" label="发布者"  />
    <el-table-column prop="title" label="视频标题" width="180" />
    <el-table-column prop="languageName" label="视频种类" />
    <el-table-column  label="视频资源" width="180" >
      <template #default="scoped">
        <el-button @click="showVideo(scoped.row.basePath)">点击查看视频</el-button>
      </template>
    </el-table-column>
    <el-table-column  label="视频状态" width="180" >
      <template #default="scoped">
         <el-tag :type="scoped.row.status == 0?'info':scoped.row.status == 1?'warning':'success'">{{scoped.row.status == 0?'未审核':scoped.row.status == 1?'未通过':'已通过'}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.row)">
          审核视频
        </el-button>

      </template>
    </el-table-column>
  </el-table>
  <div style="margin-top: 40px">
    <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[10, 15, 20, 25]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>
</div>
  <el-dialog v-model="dialogTableVisible" title="视频查看" width="800" @close="showVideoUrl = ''">
      <video style="height: 600px;width: 100%" :src="showVideoUrl" autoplay controls></video>
  </el-dialog>
  <el-dialog v-model="showDialogTableVisible" title="视频审核" width="800" @close="shenheForm.basePath  = ''">
    <img v-if="shenheForm.status == 2" style="position: absolute;top: 40px;right: 10px" width="80px" src="../assets/已通过.png" >
    <img v-if="shenheForm.status == 1" style="position: absolute;top: 40px;right: 10px" width="100px" src="../assets/不通过.png" >
    <video style="height: 500px;width: 100%" :src="path + shenheForm.basePath" autoplay controls></video>
    <div style="margin: 10px 0;font-size: 18px;font-weight: 600">【{{shenheForm.title}}】</div>
    <div style="display: flex;align-items: center;margin: 10px 0">
      <img style="width: 50px;height: 50px;border-radius: 50%" :src="path + shenheForm.avatar" alt="">
      <div style="margin-left: 10px">{{shenheForm.username}}</div>
    </div>
    <div style="margin: 20px 0;display: flex;justify-content: flex-end" v-show="shenheForm.status == 0">
      <el-button size="large" type="warning" @click="changeStatus(1,shenheForm.id)">不通过</el-button>
      <el-button size="large" type="success" @click="changeStatus(2,shenheForm.id)">通过</el-button>
    </div>
  </el-dialog>
</template>

<style scoped>

</style>