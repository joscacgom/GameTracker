package co.empathy.academy.gametracker.models;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String genre;

    @Column(nullable = true)
    private String platform;

    @Column(nullable = true)
    private LocalDate releaseDate;

    @Column(nullable = true)
    private Integer averagePlaytime;

    @Column(nullable = true)
    private String image; // uri of the image

    @ManyToMany(mappedBy = "games")
    private List<GameList> gameLists;

    // add more parameters as needed
    // maybe rating or isReleased...

    public Game() {
    }

    public Game(Long id, String name, String description, String genre, String platform, LocalDate releaseDate, Integer averagePlaytime, String image, List<GameList> gameLists) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.platform = platform;
        this.releaseDate = releaseDate;
        this.averagePlaytime = averagePlaytime;
        this.image = image;
        this.gameLists = gameLists;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getAveragePlaytime() {
        return averagePlaytime;
    }

    public void setAveragePlaytime(Integer averagePlaytime) {
        this.averagePlaytime = averagePlaytime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<GameList> getGameLists() {
        return gameLists;
    }

    public void setGameLists(List<GameList> gameLists) {
        this.gameLists = gameLists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) && Objects.equals(name, game.name) && Objects.equals(description, game.description) && Objects.equals(genre, game.genre) && Objects.equals(platform, game.platform) && Objects.equals(releaseDate, game.releaseDate) && Objects.equals(averagePlaytime, game.averagePlaytime) && Objects.equals(image, game.image) && Objects.equals(gameLists, game.gameLists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, genre, platform, releaseDate, averagePlaytime, image, gameLists);
    }

}
