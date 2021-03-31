 
<template>


 <div id="hello">
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
				<router-link class="nav-link" to="/login">Contact</router-link></li>
            </ul>
          </div>
        </div>
      </nav>
      <!-- END nav -->

 

	<section
   class="ftco-appointment ftco-no-pt ftco-no-pb img;"
  style="background-image: url(../static/images/mechanicHand.jpg);" >
			<div class="overlay"></div>
    	<div class="container">
    		<div class="row d-md-flex justify-content-end">
    			<div class="col-md-10 col-lg-6 half p-4 py-7 pl-lg-8">
    				<h2 class="mb-4">Add Operating Hours</h2>
    				<form action="#" class="appointment">
    					<div class="row">
    						<div class="col-md-10">
									<div class="form-group">
			    					<div class="form-field">
	          					<div class="select-wrap">	
	                      <div class="icon"><span class="fa fa-chevron-down"></span></div>
	                      <select name="" id="" v-model= "dayOfWeek" class="form-control">

	                      	<option value="">Select Day</option> <!-- fix later -->
	                      	<option value="Monday">Monday</option>
	                        <option value="Tuesday">Tuesday</option>
	                        <option value="Wednesday">Wednesday</option>
	                        <option value="Thursday">Thursday</option>
	                        <option value="Friday">Friday</option>
	                        <option value="Saturday">Saturday</option>
                          <option value="Sunday">Sunday</option>
	                      </select>
	                    </div>
			              </div>
			    				</div>
								</div>
		
        				<div class="col-md-5">
									<div class="form-group">
			    					<div class="input-wrap">
                       <h5> Start Time </h5>
			    	<input type="time" class="form-control appointment_time" v-model= "startTime"
					 placeholder="HH:mm">
		            		</div>
			    		</div>
					</div>


				        				<div class="col-md-5">
									<div class="form-group">
			    					<div class="input-wrap">
                       <h5> End Time </h5>

			            		<input type="time" class="form-control appointment_time"
								v-model= "endTime" placeholder="HH:mm">
		            		</div>
			    				</div>
								</div>
	 	 </div>


	  <div class="col-md-12">
				<div class="form-group">
                    <h5 v-if="errorAddOpHours" style="color:red; padding-top:20px">
						Error: {{errorAddOpHours}}</h5> 
                  </div>
				</div>

						
						 <div class="col-md-10">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"

                  v-bind:disabled="!dayOfWeek||!startTime||!endTime"
				  
                    @click="addOpHours(dayOfWeek, startTime, endTime)">
	
					 Add Operating Hours</button>
			            </div>

    					</div>
	          </form>
    			</div>
    		</div>	
    	</div>




    	<div class="container">
    		<div class="row d-md-flex justify-content-end">
    			<div class="col-md-10 col-lg-6 half p-4 py-7 pl-lg-8">
    				<h2 class="mb-4">Edit Operating Hours</h2>
    				<form action="#" class="appointment">
    					<div class="row">
    						<div class="col-md-10">
									<div class="form-group">
			    					<div class="form-field">
	          					<div class="select-wrap">
	                      <div class="icon"><span class="fa fa-chevron-down"></span></div>
	                      <select name="new" id="" v-model= "dayOfWeek1" class="form-control">
	                      	<option value="">Operating Hour to Edit</option> 
	                      	<option value="Monday">Monday</option>
	                        <option value="Tuesday">Tuesday</option>
	                        <option value="Wednesday">Wednesday</option>
	                        <option value="Thursday">Thursday</option>
	                        <option value="Friday">Friday</option>
	                        <option value="Saturday">Saturday</option>
                          <option value="Sunday">Sunday</option>
	                      </select>
	                    </div>
			              </div>
			    				</div>
	  
	  		</div>

								<div class="col-md-5">
									<div class="form-group">
			    					<div class="input-wrap">
                       <h5>New Start Time </h5>

			            		<input type="time" class="form-control appointment_time"
								 v-model= "startTime1"  placeholder="HH:mm">
		            		</div>
			    				</div>
								</div>

                		
								<div class="col-md-5">
									<div class="form-group">
			    					<div class="input-wrap">
                       <h5>New End Time </h5>
			            		<input type="time" class="form-control appointment_time" 
								 v-model= "endTime1" placeholder="HH:mm">
		            		</div>
			    				</div>
								</div>


							  <div class="col-md-12">
				<div class="form-group">
                    <h5 v-if="errorEditOpHours" style="color:red; padding-top:20px">
						Error: {{errorEditOpHours}}</h5> 
                  </div>
				</div>
							
	 <div class="col-md-10">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"
					
                  v-bind:disabled="!dayOfWeek1||!startTime1||!endTime1"
                    @click="editOpHours(dayOfWeek1,startTime1,endTime1)">
					 Edit Operating Hours</button>
    			</div>
				    			</div>
	   </div>


	          </form>
    			</div>
    		</div>
    	</div>


      
    
    		<div class="container">
    		<div class="row d-md-flex justify-content-end">
    			<div class="col-md-10 col-lg-6 half p-4 py-7 pl-lg-8">
    				<h2 class="mb-4">Delete Operating Hours</h2>
    				<form action="#" class="appointment">
    					<div class="row">
    						<div class="col-md-10">
									<div class="form-group">
			    					<div class="form-field">
	          					<div class="select-wrap">
	                      <div class="icon"><span class="fa fa-chevron-down"></span></div>
	                      <select name="" id="" v-model= "dayOfWeek2" class="form-control">
	                      	<option value="">Select Day</option> 
	                      	<option value="Monday">Monday</option>
	                        <option value="Tuesday">Tuesday</option>
	                        <option value="Wednesday">Wednesday</option>
	                        <option value="Thursday">Thursday</option>
	                        <option value="Friday">Friday</option>
	                        <option value="Saturday">Saturday</option>
                          <option value="Sunday">Sunday</option>
	                      </select>
	                    </div>
			              </div>
			    				</div>
								</div>
	  
	  		</div>
						
									  <div class="col-md-12">
				<div class="form-group">
                    <h5 v-if="errorDeleteOpHours" style="color:red; padding-top:20px">
						Error: {{errorDeleteOpHours}}</h5> 
                  </div>
				</div>	

	 <div class="col-md-10">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"

                  v-bind:disabled="!dayOfWeek2"
                    @click="deleteOpHours(dayOfWeek2)">
					 Delete Operating Hours</button>
			            </div>
    			</div>

	          </form>
    		</div>
    	</div>
    	</div>


  </section>
    

    
