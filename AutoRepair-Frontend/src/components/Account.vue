<template>
  <div id="Owner">
    <section
      class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img"
      style="background-image: url(../static/images/image_6.jpg)"
    >
      <div class="overlay"></div>
      <div class="container">
        <div class="row d-md-flex justify-content-end">
          <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
            <h2 class="mb-4">Edit Profile</h2>
            <form action="#" class="appointment">
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap"></div>
                    </div>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control"
                      v-model="firstName"
                      placeholder="First name"
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control"
                      v-model="lastName"
                      placeholder="Last name"
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <input
                      type="email"
                      class="form-control"
                      v-model="email"
                      placeholder="Email"
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control"
                      v-model="phoneNumber"
                      placeholder="Phone number"
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control"
                      v-model="address"
                      placeholder="Address"
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control"
                      v-model="zipCode"
                      placeholder="Zip code"
                    />
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <h5
                      v-if="errorEditProfile"
                      style="color: red; padding-top: 20px"
                    >
                      Error: {{ errorEditProfile }}
                    </h5>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <button
                      type="button"
                      class="btn btn-dark py-3 px-4"
                      v-bind:disabled="
                        !firstName &&
                        !lastName &&
                        !address &&
                        !zipCode &&
                        !email &&
                        !phoneNumber
                      "
                      @click="
                        editProfile(
                          firstName,
                          lastName,
                          address,
                          zipCode,
                          email,
                          phoneNumber
                        )
                      "
                    >
                      Edit Profile
                    </button>
                  </div>
                </div>
              </div>
            </form>
            <br />
          </div>
        </div>

        <div class="container">
          <div class="row d-md-flex justify-content-end">
            <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
              <h2 class="mb-4">Change Password</h2>
              <form action="#" class="appointment">
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <div class="form-field">
                        <div class="select-wrap"></div>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <input
                        type="password"
                        class="form-control"
                        v-model="oldPassword"
                        placeholder="Enter your current password"
                      />
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="form-group">
                      <input
                        type="password"
                        class="form-control"
                        v-model="newPassword"
                        placeholder="Enter your new Password"
                      />
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="form-group">
                      <input
                        type="password"
                        class="form-control"
                        v-model="confirmPassword"
                        placeholder="Confirm Password"
                      />
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <h5
                        v-if="errorPassword"
                        style="color: red; padding-top: 20px"
                      >
                        Error: {{ errorPassword }}
                      </h5>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <button
                        type="button"
                        class="btn btn-dark py-3 px-4"
                        v-bind:disabled="
                          !oldPassword || !newPassword || !confirmPassword
                        "
                        @click="
                          changePassword(
                            oldPassword,
                            newPassword,
                            confirmPassword
                          )
                        "
                      >
                        Change Password
                      </button>
                    </div>
                  </div>
                </div>
              </form>
              <br />
              <div />
            </div>
          </div>
        </div>
      </div>
    </section>

    <div style="background-color: #262626">
      <br />
      <br />
      <br />
    </div>

    <section
      class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img"
      style="background-image: url(../static/images/bg_3.jpg)"
    >
      <div class="overlay"></div>
      <div class="container">
        <div class="row d-md-flex justify-content-end">
          <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
            <h2 class="mb-4">Add Car</h2>
            <form action="#" class="appointment">
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control"
                      v-model="modelAdd"
                      placeholder="Car Model"
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control"
                      v-model="plateNumberAdd"
                      placeholder="Plate Number"
                    />
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
                          name="carTransmission"
                          id=""
                          class="form-control"
                          v-model="carTransmissionAdd"
                        >
                          <option value="">Select car transmission</option>
                          <option value="Automatic">Automatic</option>
                          <option value="Manual">Manual</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <h5
                      v-if="errorAddCar"
                      style="color: red; padding-top: 20px"
                    >
                      Error: {{ errorAddCar }}
                    </h5>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <button
                      type="button"
                      class="btn btn-dark py-3 px-4"
                      v-bind:disabled="
                        !modelAdd ||
                        !plateNumberAdd ||
                        !carTransmissionAdd 
                      "
                      @click="addCar(modelAdd, plateNumberAdd, carTransmissionAdd)"
                    >
                      Add Car
                    </button>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <h5
                      v-if="errorAddCar"
                      style="color: red; padding-top: 20px"
                    >
                      Error: {{ errorAddCar }}
                    </h5>
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
            <h2 class="mb-4">Remove Car</h2>
            <form action="#" class="appointment">
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select name="" id="" class="form-control" v-model="plateNumberRemove">
                          <option value="">Select car</option>
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
                    <button
                      type="button"
                      class="btn btn-dark py-3 px-4"
                       v-bind:disabled="!plateNumberRemove"
                      @click="removeCar(plateNumberRemove)"
                    >
                      Remove Car
                    </button>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <h5
                      v-if="errorRemoveCar"
                      style="color: red; padding-top: 20px"
                    >
                      Error: {{ errorRemoveCar }}
                    </h5>
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

<script src="./js/Account.js"></script>
