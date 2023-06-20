package co.empathy.academy.gametracker.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import co.empathy.academy.gametracker.models.Game;

import java.util.List;

public interface GameRepository extends MongoRepository<Game, String> {

    // Definition of any custom method to manipulate or retrieve data from the games collection

    List<Game> findAll();

}
