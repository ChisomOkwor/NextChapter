package com.example.searchbygenre;

import java.io.Serializable;

public class User implements Serializable {
    public String name;
    public String email;
    public String password;

    public User(){

    }

    public User(String name, String email){
        this.name = name;
        this.email = email;

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword(String password) {
        return password;
    }

}
