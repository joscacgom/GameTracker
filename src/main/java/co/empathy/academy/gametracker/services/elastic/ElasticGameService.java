package co.empathy.academy.gametracker.services.elastic;

import co.empathy.academy.gametracker.models.mongo.Platform;
import co.empathy.academy.gametracker.models.elastic.ElasticGame;
import co.empathy.academy.gametracker.repositories.elastic.ElasticGameRepository;
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
public class ElasticGameService {

    private final ElasticGameRepository elasticGameRepository;

    @Value("${rapidapi.key}")
    private String rapidAPIKey;

    @Value("${rapidapi.host}")
    private String rapidAPIHost;

    @Value("${rapidapi.url.key}")
    private String rapidAPIUrlKey;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ElasticGameService(ElasticGameRepository elasticGameRepository) {
        this.elasticGameRepository = elasticGameRepository;
    }

    /**
     * Obtains a list of games from RAWG API
     * @return games, a List<ElasticGame>
     */
    public List<ElasticGame> getAListOfGames() {
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
                List<ElasticGame> games = new ArrayList<>();
                if (gamesNode != null) {
                    for (JsonNode gameNode : gamesNode) {
                        ElasticGame game = objectMapper.readValue(gameNode.toString(), ElasticGame.class);
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

                elasticGameRepository.saveAll(games);
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
     * Find all games by genre name specified from Elastic Search index
     * @param name of the genre
     * @return a list of games with that genre
     */
    public List<ElasticGame> findAllByGenreName(String name) {
        return elasticGameRepository.findAllByGenreName(name);
    }

}
