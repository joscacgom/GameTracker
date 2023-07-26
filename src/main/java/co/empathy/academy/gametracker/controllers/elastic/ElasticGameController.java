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
     * @param game The game object containing the search criteria
     * @return a list of games matching the search criteria
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/searchGames")
    public ResponseEntity<List<ElasticGame>> searchGamesByParameters(@RequestBody ElasticGame game) {
        List<ElasticGame> matchingGames = elasticGameService.searchGamesByParameters(game);
        if (matchingGames != null && !matchingGames.isEmpty())
            return ResponseEntity.ok(matchingGames); // Return: OK response with matching games in the body.
        else
            return ResponseEntity.notFound().build(); // Return: Not Found response if no games match the criteria.
    }

    /**
     * Search games by the provided name.
     *
     * @param name The name o the search criteria
     * @return a list of games matching the search criteria
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/searchByName")
    public ResponseEntity<List<ElasticGame>> searchGamesByName(@RequestParam("name") String name) {
        List<ElasticGame> games = elasticGameService.searchGamesByName(name);
        return ResponseEntity.ok(games);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/searchWithFilters")
    public ResponseEntity<List<ElasticGame>> searchWithFilters(@RequestParam(required = false) String genre) {
        List<ElasticGame> games = elasticGameService.searchWithFilters(genre);
        return ResponseEntity.ok(games);
    }

}
