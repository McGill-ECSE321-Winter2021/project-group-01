<<<<<<< HEAD
 
<template>
  <div id="hello">
    <nav
      class="navbar navbar-expand-lg navbar-light ftco_navbar bg-light ftco-navbar-light"
      id="ftco-navbar"
    >
      <div class="container">
        <button
          class="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#ftco-nav"
          aria-controls="ftco-nav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="fa fa-bars"></span> Menu
        </button>
        <form action="#" class="searchform order-lg-last">
          <div class="form-group d-flex">
            <input type="text" class="form-control pl-3" placeholder="Search" />
            <button type="submit" placeholder="" class="form-control search">
              <span class="fa fa-search"></span>
            </button>
          </div>
        </form>
        <div class="collapse navbar-collapse" id="ftco-nav">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <router-link class="nav-link" to="/">Home</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/owner">Owner</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/services"
                >Services</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/assistant"
                >Assistant</router-link
              >
            </li>

            <li class="nav-item">
              <router-link class="nav-link" to="/login">Login</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/login">Contact</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <!-- END nav -->

    <section
      class="ftco-appointment ftco-no-pt ftco-no-pb img;"
      style="background-image: url(../static/images/mechanicHand.jpg)"
    >
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
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select
                          name=""
                          id=""
                          v-model="dayOfWeek"
                          class="form-control"
                        >
                          <option value="">Select Day</option>
                          <!-- fix later -->
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
                      <h5>Start Time</h5>
                      <input
                        type="time"
                        class="form-control appointment_time"
                        v-model="startTime"
                        placeholder="HH:mm"
                      />
                    </div>
                  </div>
                </div>

                <div class="col-md-5">
                  <div class="form-group">
                    <div class="input-wrap">
                      <h5>End Time</h5>

                      <input
                        type="time"
                        class="form-control appointment_time"
                        v-model="endTime"
                        placeholder="HH:mm"
                      />
                    </div>
                  </div>
                </div>
              </div>

              <div class="col-md-12">
                <div class="form-group">
                  <h5
                    v-if="errorAddOpHours"
                    style="color: red; padding-top: 20px"
                  >
                    Error: {{ errorAddOpHours }}
                  </h5>
                </div>
              </div>

              <div class="col-md-10">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"
                    v-bind:disabled="!dayOfWeek || !startTime || !endTime"
                    @click="addOpHours(dayOfWeek, startTime, endTime)"
                  >
                    Add Operating Hours
                  </button>
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
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select
                          name="new"
                          id=""
                          v-model="dayOfWeek"
                          class="form-control"
                        >
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
                      <h5>New Start Time</h5>

                      <input
                        type="time"
                        class="form-control appointment_time"
                        v-model="startTime1"
                        placeholder="HH:mm"
                      />
                    </div>
                  </div>
                </div>

                <div class="col-md-5">
                  <div class="form-group">
                    <div class="input-wrap">
                      <h5>New End Time</h5>
                      <input
                        type="time"
                        class="form-control appointment_time"
                        v-model="endTime1"
                        placeholder="HH:mm"
                      />
                    </div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <h5
                      v-if="errorEditOpHours"
                      style="color: red; padding-top: 20px"
                    >
                      Error: {{ errorEditOpHours }}
                    </h5>
                  </div>
                </div>

                <div class="col-md-10">
                  <div class="form-group">
                    <button
                      type="button"
                      class="btn btn-dark py-3 px-4"
                      v-bind:disabled="!dayOfWeek"
                      @click="editOpHours(dayOfWeek, startTime1, endTime1)"
                    >
                      Edit Operating Hours
                    </button>
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
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select
                          name=""
                          id=""
                          v-model="dayOfWeek"
                          class="form-control"
                        >
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
                  <h5
                    v-if="errorDeleteOpHours"
                    style="color: red; padding-top: 20px"
                  >
                    Error: {{ errorDeleteOpHours }}
                  </h5>
                </div>
              </div>

              <div class="col-md-10">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"
                    v-bind:disabled="!dayOfWeek"
                    @click="deleteOpHours(dayOfWeek)"
                  >
                    Delete Operating Hours
                  </button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </section>

    <section
      class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img"
      style="background-image: url(../static/images/work-2.jpg)"
    >
      <div style="background-color: #262626">
        <br />
        <br />
        <br />
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
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select name="" id="" class="form-control">
                          <option value="">Select service</option>
                          <option value="">Change Oil</option>
                          <option value="">Engine Repair</option>
                          <option value="">Battery Replace</option>
                          <option value="">Change Tire</option>
                          <option value="">Tow Truck</option>
                        </select>
