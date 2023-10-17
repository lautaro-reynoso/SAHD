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
    
    public int NuevoUsuario(String nombre, String contrasenia, int privilegios) {

        String sql;
        sql = " INSERT INTO Usuarios (usuario,contrasenia, privilegio)"
                + "VALUES ('" + nombre + "','" + contrasenia + "','" + privilegios + "')";
        return Main.conexion.EjecutarOperacionSQL(sql);

    }
    public ResultSet consultausuarios (){
        
        String sql = "SELECT * FROM Usuarios ";

       return Main.conexion.EjecutarConsultaSQL(sql);
    }
    public ResultSet seteariva (){
        
        String sql = "SELECT * FROM iva where id = 1 ";
        
        return Main.conexion.EjecutarConsultaSQL(sql);
    }
    
    public int cargariva (float iva){
        
        String sql = "UPDATE iva SET iva = ' " + iva + " ' WHERE id = 1 ";
        
        return Main.conexion.EjecutarOperacionSQL(sql);
    }
    public int EliminarUsuario(String usuario) {
        
        String sql = "DELETE FROM Usuarios WHERE usuario = '" + usuario + "'";

        return Main.conexion.EjecutarOperacionSQL(sql);
    }
    
    public void modificaInventario(String cantidad,String descripcion){
         Main.conexion.EjecutarOperacionSQL("UPDATE productos SET cantidad = cantidad - " +cantidad +" WHERE descripcion = '"+ descripcion +"'");
        }
    
    public int eliminarproducto (String codigo){
        
        String sql = "DELETE FROM productos WHERE codigo = '" + codigo + "'";
        
        return Main.conexion.EjecutarOperacionSQL(sql);
        
    }
    public ResultSet buscainventario (String str){
        
        String sql = "SELECT * FROM productos WHERE descripcion LIKE '" +str+"%'";

       return Main.conexion.EjecutarConsultaSQL(sql);
        
    }
    public ResultSet buscarcodigo (String codigo){
        
        String sql = "SELECT * FROM productos WHERE codigo = '" + codigo + "'";
                
        return Main.conexion.EjecutarConsultaSQL(sql);
    }
    
    public ResultSet inventario_stock(int stock){
        
        String sql = "SELECT * FROM productos WHERE cantidad <= '" + stock + "'";
        
        return Main.conexion.EjecutarConsultaSQL(sql);
    }
    public int modificarproducto (String codigoantiguo, String codigo, String descripcion, int cantidad, float precio){
        
        String sql = "UPDATE productos SET codigo ='"+ codigo + " ', descripcion ='"+ descripcion + "', cantidad = '"+ cantidad + " ', precio = ' " + precio + " ' WHERE codigo = '" + codigoantiguo + "'"; 
        
        
        return Main.conexion.EjecutarOperacionSQL(sql);
    }
    public int cargarproducto (String codigo, String descripcion, int cantidad, float precio){
        
        String sql = "INSERT INTO productos (descripcion, cantidad, precio, codigo)" + "VALUES('" + descripcion + "','" + cantidad + "','" + precio + "','" + codigo + "')";
        
        return Main.conexion.EjecutarOperacionSQL(sql);
    }
    
    public ResultSet login (String user, String pass){
        
        String sql = "SELECT * FROM Usuarios WHERE usuario = '" + user + "' AND contrasenia = '" + pass + "'";

        return Main.conexion.EjecutarConsultaSQL(sql);
    }
    
    public ResultSet CajaAbierta (String user){
        
        String sql = "SELECT * FROM caja_abierta WHERE usuario = '" + user + "'";

        return Main.conexion.EjecutarConsultaSQL(sql);
    }
           
}
