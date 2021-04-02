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
    },


    methods: {
        addreminder(username5, serviceName5, datestring, description, timestring){
            this.errorCreateReminder = 'test';
            AXIOS.post('/create_reminder/',$.param({username: username5, serviceName: serviceName5, datestring: datestring, description:description, timestring:timestring }))
 			  .then(response => {
                swal("Success", "Reminder Added Successfully", "success");
                //this.services.push(response.data)
                //this.errorCreateReminder = 'cr7';

             })
             .catch(e => {
                        swal("ERROR", e.response.data, "error");  
                 				//this.errorCreateReminder = e.response.data
                 				//console.log(this.errorCreateReminder)
                 			})
        },
        updatereminder(oldServiceName, newServiceName, username6, datestring2, description2, timestring2){
            AXIOS.post('/update_reminder/',$.param({oldServiceName: oldServiceName, newServiceName:newServiceName, username: username6, datestring:datestring2, description:description2, timestring:timestring2 }))
 			  .then(response => {
                swal("Success", "Reminder Updated Successfully", "success");
                //this.services.push(response.data)
                //this.errorUpdateReminder = 'cr7';

             })
             .catch(e => {
                        swal("ERROR", e.response.data, "error"); 
                 				//this.errorUpdateReminder = e.response.data
                 				//console.log(this.errorUpdateReminder)
                 			})
        },
        deletereminder(username4, serviceName4){
            AXIOS.post('/delete_reminder/',$.param({username: username4, serviceName: serviceName4}))
 			  .then(response => {
                swal("Success", "Reminder Deleted Successfully", "success");
                //this.services.push(response.data)
                //this.errorDeleteReminder = 'cr7';

             })
             .catch(e => {
                        swal("ERROR", e.response.data, "error"); 
                 				// this.errorDeleteReminder = e.response.data
                 				// console.log(this.errorDeleteReminder)
                 			})
        },

        updateservice(serviceName7, duration7, price7){
          AXIOS.post('/update_service/',$.param({serviceName: serviceName7, duration: duration7, price:price7}))
          .then(response => {
              swal("Success", "Service " + serviceName7 + " Updated Successfully", "success");
              //this.services.push(response.data)
              //this.errorUpdateService2 = 'cr7';

        })
        .catch(e => {
              swal("ERROR", e.response.data, "error");
              //this.errorUpdateService2 = e.response.data
              //console.log(this.errorUpdateService2)
  })
},

    }
    
   
}
