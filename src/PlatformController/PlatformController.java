/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlatformController;

import Product.Product;
import User.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lucas
 */
public class PlatformController {
    
//    private Map<Member, List<Book>> borrowedBooks = new HashMap<>();
    private List<Customer> members = new ArrayList<>(); // List to store members
    private List<Product> books = new ArrayList<>(); // List to store books
    private Customer currentMember;
    
    public PlatformController(){
        
    }

       public void registerMember(String username, String password, long phone) {
        Customer newMember = new Customer(username, password, phone);
        members.add(newMember);
    }

    // Validate login credentials
    public boolean validateLogin(String username, String password) {
        for (Customer member : members) {
            if (member.getUsername().equals(username) && member.getPassword().equals(password)) {
                currentMember = member;
                return true;
            }
        }
        return false;
    }

    public Customer getCurrentMember() {
        return currentMember;
    }

    public List<Customer> getAllMembers() {
        return members;
    }

    public List<Product> getAllBooks() {
        return books;
    }
  
}
