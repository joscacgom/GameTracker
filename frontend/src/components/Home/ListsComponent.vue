<template>
    <div class="lists-container">
      <div class="carousel-wrapper">
        <div class="carousel" :style="carouselStyle">
          <div class="carousel-item" v-for="item in visibleItems" :key="item.id">
            <img :src="item.imageUrl" :alt="item.title" class="carousel-image" />
            <div class="carousel-overlay">
              <h3>{{ item.title }}</h3>
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
  
  export default {
    name: 'ListsComponent',
    components: {
      FontAwesomeIcon,
    },
    data() {
      return {
        carouselItems: [
          {
            id: 1,
            title: 'Pending',
            imageUrl: require('@/assets/placeholder/mw2.jpeg'),
          },
          {
            id: 2,
            title: 'Ongoing',
            imageUrl: require('@/assets/placeholder/rome2.jpeg'),
          },
          {
            id: 3,
            title: 'Completed',
            imageUrl: require('@/assets/placeholder/alanwake.jpeg'),
          },
          {
            id: 4,
            title: 'Completed',
            imageUrl: require('@/assets/placeholder/alanwake.jpeg'),
          },
          {
            id: 5,
            title: 'Completed',
            imageUrl: require('@/assets/placeholder/mw2.jpeg'),
          },
        ],
        currentPosition: 0, // Current position of the carousel
        itemsToShow: 3, // Number of items to show at a time
      };
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
  </style>
  