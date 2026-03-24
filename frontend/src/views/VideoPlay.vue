<script setup lang="ts">
import type {CollapseModelValue} from 'element-plus'
import {useRoute} from "vue-router"

import vueDanmaku from "vue3-danmaku";
import {
  addLikeInfo,
  addReplyInfo,
  addTalk,
  getComments,
  getVideos,
  postComments,
  selectAllTalkAndReply
} from "@/api/video";

const route = useRoute()
const path = "http://localhost:9090"
const videoDto = ref({})

import {ref, reactive, onMounted, watch, nextTick} from 'vue'
import {ElMessage} from "element-plus";
const user = ref({})
const videoUrl = ref('')
onMounted(() => {
  user.value = JSON.parse(sessionStorage.getItem("user") || '{}')
  nextTick(()=>{
    getVideoList(null,null)
  })

})

const talkCountTotal = ref(0)

const getVideoList = (radio: any, title: any) => {

  getVideos(radio, title,1,1,route.query.id,user.value.id).then(res => {
    videoDto.value = res.data.data.rows[0]
    confirmLike.value = videoDto.value.confirmLike
    likeCount.value = videoDto.value.likeCount
    videoUrl.value = path + videoDto.value.basePath
    document.querySelector('video').load()
    getAllTalk()
    getdata();
    value1.value = true;
  })
}
const activeNames = ref(['1'])
const handleChange = (val: CollapseModelValue) => {
  console.log(val)
}
const tableData = ref([])

const speed = ref(50);
const speeds = ref(150);
//内容
const danmus = ref([]);
const value1 = ref(false);



const input = ref("");

const visible = ref(false);
//弹幕组件
const danmaku = ref(null);

watch(value1, (newValue, OldValue, onCleanup) => {
  console.log(newValue);
  onCleanup(() => {
    console.log("111111");
    if (!newValue) {
      // 如果 newValue 为 false
      danmaku.value.show();
    } else {
      danmaku.value.hide();
    }
  });
});

watch(speed, (newValue, OldValue, onCleanup) => {
  onCleanup(() => {
    if (newValue > 50) {
      // 如果 newValue 为 false
      addspeeds();
    } else {
      jianspeeds();
    }
  });
});
//继续播放弹幕
const dplay = () => {
  danmaku.value.play();
};
//暂停播放弹幕
const pause = () => {
  danmaku.value.pause();
};
//显示弹幕
const show = () => {
  danmaku.value.show();
};
//隐藏弹幕
const hide = () => {
  visible.value = !visible.value;
};
//弹幕加速
const addspeeds = () => {
  speeds.value += 20;
  console.log(speeds.value);
};
//弹幕减速
const jianspeeds = () => {
  speeds.value -= 20;
  console.log(speeds.value);
};

const getdata = () => {
  getComments(videoDto.value.id)
      .then(res => {
        danmus.value = res.data.data.map((message:any) => {
          return message.text;
          // 返回每个消息的 commentMessage 属性
        });
        tableData.value = res.data.data
      })
      .catch(err => {
        console.log(err, "err");
        ElMessage.error("获取信息失败");
      });
};

const addComment = () => {
  let comment = {
    videoId:videoDto.value.id,
    uid:1,
    text:input.value
  };
  console.log(comment)
  postComments(comment)
      .then(res => {
        input.value = " ";
        danmus.value.push(comment.text)
        getdata();
      })
      .catch(err => {
        console.log(err, "err");
        ElMessage.error("发布失败");
      });

};
const talkForm = ref({

})
const replyForm = ref({

})
const talkList = ref([])
const getAllTalk = ()=>{
  selectAllTalkAndReply(videoDto.value.id).then(res=>{
    talkList.value = res.data.data
    talkList.value.forEach(x=>{
      talkCountTotal.value += (1 +x.replyDtoList.length)
    })
  })
}
const addTalkOrReply = ()=>{
  if(talkForm.value.talkContent){
    if(placeholder.value.includes('@')){
      replyForm.value.replyContent = talkForm.value.talkContent
      replyForm.value.replyHref = talkForm.value.hrefInfo
      addReplyInfo(replyForm.value).then(res=>{
        if(res.data.code === 200){
          ElMessage.success("回复成功")
          getAllTalk()
        }else{
          ElMessage.success("回复失败")
        }
      })
    }else{
      let obj = {
        videoId:videoDto.value.id,
        userId:user.value.id,
        talkContent:talkForm.value.talkContent,
        hrefInfo:talkForm.value.hrefInfo
      }

      addTalk(obj).then(res=>{
        if(res.data.code === 200){
          ElMessage.success("评论成功")
          getAllTalk()
        }else{
          ElMessage.success("评论失败")
        }
      })
    }
  }else {
    ElMessage.error('写点东西在发布吧')
  }



}
const addReply = (rname:any,talkId:any)=>{
  placeholder.value = '回复：@ '+rname
  replyForm.value.talkId = talkId
  replyForm.value.rname = rname
  replyForm.value.userId = user.value.id
}
const placeholder = ref('请分享一下对此视频的见解吧~')

