import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Login from '@/components/Login'
import Index from '@/components/Index'


Vue.use(Router)

export default new Router({
  routes: [
	  {
      path: '/',
      component: Hello
    }, 
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/index',
      name: 'Index',
      component: Index
    }
    ]
})
