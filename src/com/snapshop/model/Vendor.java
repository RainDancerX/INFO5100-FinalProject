/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snapshop.model;

/**
 *
 * @author lucas
 */
public class Vendor extends User {

    private int vendorId;

    public Vendor(String username, String password, int vendorId) {
        super(username, password);
        this.vendorId = vendorId;
    }
}
