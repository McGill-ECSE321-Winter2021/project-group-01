import swal from 'sweetalert';
import axios from 'axios'
import JQuery from 'jquery'
let $ = JQuery
var config = require('../../../config')

var backendConfigurer = function () {
  switch (process.env.NODE_ENV) {
    case 'development':
      return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
    case 'production':
      return 'https://' + config.build.backendHost + ':' + config.build.backendPort;
  }
};

var frontendConfigurer = function () {
  switch (process.env.NODE_ENV) {
    case 'development':
      return 'http://' + config.dev.host + ':' + config.dev.port;
    case 'production':
      return 'https://' + config.build.host + ':' + config.build.port;
  }
};

var backendUrl = backendConfigurer();
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'Appointment',
  data() {
    return {
      appointments: [],
      selectedUpdate: '',
      selectedCancel: '',
      services: [],
      availableTimeSlots: [],
      username: '',
      serviceName: '',
      appointmentDate: '',
      appointmentTime: '',
      newAppointmentDate: '',
      newAppointmentTime: '',
      newServiceName: '',
      response: []
    }
  },
  /**
   * @author Tamara Zard Aboujaoudeh
   * @description initializes appointment page with the all the services and the
   * upcoming appointments of the logged in customer
   */
  created() {
    // Initializing services from backend
    AXIOS.get('/view_all_services')
      .then(response => {
        // JSON responses are automatically parsed.
        this.services = response.data
      })
      .catch(e => {
        swal("ERROR", e.response.data, "error");
      })

    AXIOS.get('/upcoming_appointmentsOf/', {
      params: {
        username: localStorage.getItem('username'),
      }
    })
      .then(response => {
        this.appointments = response.data
        this.appointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
      })
      .catch(e => {
        swal("ERROR", e.response.data, "error");
      })


  },

  methods: {
    /**
     * @author Tamara Zard Aboujaoudeh
     * @param {String} serviceName 
     * @param {String} appointmentDate 
     * @param {String} appointmentTime 
     * @param {String} newAppointmentTime 
     * @param {String} newAppointmentDate 
     * @param {String} newServiceName 
     * @description updates a customer's appointment
     */
    updateAppointment(serviceName, appointmentDate, appointmentTime, newAppointmentTime, newAppointmentDate, newServiceName) {
      this.errorCancelAppointment = serviceName
      this.errorMakeAppointment = appointmentTime
      AXIOS.patch('/update_appointment/', {}, {
        params: {
          username: localStorage.getItem('username'),
          appointmentDate: appointmentDate,
          appointmentTime: appointmentTime,
          newAppointmentDate: newAppointmentDate,
          serviceName: serviceName,
          newAppointmentTime: newAppointmentTime,
          newServiceName: newServiceName
        }
      })
        .then(response => {

          this.selectedUpdate = ''
          this.newAppointmentDate = ''
          this.newAppointmentTime = ''
          this.newServiceName = ''
          AXIOS.get('/upcoming_appointmentsOf/', {
            params: {
              username: localStorage.getItem('username'),
            }
          })
            .then(response => {
              this.appointments = response.data
              this.appointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
          this.appointment = response.data
          this.appointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
          swal("Success", "You updated your appointment on " + appointmentDate + " at " + appointmentTime + " for " + serviceName
            + " to " + newAppointmentDate + " at " + newAppointmentTime + " for " + newServiceName, "success");
          serviceName = ''
          appointmentTime = ''
          appointmentDate = ''
          errorMakeAppointment = ''
          errorCancelAppointment = ''
          errorUpdateAppointment = ''
          window.location.href = "/appointments"
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");

        })
    },

    /**
     * @author Tamara Zard Aboujaoudeh
     * @param {String} username 
     * @param {String} appointmentDate 
     * @param {String} appointmentTime 
     * @param {String} serviceName 
     * @description books an appointment
     */
    makeAppointment(username, appointmentDate, appointmentTime, serviceName) {
      AXIOS.post('/make_appointment/', $.param({ username: localStorage.getItem('username'), serviceName: serviceName, appointmentDate: appointmentDate, appointmentTime: appointmentTime }))
        .then(response => {

          this.serviceName = ''
          this.appointmentDate = ''
          this.appointmentTime = ''

          AXIOS.get('/upcoming_appointmentsOf/', {
            params: {
              username: localStorage.getItem('username'),
            }
          })
            .then(response => {
              this.appointments = response.data
              this.appointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
          this.appointment = response.data
          swal("Success", "You booked an appointment on " + appointmentDate + " at " + appointmentTime + " for " + serviceName, "success");
          serviceName = ''
          appointmentTime = ''
          appointmentDate = ''
          errorMakeAppointment = ''
          window.location.href = "/appointments"
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");

        })

    },

    cancelAppointment(serviceName, startDate, startTime) {

      AXIOS.delete('/cancel_appointment/', {
        params: {
          username: localStorage.getItem('username'),
          appointmentDate: startDate,
          appointmentTime: startTime,
          serviceName: serviceName
        }
      })
        .then(response => {
          this.selectedCancel = ''
          AXIOS.get('/upcoming_appointmentsOf/', {
            params: {
              username: localStorage.getItem('username'),
            }
          })
            .then(response => {
              this.appointments = response.data
              this.appointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
          swal("Success", "You cancelled your appointment on " + startDate + " at " + startTime + " for " + serviceName, "success");
          serviceName = ''
          appointmentTime = ''
          appointmentDate = ''
          window.location.href = "/appointments"
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");
        })

    },


    getAvailableTimeSlots: function (appointmentDate) {

      AXIOS.get('/availableTimeSlots/', {
        params: {
          appointmentDate: appointmentDate,
        }
      })
        .then(response => {

          // JSON responses are automatically parsed.
          this.availableTimeSlots = response.data
          this.availableTimeSlots.sort((a, b) => ((a.startDate + a.startTime) > (b.startDate + b.startTime)) ? 1 : -1)
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");
        })

    }
  }
}




