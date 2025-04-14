/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.northwind_gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author marcelstoltz
 */
public class DB_Con {
    
     public static Connection getConnection() throws SQLException {
        String dbProto = System.getenv("dvdrentalDB_PROTO");
        String dbHost = System.getenv("dvdrentalDB_HOST");
        String dbPort = System.getenv("dvdrentalDB_PORT");
        String dbName = System.getenv("dvdrentalDB_NAME");
        String dbUsername = System.getenv("dvdrentalDB_USERNAME");
        String dbPassword = System.getenv("dvdrentalDB_PASSWORD");
        String jdbcUrl = String.format("jdbc:%s://%s:%s/%s", dbProto, dbHost, dbPort, dbName);

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MariaDB JDBC driver not found.", e);
        }
    }

}
