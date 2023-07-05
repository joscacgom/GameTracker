<template>
  <div class="discover-container">
    <SidebarComponent></SidebarComponent>
    <div class="content">
      <h1 class="browse-title">Browse</h1>
      <div class="browse-container">
        <input class="input-search" type="text" placeholder="Search">
      </div>
      <div class="games-container">
        <h2 class="all-title">Games</h2>
        <div class="filters-container">
          <div class="filters-scroll-container">
            <div class="filter-category" v-for="(category, index) in filteredCategories" :key="index">
              <h3>{{ category.name }}</h3>
              <select v-model="category.selected">
                <option v-for="(filter, filterIndex) in category.filters" :key="filterIndex">{{ filter }}</option>
              </select>
            </div>
          </div>
        </div>
        <GamesComponent></GamesComponent>
      </div>
    </div>
  </div>
</template>

<script>
import SidebarComponent from '@/components/Layout/SidebarComponent.vue';
import GamesComponent from '@/components/Home/GamesComponent.vue';

export default {
  name: 'DiscoverComponent',
  components: {
    SidebarComponent,
    GamesComponent
  },
  data() {
    return {
      filterCategories: [
        {
          name: 'Genre',
          filters: ['Action', 'Adventure', 'RPG', 'Strategy', 'Sports']
        },
        {
          name: 'Platform',
          filters: ['PC', 'PlayStation', 'Xbox', 'Nintendo', 'Mobile']
        },
        {
          name: 'Developers',
          filters: ['Developer 1', 'Developer 2', 'Developer 3', 'Developer 4', 'Developer 5']
        },
        {
          name: 'Publishers',
          filters: ['Publisher 1', 'Publisher 2', 'Publisher 3', 'Publisher 4', 'Publisher 5']
        },
        {
          name: 'Metacritic',
          filters: ['>80', '>70', '>60', '>50', 'Any']
        },
        {
          name: 'ESRB',
          filters: ['E', 'E10+', 'T', 'M', 'AO']
        },
        {
          name: 'TBA',
          filters: ['TBA 1', 'TBA 2', 'TBA 3', 'TBA 4', 'TBA 5']
        },
        {
          name: 'Year',
          filters: ['2023', '2022', '2021', '2020', 'Any']
        }
      ]
    };
  },
  computed: {
    filteredCategories() {
      return this.filterCategories; 
    }
  },
  methods: {
    filterGames() {
      console.log('filtering games');
    }
  }
};
</script>

<style scoped>
  .discover-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
  }

  .browse-title {
    color: rgb(252, 9, 76);
    font-family: Poppins;
    font-size: 24px;
  }

  .browse-container {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-top: 2rem;
  }

  .input-search {
    width: 100%;
    padding: 12px;
    font-size: 14px;
    line-height: 18px;
    color: #575756;
    background-color: transparent;
    background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24'%3E%3Cpath d='M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z'/%3E%3Cpath d='M0 0h24v24H0z' fill='none'/%3E%3C/svg%3E");
    background-repeat: no-repeat;
    background-size: 18px 18px;
    background-position: 95% center;
    border-radius: 50px;
    border: 1px solid rgb(252, 9, 76);
    transition: all 250ms ease-in-out;
    blackface-visibility: hidden;
    transform-style: preserve-3d;
  }

  .input-search::placeholder {
    color: color(#575756 a(0.8));
    text-transform: uppercase;
    letter-spacing: 2.5px;
    padding: 1rem;
  }

  .input-search:hover,
  .input-search:focus {
    padding: 12px;
    outline: 0;
    border: 1px solid transparent;
    border-bottom: 1px solid rgb(252, 9, 76);
    border-radius: 0;
    background-position: 100% center;
  }

  .games-container {
    padding: 20px;
  }

  .all-title {
    color: rgb(252, 9, 76);
    font-family: Poppins;
    font-size: 20px;
  }

  .filters-container {
    width: 100%;
    overflow-x: auto;
    white-space: nowrap;
    margin-top: 1rem;
  }

  .filters-scroll-container {
    display: inline-flex;
    flex-wrap: nowrap;
    padding-bottom: 10px;
  }

  .filter-category {
    flex-shrink: 0;
    margin-right: 15px;
    font-family: Poppins;
    color: rgb(252, 9, 76);
    font-size: 16px;
  }

  .filter-category h3 {
    margin-bottom: 5px;
  }

  .filter-category select {
    width: 150px;
    border: 1px solid transparent;
    border-bottom: 1px solid rgb(252, 9, 76);
    border-radius: 0;
    font-family: Poppins;
    color: rgb(252, 9, 76);
    font-size: 16px;
    cursor: pointer;
  }

  @media (max-width: 768px) {
    .discover-container {
      margin-top: 5rem;
    }

    .browse-container {
      flex-direction: column;
      align-items: flex-start;
      margin-top: 1rem;
    }

    .input-search {
      width: 100%;
      padding: 12px;
    }

    .browse-title {
      text-align: center;
    }

    .filters-container {
      justify-content: flex-start;
      align-items: flex-start;
      margin-top: 1rem;
    }

    .filters-container select {
      width: 100%;
    }
  }
</style>
