import swal from 'sweetalert';
import axios from 'axios'
//import { response } from 'express';
import JQuery from 'jquery'
let $ = JQuery
var config = require ('../../../config')

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
    name: 'Assistant',
    data () {
        return {
            username: '',
            username4: '',
            username5: '',
            serviceName: '',
            serviceName4: '',
            serviceName5: '',
            datestring: '',
            description: '',
            timestring: '',
            oldServiceName:'',
            newServiceName:'',
            datestring2:'',
            timestring2:'',
            description2:'',
            errorUpdateService2:'',

            serviceName7:'',
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
    }

    
}