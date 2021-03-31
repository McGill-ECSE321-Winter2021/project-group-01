<template>
  <div id="app">
    <body>
      <div class="wrap">
        <div class="container">
          <div class="row justify-content-between">
            <div class="col-md-3 d-flex align-items-center">
              <a class="navbar-brand" href="index.html">{{businessName}}<span>.</span></a>
            </div>
            <div class="col-md-7">
              <div class="row">
                <div class="col">
                  <div class="top-wrap d-flex">
                    <div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-location-arrow"></span></div>
                    <div class="text"><span style= "color:white;">Address</span><span>{{address}}</span></div>
                  </div>
                </div>
                <div class="col">
                  <div class="top-wrap d-flex">
                    <div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-location-arrow"></span></div>
                    <div class="text"><span style= "color:white;">Call us</span><span>{{phoneNumber}}</span></div>
                  </div>
                </div>
                <div class="col-md-3 d-flex justify-content-end align-items-center">
                  <div class="social-media">
                    <p class="mb-0 d-flex">
                      <a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-facebook"><i class="sr-only">Facebook</i></span></a>
                      <a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-twitter"><i class="sr-only">Twitter</i></span></a>
                      <a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-instagram"><i class="sr-only">Instagram</i></span></a>
                      <a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-dribbble"><i class="sr-only">Dribbble</i></span></a>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      
      <router-view></router-view>
      
      <footer class="footer ftco-section">
        <div class="container">
          <div class="row mb-5">
            <div class="col-md-6 col-lg">
              <div class="ftco-footer-widget mb-4">
                <h2 class="logo"><a href="#">{{businessName}}<span>.</span></a></h2>
                <ul class="ftco-footer-social list-unstyled float-md-left float-lft mt-4">
                  <li><a href="#"><span class="fa fa-twitter"></span></a></li>
                  <li><a href="#"><span class="fa fa-facebook"></span></a></li>
                  <li><a href="#"><span class="fa fa-instagram"></span></a></li>
                </ul>
              </div>
            </div>
            <div class="col-md-6 col-lg">
              <div class="ftco-footer-widget mb-4 ml-md-5">
                <h2 class="ftco-heading-2">Services</h2>
                <ul class="list-unstyled">
                  <li  v-for="service in services" :key=service.serviceName><a href="#" class="py-1 d-block"><span class="fa fa-check mr-3"></span>{{service.name}}</a></li>
                </ul>
              </div>
            </div>
            <div class="col-md-6 col-lg">
              <div class="ftco-footer-widget mb-4">
                <h2 class="ftco-heading-2">Contact information</h2>
                <div class="block-23 mb-3">
                  <ul>
                    <li><span class="icon fa fa-map-marker"></span><span class="text">{{address}}</span></li>
                    <li><a href="#"><span class="icon fa fa-phone"></span><span class="text">{{phoneNumber}}</span></a></li>
                    <li><a href="#"><span class="icon fa fa-paper-plane"></span><span class="text">{{email}}</span></a></li>
                  </ul>
                </div>
              </div>
            </div>
            <div class="col-md-6 col-lg">
              <div class="ftco-footer-widget mb-4">
                <h2 class="ftco-heading-2">Business Hours</h2>
                <div class="opening-hours">
                  <h4>Opening Days:</h4>
                  <p class="pl-3"  v-for="hour in businessHours" :key=hour.dayOfWeek>
                    <span>{{hour.dayOfWeek}}: {{hour.startTime}} to {{hour.endTime}}</span>
                  </p>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12 text-center">
            </div>
          </div>
        </div>
      </footer>
    </body>
  </div>
</template>


<script src="./components/js/App.js"></script>

