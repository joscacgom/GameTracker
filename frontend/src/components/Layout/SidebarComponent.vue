<template>
    <aside class="sidebar">
      <img src="../../assets/joystick-white-removebg.png" alt="logo" />
      <h3>GameTracker</h3>
      <ul>
        <li>
          <router-link to="/home">
            <span class="icon"><font-awesome-icon icon="home" /></span>
            Home
          </router-link>
        </li>
        <li>
          <router-link to="/my-lists">
            <span class="icon"><font-awesome-icon icon="list" /></span>
            My Lists
          </router-link>
        </li>
        <li>
          <router-link to="/my-games">
            <span class="icon"><font-awesome-icon icon="gamepad" /></span>
            My Games
          </router-link>
        </li>
        <li>
          <router-link to="/discover">
            <span class="icon"><font-awesome-icon icon="compass" /></span>
            Discover
          </router-link>
        </li>
      </ul>
      <img class="avatar-pic" src="../../assets/placeholder/mc.jpeg" alt="avatar" @click="redirectToProfile" />
      <div class="logout">
        <button @click="logout">
          <span class="icon"><font-awesome-icon icon="sign-out" size="lg" /></span>
        </button>
      </div>
    </aside>
  </template>
  
  <script>
  import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
  
  export default {
    components: {
      FontAwesomeIcon,
    },
    methods: {
      logout() {
        this.$router.push('/');
      },
      redirectToProfile() {
        this.$router.push('/profile');
      },
    },
  };
  </script>
  
  <style scoped>
  .sidebar {
    position: fixed;
    left: 0;
    top: 0;
    height: 100%;
    width: 200px;
    background-color: rgb(252, 9, 76);
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  img {
    width: 80px;
    margin-top:1rem;
  }

  .avatar-pic {
    border-color: rgb(255, 255, 255);
    border-style: solid;
    width: 40px;
    border-radius: 100%;
    margin-bottom: 1rem;
    cursor: pointer;
  }

  h3 {
    color: #fff;
    font-family: Poppins;
    font-size: 20px;
    font-weight: 600;
    border-bottom: #ffffff 1px solid;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
    flex-grow: 1;
  }
  
  li {
    padding: 10px;
  }
  
  a {
    text-decoration: none;
    color: #fff;
    font-family: Poppins;
    transition: color 0.3s;
    display: flex;
    align-items: center;
  }
  
  a:hover {
    color: #ccc;
  }
  
  .logout {
    margin-top: auto;
    padding-bottom: 20px;
  }
  
  button {
    border: none;
    background-color: transparent;
    color: #fff;
    cursor: pointer;
    font-family: 'Poppins', sans-serif;
    font-size: 16px;
    display: flex;
    align-items: center;
  }
  .icon {
    margin-right: 10px;
  }
  </style>
  