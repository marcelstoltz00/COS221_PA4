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
        // Apply UIManager properties to customize the JTree appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Optional: Set a Look and Feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Customize JTree properties using UIManager
        UIManager.put("Tree.rowHeight", 30); // Increase row height
        UIManager.put("Tree.selectionBackground", new Color(200, 230, 255)); // Light blue selection
        UIManager.put("Tree.selectionForeground", Color.BLACK);
        UIManager.put("Tree.lineStyle", "Angled"); // Use angled lines
        UIManager.put("Tree.hash", Color.BLUE); // Set the color of the lines
        UIManager.put("Tree.background", Color.WHITE); // Set the background color of the tree
        UIManager.put("Tree.foreground", Color.BLACK); // Set the text color of the tree
        UIManager.put("Tree.font", new Font("SansSerif", Font.PLAIN, 14)); // Set the font

        // You can set default icons if you want (replace with your icon paths)
        try {
            UIManager.put("Tree.openIcon", new ImageIcon(getClass().getResource("/icons/folder_open.png")));
            UIManager.put("Tree.closedIcon", new ImageIcon(getClass().getResource("/icons/folder_closed.png")));
            UIManager.put("Tree.leafIcon", new ImageIcon(getClass().getResource("/icons/document.png")));
        } catch (Exception e) {
            System.err.println("Error loading icons: " + e.getMessage());
            // Handle the case where icons are not found
        }


        setLayout(new java.awt.BorderLayout());
        JTree schemaTree = createDatabaseSchemaTree();
        JScrollPane scrollPane = new JScrollPane(schemaTree);
        add(scrollPane, java.awt.BorderLayout.CENTER);
    }

    private JTree createDatabaseSchemaTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Northwind Database"); // Your database name
        DefaultTreeModel treeModel = new DefaultTreeModel(root);

        try (Connection conn = DB_Con.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();

            // Get all tables
            ResultSet tables = metaData.getTables("u24566552_u24564584_northwind", null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName); // Added logging
                DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(tableName);
                root.add(tableNode);

                // Get columns for each table
                ResultSet columns = metaData.getColumns("u24566552_u24564584_northwind", null, tableName, "%");
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    System.out.println("  Column: " + columnName + " (" + columnType + ")"); // Added logging
                    tableNode.add(new DefaultMutableTreeNode(columnName + " (" + columnType + ")"));
                }
                System.out.println("--- End of Columns for Table: " + tableName + " ---"); // Added logging
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving database schema: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return new JTree(treeModel);
    }

    // You would then add an instance of this panel to your DB_AppForm
}