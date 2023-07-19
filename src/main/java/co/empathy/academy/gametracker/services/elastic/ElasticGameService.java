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
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElasticGameService {

    private final ElasticGameRepository elasticGameRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Value("${rapidapi.key}")
    private String rapidAPIKey;

    @Value("${rapidapi.host}")
    private String rapidAPIHost;

    @Value("${rapidapi.url.key}")
    private String rapidAPIUrlKey;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ElasticGameService(ElasticGameRepository elasticGameRepository, ElasticsearchOperations elasticsearchOperations) {
        this.elasticGameRepository = elasticGameRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /**
     * Obtains a list of games from RAWG API
     * @return games, a List<ElasticGame>
     */
    public List<ElasticGame> getAListOfGames() {
        // Call to RAWG API
        Request request = new Request.Builder()
                .url("https://rawg-video-games-database.p.rapidapi.com/games?key=" + rapidAPIUrlKey)
                .get()
                .addHeader("X-RapidAPI-Key", rapidAPIKey)
                .addHeader("X-RapidAPI-Host", rapidAPIHost)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // Obtain the response as a JSON string
                assert response.body() != null;
                String jsonResponse = response.body().string();

                if (jsonResponse.isBlank())
                    throw new IOException("Error response body is blank.");

                // Deserialize JSON string to Java objects
                objectMapper.registerModule(new JavaTimeModule()); // Java 8 date/time module
                JsonNode gamesNode = objectMapper.readTree(jsonResponse).get("results");
                List<ElasticGame> games = new ArrayList<>();
                if (gamesNode != null) {
                    for (JsonNode gameNode : gamesNode) {
                        ElasticGame game = objectMapper.readValue(gameNode.toString(), ElasticGame.class);
                        if (game == null)
                            throw new IOException("Error while mapping: null game.");

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
                elasticGameRepository.findAll().forEach(System.out::println);
                return games;
            } else {
                throw new IOException("Error on API response.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Search for games based on the provided parameters.
     *
     * @param game The game object containing the search criteria
     * @return a list of games matching the search criteria
     */
    public List<ElasticGame> searchGamesByParameters(ElasticGame game) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        Class<? extends ElasticGame> gameClass = game.getClass();

        // Iterate over all fields of the game object
        for (Field field : gameClass.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                // Check if the field is not null and not the "id" field
                Object fieldValue = field.get(game);
                if (fieldValue != null && !field.getName().equals("id")) {
                    // Add a filter for the non-null field
                    queryBuilder.filter(QueryBuilders.matchQuery(field.getName(), fieldValue));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Execute the search query
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder);
        SearchHits<ElasticGame> searchHits = elasticsearchOperations.search(searchQueryBuilder.build(), ElasticGame.class);
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    /**
     * Find all games by genre name specified from Elasticsearch index
     *
     * @param name the name of the genre
     * @return a list of games with that genre
     */
    public List<ElasticGame> findAllByGenreName(String name) {
        return elasticGameRepository.findAllByGenreName(name);
    }
}
