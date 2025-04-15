/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.northwind_gui;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import javax.swing.JOptionPane;
/**
 *
 * @author marcelstoltz
 */
public class popup_products extends javax.swing.JDialog {

    /**
     * Creates new form popup_products
     * @param parent
     */
    public popup_products(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize); 
        loadComboBoxData(); 

        save_btn.addActionListener(evt -> loadComboBoxesAndInsertProductData()); // Use the combined function

        cancel_button.addActionListener(evt -> dispose()); // Close the popup
        
             this.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                if (getParent() instanceof DB_AppForm) { // Assuming your parent form class is Northwind_GUI
                    ((DB_AppForm) getParent()).loadProductsTableData();
                }
            }
        });
        
    }
    
        private void loadComboBoxData() {
        try (Connection conn = DB_Con.getConnection();
             Statement stmt = conn.createStatement()) {


            String categoryQuery = "SELECT DISTINCT category FROM products ORDER BY category";
            ResultSet categoryRs = stmt.executeQuery(categoryQuery);
            category_cmb.removeAllItems();
            while (categoryRs.next()) {
                category_cmb.addItem(categoryRs.getString("category"));
            }

            String supplierQuery = "SELECT DISTINCT id FROM suppliers";
            ResultSet supplierRs = stmt.executeQuery(supplierQuery);
            cmbo_supplier.removeAllItems();
            while (supplierRs.next()) {
                cmbo_supplier.addItem(supplierRs.getString("id"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading combo box data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
            private void loadComboBoxesAndInsertProductData() {
        // Input Validation
        String productCode = product_code_txt.getText().trim();
        String productName = product_name_txt.getText().trim();
        String description = description_txt.getText().trim();
        String quantityPerUnit = qty_txt.getText().trim();
        String category = (String) category_cmb.getSelectedItem();
        String supplier = (String) cmbo_supplier.getSelectedItem(); // You might need to fetch the actual Supplier ID

        boolean discontinued = chck_disc.isSelected();

        
        // --- Validation Checks ---
        if (productCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product Code cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (productCode.length() > 25) {
            JOptionPane.showMessageDialog(this, "Product Code cannot exceed 25 characters.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (productName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product Name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (productName.length() > 50) {
            JOptionPane.showMessageDialog(this, "Product Name cannot exceed 50 characters.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (quantityPerUnit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Quantity Per Unit cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (quantityPerUnit.length() > 50) {
            JOptionPane.showMessageDialog(this, "Quantity Per Unit cannot exceed 50 characters.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (category == null || category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a Category.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (supplier == null || supplier.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a Supplier.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
           
            double standardCost = ((Number) stnd_cost.getValue()).doubleValue();
            double listPrice = ((Number) lst_price.getValue()).doubleValue();
            int reorderLevel = ((Number) reorder_level.getValue()).intValue();
            int targetLevel = ((Number) trgt_level.getValue()).intValue();
            int minReorderQuantity = ((Number) minimum_reorder_qty.getValue()).intValue();

            // --- Database Insertion ---
            try (Connection conn = DB_Con.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(
                         "INSERT INTO products (product_code, product_name, description, category, quantity_per_unit, standard_cost, list_price, reorder_level, target_level, minimum_reorder_quantity, discontinued, supplier_ids) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                pstmt.setString(1, productCode);
                pstmt.setString(2, productName);
                pstmt.setString(3, description);
                pstmt.setString(4, category); // Assuming category name is stored directly
                pstmt.setString(5, quantityPerUnit);
                pstmt.setDouble(6, standardCost);
                pstmt.setDouble(7, listPrice);
                pstmt.setInt(8, reorderLevel);
                pstmt.setInt(9, targetLevel);
                pstmt.setInt(10, minReorderQuantity);
                pstmt.setBoolean(11, discontinued);
                pstmt.setString(12, supplier); // Assuming supplier name is used as supplier_ids

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Close the popup
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add product.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error during insertion: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }

        }catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Please ensure all numeric fields are filled correctly.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        // Optionally, you can highlight the specific field that caused the error
        
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        product_code_txt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        product_name_txt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        description_txt = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        stnd_cost = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        lst_price = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        reorder_level = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        trgt_level = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        qty_txt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        chck_disc = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        minimum_reorder_qty = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        cmbo_supplier = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        category_cmb = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cancel_button = new javax.swing.JButton();
        save_btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add Product");
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.GridLayout(14, 2, 5, 3));

        jLabel2.setText("Product Code:");
        jPanel2.add(jLabel2);
        jPanel2.add(product_code_txt);

        jLabel3.setText("Product Name:");
        jPanel2.add(jLabel3);
        jPanel2.add(product_name_txt);

        jLabel4.setText("Description:");
        jPanel2.add(jLabel4);

        description_txt.setColumns(20);
        description_txt.setRows(5);
        jScrollPane1.setViewportView(description_txt);

        jPanel2.add(jScrollPane1);

        jLabel5.setText("Standard Cost:");
        jPanel2.add(jLabel5);

        stnd_cost.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.0000"))));
        jPanel2.add(stnd_cost);

        jLabel6.setText("List Price:");
        jPanel2.add(jLabel6);

        lst_price.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.0000"))));
        jPanel2.add(lst_price);

        jLabel7.setText("Reorder Level:");
        jPanel2.add(jLabel7);

        reorder_level.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jPanel2.add(reorder_level);

        jLabel8.setText("Target Level:");
        jPanel2.add(jLabel8);

        trgt_level.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jPanel2.add(trgt_level);

        jLabel15.setText("Quantity Per Unit:");
        jPanel2.add(jLabel15);
        jPanel2.add(qty_txt);

        jLabel9.setText("Discontinued:");
        jPanel2.add(jLabel9);
        jPanel2.add(chck_disc);

        jLabel10.setText("Minimum Reorder Quantity:");
        jPanel2.add(jLabel10);

        minimum_reorder_qty.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jPanel2.add(minimum_reorder_qty);

        jLabel11.setText("Supplier:");
        jPanel2.add(jLabel11);

        cmbo_supplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbo_supplier);

        jLabel12.setText("Category:");
        jPanel2.add(jLabel12);

        category_cmb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(category_cmb);

        jLabel13.setText("Attachments:");
        jPanel2.add(jLabel13);

        jButton1.setText("Browse..");
        jPanel2.add(jButton1);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.GridLayout(1, 2));

        cancel_button.setText("Cancel");
        jPanel4.add(cancel_button);

        save_btn.setText("Save");
        jPanel4.add(save_btn);

        getContentPane().add(jPanel4, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(popup_products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(popup_products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(popup_products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(popup_products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                popup_products dialog = new popup_products(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel_button;
    private javax.swing.JComboBox<String> category_cmb;
    private javax.swing.JCheckBox chck_disc;
    private javax.swing.JComboBox<String> cmbo_supplier;
    private javax.swing.JTextArea description_txt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JFormattedTextField lst_price;
    private javax.swing.JFormattedTextField minimum_reorder_qty;
    private javax.swing.JTextField product_code_txt;
    private javax.swing.JTextField product_name_txt;
    private javax.swing.JTextField qty_txt;
    private javax.swing.JFormattedTextField reorder_level;
    private javax.swing.JButton save_btn;
    private javax.swing.JFormattedTextField stnd_cost;
    private javax.swing.JFormattedTextField trgt_level;
    // End of variables declaration//GEN-END:variables
}
