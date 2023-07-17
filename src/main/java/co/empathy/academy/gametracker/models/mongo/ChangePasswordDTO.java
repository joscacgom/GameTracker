package co.empathy.academy.gametracker.models.mongo;

public class ChangePasswordDTO {

    private String currentUsername;

    private String newPassword;

    public ChangePasswordDTO() {
    }

    public ChangePasswordDTO(String currentUsername, String newPassword) {
        this.currentUsername = currentUsername;
        this.newPassword = newPassword;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
}
