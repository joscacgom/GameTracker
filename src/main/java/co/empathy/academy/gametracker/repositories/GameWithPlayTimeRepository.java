package co.empathy.academy.gametracker.repositories;

import co.empathy.academy.gametracker.models.GameWithPlaytime;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameWithPlayTimeRepository extends MongoRepository<GameWithPlaytime, String> {
    List<GameWithPlaytime> findByUserId(String userId);

}