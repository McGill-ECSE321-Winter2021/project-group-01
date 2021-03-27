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
	name:'login',
	data () {
		return {
			username: '',
			password: '',
			errorLogin: '',
			response: []
		}
	},
	methods: {
		login (username, password) {
		AXIOS.post('/login/', $.param({username: username, password: password}))
		.then(response => {
			response.data
			this.errorLogin=''
			 if (this.response != '') {
                    
                    window.location.href = "/"
				}
				else{
					this.errorLogin = "Wrong username or password"
					console.log(this.errorLogin)
				}

			})
		 .catch(e => {
                var errorMessage = e.response
                console.log(e)
                this.errorLogin = errorMessage
            })	
		}
	}
}
