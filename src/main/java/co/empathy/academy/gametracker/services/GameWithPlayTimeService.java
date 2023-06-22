package co.empathy.academy.gametracker.services;

import co.empathy.academy.gametracker.models.GameWithPlaytime;
import co.empathy.academy.gametracker.repositories.GameWithPlayTimeRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class GameWithPlayTimeService {

    private final GameWithPlayTimeRepository gameWithPlayTimeRepository;

    public GameWithPlayTimeService(GameWithPlayTimeRepository gameWithPlayTimeRepository) {
        this.gameWithPlayTimeRepository = gameWithPlayTimeRepository;
    }

    public GameWithPlaytime updatePlaytimeHours(Long id, GameWithPlaytime updatedGameWithPlaytime) {
        // get the game with playtime by id
        Optional<GameWithPlaytime> optionalGameWithPlayTime = gameWithPlayTimeRepository.findById(id);
        // if the game with playtime exists, update the playtime hours and save it
        if (optionalGameWithPlayTime.isPresent()) {
            GameWithPlaytime gameWithPlayTime = optionalGameWithPlayTime.get();
            gameWithPlayTime.setPlaytimeHours(updatedGameWithPlaytime.getPlaytimeHours());
            return gameWithPlayTimeRepository.save(gameWithPlayTime);
        }
        return null;
    }
}
