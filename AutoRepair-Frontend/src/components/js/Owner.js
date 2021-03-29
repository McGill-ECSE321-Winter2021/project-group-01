import axios from 'axios'
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
	name:'owner',
	data () {
		return {
			name: '',
			email:'',
			address: '',
			phoneNumber: '',
			businessHours: '',
			holidays: '',
			errorBusiness: '',
			response: []
		}
	},
	methods: {
		registerBusiness (name, email, address, phoneNumber) {
			AXIOS.post('/registerBusiness/',$.param({name: name, password: password, address: address, phoneNumber: phoneNumber}))
			.then(response => {
				this.user = response.data
				if (response.status===200) {
					this.type = this.user.userType
					
					if(this.type.localeCompare("customer")==0){
						window.location.href = "/customer"
					}
					else if(this.type.localeCompare("assistant")==0){
						window.location.href = "/assistant"
					}
					else {
						window.location.href = "/owner"
					}
					
				}
			})
			.catch(e => {
				
				this.errorBusiness = e.response.data
				console.log(this.errorBusiness)
				
			})
		}
	}

}