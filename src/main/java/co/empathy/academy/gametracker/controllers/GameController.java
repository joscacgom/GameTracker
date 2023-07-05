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

    @CrossOrigin(origins = "*")
    @GetMapping("/{gameId}")
    public Game getGameDetails(@PathVariable Long gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        return game != null ? game.get() : null;
    }

}
