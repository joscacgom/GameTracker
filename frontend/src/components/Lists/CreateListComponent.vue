<template>
    <div class="popup-container" v-if="showPopup">
      <div class="popup-content">
        <div class="header-container">
            <button @click="closePopup" class="close-button">
                <font-awesome-icon icon="close" />
            </button>
             <h3 class="title">Create a new list</h3>
             <p>You can add games to your status list reaching out the Discover section.</p>
        </div>        
        <form @submit.prevent="submitForm">
          <label for="status">Status Name</label>
          <input type="text" id="status" v-model="status" placeholder="Example: Pending, completed, wishlist..." required>
          <div class="button-container">
            <button type="submit" class="create-button" :disabled="isCreating">
                <font-awesome-icon icon="plus-circle" />
                {{ isCreating ? 'Creating...' : 'Create' }}
            </button>
          </div>
          <p v-if="error" class="error">{{ error }}</p>
    
        </form>
      </div>
    </div>
  </template>
  
  
  <script>
  import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
  export default {
    name: 'CreateListComponent',
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
        status: '',
        error: '',
        isCreating: false,
      };
    },
    methods: {
      closePopup() {
        this.$emit('close');
      },
      async submitForm() {
        try {
          this.isCreating = true;
          this.error = '';

          const requestBody = {
            user:{
              id: sessionStorage.getItem('userId'),
              username: sessionStorage.getItem('username')
            },
            status: this.status,
            totalPlaytime: 0,
          };

          // Make the POST request
          const response = await fetch('http://localhost:8080/api/game-lists', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + sessionStorage.getItem('jwtoken')
            },
            body: JSON.stringify(requestBody)
          });

          if (!response.ok) {
          const errorResponseText = await response.text();
          this.error = errorResponseText || 'An error occurred during creating.';
        }else{
          this.$emit('create-list', { status: this.status });
        }

        } catch (error) {
          console.error('An error occurred during creating:', error);
          this.error = 'An error occurred during creating: ' + error.message;
        } finally {
          this.isCreating = false;
        }
            this.status = ''; // Reset the status value
            this.closePopup();
          },
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
    padding: 20px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin: 0 auto; 
}

.popup-container input {
  width: 100%; 
  height: 48px; 
  border: none;
  background: rgba(30, 110, 233, 0.05);
  border-radius: 10px;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  padding: 16px;
  margin: 8px 0px;
  font-family: Poppins;
  box-sizing: border-box;
}

.popup-container label {
  color: rgba(39, 40, 41, 0.3);
  font-family: Poppins;
  font-size: 18px;
  font-weight: 500;
  text-align: left;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding: 0px;
  margin: 8px 0px;
  width: 100%; 
}

  .header-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .header-container p{
    color: rgb(7, 7, 7);
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
  }

  .button-container {
    display: flex;
    justify-content: center;
    gap:1rem;
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
  .create-button{
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
  
  .popup-content h3 {
    margin: 0;
  }
  
  .popup-content button {
    margin-top: 1rem;
  }
  </style>
  