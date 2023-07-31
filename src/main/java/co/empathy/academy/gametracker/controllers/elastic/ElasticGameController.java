package co.empathy.academy.gametracker.controllers.elastic;

import co.empathy.academy.gametracker.models.elastic.ElasticGame;
import co.empathy.academy.gametracker.services.elastic.ElasticGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("games/elastic")
public class ElasticGameController {

    private ElasticGameService elasticGameService;

    public ElasticGameController(ElasticGameService elasticGameService) {
        this.elasticGameService = elasticGameService;
    }

    /**
     * Obtains a list of games from MongoDB
     * @return a response with the list of games in its body, ResponseEntity<List<ElasticGame>>,
     *  or the Http status (error)
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/listGames")
    public ResponseEntity<List<ElasticGame>> getAListOfGames() {
        List<ElasticGame> games = elasticGameService.getAListOfGames();
        if (games != null)
            return ResponseEntity.ok(games); // Retorno: respuesta ok con games en el cuerpo.
        else // Retorno: respuesta de error con mensaje.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Search games by the provided parameters.
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
    @CrossOrigin(origins = "*")
    @GetMapping("/searchWithFilters")
    public ResponseEntity<List<ElasticGame>> searchWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String platform,
            @RequestParam(required = false) String developer,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String playtime,
            @RequestParam(required = false) String metacritic,
            @RequestParam(required = false) String esrb,
            @RequestParam(required = false) String tba,
            @RequestParam(required = false) String rating,
            @RequestParam(required = false) String year
    ) {
        List<ElasticGame> games = elasticGameService.searchWithFilters(
            name, genre, platform, developer, publisher, playtime, metacritic, esrb, tba, rating, year
        );
        return ResponseEntity.ok(games);
    }

}
