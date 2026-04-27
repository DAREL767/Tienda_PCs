/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ORIANA BONILLA
 */
public class DatabaseConecction {
    private static String DB_URL  = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static String USER = "system";
    private static String PASS = "ORACLE";
    
    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Conexion establecida");
        } catch (ClassNotFoundException e) {
            System.err.println("Oracle JDBC no encontrado!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Conexion fallida!");
            e.printStackTrace();
        }

        return connection;
    }
    
}
