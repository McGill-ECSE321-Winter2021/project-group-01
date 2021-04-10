// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import BootstrapVue from "bootstrap-vue"
import App from './App'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import axios from 'axios'
import JQuery from 'jquery'

Vue.use(BootstrapVue)
Vue.config.productionTip = false


let $ = JQuery
var config = require ('../config')

var backendConfigurer = function(){
	switch(process.env.NODE_ENV){
      case 'development':
          return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
      case 'production':
          return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
	}
};

var frontendConfigurer = function(){
	switch(process.env.NODE_ENV){
      case 'development':
          return 'http://' + config.dev.host + ':' + config.dev.port;
      case 'production':
          return 'https://' + config.build.host + ':' + config.build.port ;
	}
};

var backendUrl = backendConfigurer();
//var frontendUrl = frontendConfigurer();
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
	name:'main',
	data () {
		return {
			name: 'tstst',
			email:'',
			address: '',
			phoneNumber: '',
			businessHours: [],
			holidays: [],
			errorBusiness: '',
			response: []
		}
	},
	created: function () {
    AXIOS.get('/view_operating_hours')
    .then(response => {
      this.businessHours = response.data
    })
    .catch(e => {
      this.businessHours=[]
    })
    // AXIOS.get('/view_business_info')
    // .then(response => {
    //   this.name = response.data
    //   this.email = response.data.email
    //   this.address = response.data.address
    //   this.phoneNumber = response.data.phoneNumber
    // })
    // .catch(e => {
    //   this.name = 'test'
    //   this.email = ''
    //   this.address = ''
    //   this.phoneNumber = ''
    // })
  }

}

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})