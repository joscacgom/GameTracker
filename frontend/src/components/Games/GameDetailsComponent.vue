<template>
  <div class="game-details-container">
    <SidebarComponent></SidebarComponent>
    <div class="game">
      <div class="game-cover">
        <div class="game-cover_item" :style="{ backgroundImage: `url(${game.background_image})` }"></div>
      </div>
      <div class="game-details">
        <div class="game-details_genres">
          <div class="game-details_genres_genre" v-for="(genre, $index) in game.genres" :key="$index">
            <div v-text="genre"></div>
          </div>
        </div>
        <h1 class="game-details_name">{{ game.name }}</h1>
        <p class="game-details_released">Released on: {{ game.released }}</p>
        <p class="game-details_description" v-html="game.description"></p>
        <p class="game-details_playtime">Playtime: {{ game.playtime }}</p>
        <div class="game-details_platforms">Platforms:
          <div class="game-details_platforms_platform" v-for="(platform, $index) in game.platforms" :key="$index">
            <div v-text="platform"></div>
          </div>
        </div>
      </div>
      <div class="game-bt">
        <button class="game-bt_add"> Add to list</button>
      </div>
    </div>
  </div>
</template>

<script>
import SidebarComponent from '@/components/Layout/SidebarComponent.vue';

export default {
  name: 'GameDetailsComponent',
  components: {
    SidebarComponent
  },
  data() {
    return {
      game: {
        name: "Grand Theft Auto V",
        description: "<p>Rockstar Games se hizo más grande desde su entrega anterior de la serie. Obtienes la construcción del mundo complicada y realista de Liberty City de GTA4 en el escenario de Los Santos, un viejo favorito de los fans, GTA San Andreas. 561 vehículos diferentes (incluidos todos los transportes que puede operar) y la cantidad aumenta con cada actualización.<br />Narración simultánea desde tres perspectivas únicas:<br />Sigue a Michael, ex-criminal que vive su vida de ocio lejos del pasado, Franklin, un niño que busca un futuro mejor, y Trevor, el pasado exacto del que Michael está tratando de huir.<br />GTA Online proporcionará muchos desafíos adicionales incluso para los jugadores experimentados, recién llegados del modo historia. Ahora tendrás otros jugadores cerca que pueden ayudarte con la misma probabilidad que arruinar tu misión. Los jugadores pueden experimentar todas las mecánicas de GTA actualizadas a través del personaje personalizable único, y el contenido de la comunidad combinado con el sistema de nivelación tiende a mantener a todos ocupados y comprometidos.</p>",
        released: "2013-09-16",
        background_image: "https://media.rawg.io/media/games/456/456dea5e1c7e3cd07060c14e96612001.jpg",
        playtime: 73,
        platforms: ["PlayStation", "Xbox"],
        genres: ["Action", "Adventure"]
      }
    };
  },
  methods: {
    logout() {
      this.$router.push('/');
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
  margin: 2rem;
  margin-left: 10rem;
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
}
</style>