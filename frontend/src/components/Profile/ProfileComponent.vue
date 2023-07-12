<template>
  <div class="profile-container">
    <SidebarComponent></SidebarComponent>
    <div class="header-container">
      <h1 class="title">Profile</h1>
    </div>

    <div class="avatar-container">
        <img :src="avatar" :alt="username" class="avatar" />
        <div class="overlay" v-if="hovered" ref="overlayRef">
          <div @click="openFileInput">
            <font-awesome-icon icon="camera" />
          </div>
          <input ref="fileInput" type="file" accept="image/*" @change="handleAvatarChange" style="display: none;" />
        </div>
      </div>
    <div class="forms-container">
      <div class="form-container">
        <div>
          <label for="username">Username</label>
          <input type="text" id="username" v-model="username" placeholder="Enter new username..." required />
        </div>
        <div>
          <label for="email">Email</label>
          <input type="email" id="email" v-model="email" placeholder="Enter new email..." required />
        </div>
        <button @click="updateProfile" :disabled="isUpdatingInfo">
          {{ isUpdatingInfo ? 'Saving...' : 'Save changes' }}
        </button>
        <p v-if="error1" class="error">{{ error1 }}</p>
      </div>

      <div class="form-container">
        <div>
          <label for="password">Password</label>
          <input type="password" id="password" v-model="password" placeholder="Enter new password..." required />
        </div>

        <div>
          <label for="password2">Confirm Password</label>
          <input type="password" id="password2" v-model="password2" placeholder="Repeat new password..." required />
        </div>
        <button @click="changePassword" :disabled="isUpdatingPassword">
          {{ isUpdatingPassword ? 'Saving...' : 'Change password' }}
        </button>
        <p v-if="error2" class="error">{{ error2 }}</p>
      </div>
    </div>
  </div>
