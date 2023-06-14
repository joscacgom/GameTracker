package co.empathy.academy.gametracker.models;
import javax.persistence.*;

@Entity
@Table(name = "user_game_lists")
public class UserGameList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    public UserGameList() {
    }

    public UserGameList(User user, Game game) {
        this.user = user;
        this.game = game;
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

    @Override
    public String toString() {
        return "UserGameList{" +
                "id=" + id +
                ", user=" + user +
                ", game=" + game +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGameList)) return false;

        UserGameList that = (UserGameList) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getUser() != null ? !getUser().equals(that.getUser()) : that.getUser() != null) return false;
        return getGame() != null ? getGame().equals(that.getGame()) : that.getGame() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        return result;

    }
}

