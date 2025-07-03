package com.unl.proyectogrupal.base.models;

public class Director {
    private int idDirector;

    private String nombre;

    private int anioCarrera;

    public int getIdDirector() {
        return this.idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }


    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;

    }

    public int getAniosCarrera() {
        return this.anioCarrera;
    }

    public void setAniosCarrera(int aniosCarrera) {
        this.anioCarrera = aniosCarrera;
    }

}
