/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lucas
 */
public abstract class User {
    private String username;
    private String password;
    private long contactInformation;
    private UserType usertype;
    
    public enum UserType{
        Customer, Admin, Associate;
    }
    
    public User(String username, String password, long contactInformatioc, UserType userType ){
    this.username = username;
    this.password = password;
    this.contactInformation = contactInformation;
    this.usertype = userType;
//    System.out.println("username: " + this.username + "\npassword:" + this.password);
}
    
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
    
}
