<template>
    <div class="loading-container">
      <img src="@/assets/joystick.png" alt="logo" class="logo" />
      <div class="spinner"></div>
    </div>
  </template>
  
  <style scoped>
  .loading-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    margin-left: 10rem;
  }
  
  .loading-container img {
    width: 100px;
    animation: bounce 1s ease-in-out infinite;
  }
  
  .spinner {
    width: 60px;
    height: 60px;
    border: 4px solid rgba(0, 0, 0, 0.1);
    border-left-color: rgb(252, 9, 76);
    border-radius: 50%;
    animation: spin 1s linear infinite;
  }
  
  @keyframes spin {
    to {
      transform: rotate(360deg);
    }
  }
  
  @keyframes bounce {
    0%, 100% {
      transform: translateY(0);
    }
    50% {
      transform: translateY(-20px);
    }
  }
  </style>
  