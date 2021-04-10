import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Login from '@/components/Login'
import Owner from '@/components/Owner'
import Assistant from '@/components/Assistant'
import Customer from '@/components/Customer'
import Signup from '@/components/Signup'
import OwnerSignUp from '@/components/OwnerSignUp'
import AssistantSignUp from '@/components/AssistantSignUp'
import Appointment from '@/components/Appointment'
import Account from '@/components/Account'
import Review from '@/components/Review'
import AssistantManagement from '@/components/AssistantManagement'


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
    },
    {
      path: '/ownersignup',
      name: 'OwnerSignup',
      component: OwnerSignUp
    },
    {
      path: '/assistantsignup',
      name: 'AssistantSignup',
      component: AssistantSignUp
    },
    {
      path: '/appointments',
      name: 'Appointments',
      component: Appointment
    },
    {
      path: '/account',
      name: 'Account',
      component: Account
    },
    {
      path: '/review',
      name: 'Review',
      component: Review
    },
    {
      path: '/assistantmanagement',
      name: 'AssistantManagement',
      component: AssistantManagement
    }

  ]   
})