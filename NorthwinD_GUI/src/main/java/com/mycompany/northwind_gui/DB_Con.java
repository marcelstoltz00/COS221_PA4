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
        String dbPr = System.getenv("dvdrentalDB_PROTO");
        String dbHo = System.getenv("dvdrentalDB_HOST");
        String dbPo = System.getenv("dvdrentalDB_PORT");
        String dbName = System.getenv("dvdrentalDB_NAME");
        String dbUser = System.getenv("dvdrentalDB_USERNAME");
        String dbPw = System.getenv("dvdrentalDB_PASSWORD");
        String jdb = String.format("jdbc:%s://%s:%s/%s", dbPr, dbHo, dbPo, dbName);

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(jdb, dbUser, dbPw);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MariaDB JDBC driver not found.", e);
        }
    }

}
