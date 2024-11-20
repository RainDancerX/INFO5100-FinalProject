/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snapshop.model;

/**
 *
 * @author lucas
 */
public class Customer extends User {

    private int customerId;
    private int age;
    private String address;
    private String phone;

    public Customer(String username, String password, int age, String phone, String address, int customerId) {
        super(username, password);
        this.customerId = customerId;
        this.age = age;
        this.address = address;
        this.phone = phone;
    }
}
