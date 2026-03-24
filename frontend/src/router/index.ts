import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

/*  路由规则 （路由和页面之间的规则） */
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: HomeView
    },
    {
      path: '/home',
      name: 'home',
      component: () => import('../views/Index.vue'),
      redirect:'/share',
      children:[
        {
          path: '/chat',
          name: 'chat',
          component: () => import('../views/Chat.vue')
        },
        {
          path: '/share',
          name: 'share',
          component: () => import('../views/VideoShare.vue')
        },
        {
          path: '/upload',
          name: 'upload',
          component: () => import('../views/Upload.vue')
        },
        {
          path: '/play',
          name: 'play',
          component: () => import('../views/VideoPlay.vue')
        }, {
          path: '/manager',
          name: 'manager',
          component: () => import('../views/Manager.vue')
        }
      ]
    }

  ]
})

export default router
