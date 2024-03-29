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
  name: 'Assistant',
  data() {
    return {
      dayOfWeek: '',
      dayOfWeek1: '',
      dayOfWeek2: '',
      startTime: '',
      startTime1: '',
      endTime: '',
      endTime1: '',
      errorAddOpHours: '',
      errorEditOpHours: '',
      errorDeleteOpHours: '',
      selected: '',
      selected2: '',

      username: '',
      username5: '',
      serviceName: '',
      serviceName4: '',
      serviceName5: '',
      datestring: '',
      description: '',
      timestring: '',
      oldServiceName: '',
      newServiceName: '',
      datestring2: '',
      timestring2: '',
      description2: '',
      errorUpdateService2: '',
      datestring10: '',
      timestring10: '',
      description10: '',

      serviceName7: '',
      duration7: '',
      price7: '',

      errorCreateReminder: '',
      errorUpdateReminder: '',
      errorDeleteReminder: '',
      reminders: [],
      services: [],
      response: [],
      customers: [],
      errorCustomer: '',
      errorReminder: ''
    }
  },


   /**
   * @author Eric Chehata
   * @description initializes page with the all the services, customers and reminders in the system
   */
  created: function () {
    // Initializing persons from backend
    AXIOS.get('/view_all_services')
      .then(response => {
        // JSON responses are automatically parsed.
        this.services = response.data
      })
      .catch(e => {
        this.errorService = e
      })

    AXIOS.get('/view_customers')
      .then(response => {
        // JSON responses are automatically parsed.
        this.customers = response.data
      })
      .catch(e => {
        this.errorCustomer = e
      })

    AXIOS.get('/view_reminders')
      .then(response => {
        // JSON responses are automatically parsed.
        this.reminders = response.data
        this.reminders.sort((a, b) => ((a.date + a.time) > (b.date + b.time)) ? 1 : -1)
      })
      .catch(e => {
        swal("ERROR", e.response.data, "error");
      })
  },


  methods: {
  /**
     * @author Fadi Beshay
     * @param {String} dayOfWeek
     * @param {String} startTime
     * @param {String} endTime
     * @description adds an operating hour for the system
     */
    addOpHours: function (dayOfWeek, startTime, endTime) {
      this.errorAddOpHours = ''
      AXIOS.post('/add_business_hours/', {}, {
        params: {
          dayOfWeek: dayOfWeek,
          startTime: startTime,
          endTime: endTime,
        }
      })
        .then(response => {
          this.dayOfWeek = ''
          this.startTime = ''
          this.endTime = ''
          this.errorAddOpHours = ''

          swal("Success", "Operating Hour for " + dayOfWeek + " Added Successfully", "success")
            .then(okay => {
              if (okay) {
                location.reload();
              }
            });

        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");
        })

    }

  /**
     * @author Fadi Beshay
     * @param {String} dayOfWeek2
     * @description delete an operating hour for the system on a given day of the week
     */
    , deleteOpHours: function (dayOfWeek2) {
      this.errorDeleteOpHours = ''
      AXIOS.delete('/delete_business_hours/', {
        params: {
          dayOfWeek: dayOfWeek2
        }
      })
        .then(response => {
          this.dayOfWeek2 = ''
          this.errorDeleteOpHours = ''
          location.reload();
          swal("Success", "Operating Hour for " + dayOfWeek2 + " Deleted Successfully", "success")
            .then(okay => {
              if (okay) {
                location.reload();
              }
            });
        })
        .catch(e => {
          swal("ERROR", "Operating Hour to delete does not exist", "error");
        })
    }

      /**
     * @author Fadi Beshay
     * @param {String} dayOfWeek1
     * @param {String} startTime1
     * @param {String} endTime1
     * @description editss an operating hour for the system
     */
    , editOpHours: function (dayOfWeek1, startTime1, endTime1) {
      this.errorEditOpHours = ''
      AXIOS.patch('/edit_business_hours/', {}, {
        params: {
          dayOfWeek: dayOfWeek1,
          startTime1: startTime1,
          endTime1: endTime1,
        }
      })
        .then(response => {
          this.dayOfWeek1 = ''
          this.startTime1 = ''
          this.endTime1 = ''
          this.errorEditOpHours = ''
          location.reload();
          swal("Success", "Operating Hour for " + dayOfWeek1 + " Edited Successfully", "success")
            .then(okay => {
              if (okay) {
                location.reload();
              }
            });
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");

        })
    },

  /**
     * @author Robert Aprahamian
     * @param {String} username5
     * @param {String} serviceName5
     * @param {String} datestring
     * @param {String} description
     * @param {String} timestring
     * @description adding a reminder for a certain customer for a certain service
     */
    addreminder(username5, serviceName5, datestring, description, timestring) {
      AXIOS.post('/create_reminder/', $.param({ username: username5, serviceName: serviceName5, datestring: datestring, description: description, timestring: timestring }))
        .then(response => {
          AXIOS.get('/view_reminders')
            .then(response => {
              this.username5 = ''
              this.serviceName5 = ''
              this.datestring = ''
              this.description = ''
              this.timestring = ''
              // JSON responses are automatically parsed.
              this.reminders = response.data
              this.reminders.sort((a, b) => ((a.date + a.time) > (b.date + b.time)) ? 1 : -1)
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
          swal("Success", "Reminder Added Successfully", "success");
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");
        })
    },
    /**
     * @author Robert Aprahamian
     * @param {String} oldServiceName
     * @param {String} newServiceName
     * @param {String} username6
     * @param {String} datestring2
     * @param {String} description2
     * @param {String} timestring2
     * @description updating a reminder for a certain customer for a certain service
     */
    updatereminder(oldServiceName, newServiceName, username6, datestring2, description2, timestring2) {
      AXIOS.patch('/update_reminder/', $.param({ oldServiceName: oldServiceName, newServiceName: newServiceName, username: username6, datestring: datestring2, description: description2, timestring: timestring2 }))
        .then(response => {
          AXIOS.get('/view_reminders')
            .then(response => {
              this.selected2 = ''
              this.newServiceName = ''
              this.description2 = ''
              this.datestring2 = ''
              this.timestring2 = ''
              // JSON responses are automatically parsed.
              this.reminders = response.data
              this.reminders.sort((a, b) => ((a.date + a.time) > (b.date + b.time)) ? 1 : -1)
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
          swal("Success", "Reminder Updated Successfully", "success");
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");
        })
    },
    /**
     * @author Robert Aprahamian
     * @param {String} username4
     * @param {String} serviceName4
     * @description deleting a reminder for a certain customer for a certain service
     */
    deletereminder(username4, serviceName4) {
      AXIOS.delete('/delete_reminder/', {
        params: {
          username: username4,
          serviceName: serviceName4
        }
      })
        .then(response => {
          AXIOS.get('/view_reminders')
            .then(response => {
              this.selected = ''
              // JSON responses are automatically parsed.
              this.reminders = response.data
              this.reminders.sort((a, b) => ((a.date + a.time) > (b.date + b.time)) ? 1 : -1)
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
          swal("Success", "Reminder Deleted Successfully", "success");
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");
        })
    },

    /**
     * @author Robert Aprahamian
     * @param {String} serviceName7
     * @param {String} duration7
     * @param {String} price7
     * @description updating a service for a new duration and/or price
     */
    updateservice(serviceName7, duration7, price7) {
      AXIOS.patch('/update_service/', $.param({ serviceName: serviceName7, duration: duration7, price: price7 }))
        .then(response => {
          this.serviceName7 = ''
          this.duration7 = ''
          this.price7 = ''
          swal("Success", "Service " + serviceName7 + " Updated Successfully", "success");
        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");
        })
    },

  }


}
