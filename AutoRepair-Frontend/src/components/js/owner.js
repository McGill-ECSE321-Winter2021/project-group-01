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
                
                //this.services.push(response.data)
                this.errorCreateService = 'cr7';

             })
             .catch(e => {
                 				this.errorCreateService = e.response.data
                 				console.log(this.errorCreateService)
                 			})
        },
        updateservice(serviceName2, duration2, price2){
                AXIOS.post('/update_service/',$.param({serviceName: serviceName2, duration: duration2, price:price2}))
                .then(response => {
            
                    //this.services.push(response.data)
                    this.errorUpdateService = 'cr7';

         })
         .catch(e => {
                    this.errorUpdateService = e.response.data
                    console.log(this.errorUpdateService)
        })
    },
        deleteservice(serviceName3){
           // this.errorDeleteService= 'testito'
            AXIOS.post('/delete_service/',$.param({serviceName: serviceName3}))
             .then(response => {
                //this.services.pop(response.data)
                this.errorDeleteService = 'cr7';
             })
             .catch(e => {
                this.errorDeleteService = e.response.data
                console.log(this.errorDeleteService)
            })
        }
    }
    
   
}


// import axios from 'axios'
// import JQuery from 'jquery'
// let $ = JQuery
// var config = require ('../../../config')

// var backendConfigurer = function(){
// 	switch(process.env.NODE_ENV){
//       case 'development':
//           return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
//       case 'production':
//           return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
// 	}
// };

// var frontendConfigurer = function(){
// 	switch(process.env.NODE_ENV){
//       case 'development':
//           return 'http://' + config.dev.host + ':' + config.dev.port;
//       case 'production':
//           return 'https://' + config.build.host + ':' + config.build.port ;
// 	}
// };

// var backendUrl = backendConfigurer();
// //var frontendUrl = frontendConfigurer();
// var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port

// var AXIOS = axios.create({
//   baseURL: backendUrl,
//   headers: { 'Access-Control-Allow-Origin': frontendUrl }
// })

// export default {
// 	name:'login',
// 	data () {
// 		return {
// 			user: '',
// 			type:'',
// 			username: '',
// 			password: '',
// 			errorLogin: '',
// 			response: []
// 		}
// 	},
// 	methods: {
// 		login (username, password) {
// 			AXIOS.post('/login/',$.param({username: username, password: password}))
// 			.then(response => {
// 				this.user = response.data
// 				if (response.status===200) {
// 					this.type = this.user.userType
					
// 					if(this.type.localeCompare("customer")==0){
// 						window.location.href = "/customer"
// 					}
// 					else if(this.type.localeCompare("assistant")==0){
// 						window.location.href = "/assistant"
// 					}
// 					else {
// 						window.location.href = "/owner"
// 					}
					
// 				}
// 			})
// 			.catch(e => {
				
// 				this.errorLogin = e.response.data
// 				console.log(this.errorLogin)
				
// 			})
// 		}
// 	}

// } 