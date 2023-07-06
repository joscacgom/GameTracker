<template>
  <div class="popup-container" v-if="showPopup">
    <div class="popup-content">
      <div class="header-container">
        <button @click="closePopup" class="close-button">
          <font-awesome-icon icon="close" />
        </button>
        <h3 class="title">Select list</h3>
        <p>Select one of your lists to add the game.</p>
      </div>
      <form @submit.prevent="submitForm">
        <div class="lists-container" v-for="list in gameLists" :key="list.id">
          <label><input type="radio" name="r" checked> list.status</label>
          <label><input type="radio" name="r"> list.status</label>
          <label><input type="radio" name="r"> list.status</label>
        </div>
        <div class="button-container">
          <button type="submit" class="select-button">Add</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
  import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

  export default {
    name: 'SelectListComponent',
    components: {
      FontAwesomeIcon
    },
    props: {
      showPopup: {
        type: Boolean,
        required: true,
      },
    },
    data() {
      return {
        gameLists: []
      };
    },
    async mounted() {
      try {
        /* Obtengo las listas de juegos del usuario en sesión */
        const userId = sessionStorage.getItem('userId');
        const response = await fetch(`http://localhost:8080/api/game-lists/user/${userId}`);
        if (!response.ok)
          throw new Error('Unable to fetch users game lists data');
        const data = await response.json();
        this.gameLists = await data;
      } catch(error) {
        console.error(error);
      }
    },
    methods: {
      closePopup() {
        this.$emit('close');
      },
      async submitForm() {
        try {
          /* Añadir el game a la lista seleccionada */
          this.$router.push('/my-lists');
        } catch(error) {
          console.error(error);
        }
      }
    },
  };
</script>
  
<style scoped>
.popup-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);
}

.popup-container form {
  max-width: 400px;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 0 auto;
}

.popup-container input {
  --s: 1em;             /* control the size */
  --c: rgb(252, 9, 76); /* the active color */

  height: var(--s);
  aspect-ratio: 1;
  border: calc(var(--s)/8) solid #939393;
  padding: calc(var(--s)/8);
  background:
     radial-gradient(farthest-side,var(--c) 94%,#0000)
     50%/0 0 no-repeat content-box;
  border-radius: 50%;
  outline-offset: calc(var(--s)/10);
  appearance: none;
  cursor: pointer;
  font-size: inherit;
  transition: .3s;
}

.popup-container input:checked {
  border-color: var(--c);
  background-size: 100% 100%;
}

.popup-container label {
  font-family: Poppins;
  font-size: 18px;
  display: inline-flex;
  align-items: center;
  margin: 15px;
  margin-top: 25px;
  cursor: pointer;
}

.header-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.header-container p {
  color: rgb(34, 35, 35, 0.5);
  font-family: Poppins;
  font-size: 16px;
  font-weight: 500;
  text-align: center;
  margin-bottom: -1rem;
}

.title {
  width: 200px;
  height: 30px;
  color: rgb(252, 9, 76);
  font-family: 'Poppins', sans-serif;
  font-size: 24px;
  font-weight: 600;
  text-align: center;
  margin-left: 10rem;
  margin: 0;
}

.button-container {
  display: flex;
  justify-content: center;
  gap: 1rem;
  align-items: center;
}

.close-button {
  border: none;
  background-color: rgb(252, 9, 76);
  color: #fff;
  cursor: pointer;
  height: 38px;
  width: 48px;
  border-radius: 10px;
  font-family: 'Poppins', sans-serif;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 1rem;
}

.close-button svg {
  width: 24px;
  height: 24px;
}

.select-button {
  border: none;
  background-color: rgb(252, 9, 76);
  color: #fff;
  cursor: pointer;
  height: 48px;
  border-radius: 10px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
  padding: 16px;
  font-family: 'Poppins', sans-serif;
  text-align: center;
  font-size: 18px;
  font-weight: 500;
  line-height: 18px;
}

.popup-content {
  background-color: #fff;
  padding: 0rem 2rem 2rem 2rem;
  border-radius: 4px;
}

.popup-content button {
  margin-top: 1rem;
}

</style>
  