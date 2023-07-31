<template>
    <div class="mygames-container">
      <SidebarComponent></SidebarComponent>
      <div class="header-container">
        <h1 class="title">My Games</h1>
      </div>

      <div v-if="loading" class="loading-container">
      <LoadingComponent type="big"></LoadingComponent>
    </div>

    <div v-if="!carouselItems || carouselItems.length === 0 && !loading" class="empty-carousel">
        <EmptyComponent type="big"></EmptyComponent>
    </div>
  
      <div v-else class="carousel-container">
        <div class="carousel-item" v-for="item in carouselItems" :key="item.id" @click="redirectToItemGame(item.game.id)">
          <img :src="item.game.background_image" :alt="item.game.name" class="carousel-image" />
          <div class="carousel-overlay">
              <h3>{{ item.game.name }}</h3>
              <h3>{{ item.playtimeHours }} hours</h3>
              <span class="edit-icon" @click.stop="edit(item.id, item.gameList.id, item.playtimeHours )"><font-awesome-icon icon="edit" /></span>
            </div>
        </div>
      </div>
  
      <button class="discover-button" @click="redirectToDiscover">
        <font-awesome-icon icon="compass" />
        Discover New Games
      </button>
      <edit-game-component :show-popup="showPopup" :gameId="gameId" :gameListId="gameListId" :playtime="playtime" @close="closePopup"></edit-game-component>

    </div>
  </template>
  
  <script>
  import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
  import SidebarComponent from '@/components/Layout/SidebarComponent.vue';
  import EmptyComponent from '@/components/Empty/EmptyComponent.vue';
  import LoadingComponent from '@/components/Loading/LoadingComponent.vue';
  import EditGameComponent from '@/components/Games/EditGameComponent.vue';
  export default {
    name: 'MyGamesComponent',
    components: {
      FontAwesomeIcon,
      SidebarComponent,
      EmptyComponent,
      LoadingComponent,
      EditGameComponent,
    },
    data() {
      return {
        carouselItems: [],
        loading: true,
        showPopup: false,
        gameId: null,
        gameListId: null,
        playtime: null,
      };
    },
    mounted() {
    this.fetchCarouselItems();
  },
    methods: {
      redirectToItemGame(itemId) {
        this.$router.push(`/game/${itemId}`);
    },
    
    closePopup() {
      this.showPopup = false;
    },
    edit(gameId, gameListId, playtime) {
      this.gameId = gameId;
      this.gameListId = gameListId;
      this.playtime = playtime;

      this.showPopup = true;
    },
    async fetchCarouselItems() {
      try {
        const userId = sessionStorage.getItem('userId');

        // Make the GET request
        const response = await fetch(`http://localhost:8080/games/game-with-playtime/user/${userId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + sessionStorage.getItem('jwtoken'),
          },
        });

        if (response.ok) {
          const responseData = await response.json();
          this.carouselItems = responseData;
        } else {
          console.log('An error response was received');
        }
      } catch (error) {
        console.error('An error occurred during fetching:', error);
      } finally {
        this.loading = false;
      }
    },
    redirectToDiscover() {
        this.$router.push(`/discover`);
    },

    },
  };
  </script>
  
  <style scoped>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');
  .mygames-container {
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
  
  .title {
    width: 182px;
    height: 30px;
    color: rgb(252, 9, 76);
    font-family: 'Poppins', sans-serif;
    font-size: 24px;
    font-weight: 600;
    text-align: center;
    margin-left: 10rem;
  }
  
  .carousel-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    margin-top: 2rem;
    margin-left: 10rem;
    gap: 5rem;
  }
  
  .carousel-item {
    flex: 1 0 calc(25% - 0.5rem);
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 1rem;
    font-family: 'Poppins', sans-serif;
    font-size: 18px;
    font-weight: 500;
    position: relative; 
  }
  
  .carousel-image {
    width: 200px;
    height: 250px;
    object-fit: cover;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
    border-radius: 4px;
    cursor: pointer;
    transition: transform 0.3s;
  }
  
  .carousel-item:hover .carousel-image {
    transform: scale(1.05);
  }
  
  .carousel-overlay {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: rgb(252, 9, 76);
    padding: 10px;
    font-family: 'Poppins', sans-serif;
    text-align: center;
    color: #fff;
    opacity: 0;
    transition: opacity 0.3s;
    width: 50%;
    max-width: 200px;
  }
  
  .carousel-item:hover .carousel-overlay {
    opacity: 1;
  }
  
  .carousel-overlay h3 {
    margin: 0;
    font-size: 18px;
  }
  
  .carousel-title {
    margin-top: 0.5rem;
  }
  
  .discover-button {
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
    margin-top: 10px;
    margin-left: 10rem;
    margin-bottom: 2rem;
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

  .discover-button:hover {
    background-color: rgb(252, 9, 76);
    opacity: 0.8;
    transition: all 0.3s linear;
    animation: pulse 1s ease-in-out infinite;
  }

  .edit-icon {
    position: absolute;
    top: 0;
    right: 0;
    color: #fff;
    cursor: pointer;
    transition: color 0.3s;
    z-index: 2;
}

  .edit-icon :hover {
    color: rgb(241, 112, 148);
  }

  @media (max-width: 768px) {

.mygames-container {
  margin-top: 5rem;
}
.title {
  margin-left: 0;
}

.carousel-container {
  margin-left: 0;
}

.discover-button {
  margin-left: 0;
}


}
  </style>
  