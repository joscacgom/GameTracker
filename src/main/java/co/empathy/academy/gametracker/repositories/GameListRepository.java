package co.empathy.academy.gametracker.repositories;

import co.empathy.academy.gametracker.models.GameList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameListRepository extends JpaRepository<GameList, Long> {
}
