package co.empathy.academy.gametracker.services.elastic;

import co.empathy.academy.gametracker.models.mongo.*;
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

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
     * Search games by the provided name.
     *
     * @param name
     * @param genre
     * @param platform
     * @param developer
     * @param publisher
     * @param playtime
     * @param metacritic
     * @param esrb
     * @param tba
     * @param rating
     * @param year
     * @return a list of games matching the search criteria
     */
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
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (name != null && !name.isEmpty()) {
            String analyzedName = name.toLowerCase();
            
            BoolQueryBuilder nameQuery = QueryBuilders.boolQuery();
            nameQuery.should(QueryBuilders.matchQuery("name", name)); // Matches the analyzed "name" field
            nameQuery.should(QueryBuilders.wildcardQuery("name", "*" + analyzedName + "*")); // Partial match on analyzed "name" field
        
            boolQueryBuilder.must(nameQuery);
        }     
   
        if (genre != null && !genre.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("genres.name.keyword", genre));
        }

        if (platform != null && !platform.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("platforms.name.keyword", platform));
        }

        if (developer != null && !developer.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("developers.name.keyword", developer));
        }
    
        if (publisher != null && !publisher.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("publishers.name.keyword", publisher));
        }

        if (playtime != null && !playtime.isEmpty()) {
            if (playtime.equals(">100")) {
                boolQueryBuilder.must(QueryBuilders.rangeQuery("playtime").gt(100));
            } else {
                String[] playtimeRange = playtime.split("-");
                if (playtimeRange.length == 2) {
                    int minPlaytime = Integer.parseInt(playtimeRange[0]);
                    int maxPlaytime = Integer.parseInt(playtimeRange[1]);
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("playtime").from(minPlaytime).to(maxPlaytime));
                }
            }
        }

        if (metacritic != null && !metacritic.isEmpty()) {
            if (metacritic.startsWith(">")) {
                int value = Integer.parseInt(metacritic.substring(1));
                boolQueryBuilder.must(QueryBuilders.rangeQuery("metacritic").gt(value));
            } else if (metacritic.startsWith("<")) {
                int value = Integer.parseInt(metacritic.substring(1));
                boolQueryBuilder.must(QueryBuilders.rangeQuery("metacritic").lt(value));
            } else {
                int value = Integer.parseInt(metacritic);
                boolQueryBuilder.must(QueryBuilders.matchQuery("metacritic", value));
            }
        }

        if (esrb != null && !esrb.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("esrb_rating.name.keyword", esrb));
        }

        if (tba != null && !tba.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("tba", Boolean.parseBoolean(tba)));
        }
        if (rating != null && !rating.isEmpty()) {
            int ratingValue = Integer.parseInt(rating);
            boolQueryBuilder.must(QueryBuilders.termQuery("rating", ratingValue));
        }
        

        if (year != null && !year.isEmpty()) {
            try {
                LocalDate selectedYear = LocalDate.parse(year + "-01-01");
                boolQueryBuilder.must(QueryBuilders.rangeQuery("released").gte(selectedYear));
                LocalDate nextYear = selectedYear.plusYears(1);
                boolQueryBuilder.must(QueryBuilders.rangeQuery("released").lt(nextYear));
            } catch (DateTimeParseException ex) {
                // Invalid year format, ignore the filter
            }
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
