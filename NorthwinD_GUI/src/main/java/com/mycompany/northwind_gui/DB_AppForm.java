/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.northwind_gui;

import com.mycompany.northwind_gui.DB_Con;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author marcelstoltz
 */
public class DB_AppForm extends javax.swing.JFrame {

    /**
     * Creates new form DB_AppForm
     */
    public DB_AppForm() {
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        loadEmployeeTableData(false); // Or however you are calling this
        loadProductsTableData();
        loadWarehouseReportTableData();
        JPanel schemaPanel = new DatabaseSchemaViewer();

        // Set the layout for panel_home (if not already done in initComponents)
        panel_home.setLayout(new java.awt.BorderLayout());

        // Remove any existing components from panel_home
        panel_home.removeAll();

        // Add the schemaPanel to panel_home
        panel_home.add(schemaPanel, java.awt.BorderLayout.CENTER);

        // Update the UI to reflect the changes
        panel_home.revalidate();
        panel_home.repaint();

    }

    public void loadProductsTableData() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new Object[]{
            "Product ID", "Product Code", "Product Name", "Description", "Category",
            "Quantity Per Unit", "Standard Cost", "List Price", "Reorder Level",
            "Target Level", "Minimum Reorder Quantity", "Discontinued", "Supplier IDs", "Attachments"
        });

        try (Connection conn = DB_Con.getConnection(); Statement stmt = conn.createStatement()) {

            ResultSet rs = null;
            String query = "SELECT id, product_code, product_name, description, category, quantity_per_unit, standard_cost, list_price, reorder_level, target_level, minimum_reorder_quantity, discontinued, supplier_ids, attachments FROM products"; // Selecting all relevant columns from the products table

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("product_code"),
                    rs.getString("product_name"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getString("quantity_per_unit"),
                    rs.getDouble("standard_cost"),
                    rs.getDouble("list_price"),
                    rs.getInt("reorder_level"),
                    rs.getInt("target_level"),
                    rs.getInt("minimum_reorder_quantity"),
                    rs.getBoolean("discontinued"),
                    rs.getString("supplier_ids"),
                    rs.getObject("attachments")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading product data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void loadWarehouseReportTableData() {
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new Object[]{
            "Warehouse Name", "Product Category", "Number of Products"
        });

        try (Connection conn = DB_Con.getConnection(); Statement stmt = conn.createStatement()) {

            ResultSet rs = null;
            String query = "SELECT "
                    + "    CASE p.category "
                    + "        WHEN 'Baked Goods & Mixes' THEN 'Bakery Storage' "
                    + "        WHEN 'Beverages' THEN 'Beverage Warehouse' "
                    + "        WHEN 'Candy' THEN 'Sweet Treats Depot' "
                    + "        WHEN 'Canned Fruit & Vegetables' THEN 'Canned Goods Warehouse' "
                    + "        WHEN 'Canned Meat' THEN 'Preserved Meats Storage' "
                    + "        WHEN 'Cereal' THEN 'Breakfast Foods Section' "
                    + "        WHEN 'Chips, Snacks' THEN 'Snack Aisle Warehouse' "
                    + "        WHEN 'Condiments' THEN 'Sauces & Condiments Storage' "
                    + "        WHEN 'Dairy products' THEN 'Dairy Fridge Warehouse' "
                    + "        WHEN 'Dried Fruit & Nuts' THEN 'Dried Goods Storage' "
                    + "        WHEN 'Grains' THEN 'Grains & Pulses Warehouse' "
                    + "        WHEN 'Jams, Preserves' THEN 'Jams & Preserves Storage' "
                    + "        WHEN 'Oil' THEN 'Oils & Vinegars Storage' "
                    + "        WHEN 'Pasta' THEN 'Pasta & Noodles Section' "
                    + "        WHEN 'Sauces' THEN 'Sauces Storage' "
                    + "        WHEN 'Soups' THEN 'Soup Stock Warehouse' "
                    + "        WHEN 'Uncategorized' THEN 'General Storage' "
                    + "        ELSE CONCAT(p.category, ' Storage') "
                    + // Dynamic storage name
                    "    END AS warehouse_name, "
                    + "    p.category AS category_name, "
                    + "    COUNT(p.id) AS number_of_products "
                    + "FROM "
                    + "    products p "
                    + "GROUP BY "
                    + "    warehouse_name, "
                    + "    category_name "
                    + "ORDER BY "
                    + "    warehouse_name, "
                    + "    category_name;";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("warehouse_name"),
                    rs.getString("category_name"),
                    rs.getInt("number_of_products")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading warehouse report data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadEmployeeTableData(boolean search) { // Added a boolean parameter 'search'
        DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new Object[]{
            "First Name", "Last Name", "Email", "Job Title", "Address Number", "Address Line 1",
            "City", "Region", "Home Phone", "Fax Number", "Postal Code",
            "Business Phone", "Country", "Notes", "Active"
        });

        try (Connection conn = DB_Con.getConnection(); Statement stmt = conn.createStatement()) {

            ResultSet rs = null;
            String query = "SELECT first_name, last_name, email_address, job_title, address, city, state_province, home_phone, fax_number, zip_postal_code, business_phone, country_region, notes FROM employees";

            if (search) {
                String searchText = jTextField2.getText().trim();
                if (!searchText.isEmpty()) {

                    query += " WHERE first_name LIKE '%" + searchText + "%' OR "
                            + "last_name LIKE '%" + searchText + "%' OR "
                            + "email_address LIKE '%" + searchText + "%' OR "
                            + "job_title LIKE '%" + searchText + "%' OR "
                            + "address LIKE '%" + searchText + "%' OR " // Search the combined address field
                            + "city LIKE '%" + searchText + "%' OR "
                            + "state_province LIKE '%" + searchText + "%' OR "
                            + "home_phone LIKE '%" + searchText + "%' OR "
                            + "fax_number LIKE '%" + searchText + "%' OR "
                            + "zip_postal_code LIKE '%" + searchText + "%' OR "
                            + "business_phone LIKE '%" + searchText + "%' OR "
                            + "country_region LIKE '%" + searchText + "%' OR "
                            + "notes LIKE '%" + searchText + "%'";

                }
            }
            rs = stmt.executeQuery(query); // Execute the constructed query

            while (rs.next()) {
                String fullAddress = rs.getString("address");
                String addressNumber = "";
                String addressLine1 = "";

                if (fullAddress != null) {
                    int firstSpaceIndex = fullAddress.indexOf(" ");
                    if (firstSpaceIndex != -1) {
                        addressNumber = fullAddress.substring(0, firstSpaceIndex).trim();
                        addressLine1 = fullAddress.substring(firstSpaceIndex + 1).trim();
                    } else {
                        addressNumber = fullAddress.trim();
                        addressLine1 = "";
                    }
                }

                model.addRow(new Object[]{
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email_address"),
                    rs.getString("job_title"),
                    addressNumber,
                    addressLine1,
                    rs.getString("city"),
                    rs.getString("state_province"),
                    rs.getString("home_phone"),
                    rs.getString("fax_number"),
                    rs.getString("zip_postal_code"),
                    rs.getString("business_phone"),
                    rs.getString("country_region"),
                    rs.getString("notes"),
                    "Yes"
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel6 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        Main_Pane = new javax.swing.JTabbedPane();
        panel_home = new javax.swing.JPanel();
        panel_employee = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        panel_products = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        panel_reports = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        panel_notifications = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();

        jTextField1.setText("jTextField1");

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        jLabel5.setText("jLabel5");

        jButton6.setText("jButton6");

        jButton8.setText("jButton8");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main_Form");
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        Main_Pane.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                Main_PaneComponentShown(evt);
            }
        });

        panel_home.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                panel_homeComponentShown(evt);
            }
        });
        Main_Pane.addTab("Home", panel_home);

        panel_employee.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                panel_employeeComponentShown(evt);
            }
        });
        panel_employee.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setLabelFor(jComboBox1);
        jLabel3.setText("Employee Filter");
        jPanel4.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        jTextField2.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextField2InputMethodTextChanged(evt);
            }
        });
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField2, java.awt.BorderLayout.CENTER);

        jLabel6.setText("Search:");
        jPanel4.add(jLabel6, java.awt.BorderLayout.LINE_START);

        jButton5.setText("Search/Refresh");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5, java.awt.BorderLayout.LINE_END);

        panel_employee.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        panel_employee.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        Main_Pane.addTab("Employee", panel_employee);

        panel_products.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                panel_productsComponentShown(evt);
            }
        });
        panel_products.setLayout(new java.awt.BorderLayout());

        jButton1.setText("Add Product");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton7.setText("Refresh");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7);

        panel_products.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        panel_products.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        Main_Pane.addTab("Products", panel_products);

        panel_reports.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                panel_reportsComponentShown(evt);
            }
        });
        panel_reports.setLayout(new java.awt.BorderLayout());

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        panel_reports.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel7.setLayout(new java.awt.CardLayout());

        jButton9.setText("Refresh");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton9, "card2");

        panel_reports.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        Main_Pane.addTab("Reports", panel_reports);

        panel_notifications.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel4.setText("Search Clients:");
        jPanel2.add(jLabel4, java.awt.BorderLayout.LINE_START);

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField3, java.awt.BorderLayout.CENTER);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Current Clients","Past Clients","All Clients" }));
        jPanel2.add(jComboBox1, java.awt.BorderLayout.PAGE_END);

        panel_notifications.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jButton2.setText("Update Client");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        jButton3.setText("Delete Client");
        jPanel3.add(jButton3);

        jButton4.setText("Create New Client");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4);

        panel_notifications.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel5.setLayout(new java.awt.CardLayout());

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        jPanel5.add(jScrollPane4, "card2");

        panel_notifications.add(jPanel5, java.awt.BorderLayout.CENTER);

        Main_Pane.addTab("Notifications", panel_notifications);

        getContentPane().add(Main_Pane);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        popup_products dialog = new popup_products(this, true); //popup page 

        dialog.setLocationRelativeTo(this);

        dialog.setVisible(true);

        // loadProductTableData(); // Example method name
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Client_Update_Create dialog = new Client_Update_Create(); //popup page 

        dialog.setLocationRelativeTo(this);

        dialog.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Client_Update_Create dialog = new Client_Update_Create();

        dialog.setLocationRelativeTo(this);

        dialog.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField2InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField2InputMethodTextChanged

    }//GEN-LAST:event_jTextField2InputMethodTextChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        loadEmployeeTableData(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        loadProductsTableData();

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        loadWarehouseReportTableData();         // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void Main_PaneComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_Main_PaneComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_Main_PaneComponentShown

    private void panel_homeComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_panel_homeComponentShown
        // TODO add your handling code here:
        JPanel schemaPanel = new DatabaseSchemaViewer();

        // Set the layout for panel_home (if not already done in initComponents)
        panel_home.setLayout(new java.awt.BorderLayout());

        // Remove any existing components from panel_home
        panel_home.removeAll();

        // Add the schemaPanel to panel_home
        panel_home.add(schemaPanel, java.awt.BorderLayout.CENTER);

        // Update the UI to reflect the changes
        panel_home.revalidate();
        panel_home.repaint();

    }//GEN-LAST:event_panel_homeComponentShown

    private void panel_employeeComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_panel_employeeComponentShown
        // TODO add your handling code here:
        this.loadEmployeeTableData(false);
    }//GEN-LAST:event_panel_employeeComponentShown

    private void panel_productsComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_panel_productsComponentShown
        this.loadProductsTableData();        // TODO add your handling code here:
    }//GEN-LAST:event_panel_productsComponentShown

    private void panel_reportsComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_panel_reportsComponentShown
        this.loadWarehouseReportTableData();

    }//GEN-LAST:event_panel_reportsComponentShown

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
            java.util.logging.Logger.getLogger(DB_AppForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DB_AppForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DB_AppForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DB_AppForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DB_AppForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Main_Pane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JPanel panel_employee;
    private javax.swing.JPanel panel_home;
    private javax.swing.JPanel panel_notifications;
    private javax.swing.JPanel panel_products;
    private javax.swing.JPanel panel_reports;
    // End of variables declaration//GEN-END:variables
}
