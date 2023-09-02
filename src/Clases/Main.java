/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Clases;

import Paneles_principales.Principal;
import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 *
 * @author lautaro
 */
public class Main {
    public static Conexion conexion = new Conexion();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException, UnknownHostException {
        try {
            
            conexion.Conectar();

        } catch (java.lang.NullPointerException e) {
            System.out.println("problemas");
            
        }
        
                java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
        
    }
    
}
