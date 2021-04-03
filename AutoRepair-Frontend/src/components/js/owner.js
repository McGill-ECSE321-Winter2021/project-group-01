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
    name: 'Owner',
    data () {
        return {
            serviceName: '',
            serviceName3: '',
            duration: 0,
            price: 0,
            errorCreateService: '',
            errorUpdateService: '',
            errorDeleteService: '',
            services: [],
            response: [],
            errorService: ''
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
    },
    methods: {

        addservice(serviceName, duration, price){
            AXIOS.post('/create_service/',$.param({serviceName: serviceName, duration: duration, price:price}))
 			.then(response => {
                swal("Success", "Service " + serviceName + " Added Successfully", "success");

             })
             .catch(e => {
                swal("ERROR", e.response.data, "error");  
                 			})
        },
        updateservice(serviceName2, duration2, price2){
                AXIOS.post('/update_service/',$.param({serviceName: serviceName2, duration: duration2, price:price2}))
                .then(response => {
                    swal("Success", "Service " + serviceName2 + " Updated Successfully", "success");

         })
         .catch(e => {
            swal("ERROR", e.response.data, "error");  
        })
    },
        deleteservice(serviceName3){
           // this.errorDeleteService= 'testito'
            AXIOS.post('/delete_service/',$.param({serviceName: serviceName3}))
             .then(response => {
                swal("Success", "Service " + serviceName3 + " Deleted Successfully", "success");
             })
             .catch(e => {
                swal("ERROR", e.response.data, "error"); 
            })
        }
    }
    
   
}