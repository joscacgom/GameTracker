<template>
    <div class="register-container">
      <h2>Sign up</h2>
      <form @submit.prevent="register">
        <div>
          <label for="username">Username</label>
          <input type="username" id="username" v-model="username" placeholder="Enter your username..." required />
        </div>
        <div>
          <label for="email">Email</label>
          <input type="email" id="email" v-model="email" placeholder="Enter your email..." required />
        </div>
        <div>
          <label for="password">Password</label>
          <input type="password" id="password" v-model="password" placeholder="Enter your password..." required />
        </div>
        <div>
          <label for="password2">Repeat Password</label>
          <input type="password" id="password2" v-model="password2" placeholder="Repeat your password..." required />
        </div>
        <button type="submit" :disabled="isRegisterIn">
          {{ isRegisterIn ? 'Register in...' : 'Register' }}
        </button>
        <p class="info" @click="redirectToLogin">
          Already have an account?
        </p>
        <p v-if="error" class="error">{{ error }}</p>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    name: 'RegisterForm',
    data() {
      return {
        email: '',
        username: '',
        password: '',
        password2: '',
        error: '',
        isRegisterIn: false
      };
    },
    methods: {
    redirectToLogin() {
      this.$router.push('/login');
    },
  async register() {
    try {
      this.error = '';
      this.isRegisterIn = true;

      // Check if username is empty
      if (!this.username) {
        this.error = 'Username cannot be empty!';
        return;
      }

      // Check if email is empty
      if (!this.email) {
        this.error = 'Email cannot be empty!';
        return;
      }

      // Check if password is empty
      if (!this.password) {
        this.error = 'Password cannot be empty!';
        return;
      }

      // Check if password2 is empty
      if (!this.password2) {
        this.error = 'Repeat Password cannot be empty!';
        return;
      }

      // Check if password is at least 8 characters
      if (this.password.length < 8) {
        this.error = 'Password must be at least 8 characters!';
        return;
      }

      // Check if passwords match
      if (this.password !== this.password2) {
        this.error = 'Passwords must match!';
        return;
      }

      const requestBody = {
        username: this.username,
        email: this.email,
        password: this.password
      };

       // Make the POST request
       const response = await fetch('http://localhost:8080/authenticate/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
      });

      if (response.ok) {
      this.$router.push('/home');
    } else {
      const errorResponseText = await response.text();
      this.error = errorResponseText || 'An error occurred during login.';
    }

    } catch (error) {
      console.error('An error occurred during register:', error);
      this.error = 'An error occurred during register: ' + error.message;
    } finally {
      this.isRegisterIn = false;
    }
  }
      }
  };
  </script>
  
  <style scoped>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');
  .register-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
  }
  
  .register-container form {
    max-width: 400px;
    width: 100%;
    padding: 20px;
  }
  
  .register-container h2 {
    text-align: center;
    color: rgb(0, 0, 0);
    font-family: Poppins;
    font-size: 36px;
    font-weight: 500;
  }
  
  .register-container div {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    padding: 0px;
  }
  
  .register-container label {
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
  
  .register-container input {
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
  
  .register-container button {
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
  
  .register-container button:disabled {
    background-color: rgb(241, 112, 148);
    cursor: not-allowed;
  }
  
  .error {
    color: red;
    font-size: Poppins;
    text-align: center;
    margin-top: 10px;
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

  .register-container button:hover {
    background-color: rgb(252, 9, 76);
    opacity: 0.8;
    transition: all 0.3s linear;
    animation: pulse 1s ease-in-out infinite;
  }


  @media (max-width: 768px) {
  .register-container {
    padding: 0px 20px;
  }

  .register-container form {
    max-width: 100%;
  }

  .register-container input {
    width: 90%;
  }

  .register-container button {
    width: 100%;
  }

  .register-container label {
    width: 100%;
  }
}
  </style>
  