package com.snapshop.form;

import com.snapshop.util.DatabaseConnection;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class AddCouponDialog extends JDialog {
    private JTextField couponCodeField;
    private JTextField discountFractionField;
    private JButton saveButton;
    private JButton cancelButton;

    public AddCouponDialog(Frame parent) {
        super(parent, "Add New Coupon", true);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Coupon Code:"));
        couponCodeField = new JTextField();
        inputPanel.add(couponCodeField);

        inputPanel.add(new JLabel("Discount Fraction (e.g., 0.1 for 10%):"));
        discountFractionField = new JTextField();
        inputPanel.add(discountFractionField);

        add(inputPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> saveCoupon());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
    }

    private void saveCoupon() {
        String couponCode = couponCodeField.getText().trim();
        String discountFractionStr = discountFractionField.getText().trim();

        if (couponCode.isEmpty() || discountFractionStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double discountFraction = Double.parseDouble(discountFractionStr);
            if (discountFraction < 0 || discountFraction > 1) {
                JOptionPane.showMessageDialog(this, "Discount fraction must be between 0 and 1.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query = "INSERT INTO coupon (couponCode, discountFraction, status) VALUES (?, ?, 'active')";
            try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, couponCode);
                statement.setDouble(2, discountFraction);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Coupon added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add coupon.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Discount fraction must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while saving the coupon.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}