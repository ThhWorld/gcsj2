import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/manage',

      component: () => import('../views/ManageView.vue')
    },
    {
      path: '/recall',

      component: () => import('../views/RecallView.vue')
    },
    {
      path: '/share',

      component: () => import('../views/ShareView.vue')
    }
    ,
    {
      path: '/usermanage',

      component: () => import('../views/UserManageView.vue')
    },
    {
      path: '/sharemanage',

      component: () => import('../views/ShareManageView.vue')
    },
    {
      path: '/recallmanage',

      component: () => import('../views/RecallManageView.vue')
    }
  ]
})

export default router
