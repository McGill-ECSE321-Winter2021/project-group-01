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
        addreminder(username5, serviceName5, datestring, description, timestring){
            this.errorCreateReminder = 'test';
            AXIOS.post('/create_reminder/',$.param({username: username5, serviceName: serviceName5, datestring: datestring, description:description, timestring:timestring }))
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
        deletereminder(username4, serviceName4){
            AXIOS.post('/delete_reminder/',$.param({username4: username4, serviceName4: serviceName4}))
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
    
   
}
