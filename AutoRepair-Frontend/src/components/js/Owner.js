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
//var frontendUrl = frontendConfigurer();
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


export default {
    name: 'Owner',
    data() {
        return {
            name: '',
            email: '',
            address: '',
            phoneNumber: '',
            businessHours: '',
            holidays: '',
            errorBusiness: '',
            serviceName: '',
            serviceName2: '',
            serviceName3: '',
            duration: '',
            price: '',
            duration2: '',
            price2: '',
            errorCreateService: '',
            errorUpdateService: '',
            errorDeleteService: '',
            services: [],
            reviews: [],
            appointmentsType: 'All Appointments',
            appointments: [],
            pastAppointments: [],
            upcomingAppointments: [],
            response: [],
            errorService: '',
            errorAppointment: ''
        }
    },
    /**
     * @author Marc Saber
     * @description Initializes the page with the owners in the system
     */
    created: function () {
        AXIOS.get('/view_all_services')
            .then(response => {
                this.services = response.data
            })
            .catch(e => {
                this.errorService = e
            })

        AXIOS.get('/view_all_reviews')
            .then(response => {
                // JSON responses are automatically parsed.
                this.reviews = response.data
            })
            .catch(e => {
                this.errorReview = e
            })


        AXIOS.get('/appointments')
            .then(response => {
                // JSON responses are automatically parsed.
                this.appointments = response.data
                this.appointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
                console.log(this.appointments)
            })
            .catch(e => {
                this.errorAppointment = e
            })

        AXIOS.get('/past_appointments')
            .then(response => {
                // JSON responses are automatically parsed.
                this.pastAppointments = response.data
                this.pastAppointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
                console.log(this.pastAppointments)
            })
            .catch(e => {
                this.errorAppointment = e
            })

        AXIOS.get('/upcoming_appointments')
            .then(response => {
                // JSON responses are automatically parsed.
                this.upcomingAppointments = response.data
                this.upcomingAppointments.sort((a, b) => ((a.timeSlot.startDate + a.timeSlot.startTime) > (b.timeSlot.startDate + b.timeSlot.startTime)) ? 1 : -1)
                console.log(this.upcomingAppointments)
            })
            .catch(e => {
                this.errorAppointment = e
            })


    },
    methods: {
        /**
         * @author Fadi Tawfik Beshay
         * @param {String} name 
         * @param {String} email 
         * @param {String} address 
         * @param {String} phoneNumber 
         * @description Registers the business info of the Autorepair shop given the above parameters
         */
        registerBusiness(name, email, address, phoneNumber) {
            AXIOS.post('/register_business/', $.param({ name: name, email: email, address: address, phoneNumber: phoneNumber }))
                .then(response => {
                    this.name = ''
                    this.email = ''
                    this.address = ''
                    this.phoneNumber = ''
                    
                    swal("Success", "Business " + name + " created Successfully", "success")
                    .then(okay => {
                        if(okay){
                            location.reload();
                        }
                    });
                })
                .catch(e => {
                    if (e.response.data == "Business already exists") {
                        AXIOS.patch('/edit_business/', $.param({ email: email, address: address, phoneNumber: phoneNumber }))
                            .then(response => {
                                this.name = ''
                                this.email = ''
                                this.address = ''
                                this.phoneNumber = ''
                                
                                swal("Success", "Business Info Successfully Updated", "success")
                                .then(okay => {
                                    if(okay){
                                        location.reload();
                                    }
                                });    
                            })
                            .catch(error => {
                                swal("ERROR", e.response.data, "error");
                            })
                    }
                    else {
                        swal("ERROR", e.response.data, "error");
                    }

                })
        },
        /**
         * @author Robert Aprahamian
         * @param {String} serviceName 
         * @param {String} duration 
         * @param {String} price 
         * @description Adds a service to the system given the above paramters
         */
        addservice(serviceName, duration, price) {
            AXIOS.post('/create_service/', $.param({ serviceName: serviceName, duration: duration, price: price }))
                .then(response => {
                    AXIOS.get('/view_all_services')
                        .then(response => {
                            this.serviceName=''
                            this.duration=''
                            this.price=''
                            this.services = response.data
                        })
                        .catch(e => {
                            this.errorService = e
                        })
                    swal("Success", "Service " + serviceName + " Added Successfully", "success");

                })
                .catch(e => {
                    swal("ERROR", e.response.data, "error");
                })
        },
        updateservice(serviceName2, duration2, price2) {
            AXIOS.patch('/update_service/', $.param({ serviceName: serviceName2, duration: duration2, price: price2 }))
                .then(response => {
                    AXIOS.get('/view_all_services')
                        .then(response => {
                            this.serviceName2=''
                            this.duration2=''
                            this.price2=''
                            this.services = response.data
                        })
                        .catch(e => {
                            this.errorService = e
                        })
                    swal("Success", "Service " + serviceName2 + " Updated Successfully", "success");

                })
                .catch(e => {
                    swal("ERROR", e.response.data, "error");
                })
        },
        deleteservice(serviceName3) {
            AXIOS.delete('/delete_service/', {
                params: {
                    serviceName: serviceName3
                }
            })
                .then(response => {
                    AXIOS.get('/view_all_services')
                        .then(response => {
                            this.serviceName3=''
                            this.services = response.data
                        })
                        .catch(e => {
                            this.errorService = e
                        })
                    swal("Success", "Service " + serviceName3 + " Deleted Successfully", "success");
                })
                .catch(e => {
                    swal("ERROR", e.response.data, "error");
                })
        }
    }

}