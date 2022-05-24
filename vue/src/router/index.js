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
      {
        path: 'manageorder',
        name: 'Manageorder',
        component:() => import("@/views/Manageorder")
      },
      {
        path: 'workorder',
        name: 'Workorder',
        component:() => import("@/views/Workorder")
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
    path:'/changepassword',
    name:'Changepassword',
    component: () => import("@/views/Changepassword")
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
      {
        path: '/onway',
        name: 'Onway',
        component:() => import("@/views/Onway")
      },
      {
        path: '/finished',
        name: 'Finished',
        component:() => import("@/views/Finished")
      },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