const current = ref(0)
const likeCount = ref(0)
const confirmLike = ref(false)
const addLike = ()=>{
  addLikeInfo(user.value.id,videoDto.value.id).then(res=>{
    confirmLike.value = res.data.data.confirmLike
    likeCount.value = res.data.data.likeCount
  })
}
</script>

<template>
  <div class="play">
    <el-row>
      <el-col :span="18" style="position: relative">
        <div>
          <div style="font-size: 18px;font-weight: 600;color: black">
            {{videoDto.title}}【{{videoDto.languageName}}】
          </div>
          <div style="display: flex;align-items: center;">
            <div style="display: flex;align-items: center">
              <img width="20px" src="../assets/播放量.png">
              <div style="color: gray;font-size: 13px">1200</div>
            </div>
            <div style="display: flex;align-items: center;margin-left: 10px">
              <img style="margin-top: 6px" width="20px" src="../assets/总弹幕量.png">
              <div style="color: gray;font-size: 13px">100</div>
            </div>
            <div style="margin-left: 10px;font-size: 13px;color: gray">2025-03-03</div>
          </div>
          <div class="big">


            <div>
              <video controls class="backgroundImg">
                <source :src="videoUrl" type="video/mp4"/>
              </video>
            </div>

            <vue-danmaku
                v-model:danmus="danmus"
                :loop="false"
                style="height: 25rem; width: 55rem;
            color: white; /* 白色文字 */
            text-shadow:
                -1px -1px 0 black,
                1px -1px 0 black,
                -1px 1px 0 black,
                1px 1px 0 black; /* 黑色边缘 */
            font-size: 40px; /* 字体大小 */
        } "
                ref="danmaku"
                :speeds="speeds"
            ></vue-danmaku>
            <div class="buts">
              <el-popover :visible="visible" placement="top-start" :width="200">
                <span class="demonstration" >弹幕速度控制</span>
                <el-slider v-model="speed"/>
                <template #reference>
                  <el-icon style="font-size: 28px" @click="hide()">
                    <Setting/>
                  </el-icon>
                </template>
              </el-popover>


            </div>
            <div class="left">
              <el-input
                  v-model="input"
                  size="large"
                  style="width: 500px; margin-right: 10px"
                  placeholder="快来发表你的弹幕吧"
              />
              <el-button round @click="addComment" style="margin-right: 20px">发表</el-button>

              <el-switch v-model="value1"   active-text="开启弹幕"></el-switch>
              <br/>
            </div>
          </div>
          <div style="display: flex;align-items: center;margin-top: 10px">
            <div :class="{'red':confirmLike}" style="font-size: 22px;cursor:pointer;" class="iconfont icon-dianzan" @click="addLike"></div>
            <div style="margin-left: 10px">{{likeCount}}</div>
            <div style="font-size: 13px;color: black;font-weight: 600">（喜欢这个视频就给作者一些鼓励吧~）</div>
          </div>
          <div style="margin-top: 20px">

            <div style="display: flex;align-items: flex-end">
              <div style="font-size: 20px;font-weight: 600;color:whitesmoke">评论</div>
              <div style="margin-left: 10px">{{talkCountTotal}}</div>
            </div>
            <div class="bottom-input">
              <img style="width: 50px;height: 50px;border-radius: 50%;margin-right: 10px" :src="path + user.avatar" >
              <el-input type="textarea" style="height: 45px" size="large" :placeholder="placeholder" v-model="talkForm.talkContent"></el-input>
            </div>

            <div style="display: flex;justify-content:space-between;margin-right: 36px">
              <div style="margin-left: 60px">
                <el-input
                    v-model="talkForm.hrefInfo"
                    placeholder="请输入同类型的视频资源地址"
                    style="width: 400px"
                >
                  <template #prepend>Http://</template>
                </el-input>
              </div>
              <el-button size="default" type="primary" @click="addTalkOrReply">发布</el-button>
            </div>
            <div style="margin-top: 20px" v-for="(talk,index) in talkList" :key="index">
              <div style="display: flex;align-items: center">
                <div><img style="width: 40px;height: 40px;border-radius: 50%;margin-right: 10px" :src="path + talk.pic" ></div>
                <div >
                  <div style="font-size: 14px;font-weight: 600;color: white">{{talk.username}}</div>
                  <div style="display: flex;align-items: center;color: white">
                    <div>{{talk.publishTime}}</div>
                    <div style="color: white;margin-left: 20px;font-size: 13px;cursor: pointer" @click="addReply(talk.username,talk.id)">回复</div>
                  </div>

                </div>
              </div>
              <div style="margin-left:49px;margin-top: 5px;color: white">{{talk.talkContent}}</div>
              <div v-show="talk.id == current" style="margin-top: 20px;margin-left: 50px" v-for="(reply,index) in talk.replyDtoList" :key="index">
                <div style="display: flex;align-items: center">
                  <div><img style="width: 25px;height: 25px;border-radius: 50%;margin-right: 10px" :src="path + reply.pic" ></div>
                  <div >
                    <div style="display: flex;align-items: center;color:white">
                      <div style="font-size: 14px;font-weight: 600">{{reply.username}}</div>
                      <div style="margin: 0 5px" class="iconfont icon-you-sanjiao"></div>
                      <div  style="font-size: 14px;font-weight: 600">{{reply.rname}}</div>
                    </div>
                    <div style="display: flex;align-items: center">
                      <div>{{reply.replyTime}}</div>
                      <div style="color: white;margin-left: 20px;font-size: 13px;cursor: pointer"  @click="addReply(reply.username,talk.id)">回复</div>
                    </div>
                  </div>
                </div>
                <div style="margin-left:35px;margin-top: 5px">{{ reply.replyContent }}</div>
                <div v-if="reply.replyHref" style="margin-left:35px;"> <el-link :href="reply.replyHref" target="_blank">点击视频资源：{{reply.replyHref}}</el-link></div>
              </div>
              <div v-show="talk.replyDtoList.length != 0" style="display: flex;align-items: center;margin-top: 10px;color: white;font-size: 14px">
                <div>共{{talk.replyDtoList.length}}条回复</div>
                <div class="showHz" style="cursor: pointer;margin-left: 10px" @click="current = talk.id">点击查看</div>
              </div>
              <el-divider></el-divider>
            </div>


          </div>


        </div>
      </el-col>
      <el-col :span="6">
        <div style="margin-left: 20px;display: flex;align-items: center">
          <div style="margin-right: 10px">
            <el-avatar :src="path + videoDto.avatar"></el-avatar>
          </div>
          <div>
            <div style="font-size: 17px;font-weight: 600;color: lightskyblue">{{ videoDto.username }}</div>
            <div style="font-size: 13px;color: coral">一键三连即可速速晋升为尊贵的Lv6哦！</div>
          </div>
        </div>
        <div style="margin-left: 40px;margin-top: 10px">
          <el-collapse v-model="activeNames" @change="handleChange">
            <el-collapse-item title="弹幕列表" name="1">
              <el-table size="small" :data="tableData" style="width: 100%">
                <el-table-column prop="username" label="用户"/>
                <el-table-column prop="text" label="弹幕内容"/>
                <el-table-column prop="time" label="发送时间"/>
              </el-table>
            </el-collapse-item>

          </el-collapse>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.play {
  height: 100%;
  width: 80vw;
  margin: 0 auto;
}

:deep(.el-collapse-item__header) {
  background-color: #e7e5e5;
  border-radius: 10px;
}

:deep(.el-collapse-item__header) {
  padding: 0 10px;
}

.big {
  position: relative;
}
.backgroundImg {
  position: absolute;
  height: 30rem;
  width: 55rem;
}

.buts {
  position: absolute;
  right: 50px;
  margin-top: 95px;
}
.left {
  margin-top: 95px;
}
.bottom-input{
  width: 96%;
  height: 100px;
  display: flex;
  align-items: center;
}
.showHz:hover{
  color: #409eff;
}
.red{
  color: #f34d61;
}
</style>