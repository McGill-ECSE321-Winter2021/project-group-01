import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Login from '@/components/Login'
import Owner from '@/components/Owner'
import Assistant from '@/components/Assistant'
import Customer from '@/components/Customer'
<<<<<<< HEAD
import Services from '@/components/Services'
import Signup from '@/components/Signup'
import Appointment from '@/components/Appointment'
import Test from '@/components/Test'
=======
import Signup from '@/components/Signup'
import OwnerSignUp from '@/components/OwnerSignUp'
import AssistantSignUp from '@/components/AssistantSignUp'
import Appointment from '@/components/Appointment'
import Account from '@/components/Account'
import Review from '@/components/Review'
import AssistantManagement from '@/components/AssistantManagement'
>>>>>>> main


Vue.use(Router)

export default new Router({
  routes: [{
      path: '/',
      name: 'Hello',
      component: Hello
<<<<<<< HEAD
    },
=======
    }, 
>>>>>>> main
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
<<<<<<< HEAD
      path: '/services',
      name: 'Services',
      component: Services
    },
    {
=======
>>>>>>> main
      path: '/signup',
      name: 'Signup',
      component: Signup
    },
    {
<<<<<<< HEAD
=======
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
>>>>>>> main
      path: '/appointments',
      name: 'Appointments',
      component: Appointment
    },
    {
<<<<<<< HEAD
      path: '/test',
      name: 'Test',
      component: Test
    }
=======
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

>>>>>>> main
  ]   
})
