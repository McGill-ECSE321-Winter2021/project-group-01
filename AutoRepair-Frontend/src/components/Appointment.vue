<template>
 <div id="appointment">
    <section class="ftco-section" style="background-image: url(../static/images/bg_3.jpg); background-size:cover;">
			<div class="overlay"></div>
    	<div class="container">
    		<div class="row d-md-flex justify-content-end">
    			<div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
            <div class="form-group" style="background-color:#e6f3ff; height:350px; width: 600px; text-align: center; border-radius:25px; padding:20px">
              <h2 >Book An Appointment!</h2>
              <form action="#" class="appointment">
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <div class="form-field">
                        <div class="select-wrap">
                          <div class="icon"><span class="fa fa-chevron-down"></span></div>
                          <select name="" id="" class="form-control" v-model="selectedService">
                               <option value="" disabled selected>Select Service</option>
                                <option v-for="service in services" >
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
                        <input type="date" class="form-control appointment_date" placeholder="Date">
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap">
                        <input type="time" class="form-control appointment_time" placeholder="Time">
                      </div>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <input type="submit" value="Book" class="btn btn-dark py-3 px-4">
                    </div>
                  </div>
                  <div class="error-message">
                    <p>
                       <span v-if="errorAppointment" style="color:red; padding-left: 25px;"> Error: {{errorAppointment}} </span>
                    </p>
                  </div>
                </div>
	            </form>
	         </div>
    			</div>
    		</div>
    	</div>
    </section>
    <br>
    <br>
    <br>

    <section class="ftco-section" style="background-image: url(../static/images/work-2.jpg);  background-size:cover;">
    			<div class="overlay"></div>
        	<div class="container">
        			<div class="form-group" style="background-color:#e6f3ff; height:450px; width: 600px; text-align: center; border-radius:25px; padding:20px; margin:center;">
        				<h2 class="mb-4">Update Your Appointment!</h2>
        				<form action="#" class="appointment">
        					<div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <div class="form-field">
                          <div class="select-wrap">
                            <div class="icon"><span class="fa fa-chevron-down"></span></div>
                             <select name="appointmentSelect" id="appointmentSelect" class="form-control" v-model="selectedAppointment">
                               <option value="" disabled selected>Select An Appointment</option>
                                <option v-for="appointment in appointments" >
                                  {{ appointment.toString() }}
                                </option>
                             </select>
                          </div>
                        </div>
                      </div>
                    </div>
        						<div class="col-md-12">
    									<div class="form-group">
    			    					<div class="form-field">
    	          					<div class="select-wrap">
    	                      <div class="icon"><span class="fa fa-chevron-down"></span></div>
    	                      <select name="" id="" class="form-control" v-model="selectedService">
                                 <option value="" disabled selected>Select A New Service</option>
                                  <option v-for="service in services" >
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
    			            		<input type="date" class="form-control appointment_date" placeholder="Date">
    		            		</div>
    			    				</div>
    								</div>
    								<div class="col-md-6">
    									<div class="form-group">
    			    					<div class="input-wrap">
    			            		<input type="time" class="form-control appointment_time" placeholder="Time">
    		            		</div>
    			    				</div>
    								</div>
    								<div class="col-md-12">
    									<div class="form-group">
    			              <input type="submit" value="Update" class="btn btn-dark py-3 px-4">
    			            </div>
    								</div>
    								<div class="error-message">
                      <p>
                         <span v-if="errorUpdateAppointment" style="color:red; padding-left: 25px;">Error: {{errorUpdateAppointment}} </span>
                      </p>
                    </div>
        					</div>
    	          </form>
        			</div>
        	</div>
        </section>

        <br>
        <br>
        <br>
        <section class="ftco-section" style="background-image: url(../static/images/work-1.jpg);  height:667px; background-size:cover;">
            			<div class="overlay"></div>
                	<div class="container">
                			<div class="form-group" style="background-color:#e6f3ff; height:280px; width: 600px; text-align: center; border-radius:25px; padding:20px; ">
                				<h2>Cancel Your Appointment!</h2>
                				<form action="#" class="appointment">
                					<div class="row">
                            <div class="col-md-12">
                              <div class="form-group">
                                <div class="form-field">
                                  <div class="select-wrap">
                                    <div class="icon"><span class="fa fa-chevron-down"></span></div>
                                     <select name="appointmentSelect" id="appointmentSelect" class="form-control" v-model="selectedAppointment">
                                       <option value="" disabled selected>Select An Appointment</option>
                                        <option v-for="appointment in appointments" >
                                          {{ appointment.toString() }}
                                        </option>
                                     </select>
                                  </div>
                                </div>
                              </div>
                            </div>
            								<div class="col-md-12">
            									<div class="form-group">
            			              <input type="submit" value="Cancel" class="btn btn-dark py-3 px-4">
            			            </div>
            								</div>
            								<div class="error-message">
                              <p>
                                 <span v-if="errorCancelAppointment" style="color:red; padding-left: 25px;">Error: {{errorCancelAppointment}} </span>
                              </p>
                            </div>
                					</div>
            	          </form>
                			</div>
                	</div>
                </section>

    </div>
</template>
