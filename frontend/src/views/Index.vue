<script setup lang="ts">

import {CirclePlusFilled, Plus, RemoveFilled, UserFilled} from "@element-plus/icons-vue";
import {onMounted, ref} from "vue";
import router from "@/router";

const errorHandler = () => true

const user = ref({
  id:'',
  username:'',
  password:'',
  email:'',
  avatar:'',
  role:0
})
onMounted(()=>{
  user.value = JSON.parse(sessionStorage.getItem("user") || '{}')
  console.log(user.value)
  if( user.value.role == 1){
   router.push('/manager')
  }
})
const  activeIndex = ref('/share')
</script>

<template>
  <div class="index">
    <el-container>
      <el-header>
        <div class="header-left">
          <div><img width="40px" src="../assets/miku.jpg" ></div>
          <div class="small-title">记录世界，记录自己</div>
          <div style="margin-left: 50px">
            <el-menu
                router
                :default-active="activeIndex"
                class="el-menu-demo"
                style="width: 500px"
                mode="horizontal"
                background-color="lightpink"
                text-color="#ffffff"
                active-text-color="#ffffff"
            >
              <el-menu-item v-if="user.role == 0" index="/share">视频分享</el-menu-item>
              <el-menu-item v-if="user.role == 0" index="/upload" >视频上传</el-menu-item>
              <el-menu-item v-if="user.role == 0" index="/chat">智慧助手</el-menu-item>
              <el-menu-item v-show="user.role == 1" index="/manager">视频管理</el-menu-item>
            </el-menu>
          </div>
        </div>
        <div>
          <el-dropdown trigger="click">
            <el-avatar :size="50"  @error="errorHandler">
              <img
                  :src="path +  user.avatar"
              />
            </el-avatar>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item :icon="UserFilled">{{ user.username }}</el-dropdown-item>
                <el-dropdown-item :icon="RemoveFilled" @click.native="router.push('/')">退出系统</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

        <el-main>
            <RouterView></RouterView>
        </el-main>
    </el-container>
  </div>


</template>

<style scoped>
:deep(.el-menu--horizontal.el-menu){
  border-bottom: none;
}
  .el-main{
    background: linear-gradient(150deg, pink 25%, lightskyblue 100%);
  }
  .index{
    height: 100%;
  }
  .el-container{
    height: 100%;
  }
  .el-menu-vertical-demo{
    height: 100%;
  }
  .el-header{
    background-color: lightpink;
    height: 70px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .header-left{
    display: flex;
    align-items: center;
    margin-top: 8px;
  }
  .small-title{
    margin-left: 10px;
    font-size: 16px;
    font-weight: 600;
    color: whitesmoke;
    font-family: monospace;
  }
  span{
    margin-left: 10px;
  }
</style>