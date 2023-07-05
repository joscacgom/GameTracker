<template>
  <aside class="sidebar" :class="{ collapsed: isCollapsed }">
    <img src="../../assets/joystick-white-removebg.png" alt="logo" />
    <h3>GameTracker</h3>
    <ul v-if="!isCollapsed">
      <li>
        <router-link to="/home" class="sidebar-link">
          <span class="icon"><font-awesome-icon icon="home" /></span>
          Home
        </router-link>
      </li>
      <li>
        <router-link to="/my-lists" class="sidebar-link">
          <span class="icon"><font-awesome-icon icon="list" /></span>
          My Lists
        </router-link>
      </li>
      <li>
        <router-link to="/my-games" class="sidebar-link">
          <span class="icon"><font-awesome-icon icon="gamepad" /></span>
          My Games
        </router-link>
      </li>
      <li>
        <router-link to="/discover" class="sidebar-link">
          <span class="icon"><font-awesome-icon icon="compass" /></span>
          Discover
        </router-link>
      </li>
    </ul>
    <img class="avatar-pic" v-if="!isCollapsed" src="../../assets/placeholder/mc.jpeg" alt="avatar" @click="redirectToProfile" />
    <div class="logout" v-if="!isCollapsed">
      <button @click="logout">
        <span class="icon"><font-awesome-icon icon="sign-out" size="lg" /></span>
      </button>
    </div>
    <div class="collapse-button-wrapper">
      <button class="collapse-button" @click="toggleSidebar" v-if="isCollapsed">
        <span class="icon"><font-awesome-icon icon="bars" /></span>
      </button>
      <button class="collapse-button" @click="toggleSidebar" v-else>
        <span class="icon"><font-awesome-icon icon="times" /></span>
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
  data() {
    return {
      isCollapsed: false,
    };
  },
  mounted() {
    this.checkWidth();
    window.addEventListener('resize', this.checkWidth);
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.checkWidth);
  },
  methods: {
    toggleSidebar() {
      this.isCollapsed = !this.isCollapsed;
    },
    logout() {
      sessionStorage.removeItem('jwtoken');
      sessionStorage.removeItem('username');
      this.$router.push('/');
    },
    redirectToProfile() {
      this.$router.push('/profile');
    },
    checkWidth() {
      this.isCollapsed = window.innerWidth < 768;
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
  transition: width 0.3s;
}

.collapsed {
  width: 100px;
}

.collapsed h3 {
  display: none;
}



img {
  width: 80px;
  margin-top: 1rem;
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

.sidebar-link {
  position: relative;
}

.sidebar-link::before {
  content: "";
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 0;
  height: 2px;
  background-color: #fff;
  transition: width 0.3s;
}

.sidebar-link:hover::before {
  width: 100%;
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

.collapse-button-wrapper {
  margin-top: auto;
  padding-top: 20px;
}

.collapse-button {
  border: none;
  background-color: transparent;
  color: #fff;
  cursor: pointer;
  font-family: 'Poppins', sans-serif;
  font-size: 16px;
}

@media (max-width: 767px) {
  .sidebar {
    width: 100%;
    height: auto;
    flex-direction: column;
    align-items: center;
    padding: 10px;
    z-index:1;
  }

  img {
    width: 60px;
    margin-top: 0;
    margin-right: 10px;
  }

  h3 {
    display: none;
    font-size: 16px;
    border-bottom: none;
    margin-bottom: 1rem;
    margin-right: 10px;
  }

  ul {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    gap: 1rem;
  }

  li {
    padding: 5px;
  }

  .avatar-pic {
    width: 30px;
    margin-bottom: 0;
    margin-left: 10px;
    margin-bottom: 1rem;
  }

  .logout {
    margin-top: 0;
    padding-bottom: 0;
  }
}
</style>
