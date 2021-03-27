import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import AddService from '@/components/AddService'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/addservice',
      name: 'AddService',
      component: AddService
    }
  ]
})