<section

   class="ftco-section ftco-no-pt ftco-no-pb img" 
  style="background-image: url(../static/images/holiday.jpg);">

<div style="background-color:#262626;">
        <br>
        <br>
        <br>
      </div>

      <div class="overlay"></div>
    	<div class="container">
    			<div class="col-md-10 col-lg-6 half p-4 py-7 pl-lg-7">
    				<h3 class="mb-6">Business Holidays</h3>



  	<div class="col-md-5">
									<div class="form-group">
			    					<div class="input-wrap">
                    <h5>Date</h5>
<input type="date" class="form-control appointment_date" placeholder="Date">		            		
			    				</div>
								</div>  
                	</div>

                	<div class="col-md-5">
									<div class="form-group">
			    					<div class="input-wrap">
                        <h5> Start Time </h5>
			            		<input type="time" class="form-control appointment_time" placeholder="HH:mm">
		            		</div>
			    				</div>
								</div>

                		
								<div class="col-md-5">
									<div class="form-group">
			    					<div class="input-wrap">
                       <h5> End Time </h5>

			            		<input type="time" class="form-control appointment_time" placeholder="HH:mm">
		            		</div>
			    				</div>
								</div>
                								</div>
								<div class="col-md-4">

									<div class="form-group">

			              <input type="submit" value="Add Holiday" class="btn btn-dark py-3 px-5">
			            </div>
			            </div>
                                                  								</div>


						 
    </section>

<section


 class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img" 
 style="background-image: url(../static/images/work-2.jpg);">
 <div style="background-color:#262626;">
        <br>
        <br>
        <br>
      </div>

        <div class="overlay"></div> 

  
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




                </div>
              </form>
            </div>
          </div>
        </div>

</section>
  </div>
  </template>
  		<script src="./js/OpHours.js"></script>



