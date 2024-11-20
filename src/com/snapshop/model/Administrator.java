/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snapshop.model;

/**
 *
 * @author lucas
 */
public class Administrator extends User {

    private int adminId;

    public Administrator(String username, String password, int adminId) {
        super(username, password);
        this.adminId = adminId;
    }

}
