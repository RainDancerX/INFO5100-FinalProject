package com.snapshop.form;

import com.snapshop.component.CartItemComponent;
import com.snapshop.component.CheckoutItemComponent;
import com.snapshop.controller.PlatformController;
import com.snapshop.main.Dashboard;
import com.snapshop.model.Cart;
import com.snapshop.model.Customer;
import com.snapshop.swing.ScrollBar;
import com.snapshop.util.DatabaseConnection;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
/**
 *
 * @author lucas
 */
public class FormCheckout extends javax.swing.JPanel {

    private PlatformController controller;
    private Customer currentUser;
    private int customerId;
    private int cartId;
    private Cart cart;
    private double shippingFee = 2.99; // Default to Ground Shipping
    private boolean isCouponApplied = false;
    private double discountFraction = 1.0; // Default no discount

    /**
     * Creates new form FormCheckout
     */
    public FormCheckout() {
        init();
        initComponents();
        scroll.setVerticalScrollBar(new ScrollBar());
        controller = PlatformController.getInstance();
        loadCartData();
        setupShippingListeners(); // Add listeners for shipping selection
    }

    private void init() {
        controller = PlatformController.getInstance();
        currentUser = (Customer) controller.getCurrentUser();
        customerId = currentUser.getCustomerId();
        cartId = controller.getCartId();
    }

    private void setupShippingListeners() {
        // Update shipping fee dynamically based on user selection
        ground.addActionListener(e -> updateShippingFee(2.99));
        economy.addActionListener(e -> updateShippingFee(1.99));
        express.addActionListener(e -> updateShippingFee(5.99));
        priority.addActionListener(e -> updateShippingFee(8.99));
    }

    private void updateShippingFee(double fee) {
        shippingFee = fee;
        refreshTotals();
    }

    private void refreshTotals() {
        DecimalFormat df = new DecimalFormat("$#,##0.00");

        // Calculate subtotal and taxes
        double subtotal = cart.getTotalAmount();
        double gst = subtotal * 0.05;
        double pst = subtotal * 0.07;

        // Apply the discount to subtotal and taxes
        double discountedSubtotal = subtotal * discountFraction;
        double discountedGst = gst * discountFraction;
        double discountedPst = pst * discountFraction;

        // Calculate the total amount (discounted subtotal + taxes + shipping cost)
        double total = discountedSubtotal + discountedGst + discountedPst + shippingFee;

        // Update UI
        shippingCost.setText(df.format(shippingFee));
        gstCost.setText(df.format(discountedGst));
        pstCost.setText(df.format(discountedPst));
        totalAmount.setText(df.format(total));
    }

