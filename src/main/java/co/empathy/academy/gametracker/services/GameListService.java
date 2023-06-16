package co.empathy.academy.gametracker.services;

import co.empathy.academy.gametracker.models.GameList;
import co.empathy.academy.gametracker.repositories.GameListRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class GameListService {

    private final GameListRepository gameListRepository;

    public GameListService(GameListRepository gameListRepository) {
        this.gameListRepository = gameListRepository;
    }

    public GameList createGameList(GameList gameList) {
        return gameListRepository.save(gameList);
    }
    // add new games to the list
    public GameList updateGameList(String listId, GameList gameList) {
        GameList existingGameList = getGameList(listId);
        existingGameList.getGames().addAll(gameList.getGames());
        existingGameList.setTotalPlaytime(gameList.getTotalPlaytime());
        return gameListRepository.save(existingGameList);
    }

    public GameList getGameList(String listId) {
        Optional<GameList> optionalGameList = gameListRepository.findById(Long.parseLong(listId));
        return optionalGameList.orElse(null);
    }
    

    public void deleteGameList(String listId) {
        gameListRepository.deleteById(Long.parseLong(listId));
    }
}
