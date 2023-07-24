package co.empathy.academy.gametracker.models.mongo;

public class AuthDTO {

    private String id;

    private String username;

    private String email;

    private String JWToken;

    public AuthDTO() {
    }

    public AuthDTO(String username, String email,String JWToken, String id) {
        this.username = username;
        this.email = email;
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

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public AuthDTO(String username, String JWToken) {
        this.username = username;
        this.JWToken = JWToken;
    }



    @Override
    public String toString() {
        return "AuthDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", JWToken='" + JWToken + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
    
    
}
