/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucas
 */
public class Customer {
    private String username;
    private String password;
    private static int membershipID = 0;
    private long contactInformation;
    
    public Customer(String username, String password, long contactInformation){
    this.username = username;
    this.password = password;
    this.membershipID = ++membershipID;
    this.contactInformation = contactInformation;
    System.out.println("username: " + this.username + "\npassword:" + this.password + "\nMemId: " + this.membershipID);
}
    
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
    
}
