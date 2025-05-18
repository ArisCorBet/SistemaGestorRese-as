package com.unl.proyectogrupal.base.models;

public class Cuenta {
    private Integer id;
    private String correo;
    private String clave;
    private Boolean estado;
    private EnumTipoCuenta tipoCuenta;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean isEstado() {
        return this.estado;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public EnumTipoCuenta getTipoCuenta() {
        return this.tipoCuenta;
    }

    public void setTipoCuenta(EnumTipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
    
}
