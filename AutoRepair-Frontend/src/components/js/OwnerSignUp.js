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
    name:"OwnerSignup",
    data () {
        return {
            username:'',
            password:'',
            confirmPassword:'',
            authentificationCode:'',
            errorOwnerSignup:'',
            response:[]
        }
    },

    methods: {
        ownersignup: function (name, password, confirmPassword,authentification){
            if(password!=confirmPassword){
                swal("ERROR", "Passwords do not match.", "error");  

            }else{
                this.errorOwnerSignup=''
                AXIOS.post('/create_owner/', {}, {
                    params:{
                        name: name,
                        password: password,
                        authentification:authentification
          }})
                .then(response => {
                    this.errorSignup=''
                    if(response.status===201){
                        swal("Success", "Owner " + name + " created Successfully, Please Login Below",
                         "success");  

                        // .then(okay => {
                        //   if (okay) {
                        //    window.location.href = "/owner";
                        //  }    
                        // })         
                 }})
                     
                .catch(e => {
           swal("ERROR", e.response.data, "error");  

                })

            }

        }
    }
}