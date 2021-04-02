 
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
                <router-link class="nav-link" to="/services">Services</router-link></li>
              <li class="nav-item">
				<router-link class="nav-link" to="/assistant">Assistant</router-link></li>

              <li class="nav-item">
                <router-link class="nav-link" to="/login">Login</router-link></li>
              <li class="nav-item">
				<router-link class="nav-link" to="/login">Contact</router-link></li>
            </ul>
          </div>
        </div>
        
      </nav>

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
              <div class="col-md-10">
                <div class="form-group">
                  <input
                    type="text"
                    class="form-control"
                    v-model="name"
                    placeholder="Name"
                  />
                </div>
              </div>
              <div class="col-md-5">
                <div class="form-group">
                  <input
                    type="password"
                    class="form-control"
                    v-model="password"
                    placeholder="Password"
                  />
                </div>
              </div>
              <div class="col-md-5">
                <div class="form-group">
                  <input
                    type="password"
                    class="form-control"
                    v-model="confirmPassword"
                    placeholder="Confirm password"
                  />
                </div>
              </div>
              <div class="col-md-10">
                <div class="form-group">
                  <input
                    type="password"
                    class="form-control"
                    v-model="authentification"
                    placeholder="Authentification"
                  />
                </div>
              </div>
            
          
              <div class="col-md-10">
				<div class="form-group">
                    <h5 v-if="errorOwnerSignup" style="color:red; padding-top:20px">
                        Error: {{errorOwnerSignup}}</h5> 
                  </div>
				</div>
              <div class="col-md-10">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"
                    v-bind:disabled="!name || !password || !confirmPassword || !authentification"
                    @click="ownersignup(name, password, confirmPassword, authentification)">
                        Owner Signup</button>
                </div>
              </div>
            </div>
          </form>
          <br />
          <h3 class="mb-4">Already created an owner account?</h3>
          <form action="#" class="appointment">
            <div class="row">
              <div class="col-md-10">
                <div class="form-group">
                  <div class="form-field">
                    <div class="select-wrap"></div>
                  </div>
                </div>
              </div>
              <div class="col-md-10">
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
    </div>

</template>
  		<script src="./js/ownerSignUp.js"></script>
