package co.empathy.academy.gametracker.repositories;

import co.empathy.academy.gametracker.models.APIGame;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameRepository extends MongoRepository<APIGame, String> {

    // Definition of any custom method to manipulate or retrieve data from the games collection

    List<APIGame> findAll();

}
