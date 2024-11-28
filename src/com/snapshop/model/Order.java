package com.snapshop.model;

import com.snapshop.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;

public class Order {

    private int orderId;
    private Customer customer;
    private List<OrderItem> orderItems;
    private double totalAmount;
    private String status;
    private Date orderDate;
    private String shippingAddress;
    private String paymentMethod;

    // Constructor
    public Order(int orderId, Customer customer, double totalAmount, String status, Date orderDate, String shippingAddress, String paymentMethod) {
        this.orderId = orderId;
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.orderItems = new ArrayList<>();
    }
    
    public void setStatus(String status){
        this.status = status;
    }

    // Method to calculate the total quantity of all items in the order
    public int getTotalQuantity() {
        return orderItems.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
    }

    // Fetch orders for a specific customer from the database
    public static List<Order> getOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE customerId = ? ORDER BY createdAt DESC";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("orderId");
                double totalAmount = resultSet.getDouble("totalAmount");
                String status = resultSet.getString("status");
                Date orderDate = resultSet.getTimestamp("createdAt");
                String shippingAddress = resultSet.getString("shippingAddress");
                String paymentMethod = resultSet.getString("paymentMethod");

                Order order = new Order(orderId, null, totalAmount, status, orderDate, shippingAddress, paymentMethod);
                order.loadOrderItems(); // Load order items for the specific order
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // Load order items for this order
    private void loadOrderItems() {
        String query = "SELECT oi.itemId, oi.quantity, i.itemName, i.description, i.price, i.img "
                + "FROM order_items oi "
                + "JOIN item i ON oi.itemId = i.itemId WHERE oi.orderId = ?";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, this.orderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("itemId");
                String itemName = resultSet.getString("itemName");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String imgPath = resultSet.getString("img");

                ImageIcon image;
                try {
                    image = new ImageIcon(Order.class.getResource(imgPath));
                } catch (Exception e) {
                    image = new ImageIcon(Order.class.getResource("/com/snapshop/image/default.png"));
                }

                ModelItem item = new ModelItem(itemId, itemName, description, null, null, price, 0, null, image);
                this.orderItems.add(new OrderItem(item, quantity));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Nested OrderItem class to represent each item in an order
    public static class OrderItem {

        private ModelItem item;   // Item details
        private int quantity;     // Quantity of the item in the order

        public OrderItem(ModelItem item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public ModelItem getItem() {
            return item;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
