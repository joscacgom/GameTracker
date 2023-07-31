package co.empathy.academy.gametracker.controllers.mongo;

import co.empathy.academy.gametracker.models.mongo.Game;
import co.empathy.academy.gametracker.services.mongo.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("games")
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Obtains a game with its information (details) from Mongo database
     * @param gameId, Long, identifier of the game
     * @return a Game object with the details,
     *  or null, if it is not in the ddbb
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/{gameId}")
    public Game getGameDetails(@PathVariable Long gameId) {
        return gameService.getGame(gameId);
    }

    /**
     * Obtains all games from Mongo database
     * @return all games in database, List<Game>
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/all")
    public List<Game> getGames() {
        return gameService.getGames();
    }

}
