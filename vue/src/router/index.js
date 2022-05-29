import { createRouter, createWebHistory } from 'vue-router'
import Layout from "../layout/Layout.vue"
import ClientLayout from "@/layout/ClientLayout";
import RepairmanLayout from "@/layout/RepairmanLayout"
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
  },
  {
    path: '/repairman',
    name: 'Repairman',
    component: RepairmanLayout,
    children: [
      {
        path: '/repairman',
        name: 'Repairman',
        component:() => import("@/views/Repairman")
      },
      {
        path: '/onrepair',
        name: 'Onrepair',
        component:() => import("@/views/Onrepair")
      },
      {
        path: '/repairfinished',
        name: 'Repairfinished',
        component:() => import("@/views/Repairfinished")
      },
    ]
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
