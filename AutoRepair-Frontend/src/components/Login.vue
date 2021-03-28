
<template>
  <div>
    <div class="hero-wrap">
      <div class="hero-image">
        <div class="item" style="height:800px; width: 100%; object-fit:cover; background-image:url(../static/images/bg_2.jpg);" >
      	  <div class="overlay"></div>
          <div class="container">
            <div class="row no-gutters slider-text align-items-center justify-content-start">
	            <div class="col-md-6">
	          	  <div class="text w-100">
	          		  <h2>We are best car repair services</h2>
		              <h1 class="mb-4">Make your car last longer</h1>
		              <p><a href="#" class="btn btn-primary">Book an appointment</a></p>
	              </div>
	            </div>
	          </div>
          </div>
        </div>
      </div>
    </div>

    <h1> Login </h1>
    <form class="login">
      <label for="uname">Username:</label>
      <input 
       type="text" 
       id="uname" 
       name="uname" 
       v-model="username" 
       placeholder="Username" 
       class="divForInput"
       ><br>
           
      <label for="password">Password:</label>
      <input 
      type="password" 
      id="password" 
      name="password" 
      v-model="password" 
      placeholder="Password"
      class="divForInput"
      ><br>
      <button type="button"
      v-bind:disabled="!username || !password"
      @click="login(username, password)">Login</button>
    </form>
    <br> 
  <h4 v-if="errorLogin" style="color:red; padding-top:20px">Error: {{errorLogin}}</h4>  </div>
</template>

<script src="./js/login.js"></script>

<style scoped>
    .login {
        width: 500px;
        /* border: 1px solid #CCCCCC; */
        background-color: #FFFFFF;
        margin: auto;
        margin-top: 50px;
        padding: 20px;
    }
    .divForInput{ /*For Input line only*/
        margin-bottom: 15px;
    }
</style>
