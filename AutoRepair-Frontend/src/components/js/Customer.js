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
    name: 'Owner',
    data() {
        return {

            services: [],
            reviews: [],
            reminders: [],
            errorReminder: ''

        }
    },

/**
   * @author Eric Chehata
   * @description initializes page with the all the services, reviews and reminders for the logged in customer in the system
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

            AXIOS.get('/view_reminders_for_customer',{
                params:{
                    username: window.localStorage.getItem('username')
                }
            })
			.then(response => {
				// JSON responses are automatically parsed.
				this.reminders = response.data
			})
			.catch(e => {
				this.errorReminder = e
			})
    }

}
