
  
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
            startTime:'',   
            endTime:'',
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
   
            })
                .catch(e => {
                    this.errorAddOpHours = e.response.data
                })

            }
    
            
            ,deleteOpHours: function (dayOfWeek){
                this.errorDeleteOpHours=''
                AXIOS.post('/delete_business_hours/', {}, {
                    params:{
                        dayOfWeek: dayOfWeek            
                }})
                .then(response => {
                    this.errorDeleteOpHours=''
            })
                .catch(e => {
                    this.errorDeleteOpHours =  "Operating Hour to delete does not exist"
                })
            }
            ,editOpHours: function (dayOfWeek,startTime1,endTime1){
                this.errorEditOpHours=''
                AXIOS.post('/edit_business_hours/', {}, {
                    params:{
                        dayOfWeek: dayOfWeek,   
                        startTime1: startTime1,
                        endTime1: endTime1,            
                }})
                .then(response => {
                    this.errorEditOpHours=''
            })
                .catch(e => {
                    this.errorEditOpHours = e.response.data
                })
            }
        }
    }

        

    
