package com.unl.proyectogrupal.base.models;

import java.util.Date;

public class Pelicula {
    private Integer id;
    private String titulo;
    private String sinopsis;
    private Integer duracion;
    private String trailer;
    private Date fechaEstreno;
    private Integer idGenero;

    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return this.titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getSinopsis() {
        return this.sinopsis;
    }
    
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
    
    public Integer getDuracion() {
        return this.duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getTrailer() {
        return this.trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Date getFechaEstreno() {
        return this.fechaEstreno;
    }

    public void setFechaEstreno(Date fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public Integer getIdGenero() {
        return this.idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public Object getIdPelicula() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdPelicula'");
    }
    
}
