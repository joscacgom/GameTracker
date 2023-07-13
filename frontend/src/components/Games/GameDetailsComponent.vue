<template>
  <div class="game-details-container">
    <SidebarComponent></SidebarComponent>
    <div class="game" v-if="game">
      <div class="game-cover">
        <div class="game-cover_item" :style="{ backgroundImage: `url(${game.background_image})` }"></div>
      </div>
      <div class="game-details">
        <div class="game-details_genres">
          <div class="game-details_genres_genre" v-for="(genre, $index) in game.genres" :key="$index">
            <div v-text="genre.name"></div>
          </div>
        </div>
        <h1 class="game-details_name">{{ game.name }}</h1>
        <p class="game-details_released"><span class="highlight">Released on: </span>{{ game.released }}</p>
        <p class="game-details_description" v-html="game.description"></p>
        <p class="game-details_playtime"><span class="highlight">Playtime: </span>{{ game.playtime }} hours</p>
        <div class="game-details_platforms"><span class="highlight">Platforms:</span>
          <div class="game-details_platforms_platform" v-for="(platform, $index) in game.platforms" :key="$index">
            <div v-text="platform.name"></div>
          </div>
        </div>
      </div>
      <div class="game-bt">
        <button class="game-bt_add" @click="addToList"> Add to list</button>
        <edit-game-component :show-popup="showPopup" @close="closePopup"></edit-game-component>
      </div>
    </div>
    <div class="loading" v-else>Loading...</div>
  </div>
</template>

<script>
import SidebarComponent from '@/components/Layout/SidebarComponent.vue';
import EditGameComponent from '@/components/Games/EditGameComponent.vue';
export default {
  name: 'GameDetailsComponent',
  components: {
    SidebarComponent,
    EditGameComponent,
  },
  data() {
    return {
      game: {},
      showPopup: false
    };
  },
  async mounted() {
    try {
      const id = this.$route.params.gameId;
      const response = await fetch(`http://localhost:8080/api/game/${id}`);
      if (!response.ok)
        throw new Error('Unable to fetch game data');
      const data = await response.json();
      this.game = await data;
    } catch(error) {
      console.error(error);
    }
  },
  methods: {
    addToList() {
      this.showPopup = true;
    },
    closePopup() {
      this.showPopup = false;
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');

.game-details-container {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
}

.game {
  margin: 2em;
  margin-left: 10em;
  width: 900px;
  box-shadow: 0px 15px 35px -5px rgba(50, 88, 130, 0.32);
  border-radius: 15px;
  padding: 20px;
}

.game-cover {
  width: 850px;
  height: 350px;
  margin: 25px;
  position: relative;
  border-radius: 15px;
}

.game-cover_item {
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  width: 100%;
  height: 100%;
  border-radius: 15px;
  position: absolute;
}

.game-cover_item:before {
  content: "";
  background: inherit;
  width: 100%;
  height: 100%;
  box-shadow: 0px 10px 40px 0px rgba(76, 70, 124, 0.5);
  display: block;
  position: absolute;
  top: 30px;
  transform: scale(0.9);
  filter: blur(10px);
  opacity: 0.9;
  border-radius: 15px;
}

.game-cover_item:after {
  content: "";
  background: inherit;
  width: 100%;
  height: 100%;
  box-shadow: 0px 10px 40px 0px rgba(76, 70, 124, 0.5);
  display: block;
  position: absolute;
  border-radius: 15px;
}

.game-details {
  font-family: Poppins;
  margin: 35px;
}

.game-details_genres, .game-details_platforms {
  display: flex;
}

.game-details_genres_genre, .game-details_platforms_platform {
  margin-left: 10px;
  opacity: 0.7;
}

.game-details_name {
  font-weight: bold;
}

.game-details_description {
  text-align: justify;
}

.game-bt {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
}

.game-bt_add {
  position: relative;
  width: 300px;
  height: 50px;
  background-color: rgb(252, 9, 76);
  color: white;
  font-family: Poppins;
  font-size: 18px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 12px;
  border: none;
  cursor: pointer;
}

.highlight {
  opacity: 0.7;
}

.loading {
  color: rgb(252, 9, 76);
  font-family: Poppins;
  font-size: 20px;
}

span {
color: rgb(252, 9, 76);
}
</style>