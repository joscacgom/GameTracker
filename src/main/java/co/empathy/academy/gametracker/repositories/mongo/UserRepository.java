package co.empathy.academy.gametracker.repositories.mongo;

import co.empathy.academy.gametracker.models.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Finds a user by its user name on MongoDB.
     *
     * @param username String
     * @return a User
     */
    User findByUsername(String username);

    /**
     * Finds a user by its email on MongoDB.
     *
     * @param email String
     * @return a User
     */
    User findByEmail(String email);

}
