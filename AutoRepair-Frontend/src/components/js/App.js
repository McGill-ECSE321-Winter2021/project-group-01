import axios from 'axios'
import JQuery from 'jquery'
let $ = JQuery
var config = require('../../../config')

var backendConfigurer = function () {
  switch (process.env.NODE_ENV) {
    case 'development':
      return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
    case 'production':
      return 'https://' + config.build.backendHost + ':' + config.build.backendPort;
  }
};

var frontendConfigurer = function () {
  switch (process.env.NODE_ENV) {
    case 'development':
      return 'http://' + config.dev.host + ':' + config.dev.port;
    case 'production':
      return 'https://' + config.build.host + ':' + config.build.port;
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
  name: 'app',
  data() {
    return {
      activeItem: 'home',
      username:'',
      type:'',
      businessName: '',
      address: '',
      email: '',
      phoneNumber: '',
      businessHours: [],
      holidays: [],
      services: [],
      response: []
    }
  },
  created: function () {
    // Initializing persons from backend
    this.username=localStorage.getItem('username')
    this.type=localStorage.getItem('type')
    AXIOS.get('/view_business_info')
      .then(response => {
        // JSON responses are automatically parsed.
        this.businessName = response.data.name
        this.address = response.data.address
        this.email = response.data.email
        this.phoneNumber = response.data.phoneNumber
        this.businessHours = response.data.businessHours
        this.holidays = response.data.holidays
      })
      .catch(e => {
        this.businessName = ''
        this.address = ''
        this.email = ''
        this.phoneNumber = ''
        this.businessHours = ''
        this.holidays = ''
      })

    AXIOS.get('/view_all_services')
      .then(response => {
        // JSON responses are automatically parsed.
        this.services = response.data
      })
      .catch(e => {
        this.services = []
      })
  },

  methods: {

    isActive: function (menuItem) {
      return this.activeItem === menuItem
    },
    setActive: function (menuItem) {
      this.activeItem = menuItem 
    },
		
    logout () {
      this.activeItem = 'home'
      window.localStorage.removeItem('username')
      window.localStorage.removeItem('type')
      window.location.href = "/"
      
		}

  }
}