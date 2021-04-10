<template>
  <div id="app">
    <body>
      <div class="wrap">
        <div class="container">
          <div class="row justify-content-between">
            <div class="col-md-3 d-flex align-items-center">
              <a class="navbar-brand" href="index.html"
                >{{ businessName }}<span>.</span></a
              >
            </div>
            <div class="col-md-7">
              <div class="row">
                <div class="col">
                  <div class="top-wrap d-flex">
                    <div
                      class="icon d-flex align-items-center justify-content-center"
                    >
                      <span class="fa fa-location-arrow"></span>
                    </div>
                    <div class="text">
                      <span style="color: white">Address</span
                      ><span>{{ address }}</span>
                    </div>
                  </div>
                </div>
                <div class="col">
                  <div class="top-wrap d-flex">
                    <div
                      class="icon d-flex align-items-center justify-content-center"
                    >
                      <span class="fa fa-location-arrow"></span>
                    </div>
                    <div class="text">
                      <span style="color: white">Call us</span
                      ><span>{{ phoneNumber }}</span>
                    </div>
                  </div>
                </div>
                <div
                  class="col-md-3 d-flex justify-content-end align-items-center"
                >
                  <div class="social-media">
                    <p class="mb-0 d-flex">
                      <a
                        href="#"
                        class="d-flex align-items-center justify-content-center"
                        ><span class="fa fa-facebook"
                          ><i class="sr-only">Facebook</i></span
                        ></a
                      >
                      <a
                        href="#"
                        class="d-flex align-items-center justify-content-center"
                        ><span class="fa fa-twitter"
                          ><i class="sr-only">Twitter</i></span
                        ></a
                      >
                      <a
                        href="#"
                        class="d-flex align-items-center justify-content-center"
                        ><span class="fa fa-instagram"
                          ><i class="sr-only">Instagram</i></span
                        ></a
                      >
                      <a
                        href="#"
                        class="d-flex align-items-center justify-content-center"
                        ><span class="fa fa-dribbble"
                          ><i class="sr-only">Dribbble</i></span
                        ></a
                      >
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

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
              <input
                type="text"
                class="form-control pl-3"
                placeholder="Search"
              />
              <button type="submit" placeholder="" class="form-control search">
                <span class="fa fa-search"></span>
              </button>
            </div>
          </form>
          <div class="collapse navbar-collapse" id="ftco-nav">
            <ul v-if="!username" class="navbar-nav mr-auto">
              <li class="nav-item" :class="{ active: isActive('home') }">
                <router-link class="nav-link" to="/" v-on:click.native="setActive('home')">Home</router-link>
              </li>
              <li class="nav-item" :class="{ active: isActive('owner') }">
                  <router-link class="nav-link" to="/ownersignup" v-on:click.native="setActive('owner')">Owner</router-link>
              </li>
              <li class="nav-item" :class="{ active: isActive('assistant') }">
                <router-link class="nav-link" to="/assistantsignup" v-on:click.native="setActive('assistant')">Assistant</router-link>
              </li>
              <li class="nav-item" :class="{ active: isActive('login') }">
                <router-link class="nav-link" to="/login" v-on:click.native="setActive('login')">Login</router-link>
              </li>
              <li class="nav-item" :class="{ active: isActive('signup') }">
                <router-link class="nav-link" to="/signup" v-on:click.native="setActive('signup')">Signup</router-link>
              </li>
            </ul>

            <ul v-else-if="type == 'customer'" class="navbar-nav mr-auto">
              <li class="nav-item" :class="{ active: isActive('home') }">
                <router-link class="nav-link" to="/customer" v-on:click.native="setActive('home')">Home</router-link>
              </li>
              <li class="nav-item" :class="{ active: isActive('appointment') }">
                <router-link class="nav-link" to="/appointments" v-on:click.native="setActive('appointment')"
                  >Appointment</router-link
                >
              </li>
              <li class="nav-item" :class="{ active: isActive('review') }">
                <router-link class="nav-link" to="/review" v-on:click.native="setActive('review')">Review</router-link>
              </li>
              <li class="nav-item" :class="{ active: isActive('account') }">
                <router-link class="nav-link" to="/account" v-on:click.native="setActive('account')"
                  >Account</router-link
                >
              </li>

              <li class="nav-item">
                <router-link
                  class="nav-link"
                  to="/"
                  v-on:click.native="logout()"
                  >Logout</router-link
                >
              </li>
            </ul>

            <ul v-else-if="type == 'assistant'" class="navbar-nav mr-auto">
              <li class="nav-item" :class="{ active: isActive('home') }">
                <router-link class="nav-link" to="/assistant" v-on:click.native="setActive('home')">Home</router-link>
              </li>

               <li class="nav-item" :class="{ active: isActive('management') }">
                <router-link class="nav-link" to="/assistantmanagement" v-on:click.native="setActive('management')">Manage</router-link>
              </li>

               <li class="nav-item">
                <router-link
                  class="nav-link"
                  to="/"
                  v-on:click.native="logout()"
                  >Logout</router-link
                >
              </li>
            </ul>

            <ul v-else-if="type == 'owner'" class="navbar-nav mr-auto">
              <li class="nav-item active">
                <router-link class="nav-link" to="/owner">Home</router-link>
              </li>
               <li class="nav-item">
                <router-link
                  class="nav-link"
                  to="/"
                  v-on:click.native="logout()"
                  >Logout</router-link
                >
              </li>
            </ul>
          </div>
        </div>
      </nav>

      <router-view></router-view>

      <footer class="footer ftco-section">
        <div class="container">
          <div class="row mb-5">
            <div class="col-md-6 col-lg">
              <div class="ftco-footer-widget mb-4">
                <h2 class="logo">
                  <a href="#">{{ businessName }}<span>.</span></a>
                </h2>
                <ul
                  class="ftco-footer-social list-unstyled float-md-left float-lft mt-4"
                >
                  <li>
                    <a href="#"><span class="fa fa-twitter"></span></a>
                  </li>
                  <li>
                    <a href="#"><span class="fa fa-facebook"></span></a>
                  </li>
                  <li>
                    <a href="#"><span class="fa fa-instagram"></span></a>
                  </li>
                </ul>
              </div>
            </div>
            <div class="col-md-6 col-lg">
              <div class="ftco-footer-widget mb-4 ml-md-5">
                <h2 class="ftco-heading-2">Services</h2>
                <ul class="list-unstyled">
                  <li v-for="service in services" :key="service.serviceName">
                    <a href="#" class="py-1 d-block"
                      ><span class="fa fa-check mr-3"></span
                      >{{ service.name }}</a
                    >
                  </li>
                </ul>
              </div>
            </div>
            <div class="col-md-6 col-lg">
              <div class="ftco-footer-widget mb-4">
                <h2 class="ftco-heading-2">Contact information</h2>
                <div class="block-23 mb-3">
                  <ul>
                    <li>
                      <span class="icon fa fa-map-marker"></span
                      ><span class="text">{{ address }}</span>
                    </li>
                    <li>
                      <a href="#"
                        ><span class="icon fa fa-phone"></span
                        ><span class="text">{{ phoneNumber }}</span></a
                      >
                    </li>
                    <li>
                      <a href="#"
                        ><span class="icon fa fa-paper-plane"></span
                        ><span class="text">{{ email }}</span></a
                      >
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <div class="col-md-6 col-lg">
              <div class="ftco-footer-widget mb-4">
                <h2 class="ftco-heading-2">Business Hours</h2>
                <div class="opening-hours">
                  <h4>Opening Days:</h4>
                  <p
                    class="pl-3"
                    v-for="hour in businessHours"
                    :key="hour.dayOfWeek"
                  >
                    <span
                      >{{ hour.dayOfWeek }}: {{ hour.startTime }} to
                      {{ hour.endTime }}</span
                    >
                  </p>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12 text-center"></div>
          </div>
        </div>
      </footer>
    </body>
  </div>
</template>


<script src="./components/js/App.js"></script>