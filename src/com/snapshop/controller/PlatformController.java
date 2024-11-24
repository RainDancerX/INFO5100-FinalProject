/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snapshop.controller;

import java.util.ArrayList;
import java.util.List;

import com.snapshop.model.Customer;
import com.snapshop.model.User;
import com.snapshop.util.DatabaseConnection;
import java.sql.Connection; // Add this import
import java.sql.PreparedStatement; // Add this import
import java.sql.ResultSet; // Add this import
import java.sql.SQLException; // Add this import
import javax.swing.JOptionPane;
import com.snapshop.model.Administrator;
import com.snapshop.model.Associate;
import com.snapshop.model.ModelItem;
import javax.swing.ImageIcon;

/**
 *
 * @author lucas
 */
public class PlatformController {

    // List to store members
    private List<User> userList = new ArrayList<>();
    private User currentUser;

    public PlatformController() {

    }

    public boolean registerCustomer(String username, String password, int age, String phone, String address) {
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement insertStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            connection = DatabaseConnection.getConnection();

            // Step 1: Check if the username already exists
            String checkSql = "SELECT username FROM customer WHERE username = ?";
            checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setString(1, username);

            // Execute the query to check for duplicates
            resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                // Username already exists
                JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different username.", "Duplicate Username", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Step 2: Insert the new customer if no duplicate found
            String insertSql = "INSERT INTO customer (username, password, age, phone, address) VALUES (?, ?, ?, ?, ?)";
            insertStatement = connection.prepareStatement(insertSql);
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.setInt(3, age);
            insertStatement.setString(4, phone);
            insertStatement.setString(5, address);

            // Execute the insert
            insertStatement.executeUpdate();
            System.out.println("User registered successfully: " + username);

            // Show success message
            JOptionPane.showMessageDialog(null, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred during registration. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                // Close resources
                if (resultSet != null) {
                    resultSet.close();
                }
                if (checkStatement != null) {
                    checkStatement.close();
                }
                if (insertStatement != null) {
                    insertStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean registerStaff(String username, String password, String securityCode, String role) {
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement insertStatement = null;
        ResultSet resultSet = null;

        if (!securityCode.equals("1234")) {
            JOptionPane.showMessageDialog(null, "Invalid Security code.", "ERROR", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            // Establish database connection
            connection = DatabaseConnection.getConnection();

            // Step 1: Check if the username already exists
            String checkSql = "SELECT username FROM " + role + " WHERE username = ?";
            checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setString(1, username);

            // Execute the query to check for duplicates
            resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                // Username already exists
                JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different username.", "Duplicate Username", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Step 2: Insert the new customer if no duplicate found
            String insertSql = "INSERT INTO " + role + " (username, password) VALUES (?, ?)";
            insertStatement = connection.prepareStatement(insertSql);
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);

            // Execute the insert
            insertStatement.executeUpdate();
            System.out.println("Staff registered successfully: " + username);

            // Show success message
            JOptionPane.showMessageDialog(null, "Staff registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred during registration. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                // Close resources
                if (resultSet != null) {
                    resultSet.close();
                }
                if (checkStatement != null) {
                    checkStatement.close();
                }
                if (insertStatement != null) {
                    insertStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Validate login credentials
    public boolean validateLogin(String username, String password, String role) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            connection = DatabaseConnection.getConnection();

            // SQL query to validate user credentials
            String sql = "SELECT * FROM " + role + " WHERE username = ? AND password = ?";

            // Prepare statement to execute query
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute query
            resultSet = preparedStatement.executeQuery();

            // Check if user exists
            if (resultSet.next()) {
                System.out.println("Login successful for user: " + username);
                // Set current user
                switch (role.toLowerCase()) {
                    case "customer":
                        // Retrieve all customer fields
                        String customerUsername = resultSet.getString("username");
                        String customerPassword = resultSet.getString("password");
                        int customerId = resultSet.getInt("customerId");
                        int age = resultSet.getInt("age");
                        String phone = resultSet.getString("phone");
                        String address = resultSet.getString("address");

                        // Instantiate a new Customer object
                        currentUser = new Customer(customerUsername, customerPassword, age, phone, address, customerId);
                        break;

                    case "admin":
                        int adminId = resultSet.getInt("adminId");
                        currentUser = new Administrator(username, password, adminId);
                        break;

                    case "associate":
                        int associateId = resultSet.getInt("associateId");
                        currentUser = new Associate(username, password, associateId);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid role: " + role);
                }

                return true;
            } else {
                System.out.println("Invalid username or password.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error occurred while validating login.");
            return false;
        } finally {
            try {
                // Close resources
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public User getCurrentMember() {
        return currentUser;
    }

    public List<User> getAllMembers() {
        return userList;
    }

    public List<ModelItem> getAllItems() {
        List<ModelItem> items = new ArrayList<>();
        String query = "SELECT * FROM item";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ImageIcon image;
                try {
                    // Attempt to load the image from the path in the database
                    image = new ImageIcon(getClass().getResource(rs.getString("img")));
                } catch (Exception e) {
                    // If the image path is invalid or missing, use a default image
                    image = new ImageIcon(getClass().getResource("/com/snapshop/image/default.png"));
                }
                ModelItem item = new ModelItem(
                        rs.getInt("itemId"),
                        rs.getString("itemName"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getString("gender"),
                        rs.getDouble("price"),
                        rs.getInt("inventory"),
                        rs.getString("brand"),
                        image
                // new ImageIcon(getClass().getResource(rs.getString("img"))) // Adjust based on how you store image paths
                );
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

}
