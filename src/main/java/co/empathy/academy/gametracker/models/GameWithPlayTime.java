package co.empathy.academy.gametracker.models;

import javax.persistence.*;

@Entity
@Table(name = "game_list_games")
public class GameWithPlayTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_list_id")
    private GameList gameList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(nullable = true)
    private Integer playtimeHours;

    public GameWithPlayTime() {
    }

    public GameWithPlayTime(GameList gameList, Game game, Integer playtimeHours) {
        this.gameList = gameList;
        this.game = game;
        this.playtimeHours = playtimeHours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameList getGameList() {
        return gameList;
    }

    public void setGameList(GameList gameList) {
        this.gameList = gameList;
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

}
