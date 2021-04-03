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
                        <select
                          name="selectedAppointment"
                          id=""
                          class="form-control"
                          v-model="selected"
                        >
                          <option value="">Select An Appointment</option>
                          <option
                            v-for="appointment in appointments"
                            v-bind:value="{
                              serviceName: appointment.service.name,
                              appointmentDate: appointment.timeSlot.startDate,
                              appointmentTime: appointment.timeSlot.startTime,
                            }"
                            :key="appointment.id"
                          >
                            {{ appointment.service.name }};
                            {{ appointment.timeSlot.startDate }};
                            {{ appointment.timeSlot.startTime }}
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
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select
                          name=""
                          id=""
                          class="form-control"
                          v-model="serviceRating"
                        >
                          <option value="">Select a Rating</option>
                          <option value="0">0</option>
                          <option value="1">1</option>
                          <option value="2">2</option>
                          <option value="3">3</option>
                          <option value="4">4</option>
                          <option value="5">5</option>
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
                      v-model="description"
                    ></textarea>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <h5
                      v-if="errorReview"
                      style="color: red; padding-top: 20px"
                    >
                      Error: {{ errorReview }}
                    </h5>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <button
                      type="button"
                      class="btn btn-dark py-3 px-4"
                      v-bind:disabled="
                        !description || !serviceRating || !selected
                      "
                      @click="
                        createReview(
                          description,
                          serviceRating,
                          selected.appointmentDate,
                          selected.appointmentTime
                        )
                      "
                    >
                      Submit
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
          <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
            <h2 class="mb-4">Edit Review</h2>
            <form action="#" class="appointment">
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select
                          name="selectedReview"
                          id=""
                          class="form-control"
                          v-model="selectedEdit"
                        >
                          <option value="">Select A Review</option>
                          <option
                            v-for="review in customerReviews"
                            v-bind:value="{
                              serviceName: review.appointment.service.name,
                              appointmentDate: review.appointment.timeSlot.startDate,
                              appointmentTime: review.appointment.timeSlot.startTime,
                            }"
                            :key="review.id"
                          >
                            {{ review.appointment.service.name }};
							              {{ review.serviceRating }};
                            {{ review.appointment.timeSlot.startDate }};
                            {{ review.appointment.timeSlot.startTime }}
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
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select
                          name=""
                          id=""
                          class="form-control"
                          v-model="editReviewRating"
                        >
                          <option value="">Select a Rating</option>
                          <option value="0">0</option>
                          <option value="1">1</option>
                          <option value="2">2</option>
                          <option value="3">3</option>
                          <option value="4">4</option>
                          <option value="5">5</option>
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
                      v-model="descriptionEdit"
                    ></textarea>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <h5
                      v-if="errorReview"
                      style="color: red; padding-top: 20px"
                    >
                      Error: {{ errorReview }}
                    </h5>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <button
                      type="button"
                      class="btn btn-dark py-3 px-4"
                      v-bind:disabled="
                        !descriptionEdit || !editReviewRating || !selectedEdit
                      "
                      @click="
                        editReview(
                          descriptionEdit,
                          editReviewRating,
                          selectedEdit.appointmentDate,
                          selectedEdit.appointmentTime
                        )
                      "
                    >
                      Submit
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
          <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
            <h2 class="mb-4">Delete Review</h2>
            <form action="#" class="appointment">
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select
                          name="selectedReview"
                          id=""
                          class="form-control"
                          v-model="selectedDelete"
                        >
                          <option value="">Select A Review</option>
                          <option
                            v-for="review in customerReviews"
                            v-bind:value="{
                              appointmentDate: review.appointment.timeSlot.startDate,
                              appointmentTime: review.appointment.timeSlot.startTime,
                            }"
                            :key="review.id"
                          >
                            {{ review.appointment.service.name }};
							{{ review.serviceRating }};
                            {{ review.appointment.timeSlot.startDate }};
                            {{ review.appointment.timeSlot.startTime }}
                          </option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>


                <div class="col-md-12">
                  <div class="form-group">
                    <button
                      type="button"
                      class="btn btn-dark py-3 px-4"
                      v-bind:disabled="
                        !selectedDelete
                      "
                      @click="
                        deleteReview(
                          selectedDelete.appointmentDate,
                          selectedDelete.appointmentTime
                        )
                      "
                    >
                      Delete Review
                    </button>
                  </div>
                </div>
              </div>
            </form>
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
            <span class="subheading">Testimonies</span>
            <h2>Happy Clients &amp; Feedbacks</h2>
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
  </div>
</template>

<script src="./js/review.js">
</script>
