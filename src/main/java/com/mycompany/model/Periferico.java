/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 *
 * @author jamed
 */
public class Periferico {
    
    private int id;
    private int idPc;
    private String nombre;
    private double precio;
    private boolean esGamer;
    private String estado;

    public Periferico(int id, int idPc, String nombre, double precio, String estado) {
        this.id = id;
        this.idPc = idPc;
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

    public int getIdPc() {
        return idPc;
    }

    public void setIdPc(int idPc) {
        this.idPc = idPc;
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
