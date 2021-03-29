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
            businessName:'',
            dayOfTheWeek:'',
            startTime:'',
            endTime:'',
            errorOpHours:'',
            response:[]
        }
    },
    methods: {


        addOperatingHours: function (businessName,dayOfWeek, startTime, endTime){
                this.errorOpHours=''
        //        AXIOS.get('/view_business_info') 
         //          .then(response => {
                    
          //              })

                AXIOS.post('/add_business_hours/', {}, {
                    params:{
                        businessName:businessName,
                        dayOfWeek: dayOfWeek,
                        startTime: startTime,
                        endTime: endTime,                
                }})
                .then(response => {
                    this.errorOpHours=''
             //       if(response.status===201){   //add in controller
             //           window.location.href = "/assistant"
            })
                .catch(e => {
                    this.errorOpHours = e.response.data
                })

            }
        }
    }
