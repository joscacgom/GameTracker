package co.empathy.academy.gametracker.services;

import co.empathy.academy.gametracker.models.Game;
import co.empathy.academy.gametracker.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Retrieves a game by its ID.
     *
     * @param gameId, The ID of the game.
     * @return the game with the specified ID, or null if not found.
     */
    public Game getGame(Long gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        return optionalGame.orElse(null);
    }

}
