package co.empathy.academy.gametracker.repositories;

import co.empathy.academy.gametracker.models.GameWithPlaytime;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameWithPlayTimeRepository extends MongoRepository<GameWithPlaytime, Long> {
}