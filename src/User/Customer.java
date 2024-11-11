/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;

/**
 *
 * @author lucas
 */
public class Customer extends User {

    private static int CustomerID = 0;
    private int age;

    public Customer(String username, String password, long contactInformation, UserType userType, int age) {
        super(username, password, contactInformation, userType);
        this.CustomerID = ++CustomerID;
        this.age = age;
        System.out.println(this.CustomerID + " " + this.age);
    }
}
