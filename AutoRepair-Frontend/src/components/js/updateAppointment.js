import axios from 'axios'
var config = require('../../../config')

var backendConfigurer = function(){
  switch(process.env.NODE_ENV){
      case 'development':
          return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
      case 'production':
          return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
  }
};

var backendUrl = backendConfigurer();

var AXIOS = axios.create({
  baseURL: backendUrl,
  //headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'Appointment',
  data () {
    return {
      appointments: [],
      services: [],
      availableTimeSlots: [],
      unavailableTimeSlots: [],
      oldServiceName: '',
      oldAppointmentDate: '',
      oldAppointmentTime: '',
      newServiceName: '',
      newAppointmentDate: '',
      newAppointmentTime: '',
      errorService: '',
      errorMakeAppointment: '',
      response: []
    }
  },
  created: function () {
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
     makeAppointment: function (username, oldDate, oldTime, newDate, oldService, newTime, newService) {
        AXIOS.post('/update_appointment/'.concat(username), {}, {
        params: {
          oldServiceName: oldService.name,
          oldAppointmentTime: oldTime.toTimeString(),
          oldAppointmentDate: oldDate.toDateString(),
          newServiceName: newService.name,
          newAppointmentTime: newTime.toTimeString(),
          newAppointmentDate: newDate.toDateString(),
        }})
        .then(response => {
        // JSON responses are automatically parsed.
          this.appointments.push(response.data)
          this.errorMakeAppointment = ''
          this.oldServiceName = ''
          this.oldAppointmentDate = ''
          this.oldAppointmentTime = ''
          this.newServiceName = ''
          this.newAppointmentDate = ''
          this.newAppointmentTime = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorUpdateAppointment = errorMsg
        })
     },

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


  }
}




