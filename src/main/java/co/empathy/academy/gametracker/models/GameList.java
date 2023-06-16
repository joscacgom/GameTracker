package co.empathy.academy.gametracker.models;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "game_lists")
public class GameList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "game_list_games",
            joinColumns = @JoinColumn(name = "game_list_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<GameWithPlayTime> games;

    @Column(nullable = false)
    private String status; // e.g., "pending," "playing," "completed"

    @Column(nullable = false)
    private Integer totalPlaytime;

    public GameList() {
    }

    public GameList(Long id, User user, List<GameWithPlayTime> games, String status, Integer totalPlaytime) {
        this.id = id;
        this.user = user;
        this.games = games;
        this.status = status;
        this.totalPlaytime = totalPlaytime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<GameWithPlayTime> getGames() {
        return games;
    }

    public void setGames(List<GameWithPlayTime> games) {
        this.games = games;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalPlaytime() {
        return totalPlaytime;
    }

    public void setTotalPlaytime(Integer totalPlaytime) {
        this.totalPlaytime = totalPlaytime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameList gameList = (GameList) o;
        return Objects.equals(id, gameList.id) && Objects.equals(user, gameList.user) && Objects.equals(games, gameList.games) && Objects.equals(status, gameList.status) && Objects.equals(totalPlaytime, gameList.totalPlaytime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, games, status, totalPlaytime);
    }

}
