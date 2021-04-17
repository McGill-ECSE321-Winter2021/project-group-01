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
            carTransmission:''

        }
    },
    methods: {
        /**
         * @author Eric Chehata
         * @param {String} username
         * @param {String} password
         * @param {String} confirmPassword
         * @param {String} firstName
         * @param {String} lastName
         * @param {String} address
         * @param {String} zipCode
         * @param {String} email
         * @param {String} phoneNumber
         * @param {String} model
         * @param {String} plateNumber
         * @param {String} carTransmission
         * @description Signs up a customer given the above parameters
         */
        signup: function (username, password, confirmPassword,
                     firstName, lastName, address, zipCode, email,phoneNumber,
                     model, plateNumber, carTransmission){
            if(password!=confirmPassword){
                swal("ERROR", "Passwords do not match.", "error");
            }else{
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
                })

            }

        }
    }
}
