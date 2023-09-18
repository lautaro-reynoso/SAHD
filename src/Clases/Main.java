/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Clases;

import Paneles_principales.Login;
import Paneles_principales.Principal;
import java.net.UnknownHostException;
import com.formdev.flatlaf.FlatLightLaf;
import java.sql.SQLException;

/**
 *
 * @author lautaro
 */
public class Main {
    public static Conexion conexion = new Conexion();
    public static float iva;
    public static int provilegio;
    public static Controlador controlador = new Controlador();
    
    
    
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException, UnknownHostException {
        FlatLightLaf.setup();
        try {
            
            conexion.Conectar();
            controlador.seteariva();
            
            

        } catch (java.lang.NullPointerException e) {
            System.out.println("problemas");
            
        }
        
                java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
        
    }
    
}
