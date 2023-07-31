<template>
  <div class="popup-container" v-if="showPopup">
    <div class="popup-content">
      <div class="header-container">
        <button @click="closePopup" class="close-button">
          <font-awesome-icon icon="close" />
        </button>
        <h3 class="title">Select list</h3>
      </div>
      
      <div >
        <div v-if="!gameLists || gameLists.length === 0" class="empty-gameLists">
          <p>First, create your own list to add games.</p>
        </div>
        <div v-else class="content-gameLists">
          <p>Select one of your lists to add the game.</p>
          <form @submit.prevent="submitForm">
            <div class="lists-container" v-for="list in gameLists" :key="list.id">
              <label><input type="radio" name="r" :value="list" v-model="selectedList"> {{ list.status }}</label>
            </div>
            <div class="button-container">
              <button type="submit" class="add-button" :disabled="isAdding">
                <font-awesome-icon icon="plus-circle" />
                {{ isAdding ? 'Adding...' : 'Add game' }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
  import { toast } from 'vue3-toastify';
  import 'vue3-toastify/dist/index.css';

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
        gameLists: [],
        selectedList: null,
        isAdding: false,

      };
    },
    async mounted() {
      try {
        // GET the users game lists
        const userId = sessionStorage.getItem('userId');
        const response = await fetch(`http://localhost:8080/games/lists/user/${userId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + sessionStorage.getItem('jwtoken'),
          },
        });
        if (!response.ok)
          console.log('An error response was received');
        else {
          const data = await response.json();
          this.gameLists = await data;
          
        }
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
          this.isAdding = true;
          // Add the game to the selected list
          if (this.selectedList === null) {
            console.log('User did not select a list');
            return;
          }
          const gameId = this.$route.params.gameId;
          const listId = this.selectedList.id;
          const requestBody = {...this.selectedList, status: this.selectedList.status};
          const response = await fetch(`http://localhost:8080/games/lists/${listId}/add/${gameId}`, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json',
              Authorization: 'Bearer ' + sessionStorage.getItem('jwtoken'),
            },
            body: JSON.stringify(requestBody)
          });
          if (!response.ok) {
            const errorResponseText = await response.text();
            this.error = errorResponseText || 'An error occurred during editing.';
          }
          else {
            toast('Game added successfully!', {
            type: 'success',
            position: 'top-right',
            duration: 3000,
            theme: 'colored',
            icon: {
              name: 'check-circle',
            },
            transition: 'Vue-Toastification__bounce',
          });

          setTimeout(() => {
            this.$router.push('/home');
          }, 3000);
          }
        } catch(error) {
          console.error(error);
        } finally {
          this.closePopup();
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

.popup-content {
  background-color: #fff;
  padding: 0rem 2rem 2rem 2rem;
  border-radius: 4px;
}

.header-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
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
  margin-top: 1rem;
  margin-bottom: 1rem;
}

.close-button svg {
  width: 24px;
  height: 24px;
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

.content-gameLists p {
  color: rgb(34, 35, 35, 0.5);
  font-family: Poppins;
  font-size: 16px;
  font-weight: 500;
  text-align: center;
  margin-bottom: -1rem;
}

.lists-container {
  display: inline-flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;

}

.lists-container label {
  font-family: Poppins;
  font-size: 18px;
  margin: 15px;
  margin-top: 25px;
  cursor: pointer;
}

.lists-container input {
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

.lists-container input:checked {
  border-color: var(--c);
  background-size: 100% 100%;
}

.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 1rem;
}

.add-button {
  border: none;
  background-color: rgb(252, 9, 76);
  color: #fff;
  cursor: pointer;
  height: 48px;
  border-radius: 10px;
  padding: 16px;
  font-family: 'Poppins', sans-serif;
  text-align: center;
  font-size: 18px;
  font-weight: 500;
  line-height: 18px;
}

.close-button:hover {
    background-color: rgb(252, 9, 76);
    opacity: 0.8;
    cursor: pointer;
  }

  .add-button:hover {
    background-color: rgb(252, 9, 76);
    opacity: 0.8;
    cursor: pointer;
  }

  .add-button:disabled {
    background-color: rgb(252, 9, 76);
    opacity: 0.5;
    cursor: not-allowed;
  }

.empty-gameLists p, .added-gameLists p {
  color: rgb(34, 35, 35, 0.5);
  font-family: Poppins;
  font-size: 16px;
  font-weight: 500;
  text-align: center;
  margin-bottom: -1rem;
}

</style>
  