package com.unl.proyectogrupal.base.models;

public class Actor {

    private int idActor;
    private String descripcionActor;
    private int anioCarrera;

    public int getIdActor() {
        return this.idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

    public String getDescripcionActor() {
        return this.descripcionActor;
    }

    public void setDescripcionActor(String descripcionActor) {
        this.descripcionActor = descripcionActor;
    }

    public int getAnioCarrera() {
        return this.anioCarrera;
    }

    public void setAnioCarrera(int anioCarrera) {
        this.anioCarrera = anioCarrera;
    }

}
