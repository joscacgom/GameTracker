<template>
  <div class="list-container">
    <div class="header-container" v-if="!isLoading && !error">
      <h1 class="title">{{ list.status }} <span class="edit-icon" @click="edit"><font-awesome-icon icon="edit" /></span></h1>
      <h3 class="subtitle">Check your {{ list.status }} games and track your progress. You can add more in the discover section.</h3>
    </div>
    <SidebarComponent></SidebarComponent>
    <div v-if="isLoading" class="loading-container">
      <LoadingComponent type="big"></LoadingComponent>
    </div>
    <div v-else-if="error" class="error-container">
      <ErrorComponent></ErrorComponent>
    </div>
    <div v-else-if="!list.games" class="empty-container">
      <EmptyComponent type="big"></EmptyComponent>
    </div>
    <div v-else>
      <div class="carousel-container">
        <div class="carousel-item" v-for="game in list.games" :key="game.id">
          <img :src="game.game.background_image" :alt="game.game.name" class="carousel-image" />
          <div class="carousel-overlay">
            <h3>{{ game.playtimeHours }} hours</h3>
          </div>
        </div>
      </div>
    </div>
    <button v-if="!isLoading && !error" class="discover-button" @click="navigateToDiscover">
      <font-awesome-icon icon="compass" />
      Discover new games
    </button>
    <edit-list-component :show-popup="showPopup" :listId="listId" @close="closePopup"></edit-list-component>
  </div>
</template>


<script>
import SidebarComponent from '@/components/Layout/SidebarComponent.vue';
import LoadingComponent from '@/components/Loading/LoadingComponent.vue';
import ErrorComponent from '@/components/Error/ErrorComponent.vue';
import EmptyComponent from '@/components/Empty/EmptyComponent.vue';
import EditListComponent from '@/components/Lists/EditListComponent.vue';
export default {
  name: 'ListDetailsComponent',
  props: {
    listId: {
      type: String,
      required: true,
    },
  },
  components: {
    SidebarComponent,
    LoadingComponent,
    ErrorComponent,
    EmptyComponent,
    EditListComponent,
  },
  data() {
    return {
      list: {},
      isLoading: true,
      error: false,
      showPopup: false,
    };
  },
  mounted() {
    this.fetchListDetails();
  },
  methods: {
    navigateToDiscover() {
      this.$router.push('/discover');
    },
    closePopup() {
      this.showPopup = false;
    },
    edit() {
      this.showPopup = true;
    },
    async fetchListDetails() {
      try {
        const response = await fetch(`http://localhost:8080/api/game-lists/${this.listId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + sessionStorage.getItem('jwtoken'),
          },
        });

        if (response.ok) {
          const responseData = await response.json();
          this.list = responseData;
        } else {
          console.log('An error response was received');
        }
      } catch (error) {
        console.error('An error occurred during fetching:', error);
      } finally {
        this.isLoading = false;
      }

      if (!this.listId) {
        this.error = true;
        this.isLoading = false;
      }
    },
  },
};
</script>
  
  <style scoped>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');
  .list-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
  }
  
  .header-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
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

  .subtitle {
    color: rgb(7, 7, 7);
    font-family: 'Poppins', sans-serif;
    font-size: 16px;
    font-weight: 600;
    text-align: center;
    margin-left: 10rem;
  }
  
  .carousel-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    margin-top: 2rem;
    margin-left: 15rem;
  }
  
  .carousel-item {
    flex: 1 0 21%; 
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
  .edit-icon {
    margin: 0.5rem;
    cursor: pointer;
  }

  .edit-icon :hover {
    color: rgb(241, 112, 148);
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

  @media (max-width: 768px) {
  .list-container {
    margin-top: 2rem;
  }
  .title,
  .subtitle,
  .carousel-container,
  .carousel-item {
    margin-left: 0;
  }

  .subtitle {
    width: 90%;
  }

  .loading-container,
  .empty-container,
  .error-container {
    margin-left: 0;
    margin-top:1rem;
  }

  .discover-button {
    margin-left: 0;
  }

  .carousel-item {
    flex-basis: 45%;
  }
}
  </style>
  