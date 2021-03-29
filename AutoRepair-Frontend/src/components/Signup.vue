<template>
  <section
    class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img"
    style="background-image: url(../static/images/image_6.jpg)"
  >
    <div class="overlay"></div>
    <div class="container">
      <div class="row d-md-flex justify-content-end">
        <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
          <h2 class="mb-4">Signup</h2>
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
                    type="text"
                    class="form-control"
                    v-model="username"
                    placeholder="Username"
                  />
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-group">
                  <input
                    type="password"
                    class="form-control"
                    v-model="password"
                    placeholder="Password"
                  />
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-group">
                  <input
                    type="password"
                    class="form-control"
                    v-model="confirmPassword"
                    placeholder="Confirm password"
                  />
                </div>
              </div>
              <div class="col-md-12">
                <div class="form-group">
                  <h4 class="mb-4">Personal information</h4>
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
                  <h4 class="mb-">Car information</h4>
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-group">
                  <input
                    type="text"
                    class="form-control"
                    v-model="model"
                    placeholder="Car Model"
                  />
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-group">
                  <input
                    type="text"
                    class="form-control"
                    v-model="plateNumber"
                    placeholder="Plate Number"
                  />
                </div>
              </div>
              <!-- <div class="col-md-12">
                <div class="form-group">
                  <input
                    type="text"
                    class="form-control"
                    v-model="carTransmission"
                    placeholder="Car transmission"
                  />
                </div>
              </div> -->
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
                        v-model="carTransmission"
                        placeholder="Car transmission"
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
                  <h1>{{ carTransmission }}</h1>
                </div>
              </div>
              <div class="col-md-12">
                <div class="form-group">
                  <h5 v-if="errorSignup" style="color: red; padding-top: 20px">
                    Error: {{ errorSignup }}
                  </h5>
                </div>
              </div>
              <div class="col-md-12">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"
                    v-bind:disabled="
                      !username ||
                      !password ||
                      !confirmPassword ||
                      !firstName ||
                      !lastName ||
                      !address ||
                      !zipCode ||
                      !email ||
                      !phoneNumber ||
                      !model ||
                      !plateNumber ||
                      !carTransmission
                    "
                    @click="
                      signup(
                        username,
                        password,
                        confirmPassword,
                        firstName,
                        lastName,
                        address,
                        zipCode,
                        email,
                        phoneNumber,
                        model,
                        plateNumber,
                        carTransmission
                      )
                    "
                  >
                    Signup
                  </button>
                </div>
              </div>
            </div>
          </form>
          <br />
          <h3 class="mb-4">Already have an account?</h3>
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
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"
                    onClick="location.href='/#/login'"
                  >
                    Sign in here!
                  </button>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </section>
</template>

<script src="./js/signup.js"></script>