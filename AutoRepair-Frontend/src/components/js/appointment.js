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
      username: '',
      appointment: '',
      selectedAppointment:'',
      serviceName: '',
      appointmentDate: '',
      appointmentTime: '',
      newAppointmentDate:'',
      newAppointmentTime:'',
      newServiceName:'',
      errorService: '',
      errorMakeAppointment: '',
      errorUpdateAppointment:'',
      errorCancelAppointment:'',
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

      AXIOS.get('/upcoming_appointmentsOf/', {
        params:{
        username:'bob',
        }})
        .then(response => {
         this.appointments = response.data
         this.appointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
       })
       .catch(e => {
         this.errorService = e
      })


  },

  methods: {
  updateAppointment(serviceName, appointmentDate, appointmentTime, newAppointmentTime, newAppointmentDate, newServiceName) {
      this.errorCancelAppointment=serviceName
      this.errorMakeAppointment=appointmentTime
      AXIOS.post('/update_appointment/',{},{
        params:{
          username: 'bob',
          appointmentDate: appointmentDate,
          appointmentTime: appointmentTime,
          newAppointmentDate: newAppointmentDate,
          serviceName: serviceName,
          newAppointmentTime: newAppointmentTime,
          newServiceName: newServiceName
      }})
        			.then(response => {
        				this.appointment = response.data
        				appointments.push(response.data)
        				serviceName=''
        				appointmentTime=''
        				appointmentDate=''
        				window.location.href = "/appointments"
        			})
        			.catch(e => {
        			  this.errorUpdateAppointment = e.response.data

              })
    },

  makeAppointment (username, appointmentDate, appointmentTime, serviceName) {
  			AXIOS.post('/make_appointment/',$.param({username: this.$cookie.get('username'), serviceName: serviceName, appointmentDate:appointmentDate , appointmentTime:appointmentTime}))
  			.then(response => {
  				this.appointment = response.data
  				serviceName=''
  				appointmentTime=''
  				appointmentDate=''
  				errorMakeAppointment=''
  				window.location.href = "/appointments"
  			})
  			.catch(e => {
  			  this.errorMakeAppointment = e.response.data

  			})
  },

  cancelAppointment(serviceName, startDate, startTime){

        AXIOS.delete('/cancel_appointment/',{
          params:{
            username:this.$cookie.get('username'),
            appointmentDate:startDate,
            appointmentTime:startTime,
            serviceName:serviceName
        }})
        .then(response => {
          serviceName=''
          appointmentTime=''
          appointmentDate=''
          window.location.href = "/appointments"
        })
        .catch(e => {
          this.errorCancelAppointment = e.response.data

        })

  },


   getAvailableTimeSlots: function(appointmentDate) {

      AXIOS.get('/availableTimeSlots/',{
      params:{
        appointmentDate:appointmentDate,
      }})
      .then(response => {

        // JSON responses are automatically parsed.
          this.availableTimeSlots = response.data
          this.availableTimeSlots.sort((a, b) => ((a.startDate + a.startTime) > (b.startDate + b.startTime)) ? 1 : -1)
      })
      .catch(e => {
         this.errorMakeAppointment=e.response.data
      })

   }





//   getAppointmentsOfCustomer(username) {
//       AXIOS.get('/appointmentsOf/',{
//       params: {
//         username:'bob',
//       }})
//       .then(response => {
//              this.errorUpdateAppointment="help"
//        // JSON responses are automatically parsed.
//         this.appointments = response.data
//       })
//       .catch(e => {
//       this.errorUpdateAppointment="trying"
//         this.errorMakeAppointment  = e.response.data
//       })
//   },
////     convertDate(d) {
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




