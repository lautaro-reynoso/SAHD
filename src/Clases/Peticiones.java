/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import com.mysql.cj.protocol.Resultset;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
/**
 *
 * @author lautaro
 */
public class Peticiones {
    
    
    public ResultSet consultarinventario (){
        
        String sql = "Select * From productos";
        
        return Main.conexion.EjecutarConsultaSQL(sql);
        
    }
    
    public void modificaInventario(String cantidad,String descripcion){
         Main.conexion.EjecutarOperacionSQL("UPDATE productos SET cantidad = cantidad - " +cantidad +" WHERE descripcion = '"+ descripcion +"'");
        }
    
    public ResultSet buscainventario (String str){
        
        String sql = "SELECT * FROM productos WHERE descripcion LIKE '" +str+"%'";

       return Main.conexion.EjecutarConsultaSQL(sql);
        
    }
    
    public int cargarproducto (String codigo, String descripcion, int cantidad, float precio){
        
        String sql = "INSERT INTO productos (descripcion, cantidad, precio, codigo)" + "VALUES('" + descripcion + "','" + cantidad + "','" + precio + "','" + codigo + "')";
        
        return Main.conexion.EjecutarOperacionSQL(sql);
    }
    
    public ResultSet login (String user, String pass){
        
        String sql = "SELECT * FROM Usuarios WHERE usuario = '" + user + "' AND contrasenia = '" + pass + "'";

        return Main.conexion.EjecutarConsultaSQL(sql);
    }
           
}
