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
    name: "account",
    data() {
        return {
            profile: [],
            cars: [],
            firstName: '',
            lastName: '',
            address: '',
            zipCode: '',
            phoneNumber: '',
            email: '',
            oldPassword: '',
            newPassword: '',
            confirmPassword: '',
            modelAdd: '',
            plateNumberAdd: '',
            carTransmissionAdd: '',
            selectedCar: '',
            response: []
        }
    },

    created: function () {
        // Initializing persons from backend
        AXIOS.get('/view_customer/'.concat('bob'))
            .then(response => {
                // JSON responses are automatically parsed.
                this.profile = response.data.profile
                this.cars = response.data.cars

            })
            .catch(e => {
                this.profile = [],
                    this.cars = []
            })
    },
    methods: {
        editProfile: function (firstName, lastName, address, zipCode, email, phoneNumber) {

            AXIOS.patch('/edit_profile/'.concat('bob'), {}, {
                params: {
                    firstName: firstName,
                    lastName: lastName,
                    phoneNumber: phoneNumber,
                    email: email,
                    address: address,
                    zipCode: zipCode,
                }
            })
                .then(response => {
                    this.profile = response.data.profile
                    swal("Success", "Profile edited successfully!", "success");
                })
                .catch(e => {
                    swal("ERROR", e.response.data, "error");
                })



        },

        changePassword: function (oldPassword, newPassword, confirmPassword) {
            if (newPassword != confirmPassword) {
                swal("ERROR", "Passwords do not match.", "error");
            } else {
                AXIOS.patch('/change_password/'.concat('bob'), {}, {
                    params: {
                        oldPassword: oldPassword,
                        newPassword: newPassword
                    }
                })
                    .then(response => {
                        this.oldPassword = '',
                            this.newPassword = '',
                            this.confirmPassword = '',
                            swal("Success", "Password changed successfully!", "success");
                    })
                    .catch(e => {
                        swal("ERROR", e.response.data, "error");
                    })

            }

        },

        addCar: function (modelAdd, plateNumberAdd, carTransmissionAdd) {

            AXIOS.post('/add_car/'.concat('bob'), {}, {
                params: {
                    model: modelAdd,
                    plateNumber: plateNumberAdd,
                    carTransmission: carTransmissionAdd
                }
            })
                .then(response => {
                    this.modelAdd = '',
                        this.plateNumberAdd = '',
                        this.carTransmissionAdd = '',
                        this.cars = response.data.cars
                    swal("Success", "Car added successfully!", "success");
                })
                .catch(e => {
                    swal("ERROR", e.response.data, "error");
                })

        },
       
        removeCar: function ( plateNumber ) {
            
            AXIOS.delete('/remove_car/'.concat('bob'), {
                params: {
                    plateNumber:plateNumber,
                }
            })
                .then(response => {
                    this.selectedCar='',
                    this.cars=response.data.cars
                    swal("Success", "Car removed successfully!", "success");
                })
                .catch(e => {
                    swal("ERROR", e.response.data, "error");
                })

            }

      


    }
}