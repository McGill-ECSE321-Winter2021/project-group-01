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
	name: 'review',
	data() {
		return {
			startDate: '',
			startTime: '',
			description: '',
			serviceRating: '',
			errorReview: '',
			reviews: [],
			appointments: [],
			response: []
		}
	},
	created: function () {
		// Initializing persons from backend
		AXIOS.get('/view_all_reviews')
			.then(response => {
				// JSON responses are automatically parsed.
				this.reviews = response.data
			})
			.catch(e => {
				this.errorReview = e
			})
		// Initializing services from backend
		AXIOS.get('/past_appointmentsOf/', {
			params: {
				username: 'bob'
			}
		})
			.then(response => {
				this.appointments = response.data
			})
			.catch(e => {
				this.appointments = []
			})
	},
	methods: {
        createReview: function (description, serviceRating, appointmentDate, appointmentTime) {
                this.errorSignup=''
                AXIOS.post('/create_review/', {}, {
                    params:{
                        startDate: appointmentDate,
                        startTime: appointmentTime,
                        description: description,
                        serviceRating: serviceRating,
                }})
                .then(response => {
                    this.errorSignup=''
                    if(response.status===201){
                        window.location.href = "/customer"
                     }})
                .catch(e => {
                   this.errorSignup = e.response.data
                })

            }

        }
    }