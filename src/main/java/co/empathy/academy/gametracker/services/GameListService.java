package co.empathy.academy.gametracker.services;

import co.empathy.academy.gametracker.models.GameList;
import co.empathy.academy.gametracker.models.GameWithPlaytime;
import co.empathy.academy.gametracker.repositories.GameListRepository;

import java.util.ArrayList;
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
     * @param listId   The ID of the game list to update.
     * @param gameList The updated game list.
     * @return The updated game list.
     */
    public GameList updateGameList(String listId, GameList gameList) {
        GameList existingGameList = getGameList(listId);
        if (existingGameList == null) {
            return null;
        }
        if (gameList.getStatus() != null) {
            existingGameList.setStatus(gameList.getStatus());
        }
        existingGameList.setTotalPlaytime(gameList.getTotalPlaytime());
        return gameListRepository.save(existingGameList);
    }
    //  A method to given a gameList and a gameWithPlaytime, add the gameWithPlaytime to the gameList and   
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
     * Updates a game from a game list to another list.
     * @param listId The ID of the game list to delete from.
     * @param gameId The ID of the game to delete.
     * @param newListId The ID of the game list to add the game to.
     * @return The updated game list or null.
     */
    public void updateGameStatusFromGameList(String listId, String gameId, String newListId) {
        GameList existingGameList = getGameList(listId);
        GameList newGameList = getGameList(newListId);

        if (existingGameList != null) {
            List<GameWithPlaytime> updateGameList = existingGameList.getGames();
            if (updateGameList != null) {
                for (GameWithPlaytime game: updateGameList) {
                    if (game.getId().equals(gameId)) {
                        updateGameList.remove(game);
                        if(newGameList.getGames()==null)
                            newGameList.setGames(new ArrayList<>());
                        List<GameWithPlaytime> updatedGameList=newGameList.getGames();
                        updatedGameList.add(game);
                        newGameList.setGames(updatedGameList);
                        newGameList.setTotalPlaytime(calculateTotalPlaytime(updatedGameList));
                        break;
                    }
                }
                existingGameList.setGames(updateGameList);
                existingGameList.setTotalPlaytime(calculateTotalPlaytime(updateGameList));
                gameListRepository.save(existingGameList);

                
                gameListRepository.save(newGameList);
            }
        }
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

