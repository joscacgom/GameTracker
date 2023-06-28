package co.empathy.academy.gametracker.models;

public class AuthDTO {

    private String id;

    private String username;

    private String JWToken;

    public AuthDTO() {
    }

    public AuthDTO(String username, String JWToken, String id) {
        this.username = username;
        this.JWToken = JWToken;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getJWToken() {
        return JWToken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setJWToken(String JWToken) {
        this.JWToken = JWToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AuthDTO{" +
                "username='" + username + '\'' +
                ", JWToken='" + JWToken + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
    
    
}
