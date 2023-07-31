<template>
    <div class="error-container">
      <span>❌</span>
      <h1 class="error-message">Ups... something went wrong</h1>
    </div>
  </template>

  <style scoped>
  .error-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    margin-left: 10rem;
  }
  .error-container span {
    font-size: 40px;
  }
  .error-message {
    color: rgb(252, 9, 76);
    font-size: 24px;
    font-weight: 600;
    font-family: 'Poppins', sans-serif;
    text-align: center;
  }
  @media screen and (max-width: 768px) {
  .error-container {
    margin-left: 0;
    margin-top: 3rem;
    margin-bottom: 3rem;
  }

  .error-container span {
    font-size: 30px;
  }

  .error-message {
    font-size: 20px;
  }
}

@media screen and (max-width: 480px) {
  .error-container {
    margin-left: 0;
    margin-top: 2rem;
    margin-bottom: 2rem;
  }

  .error-container span {
    font-size: 24px;
  }

  .error-message {
    font-size: 16px;
  }
}
  </style>
  