package co.empathy.academy.gametracker.repositories;

import co.empathy.academy.gametracker.models.GameList;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameListRepository extends MongoRepository<GameList, Long> {
    List<GameList> findByUserId(Long userId);

}
