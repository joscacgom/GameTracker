package co.empathy.academy.gametracker.services.elastic;

import co.empathy.academy.gametracker.models.mongo.*;
import co.empathy.academy.gametracker.models.elastic.ElasticGame;
import co.empathy.academy.gametracker.repositories.elastic.ElasticGameRepository;
import co.empathy.academy.gametracker.repositories.mongo.GameRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElasticGameService {

    private final ElasticGameRepository elasticGameRepository;
    private final GameRepository mongoGameRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public ElasticGameService(ElasticGameRepository elasticGameRepository, GameRepository mongoGameRepository, ElasticsearchOperations elasticsearchOperations) {
        this.elasticGameRepository = elasticGameRepository;
        this.mongoGameRepository = mongoGameRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /**
     * Obtains a list of games from MongoDB
     * @return games, a List<ElasticGame>
     */
    public List<ElasticGame> getAListOfGames() {
        List<Game> mongoGames = mongoGameRepository.findAll();
        List<ElasticGame> elasticGames = new ArrayList<>();
        for (Game game: mongoGames) {
            ElasticGame elasticGame = new ElasticGame(
                    game.getId(),
                    game.getName(),
                    game.getDescription(),
                    game.getReleased(),
                    game.getBackground_image(),
                    game.getPlaytime(),
                    game.getPlatforms(),
                    game.getGenres(),
                    game.getDevelopers(),
                    game.getPublishers(),
                    game.getMetacritic(),
                    game.getTba(),
                    game.getRating(),
                    game.getEsrb_rating());
            elasticGames.add(elasticGame);
        }
        elasticGameRepository.saveAll(elasticGames);
        return elasticGames;
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
     * Search games by the provided name.
     *
     * @param name The name o the search criteria
     * @return a list of games matching the search criteria
     */
    public List<ElasticGame> searchGamesByName(String name) {
        String analyzedName = name.toLowerCase();

        QueryBuilder matchQuery = QueryBuilders.matchQuery("name", analyzedName);

        // Build the native search query
        QueryBuilder finalQuery = QueryBuilders.boolQuery().must(matchQuery);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(finalQuery).build();

        // Execute the search query
        SearchHits<ElasticGame> searchHits = elasticsearchOperations.search(searchQuery, ElasticGame.class);
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

}
