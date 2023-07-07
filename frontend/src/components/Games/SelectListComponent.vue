<template>
  <div class="popup-container" v-if="showPopup">
    <div class="popup-content">
      <div class="header-container">
        <button @click="closePopup" class="close-button">
          <font-awesome-icon icon="close" />
        </button>
        <h3 class="title">Select list</h3>
      </div>
      <div v-if="added" class="added-gameLists">
        <p>Game added to your list.</p>
      </div>
      <div v-else>
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
            <button type="submit" class="add-button">Add</button>
          </div>
        </form>
      </div>
      </div>
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
        gameLists: [],
        selectedList: null,
        added: false
      };
    },
    async mounted() {
      try {
        // GET the users game lists
        const userId = sessionStorage.getItem('userId');
        const response = await fetch(`http://localhost:8080/api/game-lists/user/${userId}`, {
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
          // Add the game to the selected list
          if (this.selectedList === null) {
            console.log('User did not select a list');
            return;
          }
          const gameId = this.$route.params.gameId;
          const listId = this.selectedList.id;
          const requestBody = {...this.selectedList, status: this.selectedList.status};
          const response = await fetch(`http://localhost:8080/api/game-lists/${listId}/add/${gameId}`, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json',
              Authorization: 'Bearer ' + sessionStorage.getItem('jwtoken'),
            },
            body: JSON.stringify(requestBody)
          });
          if (!response.ok) {
            this.added = false;
            const errorResponseText = await response.text();
            this.error = errorResponseText || 'An error occurred during editing.';
          }
          else {
            this.added = true;
          }
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

.empty-gameLists p, .added-gameLists p {
  color: rgb(34, 35, 35, 0.5);
  font-family: Poppins;
  font-size: 16px;
  font-weight: 500;
  text-align: center;
  margin-bottom: -1rem;
}

</style>
  