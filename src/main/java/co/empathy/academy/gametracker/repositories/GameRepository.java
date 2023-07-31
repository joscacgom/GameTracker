package co.empathy.academy.gametracker.repositories;

import co.empathy.academy.gametracker.models.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game, Long> { // Long -> @Id

    // Definition of any custom method to manipulate or retrieve data from the games collection

}
