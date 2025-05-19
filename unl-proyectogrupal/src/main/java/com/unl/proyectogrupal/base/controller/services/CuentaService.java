package com.unl.proyectogrupal.base.controller.services;

import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoCuenta;
import com.unl.proyectogrupal.base.models.Cuenta;
import com.unl.proyectogrupal.base.models.EnumTipoCuenta;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;


@BrowserCallable
@AnonymousAllowed


public class CuentaService {
    private DaoCuenta dc;

    public CuentaService(){
        dc = new DaoCuenta();
    }

    public void createCuenta(@NotEmpty String correo,@NotEmpty String clave,@NotEmpty Boolean estado,@NotEmpty EnumTipoCuenta tipoCuenta) throws Exception{
        dc.getObj().setCorreo(correo);
        dc.getObj().setClave(clave);
        dc.getObj().setEstado(estado);
        dc.getObj().setTipoCuenta(tipoCuenta);
        if(!dc.save())
            throw new  Exception("No se pudo guardar los datos de artista");
    }  

    public void updateArtista(@NotEmpty Integer id, @NotEmpty String correo,@NotEmpty String clave,@NotEmpty Boolean estado,@NotEmpty EnumTipoCuenta tipoCuenta) throws Exception{
        dc.setObj(dc.listAll().get(id - 1));
        dc.getObj().setCorreo(correo);
        dc.getObj().setClave(clave);
        dc.getObj().setEstado(Boolean.TRUE);
        dc.getObj().setTipoCuenta(tipoCuenta);

        if(!dc.update(id - 1))
            throw new  Exception("No se pudo modificar los datos de artista");
    }

    
}
