<template>
  <div class="welcome-container">
    <SidebarComponent></SidebarComponent>
    <div class="content">
      <div class="welcome-message">
        <img class="avatar" src="../../assets/placeholder/mc.jpeg" alt="Avatar" />
        <p>Welcome, <span class="username">{{ username }}</span></p>
      </div>

      <div class="header-container">
        <h1 class="title">My Lists</h1>
        <router-link to="/my-lists">View all</router-link>
      </div>
      <ListsComponent></ListsComponent>

      <div class="header-container">
        <h1 class="title">My Games</h1>
        <router-link to="/my-games">View all</router-link>
      </div>
      <GamesComponent></GamesComponent>
    </div>
  </div>
</template>

<script>
import ListsComponent from '@/components/Home/ListsComponent.vue';
import SidebarComponent from '@/components/Layout/SidebarComponent.vue';
import GamesComponent from '@/components/Home/GamesComponent.vue';
export default {
  name: 'MainComponent',
  components: {
    SidebarComponent,
    ListsComponent,
    GamesComponent,
  },
  data() {
    return {
      username: sessionStorage.getItem('username'),
    };
  },
  methods: {
    logout() {
      this.$router.push('/');
    }
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');
.welcome-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
}

.header-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-top: 2rem;
}

.header-container a {
  color: rgb(252, 9, 76);
  text-decoration: none;
  font-family: Poppins;
  font-size: 14px;
  font-weight: 500;
  text-align: center;
  margin-top: 2rem;
}

.content {
  margin-left: 200px; /* Adjust as needed based on sidebar width */
  padding: 20px;
}

.title {
  width: 182px;
  height: 30px;
  color: rgb(252, 9, 76);
  font-family: Poppins;
  font-size: 24px;
  font-weight: 600;
  text-align: center;
}

.welcome-message {
  color: rgb(7, 7, 7);
  font-family: Poppins;
  font-size: 18px;
  font-weight: 500;
  text-align: center;
}

@keyframes fade-in {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
.username {
  color: rgb(252, 9, 76);
  display: inline-block;
  animation: fade-in 1s ease-in-out;

}
.avatar {
  width: 75px;
  height: 75px;
  border-radius: 50%;
  margin-right: 10px;
  animation: fade-in 1s ease-in-out;

}

@media screen and (max-width: 768px) {

  .welcome-container {
    margin-top:5rem;
  }

  .avatar {
    width: 75px;
    height: 75px;
    margin-right:0;
  }
  .content {
    margin-left: 0;
    padding: 10px;
  }

  .header-container {
    flex-direction: column;
    align-items: flex-end;
  }

  .title {
    width: 100%;
    margin-bottom: 1rem;
  }
}

@media screen and (max-width: 480px) {
  .avatar {
    width: 50px;
    height: 50px;
  }

  .welcome-message {
    font-size: 16px;
  }

  .header-container a {
    font-size: 12px;
    margin-top: 1rem;
  }
}
</style>
