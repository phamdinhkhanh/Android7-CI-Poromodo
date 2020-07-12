package com.example.laptoptcc.android7_pomodoro.settings;

/**
 * Created by laptopTCC on 1/14/2017.
 */

public class LoginCredentials{
    private String username;
    private String password;
    private String accessToken;

    public LoginCredentials(String username, String password, String accessToken) {
        this.username = username;
        this.password = password;
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }




    public LoginCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}