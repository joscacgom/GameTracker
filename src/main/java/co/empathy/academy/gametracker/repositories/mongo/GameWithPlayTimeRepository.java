package co.empathy.academy.gametracker.repositories.mongo;

import co.empathy.academy.gametracker.models.mongo.GameWithPlaytime;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameWithPlayTimeRepository extends MongoRepository<GameWithPlaytime, String> {

    /**
     * Finds a list of games with playtime by its user id on MongoDB.
     *
     * @param userId String
     * @return a list of GameWithPlaytime objects
     */
    List<GameWithPlaytime> findByUserId(String userId);

}