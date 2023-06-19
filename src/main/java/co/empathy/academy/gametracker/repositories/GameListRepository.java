package co.empathy.academy.gametracker.repositories;

import co.empathy.academy.gametracker.models.GameList;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameListRepository extends JpaRepository<GameList, Long> {
    List<GameList> findByUserId(Long userId);

}
