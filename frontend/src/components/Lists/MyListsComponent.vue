<template>
  <div class="mylists-container">
    <SidebarComponent></SidebarComponent>
    <div class="header-container">
      <h1 class="title">My Lists</h1>
    </div>

    <div v-if="loading" class="loading-container">
      <LoadingComponent type="big"></LoadingComponent>
    </div>

    <div v-if="!carouselItems || carouselItems.length === 0 && !loading" class="empty-carousel">
        <EmptyComponent type="big"></EmptyComponent>
    </div>
    <div v-else class="carousel-container">
      <div class="carousel-item-wrapper">
        <div class="carousel-item" v-for="item in carouselItems" :key="item.id" @click="redirectToItemList(item.id)">
            <img class='carousel-image' v-if="item.games && item.games.length > 0" :src="item.games[0].game.background_image" :alt="item.status" />
            <img class='default-image' v-else src="../../assets/placeholder/default-list-img.png" alt="Default Image" />
            <div class="carousel-overlay">
              <h3>{{ item.status }}</h3>
            </div>
          </div>
      </div>
    </div>

    <button class="create-list-button" @click="createNewList">
      <font-awesome-icon icon="plus-circle" />
      Create New List
    </button>
    <create-list-component :show-popup="showPopup" @close="closePopup"></create-list-component>
  </div>
</template>

<script>
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import SidebarComponent from '@/components/Layout/SidebarComponent.vue';
import CreateListComponent from '@/components/Lists/CreateListComponent.vue';
import EmptyComponent from '@/components/Empty/EmptyComponent.vue';
import LoadingComponent from '@/components/Loading/LoadingComponent.vue';

export default {
  name: 'MyListsComponent',
  components: {
    FontAwesomeIcon,
    SidebarComponent,
    CreateListComponent,
    EmptyComponent,
    LoadingComponent,
  },
  data() {
    return {
      carouselItems: [],
      showPopup: false,
      loading: true,
      hoveredItem: null,
    };
  },
  mounted() {
    this.fetchCarouselItems();
  },
  methods: {
    async fetchCarouselItems() {
      try {
        const userId = sessionStorage.getItem('userId');

        // Make the GET request
        const response = await fetch(`http://localhost:8080/api/game-lists/user/${userId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + sessionStorage.getItem('jwtoken'),
          },
        });

        if (response.ok) {
          const responseData = await response.json();
          this.carouselItems = await responseData;
        } else {
          console.log('An error response was received');
        }
      } catch (error) {
        console.error('An error occurred during fetching:', error);
      } finally {
        this.loading = false;
      }
    },
    createNewList() {
      this.showPopup = true;
    },
    redirectToItemList(itemId) {
      this.$router.push(`/list/${itemId}`);
    },
    showOverlay(itemId) {
      this.hoveredItem = itemId;
    },
    hideOverlay(itemId) {
      if (this.hoveredItem === itemId) {
        this.hoveredItem = null;
      }
    },
    closePopup() {
      this.showPopup = false;
    },
  },
};
</script>

<style scoped>
.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  margin-top: 5rem;
  margin-bottom: 5rem;
}

@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');
.mylists-container {
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

.carousel-item-wrapper {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  gap: 2rem;
}

.carousel-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  margin-top: 2rem;
  margin-left: 15rem;
  gap: 2rem;
}

.carousel-item {
  display: flex;
  flex: 1 0 21%;
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

.carousel-item:hover .default-image {
  transform: scale(1.05);
}

.default-image {
  background-color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 200px;
  height: 250px;
  object-fit: fill;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
  border-radius: 4px;
  transition: transform 0.3s;
  cursor: pointer;
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
  width: auto;
  max-width: 200px;
}

.carousel-item:hover .carousel-overlay,
.carousel-overlay.hovered {
  opacity: 1;
}

.carousel-overlay h3 {
  font-size: 18px;
  margin: 0;
}

.carousel-title {
  margin-top: 0.5rem;
}

.create-list-button {
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

  .create-list-button:hover {
    background-color: rgb(252, 9, 76);
    opacity: 0.8;
    transition: all 0.3s linear;
    animation: pulse 1s ease-in-out infinite;
  }

.default-image {
  background-color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
}

.default-image img {
  width: 100%;
  height: 100%;
  object-fit: fill;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
  border-radius: 4px;
}

@media (max-width: 768px) {

  .mylists-container {
    margin-top: 5rem;
  }
  .title {
    margin-left: 0;
  }

  .carousel-container {
    margin-left: 0;
  }

  .create-list-button {
    margin-left: 0;
  }
}
</style>
