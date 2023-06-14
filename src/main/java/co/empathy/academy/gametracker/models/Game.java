package co.empathy.academy.gametracker.models;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private LocalDate releaseDate;

    // add more parameters as needed

    @Column(nullable = false)
    private Integer averagePlaytime;

    public Game() {
    }

    public Game(String title, String genre, String platform, LocalDate releaseDate, Integer averagePlaytime) {
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.releaseDate = releaseDate;
        this.averagePlaytime = averagePlaytime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", platform='" + platform + '\'' +
                ", releaseDate=" + releaseDate +
                ", averagePlaytime=" + averagePlaytime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game game)) return false;
        return id.equals(game.id) && title.equals(game.title) && genre.equals(game.genre) && platform.equals(game.platform) && releaseDate.equals(game.releaseDate);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

