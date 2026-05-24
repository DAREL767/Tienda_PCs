/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author ORIANA BONILLA
 */
public class DatabaseConecction {
    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;
    
    private static final String DATABASE_NAME = "PER2026"; 

    public static MongoDatabase getDatabase() {
        if (database == null) {
            try {
                mongoClient = MongoClients.create("mongodb://localhost:27017");
                database = mongoClient.getDatabase(DATABASE_NAME);
                System.out.println("Conexión exitosa a MongoDB: " + DATABASE_NAME);
            } catch (Exception e) {
                System.out.println("Error al conectar a MongoDB: " + e.getMessage());
            }
        }
        return database;
    }

    public static void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("🔌Conexión de MongoDB cerrada.");
        }
    }
}
