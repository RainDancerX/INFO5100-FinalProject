/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snapshop.model;

import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;              // Unique ID for the order
    private User customer;            // The customer who placed the order
    private List<ModelItem> items;    // List of items in the order
    private double totalAmount;       // Total cost of the order
    private String status;            // Order status (e.g., "Pending", "Shipped", "Delivered")
    private Date orderDate;           // Date when the order was placed
    private String shippingAddress;   // Shipping address for the order
    private String paymentMethod;     // Payment method used (e.g., "Credit Card", "PayPal")

    // Constructor
    public Order(int orderId, User customer, List<ModelItem> items, double totalAmount, 
                 String status, Date orderDate, String shippingAddress, String paymentMethod) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public List<ModelItem> getItems() {
        return items;
    }

    public void setItems(List<ModelItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Helper Method to Calculate Total Amount
    public void calculateTotalAmount() {
        this.totalAmount = items.stream().mapToDouble(ModelItem::getPrice).sum();
    }
}