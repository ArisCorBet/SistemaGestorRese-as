package com.unl.proyectogrupal.base.models;
import java.util.Date;

public class ReseniaPelicula {
    private Integer id;
    private String resenia;
    private float puntuacion;
    private Date fechaResenia;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getResenia() {
        return this.resenia;
    }
    
    public void setResenia(String resenia) {
        this.resenia = resenia;
    }
    
    public float getPuntuacion() {
        return this.puntuacion;
    }
    
    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }
    
    public Date getFechaResenia() {
        return this.fechaResenia;
    }
    
    public void setFechaResenia(Date fechaResenia) {
        this.fechaResenia = fechaResenia;
    }
}
