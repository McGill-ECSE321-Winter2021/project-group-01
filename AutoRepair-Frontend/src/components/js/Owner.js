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
            serviceName3: '',
            duration: 0,
            price: 0,
            errorCreateService: '',
            errorUpdateService: '',
            errorDeleteService: '',
            services: [],
            reviews: [],
            response: [],
            errorService: ''
        }
    },
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
            

    },
    methods: {
        registerBusiness(name, email, address, phoneNumber) {
            AXIOS.post('/register_business/', $.param({ name: name, email: email, address: address, phoneNumber: phoneNumber }))
                .then(response => {
                    this.errorBusiness = ''
                })
                .catch(e => {
                    if (e.response.data == "Business already exists") {
                        AXIOS.post('/edit_business/', $.param({ email: email, address: address, phoneNumber: phoneNumber }))
                            .then(response => {
                                this.errorBusiness = ''
                            })
                            .catch(error => {
                                this.errorBusiness = error.response.data
                            })
                    }
                    else {
                        this.errorBusiness = e.response.data
                    }

                })
        },

        addservice(serviceName, duration, price) {
            AXIOS.post('/create_service/', $.param({ serviceName: serviceName, duration: duration, price: price }))
                .then(response => {

                    //this.services.push(response.data)
                    this.errorCreateService = 'cr7';

                })
                .catch(e => {
                    this.errorCreateService = e.response.data
                    console.log(this.errorCreateService)
                })
        },
        updateservice(serviceName2, duration2, price2) {
            AXIOS.post('/update_service/', $.param({ serviceName: serviceName2, duration: duration2, price: price2 }))
                .then(response => {

                    //this.services.push(response.data)
                    this.errorUpdateService = 'cr7';

                })
                .catch(e => {
                    this.errorUpdateService = e.response.data
                    console.log(this.errorUpdateService)
                })
        },
        deleteservice(serviceName3) {
            // this.errorDeleteService= 'testito'
            AXIOS.post('/delete_service/', $.param({ serviceName: serviceName3 }))
                .then(response => {
                    this.errorDeleteService = 'cr7';
                })
                .catch(e => {
                    this.errorDeleteService = e.response.data
                    console.log(this.errorDeleteService)
                })
        }
    }


}
