/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;
import org.bson.types.ObjectId;

/**
 *
 * @author jamed
 */
public class Periferico {
    private ObjectId idMongo;
    private int id; 
    private String nombre;
    private double precio;
    private boolean esGamer;
    private String estado;

    public Periferico() {
    }
    
    public Periferico(int id, String nombre, double precio, boolean esGamer, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.esGamer = esGamer;
        this.estado = estado;
    }

    public boolean isEsGamer() {
        return esGamer;
    }

    public void setEsGamer(boolean esGamer) {
        this.esGamer = esGamer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }   
    
    
}
