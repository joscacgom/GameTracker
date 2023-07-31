package co.empathy.academy.gametracker.services;

import co.empathy.academy.gametracker.models.Game;
import co.empathy.academy.gametracker.models.Platform;
import co.empathy.academy.gametracker.repositories.GameRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class APIService {

    private GameRepository gameRepository;

    @Value("${rapidapi.key}")
    private String rapidAPIKey;

    @Value("${rapidapi.host}")
    private String rapidAPIHost;

    @Value("${rapidapi.url.key}")
    private String rapidAPIUrlKey;

    private OkHttpClient client;
    private ObjectMapper objectMapper;

    public APIService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Java 8 date/time module
    }

    /**
     * Obtains a list of games from RAWG API
     * @return games, a List<Game>
     */
    public List<Game> getAListOfGames() {
        Request request = callRAWGAPI("https://rawg-video-games-database.p.rapidapi.com/games?key=");

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonResponse = obtainJsonResponse(response);

                // Unserialize: Json a Java
                JsonNode gamesNode = objectMapper.readTree(jsonResponse).get("results");
                if (gamesNode == null)
                    throw new IOException("Error while mapping: null json.");

                List<Game> games = new ArrayList<>();
                for (JsonNode gameNode : gamesNode) {
                    Game game = objectMapper.readValue(gameNode.toString(), Game.class);
                    if (game == null)
                        throw new IOException("Error while mapping: null game.");

                    JsonNode platformsNode = gameNode.get("platforms");
                    List<Platform> platforms = obtainPlatforms(platformsNode);
                    game.setPlatforms(platforms);
                    games.add(game);
                }

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
        Request request = callRAWGAPI("https://rawg-video-games-database.p.rapidapi.com/games/" + game_id + "?key=");

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonResponse = obtainJsonResponse(response);

                // Unserialize: Json a Java
                Game game = objectMapper.readValue(jsonResponse, Game.class);
                if (game == null)
                    throw new IOException("Error while mapping: null game.");

                JsonNode platformsNode = objectMapper.readTree(jsonResponse).get("platforms");
                List<Platform> platforms = obtainPlatforms(platformsNode);
                game.setPlatforms(platforms);

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

    /**
     * Make the call to the RAWG API
     * Headers needed added
     * @param call url string
     * @return the request
     */
    private Request callRAWGAPI(String call) {
        return new Request.Builder()
                .url(call + rapidAPIUrlKey)
                .get()
                .addHeader("X-RapidAPI-Key", rapidAPIKey)
                .addHeader("X-RapidAPI-Host", rapidAPIHost)
                .build();
    }

    /**
     * Obtain the JSON body of the response
     * @param response Response
     * @return jsonResponse
     * @throws IOException if the response body is blank
     */
    private String obtainJsonResponse(Response response) throws IOException {
        assert response.body() != null;
        String jsonResponse = response.body().string();

        if (jsonResponse.isBlank())
            throw new IOException("Error response body is blank.");

        return jsonResponse;
    }

    /**
     * Obtain the platforms of each video game
     * @param platformsNode JsonNode
     * @return platforms a list of Platform objects
     * @throws JsonProcessingException if the json value can not be readed
     */
    private List<Platform> obtainPlatforms(JsonNode platformsNode) throws JsonProcessingException {
        List<Platform> platforms = new ArrayList<>();
        if (platformsNode != null) {
            for (JsonNode platformNode : platformsNode) {
                JsonNode platformObj = platformNode.get("platform");
                Platform platform = objectMapper.readValue(platformObj.toString(), Platform.class);
                platforms.add(platform);
            }
        }
        return platforms;
    }

}
