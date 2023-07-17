package co.empathy.academy.gametracker.services.mongo;

import co.empathy.academy.gametracker.models.mongo.GameList;
import co.empathy.academy.gametracker.models.mongo.GameWithPlaytime;
import co.empathy.academy.gametracker.repositories.mongo.GameListRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.empathy.academy.gametracker.repositories.mongo.GameWithPlayTimeRepository;
import org.springframework.stereotype.Service;

@Service
public class GameListService {

    private final GameListRepository gameListRepository;
    private final GameWithPlayTimeRepository gameWithPlayTimeRepository;

    public GameListService(GameListRepository gameListRepository, GameWithPlayTimeRepository gameWithPlayTimeRepository) {
        this.gameListRepository = gameListRepository;
        this.gameWithPlayTimeRepository = gameWithPlayTimeRepository;
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
     * @param listId   The ID of the game list to update.
     * @param gameList The updated game list.
     * @return The updated game list.
     */
    public GameList updateGameList(String listId, GameList gameList) {
        GameList existingGameList = getGameList(listId);
        if (existingGameList == null) {
            return null;
        }
        if (gameList.getGames() != null) {
            existingGameList.getGames().addAll(gameList.getGames());
        }
        if (gameList.getStatus() != null) {
            existingGameList.setStatus(gameList.getStatus());
        }
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

    /**
     * Updates an existing game list by adding a game to it.
     *
     * @param listId   The ID of the game list to update.
     * @param game The game to be added.
     * @return The updated game list or null.
     */
    public GameList addGameToGameList(String listId, GameWithPlaytime game) {
        GameList existingGameList = getGameList(listId);
        if (existingGameList != null) {
            List<GameWithPlaytime> updateGameList = existingGameList.getGames();
            if (updateGameList == null)
                updateGameList = new ArrayList<>();
            updateGameList.add(game);
            existingGameList.setGames(updateGameList);
            existingGameList.setTotalPlaytime(calculateTotalPlaytime(updateGameList));
            return gameListRepository.save(existingGameList);
        }
        return null;
    }

    /**
     * Calculates the total playtime for the updated game list.
     *
     * @param games  the list of games updated.
     * @return total  an integer with the calculated total playtime hours.
     */
    private int calculateTotalPlaytime(List<GameWithPlaytime> games) {
        int total = 0;
        for (GameWithPlaytime game: games) {
            if (game.getPlaytimeHours() != null) // user may not added playtime hours yet!
                total += game.getPlaytimeHours();
        }
        return total;
    }

}
