package com.snapshop.form;

import com.snapshop.util.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateItemDialog extends JDialog {

    private int itemId;
    private JTextField itemNameField, categoryField, genderField, priceField, brandField, inventoryField;
    private boolean updated = false;

    public UpdateItemDialog(JFrame parent, int itemId, String itemName, String category, String gender, double price, String brand, int inventory) {
        super(parent, "Update Item", true);
        this.itemId = itemId;

        setLayout(new GridLayout(7, 2, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Fields
        add(new JLabel("Item Name:"));
        itemNameField = new JTextField(itemName);
        add(itemNameField);

        add(new JLabel("Category:"));
        categoryField = new JTextField(category);
        add(categoryField);

        add(new JLabel("Gender:"));
        genderField = new JTextField(gender);
        add(genderField);

        add(new JLabel("Price:"));
        priceField = new JTextField(String.valueOf(price));
        add(priceField);

        add(new JLabel("Brand:"));
        brandField = new JTextField(brand);
        add(brandField);

        add(new JLabel("Inventory:"));
        inventoryField = new JTextField(String.valueOf(inventory));
        add(inventoryField);

        // Buttons
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");

        updateButton.addActionListener(e -> updateItem());
        cancelButton.addActionListener(e -> dispose());

        add(updateButton);
        add(cancelButton);
    }

    private void updateItem() {
        // Validate inputs
        try {
            String itemName = itemNameField.getText().trim();
            String category = categoryField.getText().trim();
            String gender = genderField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            String brand = brandField.getText().trim();
            int inventory = Integer.parseInt(inventoryField.getText().trim());

            if (itemName.isEmpty() || category.isEmpty() || gender.isEmpty() || brand.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update database
            String query = "UPDATE item SET itemName = ?, category = ?, gender = ?, price = ?, brand = ?, inventory = ? WHERE itemId = ?";
            try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, itemName);
                statement.setString(2, category);
                statement.setString(3, gender);
                statement.setDouble(4, price);
                statement.setString(5, brand);
                statement.setInt(6, inventory);
                statement.setInt(7, itemId);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Item updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    updated = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update the item.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for price and inventory.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while updating the item.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isUpdated() {
        return updated;
    }
}
