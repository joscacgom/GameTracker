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

    @Embedded
    private List<Developer> developers; // platform names

    @Embedded
    private List<Publisher> publishers; // genre names

    private Integer metacritic;

    private Boolean tba;

    private Integer rating;

    private EsrbRating esrb_rating;

    public Game() {
    }

    public Game(Long id, String name, String description, LocalDate released, String background_image, int playtime, List<Platform> platforms, List<Genre> genres, List<Developer> developers, List<Publisher> publishers, Integer metacritic, Boolean tba, Integer rating, EsrbRating esrb_rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.released = released;
        this.background_image = background_image;
        this.playtime = playtime;
        this.platforms = platforms;
        this.genres = genres;
        this.developers = developers;
        this.publishers = publishers;
        this.metacritic = metacritic;
        this.tba = tba;
        this.rating = rating;
        this.esrb_rating = esrb_rating;
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

    public EsrbRating getEsrb_rating() {
        return esrb_rating;
    }

    public void setEsrb_rating(EsrbRating esrb_rating) {
        this.esrb_rating = esrb_rating;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) { 
        this.developers = developers; 
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<Publisher> publishers) { 
        this.publishers = publishers; 
    }

    public Integer getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(Integer metacritic) {
        this.metacritic = metacritic;
    }

    public Boolean getTba() {
        return tba;
    }

    public void setTba(Boolean tba) {
        this.tba = tba;
    }

    public Integer getRating() { 
        return rating; 
    }

    public void setRating(Integer rating) { 
        this.rating = rating; 
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
                ", developers=" + developers +
                ", publishers=" + publishers +
                ", metacritic=" + metacritic +
                ", tba=" + tba +
                ", rating=" + rating +
                ", esrb_rating=" + esrb_rating +
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
