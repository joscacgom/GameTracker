<template>
  <div class="games-container">
    <div v-if="loading" class="loading-container">
      <LoadingComponent type="small"></LoadingComponent>
    </div>
    <div v-else class="carousel-wrapper">
      <div class="carousel" :style="carouselStyle">
        <div v-if="!carouselItems || carouselItems.length === 0" class="empty-carousel">
          <EmptyComponent type="small"></EmptyComponent>
        </div>
        <div class="carousel-item" v-else v-for="item in visibleItems" :key="item.id" @click="redirectToItemList(item.id)">
          <img :src="item.background_image" :alt="item.name" class="carousel-image" />
          <div class="carousel-overlay">
            <h3>{{ item.name }}</h3>
          </div>
        </div>
      </div>
      <div class="navigation-arrows" v-if="carouselItems || carouselItems.length !== 0">
        <div class="arrow left-arrow" @click="slideCarousel('left')">
          <font-awesome-icon icon="chevron-left" />
        </div>
        <div class="arrow right-arrow" @click="slideCarousel('right')">
          <font-awesome-icon icon="chevron-right" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
  import EmptyComponent from '@/components/EmptyComponent.vue';
  import LoadingComponent from '@/components/LoadingComponent.vue';

  export default {
    name: 'GamesComponent',
    components: {
      FontAwesomeIcon,
      EmptyComponent,
      LoadingComponent
    },
    data() {
      return {
        carouselItems: [],
        currentPosition: 0,
        itemsToShowDesktop: 3,
        itemsToShowMobile: 2,
        loading: true,
        hoveredItem: null,
        isMobile: false,
      };
    },
    mounted() {
      this.fetchCarouselItems();
      window.addEventListener('resize', this.handleWindowResize);
      this.checkMobile();
    },
    unmounted() {
      window.removeEventListener('resize', this.handleWindowResize);
    },
    computed: {
      visibleItems() {
        return this.carouselItems.slice(
          this.currentPosition,
          this.currentPosition + this.itemsToShow
        );
      },
      itemsToShow() {
        return this.isMobile ? this.itemsToShowMobile : this.itemsToShowDesktop;
      },
      carouselStyle() {
        const translateX = -this.currentPosition * this.itemWidth + 'px';
        return {
          transform: `translateX(-50%) translateX(${translateX})`,
        };
      },
    },
    methods: {
      async fetchCarouselItems() {
        try {
          const response = await fetch(`http://localhost:8080/games/all`, {
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
      redirectToItemList(itemId) {
        this.$router.push(`/game/${itemId}`);
      },
      slideCarousel(direction) {
        const maxPosition = this.carouselItems.length - this.itemsToShow;
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
      handleWindowResize() {
        this.checkMobile();
        this.currentPosition = 0;
      },
      checkMobile() {
        this.isMobile = window.innerWidth <= 600;
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
  padding: 20px;
  width: 184px;
  height: 240px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  position: relative;
  cursor: pointer;
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
  margin-left: -30px;
}

.right-arrow {
  margin-right: -30px;
}

@media screen and (max-width: 600px) {
  .carousel-item {
    width: 80%;
    max-width: 240px;
    margin: 0 auto;
  }

  .empty-carousel {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 8rem;
  }

  .navigation-arrows {
    z-index: 1;
  }

  .left-arrow {
    margin-left: 0;
  }

  .right-arrow {
    margin-right: 0;
  }

  .carousel {
    height: auto;
  }
  
  .carousel-item:hover .carousel-overlay {
    opacity: 1;
  }
}

@media screen and (max-width: 400px) {
  .carousel-item {
    max-width: 200px;
  }
}
</style>
