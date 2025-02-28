package ir.setad.springsecurity.dto;

public class UserRestLoginRequest {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRestLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
