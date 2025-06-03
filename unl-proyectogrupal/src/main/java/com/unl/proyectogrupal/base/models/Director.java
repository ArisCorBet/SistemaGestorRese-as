package com.unl.proyectogrupal.base.models;

public class Director {
    private int idDirector;
    private String descripcion;
    private int aniosCarrera;

    public int getIdDirector() {
        return this.idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAniosCarrera() {
        return this.aniosCarrera;
    }

    public void setAniosCarrera(int aniosCarrera) {
        this.aniosCarrera = aniosCarrera;
    }
}
