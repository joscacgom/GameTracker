<template>
  <div :class="['loading-container', type === 'small' ? 'small' : '']">
    <img src="@/assets/joystick.png" alt="logo" class="logo" :class="type === 'small' ? 'small' : ''" />
    <div class="spinner"></div>
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
.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  margin-left: 10rem;
}

.loading-container.small {
  height: 50vh;
  margin-left: 0;
  margin-top:-2rem;
}

.loading-container img {
  width: 100px;
  animation: bounce 1s ease-in-out infinite;
}

.loading-container img.small {
  width: 50px;
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
  0%,
  100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-20px);
  }
}

@media screen and (max-width: 768px) {
  .loading-container {
    margin-left: 0;
    margin-top: 3rem;
    margin-bottom: 3rem;
  }

  .loading-container.small {
    height: 40vh;
    margin-top: -1rem;
  }

  .loading-container img {
    width: 80px;
  }

  .loading-container img.small {
    width: 40px;
  }

  .spinner {
    width: 50px;
    height: 50px;
  }
}

@media screen and (max-width: 480px) {
  .loading-container {
    margin-left: 0;
    margin-top: 2rem;
    margin-bottom: 2rem;
  }

  .loading-container.small {
    height: 30vh;
    margin-top: -0.5rem;
  }

  .loading-container img {
    width: 60px;
  }

  .loading-container img.small {
    width: 30px;
  }

  .spinner {
    width: 40px;
    height: 40px;
  }
}
</style>
