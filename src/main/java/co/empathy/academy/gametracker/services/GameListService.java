package co.empathy.academy.gametracker.services;

import co.empathy.academy.gametracker.models.GameList;
import co.empathy.academy.gametracker.repositories.GameListRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class GameListService {

    private final GameListRepository gameListRepository;

    public GameListService(GameListRepository gameListRepository) {
        this.gameListRepository = gameListRepository;
    }

    /**
     * Creates a new game list.
     *
     * @param gameList The game list to create.
     * @return The created game list.
     */
    public GameList createGameList(GameList gameList) {
        return gameListRepository.save(gameList);
    }

    /**
     * Updates an existing game list.
     *
     * @param listId    The ID of the game list to update.
     * @param gameList  The updated game list.
     * @return The updated game list.
     */
    public GameList updateGameList(String listId, GameList gameList) {
        GameList existingGameList = getGameList(listId);
        existingGameList.getGames().addAll(gameList.getGames());
        existingGameList.setTotalPlaytime(gameList.getTotalPlaytime());
        return gameListRepository.save(existingGameList);
    }

    /**
     * Retrieves a game list by its ID.
     *
     * @param listId The ID of the game list.
     * @return The game list with the specified ID, or null if not found.
     */
    public GameList getGameList(String listId) {
        Optional<GameList> optionalGameList = gameListRepository.findById(listId);
        return optionalGameList.orElse(null);
    }

    /**
     * Retrieves all game lists associated with a user ID.
     *
     * @param userId The ID of the user.
     * @return The list of game lists associated with the user.
     */
    public List<GameList> getGameListsByUserId(String userId) {
        return gameListRepository.findByUserId(userId);
    }

    /**
     * Deletes a game list by its ID.
     *
     * @param listId The ID of the game list to delete.
     */
    public void deleteGameList(String listId) {
        gameListRepository.deleteById(listId);
    }
}

