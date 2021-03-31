<template>
	<div id="Owner">


<nav class="navbar navbar-expand-lg navbar-light ftco_navbar bg-light ftco-navbar-light" id="ftco-navbar">
        <div class="container">
        
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="fa fa-bars"></span> Menu
          </button>
          <form action="#" class="searchform order-lg-last">
            <div class="form-group d-flex">
              <input type="text" class="form-control pl-3" placeholder="Search">
              <button type="submit" placeholder="" class="form-control search"><span class="fa fa-search"></span></button>
            </div>
          </form>
          <div class="collapse navbar-collapse" id="ftco-nav">
            <ul class="navbar-nav mr-auto">
              <li class="nav-item active">
                <router-link class="nav-link" to="/">Home</router-link></li>
              <li class="nav-item">
                <router-link class="nav-link" to="/owner">Owner</router-link></li>
              <li class="nav-item">
                <router-link class="nav-link" to="/services">Services</router-link></li>
              <li class="nav-item">
				<router-link class="nav-link" to="/assistant">Assistant</router-link></li>

              <li class="nav-item">
                <router-link class="nav-link" to="/login">Login</router-link></li>
              <li class="nav-item">
				<router-link class="nav-link" to="/customer">Customer</router-link></li>
            </ul>
          </div>
        </div>
      </nav>


<section class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img" style="background-image: url(../static/images/bg_3.jpg);">
			<div class="overlay"></div>
    	<div class="container">
    		<div class="row d-md-flex justify-content-end">
    			<div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
    				<span class="subheading">Adding a service</span>
    				<h2 class="mb-4">Plese enter the details below</h2>
    				<form action="#" class="appointment">
    					<div class="row">
    						
								<div class="col-md-12">
									<div class="form-group">
			              <input type="text" class="form-control" v-model="serviceName" placeholder="Service Name">
			            </div>
								</div>

                <div class="col-md-6">
									<div class="form-group">
			    					<div class="input-wrap">
			            		<!-- <div class="icon"><span class="fa fa-calendar"></span></div> -->
			            	<!--	<input type="text" class="form-control appointment_date" placeholder="Date"> -->
                      <input type="number" min="0" class="form-control" v-model="duration" placeholder="Duration in minutes">
		            		</div>
			    				</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
			    					<div class="input-wrap">
			            		<!-- <div class="icon"><span class="fa fa-clock-o"></span></div> -->
			            		<input type="number" min="0" step="0.01" class="form-control" v-model="price" placeholder="Price">
		            		</div>
			    				</div>
								</div>

								<div class="col-md-12">
									<div class="form-group">
                    <button 
                      type="button" 
                      class="btn btn-dark py-3 px-4"
                      @click="addservice(serviceName, duration, price)">Add the Service</button>
                  </div>
								</div>

                  <div class="col-md-12">
									<div class="form-group">
                    <h5 v-if="errorCreateService" style="color:red; padding-top:20px">Error: {{errorCreateService}}</h5> 
                  </div>
								  </div>


    					</div>
	          </form>
    			</div>
    		</div>
    	</div>

<!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->

        <!-- <div class="overlay"></div> -->
        <div class="container">
          <div class="row d-md-flex justify-content-end">
            <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
              <span class="subheading">Updating a service</span>
              <h2 class="mb-4">Plese enter the new details below</h2>
              <form action="#" class="appointment">
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <div class="form-field">
                        <div class="select-wrap">
                          <div class="icon"><span class="fa fa-chevron-down"></span></div>
                          <select name="" id="" class="form-control">
                            <option value="">Select service</option>
                            <option value="">Change Oil</option>
                            <option value="">Engine Repair</option>
                            <option value="">Battery Replace</option>
                            <option value="">Change Tire</option>
                            <option value="">Tow Truck</option>
                          </select>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap">
                        <!-- <div class="icon"><span class="fa fa-calendar"></span></div> -->
                      <!--	<input type="text" class="form-control appointment_date" placeholder="Date"> -->
                        <input type="number" min="0" class="form-control" placeholder="New duration in minutes">
                      </div>
                    </div>
                  </div>

                  <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap">
                        <input type="number" min="0" step="0.01" class="form-control" placeholder="New price">
                      </div>
                    </div>
                  </div>
                  
                  <div class="col-md-12">
                    <div class="form-group">
                      <input type="submit" value="Update the Service" class="btn btn-dark py-3 px-4">
                    </div>
                  </div>

                  <div class="col-md-12">
									<div class="form-group">
                    <h5 v-if="errorUpdateService" style="color:red; padding-top:20px">Error: {{errorUpdateService}}</h5> 
                  </div>
								  </div>

                </div>
              </form>
            </div>
          </div>
        </div>

