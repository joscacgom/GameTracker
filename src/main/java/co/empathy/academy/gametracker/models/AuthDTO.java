package co.empathy.academy.gametracker.models;

public class AuthDTO {

    private String username;

    private String JWToken;

    public AuthDTO() {
    }

    public AuthDTO(String username, String JWToken) {
        this.username = username;
        this.JWToken = JWToken;
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

    @Override
    public String toString() {
        return "AuthDTO{" +
                "username='" + username + '\'' +
                ", JWToken='" + JWToken + '\'' +
                '}';
    }
    
    
}
