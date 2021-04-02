import axios from 'axios'
//import { response } from 'express';
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
  name: 'Assistant',
  data() {
    return {
      dayOfWeek: '',
      dayOfWeek1: '',
      dayOfWeek2: '',
      startTime: '',
      startTime1: '',
      endTime: '',
      endTime1: '',
      errorAddOpHours: '',
      errorEditOpHours: '',
      errorDeleteOpHours: '',
      response: [],
      username: '',
      username4: '',
      username5: '',
      serviceName: '',
      serviceName4: '',
      serviceName5: '',
      datestring: '',
      description: '',
      timestring: '',

      //duration: 0,
      //price: 0,

      errorCreateReminder: '',
      errorUpdateReminder: '',
      errorDeleteReminder: '',
      reminders: [],
      services: [],
      response: [],
      customers: [],
      errorCustomer: ''
    }
  },
  created: function () {
    // Initializing persons from backend
    AXIOS.get('/view_all_services')
      .then(response => {
        // JSON responses are automatically parsed.
        this.services = response.data
      })
      .catch(e => {
        this.errorService = e
      })

    AXIOS.get('/view_customers')
      .then(response => {
        // JSON responses are automatically parsed.
        this.customers = response.data
      })
      .catch(e => {
        this.errorCustomer = e
      })
  },


  methods: {


    addOpHours: function (dayOfWeek, startTime, endTime) {
      this.errorAddOpHours = ''
      AXIOS.post('/add_business_hours/', {}, {
        params: {
          dayOfWeek: dayOfWeek,
          startTime: startTime,
          endTime: endTime,
        }
      })
        .then(response => {
          this.errorAddOpHours = ''
          swal("Success", "Operating Hour for " + dayOfWeek + " Added Successfully", "success");

        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");
        })

    }


    , deleteOpHours: function (dayOfWeek2) {
      this.errorDeleteOpHours = ''
      AXIOS.post('/delete_business_hours/', {}, {
        params: {
          dayOfWeek: dayOfWeek2
        }
      })
        .then(response => {
          this.errorDeleteOpHours = ''
          swal("Success", "Operating Hour for " + dayOfWeek2 + " Deleted Successfully", "success");
        })
        .catch(e => {
          swal("ERROR", "Operating Hour to delete does not exist", "error");
        })
    }

    , editOpHours: function (dayOfWeek1, startTime1, endTime1) {
      this.errorEditOpHours = ''
      AXIOS.post('/edit_business_hours/', {}, {
        params: {
          dayOfWeek: dayOfWeek1,
          startTime1: startTime1,
          endTime1: endTime1,
        }
      })
        .then(response => {
          this.errorEditOpHours = ''
          swal("Success", "Operating Hour for " + dayOfWeek1 + " Edited Successfully", "success");
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");

        })
    }
  },

    addreminder(username5, serviceName5, datestring, description, timestring) {
    this.errorCreateReminder = 'test';
    AXIOS.post('/create_reminder/', $.param({ username: username5, serviceName: serviceName5, datestring: datestring, description: description, timestring: timestring }))
      .then(response => {

        //this.services.push(response.data)
        this.errorCreateReminder = 'cr7';

      })
      .catch(e => {
        this.errorCreateReminder = e.response.data
        console.log(this.errorCreateReminder)
      })
  },
  // updatereminder(customerName, oldServiceName, newServiceName, datestring, description, timestring){
  //     AXIOS.post('/update_reminder/',$.param({customerName: customerName, oldServiceName: oldServiceName, newServiceName:newServiceName,  datestring:datestring, description:description, timestring:timestring }))
  // 	.then(response => {

  //         //this.services.push(response.data)
  //         this.errorUpdateReminder = 'cr7';

  //      })
  //      .catch(e => {
  //          				this.errorUpdateReminder = e.response.data
  //          				console.log(this.errorUpdateReminder)
  //          			})
  // },
  deletereminder(username4, serviceName4) {
    AXIOS.post('/delete_reminder/', $.param({ username4: username4, serviceName4: serviceName4 }))
      .then(response => {

        //this.services.push(response.data)
        this.errorDeleteReminder = 'cr7';

      })
      .catch(e => {
        this.errorDeleteReminder = e.response.data
        console.log(this.errorDeleteReminder)
      })
  }

}


