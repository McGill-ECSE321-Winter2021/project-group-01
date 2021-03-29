import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Login from '@/components/Login'
import Owner from '@/components/Owner'
import Assistant from '@/components/Assistant'
import Customer from '@/components/Customer'
import Signup from '@/components/Signup'


Vue.use(Router)

export default new Router({
  routes: [{
      path: '/',
      name: 'Hello',
      component: Hello
    }, 
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/owner',
      name: 'Owner',
      component: Owner
    },
    {
      path: '/assistant',
      name: 'Assistant',
      component: Assistant
    },
    {
      path: '/customer',
      name: 'Customer',
      component: Customer
    },
    {
      path: '/signup',
      name: 'Signup',
      component: Signup
    }
  ]   
})
