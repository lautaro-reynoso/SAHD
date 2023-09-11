package Clases;


import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lautaro
 */
public class Conexion {
    
    public String user = "sa";
    public String pass = "123";
    public java.sql.Statement s;
    public ResultSet resultado;
    public Connection conexion = null;

     public void Conectar() throws SQLException, ClassNotFoundException, UnknownHostException {

        try {
            

            String ip_local = Inet4Address.getLocalHost().getHostAddress().replaceAll("\\.\\d+$", "");
            DriverManager.setLoginTimeout(1);

            conexion = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=db_corralon;encrypt=true;trustServerCertificate=true;", user, pass);

            if (conexion != null) {
                System.out.println("Conexion a base de datos:  ... Ok");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        this.s = conexion.createStatement();

    }
     
      public void Desconectar() {
        try {
            conexion.close();
            conexion = null;
        } catch (Exception e) {
            System.out.println("Problema para cerrar la Conexión a la base de datos ");
        }
    }

    public java.sql.ResultSet EjecutarConsultaSQL(String sql) {

        try {
            resultado = s.executeQuery(sql);

        } catch (SQLException ex) {
            return null;
        }
        return resultado;
    }

    public int EjecutarOperacionSQL(String sql) {
        int respuesta = 0;
        try {
            respuesta = this.s.executeUpdate(sql);
            if (respuesta == 1) {
                System.out.println("Registro Guardado");
            } else {
                System.out.println("Ocurrio un problema al agregar el registro");

            }
        } catch (SQLException ex) {
            // Mostramos toda la informacion sobre el error disponible
            System.out.println("Error: SQLException");
            while (ex != null) {
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("Mensaje:  " + ex.getMessage());
                System.out.println("ErrorCode:   " + ex.getErrorCode());
                ex = ex.getNextException();
            }
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado:    " + e.getMessage());
        }
        return respuesta;
    }

    int EjecutarOperacion(String sql) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
