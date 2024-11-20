/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snapshop.model;

/**
 *
 * @author lucas
 */
public abstract class User {

    private String username;
    private String password;
//    private UserType usertype;
//    public enum UserType {
//        Customer, Admin, Associate;
//    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;

//    this.usertype = userType;
//    System.out.println("username: " + this.username + "\npassword:" + this.password);
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
