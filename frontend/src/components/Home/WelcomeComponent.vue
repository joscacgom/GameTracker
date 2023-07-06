<template>
    <div class="welcome-container">
      <img src="../../assets/logo.png" alt="logo" />
      <div>
        <button type="button" @click="redirectToLogin">Login</button>
        <button type="button" @click="redirectToCreateAccount">Create an account</button>
      </div>
     
    </div>
  </template>
  
  <script>
  export default {
    name: 'WelcomeComponent',
    methods: {
        redirectToLogin() {
        this.$router.push('/login');
    },
      redirectToCreateAccount() {
      this.$router.push('/register');
    },
    }
  };
  </script>
  
  <style scoped>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');
  .welcome-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
  }
  
  .welcome-container h2 {
    text-align: center;
    color: rgb(0, 0, 0);
    font-family: Poppins;
    font-size: 36px;
    font-weight: 500;
  }

  .welcome-container img {
    width: 500px;
}
  
  .welcome-container div {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    max-width: 400px;
    width: 100%;
    padding: 20px;
  }
  
  .welcome-container button {
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
  @media (max-width: 768px) {
  .welcome-container img {
    width: 80%;
  }

  .welcome-container button {
    width: 80%;
  }
}

  </style>
  