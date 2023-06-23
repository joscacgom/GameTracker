<template>
    <div class="mylists-container">
      <SidebarComponent></SidebarComponent>
      <div class="header-container">
        <h1 class="title">My Lists</h1>
      </div>
  
      <div class="carousel-container">
        <div class="carousel-item" v-for="item in carouselItems" :key="item.id" @click="redirectToItemList(item.id)">
          <img :src="item.imageUrl" :alt="item.title" class="carousel-image" />
          <div class="carousel-overlay">
            <h3>{{ item.title }}</h3>
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
  
  export default {
    name: 'MyListsComponent',
    components: {
      FontAwesomeIcon,
      SidebarComponent,
      CreateListComponent,
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
        showPopup: false,
      };
    },
    methods: {
      createNewList() {
        this.showPopup = true;
      },
      redirectToItemList(itemId) {
        this.$router.push(`/list/${itemId}`);
    },
      closePopup() {
        this.showPopup = false;
      },
    },
  };
  </script>
  
  <style scoped>
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
  
  .carousel-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    margin-top: 2rem;
    margin-left: 15rem;
  }
  
  .carousel-item {
    width: calc(100% / 4 - 1rem);
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
  </style>
  