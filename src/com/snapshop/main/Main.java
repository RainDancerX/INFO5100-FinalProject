/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.snapshop.main;

import com.snapshop.controller.PlatformController;
import com.snapshop.form.Login;

/**
 *
 * @author lucas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        
        
        PlatformController controller = new PlatformController();
        
        // Display login interface
//        Login loginFrame = new Login(controller);
//        loginFrame.setVisible(true);
//        loginFrame.pack();
//        loginFrame.setLocationRelativeTo(null);



        // For Developing Only
        Dashboard db = new Dashboard(controller);
        db.setVisible(true);
        db.pack();
        db.setLocationRelativeTo(null);
    }   
    
}
    
