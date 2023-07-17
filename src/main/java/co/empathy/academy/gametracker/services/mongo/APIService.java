package co.empathy.academy.gametracker.services.mongo;

import co.empathy.academy.gametracker.models.mongo.Game;
import co.empathy.academy.gametracker.models.mongo.Platform;
import co.empathy.academy.gametracker.repositories.mongo.GameRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class APIService {

    @Autowired
    private final GameRepository gameRepository;

    @Value("${rapidapi.key}")
    private String rapidAPIKey;

    @Value("${rapidapi.host}")
    private String rapidAPIHost;

    @Value("${rapidapi.url.key}")
    private String rapidAPIUrlKey;


    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public APIService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Obtains a list of games from RAWG API
     * @return games, a List<Game>
     */
    public List<Game> getAListOfGames() {
        // Llamada a RAWGAPI
        Request request = new Request.Builder()
                .url("https://rawg-video-games-database.p.rapidapi.com/games?key=" + rapidAPIUrlKey)
                .get()
                .addHeader("X-RapidAPI-Key", rapidAPIKey)
                .addHeader("X-RapidAPI-Host", rapidAPIHost)
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
                        if (game == null)
                            throw new IOException("Error while mapping: null game.");

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

    /**
     * Obtains more information about a game from the RAWG API
     * @param game_id, Long, identifier of the game
     * @return game, a Game object with more details
     */
    public Game getGameDetails(Long game_id) {
        // Llamada a RAWGAPI
        Request request = new Request.Builder()
                .url("https://rawg-video-games-database.p.rapidapi.com/games/" + game_id + "?key=" + rapidAPIUrlKey)
                .get()
                .addHeader("X-RapidAPI-Key", rapidAPIKey)
                .addHeader("X-RapidAPI-Host", rapidAPIHost)
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
                Game game = objectMapper.readValue(jsonResponse, Game.class);
                if (game == null)
                    throw new IOException("Error while mapping: null game.");

                // El json contiene en "platforms" las plataformas
                JsonNode platformsNode = objectMapper.readTree(jsonResponse).get("platforms");
                List<Platform> platforms = new ArrayList<>();
                if (platformsNode != null) {
                    for (JsonNode platformNode : platformsNode) {
                        JsonNode platformObj = platformNode.get("platform");
                        Platform platform = objectMapper.readValue(platformObj.toString(), Platform.class);
                        platforms.add(platform);
                    }
                }
                game.setPlatforms(platforms);

                // Guarda el juego en la bbdd Mongo
                gameRepository.save(game);

                return game;
            }
            else
                throw new IOException("Error on API response.");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
