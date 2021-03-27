
<template>
    <div>
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
        <p>  
            <span v-if="errorLogin" style="color:red">Error: {{errorLogin}} </span> 
        </p>
    </div>
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
