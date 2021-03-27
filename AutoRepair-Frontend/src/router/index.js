import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import MakeAppointment from '@/components/MakeAppointment'
import UpdateAppointment from '@/components/UpdateAppointment'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/makeappointment',
      name: 'MakeAppointment',
      component: MakeAppointment
    },
    {
      path: '/updateappointment',
      name: 'UpdateAppointment',
      component: UpdateAppointment
    }

  ]
})
