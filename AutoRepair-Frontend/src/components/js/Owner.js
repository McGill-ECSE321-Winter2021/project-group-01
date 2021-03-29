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
			AXIOS.post('/register_business/',$.param({name: name, email: email, address: address, phoneNumber: phoneNumber}))
			.then(response => {
				this.errorBusiness = ''
			})
			.catch(e => {
				if(e.response.data=="Business already exists"){
					AXIOS.post('/edit_business/',$.param({email: email, address: address, phoneNumber: phoneNumber}))
					.then(response => {
						this.errorBusiness = ''
					})
					.catch(error => {
					this.errorBusiness = error.response.data
					})
				}
				else{
					this.errorBusiness = e.response.data
				}
				
			})
		}
	}

}