<!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->

          <div class="container">
          <div class="row d-md-flex justify-content-end">
            <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
              <span class="subheading">Deleting a service</span>
              <h2 class="mb-4">Plese enter the details below</h2>
              <form action="#" class="appointment">
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <div class="form-field">
                        <div class="select-wrap">
                          <div class="icon"><span class="fa fa-chevron-down"></span></div>
                          <select name="" id="" class="form-control" v-model="serviceName">
                            <option disabled value="">Please select one</option>
                            <option v-for="service in services" :key=service.serviceName>
                                {{ service.name }}
                          </option>
                          </select>

                  

                          <!-- <select name="" id="" class="form-control">
                            <option value="">Select service</option>
                            <option value="">Change Oil</option>
                            <option value="">Engine Repair</option>
                            <option value="">Battery Replace</option>
                            <option value="">Change Tire</option>
                            <option value="">Tow Truck</option>
                          </select> -->
                        </div>
                      </div>
                    </div>
                  </div>
              
                  <div class="col-md-12">
                    <div class="form-group">
                      <h2> {{errorDeleteService}} </h2>

                    </div>
                  </div>

                  <div class="col-md-12">
                    <div class="form-group">
                      <button 
                      type="button" 
                      class="btn btn-dark py-3 px-4"
                      v-bind:disabled="!serviceName"
                      @click="deleteservice(serviceName)">Delete the Service</button>
                      <!-- <input type="submit" value="Delete the Service" class="btn btn-dark py-3 px-4"> -->
                    </div>
                  </div>

                <!-- <div class="col-md-12">
									<div class="form-group">
                    <button 
                      type="button" 
                      class="btn btn-dark py-3 px-4"
                      @click="addservice(serviceName, duration, price)">Add the Service</button>
                  </div>
								</div> -->

                  <div class="col-md-12">
									<div class="form-group">
                    <h5 v-if="errorDeleteService" style="color:red; padding-top:20px">Error: {{errorDeleteService}}</h5> 
                  </div>
								  </div>

                  
                </div>
              </form>
            </div>
          </div>
        </div>

    </section>

 
<!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
<!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->

<br>

 <section class="service">
    	<div class="container">
    		<div class="row justify-content-center pb-5 mb-3">
          <div class="col-md-7 heading-section text-center">
          	<span class="subheading">We offer Services</span>
            <h2>Our car services</h2>
          </div>
        </div>
    		<div class="row">
          <div class="col-md-4 services" v-for="service in services" :key=service.serviceName>
            <div class="d-block d-flex" >
              
              <div class="icon d-flex justify-content-center align-items-center">
            		<!-- <span class="flaticon-car-service"></span> -->
                <span class="flaticon-repair"></span>
              </div>

              <div class="media-body pl-3" >

                <h3 class="heading">{{service.name}}</h3>
                <p>Duration: {{service.duration}} minutes</p>
                <p>Price: {{service.price}} $</p>
                <p v-if="service.rating === -1.0" > Rating: N/A </p>
                <p v-else> Rating: {{service.rating}} </p>
                <!-- <p><a href="#" class="btn-custom">Read more</a></p> -->
              </div>
            </div>
          </div>

            <!-- <div class="d-block d-flex">
              <div class="icon d-flex justify-content-center align-items-center">
            		<span class="flaticon-tyre"></span>
              </div>
              <div class="media-body pl-3">
                <h3 class="heading">Tire Change</h3>
                <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
                <p><a href="#" class="btn-custom">Read more</a></p>
              </div>
            </div>

          </div> -->
          <!-- <div class="col-md-4 services">
            <div class="d-block d-flex">
              <div class="icon d-flex justify-content-center align-items-center">
            		<span class="flaticon-battery"></span>
              </div>
              <div class="media-body pl-3">
                <h3 class="heading">Batteries</h3>
                <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
                <p><a href="#" class="btn-custom">Read more</a></p>
              </div>
            </div>
            <div class="d-block d-flex">
              <div class="icon d-flex justify-content-center align-items-center">
            		<span class="flaticon-car-engine"></span>
              </div>
              <div class="media-body pl-3">
                <h3 class="heading">Engine Repair</h3>
                <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
                <p><a href="#" class="btn-custom">Read more</a></p>
              </div>
            </div>
          </div>

          <div class="col-md-4 services">
            <div class="d-block d-flex">
              <div class="icon d-flex justify-content-center align-items-center">
            		<span class="flaticon-tow-truck"></span>
              </div>
              <div class="media-body pl-3">
                <h3 class="heading">Tow Truck</h3>
                <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
                <p><a href="#" class="btn-custom">Read more</a></p>
              </div>
            </div>
            <div class="d-block d-flex">
              <div class="icon d-flex justify-content-center align-items-center">
            		<span class="flaticon-repair"></span>
              </div>
              <div class="media-body pl-3">
                <h3 class="heading">Car Maintenance</h3>
                <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
                <p><a href="#" class="btn-custom">Read more</a></p>
              </div>
            </div>
          </div> -->
        </div>
    	</div>
    </section>

