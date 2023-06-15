package co.empathy.academy.gametracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.empathy.academy.gametracker.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Object findByEmail(String email);
}

