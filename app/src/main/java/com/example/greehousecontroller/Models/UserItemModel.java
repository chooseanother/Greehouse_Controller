package com.example.greehousecontroller.Models;


import java.util.ArrayList;
import java.util.List;

public class UserItemModel {
    private String username;
    private String email;
    private String image;


    public UserItemModel(String username, String email,String image) {
        this.username = username;
        this.email = email;
        this.image = image;
    }

    public UserItemModel() {
    }

    public String getUsername() {
        return username;
    }


    public String getEmail() {
        return email;
    }



    public String getImage() {
        return image;
    }

}
