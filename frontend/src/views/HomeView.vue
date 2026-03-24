<!-- 页面动作 -->
<script setup lang="ts">
import {computed, reactive, ref} from 'vue'
import type {TabsPaneContext, ComponentSize, FormInstance, FormRules } from 'element-plus'
import {ElMessage} from "element-plus";
import {sendCode, login, passwordLogin} from "@/api/user";
import {useRouter} from "vue-router";
const router = useRouter()

const activeName = ref('first')
/* 表单对象的接口 */
interface RuleForm {
  email: string,
  code: string,
  username:string,
  password:string
}
/* 表单元素的唯一标识 */
const ruleFormRef = ref<FormInstance>()
/* 表单对象 */
const ruleForm = ref<RuleForm>({
  email: '',
  code: '',
  username:'',
  password:''

})
const rules = reactive<FormRules<RuleForm>>({
  email: [
    {required: true, message: '请输入qq邮箱', trigger: 'blur'}
  ],
  code: [
    {required: true, message: '请输入验证码', trigger: 'blur'}
  ],
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'}
  ]
})
const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event)
}
/* 控制是否勾选服务协议 */
const checked1 = ref(false)

/* 点击登录按钮的逻辑 */
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      //判断用户是否勾选服务协议
      if(checked1.value){
        if(activeName.value == 'first'){
          //正常登录逻辑
          login(redisKey.value,ruleForm.value.code,ruleForm.value.email+"@qq.com").then(res=>{
            //判断响应是否成功
            if(res.data.code === 200){
              ElMessage.success("登录成功")
              //将用户信息存储到浏览器 并跳转主界面
              sessionStorage.setItem("user",JSON.stringify(res.data.data))
              router.push("/home")
            }else {
              ElMessage.error(res.data.data)
            }
          })
        }else{
          //账号密码登录
          passwordLogin(ruleForm.value.username,ruleForm.value.password).then(res=>{
            if(res.data.code === 200){
              ElMessage.success("登录成功")
              //将用户信息存储到浏览器 并跳转主界面
              sessionStorage.setItem("user",JSON.stringify(res.data.data))
              router.push("/home")
            }else {
              ElMessage.error("账号密码错误")
            }
          })
        }

      }else{
        //提示用户勾选协议
        ElMessage.error("请勾选用户服务协议")
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}
/* 控制倒计时文字是否显示 */
const codeFlag = ref(true)

//初始化 获取验证码的key值
const redisKey = ref('')

/* 获取验证码的方法 */
const getCode = ()=>{
  if(ruleForm.value.email){
    //隐藏获取验证码的文字
    codeFlag.value = false
    //开启倒计时
    timeOut()
    //发送获取验证码的请求
    // then 表示请求的回调函数  服务器返回的数据都会跑到then函数中
    sendCode(ruleForm.value.email+"@qq.com").then(res=>{
      //将后端返回的验证 key值存储起来
      redisKey.value = res.data.data
    })
  }else {
    ElMessage.error("滚去输入QQ号！！！")
  }


}
//初始话定时器
const interval = ref(0)

/* 倒计时的方法 */
const timeOut = ()=>{
  //定时器
   interval.value =  setInterval(()=>{
    if(time.value > 0){
      time.value --
    }else {
      codeFlag.value = true
      //清空定时器
      clearInterval(interval.value)
      time.value = 60
    }
  },1000)
}
/* 初始化倒计时时间 */
const time = ref(60)
</script>

