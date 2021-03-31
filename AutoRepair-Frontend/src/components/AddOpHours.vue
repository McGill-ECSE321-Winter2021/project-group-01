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
   class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img" 
  style="background-image: url(../static/images/bg_2.jpg);" >
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
			    	<input type="time" class="form-control appointment_time" v-model= "startTime" placeholder="HH:mm">
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


  </template>
