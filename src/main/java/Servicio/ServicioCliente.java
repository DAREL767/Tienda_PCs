/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mycompany.model.Cliente;
import org.bson.Document;
import java.util.ArrayList;

public class ServicioCliente {


    private static MongoCollection<Document> getColeccion() {
        MongoDatabase db = conexion.DatabaseConecction.getDatabase();
        return db.getCollection("Clientes");
    }


    public static boolean grabarCliente(int cedula, String nombre, String correo) {
        try {

            Document existe = getColeccion().find(Filters.and(
                Filters.eq("cedula", cedula),
                Filters.eq("estado", "Activo")
            )).first();
            
            if (existe != null) {
                return false; 
            }

            Document doc = new Document()
                .append("cedula", cedula)
                .append("nombre", nombre)
                .append("correo", correo)
                .append("estado", "Activo");

            getColeccion().insertOne(doc);
            return true;
        } catch (Exception ex) {
            System.err.println("Error al grabar cliente: " + ex.getMessage());
            return false;
        }
    }


    public static Cliente buscarPorCedula(int cedula) {
        try {
            Document doc = getColeccion().find(Filters.and(
                Filters.eq("cedula", cedula),
                Filters.eq("estado", "Activo")
            )).first();

            if (doc != null) {
                Cliente c = new Cliente();
                c.setId(doc.getObjectId("_id"));
                c.setCedula(doc.getInteger("cedula"));
                c.setNombre(doc.getString("nombre"));
                c.setCorreo(doc.getString("correo"));
                return c;
            }
        } catch (Exception e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }


    public static boolean actualizarCliente(int cedula, String nuevoNombre, String nuevoCorreo) {
        try {
            com.mongodb.client.MongoDatabase db = conexion.DatabaseConecction.getDatabase();
            db.getCollection("Clientes").updateOne(
                com.mongodb.client.model.Filters.eq("cedula", cedula),
                com.mongodb.client.model.Updates.combine(
                    com.mongodb.client.model.Updates.set("nombre", nuevoNombre),
                    com.mongodb.client.model.Updates.set("correo", nuevoCorreo)
                )
            );
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar cliente en Mongo: " + e.getMessage());
            return false;
        }
    }


    public static boolean eliminarLogico(int cedula) {
        try {
            getColeccion().updateOne(
                Filters.eq("cedula", cedula),
                Updates.set("estado", "Inactivo") 
            );
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<Cliente> listarClientes() {
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            for (Document doc : getColeccion().find(Filters.eq("estado", "Activo"))) {
                Cliente c = new Cliente();
                c.setId(doc.getObjectId("_id"));
                c.setCedula(doc.getInteger("cedula"));
                c.setNombre(doc.getString("nombre"));
                c.setCorreo(doc.getString("correo"));
                lista.add(c);
            }
        } catch (Exception e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }
}

