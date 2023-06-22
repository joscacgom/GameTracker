package co.empathy.academy.gametracker.repositories;

import co.empathy.academy.gametracker.models.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameRepository extends MongoRepository<Game, Long> { // Long -> @Id

    // Definition of any custom method to manipulate or retrieve data from the games collection

    List<Game> findAll();

}
