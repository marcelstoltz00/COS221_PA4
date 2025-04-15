/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.northwind_gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSchemaViewer extends JPanel {

    public DatabaseSchemaViewer() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

    
        UIManager.put("Tree.rowHeight", 30); 
        UIManager.put("Tree.selectionBackground", new Color(200, 230, 255)); 
        UIManager.put("Tree.selectionForeground", Color.BLACK);
        UIManager.put("Tree.lineStyle", "Angled"); 
        UIManager.put("Tree.hash", Color.BLUE); 
        UIManager.put("Tree.background", Color.WHITE); 
        UIManager.put("Tree.foreground", Color.BLACK); 
        UIManager.put("Tree.font", new Font("SansSerif", Font.PLAIN, 14)); 

    
//        try {
//            UIManager.put("Tree.openIcon", new ImageIcon(getClass().getResource("/icons/folder_open.png")));
//            UIManager.put("Tree.closedIcon", new ImageIcon(getClass().getResource("/icons/folder_closed.png")));
//            UIManager.put("Tree.leafIcon", new ImageIcon(getClass().getResource("/icons/document.png")));
//        } catch (Exception e) {
//            System.err.println("Error loading icons: " + e.getMessage());
//
//        }


        setLayout(new java.awt.BorderLayout());
        JTree schemaTree = createDatabaseSchemaTree();
        JScrollPane scrollPane = new JScrollPane(schemaTree);
        add(scrollPane, java.awt.BorderLayout.CENTER);
    }

    private JTree createDatabaseSchemaTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Northwind Database"); 
        DefaultTreeModel treeModel = new DefaultTreeModel(root);

        try (Connection conn = DB_Con.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();

 
            ResultSet tables = metaData.getTables("u24566552_u24564584_northwind", null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
             
                DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(tableName);
                root.add(tableNode);

       
                ResultSet columns = metaData.getColumns("u24566552_u24564584_northwind", null, tableName, "%");
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                 
                    tableNode.add(new DefaultMutableTreeNode(columnName + " (" + columnType + ")"));
                }
            
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving database schema: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return new JTree(treeModel);
    }


}