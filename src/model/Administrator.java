/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lucas
 */
public class Administrator extends User{
    private static int adminID = 0;
    public Administrator(String username, String password, long contactInformation, UserType userType){
        super(username, password, contactInformation, userType);
        this.adminID = ++adminID;
    }
    
}
