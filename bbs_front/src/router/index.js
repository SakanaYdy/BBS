import { createRouter, createWebHashHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import PersonBlog from '../views/PersonBlog.vue'
import TestView from '../views/TestView.vue'
import NoticeView from '../views/NoticeView.vue'
const routes = [
  {
    path: '/',
    name: 'home',
    component: LoginView
  },
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/main',
    name: 'main',
    component: MainView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/person',
    name: 'person',
    component: PersonBlog
  },
  {
    path: '/test',
    name: 'test',
    component: TestView
  },
  {
    path: '/notice',
    name: 'notice',
    component: NoticeView
  },
  {
    path: '/favorite',
    name: 'favorite',
    component: () => import(/* webpackChunkName: "about" */ '../views/FavoriteView.vue')
  },
  {
    path: '/manageLabel',
    name: 'manageLabel',
    component: () => import(/* webpackChunkName: "about" */ '../views/ManageLabel.vue')
  },
  {
    path: '/manage',
    name: 'manage',
    component: () => import(/* webpackChunkName: "about" */ '../views/ManageView.vue')
  },
  {
    path: '/add',
    name: 'add',
    component: () => import(/* webpackChunkName: "about" */ '../views/AddBlog.vue')
  },
  {
    path: '/personalInfo',
    name: 'personalInfo',
    component: () => import(/* webpackChunkName: "about" */ '../views/PersonalView.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
