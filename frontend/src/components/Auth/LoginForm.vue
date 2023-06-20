<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <div>
        <label for="email">Email</label>
        <input type="email" id="email" v-model="email" placeholder="Enter your email..." required />
      </div>
      <div>
        <label for="password">Password</label>
        <input type="password" id="password" v-model="password" placeholder="Enter your password..." required />
      </div>
      <button type="submit" :disabled="isLoggingIn">
        {{ isLoggingIn ? 'Logging in...' : 'Login' }}
      </button>
      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </div>
</template>

<script>
export default {
  name: 'LoginForm',
  data() {
    return {
      email: '',
      password: '',
      error: '',
      isLoggingIn: false
    };
  },
  methods: {
    async login() {
      try {
        this.error = '';
        this.isLoggingIn = true;

        // Simulate an API call with async/await
        await new Promise(resolve => setTimeout(resolve, 1000));

        // Check if the email and password are valid
        if (this.email === 'user@example.com' && this.password === 'password') {
          console.log('Logged in with email:', this.email);
        } else {
          this.error = 'Invalid email or password';
        }
      } catch (error) {
        console.error('An error occurred during login:', error);
        this.error = 'An error occurred during login. Please try again later.';
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
  font-family: Poppins sans-serif;
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
  font-family: 'Poppins', sans-serif;
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

  font-family: 'Poppins', sans-serif;
  text-align: center;
  font-size: 18px;
  font-weight: 500;
  line-height: 18px;
  
}

.login-container button:disabled {
  background-color: rgb(241, 112, 148);
  cursor: not-allowed;
}

.error {
  color: red;
  font-size: Poppins;
  text-align: center;
  margin-top: 10px;
}
</style>
