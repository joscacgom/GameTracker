package co.empathy.academy.gametracker.controllers;

import co.empathy.academy.gametracker.models.Game;
import co.empathy.academy.gametracker.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Obtains a game with its information (details) from Mongo database
     * @param gameId, Long, identifier of the game
     * @return game, Game object with the details,
     *  or null, if it is not in the ddbb
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/{gameId}")
    public Game getGameDetails(@PathVariable Long gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        return game != null ? game.get() : null;
    }

}
