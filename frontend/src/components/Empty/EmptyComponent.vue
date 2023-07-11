<template>
    <div class="empty-container">
      <span>💀</span>
      <h1 class="empty-message">No results found.</h1>
    </div>
  </template>

<script>
export default {
  name: 'LoadingComponent',
  props: {
    type: {
      type: String, // big or small
      required: true,
    },
  },
};
</script>

  <style scoped>
  .empty-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap:1rem;
    justify-content: center;
    margin-left: 10rem;
    margin-top: 5rem;
    margin-bottom: 5rem;

  }
  .loading-container.small {
    height: 50vh;
    margin-left: 0;
    margin-top:-2rem;
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

@media screen and (max-width: 768px) {
  .empty-container {
    flex-direction: column;
    margin-top:0;
    margin-bottom:3rem;
  }

  .empty-container span {
    font-size: 30px;
  }

  .empty-message {
    font-size: 16px;
  }
}

@media screen and (max-width: 480px) {
  .empty-container {
    margin-left: 0;
    margin-top: 2rem;
    margin-bottom: 2rem;
  }

  .empty-container span {
    font-size: 24px;
  }

  .empty-message {
    font-size: 14px;
  }
}
 </style>
  