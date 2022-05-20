import { createRouter, createWebHistory } from 'vue-router'
import Layout from "../layout/Layout.vue"
import ClientLayout from "@/layout/ClientLayout";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/login",
    children: [
      {
        path: 'home',
        name: 'Home',
        component:() => import("@/views/Home")
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/Login")
  },
  {
    path:'/register',
    name:'Register',
    component: () => import("@/views/Register")
  },
  {
    path:'/client',
    name:'Client',
    component: ClientLayout,
    children: [
      {
        path: '/client',
        name: 'Client',
        component:() => import("@/views/Client")
      },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