=======
<template>
  <div id="hello">

    <div class="hero-wrap">
	    <div class="hero-image">
	      <div class="item" style="height:600px; width: 100%; background-size:cover; background-image:url(../static/images/work-4.jpg);" >

	      </div>
	    </div>
	  </div>


		<br>
		<br>
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




    <section class="ftco-counter" id="section-counter">
    	<div class="container">
				<div class="row">
          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ">
            <div class="block-18 text-center">
              <div class="text">
                <strong class="number" data-number="45">4</strong>
              </div>
              <div class="text">
              	<span>Years of Experience</span>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ">
            <div class="block-18 text-center">
              <div class="text">
                <strong class="number" data-number="8500">1043</strong>
              </div>
              <div class="text">
              	<span>Project completed</span>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ">
            <div class="block-18 text-center">
              <div class="text">
                <strong class="number" data-number="2342">300</strong>
              </div>
              <div class="text">
              	<span>Happy Customers</span>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ">
            <div class="block-18 text-center">
              <div class="text">
                <strong class="number" data-number="30">6</strong>
              </div>
              <div class="text">
              	<span>Award Winning</span>
              </div>
            </div>
          </div>
        </div>
    	</div>
    </section>

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
>>>>>>> main
                      </div>
                    </div>
                  </div>
                </div>
<<<<<<< HEAD

                <div class="col-md-6">
                  <div class="form-group">
                    <div class="input-wrap">
                      <input
                        type="number"
                        min="0"
                        class="form-control"
                        placeholder="New duration in minutes"
                      />
                    </div>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-group">
                    <div class="input-wrap">
                      <input
                        type="number"
                        min="0"
                        step="0.01"
                        class="form-control"
                        placeholder="New price"
                      />
                    </div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <input
                      type="submit"
                      value="Update the Service"
                      class="btn btn-dark py-3 px-4"
                    />
                  </div>
                </div>
              </div>
            </form>
=======
              </div>
            </div>
>>>>>>> main
          </div>
        </div>
      </div>
    </section>

