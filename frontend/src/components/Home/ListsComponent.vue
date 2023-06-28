<template>
  <div class="lists-container">
    <div v-if="loading" class="loading-container">
      <LoadingComponent type="small"></LoadingComponent>
    </div>
    <div v-else class="carousel-wrapper">
      <div class="carousel" :style="carouselStyle">
        <div v-if="!carouselItems || carouselItems.length === 0" class="empty-carousel">
          <EmptyComponent type="small"></EmptyComponent>
        </div>
        <div class="carousel-item" v-else v-for="item in visibleItems" :key="item.id" @click="redirectToItemList(item.id)">
          <div :class="{'carousel-image': true, 'default-image': !item.games || item.games.length === 0}">
            <img v-if="item.games && item.games.length > 0" :src="item.games[0].background_image" :alt="item.status" />
            <img v-else src="../../assets/placeholder/default-list-img.png" alt="Default Image" />
            <div v-if="!item.games || item.games.length === 0" class="carousel-overlay">
              <h3>{{ item.status }}</h3>
            </div>
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
  </div>
</template>

<script>
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import EmptyComponent from '@/components/Empty/EmptyComponent.vue';
import LoadingComponent from '@/components/Loading/LoadingComponent.vue';

export default {
  name: 'ListsComponent',
  components: {
    FontAwesomeIcon,
    EmptyComponent,
    LoadingComponent,
  },
  data() {
    return {
      carouselItems: [],
      currentPosition: 0, // Current position of the carousel
      itemsToShow: 3, // Number of items to show at a time
      loading: true,
      hoveredItem: null,
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
    redirectToItemList(itemId) {
      this.$router.push(`/list/${itemId}`);
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
.lists-container {
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
</style>
