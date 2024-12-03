/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snapshop.controller;

import com.snapshop.model.Vendor;
import java.util.ArrayList;
import java.util.List;

import com.snapshop.model.Customer;
import com.snapshop.model.User;
import com.snapshop.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    // Singleton instance
    private static PlatformController instance;

    // List to store members
    private List<User> userList = new ArrayList<>();
    private User currentUser;

    public PlatformController() {

    }

    public static PlatformController getInstance() {
        if (instance == null) {
            instance = new PlatformController();
        }
        return instance;
    }

    public boolean registerCustomer(String username, String password, int age, String phone, String address) {
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement insertCustomerStatement = null;
        PreparedStatement insertCartStatement = null;
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

            // Step 2: Insert the new customer
            String insertCustomerSql = "INSERT INTO customer (username, password, age, phone, address) VALUES (?, ?, ?, ?, ?)";
            insertCustomerStatement = connection.prepareStatement(insertCustomerSql, Statement.RETURN_GENERATED_KEYS);
            insertCustomerStatement.setString(1, username);
            insertCustomerStatement.setString(2, password);
            insertCustomerStatement.setInt(3, age);
            insertCustomerStatement.setString(4, phone);
            insertCustomerStatement.setString(5, address);

            // Execute the insert
            int affectedRows = insertCustomerStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating customer failed, no rows affected.");
            }

            // Retrieve the generated customerId
            ResultSet generatedKeys = insertCustomerStatement.getGeneratedKeys();
            int customerId = -1;
            if (generatedKeys.next()) {
                customerId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating customer failed, no ID obtained.");
            }

            // Step 3: Create a new cart for the customer
            String insertCartSql = "INSERT INTO cart (customerId, totalAmount) VALUES (?, ?)";
            insertCartStatement = connection.prepareStatement(insertCartSql);
            insertCartStatement.setInt(1, customerId);
            insertCartStatement.setDouble(2, 0.00); // New cart starts with a total amount of 0
            insertCartStatement.executeUpdate();

            System.out.println("Customer and cart created successfully: " + username);

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
                if (insertCustomerStatement != null) {
                    insertCustomerStatement.close();
                }
                if (insertCartStatement != null) {
                    insertCartStatement.close();
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
                    case "vendor":
                        int vendorId = resultSet.getInt("vendorId");
                        currentUser = new Vendor(username, password, vendorId);
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

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public int getCartId() {
        Customer user = (Customer) currentUser;
        int cartId = -1;
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement insertStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            connection = DatabaseConnection.getConnection();

            // Step 1: Check if the username already exists
            String checkSql = "SELECT cartId FROM cart WHERE customerId = ?";
            checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setInt(1, user.getCustomerId());

            resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                cartId = resultSet.getInt("cartId");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            // Close resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (checkStatement != null) {
                    checkStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cartId;
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

    public int fetchLatestInventory(int itemId) {
        int inventory = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT inventory FROM item WHERE itemId = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, itemId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                inventory = resultSet.getInt("inventory");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Error fetching the latest inventory: " + e.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return inventory;
    }

    public List<ModelItem> getFilteredItems(String column, String value) {
        List<ModelItem> items = new ArrayList<>();
        String query = "SELECT * FROM item WHERE " + column + " = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ImageIcon image;
                try {
                    image = new ImageIcon(getClass().getResource(rs.getString("img")));
                } catch (Exception e) {
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
                );
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

}