<<<<<<< HEAD
    <section
      class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img"
      style="background-image: url(../static/images/bg_3.jpg)"
    >
      <div style="background-color: #262626">
        <br />
        <br />
        <br />
      </div>
      <div class="overlay"></div>
      <div class="container">
        <div class="row d-md-flex justify-content-end">
          <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
            <span class="subheading">Adding a reminder</span>
            <h2 class="mb-4">Plese enter the details for the reminder below</h2>
            <form action="#" class="appointment">
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select name="" id="" class="form-control">
                          <option value="">Select Customer</option>
                          <option value="">C1</option>
                          <option value="">C2</option>
                          <option value="">C3</option>
                          <option value="">C4</option>
                          <option value="">C5</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
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
                <!--<div class="col-md-12">
                    <div class="form-group">
                      <input type="text" class="form-control" placeholder="New Service Name">
                    </div>
                  </div> -->
                <!--	<div class="col-md-6">
                    <div class="form-group">
                      <input type="text" class="form-control" placeholder="Vehicle number">
                    </div>
                  </div> -->

                <div class="col-md-6">
                  <div class="form-group">
                    <div class="input-wrap">
                      <!-- <div class="icon"><span class="fa fa-calendar"></span></div> -->
                      <input
                        type="Date"
                        class="form-control appointment_date"
                        placeholder="Date"
                      />
                    </div>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-group">
                    <div class="input-wrap">
                      <!-- <div class="icon"><span class="fa fa-calendar"></span></div> -->
                      <!--	<input type="text" class="form-control appointment_date" placeholder="Date"> -->
                      <input
                        type="time"
                        class="form-control"
                        placeholder="New duration in minutes"
                      />
                    </div>
                  </div>
                </div>

                <!-- <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap"> -->
                <!-- <div class="icon"><span class="fa fa-clock-o"></span></div> -->
                <!-- <input type="number" min="0" step="0.01" class="form-control" placeholder="New price">
                      </div>
                    </div>
                  </div> -->
                <div class="col-md-12">
                  <div class="form-group">
                    <textarea
                      name=""
                      id=""
                      cols="30"
                      rows="7"
                      class="form-control"
                      placeholder="Description"
                    ></textarea>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <input
                      type="submit"
                      value="Add the reminder"
                      class="btn btn-dark py-3 px-4"
                    />
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
            <span class="subheading">Updating a reminder</span>
            <h2 class="mb-4">Plese enter the details for the reminder below</h2>
            <form action="#" class="appointment">
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select name="" id="" class="form-control">
                          <option value="">Select Customer</option>
                          <option value="">C1</option>
                          <option value="">C2</option>
                          <option value="">C3</option>
                          <option value="">C4</option>
                          <option value="">C5</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select name="" id="" class="form-control">
                          <option value="">
                            Current service of the reminder
                          </option>
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

                <div class="col-md-12">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select name="" id="" class="form-control">
                          <option value="">New service of the reminder</option>
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
                <!--<div class="col-md-12">
                    <div class="form-group">
                      <input type="text" class="form-control" placeholder="New Service Name">
                    </div>
                  </div> -->
                <!--	<div class="col-md-6">
                    <div class="form-group">
                      <input type="text" class="form-control" placeholder="Vehicle number">
                    </div>
                  </div> -->

                <div class="col-md-6">
                  <div class="form-group">
                    <div class="input-wrap">
                      <!-- <div class="icon"><span class="fa fa-calendar"></span></div> -->
                      <input
                        type="Date"
                        class="form-control appointment_date"
                        placeholder="Date"
                      />
                    </div>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-group">
                    <div class="input-wrap">
                      <!-- <div class="icon"><span class="fa fa-calendar"></span></div> -->
                      <!--	<input type="text" class="form-control appointment_date" placeholder="Date"> -->
                      <input
                        type="time"
                        class="form-control"
                        placeholder="New duration in minutes"
                      />
                    </div>
                  </div>
                </div>

                <!-- <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap"> -->
                <!-- <div class="icon"><span class="fa fa-clock-o"></span></div> -->
                <!-- <input type="number" min="0" step="0.01" class="form-control" placeholder="New price">
                      </div>
                    </div>
                  </div> -->
                <div class="col-md-12">
                  <div class="form-group">
                    <textarea
                      name=""
                      id=""
                      cols="30"
                      rows="7"
                      class="form-control"
                      placeholder="Description"
                    ></textarea>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <input
                      type="submit"
                      value="update the reminder"
                      class="btn btn-dark py-3 px-4"
                    />
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
            <span class="subheading">Deleting a reminder</span>
            <h2 class="mb-4">Plese enter the details for the reminder below</h2>
            <form action="#" class="appointment">
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select name="" id="" class="form-control">
                          <option value="">Select Customer</option>
                          <option value="">C1</option>
                          <option value="">C2</option>
                          <option value="">C3</option>
                          <option value="">C4</option>
                          <option value="">C5</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select name="" id="" class="form-control">
                          <option value="">Service of the reminder</option>
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

                <!--  <div class="col-md-12">
                    <div class="form-group">
                      <div class="form-field">
                        <div class="select-wrap">
                          <div class="icon"><span class="fa fa-chevron-down"></span></div>
                          <select name="" id="" class="form-control">
                            <option value="">New service of the reminder</option>
                            <option value="">Change Oil</option>
                            <option value="">Engine Repair</option>
                            <option value="">Battery Replace</option>
                            <option value="">Change Tire</option>
                            <option value="">Tow Truck</option>
                          </select>
                        </div>
                      </div>
                    </div>
                  </div> -->
                <!--<div class="col-md-12">
                    <div class="form-group">
                      <input type="text" class="form-control" placeholder="New Service Name">
                    </div>
                  </div> -->
                <!--	<div class="col-md-6">
                    <div class="form-group">
                      <input type="text" class="form-control" placeholder="Vehicle number">
                    </div>
                  </div> -->

                <!--<div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap"> -->
                <!-- <div class="icon"><span class="fa fa-calendar"></span></div> -->
                <!-- <input type="Date" class="form-control appointment_date" placeholder="Date">
                      </div>
                    </div>
                  </div> -->

                <!-- <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap"> -->
                <!-- <div class="icon"><span class="fa fa-calendar"></span></div> -->
                <!--	<input type="text" class="form-control appointment_date" placeholder="Date"> -->
                <!--    <input type="time" class="form-control" placeholder="New duration in minutes">
                      </div>
                    </div>
                  </div> -->

                <!-- <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap"> -->
                <!-- <div class="icon"><span class="fa fa-clock-o"></span></div> -->
                <!-- <input type="number" min="0" step="0.01" class="form-control" placeholder="New price">
                      </div>
                    </div>
                  </div> -->
                <!-- <div class="col-md-12">
                    <div class="form-group">
                      <textarea name="" id="" cols="30" rows="7" class="form-control" placeholder="Description"></textarea>
                    </div>
                  </div> -->
                <div class="col-md-12">
                  <div class="form-group">
                    <input
                      type="submit"
                      value="Delete the reminder"
                      class="btn btn-dark py-3 px-4"
                    />
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



