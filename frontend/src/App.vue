<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
import {onMounted} from "vue";
onMounted(()=>{
  setTimeout(() => {
    window.L2Dwidget.init({
      pluginRootPath: 'static/live2dw/',
      pluginJsPath: 'lib/',

      pluginModelPath: 'live2d-widget-model-haru_2/assets/',
      tagMode: false,
      debug: false,
      model: { jsonPath: 'static/live2dw/live2d-widget-model-koharu/assets/koharu.model.json' },
      display: { position: 'left', width: 180, height: 300 },
      mobile: { show: true },
      log: false
    })
  }, 1000)
})
</script>
<!-- 全局页面文件  -->
<template>
  <!-- 路由出口 -->
  <RouterView />
</template>

<style scoped>

</style>
