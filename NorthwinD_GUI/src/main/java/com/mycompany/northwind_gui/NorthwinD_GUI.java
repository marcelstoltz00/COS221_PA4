/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.northwind_gui;

/**
 *
 * @author marcelstoltz
 */
public class NorthwinD_GUI {

    public static void main(String[] args) {
                java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            
                  DB_AppForm mainForm = new DB_AppForm();//main form

   
                mainForm.setVisible(true);
               
            }
        });

    }
}