<!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
<!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->



	<section class="ftco-section testimony-section bg-light">
      <div class="container">
        <div class="row justify-content-center pb-5 mb-3">
          <div class="col-md-7 heading-section heading-section-white text-center">
          	<span class="subheading">Testimonies</span>
            <h2>Happy Clients &amp; Feedbacks</h2>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <div class="carousel-testimony">
              <div class="item">
                <div class="testimony-wrap py-4">
                	<div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-quote-left"></span></div>
                  <div class="text">
                    <p class="mb-4">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                    <div class="d-flex align-items-center">
                    	<div class="user-img" style="background-image: url(../static/images/person_1.jpg)"></div>
                    	<div class="pl-3">
		                    <p class="name">Roger Scott</p>
		                    <span class="position">Marketing Manager</span>
		                  </div>
	                  </div>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="testimony-wrap py-4">
                	<div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-quote-left"></span></div>
                  <div class="text">
                    <p class="mb-4">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                    <div class="d-flex align-items-center">
                    	<div class="user-img" style="background-image: url(../static/images/person_2.jpg)"></div>
                    	<div class="pl-3">
		                    <p class="name">Roger Scott</p>
		                    <span class="position">Marketing Manager</span>
		                  </div>
	                  </div>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="testimony-wrap py-4">
                	<div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-quote-left"></span></div>
                  <div class="text">
                    <p class="mb-4">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                    <div class="d-flex align-items-center">
                    	<div class="user-img" style="background-image: url(../static/images/person_3.jpg)"></div>
                    	<div class="pl-3">
		                    <p class="name">Roger Scott</p>
		                    <span class="position">Marketing Manager</span>
		                  </div>
	                  </div>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="testimony-wrap py-4">
                	<div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-quote-left"></span></div>
                  <div class="text">
                    <p class="mb-4">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                    <div class="d-flex align-items-center">
                    	<div class="user-img" style="background-image: url(../static/images/person_1.jpg)"></div>
                    	<div class="pl-3">
		                    <p class="name">Roger Scott</p>
		                    <span class="position">Marketing Manager</span>
		                  </div>
	                  </div>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="testimony-wrap py-4">
                	<div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-quote-left"></span></div>
                  <div class="text">
                    <p class="mb-4">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                    <div class="d-flex align-items-center">
                    	<div class="user-img" style="background-image: url(../static/images/person_2.jpg)"></div>
                    	<div class="pl-3">
		                    <p class="name">Roger Scott</p>
		                    <span class="position">Marketing Manager</span>
		                  </div>
	                  </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
	</div>
</template>

<script src="./js/owner.js"></script>

<!-- <section class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img" style="background-image: url(../static/images/bg_3.jpg);">
			<div class="overlay"></div>
    	<div class="container">
    		<div class="row d-md-flex justify-content-end">
    			<div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
    				<span class="subheading">Booking an Appointment</span>
    				<h2 class="mb-4">Free Consultation</h2>
    				<form action="#" class="appointment">
    					<div class="row">
    						<div class="col-md-12">
									<div class="form-group">
			    					<div class="form-field">
	          					<div class="select-wrap">
	                      <div class="icon"><span class="fa fa-chevron-down"></span></div>
	                      <select name="" id="" class="form-control">
	                      	<option value="">Select services</option>
	                        <option value="">Change Oil</option>
	                        <option value="">Engine Repair</option>
	                        <option value="">Battery Replace</option>
	                        <option value="">Change Tire</option>
	                        <option value="">Tow Truck</option>
	                      </select>
	                    </div>
			              </div>
			    				</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
			              <input type="text" class="form-control" placeholder="Your Name">
			            </div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
			              <input type="text" class="form-control" placeholder="Vehicle number">
			            </div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
			    					<div class="input-wrap">
			            		<div class="icon"><span class="fa fa-calendar"></span></div>
			            		<input type="text" class="form-control appointment_date" placeholder="Date">
		            		</div>
			    				</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
			    					<div class="input-wrap">
			            		<div class="icon"><span class="fa fa-clock-o"></span></div>
			            		<input type="text" class="form-control appointment_time" placeholder="Time">
		            		</div>
			    				</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
			              <textarea name="" id="" cols="30" rows="7" class="form-control" placeholder="Message"></textarea>
			            </div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
			              <input type="submit" value="Send message" class="btn btn-dark py-3 px-4">
			            </div>
								</div>
    					</div>
	          </form>
    			</div>
    		</div>
    	</div>
    </section> -->