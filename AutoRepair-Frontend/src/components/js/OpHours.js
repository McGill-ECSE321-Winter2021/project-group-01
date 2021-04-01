import swal from 'sweetalert';
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
    name:"assistant",
    data () {
        return {
            dayOfWeek:'',
            dayOfWeek1:'',
            dayOfWeek2:'',
            startTime:'',  
            startTime1:'',  
            endTime:'',
            endTime1:'',
            errorAddOpHours:'',
            errorEditOpHours:'',
            errorDeleteOpHours:'',
            response:[]
        }
    },

    methods: {
        addOpHours: function (dayOfWeek, startTime, endTime){
                this.errorAddOpHours=''
                AXIOS.post('/add_business_hours/', {}, {
                    params:{
                        dayOfWeek: dayOfWeek,
                        startTime: startTime,
                        endTime: endTime,                
                }})
                .then(response => {
                    this.errorAddOpHours=''
       swal("Success", "Operating Hour for " + dayOfWeek + " Added Successfully", "success");

            })
                .catch(e => {
     swal("ERROR", e.response.data, "error");  
                })

            }
    
            
            ,deleteOpHours: function (dayOfWeek2){
                this.errorDeleteOpHours=''
                AXIOS.post('/delete_business_hours/', {}, {
                    params:{
                        dayOfWeek: dayOfWeek2            
                }})
                .then(response => {
                    this.errorDeleteOpHours=''
    swal("Success", "Operating Hour for " + dayOfWeek2 + " Deleted Successfully", "success");
            })
                .catch(e => {
     swal("ERROR","Operating Hour to delete does not exist", "error");  
                })
            }

            ,editOpHours: function (dayOfWeek1,startTime1,endTime1){
                this.errorEditOpHours=''
                AXIOS.post('/edit_business_hours/', {}, {
                    params:{
                        dayOfWeek: dayOfWeek1,   
                        startTime1: startTime1,
                        endTime1: endTime1,            
                }})
                .then(response => {
                    this.errorEditOpHours=''
    swal("Success", "Operating Hour for " + dayOfWeek1 + " Edited Successfully", "success"); 
            })
                .catch(e => {
      swal("ERROR", e.response.data, "error"); 

                })
            }
        }
    }

        

    
