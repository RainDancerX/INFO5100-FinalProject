/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snapshop.model;

/**
 *
 * @author lucas
 */
public class Associate extends User {

    private int associateId;

    public Associate(String username, String password, int associateId ) {
        super(username, password);
        this.associateId = associateId;
    }
}
