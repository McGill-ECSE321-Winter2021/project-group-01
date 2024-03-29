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
var frontendUrl = frontendConfigurer();

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
	name:'Login',
	data () {
		return {
			user: '',
			type:'',
			username: '',
			password: '',
			errorLogin: '',
			response: []
		}
	},
	methods: {
		/**
		 * @author Eric Chehata
		 * @param {String} username
		 * @param {String} password
		 * @description Logs in a user given a correct username and password
		 */
		login (username, password) {
			AXIOS.post('/login/',$.param({username: username, password: password}))
			.then(response => {
				this.user = response.data
				if (response.status===200) {

					this.type = this.user.userType
					window.localStorage.setItem('username', this.user.username)
					window.localStorage.setItem('type', this.type)

					if(this.type.localeCompare("customer")==0){

						window.location.href = "/#/customer"
					}
					else if(this.type.localeCompare("assistant")==0){
						window.location.href = "/#/assistant"
					}
					else {
						window.location.href = "/#/owner"
					}

					location.reload();
				}
			})
			.catch(e => {

				swal("ERROR", e.response.data, "error");

			})
		}
	}

}
