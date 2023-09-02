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
    
    
    
}
