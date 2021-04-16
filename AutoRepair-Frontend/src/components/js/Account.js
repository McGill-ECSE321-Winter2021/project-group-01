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

    /**
     * @author Eric Chehata
     * Created function that initializes the customer's information
     */
    created: function () {
        // Initializing customers from backend
        AXIOS.get('/view_customer/'.concat(localStorage.getItem('username')))
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
        /**
         * @author Eric Chehata
         * @param {String} firstName 
         * @param {String} lastName 
         * @param {String} address 
         * @param {String} zipCode 
         * @param {String} email 
         * @param {String} phoneNumber 
         * @description edits customer's profile
         */
        editProfile: function (firstName, lastName, address, zipCode, email, phoneNumber) {

            AXIOS.patch('/edit_profile/'.concat(localStorage.getItem('username')), {}, {
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
                    this.firstName=''
                    this.lastName=''
                    this.address=''
                    this.phoneNumber=''
                    this.zipCode=''
                    this.profile = response.data.profile
                    swal("Success", "Profile edited successfully!", "success");
                })
                .catch(e => {
                    swal("ERROR", e.response.data, "error");
                })

        },

        /**
         * @author Eric Chehata
         * @param {String} oldPassword 
         * @param {String} newPassword 
         * @param {String} confirmPassword 
         * @description changes customer's password
         */
        changePassword: function (oldPassword, newPassword, confirmPassword) {
            if (newPassword != confirmPassword) {
                swal("ERROR", "Passwords do not match.", "error");
            } else {
                AXIOS.patch('/change_password/'.concat(localStorage.getItem('username')), {}, {
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

        /**
         * @author Eric Chehata
         * @param {String} modelAdd 
         * @param {String} plateNumberAdd 
         * @param {String} carTransmissionAdd 
         * @description adds a car to the customer
         */
        addCar: function (modelAdd, plateNumberAdd, carTransmissionAdd) {

            AXIOS.post('/add_car/'.concat(localStorage.getItem('username')), {}, {
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

        /**
         * @author Eric Chehata
         * @param {String} plateNumber 
         * @description removes a car from a customer
         */
        removeCar: function (plateNumber) {

            AXIOS.delete('/remove_car/'.concat(localStorage.getItem('username')), {
                params: {
                    plateNumber: plateNumber,
                }
            })
                .then(response => {
                    this.selectedCar = '',
                        this.cars = response.data.cars
                    swal("Success", "Car removed successfully!", "success");
                })
                .catch(e => {
                    swal("ERROR", e.response.data, "error");
                })

        }

    }
}