</template>

  
  <script>
  import SidebarComponent from '@/components/Layout/SidebarComponent.vue';
  import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
  import { toast } from 'vue3-toastify';
  import 'vue3-toastify/dist/index.css';
  export default {
    name: 'ProfileComponent',
    components: {
      SidebarComponent,
      FontAwesomeIcon,
    },
    data() {
      return {
        avatar: require('@/assets/placeholder/mc.jpeg'),
        username: sessionStorage.getItem('username'),
        email: sessionStorage.getItem('email'),
        password: '',
        password2:'',
        error1: '',
        error2: '',
        isUpdatingInfo: false,
        isUpdatingPassword: false,
        hovered: false,
      };
    },
    methods: {
      openFileInput() {
        this.$refs.fileInput.click();
      },
      handleAvatarChange() {
        const fileInput = this.$refs.fileInput;
        if (fileInput) {
          fileInput.click();
        }
      },
  
      async updateProfile() {

      try{

        this.error1 = '';
        this.error2 = '';
        this.isUpdatingInfo = true;

        // Check if username is at least 3 characters
        if (this.username.length < 3) {
          this.error1 = 'Username must be at least 3 characters!';
          return;
        }

        // Check if email is valid
        if (this.email !== '' && !this.email.includes('@')) {
          this.error1 = 'Email must be valid!';
          return;
        }
  
        const requestBody = {
          username: this.username,
          email: this.email === '' ? sessionStorage.getItem('email') : this.email,
          currentUsername: sessionStorage.getItem('username')
      };

      // Make the PUT request
      const response = await fetch('http://localhost:8080/authenticate/update', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + sessionStorage.getItem('jwtoken')
        },
        body: JSON.stringify(requestBody)
      });

      if (response.ok) {
        const responseData = await response.json();

        // Save the response data in sessionStorage
        sessionStorage.setItem('username', responseData.username);
        sessionStorage.setItem('email', this.email === '' ? sessionStorage.getItem('email') : responseData.email);

        toast('User info updated, you must log in again!', {
              type: 'success',
              position: 'top-right',
              duration: 3000,
              theme: 'colored',
              icon: {
                name: 'check-circle',
              },
              transition: 'Vue-Toastification__bounce',
            });
            sessionStorage.removeItem('jwtoken');
            sessionStorage.removeItem('username');
            sessionStorage.removeItem('email');
            sessionStorage.removeItem('userId');
            
            setTimeout(() => {
              this.$router.push('/my-lists');
            }, 3000);


      } else {
        const errorResponseText = await response.text();
        this.error = errorResponseText || 'An error occurred during updating.';
        toast('An error has ocurred', {
              type: 'error',
              position: 'top-right',
              duration: 3000,
              theme: 'colored',
              icon: {
                name: 'times-circle',
              },
              transition: 'Vue-Toastification__bounce',
            });
      }
    }catch (error) {
      console.error('An error occurred during updating:', error);
      this.error1 = 'An error occurred during updating: ' + error.message;
    } finally {
      this.isUpdatingInfo = false;
    }
  },

      async changePassword() {
        try{

          this.error1 = '';
          this.error2 = '';
          this.isUpdatingPassword = true;

          // Check if password is at least 8 characters
          if (this.password.length < 8) {
            this.error2 = 'Password must be at least 8 characters!';
            return;
          }

          // Check if passwords match
          if (this.password !== this.password2) {
            this.error2 = 'Passwords must match!';
            return;
          }
    
          const requestBody = {
            currentUsername: sessionStorage.getItem('username'),
            newPassword: this.password,
        };

        // Make the PATCH request
        const response = await fetch('http://localhost:8080/authenticate/change-password', {
          method: 'PATCH',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + sessionStorage.getItem('jwtoken')
          },
          body: JSON.stringify(requestBody)
        });

        if (!response.ok) {
          const errorResponseText = await response.text();
          this.error2 = errorResponseText || 'An error occurred during updating.';
          toast('An error has ocurred', {
              type: 'error',
              position: 'top-right',
              duration: 3000,
              theme: 'colored',
              icon: {
                name: 'times-circle',
              },
              transition: 'Vue-Toastification__bounce',
            });
        }else{
          toast('Password changed successfully!', {
              type: 'success',
              position: 'top-right',
              duration: 3000,
              theme: 'colored',
              icon: {
                name: 'check-circle',
              },
              transition: 'Vue-Toastification__bounce',
            });
        }
      }catch (error) {
        console.error('An error occurred during updating:', error);
        this.error2 = 'An error occurred during updating: ' + error.message;
      } finally {
        this.isUpdatingPassword = false;
    }
  },
},
  };
  </script>
  
  <style scoped>
  .profile-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
  }

  .forms-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 2rem
  }

  
  .header-container {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-top: 2rem;
  }
  
  .title {
    width: 182px;
    height: 30px;
    color: rgb(252, 9, 76);
    font-family: Poppins;
    font-size: 24px;
    font-weight: 600;
    text-align: center;
    margin-left: 10rem;
  }
  
  .avatar-container {
    text-align: center;
    margin-left: 10rem;
  }
  
  .avatar {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    transition: filter 0.3s;
  }
  
  .overlay {
    width: 100%;
    z-index: 1;
    height: 100%;
    opacity: 0;
    background-color: rgba(0, 0, 0, 0.6);
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    transition: opacity 0.3s;
  }
  
  .overlay svg {
    color: #fff;
  }
  
  .avatar-container:hover .avatar {
    filter: brightness(70%);
  }
  
  .avatar-container:hover .overlay {
    opacity: 1;
  }
  
  .form-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
    margin-top: -7rem;
    margin-left: 10rem;
  }
  
  .form-container div {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    padding: 0px;
  }
  
  .form-container label {
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
  }
  
  .form-container input {
    width: 368px;
    border: none;
    background: rgba(30, 110, 233, 0.05);
    border-radius: 10px;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    padding: 16px;
    margin: 8px 0px;
    font-family: 'Poppins', sans-serif;
  }
  
  .form-container button {
    border: none;
    background-color: rgb(252, 9, 76);
    color: #fff;
    cursor: pointer;
    width: 100%;
    height: 48px;
    border-radius: 10px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    padding: 16px;
    margin-top: 10px;
    margin-left: auto;
    margin-right: auto;
    font-family: 'Poppins', sans-serif;
    text-align: center;
    font-size: 18px;
    font-weight: 500;
    line-height: 18px;
  }

  @keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
  100% {
    transform: scale(1);
  }
}
  .form-container button:hover {
    background-color: rgb(252, 9, 76);
    opacity: 0.8;
    transition: all 0.3s linear;
    animation: pulse 1s ease-in-out infinite;
  }
  .form-container button:disabled {
    background-color: rgb(241, 112, 148);
    cursor: not-allowed;
  }
  
  .error {
    color: red;
    font-size: Poppins;
    text-align: center;
    margin-top: 10px;
  }

  @media (max-width: 768px) {

  .profile-container {
    margin-top: 5rem;
  }
  .title {
    margin-left: 0;
  }
  .forms-container {
    flex-direction: column;
    margin-left: 0;
    gap: 0;
    width: 100%;
  }

  .form-container {
    margin-left:0;
    width: 100%;

  }

  .form-container input {
    width: 300px;
  }

  .avatar-container {
    margin-left: 0;
    margin-top: 1rem;
  }

  .form-container button {
    margin-left: auto;
    margin-right: auto;
    width: 330px;
    
  }
}
  </style>
  