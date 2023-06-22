package co.empathy.academy.gametracker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Embedded;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Document(collection = "games") // Persistent in MongoDB
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {

    @Id
    private Long id;

    private String name;

    private String description;

    private LocalDate released;

    private String background_image; // uri of the image

    private int playtime;

    @Embedded
    private List<Platform> platforms; // platform names

    @Embedded
    private List<Genre> genres; // genre names

    //@DBRef
    //private List<GameList> gameLists;

    public Game() {
    }

    public Game(long id, String name, LocalDate released, String background_image, int playtime, List<Platform> platforms, List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.released = released;
        this.background_image = background_image;
        this.playtime = playtime;
        this.platforms = platforms;
        this.genres = genres;
    }

    public Game(long id, String name, String description, LocalDate released, String background_image, int playtime, List<Platform> platforms, List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
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
