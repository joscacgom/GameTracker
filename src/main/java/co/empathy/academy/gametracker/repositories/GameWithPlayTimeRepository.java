package co.empathy.academy.gametracker.repositories;

import co.empathy.academy.gametracker.models.GameWithPlayTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameWithPlayTimeRepository extends JpaRepository<GameWithPlayTime, Long> {
}