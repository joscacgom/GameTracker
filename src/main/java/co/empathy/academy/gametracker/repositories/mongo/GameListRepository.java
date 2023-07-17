package co.empathy.academy.gametracker.repositories.mongo;

import co.empathy.academy.gametracker.models.mongo.GameList;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameListRepository extends MongoRepository<GameList, String> {
    List<GameList> findByUserId(String userId);

}
