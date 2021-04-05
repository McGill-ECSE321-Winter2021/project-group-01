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
<<<<<<< HEAD
            carTransmission:'',
            errorSignup:'',
            response:[]
=======
            carTransmission:''
            
>>>>>>> main
        }
    },
    methods: {
        signup: function (username, password, confirmPassword,
                     firstName, lastName, address, zipCode, email,phoneNumber,
                     model, plateNumber, carTransmission){
            if(password!=confirmPassword){
<<<<<<< HEAD
                this.errorSignup="Passwords do not match."
            }else{
                this.errorSignup=''
=======
                swal("ERROR", "Passwords do not match.", "error");
            }else{
>>>>>>> main
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
<<<<<<< HEAD
                    this.errorSignup=''
                    if(response.status===201){
                        window.location.href = "/customer"
                     }})
                .catch(e => {
                   this.errorSignup = e.response.data
=======
                    if(response.status===201){
                        this.username='',
                        this.password='',
                        this.confirmPassword='',
                        this.firstName='',
                        this.lastName='',
                        this.address='',
                        this.zipCode='',
                        this.phoneNumber='',
                        this.email='',
                        this.model='',
                        this.plateNumber='',
                        this.carTransmission=''
                        swal("Success", "Account created successfully! Please login below", "success");
                     }})
                .catch(e => {
                    swal("ERROR", e.response.data, "error");
>>>>>>> main
                })

            }

        }
    }
}