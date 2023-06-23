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
  </style>
  