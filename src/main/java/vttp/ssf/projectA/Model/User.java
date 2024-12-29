package vttp.ssf.projectA.Model;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class User implements Serializable{

    // @NotNull
    // private String id;

    @NotNull
    @Email
    private String username;

    // @Email
    // private String email;

    @NotNull
    private String password;

    public User() {}

    public User(String username, String password) {
        // this.id = id;
        this.username = username;
        //this.email = email;
        this.password = password;
    }

    // public String getId() {
    //     return id;
    // }

    // public void setId(String id) {
    //     this.id = id;
    // }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // public String getEmail() {
    //     return email;
    // }

    // public void setEmail(String email) {
    //     this.email = email;
    // }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
