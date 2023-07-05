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
          <select v-model="selected">
            <option disabled>Please select one</option>
            <option v-for="(filter, index) in filters" :key="index">{{ filter }}</option>
          </select>
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
        filters: ['Show all', 'Filter 1', 'Filter 2', 'Filter 3', 'Filter 4'],
        selectedFilters: [],
        selected: 'Show all'
      };
    }
  }
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');

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

.input-search:hover, .input-search:focus {
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
  display: flex;
  justify-content: right;
  align-items: right;
  margin-top: -5%;
}

.filters-container select {
  position: relative;
  width: 150px;
  border: 1px solid transparent;
  border-bottom: 1px solid rgb(252, 9, 76);
  border-radius: 0;
  font-family: Poppins;
  color: rgb(252, 9, 76);
  font-size: 16px;
  cursor: pointer;
}

</style>
