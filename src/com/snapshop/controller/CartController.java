/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snapshop.controller;

import com.snapshop.model.Cart;
import com.snapshop.model.Customer;
import com.snapshop.model.ModelItem;
import com.snapshop.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // Add this import
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author lucas
 */
public class CartController {

    private Customer user;
    private int cartId;

    public CartController() {
        // Get the singleton instance
        PlatformController platformController = PlatformController.getInstance();
        user = (Customer) platformController.getCurrentUser();
        cartId = platformController.getCartId();

        if (user == null) {
            throw new IllegalStateException("No logged-in user found. Please log in first.");
        }
    }

//    public Cart loadMyCart() {
//        List<Cart.CartItem> cartItem = null;
//        Cart cart = new Cart(user.getCustomerId(), cartId);
//        List<ModelItem> items = new ArrayList<>();
//        String query = "SELECT ci.itemId, ci.quantity, i.itemName, i.description, i.brand, i.category, "
//                + "i.price, i.inventory, i.gender, i.img "
//                + "FROM cart_items ci "
//                + "JOIN item i ON ci.itemId = i.itemId "
//                + "WHERE ci.cartId = " + cartId;
//
//        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
//
//            while (rs.next()) {
//                ImageIcon image;
//                int itemId = rs.getInt("itemId");
//                String itemName = rs.getString("itemName");
//                String description = rs.getString("description");
//                String brand = rs.getString("brand");
//                String category = rs.getString("category");
//                double price = rs.getDouble("price");
//                int inventory = rs.getInt("inventory");
//                String gender = rs.getString("gender");
//                int quantity = rs.getInt("quantity");
//                try {
//                    // Attempt to load the image from the path in the database
//                    image = new ImageIcon(getClass().getResource(rs.getString("img")));
//                } catch (Exception e) {
//                    // If the image path is invalid or missing, use a default image
//                    image = new ImageIcon(getClass().getResource("/com/snapshop/image/default.png"));
//                }
//                ModelItem item = new ModelItem(itemId, itemName, description, category, gender, price, inventory, brand, image);
//                cartItem.add(new Cart.CartItem(item, quantity));
//            }
//            cart.setCartItems(cartItem);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return cart;
//    }

}
