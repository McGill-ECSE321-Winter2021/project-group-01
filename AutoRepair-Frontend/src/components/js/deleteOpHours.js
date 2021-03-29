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
            errorOpHours:'',
            response:[]
        }
    },
    methods: {

        addOpHours: function (dayOfWeek){
                this.errorOpHours=''
                AXIOS.post('/delete_business_hours/', {}, {
                    params:{
                        dayOfWeek: dayOfWeek            
                }})
                .then(response => {
                    this.errorOpHours=''
            })
                .catch(e => {
                    this.errorOpHours = e.response.data
                })

            }
        }
    }
