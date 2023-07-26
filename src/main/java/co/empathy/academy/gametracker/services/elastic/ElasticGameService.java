package co.empathy.academy.gametracker.services.elastic;

import co.empathy.academy.gametracker.models.mongo.*;
import co.elastic.clients.elasticsearch.core.search.ScoreMode;
import co.empathy.academy.gametracker.models.elastic.ElasticGame;
import co.empathy.academy.gametracker.repositories.elastic.ElasticGameRepository;
import co.empathy.academy.gametracker.repositories.mongo.GameRepository;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
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
     * @param name The name of the search criteria
     * @return a list of games matching the search criteria
     */
    public List<ElasticGame> searchGamesByName(String name) {
        String analyzedName = name.toLowerCase();

        // Use wildcard query to perform a partial match
        QueryBuilder wildcardQuery = QueryBuilders.wildcardQuery("name", "*" + analyzedName + "*");

        // Build the native search query
        QueryBuilder finalQuery = QueryBuilders.boolQuery().must(wildcardQuery);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(finalQuery).build();

        // Execute the search query
        SearchHits<ElasticGame> searchHits = elasticsearchOperations.search(searchQuery, ElasticGame.class);
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

        public List<ElasticGame> searchWithFilters(
            String name,
            String genre,
            String platform,
            String developer,
            String publisher,
            String playtime,
            String metacritic,
            String esrb,
            String tba,
            String rating,
            String year
    ) {
        // Build your search query based on the provided filter parameters
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // Add a must clause for the name search
        if (name != null && !name.isEmpty()) {
            String analyzedName = name.toLowerCase();
            QueryBuilder wildcardQuery = QueryBuilders.wildcardQuery("name", "*" + analyzedName + "*");
            boolQueryBuilder.must(wildcardQuery);
        }

        // For nested fields, use nested queries
        if (genre != null && !genre.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.nestedQuery("genres",
                    QueryBuilders.matchQuery("genres.name.keyword", genre), null));
        }

        if (platform != null && !platform.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.nestedQuery("platforms",
                    QueryBuilders.matchQuery("platforms.name.keyword", platform), null));
        }

        if (developer != null && !developer.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.nestedQuery("developers",
                    QueryBuilders.matchQuery("developers.name.keyword", developer), null));
        }

        if (publisher != null && !publisher.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.nestedQuery("publishers",
                    QueryBuilders.matchQuery("publishers.name.keyword", publisher), null));
        }

        if (playtime != null && !playtime.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("playtime", playtime));
        }

        if (metacritic != null && !metacritic.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("metacritic").gt(Integer.parseInt(metacritic)));
        }

        if (esrb != null && !esrb.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("esrb_rating.name.keyword", esrb));
        }

        if (tba != null && !tba.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("tba", Boolean.parseBoolean(tba)));
        }

        if (rating != null && !rating.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("rating.keyword", rating));
        }

        if (year != null && !year.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("year.keyword", year));
        }

        searchQueryBuilder.withQuery(boolQueryBuilder);

        // Execute the search query and return the matching games
        NativeSearchQuery searchQuery = searchQueryBuilder.build();
        SearchHits<ElasticGame> searchHits = elasticsearchOperations.search(searchQuery, ElasticGame.class);
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }


}
