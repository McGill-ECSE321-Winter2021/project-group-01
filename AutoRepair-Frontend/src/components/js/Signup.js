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
    name:"signup",
    data () {
        return {
            username:'',
            password:'',
            confirmPassword:'',
            firstName:'',
            lastName:'',
            address:'',
            zipCode:'',
            phoneNumber:'',
            email:'',
            model:'',
            plateNumber:'',
            carTransmission:'',
            errorSignup:'',
            response:[]
        }
    },
    methods: {
        signup: function (username, password, confirmPassword,
                     firstName, lastName, address, zipCode, email,phoneNumber,
                     model, plateNumber, carTransmission){
            if(password!=confirmPassword){
                this.errorSignup="Passwords do not match."
            }else{
                this.errorSignup=''
                // AXIOS.post('/register_customer/',{},{
                //     params: {
                //     firstName: firstName,
                //     lastName: lastName,
                //     phoneNumber: phoneNumber,
                //     email: email,
                //     address: address,
                //     zipCode: zipCode,
                //     username: username,
                //     password: password,
                //     model: model,
                //     plateNumber: plateNumber,
                //     carTransmission:carTransmission

                // }})
                AXIOS.post('/register_customer/', {}, {
                    params:{
                        firstName: firstName,
                        lastName: lastName,
                        phoneNumber: phoneNumber,
                        email: email,
                        address: address,
                        zipCode: zipCode,
                        username: username,
                        password: password,
                        model: model,
                        plateNumber: plateNumber,
                        carTransmission:carTransmission
                }})
                .then(response => {
                    this.response=response
                    if(this.response.data.username!=''){
                        window.location.href = "/customer"
                    }else{
                        this.errorSignup=this.response.data;
                    }
            })
                .catch(e => {
                    this.errorSignup = e
                })

            }

        }
    }
}