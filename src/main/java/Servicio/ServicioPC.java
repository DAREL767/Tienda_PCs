/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import com.mycompany.model.Pc;
import conexion.DatabaseConecction;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jamed
 */
public class ServicioPC { 
    
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    
    
    public static boolean grabarPC(Pc comp) {
    String sql = "INSERT INTO PC (ID, MARCA, PRECIO, ESTADO) VALUES (?, ?, ?, ?)";
    
    try {
        
        conn = DatabaseConecction.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, comp.getId());
        pstmt.setString(2, comp.getMarca());
        pstmt.setDouble(3, comp.getPrecio());
        pstmt.setString(4, "A"); 
        
        return pstmt.executeUpdate() > 0;
        
    } catch (SQLException ex) {
        System.out.println("Error SQL: " + ex.getMessage());
        return false;
    } finally {
        cerrarConexiones();
    }
}
    
    public static boolean actualizarPC(Pc p) {
        String sql = "UPDATE PC SET MARCA = ?, PRECIO = ? WHERE ID = ?";
        try {
            
            conn = DatabaseConecction.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, p.getMarca());
            pstmt.setDouble(2, p.getPrecio());
            pstmt.setInt(3, p.getId());
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            return false;
        } finally {
            cerrarConexiones();
        }
    }
}
    
    public static Pc buscarPorIdOracle(int id) {
    Pc pc = null;

    try {
        Connection conn = DatabaseConecction.getConnection();

        String sql = "SELECT * FROM PC WHERE ID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            pc = new Pc(
                rs.getInt("ID"),
                rs.getString("MARCA"),
                rs.getDouble("PRECIO"),
                rs.getString("ESTADO")
            );
        }

        rs.close();
        ps.close();
        conn.close();

    } catch (Exception e) {
        System.out.println("Error buscar: " + e);
    }

    return pc;
}
    
    public static List<Pc> listarPCsOracle() {
        int id;
        String marca;
        double precio;
        String estado;

        Pc comp = null;
        List<Pc> Pcs = new ArrayList<>();

        try {
            conn = DatabaseConecction.getConnection();
            if (conn != null) {
                stmt = conn.createStatement();
               String selectDataSQL = "SELECT ID, MARCA, PRECIO, ESTADO FROM PC WHERE ESTADO = 'A' ORDER BY ID";
                rs = stmt.executeQuery(selectDataSQL);
                
                while (rs.next()) {
                    id = rs.getInt("ID");
                    marca = rs.getString("MARCA");
                    precio = rs.getDouble("PRECIO");
                    estado = rs.getString("ESTADO");
                    comp = new Pc(id, marca, precio, estado);
                    Pcs.add(comp);
                }   
            }
        } catch (Exception ex) {
            System.out.println("Error! " + ex);
        } finally {
        cerrarConexiones();
        }
        return Pcs;
    }
    
    public static boolean eliminarLogico(int id) {

        String sql = "UPDATE PC SET ESTADO = 'I' WHERE ID = ?";
        try {
            conn = DatabaseConecction.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    public static double calcularTotalPrecios() {
        String sql = "SELECT SUM(PRECIO) AS TOTAL FROM PC WHERE ESTADO = 'A'";
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
