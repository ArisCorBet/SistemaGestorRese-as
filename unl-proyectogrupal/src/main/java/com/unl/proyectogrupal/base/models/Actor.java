package com.unl.proyectogrupal.base.models;

public class Actor {
    private int idActor;
    private String nombre;
    private int anioCarrera;

    public int getIdActor() {
        return this.idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnioCarrera() {
        return this.anioCarrera;
    }

    public void setAnioCarrera(int anioCarrera) {
        this.anioCarrera = anioCarrera;
    }
}
