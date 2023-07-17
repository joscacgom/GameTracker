package co.empathy.academy.gametracker.repositories.mongo;

import co.empathy.academy.gametracker.models.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);

    Object findByEmail(String email);
}

