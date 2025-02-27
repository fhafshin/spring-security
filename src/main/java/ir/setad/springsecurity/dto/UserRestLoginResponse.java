package ir.setad.springsecurity.dto;

public class UserRestLoginResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserRestLoginResponse(String token) {
        this.token = token;
    }
}
