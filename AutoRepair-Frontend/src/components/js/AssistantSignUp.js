import axios from 'axios'
import JQuery from 'jquery'
import swal from 'sweetalert';

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
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name:"AssistantSignup",
    data () {
        return {
            username:'',
            password:'',
            confirmPassword:'',
            authentificationCode:'',
            errorAssistantSignup:'',
            response:[]
        }
    },

    methods: {
        assistantsignup: function (username, password, confirmPassword,authentificationCode){
            if(password!=confirmPassword){
                swal("ERROR", "Passwords do not match.", "error");  
            }
            if(authentificationCode!=5678){
                 swal("ERROR", "Wrong Authentification Code", "error");  
            

            }else{
                this.errorAssistantSignup=''
                AXIOS.post('/create_assistant/', {}, {
                    
                    params:{
                        username: username,
                        password: password,
                        authentificationCode:authentificationCode
          }})
                .then(response => {
                    this.errorAssistantSignup=''
                    if(response.status===201){
                        this.username=''
                        this.password=''
                        this.confirmPassword=''
                        this.authentificationCode=''
                        swal("Success", "Assistant " + username + 
                        " created Successfully, Please Login Below",
                        "success");  

                 }})
                     
                .catch(e => {
           swal("ERROR", e.response.data, "error");  

                })

            }

        }
    }
}