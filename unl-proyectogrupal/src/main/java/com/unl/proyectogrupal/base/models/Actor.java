package com.unl.proyectogrupal.base.models;

public class Actor {

    private int idActor;
    private String descripcion;
    private int anioCarrera;

    public int getIdActor() {
        return this.idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAnioCarrera() {
        return this.anioCarrera;
    }

    public void setAnioCarrera(int anioCarrera) {
        this.anioCarrera = anioCarrera;
    }

}
