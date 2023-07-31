package co.empathy.academy.gametracker.repositories;

import co.empathy.academy.gametracker.models.GameList;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameListRepository extends MongoRepository<GameList, String> {

    /**
     * Finds a list of game lists by user id on MongoDB.
     * @param userId String
     * @return a list of GameList objects
     */
    List<GameList> findByUserId(String userId);

}
