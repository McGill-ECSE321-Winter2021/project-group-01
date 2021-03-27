<template>
  <div id="app">
    <body>
      <div class="wrap">
        <div class="container">
          <div class="row justify-content-between">
            <div class="col-md-3 d-flex align-items-center">
              <a class="navbar-brand" href="index.html">Autorepair<span>.</span></a>
            </div>
            <div class="col-md-7">
              <div class="row">
                <div class="col">
                  <div class="top-wrap d-flex">
                    <div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-location-arrow"></span></div>
                    <div class="text"><span>Address</span><span>721 New York NY 10016</span></div>
                  </div>
                </div>
                <div class="col">
                  <div class="top-wrap d-flex">
                    <div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-location-arrow"></span></div>
                    <div class="text"><span>Call us</span><span>(+01) 123 456 7890</span></div>
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
      <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
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
              <li class="nav-item active"><a href="index.html" class="nav-link">Home</a></li>
              <li class="nav-item"><a href="about.html" class="nav-link">About</a></li>
              <li class="nav-item"><a href="services.html" class="nav-link">Services</a></li>
              <li class="nav-item"><a href="project.html" class="nav-link">Project</a></li>
              <li class="nav-item"><a href="blog.html" class="nav-link">Blog</a></li>
              <li class="nav-item"><a href="contact.html" class="nav-link">Contact</a></li>
            </ul>
          </div>
        </div>
      </nav>
      <!-- END nav -->
      
      <router-view></router-view>
      
      <footer class="footer ftco-section">
        <div class="container">
          <div class="row mb-5">
            <div class="col-md-6 col-lg">
              <div class="ftco-footer-widget mb-4">
                <h2 class="logo"><a href="#">Autorepair<span>.</span></a></h2>
                <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                <ul class="ftco-footer-social list-unstyled float-md-left float-lft mt-4">
                  <li class="ftco-animate"><a href="#"><span class="fa fa-twitter"></span></a></li>
                  <li class="ftco-animate"><a href="#"><span class="fa fa-facebook"></span></a></li>
                  <li class="ftco-animate"><a href="#"><span class="fa fa-instagram"></span></a></li>
                </ul>
              </div>
            </div>
            <div class="col-md-6 col-lg">
              <div class="ftco-footer-widget mb-4 ml-md-5">
                <h2 class="ftco-heading-2">Services</h2>
                <ul class="list-unstyled">
                  <li><a href="#" class="py-1 d-block"><span class="fa fa-check mr-3"></span>Oil Change</a></li>
                  <li><a href="#" class="py-1 d-block"><span class="fa fa-check mr-3"></span>Batteries</a></li>
                  <li><a href="#" class="py-1 d-block"><span class="fa fa-check mr-3"></span>Tow Truck</a></li>
                  <li><a href="#" class="py-1 d-block"><span class="fa fa-check mr-3"></span>Tire Change</a></li>
                  <li><a href="#" class="py-1 d-block"><span class="fa fa-check mr-3"></span>Engine Repair</a></li>
                </ul>
              </div>
            </div>
            <div class="col-md-6 col-lg">
              <div class="ftco-footer-widget mb-4">
                <h2 class="ftco-heading-2">Contact information</h2>
                <div class="block-23 mb-3">
                  <ul>
                    <li><span class="icon fa fa-map-marker"></span><span class="text">203 Fake St. Mountain View, San Francisco, California, USA</span></li>
                    <li><a href="#"><span class="icon fa fa-phone"></span><span class="text">+2 392 3929 210</span></a></li>
                    <li><a href="#"><span class="icon fa fa-paper-plane"></span><span class="text">info@yourdomain.com</span></a></li>
                  </ul>
                </div>
              </div>
            </div>
            <div class="col-md-6 col-lg">
              <div class="ftco-footer-widget mb-4">
                <h2 class="ftco-heading-2">Business Hours</h2>
                <div class="opening-hours">
                  <h4>Opening Days:</h4>
                  <p class="pl-3">
                    <span>Monday – Friday : 9am to 20 pm</span>
                    <span>Saturday : 9am to 17 pm</span>
                  </p>
                  <h4>Vacations:</h4>
                  <p class="pl-3">
                    <span>All Sunday Days</span>
                    <span>All Official Holidays</span>
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


<script></script>

