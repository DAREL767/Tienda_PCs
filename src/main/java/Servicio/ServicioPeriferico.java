/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import com.mycompany.model.Periferico;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class ServicioPeriferico {

    private static MongoCollection<Document> getColeccion() {
        MongoDatabase db = conexion.DatabaseConecction.getDatabase();
        return db.getCollection("Perifericos");
    }

    public static boolean grabarPeriferico(Periferico peri) {
        try {
            Document doc = new Document()
                .append("id", peri.getId())
                .append("nombre", peri.getNombre())
                .append("precio", peri.getPrecio()) 
                .append("es_gamer", peri.isEsGamer())
                .append("estado", "A"); 

            getColeccion().insertOne(doc);
            return true;
        } catch (Exception ex) {
            System.out.println("Error al grabar en Mongo: " + ex.getMessage());
            return false;
        }
    }

    public static Periferico buscarPorId(int id) {
        try {
            Document doc = getColeccion().find(Filters.and(
                Filters.eq("id", id),
                Filters.eq("estado", "A")
            )).first();

            if (doc != null) {
                Periferico p = new Periferico();
                p.setId(doc.getInteger("id"));
                p.setNombre(doc.getString("nombre"));
                
                if (doc.get("precio") instanceof Integer) {
                    p.setPrecio(doc.getInteger("precio").doubleValue());
                } else {
                    p.setPrecio(doc.getDouble("precio"));
                }
                
                if (doc.containsKey("es_gamer") && doc.get("es_gamer") != null) {
                    p.setEsGamer(doc.getBoolean("es_gamer"));
                } else {
                    p.setEsGamer(false);
                }
                
                return p;
            }
        } catch (Exception e) {
            System.err.println("Error al buscar periférico en Mongo: " + e.getMessage());
        }
        return null;
    }

    // 3. ACTUALIZAR POR ID PERSONALIZADO
    public static boolean actualizarPeriferico(int id, String nuevoNombre, double nuevoPrecio) {
        try {
            getColeccion().updateOne(
                Filters.eq("id", id),
                Updates.combine(
                    Updates.set("nombre", nuevoNombre),
                    Updates.set("precio", nuevoPrecio)
                )
            );
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar periférico en Mongo: " + e.getMessage());
            return false;
        }
    }
    
    // 4. ELIMINADO LÓGICO
    public static boolean eliminarLogico(int pId) {
        try {
            getColeccion().updateOne(
                Filters.eq("id", pId),
                Updates.set("estado", "I")
            );
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // 5. LISTAR
    public static List<Periferico> listarPerifericos() {
        List<Periferico> lista = new ArrayList<>();
        try {
            for (Document doc : getColeccion().find(Filters.eq("estado", "A"))) {
                Periferico p = new Periferico();
                p.setId(doc.getInteger("id"));
                p.setNombre(doc.getString("nombre"));
                if (doc.get("precio") instanceof Integer) {
                    p.setPrecio(doc.getInteger("precio").doubleValue());
                } else {
                    p.setPrecio(doc.getDouble("precio"));
                }
                lista.add(p);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    // 6. SUMATORIA
    public static double obtenerSumaPrecios() {
        double total = 0;
        try {
            for (Document doc : getColeccion().find(Filters.eq("estado", "A"))) {
                if (doc.get("precio") instanceof Integer) {
                    total += doc.getInteger("precio").doubleValue();
                } else {
                    total += doc.getDouble("precio");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return total;
    }
}
