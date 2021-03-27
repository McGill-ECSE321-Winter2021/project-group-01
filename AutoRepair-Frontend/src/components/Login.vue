<template>
	<div id="login">
		<h1> Login </h1>
		   <div class="form-group">
                <label>Username</label>
                <input type="username" v-model="input.username" placeholder="Username" class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" class="form-control form-control-lg" />
            </div>
		<button type="button" v-on:click="login(username, password)">Login</button>
    </div>
</template>

<script src="./js/login.js" ></script>

<style scoped>
    #login {
        width: 500px;
        border: 1px solid #CCCCCC;
        background-color: #FFFFFF;
        margin: auto;
        margin-top: 200px;
        padding: 20px;
    }
</style>