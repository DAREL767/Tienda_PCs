/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import com.mycompany.model.Pc;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jamed
 */
public class ServicioPC { 
    
    private static MongoCollection<Document> getColeccion() {
        MongoDatabase db = conexion.DatabaseConecction.getDatabase();
        return db.getCollection("PCs");
    }
    
    public static boolean guardarPc(Pc miPc, ObjectId idClienteMongo, ObjectId idPerifericoMongo) {
        try {
            if (idClienteMongo == null || idPerifericoMongo == null) {
                System.err.println("Error: No se puede crear el PC sin un Cliente o Periférico válido.");
                return false;
            }

            Document doc = new Document()
                .append("id", miPc.getId()) 
                .append("marca", miPc.getMarca())
                .append("precio", miPc.getPrecio()) 
                .append("id_cliente", idClienteMongo)      
                .append("id_periferico", idPerifericoMongo)  
                .append("estado", "A");

            getColeccion().insertOne(doc);
            System.out.println("Pc guardado.");
            return true;

        } catch (Exception e) {
            System.err.println("Error al guardar Pc: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean actualizarPc(int id, String nuevaMarca, double nuevoPrecio) {
        try {
            com.mongodb.client.MongoDatabase db = conexion.DatabaseConecction.getDatabase();
            db.getCollection("PCs").updateOne(
                com.mongodb.client.model.Filters.eq("id", id),
                com.mongodb.client.model.Updates.combine(
                    com.mongodb.client.model.Updates.set("marca", nuevaMarca),
                    com.mongodb.client.model.Updates.set("precio", nuevoPrecio)
                )
            );
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar PC en Mongo: " + e.getMessage());
            return false;
        }
    }
    
    public static Pc buscarPorId(int id) {
        try {
            com.mongodb.client.MongoDatabase db = conexion.DatabaseConecction.getDatabase();
            org.bson.Document doc = db.getCollection("PCs").find(com.mongodb.client.model.Filters.and(
                com.mongodb.client.model.Filters.eq("id", id),
                com.mongodb.client.model.Filters.eq("estado", "A")
            )).first();

            if (doc != null) {
                return new Pc(
                    doc.getInteger("id"),
                    doc.getString("marca"),
                    doc.getDouble("precio"),
                    doc.getString("estado")
                );
            }
        } catch (Exception e) {
            System.err.println("Error al buscar PC en Mongo: " + e.getMessage());
        }
        return null;
    }
    
    public static java.util.List<org.bson.Document> listarPcs() {
        java.util.List<org.bson.Document> listaCompleta = new java.util.ArrayList<>();
        try {

            com.mongodb.client.MongoDatabase db = conexion.DatabaseConecction.getDatabase();
            

            for (org.bson.Document pc : db.getCollection("PCs").find(com.mongodb.client.model.Filters.eq("estado", "A"))) {
                
                org.bson.types.ObjectId idCliente = pc.getObjectId("id_cliente");
                if (idCliente != null) {
                    org.bson.Document cliente = db.getCollection("Clientes").find(com.mongodb.client.model.Filters.eq("_id", idCliente)).first();
                    if (cliente != null) {
                        pc.append("nombre_cliente", cliente.getString("nombre"));
                    }
                }

                org.bson.types.ObjectId idPeriferico = pc.getObjectId("id_periferico");
                if (idPeriferico != null) {
                    org.bson.Document periferico = db.getCollection("Perifericos").find(com.mongodb.client.model.Filters.eq("_id", idPeriferico)).first();
                    if (periferico != null) {
                        pc.append("nombre_periferico", periferico.getString("nombre"));
                    }
                }

                listaCompleta.add(pc);
            }
        } catch (Exception e) {
            System.err.println("Error al listar PCs con relaciones en NoSQL: " + e.getMessage());
        }
        return listaCompleta;
    }
    
    public static void eliminarPc(int id) {
        try {
            getColeccion().updateOne(
                Filters.eq("id", id),
                Updates.set("estado", "I")
            );
            System.out.println("Registro de PC marcado como inactivo.");
        } catch (Exception e) {
            System.err.println("Error al eliminar: " + e.getMessage());
        }
    }
    
    public static double calcularGranTotal() {
        double total = 0;
        try {
            for (Document doc : getColeccion().find(Filters.eq("estado", "A"))) {
                total += doc.getDouble("precio");
            }
        } catch (Exception e) {
            System.err.println("Error al calcular suma: " + e.getMessage());
        }
        return total;
    }
}
