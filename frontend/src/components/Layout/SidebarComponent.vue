<template>
    <aside class="sidebar">
      <img src="../../assets/joystick-white-removebg.png" alt="logo" />
      <ul>
        <li>
          <router-link to="/profile">
            <span class="icon"><font-awesome-icon icon="user" /></span>
            Profile
          </router-link>
        </li>
        <li>
          <router-link to="/my-lists">
            <span class="icon"><font-awesome-icon icon="list" /></span>
            My Lists
          </router-link>
        </li>
        <li>
          <router-link to="/discover">
            <span class="icon"><font-awesome-icon icon="compass" /></span>
            Discover
          </router-link>
        </li>
      </ul>
      <div class="logout">
        <button @click="logout">
          <span class="icon"><font-awesome-icon icon="sign-out-alt" /></span>
          Logout
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
    margin-bottom: 1rem;
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
  