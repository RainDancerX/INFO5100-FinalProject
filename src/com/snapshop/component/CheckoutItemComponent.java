/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.snapshop.component;

import com.snapshop.form.MyCart;
import com.snapshop.model.Cart;
import com.snapshop.model.Cart.CartItem;
import com.snapshop.model.ModelItem;
import java.text.DecimalFormat;

/**
 *
 * @author lucas
 */
public class CheckoutItemComponent extends javax.swing.JPanel {

    private Cart cart;
    private Cart.CartItem data;
    private ModelItem item;

    /**
     * Creates new form CheckoutItemComponent
     */
    public CheckoutItemComponent(Cart cart) {
        initComponents();
        this.cart = cart;
    }

    public Cart.CartItem getData() {
        return data;
    }

    public void setData(CartItem data) {
        this.data = data;
        item = data.getItem();
        lbItemName.setText(item.getItemName());
        txtDescription.setText(item.getDescription());
        lbBrand.setText(item.getBrand());
        DecimalFormat df = new DecimalFormat("$#, #00.00");
        lbPrice.setText(df.format(item.getPrice()));
        pic.setImage(item.getImage());
        jLabel2.setText(String.valueOf(data.getQuantity()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pic = new com.snapshop.swing.PictureBox();
        lbItemName = new javax.swing.JLabel();
        lbBrand = new javax.swing.JLabel();
        lbPrice = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        pic.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/snapshop/image/img1.png"))); // NOI18N

        lbItemName.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        lbItemName.setForeground(new java.awt.Color(76, 76, 76));
        lbItemName.setText("Item Name");

        lbBrand.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        lbBrand.setForeground(new java.awt.Color(76, 76, 76));
        lbBrand.setText("Brand");

        lbPrice.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        lbPrice.setForeground(new java.awt.Color(76, 76, 76));
        lbPrice.setText("$0.00");

        txtDescription.setBorder(null);
        txtDescription.setFont(new java.awt.Font("Helvetica Neue", 2, 12)); // NOI18N
        txtDescription.setForeground(new java.awt.Color(153, 153, 153));
        txtDescription.setFocusable(false);
        txtDescription.setMargin(new java.awt.Insets(2, 2, 2, 2));
        txtDescription.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(76, 76, 76));
        jLabel1.setText("Quantity:");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(76, 76, 76));
        jLabel2.setText("7");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pic, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lbItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbBrand))
                    .addComponent(txtDescription, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbPrice)))
                .addGap(0, 26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbItemName)
                    .addComponent(lbBrand))
                .addGap(18, 18, 18)
                .addComponent(txtDescription)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPrice)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbBrand;
    private javax.swing.JLabel lbItemName;
    private javax.swing.JLabel lbPrice;
    private com.snapshop.swing.PictureBox pic;
    private javax.swing.JTextPane txtDescription;
    // End of variables declaration//GEN-END:variables
}