<!-- 页面元素 -->
<template>
  <div class="content">
      <div class="header">
        <!--   头部左侧区域     -->
        <div class="header-left-item">

          <div>
            <img width="45px" src="../assets/miku.jpg" >
          </div>
          <!--    中间的竖线      -->
          <div class="line"></div>
          <div class="tip">分享视频，分享快乐</div>
        </div>
        <!--   头部右侧区域     -->
        <div class="header-right-item">
          <div>海外登录</div>
          <div>帮助</div>
          <div>反馈</div>
          <div>修复公告</div>
        </div>
      </div>
      <!--  中间主体    -->
      <div class="main">
          <div class="main-left-out">
            <div class="main-left">
              <div>为世界上最棒的小破站</div>
              <div>干杯！</div>
            </div>
            <div class="avatar">
              <img  src="../assets/miku-ID.png" >
            </div>
          </div>
          <div class="main-right-out">
            <el-card style="width: 400px;height: 470px;margin: -40px 0  0 220px;
            border-radius: 20px">
              <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
                <el-tab-pane label="邮箱账号登录" name="first"></el-tab-pane>
                <el-tab-pane label="账号密码登录" name="second"></el-tab-pane>
              </el-tabs>
              <!--  邮箱登录  -->
              <div v-if="activeName == 'first'">
                <el-form
                    ref="ruleFormRef"
                    style="max-width: 600px;margin-top: 20px"
                    :model="ruleForm"
                    :rules="rules"
                    label-width="auto"
                    class="demo-ruleForm"
                >
                  <el-form-item  prop="email">
                    <div class="input-out">
                      <el-input placeholder="请输入QQ邮箱账号" v-model="ruleForm.email" />
                      <div class="suffix">@qq.com</div>
                    </div>
                  </el-form-item>
                  <el-form-item  prop="code">
                    <div class="input-out">
                      <el-input placeholder="请输入验证码" v-model="ruleForm.code" />
                      <div v-if="codeFlag" class="suffix" style="color: gray;cursor: pointer" @click="getCode"  >获取验证码</div>
                      <div v-else class="suffix" style="color: gray;cursor: pointer">{{time}}s</div>
                    </div>
                  </el-form-item>
                </el-form>

              </div>

              <!--   账号密码登录  -->
              <div v-else>
                <el-form
                    ref="ruleFormRef"
                    style="max-width: 600px;margin-top: 20px"
                    :model="ruleForm"
                    :rules="rules"
                    label-width="auto"
                    class="demo-ruleForm"
                >
                  <el-form-item  prop="username">
                    <div class="input-out">
                      <el-input placeholder="请输入账号" v-model="ruleForm.username" />
                    </div>
                  </el-form-item>
                  <el-form-item  prop="password">
                    <div class="input-out">
                      <el-input placeholder="请输入密码" type="password" show-password v-model="ruleForm.password" />
                    </div>
                  </el-form-item>
                </el-form>
              </div>
              <div class="xy">
                <el-checkbox v-model="checked1"  size="large" />
                <div style="margin-left: 10px">阅读并接受</div>
                <el-link :underline="false">《服务条款》</el-link>
                <div>和</div>
                <el-link :underline="false">《隐私政策》</el-link>
              </div>
              <el-button type="primary" @click="submitForm(ruleFormRef)" class="loginBt">登 录</el-button>
              <div class="card-bottom">
                <el-link :underline="false">注册新账号</el-link>
                <el-link :underline="false">忘记密码</el-link>
              </div>
            </el-card>
          </div>
      </div>
      <!--  页脚  -->
      <div class="bottom">
        <div class="bottom-out">
          <div>首页</div>
          <div class="line1"></div>
          <div>严选</div>
          <div class="line1"></div>
          <div>政府公益热线</div>
          <div class="line1"></div>
          <div>公司版权所有©1997-2025</div>
        </div>
        <div class="bottom-out">ICP备案 粤B2-20090191-18 公安 粤公网安备 44010602006299增值电信业务许可证 粤B2-20090191  B2-20090058</div>
      </div>
  </div>
</template>

<!-- 页面样式 -->
<style scoped>
  .content{
    height: 100%;
    background: linear-gradient(150deg, pink 0%, lightskyblue 100%);
    display: flex;
    /* 默认上下排列 */
    flex-direction: column;
  }
  .header-left-item{
    /* 开启弹性布局 */
    display: flex;
    /* 垂直居中 */
    align-items: center;
  }
  .header{
    /*内边距*/
    padding: 10px 20px;
    /* 保证原元素内容的空间大小不变的情况下 再调整边距 */
    box-sizing: border-box;
    display: flex;
    align-items: center;
    /* 水平对齐方式 space-between:均分排列（两边对齐）*/
    justify-content: space-between;
  }
  .line{
    height: 30px;
    width: 1px;
    background-color: lightgray;
    margin: 0 10px;
  }
  .line1{
    height: 18px;
    width: 1px;
    background-color: lightgray;
    margin: 0 10px;
  }
  .et{
    width: 140px;
    height: 46px;
    margin: 4px -10px 0 -4px;
  }
  .tip{
    color: lightpink;
  }
  .header-right-item{
    display: flex;
    align-items: center;
  }
  .header-right-item div{
    margin: 0 10px;
    font-size: 13px;
    /* 交互 （鼠标变小手） */
    cursor: pointer;
  }
  .bottom-out{
    display: flex;
    align-items: center;
    /* 水平居中 */
    justify-content: center;
    margin-top: 10px;
  }
  .main{
    /* 让所在的容器项沾满整个容器 */
    flex: 1;
    padding-top: 70px;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .bottom{
    font-size: 13px;
    margin: 20px 0;
  }
  .main-left div{
    color: #324163;
    font-size: 28px;
    font-weight: 600;
  }
  .main-left{
    margin-bottom: 20px;
  }

  .avatar {
    width: 400px;
    height:250px;
  }

   .avatar img {
    width: 100%;
    height: 100%;
  }
   .main-left-out{
     display: flex;
     flex-direction: column;
     align-items: center;
   }
   :deep(.el-tabs__item.is-active){
     font-size: 20px;
     font-weight: 600;
     font-family: math;
   }
  :deep(.el-tabs__item.is-active:hover){
    font-size: 20px;
    font-weight: 600;
    font-family: math;
    color: var(--el-color-primary);
  }
   :deep(.el-tabs__item){
     font-size: 20px;
     font-weight: 600;
     font-family: math;
   }
  :deep(.el-tabs__item:hover){
    font-size: 20px;
    font-weight: 600;
    font-family: math;
    color: black;
  }
  :deep(.el-input__inner){
      height: 45px;
  }
  :deep(.el-tabs__nav-wrap:after){
    display: none;
  }
  .input-out{
    width: 100%;
    /* 相对定位 */
    position: relative;
  }
  .suffix{
    /* 绝对定位 */
    position: absolute;
    right: 15px;
    bottom: 8px;
    font-size: 16px;
  }
  .xy{
    display: flex;
    align-items: center;
    color: gray;
  }
  .loginBt{
    width: 100%;
    height: 45px;
    margin-top: 30px;
  }
  :deep(.el-button>span){
    font-size: 19px;
  }
  .card-bottom{
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 30px;
  }
</style>
