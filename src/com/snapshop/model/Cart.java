package com.snapshop.model;

import com.snapshop.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
//import com.snapshop.controller.PlatformController;

public class Cart {

    private int customerId;
    private int cartId;
    private double totalAmount;
    private List<CartItem> cartItems;

    // Constructor
    public Cart(int customerId, int cartId) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.cartItems = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    // Fetch cart data from the database
    public static Cart loadMyCart(int customerId) {
        Connection connection = null;
        PreparedStatement cartStatement = null;
        PreparedStatement itemsStatement = null;
        ResultSet cartResultSet = null;
        ResultSet itemsResultSet = null;

        Cart cart = null;

        try {
            // Establish database connection
            connection = DatabaseConnection.getConnection();

            // Load cart metadata
            String cartQuery = "SELECT cartId, totalAmount FROM cart WHERE customerId = ?";
            cartStatement = connection.prepareStatement(cartQuery);
            cartStatement.setInt(1, customerId);
            cartResultSet = cartStatement.executeQuery();

            if (cartResultSet.next()) {
                int cartId = cartResultSet.getInt("cartId");
                double totalAmount = cartResultSet.getDouble("totalAmount");

                cart = new Cart(customerId, cartId);
                cart.totalAmount = totalAmount;

                // Load cart items
                String itemsQuery = "SELECT ci.itemId, ci.quantity, i.itemName, i.description, i.brand, i.category, "
                        + "i.price, i.inventory, i.gender, i.img "
                        + "FROM cart_items ci "
                        + "JOIN item i ON ci.itemId = i.itemId "
                        + "WHERE ci.cartId = ?";
                itemsStatement = connection.prepareStatement(itemsQuery);
                itemsStatement.setInt(1, cartId);
                itemsResultSet = itemsStatement.executeQuery();

                while (itemsResultSet.next()) {
                    int itemId = itemsResultSet.getInt("itemId");
                    String itemName = itemsResultSet.getString("itemName");
                    String description = itemsResultSet.getString("description");
                    String brand = itemsResultSet.getString("brand");
                    String category = itemsResultSet.getString("category");
                    double price = itemsResultSet.getDouble("price");
                    int inventory = itemsResultSet.getInt("inventory");
                    String gender = itemsResultSet.getString("gender");
                    int quantity = itemsResultSet.getInt("quantity");
                    String imgPath = itemsResultSet.getString("img"); // Get the image path from the database
                    ImageIcon image;
                    try {
                        // Attempt to load the image from the path in the database
                        image = new ImageIcon(Cart.class.getResource(imgPath));
                    } catch (Exception e) {
                        // If the image path is invalid or missing, use a default image
                        image = new ImageIcon(Cart.class.getResource("/com/snapshop/image/default.png"));
                    }

                    ModelItem item = new ModelItem(itemId, itemName, description, category, gender, price, inventory, brand, image);
                    cart.cartItems.add(new CartItem(item, quantity));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (cartResultSet != null) {
                    cartResultSet.close();
                }
                if (itemsResultSet != null) {
                    itemsResultSet.close();
                }
                if (cartStatement != null) {
                    cartStatement.close();
                }
                if (itemsStatement != null) {
                    itemsStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cart;
    }

    public List<CartItem> getCartItems() {
        return this.cartItems;
    }

    // Calculate the total amount of the cart
    private void calculateTotalAmount() {
        totalAmount = cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getItem().getPrice() * cartItem.getQuantity())
                .sum();
    }
    
    public double getTotalAmount(){
        return this.totalAmount;
    }

    // Add an item to the cart in the database
    public void addItem(ModelItem item, int quantity) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Check if the item already exists in the cart
            String checkQuery = "SELECT quantity FROM cart_items WHERE cartId = ? AND itemId = ?";
            statement = connection.prepareStatement(checkQuery);
            statement.setInt(1, this.cartId);
            statement.setInt(2, item.getItemId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Update quantity
//                int newQuantity = resultSet.getInt("quantity") + quantity;
                String updateQuery = "UPDATE cart_items SET quantity = ? WHERE cartId = ? AND itemId = ?";
                statement = connection.prepareStatement(updateQuery);
                statement.setInt(1, quantity);
                statement.setInt(2, this.cartId);
                statement.setInt(3, item.getItemId());
            } else {
                // Insert new item
                String insertQuery = "INSERT INTO cart_items (cartId, itemId, quantity) VALUES (?, ?, ?)";
                statement = connection.prepareStatement(insertQuery);
                statement.setInt(1, this.cartId);
                statement.setInt(2, item.getItemId());
                statement.setInt(3, quantity);
            }
            statement.executeUpdate();

            // Update cart object and total amount
            cartItems.add(new CartItem(item, quantity));
            calculateTotalAmount();
            updateTotalAmount();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
    }

    // Update the total amount in the database
    private void updateTotalAmount() {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            String updateQuery = "UPDATE cart SET totalAmount = ? WHERE cartId = ?";
            statement = connection.prepareStatement(updateQuery);
            statement.setDouble(1, this.totalAmount);
            statement.setInt(2, this.cartId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
    }

    // Remove an item from the cart in the database
    public void removeItem(int itemId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();
            String deleteQuery = "DELETE FROM cart_items WHERE cartId = ? AND itemId = ?";
            statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, this.cartId);
            statement.setInt(2, itemId);
            statement.executeUpdate();

            // Update cart object and total amount
            cartItems.removeIf(cartItem -> cartItem.getItem().getItemId() == itemId);
            calculateTotalAmount();
            updateTotalAmount();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
    }

    // Update the quantity of an item in the cart and the database
    public void setQuantity(int itemId, int newQuantity) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Update the quantity in the database
            String updateQuery = "UPDATE cart_items SET quantity = ? WHERE cartId = ? AND itemId = ?";
            statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, newQuantity);
            statement.setInt(2, this.cartId);
            statement.setInt(3, itemId);
            statement.executeUpdate();

            // Update the cart object
            for (CartItem cartItem : cartItems) {
                if (cartItem.getItem().getItemId() == itemId) {
                    cartItem.setQuantity(newQuantity);
                    break;
                }
            }

            // Recalculate the total amount and update it in the database
            calculateTotalAmount();
            updateTotalAmount();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
    }

    // Nested Class to Represent an Item in the Cart
    public static class CartItem {

        private ModelItem item;   // Item details
        private int quantity;     // Quantity of the item

        // Constructor
        public CartItem(ModelItem item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        // Getters and Setters
        public ModelItem getItem() {
            return item;
        }

        public void setItem(ModelItem item) {
            this.item = item;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "CartItem{"
                    + "item=" + item.getItemName()
                    + ", quantity=" + quantity
                    + ", total=" + (item.getPrice() * quantity)
                    + '}';
        }
    }
}
