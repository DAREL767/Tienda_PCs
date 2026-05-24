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
public class ComboItem {

    private ObjectId idMongo;
    private String label;

    public ComboItem(ObjectId idMongo, String label) {
        this.idMongo = idMongo;
        this.label = label;
    }

    public ObjectId getIdMongo() {
        return idMongo;
    }

    public void setIdMongo(ObjectId idMongo) {
        this.idMongo = idMongo;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    @Override
    public String toString() {
        return this.label; 
    }

}
