/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import com.mycompany.model.Pc;
import conexion.DatabaseConecction;
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
    
    
    public static void guardarPc(Pc miPc) {
        String sql = "INSERT INTO PC (id, marca, precio, estado) VALUES (?, ?, ?, ?)";
        
        try (Connection con = DatabaseConecction.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, miPc.getId());
            ps.setString(2, miPc.getMarca());
            ps.setDouble(3, miPc.getPrecio());
            ps.setString(4, miPc.getEstado());
            
            ps.executeUpdate();
            System.out.println("Pc guardado: " + miPc.getMarca());
            
        } catch (SQLException e) {
            System.err.println("Error al guardar Pc: " + e.getMessage());
        }
    }
    
    public static void actualizarPc(Pc miPc) {
        String sql = "UPDATE PC SET marca=?, precio=?, estado=? WHERE id=?";
        
        try (Connection con = DatabaseConecction.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, miPc.getMarca());
            ps.setDouble(2, miPc.getPrecio());
            ps.setString(3, miPc.getEstado());
            ps.setInt(4, miPc.getId());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar Pc: " + e.getMessage());
        }
    }
    
    public static Pc buscarPcPorId(int idBusqueda) {
        String sql = "SELECT id, marca, precio, estado FROM PC WHERE id = ?";
        try (Connection con = DatabaseConecction.getConnection(); // Corregido
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idBusqueda);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pc(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getDouble("precio"),
                        rs.getString("estado")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar PC: " + e.getMessage());
        }
        return null; 
    }
    
    public static List<Pc> listarPcs() {
        List<Pc> lista = new ArrayList<>();
        String sql = "SELECT id, marca, precio, estado FROM PC WHERE estado = 'A'";
        
        try (Connection con = DatabaseConecction.getConnection(); // Corregido
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Pc(
                    rs.getInt("id"),
                    rs.getString("marca"),
                    rs.getDouble("precio"),
                    rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar PCs: " + e.getMessage());
        }
        return lista;
    }
    
    public void eliminarPc(int id) {
    String sql = "DELETE FROM PC WHERE id = ?";
    
    try (Connection con = DatabaseConecction.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Registro de PC eliminado con éxito.");
        
    } catch (SQLException e) {
        System.err.println("Error al eliminar (verifica si tiene periféricos): " + e.getMessage());
    }
}
    
    public static double calcularGranTotal() {
        double total = 0;
        String sql = "SELECT SUM(precio) AS total_inventario FROM PC WHERE estado = 'A'";
        
        try (Connection con = DatabaseConecction.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                total = rs.getDouble("total_inventario");
            }
        } catch (SQLException e) {
            System.err.println("Error al calcular suma: " + e.getMessage());
        }
        return total;
    }
}        
