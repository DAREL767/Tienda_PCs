/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import com.mycompany.model.Periferico;
import conexion.DatabaseConecction; 
import java.sql.*;
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

        
        String sql = "INSERT INTO PERIFERICO (id, idPc, nombre, precio, es_gamer, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConecction.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, peri.getId());
            pstmt.setInt(2, peri.getIdPc());
            pstmt.setString(3, peri.getNombre());
            pstmt.setDouble(4, peri.getPrecio());
            pstmt.setInt(5, peri.isEsGamer() ? 1 : 0); 
            pstmt.setString(6, "A"); 

            return pstmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al grabar: " + ex.getMessage());
            return false;
        }
    }

    public static Periferico buscarPerifericoPorId(int idBusqueda) {
    String sql = "SELECT * FROM PERIFERICO WHERE ID = ?";
    try (Connection conn = DatabaseConecction.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, idBusqueda);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                
                Periferico p = new Periferico(
                    rs.getInt("ID"),
                    rs.getInt("IDPC"),
                    rs.getString("NOMBRE"),
                    rs.getDouble("PRECIO"),
                    rs.getString("ESTADO")
                );

                p.setEsGamer(rs.getInt("ES_GAMER") == 1);
                return p;
            }
        }
    } catch (SQLException ex) {
        System.err.println("Error al buscar Periférico: " + ex.getMessage());
    }
    return null;
}
    
    public static List<Periferico> listarTodos() {
        List<Periferico> lista = new ArrayList<>();
        String sql = "SELECT * FROM PERIFERICO WHERE ESTADO = 'A'";
        
        try (Connection conn = DatabaseConecction.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Periferico(
                    rs.getInt("ID"),
                    rs.getInt("IDPC"),
                    rs.getString("NOMBRE"),
                    rs.getDouble("PRECIO"),
                    rs.getString("ESTADO")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
    
    public static double obtenerSumaPrecios() {
        String sql = "SELECT SUM(PRECIO) FROM PERIFERICO WHERE ESTADO = 'A'";
        try (Connection conn = DatabaseConecction.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static boolean actualizarPeriferico(Periferico p) {
        String sql = "UPDATE PERIFERICO SET NOMBRE = ?, PRECIO = ?, IDPC = ? WHERE ID = ?";
        try (Connection conn = conexion.DatabaseConecction.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getNombre());
            pstmt.setDouble(2, p.getPrecio());
            pstmt.setInt(3, p.getIdPc()); 
            pstmt.setInt(4, p.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al actualizar: " + ex.getMessage());
            return false;
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
