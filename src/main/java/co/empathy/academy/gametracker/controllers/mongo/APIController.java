package co.empathy.academy.gametracker.controllers.mongo;

import co.empathy.academy.gametracker.models.mongo.Game;
import co.empathy.academy.gametracker.services.mongo.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    private final APIService apiService;

    public APIController(APIService apiService) {
        this.apiService = apiService;
    }

    /**
     * Obtains a list of games from RAWG API
     * @return a response with the list of games in its body, ResponseEntity<List<Game>>,
     *  or the Http status (error)
     */
    @GetMapping("/listGames")
    public ResponseEntity<List<Game>> getAListOfGames() {
        List<Game> games = apiService.getAListOfGames();
        if (games != null)
            return ResponseEntity.ok(games); // Retorno: respuesta ok con games en el cuerpo.
        else // Retorno: respuesta de error con mensaje.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Obtains a game with more details (information) from RAWG API
     * @return a response with the game in its body, ResponseEntity<Game>,
     *  or the Http status (error)
     */
    @GetMapping("/gameDetails/{game_id}")
    public ResponseEntity<Game> getGameDetails(@PathVariable Long game_id) {
        Game game = apiService.getGameDetails(game_id);
        if (game != null)
            return ResponseEntity.ok(game); // Retorno: respuesta ok con el juego en el cuerpo.
        else // Retorno: respuesta de error con mensaje.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}