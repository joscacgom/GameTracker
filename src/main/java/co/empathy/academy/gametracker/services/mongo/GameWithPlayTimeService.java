package co.empathy.academy.gametracker.services.mongo;

import co.empathy.academy.gametracker.models.mongo.GameWithPlaytime;
import co.empathy.academy.gametracker.repositories.mongo.GameWithPlayTimeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class GameWithPlayTimeService {

    private final GameWithPlayTimeRepository gameWithPlayTimeRepository;
    private final GameListService gameListService;

    public GameWithPlayTimeService(GameWithPlayTimeRepository gameWithPlayTimeRepository, GameListService gameListService) {
        this.gameWithPlayTimeRepository = gameWithPlayTimeRepository;
        this.gameListService = gameListService;
    }

    /**
     * Create a new GameWithPlaytime on Mongo ddbb
     *
     * @param gameToAdd the game to be added on ddbb
     * @return the new game added
     */
    public GameWithPlaytime createGame(GameWithPlaytime gameToAdd) {
        return gameWithPlayTimeRepository.save(gameToAdd);
    }

    /**
     * Update a game with playtime, given an Id.
     *
     * @param id The ID of the game with playtime.
     * @param updatedGameWithPlaytime The updated game with playtime.
     * @return The game with playtime.
     */
    public GameWithPlaytime updatePlaytimeHours(String id, GameWithPlaytime updatedGameWithPlaytime) {
        // get the game with playtime by id
        Optional<GameWithPlaytime> optionalGameWithPlayTime = gameWithPlayTimeRepository.findById(id);
        // if the game with playtime exists, update the playtime hours and save it
        if (optionalGameWithPlayTime.isPresent()) {
            GameWithPlaytime gameWithPlayTime = optionalGameWithPlayTime.get();
            gameWithPlayTime.setPlaytimeHours(updatedGameWithPlaytime.getPlaytimeHours());
            // remove the gamewithplaytime from the list and add the updated one
            gameListService.updateGameStatusFromGameList(gameWithPlayTime.getGameList().getId(), id, updatedGameWithPlaytime.getGameList().getId() );

            gameWithPlayTime.setGameList(updatedGameWithPlaytime.getGameList());
            return gameWithPlayTimeRepository.save(gameWithPlayTime);
        }
        return null;
    }

    /**
     * Delete a game with playtime, given an Id.
     * 
     * @param id The ID of the game with playtime.
     * @return The game with playtime.
     * 
     * @throws Exception If the game with playtime does not exist.
     * 
     */
    public GameWithPlaytime deleteGameById(String id) {
        Optional<GameWithPlaytime> optionalGameWithPlayTime = gameWithPlayTimeRepository.findById(id);
        if (optionalGameWithPlayTime.isPresent()) {
            GameWithPlaytime gameWithPlayTime = optionalGameWithPlayTime.get();
            gameWithPlayTimeRepository.delete(gameWithPlayTime);
            return gameWithPlayTime;
        }
        return null;
    }

     /**
     * Get the games with playtime hours for a specific user.
     *
     * @param userId The ID of the user.
     * @return The list of games with playtime hours for the user.
     */
    public List<GameWithPlaytime> getGamesByUserId(String userId) {
        List<GameWithPlaytime> userGames = gameWithPlayTimeRepository.findByUserId(userId);
        List<GameWithPlaytime> games = new ArrayList<>();
        
        for (GameWithPlaytime game : userGames) {
            if (game.getUser().getId().equals(userId)) {
                games.add(game);
            }
        }
        
        return games;
    }

    /**
     * Get the game with playtime hours by its id.
     *
     * @param id The ID of the game.
     * @return The game with playtime hours or null.
     */
    public GameWithPlaytime getGameById(String id) {
        Optional<GameWithPlaytime> optionalGameWithPlayTime = gameWithPlayTimeRepository.findById(id);
        if (optionalGameWithPlayTime.isPresent()) {
            return optionalGameWithPlayTime.get();
        }
        return null;
    }

}
