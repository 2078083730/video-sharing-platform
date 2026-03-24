<script setup lang="ts">
import {onMounted, ref} from 'vue'
import {getVideos, getDict} from "@/api/video";
import {useRouter} from "vue-router";
const router = useRouter()
const radio = ref(1)
const title = ref('')
const videoList = ref([])
const dictList = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const user = ref({})
onMounted(() => {
  user.value = JSON.parse(sessionStorage.getItem("user") || '{}')
  getDict('language_type').then(res => {
    dictList.value = res.data.data
  })
  getVideoList(radio.value, title.value)
})
const queryVideo = (val: any) => {
  getVideoList(val, title.value)
}
const getVideoList = (radio: any, title: any) => {
  getVideos(radio, title,pageNum.value,pageSize.value,null,user.value.id).then(res => {
    videoList.value = res.data.data.rows
    total.value = res.data.data.total
    pageNum.value = res.data.data.pageNum
    pageSize.value = res.data.data.pageSize
  })
}
const handleSizeChange = (val: number) => {
  pageSize.value = val
  getVideoList(radio.value,title.value)
}
const handleCurrentChange = (val: number) => {
  pageNum.value = val
  getVideoList(radio.value,title.value)
}
</script>

<template>
  <div>
    <div style="display: flex;justify-content: space-between">
      <el-radio-group v-model="radio" size="large" @change="queryVideo" >
        <el-radio-button :label="item.name" :value="item.value" v-for="(item,index) in dictList" :key="index"/>
      </el-radio-group>
      <div style="display: flex;align-items: center">
        <el-input
            v-model="title"
            @keyup.enter="getVideoList(radio, title)"
            placeholder="请输入要搜索的视频"
        ></el-input>
        <el-button
            @click="getVideoList(radio, title)"
            style="height: 40px; margin-left: 10px"
            type="primary"
        >搜 索</el-button>
      </div>
    </div>
    <div style="padding: 20px 0" class="grid-container">
      <div style="width: 200px;margin-bottom: 20px" v-for="(item,index) in videoList" :key="index">
        <div style="position: relative" @click="router.push({path:'/play/',query:{id:item.id}})">
          <img style="border-radius: 10px" width="200px" height="200px" :src="path + item.pic">
          <div class="bg_shadow"></div>
          <div style="display: flex;align-items: center;position: absolute;bottom: 10px;left: 10px">
            <div style="display: flex;align-items: center">
              <img width="20px" src="../assets/播放量.png">
              <div style="color: white;font-size: 13px">1200</div>
            </div>
            <div style="display: flex;align-items: center;margin-left: 10px">
              <img style="margin-top: 6px" width="20px" src="../assets/总弹幕量.png">
              <div style="color: white;font-size: 13px">100</div>
            </div>
          </div>

        </div>
        <div style="font-weight: 600;font-size: 14px">{{item.title}}</div>
        <div style="display: flex;align-items: center;margin-top: 3px">
          <el-avatar size="small" shape="square" :src="path + '/pics/4ef322acb5ff44edb48f077dd3a8642a.jpg'"></el-avatar>
          <div style="color: gray;margin-left: 5px;font-size: 13px;">{{item.username}}</div>
        </div>
      </div>
    </div>
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
</template>

<style scoped>
:deep(.el-input__inner) {
  height: 40px;
}

.bg_shadow {
  position: absolute;
  bottom: 6px;
  left: 0;
  width: 100%;
  height: 30px;
  background-image: linear-gradient(to top, black, #ffffff00);
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
}

/* 强制标题为白色 */
.grid-container > div > div:nth-child(2) {
  color: white !important;
  font-weight: 600;
}

:deep(.el-radio-group) {
  border: none !important;
}

:deep(.el-radio-button) {
  border: none !important;
}

:deep(.el-radio-button__inner) {
  background: #ff69b4 !important;
  border: 1px solid #ff69b4 !important;
  color: white !important;
  margin-left: -1px !important;
  border-radius: 0 !important; /* 先重置圆角 */
}

:deep(.el-radio-button:first-child .el-radio-button__inner) {
  border-radius: 4px 0 0 4px !important;
  margin-left: 0 !important;
}

:deep(.el-radio-button:last-child .el-radio-button__inner) {
  border-radius: 0 4px 4px 0 !important;
}

:deep(.el-radio-button:not(:first-child) .el-radio-button__inner) {
  border-left: 1px solid rgba(255, 255, 255, 0.3) !important; /* 白色半透明分隔线 */
}

:deep(.el-radio-button__orig-radio:checked + .el-radio-button__inner) {
  background: #ff1493 !important;
  border-color: #ff1493 !important;
  color: white !important;
  z-index: 1 !important;
}

:deep(.el-radio-button__inner:hover) {
  background: #ff3e8a !important;
  border-color: #ff3e8a !important;
}

/* 如果还有蓝色边框，使用这个强力清除 */
:deep(.el-radio-button__inner) {
  box-shadow: none !important;
  outline: none !important;
}
</style>