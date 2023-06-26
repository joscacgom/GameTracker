package co.empathy.academy.gametracker.services;

import co.empathy.academy.gametracker.models.Game;
import co.empathy.academy.gametracker.models.Platform;
import co.empathy.academy.gametracker.repositories.GameRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class APIService {

    @Autowired
    private final GameRepository gameRepository;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public APIService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAListOfGames() {
        // Llamada a RAWGAPI
        Request request = new Request.Builder()
                .url("https://rawg-video-games-database.p.rapidapi.com/games?key=6ecc279ebc114b0194d9600c889c4ab9")
                .get()
                .addHeader("X-RapidAPI-Key", "2f671919fcmsh2c7423d14d84b80p1f2e01jsnfe69a30cb963")
                .addHeader("X-RapidAPI-Host", "rawg-video-games-database.p.rapidapi.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // Obtencion de la respuesta como cadena JSON
                assert response.body() != null;
                String jsonResponse = response.body().string();

                if (jsonResponse.isBlank())
                    throw new IOException("Error response body is blank.");

                // Des-serializar: Cadena JSON a Java
                objectMapper.registerModule(new JavaTimeModule()); // Java 8 date/time module
                // El json contiene en "results" los juegos
                JsonNode gamesNode = objectMapper.readTree(jsonResponse).get("results");
                List<Game> games = new ArrayList<>();
                if (gamesNode != null) {
                    for (JsonNode gameNode : gamesNode) {
                        Game game = objectMapper.readValue(gameNode.toString(), Game.class);

                        // El json contiene en "platforms [{ platform" las plataformas
                        List<Platform> platforms = new ArrayList<>();
                        JsonNode platformsNode = gameNode.get("platforms");
                        if (platformsNode != null) {
                            for (JsonNode platformNode : platformsNode) {
                                JsonNode platformObj = platformNode.get("platform");
                                Platform platform = objectMapper.readValue(platformObj.toString(), Platform.class);
                                platforms.add(platform);
                            }
                        }

                        game.setPlatforms(platforms);
                        games.add(game);
                    }
                }

                // Guarda los juegos en la bbdd Mongo
                gameRepository.saveAll(games);

                return games;
            }
            else
                throw new IOException("Error on API response.");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getGameDetails(String game_id) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://rawg-video-games-database.p.rapidapi.com/games/" + game_id)
                .get()
                .addHeader("X-RapidAPI-Key", "2f671919fcmsh2c7423d14d84b80p1f2e01jsnfe69a30cb963")
                .addHeader("X-RapidAPI-Host", "rawg-video-games-database.p.rapidapi.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().string();
            }
            else
                throw new IOException("Error on API response.");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
