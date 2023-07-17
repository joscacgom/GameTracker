package co.empathy.academy.gametracker.controllers.elastic;

import co.empathy.academy.gametracker.models.elastic.ElasticGame;
import co.empathy.academy.gametracker.services.elastic.ElasticGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * Obtains a list of games from RAWG API
     * @return a response with the list of games in its body, ResponseEntity<List<ElasticGame>>,
     *  or the Http status (error)
     */
    @CrossOrigin(origins = "https://localhost:9200")
    @GetMapping("/listGames")
    public ResponseEntity<List<ElasticGame>> getAListOfGames() {
        List<ElasticGame> games = elasticGameService.getAListOfGames();
        if (games != null)
            return ResponseEntity.ok(games); // Retorno: respuesta ok con games en el cuerpo.
        else // Retorno: respuesta de error con mensaje.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Find all games by genre name specified from Elastic Search index
     * @param name of the genre
     * @return a list of games with that genre
     */
    @GetMapping("/all/genre/{name}")
    public List<ElasticGame> findAllByGenreName(@PathVariable String name) {
        return elasticGameService.findAllByGenreName(name);
    }

}