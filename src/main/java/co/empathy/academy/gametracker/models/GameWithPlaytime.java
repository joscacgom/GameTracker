package co.empathy.academy.gametracker.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Objects;

@Document(collection = "gamesWithPlaytime") // Persistent in MongoDB
public class GameWithPlaytime {

    @Id
    private String id;

    @DBRef
    private Game game;

    private Integer playtimeHours; // user time spend playing on game

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
