<template>
	<div id="Owner">

 <section class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img" style="background-image: url(../static/images/image_4.jpg);">
			<div class="overlay"></div>
    	<div class="container">
    		<div class="row d-md-flex justify-content-end">
    			<div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
    				<h2 class="mb-4">Business Information</h2>
    				<form action="#" class="appointment">
    					<div class="row">
    						<div class="col-md-12">
									<div class="form-group">
			    					<div class="form-field">
	          					<div class="select-wrap">
	                    </div>
			              </div>
			    				</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
			              <input type="text" class="form-control" v-model="name" placeholder="Business Name">
			            </div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
			              <input type="text" class="form-control" v-model="email" placeholder="E-mail">
			            </div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
			              <input type="text" class="form-control" v-model="address" placeholder="Address">
			            </div>			            
								</div>
								<div class="col-md-6">
									<div class="form-group">
			              <input type="text" class="form-control" v-model="phoneNumber" placeholder="Phone Number">
			            </div>			            
								</div>
                <div class="col-md-12">
									<div class="form-group">
                    <h5 v-if="errorBusiness" style="color:red; padding-top:20px">Error: {{errorBusiness}}</h5> 
                  </div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
                    <button 
                      type="button" 
                      class="btn btn-dark py-3 px-4"
					  v-bind:disabled="!email||!address||!phoneNumber"
                      @click="registerBusiness(name, email, address, phoneNumber)">Confirm</button>
                  </div>
								</div>
    					</div>
	          </form>
            <br>
    				<form action="#" class="appointment">
    					<div class="row">
    						<div class="col-md-12">
									<div class="form-group">
			    					<div class="form-field">
	          					<div class="select-wrap">
	                    </div>
			              </div>
			    				</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
                  
                  </div>
								</div>
    					</div>
	          </form>
    			</div>
    		</div>
    	</div>
    </section>
        


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
                      <input type="number" min="0" class="form-control" v-model="duration" placeholder="Duration in minutes">
		            		</div>
			    				</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
			    					<div class="input-wrap">
			            		<input type="number" min="0" step="0.1" class="form-control" v-model="price" placeholder="Price">
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
                           <select name="" id="" class="form-control" v-model="serviceName2">
                            <option disabled value="">Please select one</option>
                            <option v-for="service in services" :key=service.serviceName>
                                {{ service.name }}
                          </option>
                          </select>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap">
                        <input type="number" min="0" class="form-control" v-model="duration2" placeholder="New duration in minutes">
                      </div>
                    </div>
                  </div>

                  <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap">
                        <input type="number" min="0" step="0.1" class="form-control" v-model="price2" placeholder="New price">
                      </div>
                    </div>
                  </div>
         
                  <div class="col-md-12">
									<div class="form-group">
                    <button 
                      type="button" 
                      class="btn btn-dark py-3 px-4"
                      @click="updateservice(serviceName2, duration2, price2)">Update the Service</button>
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
                          <select name="" id="" class="form-control" v-model="serviceName3">
                            <option disabled value="">Please select one</option>
                            <option v-for="service in services"
                             
                             :key=service.serviceName>
                                {{ service.name }}
                          </option>
                          </select>

                        </div>
                      </div>
                    </div>
                  </div>

 <!-- v-bind:disabled="!serviceName3" -->

                  <div class="col-md-12">
                    <div class="form-group">
                      <button 
                      type="button" 
                      class="btn btn-dark py-3 px-4"
                      v-bind:disabled="!serviceName3"
                      @click="deleteservice(serviceName3)">Delete the Service</button>
                    </div>
                  </div>

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
        </div>
    	</div>
    </section>

<!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
<!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->



	 <section class="ftco-section testimony-section bg-light">
      <div class="container">
        <div class="row justify-content-center pb-5 mb-3">
          <div
            class="col-md-7 heading-section heading-section-white text-center"
          >
            <span class="subheading">Client Feedbacks</span>
            <h2>Reviews</h2>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <div class="carousel-testimony">
              <div class="item" v-for="review in reviews" :key="review.id">
                <div class="testimony-wrap py-4">
                  <div
                    class="icon d-flex align-items-center justify-content-center"
                  >
                    <span class="fa fa-quote-left"></span>
                  </div>
                  <div class="text">
                    <p class="mb-4">{{ review.description }}</p>
                    <div class="d-flex align-items-center">
                      <div
                        class="user-img"
                        style="
                          background-image: url(../static/images/person_1.jpg);
                        "
                      ></div>
                      <div class="pl-3">
                        <p class="name">{{ review.service.name }}</p>
                        <span class="position"
                          >{{ review.customer.profile.firstName }}
                          {{ review.customer.profile.lastName }}</span
                        >
                        <br />
                        <span class="position"
                          >Rating: {{ review.serviceRating }}</span
                        >
                        <br />
                        <span class="position">{{
                          review.appointment.timeSlot.startDate
                        }}</span>
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

 
<!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
<!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
	</div>
</template>

<script src="./js/owner.js"></script>
