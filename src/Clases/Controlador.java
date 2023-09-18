/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;
import Clases.Producto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * @author lautaro
 */
public class Controlador {
    
    
   Peticiones peticiones = new Peticiones();
           
   public int seteariva () throws SQLException{
       ResultSet res;
       
       res = peticiones.seteariva();
       if(res.next()){
           Main.iva = res.getFloat("iva");
           return 1;//exito
       }else
           return 0;//no exito
       
       
   }
}
