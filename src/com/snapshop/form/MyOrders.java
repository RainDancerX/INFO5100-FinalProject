/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.snapshop.form;

import com.snapshop.component.OrderItemComponent;
import com.snapshop.controller.PlatformController;
import com.snapshop.model.Customer;
import com.snapshop.model.Order;
import com.snapshop.swing.ScrollBar;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author lucas
 */
public class MyOrders extends javax.swing.JPanel {
    private PlatformController controller;
    private int customerId;
    private Customer customer;

    /**
     * Creates new form MyOrders
     */
    public MyOrders() {
        initComponents();
        scroll.setVerticalScrollBar(new ScrollBar());
        controller = PlatformController.getInstance();
        customer = (Customer)controller.getCurrentUser();
        customerId = customer.getCustomerId();
        loadOrders();
    }
    
    private void loadOrders() {
        SwingUtilities.invokeLater(() -> {
            List<Order> orders = Order.getOrdersByCustomerId(customerId);
            panelItem.removeAll();

            for (Order order : orders) {
                OrderItemComponent orderItemComponent = new OrderItemComponent();
                orderItemComponent.setData(order);
                panelItem.add(orderItemComponent);
            }

            panelItem.repaint();
            panelItem.revalidate();
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        scroll = new javax.swing.JScrollPane();
        panelItem = new com.snapshop.swing.PanelItem();

        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("Your Orders");

        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setViewportView(panelItem);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1092, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private com.snapshop.swing.PanelItem panelItem;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
}
