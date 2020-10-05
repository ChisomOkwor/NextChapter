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
}
