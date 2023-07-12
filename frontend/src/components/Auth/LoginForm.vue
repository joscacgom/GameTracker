<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <div>
        <label for="username">Username</label>
        <input type="username" id="username" v-model="username" placeholder="Enter your username..." required />
      </div>
      <div>
        <label for="password">Password</label>
        <input type="password" id="password" v-model="password" placeholder="Enter your password..." required />
      </div>
      <button type="submit" :disabled="isLoggingIn">
        {{ isLoggingIn ? 'Logging in...' : 'Login' }}
      </button>
      <p class="info" @click="redirectToRegister">
        Need an account?
      </p>
      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </div>
</template>

<script>
export default {
  name: 'LoginForm',
  data() {
    return {
      username: '',
      password: '',
      error: '',
      isLoggingIn: false
    };
  },
  methods: {
    redirectToRegister() {
      this.$router.push('/register');
    },
    async login() {
  try {
    this.error = '';
    this.isLoggingIn = true;

    const requestBody = {
      username: this.username,
      password: this.password
    };

    // Make the POST request
    const response = await fetch('http://localhost:8080/authenticate/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(requestBody)
    });

    if (response.ok) {
      const responseData = await response.json();

      // Save the response data in sessionStorage
      sessionStorage.setItem('jwtoken', responseData.jwtoken);
      sessionStorage.setItem('username', responseData.username);
      sessionStorage.setItem('userId', responseData.id);
      sessionStorage.setItem('email', responseData.email);

      this.$router.push('/home');
    } else {
      const errorResponseText = await response.text();
      this.error = errorResponseText || 'An error occurred during login.';
    }
  } catch (error) {
    console.error('An error occurred during login:', error);
    this.error = 'An error occurred during login: ' + error.message;
  } finally {
    this.isLoggingIn = false;
  }
}

  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
}

.login-container form {
  max-width: 400px;
  width: 100%;
  padding: 20px;
}

.login-container h2 {
  text-align: center;
  color: rgb(0, 0, 0);
  font-family: Poppins;
  font-size: 36px;
  font-weight: 500;
}

.login-container div {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 0px;
}

.login-container label {
  color: rgba(39, 40, 41, 0.3);
  font-family: Poppins;
  font-size: 18px;
  font-weight: 500;
  text-align: left;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding: 0px;
  margin: 8px 0px;
}

.login-container input {
  width: 368px;
  border:none;
  background: rgba(30, 110, 233, 0.05);
  border-radius: 10px;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  padding: 16px;
  margin: 8px 0px;
  font-family: Poppins;
}

.login-container button {
  border: none;
  background-color: rgb(252, 9, 76);
  color: #fff;
  cursor: pointer;
  width: 100%;
  height: 48px;
  border-radius: 10px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 16px;
  margin-top: 10px;
  margin-left: auto;
  margin-right: auto;

  font-family: Poppins;
  text-align: center;
  font-size: 18px;
  font-weight: 500;
  line-height: 18px;
  
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
  100% {
    transform: scale(1);
  }
}

  .login-container button:hover {
    background-color: rgb(252, 9, 76);
    opacity: 0.8;
    transition: all 0.3s linear;
    animation: pulse 1s ease-in-out infinite;
  }

.login-container button:disabled {
  background-color: rgb(241, 112, 148);
  cursor: not-allowed;
}

.info{
  color: rgb(241, 112, 148);
  font-family: Poppins;
  font-size: 16px;
  font-weight: 400;
  text-align: center;
  margin-top: 20px;
  cursor: pointer;
}

.info:hover {
  text-decoration: underline;
}

.error {
  color: red;
  font-size: Poppins;
  text-align: center;
  margin-top: 10px;
}

@media (max-width: 768px) {
  .login-container {
    padding: 0px 20px;
  }

  .login-container form {
    max-width: 100%;
  }

  .login-container input {
    width: 90%;
  }

  .login-container button {
    width: 100%;
  }

  .login-container label {
    width: 100%;
  }
}
</style>
