
<template>
	<div id="login">
		<h2> Login </h2>
		<input type="text" name="username" v-model="input.username" placeholder="Username" />
		<input type="password" name="password" v-model="input.password" placeholder="Password" />
		<button type="button" v-bind:disabled="(!input.username || !input.password)" v-on:click="login(username, password)">Login</button>
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