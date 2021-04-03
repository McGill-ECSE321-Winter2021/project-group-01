<template>
  <div id="customer">
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
              <router-link class="nav-link" to="/services"
                >Services</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/appointments"
                >Appointments</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/account">Account</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/logout">Logout</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <section
      class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img"
      style="background-image: url(../static/images/image_4.jpg)"
    >
      <div class="overlay"></div>
      <div class="container">
        <div class="row d-md-flex justify-content-end">
          <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
            <span class="subheading">Want to provide feedback?</span>
            <h2 class="mb-4">Write Review</h2>
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
                          <option value="">Select an Appointment</option>
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
                          <option value="">Select a Rating</option>
                          <option value="">0</option>
                          <option value="">1</option>
                          <option value="">2</option>
                          <option value="">3</option>
                          <option value="">4</option>
                          <option value="">5</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <textarea
                      name=""
                      id=""
                      cols="30"
                      rows="7"
                      class="form-control"
                      placeholder="Provide a description"
                    ></textarea>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <input
                      type="submit"
                      value="Submit"
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
              </div>
            </div>
          </div>

           
        </div>
    	</div>
    </section>

  </div>
</template>

<script src="./js/customer.js"></script>