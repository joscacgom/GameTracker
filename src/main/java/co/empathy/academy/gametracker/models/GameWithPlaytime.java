package co.empathy.academy.gametracker.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Document(collection = "gamesWithPlaytime") // Persistent in MongoDB
public class GameWithPlaytime {

    @Id
    private String id;

    @DBRef
    private Game game;

    @DBRef
    private User user;

    @DBRef
    @JsonIgnoreProperties("gamesWithPlaytime")
    private GameList gameList;

    private Integer playtimeHours;

    public GameWithPlaytime() {
    }

    public GameWithPlaytime(String id, Game game, Integer playtimeHours) {
        this.id = id;
        this.game = game;
        this.playtimeHours = playtimeHours;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getPlaytimeHours() {
        return playtimeHours;
    }

    public void setPlaytimeHours(Integer playtimeHours) {
        this.playtimeHours = playtimeHours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GameList getGameList() {
        return gameList;
    }

    public void setGameList(GameList gameList) {
        this.gameList = gameList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameWithPlaytime that = (GameWithPlaytime) o;
        return Objects.equals(id, that.id) && Objects.equals(game, that.game) && Objects.equals(playtimeHours, that.playtimeHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, game, playtimeHours);
    }

    @Override
    public String toString() {
        return "GameWithPlaytime{" +
                "id=" + id +
                ", game=" + game +
                ", playtimeHours=" + playtimeHours +
                '}';
    }

}
