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
  name: 'Appointment',
  data () {
    return {
      appointments: [],
      services: [],
      availableTimeSlots: [],
      unavailableTimeSlots: [],
      appointment: '',
      serviceName: '',
      appointmentDate: '',
      appointmentTime: '',
      errorService: '',
      errorMakeAppointment: '',
      response: []
    }
  },
      created() {
      // Initializing services from backend
      AXIOS.get('/view_all_services')
      .then(response => {
        // JSON responses are automatically parsed.
        this.services = response.data
      })
      .catch(e => {
        this.errorService = e
      })
  },

  methods: {
  makeAppointment (username, date, time, service) {
  			AXIOS.post('/make_appointment/'.concat(username),$.param({serviceName: service, appointmentDate:date , appointmentTime:appointmentTime}))
  			.then(response => {
  			var d1 = new Date(date);
  			if(date.getTime()== d1.getTime()){
          this.errorMakeAppointment = "Incorrect username or password"
          console.log(this.errorMakeAppointment)
  			}
  				this.appointment = response.data
  				appointments.push(response.data)
  				window.location.href = "/appointments"
  			})
  			.catch(e => {

  				this.errorMakeAppointment = "Incorrect username or password"
  				console.log(this.errorMakeAppointment)

  			})
  		},
//     makeAppointment: function (username, date, time, service) {
//        AXIOS.post('/make_appointment/'.concat(username), {}, {
//        params: {
//          serviceName: service,
//          appointmentTime: time.toTimeString(),
//          appointmentDate: date.toDateString()
//        }})
//        .then(response => {
//        // JSON responses are automatically parsed.
//        if(service!="a") {
//          this.errorMakeAppointment="Not a"
//        }
//
//          this.appointments.push(response.data)
//          this.errorMakeAppointment = ''
//          this.newAppointment.serviceName = ''
//          this.newAppointment.appointmentDate = ''
//          this.newAppointment.appointmentTime = ''
//          window.location.href = "/appointments"
//        })
//        .catch(e => {
//          var errorMsg = e.response.data.message
//          console.log(errorMsg)
//          this.errorMakeAppointment = errorMsg
//        })
//     },

     getAvailableTimeSlots: function (date) {
        AXIOS.get('/availableTimeSlots/'.concat(date), {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          this.availableTimeSlots = response.data
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorMakeAppointment = errorMsg
        })
     },
     getUnavailableTimeSlots: function (date) {
         AXIOS.get('/unavailableTimeSlots/'.concat(date), {}, {})
         .then(response => {
         // JSON responses are automatically parsed.
           this.unavailableTimeSlots = response.data
         })
         .catch(e => {
           var errorMsg = e.response.data.message
           console.log(errorMsg)
           this.errorMakeAppointment = errorMsg
         })
     },
     getAppointmentsOfCustomer: function (username) {
         AXIOS.get('/appointments/'.concat(username), {}, {})
         .then(response => {
         // JSON responses are automatically parsed.
           this.appointments=response.data
         })
         .catch(e => {
           var errorMsg = e.response.data.message
           console.log(errorMsg)
           this.errorMakeAppointment = errorMsg
         })
     },
//     convertDate(d) {
//       var p = d.split("-");
//       return +(p[0]+p[1]+p[2]);
//     },
//     sortByDate(){
//     var tbody = document.querySelector("#results tbody");
//       // get trs as array for ease of use
//       var rows = [].slice.call(tbody.querySelectorAll("tr"));
//
//       rows.sort(function(a,b) {
//         return convertDate(a.cells[0].innerHTML) - convertDate(b.cells[0].innerHTML);
//       });
//
//       rows.forEach(function(v) {
//         tbody.appendChild(v); // note that .appendChild() *moves* elements
//       });
//     }


  }
}




