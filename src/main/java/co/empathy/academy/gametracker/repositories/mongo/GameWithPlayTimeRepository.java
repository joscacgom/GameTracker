package co.empathy.academy.gametracker.repositories.mongo;

import co.empathy.academy.gametracker.models.mongo.GameWithPlaytime;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameWithPlayTimeRepository extends MongoRepository<GameWithPlaytime, String> {
    List<GameWithPlaytime> findByUserId(String userId);

}