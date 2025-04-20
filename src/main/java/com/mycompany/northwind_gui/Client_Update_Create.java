/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.northwind_gui;


import java.awt.Toolkit;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author marcelstoltz, johanneman
 */
public class Client_Update_Create extends javax.swing.JFrame {

    /**
     * Creates new form Client_Update_Create
     */
    public Client_Update_Create() {
        initComponents();
           Dimension zz = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Create New Client");
    
        this.setSize(zz);
    }
    
    private int clientId = -1;
    private boolean isUpdate = false;
  
    
    public Client_Update_Create(int clientId){
        initComponents();
        setTitle("Update Client");
        this.clientId = clientId;
        this.isUpdate = true;
        loadClientData();
    }
    
    private void loadClientData() {
        try (Connection conn = DB_Con.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM customers WHERE id = ?")) {
            
            pstmt.setInt(1, clientId);
            ResultSet res = pstmt.executeQuery();
            
            if (res.next()) {
                txtCompany.setText(res.getString("company"));
                txtLastName.setText(res.getString("last_name"));
                txtFirstName.setText(res.getString("first_name"));
                txtEmail.setText(res.getString("email_address"));
                txtJobTitle.setText(res.getString("job_title"));
                txtBusinessPhone.setText(res.getString("business_phone"));
                txtHomePhone.setText(res.getString("home_phone"));
                txtMobilePhone.setText(res.getString("mobile_phone"));
                txtFaxNumber.setText(res.getString("fax_number"));
                txtAddress.setText(res.getString("address"));
                txtCity.setText(res.getString("city"));
                txtStateProvince.setText(res.getString("state_province"));
                txtZipPostalCode.setText(res.getString("zip_postal_code"));
                txtCountryRegion.setText(res.getString("country_region"));
                txtWebPage.setText(res.getString("web_page"));
                txtNotes.setText(res.getString("notes"));
            }
            
        }catch (SQLException err){
            err.printStackTrace();
            JOptionPane.showMessageDialog(this, "Client data no load :( : " + err.getMessage(),
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void saveClient(){
        try (Connection conn = DB_Con.getConnection()){
            PreparedStatement pstmt;
            
            if (isUpdate){
                pstmt = conn.prepareStatement(
                    "UPDATE customers SET company = ?, last_name = ?, first_name = ?, " +
                    "email_address = ?, job_title = ?, business_phone = ?, home_phone = ?, " +
                    "mobile_phone = ?, fax_number = ?, address = ?, city = ?, " +
                    "state_province = ?, zip_postal_code = ?, country_region = ?, " +
                    "web_page = ?, notes = ?, attachments = null WHERE id = ?");
            }else{
                pstmt = conn.prepareStatement(
                    "INSERT INTO customers (company, last_name, first_name, email_address, " +
                    "job_title, business_phone, home_phone, mobile_phone, fax_number, " +
                    "address, city, state_province, zip_postal_code, country_region, " +
                    "web_page, notes, attachments) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, null)");
            }
            
            pstmt.setString(1, txtCompany.getText());
            pstmt.setString(2, txtLastName.getText());
            pstmt.setString(3, txtFirstName.getText());
            pstmt.setString(4, txtEmail.getText());
            pstmt.setString(5, txtJobTitle.getText());
            pstmt.setString(6, txtBusinessPhone.getText());
            pstmt.setString(7, txtHomePhone.getText());
            pstmt.setString(8, txtMobilePhone.getText());
            pstmt.setString(9, txtFaxNumber.getText());
            pstmt.setString(10, txtAddress.getText());
            pstmt.setString(11, txtCity.getText());
            pstmt.setString(12, txtStateProvince.getText());
            pstmt.setString(13, txtZipPostalCode.getText());
            pstmt.setString(14, txtCountryRegion.getText());
            pstmt.setString(15, txtWebPage.getText());
            pstmt.setString(16, txtNotes.getText());
            
            if (isUpdate) {
                pstmt.setInt(17, clientId);
            }
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                JOptionPane.showMessageDialog(this, 
                    isUpdate ? "Client updated successfully." : "Client created successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    isUpdate ? "Failed to update client." : "Failed to create client.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException err) {
            err.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error " + (isUpdate ? "Updating" : "Creating") + " Client: " + err.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCompany = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtJobTitle = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtBusinessPhone = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtHomePhone = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtMobilePhone = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtFaxNumber = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCity = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtStateProvince = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtZipPostalCode = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtCountryRegion = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtWebPage = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNotes = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtAttachments = new javax.swing.JTextField();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Create/Edit Client");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        btnSave.setText("Create/Edit");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancel);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel3.setLayout(new java.awt.GridLayout(17, 2, 5, 5));

        jLabel2.setText("Company:");
        jPanel3.add(jLabel2);
        jPanel3.add(txtCompany);

        jLabel3.setText("Last Name:");
        jPanel3.add(jLabel3);
        jPanel3.add(txtLastName);

        jLabel4.setText("First Name:");
        jPanel3.add(jLabel4);
        jPanel3.add(txtFirstName);

        jLabel5.setText("Email Address:");
        jPanel3.add(jLabel5);
        jPanel3.add(txtEmail);

        jLabel6.setText("Job Title:");
        jPanel3.add(jLabel6);
        jPanel3.add(txtJobTitle);

        jLabel7.setText("Business Phone:");
        jPanel3.add(jLabel7);
        jPanel3.add(txtBusinessPhone);

        jLabel8.setText("Home Phone:");
        jPanel3.add(jLabel8);
        jPanel3.add(txtHomePhone);

        jLabel9.setText("Mobile Phone:");
        jPanel3.add(jLabel9);
        jPanel3.add(txtMobilePhone);

        jLabel10.setText("Fax Number:");
        jPanel3.add(jLabel10);
        jPanel3.add(txtFaxNumber);

        jLabel11.setText("Address:");
        jPanel3.add(jLabel11);

        jScrollPane1.setViewportView(txtAddress);

        jPanel3.add(jScrollPane1);

        jLabel12.setText("City:");
        jPanel3.add(jLabel12);
        jPanel3.add(txtCity);

        jLabel13.setText("State Provinces:");
        jPanel3.add(jLabel13);
        jPanel3.add(txtStateProvince);

        jLabel14.setText("Zip Postal Code:");
        jPanel3.add(jLabel14);
        jPanel3.add(txtZipPostalCode);

        jLabel15.setText("Country Of Origin:");
        jPanel3.add(jLabel15);
        jPanel3.add(txtCountryRegion);

        jLabel16.setText("Web Page:");
        jPanel3.add(jLabel16);

        jScrollPane2.setViewportView(txtWebPage);

        jPanel3.add(jScrollPane2);

        jLabel17.setText("Notes:");
        jPanel3.add(jLabel17);

        jScrollPane3.setViewportView(txtNotes);

        jPanel3.add(jScrollPane3);

        jLabel18.setText("Attachments:");
        jPanel3.add(jLabel18);

        txtAttachments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAttachmentsActionPerformed(evt);
            }
        });
        jPanel3.add(txtAttachments);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAttachmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAttachmentsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAttachmentsActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveClient();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

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
            java.util.logging.Logger.getLogger(Client_Update_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client_Update_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client_Update_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client_Update_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client_Update_Create().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtAttachments;
    private javax.swing.JTextField txtBusinessPhone;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtCompany;
    private javax.swing.JTextField txtCountryRegion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFaxNumber;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtHomePhone;
    private javax.swing.JTextField txtJobTitle;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtMobilePhone;
    private javax.swing.JTextField txtNotes;
    private javax.swing.JTextField txtStateProvince;
    private javax.swing.JTextField txtWebPage;
    private javax.swing.JTextField txtZipPostalCode;
    // End of variables declaration//GEN-END:variables
}
