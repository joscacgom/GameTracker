package co.empathy.academy.gametracker.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Document(collection = "games") // Persistent in MongoDB
public class Game {

    @Id
    private long id;

    private String name;

    private LocalDate released;

    private String background_image; // uri of the image

    private int playtime;

    private List<String> platforms; // platform names

    private List<String> genres; // genre names

    //@ManyToMany(mappedBy = "games")
    private List<GameList> gameLists;

    // add more parameters as needed

    public Game() {
    }

    public Game(long id, String name, LocalDate released, String background_image, int playtime, List<String> platforms, List<String> genres) {
        this.id = id;
        this.name = name;
        this.released = released;
        this.background_image = background_image;
        this.playtime = playtime;
        this.platforms = platforms;
        this.genres = genres;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public String getBackground_image() {
        return background_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    public int getPlaytime() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", released=" + released +
                ", background_image='" + background_image + '\'' +
                ", playtime=" + playtime +
                ", platforms=" + platforms +
                ", genres=" + genres +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && Objects.equals(name, game.name) && Objects.equals(released, game.released);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, released);
    }

}
