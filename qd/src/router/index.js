import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import recycleBin from "@/views/RecycleBin.vue";
import adminPage from "@/views/adminPage.vue";
import index from "@/views/Index.vue";
import MainPage from "@/views/MainPage.vue";
import index from "vuex";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/index',
    name: 'Index',
    component: index
  },
  {
    path: '/adminPage',
    name: 'adminPage',
    component: adminPage
  },
  {
    path: '/recyclebin',
    name: 'RecycleBin',
    component: recycleBin
  },
  {
    path:'/MainPage',
    name:'MainPage',
    component: MainPage
  }
]

const router = new VueRouter({
  routes
})

export default router
