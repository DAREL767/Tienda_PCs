/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import com.mycompany.model.Periferico;
import conexion.DatabaseConecction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jamed
 */
public class ServicioPeriferico {

    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;

    private static void cerrarConexiones() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 

    public static boolean grabarPeriferico(Periferico peri) {
        
        String sql = "INSERT INTO PERIFERICO (ID, IDPC, NOMBRE, PRECIO, ES_GAMER, ESTADO) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            conn = DatabaseConecction.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, peri.getId());
            pstmt.setInt(2, peri.getIdPC());
            pstmt.setString(3, peri.getNombre());
            pstmt.setDouble(4, peri.getPrecio());
            pstmt.setInt(5, peri.isEsGamer() ? 1 : 0); 
            pstmt.setString(6, "A"); 
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al grabar periférico: " + ex.getMessage());
            return false;
        } finally {
            cerrarConexiones();
        }
    }

    public static Periferico buscarPorId(int pId) {
        String sql = "SELECT * FROM PERIFERICO WHERE ID = ? AND ESTADO = 'A'";
        try {
            conn = DatabaseConecction.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Periferico(
                    rs.getInt("ID"),
                    rs.getInt("IDPC"),
                    rs.getString("NOMBRE"),
                    rs.getDouble("PRECIO"),
                    rs.getInt("ES_GAMER") == 1,
                    rs.getString("ESTADO")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return null;
    }
    
    public static List<Object[]> listarPerifericosConMarca() {
        List<Object[]> lista = new ArrayList<>();

        String sql = "SELECT p.ID, p.NOMBRE, p.PRECIO, pc.MARCA " +
                     "FROM PERIFERICO p " +
                     "INNER JOIN PC pc ON p.IDPC = pc.ID " +
                     "WHERE p.ESTADO = 'A'";
        try {
            conn = DatabaseConecction.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("ID"),
                    rs.getString("NOMBRE"),
                    rs.getDouble("PRECIO"),
                    rs.getString("MARCA") 
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    public static double calcularTotalPrecios() {
        String sql = "SELECT SUM(PRECIO) AS TOTAL FROM PERIFERICO WHERE ESTADO = 'A'";
        try {
            conn = DatabaseConecction.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("TOTAL");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return 0;
    }
}
    
    public static boolean actualizarPeriferico(Periferico p) {
        String sql = "UPDATE PERIFERICO SET NOMBRE = ?, PRECIO = ? WHERE ID = ?";
        try {
            conn = DatabaseConecction.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, p.getNombre());
            pstmt.setDouble(2, p.getPrecio());
            pstmt.setInt(3, p.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    public static boolean eliminarLogico(int pId) {
        String sql = "UPDATE PERIFERICO SET ESTADO = 'I' WHERE ID = ?";
        try {
            conn = DatabaseConecction.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
}

