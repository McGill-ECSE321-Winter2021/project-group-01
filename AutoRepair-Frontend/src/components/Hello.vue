<template>
  <div id="hello">
    <nav class="navbar navbar-expand-lg navbar-light ftco_navbar bg-light ftco-navbar-light" id="ftco-navbar">
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
              <li class="nav-item active">
                <router-link class="nav-link" to="/">Home</router-link></li>
              <li class="nav-item">
                <router-link class="nav-link" to="/owner">Owner</router-link></li>
              <li class="nav-item">
                <router-link class="nav-link" to="/test">Services</router-link></li>
              <li class="nav-item">
				<router-link class="nav-link" to="/assistant">Assistant</router-link></li>
              <li class="nav-item">
                <router-link class="nav-link" to="/login">Login</router-link></li>
              <li class="nav-item">
				<router-link class="nav-link" to="/customer">Customer</router-link></li>
            </ul>
          </div>
        </div>
      </nav>
      <!-- END nav -->
    <div class="hero-wrap">
	    <div class="hero-image">
	      <div class="item" style="height:600px; width: 100%; background-size:cover; background-image:url(../static/images/work-4.jpg);" >

	      </div>
	    </div>
	  </div>



		<section class="intro">
			<div class="container intro-wrap">
				<div class="row no-gutters">
					<div class="col-md-12 col-lg-9 bg-intro d-sm-flex align-items-center align-items-stretch">
						<div class="intro-box d-flex align-items-center">
							<div class="icon d-flex align-items-center justify-content-center">
								<i class="flaticon-repair"></i>
							</div>
							<h2 class="mb-0">Are you ready? <span>Let's repair it now!</span></h2>
						</div>
						<a href="#/login" class="bg-primary btn-custom d-flex align-items-center"><span>Book an Appointment</span></a>
					</div>
				</div>
			</div>
		</section>
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
          <div class="col-md-4 services">
            <div class="d-block d-flex">
              <div class="icon d-flex justify-content-center align-items-center">
            		<span class="flaticon-car-service"></span>
              </div>
              <div class="media-body pl-3">
                <h3 class="heading">Oil change</h3>
                <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
                <p><a href="#" class="btn-custom">Read more</a></p>
              </div>
            </div>
            <div class="d-block d-flex">
              <div class="icon d-flex justify-content-center align-items-center">
            		<span class="flaticon-tyre"></span>
              </div>
              <div class="media-body pl-3">
                <h3 class="heading">Tire Change</h3>
                <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
                <p><a href="#" class="btn-custom">Read more</a></p>
              </div>
            </div>

          </div>
          <div class="col-md-4 services">
            <div class="d-block d-flex">
              <div class="icon d-flex justify-content-center align-items-center">
            		<span class="flaticon-battery"></span>
              </div>
              <div class="media-body pl-3">
                <h3 class="heading">Batteries</h3>
                <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
                <p><a href="#" class="btn-custom">Read more</a></p>
              </div>
            </div>
            <div class="d-block d-flex">
              <div class="icon d-flex justify-content-center align-items-center">
            		<span class="flaticon-car-engine"></span>
              </div>
              <div class="media-body pl-3">
                <h3 class="heading">Engine Repair</h3>
                <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
                <p><a href="#" class="btn-custom">Read more</a></p>
              </div>
            </div>
          </div>

          <div class="col-md-4 services">
            <div class="d-block d-flex">
              <div class="icon d-flex justify-content-center align-items-center">
            		<span class="flaticon-tow-truck"></span>
              </div>
              <div class="media-body pl-3">
                <h3 class="heading">Tow Truck</h3>
                <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
                <p><a href="#" class="btn-custom">Read more</a></p>
              </div>
            </div>
            <div class="d-block d-flex">
              <div class="icon d-flex justify-content-center align-items-center">
            		<span class="flaticon-repair"></span>
              </div>
              <div class="media-body pl-3">
                <h3 class="heading">Car Maintenance</h3>
                <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
                <p><a href="#" class="btn-custom">Read more</a></p>
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
              	<span>Years of Experienced</span>
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
          <div class="col-md-7 heading-section heading-section-white text-center">
          	<span class="subheading">Testimonies</span>
            <h2>Happy Clients &amp; Feedbacks</h2>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <div class="carousel-testimony">
              <div class="item">
                <div class="testimony-wrap py-4">
                	<div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-quote-left"></span></div>
                  <div class="text">
                    <p class="mb-4">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                    <div class="d-flex align-items-center">
                    	<div class="user-img" style="background-image: url(../static/images/person_1.jpg)"></div>
                    	<div class="pl-3">
		                    <p class="name">Roger Scott</p>
		                    <span class="position">Marketing Manager</span>
		                  </div>
	                  </div>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="testimony-wrap py-4">
                	<div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-quote-left"></span></div>
                  <div class="text">
                    <p class="mb-4">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                    <div class="d-flex align-items-center">
                    	<div class="user-img" style="background-image: url(../static/images/person_2.jpg)"></div>
                    	<div class="pl-3">
		                    <p class="name">Roger Scott</p>
		                    <span class="position">Marketing Manager</span>
		                  </div>
	                  </div>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="testimony-wrap py-4">
                	<div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-quote-left"></span></div>
                  <div class="text">
                    <p class="mb-4">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                    <div class="d-flex align-items-center">
                    	<div class="user-img" style="background-image: url(../static/images/person_3.jpg)"></div>
                    	<div class="pl-3">
		                    <p class="name">Roger Scott</p>
		                    <span class="position">Marketing Manager</span>
		                  </div>
	                  </div>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="testimony-wrap py-4">
                	<div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-quote-left"></span></div>
                  <div class="text">
                    <p class="mb-4">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                    <div class="d-flex align-items-center">
                    	<div class="user-img" style="background-image: url(../static/images/person_1.jpg)"></div>
                    	<div class="pl-3">
		                    <p class="name">Roger Scott</p>
		                    <span class="position">Marketing Manager</span>
		                  </div>
	                  </div>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="testimony-wrap py-4">
                	<div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-quote-left"></span></div>
                  <div class="text">
                    <p class="mb-4">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                    <div class="d-flex align-items-center">
                    	<div class="user-img" style="background-image: url(../static/images/person_2.jpg)"></div>
                    	<div class="pl-3">
		                    <p class="name">Roger Scott</p>
		                    <span class="position">Marketing Manager</span>
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


    <section class="ftco-section bg-light">
    	<div class="container">
    		<div class="row justify-content-center pb-5 mb-3">
          <div class="col-md-7 heading-section text-center ">
          	<span class="subheading">Price &amp; Plans</span>
            <h2>Pricing</h2>
          </div>
        </div>
    		<div class="row">
    			<div class="col-md-6 col-lg-3 ">
	          <div class="block-7">
	            <div class="text-center">
            	<span class="excerpt d-block">Oil Change</span>
	            <span class="price"><sup>$</sup> <span class="number">10.00</span></span>

	            <div class="pricing-text">
	              <p>A small river named Duden flows by their place and supplies it with the necessary regelialia.</p>
	            </div>

	            <a href="#/login" class="btn btn-secondary d-block px-2 py-3">Get Started</a>
	            </div>
	          </div>
	        </div>
	        <div class="col-md-6 col-lg-3 ">
	          <div class="block-7">
	            <div class="text-center">
            	<span class="excerpt d-block">Batteries</span>
	            <span class="price"><sup>$</sup> <span class="number">83.75</span></span>

	            <div class="pricing-text">
	              <p>A small river named Duden flows by their place and supplies it with the necessary regelialia.</p>
	            </div>

	            <a href="#/login" class="btn btn-secondary d-block px-2 py-3">Get Started</a>
	            </div>
	          </div>
	        </div>
	        <div class="col-md-6 col-lg-3 ">
	          <div class="block-7">
	            <div class="text-center">
            	<span class="excerpt d-block">Tow Truck</span>
	            <span class="price"><sup>$</sup> <span class="number">85.00</span></span>

	            <div class="pricing-text">
	              <p>A small river named Duden flows by their place and supplies it with the necessary regelialia.</p>
	            </div>

	            <a href="#/login" class="btn btn-secondary d-block px-2 py-3">Get Started</a>
	            </div>
	          </div>
	        </div>
	        <div class="col-md-6 col-lg-3 ">
	          <div class="block-7">
	            <div class="text-center">
            	<span class="excerpt d-block">Car Wash</span>
	            <span class="price"><sup>$</sup> <span class="number">30.50</span></span>

	            <div class="pricing-text">
	              <p>A small river named Duden flows by their place and supplies it with the necessary regelialia.</p>
	            </div>

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


<script>
export default {
  name: 'hello',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
