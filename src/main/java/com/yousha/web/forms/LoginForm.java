package com.yousha.web.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginForm {

    @NotBlank
    @Size(min = 5,  max = 20)
    private String username, password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // without setter thyme leaf can't set model values
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
