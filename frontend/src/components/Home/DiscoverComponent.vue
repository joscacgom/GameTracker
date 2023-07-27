<template>
  <div class="discover-container">
    <SidebarComponent></SidebarComponent>
    <div class="content">
      <h1 class="browse-title">Browse</h1>
      <div class="browse-container">
        <input class="input-search" v-model="searchQuery" @input="searchGames" type="text" placeholder="Search by name">
      </div>
      <div class="games-container">
        <h2 class="games-title">Games</h2>
        <div class="filters-container">
          <div class="filters-scroll-container">
            <div class="filter-category" v-for="(category, index) in visibleCategories" :key="index">
              <h3>{{ category.name }}</h3>
              <select v-model="selectedFilters[category.name]" @change="searchGames">
                <option value="">All</option>
                <option v-for="(filter, filterIndex) in category.filters" :key="filterIndex">{{ filter }}</option>
              </select>
            </div>
          </div>
          <button class="scroll-button" @click="scrollLeft">&lt;</button>
          <button class="scroll-button" @click="scrollRight">&gt;</button>
        </div>
        <!-- Display results here or all games component -->
        <div v-if="showFilteredGames">
          <FilterGamesComponent :searchQuery="searchQuery" :selectedFilters="selectedFilters" />
        </div>
        <div v-else>
          <AllGamesComponent/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SidebarComponent from '@/components/Layout/SidebarComponent.vue';
import AllGamesComponent from '@/components/Home/AllGamesComponent.vue';
import FilterGamesComponent from '@/components/Home/FilterGamesComponent.vue';

export default {
  name: 'DiscoverComponent',
  components: {
    SidebarComponent,
    AllGamesComponent,
    FilterGamesComponent
  },
  data() {
    return {
      searchQuery: '',
      selectedFilters: {},
      filterCategories: [
        {
          name: 'Genre',
          filters: ['Action', 'Indie', 'Adventure', 'RPG', 'Strategy', 'Shooter', 'Casual', 'Simulation', 'Puzzle', 'Arcade', 'Platformer', 'Massively Multiplayer', 'Racing' ,'Sports', 'Fighting', 'Family', 'Board Games', 'Educational', 'Card' ]
        },
        {
          name: 'Platform',
          filters: ['PC', 'PlayStation 5', 'Xbox Series S/X', 'Nintendo Switch', 'Android', 'iOS', 'PlayStation 4', 'Xbox One', 'PlayStation 3', 'Xbox 360', 'Linux', 'macOS', 'PS Vita']
        },
        {
          name: 'Developers',
          filters: ['Valve Software', 'CD PROJEKT RED', 'Rockstar Games', 'Crystal Dynamics', 'Bethesda Game Studios', 'Irrational Games', 'Gearbox Software', 'DONTNOD Entertainment', 'Double Eleven', 'Santa Monica Studio', 'id Software']
        },
        {
          name: 'Publishers',
          filters: ['Valve', 'Rockstar Games', 'CD PROJEKT RED', 'Electronic Arts', 'Square Enix', 'Ubisoft', 'Bethesda Softworks', '2K Games', 'Activision', 'Microsoft Studios', 'Sony Interactive Entertainment', 'Nintendo']
        },
        {
          name: 'Playtime',
          filters: ['0-10', '10-20', '20-30', '30-40', '40-50', '50-60', '60-70', '70-80', '80-90', '90-100', '>100']
        },
        {
          name: 'Metacritic',
          filters: ['>80', '>70', '>60', '>50', '<50']
        },
        {
          name: 'ESRB',
          filters: ['Mature', 'Teen', 'Everyone 10+']
        },
        {
          name: 'TBA',
          filters: ['true', 'false']
        },
        {
          name: 'Rating',
          filters: ['1', '2', '3', '4', '5']
        },
        {
          name: 'Year',
          filters: ['2023', '2022', '2021', '2020', '2019', '2018', '2017', '2016', '2015', '2014', '2013', '2012', '2011', '2010', '2009', '2008', '2007', '2006', '2005', '2004' ]
        }
      ],
      visibleCategories: [],
      visibleStartIndex: 0,
      visibleEndIndex: 3
    };
  },
  computed: {
    filteredCategories() {
      return this.filterCategories;
    },
    showFilteredGames() {
      return this.searchQuery.trim() !== '' || Object.values(this.selectedFilters).some(filter => filter);
    }
  },
  mounted() {
    this.updateVisibleCategories();
  },
  methods: {
    scrollLeft() {
      if (this.visibleStartIndex > 0) {
        this.visibleStartIndex--;
        this.visibleEndIndex--;
        this.updateVisibleCategories();
      }
    },
    scrollRight() {
      if (this.visibleEndIndex < this.filterCategories.length - 1) {
        this.visibleStartIndex++;
        this.visibleEndIndex++;
        this.updateVisibleCategories();
      }
    },
    updateVisibleCategories() {
      this.visibleCategories = this.filterCategories.slice(
        this.visibleStartIndex,
        this.visibleEndIndex + 1
      );
    },
    searchGames() {
      if (this.searchQuery.trim() !== '' || Object.values(this.selectedFilters).some(filter => filter)) {

        this.$forceUpdate(); // Force a re-render to switch components
      }
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
  text-transform: uppercase;
  letter-spacing: 2.5px;
}

.games-container {
  padding: 20px;
}

.games-title {
  color: rgb(252, 9, 76);
  font-family: Poppins;
  font-size: 20px;
}

.filters-container {
  position: relative;
  width: 100%;
  overflow: hidden;
  margin-top: 1rem;
}

.filters-scroll-container {
  display: flex;
  flex-wrap: nowrap;
  padding-bottom: 15px;
  padding-right: 30px;
  padding-left: 40px;
  transition: transform 250ms ease-in-out;
  transform: translateX(0);
}

.filter-category {
  flex-shrink: 0;
  margin-right: 10px;
  font-family: Poppins;
  color: rgb(252, 9, 76);
  font-size: 14px;
}

.filter-category h3 {
  margin-left: 15px;
  margin-bottom: 5px;
}

.filter-category select {
  width: 150px;
  border: 1px solid transparent;
  border-bottom: 1px solid rgb(252, 9, 76);
  border-radius: 0;
  font-family: Poppins;
  color: #575756;
  font-size: 16px;
  cursor: pointer;
}

.scroll-button {
  position: absolute;
  top: 0;
  bottom: 0;
  width: 40px;
  background-color: transparent;
  border: none;
  font-size: 24px;
  color: rgb(252, 9, 76);
  cursor: pointer;
  transition: opacity 250ms ease-in-out;
  z-index: 1;
}

.scroll-button:hover {
  opacity: 0.7;
}

.scroll-button:first-child {
  left: 0;
  margin-right: 1rem;
}

.scroll-button:last-child {
  right: 0;
}

@media (max-width: 768px) {
  .discover-container {
    padding: 1rem;
    
  }

  .scroll-button:first-child {
    margin-right: 0;
  }

  .scroll-button:last-child {
      margin-left: 0;
  }

  .browse-container {
    flex-direction: column;
    align-items: flex-start;
    margin: 0 auto;
    max-width: 300px;
   
  }

  .input-search {
    width: 100%;
    padding: 12px;
  }

  .browse-title {
    text-align: center;
  }

  .filters-container {
    justify-content: center;
    max-width: 350px;
}

  .filters-container select {
    width: 100px;
  }
}
</style>
