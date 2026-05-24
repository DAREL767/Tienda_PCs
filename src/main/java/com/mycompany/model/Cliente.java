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
public class Cliente {
    private ObjectId id; 
    private int cedula;   
    private String nombre;
    private String correo;
    private String estado; 

    public Cliente() {
    }

    public Cliente(ObjectId id, int cedula, String nombre, String correo, String estado) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.estado = estado;
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public int getCedula() { return cedula; }
    public void setCedula(int cedula) { this.cedula = cedula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
