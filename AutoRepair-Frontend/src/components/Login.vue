
<template>
  <section
    class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img"
    style="background-image: url(../static/images/image_6.jpg)"
  >
    <div class="overlay"></div>
    <div class="container">
      <div class="row d-md-flex justify-content-end">
        <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
          <h2 class="mb-4">Login</h2>
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
              <div class="col-md-12">
                <div class="form-group">
                  <h5 v-if="errorLogin" style="color: red; padding-top: 20px">
                    Error: {{ errorLogin }}
                  </h5>
                </div>
              </div>
              <div class="col-md-12">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"
                    v-bind:disabled="!username || !password"
                    @click="login(username, password)"
                  >
                    Login
                  </button>
                </div>
              </div>
            </div>
          </form>
          <br />
        </div>
      </div>
    </div>
  </section>
</template>

<script src="./js/login.js"></script>