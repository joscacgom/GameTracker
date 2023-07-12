package co.empathy.academy.gametracker.services.elastic;

import co.empathy.academy.gametracker.models.elastic.ElasticGame;
import co.empathy.academy.gametracker.repositories.GameRepository;
import co.empathy.academy.gametracker.repositories.elastic.ElasticGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticGameService {

    private final ElasticGameRepository elasticGameRepository;

    public ElasticGameService(ElasticGameRepository elasticGameRepository) {
        this.elasticGameRepository = elasticGameRepository;
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
