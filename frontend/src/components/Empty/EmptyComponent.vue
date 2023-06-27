<template>
    <div class="empty-container">
      <span>💀</span>
      <h1 class="empty-message">No results found.</h1>
    </div>
  </template>

  <style scoped>
  .empty-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap:1rem;
    justify-content: center;
    margin-left: -5rem;
    margin-top: 15rem;
    margin-bottom: 15rem;

  }
  .empty-container span {
    font-size: 40px;
  }
  .empty-message {
    opacity: 0.5;
    color: rgb(252, 9, 76);
    font-size: 20px;
    font-weight: 600;
    font-family: 'Poppins', sans-serif;
    text-align: center;
  }
  </style>
  