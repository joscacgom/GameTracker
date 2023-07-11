package co.empathy.academy.gametracker.models;

public class UserUpdateDTO {

    private String username;

    private String email;

    private String currentUsername;


    public UserUpdateDTO() {
    }

    public UserUpdateDTO(String username, String email, String currentUsername) {
        this.username = username;
        this.email = email;
        this.currentUsername = currentUsername;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
