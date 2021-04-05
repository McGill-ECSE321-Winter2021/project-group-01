<<<<<<< HEAD
=======
import swal from 'sweetalert';
>>>>>>> main
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
<<<<<<< HEAD
      services: [],
      availableTimeSlots: [],
      unavailableTimeSlots: [],
      username: '',
      appointment: '',
      selectedAppointment:'',
=======
      selectedUpdate: '',
      selectedCancel: '',
      services: [],
      availableTimeSlots: [],
      username: '',
>>>>>>> main
      serviceName: '',
      appointmentDate: '',
      appointmentTime: '',
      newAppointmentDate:'',
      newAppointmentTime:'',
      newServiceName:'',
<<<<<<< HEAD
      errorService: '',
      errorMakeAppointment: '',
      errorUpdateAppointment:'',
      errorCancelAppointment:'',
=======
>>>>>>> main
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
<<<<<<< HEAD
        this.errorService = e
=======
        swal("ERROR", e.response.data, "error");
>>>>>>> main
      })

      AXIOS.get('/upcoming_appointmentsOf/', {
        params:{
<<<<<<< HEAD
        username:'bob'
        }})
        .then(response => {
         this.appointments = response.data
       })
       .catch(e => {
         this.errorService = e
=======
        username: localStorage.getItem('username'),
        }})
        .then(response => {
         this.appointments = response.data
         this.appointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
       })
       .catch(e => {
         swal("ERROR", e.response.data, "error");
>>>>>>> main
      })


  },

  methods: {
<<<<<<< HEAD
  updateAppointment(username, selectedAppointment, newAppointmentTime, newAppointmentDate, newServiceName) {

      if(selectedAppointment!=""){
            var splitAppointment = selectedAppointment.split("; ");
            this.serviceName = splitAppointment[0];
            this.appointmentDate = splitAppointment[1];
            this.appointmentTime = splitAppointment[2];
      }

      AXIOS.post('/update_appointment/',{},{
        params:{
          username: 'bob',
          appointmentDate: this.appointmentDate,
          appointmentTime: this.appointmentTime,
          newAppointmentDate: newAppointmentDate,
          serviceName: this.serviceName,
=======
  updateAppointment(serviceName, appointmentDate, appointmentTime, newAppointmentTime, newAppointmentDate, newServiceName) {
      this.errorCancelAppointment=serviceName
      this.errorMakeAppointment=appointmentTime
      AXIOS.patch('/update_appointment/',{},{
        params:{
          username: localStorage.getItem('username'),
          appointmentDate: appointmentDate,
          appointmentTime: appointmentTime,
          newAppointmentDate: newAppointmentDate,
          serviceName: serviceName,
>>>>>>> main
          newAppointmentTime: newAppointmentTime,
          newServiceName: newServiceName
      }})
        			.then(response => {
<<<<<<< HEAD
        				this.appointment = response.data
        				appointments.push(response.data)
        				serviceName=''
        				appointmentTime=''
        				appointmentDate=''
        				window.location.href = "/appointments"
        			})
        			.catch(e => {
        			  this.errorUpdateAppointment = e.response.data
=======

                this.selectedUpdate=''
                this.newAppointmentDate=''
                this.newAppointmentTime=''
                this.newServiceName=''
                AXIOS.get('/upcoming_appointmentsOf/', {
                  params:{
                  username: localStorage.getItem('username'),
                  }})
                  .then(response => {
                   this.appointments = response.data
                   this.appointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
                 })
                 .catch(e => {
                   swal("ERROR", e.response.data, "error");
                })
        				this.appointment = response.data
        				this.appointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
        				swal("Success", "You updated your appointment on " + appointmentDate + " at " + appointmentTime +" for " + serviceName
        				+" to " + newAppointmentDate + " at " + newAppointmentTime +" for " + newServiceName, "success");
        				serviceName=''
        				appointmentTime=''
        				appointmentDate=''
        				errorMakeAppointment=''
        				errorCancelAppointment=''
        				errorUpdateAppointment=''
        				window.location.href = "/appointments"
        				})
        			.catch(e => {
        			  swal("ERROR", e.response.data, "error");
>>>>>>> main

              })
    },

  makeAppointment (username, appointmentDate, appointmentTime, serviceName) {
<<<<<<< HEAD
  			AXIOS.post('/make_appointment/',$.param({username: 'bob', serviceName: serviceName, appointmentDate:appointmentDate , appointmentTime:appointmentTime}))
  			.then(response => {
  				this.appointment = response.data
=======
  			AXIOS.post('/make_appointment/',$.param({username: localStorage.getItem('username'), serviceName: serviceName, appointmentDate:appointmentDate , appointmentTime:appointmentTime}))
  			.then(response => {

          this.serviceName=''
          this.appointmentDate=''
          this.appointmentTime=''

          AXIOS.get('/upcoming_appointmentsOf/', {
            params:{
            username: localStorage.getItem('username'),
            }})
            .then(response => {
             this.appointments = response.data
             this.appointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
           })
           .catch(e => {
             swal("ERROR", e.response.data, "error");
          })
  				this.appointment = response.data
  				swal("Success", "You booked an appointment on " + appointmentDate + " at " + appointmentTime +" for " + serviceName, "success");
>>>>>>> main
  				serviceName=''
  				appointmentTime=''
  				appointmentDate=''
  				errorMakeAppointment=''
  				window.location.href = "/appointments"
  			})
  			.catch(e => {
<<<<<<< HEAD
  			  this.errorMakeAppointment = e.response.data

  			})
  },

  cancelAppointment(serviceName, startDate, startTime){
       
        AXIOS.delete('/cancel_appointment/',{
          params:{
            username:'bob',
=======
  			  swal("ERROR", e.response.data, "error");

  			})

  },

  cancelAppointment(serviceName, startDate, startTime){

        AXIOS.delete('/cancel_appointment/',{
          params:{
            username:localStorage.getItem('username'),
>>>>>>> main
            appointmentDate:startDate,
            appointmentTime:startTime,
            serviceName:serviceName
        }})
        .then(response => {
<<<<<<< HEAD
=======
          this.selectedCancel=''
          AXIOS.get('/upcoming_appointmentsOf/', {
            params:{
            username: localStorage.getItem('username'),
            }})
            .then(response => {
             this.appointments = response.data
             this.appointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
           })
           .catch(e => {
             swal("ERROR", e.response.data, "error");
          })
          swal("Success", "You cancelled your appointment on " + startDate + " at " + startTime +" for " + serviceName, "success");
>>>>>>> main
          serviceName=''
          appointmentTime=''
          appointmentDate=''
          window.location.href = "/appointments"
        })
        .catch(e => {
<<<<<<< HEAD
          this.errorCancelAppointment = e.response.data

=======
         swal("ERROR", e.response.data, "error");
>>>>>>> main
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
<<<<<<< HEAD
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


=======
          this.availableTimeSlots.sort((a, b) => ((a.startDate + a.startTime) > (b.startDate + b.startTime)) ? 1 : -1)
      })
      .catch(e => {
         swal("ERROR", e.response.data, "error");
      })

   }
>>>>>>> main
  }
}




