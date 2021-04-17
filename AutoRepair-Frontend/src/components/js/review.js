import swal from 'sweetalert'
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
	name: 'review',
	data() {
		return {
			startDate: '',
			startTime: '',
			description: '',
			serviceRating: '',
			selected: '',
			descriptionEdit: '',
			editReviewRating: '',
			selectedEdit: '',
			selectedDelete: '',
			errorReview: '',
			customerReviews: '',
			reviews: [],
			appointments: [],
			response: []
		}
	},
	/**
	 * @author Mohammad Saeid Nafar
	 * @description Initializes the page with all the reviews in the system
	 */
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

		AXIOS.get('/view_reviews_of_customer', {
			params: {
				username: localStorage.getItem('username')
			}
		})
			.then(response => {
				this.customerReviews = response.data
			})
			.catch(e => {
				this.this.customerReviews = []

			})

		// Initializing services from backend
		AXIOS.get('/past_appointmentsOf/', {
			params: {
				username: localStorage.getItem('username')
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
		/**
		 * @author Mohammad Saeid Nafar
		 * @param {String} description
		 * @param {int} serviceRating
		 * @param {String} appointmentDate
		 * @param {String} appointmentTime
		 * @description Creates a customer review of an appointment given the above paramters
		 */
		createReview: function (description, serviceRating, appointmentDate, appointmentTime) {

			AXIOS.post('/create_review/', {}, {
				params: {
					startDate: appointmentDate,
					startTime: appointmentTime,
					description: description,
					serviceRating: serviceRating,
				}
			})
				.then(response => {
					this.selected=''
					this.serviceRating=''
					this.description=''
					AXIOS.get('/view_reviews_of_customer', {
						params: {
							username: localStorage.getItem('username')
						}
					})
						.then(response => {
							this.customerReviews = response.data
						})
						.catch(e => {
							this.this.customerReviews = []

						})

					AXIOS.get('/view_all_reviews')
						.then(response => {
							// JSON responses are automatically parsed.
							this.reviews = response.data
						})
						.catch(e => {
							this.errorReview = e
						})
					swal("Success", "Thank you for your feedback!", "success");

				})
				.catch(e => {
					swal("ERROR", e.response.data, "error");
				})

		},
		/**
		 * @author Mohammad Saeid Nafar
		 * @param {String} description
		 * @param {String} serviceRating
		 * @param {String} appointmentDate
		 * @param {String} appointmentTime
		 * @description Edits a customer review of an appointment given the above customers
		 */
		editReview: function (description, serviceRating, appointmentDate, appointmentTime) {

			AXIOS.patch('/edit_review/', {}, {
				params: {
					startDate: appointmentDate,
					startTime: appointmentTime,
					newDescription: description,
					newRating: serviceRating,
				}
			})
				.then(response => {
					this.selectedEdit=''
					this.editReviewRating=''
					this.descriptionEdit=''
					AXIOS.get('/view_reviews_of_customer', {
						params: {
							username: localStorage.getItem('username')
						}
					})
						.then(response => {
							this.customerReviews = response.data
						})
						.catch(e => {
							this.this.customerReviews = []

						})

					AXIOS.get('/view_all_reviews')
						.then(response => {
							// JSON responses are automatically parsed.
							this.reviews = response.data
						})
						.catch(e => {
							this.errorReview = e
						})
					swal("Success", "Review edit successfully!", "success");

				})
				.catch(e => {
					swal("ERROR", e.response.data, "error");
				})

		},
		/**
		 * @author Mohammad Saeid Nafar
		 * @param {String} appointmentDate
		 * @param {String} appointmentTime
		 * @description Deletes a customer review of an appointment given the above parameters
		 */
		deleteReview: function (appointmentDate, appointmentTime) {

			AXIOS.delete('/delete_review/', {
				params: {
					startDate: appointmentDate,
					startTime: appointmentTime
				}
			})
				.then(response => {
					this.selectedDelete=''
					AXIOS.get('/view_reviews_of_customer', {
						params: {
							username: localStorage.getItem('username')
						}
					})
						.then(response => {
							this.customerReviews = response.data
						})
						.catch(e => {
							this.this.customerReviews = []

						})

					AXIOS.get('/view_all_reviews')
						.then(response => {
							// JSON responses are automatically parsed.
							this.reviews = response.data
						})
						.catch(e => {
							this.errorReview = e
						})
					swal("Success", "Review deleted successfully!", "success");

				})
				.catch(e => {
					swal("ERROR", e.response.data, "error");
				})

		}

	}
}
