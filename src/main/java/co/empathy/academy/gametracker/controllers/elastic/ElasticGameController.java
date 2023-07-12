package co.empathy.academy.gametracker.controllers.elastic;

import co.empathy.academy.gametracker.models.elastic.ElasticGame;
import co.empathy.academy.gametracker.services.elastic.ElasticGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/elastic/game")
public class ElasticGameController {

    @Autowired
    private ElasticGameService elasticGameService;

    public ElasticGameController(ElasticGameService elasticGameService) {
        this.elasticGameService = elasticGameService;
    }

    /**
     * Find all games by genre name specified from Elastic Search index
     * @param name of the genre
     * @return a list of games with that genre
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/all/genre/{name}")
    public List<ElasticGame> findAllByGenreName(@PathVariable String name) {
        return elasticGameService.findAllByGenreName(name);
    }

}
