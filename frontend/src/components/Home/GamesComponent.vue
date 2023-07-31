<template>
    <div class="games-container">
      <div v-if="loading" class="loading-container">
        <LoadingComponent type="small"></LoadingComponent>
      </div>
      <div v-else class="carousel-wrapper">
        <div class="carousel" :style="carouselStyle">
          <div v-if="!carouselItems || carouselItems.length === 0" class="empty-carousel">
            <EmptyComponent  type="small"></EmptyComponent>
          </div>
          <div class="carousel-item" v-else v-for="item in visibleItems" :key="item.game.id" @click="redirectToItemList(item.game.id)">
            <img :src="item.game.background_image" :alt="item.game.name" class="carousel-image" />
            <div class="carousel-overlay">
              <h3>{{ item.game.name }}</h3>
              <h3>{{ item.playtimeHours }} hours</h3>
              <span class="edit-icon" @click.stop="edit(item.id, item.gameList.id, item.playtimeHours )"><font-awesome-icon icon="edit" /></span>
            </div>
            
          </div>
        </div>
        <div class="navigation-arrows">
          <div class="arrow left-arrow" @click="slideCarousel('left')">
            <font-awesome-icon icon="chevron-left" />
          </div>
          <div class="arrow right-arrow" @click="slideCarousel('right')">
            <font-awesome-icon icon="chevron-right" />
          </div>
        </div>
      </div>
      <edit-game-component :show-popup="showPopup" :gameId="gameId" :gameListId="gameListId" :playtime="playtime" @close="closePopup"></edit-game-component>

    </div>
  </template>
  
  <script>
  import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
  import EmptyComponent from '@/components/EmptyComponent.vue';
  import LoadingComponent from '@/components/LoadingComponent.vue';
  import EditGameComponent from '@/components/Games/EditGameComponent.vue';


  export default {
    name: 'GamesComponent',
    components: {
      FontAwesomeIcon,
      EmptyComponent,
      LoadingComponent,
      EditGameComponent,
    },
    data() {
      return {
        carouselItems: [],
        currentPosition: 0, // Current position of the carousel
        itemsToShow: 3, // Number of items to show at a time
        loading: true,
        hoveredItem: null,
        showPopup: false,
        gameId: null,
        gameListId: null,
        playtime: null,
      };
    },
    mounted() {
      this.fetchCarouselItems();
    },
    computed: {
      visibleItems() {
        return this.carouselItems.slice(
          this.currentPosition,
          this.currentPosition + this.itemsToShow
        );
      },
      carouselStyle() {
        const translateX = -this.currentPosition * this.itemWidth + 'px';
        return {
          transform: `translateX(-50%) translateX(${translateX})`,
        };
      },
    },
    methods: {
      closePopup() {
        this.showPopup = false;
        this.fetchCarouselItems();
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
    redirectToItemList(itemId) {
      this.$router.push(`/game/${itemId}`);
    },
      slideCarousel(direction) {
        const maxPosition = this.carouselItems.length - this.itemsToShow; // Maximum position of the carousel
  
        if (direction === 'left') {
          if (this.currentPosition > 0) {
            this.currentPosition--;
          }
        } else if (direction === 'right') {
          if (this.currentPosition < maxPosition) {
            this.currentPosition++;
          }
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .games-container {
    max-width: 800px;
    margin: 0 auto;
  }
  
  .carousel-wrapper {
  position: relative;
}

.carousel {
  display: flex;
  overflow: hidden;
  height: 270px;
  width: 100%;
}

.carousel-item {
  padding: 10px;
  width: 184px;
  height: 240px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  position: relative;
}

.carousel-image {
  width: 100%;
  height: 100%;
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
  font-family: Poppins;
  text-align: center;
  color: #fff;
  opacity: 0;
  transition: opacity 0.3s;
}

.carousel-item:hover .carousel-overlay {
  opacity: 1;
}

.carousel-overlay h3 {
  margin: 0;
  font-size: 18px;
}

.navigation-arrows {
  display: flex;
  justify-content: space-between;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 100%;
}

.arrow {
  background-color: rgba(0, 0, 0, 0.3);
  color: #fff;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  cursor: pointer;
  transition: background-color 0.3s;
}

.arrow i {
  font-size: 16px;
}

.arrow:hover {
  background-color: rgba(0, 0, 0, 0.5);
}

.left-arrow {
  margin-left: 20px;
}

.right-arrow {
  margin-right: 20px;
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


@media screen and (max-width: 768px) {
  .carousel-item {
    width: 100%;
    height: 200px;
    max-width: 200px;
    margin: 0 auto;
  }

  .empty-carousel {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 1rem;
  }
  
  .carousel {
    height: auto;
  }
}

@media screen and (max-width: 400px) {
  .carousel-item {
    max-width: 160px;
  }
}
  </style>
  