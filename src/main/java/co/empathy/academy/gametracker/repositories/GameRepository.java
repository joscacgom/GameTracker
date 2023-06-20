package co.empathy.academy.gametracker.repositories;

import co.empathy.academy.gametracker.models.APIGames;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameRepository extends MongoRepository<APIGames, String> {

    // Definition of any custom method to manipulate or retrieve data from the games collection

    List<APIGames> findAll();

}
