/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.snapshop.main;

import com.snapshop.controller.PlatformController;
import com.snapshop.event.AddToCartListener;
import com.snapshop.event.CheckoutListener;
import com.snapshop.event.EventItem;
import com.snapshop.form.FormCheckout;
import com.snapshop.form.FormHome;
import com.snapshop.form.HomeHeader;
import com.snapshop.form.MyCart;
import com.snapshop.form.MyOrders;
import com.snapshop.model.Cart;
import com.snapshop.model.Customer;
import com.snapshop.model.ModelItem;
import com.snapshop.model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import com.snapshop.event.SwitchMainPanelListener;

/**
 *
 * @author lucas
 */
public class Dashboard extends javax.swing.JFrame implements AddToCartListener, SwitchMainPanelListener, CheckoutListener {

    private PlatformController controller;
    private User currentUser;
    private FormHome home;
    private HomeHeader homehd;
    private ModelItem itemSelected;
    private Cart cart;
    private MyCart myCart;
    private MyOrders myOrders;

    private int customerId;
    private int cartId;

    /**
     * Creates new form Dashboard
     */
    public Dashboard() {
        initComponents();
        controller = PlatformController.getInstance();
        currentUser = (Customer) controller.getCurrentUser();
        setBackground(new Color(0, 0, 0, 0));

        Customer currentCustomer = (Customer) currentUser;
        customerId = currentCustomer.getCustomerId();
        cartId = controller.getCartId();
        init();
    }

    @Override
    public void onAddToCart(ModelItem item, int quantity) {
        // Fetch the latest inventory from the database
        int latestInventory = controller.fetchLatestInventory(item.getItemId());

        if (latestInventory < quantity) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Not enough inventory available. Current inventory: " + latestInventory,
                    "Warning",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Update the item's inventory to the latest value
        item.setInventory(latestInventory);

        // Proceed to add the item to the cart
        cart.addItem(item, quantity);

        javax.swing.JOptionPane.showMessageDialog(this,
                "Item added to cart successfully!",
                "Success",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onCheckout() {
        mainPanel.removeAll(); // Remove all components from mainPanel
        FormCheckout checkoutPanel = new FormCheckout(); // Create the FormCheckout instance
        mainPanel.setLayout(new BorderLayout()); // Set the layout for the panel
        mainPanel.add(checkoutPanel); // Add the new FormCheckout panel
        mainPanel.revalidate(); // Revalidate the panel to refresh the layout
        mainPanel.repaint(); // Repaint the panel to reflect changes
    }

    @Override
    public void showCartPanel() {
        // Remove current panel and show MyCart panel
        System.out.println("Switching to MyCart panel...");
        mainPanel.removeAll();
        myCart = new MyCart(this);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(myCart);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    @Override
    public void showMyOrdersPanel() {
        // Remove current panel and show MyCart panel
        System.out.println("Switching to MyCart panel...");
        mainPanel.removeAll();
        myOrders = new MyOrders();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(myOrders);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    @Override
    public void showHomePanel() {
        System.out.println("Switching to Home panel...");
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(home); // Add the FormHome panel back
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void init() {
        // set headerPanel for customer
        homehd = new HomeHeader(this, background1);
        homehd.setMainPanelListener(this);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(homehd);
        // set mainPanel for customer
        home = new FormHome(this);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(home);

        // load product data
        loadData();
        // For storing added items
        cart = new Cart(customerId, cartId);
    }

    private void loadData() {
        home.setEvent(new EventItem() {
            @Override
            public void itemClick(Component com, ModelItem item) {
                home.setSelected(com);
                home.showItem(item);
            }
        });
        List<ModelItem> items = controller.getAllItems();

        for (ModelItem item : items) {
            home.addItem(item);
        }
    }

    public FormHome getHome() {
        return home;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new com.snapshop.swing.Background();
        headerPanel = new javax.swing.JPanel();
        mainPanel = new com.snapshop.swing.MainPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        headerPanel.setOpaque(false);

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1038, Short.MAX_VALUE)
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 658, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Dashboard().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.snapshop.swing.Background background1;
    private javax.swing.JPanel headerPanel;
    private com.snapshop.swing.MainPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
