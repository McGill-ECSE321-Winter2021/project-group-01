import swal from 'sweetalert';
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

      username: '',
      username4: '',
      username5: '',
      serviceName: '',
      serviceName4: '',
      serviceName5: '',
      datestring: '',
      description: '',
      timestring: '',
      oldServiceName: '',
      newServiceName: '',
      datestring2: '',
      timestring2: '',
      description2: '',
      errorUpdateService2: '',

      serviceName7: '',
      duration7: 0,
      price7: 0,

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
    },


    addreminder(username5, serviceName5, datestring, description, timestring) {
      AXIOS.post('/create_reminder/', $.param({ username: username5, serviceName: serviceName5, datestring: datestring, description: description, timestring: timestring }))
        .then(response => {
          swal("Success", "Reminder Added Successfully", "success");
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");
        })
    },
    updatereminder(oldServiceName, newServiceName, username6, datestring2, description2, timestring2) {
      AXIOS.post('/update_reminder/', $.param({ oldServiceName: oldServiceName, newServiceName: newServiceName, username: username6, datestring: datestring2, description: description2, timestring: timestring2 }))
        .then(response => {
          swal("Success", "Reminder Updated Successfully", "success");
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");
        })
    },
    deletereminder(username4, serviceName4) {
      AXIOS.post('/delete_reminder/', $.param({ username: username4, serviceName: serviceName4 }))
        .then(response => {
          swal("Success", "Reminder Deleted Successfully", "success");
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");
        })
    },

    updateservice(serviceName7, duration7, price7) {
      AXIOS.post('/update_service/', $.param({ serviceName: serviceName7, duration: duration7, price: price7 }))
        .then(response => {
          swal("Success", "Service " + serviceName7 + " Updated Successfully", "success");
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");
        })
    },

  }


}