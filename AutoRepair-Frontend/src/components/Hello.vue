<template>
  <section>
    <div class="hero-wrap">
        <div class="home-slider owl-carousel">
          <div class="slider-item" style="background-image:url(images/bg_1.jpg);">
            <div class="overlay"></div>
            <div class="container">
              <div class="row no-gutters slider-text align-items-center justify-content-start">
                <div class="col-md-6 ftco-animate">
                  <div class="text w-100">
                    <h2>We are best car repair services</h2>
                    <h1 class="mb-4">Make your car last longer</h1>
                    <p><a href="#" class="btn btn-primary">Book an appointment</a></p>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="slider-item" style="background-image:url(images/bg_2.jpg);">
            <div class="overlay"></div>
            <div class="container">
              <div class="row no-gutters slider-text align-items-center justify-content-start">
                <div class="col-md-6 ftco-animate">
                  <div class="text w-100">
                    <h2>We care about your car</h2>
                    <h1 class="mb-4">It's time to come to repair your car</h1>
                    <p><a href="#" class="btn btn-primary">Book an appointment</a></p>
                  </div>
                </div>
              </div>
            </div>
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
              <a href="#" class="bg-primary btn-custom d-flex align-items-center"><span>Book an Appointment</span></a>
            </div>
          </div>
        </div>	
      </section>
    </section>
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
