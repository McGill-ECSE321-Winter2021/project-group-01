 <template>


 <div id="hello">
    

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
                    v-model="username"
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
                    v-model="authentificationCode"
                    placeholder="Authentification"
                  />
                </div>
              </div>
            
          
              <div class="col-md-10">
				<div class="form-group">
                    <h5 v-if="errorAssistantSignup" style="color:red; padding-top:20px">
                        Error: {{errorAssistantSignup}}</h5> 
                  </div>
				</div>
              <div class="col-md-10">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"
                    v-bind:disabled="!username || !password || !confirmPassword || !authentificationCode"
                    @click="assistantsignup(username, password, confirmPassword, authentificationCode)">
                        Assistant Signup</button>
                </div>
              </div>
            </div>
          </form>
          <br />
          <h3 class="mb-4">Already created an assistant account?</h3>
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
  		<script src="./js/assistantSignUp.js"></script>
