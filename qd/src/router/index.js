import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/user/Login.vue'
import Register from '../views/Register.vue'
import Index from '../views/Index.vue'
import MainPage from "@/views/MainPage";
import ShareCenter from "@/views/ShareCenter.vue";
import RecycleBin from "@/views/RecycleBin.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/recyclebin',
    name: 'RecycleBin',
    component: RecycleBin
  },
  {
    path: '/sharecenter',
    name: 'ShareCenter',
    component: ShareCenter
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/index',
    name: 'Index',
    component: Index
  },
  {
    path:'/MainPage',
    name:'MainPage',
    component: MainPage
  },
]

const router = new VueRouter({
  routes
})

export default router
