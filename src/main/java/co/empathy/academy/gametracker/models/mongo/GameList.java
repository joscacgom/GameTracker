package co.empathy.academy.gametracker.models.mongo;

import javax.persistence.*;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = "gameLists") // Persistent in MongoDB
public class GameList {

    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private List<GameWithPlaytime> games;

    private String status; // e.g., "pending," "playing," "completed"

    private Integer totalPlaytime;

    public GameList() {
    }

    public GameList(String id, User user, List<GameWithPlaytime> games, String status, Integer totalPlaytime) {
        this.id = id;
        this.user = user;
        this.games = games;
        this.status = status;
        this.totalPlaytime = totalPlaytime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<GameWithPlaytime> getGames() {
        return games;
    }

    public void setGames(List<GameWithPlaytime> games) {
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
