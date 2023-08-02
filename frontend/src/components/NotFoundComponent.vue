<template>
    <div class="not-found-container">
      <h1 class="not-found-status">404</h1>
      <h2 class="not-found-message">Page Not Found</h2>
    </div>
  </template>
  
  <style scoped>
  .not-found-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
  }
  
  .not-found-message {
    color: rgb(7, 7, 7);
    font-size: 24px;
    font-weight: 600;
    font-family: 'Poppins', sans-serif;
    text-align: center;
    margin-top: -2rem;
  }
  
  .not-found-status {
    color: rgb(252, 9, 76);
    font-size: 100px;
    font-weight: 600;
    font-family: 'Poppins', sans-serif;
    text-align: center;
    animation: shake 0.8s ease-in-out infinite;
  }
  
  @keyframes shake {
    0% {
      transform: translateX(0);
    }
    25% {
      transform: translateX(-10px) rotate(-5deg);
    }
    50% {
      transform: translateX(10px) rotate(5deg);
    }
    75% {
      transform: translateX(-10px) rotate(-5deg);
    }
    100% {
      transform: translateX(0);
    }
  }


@media (max-width: 767px) {
  .not-found-message {
    font-size: 18px;
    margin-top: -1.5rem;
  }

  .not-found-status {
    font-size: 60px;
  }
}
  </style>
  