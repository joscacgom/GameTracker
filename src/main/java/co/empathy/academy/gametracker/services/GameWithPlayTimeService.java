package co.empathy.academy.gametracker.services;

import co.empathy.academy.gametracker.models.GameWithPlayTime;
import co.empathy.academy.gametracker.repositories.GameWithPlayTimeRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class GameWithPlayTimeService {

    private final GameWithPlayTimeRepository gameWithPlayTimeRepository;

    public GameWithPlayTimeService(GameWithPlayTimeRepository gameWithPlayTimeRepository) {
        this.gameWithPlayTimeRepository = gameWithPlayTimeRepository;
    }

    public GameWithPlayTime updatePlaytimeHours(Long id, GameWithPlayTime updatedGameWithPlayTime) {
        // get the game with playtime by id
        Optional<GameWithPlayTime> optionalGameWithPlayTime = gameWithPlayTimeRepository.findById(id);
        // if the game with playtime exists, update the playtime hours and save it
        if (optionalGameWithPlayTime.isPresent()) {
            GameWithPlayTime gameWithPlayTime = optionalGameWithPlayTime.get();
            gameWithPlayTime.setPlaytimeHours(updatedGameWithPlayTime.getPlaytimeHours());
            return gameWithPlayTimeRepository.save(gameWithPlayTime);
        }
        return null;
    }
}