    // Load the cart data from the database
    private void loadCartData() {
        SwingUtilities.invokeLater(() -> {
            cart = Cart.loadMyCart(customerId);
            if (cart != null) {
                panelItem.removeAll();

                for (Cart.CartItem cartItem : cart.getCartItems()) {
                    CheckoutItemComponent checkoutItemComponent = new CheckoutItemComponent(cart);
                    checkoutItemComponent.setData(cartItem);
                    panelItem.add(checkoutItemComponent);
                }

                panelItem.repaint();
                panelItem.revalidate();

                int totalQuantity = cart.getCartItems().stream()
                        .mapToInt(Cart.CartItem::getQuantity)
                        .sum();
                allQuantity.setText(String.valueOf(totalQuantity));
            }

            customerName.setText(currentUser.getUsername());
            phone.setText(currentUser.getPhone());
            address.setText(currentUser.getAddress());

            refreshTotals();

            if (cart.getCartItems().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Your Cart is empty!", "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void applyCoupon(String couponCode) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT discountFraction FROM coupon WHERE couponCode = ? AND status = 'active'";
            statement = connection.prepareStatement(query);
            statement.setString(1, couponCode);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                discountFraction = resultSet.getDouble("discountFraction");
                isCouponApplied = true;
                JOptionPane.showMessageDialog(this, "Coupon applied successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTotals();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid or inactive coupon code.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error validating coupon code.", "Error", JOptionPane.ERROR_MESSAGE);
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        shippingChoice = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        customerName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        address = new javax.swing.JTextPane();
        phone = new javax.swing.JLabel();
        pictureBox1 = new com.snapshop.swing.PictureBox();
        scroll = new javax.swing.JScrollPane();
        panelItem = new com.snapshop.swing.PanelItem();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        paymentInput = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        couponInput = new javax.swing.JTextField();
        applyCouponBtn = new javax.swing.JButton();
        orderDesc = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        placeOrderBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        allQuantity = new javax.swing.JLabel();
        shippingCost = new javax.swing.JLabel();
        gstCost = new javax.swing.JLabel();
        pstCost = new javax.swing.JLabel();
        totalAmount = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        ground = new javax.swing.JRadioButton();
        economy = new javax.swing.JRadioButton();
        express = new javax.swing.JRadioButton();
        jSeparator2 = new javax.swing.JSeparator();
        priority = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1170, 697));

        jLabel1.setBackground(new java.awt.Color(48, 110, 77));
        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 2, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Secure checkout");
        jLabel1.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(246, 246, 246));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Delivering to");

        customerName.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        customerName.setForeground(new java.awt.Color(102, 102, 102));
        customerName.setText("NAME");

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Address");

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Phone");

        address.setBorder(null);
        address.setFont(new java.awt.Font("Helvetica Neue", 2, 12)); // NOI18N
        address.setForeground(new java.awt.Color(153, 153, 153));
        address.setText("3050 E 22nd Ave, Vancouver, BC V5M 2Y6");
        address.setFocusable(false);
        address.setMargin(new java.awt.Insets(2, 2, 2, 2));
        address.setOpaque(false);

        phone.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        phone.setForeground(new java.awt.Color(102, 102, 102));
        phone.setText("PHONE");

        pictureBox1.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/snapshop/icon/IMG_0438.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(customerName, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pictureBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(customerName)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(phone))
                    .addComponent(pictureBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scroll.setBackground(new java.awt.Color(255, 255, 255));
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelItem.setBackground(new java.awt.Color(244, 244, 244));
        scroll.setViewportView(panelItem);

        jPanel2.setBackground(new java.awt.Color(244, 243, 243));
        jPanel2.setForeground(new java.awt.Color(102, 102, 102));

        jLabel17.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("Payment Method");

        paymentInput.setBackground(new java.awt.Color(204, 204, 204));
        paymentInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Credit Card", "PayPal", "Bank Transfer", "Balance" }));

        jLabel18.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setText("Enter a gift card, voucher or promotional code");

        couponInput.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        couponInput.setForeground(new java.awt.Color(102, 102, 102));

        applyCouponBtn.setBackground(new java.awt.Color(244, 243, 243));
        applyCouponBtn.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        applyCouponBtn.setForeground(new java.awt.Color(255, 255, 255));
        applyCouponBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/snapshop/icon/211877_plus_round_icon_20x20.png"))); // NOI18N
        applyCouponBtn.setBorder(null);
        applyCouponBtn.setBorderPainted(false);
        applyCouponBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        applyCouponBtn.setFocusPainted(false);
        applyCouponBtn.setOpaque(true);
        applyCouponBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyCouponBtnActionPerformed(evt);
            }
        });

        orderDesc.setBorder(null);
        orderDesc.setFont(new java.awt.Font("Helvetica Neue", 2, 12)); // NOI18N
        orderDesc.setForeground(new java.awt.Color(153, 153, 153));
        orderDesc.setText("Join Membership, only $0.99/month \n\n                  @SnapShop");
        orderDesc.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        orderDesc.setFocusable(false);
        orderDesc.setMargin(new java.awt.Insets(2, 2, 2, 2));
        orderDesc.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paymentInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(orderDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(couponInput, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(applyCouponBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(paymentInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(couponInput, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(applyCouponBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(orderDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(244, 244, 244));

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));

        placeOrderBtn.setBackground(new java.awt.Color(255, 204, 51));
        placeOrderBtn.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        placeOrderBtn.setForeground(new java.awt.Color(0, 0, 0));
        placeOrderBtn.setText("Place your order");
        placeOrderBtn.setBorderPainted(false);
        placeOrderBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        placeOrderBtn.setFocusPainted(false);
        placeOrderBtn.setOpaque(true);
        placeOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeOrderBtnActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("items:");

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Shipping & Handling:");

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Estimated GST/HST:");

        jLabel10.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Estimated PST/RST/QST:");

        jLabel11.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Order Total:");

        allQuantity.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        allQuantity.setForeground(new java.awt.Color(102, 102, 102));
        allQuantity.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        allQuantity.setText("1");

        shippingCost.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        shippingCost.setForeground(new java.awt.Color(102, 102, 102));
        shippingCost.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        shippingCost.setText("2");

        gstCost.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        gstCost.setForeground(new java.awt.Color(102, 102, 102));
        gstCost.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        gstCost.setText("3");

        pstCost.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        pstCost.setForeground(new java.awt.Color(102, 102, 102));
        pstCost.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        pstCost.setText("4");

        totalAmount.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        totalAmount.setForeground(new java.awt.Color(0, 0, 0));
        totalAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalAmount.setText("5");

        txtDescription.setBorder(null);
        txtDescription.setFont(new java.awt.Font("Helvetica Neue", 2, 11)); // NOI18N
        txtDescription.setForeground(new java.awt.Color(153, 153, 153));
        txtDescription.setText("By placing your order, you are agree to Snapshop's  privacy notice and conditions of use.");
        txtDescription.setFocusable(false);
        txtDescription.setMargin(new java.awt.Insets(2, 2, 2, 2));
        txtDescription.setOpaque(false);

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Shipping Method");

        shippingChoice.add(ground);
        ground.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        ground.setSelected(true);
        ground.setText("Ground Shipping (7-10 days)");

        shippingChoice.add(economy);
        economy.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        economy.setText("Economy Shipping (1-14 days)");

        shippingChoice.add(express);
        express.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        express.setText("Express Shipping (3-5 days)");

        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));

        shippingChoice.add(priority);
        priority.setText("Priority Shipping (1-3 days)");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("$2.99");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("$1.99");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("$5.99");

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("$8.99");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(94, 94, 94))
                                .addComponent(jLabel8))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel10)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addGap(72, 72, 72))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addGap(31, 31, 31))))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(allQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addComponent(shippingCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(totalAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(gstCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pstCost, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(placeOrderBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(ground)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(economy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(express)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(priority)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ground, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(economy)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(express)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priority)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(allQuantity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(shippingCost))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(gstCost))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(pstCost))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(placeOrderBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scroll, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void placeOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeOrderBtnActionPerformed
        // TODO add your handling code here:
        if (cart.getCartItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty. Add items to proceed.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection connection = null;
        PreparedStatement orderStatement = null;
        PreparedStatement orderItemStatement = null;
        PreparedStatement updateInventoryStatement = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            double subtotal = cart.getTotalAmount();
            double gst = subtotal * 0.05;
            double pst = subtotal * 0.07;

            // Apply the discount to subtotal and taxes
            double discountedSubtotal = subtotal * discountFraction;
            double discountedGst = gst * discountFraction;
            double discountedPst = pst * discountFraction;

            // Calculate the final total amount
            double total = discountedSubtotal + discountedGst + discountedPst + shippingFee;

            String insertOrderQuery = "INSERT INTO orders (customerId, totalAmount, shippingAddress, paymentMethod) VALUES (?, ?, ?, ?)";
            orderStatement = connection.prepareStatement(insertOrderQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            orderStatement.setInt(1, customerId);
            orderStatement.setDouble(2, total);
            orderStatement.setString(3, currentUser.getAddress());
            orderStatement.setString(4, paymentInput.getSelectedItem().toString());
            orderStatement.executeUpdate();

            ResultSet generatedKeys = orderStatement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new SQLException("Failed to retrieve the order ID.");
            }
            int orderId = generatedKeys.getInt(1);

            String insertOrderItemQuery = "INSERT INTO order_items (orderId, itemId, quantity) VALUES (?, ?, ?)";
            orderItemStatement = connection.prepareStatement(insertOrderItemQuery);

            String updateInventoryQuery = "UPDATE item SET inventory = inventory - ? WHERE itemId = ?";
            updateInventoryStatement = connection.prepareStatement(updateInventoryQuery);

            for (Cart.CartItem cartItem : cart.getCartItems()) {
                orderItemStatement.setInt(1, orderId);
                orderItemStatement.setInt(2, cartItem.getItem().getItemId());
                orderItemStatement.setInt(3, cartItem.getQuantity());
                orderItemStatement.addBatch();

                updateInventoryStatement.setInt(1, cartItem.getQuantity());
                updateInventoryStatement.setInt(2, cartItem.getItem().getItemId());
                updateInventoryStatement.addBatch();
            }

            orderItemStatement.executeBatch();
            updateInventoryStatement.executeBatch();

            // Clear the cart in the database
            cart.clearCart(connection);

            connection.commit();

            JOptionPane.showMessageDialog(this, "Your order has been placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            Dashboard dashboard = (Dashboard) SwingUtilities.getWindowAncestor(this);
            dashboard.showHomePanel();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Failed to place order. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (orderStatement != null) {
                    orderStatement.close();
                }
                if (orderItemStatement != null) {
                    orderItemStatement.close();
                }
                if (updateInventoryStatement != null) {
                    updateInventoryStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_placeOrderBtnActionPerformed

    private void applyCouponBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyCouponBtnActionPerformed
        // TODO add your handling code here:
        String couponCode = couponInput.getText().trim();
        if (couponCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a coupon code.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        applyCoupon(couponCode);
    }//GEN-LAST:event_applyCouponBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane address;
    private javax.swing.JLabel allQuantity;
    private javax.swing.JButton applyCouponBtn;
    private javax.swing.JTextField couponInput;
    private javax.swing.JLabel customerName;
    private javax.swing.JRadioButton economy;
    private javax.swing.JRadioButton express;
    private javax.swing.JRadioButton ground;
    private javax.swing.JLabel gstCost;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextPane orderDesc;
    private com.snapshop.swing.PanelItem panelItem;
    private javax.swing.JComboBox<String> paymentInput;
    private javax.swing.JLabel phone;
    private com.snapshop.swing.PictureBox pictureBox1;
    private javax.swing.JButton placeOrderBtn;
    private javax.swing.JRadioButton priority;
    private javax.swing.JLabel pstCost;
    private javax.swing.JScrollPane scroll;
    private javax.swing.ButtonGroup shippingChoice;
    private javax.swing.JLabel shippingCost;
    private javax.swing.JLabel totalAmount;
    private javax.swing.JTextPane txtDescription;
    // End of variables declaration//GEN-END:variables
}
