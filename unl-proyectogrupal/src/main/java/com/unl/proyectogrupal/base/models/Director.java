package com.unl.proyectogrupal.base.models;

public class Director {
    private int idDirector;
    private String descripcionDirector;
    private int aniosCarrera;

    public int getIdDirector() {
        return this.idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public String getDescripcionDirector() {
        return this.descripcionDirector;
    }

    public void setDescripcionDirector(String descripcionDirector) {
        this.descripcionDirector = descripcionDirector;
    }

    public int getAniosCarrera() {
        return this.aniosCarrera;
    }

    public void setAniosCarrera(int aniosCarrera) {
        this.aniosCarrera = aniosCarrera;
    }

}
