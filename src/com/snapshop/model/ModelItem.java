/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snapshop.model;

import javax.swing.Icon;

/**
 *
 * @author lucas
 */
public class ModelItem {

    private int itemId;
    private String itemName;
    private String description;
    private String brand;
    private String category;
    private double price;
    private Icon image;
    private int inventory;
    private String gender;

    public ModelItem(int itemId, String itemName, String description, String category, String gender, double price, int inventory, String brand, Icon image) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.image = image;
        this.inventory = inventory;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender() {
        this.gender = gender;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory() {
        this.inventory = inventory;
    }

    /**
     * @return the itemId
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public String setBrand(String brand) {
        return this.brand = brand;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the image
     */
    public Icon getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(Icon image) {
        this.image = image;
    }

}
