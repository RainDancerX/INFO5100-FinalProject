/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;

/**
 *
 * @author lucas
 */
public class Associate extends User {

    private static int AssociateID = 0;

    public Associate(String username, String password, long contactInformation, UserType userType) {
        super(username, password, contactInformation, userType);
        this.AssociateID = ++AssociateID;
    }
}
