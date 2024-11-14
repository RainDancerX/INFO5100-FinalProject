/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Dashboard;

import PlatformController.PlatformController;

/**
 *
 * @author lucas
 */
public class MainDashboard {

    /**
     * @param args the command line arguments
     * some changes
     * 
     */
    public static void main(String args[]) {
        
        PlatformController controller = new PlatformController();
        
        // Display login interface
        Login loginFrame = new Login(controller);
        loginFrame.setVisible(true);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
    }   
    
}
    
