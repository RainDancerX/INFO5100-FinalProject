/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Customer;
import model.User;
import model.User.UserType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucas
 */
public class PlatformController {
    // List to store members
    private List<User> userList = new ArrayList<>();
    private User currentUser;

    public PlatformController() {

    }

    public void registerMember(String username, String password, long phone, UserType userType, int age) {
        User newMember = new Customer(username, password, phone, userType, age);
        userList.add(newMember);
    }

    // Validate login credentials
    public boolean validateLogin(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public User getCurrentMember() {
        return currentUser;
    }

    public List<User> getAllMembers() {
        return userList;
    }

}
