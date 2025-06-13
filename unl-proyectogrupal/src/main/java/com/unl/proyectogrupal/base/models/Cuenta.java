package com.unl.proyectogrupal.base.models;

import com.unl.proyectogrupal.base.models.enums.Estado_cuenta;
import com.unl.proyectogrupal.base.models.enums.Tipo_cuenta;

public class Cuenta {
    private Integer id;
    private String correo;
    private String contrasenia;
    private Estado_cuenta estado_cuenta;
    private Tipo_cuenta tipo_cuenta;
    private Integer id_persona;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id_cuenta) {
        this.id = id_cuenta;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Estado_cuenta getEstado_cuenta() {
        return estado_cuenta;
    }

    public void setEstado_cuenta(Estado_cuenta estado_cuenta) {
        this.estado_cuenta = estado_cuenta;
    }
    public Tipo_cuenta getTipo_cuenta() {
        return tipo_cuenta;
    }

    public void setTipo_cuenta(Tipo_cuenta tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }


    public Integer getId_persona() {
        return id_persona;
    }

    public void setId_persona(Integer id_persona) {
        this.id_persona = id_persona;
    }

    @Override
    public String toString() {
        return "id_cuenta=" + id + ", correo=" + correo + ", contrasenia=" + contrasenia
                + ", tipo_cuenta=" + tipo_cuenta;
    }

}