=======

   <section class="ftco-section bg-light">
    	<div class="container">
    		<div class="row justify-content-center pb-5 mb-3">
          <div class="col-md-7 heading-section text-center ">
          	<span class="subheading">Price &amp; Plans</span>
            <h2>Pricing</h2>
          </div>
        </div>
    		<div class="row">
    			<div class="col-md-6 col-lg-3 " v-for="service in services" :key=service.serviceName>
	          <div class="block-7">
	            <div class="text-center">
            	<span class="excerpt d-block">{{ service.name }}</span>
	            <span class="price"><sup>$</sup> <span class="number">{{ service.price }}</span></span>

	            <a href="#/login" class="btn btn-secondary d-block px-2 py-3">Get Started</a>
	            </div>
	          </div>
	        </div>
	      </div>
    	</div>
    </section>

    <section class="ftco-section ftco-no-pb">
			<div class="container">
				<div class="row justify-content-center pb-5 mb-3">
          <div class="col-md-7 heading-section text-center ">
          	<span class="subheading">Projects</span>
            <h2>Done Projects</h2>
          </div>
        </div>
			</div>
			<div class="container-fluid px-md-0">
				<div class="row no-gutters">
          <div class="col-md-3 ">
            <div class="work img d-flex align-items-end" style="background-image: url(../static/images/work-1.jpg);">
            	<a href="../static/images/work-1.jpg" class="icon image-popup d-flex justify-content-center align-items-center">
	    					<span class="fa fa-expand"></span>
	    				</a>
            	<div class="desc w-100 px-4">
	              <div class="text w-100 mb-3">
	              	<span>Engine</span>
	              	<h2><a href="work-single.html">Engine Testing Complated</a></h2>
	              </div>
              </div>
            </div>
          </div>
          <div class="col-md-3 ">
            <div class="work img d-flex align-items-end" style="background-image: url(../static/images/work-2.jpg);">
            	<a href="../static/images/work-2.jpg" class="icon image-popup d-flex justify-content-center align-items-center">
	    					<span class="fa fa-expand"></span>
	    				</a>
            	<div class="desc w-100 px-4">
	              <div class="text w-100 mb-3">
	              	<span>Oil Change</span>
	              	<h2><a href="work-single.html">Change Oil Completed</a></h2>
	              </div>
              </div>
            </div>
          </div>
          <div class="col-md-3 ">
            <div class="work img d-flex align-items-end" style="background-image: url(../static/images/work-3.jpg);">
            	<a href="../static/images/work-3.jpg" class="icon image-popup d-flex justify-content-center align-items-center">
	    					<span class="fa fa-expand"></span>
	    				</a>
            	<div class="desc w-100 px-4">
	              <div class="text w-100 mb-3">
	              	<span>Engine</span>
	              	<h2><a href="work-single.html">Engine Diagnostics</a></h2>
	              </div>
              </div>
            </div>
          </div>

          <div class="col-md-3 ">
            <div class="work img d-flex align-items-end" style="background-image: url(../static/images/work-4.jpg);">
            	<a href="../static/images/work-4.jpg" class="icon image-popup d-flex justify-content-center align-items-center">
	    					<span class="fa fa-expand"></span>
	    				</a>
            	<div class="desc w-100 px-4">
	              <div class="text w-100 mb-3">
	              	<span>Tire</span>
	              	<h2><a href="work-single.html">Tire Change</a></h2>
	              </div>
              </div>
            </div>
          </div>
        </div>
			</div>
		</section>
  </div>
</template>


<script src="./js/Assistant.js"></script>
>>>>>>> main
