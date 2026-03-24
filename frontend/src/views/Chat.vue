<script setup lang="ts">
import {onMounted, ref, nextTick} from "vue";
import {ElMessage} from "element-plus";
import {chatAi} from "@/api/user";

const msgForm = ref({
  msg:''
})
const messageList = ref([
  {msg:'您可以随意咨询我有关学习的问题😀',uid:0,avatar:'',username:''}
])

// 创建滚动条引用
const scrollContainer = ref<any>(null)

/* 改进的自动滚动函数 */
const scrollToBottom = () => {
  nextTick(() => {
    if (scrollContainer.value) {
      // 使用原生DOM方式精确滚动到底部
      const container = scrollContainer.value.$el || scrollContainer.value
      const wrap = container.querySelector ? container.querySelector('.el-scrollbar__wrap') : null

      if (wrap) {
        wrap.scrollTop = wrap.scrollHeight
      } else if (container.scrollHeight) {
        // 备用方案
        container.scrollTop = container.scrollHeight
      }
    }
  })
}

/* 发送并封装消息 */
const sendMsg = ()=>{
  // 防止空内容发送
  if (!msgForm.value.msg.trim()) {
    ElMessage.error("先写点问题再发送好吗？")
    return
  }

  let msgObj = {
    msg: msgForm.value.msg,
    uid: user.value.id,
    avatar: user.value.avatar,
    username: user.value.username
  }
  messageList.value.push(msgObj)
  // 发送后立即滚动到底部
  scrollToBottom()

  loading.value = true

  chatAi(msgForm.value.msg).then(res=>{
    let aiResponse = ''

    // 安全地获取AI回复内容
    if (res.data && res.data.generation) {
      aiResponse = res.data.generation
    } else if (res.data && res.data.data) {
      aiResponse = res.data.data
    } else {
      aiResponse = '抱歉，AI暂时无法回复'
      console.error('无法解析的响应结构:', res.data)
    }

    let msgObj = {
      msg: aiResponse,
      uid: 0,
      avatar:'',
      username:''
    }
    loading.value = false
    messageList.value.push(msgObj)
    msgForm.value.msg = ''
    // AI回复后滚动到底部
    scrollToBottom()
  }).catch(error => {
    console.error('请求错误:', error)
    loading.value = false
    ElMessage.error('请求失败，请检查网络连接')
    // 错误时也确保滚动位置正确
    scrollToBottom()
  })
}


const user = ref({
  id: -1,
  username: '',
  password: '',
  email: '',
  avatar: ''
})
const loading = ref(false)

onMounted(()=>{
  user.value = JSON.parse(sessionStorage.getItem("user") || '{}')
  // 页面加载完成后滚动到底部
  scrollToBottom()
})
</script>

<template>
  <div class="chat">
    <el-card body-style="padding:0;" class="card-header">
      <div>Ai智慧助手</div>
    </el-card>

    <el-card style="flex: 1;margin: 10px 0" body-style="height:100%">
      <div class="card-content">
        <el-scrollbar height="100%" ref="scrollContainer" class="scroll">
          <div v-for="(item,index) in messageList" :key="index" class="message-item">
            <div class="content-left" v-if="item.uid === 0">
              <div>
                <img style="margin-right: 5px;border-radius: 5px" width="35px" height="35px" src="../assets/1.png">
              </div>
              <div>
                <div style="font-size: 13px">智慧助手</div>
                <div class="reply-out">
                  <div style="font-size: 12px;max-width: 300px; white-space: pre-wrap;">{{item.msg}}</div>
                </div>
              </div>
            </div>

            <div class="content-right" v-else>
              <div class="title-right">
                <div style="font-size: 13px;align-self: flex-end">{{user.username}}</div>
                <div class="reply-out-right">
                  <div style="font-size: 12px;max-width: 300px; white-space: pre-wrap;">{{item.msg}}</div>
                </div>
              </div>
              <div>
                <img style="margin-right: 5px;border-radius: 5px" width="35px" height="35px" :src="path + user.avatar">
              </div>
            </div>
          </div>
        </el-scrollbar>
      </div>
    </el-card>

    <el-card body-style="height:150px">
      <div class="card-bottom-out">
        <div style="position: relative; width: 100%;">
          <!-- 添加回车发送功能 -->
          <el-input
              @keyup.enter="sendMsg"
              placeholder="请输入您的问题...（回车发送）"
              v-model="msgForm.msg"
              :disabled="loading"
          ></el-input>
          <div element-loading-text="思考中......" v-loading="loading" class="loading"></div>

        </div>

        <div class="sendBt">
          <el-button type="primary" @click="sendMsg" :loading="loading">发送</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.card-header {
  padding: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(150deg, pink 0%, lightpink 60%, skyblue 100%) !important;
  color: white;
}
.chat{
  height: 100%;
  display: flex;
  flex-direction: column;
  width: 80%;
  margin: 0 auto ;
}
:deep(.el-input__inner){
  height: 65px;
}
.card-bottom-out{
  display: flex;
  flex-direction: column;
}
.sendBt{
  align-self: flex-end;
  margin-top: 10px;
}
.card-content{
  height: 100%;
  display: flex;
  flex-direction: column;
}
.content-left{
  display: flex;
  margin-top: 10px;
}
.content-right{
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}
.reply-out{
  background-color: #ececec;
  padding: 10px;
  border-radius: 5px;
  margin-top: 10px;
}
.reply-out-right{
  background-color: #409eff;
  padding: 10px;
  border-radius: 5px;
  margin-top: 10px;
  color: white;
}
.title-right{
  display: flex;
  flex-direction: column;
  margin-right: 5px;
}
.scroll{
  padding: 0 10px;
  box-sizing: border-box;
}
.loading{
  position: absolute;
  height: 50px;
  width: 150px;
  right: 10px;
  bottom: 8px;
}
:deep(.el-loading-spinner){
  display: flex;
  justify-content: space-around;
  align-items: center;
}
:deep(.el-loading-spinner .circular){
  width: 30px;
  margin-left: 50px;
}
/* 添加消息项样式，确保布局正确 */
.message-item {
  margin-bottom: 15px;
}
</style>