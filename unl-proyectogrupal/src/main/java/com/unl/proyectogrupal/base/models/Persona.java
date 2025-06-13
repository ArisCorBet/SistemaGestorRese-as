package com.unl.proyectogrupal.base.models;

import java.util.Date;

import com.unl.proyectogrupal.base.models.enums.Genero;

public class Persona {
    private Integer id;
    private String nombre;
    private String telefono;
    private Date fecha_nacimiento;

    private Cuenta cuenta;

    public Persona() {

    }

    public Integer getId() {
        return id;
    }

    public void setId_persona(Integer id_persona) {
        this.id = id_persona;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public String toString() {
        return "id_persona=" + id +   ", nombre=" + nombre +  ", telefono=" + telefono
                + ", fecha_nacimiento=" + fecha_nacimiento;}

}
