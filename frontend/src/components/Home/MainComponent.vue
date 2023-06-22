<template>
  <div class="welcome-container">
    <SidebarComponent></SidebarComponent>
    <div class="content">
      <div class="welcome-message">
        <img class="avatar" src="../../assets/placeholder/mc.jpeg" alt="Avatar" />
        <p>Welcome, {{ username }}</p>
      </div>

      <div class="header-container">
        <h1 class="title">My Lists</h1>
        <router-link to="/my-lists">View all</router-link>
      </div>
      <ListsComponent></ListsComponent>

      <div class="header-container">
        <h1 class="title">My Games</h1>
        <router-link to="/games">View all</router-link>
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
      username: 'John Doe',
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

.avatar {
  width: 75px;
  height: 75px;
  border-radius: 50%;
  margin-right: 10px;
}
</style>
