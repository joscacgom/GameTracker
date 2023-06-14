package co.empathy.academy.gametracker.models;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "game_lists")
public class GameList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false)
    private String status; // e.g., "pending," "playing," "completed"

    @Column(nullable = false)
    private Integer playtimeHours;

    public GameList() {
    }

    public GameList(User user, Game game, String status, Integer playtimeHours) {
        this.user = user;
        this.game = game;
        this.status = status;
        this.playtimeHours = playtimeHours;
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

    public Game getGame() {
        return game;
    }
    
    public void setGame(Game game) {
        this.game = game;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getPlaytimeHours() {
        return playtimeHours;
    }
    
    public void setPlaytimeHours(Integer playtimeHours) {
        this.playtimeHours = playtimeHours;
    }
    


    @Override
    public String toString() {
        return "GameList{" +
                "id=" + id +
                ", user=" + user +
                ", game=" + game +
                ", status='" + status + '\'' +
                ", playtimeHours=" + playtimeHours +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameList)) return false;
        GameList gameList = (GameList) o;
        return id.equals(gameList.id) &&
                user.equals(gameList.user) &&
                game.equals(gameList.game) &&
                status.equals(gameList.status) &&
                playtimeHours.equals(gameList.playtimeHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, game, status, playtimeHours);
    }

}
